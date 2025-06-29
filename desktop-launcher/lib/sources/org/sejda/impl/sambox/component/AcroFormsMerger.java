package org.sejda.impl.sambox.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.LookupTable;
import org.sejda.impl.sambox.util.AcroFormUtils;
import org.sejda.model.pdf.form.AcroFormPolicy;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.sejda.sambox.pdmodel.interactive.form.PDField;
import org.sejda.sambox.pdmodel.interactive.form.PDFieldFactory;
import org.sejda.sambox.pdmodel.interactive.form.PDNonTerminalField;
import org.sejda.sambox.pdmodel.interactive.form.PDTerminalField;
import org.sejda.sambox.pdmodel.interactive.form.PDVariableText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/AcroFormsMerger.class */
public class AcroFormsMerger {
    private static final Logger LOG = LoggerFactory.getLogger(AcroFormsMerger.class);
    private static final COSName[] FIELD_KEYS = {COSName.FT, COSName.PARENT, COSName.KIDS, COSName.T, COSName.TU, COSName.TM, COSName.FF, COSName.V, COSName.DV, COSName.Q, COSName.DS, COSName.RV, COSName.OPT, COSName.MAX_LEN, COSName.TI, COSName.I, COSName.LOCK, COSName.SV, COSName.DATAPREP};
    private static final COSName[] WIDGET_KEYS = {COSName.TYPE, COSName.SUBTYPE, COSName.RECT, COSName.CONTENTS, COSName.P, COSName.NM, COSName.M, COSName.F, COSName.AP, COSName.AS, COSName.BORDER, COSName.C, COSName.STRUCT_PARENT, COSName.OC, COSName.AF, COSName.CA, COSName.CA_NS, COSName.LANG, COSName.BM, COSName.H, COSName.MK, COSName.A, COSName.BS, COSName.PMD};
    private AcroFormPolicy policy;
    private PDAcroForm form;
    private String random = Long.toString(UUID.randomUUID().getMostSignificantBits(), 36);
    private Long counter = 0L;
    private final BiFunction<PDTerminalField, LookupTable<PDField>, PDTerminalField> createOrReuseTerminalField = (existing, fieldsLookup) -> {
        PDField previouslyCreated = (PDField) Optional.ofNullable(getMergedField(existing.getFullyQualifiedName())).orElseGet(() -> {
            return (PDField) fieldsLookup.lookup(existing);
        });
        boolean shouldCreateNew = Objects.isNull(previouslyCreated);
        boolean shouldCreateNewAndRename = (previouslyCreated == null || (previouslyCreated.getClass().equals(existing.getClass()) && previouslyCreated.getValueAsString().equals(existing.getValueAsString()))) ? false : true;
        if (shouldCreateNew || shouldCreateNewAndRename) {
            previouslyCreated = PDFieldFactory.createFieldAddingChildToParent(this.form, existing.getCOSObject().duplicate(), (PDNonTerminalField) fieldsLookup.lookup(existing.getParent()));
            if (shouldCreateNewAndRename) {
                LOG.warn("Cannot merge terminal field because another field with the same name but different value already exists: {}", existing.getFullyQualifiedName());
                Long lValueOf = Long.valueOf(this.counter.longValue() + 1);
                this.counter = lValueOf;
                previouslyCreated.setPartialName(String.format("%s%s%d", removeDotsIfAny(existing.getPartialName()), this.random, lValueOf));
            }
            previouslyCreated.getCOSObject().removeItem(COSName.KIDS);
            fieldsLookup.addLookupEntry(existing, previouslyCreated);
        }
        if (!previouslyCreated.isTerminal()) {
            LOG.warn("Cannot merge terminal field because a non terminal field with the same name already exists: {}", existing.getFullyQualifiedName());
            return null;
        }
        return (PDTerminalField) previouslyCreated;
    };
    private final BiFunction<PDTerminalField, LookupTable<PDField>, PDTerminalField> createRenamingTerminalField = (existing, fieldsLookup) -> {
        PDTerminalField newField = (PDTerminalField) PDFieldFactory.createFieldAddingChildToParent(this.form, existing.getCOSObject().duplicate(), (PDNonTerminalField) fieldsLookup.lookup(existing.getParent()));
        if (Objects.nonNull(getMergedField(existing.getFullyQualifiedName())) || fieldsLookup.hasLookupFor(existing)) {
            String partialName = removeDotsIfAny(existing.getPartialName());
            Long lValueOf = Long.valueOf(this.counter.longValue() + 1);
            this.counter = lValueOf;
            newField.setPartialName(String.format("%s%s%d", partialName, this.random, lValueOf));
            LOG.info("Existing terminal field renamed from {} to {}", partialName, newField.getPartialName());
        }
        newField.getCOSObject().removeItem(COSName.KIDS);
        fieldsLookup.addLookupEntry(existing, newField);
        return newField;
    };
    private final BiConsumer<PDField, LookupTable<PDField>> createOrReuseNonTerminalField = (existing, fieldsLookup) -> {
        if (!fieldsLookup.hasLookupFor(existing)) {
            PDField mergedField = getMergedField(existing.getFullyQualifiedName());
            if (Objects.isNull(mergedField)) {
                mergedField = PDFieldFactory.createFieldAddingChildToParent(this.form, existing.getCOSObject().duplicate(), (PDNonTerminalField) fieldsLookup.lookup(existing.getParent()));
                mergedField.getCOSObject().removeItem(COSName.KIDS);
            } else if (mergedField.isTerminal()) {
                mergedField = PDFieldFactory.createFieldAddingChildToParent(this.form, existing.getCOSObject().duplicate(), (PDNonTerminalField) fieldsLookup.lookup(existing.getParent()));
                mergedField.getCOSObject().removeItem(COSName.KIDS);
                Long lValueOf = Long.valueOf(this.counter.longValue() + 1);
                this.counter = lValueOf;
                mergedField.setPartialName(String.format("%s%s%d", removeDotsIfAny(existing.getPartialName()), this.random, lValueOf));
                LOG.warn("Cannot reuse merged terminal field {} as a non terminal field, renaming it to {}", existing.getPartialName(), mergedField.getPartialName());
            }
            fieldsLookup.addLookupEntry(existing, mergedField);
        }
    };
    private final BiConsumer<PDField, LookupTable<PDField>> createRenamingNonTerminalField = (field, fieldsLookup) -> {
        PDField newField = PDFieldFactory.createFieldAddingChildToParent(this.form, field.getCOSObject().duplicate(), (PDNonTerminalField) fieldsLookup.lookup(field.getParent()));
        if (getMergedField(field.getFullyQualifiedName()) != null || fieldsLookup.hasLookupFor(field)) {
            Long lValueOf = Long.valueOf(this.counter.longValue() + 1);
            this.counter = lValueOf;
            newField.setPartialName(String.format("%s%s%d", removeDotsIfAny(field.getPartialName()), this.random, lValueOf));
            LOG.info("Existing non terminal field renamed from {} to {}", field.getPartialName(), newField.getPartialName());
        }
        newField.getCOSObject().removeItem(COSName.KIDS);
        fieldsLookup.addLookupEntry(field, newField);
    };

    private static String removeDotsIfAny(String s) {
        if (s == null) {
            return null;
        }
        return s.replace(".", "");
    }

    private PDField getMergedField(String fullyQualifiedName) {
        Optional optionalOfNullable = Optional.ofNullable(fullyQualifiedName);
        PDAcroForm pDAcroForm = this.form;
        Objects.requireNonNull(pDAcroForm);
        return (PDField) optionalOfNullable.map(pDAcroForm::getField).orElse(null);
    }

    public AcroFormsMerger(AcroFormPolicy policy, PDDocument destination) {
        this.policy = policy;
        this.form = new PDAcroForm(destination);
    }

    public void mergeForm(PDAcroForm originalForm, LookupTable<PDAnnotation> annotationsLookup) {
        if (Objects.nonNull(originalForm)) {
            if (originalForm.hasXFA()) {
                LOG.warn("The AcroForm has XFA resources which will be ignored");
            }
            LOG.debug("Merging acroforms with policy {}", this.policy);
            switch (this.policy) {
                case MERGE_RENAMING_EXISTING_FIELDS:
                    updateForm(originalForm, annotationsLookup, this.createRenamingTerminalField, this.createRenamingNonTerminalField);
                    break;
                case MERGE:
                    updateForm(originalForm, annotationsLookup, this.createOrReuseTerminalField, this.createOrReuseNonTerminalField);
                    break;
                case FLATTEN:
                    updateForm(originalForm, annotationsLookup, this.createRenamingTerminalField, this.createRenamingNonTerminalField);
                    flatten();
                    break;
                default:
                    LOG.debug("Discarding acroform");
                    break;
            }
        }
        LOG.debug("Skipped acroform merge, nothing to merge");
    }

    private void removeFieldKeysFromWidgets(Collection<PDAnnotationWidget> annotations) {
        annotations.stream().map((v0) -> {
            return v0.getCOSObject();
        }).forEach(a -> {
            Arrays.stream(FIELD_KEYS).forEach(key -> {
                if (this.policy == AcroFormPolicy.MERGE_RENAMING_EXISTING_FIELDS && key == COSName.Q) {
                    return;
                }
                a.removeItem(key);
            });
            if (annotations.size() == 1 && this.policy != AcroFormPolicy.MERGE_RENAMING_EXISTING_FIELDS) {
                a.removeItem(COSName.DA);
            }
        });
        LOG.trace("Removed fields keys from widget annotations");
    }

    private void updateForm(PDAcroForm originalForm, LookupTable<PDAnnotation> annotationsLookup, BiFunction<PDTerminalField, LookupTable<PDField>, PDTerminalField> getTerminalField, BiConsumer<PDField, LookupTable<PDField>> createNonTerminalField) {
        AcroFormUtils.mergeDefaults(originalForm, this.form);
        LookupTable<PDField> fieldsLookup = new LookupTable<>();
        Set<PDAnnotationWidget> allRelevantWidgets = (Set) annotationsLookup.keys().stream().filter(a -> {
            return a instanceof PDAnnotationWidget;
        }).map(a2 -> {
            return (PDAnnotationWidget) a2;
        }).collect(Collectors.toSet());
        Set<PDField> rootFields = new HashSet<>();
        originalForm.getFieldTree().stream().forEach(f -> {
            Objects.requireNonNull(allRelevantWidgets);
            mergeField(f, annotationsLookup, getTerminalField, createNonTerminalField, fieldsLookup, Optional.of((v1) -> {
                r6.remove(v1);
            }));
        });
        Stream<PDField> stream = originalForm.getFields().stream();
        Objects.requireNonNull(fieldsLookup);
        Stream streamFilter = stream.map((v1) -> {
            return r1.lookup(v1);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        });
        Objects.requireNonNull(rootFields);
        streamFilter.forEach((v1) -> {
            r1.add(v1);
        });
        if (!allRelevantWidgets.isEmpty()) {
            LOG.info("Found relevant widget annotations ({}) not linked to the form", Integer.valueOf(allRelevantWidgets.size()));
            PDAcroForm dummy = new PDAcroForm(null);
            allRelevantWidgets.forEach(w -> {
                COSDictionary cOSObject = w.getCOSObject();
                while (true) {
                    COSDictionary currentField = cOSObject;
                    if (Objects.nonNull(currentField.getDictionaryObject(COSName.PARENT, COSDictionary.class))) {
                        cOSObject = (COSDictionary) currentField.getDictionaryObject(COSName.PARENT, COSDictionary.class);
                    } else {
                        dummy.addFields(List.of(PDFieldFactory.createField(originalForm, currentField, null)));
                        return;
                    }
                }
            });
            dummy.getFieldTree().stream().forEach(f2 -> {
                mergeField(f2, annotationsLookup, getTerminalField, createNonTerminalField, fieldsLookup, Optional.empty());
            });
            Stream<PDField> stream2 = dummy.getFields().stream();
            Objects.requireNonNull(fieldsLookup);
            Stream streamFilter2 = stream2.map((v1) -> {
                return r1.lookup(v1);
            }).filter((v0) -> {
                return Objects.nonNull(v0);
            });
            Objects.requireNonNull(rootFields);
            streamFilter2.forEach((v1) -> {
                r1.add(v1);
            });
        }
        List<PDField> currentRoots = this.form.getFields();
        this.form.addFields((Collection) rootFields.stream().filter(f3 -> {
            return !currentRoots.contains(f3);
        }).collect(Collectors.toList()));
        mergeCalculationOrder(originalForm, fieldsLookup);
    }

    private void mergeField(PDField field, LookupTable<PDAnnotation> annotationsLookup, BiFunction<PDTerminalField, LookupTable<PDField>, PDTerminalField> getTerminalField, BiConsumer<PDField, LookupTable<PDField>> createNonTerminalField, LookupTable<PDField> fieldsLookup, Optional<Consumer<PDAnnotationWidget>> onProcessedWidget) {
        if (!field.isTerminal()) {
            createNonTerminalField.accept(field, fieldsLookup);
            return;
        }
        List<PDAnnotationWidget> relevantWidgets = findMappedWidgetsFor((PDTerminalField) field, annotationsLookup);
        if (!relevantWidgets.isEmpty()) {
            PDTerminalField terminalField = getTerminalField.apply((PDTerminalField) field, fieldsLookup);
            if (Objects.nonNull(terminalField)) {
                removeFieldKeysFromWidgets(relevantWidgets);
                for (PDAnnotationWidget widget : relevantWidgets) {
                    terminalField.addWidgetIfMissing(widget);
                    onProcessedWidget.ifPresent(c -> {
                        field.getWidgets().forEach(c);
                    });
                }
                terminalField.getCOSObject().removeItems(WIDGET_KEYS);
                return;
            }
            return;
        }
        LOG.debug("Discarded not relevant field {}", field.getFullyQualifiedName());
    }

    private void mergeCalculationOrder(PDAcroForm originalForm, LookupTable<PDField> fieldsLookup) {
        Stream<PDField> stream = originalForm.getCalculationOrder().stream();
        Objects.requireNonNull(fieldsLookup);
        List<PDField> co = stream.map((v1) -> {
            return r1.lookup(v1);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).toList();
        if (co.size() > 0) {
            COSArray formCo = (COSArray) Optional.ofNullable((COSArray) this.form.getCOSObject().getDictionaryObject(COSName.CO, COSArray.class)).orElseGet(COSArray::new);
            for (PDField field : co) {
                formCo.add((COSObjectable) field);
            }
            this.form.setCalculationOrder(formCo);
        }
    }

    private List<PDAnnotationWidget> findMappedWidgetsFor(PDTerminalField field, LookupTable<PDAnnotation> annotationsLookup) {
        Stream<PDAnnotationWidget> stream = field.getWidgets().stream();
        Objects.requireNonNull(annotationsLookup);
        return (List) stream.map((v1) -> {
            return r1.lookup(v1);
        }).filter(w -> {
            return w instanceof PDAnnotationWidget;
        }).map(w2 -> {
            return (PDAnnotationWidget) w2;
        }).collect(Collectors.toList());
    }

    private void flatten() {
        try {
            List<PDField> fields = new ArrayList<>();
            Iterator<PDField> it = this.form.getFieldTree().iterator();
            while (it.hasNext()) {
                PDField field = it.next();
                fields.add(field);
                if (field instanceof PDVariableText) {
                    PDVariableText variableText = (PDVariableText) field;
                    AcroFormUtils.ensureValueCanBeDisplayed(variableText, this.form.getDocument());
                }
            }
            this.form.flatten(fields, this.form.isNeedAppearances());
        } catch (IOException | UnsupportedOperationException ex) {
            LOG.warn("Failed to flatten form", ex);
        }
    }

    public PDAcroForm getForm() {
        Iterator<PDField> it = this.form.getFieldTree().iterator();
        while (it.hasNext()) {
            PDField current = it.next();
            if (!current.isTerminal() && !((PDNonTerminalField) current).hasChildren()) {
                LOG.info("Removing non terminal field with no child {}", current.getFullyQualifiedName());
                if (Objects.nonNull(current.getParent())) {
                    current.getParent().removeChild(current);
                } else {
                    this.form.removeField(current);
                }
            } else if (SignatureClipper.clipSignature(current)) {
                this.form.setSignaturesExist(true);
            }
        }
        if (StringUtils.isBlank(this.form.getDefaultAppearance())) {
            this.form.setDefaultAppearance("/Helv 0 Tf 0 g ");
        }
        return this.form;
    }
}

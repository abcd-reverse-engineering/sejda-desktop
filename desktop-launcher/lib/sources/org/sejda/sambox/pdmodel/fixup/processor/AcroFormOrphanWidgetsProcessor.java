package org.sejda.sambox.pdmodel.fixup.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.font.FontMapper;
import org.sejda.sambox.pdmodel.font.FontMappers;
import org.sejda.sambox.pdmodel.font.FontMapping;
import org.sejda.sambox.pdmodel.font.PDType0Font;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.pdmodel.interactive.form.PDAcroForm;
import org.sejda.sambox.pdmodel.interactive.form.PDField;
import org.sejda.sambox.pdmodel.interactive.form.PDFieldFactory;
import org.sejda.sambox.pdmodel.interactive.form.PDVariableText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/fixup/processor/AcroFormOrphanWidgetsProcessor.class */
public class AcroFormOrphanWidgetsProcessor extends AbstractProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(AcroFormOrphanWidgetsProcessor.class);

    public AcroFormOrphanWidgetsProcessor(PDDocument document) {
        super(document);
    }

    @Override // org.sejda.sambox.pdmodel.fixup.processor.PDDocumentProcessor
    public void process() {
        PDAcroForm acroForm = this.document.getDocumentCatalog().getAcroForm(null);
        if (acroForm != null) {
            resolveFieldsFromWidgets(acroForm);
        }
    }

    private void resolveFieldsFromWidgets(PDAcroForm acroForm) {
        LOG.debug("rebuilding fields from widgets");
        PDResources resources = acroForm.getDefaultResources();
        if (resources == null) {
            LOG.debug("AcroForm default resources is null");
            return;
        }
        List<PDField> fields = new ArrayList<>();
        Map<String, PDField> nonTerminalFieldsMap = new HashMap<>();
        Iterator<PDPage> it = this.document.getPages().iterator();
        while (it.hasNext()) {
            PDPage page = it.next();
            handleAnnotations(acroForm, resources, fields, page.getAnnotations(), nonTerminalFieldsMap);
        }
        acroForm.setFields(fields);
        Iterator<PDField> it2 = acroForm.getFieldTree().iterator();
        while (it2.hasNext()) {
            PDField field = it2.next();
            if (field instanceof PDVariableText) {
                ensureFontResources(resources, (PDVariableText) field);
            }
        }
    }

    private void handleAnnotations(PDAcroForm acroForm, PDResources acroFormResources, List<PDField> fields, List<PDAnnotation> annotations, Map<String, PDField> nonTerminalFieldsMap) {
        for (PDAnnotation annot : annotations) {
            if (annot instanceof PDAnnotationWidget) {
                addFontFromWidget(acroFormResources, annot);
                COSDictionary parent = (COSDictionary) annot.getCOSObject().getDictionaryObject(COSName.PARENT, COSDictionary.class);
                if (parent != null) {
                    PDField resolvedField = resolveNonRootField(acroForm, parent, nonTerminalFieldsMap);
                    if (resolvedField != null) {
                        fields.add(resolvedField);
                    }
                } else {
                    fields.add(PDFieldFactory.createField(acroForm, annot.getCOSObject(), null));
                }
            }
        }
    }

    private void addFontFromWidget(PDResources acroFormResources, PDAnnotation annotation) {
        PDResources widgetResources;
        PDAppearanceStream normalAppearanceStream = annotation.getNormalAppearanceStream();
        if (normalAppearanceStream == null || (widgetResources = normalAppearanceStream.getResources()) == null) {
            return;
        }
        for (COSName fontName : widgetResources.getFontNames()) {
            if (!fontName.getName().startsWith("+")) {
                try {
                    if (acroFormResources.getFont(fontName) == null) {
                        acroFormResources.put(fontName, widgetResources.getFont(fontName));
                        LOG.debug("added font resource to AcroForm from widget for font name " + fontName.getName());
                    }
                } catch (IOException e) {
                    LOG.debug("unable to add font to AcroForm for font name " + fontName.getName());
                }
            } else {
                LOG.debug("font resource for widget was a subsetted font - ignored: " + fontName.getName());
            }
        }
    }

    private PDField resolveNonRootField(PDAcroForm acroForm, COSDictionary parent, Map<String, PDField> nonTerminalFieldsMap) {
        while (parent.containsKey(COSName.PARENT)) {
            parent = (COSDictionary) parent.getDictionaryObject(COSName.PARENT, COSDictionary.class);
            if (parent == null) {
                return null;
            }
        }
        if (nonTerminalFieldsMap.get(parent.getString(COSName.T)) == null) {
            PDField field = PDFieldFactory.createField(acroForm, parent, null);
            if (field != null) {
                nonTerminalFieldsMap.put(field.getFullyQualifiedName(), field);
            }
            return field;
        }
        return null;
    }

    private void ensureFontResources(PDResources defaultResources, PDVariableText field) {
        String daString = field.getDefaultAppearance();
        if (daString.startsWith("/") && daString.length() > 1) {
            COSName fontName = COSName.getPDFName(daString.substring(1, daString.indexOf(" ")));
            try {
                if (defaultResources.getFont(fontName) == null) {
                    LOG.debug("Trying to add missing font resource for field " + field.getFullyQualifiedName());
                    FontMapper mapper = FontMappers.instance();
                    FontMapping<TrueTypeFont> fontMapping = mapper.getTrueTypeFont(fontName.getName(), null);
                    if (fontMapping != null) {
                        PDType0Font pdFont = PDType0Font.load(this.document, fontMapping.getFont(), false);
                        LOG.debug("looked up font for " + fontName.getName() + " - found " + fontMapping.getFont().getName());
                        defaultResources.put(fontName, pdFont);
                    } else {
                        LOG.debug("no suitable font found for field " + field.getFullyQualifiedName() + " for font name " + fontName.getName());
                    }
                }
            } catch (IOException ioe) {
                LOG.debug("Unable to handle font resources for field " + field.getFullyQualifiedName() + ": " + ioe.getMessage());
            }
        }
    }
}

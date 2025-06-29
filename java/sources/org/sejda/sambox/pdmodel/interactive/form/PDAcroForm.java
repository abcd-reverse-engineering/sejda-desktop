package org.sejda.sambox.pdmodel.interactive.form;

import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDAcroForm.class */
public final class PDAcroForm extends PDDictionaryWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(PDAcroForm.class);
    private static final int FLAG_SIGNATURES_EXIST = 1;
    private static final int FLAG_APPEND_ONLY = 2;
    private final PDDocument document;
    private ScriptingHandler scriptingHandler;
    private final Map<COSName, SoftReference<PDFont>> directFontCache;

    public PDAcroForm(PDDocument document) {
        this.directFontCache = new HashMap();
        this.document = document;
        getCOSObject().setItem(COSName.FIELDS, (COSBase) new COSArray());
    }

    public PDAcroForm(PDDocument document, COSDictionary form) {
        super(form);
        this.directFontCache = new HashMap();
        this.document = document;
    }

    public PDDocument getDocument() {
        return this.document;
    }

    public void flatten() throws IOException {
        if (xfaIsDynamic()) {
            LOG.warn("Flatten for a dynamic XFA form is not supported");
            return;
        }
        List<PDField> fields = new ArrayList<>();
        Iterator<PDField> it = getFieldTree().iterator();
        while (it.hasNext()) {
            PDField field = it.next();
            fields.add(field);
        }
        flatten(fields, false);
    }

    public void flatten(List<PDField> fields, boolean refreshAppearances) throws IOException {
        if (fields.isEmpty()) {
            return;
        }
        if (xfaIsDynamic()) {
            LOG.warn("Flatten for a dynamix XFA form is not supported");
            return;
        }
        if (!refreshAppearances && isNeedAppearances()) {
            LOG.warn("AcroForm NeedAppearances is true, visual field appearances may not have been set");
            LOG.warn("call acroForm.refreshAppearances() or use the flatten() method with refreshAppearances parameter");
        }
        if (refreshAppearances) {
            refreshAppearances(fields);
        }
        Map<COSDictionary, PDAnnotationWidget> toFlatten = widgets(fields);
        Iterator<PDPage> it = this.document.getPages().iterator();
        while (it.hasNext()) {
            PDPage page = it.next();
            boolean isContentStreamWrapped = false;
            List<PDAnnotation> annotations = new ArrayList<>();
            for (PDAnnotation annotation : page.getAnnotations()) {
                PDAnnotationWidget widget = toFlatten.get(annotation.getCOSObject());
                if (Objects.isNull(widget)) {
                    annotations.add(annotation);
                } else if (isVisibleAnnotation(annotation)) {
                    PDPageContentStream contentStream = new PDPageContentStream(this.document, page, PDPageContentStream.AppendMode.APPEND, true, !isContentStreamWrapped);
                    try {
                        isContentStreamWrapped = true;
                        PDAppearanceStream appearanceStream = annotation.getNormalAppearanceStream();
                        PDFormXObject fieldObject = new PDFormXObject(appearanceStream.getCOSObject());
                        contentStream.saveGraphicsState();
                        Matrix transformationMatrix = resolveTransformationMatrix(annotation, appearanceStream);
                        contentStream.transform(transformationMatrix);
                        contentStream.drawForm(fieldObject);
                        contentStream.restoreGraphicsState();
                        contentStream.close();
                    } catch (Throwable th) {
                        try {
                            contentStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } else {
                    continue;
                }
            }
            page.setAnnotations(annotations);
        }
        removeFields(fields);
        getCOSObject().removeItem(COSName.XFA);
    }

    private boolean isVisibleAnnotation(PDAnnotation annotation) {
        PDAppearanceStream normalAppearanceStream;
        PDRectangle bbox;
        return (annotation.isInvisible() || annotation.isHidden() || (normalAppearanceStream = annotation.getNormalAppearanceStream()) == null || (bbox = normalAppearanceStream.getBBox()) == null || bbox.getWidth() <= 0.0f || bbox.getHeight() <= 0.0f) ? false : true;
    }

    public void refreshAppearances() throws IOException {
        Iterator<PDField> it = getFieldTree().iterator();
        while (it.hasNext()) {
            PDField field = it.next();
            if (field instanceof PDTerminalField) {
                ((PDTerminalField) field).constructAppearances();
            }
        }
    }

    public void refreshAppearances(List<PDField> fields) throws IOException {
        for (PDField field : fields) {
            if (field instanceof PDTerminalField) {
                ((PDTerminalField) field).constructAppearances();
            }
        }
    }

    public List<PDField> getFields() {
        return fieldsFromArray((COSArray) getCOSObject().getDictionaryObject(COSName.FIELDS, COSArray.class));
    }

    private List<PDField> fieldsFromArray(COSArray array) {
        if (Objects.nonNull(array) && array.size() > 0) {
            return (List) array.stream().filter((v0) -> {
                return Objects.nonNull(v0);
            }).map((v0) -> {
                return v0.getCOSObject();
            }).filter(d -> {
                return d instanceof COSDictionary;
            }).map(d2 -> {
                return PDField.fromDictionary(this, (COSDictionary) d2, null);
            }).collect(Collectors.toList());
        }
        return new ArrayList();
    }

    public void addFields(Collection<PDField> toAdd) {
        COSArray fields = (COSArray) Optional.ofNullable((COSArray) getCOSObject().getDictionaryObject(COSName.FIELDS, COSArray.class)).orElseGet(COSArray::new);
        for (PDField field : toAdd) {
            fields.add((COSObjectable) field);
        }
        getCOSObject().setItem(COSName.FIELDS, (COSBase) fields);
    }

    public COSBase removeField(PDField remove) {
        int removeIdx;
        COSArray fields = (COSArray) getCOSObject().getDictionaryObject(COSName.FIELDS, COSArray.class);
        if (Objects.nonNull(fields) && (removeIdx = fields.indexOfObject(remove.getCOSObject())) >= 0) {
            return fields.remove(removeIdx);
        }
        return null;
    }

    public void setFields(List<PDField> fields) {
        getCOSObject().setItem(COSName.FIELDS, (COSBase) COSArrayList.converterToCOSArray(fields));
    }

    public Iterator<PDField> getFieldIterator() {
        return new PDFieldTree(this).iterator();
    }

    public PDFieldTree getFieldTree() {
        return new PDFieldTree(this);
    }

    public PDField getField(String fullyQualifiedName) {
        if (fullyQualifiedName == null) {
            return null;
        }
        return getFieldTree().stream().filter(f -> {
            return f != null && fullyQualifiedName.equals(f.getFullyQualifiedName());
        }).findFirst().orElse(null);
    }

    public String getDefaultAppearance() {
        return (String) Optional.ofNullable((COSString) getCOSObject().getDictionaryObject(COSName.DA, COSString.class)).map((v0) -> {
            return v0.getString();
        }).orElse("");
    }

    public void setDefaultAppearance(String daValue) {
        getCOSObject().setString(COSName.DA, daValue);
    }

    public List<PDField> getCalculationOrder() {
        return fieldsFromArray((COSArray) getCOSObject().getDictionaryObject(COSName.CO, COSArray.class));
    }

    public void setCalculationOrder(COSArray co) {
        getCOSObject().setItem(COSName.CO, (COSBase) co);
    }

    public boolean isNeedAppearances() {
        return getCOSObject().getBoolean(COSName.NEED_APPEARANCES, false);
    }

    public void setNeedAppearances(Boolean value) {
        getCOSObject().setBoolean(COSName.NEED_APPEARANCES, value.booleanValue());
    }

    public PDResources getDefaultResources() {
        return (PDResources) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.DR, COSDictionary.class)).map(dr -> {
            return new PDResources(dr, this.document.getResourceCache(), this.directFontCache);
        }).orElse(null);
    }

    public void setDefaultResources(PDResources dr) {
        getCOSObject().setItem(COSName.DR, dr);
    }

    public boolean hasXFA() {
        return getCOSObject().containsKey(COSName.XFA);
    }

    public boolean xfaIsDynamic() {
        return hasXFA() && getFields().isEmpty();
    }

    public PDXFAResource getXFA() {
        return (PDXFAResource) Optional.ofNullable((COSDictionary) getCOSObject().getDictionaryObject(COSName.XFA, COSDictionary.class)).map((v1) -> {
            return new PDXFAResource(v1);
        }).orElse(null);
    }

    public void setXFA(PDXFAResource xfa) {
        getCOSObject().setItem(COSName.XFA, xfa);
    }

    public int getQuadding() {
        return getCOSObject().getInt(COSName.Q, 0);
    }

    public void setQuadding(int q) {
        getCOSObject().setInt(COSName.Q, q);
    }

    public boolean isSignaturesExist() {
        return getCOSObject().getFlag(COSName.SIG_FLAGS, 1);
    }

    public void setSignaturesExist(boolean signaturesExist) {
        getCOSObject().setFlag(COSName.SIG_FLAGS, 1, signaturesExist);
    }

    public boolean isAppendOnly() {
        return getCOSObject().getFlag(COSName.SIG_FLAGS, 2);
    }

    public void setAppendOnly(boolean appendOnly) {
        getCOSObject().setFlag(COSName.SIG_FLAGS, 2, appendOnly);
    }

    public ScriptingHandler getScriptingHandler() {
        return this.scriptingHandler;
    }

    public void setScriptingHandler(ScriptingHandler scriptingHandler) {
        this.scriptingHandler = scriptingHandler;
    }

    private Matrix resolveTransformationMatrix(PDAnnotation annotation, PDAppearanceStream appearanceStream) {
        Rectangle2D transformedAppearanceBox = getTransformedAppearanceBBox(appearanceStream);
        PDRectangle annotationRect = annotation.getRectangle();
        Matrix transformationMatrix = new Matrix();
        transformationMatrix.translate((float) (annotationRect.getLowerLeftX() - transformedAppearanceBox.getX()), (float) (annotationRect.getLowerLeftY() - transformedAppearanceBox.getY()));
        transformationMatrix.scale((float) (annotationRect.getWidth() / transformedAppearanceBox.getWidth()), (float) (annotationRect.getHeight() / transformedAppearanceBox.getHeight()));
        return transformationMatrix;
    }

    private Rectangle2D getTransformedAppearanceBBox(PDAppearanceStream appearanceStream) {
        Matrix appearanceStreamMatrix = appearanceStream.getMatrix();
        PDRectangle appearanceStreamBBox = appearanceStream.getBBox();
        GeneralPath transformedAppearanceBox = appearanceStreamBBox.transform(appearanceStreamMatrix);
        return transformedAppearanceBox.getBounds2D();
    }

    private Map<COSDictionary, PDAnnotationWidget> widgets(List<PDField> fields) {
        Map<COSDictionary, PDAnnotationWidget> widgetMap = new HashMap<>();
        for (PDField field : fields) {
            for (PDAnnotationWidget widget : field.getWidgets()) {
                widgetMap.put(widget.getCOSObject(), widget);
            }
        }
        return widgetMap;
    }

    private Map<COSDictionary, Set<COSDictionary>> buildPagesWidgetsMap(List<PDField> fields) throws IOException {
        Map<COSDictionary, Set<COSDictionary>> pagesAnnotationsMap = new HashMap<>();
        boolean hasMissingPageRef = false;
        for (PDField field : fields) {
            List<PDAnnotationWidget> widgets = field.getWidgets();
            for (PDAnnotationWidget widget : widgets) {
                PDPage page = widget.getPage();
                if (page != null) {
                    fillPagesAnnotationMap(pagesAnnotationsMap, page, widget);
                } else {
                    hasMissingPageRef = true;
                }
            }
        }
        if (!hasMissingPageRef) {
            return pagesAnnotationsMap;
        }
        LOG.warn("There has been a widget with a missing page reference, will check all page annotations");
        Iterator<PDPage> it = this.document.getPages().iterator();
        while (it.hasNext()) {
            PDPage page2 = it.next();
            for (PDAnnotation annotation : page2.getAnnotations()) {
                if (annotation instanceof PDAnnotationWidget) {
                    fillPagesAnnotationMap(pagesAnnotationsMap, page2, (PDAnnotationWidget) annotation);
                }
            }
        }
        return pagesAnnotationsMap;
    }

    private void fillPagesAnnotationMap(Map<COSDictionary, Set<COSDictionary>> pagesAnnotationsMap, PDPage page, PDAnnotationWidget widget) {
        Set<COSDictionary> widgetsForPage = pagesAnnotationsMap.get(page.getCOSObject());
        if (widgetsForPage == null) {
            Set<COSDictionary> widgetsForPage2 = new HashSet<>();
            widgetsForPage2.add(widget.getCOSObject());
            pagesAnnotationsMap.put(page.getCOSObject(), widgetsForPage2);
            return;
        }
        widgetsForPage.add(widget.getCOSObject());
    }

    private void removeFields(List<PDField> fields) {
        for (PDField current : fields) {
            if (Objects.nonNull(current.getParent())) {
                current.getParent().removeChild(current);
            } else if (Objects.isNull(removeField(current))) {
                LOG.warn("Unable to remove root field {}, not found", current.getFullyQualifiedName());
            }
        }
    }
}

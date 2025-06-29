package org.sejda.sambox.pdmodel;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.contentstream.PDContentStream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSArrayList;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.common.PDMetadata;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.interactive.action.PDPageAdditionalActions;
import org.sejda.sambox.pdmodel.interactive.annotation.AnnotationFilter;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.form.Tabs;
import org.sejda.sambox.pdmodel.interactive.measurement.PDViewportDictionary;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDThreadBead;
import org.sejda.sambox.pdmodel.interactive.pagenavigation.PDTransition;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDPage.class */
public class PDPage implements COSObjectable, PDContentStream {
    private static final Logger LOG = LoggerFactory.getLogger(PDPage.class);
    private static final List<COSName> VALID_STREAM_KEYS = Arrays.asList(COSName.LENGTH, COSName.FILTER, COSName.DECODE_PARMS, COSName.F, COSName.F_FILTER, COSName.F_DECODE_PARMS, COSName.DL);
    private final COSDictionary page;
    private PDResources pageResources;
    private ResourceCache resourceCache;
    private PDRectangle mediaBox;
    private COSDictionary pageTreeParent;

    public PDPage() {
        this(PDRectangle.LETTER);
    }

    public PDPage(PDRectangle mediaBox) {
        this.page = new COSDictionary();
        this.page.setItem(COSName.TYPE, (COSBase) COSName.PAGE);
        this.page.setItem(COSName.MEDIA_BOX, mediaBox);
    }

    public PDPage(COSDictionary pageDictionary) {
        this.page = pageDictionary;
    }

    PDPage(COSDictionary pageDictionary, ResourceCache resourceCache) {
        this.page = pageDictionary;
        this.resourceCache = resourceCache;
    }

    PDPage(COSDictionary pageDictionary, ResourceCache resourceCache, COSDictionary pageTreeParent) {
        this.page = pageDictionary;
        this.resourceCache = resourceCache;
        this.pageTreeParent = pageTreeParent;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.page;
    }

    public Iterator<PDStream> getContentStreams() {
        List<PDStream> streams = new ArrayList<>();
        COSBase base = this.page.getDictionaryObject(COSName.CONTENTS);
        if (base instanceof COSStream) {
            streams.add(new PDStream((COSStream) base));
        } else if (base instanceof COSArray) {
            COSArray array = (COSArray) base;
            for (int i = 0; i < array.size(); i++) {
                COSBase baseObject = array.getObject(i);
                if (baseObject instanceof COSStream) {
                    COSStream stream = (COSStream) baseObject;
                    if (Objects.nonNull(stream)) {
                        streams.add(new PDStream(stream));
                    }
                } else {
                    LOG.warn("Page has contents object that is not a stream: " + baseObject);
                }
            }
        }
        return streams.iterator();
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public InputStream getContents() throws IOException {
        COSBase base = this.page.getDictionaryObject(COSName.CONTENTS);
        if (base instanceof COSStream) {
            return ((COSStream) base).getUnfilteredStream();
        }
        if (base instanceof COSArray) {
            COSArray streams = (COSArray) base;
            if (((COSArray) base).size() > 0) {
                byte[] delimiter = {10};
                List<InputStream> inputStreams = new ArrayList<>();
                for (int i = 0; i < streams.size(); i++) {
                    COSBase strm = streams.getObject(i);
                    if (strm instanceof COSStream) {
                        COSStream stream = (COSStream) strm;
                        inputStreams.add(stream.getUnfilteredStream());
                        inputStreams.add(new ByteArrayInputStream(delimiter));
                    }
                }
                return new SequenceInputStream(Collections.enumeration(inputStreams));
            }
        }
        return new ByteArrayInputStream(new byte[0]);
    }

    public boolean hasContents() {
        COSBase contents = this.page.getDictionaryObject(COSName.CONTENTS);
        return contents instanceof COSStream ? ((COSStream) contents).size() > 0 : (contents instanceof COSArray) && ((COSArray) contents).size() > 0;
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDResources getResources() {
        if (this.pageResources == null) {
            this.pageResources = new PDResources((COSDictionary) Optional.ofNullable((COSDictionary) PDPageTree.getInheritableAttribute(this.page, COSName.RESOURCES, COSDictionary.class)).orElseGet(() -> {
                COSDictionary emptyRes = new COSDictionary();
                this.page.setItem(COSName.RESOURCES, (COSBase) emptyRes);
                return emptyRes;
            }), this.resourceCache);
        }
        return this.pageResources;
    }

    public void setResources(PDResources resources) {
        this.pageResources = resources;
        if (resources != null) {
            this.page.setItem(COSName.RESOURCES, resources);
        } else {
            this.page.removeItem(COSName.RESOURCES);
        }
    }

    public int getStructParents() {
        return this.page.getInt(COSName.STRUCT_PARENTS);
    }

    public void setStructParents(int structParents) {
        this.page.setInt(COSName.STRUCT_PARENTS, structParents);
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public PDRectangle getBBox() {
        return getCropBox();
    }

    @Override // org.sejda.sambox.contentstream.PDContentStream
    public Matrix getMatrix() {
        return new Matrix();
    }

    public PDRectangle getMediaBox() {
        if (this.mediaBox == null) {
            COSBase base = PDPageTree.getInheritableAttribute(this.page, COSName.MEDIA_BOX);
            if (base instanceof COSArray) {
                this.mediaBox = new PDRectangle((COSArray) base);
            } else {
                LOG.debug("Can't find MediaBox, will use U.S. Letter");
                this.mediaBox = PDRectangle.LETTER;
            }
        }
        return this.mediaBox;
    }

    public PDRectangle getMediaBoxRaw() {
        COSBase array = PDPageTree.getInheritableAttribute(this.page, COSName.MEDIA_BOX);
        if (array instanceof COSArray) {
            return PDRectangle.rectangleFrom((COSArray) array);
        }
        return null;
    }

    public void setMediaBox(PDRectangle mediaBox) {
        this.mediaBox = mediaBox;
        if (mediaBox == null) {
            this.page.removeItem(COSName.MEDIA_BOX);
        } else {
            this.page.setItem(COSName.MEDIA_BOX, mediaBox);
        }
    }

    public PDRectangle getCropBox() {
        try {
            COSBase base = PDPageTree.getInheritableAttribute(this.page, COSName.CROP_BOX);
            if (base instanceof COSArray) {
                return clipToMediaBox(new PDRectangle((COSArray) base));
            }
        } catch (Exception ex) {
            LOG.debug("An error occurred parsing the crop box", ex);
        }
        return getMediaBox();
    }

    public PDRectangle getCropBoxRaw() {
        COSBase array = PDPageTree.getInheritableAttribute(this.page, COSName.CROP_BOX);
        if (array instanceof COSArray) {
            return PDRectangle.rectangleFrom((COSArray) array);
        }
        return null;
    }

    public void setCropBox(PDRectangle cropBox) {
        if (cropBox == null) {
            this.page.removeItem(COSName.CROP_BOX);
        } else {
            this.page.setItem(COSName.CROP_BOX, (COSBase) cropBox.getCOSObject());
        }
    }

    public PDRectangle getBleedBox() {
        try {
            PDRectangle trimBox = getBleedBoxRaw();
            if (inMediaBoxBounds(trimBox)) {
                return trimBox;
            }
        } catch (Exception ex) {
            LOG.debug("An error occurred parsing page bleed box", ex);
        }
        return getCropBox();
    }

    public PDRectangle getBleedBoxRaw() {
        return PDRectangle.rectangleFrom((COSArray) this.page.getDictionaryObject(COSName.BLEED_BOX, COSArray.class));
    }

    public void setBleedBox(PDRectangle bleedBox) {
        if (bleedBox == null) {
            this.page.removeItem(COSName.BLEED_BOX);
        } else {
            this.page.setItem(COSName.BLEED_BOX, bleedBox);
        }
    }

    public PDRectangle getTrimBox() {
        try {
            PDRectangle trimBox = getTrimBoxRaw();
            if (inMediaBoxBounds(trimBox)) {
                return trimBox;
            }
        } catch (Exception ex) {
            LOG.debug("An error occurred parsing page trim box", ex);
        }
        return getCropBox();
    }

    public PDRectangle getTrimBoxRaw() {
        return PDRectangle.rectangleFrom((COSArray) this.page.getDictionaryObject(COSName.TRIM_BOX, COSArray.class));
    }

    public void setTrimBox(PDRectangle trimBox) {
        if (trimBox == null) {
            this.page.removeItem(COSName.TRIM_BOX);
        } else {
            this.page.setItem(COSName.TRIM_BOX, trimBox);
        }
    }

    public PDRectangle getArtBox() {
        try {
            PDRectangle artBox = getArtBoxRaw();
            if (inMediaBoxBounds(artBox)) {
                return artBox;
            }
        } catch (Exception ex) {
            LOG.debug("An error occurred parsing page art box", ex);
        }
        return getCropBox();
    }

    public PDRectangle getArtBoxRaw() {
        return PDRectangle.rectangleFrom((COSArray) this.page.getDictionaryObject(COSName.ART_BOX, COSArray.class));
    }

    public void setArtBox(PDRectangle artBox) {
        if (artBox == null) {
            this.page.removeItem(COSName.ART_BOX);
        } else {
            this.page.setItem(COSName.ART_BOX, artBox);
        }
    }

    private PDRectangle clipToMediaBox(PDRectangle box) {
        PDRectangle mediaBox = getMediaBox();
        PDRectangle result = new PDRectangle();
        result.setLowerLeftX(Math.max(mediaBox.getLowerLeftX(), box.getLowerLeftX()));
        result.setLowerLeftY(Math.max(mediaBox.getLowerLeftY(), box.getLowerLeftY()));
        result.setUpperRightX(Math.min(mediaBox.getUpperRightX(), box.getUpperRightX()));
        result.setUpperRightY(Math.min(mediaBox.getUpperRightY(), box.getUpperRightY()));
        return result;
    }

    private boolean inMediaBoxBounds(PDRectangle box) {
        PDRectangle mediaBox = getMediaBox();
        return Objects.nonNull(box) && mediaBox.getLowerLeftX() <= box.getLowerLeftX() && mediaBox.getLowerLeftY() <= box.getLowerLeftY() && mediaBox.getUpperRightX() >= box.getUpperRightX() && mediaBox.getUpperRightY() >= box.getUpperRightY();
    }

    public int getRotation() {
        COSBase obj = PDPageTree.getInheritableAttribute(this.page, COSName.ROTATE);
        if (obj instanceof COSNumber) {
            int rotationAngle = ((COSNumber) obj).intValue();
            if (rotationAngle % 90 == 0) {
                return ((rotationAngle % 360) + 360) % 360;
            }
            return 0;
        }
        return 0;
    }

    public void setRotation(int rotation) {
        this.page.setInt(COSName.ROTATE, rotation);
    }

    public void setContents(PDStream contents) {
        this.page.setItem(COSName.CONTENTS, contents);
    }

    public void setContents(List<PDStream> contents) {
        COSArray array = new COSArray();
        for (PDStream stream : contents) {
            array.add((COSObjectable) stream);
        }
        this.page.setItem(COSName.CONTENTS, (COSBase) array);
    }

    public List<PDThreadBead> getThreadBeads() {
        COSArray beads = (COSArray) this.page.getDictionaryObject(COSName.B, COSArray.class);
        if (beads == null) {
            return new COSArrayList(this.page, COSName.B);
        }
        List<PDThreadBead> actuals = new ArrayList<>(beads.size());
        for (int i = 0; i < beads.size(); i++) {
            COSBase item = beads.getObject(i);
            Optional optionalFilter = Optional.ofNullable(item).filter(d -> {
                return d instanceof COSDictionary;
            });
            Class<COSDictionary> cls = COSDictionary.class;
            Objects.requireNonNull(COSDictionary.class);
            PDThreadBead bead = (PDThreadBead) optionalFilter.map((v1) -> {
                return r1.cast(v1);
            }).map(PDThreadBead::new).orElseGet(() -> {
                LOG.warn("Ignored thread bead expected to be a dictionary but was {}", item);
                return null;
            });
            if (Objects.nonNull(bead)) {
                actuals.add(bead);
            }
        }
        return new COSArrayList(actuals, beads);
    }

    public void setThreadBeads(List<PDThreadBead> beads) {
        this.page.setItem(COSName.B, (COSBase) COSArrayList.converterToCOSArray(beads));
    }

    public PDMetadata getMetadata() {
        return (PDMetadata) Optional.ofNullable((COSStream) this.page.getDictionaryObject(COSName.METADATA, COSStream.class)).map(PDMetadata::new).orElse(null);
    }

    public void setMetadata(PDMetadata meta) {
        this.page.setItem(COSName.METADATA, meta);
    }

    public PDPageAdditionalActions getActions() {
        COSDictionary addAct = (COSDictionary) this.page.getDictionaryObject(COSName.AA, COSDictionary.class);
        if (addAct == null) {
            addAct = new COSDictionary();
            this.page.setItem(COSName.AA, (COSBase) addAct);
        }
        return new PDPageAdditionalActions(addAct);
    }

    public void setActions(PDPageAdditionalActions actions) {
        this.page.setItem(COSName.AA, actions);
    }

    public PDTransition getTransition() {
        return (PDTransition) Optional.ofNullable((COSDictionary) this.page.getDictionaryObject(COSName.TRANS, COSDictionary.class)).map(PDTransition::new).orElse(null);
    }

    public void setTransition(PDTransition transition) {
        this.page.setItem(COSName.TRANS, transition);
    }

    public void setTransition(PDTransition transition, float duration) {
        this.page.setItem(COSName.TRANS, transition);
        this.page.setItem(COSName.DUR, (COSBase) new COSFloat(duration));
    }

    public List<PDAnnotation> getAnnotations() {
        return getAnnotations(annotation -> {
            return true;
        });
    }

    public List<PDAnnotation> getAnnotations(AnnotationFilter annotationFilter) {
        COSArray annots = (COSArray) this.page.getDictionaryObject(COSName.ANNOTS, COSArray.class);
        if (annots == null) {
            return new COSArrayList(this.page, COSName.ANNOTS);
        }
        List<PDAnnotation> actuals = new ArrayList<>();
        for (int i = 0; i < annots.size(); i++) {
            COSBase item = annots.getObject(i);
            Optional optionalFilter = Optional.ofNullable(item).filter(d -> {
                return d instanceof COSDictionary;
            });
            Class<COSDictionary> cls = COSDictionary.class;
            Objects.requireNonNull(COSDictionary.class);
            PDAnnotation annotation = (PDAnnotation) optionalFilter.map((v1) -> {
                return r1.cast(v1);
            }).map((v0) -> {
                return PDAnnotation.createAnnotation(v0);
            }).orElseGet(() -> {
                LOG.warn("Ignored annotation expected to be a dictionary but was {}", item);
                return null;
            });
            if (Objects.nonNull(annotation) && annotationFilter.accept(annotation)) {
                actuals.add(annotation);
            }
        }
        return new COSArrayList(actuals, annots);
    }

    public Point cropBoxCoordinatesToDraw(Point2D point) {
        PDRectangle cropBox = getCropBox();
        double x = point.getX() + cropBox.getLowerLeftX();
        double y = point.getY() + cropBox.getLowerLeftY();
        if (getRotation() == 90) {
            x = cropBox.getUpperRightX() - point.getY();
            y = cropBox.getLowerLeftY() + point.getX();
        } else if (getRotation() == 180) {
            x = cropBox.getUpperRightX() - point.getX();
            y = cropBox.getUpperRightY() - point.getY();
        } else if (getRotation() == 270) {
            x = cropBox.getLowerLeftX() + point.getY();
            y = cropBox.getUpperRightY() - point.getX();
        }
        return new Point((int) x, (int) y);
    }

    public void setAnnotations(List<PDAnnotation> annotations) {
        this.page.setItem(COSName.ANNOTS, (COSBase) COSArrayList.converterToCOSArray(annotations));
    }

    public boolean equals(Object other) {
        return (other instanceof PDPage) && ((PDPage) other).getCOSObject() == getCOSObject();
    }

    public int hashCode() {
        return this.page.hashCode();
    }

    public ResourceCache getResourceCache() {
        return this.resourceCache;
    }

    public List<PDViewportDictionary> getViewports() {
        COSBase base = this.page.getDictionaryObject(COSName.VP);
        if (!(base instanceof COSArray)) {
            return null;
        }
        COSArray array = (COSArray) base;
        List<PDViewportDictionary> viewports = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            COSBase base2 = array.getObject(i);
            if (base2 instanceof COSDictionary) {
                viewports.add(new PDViewportDictionary((COSDictionary) base2));
            } else {
                LOG.warn("Array element {} is skipped, must be a (viewport) dictionary", base2);
            }
        }
        return viewports;
    }

    public void setViewports(List<PDViewportDictionary> viewports) {
        if (viewports == null) {
            this.page.removeItem(COSName.VP);
            return;
        }
        COSArray array = new COSArray();
        for (PDViewportDictionary viewport : viewports) {
            array.add((COSObjectable) viewport);
        }
        this.page.setItem(COSName.VP, (COSBase) array);
    }

    public float getUserUnit() {
        float userUnit = this.page.getFloat(COSName.USER_UNIT, 1.0f);
        if (userUnit > 0.0f) {
            return userUnit;
        }
        return 1.0f;
    }

    public void setUserUnit(float userUnit) {
        if (userUnit <= 0.0f) {
            throw new IllegalArgumentException("User unit must be positive");
        }
        this.page.setFloat(COSName.USER_UNIT, userUnit);
    }

    public void sanitizeDictionary() {
        getContentStreams().forEachRemaining(s -> {
            COSStream stream = s.getCOSObject();
            List<COSName> invalid = stream.keySet().stream().filter(k -> {
                return !VALID_STREAM_KEYS.contains(k);
            }).toList();
            Objects.requireNonNull(stream);
            invalid.forEach(stream::removeItem);
        });
    }

    public COSDictionary getPageTreeParent() {
        return this.pageTreeParent;
    }

    public Tabs getTabs() {
        return Tabs.fromString(this.page.getString(COSName.Tabs));
    }

    public void setTabs(Tabs value) {
        if (value == null) {
            this.page.removeItem(COSName.Tabs);
        } else {
            this.page.setString(COSName.Tabs, value.stringValue());
        }
    }
}

package org.sejda.sambox.pdmodel;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.sejda.sambox.pdmodel.graphics.shading.PDShading;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/DefaultResourceCache.class */
public class DefaultResourceCache implements ResourceCache {
    private final Map<COSObjectKey, SoftReference<PDFont>> fonts = new HashMap();
    private final Map<COSObjectKey, SoftReference<PDColorSpace>> colorSpaces = new HashMap();
    private final Map<COSObjectKey, SoftReference<PDXObject>> xobjects = new HashMap();
    private final Map<COSObjectKey, SoftReference<PDExtendedGraphicsState>> extGStates = new HashMap();
    private final Map<COSObjectKey, SoftReference<PDShading>> shadings = new HashMap();
    private final Map<COSObjectKey, SoftReference<PDAbstractPattern>> patterns = new HashMap();
    private final Map<COSObjectKey, SoftReference<PDPropertyList>> properties = new HashMap();

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public PDFont getFont(COSObjectKey key) {
        return (PDFont) Optional.ofNullable(this.fonts.get(key)).map((v0) -> {
            return v0.get();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void put(COSObjectKey key, PDFont font) {
        this.fonts.put(key, new SoftReference<>(font));
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public PDColorSpace getColorSpace(COSObjectKey key) {
        return (PDColorSpace) Optional.ofNullable(this.colorSpaces.get(key)).map((v0) -> {
            return v0.get();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void put(COSObjectKey key, PDColorSpace colorSpace) {
        this.colorSpaces.put(key, new SoftReference<>(colorSpace));
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public PDExtendedGraphicsState getExtGState(COSObjectKey key) {
        return (PDExtendedGraphicsState) Optional.ofNullable(this.extGStates.get(key)).map((v0) -> {
            return v0.get();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void put(COSObjectKey key, PDExtendedGraphicsState extGState) {
        this.extGStates.put(key, new SoftReference<>(extGState));
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public PDShading getShading(COSObjectKey key) {
        return (PDShading) Optional.ofNullable(this.shadings.get(key)).map((v0) -> {
            return v0.get();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void put(COSObjectKey key, PDShading shading) {
        this.shadings.put(key, new SoftReference<>(shading));
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public PDAbstractPattern getPattern(COSObjectKey key) {
        return (PDAbstractPattern) Optional.ofNullable(this.patterns.get(key)).map((v0) -> {
            return v0.get();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void put(COSObjectKey key, PDAbstractPattern pattern) {
        this.patterns.put(key, new SoftReference<>(pattern));
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public PDPropertyList getProperties(COSObjectKey key) {
        return (PDPropertyList) Optional.ofNullable(this.properties.get(key)).map((v0) -> {
            return v0.get();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void put(COSObjectKey key, PDPropertyList propertyList) {
        this.properties.put(key, new SoftReference<>(propertyList));
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public PDXObject getXObject(COSObjectKey key) {
        return (PDXObject) Optional.ofNullable(this.xobjects.get(key)).map((v0) -> {
            return v0.get();
        }).orElse(null);
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void put(COSObjectKey key, PDXObject xobject) {
        this.xobjects.put(key, new SoftReference<>(xobject));
    }

    @Override // org.sejda.sambox.pdmodel.ResourceCache
    public void clear() {
        this.fonts.clear();
        this.colorSpaces.clear();
        this.extGStates.clear();
        this.patterns.clear();
        this.properties.clear();
        this.shadings.clear();
        this.xobjects.clear();
    }
}

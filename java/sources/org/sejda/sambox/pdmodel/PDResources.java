package org.sejda.sambox.pdmodel;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.input.ExistingIndirectCOSObject;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDFontFactory;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDPattern;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
import org.sejda.sambox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.sejda.sambox.pdmodel.graphics.shading.PDShading;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDResources.class */
public final class PDResources implements COSObjectable {
    private final COSDictionary resources;
    private final ResourceCache cache;
    private final Map<COSName, SoftReference<PDFont>> directFontCache;

    public PDResources() {
        this.resources = new COSDictionary();
        this.cache = null;
        this.directFontCache = new HashMap();
    }

    public PDResources(COSDictionary resourceDictionary) {
        if (resourceDictionary == null) {
            throw new IllegalArgumentException("resourceDictionary is null");
        }
        this.resources = resourceDictionary;
        this.cache = null;
        this.directFontCache = new HashMap();
    }

    public PDResources(COSDictionary resourceDictionary, ResourceCache resourceCache) {
        if (resourceDictionary == null) {
            throw new IllegalArgumentException("resourceDictionary is null");
        }
        this.resources = resourceDictionary;
        this.cache = resourceCache;
        this.directFontCache = new HashMap();
    }

    public PDResources(COSDictionary resourceDictionary, ResourceCache resourceCache, Map<COSName, SoftReference<PDFont>> directFontCache) {
        if (resourceDictionary == null) {
            throw new IllegalArgumentException("resourceDictionary is null");
        }
        if (directFontCache == null) {
            throw new IllegalArgumentException("directFontCache is null");
        }
        this.resources = resourceDictionary;
        this.cache = resourceCache;
        this.directFontCache = directFontCache;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.resources;
    }

    public PDFont getFont(COSName name) throws IOException {
        SoftReference<PDFont> ref;
        PDFont cached;
        COSObjectKey key = getIndirectKey(COSName.FONT, name);
        if (this.cache != null && key != null) {
            PDFont cached2 = this.cache.getFont(key);
            if (cached2 != null) {
                return cached2;
            }
        } else if (key == null && (ref = this.directFontCache.get(name)) != null && (cached = ref.get()) != null) {
            return cached;
        }
        PDFont font = null;
        COSDictionary dict = (COSDictionary) get(COSName.FONT, name, COSDictionary.class);
        if (dict != null) {
            font = PDFontFactory.createFont(dict, this.cache);
        }
        if (this.cache != null && key != null) {
            this.cache.put(key, font);
        } else if (key == null) {
            this.directFontCache.put(name, new SoftReference<>(font));
        }
        return font;
    }

    public PDColorSpace getColorSpace(COSName name) throws IOException {
        return getColorSpace(name, false);
    }

    public PDColorSpace getColorSpace(COSName name, boolean wasDefault) throws IOException {
        PDColorSpace colorSpace;
        PDColorSpace cached;
        COSObjectKey key = getIndirectKey(COSName.COLORSPACE, name);
        if (this.cache != null && key != null && (cached = this.cache.getColorSpace(key)) != null) {
            return cached;
        }
        COSBase object = get(COSName.COLORSPACE, name);
        if (object != null) {
            colorSpace = PDColorSpace.create(object, this, wasDefault);
        } else {
            colorSpace = PDColorSpace.create(name, this, wasDefault);
        }
        if (this.cache != null && !(colorSpace instanceof PDPattern)) {
            this.cache.put(key, colorSpace);
        }
        return colorSpace;
    }

    public boolean hasColorSpace(COSName name) {
        return get(COSName.COLORSPACE, name) != null;
    }

    public PDExtendedGraphicsState getExtGState(COSName name) {
        PDExtendedGraphicsState cached;
        COSObjectKey key = getIndirectKey(COSName.EXT_G_STATE, name);
        if (this.cache != null && key != null && (cached = this.cache.getExtGState(key)) != null) {
            return cached;
        }
        PDExtendedGraphicsState extGState = null;
        COSDictionary dict = (COSDictionary) get(COSName.EXT_G_STATE, name, COSDictionary.class);
        if (dict != null) {
            extGState = new PDExtendedGraphicsState(dict);
        }
        if (Objects.nonNull(this.cache) && Objects.nonNull(key)) {
            this.cache.put(key, extGState);
        }
        return extGState;
    }

    public PDShading getShading(COSName name) throws IOException {
        PDShading cached;
        COSObjectKey key = getIndirectKey(COSName.SHADING, name);
        if (this.cache != null && key != null && (cached = this.cache.getShading(key)) != null) {
            return cached;
        }
        PDShading shading = null;
        COSDictionary dict = (COSDictionary) get(COSName.SHADING, name, COSDictionary.class);
        if (dict != null) {
            shading = PDShading.create(dict);
        }
        if (Objects.nonNull(this.cache) && Objects.nonNull(key)) {
            this.cache.put(key, shading);
        }
        return shading;
    }

    public PDAbstractPattern getPattern(COSName name) throws IOException {
        PDAbstractPattern cached;
        COSObjectKey key = getIndirectKey(COSName.PATTERN, name);
        if (this.cache != null && key != null && (cached = this.cache.getPattern(key)) != null) {
            return cached;
        }
        PDAbstractPattern pattern = null;
        COSDictionary dict = (COSDictionary) get(COSName.PATTERN, name, COSDictionary.class);
        if (dict != null) {
            pattern = PDAbstractPattern.create(dict, getResourceCache());
        }
        if (Objects.nonNull(this.cache) && Objects.nonNull(key)) {
            this.cache.put(key, pattern);
        }
        return pattern;
    }

    public PDPropertyList getProperties(COSName name) {
        PDPropertyList cached;
        COSObjectKey key = getIndirectKey(COSName.PROPERTIES, name);
        if (this.cache != null && key != null && (cached = this.cache.getProperties(key)) != null) {
            return cached;
        }
        PDPropertyList propertyList = null;
        COSDictionary dict = (COSDictionary) get(COSName.PROPERTIES, name, COSDictionary.class);
        if (dict != null) {
            propertyList = PDPropertyList.create(dict);
        }
        if (Objects.nonNull(this.cache) && Objects.nonNull(key)) {
            this.cache.put(key, propertyList);
        }
        return propertyList;
    }

    public boolean isImageXObject(COSName name) {
        return ((Boolean) Optional.ofNullable(get(COSName.XOBJECT, name)).map((v0) -> {
            return v0.getCOSObject();
        }).filter(s -> {
            return s instanceof COSStream;
        }).map(s2 -> {
            return (COSStream) s2;
        }).map(s3 -> {
            return Boolean.valueOf(COSName.IMAGE.equals(s3.getCOSName(COSName.SUBTYPE)));
        }).orElse(false)).booleanValue();
    }

    public boolean isFormXObject(COSName name) {
        return ((Boolean) Optional.ofNullable(get(COSName.XOBJECT, name)).map((v0) -> {
            return v0.getCOSObject();
        }).map(s -> {
            return (COSStream) s;
        }).map(s2 -> {
            return Boolean.valueOf(COSName.FORM.equals(s2.getCOSName(COSName.SUBTYPE)));
        }).orElse(false)).booleanValue();
    }

    public PDXObject getXObject(COSName name) throws IOException {
        PDXObject xobject;
        PDXObject cached;
        COSObjectKey key = getIndirectKey(COSName.XOBJECT, name);
        if (this.cache != null && key != null && (cached = this.cache.getXObject(key)) != null) {
            return cached;
        }
        COSBase value = get(COSName.XOBJECT, name);
        if (value == null) {
            xobject = null;
        } else {
            xobject = PDXObject.createXObject(value.getCOSObject(), this);
        }
        if (this.cache != null && isAllowedCache(xobject)) {
            this.cache.put(key, xobject);
        }
        return xobject;
    }

    private boolean isAllowedCache(PDXObject xobject) {
        if (xobject instanceof PDImageXObject) {
            COSBase colorSpace = xobject.getCOSObject().getDictionaryObject(COSName.COLORSPACE);
            if (colorSpace instanceof COSName) {
                COSName colorSpaceName = (COSName) colorSpace;
                if (colorSpaceName.equals(COSName.DEVICECMYK) && hasColorSpace(COSName.DEFAULT_CMYK)) {
                    return false;
                }
                if (colorSpaceName.equals(COSName.DEVICERGB) && hasColorSpace(COSName.DEFAULT_RGB)) {
                    return false;
                }
                return ((colorSpaceName.equals(COSName.DEVICEGRAY) && hasColorSpace(COSName.DEFAULT_GRAY)) || hasColorSpace(colorSpaceName)) ? false : true;
            }
            return true;
        }
        return true;
    }

    public COSObjectKey getIndirectKey(COSName kind, COSName name) {
        COSBase found = (COSBase) Optional.ofNullable((COSDictionary) this.resources.getDictionaryObject(kind, COSDictionary.class)).map(d -> {
            return d.getItem(name);
        }).orElse(null);
        if (found instanceof ExistingIndirectCOSObject) {
            ExistingIndirectCOSObject indirect = (ExistingIndirectCOSObject) found;
            return indirect.id().objectIdentifier;
        }
        return (COSObjectKey) Optional.ofNullable(found).map((v0) -> {
            return v0.id();
        }).map(id -> {
            return id.objectIdentifier;
        }).orElse(null);
    }

    private COSBase get(COSName kind, COSName name) {
        return (COSBase) Optional.ofNullable((COSDictionary) this.resources.getDictionaryObject(kind, COSDictionary.class)).map(d -> {
            return d.getDictionaryObject(name);
        }).orElse(null);
    }

    private <T extends COSBase> T get(COSName kind, COSName name, Class<T> clazz) {
        return (T) Optional.ofNullable((COSDictionary) this.resources.getDictionaryObject(kind, COSDictionary.class)).map(d -> {
            return d.getDictionaryObject(name, clazz);
        }).orElse(null);
    }

    public Iterable<COSName> getColorSpaceNames() {
        return getNames(COSName.COLORSPACE);
    }

    public Iterable<COSName> getXObjectNames() {
        return getNames(COSName.XOBJECT);
    }

    public Iterable<COSName> getFontNames() {
        return getNames(COSName.FONT);
    }

    public Iterable<COSName> getPropertiesNames() {
        return getNames(COSName.PROPERTIES);
    }

    public Iterable<COSName> getShadingNames() {
        return getNames(COSName.SHADING);
    }

    public Iterable<COSName> getPatternNames() {
        return getNames(COSName.PATTERN);
    }

    public Iterable<COSName> getExtGStateNames() {
        return getNames(COSName.EXT_G_STATE);
    }

    private Iterable<COSName> getNames(COSName kind) {
        return (Iterable) Optional.ofNullable((COSDictionary) this.resources.getDictionaryObject(kind, COSDictionary.class)).map((v0) -> {
            return v0.keySet();
        }).orElseGet(Collections::emptySet);
    }

    public COSName add(PDFont font) {
        return add(COSName.FONT, "F", font);
    }

    public COSName add(PDColorSpace colorSpace) {
        return add(COSName.COLORSPACE, OperatorName.NON_STROKING_COLORSPACE, colorSpace);
    }

    public COSName add(PDExtendedGraphicsState extGState) {
        return add(COSName.EXT_G_STATE, OperatorName.SET_GRAPHICS_STATE_PARAMS, extGState);
    }

    public COSName add(PDShading shading) {
        return add(COSName.SHADING, OperatorName.SHADING_FILL, shading);
    }

    public COSName add(PDAbstractPattern pattern) {
        return add(COSName.PATTERN, "p", pattern);
    }

    public COSName add(PDPropertyList properties) {
        if (properties instanceof PDOptionalContentGroup) {
            return add(COSName.PROPERTIES, "oc", properties);
        }
        return add(COSName.PROPERTIES, "Prop", properties);
    }

    public COSName add(PDImageXObject image) {
        return add(COSName.XOBJECT, "Im", image);
    }

    public COSName add(PDFormXObject form) {
        return add(COSName.XOBJECT, StandardStructureTypes.FORM, form);
    }

    public COSName add(PDXObject xobject, String prefix) {
        return add(COSName.XOBJECT, prefix, xobject);
    }

    private COSName add(COSName kind, String prefix, COSObjectable object) {
        COSDictionary dict = (COSDictionary) this.resources.getDictionaryObject(kind, COSDictionary.class);
        return (COSName) Optional.ofNullable(dict).map(d -> {
            return d.getKeyForValue(object.getCOSObject());
        }).orElseGet(() -> {
            COSName name = createKey(kind, prefix);
            put(kind, name, object);
            return name;
        });
    }

    private COSName createKey(COSName kind, String prefix) {
        String key;
        COSDictionary dict = (COSDictionary) this.resources.getDictionaryObject(kind, COSDictionary.class);
        if (dict == null) {
            return COSName.getPDFName(prefix + "1");
        }
        int n = dict.keySet().size();
        do {
            n++;
            key = prefix + n;
        } while (dict.containsKey(key));
        return COSName.getPDFName(key);
    }

    private void put(COSName kind, COSName name, COSObjectable object) {
        COSDictionary dict = (COSDictionary) this.resources.getDictionaryObject(kind, COSDictionary.class);
        if (dict == null) {
            dict = new COSDictionary();
            this.resources.setItem(kind, (COSBase) dict);
        }
        dict.setItem(name, object);
    }

    public void put(COSName name, PDFont font) {
        put(COSName.FONT, name, font);
    }

    public void put(COSName name, PDColorSpace colorSpace) {
        put(COSName.COLORSPACE, name, colorSpace);
    }

    public void put(COSName name, PDExtendedGraphicsState extGState) {
        put(COSName.EXT_G_STATE, name, extGState);
    }

    public void put(COSName name, PDShading shading) {
        put(COSName.SHADING, name, shading);
    }

    public void put(COSName name, PDAbstractPattern pattern) {
        put(COSName.PATTERN, name, pattern);
    }

    public void put(COSName name, PDPropertyList properties) {
        put(COSName.PROPERTIES, name, properties);
    }

    public void put(COSName name, PDXObject xobject) {
        put(COSName.XOBJECT, name, xobject);
    }

    public ResourceCache getResourceCache() {
        return this.cache;
    }
}

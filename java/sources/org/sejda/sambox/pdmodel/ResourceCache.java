package org.sejda.sambox.pdmodel;

import java.io.IOException;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.pdmodel.documentinterchange.markedcontent.PDPropertyList;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.sejda.sambox.pdmodel.graphics.shading.PDShading;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/ResourceCache.class */
public interface ResourceCache {
    PDFont getFont(COSObjectKey cOSObjectKey) throws IOException;

    PDColorSpace getColorSpace(COSObjectKey cOSObjectKey) throws IOException;

    PDExtendedGraphicsState getExtGState(COSObjectKey cOSObjectKey);

    PDShading getShading(COSObjectKey cOSObjectKey) throws IOException;

    PDAbstractPattern getPattern(COSObjectKey cOSObjectKey) throws IOException;

    PDPropertyList getProperties(COSObjectKey cOSObjectKey);

    PDXObject getXObject(COSObjectKey cOSObjectKey) throws IOException;

    void put(COSObjectKey cOSObjectKey, PDFont pDFont) throws IOException;

    void put(COSObjectKey cOSObjectKey, PDColorSpace pDColorSpace) throws IOException;

    void put(COSObjectKey cOSObjectKey, PDExtendedGraphicsState pDExtendedGraphicsState);

    void put(COSObjectKey cOSObjectKey, PDShading pDShading) throws IOException;

    void put(COSObjectKey cOSObjectKey, PDAbstractPattern pDAbstractPattern) throws IOException;

    void put(COSObjectKey cOSObjectKey, PDPropertyList pDPropertyList);

    void put(COSObjectKey cOSObjectKey, PDXObject pDXObject) throws IOException;

    void clear();
}

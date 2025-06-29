package org.sejda.sambox.pdmodel.graphics.color;

import java.awt.color.ICC_Profile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.common.PDStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/color/PDOutputIntent.class */
public final class PDOutputIntent implements COSObjectable {
    private final COSDictionary dictionary;

    public PDOutputIntent(PDDocument doc, InputStream colorProfile) throws IOException {
        this.dictionary = new COSDictionary();
        this.dictionary.setItem(COSName.TYPE, (COSBase) COSName.OUTPUT_INTENT);
        this.dictionary.setItem(COSName.S, (COSBase) COSName.GTS_PDFA1);
        PDStream destOutputIntent = configureOutputProfile(colorProfile);
        this.dictionary.setItem(COSName.DEST_OUTPUT_PROFILE, destOutputIntent);
    }

    public PDOutputIntent(COSDictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSBase getCOSObject() {
        return this.dictionary;
    }

    public COSStream getDestOutputIntent() {
        return (COSStream) this.dictionary.getItem(COSName.DEST_OUTPUT_PROFILE);
    }

    public String getInfo() {
        return this.dictionary.getString(COSName.INFO);
    }

    public void setInfo(String value) {
        this.dictionary.setString(COSName.INFO, value);
    }

    public String getOutputCondition() {
        return this.dictionary.getString(COSName.OUTPUT_CONDITION);
    }

    public void setOutputCondition(String value) {
        this.dictionary.setString(COSName.OUTPUT_CONDITION, value);
    }

    public String getOutputConditionIdentifier() {
        return this.dictionary.getString(COSName.OUTPUT_CONDITION_IDENTIFIER);
    }

    public void setOutputConditionIdentifier(String value) {
        this.dictionary.setString(COSName.OUTPUT_CONDITION_IDENTIFIER, value);
    }

    public String getRegistryName() {
        return this.dictionary.getString(COSName.REGISTRY_NAME);
    }

    public void setRegistryName(String value) {
        this.dictionary.setString(COSName.REGISTRY_NAME, value);
    }

    private PDStream configureOutputProfile(InputStream colorProfile) throws IOException {
        ICC_Profile icc = ICC_Profile.getInstance(colorProfile);
        PDStream stream = new PDStream((InputStream) new ByteArrayInputStream(icc.getData()), COSName.FLATE_DECODE);
        stream.getCOSObject().setInt(COSName.N, icc.getNumComponents());
        return stream;
    }
}

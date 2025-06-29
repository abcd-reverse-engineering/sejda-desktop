package org.sejda.sambox.pdmodel;

import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.PDDictionaryWrapper;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDocumentInformation.class */
public class PDDocumentInformation extends PDDictionaryWrapper {
    public PDDocumentInformation() {
    }

    public PDDocumentInformation(COSDictionary dictionary) {
        super(dictionary);
    }

    public Object getPropertyStringValue(String propertyKey) {
        return getCOSObject().getString(propertyKey);
    }

    public String getTitle() {
        return getCOSObject().getString(COSName.TITLE);
    }

    public void setTitle(String title) {
        getCOSObject().setString(COSName.TITLE, title);
    }

    public String getAuthor() {
        return getCOSObject().getString(COSName.AUTHOR);
    }

    public void setAuthor(String author) {
        getCOSObject().setString(COSName.AUTHOR, author);
    }

    public String getSubject() {
        return getCOSObject().getString(COSName.SUBJECT);
    }

    public void setSubject(String subject) {
        getCOSObject().setString(COSName.SUBJECT, subject);
    }

    public String getKeywords() {
        return getCOSObject().getString(COSName.KEYWORDS);
    }

    public void setKeywords(String keywords) {
        getCOSObject().setString(COSName.KEYWORDS, keywords);
    }

    public String getCreator() {
        return getCOSObject().getString(COSName.CREATOR);
    }

    public void setCreator(String creator) {
        getCOSObject().setString(COSName.CREATOR, creator);
    }

    public String getProducer() {
        return getCOSObject().getString(COSName.PRODUCER);
    }

    public void setProducer(String producer) {
        getCOSObject().setString(COSName.PRODUCER, producer);
    }

    public Calendar getCreationDate() {
        return getCOSObject().getDate(COSName.CREATION_DATE);
    }

    public void setCreationDate(Calendar date) {
        getCOSObject().setDate(COSName.CREATION_DATE, date);
    }

    public Calendar getModificationDate() {
        return getCOSObject().getDate(COSName.MOD_DATE);
    }

    public void setModificationDate(Calendar date) {
        getCOSObject().setDate(COSName.MOD_DATE, date);
    }

    public String getTrapped() {
        return getCOSObject().getNameAsString(COSName.TRAPPED);
    }

    public Set<String> getMetadataKeys() {
        Set<String> keys = new TreeSet<>();
        for (COSName key : getCOSObject().keySet()) {
            keys.add(key.getName());
        }
        return keys;
    }

    public String getCustomMetadataValue(String fieldName) {
        return getCOSObject().getString(fieldName);
    }

    public void setCustomMetadataValue(String fieldName, String fieldValue) {
        getCOSObject().setString(fieldName, fieldValue);
    }

    public void removeMetadataField(String fieldName) {
        getCOSObject().removeItem(COSName.getPDFName(fieldName));
    }

    public void setTrapped(String value) {
        if (value != null && !value.equals("True") && !value.equals("False") && !value.equals("Unknown")) {
            throw new IllegalArgumentException("Valid values for trapped are 'True', 'False', or 'Unknown'");
        }
        getCOSObject().setName(COSName.TRAPPED, value);
    }
}

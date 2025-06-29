package org.sejda.sambox.pdmodel.interactive.annotation;

import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.common.filespecification.FileSpecifications;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationFileAttachment.class */
public class PDAnnotationFileAttachment extends PDAnnotationMarkup {
    public static final String ATTACHMENT_NAME_PUSH_PIN = "PushPin";
    public static final String ATTACHMENT_NAME_GRAPH = "Graph";
    public static final String ATTACHMENT_NAME_PAPERCLIP = "Paperclip";
    public static final String ATTACHMENT_NAME_TAG = "Tag";
    public static final String SUB_TYPE = "FileAttachment";

    public PDAnnotationFileAttachment() {
        getCOSObject().setName(COSName.SUBTYPE, SUB_TYPE);
    }

    public PDAnnotationFileAttachment(COSDictionary field) {
        super(field);
    }

    public PDFileSpecification getFile() {
        return FileSpecifications.fileSpecificationFor(getCOSObject().getDictionaryObject("FS"));
    }

    public void setFile(PDFileSpecification file) {
        getCOSObject().setItem("FS", file);
    }

    public String getAttachmentName() {
        return getCOSObject().getNameAsString(COSName.NAME, ATTACHMENT_NAME_PUSH_PIN);
    }

    public void setAttachmentName(String name) {
        getCOSObject().setName(COSName.NAME, name);
    }
}

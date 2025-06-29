package org.sejda.sambox.pdmodel;

import java.util.Optional;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDocumentNameDictionary.class */
public class PDDocumentNameDictionary implements COSObjectable {
    private final COSDictionary nameDictionary;

    public PDDocumentNameDictionary(PDDocumentCatalog catalog) {
        this.nameDictionary = (COSDictionary) Optional.ofNullable((COSDictionary) catalog.getCOSObject().getDictionaryObject(COSName.NAMES, COSDictionary.class)).orElseGet(COSDictionary::new);
        catalog.getCOSObject().putIfAbsent(COSName.NAMES, (COSBase) this.nameDictionary);
    }

    public PDDocumentNameDictionary(COSDictionary names) {
        this.nameDictionary = names;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.nameDictionary;
    }

    public PDDestinationNameTreeNode getDests() {
        return (PDDestinationNameTreeNode) Optional.ofNullable((COSDictionary) this.nameDictionary.getDictionaryObject(COSName.DESTS, COSDictionary.class)).map(PDDestinationNameTreeNode::new).orElse(null);
    }

    public void setDests(PDDestinationNameTreeNode dests) {
        this.nameDictionary.setItem(COSName.DESTS, dests);
    }

    public PDEmbeddedFilesNameTreeNode getEmbeddedFiles() {
        return (PDEmbeddedFilesNameTreeNode) Optional.ofNullable((COSDictionary) this.nameDictionary.getDictionaryObject(COSName.EMBEDDED_FILES, COSDictionary.class)).map(PDEmbeddedFilesNameTreeNode::new).orElse(null);
    }

    public void setEmbeddedFiles(PDEmbeddedFilesNameTreeNode ef) {
        this.nameDictionary.setItem(COSName.EMBEDDED_FILES, ef);
    }

    public PDJavascriptNameTreeNode getJavaScript() {
        return (PDJavascriptNameTreeNode) Optional.ofNullable((COSDictionary) this.nameDictionary.getDictionaryObject(COSName.JAVA_SCRIPT, COSDictionary.class)).map(PDJavascriptNameTreeNode::new).orElse(null);
    }

    public void setJavascript(PDJavascriptNameTreeNode js) {
        this.nameDictionary.setItem(COSName.JAVA_SCRIPT, js);
    }
}

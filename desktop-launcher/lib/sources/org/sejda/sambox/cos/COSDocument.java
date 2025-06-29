package org.sejda.sambox.cos;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.util.SpecVersionUtils;
import org.sejda.sambox.xref.FileTrailer;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSDocument.class */
public class COSDocument extends COSBase {
    private String headerVersion;
    private final FileTrailer trailer;

    public COSDocument() {
        this(new FileTrailer(), SpecVersionUtils.V1_4);
        COSDictionary catalog = new COSDictionary();
        catalog.setItem(COSName.TYPE, (COSBase) COSName.CATALOG);
        this.trailer.getCOSObject().setItem(COSName.ROOT, (COSBase) catalog);
    }

    public COSDocument(FileTrailer trailer, String headerVersion) {
        RequireUtils.requireNotNullArg(trailer, "Trailer cannot be null");
        RequireUtils.requireNotBlank(headerVersion, "Header version cannot be blank");
        this.trailer = trailer;
        this.headerVersion = headerVersion;
        Optional.ofNullable((COSDictionary) this.trailer.getCOSObject().getDictionaryObject(COSName.ROOT, COSDictionary.class)).ifPresent(d -> {
            d.setItem(COSName.TYPE, (COSBase) COSName.CATALOG);
        });
    }

    public void setHeaderVersion(String headerVersion) {
        RequireUtils.requireNotBlank(headerVersion, "Header version cannot be null");
        this.headerVersion = headerVersion;
    }

    public String getHeaderVersion() {
        return this.headerVersion;
    }

    public boolean isEncrypted() {
        return Objects.nonNull(this.trailer.getCOSObject().getDictionaryObject(COSName.ENCRYPT, COSDictionary.class));
    }

    public COSDictionary getEncryptionDictionary() {
        return (COSDictionary) this.trailer.getCOSObject().getDictionaryObject(COSName.ENCRYPT, COSDictionary.class);
    }

    public void setEncryptionDictionary(COSDictionary dictionary) {
        this.trailer.getCOSObject().setItem(COSName.ENCRYPT, (COSBase) dictionary);
    }

    public COSArray getDocumentID() {
        return (COSArray) this.trailer.getCOSObject().getDictionaryObject(COSName.ID, COSArray.class);
    }

    public void setDocumentID(COSArray id) {
        this.trailer.getCOSObject().setItem(COSName.ID, (COSBase) id);
    }

    public COSDictionary getCatalog() {
        return (COSDictionary) Optional.ofNullable((COSDictionary) this.trailer.getCOSObject().getDictionaryObject(COSName.ROOT, COSDictionary.class)).orElseThrow(() -> {
            return new IllegalStateException("Catalog cannot be found");
        });
    }

    public FileTrailer getTrailer() {
        return this.trailer;
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }
}

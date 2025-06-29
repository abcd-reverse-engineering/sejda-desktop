package org.sejda.sambox.pdmodel.common.filespecification;

import java.util.Optional;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/common/filespecification/PDComplexFileSpecification.class */
public class PDComplexFileSpecification implements PDFileSpecification {
    private final COSDictionary fileSpecificationDictionary;
    private COSDictionary embeddedFileDictionary;

    public PDComplexFileSpecification(COSDictionary dict) {
        this.fileSpecificationDictionary = (COSDictionary) Optional.ofNullable(dict).orElseGet(COSDictionary::new);
        this.fileSpecificationDictionary.setItem(COSName.TYPE, (COSBase) COSName.FILESPEC);
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.fileSpecificationDictionary;
    }

    private COSDictionary getEFDictionary() {
        if (this.embeddedFileDictionary == null && this.fileSpecificationDictionary != null) {
            this.embeddedFileDictionary = (COSDictionary) this.fileSpecificationDictionary.getDictionaryObject(COSName.EF, COSDictionary.class);
        }
        return this.embeddedFileDictionary;
    }

    private COSBase getObjectFromEFDictionary(COSName key) {
        COSDictionary ef = getEFDictionary();
        if (ef != null) {
            return ef.getDictionaryObject(key);
        }
        return null;
    }

    public String getFilename() {
        String filename = getFileUnicode();
        if (filename == null) {
            filename = getFileDos();
        }
        if (filename == null) {
            filename = getFileMac();
        }
        if (filename == null) {
            filename = getFileUnix();
        }
        if (filename == null) {
            filename = getFile();
        }
        return filename;
    }

    public String getFileUnicode() {
        return this.fileSpecificationDictionary.getString(COSName.UF);
    }

    public void setFileUnicode(String file) {
        this.fileSpecificationDictionary.setString(COSName.UF, file);
    }

    @Override // org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification
    public String getFile() {
        return this.fileSpecificationDictionary.getString(COSName.F);
    }

    @Override // org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification
    public void setFile(String file) {
        this.fileSpecificationDictionary.setString(COSName.F, file);
    }

    public String getFileDos() {
        return this.fileSpecificationDictionary.getString(COSName.DOS);
    }

    @Deprecated
    public void setFileDos(String file) {
        this.fileSpecificationDictionary.setString(COSName.DOS, file);
    }

    public String getFileMac() {
        return this.fileSpecificationDictionary.getString(COSName.MAC);
    }

    @Deprecated
    public void setFileMac(String file) {
        this.fileSpecificationDictionary.setString(COSName.MAC, file);
    }

    public String getFileUnix() {
        return this.fileSpecificationDictionary.getString(COSName.UNIX);
    }

    @Deprecated
    public void setFileUnix(String file) {
        this.fileSpecificationDictionary.setString(COSName.UNIX, file);
    }

    public void setVolatile(boolean fileIsVolatile) {
        this.fileSpecificationDictionary.setBoolean(COSName.V, fileIsVolatile);
    }

    public boolean isVolatile() {
        return this.fileSpecificationDictionary.getBoolean(COSName.V, false);
    }

    public PDEmbeddedFile getEmbeddedFile() {
        return embeddedFileFor(COSName.F);
    }

    public void setEmbeddedFile(PDEmbeddedFile file) {
        COSDictionary ef = getEFDictionary();
        if (ef == null && file != null) {
            ef = new COSDictionary();
            this.fileSpecificationDictionary.setItem(COSName.EF, (COSBase) ef);
        }
        if (ef != null) {
            ef.setItem(COSName.F, file);
        }
    }

    public PDEmbeddedFile getEmbeddedFileDos() {
        return embeddedFileFor(COSName.DOS);
    }

    @Deprecated
    public void setEmbeddedFileDos(PDEmbeddedFile file) {
        COSDictionary ef = getEFDictionary();
        if (ef == null && file != null) {
            ef = new COSDictionary();
            this.fileSpecificationDictionary.setItem(COSName.EF, (COSBase) ef);
        }
        if (ef != null) {
            ef.setItem(COSName.DOS, file);
        }
    }

    public PDEmbeddedFile getEmbeddedFileMac() {
        return embeddedFileFor(COSName.MAC);
    }

    @Deprecated
    public void setEmbeddedFileMac(PDEmbeddedFile file) {
        COSDictionary ef = getEFDictionary();
        if (ef == null && file != null) {
            ef = new COSDictionary();
            this.fileSpecificationDictionary.setItem(COSName.EF, (COSBase) ef);
        }
        if (ef != null) {
            ef.setItem(COSName.MAC, file);
        }
    }

    public PDEmbeddedFile getEmbeddedFileUnix() {
        return embeddedFileFor(COSName.UNIX);
    }

    @Deprecated
    public void setEmbeddedFileUnix(PDEmbeddedFile file) {
        COSDictionary ef = getEFDictionary();
        if (ef == null && file != null) {
            ef = new COSDictionary();
            this.fileSpecificationDictionary.setItem(COSName.EF, (COSBase) ef);
        }
        if (ef != null) {
            ef.setItem(COSName.UNIX, file);
        }
    }

    public PDEmbeddedFile getEmbeddedFileUnicode() {
        return embeddedFileFor(COSName.UF);
    }

    private PDEmbeddedFile embeddedFileFor(COSName key) {
        return (PDEmbeddedFile) Optional.ofNullable(getObjectFromEFDictionary(key)).filter(s -> {
            return s instanceof COSStream;
        }).map(s2 -> {
            return (COSStream) s2;
        }).map(PDEmbeddedFile::new).orElse(null);
    }

    public void setEmbeddedFileUnicode(PDEmbeddedFile file) {
        COSDictionary ef = getEFDictionary();
        if (ef == null && file != null) {
            ef = new COSDictionary();
            this.fileSpecificationDictionary.setItem(COSName.EF, (COSBase) ef);
        }
        if (ef != null) {
            ef.setItem(COSName.UF, file);
        }
    }

    public PDEmbeddedFile getBestEmbeddedFile() {
        return (PDEmbeddedFile) Optional.ofNullable(getEmbeddedFileUnicode()).orElseGet(() -> {
            return (PDEmbeddedFile) Optional.ofNullable(getEmbeddedFileDos()).orElseGet(() -> {
                return (PDEmbeddedFile) Optional.ofNullable(getEmbeddedFileMac()).orElseGet(() -> {
                    return (PDEmbeddedFile) Optional.ofNullable(getEmbeddedFileUnix()).orElseGet(this::getEmbeddedFile);
                });
            });
        });
    }

    public void setFileDescription(String description) {
        this.fileSpecificationDictionary.setString(COSName.DESC, description);
    }

    public String getFileDescription() {
        return this.fileSpecificationDictionary.getString(COSName.DESC);
    }

    public void setCollectionItem(COSDictionary dictionary) {
        this.fileSpecificationDictionary.setItem(COSName.CI, (COSBase) dictionary);
    }
}

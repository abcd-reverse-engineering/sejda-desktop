package org.sejda.sambox.pdmodel.graphics.image;

import org.sejda.sambox.util.filetypedetector.FileType;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/graphics/image/UnsupportedImageFormatException.class */
public class UnsupportedImageFormatException extends IllegalArgumentException {
    private final FileType fileType;
    private final String filename;

    public UnsupportedImageFormatException(FileType fileType, String filename, Throwable cause) {
        super("Image type " + fileType + " not supported " + filename, cause);
        this.filename = filename;
        this.fileType = fileType;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public String getFilename() {
        return this.filename;
    }
}

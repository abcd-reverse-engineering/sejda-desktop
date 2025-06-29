package org.sejda.model.input;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.rotation.Rotation;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/input/FileIndexAndPage.class */
public class FileIndexAndPage {
    private int fileIndex;
    private int page;
    private Rotation rotation;

    public FileIndexAndPage(int fileIndex, int page) {
        this(fileIndex, page, Rotation.DEGREES_0);
    }

    public FileIndexAndPage(int fileIndex, int page, Rotation rotation) {
        this.fileIndex = fileIndex;
        this.page = page;
        this.rotation = rotation;
    }

    public int getFileIndex() {
        return this.fileIndex;
    }

    public int getPage() {
        return this.page;
    }

    public Rotation getRotation() {
        return this.rotation;
    }

    public int hashCode() {
        return new HashCodeBuilder().append(this.fileIndex).append(this.page).append(this.rotation).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FileIndexAndPage other = (FileIndexAndPage) obj;
        return new EqualsBuilder().append(this.fileIndex, other.fileIndex).append(this.page, other.page).append(this.rotation, other.rotation).isEquals();
    }

    public String toString() {
        return String.format("%d:%d:%d", Integer.valueOf(this.fileIndex), Integer.valueOf(this.page), Integer.valueOf(this.rotation.getDegrees()));
    }

    public boolean isAddBlankPage() {
        return this.page < 0 || this.fileIndex < 0;
    }
}

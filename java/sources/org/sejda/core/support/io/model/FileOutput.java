package org.sejda.core.support.io.model;

import java.io.File;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/io/model/FileOutput.class */
public final class FileOutput implements OngoingFileOuputCreation, PopulatedFileOutput {
    private File file;
    private String name;

    private FileOutput(File file) {
        this.file = file;
    }

    public static OngoingFileOuputCreation file(File file) {
        return new FileOutput(file);
    }

    @Override // org.sejda.core.support.io.model.PopulatedFileOutput
    public File getFile() {
        return this.file;
    }

    @Override // org.sejda.core.support.io.model.PopulatedFileOutput
    public String getName() {
        return this.name;
    }

    @Override // org.sejda.core.support.io.model.OngoingFileOuputCreation
    public PopulatedFileOutput name(String name) {
        this.name = name;
        return this;
    }
}

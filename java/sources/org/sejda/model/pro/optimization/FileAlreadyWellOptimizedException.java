package org.sejda.model.pro.optimization;

import org.sejda.model.exception.TaskException;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/optimization/FileAlreadyWellOptimizedException.class */
public class FileAlreadyWellOptimizedException extends TaskException {
    public FileAlreadyWellOptimizedException() {
        super("Your PDF file is already very well compressed");
    }
}

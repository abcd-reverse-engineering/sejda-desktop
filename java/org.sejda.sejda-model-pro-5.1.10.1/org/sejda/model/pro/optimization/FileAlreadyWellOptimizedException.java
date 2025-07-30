package org.sejda.model.pro.optimization;

import org.sejda.model.exception.TaskException;

public class FileAlreadyWellOptimizedException extends TaskException {
   public FileAlreadyWellOptimizedException() {
      super("Your PDF file is already very well compressed");
   }
}

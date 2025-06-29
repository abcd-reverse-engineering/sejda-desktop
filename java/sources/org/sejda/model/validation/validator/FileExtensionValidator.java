package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.File;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.validation.constraint.FileExtension;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/FileExtensionValidator.class */
public class FileExtensionValidator implements ConstraintValidator<FileExtension, File> {
    private String expectedExtension;

    public void initialize(FileExtension constraintAnnotation) {
        this.expectedExtension = constraintAnnotation.value();
    }

    public boolean isValid(File value, ConstraintValidatorContext context) {
        if (value != null && value.isFile()) {
            String extension = FilenameUtils.getExtension(value.getName());
            return StringUtils.equalsIgnoreCase(this.expectedExtension, extension) && FilenameUtils.indexOfExtension(value.getName()) > 0;
        }
        return true;
    }
}

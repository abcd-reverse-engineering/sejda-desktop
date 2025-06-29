package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.File;
import org.sejda.model.validation.constraint.Directory;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/DirectoryValidator.class */
public class DirectoryValidator implements ConstraintValidator<Directory, File> {
    public boolean isValid(File value, ConstraintValidatorContext context) {
        if (value != null && value.exists()) {
            return value.isDirectory();
        }
        return true;
    }
}

package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.File;
import org.sejda.model.validation.constraint.ExistingFile;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/ExistingFileValidator.class */
public class ExistingFileValidator implements ConstraintValidator<ExistingFile, File> {
    public boolean isValid(File value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.exists();
        }
        return true;
    }
}

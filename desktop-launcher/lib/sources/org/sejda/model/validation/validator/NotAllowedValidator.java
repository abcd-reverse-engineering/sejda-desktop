package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.validation.constraint.NotAllowed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/NotAllowedValidator.class */
public class NotAllowedValidator implements ConstraintValidator<NotAllowed, PredefinedSetOfPages> {
    private PredefinedSetOfPages[] disallow;

    public void initialize(NotAllowed constraintAnnotation) {
        this.disallow = constraintAnnotation.disallow();
    }

    public boolean isValid(PredefinedSetOfPages value, ConstraintValidatorContext context) {
        return value == null || this.disallow == null || !Arrays.asList(this.disallow).contains(value);
    }
}

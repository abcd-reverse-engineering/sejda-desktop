package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sejda.model.RectangularBox;
import org.sejda.model.validation.constraint.NotNegativeCoordinates;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/NotNegativeCoordinatesValidator.class */
public class NotNegativeCoordinatesValidator implements ConstraintValidator<NotNegativeCoordinates, RectangularBox> {
    public boolean isValid(RectangularBox value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.getBottom() >= 0 && value.getTop() >= 0 && value.getLeft() >= 0 && value.getRight() >= 0;
        }
        return true;
    }
}

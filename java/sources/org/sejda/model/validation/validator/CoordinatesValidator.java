package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sejda.model.RectangularBox;
import org.sejda.model.validation.constraint.ValidCoordinates;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/CoordinatesValidator.class */
public class CoordinatesValidator implements ConstraintValidator<ValidCoordinates, RectangularBox> {
    public boolean isValid(RectangularBox value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.getTop() > value.getBottom() && value.getRight() > value.getLeft();
        }
        return true;
    }
}

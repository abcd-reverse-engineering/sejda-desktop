package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.awt.geom.Dimension2D;
import org.sejda.model.validation.constraint.PositiveDimensions;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/PositiveDimensionValidator.class */
public class PositiveDimensionValidator implements ConstraintValidator<PositiveDimensions, Dimension2D> {
    public boolean isValid(Dimension2D value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.getWidth() > 0.0d && value.getHeight() > 0.0d;
        }
        return true;
    }
}

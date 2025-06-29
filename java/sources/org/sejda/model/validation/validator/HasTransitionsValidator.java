package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sejda.model.parameter.SetPagesTransitionParameters;
import org.sejda.model.validation.constraint.HasTransitions;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/HasTransitionsValidator.class */
public class HasTransitionsValidator implements ConstraintValidator<HasTransitions, SetPagesTransitionParameters> {
    public boolean isValid(SetPagesTransitionParameters value, ConstraintValidatorContext context) {
        return (value != null && value.getDefaultTransition() == null && value.getTransitions().isEmpty()) ? false : true;
    }
}

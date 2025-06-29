package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.sejda.model.output.ExistingOutputPolicy;
import org.sejda.model.parameter.base.SingleOutputTaskParameters;
import org.sejda.model.validation.constraint.ValidSingleOutput;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/SingleOutputValidator.class */
public class SingleOutputValidator implements ConstraintValidator<ValidSingleOutput, SingleOutputTaskParameters> {
    public boolean isValid(SingleOutputTaskParameters value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            if (Objects.isNull(value.getOutput())) {
                return false;
            }
            if (!Objects.isNull(value.getOutput().getDestination())) {
                if (value.getOutput().getDestination().exists()) {
                    if (value.getExistingOutputPolicy() != ExistingOutputPolicy.FAIL && value.getExistingOutputPolicy() != ExistingOutputPolicy.SKIP) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("File destination already exists: %s.", value.getOutput().getDestination())).addConstraintViolation();
            return false;
        }
        return true;
    }
}

package org.sejda.model.pro.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.pro.parameter.EncryptParameters;
import org.sejda.model.pro.validation.constraint.HasAPassword;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/validation/validator/HasAPasswordValidator.class */
public class HasAPasswordValidator implements ConstraintValidator<HasAPassword, EncryptParameters> {
    public void initialize(HasAPassword constraintAnnotation) {
    }

    public boolean isValid(EncryptParameters value, ConstraintValidatorContext context) {
        return value == null || StringUtils.isNotBlank(value.getOwnerPassword()) || StringUtils.isNotBlank(value.getUserPassword());
    }
}

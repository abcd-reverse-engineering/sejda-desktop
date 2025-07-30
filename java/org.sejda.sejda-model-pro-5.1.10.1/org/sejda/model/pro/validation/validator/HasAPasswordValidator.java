package org.sejda.model.pro.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.pro.parameter.EncryptParameters;
import org.sejda.model.pro.validation.constraint.HasAPassword;

public class HasAPasswordValidator implements ConstraintValidator<HasAPassword, EncryptParameters> {
   public void initialize(HasAPassword constraintAnnotation) {
   }

   public boolean isValid(EncryptParameters value, ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      } else {
         return StringUtils.isNotBlank(value.getOwnerPassword()) || StringUtils.isNotBlank(value.getUserPassword());
      }
   }
}

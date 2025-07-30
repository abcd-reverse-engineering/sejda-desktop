package org.sejda.model.pro.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.pro.validation.validator.HasAPasswordValidator;

@Constraint(
   validatedBy = {HasAPasswordValidator.class}
)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasAPassword {
   String message() default "A user or an owner password is required.";

   Class<?>[] groups() default {};

   Class<? extends Payload>[] payload() default {};
}

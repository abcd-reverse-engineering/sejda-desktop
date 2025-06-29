package org.sejda.model.pro.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.pro.validation.validator.HasAPasswordValidator;

@Target({ElementType.TYPE})
@Constraint(validatedBy = {HasAPasswordValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/validation/constraint/HasAPassword.class */
public @interface HasAPassword {
    String message() default "A user or an owner password is required.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

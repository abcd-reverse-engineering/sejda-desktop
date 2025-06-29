package org.sejda.model.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.validation.validator.SingleOutputValidator;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE})
@NotNull
@Constraint(validatedBy = {SingleOutputValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/constraint/ValidSingleOutput.class */
public @interface ValidSingleOutput {
    String message() default "Output not set.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

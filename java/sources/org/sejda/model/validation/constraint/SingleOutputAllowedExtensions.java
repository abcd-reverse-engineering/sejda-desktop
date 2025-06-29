package org.sejda.model.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.validation.validator.SingleOutputExtensionsValidator;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE})
@ValidSingleOutput
@Constraint(validatedBy = {SingleOutputExtensionsValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/constraint/SingleOutputAllowedExtensions.class */
public @interface SingleOutputAllowedExtensions {
    String message() default "TaskOutput is not of the expected type.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] extensions() default {"pdf"};
}

package org.sejda.model.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.validation.validator.NoIntersectionsValidator;

@Target({ElementType.FIELD, ElementType.TYPE})
@Constraint(validatedBy = {NoIntersectionsValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/constraint/NoIntersections.class */
public @interface NoIntersections {
    String message() default "The selected page ranges contain intersections.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

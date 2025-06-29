package org.sejda.model.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.validation.validator.FileValidator;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@NotNull
@Constraint(validatedBy = {FileValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/constraint/IsFile.class */
public @interface IsFile {
    String message() default "The given file instance is not a file.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

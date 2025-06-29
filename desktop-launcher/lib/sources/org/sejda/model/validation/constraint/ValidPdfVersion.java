package org.sejda.model.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.validation.validator.PdfVersionValidator;

@Target({ElementType.TYPE})
@NotNull
@ReportAsSingleViolation
@Constraint(validatedBy = {PdfVersionValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/constraint/ValidPdfVersion.class */
public @interface ValidPdfVersion {
    String message() default "The minimum pdf version required is higher then the selected one.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

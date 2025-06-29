package org.sejda.model.validation.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.sejda.model.SejdaFileExtensions;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@ReportAsSingleViolation
@FileExtension(SejdaFileExtensions.PDF_EXTENSION)
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/constraint/PdfFile.class */
public @interface PdfFile {
    String message() default "Not a valid pdf file.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

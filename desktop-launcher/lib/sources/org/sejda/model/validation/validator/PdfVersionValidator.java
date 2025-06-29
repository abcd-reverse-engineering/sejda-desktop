package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sejda.model.parameter.base.AbstractPdfOutputParameters;
import org.sejda.model.validation.constraint.ValidPdfVersion;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/PdfVersionValidator.class */
public class PdfVersionValidator implements ConstraintValidator<ValidPdfVersion, AbstractPdfOutputParameters> {
    public boolean isValid(AbstractPdfOutputParameters value, ConstraintValidatorContext context) {
        boolean isValid = value == null || value.getVersion() == null || value.getVersion().compareTo(value.getMinRequiredPdfVersion()) >= 0;
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("Invalid version %s. Minimum version required is %s.", value.getVersion(), value.getMinRequiredPdfVersion())).addPropertyNode("version").addConstraintViolation();
        }
        return isValid;
    }
}

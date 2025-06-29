package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sejda.model.parameter.ExtractPagesParameters;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.validation.constraint.HasSelectedPages;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/HasSelectedPagesValidator.class */
public class HasSelectedPagesValidator implements ConstraintValidator<HasSelectedPages, ExtractPagesParameters> {
    public boolean isValid(ExtractPagesParameters value, ConstraintValidatorContext context) {
        return value == null || value.getPredefinedSetOfPages() != PredefinedSetOfPages.NONE || value.hasPageSelection();
    }
}

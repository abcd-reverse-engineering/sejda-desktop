package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.validation.constraint.EndGreaterThenOrEqualToStart;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/PageRangeValidator.class */
public class PageRangeValidator implements ConstraintValidator<EndGreaterThenOrEqualToStart, PageRange> {
    public boolean isValid(PageRange value, ConstraintValidatorContext context) {
        return value == null || value.getStart() <= value.getEnd();
    }
}

package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PageRangeSelection;
import org.sejda.model.validation.constraint.NoIntersections;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/NoIntersectionsValidator.class */
public class NoIntersectionsValidator implements ConstraintValidator<NoIntersections, PageRangeSelection> {
    public boolean isValid(PageRangeSelection value, ConstraintValidatorContext context) {
        if (value != null) {
            List<PageRange> ranges = new ArrayList<>(value.getPageSelection());
            for (int i = 0; i < ranges.size(); i++) {
                PageRange range = ranges.get(i);
                for (int j = i + 1; j < ranges.size(); j++) {
                    PageRange current = ranges.get(j);
                    if (range.intersects(current)) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(String.format("Invalid page ranges, found an intersection between %s and %s", range, current)).addPropertyNode("page ranges").addConstraintViolation();
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }
}

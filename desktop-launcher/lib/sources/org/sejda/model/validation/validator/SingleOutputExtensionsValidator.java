package org.sejda.model.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.parameter.base.SingleOutputTaskParameters;
import org.sejda.model.validation.constraint.SingleOutputAllowedExtensions;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/validation/validator/SingleOutputExtensionsValidator.class */
public class SingleOutputExtensionsValidator implements ConstraintValidator<SingleOutputAllowedExtensions, SingleOutputTaskParameters> {
    private String[] extensions;

    public void initialize(SingleOutputAllowedExtensions constraintAnnotation) {
        this.extensions = constraintAnnotation.extensions();
    }

    public boolean isValid(SingleOutputTaskParameters value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value) && Objects.nonNull(value.getOutput()) && ArrayUtils.isNotEmpty(this.extensions)) {
            String fileName = value.getOutput().getDestination().getName();
            if (hasAllowedExtension(fileName)) {
                return true;
            }
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("The output '%s' is not one of the expected types: %s", fileName, ArrayUtils.toString(this.extensions))).addPropertyNode("taskOutput").addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean hasAllowedExtension(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        for (String current : this.extensions) {
            if (StringUtils.equalsIgnoreCase(current, extension) && FilenameUtils.indexOfExtension(fileName) > 0) {
                return true;
            }
        }
        return false;
    }
}

package org.sejda.core.validation;

import jakarta.validation.Configuration;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.sejda.core.context.DefaultSejdaConfiguration;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/validation/DefaultValidationContext.class */
public final class DefaultValidationContext implements ValidationContext {
    private Validator validator;

    private DefaultValidationContext() {
        Configuration<?> validationConfig = Validation.byDefaultProvider().configure();
        if (DefaultSejdaConfiguration.getInstance().isValidationIgnoringXmlConfiguration()) {
            validationConfig.ignoreXmlConfiguration();
        }
        ValidatorFactory factory = validationConfig.buildValidatorFactory();
        this.validator = factory.getValidator();
    }

    public static ValidationContext getContext() {
        return DefaultValidationContextHolder.VALIDATION_CONTEXT;
    }

    @Override // org.sejda.core.validation.ValidationContext
    public Validator getValidator() {
        return this.validator;
    }

    /* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/validation/DefaultValidationContext$DefaultValidationContextHolder.class */
    private static final class DefaultValidationContextHolder {
        static final DefaultValidationContext VALIDATION_CONTEXT = new DefaultValidationContext();

        private DefaultValidationContextHolder() {
        }
    }
}

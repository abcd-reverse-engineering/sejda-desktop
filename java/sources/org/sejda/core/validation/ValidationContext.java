package org.sejda.core.validation;

import jakarta.validation.Validator;

@FunctionalInterface
/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/validation/ValidationContext.class */
public interface ValidationContext {
    Validator getValidator();
}

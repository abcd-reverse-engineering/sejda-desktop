package org.sejda.sambox.pdmodel;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/InvalidNumberOfPagesException.class */
public class InvalidNumberOfPagesException extends RuntimeException {
    public InvalidNumberOfPagesException(int actual, int expected) {
        super("Actual number of pages is different than expected: " + expected + ", actual: " + actual);
    }
}

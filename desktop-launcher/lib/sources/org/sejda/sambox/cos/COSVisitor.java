package org.sejda.sambox.cos;

import java.io.IOException;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSVisitor.class */
public interface COSVisitor {
    default void visit(COSDocument value) throws IOException {
    }

    default void visit(COSArray value) throws IOException {
    }

    default void visit(COSBoolean value) throws IOException {
    }

    default void visit(COSDictionary value) throws IOException {
    }

    default void visit(COSFloat value) throws IOException {
    }

    default void visit(COSInteger value) throws IOException {
    }

    default void visit(COSName value) throws IOException {
    }

    default void visit(COSNull value) throws IOException {
    }

    default void visit(COSStream value) throws IOException {
    }

    default void visit(COSString value) throws IOException {
    }

    default void visit(IndirectCOSObjectReference value) throws IOException {
    }
}

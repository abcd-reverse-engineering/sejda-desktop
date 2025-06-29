package com.sejda.pdf2html.sambox;

import java.io.Serializable;
import java.util.List;
import org.sejda.sambox.text.TextPosition;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

/* compiled from: HeaderFooterRemover.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/PdfLine$.class */
public final class PdfLine$ implements Serializable {
    public static final PdfLine$ MODULE$ = new PdfLine$();

    public PdfLine apply(final List<TextPosition> lineSeq) {
        return new PdfLine(lineSeq);
    }

    public Option<List<TextPosition>> unapply(final PdfLine x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(x$0.lineSeq());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(PdfLine$.class);
    }

    private PdfLine$() {
    }

    public PdfLine lineToPdfLine(final List<TextPosition> lineSeq) {
        return new PdfLine(lineSeq);
    }
}

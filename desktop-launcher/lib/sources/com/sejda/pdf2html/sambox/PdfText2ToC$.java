package com.sejda.pdf2html.sambox;

import java.io.File;
import java.io.IOException;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.input.PDFParser;
import org.sejda.sambox.pdmodel.PDDocument;
import scala.Function1;
import scala.runtime.BoxesRunTime;

/* compiled from: PdfText2ToC.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/PdfText2ToC$.class */
public final class PdfText2ToC$ {
    public static final PdfText2ToC$ MODULE$ = new PdfText2ToC$();

    private PdfText2ToC$() {
    }

    public <A> PdfText2ToC apply(final File in, final String bookFile) throws IOException {
        PDDocument doc = PDFParser.parse(SeekableSources.seekableSourceFrom(in));
        Function1 anchorFactory = pageNumber -> {
            return $anonfun$apply$1(bookFile, BoxesRunTime.unboxToInt(pageNumber));
        };
        return new PdfText2ToC(doc, anchorFactory);
    }

    public static final /* synthetic */ String $anonfun$apply$1(final String bookFile$1, final int pageNumber) {
        return new StringBuilder(5).append(bookFile$1).append("#page").append(pageNumber).toString();
    }

    public Function1<Object, String> $lessinit$greater$default$2() {
        return pageNumber -> {
            return $anonfun$$lessinit$greater$default$2$1(BoxesRunTime.unboxToInt(pageNumber));
        };
    }

    public static final /* synthetic */ String $anonfun$$lessinit$greater$default$2$1(final int pageNumber) {
        return new StringBuilder(5).append("#page").append(pageNumber).toString();
    }
}

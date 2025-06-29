package com.sejda.pdf2html.sambox;

import java.io.File;
import java.io.IOException;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.input.PDFParser;
import org.sejda.sambox.pdmodel.PDDocument;
import scala.None$;
import scala.Option;

/* compiled from: OpfFileWriter.scala */
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/OPFFileWriter$.class */
public final class OPFFileWriter$ {
    public static final OPFFileWriter$ MODULE$ = new OPFFileWriter$();

    private OPFFileWriter$() {
    }

    public <A> OPFFileWriter apply(final File in) throws IOException {
        PDDocument doc = PDFParser.parse(SeekableSources.seekableSourceFrom(in));
        return new OPFFileWriter(doc, $lessinit$greater$default$2());
    }

    public Option<String> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }
}

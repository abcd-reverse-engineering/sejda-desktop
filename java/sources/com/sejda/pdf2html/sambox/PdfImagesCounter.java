package com.sejda.pdf2html.sambox;

import java.io.IOException;
import java.util.List;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import scala.None$;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: PdfImagesCounter.scala */
@ScalaSignature(bytes = "\u0006\u0005E3Aa\u0002\u0005\u0001#!AA\u0004\u0001B\u0001B\u0003%Q\u0004C\u0003$\u0001\u0011\u0005A\u0005C\u0004)\u0001\u0001\u0007I\u0011A\u0015\t\u000fA\u0002\u0001\u0019!C\u0001c!1q\u0007\u0001Q!\n)BQ\u0001\u000f\u0001\u0005Be\u0012\u0001\u0003\u00153g\u00136\fw-Z:D_VtG/\u001a:\u000b\u0005%Q\u0011AB:b[\n|\u0007P\u0003\u0002\f\u0019\u0005A\u0001\u000f\u001a43QRlGN\u0003\u0002\u000e\u001d\u0005)1/\u001a6eC*\tq\"A\u0002d_6\u001c\u0001a\u0005\u0002\u0001%A\u00111CG\u0007\u0002))\u0011QCF\u0001\u000eG>tG/\u001a8ugR\u0014X-Y7\u000b\u0005%9\"BA\u0007\u0019\u0015\u0005I\u0012aA8sO&\u00111\u0004\u0006\u0002\u0010!\u001235\u000b\u001e:fC6,enZ5oK\u0006\u0019Am\\2\u0011\u0005y\tS\"A\u0010\u000b\u0005\u00012\u0012a\u00029e[>$W\r\\\u0005\u0003E}\u0011!\u0002\u0015#E_\u000e,X.\u001a8u\u0003\u0019a\u0014N\\5u}Q\u0011Qe\n\t\u0003M\u0001i\u0011\u0001\u0003\u0005\u00069\t\u0001\r!H\u0001\u0006G>,h\u000e^\u000b\u0002UA\u00111FL\u0007\u0002Y)\tQ&A\u0003tG\u0006d\u0017-\u0003\u00020Y\t\u0019\u0011J\u001c;\u0002\u0013\r|WO\u001c;`I\u0015\fHC\u0001\u001a6!\tY3'\u0003\u00025Y\t!QK\\5u\u0011\u001d1D!!AA\u0002)\n1\u0001\u001f\u00132\u0003\u0019\u0019w.\u001e8uA\u0005y\u0001O]8dKN\u001cx\n]3sCR|'\u000fF\u00023u\u0005CQa\u000f\u0004A\u0002q\n\u0001b\u001c9fe\u0006$xN\u001d\t\u0003{}j\u0011A\u0010\u0006\u0003wQI!\u0001\u0011 \u0003\u0011=\u0003XM]1u_JDQA\u0011\u0004A\u0002\r\u000b\u0011\"\u0019:hk6,g\u000e^:\u0011\u0007\u0011K5*D\u0001F\u0015\t1u)\u0001\u0003vi&d'\"\u0001%\u0002\t)\fg/Y\u0005\u0003\u0015\u0016\u0013A\u0001T5tiB\u0011AjT\u0007\u0002\u001b*\u0011aJF\u0001\u0004G>\u001c\u0018B\u0001)N\u0005\u001d\u0019uj\u0015\"bg\u0016\u0004")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/sambox/PdfImagesCounter.class */
public class PdfImagesCounter extends PDFStreamEngine {
    private int count = 0;

    public PdfImagesCounter(final PDDocument doc) {
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaIteratorConverter(doc.getDocumentCatalog().getPages().iterator()).asScala()).foreach(pdPage -> {
            this.processPage(pdPage);
            return BoxedUnit.UNIT;
        });
    }

    public int count() {
        return this.count;
    }

    public void count_$eq(final int x$1) {
        this.count = x$1;
    }

    @Override // org.sejda.sambox.contentstream.PDFStreamEngine
    public void processOperator(final Operator operator, final List<COSBase> arguments) throws IOException {
        String name = operator.getName();
        switch (name == null ? 0 : name.hashCode()) {
            case 2219:
                if (OperatorName.DRAW_OBJECT.equals(name)) {
                    ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(arguments).asScala()).headOption().map(x$1 -> {
                        return (COSName) x$1;
                    }).foreach(objectName -> {
                        if (this.getResources().getXObject(objectName) instanceof PDImageXObject) {
                            this.count_$eq(this.count() + 1);
                            return BoxedUnit.UNIT;
                        }
                        return None$.MODULE$;
                    });
                    return;
                }
                break;
        }
        super.processOperator(operator, arguments);
    }
}

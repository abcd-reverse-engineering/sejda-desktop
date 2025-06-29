package code.sejda.tasks.excel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import scala.reflect.ScalaSignature;

/* compiled from: PdfToExcelNextParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005u3A\u0001E\t\u00015!A\u0001\u0006\u0001BC\u0002\u0013\u0005\u0011\u0006\u0003\u00051\u0001\t\u0005\t\u0015!\u0003+\u0011!\t\u0004A!b\u0001\n\u0003I\u0003\u0002\u0003\u001a\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0016\t\u0011M\u0002!Q1A\u0005\u0002%B\u0001\u0002\u000e\u0001\u0003\u0002\u0003\u0006IA\u000b\u0005\u0006k\u0001!\tA\u000e\u0005\u0006y\u0001!\t%\u0010\u0005\u0006\u0007\u0002!\t\u0005R\u0004\b\u0011F\t\t\u0011#\u0001J\r\u001d\u0001\u0012#!A\t\u0002)CQ!N\u0006\u0005\u00029CqaT\u0006\u0012\u0002\u0013\u0005\u0001\u000bC\u0004\\\u0017E\u0005I\u0011\u0001)\t\u000fq[\u0011\u0013!C\u0001!\nA\u0002\u000b\u001a4U_\u0016C8-\u001a7OKb$\b+\u0019:b[\u0016$XM]:\u000b\u0005I\u0019\u0012!B3yG\u0016d'B\u0001\u000b\u0016\u0003\u0015!\u0018m]6t\u0015\t1r#A\u0003tK*$\u0017MC\u0001\u0019\u0003\u0011\u0019w\u000eZ3\u0004\u0001M\u0011\u0001a\u0007\t\u00039\u0019j\u0011!\b\u0006\u0003=}\tAAY1tK*\u0011\u0001%I\u0001\na\u0006\u0014\u0018-\\3uKJT!AI\u0012\u0002\u000b5|G-\u001a7\u000b\u0005Y!#\"A\u0013\u0002\u0007=\u0014x-\u0003\u0002(;\tIS*\u001e7uSBdW\r\u00153g'>,(oY3Nk2$\u0018\u000e\u001d7f\u001fV$\b/\u001e;QCJ\fW.\u001a;feN\f\u0001%\\3sO\u0016$\u0016M\u00197fgN\u0003\u0018M\u001c8j]\u001elU\u000f\u001c;ja2,\u0007+Y4fgV\t!\u0006\u0005\u0002,]5\tAFC\u0001.\u0003\u0015\u00198-\u00197b\u0013\tyCFA\u0004C_>dW-\u00198\u0002C5,'oZ3UC\ndWm]*qC:t\u0017N\\4Nk2$\u0018\u000e\u001d7f!\u0006<Wm\u001d\u0011\u0002\u0013\r\u001chOR8s[\u0006$\u0018AC2tm\u001a{'/\\1uA\u0005Y1/\u001b8hY\u0016\u001c\u0006.Z3u\u00031\u0019\u0018N\\4mKNCW-\u001a;!\u0003\u0019a\u0014N\\5u}Q!q'\u000f\u001e<!\tA\u0004!D\u0001\u0012\u0011\u001dAs\u0001%AA\u0002)Bq!M\u0004\u0011\u0002\u0003\u0007!\u0006C\u00044\u000fA\u0005\t\u0019\u0001\u0016\u0002\r\u0015\fX/\u00197t)\tQc\bC\u0003@\u0011\u0001\u0007\u0001)A\u0003pi\",'\u000f\u0005\u0002,\u0003&\u0011!\t\f\u0002\u0004\u0003:L\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003\u0015\u0003\"a\u000b$\n\u0005\u001dc#aA%oi\u0006A\u0002\u000b\u001a4U_\u0016C8-\u001a7OKb$\b+\u0019:b[\u0016$XM]:\u0011\u0005aZ1CA\u0006L!\tYC*\u0003\u0002NY\t1\u0011I\\=SK\u001a$\u0012!S\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u0016\u0003ES#A\u000b*,\u0003M\u0003\"\u0001V-\u000e\u0003US!AV,\u0002\u0013Ut7\r[3dW\u0016$'B\u0001--\u0003)\tgN\\8uCRLwN\\\u0005\u00035V\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%e\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/excel/PdfToExcelNextParameters.class */
public class PdfToExcelNextParameters extends MultiplePdfSourceMultipleOutputParameters {
    private final boolean mergeTablesSpanningMultiplePages;
    private final boolean csvFormat;
    private final boolean singleSheet;

    public boolean mergeTablesSpanningMultiplePages() {
        return this.mergeTablesSpanningMultiplePages;
    }

    public PdfToExcelNextParameters(final boolean mergeTablesSpanningMultiplePages, final boolean csvFormat, final boolean singleSheet) {
        this.mergeTablesSpanningMultiplePages = mergeTablesSpanningMultiplePages;
        this.csvFormat = csvFormat;
        this.singleSheet = singleSheet;
    }

    public boolean csvFormat() {
        return this.csvFormat;
    }

    public boolean singleSheet() {
        return this.singleSheet;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(final Object other) {
        if (other instanceof PdfToExcelNextParameters) {
            PdfToExcelNextParameters pdfToExcelNextParameters = (PdfToExcelNextParameters) other;
            return new EqualsBuilder().appendSuper(super.equals(pdfToExcelNextParameters)).append(mergeTablesSpanningMultiplePages(), pdfToExcelNextParameters.mergeTablesSpanningMultiplePages()).append(csvFormat(), pdfToExcelNextParameters.csvFormat()).isEquals();
        }
        return false;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(mergeTablesSpanningMultiplePages()).append(csvFormat()).toHashCode();
    }
}

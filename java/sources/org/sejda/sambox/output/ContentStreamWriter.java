package org.sejda.sambox.output;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.apache.fontbox.util.Charsets;
import org.sejda.io.BufferedCountingChannelWriter;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSBoolean;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNull;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.cos.IndirectCOSObjectReference;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/ContentStreamWriter.class */
public class ContentStreamWriter extends DefaultCOSWriter {
    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.output.COSWriter
    public /* bridge */ /* synthetic */ BufferedCountingChannelWriter writer() {
        return super.writer();
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSString cOSString) throws IOException {
        super.visit(cOSString);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSNull cOSNull) throws IOException {
        super.visit(cOSNull);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSName cOSName) throws IOException {
        super.visit(cOSName);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSInteger cOSInteger) throws IOException {
        super.visit(cOSInteger);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSFloat cOSFloat) throws IOException {
        super.visit(cOSFloat);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSDictionary cOSDictionary) throws IOException {
        super.visit(cOSDictionary);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSBoolean cOSBoolean) throws IOException {
        super.visit(cOSBoolean);
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public /* bridge */ /* synthetic */ void visit(COSArray cOSArray) throws IOException {
        super.visit(cOSArray);
    }

    public ContentStreamWriter(CountingWritableByteChannel channel) {
        super(channel);
    }

    public ContentStreamWriter(BufferedCountingChannelWriter writer) {
        super(writer);
    }

    public void writeTokens(List<Object> tokens) throws IOException {
        for (Object token : tokens) {
            if (token instanceof COSBase) {
                ((COSBase) token).accept(this);
                writeSpace();
            } else if (token instanceof Operator) {
                writeOperator((Operator) token);
            } else {
                throw new IOException("Unsupported type in content stream:" + token);
            }
        }
    }

    public void writeTokens(Operator... tokens) throws IOException {
        for (Operator token : tokens) {
            writeOperator(token);
        }
    }

    public void writeOperator(List<COSBase> operands, Operator operator) throws IOException {
        for (COSBase operand : operands) {
            operand.accept(this);
            writeSpace();
        }
        writeOperator(operator);
    }

    public void writeContent(byte[] byteArray) throws IOException {
        writer().write(byteArray);
    }

    public void writeEOL() throws IOException {
        writer().writeEOL();
    }

    public void writeSpace() throws IOException {
        writer().write((byte) 32);
    }

    public void writeComment(String comment) throws IOException {
        writer().write((byte) 37);
        writeContent(comment.getBytes(Charsets.US_ASCII));
        writeEOL();
    }

    private void writeOperator(Operator token) throws IOException {
        writer().write(token.getName().getBytes(StandardCharsets.ISO_8859_1));
        if (token.getName().equals(OperatorName.BEGIN_INLINE_IMAGE)) {
            writeEOL();
            COSDictionary imageParams = (COSDictionary) Optional.ofNullable(token.getImageParameters()).orElseGet(COSDictionary::new);
            for (COSName key : imageParams.keySet()) {
                key.accept(this);
                writeSpace();
                COSBase imageParamsDictionaryObject = imageParams.getDictionaryObject(key);
                if (imageParamsDictionaryObject != null) {
                    imageParamsDictionaryObject.accept(this);
                }
                writeEOL();
            }
            writer().write(OperatorName.BEGIN_INLINE_IMAGE_DATA.getBytes(StandardCharsets.US_ASCII));
            writeEOL();
            writer().write(token.getImageData());
            writeEOL();
            writer().write(OperatorName.END_INLINE_IMAGE.getBytes(StandardCharsets.US_ASCII));
        }
        writeEOL();
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public void visit(COSStream value) {
        throw new UnsupportedOperationException("Cannot write a stream inside a stream");
    }

    @Override // org.sejda.sambox.output.DefaultCOSWriter, org.sejda.sambox.cos.COSVisitor
    public void visit(IndirectCOSObjectReference value) {
        throw new UnsupportedOperationException("Cannot write an indirect object reference inside a stream");
    }

    @Override // org.sejda.sambox.output.COSWriter
    public void writeComplexObjectSeparator() {
    }
}

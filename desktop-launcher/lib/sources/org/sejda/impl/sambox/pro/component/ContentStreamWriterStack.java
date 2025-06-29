package org.sejda.impl.sambox.pro.component;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import org.sejda.commons.util.IOUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.output.ContentStreamWriter;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/ContentStreamWriterStack.class */
public class ContentStreamWriterStack {
    private Deque<ContentStreamWriter> writers = new ArrayDeque();

    public COSStream newWriter() {
        COSStream stream = new COSStream();
        this.writers.offerFirst(new ContentStreamWriter(CountingWritableByteChannel.from(stream.createFilteredStream(COSName.FLATE_DECODE))));
        return stream;
    }

    public void closeCurrentWriter() {
        Optional.ofNullable(this.writers.poll()).ifPresent((v0) -> {
            IOUtils.closeQuietly(v0);
        });
    }

    public void writeOperator(List<COSBase> operands, Operator operator) throws IOException {
        Optional<ContentStreamWriter> writer = Optional.ofNullable(this.writers.peek());
        if (writer.isPresent()) {
            writer.get().writeOperator(operands, operator);
            return;
        }
        throw new IOException("Cannot write content stream, missing writer");
    }
}

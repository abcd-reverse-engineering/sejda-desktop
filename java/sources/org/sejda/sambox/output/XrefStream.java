package org.sejda.sambox.output;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.DirectCOSObject;
import org.sejda.sambox.xref.XrefEntry;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/output/XrefStream.class */
class XrefStream extends COSStream {
    XrefStream(COSDictionary dictionary, PDFWriteContext context) throws IOException {
        super(dictionary);
        setItem(COSName.TYPE, (COSBase) COSName.XREF);
        setItem(COSName.SIZE, (COSBase) asDirect(context.highestObjectNumber() + 1));
        COSArray index = new COSArray();
        for (List<Long> continuos : context.getWrittenContiguousGroups()) {
            index.add((COSBase) asDirect(continuos.get(0).longValue()));
            index.add((COSBase) asDirect(continuos.size()));
        }
        setItem(COSName.INDEX, (COSBase) DirectCOSObject.asDirectObject(index));
        int secondFieldLength = sizeOf(context.highestWritten().getByteOffset());
        setItem(COSName.W, (COSBase) DirectCOSObject.asDirectObject(new COSArray(asDirect(1L), asDirect(secondFieldLength), asDirect(2L))));
        OutputStream out = createUnfilteredStream();
        try {
            Iterator<List<Long>> it = context.getWrittenContiguousGroups().iterator();
            while (it.hasNext()) {
                Iterator<Long> it2 = it.next().iterator();
                while (it2.hasNext()) {
                    long key = it2.next().longValue();
                    out.write(((XrefEntry) Optional.ofNullable(context.getWritten(Long.valueOf(key))).orElse(XrefEntry.freeEntry(key, 0))).toXrefStreamEntry(secondFieldLength, 2));
                }
            }
            if (out != null) {
                out.close();
            }
            setItem(COSName.DL, (COSBase) asDirect(getUnfilteredLength()));
            setItem(COSName.FILTER, (COSBase) DirectCOSObject.asDirectObject(COSName.FLATE_DECODE));
        } catch (Throwable th) {
            if (out != null) {
                try {
                    out.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static DirectCOSObject asDirect(long num) {
        return DirectCOSObject.asDirectObject(COSInteger.get(num));
    }

    @Override // org.sejda.sambox.cos.COSStream, org.sejda.sambox.cos.Encryptable
    public boolean encryptable() {
        return false;
    }

    @Override // org.sejda.sambox.cos.COSStream, org.sejda.sambox.cos.Encryptable
    public void encryptable(boolean encryptable) {
    }

    @Override // org.sejda.sambox.cos.COSStream
    public void setEncryptor(Function<InputStream, InputStream> encryptor) {
    }

    private static int sizeOf(long number) {
        int size = 0;
        while (number > 0) {
            size++;
            number >>= 8;
        }
        return size;
    }
}

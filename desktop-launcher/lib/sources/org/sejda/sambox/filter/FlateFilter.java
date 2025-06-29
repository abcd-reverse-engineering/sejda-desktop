package org.sejda.sambox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import org.sejda.commons.util.IOUtils;
import org.sejda.sambox.cos.COSDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/FlateFilter.class */
final class FlateFilter extends Filter {
    private static final Logger LOG = LoggerFactory.getLogger(FlateFilter.class);

    FlateFilter() {
    }

    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        COSDictionary decodeParams = getDecodeParams(parameters, index);
        try {
            decompress(encoded, Predictor.wrapPredictor(decoded, decodeParams));
            return new DecodeResult(parameters);
        } catch (DataFormatException e) {
            LOG.error("FlateFilter: stop reading corrupt stream due to a DataFormatException");
            throw new IOException(e);
        }
    }

    private void decompress(InputStream in, OutputStream out) throws DataFormatException, IOException {
        byte[] buf = new byte[2048];
        in.read();
        in.read();
        int read = in.read(buf);
        if (read > 0) {
            Inflater inflater = new Inflater(true);
            inflater.setInput(buf, 0, read);
            byte[] res = new byte[1024];
            boolean dataWritten = false;
            while (true) {
                try {
                    try {
                        int resRead = inflater.inflate(res);
                        if (resRead != 0) {
                            out.write(res, 0, resRead);
                            dataWritten = true;
                        } else if (inflater.finished() || inflater.needsDictionary() || in.available() == 0) {
                            break;
                        } else {
                            inflater.setInput(buf, 0, in.read(buf));
                        }
                    } catch (DataFormatException exception) {
                        if (dataWritten) {
                            LOG.warn("FlateFilter: premature end of stream due to a DataFormatException");
                        } else {
                            throw exception;
                        }
                    }
                } finally {
                    inflater.end();
                }
            }
        }
        out.flush();
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream input, OutputStream encoded, COSDictionary parameters) throws IOException {
        int compressionLevel = getCompressionLevel();
        Deflater deflater = new Deflater(compressionLevel);
        DeflaterOutputStream out = new DeflaterOutputStream(encoded, deflater);
        try {
            IOUtils.copy(input, out);
            out.close();
            encoded.flush();
        } catch (Throwable th) {
            try {
                out.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}

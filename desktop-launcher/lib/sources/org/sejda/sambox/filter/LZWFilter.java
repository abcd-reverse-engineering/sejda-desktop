package org.sejda.sambox.filter;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/filter/LZWFilter.class */
public class LZWFilter extends Filter {
    public static final long CLEAR_TABLE = 256;
    public static final long EOD = 257;
    private static final Logger LOG = LoggerFactory.getLogger(LZWFilter.class);
    private static final List<byte[]> INITIAL_CODE_TABLE = createInitialCodeTable();

    @Override // org.sejda.sambox.filter.Filter
    public DecodeResult decode(InputStream encoded, OutputStream decoded, COSDictionary parameters, int index) throws IOException {
        COSDictionary decodeParams = getDecodeParams(parameters, index);
        boolean earlyChange = decodeParams.getInt(COSName.EARLY_CHANGE, 1) != 0;
        doLZWDecode(encoded, Predictor.wrapPredictor(decoded, decodeParams), earlyChange);
        return new DecodeResult(parameters);
    }

    private void doLZWDecode(InputStream encoded, OutputStream decoded, boolean earlyChange) throws IOException {
        List<byte[]> codeTable = new ArrayList<>();
        int chunk = 9;
        long prevCommand = -1;
        try {
            MemoryCacheImageInputStream in = new MemoryCacheImageInputStream(encoded);
            while (true) {
                try {
                    long nextCommand = in.readBits(chunk);
                    if (nextCommand == 257) {
                        break;
                    }
                    if (nextCommand == 256) {
                        chunk = 9;
                        codeTable = createCodeTable();
                        prevCommand = -1;
                    } else {
                        if (nextCommand < codeTable.size()) {
                            byte[] data = codeTable.get((int) nextCommand);
                            byte firstByte = data[0];
                            decoded.write(data);
                            if (prevCommand != -1) {
                                checkIndexBounds(codeTable, prevCommand, in);
                                byte[] data2 = codeTable.get((int) prevCommand);
                                byte[] newData = Arrays.copyOf(data2, data2.length + 1);
                                newData[data2.length] = firstByte;
                                codeTable.add(newData);
                            }
                        } else {
                            checkIndexBounds(codeTable, prevCommand, in);
                            byte[] data3 = codeTable.get((int) prevCommand);
                            byte[] newData2 = Arrays.copyOf(data3, data3.length + 1);
                            newData2[data3.length] = data3[0];
                            decoded.write(newData2);
                            codeTable.add(newData2);
                        }
                        chunk = calculateChunk(codeTable.size(), earlyChange);
                        prevCommand = nextCommand;
                    }
                } finally {
                }
            }
            in.close();
        } catch (EOFException e) {
            LOG.warn("Premature EOF in LZW stream, EOD code missing");
        }
        decoded.flush();
    }

    private static void checkIndexBounds(List<byte[]> codeTable, long index, MemoryCacheImageInputStream in) throws IOException {
        if (index < 0) {
            in.getStreamPosition();
            IOException iOException = new IOException("negative array index: " + index + " near offset " + iOException);
            throw iOException;
        }
        if (index >= codeTable.size()) {
            int size = codeTable.size();
            in.getStreamPosition();
            IOException iOException2 = new IOException("array index overflow: " + index + " >= " + iOException2 + " near offset " + size);
            throw iOException2;
        }
    }

    @Override // org.sejda.sambox.filter.Filter
    public void encode(InputStream rawData, OutputStream encoded, COSDictionary parameters) throws IOException {
        List<byte[]> codeTable = createCodeTable();
        byte[] inputPattern = null;
        MemoryCacheImageOutputStream out = new MemoryCacheImageOutputStream(encoded);
        try {
            out.writeBits(256L, 9);
            int foundCode = -1;
            while (true) {
                int r = rawData.read();
                if (r == -1) {
                    break;
                }
                byte by = (byte) r;
                if (inputPattern == null) {
                    inputPattern = new byte[]{by};
                    foundCode = by & 255;
                } else {
                    inputPattern = Arrays.copyOf(inputPattern, inputPattern.length + 1);
                    inputPattern[inputPattern.length - 1] = by;
                    int newFoundCode = findPatternCode(codeTable, inputPattern);
                    if (newFoundCode == -1) {
                        int chunk = calculateChunk(codeTable.size() - 1, true);
                        out.writeBits(foundCode, chunk);
                        codeTable.add(inputPattern);
                        if (codeTable.size() == 4096) {
                            out.writeBits(256L, chunk);
                            codeTable = createCodeTable();
                        }
                        inputPattern = new byte[]{by};
                        foundCode = by & 255;
                    } else {
                        foundCode = newFoundCode;
                    }
                }
            }
            if (foundCode != -1) {
                int chunk2 = calculateChunk(codeTable.size() - 1, true);
                out.writeBits(foundCode, chunk2);
            }
            int chunk3 = calculateChunk(codeTable.size(), true);
            out.writeBits(257L, chunk3);
            out.writeBits(0L, 7);
            out.flush();
            out.close();
        } catch (Throwable th) {
            try {
                out.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static int findPatternCode(List<byte[]> codeTable, byte[] pattern) {
        if (pattern.length == 1) {
            return pattern[0];
        }
        for (int i = 257; i < codeTable.size(); i++) {
            if (Arrays.equals(codeTable.get(i), pattern)) {
                return i;
            }
        }
        return -1;
    }

    private static List<byte[]> createCodeTable() {
        List<byte[]> codeTable = new ArrayList<>(4096);
        codeTable.addAll(INITIAL_CODE_TABLE);
        return codeTable;
    }

    private static List<byte[]> createInitialCodeTable() {
        List<byte[]> codeTable = new ArrayList<>(258);
        for (int i = 0; i < 256; i++) {
            codeTable.add(new byte[]{(byte) (i & 255)});
        }
        codeTable.add(null);
        codeTable.add(null);
        return codeTable;
    }

    private static int calculateChunk(int tabSize, boolean earlyChange) {
        int i = tabSize + (earlyChange ? 1 : 0);
        if (i >= 2048) {
            return 12;
        }
        if (i >= 1024) {
            return 11;
        }
        if (i >= 512) {
            return 10;
        }
        return 9;
    }
}

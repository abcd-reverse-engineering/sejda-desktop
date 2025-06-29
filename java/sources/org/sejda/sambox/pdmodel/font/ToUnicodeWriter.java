package org.sejda.sambox.pdmodel.font;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.fontbox.util.Charsets;
import org.sejda.sambox.util.Hex;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/ToUnicodeWriter.class */
final class ToUnicodeWriter {
    private final Map<Integer, String> cidToUnicode = new TreeMap();
    private int wMode = 0;
    static final int MAX_ENTRIES_PER_OPERATOR = 100;

    ToUnicodeWriter() {
    }

    public void setWMode(int wMode) {
        this.wMode = wMode;
    }

    public void add(int cid, String text) {
        if (cid < 0 || cid > 65535) {
            throw new IllegalArgumentException("CID is not valid");
        }
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text is null or empty");
        }
        this.cidToUnicode.put(Integer.valueOf(cid), text);
    }

    public void writeTo(OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, Charsets.US_ASCII));
        writeLine(writer, "/CIDInit /ProcSet findresource begin");
        writeLine(writer, "12 dict begin\n");
        writeLine(writer, "begincmap");
        writeLine(writer, "/CIDSystemInfo");
        writeLine(writer, "<< /Registry (Adobe)");
        writeLine(writer, "/Ordering (UCS)");
        writeLine(writer, "/Supplement 0");
        writeLine(writer, ">> def\n");
        writeLine(writer, "/CMapName /Adobe-Identity-UCS def");
        writeLine(writer, "/CMapType 2 def\n");
        if (this.wMode != 0) {
            writeLine(writer, "/WMode /" + this.wMode + " def");
        }
        writeLine(writer, "1 begincodespacerange");
        writeLine(writer, "<0000> <FFFF>");
        writeLine(writer, "endcodespacerange\n");
        List<Integer> srcFrom = new ArrayList<>();
        List<Integer> srcTo = new ArrayList<>();
        List<String> dstString = new ArrayList<>();
        Map.Entry<Integer, String> prev = null;
        for (Map.Entry<Integer, String> next : this.cidToUnicode.entrySet()) {
            if (allowCIDToUnicodeRange(prev, next)) {
                srcTo.set(srcTo.size() - 1, next.getKey());
            } else {
                srcFrom.add(next.getKey());
                srcTo.add(next.getKey());
                dstString.add(next.getValue());
            }
            prev = next;
        }
        int batchCount = (int) Math.ceil(srcFrom.size() / 100.0d);
        int batch = 0;
        while (batch < batchCount) {
            int count = batch == batchCount - 1 ? srcFrom.size() - (MAX_ENTRIES_PER_OPERATOR * batch) : MAX_ENTRIES_PER_OPERATOR;
            writer.write(count + " beginbfrange\n");
            for (int j = 0; j < count; j++) {
                int index = (batch * MAX_ENTRIES_PER_OPERATOR) + j;
                writer.write(60);
                writer.write(Hex.getChars(srcFrom.get(index).shortValue()));
                writer.write("> ");
                writer.write(60);
                writer.write(Hex.getChars(srcTo.get(index).shortValue()));
                writer.write("> ");
                writer.write(60);
                writer.write(Hex.getCharsUTF16BE(dstString.get(index)));
                writer.write(">\n");
            }
            writeLine(writer, "endbfrange\n");
            batch++;
        }
        writeLine(writer, "endcmap");
        writeLine(writer, "CMapName currentdict /CMap defineresource pop");
        writeLine(writer, "end");
        writeLine(writer, "end");
        writer.flush();
    }

    private void writeLine(BufferedWriter writer, String text) throws IOException {
        writer.write(text);
        writer.write(10);
    }

    static boolean allowCIDToUnicodeRange(Map.Entry<Integer, String> prev, Map.Entry<Integer, String> next) {
        return prev != null && next != null && allowCodeRange(prev.getKey().intValue(), next.getKey().intValue()) && allowDestinationRange(prev.getValue(), next.getValue());
    }

    static boolean allowCodeRange(int prev, int next) {
        if (prev + 1 != next) {
            return false;
        }
        int prevH = (prev >> 8) & 255;
        int prevL = prev & 255;
        int nextH = (next >> 8) & 255;
        int nextL = next & 255;
        return prevH == nextH && prevL < nextL;
    }

    static boolean allowDestinationRange(String prev, String next) {
        if (prev.isEmpty() || next.isEmpty()) {
            return false;
        }
        int prevCode = prev.codePointAt(0);
        int nextCode = next.codePointAt(0);
        return allowCodeRange(prevCode, nextCode) && prev.codePointCount(0, prev.length()) == 1;
    }
}

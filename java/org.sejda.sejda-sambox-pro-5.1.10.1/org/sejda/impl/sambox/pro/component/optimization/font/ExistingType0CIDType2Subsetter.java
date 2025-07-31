package org.sejda.impl.sambox.pro.component.optimization.font;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.apache.fontbox.ttf.TTFTable;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.font.BaseTTFSubsetter;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/ExistingType0CIDType2Subsetter.class */
class ExistingType0CIDType2Subsetter extends BaseTTFSubsetter {
    public ExistingType0CIDType2Subsetter(COSDictionary existingFont, COSStream fontFileStream) throws IOException {
        super(existingFont, fontFileStream);
    }

    public COSStream doSubset(String subsetFontName) throws IOException {
        int numberOfGlyphs = Math.min(((Integer) getGlyphIds().last()).intValue(), getFont().getNumberOfGlyphs());
        setNumberOfGlyphs(numberOfGlyphs);
        COSStream subsetStream = new COSStream();
        subsetStream.addCompression();
        DataOutputStream out = new DataOutputStream(subsetStream.createUnfilteredStream());
        try {
            long[] newLoca = new long[numberOfGlyphs + 2];
            Map<String, byte[]> tables = new TreeMap<>();
            tables.put("head", buildHeadTable());
            tables.put("glyf", buildGlyfTable(newLoca));
            tables.put("hhea", buildHheaTable());
            tables.put("hmtx", buildHmtxTable());
            tables.put("loca", buildLocaTable(newLoca));
            tables.put("maxp", buildMaxpTable());
            tables.put("name", buildNameTable(subsetFontName));
            tables.put("post", buildPostTable());
            String[] keepTables = {"OS/2", "prep", "cvt ", "fpgm"};
            for (String table : keepTables) {
                TTFTable ttfTable = (TTFTable) getFont().getTableMap().get(table);
                if (Objects.nonNull(ttfTable)) {
                    tables.put(table, getFont().getTableBytes(ttfTable));
                }
            }
            updateChecksum(out, tables);
            for (byte[] bytes : tables.values()) {
                writeTableBody(out, bytes);
            }
            out.close();
            return subsetStream;
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

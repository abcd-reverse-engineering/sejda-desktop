package org.sejda.sambox.pdmodel.font;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.fontbox.ttf.GlyphTable;
import org.apache.fontbox.ttf.HeaderTable;
import org.apache.fontbox.ttf.HorizontalHeaderTable;
import org.apache.fontbox.ttf.HorizontalMetricsTable;
import org.apache.fontbox.ttf.MaximumProfileTable;
import org.apache.fontbox.ttf.NameRecord;
import org.apache.fontbox.ttf.NamingTable;
import org.apache.fontbox.ttf.OS2WindowsMetricsTable;
import org.apache.fontbox.ttf.PostScriptTable;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.sejda.commons.FastByteArrayOutputStream;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.SeekableSource;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/BaseTTFSubsetter.class */
public abstract class BaseTTFSubsetter implements Function<String, COSStream> {
    private static final Logger LOG = LoggerFactory.getLogger(BaseTTFSubsetter.class);
    private static final byte[] PAD_BUF = {0, 0, 0};
    private final SeekableSource fontSource;
    private final SortedSet<Integer> glyphIds;
    private final SortedMap<Integer, Integer> codeToGIDLookup;
    private final COSStream fontFileStream;
    private final COSDictionary existingFont;
    private TrueTypeFont font;
    private int numberOfGlyphs;

    public abstract COSStream doSubset(String str) throws IOException;

    public BaseTTFSubsetter(COSDictionary existingFont, COSStream fontFileStream, TrueTypeFont font) throws IOException {
        this.glyphIds = new TreeSet();
        this.codeToGIDLookup = new TreeMap();
        this.existingFont = (COSDictionary) Objects.requireNonNull(existingFont);
        this.fontFileStream = (COSStream) Objects.requireNonNull(fontFileStream);
        this.fontSource = fontFileStream.getUnfilteredSource();
        this.font = font;
        this.glyphIds.add(0);
    }

    public BaseTTFSubsetter(COSDictionary existingFont, COSStream fontFileStream) throws IOException {
        this(existingFont, fontFileStream, null);
    }

    public void putAll(Map<Integer, Integer> codesToGID) {
        this.codeToGIDLookup.putAll(codesToGID);
    }

    @Override // java.util.function.Function
    public COSStream apply(String subsetFontName) {
        String existingFontName = this.existingFont.getNameAsString(COSName.BASE_FONT);
        LOG.debug("Subsetting {}", existingFontName);
        try {
            TrueTypeFont font = getFont();
            try {
                if (FontUtils.isSubsettingPermitted(font)) {
                    RequireUtils.requireState(!this.codeToGIDLookup.isEmpty(), "Unable to subset, no glyph was specified");
                    this.glyphIds.addAll(this.codeToGIDLookup.values());
                    addCompositeGlyphs();
                    COSStream cOSStreamDoSubset = doSubset(subsetFontName);
                    if (font != null) {
                        font.close();
                    }
                    return cOSStreamDoSubset;
                }
                if (font != null) {
                    font.close();
                }
                return null;
            } finally {
            }
        } catch (Exception e) {
            LOG.error("Unable to subset font " + existingFontName, e);
            return null;
        }
    }

    public TrueTypeFont getFont() throws IOException {
        if (Objects.isNull(this.font)) {
            this.font = new TTFParser(true, true).parse(this.fontFileStream.getUnfilteredStream());
        }
        return this.font;
    }

    public SortedSet<Integer> getGlyphIds() {
        return Collections.unmodifiableSortedSet(this.glyphIds);
    }

    public void setNumberOfGlyphs(int numberOfGlyphs) {
        this.numberOfGlyphs = numberOfGlyphs;
    }

    protected void updateChecksum(DataOutputStream out, Map<String, byte[]> tables) throws IOException {
        long checksum = writeFileHeader(out, tables.size());
        long offset = 12 + (16 * tables.size());
        for (Map.Entry<String, byte[]> entry : tables.entrySet()) {
            checksum += writeTableHeader(out, entry.getKey(), offset, entry.getValue());
            offset += ((entry.getValue().length + 3) / 4) * 4;
        }
        byte[] head = tables.get("head");
        head[8] = (byte) (r0 >>> 24);
        head[9] = (byte) (r0 >>> 16);
        head[10] = (byte) (r0 >>> 8);
        head[11] = (byte) (2981146554L - (checksum & 4294967295L));
    }

    private void addCompositeGlyphs() throws IOException {
        int flags;
        GlyphTable glyphTable = getFont().getGlyph();
        long[] offsets = getFont().getIndexToLocation().getOffsets();
        Set<Integer> toProcess = new HashSet<>(this.glyphIds);
        while (!toProcess.isEmpty()) {
            Set<Integer> composite = new HashSet<>();
            for (Integer glyphId : toProcess) {
                if (glyphId.intValue() < offsets.length) {
                    long offset = offsets[glyphId.intValue()];
                    long length = offsets[glyphId.intValue() + 1] - offset;
                    if (length >= 0) {
                        this.fontSource.position(offset + glyphTable.getOffset());
                        ByteBuffer glyphData = ByteBuffer.allocate((int) length);
                        this.fontSource.read(glyphData);
                        glyphData.flip();
                        if (isComposite(glyphData)) {
                            int off = 10;
                            do {
                                flags = ((glyphData.get(off) & 255) << 8) | (glyphData.get(off + 1) & 255);
                                int off2 = off + 2;
                                int ogid = ((glyphData.get(off2) & 255) << 8) | (glyphData.get(off2 + 1) & 255);
                                composite.add(Integer.valueOf(ogid));
                                int off3 = off2 + 2;
                                if ((flags & 1) != 0) {
                                    off = off3 + 4;
                                } else {
                                    off = off3 + 2;
                                }
                                if ((flags & PDAnnotation.FLAG_LOCKED) != 0) {
                                    off += 8;
                                } else if ((flags & 64) != 0) {
                                    off += 4;
                                } else if ((flags & 8) != 0) {
                                    off += 2;
                                }
                            } while ((flags & 32) != 0);
                        }
                    }
                } else {
                    LOG.warn("Ignored composite glyph element {}", glyphId);
                }
            }
            this.glyphIds.addAll(composite);
            toProcess.clear();
            toProcess.addAll(composite);
        }
    }

    private boolean isComposite(ByteBuffer glyphData) {
        return glyphData.limit() >= 2 && glyphData.getShort() == -1;
    }

    protected byte[] buildHeadTable() throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            DataOutputStream out = new DataOutputStream(bos);
            try {
                HeaderTable h = getFont().getHeader();
                writeFixed(out, h.getVersion());
                writeFixed(out, h.getFontRevision());
                writeUint32(out, 0L);
                writeUint32(out, h.getMagicNumber());
                writeUint16(out, h.getFlags());
                writeUint16(out, h.getUnitsPerEm());
                writeLongDateTime(out, h.getCreated());
                writeLongDateTime(out, h.getModified());
                writeSInt16(out, h.getXMin());
                writeSInt16(out, h.getYMin());
                writeSInt16(out, h.getXMax());
                writeSInt16(out, h.getYMax());
                writeUint16(out, h.getMacStyle());
                writeUint16(out, h.getLowestRecPPEM());
                writeSInt16(out, h.getFontDirectionHint());
                writeSInt16(out, (short) 1);
                writeSInt16(out, h.getGlyphDataFormat());
                out.close();
                byte[] byteArray = bos.toByteArray();
                bos.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildHheaTable() throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            DataOutputStream out = new DataOutputStream(bos);
            try {
                HorizontalHeaderTable h = getFont().getHorizontalHeader();
                writeFixed(out, h.getVersion());
                writeSInt16(out, h.getAscender());
                writeSInt16(out, h.getDescender());
                writeSInt16(out, h.getLineGap());
                writeUint16(out, h.getAdvanceWidthMax());
                writeSInt16(out, h.getMinLeftSideBearing());
                writeSInt16(out, h.getMinRightSideBearing());
                writeSInt16(out, h.getXMaxExtent());
                writeSInt16(out, h.getCaretSlopeRise());
                writeSInt16(out, h.getCaretSlopeRun());
                writeSInt16(out, h.getReserved1());
                writeSInt16(out, h.getReserved2());
                writeSInt16(out, h.getReserved3());
                writeSInt16(out, h.getReserved4());
                writeSInt16(out, h.getReserved5());
                writeSInt16(out, h.getMetricDataFormat());
                writeUint16(out, Math.min(h.getNumberOfHMetrics(), this.numberOfGlyphs + 1));
                out.close();
                byte[] byteArray = bos.toByteArray();
                bos.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildMaxpTable() throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            DataOutputStream out = new DataOutputStream(bos);
            try {
                MaximumProfileTable p = getFont().getMaximumProfile();
                writeFixed(out, 1.0d);
                writeUint16(out, this.numberOfGlyphs + 1);
                writeUint16(out, p.getMaxPoints());
                writeUint16(out, p.getMaxContours());
                writeUint16(out, p.getMaxCompositePoints());
                writeUint16(out, p.getMaxCompositeContours());
                writeUint16(out, p.getMaxZones());
                writeUint16(out, p.getMaxTwilightPoints());
                writeUint16(out, p.getMaxStorage());
                writeUint16(out, p.getMaxFunctionDefs());
                writeUint16(out, p.getMaxInstructionDefs());
                writeUint16(out, p.getMaxStackElements());
                writeUint16(out, p.getMaxSizeOfInstructions());
                writeUint16(out, p.getMaxComponentElements());
                writeUint16(out, p.getMaxComponentDepth());
                out.close();
                byte[] byteArray = bos.toByteArray();
                bos.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildNameTable(String subsetFontName) throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            DataOutputStream out = new DataOutputStream(bos);
            try {
                NamingTable name = getFont().getNaming();
                if (Objects.nonNull(name)) {
                    Map<Integer, byte[]> records = (Map) name.getNameRecords().stream().filter(this::shouldCopyNameRecord).collect(Collectors.toMap((v0) -> {
                        return v0.getNameId();
                    }, record -> {
                        return record.getString().getBytes(StandardCharsets.UTF_16BE);
                    }));
                    records.put(0, "This is a subeset for internal PDF use".getBytes(StandardCharsets.UTF_16BE));
                    records.put(1, subsetFontName.getBytes(StandardCharsets.UTF_16BE));
                    records.remove(3);
                    records.put(4, subsetFontName.getBytes(StandardCharsets.UTF_16BE));
                    records.put(5, "Version 1.0".getBytes(StandardCharsets.UTF_16BE));
                    records.put(6, subsetFontName.getBytes(StandardCharsets.UTF_16BE));
                    writeUint16(out, 0);
                    writeUint16(out, records.size());
                    writeUint16(out, 6 + (12 * records.size()));
                    int offset = 0;
                    for (Map.Entry<Integer, byte[]> nameRecord : records.entrySet()) {
                        writeUint16(out, 3);
                        writeUint16(out, 1);
                        writeUint16(out, 1033);
                        writeUint16(out, nameRecord.getKey().intValue());
                        writeUint16(out, nameRecord.getValue().length);
                        writeUint16(out, offset);
                        offset += nameRecord.getValue().length;
                    }
                    for (byte[] bytes : records.values()) {
                        out.write(bytes);
                    }
                }
                out.close();
                byte[] byteArray = bos.toByteArray();
                bos.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    boolean shouldCopyNameRecord(NameRecord nr) {
        return nr.getPlatformId() == 3 && nr.getPlatformEncodingId() == 1 && nr.getLanguageId() == 1033 && nr.getNameId() >= 0 && nr.getNameId() < 7;
    }

    protected byte[] buildGlyfTable(long[] newOffsets) throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            GlyphTable glyphTable = getFont().getGlyph();
            long[] offsets = getFont().getIndexToLocation().getOffsets();
            long newOffset = 0;
            for (int glyphId = 0; glyphId <= this.numberOfGlyphs + 1; glyphId++) {
                newOffsets[glyphId] = newOffset;
                if (this.glyphIds.contains(Integer.valueOf(glyphId)) && glyphId < offsets.length) {
                    long offset = offsets[glyphId];
                    long length = offsets[glyphId + 1] - offset;
                    if (length >= 0) {
                        this.fontSource.position(offset + glyphTable.getOffset());
                        ByteBuffer glyphData = ByteBuffer.allocate((int) length);
                        this.fontSource.read(glyphData);
                        glyphData.flip();
                        bos.write(glyphData.array());
                        newOffset += glyphData.limit();
                    } else {
                        LOG.warn("Ignored glyph {}, cannot find a valid offset", Integer.valueOf(glyphId));
                    }
                }
            }
            byte[] byteArray = bos.toByteArray();
            bos.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildLocaTable(long[] newOffsets) throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            DataOutputStream out = new DataOutputStream(bos);
            try {
                for (long offset : newOffsets) {
                    writeUint32(out, offset);
                }
                out.close();
                byte[] byteArray = bos.toByteArray();
                bos.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildHmtxTable() throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            HorizontalHeaderTable h = getFont().getHorizontalHeader();
            HorizontalMetricsTable hm = getFont().getHorizontalMetrics();
            int lastgid = h.getNumberOfHMetrics() - 1;
            this.fontSource.position(hm.getOffset());
            for (int glyphId = 0; glyphId <= this.numberOfGlyphs; glyphId++) {
                if (glyphId <= lastgid) {
                    long offset = glyphId * 4;
                    copyBytes(bos, offset + hm.getOffset(), 4);
                } else {
                    long offset2 = (h.getNumberOfHMetrics() * 4) + ((glyphId - h.getNumberOfHMetrics()) * 2);
                    copyBytes(bos, offset2 + hm.getOffset(), 2);
                }
            }
            byte[] byteArray = bos.toByteArray();
            bos.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildPostTable() throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            PostScriptTable post = getFont().getPostScript();
            if (Objects.nonNull(post)) {
                DataOutputStream out = new DataOutputStream(bos);
                try {
                    writeFixed(out, 3.0d);
                    writeFixed(out, post.getItalicAngle());
                    writeSInt16(out, post.getUnderlinePosition());
                    writeSInt16(out, post.getUnderlineThickness());
                    writeUint32(out, post.getIsFixedPitch());
                    writeUint32(out, post.getMinMemType42());
                    writeUint32(out, post.getMaxMemType42());
                    writeUint32(out, post.getMinMemType1());
                    writeUint32(out, post.getMaxMemType1());
                    out.close();
                } finally {
                }
            }
            byte[] byteArray = bos.toByteArray();
            bos.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildOS2Table() throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            DataOutputStream out = new DataOutputStream(bos);
            try {
                OS2WindowsMetricsTable os2 = getFont().getOS2Windows();
                if (Objects.nonNull(os2)) {
                    writeUint16(out, os2.getVersion());
                    writeSInt16(out, os2.getAverageCharWidth());
                    writeUint16(out, os2.getWeightClass());
                    writeUint16(out, os2.getWidthClass());
                    writeSInt16(out, os2.getFsType());
                    writeSInt16(out, os2.getSubscriptXSize());
                    writeSInt16(out, os2.getSubscriptYSize());
                    writeSInt16(out, os2.getSubscriptXOffset());
                    writeSInt16(out, os2.getSubscriptYOffset());
                    writeSInt16(out, os2.getSuperscriptXSize());
                    writeSInt16(out, os2.getSuperscriptYSize());
                    writeSInt16(out, os2.getSuperscriptXOffset());
                    writeSInt16(out, os2.getSuperscriptYOffset());
                    writeSInt16(out, os2.getStrikeoutSize());
                    writeSInt16(out, os2.getStrikeoutPosition());
                    writeSInt16(out, (short) os2.getFamilyClass());
                    out.write(os2.getPanose());
                    writeUint32(out, 0L);
                    writeUint32(out, 0L);
                    writeUint32(out, 0L);
                    writeUint32(out, 0L);
                    out.write(os2.getAchVendId().getBytes(StandardCharsets.US_ASCII));
                    writeUint16(out, os2.getFsSelection());
                    writeUint16(out, this.codeToGIDLookup.firstKey().intValue());
                    writeUint16(out, this.codeToGIDLookup.lastKey().intValue());
                    writeUint16(out, os2.getTypoAscender());
                    writeUint16(out, os2.getTypoDescender());
                    writeUint16(out, os2.getTypoLineGap());
                    writeUint16(out, os2.getWinAscent());
                    writeUint16(out, os2.getWinDescent());
                }
                out.close();
                byte[] byteArray = bos.toByteArray();
                bos.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    protected byte[] buildCMapTable() throws IOException {
        FastByteArrayOutputStream bos = new FastByteArrayOutputStream();
        try {
            DataOutputStream out = new DataOutputStream(bos);
            try {
                writeUint16(out, 0);
                writeUint16(out, 1);
                writeUint16(out, 3);
                writeUint16(out, 1);
                writeUint32(out, 12L);
                Iterator<Map.Entry<Integer, Integer>> it = this.codeToGIDLookup.entrySet().iterator();
                Map.Entry<Integer, Integer> lastChar = it.next();
                Map.Entry<Integer, Integer> prevChar = lastChar;
                int lastGid = lastChar.getValue().intValue();
                int[] startCode = new int[this.codeToGIDLookup.size() + 1];
                int[] endCode = new int[startCode.length];
                int[] idDelta = new int[startCode.length];
                int segCount = 0;
                while (it.hasNext()) {
                    Map.Entry<Integer, Integer> curChar2Gid = it.next();
                    int curGid = curChar2Gid.getValue().intValue();
                    if (curChar2Gid.getKey().intValue() > 65535) {
                        throw new UnsupportedOperationException("non-BMP Unicode character");
                    }
                    if (curChar2Gid.getKey().intValue() != prevChar.getKey().intValue() + 1 || curGid - lastGid != curChar2Gid.getKey().intValue() - lastChar.getKey().intValue()) {
                        if (lastGid != 0) {
                            startCode[segCount] = lastChar.getKey().intValue();
                            endCode[segCount] = prevChar.getKey().intValue();
                            idDelta[segCount] = lastGid - lastChar.getKey().intValue();
                            segCount++;
                        } else if (!lastChar.getKey().equals(prevChar.getKey())) {
                            startCode[segCount] = lastChar.getKey().intValue() + 1;
                            endCode[segCount] = prevChar.getKey().intValue();
                            idDelta[segCount] = lastGid - lastChar.getKey().intValue();
                            segCount++;
                        }
                        lastGid = curGid;
                        lastChar = curChar2Gid;
                    }
                    prevChar = curChar2Gid;
                }
                startCode[segCount] = lastChar.getKey().intValue();
                endCode[segCount] = prevChar.getKey().intValue();
                idDelta[segCount] = lastGid - lastChar.getKey().intValue();
                int segCount2 = segCount + 1;
                startCode[segCount2] = 65535;
                endCode[segCount2] = 65535;
                idDelta[segCount2] = 1;
                int segCount3 = segCount2 + 1;
                int searchRange = 2 * ((int) Math.pow(2.0d, log2(segCount3)));
                writeUint16(out, 4);
                writeUint16(out, 16 + (segCount3 * 4 * 2));
                writeUint16(out, 0);
                writeUint16(out, segCount3 * 2);
                writeUint16(out, searchRange);
                writeUint16(out, log2(searchRange / 2));
                writeUint16(out, (2 * segCount3) - searchRange);
                for (int i = 0; i < segCount3; i++) {
                    writeUint16(out, endCode[i]);
                }
                writeUint16(out, 0);
                for (int i2 = 0; i2 < segCount3; i2++) {
                    writeUint16(out, startCode[i2]);
                }
                for (int i3 = 0; i3 < segCount3; i3++) {
                    writeUint16(out, idDelta[i3]);
                }
                for (int i4 = 0; i4 < segCount3; i4++) {
                    writeUint16(out, 0);
                }
                out.close();
                byte[] byteArray = bos.toByteArray();
                bos.close();
                return byteArray;
            } finally {
            }
        } catch (Throwable th) {
            try {
                bos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void copyBytes(OutputStream os, long offset, int count) throws IOException {
        ByteBuffer data = ByteBuffer.allocate(count);
        this.fontSource.position(offset);
        this.fontSource.read(data);
        data.flip();
        os.write(data.array());
    }

    private void writeFixed(DataOutputStream out, double f) throws IOException {
        double ip = Math.floor(f);
        double fp = (f - ip) * 65536.0d;
        out.writeShort((int) ip);
        out.writeShort((int) fp);
    }

    private void writeUint32(DataOutputStream out, long l) throws IOException {
        out.writeInt((int) l);
    }

    private void writeUint16(DataOutputStream out, int i) throws IOException {
        out.writeShort(i);
    }

    private void writeSInt16(DataOutputStream out, short i) throws IOException {
        out.writeShort(i);
    }

    private void writeLongDateTime(DataOutputStream out, Calendar calendar) throws IOException {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(1904, 0, 1, 0, 0, 0);
        cal.set(14, 0);
        long millisFor1904 = cal.getTimeInMillis();
        long secondsSince1904 = (calendar.getTimeInMillis() - millisFor1904) / 1000;
        out.writeLong(secondsSince1904);
    }

    private long writeFileHeader(DataOutputStream out, int nTables) throws IOException {
        out.writeInt(65536);
        out.writeShort(nTables);
        int mask = Integer.highestOneBit(nTables);
        int searchRange = mask * 16;
        out.writeShort(searchRange);
        int entrySelector = log2(mask);
        out.writeShort(entrySelector);
        int last = (16 * nTables) - searchRange;
        out.writeShort(last);
        return 65536 + toUInt32(nTables, searchRange) + toUInt32(entrySelector, last);
    }

    private long writeTableHeader(DataOutputStream out, String tag, long offset, byte[] bytes) throws IOException {
        long checksum = 0;
        int n = bytes.length;
        for (int nup = 0; nup < n; nup++) {
            checksum += (bytes[nup] & 255) << (24 - ((nup % 4) * 8));
        }
        long checksum2 = checksum & 4294967295L;
        byte[] tagbytes = tag.getBytes(StandardCharsets.US_ASCII);
        out.write(tagbytes, 0, 4);
        out.writeInt((int) checksum2);
        out.writeInt((int) offset);
        out.writeInt(bytes.length);
        return toUInt32(tagbytes) + checksum2 + checksum2 + offset + bytes.length;
    }

    public void writeTableBody(OutputStream os, byte[] bytes) throws IOException {
        int n = bytes.length;
        os.write(bytes);
        if (n % 4 != 0) {
            os.write(PAD_BUF, 0, 4 - (n % 4));
        }
    }

    private long toUInt32(int high, int low) {
        return ((high & 65535) << 16) | (low & 65535);
    }

    private long toUInt32(byte[] bytes) {
        return ((bytes[0] & 255) << 24) | ((bytes[1] & 255) << 16) | ((bytes[2] & 255) << 8) | (bytes[3] & 255);
    }

    private int log2(int num) {
        return (int) Math.floor(Math.log(num) / Math.log(2.0d));
    }
}

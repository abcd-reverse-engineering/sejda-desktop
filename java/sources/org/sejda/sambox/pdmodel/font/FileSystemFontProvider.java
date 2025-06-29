package org.sejda.sambox.pdmodel.font;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.cff.CFFCIDFont;
import org.apache.fontbox.ttf.NamingTable;
import org.apache.fontbox.ttf.OS2WindowsMetricsTable;
import org.apache.fontbox.ttf.OTFParser;
import org.apache.fontbox.ttf.OpenTypeFont;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TTFTable;
import org.apache.fontbox.ttf.TrueTypeCollection;
import org.apache.fontbox.ttf.TrueTypeFont;
import org.apache.fontbox.type1.Type1Font;
import org.apache.fontbox.util.autodetect.FontFileFinder;
import org.sejda.commons.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FileSystemFontProvider.class */
public class FileSystemFontProvider extends FontProvider {
    private static final String FONT_CACHE_SEPARATOR = "|";
    private final List<FSFontInfo> fontInfoList = new ArrayList();
    private static final FontCache FONT_CACHE = new FontCache();
    private static final Logger LOG = LoggerFactory.getLogger(FileSystemFontProvider.class);

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FileSystemFontProvider$FSFontInfo.class */
    private static class FSFontInfo extends FontInfo {
        private final String postScriptName;
        private final FontFormat format;
        private final CIDSystemInfo cidSystemInfo;
        private final int usWeightClass;
        private final int sFamilyClass;
        private final int ulCodePageRange1;
        private final int ulCodePageRange2;
        private final int macStyle;
        private final PDPanoseClassification panose;
        private final File file;

        private FSFontInfo(File file, FontFormat format, String postScriptName, CIDSystemInfo cidSystemInfo, int usWeightClass, int sFamilyClass, int ulCodePageRange1, int ulCodePageRange2, int macStyle, byte[] panose) {
            this.file = file;
            this.format = format;
            this.postScriptName = postScriptName;
            this.cidSystemInfo = cidSystemInfo;
            this.usWeightClass = usWeightClass;
            this.sFamilyClass = sFamilyClass;
            this.ulCodePageRange1 = ulCodePageRange1;
            this.ulCodePageRange2 = ulCodePageRange2;
            this.macStyle = macStyle;
            this.panose = (panose == null || panose.length < 10) ? null : new PDPanoseClassification(panose);
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public String getPostScriptName() {
            return this.postScriptName;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public FontFormat getFormat() {
            return this.format;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public CIDSystemInfo getCIDSystemInfo() {
            return this.cidSystemInfo;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public synchronized FontBoxFont getFont() throws IOException {
            Type1Font oTFFont;
            FontBoxFont cached = FileSystemFontProvider.FONT_CACHE.getFont(this);
            if (cached != null) {
                return cached;
            }
            FileSystemFontProvider.LOG.debug("Loading {} from {}", this.postScriptName, this.file);
            switch (this.format) {
                case PFB:
                    oTFFont = getType1Font(this.postScriptName, this.file);
                    break;
                case TTF:
                    oTFFont = getTrueTypeFont(this.postScriptName, this.file);
                    break;
                case OTF:
                    oTFFont = getOTFFont(this.postScriptName, this.file);
                    break;
                default:
                    throw new RuntimeException("can't happen");
            }
            if (oTFFont != null) {
                FileSystemFontProvider.FONT_CACHE.addFont(this, oTFFont);
            }
            return oTFFont;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public int getFamilyClass() {
            return this.sFamilyClass;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public int getWeightClass() {
            return this.usWeightClass;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public int getCodePageRange1() {
            return this.ulCodePageRange1;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public int getCodePageRange2() {
            return this.ulCodePageRange2;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public int getMacStyle() {
            return this.macStyle;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public PDPanoseClassification getPanose() {
            return this.panose;
        }

        @Override // org.sejda.sambox.pdmodel.font.FontInfo
        public String toString() {
            return super.toString() + " " + this.file;
        }

        private TrueTypeFont getTrueTypeFont(String postScriptName, File file) {
            try {
                TrueTypeFont ttf = readTrueTypeFont(postScriptName, file);
                FileSystemFontProvider.LOG.debug("Loaded {} from {}", postScriptName, file);
                return ttf;
            } catch (IOException | NullPointerException e) {
                FileSystemFontProvider.LOG.warn("Could not load font file: " + file, e);
                return null;
            }
        }

        private TrueTypeFont readTrueTypeFont(String postScriptName, File file) throws IOException {
            if (file.getName().toLowerCase().endsWith(".ttc")) {
                TrueTypeCollection ttc = new TrueTypeCollection(file);
                try {
                    TrueTypeFont ttf = ttc.getFontByName(postScriptName);
                    if (ttf == null) {
                        ttc.close();
                        throw new IOException("Font " + postScriptName + " not found in " + file);
                    }
                    return ttf;
                } catch (IOException ex) {
                    ttc.close();
                    throw ex;
                }
            }
            TTFParser ttfParser = new TTFParser(false, true);
            return ttfParser.parse(file);
        }

        private static OpenTypeFont getOTFFont(String postScriptName, File file) throws IOException {
            try {
                if (file.getName().toLowerCase().endsWith(".ttc")) {
                    TrueTypeCollection ttc = new TrueTypeCollection(file);
                    try {
                        OpenTypeFont fontByName = ttc.getFontByName(postScriptName);
                        if (fontByName == null) {
                            ttc.close();
                            throw new IOException("Font " + postScriptName + " not found in " + file);
                        }
                        return fontByName;
                    } catch (IOException ex) {
                        FileSystemFontProvider.LOG.error(ex.getMessage(), ex);
                        ttc.close();
                        return null;
                    }
                }
                OTFParser parser = new OTFParser(false, true);
                OpenTypeFont otf = parser.parse(file);
                FileSystemFontProvider.LOG.debug("Loaded {} from {}", postScriptName, file);
                return otf;
            } catch (IOException e) {
                FileSystemFontProvider.LOG.warn("Could not load font file: " + file, e);
                return null;
            }
        }

        private static Type1Font getType1Font(String postScriptName, File file) throws IOException {
            try {
                InputStream input = new FileInputStream(file);
                try {
                    Type1Font type1 = Type1Font.createWithPFB(input);
                    FileSystemFontProvider.LOG.debug("Loaded {} from {}", postScriptName, file);
                    input.close();
                    return type1;
                } finally {
                }
            } catch (IOException e) {
                FileSystemFontProvider.LOG.warn("Could not load font file " + file, e);
                return null;
            }
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/font/FileSystemFontProvider$FSIgnored.class */
    private static final class FSIgnored extends FSFontInfo {
        private FSIgnored(File file, FontFormat format, String postScriptName) {
            super(file, format, postScriptName, null, 0, 0, 0, 0, 0, null);
        }
    }

    protected List<File> findFontFiles() {
        return (List) new FontFileFinder().find().stream().map(File::new).collect(Collectors.toList());
    }

    private void initialize() throws IOException {
        try {
            LOG.trace("Will search the local system for fonts");
            List<File> files = new ArrayList<>();
            List<File> fonts = findFontFiles();
            files.addAll(fonts);
            LOG.trace("Found {} fonts on the local system", Integer.valueOf(files.size()));
            if (!files.isEmpty()) {
                List<FSFontInfo> cachedInfos = loadDiskCache(files);
                if (cachedInfos != null && !cachedInfos.isEmpty()) {
                    this.fontInfoList.addAll(cachedInfos);
                } else {
                    LOG.warn("Building on-disk font cache, this may take a while");
                    scanFonts(files);
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(this::saveDiskCache);
                    executor.shutdown();
                    LOG.info("Finished building on-disk font cache, found {} fonts", Integer.valueOf(this.fontInfoList.size()));
                }
            }
        } catch (AccessControlException e) {
            LOG.error("Error accessing the file system", e);
        }
    }

    private void scanFonts(List<File> files) {
        for (File file : files) {
            try {
                String filePath = file.getPath().toLowerCase();
                if (filePath.endsWith(".ttf") || filePath.endsWith(".otf")) {
                    addTrueTypeFont(file);
                } else if (filePath.toLowerCase().endsWith(".ttc") || filePath.toLowerCase().endsWith(".otc")) {
                    addTrueTypeCollection(file);
                } else if (filePath.toLowerCase().endsWith(".pfb")) {
                    addType1Font(file);
                }
            } catch (Exception e) {
                LOG.warn("Unable to load font file: " + file, e);
            }
        }
    }

    protected File getDiskCacheFile() {
        String path = System.getProperty("org.sambox.fontcache");
        if (isBadPath(path)) {
            path = System.getProperty("user.home");
            if (isBadPath(path)) {
                path = System.getProperty("java.io.tmpdir");
            }
        }
        return new File(path, ".sambox.cache");
    }

    private static boolean isBadPath(String path) {
        return (path != null && new File(path).isDirectory() && new File(path).canWrite()) ? false : true;
    }

    private void saveDiskCache() throws IOException {
        try {
            File file = getDiskCacheFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            try {
                for (FSFontInfo fontInfo : this.fontInfoList) {
                    writer.write(fontInfo.postScriptName.trim().replace(FONT_CACHE_SEPARATOR, "\\|"));
                    writer.write(FONT_CACHE_SEPARATOR);
                    writer.write(fontInfo.format.toString());
                    writer.write(FONT_CACHE_SEPARATOR);
                    if (fontInfo.cidSystemInfo != null) {
                        writer.write(fontInfo.cidSystemInfo.getRegistry() + "-" + fontInfo.cidSystemInfo.getOrdering() + "-" + fontInfo.cidSystemInfo.getSupplement());
                    }
                    writer.write(FONT_CACHE_SEPARATOR);
                    if (fontInfo.usWeightClass > -1) {
                        writer.write(Integer.toHexString(fontInfo.usWeightClass));
                    }
                    writer.write(FONT_CACHE_SEPARATOR);
                    if (fontInfo.sFamilyClass > -1) {
                        writer.write(Integer.toHexString(fontInfo.sFamilyClass));
                    }
                    writer.write(FONT_CACHE_SEPARATOR);
                    writer.write(Integer.toHexString(fontInfo.ulCodePageRange1));
                    writer.write(FONT_CACHE_SEPARATOR);
                    writer.write(Integer.toHexString(fontInfo.ulCodePageRange2));
                    writer.write(FONT_CACHE_SEPARATOR);
                    if (fontInfo.macStyle > -1) {
                        writer.write(Integer.toHexString(fontInfo.macStyle));
                    }
                    writer.write(FONT_CACHE_SEPARATOR);
                    if (fontInfo.panose != null) {
                        byte[] bytes = fontInfo.panose.getBytes();
                        for (int i = 0; i < 10; i++) {
                            String str = Integer.toHexString(bytes[i]);
                            if (str.length() == 1) {
                                writer.write(48);
                            }
                            writer.write(str);
                        }
                    }
                    writer.write(FONT_CACHE_SEPARATOR);
                    writer.write(fontInfo.file.getAbsolutePath());
                    writer.newLine();
                }
                writer.close();
            } finally {
            }
        } catch (IOException | SecurityException e) {
            LOG.error("Could not write to font cache", e);
        }
    }

    private List<FSFontInfo> loadDiskCache(List<File> files) throws IOException {
        Set<String> pending = new HashSet<>();
        Iterator<File> it = files.iterator();
        while (it.hasNext()) {
            pending.add(it.next().getAbsolutePath());
        }
        List<FSFontInfo> results = new ArrayList<>();
        File file = getDiskCacheFile();
        boolean fileExists = false;
        try {
            fileExists = file.exists();
        } catch (SecurityException e) {
        }
        if (fileExists) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        String[] parts = line.split("(?<!\\\\)\\|", 10);
                        if (parts.length < 10) {
                            LOG.warn("Incorrect line '" + line + "' in font disk cache is skipped");
                        } else {
                            CIDSystemInfo cidSystemInfo = null;
                            int usWeightClass = -1;
                            int sFamilyClass = -1;
                            int macStyle = -1;
                            byte[] panose = null;
                            String postScriptName = parts[0].replace("\\|", FONT_CACHE_SEPARATOR);
                            FontFormat format = FontFormat.valueOf(parts[1]);
                            if (parts[2].length() > 0) {
                                String[] ros = parts[2].split("-");
                                cidSystemInfo = new CIDSystemInfo(ros[0], ros[1], Integer.parseInt(ros[2]));
                            }
                            if (parts[3].length() > 0) {
                                usWeightClass = (int) Long.parseLong(parts[3], 16);
                            }
                            if (parts[4].length() > 0) {
                                sFamilyClass = (int) Long.parseLong(parts[4], 16);
                            }
                            int ulCodePageRange1 = (int) Long.parseLong(parts[5], 16);
                            int ulCodePageRange2 = (int) Long.parseLong(parts[6], 16);
                            if (parts[7].length() > 0) {
                                macStyle = (int) Long.parseLong(parts[7], 16);
                            }
                            if (parts[8].length() > 0) {
                                panose = new byte[10];
                                for (int i = 0; i < 10; i++) {
                                    String str = parts[8].substring(i * 2, (i * 2) + 2);
                                    int b = Integer.parseInt(str, 16);
                                    panose[i] = (byte) (b & 255);
                                }
                            }
                            File fontFile = new File(parts[9]);
                            if (fontFile.exists()) {
                                FSFontInfo info = new FSFontInfo(fontFile, format, postScriptName, cidSystemInfo, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose);
                                results.add(info);
                            } else {
                                LOG.debug("Font file {} not found, skipped", fontFile.getAbsolutePath());
                            }
                            pending.remove(fontFile.getAbsolutePath());
                        }
                    } finally {
                    }
                }
                reader.close();
            } catch (IOException e2) {
                LOG.warn("Error loading font cache, will be re-built", e2);
                return null;
            }
        }
        if (!pending.isEmpty()) {
            LOG.warn("New fonts found, font cache will be re-built");
            return null;
        }
        return results;
    }

    private void addTrueTypeCollection(File ttcFile) throws IOException {
        TrueTypeCollection ttc = new TrueTypeCollection(ttcFile);
        try {
            ttc.processAllFonts(ttf -> {
                addTrueTypeFontImpl(ttf, ttcFile);
            });
            ttc.close();
        } catch (Throwable th) {
            try {
                ttc.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void addTrueTypeFont(File ttfFile) throws IOException {
        if (ttfFile.getPath().toLowerCase().endsWith(".otf")) {
            OTFParser parser = new OTFParser(false, true);
            OpenTypeFont otf = parser.parse(ttfFile);
            addTrueTypeFontImpl(otf, ttfFile);
        } else {
            TTFParser parser2 = new TTFParser(false, true);
            TrueTypeFont ttf = parser2.parse(ttfFile);
            addTrueTypeFontImpl(ttf, ttfFile);
        }
    }

    private void addTrueTypeFontImpl(TrueTypeFont ttf, File file) throws IOException {
        String format;
        NamingTable name;
        try {
            try {
                if (ttf.getName() == null) {
                    this.fontInfoList.add(new FSIgnored(file, FontFormat.TTF, "*skipnoname*"));
                    LOG.warn("Missing 'name' entry for PostScript name in font " + file);
                } else {
                    if (ttf.getHeader() == null) {
                        this.fontInfoList.add(new FSIgnored(file, FontFormat.TTF, ttf.getName()));
                        IOUtils.close(ttf);
                        return;
                    }
                    int macStyle = ttf.getHeader().getMacStyle();
                    int sFamilyClass = -1;
                    int usWeightClass = -1;
                    int ulCodePageRange1 = 0;
                    int ulCodePageRange2 = 0;
                    byte[] panose = null;
                    OS2WindowsMetricsTable os2WindowsMetricsTable = ttf.getOS2Windows();
                    if (Objects.nonNull(os2WindowsMetricsTable)) {
                        sFamilyClass = os2WindowsMetricsTable.getFamilyClass();
                        usWeightClass = os2WindowsMetricsTable.getWeightClass();
                        ulCodePageRange1 = (int) os2WindowsMetricsTable.getCodePageRange1();
                        ulCodePageRange2 = (int) os2WindowsMetricsTable.getCodePageRange2();
                        panose = os2WindowsMetricsTable.getPanose();
                    }
                    if ((ttf instanceof OpenTypeFont) && ((OpenTypeFont) ttf).isPostScript()) {
                        format = "OTF";
                        CFFCIDFont font = ((OpenTypeFont) ttf).getCFF().getFont();
                        CIDSystemInfo ros = null;
                        if (font instanceof CFFCIDFont) {
                            CFFCIDFont cidFont = font;
                            String registry = cidFont.getRegistry();
                            String ordering = cidFont.getOrdering();
                            int supplement = cidFont.getSupplement();
                            ros = new CIDSystemInfo(registry, ordering, supplement);
                        }
                        this.fontInfoList.add(new FSFontInfo(file, FontFormat.OTF, ttf.getName(), ros, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose));
                    } else {
                        CIDSystemInfo ros2 = null;
                        if (ttf.getTableMap().containsKey("gcid")) {
                            byte[] bytes = ttf.getTableBytes((TTFTable) ttf.getTableMap().get("gcid"));
                            String reg = new String(bytes, 10, 64, StandardCharsets.US_ASCII);
                            String registryName = reg.substring(0, reg.indexOf(0));
                            String ord = new String(bytes, 76, 64, StandardCharsets.US_ASCII);
                            String orderName = ord.substring(0, ord.indexOf(0));
                            int supplementVersion = (bytes[140] << 8) & bytes[141] & 255;
                            ros2 = new CIDSystemInfo(registryName, orderName, supplementVersion);
                        }
                        format = "TTF";
                        this.fontInfoList.add(new FSFontInfo(file, FontFormat.TTF, ttf.getName(), ros2, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose));
                    }
                    if (LOG.isTraceEnabled() && (name = ttf.getNaming()) != null) {
                        LOG.trace(format + ": '" + name.getPostScriptName() + "' / '" + name.getFontFamily() + "' / '" + name.getFontSubFamily() + "'");
                    }
                }
                IOUtils.close(ttf);
            } catch (IOException e) {
                this.fontInfoList.add(new FSIgnored(file, FontFormat.TTF, "*skipexception*"));
                LOG.warn("Could not load font file: " + file, e);
                IOUtils.close(ttf);
            }
        } catch (Throwable th) {
            IOUtils.close(ttf);
            throw th;
        }
    }

    private void addType1Font(File pfbFile) throws IOException {
        InputStream input = new FileInputStream(pfbFile);
        try {
            Type1Font type1 = Type1Font.createWithPFB(input);
            this.fontInfoList.add(new FSFontInfo(pfbFile, FontFormat.PFB, type1.getName(), null, -1, -1, 0, 0, -1, null));
            if (LOG.isTraceEnabled()) {
                LOG.trace("PFB: '" + type1.getName() + "' / '" + type1.getFamilyName() + "' / '" + type1.getWeight() + "'");
            }
            input.close();
        } catch (Throwable th) {
            try {
                input.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // org.sejda.sambox.pdmodel.font.FontProvider
    public List<? extends FontInfo> getFontInfo() throws IOException {
        initialize();
        return this.fontInfoList;
    }
}

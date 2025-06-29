package org.sejda.sambox.util.filetypedetector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import org.sejda.io.SeekableSource;
import org.sejda.io.SeekableSources;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/util/filetypedetector/FileTypeDetector.class */
public final class FileTypeDetector {
    private static final ByteTrie<FileType> ROOT = new ByteTrie<>();
    private static final HashMap<String, FileType> FTYP_MAP;

    /* JADX WARN: Type inference failed for: r2v1, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v11, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v13, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v15, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v17, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v19, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v21, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v23, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v25, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v27, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v29, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v3, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v31, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v33, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v35, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v37, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v39, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v41, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v5, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v7, types: [byte[], byte[][]] */
    /* JADX WARN: Type inference failed for: r2v9, types: [byte[], byte[][]] */
    static {
        ROOT.setDefaultValue(FileType.UNKNOWN);
        ROOT.addPath(FileType.JPEG, new byte[]{new byte[]{-1, -40}});
        ROOT.addPath(FileType.TIFF, new byte[]{"II".getBytes(StandardCharsets.ISO_8859_1), new byte[]{42, 0}});
        ROOT.addPath(FileType.TIFF, new byte[]{"MM".getBytes(StandardCharsets.ISO_8859_1), new byte[]{0, 42}});
        ROOT.addPath(FileType.PSD, new byte[]{"8BPS".getBytes(StandardCharsets.ISO_8859_1)});
        ROOT.addPath(FileType.PNG, new byte[]{new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82}});
        ROOT.addPath(FileType.BMP, new byte[]{"BM".getBytes(StandardCharsets.ISO_8859_1)});
        ROOT.addPath(FileType.GIF, new byte[]{"GIF87a".getBytes(StandardCharsets.ISO_8859_1)});
        ROOT.addPath(FileType.GIF, new byte[]{"GIF89a".getBytes(StandardCharsets.ISO_8859_1)});
        ROOT.addPath(FileType.ICO, new byte[]{new byte[]{0, 0, 1, 0}});
        ROOT.addPath(FileType.PCX, new byte[]{new byte[]{10, 0, 1}});
        ROOT.addPath(FileType.PCX, new byte[]{new byte[]{10, 2, 1}});
        ROOT.addPath(FileType.PCX, new byte[]{new byte[]{10, 3, 1}});
        ROOT.addPath(FileType.PCX, new byte[]{new byte[]{10, 5, 1}});
        ROOT.addPath(FileType.RIFF, new byte[]{"RIFF".getBytes(StandardCharsets.ISO_8859_1)});
        ROOT.addPath(FileType.CRW, new byte[]{"II".getBytes(StandardCharsets.ISO_8859_1), new byte[]{26, 0, 0, 0}, "HEAPCCDR".getBytes(StandardCharsets.ISO_8859_1)});
        ROOT.addPath(FileType.CR2, new byte[]{"II".getBytes(StandardCharsets.ISO_8859_1), new byte[]{42, 0, 16, 0, 0, 0, 67, 82}});
        ROOT.addPath(FileType.NEF, new byte[]{"MM".getBytes(StandardCharsets.ISO_8859_1), new byte[]{0, 42, 0, 0, 0, Byte.MIN_VALUE, 0}});
        ROOT.addPath(FileType.ORF, new byte[]{"IIRO".getBytes(StandardCharsets.ISO_8859_1), new byte[]{8, 0}});
        ROOT.addPath(FileType.ORF, new byte[]{"IIRS".getBytes(StandardCharsets.ISO_8859_1), new byte[]{8, 0}});
        ROOT.addPath(FileType.RAF, new byte[]{"FUJIFILMCCD-RAW".getBytes(StandardCharsets.ISO_8859_1)});
        ROOT.addPath(FileType.RW2, new byte[]{"II".getBytes(StandardCharsets.ISO_8859_1), new byte[]{85, 0}});
        FTYP_MAP = new HashMap<>();
        FTYP_MAP.put("ftypmif1", FileType.HEIF);
        FTYP_MAP.put("ftypmsf1", FileType.HEIF);
        FTYP_MAP.put("ftypheic", FileType.HEIF);
        FTYP_MAP.put("ftypheix", FileType.HEIF);
        FTYP_MAP.put("ftyphevc", FileType.HEIF);
        FTYP_MAP.put("ftyphevx", FileType.HEIF);
    }

    private FileTypeDetector() {
    }

    public static FileType detectFileType(File file) throws IOException {
        return detectFileType(SeekableSources.seekableSourceFrom(file));
    }

    public static FileType detectFileType(SeekableSource source) throws IOException {
        byte[] firstBytes = new byte[ROOT.getMaxDepth()];
        InputStream fin = source.asNewInputStream();
        try {
            fin.read(firstBytes);
            if (fin != null) {
                fin.close();
            }
            FileType fileType = ROOT.find(firstBytes);
            if (fileType == FileType.UNKNOWN) {
                String eightCC = new String(firstBytes, 4, 8);
                FileType t = FTYP_MAP.get(eightCC);
                if (t != null) {
                    return t;
                }
            } else if (fileType == FileType.RIFF) {
                String fourCC = new String(firstBytes, 8, 4);
                if (fourCC.equals("WEBP")) {
                    return FileType.WEBP;
                }
            }
            return fileType;
        } catch (Throwable th) {
            if (fin != null) {
                try {
                    fin.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}

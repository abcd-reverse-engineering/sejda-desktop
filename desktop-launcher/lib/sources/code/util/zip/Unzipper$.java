package code.util.zip;

import code.util.sort.AlphanumComparator$;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.runtime.BoxesRunTime;

/* compiled from: Unzipper.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/zip/Unzipper$.class */
public final class Unzipper$ {
    public static final Unzipper$ MODULE$ = new Unzipper$();

    private Unzipper$() {
    }

    public Seq<String> list(final InputStream in) throws IOException {
        ZipInputStream zis = new ZipInputStream(in);
        List entries = Nil$.MODULE$;
        for (ZipEntry entry = zis.getNextEntry(); entry != null; entry = zis.getNextEntry()) {
            entries = entries.$colon$colon(entry.getName());
        }
        return (Seq) entries.sortWith((s1, s2) -> {
            return BoxesRunTime.boxToBoolean($anonfun$list$1(s1, s2));
        });
    }

    public static final /* synthetic */ boolean $anonfun$list$1(final String s1, final String s2) {
        return AlphanumComparator$.MODULE$.filenameSorting(s1, s2);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0053 A[Catch: all -> 0x006c, TryCatch #0 {all -> 0x006c, blocks: (B:12:0x0044, B:15:0x0053), top: B:25:0x0044 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void unzip(final java.io.InputStream r7, final java.lang.String r8, final java.io.File r9) throws java.io.IOException {
        /*
            r6 = this;
            java.util.zip.ZipInputStream r0 = new java.util.zip.ZipInputStream
            r1 = r0
            r2 = r7
            r1.<init>(r2)
            r10 = r0
            java.io.FileOutputStream r0 = new java.io.FileOutputStream
            r1 = r0
            r2 = r9
            r1.<init>(r2)
            r11 = r0
            r0 = 2048(0x800, float:2.87E-42)
            byte[] r0 = new byte[r0]
            r12 = r0
            r0 = r10
            java.util.zip.ZipEntry r0 = r0.getNextEntry()
            r13 = r0
        L22:
            r0 = r13
            if (r0 == 0) goto L86
            r0 = r13
            java.lang.String r0 = r0.getName()
            r1 = r8
            r14 = r1
            r1 = r0
            if (r1 != 0) goto L3c
        L34:
            r0 = r14
            if (r0 == 0) goto L44
            goto L7c
        L3c:
            r1 = r14
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L7c
        L44:
            r0 = r10
            r1 = r12
            int r0 = r0.read(r1)     // Catch: java.lang.Throwable -> L6c
            r15 = r0
        L4d:
            r0 = r15
            r1 = 0
            if (r0 <= r1) goto L69
            r0 = r11
            r1 = r12
            r2 = 0
            r3 = r15
            r0.write(r1, r2, r3)     // Catch: java.lang.Throwable -> L6c
            r0 = r10
            r1 = r12
            int r0 = r0.read(r1)     // Catch: java.lang.Throwable -> L6c
            r15 = r0
            goto L4d
        L69:
            goto L76
        L6c:
            r16 = move-exception
            r0 = r11
            org.apache.commons.io.IOUtils.closeQuietly(r0)
            r0 = r16
            throw r0
        L76:
            r0 = r11
            org.apache.commons.io.IOUtils.closeQuietly(r0)
            return
        L7c:
            r0 = r10
            java.util.zip.ZipEntry r0 = r0.getNextEntry()
            r13 = r0
            goto L22
        L86:
            java.io.FileNotFoundException r0 = new java.io.FileNotFoundException
            r1 = r0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = r2
            r4 = 21
            r3.<init>(r4)
            java.lang.String r3 = "Zip entry not found: "
            java.lang.StringBuilder r2 = r2.append(r3)
            r3 = r8
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.util.zip.Unzipper$.unzip(java.io.InputStream, java.lang.String, java.io.File):void");
    }
}

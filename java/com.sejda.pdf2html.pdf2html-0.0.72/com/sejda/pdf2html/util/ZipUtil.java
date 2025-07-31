package com.sejda.pdf2html.util;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

/* compiled from: ZipUtil.scala */
@ScalaSignature(bytes = "\u0006\u0005%3q\u0001B\u0003\u0011\u0002\u0007\u0005a\u0002C\u0003\u0016\u0001\u0011\u0005a\u0003C\u0003\u001b\u0001\u0011\u00051\u0004C\u0004>\u0001E\u0005I\u0011\u0001 \u0003\u000fiK\u0007/\u0016;jY*\u0011aaB\u0001\u0005kRLGN\u0003\u0002\t\u0013\u0005A\u0001\u000f\u001a43QRlGN\u0003\u0002\u000b\u0017\u0005)1/\u001a6eC*\tA\"A\u0002d_6\u001c\u0001a\u0005\u0002\u0001\u001fA\u0011\u0001cE\u0007\u0002#)\t!#A\u0003tG\u0006d\u0017-\u0003\u0002\u0015#\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#A\f\u0011\u0005AA\u0012BA\r\u0012\u0005\u0011)f.\u001b;\u0002\u000bUt'0\u001b9\u0015\t]abE\f\u0005\u0006;\t\u0001\rAH\u0001\u0004gJ\u001c\u0007CA\u0010%\u001b\u0005\u0001#BA\u0011#\u0003\rqW\r\u001e\u0006\u0002G\u0005!!.\u0019<b\u0013\t)\u0003EA\u0002V%2CQa\n\u0002A\u0002!\nA\u0001Z3tiB\u0011\u0011\u0006L\u0007\u0002U)\u00111FI\u0001\u0003S>L!!\f\u0016\u0003\t\u0019KG.\u001a\u0005\b_\t\u0001\n\u00111\u00011\u0003\u00191\u0017\u000e\u001c;feB!\u0001#M\u001a;\u0013\t\u0011\u0014CA\u0005Gk:\u001cG/[8ocA\u0011A\u0007O\u0007\u0002k)\u0011agN\u0001\u0004u&\u0004(B\u0001\u0004#\u0013\tITG\u0001\u0005[SB,e\u000e\u001e:z!\t\u00012(\u0003\u0002=#\t9!i\\8mK\u0006t\u0017aD;ou&\u0004H\u0005Z3gCVdG\u000fJ\u001a\u0016\u0003}R#\u0001\r!,\u0003\u0005\u0003\"AQ$\u000e\u0003\rS!\u0001R#\u0002\u0013Ut7\r[3dW\u0016$'B\u0001$\u0012\u0003)\tgN\\8uCRLwN\\\u0005\u0003\u0011\u000e\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u0001")
/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/util/ZipUtil.class */
public interface ZipUtil {
    static void $init$(final ZipUtil $this) {
    }

    default Function1<ZipEntry, Object> unzip$default$3() {
        return x$1 -> {
            return BoxesRunTime.boxToBoolean($anonfun$unzip$default$3$1(x$1));
        };
    }

    static /* synthetic */ boolean $anonfun$unzip$default$3$1(final ZipEntry x$1) {
        return true;
    }

    private static void _extractEntry$1(final ZipEntry ze, final ZipInputStream zip, final File dest$1) throws IOException {
        if (ze.getName().startsWith("profiles")) {
            File file = new File(dest$1, ze.getName());
            Files.createParentDirs(file);
            FileOutputStream out = new FileOutputStream(file);
            try {
                ByteStreams.copy(zip, out);
            } finally {
                out.close();
            }
        }
    }

    private default void _extract$1(final ZipInputStream zip, final File dest$1) throws IOException {
        while (true) {
            ZipEntry ze = zip.getNextEntry();
            if (ze == null) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            } else {
                if (!ze.isDirectory()) {
                    _extractEntry$1(ze, zip, dest$1);
                }
                zip = zip;
            }
        }
    }

    default void unzip(final URL src, final File dest, final Function1<ZipEntry, Object> filter) throws IOException {
        ZipInputStream zip = new ZipInputStream(src.openStream());
        try {
            _extract$1(zip, dest);
        } finally {
            zip.close();
        }
    }
}

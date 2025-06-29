package code.util.encryption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.crypto.Cipher;
import org.sejda.model.encryption.CipherBasedEncryptionAtRest;
import org.sejda.model.encryption.CipherSupplier;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

/* compiled from: FileEncryption.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005Ug\u0001B\u0010!\u0001\u001eB\u0001\"\u0011\u0001\u0003\u0016\u0004%\tA\u0011\u0005\t\u0017\u0002\u0011\t\u0012)A\u0005\u0007\")A\n\u0001C\u0001\u001b\"A\u0001\u000b\u0001EC\u0002\u0013\u0005\u0011\u000bC\u0003i\u0001\u0011\u0005\u0011\u000eC\u0003s\u0001\u0011\u0005\u0011\u000eC\u0003t\u0001\u0011\u0005C\u000fC\u0003y\u0001\u0011\u0005\u0013\u0010C\u0004\u0002\f\u0001!\t%!\u0004\t\u000f\u0005e\u0001\u0001\"\u0011\u0002\u001c!9\u0011\u0011\u0004\u0001\u0005B\u0005%\u0002bBA\u0018\u0001\u0011\u0005\u0013\u0011\u0007\u0005\n\u0003o\u0001\u0011\u0011!C\u0001\u0003sA\u0011\"!\u0010\u0001#\u0003%\t!a\u0010\t\u0013\u0005U\u0003!!A\u0005B\u0005]\u0003\"CA/\u0001\u0005\u0005I\u0011AA0\u0011%\t9\u0007AA\u0001\n\u0003\tI\u0007C\u0005\u0002v\u0001\t\t\u0011\"\u0011\u0002x!I\u0011Q\u0011\u0001\u0002\u0002\u0013\u0005\u0011q\u0011\u0005\n\u0003#\u0003\u0011\u0011!C!\u0003'C\u0011\"a&\u0001\u0003\u0003%\t%!'\t\u0013\u0005m\u0005!!A\u0005B\u0005u\u0005\"CAP\u0001\u0005\u0005I\u0011IAQ\u000f%\t)\u000bIA\u0001\u0012\u0003\t9K\u0002\u0005 A\u0005\u0005\t\u0012AAU\u0011\u0019a\u0015\u0004\"\u0001\u0002<\"I\u00111T\r\u0002\u0002\u0013\u0015\u0013Q\u0014\u0005\n\u0003{K\u0012\u0011!CA\u0003\u007fC\u0011\"a1\u001a\u0003\u0003%\t)!2\t\u0013\u0005E\u0017$!A\u0005\n\u0005M'!D&fs\u0016s7M]=qi&|gN\u0003\u0002\"E\u0005QQM\\2ssB$\u0018n\u001c8\u000b\u0005\r\"\u0013\u0001B;uS2T\u0011!J\u0001\u0005G>$Wm\u0001\u0001\u0014\u000b\u0001AcFM\u001b\u0011\u0005%bS\"\u0001\u0016\u000b\u0003-\nQa]2bY\u0006L!!\f\u0016\u0003\r\u0005s\u0017PU3g!\ty\u0003'D\u0001!\u0013\t\t\u0004E\u0001\bGS2,WI\\2ssB$\u0018n\u001c8\u0011\u0005%\u001a\u0014B\u0001\u001b+\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\u000e \u000f\u0005]bdB\u0001\u001d<\u001b\u0005I$B\u0001\u001e'\u0003\u0019a$o\\8u}%\t1&\u0003\u0002>U\u00059\u0001/Y2lC\u001e,\u0017BA A\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\ti$&A\u0002lKf,\u0012a\u0011\t\u0003\t\"s!!\u0012$\u0011\u0005aR\u0013BA$+\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011J\u0013\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u001dS\u0013\u0001B6fs\u0002\na\u0001P5oSRtDC\u0001(P!\ty\u0003\u0001C\u0003B\u0007\u0001\u00071)\u0001\bdSBDWM]*vaBd\u0017.\u001a:\u0016\u0003I\u00132aU+^\r\u0011!F\u0001\u0001*\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0005Y[V\"A,\u000b\u0005aK\u0016\u0001\u00027b]\u001eT\u0011AW\u0001\u0005U\u00064\u0018-\u0003\u0002]/\n1qJ\u00196fGR\u0004\"A\u00184\u000e\u0003}S!!\t1\u000b\u0005\u0005\u0014\u0017!B7pI\u0016d'BA2e\u0003\u0015\u0019XM\u001b3b\u0015\u0005)\u0017aA8sO&\u0011qm\u0018\u0002\u000f\u0007&\u0004\b.\u001a:TkB\u0004H.[3s\u00035)gn\u0019:zaR\u001c\u0015\u000e\u001d5feV\t!\u000e\u0005\u0002la6\tAN\u0003\u0002n]\u000611M]=qi>T\u0011a\\\u0001\u0006U\u00064\u0018\r_\u0005\u0003c2\u0014aaQ5qQ\u0016\u0014\u0018!\u00043fGJL\b\u000f^\"ja\",'/\u0001\u0004q_2L7-_\u000b\u0002kB\u0011aL^\u0005\u0003o~\u00131dQ5qQ\u0016\u0014()Y:fI\u0016s7M]=qi&|g.\u0011;SKN$\u0018\u0001E3oGJL\b\u000f^3e\r&dWmT;u)\rQ\u0018\u0011\u0001\t\u0003wzl\u0011\u0001 \u0006\u0003{f\u000b!![8\n\u0005}d(\u0001D(viB,Ho\u0015;sK\u0006l\u0007bBA\u0002\u0011\u0001\u0007\u0011QA\u0001\u0005M&dW\rE\u0002|\u0003\u000fI1!!\u0003}\u0005\u00111\u0015\u000e\\3\u0002\u001f\u0011,7M]=qi\u0016$g)\u001b7f\u0013:$B!a\u0004\u0002\u0016A\u001910!\u0005\n\u0007\u0005MAPA\u0006J]B,Ho\u0015;sK\u0006l\u0007bBA\f\u0013\u0001\u0007\u0011QA\u0001\u0007g>,(oY3\u0002\u000f\u0015t7M]=qiR1\u0011QDA\u0012\u0003K\u00012!KA\u0010\u0013\r\t\tC\u000b\u0002\u0005+:LG\u000fC\u0004\u0002\u0018)\u0001\r!!\u0002\t\u000f\u0005\u001d\"\u00021\u0001\u0002\u0006\u0005!A-Z:u)\u0019\ti\"a\u000b\u0002.!9\u0011qC\u0006A\u0002\u0005=\u0001bBA\u0014\u0017\u0001\u0007\u0011QA\u0001\bI\u0016\u001c'/\u001f9u)\u0019\ti\"a\r\u00026!9\u0011q\u0003\u0007A\u0002\u0005\u0015\u0001bBA\u0014\u0019\u0001\u0007\u0011QA\u0001\u0005G>\u0004\u0018\u0010F\u0002O\u0003wAq!Q\u0007\u0011\u0002\u0003\u00071)\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005\u0005#fA\"\u0002D-\u0012\u0011Q\t\t\u0005\u0003\u000f\n\t&\u0004\u0002\u0002J)!\u00111JA'\u0003%)hn\u00195fG.,GMC\u0002\u0002P)\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019&!\u0013\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u00033\u00022AVA.\u0013\tIu+\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002bA\u0019\u0011&a\u0019\n\u0007\u0005\u0015$FA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002l\u0005E\u0004cA\u0015\u0002n%\u0019\u0011q\u000e\u0016\u0003\u0007\u0005s\u0017\u0010C\u0005\u0002tE\t\t\u00111\u0001\u0002b\u0005\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!\u001f\u0011\r\u0005m\u0014\u0011QA6\u001b\t\tiHC\u0002\u0002��)\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\t\u0019)! \u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u0013\u000by\tE\u0002*\u0003\u0017K1!!$+\u0005\u001d\u0011un\u001c7fC:D\u0011\"a\u001d\u0014\u0003\u0003\u0005\r!a\u001b\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u00033\n)\nC\u0005\u0002tQ\t\t\u00111\u0001\u0002b\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002b\u0005AAo\\*ue&tw\r\u0006\u0002\u0002Z\u00051Q-];bYN$B!!#\u0002$\"I\u00111O\f\u0002\u0002\u0003\u0007\u00111N\u0001\u000e\u0017\u0016LXI\\2ssB$\u0018n\u001c8\u0011\u0005=J2#B\r\u0002,\u0006]\u0006CBAW\u0003g\u001be*\u0004\u0002\u00020*\u0019\u0011\u0011\u0017\u0016\u0002\u000fI,h\u000e^5nK&!\u0011QWAX\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0004w\u0006e\u0016BA })\t\t9+A\u0003baBd\u0017\u0010F\u0002O\u0003\u0003DQ!\u0011\u000fA\u0002\r\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002H\u00065\u0007\u0003B\u0015\u0002J\u000eK1!a3+\u0005\u0019y\u0005\u000f^5p]\"A\u0011qZ\u000f\u0002\u0002\u0003\u0007a*A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\u0012!\u0016")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/encryption/KeyEncryption.class */
public class KeyEncryption implements FileEncryption, Product, Serializable {
    private CipherSupplier cipherSupplier;
    private final String key;
    private volatile boolean bitmap$0;

    public static Option<String> unapply(final KeyEncryption x$0) {
        return KeyEncryption$.MODULE$.unapply(x$0);
    }

    public static KeyEncryption apply(final String key) {
        return KeyEncryption$.MODULE$.apply(key);
    }

    public static <A> Function1<String, A> andThen(final Function1<KeyEncryption, A> g) {
        return KeyEncryption$.MODULE$.andThen(g);
    }

    public static <A> Function1<A, KeyEncryption> compose(final Function1<A, String> g) {
        return KeyEncryption$.MODULE$.compose(g);
    }

    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String key() {
        return this.key;
    }

    public KeyEncryption copy(final String key) {
        return new KeyEncryption(key);
    }

    public String copy$default$1() {
        return key();
    }

    public String productPrefix() {
        return "KeyEncryption";
    }

    public int productArity() {
        return 1;
    }

    public Object productElement(final int x$1) {
        switch (x$1) {
            case 0:
                return key();
            default:
                return Statics.ioobe(x$1);
        }
    }

    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    public boolean canEqual(final Object x$1) {
        return x$1 instanceof KeyEncryption;
    }

    public String productElementName(final int x$1) {
        switch (x$1) {
            case 0:
                return "key";
            default:
                return (String) Statics.ioobe(x$1);
        }
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    public boolean equals(final Object x$1) {
        if (this != x$1) {
            if (x$1 instanceof KeyEncryption) {
                KeyEncryption keyEncryption = (KeyEncryption) x$1;
                String strKey = key();
                String strKey2 = keyEncryption.key();
                if (strKey != null ? strKey.equals(strKey2) : strKey2 == null) {
                    if (keyEncryption.canEqual(this)) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public KeyEncryption(final String key) {
        this.key = key;
        Product.$init$(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.util.encryption.KeyEncryption] */
    private CipherSupplier cipherSupplier$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$0) {
                this.cipherSupplier = new CipherSupplier(this) { // from class: code.util.encryption.KeyEncryption$$anon$1
                    private final /* synthetic */ KeyEncryption $outer;

                    {
                        if (this == null) {
                            throw null;
                        }
                        this.$outer = this;
                    }

                    @Override // org.sejda.model.encryption.CipherSupplier
                    public Cipher get(final int mode) {
                        return EncryptionUtil$Files$.MODULE$.getCipher(this.$outer.key(), mode);
                    }
                };
                r0 = this;
                r0.bitmap$0 = true;
            }
        }
        return this.cipherSupplier;
    }

    public CipherSupplier cipherSupplier() {
        return !this.bitmap$0 ? cipherSupplier$lzycompute() : this.cipherSupplier;
    }

    public Cipher encryptCipher() {
        return cipherSupplier().get(1);
    }

    public Cipher decryptCipher() {
        return cipherSupplier().get(2);
    }

    @Override // code.util.encryption.FileEncryption
    public CipherBasedEncryptionAtRest policy() {
        return new CipherBasedEncryptionAtRest(cipherSupplier());
    }

    @Override // code.util.encryption.FileEncryption
    public OutputStream encryptedFileOut(final File file) {
        return EncryptionUtil$Files$.MODULE$.wrap(new FileOutputStream(file), encryptCipher());
    }

    @Override // code.util.encryption.FileEncryption
    public InputStream decryptedFileIn(final File source) {
        return EncryptionUtil$Files$.MODULE$.decryptedStream(source, decryptCipher());
    }

    @Override // code.util.encryption.FileEncryption
    public void encrypt(final File source, final File dest) {
        EncryptionUtil$Files$.MODULE$.encrypt(source, dest, encryptCipher());
    }

    @Override // code.util.encryption.FileEncryption
    public void encrypt(final InputStream source, final File dest) {
        EncryptionUtil$Files$.MODULE$.encrypt(source, dest, encryptCipher());
    }

    @Override // code.util.encryption.FileEncryption
    public void decrypt(final File source, final File dest) {
        EncryptionUtil$Files$.MODULE$.decrypt(source, dest, decryptCipher());
    }
}

package org.sejda.sambox.encryption;

import java.security.MessageDigest;
import java.util.Objects;
import java.util.function.Function;
import org.bouncycastle.util.Arrays;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.COSString;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/encryption/Algorithm1.class */
class Algorithm1 implements GeneralEncryptionAlgorithm {
    private static final byte[] AES_SALT = {115, 65, 108, 84};
    private final EncryptionAlgorithmEngine engine;
    private final MessageDigest digest = MessageDigests.md5();
    private final Function<COSObjectKey, byte[]> keyCalculator;
    private Function<byte[], byte[]> md5Initializer;
    private final Function<byte[], byte[]> md5ToKey;
    private COSObjectKey currentCOSObjectKey;

    private Algorithm1(EncryptionAlgorithmEngine engine, byte[] key) {
        RequireUtils.requireNotNullArg(engine, "Encryption engine cannot be null");
        RequireUtils.requireArg(key != null && key.length > 0, "Encryption key cannot be blank");
        this.engine = engine;
        this.keyCalculator = cosKey -> {
            RequireUtils.requireNotNullArg(cosKey, "Cannot encrypt a reference with a null key");
            byte[] append = {(byte) (cosKey.objectNumber() & 255), (byte) ((cosKey.objectNumber() >> 8) & 255), (byte) ((cosKey.objectNumber() >> 16) & 255), (byte) (cosKey.generation() & 255), (byte) ((cosKey.generation() >> 8) & 255)};
            return Arrays.concatenate(key, append);
        };
        this.md5Initializer = newKey -> {
            this.digest.reset();
            this.digest.update(newKey);
            return newKey;
        };
        this.md5ToKey = newKey2 -> {
            return java.util.Arrays.copyOf(this.digest.digest(), Math.min(newKey2.length, 16));
        };
    }

    @Override // org.sejda.sambox.encryption.GeneralEncryptionAlgorithm
    public void setCurrentCOSObjectKey(COSObjectKey currentCOSObjectKey) {
        this.currentCOSObjectKey = currentCOSObjectKey;
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSString value) {
        if (value.encryptable()) {
            requireObjectKey();
            value.setValue(this.engine.encryptBytes(value.getBytes(), (byte[]) this.keyCalculator.andThen(this.md5Initializer).andThen(this.md5ToKey).apply(this.currentCOSObjectKey)));
        }
    }

    @Override // org.sejda.sambox.cos.COSVisitor
    public void visit(COSStream value) {
        if (value.encryptable()) {
            requireObjectKey();
            value.setEncryptor(i -> {
                return this.engine.encryptStream(i, (byte[]) this.keyCalculator.andThen(this.md5Initializer).andThen(this.md5ToKey).apply(this.currentCOSObjectKey));
            });
        }
    }

    private void requireObjectKey() {
        if (Objects.isNull(this.currentCOSObjectKey)) {
            throw new EncryptionException("General encryption algorithm 1 requires object number and generation number");
        }
    }

    public String toString() {
        return "Algorithm1 with engine " + this.engine;
    }

    static Algorithm1 withAESEngine(byte[] key) {
        Algorithm1 algorithm = new Algorithm1(new ConcatenatingAESEngine(), key);
        algorithm.md5Initializer = algorithm.md5Initializer.andThen(k -> {
            algorithm.digest.update(AES_SALT);
            return k;
        });
        return algorithm;
    }

    static Algorithm1 withARC4Engine(byte[] key) {
        return new Algorithm1(new ARC4Engine(), key);
    }
}

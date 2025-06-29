package org.sejda.impl.sambox.pro.component.optimization.font;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.Random;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/FontFileKey.class */
final class FontFileKey extends Record {
    private final IndirectCOSObjectIdentifier id;
    private final COSName subType;
    private final String subsetName;

    FontFileKey(IndirectCOSObjectIdentifier id, COSName subType, String subsetName) {
        this.id = id;
        this.subType = subType;
        this.subsetName = subsetName;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FontFileKey.class), FontFileKey.class, "id;subType;subsetName", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->id:Lorg/sejda/sambox/cos/IndirectCOSObjectIdentifier;", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->subType:Lorg/sejda/sambox/cos/COSName;", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->subsetName:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FontFileKey.class), FontFileKey.class, "id;subType;subsetName", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->id:Lorg/sejda/sambox/cos/IndirectCOSObjectIdentifier;", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->subType:Lorg/sejda/sambox/cos/COSName;", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->subsetName:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FontFileKey.class, Object.class), FontFileKey.class, "id;subType;subsetName", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->id:Lorg/sejda/sambox/cos/IndirectCOSObjectIdentifier;", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->subType:Lorg/sejda/sambox/cos/COSName;", "FIELD:Lorg/sejda/impl/sambox/pro/component/optimization/font/FontFileKey;->subsetName:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public IndirectCOSObjectIdentifier id() {
        return this.id;
    }

    public COSName subType() {
        return this.subType;
    }

    public String subsetName() {
        return this.subsetName;
    }

    public static FontFileKey keyOf(COSStream fontFileStream, COSName subType, String subsetName) {
        RequireUtils.requireNotNullArg(fontFileStream, "Font file stream cannot be null");
        RequireUtils.requireNotNullArg(subType, "Subtype cannot be null");
        RequireUtils.requireNotBlank(subsetName, "Subset name cannot be blank");
        IndirectCOSObjectIdentifier id = (IndirectCOSObjectIdentifier) Optional.ofNullable(fontFileStream.id()).orElseGet(() -> {
            Random random = new Random();
            return new IndirectCOSObjectIdentifier(new COSObjectKey(random.nextLong(), random.nextInt()), "dummy");
        });
        return new FontFileKey(id, subType, subsetName);
    }
}

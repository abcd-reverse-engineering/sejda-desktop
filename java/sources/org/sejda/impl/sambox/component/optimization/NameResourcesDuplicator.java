package org.sejda.impl.sambox.component.optimization;

import java.util.Optional;
import java.util.function.Consumer;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/NameResourcesDuplicator.class */
public class NameResourcesDuplicator implements Consumer<PDPage> {
    @Override // java.util.function.Consumer
    public void accept(PDPage page) {
        COSDictionary resources = (COSDictionary) Optional.ofNullable(page.getResources().getCOSObject()).map((v0) -> {
            return v0.duplicate();
        }).orElseGet(COSDictionary::new);
        page.setResources(new PDResources(resources));
        Optional.ofNullable((COSDictionary) resources.getDictionaryObject(COSName.XOBJECT, COSDictionary.class)).map((v0) -> {
            return v0.duplicate();
        }).ifPresent(d -> {
            resources.setItem(COSName.XOBJECT, (COSBase) d);
        });
        Optional.ofNullable((COSDictionary) resources.getDictionaryObject(COSName.FONT, COSDictionary.class)).map((v0) -> {
            return v0.duplicate();
        }).ifPresent(d2 -> {
            resources.setItem(COSName.FONT, (COSBase) d2);
        });
        Optional.ofNullable((COSDictionary) resources.getDictionaryObject(COSName.EXT_G_STATE, COSDictionary.class)).map((v0) -> {
            return v0.duplicate();
        }).ifPresent(d3 -> {
            resources.setItem(COSName.EXT_G_STATE, (COSBase) d3);
        });
        Optional.ofNullable((COSDictionary) resources.getDictionaryObject(COSName.PATTERN, COSDictionary.class)).map((v0) -> {
            return v0.duplicate();
        }).ifPresent(d4 -> {
            resources.setItem(COSName.PATTERN, (COSBase) d4);
        });
    }
}

package org.sejda.sambox.pdmodel;

import java.io.IOException;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/PDDocumentNameDestinationDictionary.class */
public class PDDocumentNameDestinationDictionary implements COSObjectable {
    private final COSDictionary nameDictionary;

    public PDDocumentNameDestinationDictionary(COSDictionary dict) {
        this.nameDictionary = dict;
    }

    @Override // org.sejda.sambox.cos.COSObjectable
    public COSDictionary getCOSObject() {
        return this.nameDictionary;
    }

    public PDDestination getDestination(String name) throws IOException {
        COSBase item = this.nameDictionary.getDictionaryObject(name);
        if (item instanceof COSDictionary) {
            return PDDestination.create(((COSDictionary) item).getDictionaryObject(COSName.D));
        }
        return PDDestination.create(item);
    }
}

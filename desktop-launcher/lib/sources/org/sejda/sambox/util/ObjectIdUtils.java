package org.sejda.sambox.util;

import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSObjectable;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/util/ObjectIdUtils.class */
public class ObjectIdUtils {
    private ObjectIdUtils() {
    }

    public static String getObjectIdOf(COSObjectable obj) {
        try {
            return getObjectIdOf(obj.getCOSObject().id().objectIdentifier);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getObjectIdOf(COSObjectKey ident) {
        String str = ident.generation() == 0 ? "" : ident.generation();
        long jObjectNumber = ident.objectNumber();
        return jObjectNumber + "R" + jObjectNumber;
    }
}

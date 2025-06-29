package org.sejda.sambox.cos;

import org.sejda.commons.util.RequireUtils;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/IndirectCOSObjectIdentifier.class */
public final class IndirectCOSObjectIdentifier {
    public final COSObjectKey objectIdentifier;
    public final String ownerIdentifier;

    public IndirectCOSObjectIdentifier(COSObjectKey objectIdentifier, String ownerIdentifier) {
        RequireUtils.requireNotNullArg(objectIdentifier, "Object identifier cannot be null");
        RequireUtils.requireNotBlank(ownerIdentifier, "Owning document identifier cannot be blank");
        this.objectIdentifier = objectIdentifier;
        this.ownerIdentifier = ownerIdentifier;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IndirectCOSObjectIdentifier)) {
            return false;
        }
        IndirectCOSObjectIdentifier other = (IndirectCOSObjectIdentifier) obj;
        return this.objectIdentifier.equals(other.objectIdentifier) && this.ownerIdentifier.equals(other.ownerIdentifier);
    }

    public int hashCode() {
        return Long.hashCode(this.objectIdentifier.hashCode() + this.ownerIdentifier.hashCode());
    }

    public String toString() {
        return this.objectIdentifier + " " + this.ownerIdentifier;
    }
}

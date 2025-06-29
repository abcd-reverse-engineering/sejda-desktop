package code.util.pdf;

import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSObjectKey;
import org.sejda.sambox.cos.COSObjectable;
import scala.Predef$;
import scala.collection.StringOps$;
import scala.collection.immutable.Set;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

/* compiled from: ObjIdUtils.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/util/pdf/ObjIdUtils$.class */
public final class ObjIdUtils$ {
    public static final ObjIdUtils$ MODULE$ = new ObjIdUtils$();

    private ObjIdUtils$() {
    }

    public String objIdOf(final COSObjectable o) {
        return objIdOf(o.getCOSObject().id().objectIdentifier);
    }

    public String objIdOf(final COSObjectKey ident) {
        Object gen = ident.generation() == 0 ? "" : BoxesRunTime.boxToInteger(ident.generation());
        return new StringBuilder(1).append(ident.objectNumber()).append("R").append(gen).toString();
    }

    public String objIdOfOrEmpty(final COSObjectable o) {
        try {
            return objIdOf(o);
        } catch (Throwable th) {
            if (NonFatal$.MODULE$.apply(th)) {
                return "";
            }
            throw th;
        }
    }

    public Set<String> getInheritableAttribute$default$3() {
        return Predef$.MODULE$.Set().empty();
    }

    public COSBase getInheritableAttribute(final COSDictionary node, final COSName key, final Set<String> visitedObjIds) {
        COSBase value = node.getDictionaryObject(key);
        if (value != null) {
            return value;
        }
        COSDictionary parent = (COSDictionary) node.getDictionaryObject(COSName.PARENT, COSName.P, COSDictionary.class);
        String objId = objIdOfOrEmpty(node);
        if ((!StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(objId)) || !visitedObjIds.contains(objId)) && parent != null) {
            return getInheritableAttribute(parent, key, (Set) visitedObjIds.$plus(objId));
        }
        return null;
    }

    public boolean haveSameId(final COSObjectable a, final COSObjectable b) {
        String ida = objIdOfOrEmpty(a);
        String idb = objIdOfOrEmpty(b);
        if (ida != null ? ida.equals(idb) : idb == null) {
            if (StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(ida))) {
                return true;
            }
        }
        return false;
    }
}

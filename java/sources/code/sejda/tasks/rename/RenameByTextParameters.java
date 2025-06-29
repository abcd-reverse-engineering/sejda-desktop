package code.sejda.tasks.rename;

import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

/* compiled from: RenameByTextParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\r3AAB\u0004\u0001!!Aa\u0004\u0001BC\u0002\u0013\u0005q\u0004\u0003\u00053\u0001\t\u0005\t\u0015!\u0003!\u0011!\u0019\u0004A!b\u0001\n\u0003!\u0004\u0002C\u001f\u0001\u0005\u0003\u0005\u000b\u0011B\u001b\t\u000by\u0002A\u0011A \u0003-I+g.Y7f\u0005f$V\r\u001f;QCJ\fW.\u001a;feNT!\u0001C\u0005\u0002\rI,g.Y7f\u0015\tQ1\"A\u0003uCN\\7O\u0003\u0002\r\u001b\u0005)1/\u001a6eC*\ta\"\u0001\u0003d_\u0012,7\u0001A\n\u0003\u0001E\u0001\"A\u0005\u000f\u000e\u0003MQ!\u0001F\u000b\u0002\t\t\f7/\u001a\u0006\u0003-]\t\u0011\u0002]1sC6,G/\u001a:\u000b\u0005aI\u0012!B7pI\u0016d'B\u0001\u0007\u001b\u0015\u0005Y\u0012aA8sO&\u0011Qd\u0005\u0002*\u001bVdG/\u001b9mKB#gmU8ve\u000e,W*\u001e7uSBdWmT;uaV$\b+\u0019:b[\u0016$XM]:\u0002\u000b\u0005\u0014X-Y:\u0016\u0003\u0001\u00022!I\u0016/\u001d\t\u0011\u0003F\u0004\u0002$M5\tAE\u0003\u0002&\u001f\u00051AH]8pizJ\u0011aJ\u0001\u0006g\u000e\fG.Y\u0005\u0003S)\nq\u0001]1dW\u0006<WMC\u0001(\u0013\taSFA\u0002TKFT!!\u000b\u0016\u0011\u0005=\u0002T\"A\u0004\n\u0005E:!A\u0003*f]\u0006lW-\u0011:fC\u00061\u0011M]3bg\u0002\nQB]3oC6,\u0007+\u0019;uKJtW#A\u001b\u0011\u0005YRdBA\u001c9!\t\u0019#&\u0003\u0002:U\u00051\u0001K]3eK\u001aL!a\u000f\u001f\u0003\rM#(/\u001b8h\u0015\tI$&\u0001\bsK:\fW.\u001a)biR,'O\u001c\u0011\u0002\rqJg.\u001b;?)\r\u0001\u0015I\u0011\t\u0003_\u0001AQAH\u0003A\u0002\u0001BQaM\u0003A\u0002U\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/rename/RenameByTextParameters.class */
public class RenameByTextParameters extends MultiplePdfSourceMultipleOutputParameters {
    private final Seq<RenameArea> areas;
    private final String renamePattern;

    public Seq<RenameArea> areas() {
        return this.areas;
    }

    public String renamePattern() {
        return this.renamePattern;
    }

    public RenameByTextParameters(final Seq<RenameArea> areas, final String renamePattern) {
        this.areas = areas;
        this.renamePattern = renamePattern;
    }
}

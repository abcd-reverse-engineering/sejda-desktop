package code.sejda.tasks.bookmark;

import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

/* compiled from: EditBookmarksTask.scala */
@ScalaSignature(bytes = "\u0006\u0005q2Q!\u0002\u0004\u0002\u0002=AQA\u0006\u0001\u0005\u0002]AQA\u0007\u0001\u0007\u0002mAQA\r\u0001\u0005\nMBQA\u000e\u0001\u0005\u0002]\u0012\u0011\u0004U1sK:$h)\u001b:ti>+H\u000f\\5oKZK7/\u001b;pe*\u0011q\u0001C\u0001\tE>|7.\\1sW*\u0011\u0011BC\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003\u00171\tQa]3kI\u0006T\u0011!D\u0001\u0005G>$Wm\u0001\u0001\u0014\u0005\u0001\u0001\u0002CA\t\u0015\u001b\u0005\u0011\"\"A\n\u0002\u000bM\u001c\u0017\r\\1\n\u0005U\u0011\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u00021A\u0011\u0011\u0004A\u0007\u0002\r\u0005)a/[:jiR\u0011Ad\b\t\u0003#uI!A\b\n\u0003\tUs\u0017\u000e\u001e\u0005\u0006A\t\u0001\r!I\u0001\u0005SR,W\u000e\u0005\u0002#a5\t1E\u0003\u0002%K\u00059q.\u001e;mS:,'B\u0001\u0014(\u0003I!wnY;nK:$h.\u0019<jO\u0006$\u0018n\u001c8\u000b\u0005!J\u0013aC5oi\u0016\u0014\u0018m\u0019;jm\u0016T!AK\u0016\u0002\u000fA$Wn\u001c3fY*\u0011A&L\u0001\u0007g\u0006l'm\u001c=\u000b\u0005-q#\"A\u0018\u0002\u0007=\u0014x-\u0003\u00022G\ti\u0001\u000bR(vi2Lg.\u001a(pI\u0016\faa\u0018<jg&$HC\u0001\u000f5\u0011\u0015)4\u00011\u0001\"\u0003\u0011qw\u000eZ3\u0002\u0019YL7/\u001b;PkRd\u0017N\\3\u0015\u0005qA\u0004\"\u0002\u0013\u0005\u0001\u0004I\u0004C\u0001\u0012;\u0013\tY4EA\tQ\t\u0012{7-^7f]R|U\u000f\u001e7j]\u0016\u0004")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/ParentFirstOutlineVisitor.class */
public abstract class ParentFirstOutlineVisitor {
    public abstract void visit(final PDOutlineNode item);

    /* JADX INFO: Access modifiers changed from: private */
    public void _visit(final PDOutlineNode node) {
        visit(node);
        ((IterableOnceOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(node.children()).asScala()).foreach(c -> {
            this._visit(c);
            return BoxedUnit.UNIT;
        });
    }

    public void visitOutline(final PDDocumentOutline outline) {
        _visit(outline);
    }
}

package code.sejda.tasks.bookmark;

import code.util.Loggable;
import code.util.pdf.ObjIdUtils$;
import java.io.File;
import java.io.IOException;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.task.BaseTask;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.slf4j.Logger;
import scala.MatchError;
import scala.Option$;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.StrictOptimizedSeqOps;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.collection.mutable.Set;
import scala.collection.mutable.Set$;
import scala.math.Ordering$Int$;
import scala.math.Ordering$String$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.LazyRef;
import scala.runtime.ObjectRef;

/* compiled from: EditBookmarksTask.scala */
@ScalaSignature(bytes = "\u0006\u0005\u001d3A!\u0002\u0004\u0001\u001f!)Q\u0005\u0001C\u0001M!)\u0001\u0006\u0001C\u0001S!)Q\b\u0001C!}!Qq\b\u0001I\u0001\u0002\u0003\u0005I\u0011\u0001!\u0003#\u0015#\u0017\u000e\u001e\"p_.l\u0017M]6t)\u0006\u001c8N\u0003\u0002\b\u0011\u0005A!m\\8l[\u0006\u00148N\u0003\u0002\n\u0015\u0005)A/Y:lg*\u00111\u0002D\u0001\u0006g\u0016TG-\u0019\u0006\u0002\u001b\u0005!1m\u001c3f\u0007\u0001\u00192\u0001\u0001\t !\r\t\u0012dG\u0007\u0002%)\u00111\u0003F\u0001\u0005i\u0006\u001c8N\u0003\u0002\u0016-\u0005)Qn\u001c3fY*\u00111b\u0006\u0006\u00021\u0005\u0019qN]4\n\u0005i\u0011\"\u0001\u0003\"bg\u0016$\u0016m]6\u0011\u0005qiR\"\u0001\u0004\n\u0005y1!aF#eSR\u0014un\\6nCJ\\7\u000fU1sC6,G/\u001a:t!\t\u00013%D\u0001\"\u0015\t\u0011C\"\u0001\u0003vi&d\u0017B\u0001\u0013\"\u0005!aunZ4bE2,\u0017A\u0002\u001fj]&$h\bF\u0001(!\ta\u0002!A\u0004fq\u0016\u001cW\u000f^3\u0015\u0005)\u0002\u0004CA\u0016/\u001b\u0005a#\"A\u0017\u0002\u000bM\u001c\u0017\r\\1\n\u0005=b#\u0001B+oSRDQ!\r\u0002A\u0002m\ta\u0001]1sC6\u001c\bf\u0001\u00024yA\u00191\u0006\u000e\u001c\n\u0005Ub#A\u0002;ie><8\u000f\u0005\u00028u5\t\u0001H\u0003\u0002:)\u0005IQ\r_2faRLwN\\\u0005\u0003wa\u0012Q\u0002V1tW\u0016C8-\u001a9uS>t7%\u0001\u001c\u0002\u000b\u00054G/\u001a:\u0015\u0003)\n!\u0004\u001d:pi\u0016\u001cG/\u001a3%Kb,7-\u001e;j_:\u001cuN\u001c;fqR$\"!Q#\u0015\u0003\t\u0003\"!E\"\n\u0005\u0011\u0013\"\u0001\u0006+bg.,\u00050Z2vi&|gnQ8oi\u0016DH\u000fC\u0004G\t\u0005\u0005\t\u0019A\u0014\u0002\u0007a$\u0013\u0007")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/bookmark/EditBookmarksTask.class */
public class EditBookmarksTask extends BaseTask<EditBookmarksParameters> implements Loggable {
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    @Override // org.sejda.model.task.Task
    public void after() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.sejda.tasks.bookmark.EditBookmarksTask] */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!this.bitmap$trans$0) {
                this.logger = logger();
                r0 = this;
                r0.bitmap$trans$0 = true;
            }
        }
        return this.logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !this.bitmap$trans$0 ? logger$lzycompute() : this.logger;
    }

    public EditBookmarksTask() {
        Loggable.$init$(this);
    }

    @Override // org.sejda.model.task.Task
    public void execute(final EditBookmarksParameters params) throws TaskException {
        int totalSteps = params.getSourceList().size();
        IntRef currentStep = IntRef.create(0);
        MultipleOutputWriter outputWriter = OutputWriters.newMultipleOutputWriter(params.getExistingOutputPolicy(), executionContext());
        DefaultPdfSourceOpener opener = new DefaultPdfSourceOpener(executionContext());
        IntRef updatedItems = IntRef.create(0);
        ObjectRef updatedItemIds = ObjectRef.create(Nil$.MODULE$);
        ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(params.getSourceList()).asScala()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, opener, params, updatedItems, updatedItemIds, outputWriter, totalSteps, source);
            return BoxedUnit.UNIT;
        });
        if (updatedItems.elem == 0) {
            throw new TaskException("No bookmarks were updated");
        }
        int expectedUpdatedCount = params.added().size() + params.updated().size() + params.deleted().size();
        List expectedUpdatedIds = (List) ((StrictOptimizedSeqOps) ((IterableOps) params.added().map(x$5 -> {
            return x$5.tmpId();
        }).$plus$plus(params.updated().map(x$6 -> {
            return x$6.id();
        }))).$plus$plus(params.deleted().map(x$7 -> {
            return x$7.id();
        }))).sorted(Ordering$String$.MODULE$);
        if (updatedItems.elem != expectedUpdatedCount) {
            logger().debug(new StringBuilder(14).append("Expected ids: ").append(expectedUpdatedIds).toString());
            logger().debug(new StringBuilder(12).append("Actual ids: ").append(((List) updatedItemIds.elem).sorted(Ordering$String$.MODULE$)).toString());
            throw new TaskException(new StringBuilder(46).append("Update bookmarks mismatch, expected: ").append(expectedUpdatedCount).append(" actual: ").append(updatedItems.elem).toString());
        }
        params.getOutput().accept(outputWriter);
    }

    public static final /* synthetic */ void $anonfun$execute$1(final EditBookmarksTask $this, final IntRef currentStep$1, final DefaultPdfSourceOpener opener$1, final EditBookmarksParameters params$1, final IntRef updatedItems$1, final ObjectRef updatedItemIds$1, final MultipleOutputWriter outputWriter$1, final int totalSteps$1, final PdfSource source) throws IllegalStateException, IOException, TaskException {
        $this.logger().debug(new StringBuilder(21).append("Editing bookmarks in ").append(source).toString());
        currentStep$1.elem++;
        PDDocumentHandler docHandler = (PDDocumentHandler) source.open(opener$1);
        final PDDocument doc = docHandler.getUnderlyingPDDocument();
        File outputFile = IOUtils.createTemporaryBuffer();
        PDDocumentOutline outline = (PDDocumentOutline) Option$.MODULE$.apply(doc.getDocumentCatalog().getDocumentOutline()).getOrElse(() -> {
            return new PDDocumentOutline();
        });
        final Map tmpIdMapping = (Map) Map$.MODULE$.apply(Nil$.MODULE$);
        final Set visited = (Set) Set$.MODULE$.apply(Nil$.MODULE$);
        ParentFirstOutlineVisitor visitor = new ParentFirstOutlineVisitor($this, visited, params$1, updatedItems$1, updatedItemIds$1, doc, tmpIdMapping) { // from class: code.sejda.tasks.bookmark.EditBookmarksTask$$anon$1
            private final /* synthetic */ EditBookmarksTask $outer;
            private final Set visited$1;
            private final EditBookmarksParameters params$1;
            private final IntRef updatedItems$1;
            private final ObjectRef updatedItemIds$1;
            private final PDDocument doc$1;
            private final Map tmpIdMapping$1;

            {
                if ($this == null) {
                    throw null;
                }
                this.$outer = $this;
                this.visited$1 = visited;
                this.params$1 = params$1;
                this.updatedItems$1 = updatedItems$1;
                this.updatedItemIds$1 = updatedItemIds$1;
                this.doc$1 = doc;
                this.tmpIdMapping$1 = tmpIdMapping;
            }

            /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
            @Override // code.sejda.tasks.bookmark.ParentFirstOutlineVisitor
            public void visit(final PDOutlineNode node) throws MatchError {
                String title;
                LazyRef item$lzy = new LazyRef();
                String id = EditBookmarksTask.code$sejda$tasks$bookmark$EditBookmarksTask$$objIdMaybeTempOfNode$1(node, this.tmpIdMapping$1);
                if (this.visited$1.contains(id)) {
                    return;
                }
                this.visited$1.$plus$eq(id);
                if (node instanceof PDOutlineItem) {
                    title = ((PDOutlineItem) node).getTitle();
                } else {
                    if (!(node instanceof PDDocumentOutline)) {
                        throw new MatchError(node);
                    }
                    title = "root/outline";
                }
                String title2 = title;
                this.$outer.logger().debug(new StringBuilder(34).append("Visiting outline item id: ").append(id).append(" title: ").append(title2).toString());
                this.params$1.deleted().filter(x$1 -> {
                    return BoxesRunTime.boxToBoolean($anonfun$visit$1(id, x$1));
                }).foreach(i -> {
                    $anonfun$visit$2(this, id, title2, item$lzy, node, i);
                    return BoxedUnit.UNIT;
                });
                this.params$1.updated().filter(x$2 -> {
                    return BoxesRunTime.boxToBoolean($anonfun$visit$3(id, x$2));
                }).foreach(i2 -> {
                    $anonfun$visit$4(this, id, title2, item$lzy, node, i2);
                    return BoxedUnit.UNIT;
                });
                ((List) this.params$1.added().filter(x$3 -> {
                    return BoxesRunTime.boxToBoolean($anonfun$visit$6(id, x$3));
                }).sortBy(x$4 -> {
                    return BoxesRunTime.boxToInteger(x$4.index());
                }, Ordering$Int$.MODULE$)).foreach(i3 -> {
                    this.$outer.logger().debug(new StringBuilder(44).append("Adding outline item ").append(i3.tmpId()).append(" to parent id: ").append(id).append(", title: ").append(title2).toString());
                    PDOutlineItem newItem = new PDOutlineItem();
                    newItem.setTitle(i3.title());
                    newItem.setDestination(this.doc$1.getPage(i3.page() - 1));
                    int kidsBefore = ((IterableOnceOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(node.children()).asScala()).toList().size();
                    node.addAtPosition(newItem, i3.index());
                    int kidsAfter = ((IterableOnceOps) JavaConverters$.MODULE$.iterableAsScalaIterableConverter(node.children()).asScala()).toList().size();
                    if (kidsAfter != kidsBefore + 1) {
                        throw new RuntimeException(new StringBuilder(51).append("Could not add outline item ").append(i3.tmpId()).append(" to parent id: ").append(id).append(", title: ").append(title2).toString());
                    }
                    this.updatedItems$1.elem++;
                    this.updatedItemIds$1.elem = ((List) this.updatedItemIds$1.elem).$colon$colon(id);
                    return this.tmpIdMapping$1.put(newItem, i3.tmpId());
                });
            }

            private static final /* synthetic */ PDOutlineItem item$lzycompute$1(final LazyRef item$lzy$1, final PDOutlineNode node$2) {
                PDOutlineItem pDOutlineItem;
                synchronized (item$lzy$1) {
                    pDOutlineItem = item$lzy$1.initialized() ? (PDOutlineItem) item$lzy$1.value() : (PDOutlineItem) item$lzy$1.initialize((PDOutlineItem) node$2);
                }
                return pDOutlineItem;
            }

            private static final PDOutlineItem item$1(final LazyRef item$lzy$1, final PDOutlineNode node$2) {
                return item$lzy$1.initialized() ? (PDOutlineItem) item$lzy$1.value() : item$lzycompute$1(item$lzy$1, node$2);
            }

            public static final /* synthetic */ boolean $anonfun$visit$1(final String id$1, final DeletedBookmark x$1) {
                String strId = x$1.id();
                return strId != null ? strId.equals(id$1) : id$1 == null;
            }

            public static final /* synthetic */ void $anonfun$visit$2(final EditBookmarksTask$$anon$1 $this2, final String id$1, final String title$1, final LazyRef item$lzy$1, final PDOutlineNode node$2, final DeletedBookmark i) {
                $this2.$outer.logger().debug(new StringBuilder(34).append("Deleting outline item id: ").append(id$1).append(" title: ").append(title$1).toString());
                item$1(item$lzy$1, node$2).delete();
                $this2.updatedItems$1.elem++;
                $this2.updatedItemIds$1.elem = ((List) $this2.updatedItemIds$1.elem).$colon$colon(id$1);
            }

            public static final /* synthetic */ boolean $anonfun$visit$3(final String id$1, final UpdatedBookmark x$2) {
                String strId = x$2.id();
                return strId != null ? strId.equals(id$1) : id$1 == null;
            }

            public static final /* synthetic */ void $anonfun$visit$4(final EditBookmarksTask$$anon$1 $this2, final String id$1, final String title$1, final LazyRef item$lzy$1, final PDOutlineNode node$2, final UpdatedBookmark i) {
                $this2.$outer.logger().debug(new StringBuilder(38).append("Updating outline item id: ").append(id$1).append(" old title: ").append(title$1).toString());
                item$1(item$lzy$1, node$2).setTitle(i.title());
                i.page().foreach(p -> {
                    item$1(item$lzy$1, node$2).setDestination($this2.doc$1.getPage(p - 1));
                });
                $this2.updatedItems$1.elem++;
                $this2.updatedItemIds$1.elem = ((List) $this2.updatedItemIds$1.elem).$colon$colon(id$1);
            }

            public static final /* synthetic */ boolean $anonfun$visit$6(final String id$1, final AddedBookmark x$3) {
                String strParentId = x$3.parentId();
                return strParentId != null ? strParentId.equals(id$1) : id$1 == null;
            }
        };
        visitor.visitOutline(outline);
        doc.getDocumentCatalog().setDocumentOutline(outline);
        docHandler.savePDDocument(outputFile, params$1.getOutput().getEncryptionAtRestPolicy());
        String outName = NameGenerator.nameGenerator(params$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(currentStep$1.elem));
        outputWriter$1.addOutput(FileOutput.file(outputFile).name(outName));
        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf(totalSteps$1);
        docHandler.close();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final String code$sejda$tasks$bookmark$EditBookmarksTask$$objIdMaybeTempOfNode$1(final PDOutlineNode node, final Map tmpIdMapping$1) throws MatchError {
        if (node instanceof PDOutlineItem) {
            return (String) tmpIdMapping$1.getOrElse(node, () -> {
                return ObjIdUtils$.MODULE$.objIdOf(node);
            });
        }
        if (node instanceof PDDocumentOutline) {
            return "ROOT";
        }
        throw new MatchError(node);
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final EditBookmarksTask x$1) {
        return x$1.executionContext();
    }
}

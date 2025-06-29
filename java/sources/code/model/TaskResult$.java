package code.model;

import code.limits.QuotaUpdate;
import code.limits.QuotaUpdate$;
import code.limits.Upgrade;
import code.limits.Upgrade$;
import code.sejda.tasks.common.ClientFacingTaskException;
import code.sejda.tasks.html.HtmlToPdfConversionException;
import code.sejda.tasks.ocr.NoTextFoundException;
import code.service.TaskFailed;
import code.service.TaskOutputTooLargeException;
import code.service.TaskTimedOutBefore;
import code.util.JsonExtract$;
import code.util.Loggable;
import code.util.StringHelpers$;
import code.util.exceptions.ExceptionUtils$;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import javax.imageio.IIOException;
import net.liftweb.json.JsonAST;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FilenameUtils;
import org.sejda.model.exception.TaskNonLenientExecutionException;
import org.sejda.model.exception.TaskOutputVisitException;
import org.sejda.model.exception.TaskPermissionsException;
import org.sejda.model.exception.TaskWrongPasswordException;
import org.sejda.model.pro.optimization.FileAlreadyWellOptimizedException;
import org.sejda.sambox.pdmodel.InvalidNumberOfPagesException;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.graphics.image.UnsupportedImageFormatException;
import org.slf4j.Logger;
import scala.$less$colon$less$;
import scala.Enumeration;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.Tuple12;
import scala.collection.Iterable;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.NonLocalReturnControl;
import scala.runtime.ObjectRef;
import scala.runtime.ScalaRunTime$;
import scala.util.Try$;
import scala.util.control.NonFatal$;
import scala.util.matching.Regex;

/* compiled from: Task.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskResult$.class */
public final class TaskResult$ implements Loggable, Serializable {
    public static final TaskResult$ MODULE$ = new TaskResult$();
    private static transient Logger logger;
    private static volatile transient boolean bitmap$trans$0;

    public TaskResult apply(final Enumeration.Value status, final Option<File> result, final Seq<File> resultItems, final Option<String> failureReason, final Option<String> failureReasonCode, final Option<String> failureStack, final boolean timedOut, final Seq<String> warnings, final Option<String> failureScope, final Option<Upgrade> upgrade, final Option<String> remark, final Option<QuotaUpdate> quotaUpdate) {
        return new TaskResult(status, result, resultItems, failureReason, failureReasonCode, failureStack, timedOut, warnings, failureScope, upgrade, remark, quotaUpdate);
    }

    public Option<Tuple12<Enumeration.Value, Option<File>, Seq<File>, Option<String>, Option<String>, Option<String>, Object, Seq<String>, Option<String>, Option<Upgrade>, Option<String>, Option<QuotaUpdate>>> unapply(final TaskResult x$0) {
        return x$0 == null ? None$.MODULE$ : new Some(new Tuple12(x$0.status(), x$0.result(), x$0.resultItems(), x$0.failureReason(), x$0.failureReasonCode(), x$0.failureStack(), BoxesRunTime.boxToBoolean(x$0.timedOut()), x$0.warnings(), x$0.failureScope(), x$0.upgrade(), x$0.remark(), x$0.quotaUpdate()));
    }

    public Option<File> $lessinit$greater$default$2() {
        return None$.MODULE$;
    }

    public Option<File> apply$default$2() {
        return None$.MODULE$;
    }

    public Seq<File> $lessinit$greater$default$3() {
        return package$.MODULE$.Seq().empty();
    }

    public Seq<File> apply$default$3() {
        return package$.MODULE$.Seq().empty();
    }

    public Option<String> $lessinit$greater$default$4() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$4() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$5() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$5() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$6() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$6() {
        return None$.MODULE$;
    }

    public boolean $lessinit$greater$default$7() {
        return false;
    }

    public boolean apply$default$7() {
        return false;
    }

    public Seq<String> $lessinit$greater$default$8() {
        return Nil$.MODULE$;
    }

    public Seq<String> apply$default$8() {
        return Nil$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$9() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$9() {
        return None$.MODULE$;
    }

    public Option<Upgrade> $lessinit$greater$default$10() {
        return None$.MODULE$;
    }

    public Option<Upgrade> apply$default$10() {
        return None$.MODULE$;
    }

    public Option<String> $lessinit$greater$default$11() {
        return None$.MODULE$;
    }

    public Option<String> apply$default$11() {
        return None$.MODULE$;
    }

    public Option<QuotaUpdate> $lessinit$greater$default$12() {
        return None$.MODULE$;
    }

    public Option<QuotaUpdate> apply$default$12() {
        return None$.MODULE$;
    }

    static {
        Loggable.$init$(MODULE$);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private Logger logger$lzycompute() {
        ?? r0 = this;
        synchronized (r0) {
            if (!bitmap$trans$0) {
                logger = logger();
                r0 = 1;
                bitmap$trans$0 = true;
            }
        }
        return logger;
    }

    @Override // code.util.Loggable
    public Logger logger() {
        return !bitmap$trans$0 ? logger$lzycompute() : logger;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TaskResult$.class);
    }

    private TaskResult$() {
    }

    public TaskResult failureKnownReasonDesktopOnly(final String reason, final Throwable cause) {
        return failureKnownReason(reason, cause, new Some("desktop"), failureKnownReason$default$4());
    }

    public Option<String> failureKnownReason$default$3() {
        return None$.MODULE$;
    }

    public String failureKnownReason$default$4() {
        return "";
    }

    public TaskResult failureKnownReason(final String reason, final Throwable cause, final Option<String> failureScope, final String code2) {
        Enumeration.Value x$1 = TaskStatus$.MODULE$.Failed();
        Some x$2 = new Some(reason);
        Some x$3 = new Some(ExceptionUtils$.MODULE$.asString(cause));
        Option x$5 = StringHelpers$.MODULE$.asOpt(code2);
        Option x$6 = $lessinit$greater$default$2();
        Seq x$7 = $lessinit$greater$default$3();
        boolean x$8 = $lessinit$greater$default$7();
        Seq x$9 = $lessinit$greater$default$8();
        Option x$10 = $lessinit$greater$default$10();
        Option x$11 = $lessinit$greater$default$11();
        Option x$12 = $lessinit$greater$default$12();
        return new TaskResult(x$1, x$6, x$7, x$2, x$5, x$3, x$8, x$9, failureScope, x$10, x$11, x$12);
    }

    public Option<Throwable> failureTimedOut$default$1() {
        return None$.MODULE$;
    }

    public TaskResult failureTimedOut(final Option<Throwable> causeOpt) {
        Enumeration.Value x$1 = TaskStatus$.MODULE$.Failed();
        Some x$2 = new Some("Task took too long to process.");
        Option x$4 = causeOpt.map(throwable -> {
            return ExceptionUtils$.MODULE$.asString(throwable);
        });
        Some x$5 = new Some(new Upgrade("timeout", Upgrade$.MODULE$.apply$default$2()));
        Option x$6 = $lessinit$greater$default$2();
        Seq x$7 = $lessinit$greater$default$3();
        Option x$8 = $lessinit$greater$default$5();
        Seq x$9 = $lessinit$greater$default$8();
        Option x$10 = $lessinit$greater$default$9();
        Option x$11 = $lessinit$greater$default$11();
        Option x$12 = $lessinit$greater$default$12();
        return new TaskResult(x$1, x$6, x$7, x$2, x$8, x$4, true, x$9, x$10, x$5, x$11, x$12);
    }

    public TaskResult failureCancelled() {
        Enumeration.Value x$1 = TaskStatus$.MODULE$.Failed();
        Some x$2 = new Some("Task cancelled");
        Option x$3 = $lessinit$greater$default$2();
        Seq x$4 = $lessinit$greater$default$3();
        Option x$5 = $lessinit$greater$default$5();
        Option x$6 = $lessinit$greater$default$6();
        boolean x$7 = $lessinit$greater$default$7();
        Seq x$8 = $lessinit$greater$default$8();
        Option x$9 = $lessinit$greater$default$9();
        Option x$10 = $lessinit$greater$default$10();
        Option x$11 = $lessinit$greater$default$11();
        Option x$12 = $lessinit$greater$default$12();
        return new TaskResult(x$1, x$3, x$4, x$2, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12);
    }

    public TaskResult failure(final Throwable cause) {
        Enumeration.Value x$1 = TaskStatus$.MODULE$.Failed();
        Some x$2 = new Some(ExceptionUtils$.MODULE$.asString(cause));
        Option x$3 = $lessinit$greater$default$2();
        Seq x$4 = $lessinit$greater$default$3();
        Option x$5 = $lessinit$greater$default$4();
        Option x$6 = $lessinit$greater$default$5();
        boolean x$7 = $lessinit$greater$default$7();
        Seq x$8 = $lessinit$greater$default$8();
        Option x$9 = $lessinit$greater$default$9();
        Option x$10 = $lessinit$greater$default$10();
        Option x$11 = $lessinit$greater$default$11();
        Option x$12 = $lessinit$greater$default$12();
        return new TaskResult(x$1, x$3, x$4, x$5, x$6, x$2, x$7, x$8, x$9, x$10, x$11, x$12);
    }

    public TaskResult success(final File result, final Seq<String> warnings, final Option<Upgrade> upgrade, final Option<String> remark, final Seq<File> resultItems) {
        Enumeration.Value x$1 = TaskStatus$.MODULE$.Completed();
        Some x$2 = new Some(result);
        Option x$7 = $lessinit$greater$default$4();
        Option x$8 = $lessinit$greater$default$5();
        Option x$9 = $lessinit$greater$default$6();
        boolean x$10 = $lessinit$greater$default$7();
        Option x$11 = $lessinit$greater$default$9();
        Option x$12 = $lessinit$greater$default$12();
        return new TaskResult(x$1, x$2, resultItems, x$7, x$8, x$9, x$10, warnings, x$11, upgrade, remark, x$12);
    }

    public TaskResult processing() {
        return new TaskResult(TaskStatus$.MODULE$.Processing(), $lessinit$greater$default$2(), $lessinit$greater$default$3(), $lessinit$greater$default$4(), $lessinit$greater$default$5(), $lessinit$greater$default$6(), $lessinit$greater$default$7(), $lessinit$greater$default$8(), $lessinit$greater$default$9(), $lessinit$greater$default$10(), $lessinit$greater$default$11(), $lessinit$greater$default$12());
    }

    public TaskResult queued() {
        return new TaskResult(TaskStatus$.MODULE$.Queued(), $lessinit$greater$default$2(), $lessinit$greater$default$3(), $lessinit$greater$default$4(), $lessinit$greater$default$5(), $lessinit$greater$default$6(), $lessinit$greater$default$7(), $lessinit$greater$default$8(), $lessinit$greater$default$9(), $lessinit$greater$default$10(), $lessinit$greater$default$11(), $lessinit$greater$default$12());
    }

    public TaskResult fromJson(final JsonAST.JValue json) {
        Enumeration.Value x$1 = TaskStatus$.MODULE$.fromString(JsonExtract$.MODULE$.toString(json.$bslash("status")));
        Option x$2 = JsonExtract$.MODULE$.toStringOption(json.$bslash("result")).map(path -> {
            return new File(path);
        });
        Option x$3 = JsonExtract$.MODULE$.toStringOption(json.$bslash("failureReason"));
        Option x$4 = JsonExtract$.MODULE$.toStringOption(json.$bslash("failureReasonCode"));
        Option x$5 = JsonExtract$.MODULE$.toStringOption(json.$bslash("failureStack"));
        boolean x$6 = JsonExtract$.MODULE$.toBool(json.$bslash("timedOut"));
        List x$7 = JsonExtract$.MODULE$.toList(json.$bslash("warnings"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        });
        Option x$8 = JsonExtract$.MODULE$.toStringOption(json.$bslash("failureScope"));
        Option x$9 = Upgrade$.MODULE$.fromJsonOpt(json.$bslash("upgrade"));
        Option x$10 = JsonExtract$.MODULE$.toStringOption(json.$bslash("remark"));
        List x$11 = JsonExtract$.MODULE$.toStringListOr(json.$bslash("resultItems"), package$.MODULE$.List().empty()).map(path2 -> {
            return new File(path2);
        });
        Option x$12 = QuotaUpdate$.MODULE$.fromJsonOpt(json.$bslash("quotaUpdate"));
        return new TaskResult(x$1, x$2, x$11, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$12);
    }

    public TaskResult knownFailureReasons(final Throwable ex, final int taskContext) {
        try {
            return _knownFailureReasons(ex, taskContext);
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            logger().warn("Exception occurred determining if known failure reason", th);
            return failure(ex);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.runtime.NonLocalReturnControl */
    public TaskResult _knownFailureReasons(final Throwable ex, final int taskContext) throws NonLocalReturnControl, MatchError {
        IIOException iioex;
        DataFormatException dfe;
        UnsupportedImageFormatException uife;
        Object obj = new Object();
        try {
            boolean isDesktop = taskContext == TaskContext$.MODULE$.desktop();
            boolean isNotDesktop = !isDesktop;
            Some someFind = ExceptionUtils$.MODULE$.find(ex, ClassTag$.MODULE$.apply(UnsupportedImageFormatException.class));
            if ((someFind instanceof Some) && (uife = (UnsupportedImageFormatException) someFind.value()) != null) {
                return failureKnownReason(new StringBuilder(19).append("Unsupported image: ").append(uife.getFilename()).toString(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            Some someFind2 = ExceptionUtils$.MODULE$.find(ex, ClassTag$.MODULE$.apply(DataFormatException.class));
            if ((someFind2 instanceof Some) && (dfe = (DataFormatException) someFind2.value()) != null && (dfe.getMessage().contains("invalid distance too far back") || dfe.getMessage().contains("invalid stored block lengths"))) {
                return failureKnownReason("Malformed PDF file: invalid stream with unknown compression", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
            Some someFind3 = ExceptionUtils$.MODULE$.find(ex, ClassTag$.MODULE$.apply(IIOException.class));
            if ((someFind3 instanceof Some) && (iioex = (IIOException) someFind3.value()) != null && Option$.MODULE$.apply(iioex.getMessage()).exists(x$8 -> {
                return BoxesRunTime.boxToBoolean($anonfun$_knownFailureReasons$1(x$8));
            })) {
                return failureKnownReason("Maximum image dimensions exceeded, try lowering the DPI settings", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
            if (ex instanceof TimeoutException) {
                return failureTimedOut(new Some(ex));
            }
            if (ex instanceof TaskTimedOutBefore) {
                return failureTimedOut(new Some(ex));
            }
            if (ex instanceof TaskPermissionsException) {
                return failureKnownReason(((TaskPermissionsException) ex).getMessage(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ex instanceof TaskFailed) {
                return failureKnownReason(ex.getMessage(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ex instanceof OutOfMemoryError) {
                return failureKnownReason("Out of memory", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ex instanceof TaskOutputTooLargeException) {
                return failureKnownReason("Task output is too large. Please contact support.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ex instanceof ClientFacingTaskException) {
                return failureKnownReason(ex.getMessage(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ex instanceof TaskNonLenientExecutionException) {
                Throwable cause = ((TaskNonLenientExecutionException) ex).getCause();
                if (cause instanceof PageNotFoundException) {
                    PageNotFoundException pageNotFoundException = (PageNotFoundException) cause;
                    int pageNumber = pageNotFoundException.getPage();
                    String filename = FilenameUtils.getName(pageNotFoundException.getSourcePath());
                    return failureKnownReason(new StringBuilder(24).append("Unable to read page ").append(pageNumber).append(" of ").append(filename).toString(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                BoxedUnit boxedUnit4 = BoxedUnit.UNIT;
                BoxedUnit boxedUnit5 = BoxedUnit.UNIT;
            } else {
                if (ex instanceof PageNotFoundException) {
                    PageNotFoundException pageNotFoundException2 = (PageNotFoundException) ex;
                    int pageNumber2 = pageNotFoundException2.getPage();
                    String filename2 = FilenameUtils.getName(pageNotFoundException2.getSourcePath());
                    return failureKnownReason(new StringBuilder(24).append("Unable to read page ").append(pageNumber2).append(" of ").append(filename2).toString(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                if (ex instanceof FileAlreadyWellOptimizedException) {
                    return failureKnownReason("Your PDF file is already very well compressed", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                if (ex instanceof HtmlToPdfConversionException) {
                    HtmlToPdfConversionException x$2 = (HtmlToPdfConversionException) ex;
                    String message = x$2.getMessage();
                    switch (message == null ? 0 : message.hashCode()) {
                        case -952747230:
                            if (!"Blocked (Cloudflare Captcha)".equals(message)) {
                                break;
                            } else {
                                Option x$4 = failureKnownReason$default$3();
                                return failureKnownReason("Could not convert: page uses captcha technology to block automated conversion.", x$2, x$4, "htmlpdf-captcha");
                            }
                    }
                    return failureKnownReason(x$2.getMessage(), x$2, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                if (ex instanceof TaskKnownFailureReasonException) {
                    TaskKnownFailureReasonException taskKnownFailureReasonException = (TaskKnownFailureReasonException) ex;
                    return failureKnownReason(taskKnownFailureReasonException.getMessage(), taskKnownFailureReasonException, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                if (ex instanceof NoTextFoundException) {
                    NoTextFoundException noTextFoundException = (NoTextFoundException) ex;
                    return failureKnownReason(noTextFoundException.getMessage(), noTextFoundException, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                if (ex instanceof TaskWrongPasswordException) {
                    Some someRegexMatch = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("Unable to open '(.+)' due to a wrong password")));
                    if (someRegexMatch instanceof Some) {
                        Regex.Match regex = (Regex.Match) someRegexMatch.value();
                        if (regex.groupCount() == 1) {
                            String filename3 = regex.group(1);
                            return failureKnownReason(new StringBuilder(44).append("Could not open password protected document: ").append(filename3).toString(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
                        }
                    }
                    return failureKnownReason("Could not open password protected document.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                if (ex instanceof InvalidNumberOfPagesException) {
                    return failureKnownReason("Document has unexpected number of pages.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
                }
                BoxedUnit boxedUnit6 = BoxedUnit.UNIT;
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"PdfReader not opened with owner password", "Pdf file is password protected", "Bad user password", "Cannot decrypt PDF, the password is incorrect", "Encryption error"}))) {
                return failureKnownReason("Could not open password protected document.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"Unable to split, no page number given", "No page has been selected", "No pages converted"}))) {
                return failureKnownReason("No matching pages found. Correct your input and try again.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"PDF header signature not found", "Error: End-of-File, expected", "Catalog cannot be found", "Unable to find expected file header", "root cannot be null", "Expected line but was end of file"}))) {
                return (TaskResult) Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("An error occurred opening the source: (.+)\\.$")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                        if (regex2.groupCount() == 1) {
                            String filename4 = FilenameUtils.getName(regex2.group(1));
                            return new Some(MODULE$.failureKnownReason(new StringBuilder(21).append("Unreadable PDF file: ").append(filename4).toString(), ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4()));
                        }
                    }
                    return None$.MODULE$;
                }).toOption().flatten($less$colon$less$.MODULE$.refl()).getOrElse(() -> {
                    return MODULE$.failureKnownReason("Unreadable PDF file.", ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4());
                });
            }
            if (isNotDesktop && ((ex instanceof PermanentFailureDownloadException) || ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"Failed to download file", "File not found"})) || ExceptionUtils$.MODULE$.find(ex, ClassTag$.MODULE$.apply(FileNotFoundException.class)).isDefined())) {
                return failureKnownReason("File not found. Please reupload and try again.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"Unknown encryption type R = 6", "Illegal key size", "No security handler for filter", "Security Provider Not Found."}))) {
                return failureKnownReason("Unsupported encryption", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if ((ex instanceof TemporaryFailureDownloadException) || ExceptionUtils$.MODULE$.containsString(ex, "External JVM process returned error")) {
                return failureKnownReason("A glitch, try again.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"no such entry: \"EncryptionInfo\""}))) {
                return failureKnownReason("Password protected Word documents are not yet supported.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsString(ex, "Unable to repair and recover any page from")) {
                return failureKnownReason("Could not recover any pages", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsString(ex, "Could not extract PDF contents")) {
                return failureKnownReason("No text found. Use the OCR tool first.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsString(ex, "No text could be extracted")) {
                return failureKnownReason("No text found. Use the OCR tool first.", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsString(ex, "Scans are not supported")) {
                return failureKnownReason("Scans are not supported", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsString(ex, "had all pages removed")) {
                return failureKnownReason(ex.getMessage(), ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsString(ex, "The task didn't generate any output file")) {
                return failureKnownReason("All documents had all pages removed", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsString(ex, "No pages cropped")) {
                return failureKnownReason("No pages cropped", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"Could not crop automatically", "Could not determine automatic crop areas"}))) {
                return failureKnownReason("Could not crop automatically", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"Maximum size of image exceeded"}))) {
                return failureKnownReason("Maximum image dimensions exceeded, try with lower DPI", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"All images could not be processed"}))) {
                return failureKnownReason("All images could not be processed", ex, failureKnownReason$default$3(), failureKnownReason$default$4());
            }
            if (isDesktop) {
                if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"The specified network name is no longer available", "The referenced account is currently locked out and may not be logged on", "The network path was not found"}))) {
                    return failureKnownReasonDesktopOnly("Network destination no longer available", ex);
                }
                if (ExceptionUtils$.MODULE$.containsStrings(ex, (Iterable<String>) ExceptionUtils$.MODULE$.diskFullStrings())) {
                    return failureKnownReasonDesktopOnly("Disk is full", ex);
                }
                if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"An unexpected network error occurred", "The semaphore timeout period has expired"}))) {
                    return failureKnownReasonDesktopOnly("Network error saving files", ex);
                }
                if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"The parameter is incorrect", "A device which does not exist was specified", "The filename, directory name, or volume label syntax is incorrect", "Unable to make destination directory tree"}))) {
                    return failureKnownReasonDesktopOnly("Filesystem location error: Try saving to a different location", ex);
                }
                if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"cloud file provider", "El proveedor de archivos de nube no se está ejecutando", "Leverandøren av skyfilen ble avsluttet uventet"}))) {
                    return failureKnownReasonDesktopOnly("OneDrive error accessing files", ex);
                }
                if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"Too many open files in system"}))) {
                    return failureKnownReasonDesktopOnly("System error: Too many open files in system", ex);
                }
            }
            Try$.MODULE$.apply(() -> {
                Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("The page (\\d+) was requested but the document has only (\\d+) pages")));
                if (someRegexMatch2 instanceof Some) {
                    Regex.Match regexMatch = (Regex.Match) someRegexMatch2.value();
                    throw new NonLocalReturnControl(obj, MODULE$.failureKnownReason(regexMatch.source().toString(), ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4()));
                }
                BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
            });
            if (isDesktop) {
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("(.+) \\(Access is denied\\)")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                        String target = regex2.group(1);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(17).append("Access denied to ").append(target).toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("(.+) \\(Operation not permitted\\)")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                        String target = regex2.group(1);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(17).append("Access denied to ").append(target).toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatchTyped = ExceptionUtils$.MODULE$.regexMatchTyped(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("(.+): Operation not permitted")), ClassTag$.MODULE$.apply(FileSystemException.class));
                    if (someRegexMatchTyped instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatchTyped.value();
                        String target = regex2.group(1);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(17).append("Access denied to ").append(target).toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("Unable to write (.+) to the already existing file destination (.+). \\(policy is FAIL\\)")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                        String target = regex2.group(2);
                        String filename4 = FilenameUtils.getName(target);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReason(new StringBuilder(35).append("Refused to overwrite existing file ").append(filename4).toString(), ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4()));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatchTyped = ExceptionUtils$.MODULE$.regexMatchTyped(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("(.+)\\: The process cannot access the file because it is being used by another process.")), ClassTag$.MODULE$.apply(FileSystemException.class));
                    if (someRegexMatchTyped instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatchTyped.value();
                        String target = regex2.group(1);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(84).append("Access denied to ").append(target).append(". Close other programs where the file is already open and try again").toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("A not null File instance that isFile is expected. Path: (.+)")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                        String target = regex2.group(1);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(64).append("Original file renamed or moved: ").append(target).append(". Restore the file and try again").toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("A not null directory instance is expected. Path: (.+)")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                        String target = regex2.group(1);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(21).append("Directory not found: ").append(target).toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someFind4 = ExceptionUtils$.MODULE$.find(ex, ClassTag$.MODULE$.apply(FileNotFoundException.class));
                    if (someFind4 instanceof Some) {
                        FileNotFoundException fnfe = (FileNotFoundException) someFind4.value();
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(45).append("Cannot access: ").append(fnfe.getMessage()).append(". Restore access and try again").toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someFind4 = ExceptionUtils$.MODULE$.find(ex, ClassTag$.MODULE$.apply(NoSuchFileException.class));
                    if (someFind4 instanceof Some) {
                        NoSuchFileException nsfe = (NoSuchFileException) someFind4.value();
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(64).append("Original file renamed or moved: ").append(nsfe.getMessage()).append(". Restore the file and try again").toString(), ex));
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("Destination '(.+)' directory cannot be created")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regexMatch = (Regex.Match) someRegexMatch2.value();
                        if (regexMatch.groupCount() >= 1) {
                            String target = regexMatch.group(1);
                            throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(41).append("Destination directory cannot be created: ").append(target).toString(), ex));
                        }
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    if ((ex instanceof TaskOutputVisitException) && (ex.getCause() instanceof FileAlreadyExistsException)) {
                        Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("(.+) -> (.+)")));
                        if (someRegexMatch2 instanceof Some) {
                            Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                            if (regex2.groupCount() >= 2) {
                                String target = regex2.group(2);
                                throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(26).append("Could not overwrite file: ").append(target).toString(), ex));
                            }
                        }
                        BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                        BoxedUnit boxedUnit8 = BoxedUnit.UNIT;
                        return;
                    }
                    BoxedUnit boxedUnit9 = BoxedUnit.UNIT;
                });
                Try$.MODULE$.apply(() -> {
                    Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("Wrong output destination (.+), must be a directory.")));
                    if (someRegexMatch2 instanceof Some) {
                        Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                        if (regex2.groupCount() >= 1) {
                            String destination = regex2.group(1);
                            throw new NonLocalReturnControl(obj, MODULE$.failureKnownReasonDesktopOnly(new StringBuilder(32).append("Destination must be a directory ").append(destination).toString(), ex));
                        }
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                });
                if ((ex instanceof UncheckedIOException) && ex.getCause() != null) {
                    Throwable cause2 = ex.getCause();
                    if (cause2 instanceof AccessDeniedException) {
                        String filePath = ((AccessDeniedException) cause2).getMessage();
                        if (filePath.contains("\\OneDrive\\")) {
                            return failureKnownReasonDesktopOnly(new StringBuilder(32).append("OneDrive error accessing files: ").append(filePath).toString(), ex);
                        }
                        return failureKnownReasonDesktopOnly(new StringBuilder(15).append("Access denied: ").append(filePath).toString(), ex);
                    }
                    BoxedUnit boxedUnit7 = BoxedUnit.UNIT;
                }
                if ((ex instanceof TaskOutputVisitException) && ex.getCause() != null) {
                    Throwable cause3 = ex.getCause();
                    if (cause3 instanceof AccessDeniedException) {
                        AccessDeniedException accessDeniedException = (AccessDeniedException) cause3;
                        String message2 = accessDeniedException.getMessage();
                        ObjectRef filePath2 = ObjectRef.create(accessDeniedException.getMessage());
                        Try$.MODULE$.apply(() -> {
                            Some some = (Option) Predef$.MODULE$.wrapRefArray(message2.split(Pattern.quote("->"))).lift().apply(BoxesRunTime.boxToInteger(1));
                            if (!(some instanceof Some)) {
                                BoxedUnit boxedUnit8 = BoxedUnit.UNIT;
                                return;
                            }
                            String secondPart = (String) some.value();
                            filePath2.elem = secondPart.trim();
                            BoxedUnit boxedUnit9 = BoxedUnit.UNIT;
                        });
                        return failureKnownReasonDesktopOnly(new StringBuilder(15).append("Access denied: ").append((String) filePath2.elem).toString(), ex);
                    }
                    boolean z = (cause3 instanceof FileExistsException) || (cause3 instanceof FileSystemException);
                    if (z) {
                        String message3 = cause3.getMessage();
                        return failureKnownReasonDesktopOnly(message3, ex);
                    }
                    BoxedUnit boxedUnit8 = BoxedUnit.UNIT;
                }
            }
            Try$.MODULE$.apply(() -> {
                Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("Image type not supported (.+)$")));
                if (someRegexMatch2 instanceof Some) {
                    Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                    if (regex2.groupCount() == 1) {
                        String imageFile = regex2.group(1);
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReason(new StringBuilder(19).append("Unsupported image: ").append(imageFile).toString(), ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4()));
                    }
                }
                BoxedUnit boxedUnit9 = BoxedUnit.UNIT;
            });
            Try$.MODULE$.apply(() -> {
                Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("An error occurred creating PDImageXObject from file source: (.+)$")));
                if (someRegexMatch2 instanceof Some) {
                    Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                    if (regex2.groupCount() == 1) {
                        String imageFile = regex2.group(1);
                        if (ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"destination width * height * samplesPerPixel > Integer.MAX_VALUE", "destination width * height > Integer.MAX_VALUE"}))) {
                            throw new NonLocalReturnControl(obj, MODULE$.failureKnownReason(new StringBuilder(60).append("Maximum image dimensions exceeded, try resizing your image: ").append(imageFile).toString(), ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4()));
                        }
                        if (!ExceptionUtils$.MODULE$.containsStrings(ex, (Seq<String>) ScalaRunTime$.MODULE$.wrapRefArray(new String[]{"No SOF segment in stream", "Unexpected JPEG Quantization Table Id", "Unexpected JPEG Huffman Table", "Invalid scanline stride", "Unexpected SOF length"})) && !ExceptionUtils$.MODULE$.find(ex, ClassTag$.MODULE$.apply(NegativeArraySizeException.class)).isDefined()) {
                            BoxedUnit boxedUnit9 = BoxedUnit.UNIT;
                            return;
                        }
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReason(new StringBuilder(19).append("Unsupported image: ").append(imageFile).toString(), ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4()));
                    }
                }
                BoxedUnit boxedUnit10 = BoxedUnit.UNIT;
            });
            Try$.MODULE$.apply(() -> {
                Some someRegexMatch2 = ExceptionUtils$.MODULE$.regexMatch(ex, StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("An error occurred opening the source: (.+)\\.$")));
                if (someRegexMatch2 instanceof Some) {
                    Regex.Match regex2 = (Regex.Match) someRegexMatch2.value();
                    if (regex2.groupCount() == 1) {
                        String filename4 = FilenameUtils.getName(regex2.group(1));
                        throw new NonLocalReturnControl(obj, MODULE$.failureKnownReason(new StringBuilder(15).append("Could not open ").append(filename4).toString(), ex, MODULE$.failureKnownReason$default$3(), MODULE$.failureKnownReason$default$4()));
                    }
                }
                return None$.MODULE$;
            });
            return failure(ex);
        } catch (NonLocalReturnControl ex2) {
            if (ex2.key() == obj) {
                return (TaskResult) ex2.value();
            }
            throw ex2;
        }
    }

    public static final /* synthetic */ boolean $anonfun$_knownFailureReasons$1(final String x$8) {
        return x$8.contains("Maximum supported image dimension is 65500 pixels");
    }
}

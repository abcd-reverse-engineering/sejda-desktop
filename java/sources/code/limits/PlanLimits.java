package code.limits;

import code.sejda.tasks.doc.PdfToOfficeWordParameters;
import code.sejda.tasks.excel.PdfToExcelNextParameters;
import code.sejda.tasks.ocr.OcrParameters;
import code.sejda.tasks.rename.RenameByTextParameters;
import code.util.ImplicitJavaConversions$;
import code.util.pdf.PdfDetails$;
import java.io.File;
import org.sejda.model.input.ImageMergeInput;
import org.sejda.model.input.PdfMergeInput;
import org.sejda.model.input.TaskSource;
import org.sejda.model.parameter.AlternateMixMultipleInputParameters;
import org.sejda.model.parameter.BaseMergeParameters;
import org.sejda.model.parameter.MergeParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceTaskParameters;
import org.sejda.model.parameter.base.MultipleSourceParameters;
import org.sejda.model.parameter.base.SinglePdfSourceTaskParameters;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters;
import org.sejda.model.pro.parameter.CombineReorderParameters;
import org.sejda.model.pro.parameter.JpegToPdfParameters;
import org.sejda.model.pro.parameter.OptimizeParameters;
import org.sejda.model.pro.parameter.SplitByTextContentParameters;
import org.sejda.model.pro.parameter.SplitDownTheMiddleParameters;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.$colon;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Buffer;
import scala.math.Numeric$IntIsIntegral$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

/* compiled from: PlanLimits.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0005=baB\b\u0011!\u0003\r\t!\u0006\u0005\u00069\u0001!\t!\b\u0005\bC\u0001\u0011\r\u0011\"\u0001#\u0011\u001d1\u0003A1A\u0005\u0002\u001dBQa\u000b\u0001\u0005\u00121BQ\u0001\u0011\u0001\u0005\u0012\u0005CQ\u0001\u0013\u0001\u0005\u0012%CQ\u0001\u0014\u0001\u0005\u00125CQa\u0014\u0001\u0005\u0012ACQA\u0015\u0001\u0005\u0012MCQa\u0019\u0001\u0005\u0012\u0011DQA\u001a\u0001\u0005\u0012\u001dDQA\u001f\u0001\u0005\u0012mDq!!\t\u0001\t\u0003\t\u0019\u0003C\u0004\u0002*\u0001!\t!a\u000b\u0003\u0015Ac\u0017M\u001c'j[&$8O\u0003\u0002\u0012%\u00051A.[7jiNT\u0011aE\u0001\u0005G>$Wm\u0001\u0001\u0014\u0005\u00011\u0002CA\f\u001b\u001b\u0005A\"\"A\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005mA\"AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u0002=A\u0011qcH\u0005\u0003Aa\u0011A!\u00168ji\u0006\u0011QJQ\u000b\u0002GA\u0011q\u0003J\u0005\u0003Ka\u00111!\u00138u\u0003iIej\u0011*F\u0003N+u\fR+F?R{u,\u0012(D%f\u0003F+S(O+\u0005A\u0003CA\f*\u0013\tQ\u0003D\u0001\u0004E_V\u0014G.Z\u0001$Q\u0006\u001ch)\u001b7f\u0007>,h\u000e\u001e'be\u001e,'\u000f\u00165b]\u001a\u0013X-Z+tKJ\fVo\u001c;b)\ti\u0003\u0007\u0005\u0002\u0018]%\u0011q\u0006\u0007\u0002\b\u0005>|G.Z1o\u0011\u0015\tD\u00011\u00013\u0003\u0019\u0001\u0018M]1ngB\u00111GP\u0007\u0002i)\u0011QGN\u0001\u0005E\u0006\u001cXM\u0003\u00028q\u0005I\u0001/\u0019:b[\u0016$XM\u001d\u0006\u0003si\nQ!\\8eK2T!a\u000f\u001f\u0002\u000bM,'\u000eZ1\u000b\u0003u\n1a\u001c:h\u0013\tyDG\u0001\bUCN\\\u0007+\u0019:b[\u0016$XM]:\u0002=!\f7OR5mK2\u000b'oZ3s)\"\fgN\u0012:fKV\u001bXM])v_R\fGcA\u0017C\u0007\")\u0011'\u0002a\u0001e!)A)\u0002a\u0001\u000b\u0006\u0011R.\u0019=TSj,gi\u001c:Ge\u0016,\u0017J\\'c!\t9b)\u0003\u0002H1\t!Aj\u001c8h\u0003\rB\u0017m]%nC\u001e,g)\u001b7f\u0019\u0006\u0014x-\u001a:UQ\u0006tgI]3f+N,'/U;pi\u0006$2!\f&L\u0011\u0015\td\u00011\u00013\u0011\u0015!e\u00011\u0001F\u0003eA\u0017m\u001d$jY\u0016<\u0016\u000e\u001e5MCJ<W\rU1hK\u000e{WO\u001c;\u0015\u00055r\u0005\"B\u0019\b\u0001\u0004\u0011\u0014\u0001D5t\u0005\u0006$8\r\u001b$jY\u0016\u001cHCA\u0017R\u0011\u0015\t\u0004\u00021\u00013\u0003\u0019J7\u000fT1sO\u0016\u0004\u0016mZ3D_VtGo\u0018+bg.\u001c\u0006/Z2jM&\u001cwL\u00117pG.Lgn\u001a\u000b\u0003)\n\u00042aF+X\u0013\t1\u0006D\u0001\u0004PaRLwN\u001c\t\u00031~s!!W/\u0011\u0005iCR\"A.\u000b\u0005q#\u0012A\u0002\u001fs_>$h(\u0003\u0002_1\u00051\u0001K]3eK\u001aL!\u0001Y1\u0003\rM#(/\u001b8h\u0015\tq\u0006\u0004C\u00032\u0013\u0001\u0007!'A\u000fjg2\u000b'oZ3QC\u001e,7i\\;oi~#\u0016m]6Ta\u0016\u001c\u0017NZ5d)\t!V\rC\u00032\u0015\u0001\u0007!'\u0001\u0006j]B,HOR5mKN$\"\u0001[=\u0011\u0007%t\u0017O\u0004\u0002kY:\u0011!l[\u0005\u00023%\u0011Q\u000eG\u0001\ba\u0006\u001c7.Y4f\u0013\ty\u0007OA\u0002TKFT!!\u001c\r\u0011\u0005I<X\"A:\u000b\u0005Q,\u0018AA5p\u0015\u00051\u0018\u0001\u00026bm\u0006L!\u0001_:\u0003\t\u0019KG.\u001a\u0005\u0006c-\u0001\rAM\u0001\bg>,(oY3t)\ra\u0018q\u0004\t\u0004S:l\bg\u0001@\u0002\u000eA)q0!\u0002\u0002\n5\u0011\u0011\u0011\u0001\u0006\u0004\u0003\u0007A\u0014!B5oaV$\u0018\u0002BA\u0004\u0003\u0003\u0011!\u0002V1tWN{WO]2f!\u0011\tY!!\u0004\r\u0001\u0011Y\u0011q\u0002\u0007\u0002\u0002\u0003\u0005)\u0011AA\t\u0005\ryF%M\t\u0005\u0003'\tI\u0002E\u0002\u0018\u0003+I1!a\u0006\u0019\u0005\u001dqu\u000e\u001e5j]\u001e\u00042aFA\u000e\u0013\r\ti\u0002\u0007\u0002\u0004\u0003:L\b\"B\u0019\r\u0001\u0004\u0011\u0014A\u00039bO\u0016\u001cu.\u001e8ugR!\u0011QEA\u0014!\rIgn\t\u0005\u0006c5\u0001\rAM\u0001!SNd\u0015M]4f\r&dWmQ8v]R|fi\u001c:SK:\fW.\u001a\"z)\u0016DH\u000fF\u0002.\u0003[AQ!\r\bA\u0002I\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/limits/PlanLimits.class */
public interface PlanLimits {
    void code$limits$PlanLimits$_setter_$MB_$eq(final int x$1);

    void code$limits$PlanLimits$_setter_$INCREASE_DUE_TO_ENCRYPTION_$eq(final double x$1);

    int MB();

    double INCREASE_DUE_TO_ENCRYPTION();

    static void $init$(final PlanLimits $this) {
        $this.code$limits$PlanLimits$_setter_$MB_$eq(1048576);
        $this.code$limits$PlanLimits$_setter_$INCREASE_DUE_TO_ENCRYPTION_$eq(1.35d);
    }

    default boolean hasFileCountLargerThanFreeUserQuota(final TaskParameters params) {
        return sources(params).size() > 30;
    }

    default boolean hasFileLargerThanFreeUserQuota(final TaskParameters params, final long maxSizeForFreeInMb) {
        return params instanceof AbstractPdfToMultipleImageParameters ? inputFiles(params).exists(x$1 -> {
            return BoxesRunTime.boxToBoolean($anonfun$hasFileLargerThanFreeUserQuota$1(this, x$1));
        }) : params instanceof OptimizeParameters ? inputFiles(params).exists(x$2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$hasFileLargerThanFreeUserQuota$2(this, x$2));
        }) : inputFiles(params).exists(x$3 -> {
            return BoxesRunTime.boxToBoolean($anonfun$hasFileLargerThanFreeUserQuota$3(this, maxSizeForFreeInMb, x$3));
        });
    }

    static /* synthetic */ boolean $anonfun$hasFileLargerThanFreeUserQuota$1(final PlanLimits $this, final File x$1) {
        return ((double) x$1.length()) > ((double) (100 * $this.MB())) * $this.INCREASE_DUE_TO_ENCRYPTION();
    }

    static /* synthetic */ boolean $anonfun$hasFileLargerThanFreeUserQuota$2(final PlanLimits $this, final File x$2) {
        return ((double) x$2.length()) > ((double) (100 * $this.MB())) * $this.INCREASE_DUE_TO_ENCRYPTION();
    }

    static /* synthetic */ boolean $anonfun$hasFileLargerThanFreeUserQuota$3(final PlanLimits $this, final long maxSizeForFreeInMb$1, final File x$3) {
        return ((double) x$3.length()) > ((double) (maxSizeForFreeInMb$1 * ((long) $this.MB()))) * $this.INCREASE_DUE_TO_ENCRYPTION();
    }

    default boolean hasImageFileLargerThanFreeUserQuota(final TaskParameters params, final long maxSizeForFreeInMb) {
        if (params instanceof JpegToPdfParameters) {
            return inputFiles(params).exists(x$4 -> {
                return BoxesRunTime.boxToBoolean($anonfun$hasImageFileLargerThanFreeUserQuota$1(this, maxSizeForFreeInMb, x$4));
            });
        }
        return false;
    }

    static /* synthetic */ boolean $anonfun$hasImageFileLargerThanFreeUserQuota$1(final PlanLimits $this, final long maxSizeForFreeInMb$2, final File x$4) {
        return ((double) x$4.length()) > ((double) (maxSizeForFreeInMb$2 * ((long) $this.MB()))) * $this.INCREASE_DUE_TO_ENCRYPTION();
    }

    default boolean hasFileWithLargePageCount(final TaskParameters params) {
        if (params instanceof SplitDownTheMiddleParameters) {
            return pageCounts(params).exists(x$5 -> {
                return x$5 > 100;
            });
        }
        if (params instanceof AlternateMixMultipleInputParameters) {
            return BoxesRunTime.unboxToInt(pageCounts(params).sum(Numeric$IntIsIntegral$.MODULE$)) > 200;
        }
        if (params instanceof AbstractPdfToMultipleImageParameters) {
            return false;
        }
        return pageCounts(params).exists(x$6 -> {
            return x$6 > 200;
        });
    }

    default boolean isBatchFiles(final TaskParameters params) {
        return ((params instanceof BaseMergeParameters) || (params instanceof RenameByTextParameters) || (params instanceof JpegToPdfParameters) || sources(params).size() <= 1) ? false : true;
    }

    default Option<String> isLargePageCount_TaskSpecific_Blocking(final TaskParameters params) {
        return ((params instanceof OcrParameters) && pageCounts(params).exists(x$7 -> {
            return x$7 > 10;
        })) ? new Some("page-count-per-ocr") : ((params instanceof PdfToOfficeWordParameters) && pageCounts(params).exists(x$8 -> {
            return x$8 > 50;
        })) ? new Some("page-count-per-pdf-to-word") : None$.MODULE$;
    }

    default Option<String> isLargePageCount_TaskSpecific(final TaskParameters params) {
        return ((params instanceof OcrParameters) && pageCounts(params).exists(x$9 -> {
            return x$9 > 10;
        })) ? new Some("page-count-per-ocr") : ((params instanceof AbstractPdfToMultipleImageParameters) && pageCounts(params).exists(x$10 -> {
            return x$10 > 20;
        })) ? new Some("page-count-per-pdf-to-jpg") : (!(params instanceof CombineReorderParameters) || ((CombineReorderParameters) params).getPages().size() < 50) ? (!(params instanceof MergeParameters) || BoxesRunTime.unboxToInt(pageCounts(params).sum(Numeric$IntIsIntegral$.MODULE$)) <= 50) ? ((params instanceof SplitByTextContentParameters) && pageCounts(params).exists(x$11 -> {
            return x$11 > 10;
        })) ? new Some("page-count-per-split-by-text") : ((params instanceof PdfToExcelNextParameters) && pageCounts(params).exists(x$12 -> {
            return x$12 > 10;
        })) ? new Some("page-count-per-pdf-to-excel") : ((params instanceof PdfToOfficeWordParameters) && pageCounts(params).exists(x$13 -> {
            return x$13 > 50;
        })) ? new Some("page-count-per-pdf-to-word") : None$.MODULE$ : new Some("page-count-per-merge") : new Some("page-count-per-visual-merge");
    }

    default Seq<File> inputFiles(final TaskParameters params) {
        return (Seq) ((IterableOps) sources(params).map(x$14 -> {
            return x$14.getSource();
        })).map(x0$1 -> {
            if (x0$1 instanceof File) {
                return (File) x0$1;
            }
            throw new RuntimeException(new StringBuilder(24).append("Unexpected source type: ").append(x0$1).toString());
        });
    }

    default Seq<TaskSource<?>> sources(final TaskParameters params) {
        if (params instanceof MultiplePdfSourceTaskParameters) {
            return ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq((Buffer) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(((MultiplePdfSourceTaskParameters) params).getSourceList()).asScala()).map(x$15 -> {
                return x$15;
            }));
        }
        if (params instanceof SinglePdfSourceTaskParameters) {
            return (Seq) new $colon.colon(((SinglePdfSourceTaskParameters) params).getSource(), Nil$.MODULE$).map(x$16 -> {
                return x$16;
            });
        }
        if (params instanceof MultipleSourceParameters) {
            return ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq((Buffer) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(((MultipleSourceParameters) params).getSourceList()).asScala()).map(x$17 -> {
                return x$17;
            }));
        }
        if (params instanceof BaseMergeParameters) {
            return ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq((Buffer) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(((BaseMergeParameters) params).getInputList()).asScala()).map(x0$1 -> {
                if (x0$1 instanceof PdfMergeInput) {
                    return ((PdfMergeInput) x0$1).getSource();
                }
                if (x0$1 instanceof ImageMergeInput) {
                    return ((ImageMergeInput) x0$1).getSource();
                }
                throw new MatchError(x0$1);
            }));
        }
        throw new RuntimeException(new StringBuilder(19).append("Unexpected params: ").append(params).toString());
    }

    default Seq<Object> pageCounts(final TaskParameters params) {
        return (Seq) ((IterableOps) sources(params).flatMap(source -> {
            return PdfDetails$.MODULE$.parseCached(source);
        })).map(x$18 -> {
            return BoxesRunTime.boxToInteger(x$18.pages());
        });
    }

    default boolean isLargeFileCount_ForRenameByText(final TaskParameters params) {
        return (params instanceof RenameByTextParameters) && ((RenameByTextParameters) params).getSourceList().size() > 5;
    }
}

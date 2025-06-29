package code.model;

import code.sejda.tasks.annotation.AnnotationType;
import code.sejda.tasks.annotation.AnnotationType$;
import code.sejda.tasks.annotation.DeleteAnnotationsParameters;
import code.sejda.tasks.bookmark.AddedBookmark;
import code.sejda.tasks.bookmark.DeletedBookmark;
import code.sejda.tasks.bookmark.EditBookmarksParameters;
import code.sejda.tasks.bookmark.UpdatedBookmark;
import code.sejda.tasks.deskew.DeskewParameters;
import code.sejda.tasks.doc.DocToPdfParameters;
import code.sejda.tasks.doc.PdfToDocParameters;
import code.sejda.tasks.doc.PdfToOfficeWordParameters;
import code.sejda.tasks.edit.AddImageOperation;
import code.sejda.tasks.edit.AddShapeOperation;
import code.sejda.tasks.edit.AppendAttachmentOperation;
import code.sejda.tasks.edit.AppendDrawOperation;
import code.sejda.tasks.edit.AppendEmbeddedFileOperation;
import code.sejda.tasks.edit.AppendFormFieldOperation;
import code.sejda.tasks.edit.AppendFormFieldOperation$;
import code.sejda.tasks.edit.AppendLineOperation;
import code.sejda.tasks.edit.AppendLinkOperation;
import code.sejda.tasks.edit.AppendTextOperation;
import code.sejda.tasks.edit.DeleteAttachmentOperation;
import code.sejda.tasks.edit.DeleteEmbeddedFileOperation;
import code.sejda.tasks.edit.DeleteFormFieldOperation;
import code.sejda.tasks.edit.DeleteHighlightOperation;
import code.sejda.tasks.edit.DeleteImageOperation;
import code.sejda.tasks.edit.DeleteLinkOperation;
import code.sejda.tasks.edit.DeletePageOperation;
import code.sejda.tasks.edit.EditHighlightOperation;
import code.sejda.tasks.edit.EditLinkOperation;
import code.sejda.tasks.edit.EditParameters;
import code.sejda.tasks.edit.EditTextOperation;
import code.sejda.tasks.edit.FillFormOperation;
import code.sejda.tasks.edit.FormFieldAlign;
import code.sejda.tasks.edit.FormFieldAlign$;
import code.sejda.tasks.edit.FormFieldAlign$Left$;
import code.sejda.tasks.edit.FormFieldType;
import code.sejda.tasks.edit.FormFieldType$;
import code.sejda.tasks.edit.HighlightTextOperation;
import code.sejda.tasks.edit.HighlightType;
import code.sejda.tasks.edit.HighlightType$;
import code.sejda.tasks.edit.HighlightType$Highlight$;
import code.sejda.tasks.edit.HighlightType$Strikethrough$;
import code.sejda.tasks.edit.InsertPageOperation;
import code.sejda.tasks.edit.LinkType;
import code.sejda.tasks.edit.LinkType$;
import code.sejda.tasks.edit.RotatePageOperation;
import code.sejda.tasks.edit.SystemFont;
import code.sejda.tasks.excel.PdfToExcelNextParameters;
import code.sejda.tasks.excel.PdfToOfficeExcelParameters;
import code.sejda.tasks.flatten.FlattenMode;
import code.sejda.tasks.flatten.FlattenMode$;
import code.sejda.tasks.flatten.FlattenParameters;
import code.sejda.tasks.html.HtmlToPdfParameters;
import code.sejda.tasks.image.ExtractImagesParameters;
import code.sejda.tasks.ocr.OcrParameters;
import code.sejda.tasks.ocr.OutputFormat;
import code.sejda.tasks.ocr.OutputFormat$Pdf$;
import code.sejda.tasks.ppt.PdfToPptParameters;
import code.sejda.tasks.rename.RenameArea;
import code.sejda.tasks.rename.RenameByTextParameters;
import code.util.JsonExtract$;
import code.util.Loggable;
import code.util.StringHelpers$;
import code.util.pdf.PageRanges$;
import code.util.pdf.PdfTempFileTaskOutput;
import code.util.pdf.TempDirTaskOutput;
import com.google.common.io.BaseEncoding;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.util.HashMap;
import java.util.UUID;
import net.liftweb.json.JsonAST;
import org.apache.commons.io.FilenameUtils;
import org.sejda.model.HorizontalAlign;
import org.sejda.model.PageOrientation;
import org.sejda.model.PageSize;
import org.sejda.model.RectangularBox;
import org.sejda.model.SejdaFileExtensions;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.model.VerticalAlign;
import org.sejda.model.input.ImageMergeInput;
import org.sejda.model.input.PdfMergeInput;
import org.sejda.model.input.PdfMixInput;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.Source;
import org.sejda.model.input.TaskSource;
import org.sejda.model.outline.OutlinePolicy;
import org.sejda.model.output.ExistingOutputPolicy;
import org.sejda.model.parameter.AlternateMixMultipleInputParameters;
import org.sejda.model.parameter.ExtractByOutlineParameters;
import org.sejda.model.parameter.ExtractPagesParameters;
import org.sejda.model.parameter.MergeParameters;
import org.sejda.model.parameter.RotateParameters;
import org.sejda.model.parameter.SetMetadataParameters;
import org.sejda.model.parameter.SimpleSplitParameters;
import org.sejda.model.parameter.SplitByEveryXPagesParameters;
import org.sejda.model.parameter.SplitByPagesParameters;
import org.sejda.model.parameter.SplitBySizeParameters;
import org.sejda.model.parameter.base.AbstractPdfOutputParameters;
import org.sejda.model.parameter.base.MultipleOutputTaskParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceParameters;
import org.sejda.model.parameter.base.MultiplePdfSourceTaskParameters;
import org.sejda.model.parameter.base.MultipleSourceParameters;
import org.sejda.model.parameter.base.SingleOrMultipleOutputTaskParameters;
import org.sejda.model.parameter.base.SingleOutputTaskParameters;
import org.sejda.model.parameter.base.SinglePdfSourceTaskParameters;
import org.sejda.model.parameter.base.TaskParameters;
import org.sejda.model.parameter.edit.Shape;
import org.sejda.model.parameter.image.AbstractPdfToMultipleImageParameters;
import org.sejda.model.parameter.image.PdfToJpegParameters;
import org.sejda.model.parameter.image.PdfToSingleTiffParameters;
import org.sejda.model.pdf.StandardType1Font;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.pdf.form.AcroFormPolicy;
import org.sejda.model.pdf.headerfooter.NumberingStyle;
import org.sejda.model.pdf.page.PageRange;
import org.sejda.model.pdf.page.PredefinedSetOfPages;
import org.sejda.model.pro.nup.PageOrder;
import org.sejda.model.pro.optimization.Optimization;
import org.sejda.model.pro.parameter.AutoCropMode;
import org.sejda.model.pro.parameter.CombineReorderParameters;
import org.sejda.model.pro.parameter.ConvertToGrayscaleParameters;
import org.sejda.model.pro.parameter.CropParameters;
import org.sejda.model.pro.parameter.DecryptParameters;
import org.sejda.model.pro.parameter.EncryptParameters;
import org.sejda.model.pro.parameter.ExtractTextParameters;
import org.sejda.model.pro.parameter.JpegToPdfParameters;
import org.sejda.model.pro.parameter.NupParameters;
import org.sejda.model.pro.parameter.OptimizeParameters;
import org.sejda.model.pro.parameter.RepairParameters;
import org.sejda.model.pro.parameter.ResizePagesParameters;
import org.sejda.model.pro.parameter.SetHeaderFooterParameters;
import org.sejda.model.pro.parameter.SplitByTextContentParameters;
import org.sejda.model.pro.parameter.SplitDownTheMiddleParameters;
import org.sejda.model.pro.parameter.WatermarkParameters;
import org.sejda.model.pro.pdf.encryption.PdfEncryption;
import org.sejda.model.pro.pdf.numbering.BatesSequence;
import org.sejda.model.pro.split.SplitDownTheMiddleMode;
import org.sejda.model.pro.watermark.Location;
import org.sejda.model.pro.watermark.Watermark;
import org.sejda.model.repaginate.Repagination;
import org.sejda.model.rotation.Rotation;
import org.sejda.model.scale.Margins;
import org.sejda.model.toc.ToCPolicy;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.pdmodel.interactive.action.PDWindowsLaunchParams;
import org.slf4j.Logger;
import scala.$less$colon$less$;
import scala.Array$;
import scala.Function0;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.collection.ArrayOps$;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.MapOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.mutable.ListBuffer;
import scala.math.BigInt;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.util.Try$;
import scala.util.control.NonFatal$;

/* compiled from: TaskJsonParser.scala */
@ScalaSignature(bytes = "\u0006\u00051EaaBA{\u0003o\u0004!\u0011\u0001\u0005\u000b\u00057\u0001!\u0011!Q\u0001\n\tu\u0001b\u0002B\u0013\u0001\u0011\u0005!q\u0005\u0005\b\u0005[\u0001A\u0011\u0001B\u0018\u0011\u001d\u0011I\b\u0001C\u0001\u0005wB\u0011B!.\u0001#\u0003%\tAa.\t\u000f\t5\u0007\u0001\"\u0001\u0003P\"I!Q\u001c\u0001\u0012\u0002\u0013\u0005!q\u0017\u0005\b\u0005?\u0004A\u0011\u0001Bq\u0011%\u0011y\u000fAI\u0001\n\u0003\u00119\fC\u0004\u0003r\u0002!\tAa=\t\u0013\rE\u0001!%A\u0005\u0002\t]\u0006bBB\n\u0001\u0011\u00051Q\u0003\u0005\b\u0007'\u0001A\u0011AB\u0019\u0011%\u0019I\u0005AI\u0001\n\u0003\u00119\fC\u0005\u0004L\u0001\t\n\u0011\"\u0001\u0004N!911\u0003\u0001\u0005\u0002\rE\u0003bBB2\u0001\u0011\u00051Q\r\u0005\b\u0007c\u0002A\u0011AB:\u0011\u001d\u0019\u0019\t\u0001C\u0001\u0007\u000bCqaa!\u0001\t\u0003\u0019I\nC\u0004\u0004,\u0002!\ta!,\t\u000f\r\r\u0005\u0001\"\u0001\u0004B\"91Q\u001c\u0001\u0005\u0002\r}\u0007bBBr\u0001\u0011%1Q\u001d\u0005\b\u0007W\u0004A\u0011ABw\u0011\u001d\u0019y\u0010\u0001C\u0001\t\u0003Aq\u0001\"\u0002\u0001\t\u0003!9\u0001C\u0004\u0005\u000e\u0001!\t\u0001b\u0004\t\u000f\u0011\r\u0002\u0001\"\u0003\u0005&!9A\u0011\u0007\u0001\u0005\u0002\u0011M\u0002b\u0002C%\u0001\u0011\u0005A1\n\u0005\b\t\u001f\u0002A\u0011\u0001C)\u0011\u001d!y\u0006\u0001C\u0001\tCBq\u0001b\u001a\u0001\t\u0003!I\u0007C\u0004\u0005n\u0001!\t\u0001b\u001c\t\u000f\u0011U\u0004\u0001\"\u0001\u0005x!9A1\u0010\u0001\u0005\u0002\u0011u\u0004b\u0002CA\u0001\u0011\u0005A1\u0011\u0005\b\t\u000f\u0003A\u0011\u0001CE\u0011\u001d!y\t\u0001C\u0001\t#Cq\u0001b&\u0001\t\u0003!I\nC\u0004\u0004H\u0001!\t\u0001\"(\t\u000f\u0011\u0005\u0006\u0001\"\u0001\u0005$\"9Aq\u0015\u0001\u0005\u0002\u0011%\u0006b\u0002C`\u0001\u0011\u0005A\u0011\u0019\u0005\b\t#\u0004A\u0011\u0001Cj\u0011\u001d!\u0019\u000f\u0001C\u0001\tKDq\u0001\">\u0001\t\u0003!9\u0010C\u0004\u0005|\u0002!\t\u0001\"@\t\u000f\u0015\u0005\u0001\u0001\"\u0001\u0006\u0004!9Qq\u0001\u0001\u0005\u0002\u0015%\u0001bBC\u0007\u0001\u0011\u0005Qq\u0002\u0005\b\u000b'\u0001A\u0011AC\u000b\u0011\u001d)I\u0002\u0001C\u0001\u000b7Aq!b\n\u0001\t\u0003)I\u0003C\u0004\u0006:\u0001!\t!b\u000f\t\u000f\u0015}\u0002\u0001\"\u0001\u0006B!9QQ\n\u0001\u0005\u0002\u0015=\u0003bBC*\u0001\u0011\u0005QQ\u000b\u0005\b\u000b3\u0002A\u0011AC.\u0011\u001d)y\u0006\u0001C\u0001\u000bCBq!\"\u001a\u0001\t\u0003)9\u0007C\u0004\u0006|\u0001!\t!\" \t\u000f\u0015\u001d\u0005\u0001\"\u0001\u0006\n\"9QQ\u0012\u0001\u0005\u0002\u0015=\u0005bBCQ\u0001\u0011\u0005Q1\u0015\u0005\b\u000bc\u0003A\u0011ACZ\u0011\u001d)y\f\u0001C\u0001\u000b\u0003Dq!\"2\u0001\t\u0003)9\rC\u0004\u0006L\u0002!\t!\"4\t\u000f\u0015e\u0007\u0001\"\u0001\u0006\\\"9Qq\u001c\u0001\u0005\u0002\u0015\u0005\bbBCs\u0001\u0011\u0005Qq\u001d\u0005\b\u000b_\u0004A\u0011ACy\u0011\u001d1\u0019\u0001\u0001C\u0001\r\u000bA\u0011B\"\u0003\u0001\u0005\u0004%\tAb\u0003\t\u0011\u0019M\u0001\u0001)A\u0005\r\u001bA\u0011B\"\u0006\u0001\u0005\u0004%\tAb\u0003\t\u0011\u0019]\u0001\u0001)A\u0005\r\u001bAqA\"\u0007\u0001\t\u00031Y\u0002C\u0004\u0007 \u0001!\tA\"\t\t\u000f\u0019\u0015\u0002\u0001\"\u0001\u0007(!9a1\u0007\u0001\u0005\u0002\u0019U\u0002b\u0002D\u001d\u0001\u0011\u0005a1\b\u0005\b\r\u0003\u0002A\u0011\u0001D\"\u0011\u001d1\u0019\u0006\u0001C\u0001\r+BqAb\u001a\u0001\t\u00031I\u0007C\u0004\u0007n\u0001!\tAb\u001c\t\u000f\u0019\u001d\u0005\u0001\"\u0001\u0007\n\"9aQ\u0013\u0001\u0005\u0002\u0019]\u0005b\u0002DR\u0001\u0011\u0005aQ\u0015\u0005\b\r_\u0003A\u0011\u0001DY\u0011\u001d19\f\u0001C\u0001\rsCqA\"0\u0001\t\u00031y\fC\u0004\u0007D\u0002!\tA\"2\t\u000f\u0019%\u0007\u0001\"\u0001\u0007L\"9aq\u001a\u0001\u0005\u0002\u0019E\u0007b\u0002Dk\u0001\u0011\u0005aq\u001b\u0005\b\r7\u0004A\u0011\u0001Do\u0011\u001d1I\u000f\u0001C\u0001\rWDqAb<\u0001\t\u00031\t\u0010C\u0004\u0007~\u0002!\tAb@\t\u000f\u001d\r\u0001\u0001\"\u0001\b\u0006!9qQ\u0005\u0001\u0005\u0002\u001d\u001d\u0002bBD\u001b\u0001\u0011\u0005qq\u0007\u0005\b\u000f\u0003\u0002A\u0011AD%\u0011\u001d9)\u0003\u0001C\u0001\u000f\u001bBqa\"\u0018\u0001\t\u00039y\u0006C\u0004\bj\u0001!\tab\u001b\t\u000f\u001d=\u0004\u0001\"\u0001\br!9q1\u0010\u0001\u0005\u0002\u001du\u0004bBDB\u0001\u0011\u0005qQ\u0011\u0005\b\u000f\u0013\u0003A\u0011ADF\u0011\u001d9y\t\u0001C\u0001\u000f#Cqa\"&\u0001\t\u000399\nC\u0004\b$\u0002!\ta\"*\t\u000f\u001d%\u0006\u0001\"\u0001\b,\"9qQ\u0017\u0001\u0005\u0002\u001d]\u0006bBD^\u0001\u0011\u0005qQ\u0018\u0005\b\u000f\u0003\u0004A\u0011ADb\u0011\u001d9i\r\u0001C\u0001\u000f\u001fDqab7\u0001\t\u00039i\u000eC\u0004\bb\u0002!\tab9\t\u000f\u001d=\b\u0001\"\u0001\br\"9qQ\u001f\u0001\u0005\u0002\u001d]\bb\u0002E\u0002\u0001\u0011\u0005\u0001R\u0001\u0005\b\u0011\u0013\u0001A\u0011\u0001E\u0006\u0011\u001dA9\u0002\u0001C\u0001\u00113Aq\u0001#\b\u0001\t\u0003Ay\u0002C\u0004\t,\u0001!\t\u0001#\f\t\u000f!E\u0002\u0001\"\u0001\t4!9\u0001r\b\u0001\u0005\u0002!\u0005\u0003b\u0002E#\u0001\u0011\u0005\u0001r\t\u0005\b\u0011\u0017\u0002A\u0011\u0001E'\u0011\u001dAI\u0006\u0001C\u0001\u00117Bq\u0001c\u0018\u0001\t\u0003A\t\u0007C\u0004\tn\u0001!\t\u0001c\u001c\t\u000f!M\u0004\u0001\"\u0001\tv!9\u0001\u0012\u0011\u0001\u0005\u0002!\r\u0005b\u0002ED\u0001\u0011\u0005\u0001\u0012\u0012\u0005\b\u0011+\u0003A\u0011\u0001EL\u0011\u001dAY\n\u0001C\u0001\u0011;Cq\u0001#+\u0001\t\u0003AY\u000bC\u0004\t0\u0002!\t\u0001#-\t\u000f!u\u0006\u0001\"\u0001\t@\"9\u00012\u0019\u0001\u0005\n!\u0015\u0007b\u0002Ee\u0001\u0011\u0005\u00012\u001a\u0005\b\u0011/\u0004A\u0011\u0002Em\u0011\u001dAy\u000e\u0001C\u0005\u0011CDq\u0001c;\u0001\t\u0003Ai\u000fC\u0004\tr\u0002!\t\u0001c=\t\u000f!}\b\u0001\"\u0001\n\u0002!9\u0011R\u0001\u0001\u0005\u0002%\u001d\u0001bBE\u0007\u0001\u0011\u0005\u0011r\u0002\u0005\b\u00133\u0001A\u0011AE\u000e\u0011\u001dI9\u0003\u0001C\u0001\u0013SAq!#\f\u0001\t\u0003Iy\u0003C\u0004\n@\u0001!\t!#\u0011\t\u000f%\u001d\u0003\u0001\"\u0001\nJ!9\u0011r\n\u0001\u0005\u0002%E\u0003bBE/\u0001\u0011\u0005\u0011r\f\u0005\b\u0013G\u0002A\u0011AE3\u0011\u001dII\u0007\u0001C\u0005\u0013WBq!c\u001c\u0001\t\u0013I\t\bC\u0004\nv\u0001!I!c\u001e\t\u000f%\u0015\u0005\u0001\"\u0003\n\b\"9\u00112\u0012\u0001\u0005\n%5\u0005bBEI\u0001\u0011\u0005\u00112\u0013\u0005\b\u0013/\u0003A\u0011AEM\u0011\u001dIi\n\u0001C\u0001\u0013?Cq!c)\u0001\t\u0003I)\u000bC\u0004\n*\u0002!\t!c+\t\u000f%=\u0006\u0001\"\u0001\n2\"9\u0011R\u0017\u0001\u0005\u0002%]\u0006bBE^\u0001\u0011\u0005\u0011R\u0018\u0005\b\u0013\u0003\u0004A\u0011AEb\u0011\u001dI\u0019\u000e\u0001C\u0001\u0013+Dq!#7\u0001\t\u0003IY\u000eC\u0004\n`\u0002!\t!#9\t\u000f%E\b\u0001\"\u0001\nt\"9\u0011r\u001f\u0001\u0005\u0002%e\bbBE\u007f\u0001\u0011\u0005\u0011r \u0005\b\u0015\u001f\u0001A\u0011\u0001F\t\u0011\u001dQ9\u0002\u0001C\u0001\u00153AqA#\b\u0001\t\u0003Qy\u0002C\u0004\u000b$\u0001!\tA#\n\t\u000f)%\u0002\u0001\"\u0001\u000b,!91q\u001f\u0001\u0005\u0002)=\u0002b\u0002F\u001a\u0001\u0011\u0005!R\u0007\u0005\b\u0015w\u0001A\u0011\u0001F\u001f\u0011\u001dQ\t\u0005\u0001C\u0001\u0015\u0007BqAc\u0012\u0001\t\u0003QI\u0005C\u0004\u000bN\u0001!\tAc\u0014\t\u000f)M\u0003\u0001\"\u0001\u000bV!9!\u0012\f\u0001\u0005\u0002)m\u0003b\u0002F0\u0001\u0011\u0005!\u0012\r\u0005\b\u0015g\u0002A\u0011\u0002F;\u0011\u001dQY\b\u0001C\u0001\u0015{BqA##\u0001\t\u0003QY\tC\u0004\u000b\u0016\u0002!\tAc&\t\u000f)m\u0005\u0001\"\u0001\u000b\u001e\"9!\u0012\u0015\u0001\u0005\u0002)\r\u0006b\u0002FT\u0001\u0011\u0005!\u0012\u0016\u0005\b\u0015[\u0003A\u0011\u0001FX\u0011\u001dQ\u0019\f\u0001C\u0001\u0015kCqA#/\u0001\t\u0003QY\fC\u0004\u000b@\u0002!\tA#1\t\u000f)\u0015\u0007\u0001\"\u0001\u000bH\"9!2\u001a\u0001\u0005\u0002)5\u0007b\u0002Fl\u0001\u0011\u0005!\u0012\u001c\u0005\b\u0015;\u0004A\u0011\u0001Fp\u0011\u001dQ\u0019\u000f\u0001C\u0001\u0015KDqAc;\u0001\t\u0003Qi\u000fC\u0004\u000br\u0002!\tAc=\t\u000f)]\b\u0001\"\u0001\u000bz\"9!R \u0001\u0005\u0002)}\bbBF\u0002\u0001\u0011\u00051R\u0001\u0005\b\u0017\u0013\u0001A\u0011AF\u0006\u0011\u001dYy\u0001\u0001C\u0001\u0017#Aqa#\u0006\u0001\t\u0003Y9\u0002C\u0004\f\u001c\u0001!\ta#\b\t\u000f-\u0005\u0002\u0001\"\u0001\f$!91r\u0005\u0001\u0005\u0002-%\u0002bBF\u0017\u0001\u0011\u00051r\u0006\u0005\b\u0017g\u0001A\u0011AF\u001b\u0011\u001dYI\u0004\u0001C\u0001\u0017wAqa!.\u0001\t\u0003Yi\u0005C\u0004\fR\u0001!\tac\u0015\t\u000f-]\u0003\u0001\"\u0001\fZ!91\u0012\u000e\u0001\u0005\u0002--\u0004bBF>\u0001\u0011\u00051R\u0010\u0005\b\u0017\u0003\u0003A\u0011AFB\u0011\u001dY9\t\u0001C\u0001\u0017\u0013Cqa#$\u0001\t\u0003Yy\tC\u0004\f\u0014\u0002!\ta#&\t\u000f-\u001d\u0006\u0001\"\u0001\f*\"91R\u0016\u0001\u0005\u0002-=\u0006bBFZ\u0001\u0011\u00051R\u0017\u0005\b\u0017s\u0003A\u0011AF^\u0011\u001dYy\f\u0001C\u0001\u0017\u0003Dqac2\u0001\t\u0003YI\rC\u0004\f\\\u0002!\ta#8\t\u000f-%\b\u0001\"\u0001\fl\"91r\u001e\u0001\u0005\u0002-E\bbBF\u007f\u0001\u0011\u00051r \u0005\b\u0019\u0007\u0001A\u0011\u0001G\u0003\u000f!aI!a>\t\u00021-a\u0001CA{\u0003oD\t\u0001$\u0004\t\u0011\t\u0015\u0012\u0011\u001fC\u0001\u0019\u001f\u0011a\u0002V1tW*\u001bxN\u001c)beN,'O\u0003\u0003\u0002z\u0006m\u0018!B7pI\u0016d'BAA\u007f\u0003\u0011\u0019w\u000eZ3\u0004\u0001M)\u0001Aa\u0001\u0003\u0010A!!Q\u0001B\u0006\u001b\t\u00119A\u0003\u0002\u0003\n\u0005)1oY1mC&!!Q\u0002B\u0004\u0005\u0019\te.\u001f*fMB!!\u0011\u0003B\f\u001b\t\u0011\u0019B\u0003\u0003\u0003\u0016\u0005m\u0018\u0001B;uS2LAA!\u0007\u0003\u0014\tAAj\\4hC\ndW-\u0001\u0002ggB!!q\u0004B\u0011\u001b\t\t90\u0003\u0003\u0003$\u0005](\u0001\u0004$jY\u0016\u0004&o\u001c<jI\u0016\u0014\u0018A\u0002\u001fj]&$h\b\u0006\u0003\u0003*\t-\u0002c\u0001B\u0010\u0001!9!1\u0004\u0002A\u0002\tu\u0011\u0001\u00034s_6T5o\u001c8\u0015\t\tE\"1\n\t\u0005\u0005g\u00119%\u0004\u0002\u00036)!!q\u0007B\u001d\u0003\u0011\u0011\u0017m]3\u000b\t\tm\"QH\u0001\na\u0006\u0014\u0018-\\3uKJTA!!?\u0003@)!!\u0011\tB\"\u0003\u0015\u0019XM\u001b3b\u0015\t\u0011)%A\u0002pe\u001eLAA!\u0013\u00036\tqA+Y:l!\u0006\u0014\u0018-\\3uKJ\u001c\bb\u0002B'\u0007\u0001\u000f!qJ\u0001\u0005UN|g\u000e\u0005\u0003\u0003R\tMd\u0002\u0002B*\u0005[rAA!\u0016\u0003j9!!q\u000bB2\u001d\u0011\u0011IFa\u0018\u000e\u0005\tm#\u0002\u0002B/\u0003\u007f\fa\u0001\u0010:p_Rt\u0014B\u0001B1\u0003\rqW\r^\u0005\u0005\u0005K\u00129'A\u0004mS\u001a$x/\u001a2\u000b\u0005\t\u0005\u0014\u0002\u0002B'\u0005WRAA!\u001a\u0003h%!!q\u000eB9\u0003\u001d\u0001\u0018mY6bO\u0016TAA!\u0014\u0003l%!!Q\u000fB<\u0005\u0019Qe+\u00197vK*!!q\u000eB9\u00039\u0001HM\u001a$jY\u0016\u001cv.\u001e:dKN$bA! \u0003*\n-\u0006C\u0002B@\u0005\u0013\u0013i)\u0004\u0002\u0003\u0002*!!1\u0011BC\u0003%IW.\\;uC\ndWM\u0003\u0003\u0003\b\n\u001d\u0011AC2pY2,7\r^5p]&!!1\u0012BA\u0005\u0011a\u0015n\u001d;\u0011\r\t=%Q\u0013BM\u001b\t\u0011\tJ\u0003\u0003\u0003\u0014\nu\u0012!B5oaV$\u0018\u0002\u0002BL\u0005#\u0013\u0011\u0002\u00153g'>,(oY3\u0011\t\tm%QU\u0007\u0003\u0005;SAAa(\u0003\"\u0006\u0011\u0011n\u001c\u0006\u0003\u0005G\u000bAA[1wC&!!q\u0015BO\u0005\u00111\u0015\u000e\\3\t\u000f\t5C\u0001q\u0001\u0003P!I!Q\u0016\u0003\u0011\u0002\u0003\u000f!qV\u0001\u0007k:L\u0017/^3\u0011\t\t\u0015!\u0011W\u0005\u0005\u0005g\u00139AA\u0004C_>dW-\u00198\u00021A$gMR5mKN{WO]2fg\u0012\"WMZ1vYR$#'\u0006\u0002\u0003:*\"!q\u0016B^W\t\u0011i\f\u0005\u0003\u0003@\n%WB\u0001Ba\u0015\u0011\u0011\u0019M!2\u0002\u0013Ut7\r[3dW\u0016$'\u0002\u0002Bd\u0005\u000f\t!\"\u00198o_R\fG/[8o\u0013\u0011\u0011YM!1\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u000bqI\u001a|%/S7bO\u00164\u0015\u000e\\3T_V\u00148-Z:\u0015\r\tE'\u0011\u001cBn!\u0019\u0011yH!#\u0003TB1!q\u0012Bk\u00053KAAa6\u0003\u0012\nQA+Y:l'>,(oY3\t\u000f\t5c\u0001q\u0001\u0003P!I!Q\u0016\u0004\u0011\u0002\u0003\u000f!qV\u0001 a\u00124wJ]%nC\u001e,g)\u001b7f'>,(oY3tI\u0011,g-Y;mi\u0012\u0012\u0014a\u00034jY\u0016\u001cv.\u001e:dKN$bAa9\u0003l\n5\bC\u0002B@\u0005\u0013\u0013)\u000f\u0005\u0004\u0003\u0010\n\u001d(\u0011T\u0005\u0005\u0005S\u0014\tJ\u0001\u0004T_V\u00148-\u001a\u0005\b\u0005\u001bB\u00019\u0001B(\u0011%\u0011i\u000b\u0003I\u0001\u0002\b\u0011y+A\u000bgS2,7k\\;sG\u0016\u001cH\u0005Z3gCVdG\u000f\n\u001a\u0002+A$gMR5mKN{WO]2fg&sG-\u001a=fIR1!Q_B\u0007\u0007\u001f\u0001bAa \u0003\n\n]\b\u0003\u0003B\u0003\u0005s\u0014iP!$\n\t\tm(q\u0001\u0002\u0007)V\u0004H.\u001a\u001a\u0011\t\t}8q\u0001\b\u0005\u0007\u0003\u0019\u0019\u0001\u0005\u0003\u0003Z\t\u001d\u0011\u0002BB\u0003\u0005\u000f\ta\u0001\u0015:fI\u00164\u0017\u0002BB\u0005\u0007\u0017\u0011aa\u0015;sS:<'\u0002BB\u0003\u0005\u000fAqA!\u0014\u000b\u0001\b\u0011y\u0005C\u0005\u0003.*\u0001\n\u0011q\u0001\u00030\u0006y\u0002\u000f\u001a4GS2,7k\\;sG\u0016\u001c\u0018J\u001c3fq\u0016$G\u0005Z3gCVdG\u000f\n\u001a\u0002\u001f]LG\u000f[*u_J,GMR5mKN$Baa\u0006\u0004(Q!1\u0011DB\u000f)\u0011\u0011\tda\u0007\t\u000f\t5C\u0002q\u0001\u0003P!A1q\u0004\u0007\u0005\u0002\u0004\u0019\t#A\u0003cY>\u001c7\u000e\u0005\u0004\u0003\u0006\r\r\"\u0011G\u0005\u0005\u0007K\u00119A\u0001\u0005=Eft\u0017-\\3?\u0011\u001d\u0019I\u0003\u0004a\u0001\u0007W\t\u0011\u0001\u001d\t\u0005\u0005g\u0019i#\u0003\u0003\u00040\tU\"!H*j]\u001edW\r\u00153g'>,(oY3UCN\\\u0007+\u0019:b[\u0016$XM]:\u0015\u0011\rM21HB\"\u0007\u000b\"Ba!\u000e\u0004:Q!!\u0011GB\u001c\u0011\u001d\u0011i%\u0004a\u0002\u0005\u001fB\u0001ba\b\u000e\t\u0003\u00071\u0011\u0005\u0005\b\u0007Si\u0001\u0019AB\u001f!\u0011\u0011\u0019da\u0010\n\t\r\u0005#Q\u0007\u0002 \u001bVdG/\u001b9mKB#gmU8ve\u000e,G+Y:l!\u0006\u0014\u0018-\\3uKJ\u001c\b\"\u0003BW\u001bA\u0005\t\u0019\u0001BX\u0011%\u00199%\u0004I\u0001\u0002\u0004\u0011i0A\u0007po:,'\u000fU1tg^|'\u000fZ\u0001\u001ao&$\bn\u0015;pe\u0016$g)\u001b7fg\u0012\"WMZ1vYR$#'A\rxSRD7\u000b^8sK\u00124\u0015\u000e\\3tI\u0011,g-Y;mi\u0012\u001aTCAB(U\u0011\u0011iPa/\u0015\t\rM31\f\u000b\u0005\u0007+\u001aI\u0006\u0006\u0003\u00032\r]\u0003b\u0002B'!\u0001\u000f!q\n\u0005\t\u0007?\u0001B\u00111\u0001\u0004\"!91\u0011\u0006\tA\u0002\ru\u0003\u0003\u0002B\u001a\u0007?JAa!\u0019\u00036\tAR*\u001e7uSBdWmU8ve\u000e,\u0007+\u0019:b[\u0016$XM]:\u0002/]LG\u000f[(qi&|g.\u00197Ti>\u0014X\r\u001a$jY\u0016\u001cH\u0003BB4\u0007_\"Ba!\u001b\u0004nQ!!\u0011GB6\u0011\u001d\u0011i%\u0005a\u0002\u0005\u001fB\u0001ba\b\u0012\t\u0003\u00071\u0011\u0005\u0005\b\u0007S\t\u0002\u0019AB/\u00039aWM\\5f]RLeMQ1uG\"$Ba!\u001e\u0004|A!!QAB<\u0013\u0011\u0019IHa\u0002\u0003\tUs\u0017\u000e\u001e\u0005\b\u0007S\u0011\u0002\u0019AB?!\u0011\u0011\u0019da \n\t\r\u0005%Q\u0007\u0002\u001c\u001bVdG/\u001b9mKB#gmU8ve\u000e,\u0007+\u0019:b[\u0016$XM]:\u0002\u0015]LG\u000f[(viB,H\u000f\u0006\u0003\u0004\b\u000eEE\u0003BBE\u0007\u001b#BA!\r\u0004\f\"9!QJ\nA\u0004\t=\u0003\u0002CB\u0010'\u0011\u0005\raa$\u0011\r\t\u001511EB;\u0011\u001d\u0019Ic\u0005a\u0001\u0007'\u0003BAa\r\u0004\u0016&!1q\u0013B\u001b\u0005\u0011\u001a\u0016N\\4mK>\u0013X*\u001e7uSBdWmT;uaV$H+Y:l!\u0006\u0014\u0018-\\3uKJ\u001cH\u0003BBN\u0007G#Ba!(\u0004\"R!!\u0011GBP\u0011\u001d\u0011i\u0005\u0006a\u0002\u0005\u001fB\u0001ba\b\u0015\t\u0003\u00071q\u0012\u0005\b\u0007S!\u0002\u0019ABS!\u0011\u0011\u0019da*\n\t\r%&Q\u0007\u0002\u001d\u001bVdG/\u001b9mK>+H\u000f];u)\u0006\u001c8\u000eU1sC6,G/\u001a:t\u0003I1\u0017\u000e\\3oC6,w+\u001b;i'V4g-\u001b=\u0015\r\r=6\u0011XB_!\u0011\u0019\tla.\u000e\u0005\rM&\u0002BB[\u0005C\u000bA\u0001\\1oO&!1\u0011BBZ\u0011\u001d\u0019Y,\u0006a\u0001\u0005{\f\u0001BZ5mK:\fW.\u001a\u0005\b\u0007\u007f+\u0002\u0019\u0001B\u007f\u0003\u0019\u0019XO\u001a4jqR111YBf\u0007'$Ba!2\u0004JR!!\u0011GBd\u0011\u001d\u0011iE\u0006a\u0002\u0005\u001fB\u0001ba\b\u0017\t\u0003\u00071q\u0012\u0005\b\u0007S1\u0002\u0019ABg!\u0011\u0011\u0019da4\n\t\rE'Q\u0007\u0002\u001b'&tw\r\\3PkR\u0004X\u000f\u001e+bg.\u0004\u0016M]1nKR,'o\u001d\u0005\b\u0007+4\u0002\u0019ABl\u00039yW\u000f\u001e9vi\u001aKG.\u001a8b[\u0016\u0004bA!\u0002\u0004Z\nu\u0018\u0002BBn\u0005\u000f\u0011aa\u00149uS>t\u0017\u0001C3oG>$\u0017N\\4\u0015\t\tu8\u0011\u001d\u0005\b\u0005\u001b:\u00029\u0001B(\u000351\u0018\r\\5e\u000b:\u001cw\u000eZ5oOR!!qVBt\u0011\u001d\u0019I\u000f\u0007a\u0001\u0005{\f1!\u001a8d\u0003=\u0011x\u000e^1uS>tG)Z4sK\u0016\u001cH\u0003BBx\u0007{\u0004bA!\u0002\u0004Z\u000eE\b\u0003BBz\u0007sl!a!>\u000b\t\r](QH\u0001\te>$\u0018\r^5p]&!11`B{\u0005!\u0011v\u000e^1uS>t\u0007b\u0002B'3\u0001\u000f!qJ\u0001\r[\u0016\u0014x-Z(viB,Ho\u001d\u000b\u0005\u0005_#\u0019\u0001C\u0004\u0003Ni\u0001\u001dAa\u0014\u0002\u0013I|G/\u0019;j_:\u001cH\u0003\u0002C\u0005\t\u0017\u0001bAa \u0003\n\u000eE\bb\u0002B'7\u0001\u000f!qJ\u0001\u0013e>$\u0018\r^5p]\u0012+wM]3fgN+\u0017\u000f\u0006\u0003\u0005\u0012\u0011\u0005\u0002C\u0002C\n\t7\u0019\tP\u0004\u0003\u0005\u0016\u0011ea\u0002\u0002B-\t/I!A!\u0003\n\t\t=$qA\u0005\u0005\t;!yBA\u0002TKFTAAa\u001c\u0003\b!9!Q\n\u000fA\u0004\t=\u0013A\u0003;p%>$\u0018\r^5p]R!1q\u001eC\u0014\u0011\u001d!I#\ba\u0001\tW\tq\u0001Z3he\u0016,7\u000f\u0005\u0003\u0003\u0006\u00115\u0012\u0002\u0002C\u0018\u0005\u000f\u00111!\u00138u\u0003\u001d1G.\u001b9qK\u0012$B\u0001\"\u000e\u0005FA!Aq\u0007C!\u001b\t!ID\u0003\u0003\u0005<\u0011u\u0012\u0001\u00029bO\u0016TA\u0001b\u0010\u0003>\u0005\u0019\u0001\u000f\u001a4\n\t\u0011\rC\u0011\b\u0002\u0015!J,G-\u001a4j]\u0016$7+\u001a;PMB\u000bw-Z:\t\u000f\u0011\u001dc\u00041\u0001\u00056\u0005\t\u00010\u0001\u000bqe\u0016$WMZ5oK\u0012\u001cV\r^(g!\u0006<Wm\u001d\u000b\u0005\tk!i\u0005C\u0004\u0003N}\u0001\u001dAa\u0014\u0002\u001bM\u0004XmY5gS\u000e\u0004\u0016mZ3t)\t!\u0019\u0006\u0006\u0003\u0005V\u0011u\u0003C\u0002C\n\t7!9\u0006\u0005\u0003\u00058\u0011e\u0013\u0002\u0002C.\ts\u0011\u0011\u0002U1hKJ\u000bgnZ3\t\u000f\t5\u0003\u0005q\u0001\u0003P\u0005!1\u000f^3q)\u0011!\u0019\u0007\"\u001a\u0011\r\t\u00151\u0011\u001cC\u0016\u0011\u001d\u0011i%\ta\u0002\u0005\u001f\nA\"\\3sO\u0016\u0014Vm];miN$BAa,\u0005l!9!Q\n\u0012A\u0004\t=\u0013!\u00049bO\u0016\u0014\u0016M\\4fgN+\u0017\u000f\u0006\u0003\u0005r\u0011M\u0004C\u0002C\n\t7!)\u0006C\u0004\u0003N\r\u0002\u001dAa\u0014\u0002\u001b\u0015D8\r\\;eK\u0012\u0004\u0016mZ3t)\u0011!)\u0006\"\u001f\t\u000f\t5C\u0005q\u0001\u0003P\u0005Q\u0001/Y4f%\u0006tw-Z:\u0015\t\u0011UCq\u0010\u0005\b\u0005\u001b*\u00039\u0001B(\u0003\u001d\u0001\u0018mZ3Ok6$B\u0001b\u000b\u0005\u0006\"9!Q\n\u0014A\u0004\t=\u0013!B:uKB\u001cH\u0003\u0002CF\t\u001b\u0003b\u0001b\u0005\u0005\u001c\u0011-\u0002b\u0002B'O\u0001\u000f!qJ\u0001\be\u00164XM]:f)\u0011!\u0019\n\"&\u0011\r\u0011MA1\u0004BX\u0011\u001d\u0011i\u0005\u000ba\u0002\u0005\u001f\naA]3qK\u0006$H\u0003\u0002CJ\t7CqA!\u0014*\u0001\b\u0011y\u0005\u0006\u0003\u0003~\u0012}\u0005b\u0002B'U\u0001\u000f!qJ\u0001\rkN,'\u000fU1tg^|'\u000f\u001a\u000b\u0005\u0005{$)\u000bC\u0004\u0003N-\u0002\u001dAa\u0014\u0002'\u0015t7M]=qi&|g.\u00117h_JLG\u000f[7\u0015\t\u0011-FQ\u0018\t\u0005\t[#I,\u0004\u0002\u00050*!A\u0011\u0017CZ\u0003))gn\u0019:zaRLwN\u001c\u0006\u0005\t\u007f!)L\u0003\u0003\u00058\nu\u0012a\u00019s_&!A1\u0018CX\u00055\u0001FMZ#oGJL\b\u000f^5p]\"9!Q\n\u0017A\u0004\t=\u0013a\u00039fe6L7o]5p]N$B\u0001b1\u0005PB1A1\u0003C\u000e\t\u000b\u0004B\u0001b2\u0005L6\u0011A\u0011\u001a\u0006\u0005\tc#i$\u0003\u0003\u0005N\u0012%'a\u0005)eM\u0006\u001b7-Z:t!\u0016\u0014X.[:tS>t\u0007b\u0002B'[\u0001\u000f!qJ\u0001\u000e_V$H.\u001b8f!>d\u0017nY=\u0015\t\u0011UG\u0011\u001d\t\u0005\t/$i.\u0004\u0002\u0005Z*!A1\u001cB\u001f\u0003\u001dyW\u000f\u001e7j]\u0016LA\u0001b8\u0005Z\niq*\u001e;mS:,\u0007k\u001c7jGfDqA!\u0014/\u0001\b\u0011y%A\u0005u_\u000e\u0004v\u000e\\5dsR!Aq\u001dCz!\u0011!I\u000fb<\u000e\u0005\u0011-(\u0002\u0002Cw\u0005{\t1\u0001^8d\u0013\u0011!\t\u0010b;\u0003\u0013Q{7\tU8mS\u000eL\bb\u0002B'_\u0001\u000f!qJ\u0001\u000fM&dWM\\1nK\u001a{w\u000e^3s)\u0011\u0011y\u000b\"?\t\u000f\t5\u0003\u0007q\u0001\u0003P\u0005\u0011bn\u001c:nC2L'0\u001a)bO\u0016\u001c\u0016N_3t)\u0011\u0011y\u000bb@\t\u000f\t5\u0013\u0007q\u0001\u0003P\u0005!b-\u001b:ti&s\u0007/\u001e;D_Z,'\u000fV5uY\u0016$BAa,\u0006\u0006!9!Q\n\u001aA\u0004\t=\u0013A\u00042p_.l\u0017M]6t\u0019\u00164X\r\u001c\u000b\u0005\tW)Y\u0001C\u0004\u0003NM\u0002\u001dAa\u0014\u0002\u001d\t|wn[7be.\u001c(+Z4FqR!1q[C\t\u0011\u001d\u0011i\u0005\u000ea\u0002\u0005\u001f\nQc\\;uaV$h)\u001b7f]\u0006lW\rU1ui\u0016\u0014h\u000e\u0006\u0003\u0004X\u0016]\u0001b\u0002B'k\u0001\u000f!qJ\u0001\u0005M>tG\u000f\u0006\u0003\u0006\u001e\u0015\u0015\u0002\u0003BC\u0010\u000bCi!\u0001\"\u0010\n\t\u0015\rBQ\b\u0002\u0012'R\fg\u000eZ1sIRK\b/Z\u0019G_:$\bb\u0002B'm\u0001\u000f!qJ\u0001\u000f]Vl'-\u001a:j]\u001e\u001cF/\u001f7f)\u0011)Y#b\u000e\u0011\t\u00155R1G\u0007\u0003\u000b_QA!\"\r\u0005>\u0005a\u0001.Z1eKJ4wn\u001c;fe&!QQGC\u0018\u00059qU/\u001c2fe&twm\u0015;zY\u0016DqA!\u00148\u0001\b\u0011y%A\u0004qCR$XM\u001d8\u0015\t\tuXQ\b\u0005\b\u0005\u001bB\u00049\u0001B(\u0003-1wN\u001c;TSj,w\n\u001d;\u0015\t\u0015\rS1\n\t\u0007\u0005\u000b\u0019I.\"\u0012\u0011\t\t\u0015QqI\u0005\u0005\u000b\u0013\u00129A\u0001\u0004E_V\u0014G.\u001a\u0005\b\u0005\u001bJ\u00049\u0001B(\u0003!1wN\u001c;TSj,G\u0003BC#\u000b#BqA!\u0014;\u0001\b\u0011y%A\u0007mS:,\u0007*Z5hQR|\u0005\u000f\u001e\u000b\u0005\u000b\u0007*9\u0006C\u0004\u0003Nm\u0002\u001dAa\u0014\u0002\u0013\u0019|g\u000e^%e\u001fB$H\u0003BBl\u000b;BqA!\u0014=\u0001\b\u0011y%\u0001\nqC\u001e,7i\\;oiN#\u0018M\u001d;Ge>lG\u0003\u0002C2\u000bGBqA!\u0014>\u0001\b\u0011y%\u0001\u0007qC\u001e,Gj\\2bi&|g\u000e\u0006\u0003\u0006j\u0015e\u0004\u0003\u0003B\u0003\u0005s,Y'b\u001d\u0011\t\u00155TqN\u0007\u0003\u0005{IA!\"\u001d\u0003>\tia+\u001a:uS\u000e\fG.\u00117jO:\u0004B!\"\u001c\u0006v%!Qq\u000fB\u001f\u0005=AuN]5{_:$\u0018\r\\!mS\u001et\u0007b\u0002B'}\u0001\u000f!qJ\u0001\u0005CJ,\u0017\r\u0006\u0003\u0006��\u0015\u0015\u0005\u0003BC7\u000b\u0003KA!b!\u0003>\t)Bk\u001c9MK\u001a$(+Z2uC:<W\u000f\\1s\u0005>D\bb\u0002B'\u007f\u0001\u000f!qJ\u0001\fM2\fG\u000f^3o\r>\u0014X\u000e\u0006\u0003\u00030\u0016-\u0005b\u0002B'\u0001\u0002\u000f!qJ\u0001\u000fC\u000e\u0014xNR8s[B{G.[2z)\u0011)\t*b(\u0011\r\t\u00151\u0011\\CJ!\u0011))*b'\u000e\u0005\u0015]%\u0002BCM\t{\tAAZ8s[&!QQTCL\u00059\t5M]8G_Jl\u0007k\u001c7jGfDqA!\u0014B\u0001\b\u0011y%\u0001\u0007bkR|7I]8q\u001b>$W\r\u0006\u0003\u0006&\u0016=\u0006\u0003BCT\u000bWk!!\"+\u000b\t\tmBQW\u0005\u0005\u000b[+IK\u0001\u0007BkR|7I]8q\u001b>$W\rC\u0004\u0003N\t\u0003\u001dAa\u0014\u0002\u0017\u0005\u0014X-Y\"s_B|\u0005\u000f\u001e\u000b\u0005\u000bk+i\f\u0005\u0004\u0003\u0006\reWq\u0017\t\u0005\u000b[*I,\u0003\u0003\u0006<\nu\"A\u0004*fGR\fgnZ;mCJ\u0014u\u000e\u001f\u0005\b\u0005\u001b\u001a\u00059\u0001B(\u00039yG\rZ!sK\u0006\u001c%o\u001c9PaR$B!\".\u0006D\"9!Q\n#A\u0004\t=\u0013aD3wK:\f%/Z1De>\u0004x\n\u001d;\u0015\t\u0015UV\u0011\u001a\u0005\b\u0005\u001b*\u00059\u0001B(\u0003A\u0001\u0018mZ3t\u0003J,\u0017m\u0011:pa>\u0003H\u000f\u0006\u0003\u0006P\u0016]\u0007C\u0002B\u0003\u00073,\t\u000e\u0005\u0005\u0003��\u0016M'Q`C\\\u0013\u0011))na\u0003\u0003\u00075\u000b\u0007\u000fC\u0004\u0003N\u0019\u0003\u001dAa\u0014\u0002!Q|'+Z2uC:<W\u000f\\1s\u0005>DH\u0003BC\\\u000b;DqA!\u0014H\u0001\u0004\u0011y%A\nu_J+7\r^1oOVd\u0017M\u001d\"pq>\u0003H\u000f\u0006\u0003\u00066\u0016\r\bb\u0002B'\u0011\u0002\u000f!qJ\u0001\u0015i>\u0014Vm\u0019;b]\u001e,H.\u0019:C_bd\u0015n\u001d;\u0015\t\u0015%XQ\u001e\t\u0007\t')Y/b.\n\t\t-Eq\u0004\u0005\b\u0005\u001bJ\u00059\u0001B(\u0003!\u0011\u0017\r^3t'\u0016\fH\u0003BCz\r\u0003\u0001bA!\u0002\u0004Z\u0016U\b\u0003BC|\u000b{l!!\"?\u000b\t\u0015mH1W\u0001\n]Vl'-\u001a:j]\u001eLA!b@\u0006z\ni!)\u0019;fgN+\u0017/^3oG\u0016DqA!\u0014K\u0001\b\u0011y%A\u0006cCR,7\u000fR5hSR\u001cH\u0003\u0002C\u0016\r\u000fAqA!\u0014L\u0001\b\u0011y%A\u0005lS2|')\u001f;fgV\u0011aQ\u0002\t\u0005\u0005\u000b1y!\u0003\u0003\u0007\u0012\t\u001d!\u0001\u0002'p]\u001e\f!b[5m_\nKH/Z:!\u0003%iWmZ1CsR,7/\u0001\u0006nK\u001e\f')\u001f;fg\u0002\nAa]5{KR!QQ\tD\u000f\u0011\u001d\u0011i\u0005\u0015a\u0002\u0005\u001f\n\u0001b]5{KVs\u0017\u000e\u001e\u000b\u0005\r\u001b1\u0019\u0003C\u0004\u0003NE\u0003\u001dAa\u0014\u0002\u0013\u0019LG.\u001a)bO\u0016\u001cH\u0003\u0002D\u0015\rc\u0001bAa \u0003\n\u001a-\u0002C\u0003B\u0003\r[!Y\u0003b\u000b\u0005,%!aq\u0006B\u0004\u0005\u0019!V\u000f\u001d7fg!9!Q\n*A\u0004\t=\u0013A\u0003:fg>dW\u000f^5p]R!A1\u0006D\u001c\u0011\u001d\u0011ie\u0015a\u0002\u0005\u001f\n1\"[7bO\u00164uN]7biR!aQ\bD !\u0019\u0011)a!7\u00040\"9!Q\n+A\u0004\t=\u0013\u0001\u0004:fa\u0006<\u0017N\\1uS>tG\u0003\u0002D#\r#\u0002BAb\u0012\u0007N5\u0011a\u0011\n\u0006\u0005\r\u0017\u0012i$\u0001\u0006sKB\fw-\u001b8bi\u0016LAAb\u0014\u0007J\ta!+\u001a9bO&t\u0017\r^5p]\"9!QJ+A\u0004\t=\u0013!B2pY>\u0014H\u0003\u0002D,\rK\u0002bA!\u0002\u0004Z\u001ae\u0003\u0003\u0002D.\rCj!A\"\u0018\u000b\t\u0019}#\u0011U\u0001\u0004C^$\u0018\u0002\u0002D2\r;\u0012QaQ8m_JDqA!\u0014W\u0001\b\u0011y%\u0001\u0004`G>dwN\u001d\u000b\u0005\r/2Y\u0007C\u0004\u0003N]\u0003\rAa\u0014\u0002%\u0015$\u0017\u000e\u001e+fqR|\u0005/\u001a:bi&|gn\u001d\u000b\u0005\rc2)\t\u0005\u0004\u0005\u0014\u0015-h1\u000f\t\u0005\rk2\t)\u0004\u0002\u0007x)!a\u0011\u0010D>\u0003\u0011)G-\u001b;\u000b\t\u0019udqP\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0005\u0005\u0003\nY0\u0003\u0003\u0007\u0004\u001a]$!E#eSR$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]\"9!Q\n-A\u0004\t=\u0013\u0001F1qa\u0016tG\rV3yi>\u0003XM]1uS>t7\u000f\u0006\u0003\u0007\f\u001aM\u0005C\u0002C\n\u000bW4i\t\u0005\u0003\u0007v\u0019=\u0015\u0002\u0002DI\ro\u00121#\u00119qK:$G+\u001a=u\u001fB,'/\u0019;j_:DqA!\u0014Z\u0001\b\u0011y%A\bj[\u0006<Wm\u00149fe\u0006$\u0018n\u001c8t)\u00111IJ\")\u0011\r\u0011MQ1\u001eDN!\u00111)H\"(\n\t\u0019}eq\u000f\u0002\u0012\u0003\u0012$\u0017*\\1hK>\u0003XM]1uS>t\u0007b\u0002B'5\u0002\u000f!qJ\u0001\u000bgf\u001cH/Z7G_:$H\u0003\u0002DT\r[\u0003BA\"\u001e\u0007*&!a1\u0016D<\u0005)\u0019\u0016p\u001d;f[\u001a{g\u000e\u001e\u0005\b\u0005\u001bZ\u00069\u0001B(\u0003-\u0019\u0018p\u001d;f[\u001a{g\u000e^:\u0015\t\u0019MfQ\u0017\t\u0007\t')YOb*\t\u000f\t5C\fq\u0001\u0003P\u0005!A/\u001a=u)\u0011\u0011iPb/\t\u000f\t5S\fq\u0001\u0003P\u0005aqN]5hS:\fG\u000eV3yiR!!Q Da\u0011\u001d\u0011iE\u0018a\u0002\u0005\u001f\n\u0011#\u001a3jiR+\u0007\u0010^(qKJ\fG/[8o)\u00111\u0019Hb2\t\u000f\t5s\fq\u0001\u0003P\u0005\u0019\u0012\r\u001d9f]\u0012$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]R!aQ\u0012Dg\u0011\u001d\u0011i\u0005\u0019a\u0002\u0005\u001f\n!c]5h]\u0006$XO]3GS\u0016dGMT1nKR!1q\u001bDj\u0011\u001d\u0011i%\u0019a\u0002\u0005\u001f\na\"[7bO\u0016|\u0005/\u001a:bi&|g\u000e\u0006\u0003\u0007\u001c\u001ae\u0007b\u0002B'E\u0002\u000f!qJ\u0001\u0015S:\u001cXM\u001d;QC\u001e,w\n]3sCRLwN\\:\u0015\t\u0019}gq\u001d\t\u0007\t')YO\"9\u0011\t\u0019Ud1]\u0005\u0005\rK49HA\nJ]N,'\u000f\u001e)bO\u0016|\u0005/\u001a:bi&|g\u000eC\u0004\u0003N\r\u0004\u001dAa\u0014\u0002'%t7/\u001a:u!\u0006<Wm\u00149fe\u0006$\u0018n\u001c8\u0015\t\u0019\u0005hQ\u001e\u0005\b\u0005\u001b\"\u00079\u0001B(\u0003Q!W\r\\3uKB\u000bw-Z(qKJ\fG/[8ogR!a1\u001fD~!\u0019\u0011yH!#\u0007vB!aQ\u000fD|\u0013\u00111IPb\u001e\u0003'\u0011+G.\u001a;f!\u0006<Wm\u00149fe\u0006$\u0018n\u001c8\t\u000f\t5S\rq\u0001\u0003P\u0005\u0019B-\u001a7fi\u0016\u0004\u0016mZ3Pa\u0016\u0014\u0018\r^5p]R!aQ_D\u0001\u0011\u001d\u0011iE\u001aa\u0002\u0005\u001f\na\"[7bO\u0016\u001cv.\u001e:dK>\u0003H\u000f\u0006\u0003\b\b\u001d\r\u0002C\u0002B\u0003\u00073<I\u0001\r\u0003\b\f\u001dE\u0001C\u0002BH\u0005O<i\u0001\u0005\u0003\b\u0010\u001dEA\u0002\u0001\u0003\f\u000f'9\u0017\u0011!A\u0001\u0006\u00039)BA\u0002`IE\nBab\u0006\b\u001eA!!QAD\r\u0013\u00119YBa\u0002\u0003\u000f9{G\u000f[5oOB!!QAD\u0010\u0013\u00119\tCa\u0002\u0003\u0007\u0005s\u0017\u0010C\u0004\u0003N\u001d\u0004\u001dAa\u0014\u0002\u0017%l\u0017mZ3T_V\u00148-\u001a\u000b\u0005\u000fS9\u0019\u0004\r\u0003\b,\u001d=\u0002C\u0002BH\u0005O<i\u0003\u0005\u0003\b\u0010\u001d=BaCD\u0019Q\u0006\u0005\t\u0011!B\u0001\u000f+\u00111a\u0018\u00133\u0011\u001d\u0011i\u0005\u001ba\u0002\u0005\u001f\n!b^1uKJl\u0017M]6t)\u00119Idb\u0012\u0011\r\u0011MA1DD\u001e!\u00119idb\u0011\u000e\u0005\u001d}\"\u0002BD!\tk\u000b\u0011b^1uKJl\u0017M]6\n\t\u001d\u0015sq\b\u0002\n/\u0006$XM]7be.DqA!\u0014j\u0001\b\u0011y\u0005\u0006\u0003\b<\u001d-\u0003b\u0002B'U\u0002\u000f!q\n\u000b\u0005\u000f\u001f:I\u0006\r\u0003\bR\u001dU\u0003C\u0002BH\u0005O<\u0019\u0006\u0005\u0003\b\u0010\u001dUCaCD,W\u0006\u0005\t\u0011!B\u0001\u000f+\u00111a\u0018\u00134\u0011\u001d9Yf\u001ba\u0001\u0005{\fQ![7bO\u0016\fQa^5ei\"$Ba\"\u0019\bhA!!QAD2\u0013\u00119)Ga\u0002\u0003\u000b\u0019cw.\u0019;\t\u000f\t5C\u000eq\u0001\u0003P\u00051\u0001.Z5hQR$Ba\"\u0019\bn!9!QJ7A\u0004\t=\u0013\u0001\u00039pg&$\u0018n\u001c8\u0015\t\u001dMt\u0011\u0010\t\u0005\r7:)(\u0003\u0003\bx\u0019u#!\u0002)pS:$\bb\u0002B']\u0002\u000f!qJ\u0001\fa>\u001c\u0018\u000e^5p]>\u0003H\u000f\u0006\u0003\b��\u001d\u0005\u0005C\u0002B\u0003\u00073<\u0019\bC\u0004\u0003N=\u0004\u001dAa\u0014\u0002\t9\fW.\u001a\u000b\u0005\u0005{<9\tC\u0004\u0003NA\u0004\u001dAa\u0014\u0002\u000f9\fW.Z(qiR!1q[DG\u0011\u001d\u0011i%\u001da\u0002\u0005\u001f\n\u0001c\u001d;beR\u001cx+\u001b;i!J,g-\u001b=\u0015\t\tux1\u0013\u0005\b\u0005\u001b\u0012\b9\u0001B(\u0003]A\u0017n\u001a5mS\u001eDG\u000fV3yi>\u0003XM]1uS>t7\u000f\u0006\u0003\b\u001a\u001e\u0005\u0006C\u0002C\n\u000bW<Y\n\u0005\u0003\u0007v\u001du\u0015\u0002BDP\ro\u0012a\u0003S5hQ2Lw\r\u001b;UKb$x\n]3sCRLwN\u001c\u0005\b\u0005\u001b\u001a\b9\u0001B(\u0003YA\u0017n\u001a5mS\u001eDG\u000fV3yi>\u0003XM]1uS>tG\u0003BDN\u000fOCqA!\u0014u\u0001\b\u0011y%A\u0007iS\u001eDG.[4ii.Kg\u000e\u001a\u000b\u0005\u000f[;\u0019\f\u0005\u0003\u0007v\u001d=\u0016\u0002BDY\ro\u0012Q\u0002S5hQ2Lw\r\u001b;UsB,\u0007b\u0002B'k\u0002\u000f!qJ\u0001\u001bgR\u0014\u0018n[3uQ>,x\r\u001b+fqR|\u0005/\u001a:bi&|gn\u001d\u000b\u0005\u000f3;I\fC\u0004\u0003NY\u0004\u001dAa\u0014\u00025M$(/[6fi\"\u0014x.^4i)\u0016DHo\u00149fe\u0006$\u0018n\u001c8\u0015\t\u001dmuq\u0018\u0005\b\u0005\u001b:\b9\u0001B(\u0003!a\u0017N\\6LS:$G\u0003BDc\u000f\u0017\u0004BA\"\u001e\bH&!q\u0011\u001aD<\u0005!a\u0015N\\6UsB,\u0007b\u0002B'q\u0002\u000f!qJ\u0001\u0015CB\u0004XM\u001c3MS:\\w\n]3sCRLwN\\:\u0015\t\u001dEw\u0011\u001c\t\u0007\t')Yob5\u0011\t\u0019UtQ[\u0005\u0005\u000f/49HA\nBaB,g\u000e\u001a'j].|\u0005/\u001a:bi&|g\u000eC\u0004\u0003Ne\u0004\u001dAa\u0014\u0002'\u0005\u0004\b/\u001a8e\u0019&t7n\u00149fe\u0006$\u0018n\u001c8\u0015\t\u001dMwq\u001c\u0005\b\u0005\u001bR\b9\u0001B(\u0003I)G-\u001b;MS:\\w\n]3sCRLwN\\:\u0015\t\u001d\u0015xQ\u001e\t\u0007\t')Yob:\u0011\t\u0019Ut\u0011^\u0005\u0005\u000fW49HA\tFI&$H*\u001b8l\u001fB,'/\u0019;j_:DqA!\u0014|\u0001\b\u0011y%A\tfI&$H*\u001b8l\u001fB,'/\u0019;j_:$Bab:\bt\"9!Q\n?A\u0004\t=\u0013\u0001\u00063fY\u0016$X\rT5oW>\u0003XM]1uS>t7\u000f\u0006\u0003\bz\"\u0005\u0001C\u0002C\n\u000bW<Y\u0010\u0005\u0003\u0007v\u001du\u0018\u0002BD��\ro\u00121\u0003R3mKR,G*\u001b8l\u001fB,'/\u0019;j_:DqA!\u0014~\u0001\b\u0011y%A\neK2,G/\u001a'j].|\u0005/\u001a:bi&|g\u000e\u0006\u0003\b|\"\u001d\u0001b\u0002B'}\u0002\u000f!qJ\u0001\u001bCB\u0004XM\u001c3BiR\f7\r[7f]R|\u0005/\u001a:bi&|gn\u001d\u000b\u0005\u0011\u001bA)\u0002\u0005\u0004\u0005\u0014\u0015-\br\u0002\t\u0005\rkB\t\"\u0003\u0003\t\u0014\u0019]$!G!qa\u0016tG-\u0011;uC\u000eDW.\u001a8u\u001fB,'/\u0019;j_:DqA!\u0014��\u0001\b\u0011y%A\rbaB,g\u000eZ!ui\u0006\u001c\u0007.\\3oi>\u0003XM]1uS>tG\u0003\u0002E\b\u00117A\u0001B!\u0014\u0002\u0002\u0001\u000f!qJ\u0001\u001bI\u0016dW\r^3BiR\f7\r[7f]R|\u0005/\u001a:bi&|gn\u001d\u000b\u0005\u0011CAI\u0003\u0005\u0004\u0005\u0014\u0015-\b2\u0005\t\u0005\rkB)#\u0003\u0003\t(\u0019]$!\u0007#fY\u0016$X-\u0011;uC\u000eDW.\u001a8u\u001fB,'/\u0019;j_:D\u0001B!\u0014\u0002\u0004\u0001\u000f!qJ\u0001\u001aI\u0016dW\r^3BiR\f7\r[7f]R|\u0005/\u001a:bi&|g\u000e\u0006\u0003\t$!=\u0002\u0002\u0003B'\u0003\u000b\u0001\u001dAa\u0014\u00029\u0005\u0004\b/\u001a8e\u000b6\u0014W\r\u001a3fI\u001aKG.Z(qKJ\fG/[8ogR!\u0001R\u0007E\u001f!\u0019!\u0019\"b;\t8A!aQ\u000fE\u001d\u0013\u0011AYDb\u001e\u00037\u0005\u0003\b/\u001a8e\u000b6\u0014W\r\u001a3fI\u001aKG.Z(qKJ\fG/[8o\u0011!\u0011i%a\u0002A\u0004\t=\u0013aG1qa\u0016tG-R7cK\u0012$W\r\u001a$jY\u0016|\u0005/\u001a:bi&|g\u000e\u0006\u0003\t8!\r\u0003\u0002\u0003B'\u0003\u0013\u0001\u001dAa\u0014\u0002\u0015\u0019LG.Z*pkJ\u001cW\r\u0006\u0003\u0003f\"%\u0003\u0002\u0003B'\u0003\u0017\u0001\u001dAa\u0014\u00029\u0011,G.\u001a;f\u000b6\u0014W\r\u001a3fI\u001aKG.Z(qKJ\fG/[8ogR!\u0001r\nE,!\u0019!\u0019\"b;\tRA!aQ\u000fE*\u0013\u0011A)Fb\u001e\u00037\u0011+G.\u001a;f\u000b6\u0014W\r\u001a3fI\u001aKG.Z(qKJ\fG/[8o\u0011!\u0011i%!\u0004A\u0004\t=\u0013a\u00073fY\u0016$X-R7cK\u0012$W\r\u001a$jY\u0016|\u0005/\u001a:bi&|g\u000e\u0006\u0003\tR!u\u0003\u0002\u0003B'\u0003\u001f\u0001\u001dAa\u0014\u0002+\u0011,G.\u001a;f\u00136\fw-Z(qKJ\fG/[8ogR!\u00012\rE6!\u0019!\u0019\"b;\tfA!aQ\u000fE4\u0013\u0011AIGb\u001e\u0003)\u0011+G.\u001a;f\u00136\fw-Z(qKJ\fG/[8o\u0011!\u0011i%!\u0005A\u0004\t=\u0013\u0001\u00063fY\u0016$X-S7bO\u0016|\u0005/\u001a:bi&|g\u000e\u0006\u0003\tf!E\u0004\u0002\u0003B'\u0003'\u0001\u001dAa\u0014\u00023\u0011,G.\u001a;f\u0011&<\u0007\u000e\\5hQR|\u0005/\u001a:bi&|gn\u001d\u000b\u0005\u0011oBy\b\u0005\u0004\u0005\u0014\u0015-\b\u0012\u0010\t\u0005\rkBY(\u0003\u0003\t~\u0019]$\u0001\u0007#fY\u0016$X\rS5hQ2Lw\r\u001b;Pa\u0016\u0014\u0018\r^5p]\"A!QJA\u000b\u0001\b\u0011y%\u0001\reK2,G/\u001a%jO\"d\u0017n\u001a5u\u001fB,'/\u0019;j_:$B\u0001#\u001f\t\u0006\"A!QJA\f\u0001\b\u0011y%\u0001\u000bbaB,g\u000e\u001a#sC^|\u0005/\u001a:bi&|gn\u001d\u000b\u0005\u0011\u0017C\u0019\n\u0005\u0004\u0005\u0014\u0015-\bR\u0012\t\u0005\rkBy)\u0003\u0003\t\u0012\u001a]$aE!qa\u0016tG\r\u0012:bo>\u0003XM]1uS>t\u0007\u0002\u0003B'\u00033\u0001\u001dAa\u0014\u0002'\u0005\u0004\b/\u001a8e\tJ\fwo\u00149fe\u0006$\u0018n\u001c8\u0015\t!5\u0005\u0012\u0014\u0005\t\u0005\u001b\nY\u0002q\u0001\u0003P\u0005!\u0012\r\u001d9f]\u0012d\u0015N\\3Pa\u0016\u0014\u0018\r^5p]N$B\u0001c(\t(B1A1CCv\u0011C\u0003BA\"\u001e\t$&!\u0001R\u0015D<\u0005M\t\u0005\u000f]3oI2Kg.Z(qKJ\fG/[8o\u0011!\u0011i%!\bA\u0004\t=\u0013aE1qa\u0016tG\rT5oK>\u0003XM]1uS>tG\u0003\u0002EQ\u0011[C\u0001B!\u0014\u0002 \u0001\u000f!qJ\u0001\u0018K\u0012LG\u000fS5hQ2Lw\r\u001b;Pa\u0016\u0014\u0018\r^5p]N$B\u0001c-\t<B1A1CCv\u0011k\u0003BA\"\u001e\t8&!\u0001\u0012\u0018D<\u0005Y)E-\u001b;IS\u001eDG.[4ii>\u0003XM]1uS>t\u0007\u0002\u0003B'\u0003C\u0001\u001dAa\u0014\u0002-\u0015$\u0017\u000e\u001e%jO\"d\u0017n\u001a5u\u001fB,'/\u0019;j_:$B\u0001#.\tB\"A!QJA\u0012\u0001\b\u0011y%\u0001\u0007b]:|G/\u0019;j_:LE\r\u0006\u0003\u0005,!\u001d\u0007\u0002\u0003B'\u0003K\u0001\rAa\u0014\u00023\u0005\u0004\b/\u001a8e\r>\u0014XNR5fY\u0012|\u0005/\u001a:bi&|gn\u001d\u000b\u0005\u0011\u001bD)\u000e\u0005\u0004\u0005\u0014\u0015-\br\u001a\t\u0005\rkB\t.\u0003\u0003\tT\u001a]$\u0001G!qa\u0016tGMR8s[\u001aKW\r\u001c3Pa\u0016\u0014\u0018\r^5p]\"A!QJA\u0014\u0001\b\u0011y%\u0001\u0006sK6|g/\u001a#piN$BA!@\t\\\"A\u0001R\\A\u0015\u0001\u0004\u0011i0A\u0001t\u000391wN]7GS\u0016dG-\u00117jO:$B\u0001c9\tjB!aQ\u000fEs\u0013\u0011A9Ob\u001e\u0003\u001d\u0019{'/\u001c$jK2$\u0017\t\\5h]\"A!QJA\u0016\u0001\b\u0011y%\u0001\rbaB,g\u000e\u001a$pe64\u0015.\u001a7e\u001fB,'/\u0019;j_:$B\u0001c4\tp\"A!QJA\u0017\u0001\b\u0011y%A\reK2,G/\u001a$pe64\u0015.\u001a7e\u001fB,'/\u0019;j_:\u001cH\u0003\u0002E{\u0011{\u0004b\u0001b\u0005\u0006l\"]\b\u0003\u0002D;\u0011sLA\u0001c?\u0007x\tAB)\u001a7fi\u00164uN]7GS\u0016dGm\u00149fe\u0006$\u0018n\u001c8\t\u0011\t5\u0013q\u0006a\u0002\u0005\u001f\n\u0001\u0004Z3mKR,gi\u001c:n\r&,G\u000eZ(qKJ\fG/[8o)\u0011A90c\u0001\t\u0011\t5\u0013\u0011\u0007a\u0002\u0005\u001f\n\u0001CZ8s[\u001aKW\r\u001c3PaRLwN\\:\u0015\t%%\u00112\u0002\t\u0007\t'!YB!@\t\u0011\t5\u00131\u0007a\u0002\u0005\u001f\nQBZ8s[\u001aKW\r\u001c3LS:$G\u0003BE\t\u0013/\u0001BA\"\u001e\n\u0014%!\u0011R\u0003D<\u000551uN]7GS\u0016dG\rV=qK\"A!QJA\u001b\u0001\b\u0011y%A\btQ\u0006\u0004Xm\u00149fe\u0006$\u0018n\u001c8t)\u0011Ii\"#\n\u0011\r\u0011MQ1^E\u0010!\u00111)(#\t\n\t%\rbq\u000f\u0002\u0012\u0003\u0012$7\u000b[1qK>\u0003XM]1uS>t\u0007\u0002\u0003B'\u0003o\u0001\u001dAa\u0014\u0002\u001dMD\u0017\r]3Pa\u0016\u0014\u0018\r^5p]R!\u0011rDE\u0016\u0011!\u0011i%!\u000fA\u0004\t=\u0013A\u00054jY24uN]7Pa\u0016\u0014\u0018\r^5p]N$B!#\r\n>A1A1CE\u001a\u0013oIA!#\u000e\u0005 \tA\u0011\n^3sC\ndW\r\u0005\u0003\u0007v%e\u0012\u0002BE\u001e\ro\u0012\u0011CR5mY\u001a{'/\\(qKJ\fG/[8o\u0011!\u0011i%a\u000fA\u0004\t=\u0013a\u00034pe6<\u0016\u000eZ4fiN$B!c\u0011\nFAA!q`Cj\u0005{\u0014i\u0010\u0003\u0005\u0003N\u0005u\u00029\u0001B(\u0003!!\u0018MY(sI\u0016\u0014H\u0003BE&\u0013\u001b\u0002b\u0001b\u0005\u0006l\nu\b\u0002\u0003B'\u0003\u007f\u0001\u001dAa\u0014\u0002)I|G/\u0019;f!\u0006<Wm\u00149fe\u0006$\u0018n\u001c8t)\u0011I\u0019&c\u0017\u0011\r\u0011MQ1^E+!\u00111)(c\u0016\n\t%ecq\u000f\u0002\u0014%>$\u0018\r^3QC\u001e,w\n]3sCRLwN\u001c\u0005\t\u0005\u001b\n\t\u0005q\u0001\u0003P\u0005\u0019\"o\u001c;bi\u0016\u0004\u0016mZ3Pa\u0016\u0014\u0018\r^5p]R!\u0011RKE1\u0011!\u0011i%a\u0011A\u0004\t=\u0013A\u00034pe6,%O]8sgR!\u00112JE4\u0011!\u0011i%!\u0012A\u0004\t=\u0013a\u00032pe\u0012,'oQ8m_J$BAb\u0016\nn!A!QJA$\u0001\b\u0011y%A\bcC\u000e\\wM]8v]\u0012\u001cu\u000e\\8s)\u001119&c\u001d\t\u0011\t5\u0013\u0011\na\u0002\u0005\u001f\nQa\u001d5ba\u0016$B!#\u001f\n\u0004B!\u00112PE@\u001b\tIiH\u0003\u0003\u0007z\te\u0012\u0002BEA\u0013{\u0012Qa\u00155ba\u0016D\u0001B!\u0014\u0002L\u0001\u000f!qJ\u0001\fE>\u0014H-\u001a:XS\u0012$\b\u000e\u0006\u0003\bb%%\u0005\u0002\u0003B'\u0003\u001b\u0002\u001dAa\u0014\u0002\u000f!,\u0007P\r*hER!a\u0011LEH\u0011!Ai.a\u0014A\u0002\tu\u0018A\u00043jg\u000e\f'\u000fZ(vi2Lg.\u001a\u000b\u0005\u0005_K)\n\u0003\u0005\u0003N\u0005E\u00039\u0001B(\u0003!IW.Y4f\tBLG\u0003\u0002C2\u00137C\u0001B!\u0014\u0002T\u0001\u000f!qJ\u0001\u000fS6\fw-Z$sCf\u001c8-\u00197f)\u0011\u0011y+#)\t\u0011\t5\u0013Q\u000ba\u0002\u0005\u001f\n\u0011\u0003Z5tG\u0006\u0014H-T;mi&lW\rZ5b)\u0011\u0011y+c*\t\u0011\t5\u0013q\u000ba\u0002\u0005\u001f\n1b];cg\u0016$hi\u001c8ugR!!qVEW\u0011!\u0011i%!\u0017A\u0004\t=\u0013!B:qK\u0016$G\u0003\u0002C2\u0013gC\u0001B!\u0014\u0002\\\u0001\u000f!qJ\u0001\rS6\fw-Z)vC2LG/\u001f\u000b\u0005\u000b\u0007JI\f\u0003\u0005\u0003N\u0005u\u00039\u0001B(\u0003\u0011qW\u000f\u001d(\u0015\t\u0011-\u0012r\u0018\u0005\t\u0005\u001b\ny\u0006q\u0001\u0003P\u0005I\u0001/Y4f\u001fJ$WM\u001d\u000b\u0005\u0013\u000bL\t\u000e\u0005\u0003\nH&5WBAEe\u0015\u0011IY\r\".\u0002\u00079,\b/\u0003\u0003\nP&%'!\u0003)bO\u0016|%\u000fZ3s\u0011!\u0011i%!\u0019A\u0004\t=\u0013a\u0003;pa2+g\r\u001e*fGR$B!b \nX\"A!QJA2\u0001\b\u0011y%A\u0003sCRLw\u000e\u0006\u0003\u0006F%u\u0007\u0002\u0003B'\u0003K\u0002\u001dAa\u0014\u0002-M\u0004H.\u001b;E_^tG\u000b[3NS\u0012$G.Z'pI\u0016$B!c9\npB!\u0011R]Ev\u001b\tI9O\u0003\u0003\nj\u0012U\u0016!B:qY&$\u0018\u0002BEw\u0013O\u0014ac\u00159mSR$un\u001e8UQ\u0016l\u0015\u000e\u001a3mK6{G-\u001a\u0005\t\u0005\u001b\n9\u0007q\u0001\u0003P\u0005\u0001\u0002O]3tKJ4X\rU1hKNK'0\u001a\u000b\u0005\u0005_K)\u0010\u0003\u0005\u0003N\u0005%\u00049\u0001B(\u0003I1\u0017\u000e\\3D_VtGo\u0015;beR4%o\\7\u0015\t\u0011-\u00122 \u0005\t\u0005\u001b\nY\u0007q\u0001\u0003P\u0005!R\r_5ti&twmT;uaV$\bk\u001c7jGf$BA#\u0001\u000b\u000eA!!2\u0001F\u0005\u001b\tQ)A\u0003\u0003\u000b\b\tu\u0012AB8viB,H/\u0003\u0003\u000b\f)\u0015!\u0001F#ySN$\u0018N\\4PkR\u0004X\u000f\u001e)pY&\u001c\u0017\u0010\u0003\u0005\u0003N\u00055\u00049\u0001B(\u0003M!WMZ1vYR|U\u000f\u001e9vi\u001a{G\u000eZ3s)\u0011Q\u0019B#\u0006\u0011\r\t\u00151\u0011\u001cBM\u0011!\u0011i%a\u001cA\u0004\t=\u0013!\u00053fM\u0006,H\u000e^(viB,HOR5mKR!1q\u001bF\u000e\u0011!\u0011i%!\u001dA\u0004\t=\u0013AC1eI6\u000b'oZ5ogR!!q\u0016F\u0011\u0011!\u0011i%a\u001dA\u0004\t=\u0013\u0001E5oG2,H-\u001a)bO\u0016\fe\r^3s)\u0011\u0011yKc\n\t\u0011\t5\u0013Q\u000fa\u0002\u0005\u001f\nqa\u001c9bG&$\u0018\u0010\u0006\u0003\u0005,)5\u0002\u0002\u0003B'\u0003o\u0002\u001dAa\u0014\u0015\t\u0011-\"\u0012\u0007\u0005\t\u0005\u001b\nI\bq\u0001\u0003P\u0005YQ\r_2faR\u0004\u0016mZ3t)\u0011Q9D#\u000f\u0011\r\u0011MQ1\u001eC\u0016\u0011!\u0011i%a\u001fA\u0004\t=\u0013a\u0003:jO\"$Hk\u001c'fMR$BAa,\u000b@!A!QJA?\u0001\b\u0011y%A\fbkR|G)\u001a;fGR,\u0005p\u00197vI\u0016$\u0007+Y4fgR!!q\u0016F#\u0011!\u0011i%a A\u0004\t=\u0013A\u00044p]R4\u0015-\\5ms:\u000bW.\u001a\u000b\u0005\u0005{TY\u0005\u0003\u0005\u0003N\u0005\u0005\u00059\u0001B(\u0003E1wN\u001c;GC6LG.\u001f(b[\u0016|\u0005\u000f\u001e\u000b\u0005\u0007/T\t\u0006\u0003\u0005\u0003N\u0005\r\u00059\u0001B(\u0003!1wN\u001c;C_2$G\u0003\u0002BX\u0015/B\u0001B!\u0014\u0002\u0006\u0002\u000f!qJ\u0001\u000bM>tG/\u0013;bY&\u001cG\u0003\u0002BX\u0015;B\u0001B!\u0014\u0002\b\u0002\u000f!qJ\u0001\u000f[\u0006\u0014x-\u001b8J]&s7\r[3t)\u0011Q\u0019G#\u001d\u0011\r\t\u00151\u0011\u001cF3!\u0011Q9G#\u001c\u000e\u0005)%$\u0002\u0002F6\u0005{\tQa]2bY\u0016LAAc\u001c\u000bj\t9Q*\u0019:hS:\u001c\b\u0002\u0003B'\u0003\u0013\u0003\u001dAa\u0014\u0002\u0011Q|\u0017J\\2iKN$B!\"\u0012\u000bx!A!\u0012PAF\u0001\u0004))%\u0001\u0002d[\u0006Q\u0002/Y4f'&TXM\u0012:p[Vs\u0017\u000e\u001e#j[\u0016t7/[8ogR!!r\u0010FD!\u0019\u0011)a!7\u000b\u0002B!QQ\u000eFB\u0013\u0011Q)I!\u0010\u0003\u0011A\u000bw-Z*ju\u0016D\u0001B!\u0014\u0002\u000e\u0002\u000f!qJ\u0001\u0005kJd7\u000f\u0006\u0003\u000b\u000e*M\u0005C\u0002B��\u0015\u001f\u0013i0\u0003\u0003\u000b\u0012\u000e-!aA*fi\"A!QJAH\u0001\b\u0011y%\u0001\u0005ii6d7i\u001c3f)\u0011\u00199N#'\t\u0011\t5\u0013\u0011\u0013a\u0002\u0005\u001f\nA\u0003^3yi\u001e\u0013\u0018-_:dC2,Gk\u001c\"mC\u000e\\G\u0003\u0002BX\u0015?C\u0001B!\u0014\u0002\u0014\u0002\u000f!qJ\u0001\u0014g.L\u0007/S7bO\u0016\u001cuN\u001c<feNLwN\u001c\u000b\u0005\u0005_S)\u000b\u0003\u0005\u0003N\u0005U\u00059\u0001B(\u00031\u0011Xm]5{K&k\u0017mZ3t)\u0011\u0011yKc+\t\u0011\t5\u0013q\u0013a\u0002\u0005\u001f\n!DZ1jY&3w*\u001e;qkR\u001c\u0016N_3O_R\u0014V\rZ;dK\u0012$BAa,\u000b2\"A!QJAM\u0001\b\u0011y%A\u0006qC\u001e,7+\u001b>f\u001fB$H\u0003\u0002F@\u0015oC\u0001B!\u0014\u0002\u001c\u0002\u000f!qJ\u0001\ta\u0006<WmU5{KR!!\u0012\u0011F_\u0011!\u0011i%!(A\u0004\t=\u0013\u0001\u00079bO\u0016\u001c\u0016N_3NCR\u001c\u0007.Z:J[\u0006<WmU5{KR!!q\u0016Fb\u0011!\u0011i%a(A\u0004\t=\u0013A\u00039bO\u0016l\u0015M]4j]R!QQ\tFe\u0011!\u0011i%!)A\u0004\t=\u0013a\u00049bO\u0016|%/[3oi\u0006$\u0018n\u001c8\u0015\t)='R\u001b\t\u0005\u000b[R\t.\u0003\u0003\u000bT\nu\"a\u0004)bO\u0016|%/[3oi\u0006$\u0018n\u001c8\t\u0011\t5\u00131\u0015a\u0002\u0005\u001f\n1\u0002]1hK6\u000b'oZ5ogR!1q\u001bFn\u0011!\u0011i%!*A\u0004\t=\u0013!\u0004<jK^\u0004xN\u001d;XS\u0012$\b\u000e\u0006\u0003\u0005d)\u0005\b\u0002\u0003B'\u0003O\u0003\u001dAa\u0014\u0002\u0017!LG-\u001a(pi&\u001cWm\u001d\u000b\u0005\u0015OTI\u000f\u0005\u0004\u0003\u0006\re'q\u0016\u0005\t\u0005\u001b\nI\u000bq\u0001\u0003P\u0005)A-\u001a7bsR!A1\u0006Fx\u0011!\u0011i%a+A\u0004\t=\u0013!D;tKB\u0013\u0018N\u001c;NK\u0012L\u0017\r\u0006\u0003\u000bh*U\b\u0002\u0003B'\u0003[\u0003\u001dAa\u0014\u0002+A\f'\u000f^5bY\u000e{g\u000e^3oi\u0006cGn\\<fIR!!r\u001dF~\u0011!\u0011i%a,A\u0004\t=\u0013aD<bSR4uN]*fY\u0016\u001cGo\u001c:\u0015\t\r]7\u0012\u0001\u0005\t\u0005\u001b\n\t\fq\u0001\u0003P\u0005AA/[7fu>tW\r\u0006\u0003\u0004X.\u001d\u0001\u0002\u0003B'\u0003g\u0003\u001dAa\u0014\u0002\u0017\t\u0014xn^:fe2\u000bgn\u001a\u000b\u0005\u0007/\\i\u0001\u0003\u0005\u0003N\u0005U\u00069\u0001B(\u0003IAW-\u00193fe\u001a{w\u000e^3s\u000bb$(/Y:\u0015\t\r]72\u0003\u0005\t\u0005\u001b\n9\fq\u0001\u0003P\u0005y!M]8xg\u0016\u0014Hj\\2bi&|g\u000e\u0006\u0003\u0004X.e\u0001\u0002\u0003B'\u0003s\u0003\u001dAa\u0014\u0002\u0019\u0019|'oY3P]\u0016\u0004\u0016mZ3\u0015\t)\u001d8r\u0004\u0005\t\u0005\u001b\nY\fq\u0001\u0003P\u0005Q1o\u0019:pY2\u0004\u0016mZ3\u0015\t)\u001d8R\u0005\u0005\t\u0005\u001b\ni\fq\u0001\u0003P\u0005a\u0011-\u001e;i+N,'O\\1nKR!1q[F\u0016\u0011!\u0011i%a0A\u0004\t=\u0013\u0001D1vi\"\u0004\u0016m]:x_J$G\u0003BBl\u0017cA\u0001B!\u0014\u0002B\u0002\u000f!qJ\u0001\rKb\u0004xN\u001d;G_Jl\u0017\r\u001e\u000b\u0005\u0007/\\9\u0004\u0003\u0005\u0003N\u0005\r\u00079\u0001B(\u00035yW\u000f\u001e9vi\u001a{'/\\1ugR!1RHF&!\u0019!\u0019\u0002b\u0007\f@A!1\u0012IF$\u001b\tY\u0019E\u0003\u0003\fF\u0019m\u0014aA8de&!1\u0012JF\"\u00051yU\u000f\u001e9vi\u001a{'/\\1u\u0011!\u0011i%!2A\u0004\t=C\u0003BBl\u0017\u001fB\u0001B!\u0014\u0002H\u0002\u000f!qJ\u0001\u0017a\u0006<WMU8uCRLwN\\:QKJ\u001cv.\u001e:dKR!a\u0011FF+\u0011!\u0011i%!3A\u0004\t=\u0013a\u00034mCR$XM\\'pI\u0016$Bac\u0017\fhA!1RLF2\u001b\tYyF\u0003\u0003\fb\u0019m\u0014a\u00024mCR$XM\\\u0005\u0005\u0017KZyFA\u0006GY\u0006$H/\u001a8N_\u0012,\u0007\u0002\u0003B'\u0003\u0017\u0004\u001dAa\u0014\u0002\u001f\u0005tgn\u001c;bi&|g\u000eV=qKN$Ba#\u001c\fzA1!q FH\u0017_\u0002Ba#\u001d\fv5\u001112\u000f\u0006\u0005\u0005\u000f4Y(\u0003\u0003\fx-M$AD!o]>$\u0018\r^5p]RK\b/\u001a\u0005\t\u0005\u001b\ni\rq\u0001\u0003P\u0005AQ.\u001a;bI\u0006$\u0018\r\u0006\u0003\nD-}\u0004\u0002\u0003B'\u0003\u001f\u0004\u001dAa\u0014\u0002\u001d\u0019LW\r\u001c3t)>\u0014V-\\8wKR!\u00112JFC\u0011!\u0011i%!5A\u0004\t=\u0013!\u0005:f[>4X-\u00117m\u001b\u0016$\u0018\rZ1uCR!!qVFF\u0011!\u0011i%a5A\u0004\t=\u0013aF:qK\u000eLg-[2SKN,H\u000e\u001e$jY\u0016t\u0017-\\3t)\u0011IIa#%\t\u0011\t5\u0013Q\u001ba\u0002\u0005\u001f\n1B]3oC6,\u0017I]3bgR!1rSFS!\u0019!\u0019\"b;\f\u001aB!12TFQ\u001b\tYiJ\u0003\u0003\f \u001am\u0014A\u0002:f]\u0006lW-\u0003\u0003\f$.u%A\u0003*f]\u0006lW-\u0011:fC\"A!QJAl\u0001\b\u0011y%\u0001\u0006sK:\fW.Z!sK\u0006$Ba#'\f,\"A!QJAm\u0001\u0004\u0011y%A\u0007sK:\fW.\u001a)biR,'O\u001c\u000b\u0005\u0005{\\\t\f\u0003\u0005\u0003N\u0005m\u00079\u0001B(\u0003-\u0019\u0018N\\4mKNCW-\u001a;\u0015\t\t=6r\u0017\u0005\t\u0005\u001b\ni\u000eq\u0001\u0003P\u0005\u0011rN\u001c7z'R\u0014xN\\4msN[Wm^3e)\u0011\u0011yk#0\t\u0011\t5\u0013q\u001ca\u0002\u0005\u001f\na!\u00198hY\u0016\u001cH\u0003BFb\u0017\u000b\u0004\u0002Ba@\u0006T\u0012-RQ\t\u0005\t\u0005\u001b\n\t\u000fq\u0001\u0003P\u0005\u0001B-\u001a7fi\u0016$'i\\8l[\u0006\u00148n\u001d\u000b\u0005\u0017\u0017\\I\u000e\u0005\u0004\u0005\u0014\u0015-8R\u001a\t\u0005\u0017\u001f\\).\u0004\u0002\fR*!12\u001bD>\u0003!\u0011wn\\6nCJ\\\u0017\u0002BFl\u0017#\u0014q\u0002R3mKR,GMQ8pW6\f'o\u001b\u0005\t\u0005\u001b\n\u0019\u000fq\u0001\u0003P\u0005q\u0011\r\u001a3fI\n{wn[7be.\u001cH\u0003BFp\u0017O\u0004b\u0001b\u0005\u0006l.\u0005\b\u0003BFh\u0017GLAa#:\fR\ni\u0011\t\u001a3fI\n{wn[7be.D\u0001B!\u0014\u0002f\u0002\u000f!qJ\u0001\u000eC\u0012$W\r\u001a\"p_.l\u0017M]6\u0015\t-\u00058R\u001e\u0005\t\u0005\u001b\n9\u000f1\u0001\u0003P\u0005\u0001R\u000f\u001d3bi\u0016$'i\\8l[\u0006\u00148n\u001d\u000b\u0005\u0017g\\Y\u0010\u0005\u0004\u0005\u0014\u0015-8R\u001f\t\u0005\u0017\u001f\\90\u0003\u0003\fz.E'aD+qI\u0006$X\r\u001a\"p_.l\u0017M]6\t\u0011\t5\u0013\u0011\u001ea\u0002\u0005\u001f\nq\"\u001e9eCR,GMQ8pW6\f'o\u001b\u000b\u0005\u0017kd\t\u0001\u0003\u0005\u0003N\u0005-\b\u0019\u0001B(\u0003\u001d\u001ax/\u001b;dQ\u0016C\u0018n\u001d;j]\u001e|U\u000f\u001e9viB{G.[2z)>\u0014VM\\1nK>sw+\u001a2\u0015\t\rUDr\u0001\u0005\t\u0007S\ti\u000f1\u0001\u00032\u0005qA+Y:l\u0015N|g\u000eU1sg\u0016\u0014\b\u0003\u0002B\u0010\u0003c\u001cB!!=\u0003*Q\u0011A2\u0002")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/model/TaskJsonParser.class */
public class TaskJsonParser implements Loggable {
    private final FileProvider fs;
    private final long kiloBytes;
    private final long megaBytes;
    private transient Logger logger;
    private volatile transient boolean bitmap$trans$0;

    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
        try {
            return (Object) LambdaDeserialize.bootstrap(MethodHandles.lookup(), "lambdaDeserialize", MethodType.methodType(Object.class, SerializedLambda.class), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$acroFormPolicy$1", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$addedBookmarks$1", MethodType.methodType(AddedBookmark.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$angles$1$adapted", MethodType.methodType(Object.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$angles$2", MethodType.methodType(Map.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$angles$3", MethodType.methodType(Tuple2.class, Tuple2.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$annotationId$1", MethodType.methodType(Integer.TYPE, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$annotationTypes$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$annotationTypes$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$annotationTypes$3", MethodType.methodType(Option.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendAttachmentOperations$1", MethodType.methodType(AppendAttachmentOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendAttachmentOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendDrawOperation$1", MethodType.methodType(Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendDrawOperation$2$adapted", MethodType.methodType(Object.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendDrawOperation$3", MethodType.methodType(List.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendDrawOperation$4$adapted", MethodType.methodType(Object.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendDrawOperation$5", MethodType.methodType(Double.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendDrawOperations$1", MethodType.methodType(AppendDrawOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendDrawOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendEmbeddedFileOperations$1", MethodType.methodType(AppendEmbeddedFileOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendEmbeddedFileOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendFormFieldOperation$1", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendFormFieldOperation$2", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendFormFieldOperations$1", MethodType.methodType(AppendFormFieldOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendFormFieldOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLineOperation$1", MethodType.methodType(Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLineOperation$2$adapted", MethodType.methodType(Object.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLineOperation$3", MethodType.methodType(Double.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLineOperations$1", MethodType.methodType(AppendLineOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLineOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLinkOperation$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLinkOperations$1", MethodType.methodType(AppendLinkOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLinkOperations$2", MethodType.methodType(Option.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLinkOperations$3", MethodType.methodType(AppendLinkOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendLinkOperations$4", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendTextOperation$1", MethodType.methodType(Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendTextOperations$1", MethodType.methodType(AppendTextOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$appendTextOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$bookmarksRegEx$1", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$browserLocation$1", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$defaultOutputFolder$1", MethodType.methodType(File.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteAttachmentOperations$1", MethodType.methodType(DeleteAttachmentOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteAttachmentOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteEmbeddedFileOperations$1", MethodType.methodType(DeleteEmbeddedFileOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteEmbeddedFileOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteFormFieldOperations$1", MethodType.methodType(DeleteFormFieldOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteFormFieldOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteHighlightOperations$1", MethodType.methodType(DeleteHighlightOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteHighlightOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteImageOperations$1", MethodType.methodType(DeleteImageOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteImageOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteLinkOperations$1", MethodType.methodType(DeleteLinkOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deleteLinkOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deletePageOperations$1", MethodType.methodType(DeletePageOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deletePageOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deletedBookmarks$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$deletedBookmarks$2", MethodType.methodType(DeletedBookmark.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editHighlightOperation$1", MethodType.methodType(Nothing$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editHighlightOperations$1", MethodType.methodType(EditHighlightOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editHighlightOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editLinkOperation$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editLinkOperations$1", MethodType.methodType(EditLinkOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editLinkOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editTextOperations$1", MethodType.methodType(EditTextOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$editTextOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$encoding$1$adapted", MethodType.methodType(Object.class, TaskJsonParser.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$encoding$2", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$exceptPages$1$adapted", MethodType.methodType(Object.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fieldsToRemove$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fieldsToRemove$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$filePages$1", MethodType.methodType(Tuple3.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$filePages$2$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$filePages$3", MethodType.methodType(Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fileSources$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fileSources$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fileSources$3$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fileSources$4", MethodType.methodType(Source.class, TaskJsonParser.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fillFormOperations$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fillFormOperations$2", MethodType.methodType(Map.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fillFormOperations$3", MethodType.methodType(FillFormOperation.class, Tuple2.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$flattenMode$1", MethodType.methodType(Nothing$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fontSize$1", MethodType.methodType(Double.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formErrors$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formErrors$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formFieldAlign$1", MethodType.methodType(Option.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formFieldAlign$2", MethodType.methodType(FormFieldAlign$Left$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formFieldKind$1", MethodType.methodType(Nothing$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formFieldOptions$1", MethodType.methodType(Seq.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formFieldOptions$2", MethodType.methodType(Seq.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formWidgets$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$formWidgets$2", MethodType.methodType(Map.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$1$adapted", MethodType.methodType(Object.class, SplitByEveryXPagesParameters.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$10$adapted", MethodType.methodType(Object.class, SplitByPagesParameters.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$100$adapted", MethodType.methodType(Object.class, EditParameters.class, EditTextOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$101$adapted", MethodType.methodType(Object.class, EditParameters.class, AppendTextOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$102$adapted", MethodType.methodType(Object.class, EditParameters.class, AddImageOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$103$adapted", MethodType.methodType(Object.class, EditParameters.class, AddShapeOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$104$adapted", MethodType.methodType(Object.class, EditParameters.class, InsertPageOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$105$adapted", MethodType.methodType(Object.class, EditParameters.class, DeletePageOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$106$adapted", MethodType.methodType(Object.class, EditParameters.class, HighlightTextOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$107$adapted", MethodType.methodType(Object.class, EditParameters.class, HighlightTextOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$108$adapted", MethodType.methodType(Object.class, EditParameters.class, AppendLinkOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$109$adapted", MethodType.methodType(Object.class, EditParameters.class, EditLinkOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$11", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SplitByPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$110$adapted", MethodType.methodType(Object.class, EditParameters.class, DeleteLinkOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$111$adapted", MethodType.methodType(Object.class, EditParameters.class, AppendAttachmentOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$112$adapted", MethodType.methodType(Object.class, EditParameters.class, DeleteAttachmentOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$113$adapted", MethodType.methodType(Object.class, EditParameters.class, AppendEmbeddedFileOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$114$adapted", MethodType.methodType(Object.class, EditParameters.class, DeleteEmbeddedFileOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$115$adapted", MethodType.methodType(Object.class, EditParameters.class, FillFormOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$116$adapted", MethodType.methodType(Object.class, EditParameters.class, AppendFormFieldOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$117$adapted", MethodType.methodType(Object.class, EditParameters.class, DeleteFormFieldOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$118$adapted", MethodType.methodType(Object.class, EditParameters.class, SystemFont.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$119$adapted", MethodType.methodType(Object.class, EditParameters.class, EditHighlightOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$12", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, SplitByPagesParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$120$adapted", MethodType.methodType(Object.class, EditParameters.class, DeleteHighlightOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$121", MethodType.methodType(ListBuffer.class, EditParameters.class, AppendDrawOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$122", MethodType.methodType(ListBuffer.class, EditParameters.class, AppendLineOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$123", MethodType.methodType(ListBuffer.class, EditParameters.class, DeleteImageOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$124", MethodType.methodType(ListBuffer.class, EditParameters.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$125", MethodType.methodType(ListBuffer.class, EditParameters.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$126$adapted", MethodType.methodType(Object.class, EditParameters.class, RotatePageOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$127", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$128", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, EditParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$129", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, EditParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$13$adapted", MethodType.methodType(Object.class, SplitByPagesParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$130$adapted", MethodType.methodType(Object.class, EditParameters.class, AppendTextOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$131$adapted", MethodType.methodType(Object.class, EditParameters.class, AddImageOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$132$adapted", MethodType.methodType(Object.class, EditParameters.class, FillFormOperation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$133$adapted", MethodType.methodType(Object.class, EditParameters.class, SystemFont.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$134", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$135", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, JpegToPdfParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$136", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JpegToPdfParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$137$adapted", MethodType.methodType(Object.class, JpegToPdfParameters.class, PageSize.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$138", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, NupParameters.class, JsonAST.JValue.class, Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$139", MethodType.methodType(Void.TYPE, TaskJsonParser.class, NupParameters.class, JsonAST.JValue.class, Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$14", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SplitByTextContentParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$140", MethodType.methodType(String.class, Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$141", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, PdfToExcelNextParameters.class, Boolean.TYPE, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$142", MethodType.methodType(Void.TYPE, Boolean.TYPE, PdfToExcelNextParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$143", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, PdfToOfficeExcelParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$144", MethodType.methodType(Void.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$145", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, WatermarkParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$146", MethodType.methodType(Void.TYPE, TaskJsonParser.class, WatermarkParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$147", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$148", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ResizePagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$149", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, ResizePagesParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$15", MethodType.methodType(Void.TYPE, TaskJsonParser.class, SplitByTextContentParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$150$adapted", MethodType.methodType(Object.class, ResizePagesParameters.class, Margins.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$151", MethodType.methodType(Option.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$152$adapted", MethodType.methodType(Object.class, ResizePagesParameters.class, PageSize.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$153$adapted", MethodType.methodType(Object.class, ResizePagesParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$154", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$155", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, HtmlToPdfParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$156", MethodType.methodType(Void.TYPE, HtmlToPdfParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$157", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ConvertToGrayscaleParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$158", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, ConvertToGrayscaleParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$159", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, OcrParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$16", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$160", MethodType.methodType(Void.TYPE, TaskJsonParser.class, OcrParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$161", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$162", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, FlattenParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$163", MethodType.methodType(Void.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$164", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, DeleteAnnotationsParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$165", MethodType.methodType(Void.TYPE, TaskJsonParser.class, DeleteAnnotationsParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$166$adapted", MethodType.methodType(Object.class, DeleteAnnotationsParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$167", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ExtractImagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$168", MethodType.methodType(Void.TYPE, ExtractImagesParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$169", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SetMetadataParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$17", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SplitBySizeParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$170", MethodType.methodType(Void.TYPE, TaskJsonParser.class, SetMetadataParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$171", MethodType.methodType(Object.class, HashMap.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$172", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, PdfToPptParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$173", MethodType.methodType(Void.TYPE, PdfToPptParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$174", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, DeskewParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$175", MethodType.methodType(Void.TYPE, TaskJsonParser.class, DeskewParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$176$adapted", MethodType.methodType(Object.class, DeskewParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$177", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, RenameByTextParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$178", MethodType.methodType(Void.TYPE, RenameByTextParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$179", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, EditBookmarksParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$18", MethodType.methodType(Void.TYPE, TaskJsonParser.class, SplitBySizeParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$180", MethodType.methodType(Void.TYPE, EditBookmarksParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$19", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$2", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SplitByEveryXPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$20", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ExtractByOutlineParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$21", MethodType.methodType(Void.TYPE, TaskJsonParser.class, ExtractByOutlineParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$22", MethodType.methodType(AcroFormPolicy.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$23", MethodType.methodType(String.class, TaskJsonParser.class, List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$24", MethodType.methodType(Void.TYPE, List.class, MergeParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$25$adapted", MethodType.methodType(Object.class, MergeParameters.class, Tuple2.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$26", MethodType.methodType(Integer.TYPE, Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$27$adapted", MethodType.methodType(Object.class, PdfMixInput.class, Object.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$28", MethodType.methodType(Void.TYPE, AlternateMixMultipleInputParameters.class, List.class, Seq.class, Seq.class, Seq.class, Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$29", MethodType.methodType(String.class, TaskJsonParser.class, List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$3", MethodType.methodType(Void.TYPE, TaskJsonParser.class, SplitByEveryXPagesParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$30", MethodType.methodType(Void.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$31", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$32", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ExtractPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$33", MethodType.methodType(Void.TYPE, TaskJsonParser.class, ExtractPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$34", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ExtractPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$35", MethodType.methodType(Void.TYPE, TaskJsonParser.class, ExtractPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$36", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$37$adapted", MethodType.methodType(Object.class, RotateParameters.class, Tuple3.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$38", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, RotateParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$39", MethodType.methodType(Void.TYPE, TaskJsonParser.class, RotateParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$4$adapted", MethodType.methodType(Object.class, SimpleSplitParameters.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$40", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$41", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ExtractTextParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$42", MethodType.methodType(Void.TYPE, TaskJsonParser.class, ExtractTextParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$43", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, OptimizeParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$44", MethodType.methodType(Void.TYPE, TaskJsonParser.class, OptimizeParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$45$adapted", MethodType.methodType(Object.class, OptimizeParameters.class, Optimization.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$46", MethodType.methodType(Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$47", MethodType.methodType(Double.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$48", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$49", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, RepairParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$5", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SimpleSplitParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$50", MethodType.methodType(Void.TYPE, TaskJsonParser.class, RepairParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$51", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$52", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, PdfToJpegParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$53", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, PdfToJpegParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$54$adapted", MethodType.methodType(Object.class, PdfToJpegParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$55", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, AbstractPdfToMultipleImageParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$56", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, AbstractPdfToMultipleImageParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$57$adapted", MethodType.methodType(Object.class, AbstractPdfToMultipleImageParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$58", MethodType.methodType(String.class, TaskJsonParser.class, List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$59", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, PdfToSingleTiffParameters.class, Option.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$6", MethodType.methodType(Void.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$60", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, PdfToSingleTiffParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$61$adapted", MethodType.methodType(Object.class, PdfToSingleTiffParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$62", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, DecryptParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$63", MethodType.methodType(Void.TYPE, TaskJsonParser.class, DecryptParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$64", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$65$adapted", MethodType.methodType(Object.class, EncryptParameters.class, PdfAccessPermission.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$66", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, EncryptParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$67", MethodType.methodType(Void.TYPE, TaskJsonParser.class, EncryptParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$68", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$69", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SetHeaderFooterParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$7", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, ExtractPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$70", MethodType.methodType(Void.TYPE, TaskJsonParser.class, SetHeaderFooterParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$71$adapted", MethodType.methodType(Object.class, SetHeaderFooterParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$72", MethodType.methodType(Void.TYPE, SetHeaderFooterParameters.class, Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$73$adapted", MethodType.methodType(Object.class, SetHeaderFooterParameters.class, Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$74", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$75", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SetHeaderFooterParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$76", MethodType.methodType(Void.TYPE, TaskJsonParser.class, SetHeaderFooterParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$77$adapted", MethodType.methodType(Object.class, SetHeaderFooterParameters.class, Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$78$adapted", MethodType.methodType(Object.class, SetHeaderFooterParameters.class, BatesSequence.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$79", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$8", MethodType.methodType(Void.TYPE, TaskJsonParser.class, ExtractPagesParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$80", MethodType.methodType(String.class, TaskJsonParser.class, List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$81", MethodType.methodType(Void.TYPE, TaskJsonParser.class, List.class, CombineReorderParameters.class, JsonAST.JValue.class))).dynamicInvoker().invoke(serializedLambda) /* invoke-custom */;
        } catch (IllegalArgumentException e) {
            return (Object) LambdaDeserialize.bootstrap(MethodHandles.lookup(), "lambdaDeserialize", MethodType.methodType(Object.class, SerializedLambda.class), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$82$adapted", MethodType.methodType(Object.class, CombineReorderParameters.class, Tuple2.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$83$adapted", MethodType.methodType(Object.class, CombineReorderParameters.class, Tuple3.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$84", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, SplitDownTheMiddleParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$85", MethodType.methodType(Void.TYPE, TaskJsonParser.class, SplitDownTheMiddleParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$86", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$87$adapted", MethodType.methodType(Object.class, SplitDownTheMiddleParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$88", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, CropParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$89", MethodType.methodType(Void.TYPE, TaskJsonParser.class, CropParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$9$adapted", MethodType.methodType(Object.class, ExtractPagesParameters.class, PageRange.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$90$adapted", MethodType.methodType(Object.class, CropParameters.class, Tuple2.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$91", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$92", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, PdfToDocParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$93", MethodType.methodType(Void.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$94", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, PdfToOfficeWordParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$95", MethodType.methodType(Void.TYPE, TaskJsonParser.class, PdfToOfficeWordParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$96", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, DocToPdfParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$97", MethodType.methodType(Void.TYPE, DocToPdfParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$98", MethodType.methodType(TaskParameters.class, TaskJsonParser.class, EditParameters.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$fromJson$99", MethodType.methodType(Void.TYPE, TaskJsonParser.class, JsonAST.JValue.class, EditParameters.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$highlightKind$1", MethodType.methodType(HighlightType$Highlight$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$highlightTextOperation$1", MethodType.methodType(Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$highlightTextOperations$1", MethodType.methodType(HighlightTextOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$highlightTextOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$htmlCode$1", MethodType.methodType(Option.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$imageFormat$1", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$imageOperations$1", MethodType.methodType(AddImageOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$imageOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$imageSource$1", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$imageSourceOpt$1", MethodType.methodType(Source.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$insertPageOperations$1", MethodType.methodType(InsertPageOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$insertPageOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$linkKind$1", MethodType.methodType(Nothing$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$marginInInches$1", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$metadata$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$metadata$2", MethodType.methodType(Map.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$name$1", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$outlinePolicy$1", MethodType.methodType(OutlinePolicy.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$outlinePolicy$2", MethodType.methodType(OutlinePolicy.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$outputFormats$1", MethodType.methodType(Option.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$outputFormats$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$outputFormats$3", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$outputFormats$4", MethodType.methodType(Option.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pageRangesSeq$1", MethodType.methodType(Seq.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pageRotationsPerSource$1", MethodType.methodType(Tuple3.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pageSizeFromUnitDimensions$1", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pageSizeMatchesImageSize$1", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pageSizeOpt$1", MethodType.methodType(PageSize.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pagesAreaCropOpt$1", MethodType.methodType(RectangularBox.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pattern$1", MethodType.methodType(Option.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pattern$2$adapted", MethodType.methodType(String.class, Object.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pattern$3", MethodType.methodType(Nothing$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfFileSources$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfFileSources$2$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfFileSources$3", MethodType.methodType(PdfSource.class, TaskJsonParser.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfFileSourcesIndexed$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfFileSourcesIndexed$2$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfFileSourcesIndexed$3", MethodType.methodType(Tuple2.class, TaskJsonParser.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfOrImageFileSources$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfOrImageFileSources$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfOrImageFileSources$3$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfOrImageFileSources$4", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfOrImageFileSources$5$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$pdfOrImageFileSources$6", MethodType.methodType(TaskSource.class, TaskJsonParser.class, List.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$permissions$1", MethodType.methodType(Seq.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$permissions$2", MethodType.methodType(IterableOnce.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$position$1", MethodType.methodType(Nothing$.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$renameAreas$1", MethodType.methodType(RenameArea.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotatePageOperation$1", MethodType.methodType(Rotation.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotatePageOperations$1", MethodType.methodType(RotatePageOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotatePageOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotationDegrees$1$adapted", MethodType.methodType(Option.class, TaskJsonParser.class, Object.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotationDegreesSeq$1", MethodType.methodType(Option.class, TaskJsonParser.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotations$1$adapted", MethodType.methodType(Object.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotations$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$rotations$3$adapted", MethodType.methodType(Option.class, TaskJsonParser.class, Object.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$shapeOperation$1", MethodType.methodType(Option.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$shapeOperation$2", MethodType.methodType(Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$shapeOperations$1", MethodType.methodType(AddShapeOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$shapeOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$specificResultFilenames$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$specificResultFilenames$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$startsWithPrefix$1", MethodType.methodType(String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$strikethoughTextOperations$1", MethodType.methodType(HighlightTextOperation.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$strikethoughTextOperations$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$strikethroughTextOperation$1", MethodType.methodType(Color.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$systemFonts$1", MethodType.methodType(SystemFont.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$systemFonts$2", MethodType.methodType(Nil$.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$tabOrder$1", MethodType.methodType(String.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$tabOrder$2", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$toRectangularBox$1", MethodType.methodType(Nothing$.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$toRectangularBoxList$1", MethodType.methodType(RectangularBox.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$toRectangularBoxOpt$1", MethodType.methodType(RectangularBox.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$tocPolicy$1", MethodType.methodType(ToCPolicy.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$tocPolicy$2", MethodType.methodType(ToCPolicy.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$updatedBookmarks$1", MethodType.methodType(UpdatedBookmark.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$urls$1", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$urls$2$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$urls$3", MethodType.methodType(Option.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$urls$4", MethodType.methodType(List.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$urls$5", MethodType.methodType(List.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$urls$6", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$urls$7$adapted", MethodType.methodType(Object.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$validEncoding$1", MethodType.methodType(String.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$watermarks$1", MethodType.methodType(Watermark.class, TaskJsonParser.class, JsonAST.JValue.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withOptionalStoredFiles$1$adapted", MethodType.methodType(Object.class, MultipleSourceParameters.class, Source.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withOutput$1", MethodType.methodType(File.class, TaskJsonParser.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withOutput$2$adapted", MethodType.methodType(Object.class, SingleOrMultipleOutputTaskParameters.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withOutput$3", MethodType.methodType(File.class, TaskJsonParser.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withOutput$4$adapted", MethodType.methodType(Object.class, MultipleOutputTaskParameters.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withOutput$5", MethodType.methodType(File.class, String.class, File.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withOutput$6", MethodType.methodType(File.class, TaskJsonParser.class, String.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withStoredFiles$1$adapted", MethodType.methodType(Object.class, String.class, MultiplePdfSourceTaskParameters.class, PdfSource.class)), MethodHandles.lookup().findStatic(TaskJsonParser.class, "$anonfun$withStoredFiles$2$adapted", MethodType.methodType(Object.class, MultipleSourceParameters.class, Source.class))).dynamicInvoker().invoke(e) /* invoke-custom */;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8, types: [code.model.TaskJsonParser] */
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

    public TaskJsonParser(final FileProvider fs) {
        this.fs = fs;
        Loggable.$init$(this);
        this.kiloBytes = 1000L;
        this.megaBytes = kiloBytes() * 1000;
    }

    /* JADX WARN: Removed duplicated region for block: B:152:0x06f1 A[PHI: r88 r89
  0x06f1: PHI (r88v1 boolean) = (r88v0 boolean), (r88v2 boolean) binds: [B:146:0x06bc, B:148:0x06db] A[DONT_GENERATE, DONT_INLINE]
  0x06f1: PHI (r89v1 scala.Some) = (r89v0 scala.Some), (r89v2 scala.Some) binds: [B:146:0x06bc, B:148:0x06db] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.sejda.model.parameter.base.TaskParameters fromJson(final net.liftweb.json.JsonAST.JValue r31) {
        /*
            Method dump skipped, instructions count: 4674
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.model.TaskJsonParser.fromJson(net.liftweb.json.JsonAST$JValue):org.sejda.model.parameter.base.TaskParameters");
    }

    public static final /* synthetic */ void $anonfun$fromJson$13(final SplitByPagesParameters p$4, final PageRange range) {
        if (!range.isUnbounded()) {
            p$4.addPage(Predef$.MODULE$.int2Integer(range.getEnd()));
        }
        if (range.getStart() == 1 || range.getStart() == range.getEnd()) {
            return;
        }
        p$4.addPage(Predef$.MODULE$.int2Integer(range.getStart() - 1));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$fromJson$25(final MergeParameters p$8, final Tuple2 x0$1) throws MatchError {
        if (x0$1 != null) {
            TaskSource file = (TaskSource) x0$1._1();
            if (file instanceof PdfSource) {
                p$8.addInput(new PdfMergeInput((PdfSource) file));
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
            } else if (file instanceof Source) {
                p$8.addInput(new ImageMergeInput((Source) file));
                BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
            } else {
                BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
            }
            BoxedUnit boxedUnit4 = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$1);
    }

    private static final PdfMixInput inputs$1(final int i, final List files$2, final Seq reversi$1, final Seq step$1, final Seq repeats$1) {
        PdfMixInput input = new PdfMixInput((PdfSource) files$2.apply(i), BoxesRunTime.unboxToBoolean(reversi$1.apply(i)), BoxesRunTime.unboxToInt(step$1.apply(i)));
        ((Option) repeats$1.lift().apply(BoxesRunTime.boxToInteger(i))).foreach(repeatForever -> {
            input.setRepeatForever(BoxesRunTime.unboxToBoolean(repeatForever));
            return BoxedUnit.UNIT;
        });
        return input;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$fromJson$37(final RotateParameters p$12, final Tuple3 x0$2) throws MatchError {
        if (x0$2 != null) {
            int fileIndex = BoxesRunTime.unboxToInt(x0$2._1());
            int page = BoxesRunTime.unboxToInt(x0$2._2());
            int rotation = BoxesRunTime.unboxToInt(x0$2._3());
            p$12.addPageRangePerSource(fileIndex, PageRange.one(page), Rotation.getRotation(rotation));
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$2);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$fromJson$82(final CombineReorderParameters p$23, final Tuple2 x0$3) throws MatchError {
        if (x0$3 != null) {
            TaskSource file = (TaskSource) x0$3._1();
            if (file instanceof PdfSource) {
                p$23.addInput(new PdfMergeInput((PdfSource) file));
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
            } else if (file instanceof Source) {
                p$23.addInput(new ImageMergeInput((Source) file));
                BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
            } else {
                BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
            }
            BoxedUnit boxedUnit4 = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$3);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$fromJson$83(final CombineReorderParameters p$23, final Tuple3 x0$4) throws MatchError {
        if (x0$4 != null) {
            int fileId = BoxesRunTime.unboxToInt(x0$4._1());
            int page = BoxesRunTime.unboxToInt(x0$4._2());
            int degrees = BoxesRunTime.unboxToInt(x0$4._3());
            p$23.addPage(fileId, page, Rotation.getRotation(degrees));
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$4);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$fromJson$90(final CropParameters p$25, final Tuple2 x0$5) throws MatchError {
        if (x0$5 != null) {
            String key = (String) x0$5._1();
            RectangularBox value = (RectangularBox) x0$5._2();
            p$25.addCropArea(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(key)), value);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$5);
    }

    public boolean pdfFileSources$default$2() {
        return true;
    }

    public List<PdfSource<File>> pdfFileSources(final JsonAST.JValue json, final boolean unique) {
        List files = JsonExtract$.MODULE$.toList(json.$bslash("files"), v -> {
            return JsonExtract$.MODULE$.toStringOr(v, "");
        }).filter(x$3 -> {
            return BoxesRunTime.boxToBoolean($anonfun$pdfFileSources$2(x$3));
        });
        if (unique) {
            files = (List) files.distinct();
        }
        return files.map(id -> {
            return this.fs.getPdfSource(id);
        });
    }

    public static final /* synthetic */ boolean $anonfun$pdfFileSources$2(final String x$3) {
        return !x$3.trim().isEmpty();
    }

    public boolean pdfOrImageFileSources$default$2() {
        return true;
    }

    public List<TaskSource<File>> pdfOrImageFileSources(final JsonAST.JValue json, final boolean unique) {
        List images = ((List) JsonExtract$.MODULE$.toListOption(json.$bslash("images"), v -> {
            return JsonExtract$.MODULE$.toStringOr(v, "");
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        })).filter(x$4 -> {
            return BoxesRunTime.boxToBoolean($anonfun$pdfOrImageFileSources$3(x$4));
        });
        List files = JsonExtract$.MODULE$.toList(json.$bslash("files"), v2 -> {
            return JsonExtract$.MODULE$.toStringOr(v2, "");
        }).filter(x$5 -> {
            return BoxesRunTime.boxToBoolean($anonfun$pdfOrImageFileSources$5(x$5));
        });
        if (unique) {
            files = (List) files.distinct();
        }
        return files.map(id -> {
            return images.contains(id) ? this.fs.getSource(id) : this.fs.getPdfSource(id);
        });
    }

    public static final /* synthetic */ boolean $anonfun$pdfOrImageFileSources$3(final String x$4) {
        return !x$4.trim().isEmpty();
    }

    public static final /* synthetic */ boolean $anonfun$pdfOrImageFileSources$5(final String x$5) {
        return !x$5.trim().isEmpty();
    }

    public boolean fileSources$default$2() {
        return true;
    }

    public List<Source<File>> fileSources(final JsonAST.JValue json, final boolean unique) {
        List files = ((List) JsonExtract$.MODULE$.toListOption(json.$bslash("files"), v -> {
            return JsonExtract$.MODULE$.toStringOr(v, "");
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        })).filter(x$6 -> {
            return BoxesRunTime.boxToBoolean($anonfun$fileSources$3(x$6));
        });
        if (unique) {
            files = (List) files.distinct();
        }
        return files.map(id -> {
            return this.fs.getSource(id);
        });
    }

    public static final /* synthetic */ boolean $anonfun$fileSources$3(final String x$6) {
        return !x$6.trim().isEmpty();
    }

    public boolean pdfFileSourcesIndexed$default$2() {
        return true;
    }

    public List<Tuple2<String, PdfSource<File>>> pdfFileSourcesIndexed(final JsonAST.JValue json, final boolean unique) {
        List files = JsonExtract$.MODULE$.toList(json.$bslash("files"), v -> {
            return JsonExtract$.MODULE$.toStringOr(v, "");
        }).filter(x$7 -> {
            return BoxesRunTime.boxToBoolean($anonfun$pdfFileSourcesIndexed$2(x$7));
        });
        if (unique) {
            files = (List) files.distinct();
        }
        return files.map(id -> {
            return new Tuple2(id, this.fs.getPdfSource(id));
        });
    }

    public static final /* synthetic */ boolean $anonfun$pdfFileSourcesIndexed$2(final String x$7) {
        return !x$7.trim().isEmpty();
    }

    public TaskParameters withStoredFiles(final SinglePdfSourceTaskParameters p, final Function0<TaskParameters> block, final JsonAST.JValue json) {
        List files = pdfFileSources(json, pdfFileSources$default$2());
        if (files.isEmpty()) {
            throw new RuntimeException("No input files found");
        }
        p.setSource((PdfSource) files.head());
        return (TaskParameters) block.apply();
    }

    public boolean withStoredFiles$default$2() {
        return true;
    }

    public String withStoredFiles$default$3() {
        return "";
    }

    public TaskParameters withStoredFiles(final MultiplePdfSourceTaskParameters p, final boolean unique, final String ownerPassword, final Function0<TaskParameters> block, final JsonAST.JValue json) {
        List files = pdfFileSources(json, unique);
        if (files.isEmpty()) {
            throw new RuntimeException("No input files found");
        }
        files.foreach(file -> {
            $anonfun$withStoredFiles$1(ownerPassword, p, file);
            return BoxedUnit.UNIT;
        });
        return (TaskParameters) block.apply();
    }

    public static final /* synthetic */ void $anonfun$withStoredFiles$1(final String ownerPassword$1, final MultiplePdfSourceTaskParameters p$48, final PdfSource file) {
        if (!ownerPassword$1.isEmpty()) {
            file.setPassword(ownerPassword$1);
        }
        p$48.addSource(file);
    }

    public TaskParameters withStoredFiles(final MultipleSourceParameters p, final Function0<TaskParameters> block, final JsonAST.JValue json) {
        List files = fileSources(json, true);
        if (files.isEmpty()) {
            throw new RuntimeException("No input files found");
        }
        files.foreach(file -> {
            p.addSource(file);
            return BoxedUnit.UNIT;
        });
        return (TaskParameters) block.apply();
    }

    public TaskParameters withOptionalStoredFiles(final MultipleSourceParameters p, final Function0<TaskParameters> block, final JsonAST.JValue json) {
        List files = fileSources(json, true);
        files.foreach(file -> {
            p.addSource(file);
            return BoxedUnit.UNIT;
        });
        return (TaskParameters) block.apply();
    }

    public void lenientIfBatch(final MultiplePdfSourceParameters p) {
        if (p.getSourceList().size() > 1) {
            p.setLenient(true);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.sejda.model.parameter.base.TaskParameters withOutput(final org.sejda.model.parameter.base.SingleOrMultipleOutputTaskParameters r8, final scala.Function0<scala.runtime.BoxedUnit> r9, final net.liftweb.json.JsonAST.JValue r10) {
        /*
            r7 = this;
            r0 = r8
            r13 = r0
            r0 = r13
            boolean r0 = r0 instanceof org.sejda.model.parameter.base.AbstractPdfOutputParameters
            if (r0 == 0) goto L1f
            r0 = r13
            org.sejda.model.parameter.base.AbstractPdfOutputParameters r0 = (org.sejda.model.parameter.base.AbstractPdfOutputParameters) r0
            r14 = r0
            r0 = r14
            r1 = 1
            r0.setCompress(r1)
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto L29
        L1f:
            goto L22
        L22:
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto L29
        L29:
            r0 = r7
            r1 = r10
            scala.Option r0 = r0.defaultOutputFile(r1)
            r15 = r0
            r0 = r15
            boolean r0 = r0 instanceof scala.Some
            if (r0 == 0) goto L7d
            r0 = r15
            scala.Some r0 = (scala.Some) r0
            r16 = r0
            r0 = r16
            java.lang.Object r0 = r0.value()
            java.lang.String r0 = (java.lang.String) r0
            r17 = r0
            scala.collection.StringOps$ r0 = scala.collection.StringOps$.MODULE$
            scala.Predef$ r1 = scala.Predef$.MODULE$
            r2 = r17
            java.lang.String r2 = r2.trim()
            java.lang.String r1 = r1.augmentString(r2)
            boolean r0 = r0.nonEmpty$extension(r1)
            if (r0 == 0) goto L7a
            r0 = r8
            code.util.pdf.TempFileOrDirTaskOutput r1 = new code.util.pdf.TempFileOrDirTaskOutput
            r2 = r1
            java.io.File r3 = new java.io.File
            r4 = r3
            r5 = r17
            r4.<init>(r5)
            r2.<init>(r3)
            r0.setOutput(r1)
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto La9
        L7a:
            goto L80
        L7d:
            goto L80
        L80:
            r0 = r7
            r1 = r10
            scala.Option r0 = r0.defaultOutputFolder(r1)
            r1 = r7
            org.sejda.model.parameter.base.TaskParameters r1 = () -> { // scala.Function0.apply():java.lang.Object
                return $anonfun$withOutput$1(r1);
            }
            java.lang.Object r0 = r0.getOrElse(r1)
            java.io.File r0 = (java.io.File) r0
            r18 = r0
            r0 = r8
            code.util.pdf.TempFileOrDirTaskOutput r1 = new code.util.pdf.TempFileOrDirTaskOutput
            r2 = r1
            r3 = r18
            r2.<init>(r3)
            r0.setOutput(r1)
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto La9
        La9:
            r0 = r8
            r1 = r7
            r2 = r10
            org.sejda.model.output.ExistingOutputPolicy r1 = r1.existingOutputPolicy(r2)
            r0.setExistingOutputPolicy(r1)
            r0 = r7
            r1 = r10
            scala.Option r0 = r0.outputFilenamePattern(r1)
            r1 = r8
            org.sejda.model.parameter.base.TaskParameters r1 = (v1) -> { // scala.Function1.apply(java.lang.Object):java.lang.Object
                return $anonfun$withOutput$2$adapted(r1, v1);
            }
            scala.Option r0 = r0.map(r1)
            r0 = r8
            org.sejda.model.output.TaskOutput r0 = r0.getOutput()
            r1 = r7
            code.model.FileProvider r1 = r1.fs
            org.sejda.model.encryption.EncryptionAtRestPolicy r1 = r1.encryption()
            r0.setEncryptionAtRestPolicy(r1)
            r0 = r9
            r0.apply$mcV$sp()
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.model.TaskJsonParser.withOutput(org.sejda.model.parameter.base.SingleOrMultipleOutputTaskParameters, scala.Function0, net.liftweb.json.JsonAST$JValue):org.sejda.model.parameter.base.TaskParameters");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TaskParameters withOutput(final MultipleOutputTaskParameters p, final Function0<BoxedUnit> block, final JsonAST.JValue json) {
        if (p instanceof AbstractPdfOutputParameters) {
            ((AbstractPdfOutputParameters) p).setCompress(true);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        File destination = (File) defaultOutputFolder(json).getOrElse(() -> {
            return this.fs.createTmpDir();
        });
        p.setOutput(new TempDirTaskOutput(destination));
        p.setExistingOutputPolicy(existingOutputPolicy(json));
        outputFilenamePattern(json).map(outputPrefix -> {
            p.setOutputPrefix(outputPrefix);
            return BoxedUnit.UNIT;
        });
        p.getOutput().setEncryptionAtRestPolicy(this.fs.encryption());
        block.apply$mcV$sp();
        return p;
    }

    public String filenameWithSuffix(final String filename, final String suffix) {
        return new StringBuilder(0).append(FilenameUtils.getBaseName(filename)).append(suffix).toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TaskParameters withOutput(final SingleOutputTaskParameters p, final Option<String> outputFilename, final Function0<BoxedUnit> block, final JsonAST.JValue json) {
        File file;
        if (p instanceof AbstractPdfOutputParameters) {
            ((AbstractPdfOutputParameters) p).setCompress(true);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        Some someDefaultOutputFile = defaultOutputFile(json);
        if (someDefaultOutputFile instanceof Some) {
            String filePath = (String) someDefaultOutputFile.value();
            file = new File(filePath);
        } else {
            String filename = this.fs.filenameOrDefault(outputFilename);
            file = (File) defaultOutputFolder(json).map(folder -> {
                return new File(folder, filename);
            }).getOrElse(() -> {
                return this.fs.createTmpFile(filename);
            });
        }
        File destination = file;
        p.setOutput(new PdfTempFileTaskOutput(destination));
        p.setExistingOutputPolicy(existingOutputPolicy(json));
        p.getOutput().setEncryptionAtRestPolicy(this.fs.encryption());
        block.apply$mcV$sp();
        return p;
    }

    public String encoding(final JsonAST.JValue json) {
        return (String) JsonExtract$.MODULE$.toStringOption(json.$bslash("encoding")).filter(enc -> {
            return BoxesRunTime.boxToBoolean(this.validEncoding(enc));
        }).getOrElse(() -> {
            return "utf8";
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean validEncoding(final String enc) {
        return Try$.MODULE$.apply(() -> {
            return new String((byte[]) Array$.MODULE$.apply(Nil$.MODULE$, ClassTag$.MODULE$.Byte()), enc);
        }).isSuccess();
    }

    public Option<Rotation> rotationDegrees(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOption(json.$bslash("degrees")).flatMap(degrees -> {
            return this.toRotation(BoxesRunTime.unboxToInt(degrees));
        });
    }

    public boolean mergeOutputs(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("mergeOutputs"), true);
    }

    public List<Rotation> rotations(final JsonAST.JValue json) {
        return ((List) JsonExtract$.MODULE$.toListOption(json.$bslash("rotations"), v -> {
            return BoxesRunTime.boxToInteger($anonfun$rotations$1(v));
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        })).flatMap(degrees -> {
            return this.toRotation(BoxesRunTime.unboxToInt(degrees));
        });
    }

    public static final /* synthetic */ int $anonfun$rotations$1(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toInt(v);
    }

    public Seq<Rotation> rotationDegreesSeq(final JsonAST.JValue json) {
        JsonAST.JString jString$bslash = json.$bslash("rotations");
        if (!(jString$bslash instanceof JsonAST.JString)) {
            return package$.MODULE$.Seq().empty();
        }
        String s = jString$bslash.s();
        return Predef$.MODULE$.copyArrayToImmutableIndexedSeq(ArrayOps$.MODULE$.flatMap$extension(Predef$.MODULE$.refArrayOps(s.split(",")), i -> {
            return this.toRotation(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(i)));
        }, ClassTag$.MODULE$.apply(Rotation.class)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Option<Rotation> toRotation(final int degrees) {
        switch (degrees) {
            case 0:
            case 360:
                return new Some(Rotation.DEGREES_0);
            case 90:
                return new Some(Rotation.DEGREES_90);
            case 180:
                return new Some(Rotation.DEGREES_180);
            case 270:
                return new Some(Rotation.DEGREES_270);
            default:
                return None$.MODULE$;
        }
    }

    public PredefinedSetOfPages flipped(final PredefinedSetOfPages x) {
        return PredefinedSetOfPages.EVEN_PAGES.equals(x) ? PredefinedSetOfPages.ODD_PAGES : PredefinedSetOfPages.ODD_PAGES.equals(x) ? PredefinedSetOfPages.EVEN_PAGES : x;
    }

    public PredefinedSetOfPages predefinedSetOfPages(final JsonAST.JValue json) {
        boolean z = false;
        JsonAST.JString jString = null;
        JsonAST.JValue jValue$bslash = json.$bslash("pages");
        if (jValue$bslash instanceof JsonAST.JString) {
            z = true;
            jString = (JsonAST.JString) jValue$bslash;
            if ("even".equals(jString.s())) {
                return PredefinedSetOfPages.EVEN_PAGES;
            }
        }
        return (z && "odd".equals(jString.s())) ? PredefinedSetOfPages.ODD_PAGES : (z && "all".equals(jString.s())) ? PredefinedSetOfPages.ALL_PAGES : PredefinedSetOfPages.NONE;
    }

    public Seq<PageRange> specificPages(final JsonAST.JValue json) {
        JsonAST.JString jString$bslash = json.$bslash("pages");
        if (jString$bslash instanceof JsonAST.JString) {
            String ps = jString$bslash.s();
            return PageRanges$.MODULE$.fromString(ps);
        }
        if (!(jString$bslash instanceof JsonAST.JInt)) {
            return package$.MODULE$.Seq().empty();
        }
        BigInt ps2 = ((JsonAST.JInt) jString$bslash).num();
        return PageRanges$.MODULE$.fromString(ps2.toString());
    }

    public Option<Object> step(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOption(json.$bslash("step"));
    }

    public boolean mergeResults(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("mergeResults"), false);
    }

    public Seq<Seq<PageRange>> pageRangesSeq(final JsonAST.JValue json) {
        JsonAST.JArray jArray$bslash = json.$bslash("pages");
        if (jArray$bslash instanceof JsonAST.JArray) {
            List pageSelections = jArray$bslash.arr();
            return pageSelections.map(x0$1 -> {
                if (x0$1 instanceof JsonAST.JString) {
                    String ps = ((JsonAST.JString) x0$1).s();
                    return PageRanges$.MODULE$.fromString(ps);
                }
                if (x0$1 instanceof JsonAST.JInt) {
                    BigInt ps2 = ((JsonAST.JInt) x0$1).num();
                    return PageRanges$.MODULE$.fromString(ps2.toString());
                }
                return package$.MODULE$.Seq().empty();
            });
        }
        return package$.MODULE$.Seq().empty();
    }

    public Seq<PageRange> excludedPages(final JsonAST.JValue json) {
        JsonAST.JString jString$bslash = json.$bslash("excludedPages");
        if (jString$bslash instanceof JsonAST.JString) {
            String ps = jString$bslash.s();
            return PageRanges$.MODULE$.fromString(ps);
        }
        if (!(jString$bslash instanceof JsonAST.JInt)) {
            return package$.MODULE$.Seq().empty();
        }
        BigInt ps2 = ((JsonAST.JInt) jString$bslash).num();
        return PageRanges$.MODULE$.fromString(ps2.toString());
    }

    public Seq<PageRange> pageRanges(final JsonAST.JValue json) {
        JsonAST.JString jString$bslash = json.$bslash("pages");
        if (jString$bslash instanceof JsonAST.JString) {
            String ps = jString$bslash.s();
            return PageRanges$.MODULE$.fromString(ps);
        }
        if (!(jString$bslash instanceof JsonAST.JInt)) {
            return package$.MODULE$.Seq().empty();
        }
        BigInt ps2 = ((JsonAST.JInt) jString$bslash).num();
        return PageRanges$.MODULE$.fromString(ps2.toString());
    }

    public int pageNum(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toInt(json.$bslash("pages"));
    }

    public Seq<Object> steps(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOrList(json.$bslash("step"), 1);
    }

    public Seq<Object> reverse(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolList(json.$bslash("reverse"));
    }

    public Seq<Object> repeat(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolList(json.$bslash("repeat"));
    }

    public String ownerPassword(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOr(json.$bslash("ownerPassword"), "");
    }

    public String userPassword(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOr(json.$bslash("userPassword"), "");
    }

    public PdfEncryption encryptionAlgorithm(final JsonAST.JValue json) {
        boolean z = false;
        JsonAST.JString jString = null;
        JsonAST.JValue jValue$bslash = json.$bslash("encryptionAlgorithm");
        if (jValue$bslash instanceof JsonAST.JString) {
            z = true;
            jString = (JsonAST.JString) jValue$bslash;
            if ("aes_128".equals(jString.s())) {
                return PdfEncryption.AES_ENC_128;
            }
        }
        if (z && "rc4_128".equals(jString.s())) {
            return PdfEncryption.STANDARD_ENC_128;
        }
        if (z && "aes_256".equals(jString.s())) {
            return PdfEncryption.AES_ENC_256;
        }
        throw new RuntimeException(new StringBuilder(34).append("Unknown pdf encryption algorithm: ").append(jValue$bslash).toString());
    }

    public Seq<PdfAccessPermission> permissions(final JsonAST.JValue json) {
        return (Seq) ((IterableOps) JsonExtract$.MODULE$.toStringListOpt(json.$bslash("permissions")).getOrElse(() -> {
            return package$.MODULE$.Seq().empty();
        })).flatMap(x0$1 -> {
            switch (x0$1 == null ? 0 : x0$1.hashCode()) {
                case -1068795718:
                    if ("modify".equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.MODIFY, Nil$.MODULE$);
                    }
                    break;
                case -1057595581:
                    if ("degradedprinting".equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.PRINT, Nil$.MODULE$);
                    }
                    break;
                case -373408302:
                    if ("assemble".equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.ASSEMBLE, Nil$.MODULE$);
                    }
                    break;
                case 3059573:
                    if ("copy".equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.COPY_AND_EXTRACT, Nil$.MODULE$);
                    }
                    break;
                case 3143043:
                    if ("fill".equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.FILL_FORMS, Nil$.MODULE$);
                    }
                    break;
                case 106934957:
                    if (PDWindowsLaunchParams.OPERATION_PRINT.equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.PRINT, new $colon.colon(PdfAccessPermission.DEGRADATED_PRINT, Nil$.MODULE$));
                    }
                    break;
                case 797371940:
                    if ("screenreaders".equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.EXTRACTION_FOR_DISABLES, Nil$.MODULE$);
                    }
                    break;
                case 1444489770:
                    if ("modifyannotations".equals(x0$1)) {
                        return new $colon.colon(PdfAccessPermission.ANNOTATION, Nil$.MODULE$);
                    }
                    break;
            }
            throw new RuntimeException(new StringBuilder(31).append("Unknown pdf access permission: ").append(x0$1).toString());
        });
    }

    public OutlinePolicy outlinePolicy(final JsonAST.JValue json) {
        return (OutlinePolicy) JsonExtract$.MODULE$.toStringOption(json.$bslash("outlinePolicy")).map(s -> {
            return OutlinePolicy.valueOf(s.toUpperCase());
        }).getOrElse(() -> {
            return OutlinePolicy.RETAIN;
        });
    }

    public ToCPolicy tocPolicy(final JsonAST.JValue json) {
        return (ToCPolicy) JsonExtract$.MODULE$.toStringOption(json.$bslash("tocPolicy")).map(s -> {
            return ToCPolicy.valueOf(s.toUpperCase());
        }).getOrElse(() -> {
            return ToCPolicy.NONE;
        });
    }

    public boolean filenameFooter(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("filenameFooter"), false);
    }

    public boolean normalizePageSizes(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("normalizePageSizes"), false);
    }

    public boolean firstInputCoverTitle(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("firstInputCoverTitle"), false);
    }

    public int bookmarksLevel(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOr(json.$bslash("bookmarksLevel"), 1);
    }

    public Option<String> bookmarksRegEx(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("bookmarksExpression")).map(expression -> {
            return expression.startsWith("(*.)") ? expression : new StringBuilder(10).append("(.*)(").append(expression).append(")(.+)").toString();
        });
    }

    public Option<String> outputFilenamePattern(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("outputFilenamePattern"));
    }

    public StandardType1Font font(final JsonAST.JValue json) {
        boolean bold = JsonExtract$.MODULE$.toBoolOr(json.$bslash("bold"), false);
        boolean italic = JsonExtract$.MODULE$.toBoolOr(json.$bslash("italic"), false);
        String strReplaceAll = JsonExtract$.MODULE$.toStringOr(json.$bslash("font"), "helvetica").toLowerCase().replaceAll("-", "_");
        switch (strReplaceAll == null ? 0 : strReplaceAll.hashCode()) {
            case 851308484:
                if ("times_roman".equals(strReplaceAll)) {
                    return (bold && italic) ? StandardType1Font.TIMES_BOLD_ITALIC : bold ? StandardType1Font.TIMES_BOLD : italic ? StandardType1Font.TIMES_ITALIC : StandardType1Font.TIMES_ROMAN;
                }
                break;
            case 957939245:
                if ("courier".equals(strReplaceAll)) {
                    return (bold && italic) ? StandardType1Font.CURIER_BOLD_OBLIQUE : bold ? StandardType1Font.CURIER_BOLD : italic ? StandardType1Font.CURIER_OBLIQUE : StandardType1Font.CURIER;
                }
                break;
            case 1474706577:
                if ("helvetica".equals(strReplaceAll)) {
                    return (bold && italic) ? StandardType1Font.HELVETICA_BOLD_OBLIQUE : bold ? StandardType1Font.HELVETICA_BOLD : italic ? StandardType1Font.HELVETICA_OBLIQUE : StandardType1Font.HELVETICA;
                }
                break;
        }
        throw new RuntimeException(new StringBuilder(29).append("Unknown standard type1 font: ").append(strReplaceAll).toString());
    }

    public NumberingStyle numberingStyle(final JsonAST.JValue json) {
        boolean z = false;
        JsonAST.JString jString = null;
        JsonAST.JValue jValue$bslash = json.$bslash("numberingStyle");
        if (jValue$bslash instanceof JsonAST.JString) {
            z = true;
            jString = (JsonAST.JString) jValue$bslash;
            if ("empty".equals(jString.s())) {
                return NumberingStyle.EMPTY;
            }
        }
        if (z && "arabic".equals(jString.s())) {
            return NumberingStyle.ARABIC;
        }
        if (z && "roman".equals(jString.s())) {
            return NumberingStyle.ROMAN;
        }
        throw new RuntimeException(new StringBuilder(25).append("Unknown numbering style: ").append(jValue$bslash).toString());
    }

    public String pattern(final JsonAST.JValue json) {
        return (String) JsonExtract$.MODULE$.toStringOption(json.$bslash("pattern")).orElse(() -> {
            return JsonExtract$.MODULE$.toIntOption(json.$bslash("pattern")).map(x$8 -> {
                return Integer.toString(BoxesRunTime.unboxToInt(x$8));
            });
        }).getOrElse(() -> {
            throw new RuntimeException("Pattern is missing");
        });
    }

    public Option<Object> fontSizeOpt(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toDoubleOption(json.$bslash("fontSize"));
    }

    public double fontSize(final JsonAST.JValue json) {
        return BoxesRunTime.unboxToDouble(fontSizeOpt(json).getOrElse(() -> {
            return 10.0d;
        }));
    }

    public Option<Object> lineHeightOpt(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toDoubleOption(json.$bslash("lineHeight"));
    }

    public Option<String> fontIdOpt(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("fontId"));
    }

    public Option<Object> pageCountStartFrom(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOption(json.$bslash("pageCountStartFrom"));
    }

    public Tuple2<VerticalAlign, HorizontalAlign> pageLocation(final JsonAST.JValue json) {
        boolean z = false;
        JsonAST.JString jString = null;
        JsonAST.JValue jValue$bslash = json.$bslash("pageLocation");
        if (jValue$bslash instanceof JsonAST.JString) {
            z = true;
            jString = (JsonAST.JString) jValue$bslash;
            if ("top-left".equals(jString.s())) {
                return new Tuple2<>(VerticalAlign.TOP, HorizontalAlign.LEFT);
            }
        }
        if (z && "top-center".equals(jString.s())) {
            return new Tuple2<>(VerticalAlign.TOP, HorizontalAlign.CENTER);
        }
        if (z && "top-right".equals(jString.s())) {
            return new Tuple2<>(VerticalAlign.TOP, HorizontalAlign.RIGHT);
        }
        if (z && "bottom-left".equals(jString.s())) {
            return new Tuple2<>(VerticalAlign.BOTTOM, HorizontalAlign.LEFT);
        }
        if (z && "bottom-center".equals(jString.s())) {
            return new Tuple2<>(VerticalAlign.BOTTOM, HorizontalAlign.CENTER);
        }
        if (z && "bottom-right".equals(jString.s())) {
            return new Tuple2<>(VerticalAlign.BOTTOM, HorizontalAlign.RIGHT);
        }
        throw new RuntimeException(new StringBuilder(23).append("Unknown page location: ").append(jValue$bslash).toString());
    }

    public TopLeftRectangularBox area(final JsonAST.JValue json) {
        JsonAST.JValue area = json.$bslash("area");
        Tuple4 tuple4 = new Tuple4(JsonExtract$.MODULE$.toIntOption(area.$bslash("x")), JsonExtract$.MODULE$.toIntOption(area.$bslash(OperatorName.CURVE_TO_REPLICATE_FINAL_POINT)), JsonExtract$.MODULE$.toIntOption(area.$bslash("width")), JsonExtract$.MODULE$.toIntOption(area.$bslash("height")));
        if (tuple4 != null) {
            Some some = (Option) tuple4._1();
            Some some2 = (Option) tuple4._2();
            Some some3 = (Option) tuple4._3();
            Some some4 = (Option) tuple4._4();
            if (some instanceof Some) {
                int x = BoxesRunTime.unboxToInt(some.value());
                if (some2 instanceof Some) {
                    int y = BoxesRunTime.unboxToInt(some2.value());
                    if (some3 instanceof Some) {
                        int width = BoxesRunTime.unboxToInt(some3.value());
                        if (some4 instanceof Some) {
                            int height = BoxesRunTime.unboxToInt(some4.value());
                            return new TopLeftRectangularBox(x, y, width, height);
                        }
                    }
                }
            }
        }
        throw new RuntimeException(new StringBuilder(34).append("Unknown top left rectangular box: ").append(net.liftweb.json.package$.MODULE$.compactRender(area)).toString());
    }

    public boolean flattenForm(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("flattenForm"), false);
    }

    public Option<AcroFormPolicy> acroFormPolicy(final JsonAST.JValue json) {
        boolean z = false;
        Some some = null;
        Option map = JsonExtract$.MODULE$.toStringOption(json.$bslash("acroFormPolicy")).map(x$9 -> {
            return x$9.toLowerCase();
        });
        if (map instanceof Some) {
            z = true;
            some = (Some) map;
            if ("discard".equals((String) some.value())) {
                return new Some(AcroFormPolicy.DISCARD);
            }
        }
        return (z && "merge".equals((String) some.value())) ? new Some(AcroFormPolicy.MERGE) : (z && "merge_renaming".equals((String) some.value())) ? new Some(AcroFormPolicy.MERGE_RENAMING_EXISTING_FIELDS) : (z && "flatten".equals((String) some.value())) ? new Some(AcroFormPolicy.FLATTEN) : None$.MODULE$;
    }

    public AutoCropMode autoCropMode(final JsonAST.JValue json) {
        boolean z = false;
        JsonAST.JString jString = null;
        JsonAST.JValue jValue$bslash = json.$bslash("autoCropMode");
        if (jValue$bslash instanceof JsonAST.JString) {
            z = true;
            jString = (JsonAST.JString) jValue$bslash;
            if ("automatic_consistent".equals(jString.s())) {
                return AutoCropMode.AUTOMATIC_CONSISTENT;
            }
        }
        return (z && "automatic_maximum".equals(jString.s())) ? AutoCropMode.AUTOMATIC_MAXIMUM : AutoCropMode.NONE;
    }

    public Option<RectangularBox> areaCropOpt(final JsonAST.JValue json) {
        return toRectangularBoxOpt(json.$bslash("area"));
    }

    public Option<RectangularBox> oddAreaCropOpt(final JsonAST.JValue json) {
        return toRectangularBoxOpt(json.$bslash("oddArea"));
    }

    public Option<RectangularBox> evenAreaCropOpt(final JsonAST.JValue json) {
        return toRectangularBoxOpt(json.$bslash("evenArea"));
    }

    public Option<Map<String, RectangularBox>> pagesAreaCropOpt(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toMapOption(json.$bslash("pageAreas"), json2 -> {
            return this.toRectangularBox(json2);
        });
    }

    public RectangularBox toRectangularBox(final JsonAST.JValue json) {
        return (RectangularBox) toRectangularBoxOpt(json).getOrElse(() -> {
            throw new RuntimeException(new StringBuilder(25).append("Invalid rectangular box: ").append(net.liftweb.json.package$.MODULE$.compactRender(json)).toString());
        });
    }

    public Option<RectangularBox> toRectangularBoxOpt(final JsonAST.JValue json) {
        Tuple4 tuple4 = new Tuple4(JsonExtract$.MODULE$.toIntOption(json.$bslash("bottom")), JsonExtract$.MODULE$.toIntOption(json.$bslash("left")), JsonExtract$.MODULE$.toIntOption(json.$bslash("top")), JsonExtract$.MODULE$.toIntOption(json.$bslash("right")));
        if (tuple4 != null) {
            Some some = (Option) tuple4._1();
            Some some2 = (Option) tuple4._2();
            Some some3 = (Option) tuple4._3();
            Some some4 = (Option) tuple4._4();
            if (some instanceof Some) {
                int bottom = BoxesRunTime.unboxToInt(some.value());
                if (some2 instanceof Some) {
                    int left = BoxesRunTime.unboxToInt(some2.value());
                    if (some3 instanceof Some) {
                        int top = BoxesRunTime.unboxToInt(some3.value());
                        if (some4 instanceof Some) {
                            int right = BoxesRunTime.unboxToInt(some4.value());
                            return Try$.MODULE$.apply(() -> {
                                return RectangularBox.newInstance(bottom, left, top, right);
                            }).toOption();
                        }
                    }
                }
            }
        }
        return None$.MODULE$;
    }

    public List<RectangularBox> toRectangularBoxList(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toList(json, v -> {
            return this.toRectangularBox(v);
        });
    }

    public Option<BatesSequence> batesSeq(final JsonAST.JValue json) {
        Tuple2 tuple2 = new Tuple2(json.$bslash("batesStartFrom"), json.$bslash("batesIncrement"));
        if (tuple2 != null) {
            JsonAST.JInt jInt = (JsonAST.JValue) tuple2._1();
            JsonAST.JInt jInt2 = (JsonAST.JValue) tuple2._2();
            if (jInt instanceof JsonAST.JInt) {
                BigInt startFrom = jInt.num();
                if (jInt2 instanceof JsonAST.JInt) {
                    BigInt increment = jInt2.num();
                    return new Some(new BatesSequence(startFrom.toLong(), increment.toInt(), batesDigits(json)));
                }
            }
        }
        return None$.MODULE$;
    }

    public int batesDigits(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOr(json.$bslash("batesDigits"), 6);
    }

    public long kiloBytes() {
        return this.kiloBytes;
    }

    public long megaBytes() {
        return this.megaBytes;
    }

    public double size(final JsonAST.JValue json) {
        JsonAST.JInt jInt$bslash = json.$bslash("size");
        if (jInt$bslash instanceof JsonAST.JInt) {
            return jInt$bslash.num().toDouble();
        }
        if (jInt$bslash instanceof JsonAST.JDouble) {
            return ((JsonAST.JDouble) jInt$bslash).num();
        }
        if (!(jInt$bslash instanceof JsonAST.JString)) {
            return 10.0d;
        }
        String value = ((JsonAST.JString) jInt$bslash).s();
        if (StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(value.trim()))) {
            return StringOps$.MODULE$.toDouble$extension(Predef$.MODULE$.augmentString(value.replace(",", ".")));
        }
        return 10.0d;
    }

    public long sizeUnit(final JsonAST.JValue json) {
        String upperCase = JsonExtract$.MODULE$.toStringOr(json.$bslash("sizeUnit"), "MB").toUpperCase();
        switch (upperCase == null ? 0 : upperCase.hashCode()) {
            case 2391:
                if ("KB".equals(upperCase)) {
                    return kiloBytes();
                }
                break;
        }
        return megaBytes();
    }

    public List<Tuple3<Object, Object, Object>> filePages(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringList(json.$bslash("filePages")).map(i -> {
            String[] fragments = StringOps$.MODULE$.split$extension(Predef$.MODULE$.augmentString(i), ':');
            return new Tuple3(BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(fragments[0]))), BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(fragments[1]))), ((Option) Predef$.MODULE$.wrapRefArray(fragments).lift().apply(BoxesRunTime.boxToInteger(2))).map(x$10 -> {
                return BoxesRunTime.boxToInteger($anonfun$filePages$2(x$10));
            }).getOrElse(() -> {
                return 0;
            }));
        });
    }

    public static final /* synthetic */ int $anonfun$filePages$2(final String x$10) {
        return StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(x$10));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public int resolution(final JsonAST.JValue json) throws MatchError {
        Some intOption = JsonExtract$.MODULE$.toIntOption(json.$bslash("resolution"));
        if (intOption instanceof Some) {
            int r = BoxesRunTime.unboxToInt(intOption.value());
            return r;
        }
        if (None$.MODULE$.equals(intOption)) {
            return 150;
        }
        throw new MatchError(intOption);
    }

    public Option<String> imageFormat(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("imageFormat")).map(x$11 -> {
            return x$11.toLowerCase();
        });
    }

    public Repagination repagination(final JsonAST.JValue json) {
        JsonAST.JString jString$bslash = json.$bslash("repagination");
        return ((jString$bslash instanceof JsonAST.JString) && "last-first".equals(jString$bslash.s())) ? Repagination.LAST_FIRST : Repagination.NONE;
    }

    public Option<Color> color(final JsonAST.JValue json) {
        return _color(json.$bslash("color"));
    }

    public Option<Color> _color(final JsonAST.JValue json) {
        if (json instanceof JsonAST.JString) {
            String s = ((JsonAST.JString) json).s();
            if (s.startsWith("#") && s.length() == 7) {
                return new Some(hex2Rgb(s));
            }
        }
        return None$.MODULE$;
    }

    public List<EditTextOperation> editTextOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("editTextOperations"), j -> {
            return this.editTextOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public List<AppendTextOperation> appendTextOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("appendTextOperations"), j -> {
            return this.appendTextOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public List<AddImageOperation> imageOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("imageOperations"), j -> {
            return this.imageOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public SystemFont systemFont(final JsonAST.JValue json) {
        return new SystemFont(JsonExtract$.MODULE$.toString(json.$bslash("path")), JsonExtract$.MODULE$.toString(json.$bslash("postscriptName")), JsonExtract$.MODULE$.toString(json.$bslash("family")), JsonExtract$.MODULE$.toString(json.$bslash("style")));
    }

    public List<SystemFont> systemFonts(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("systemFonts"), j -> {
            return this.systemFont(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public String text(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toString(json.$bslash("text"));
    }

    public String originalText(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toString(json.$bslash("originalText"));
    }

    public EditTextOperation editTextOperation(final JsonAST.JValue json) {
        return new EditTextOperation(text(json), fontFamilyNameOpt(json), area(json), pageNum(json), fontBold(json), fontItalic(json), fontSizeOpt(json), positionOpt(json), originalText(json), color(json), lineHeightOpt(json), fontIdOpt(json));
    }

    public AppendTextOperation appendTextOperation(final JsonAST.JValue json) {
        return new AppendTextOperation(text(json), fontFamilyName(json), fontSize(json), (Color) color(json).getOrElse(() -> {
            return Color.black;
        }), position(json), (PageRange) pageRanges(json).head(), fontBold(json), fontItalic(json), lineHeightOpt(json));
    }

    public Option<String> signatureFieldName(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("signatureFieldName"));
    }

    public AddImageOperation imageOperation(final JsonAST.JValue json) {
        return new AddImageOperation(imageSource(json), width(json), height(json), position(json), (PageRange) pageRanges(json).head(), rotation(json), exceptPages(json), signatureFieldName(json));
    }

    public List<InsertPageOperation> insertPageOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("insertPageOperations"), j -> {
            return this.insertPageOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public InsertPageOperation insertPageOperation(final JsonAST.JValue json) {
        return new InsertPageOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")));
    }

    public List<DeletePageOperation> deletePageOperations(final JsonAST.JValue json) {
        List ops = (List) JsonExtract$.MODULE$.toListOption(json.$bslash("deletePageOperations"), j -> {
            return this.deletePageOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
        return (List) ops.distinct();
    }

    public DeletePageOperation deletePageOperation(final JsonAST.JValue json) {
        return new DeletePageOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")));
    }

    public Option<Source<?>> imageSourceOpt(final JsonAST.JValue json) {
        return Try$.MODULE$.apply(() -> {
            return this.imageSource(json);
        }).toOption();
    }

    public Source<?> imageSource(final JsonAST.JValue json) {
        return imageSource(JsonExtract$.MODULE$.toString(json.$bslash("image")));
    }

    public Seq<Watermark> watermarks(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toList(json.$bslash("watermarks"), jv -> {
            return this.watermark(jv);
        });
    }

    public Watermark watermark(final JsonAST.JValue json) {
        return new Watermark(imageSource(json), Location.OVER, opacity(json), new Dimension((int) width(json), (int) height(json)), position(json), rotation(json), name(json));
    }

    public Source<?> imageSource(final String image) {
        if (image != null ? !image.equals("data:,") : "data:," != 0) {
            if (!image.endsWith(";base64,")) {
                String[] fragments = image.split(";base64,");
                if (ArrayOps$.MODULE$.size$extension(Predef$.MODULE$.refArrayOps(fragments)) == 1) {
                    return this.fs.getSource(image);
                }
                byte[] bytes = BaseEncoding.base64().decode(fragments[1].trim());
                String ext = (String) ((Option) Predef$.MODULE$.wrapRefArray(fragments[0].split("/")).lift().apply(BoxesRunTime.boxToInteger(1))).getOrElse(() -> {
                    return "jpg";
                });
                String name = new StringBuilder(6).append("image.").append(ext).toString();
                return this.fs.save(bytes, name);
            }
        }
        throw new RuntimeException(new StringBuilder(24).append("Invalid image source: '").append(image).append(OperatorName.SHOW_TEXT_LINE).toString());
    }

    public float width(final JsonAST.JValue json) {
        return (float) JsonExtract$.MODULE$.toDouble(json.$bslash("width"));
    }

    public float height(final JsonAST.JValue json) {
        return (float) JsonExtract$.MODULE$.toDouble(json.$bslash("height"));
    }

    public Point position(final JsonAST.JValue json) {
        return (Point) positionOpt(json).getOrElse(() -> {
            throw new RuntimeException(new StringBuilder(21).append("Unparsable position: ").append(net.liftweb.json.package$.MODULE$.compactRender(json)).toString());
        });
    }

    public Option<Point> positionOpt(final JsonAST.JValue json) {
        try {
            return new Some(new Point((int) JsonExtract$.MODULE$.toDouble(json.$bslash("position").$bslash("x")), (int) JsonExtract$.MODULE$.toDouble(json.$bslash("position").$bslash(OperatorName.CURVE_TO_REPLICATE_FINAL_POINT))));
        } catch (Throwable th) {
            if (NonFatal$.MODULE$.apply(th)) {
                return None$.MODULE$;
            }
            throw th;
        }
    }

    public String name(final JsonAST.JValue json) {
        return (String) nameOpt(json).getOrElse(() -> {
            return "";
        });
    }

    public Option<String> nameOpt(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("name"));
    }

    public String startsWithPrefix(final JsonAST.JValue json) {
        return (String) JsonExtract$.MODULE$.toStringOption(json.$bslash("startsWith")).getOrElse(() -> {
            return "";
        });
    }

    public List<HighlightTextOperation> highlightTextOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("highlightTextOperations"), j -> {
            return this.highlightTextOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public HighlightTextOperation highlightTextOperation(final JsonAST.JValue json) {
        return new HighlightTextOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")), toRectangularBoxList(json.$bslash("boundingBoxes")).toSet(), (Color) color(json).getOrElse(() -> {
            return Color.YELLOW;
        }), highlightKind(json));
    }

    public HighlightType highlightKind(final JsonAST.JValue json) {
        return (HighlightType) HighlightType$.MODULE$.fromString(JsonExtract$.MODULE$.toStringOr(json.$bslash("kind"), "highlight")).getOrElse(() -> {
            return HighlightType$Highlight$.MODULE$;
        });
    }

    public List<HighlightTextOperation> strikethoughTextOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("strikethroughTextOperations"), j -> {
            return this.strikethroughTextOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public HighlightTextOperation strikethroughTextOperation(final JsonAST.JValue json) {
        return new HighlightTextOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")), toRectangularBoxList(json.$bslash("boundingBoxes")).toSet(), (Color) color(json).getOrElse(() -> {
            return Color.RED;
        }), HighlightType$Strikethrough$.MODULE$);
    }

    public LinkType linkKind(final JsonAST.JValue json) {
        return (LinkType) LinkType$.MODULE$.fromString(JsonExtract$.MODULE$.toStringOr(json.$bslash("kind"), "uri")).getOrElse(() -> {
            throw new RuntimeException("Unknown link kind");
        });
    }

    public List<AppendLinkOperation> appendLinkOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("linkOperations"), j -> {
            return this.appendLinkOperation(j);
        }).orElse(() -> {
            return JsonExtract$.MODULE$.toListOption(json.$bslash("appendLinkOperations"), j2 -> {
                return this.appendLinkOperation(j2);
            });
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public AppendLinkOperation appendLinkOperation(final JsonAST.JValue json) {
        return new AppendLinkOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")), toRectangularBoxList(json.$bslash("boundingBoxes")).toSet(), (String) JsonExtract$.MODULE$.toStringOption(json.$bslash("uri")).getOrElse(() -> {
            return JsonExtract$.MODULE$.toString(json.$bslash("href"));
        }), linkKind(json));
    }

    public List<EditLinkOperation> editLinkOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("editLinkOperations"), j -> {
            return this.editLinkOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public EditLinkOperation editLinkOperation(final JsonAST.JValue json) {
        return new EditLinkOperation(annotationId(json.$bslash("annotationId")), JsonExtract$.MODULE$.toInt(json.$bslash("page")), toRectangularBoxList(json.$bslash("boundingBoxes")).toSet(), (String) JsonExtract$.MODULE$.toStringOption(json.$bslash("uri")).getOrElse(() -> {
            return JsonExtract$.MODULE$.toString(json.$bslash("href"));
        }), linkKind(json));
    }

    public List<DeleteLinkOperation> deleteLinkOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("deleteLinkOperations"), j -> {
            return this.deleteLinkOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public DeleteLinkOperation deleteLinkOperation(final JsonAST.JValue json) {
        return new DeleteLinkOperation(annotationId(json.$bslash("annotationId")), JsonExtract$.MODULE$.toInt(json.$bslash("page")));
    }

    public List<AppendAttachmentOperation> appendAttachmentOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("appendAttachmentOperations"), j -> {
            return this.appendAttachmentOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public AppendAttachmentOperation appendAttachmentOperation(final JsonAST.JValue json) {
        return new AppendAttachmentOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")), toRectangularBoxList(json.$bslash("boundingBoxes")).toSet(), JsonExtract$.MODULE$.toString(json.$bslash("filename")));
    }

    public List<DeleteAttachmentOperation> deleteAttachmentOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("deleteAttachmentOperations"), j -> {
            return this.deleteAttachmentOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public DeleteAttachmentOperation deleteAttachmentOperation(final JsonAST.JValue json) {
        return new DeleteAttachmentOperation(annotationId(json.$bslash("annotationId")), JsonExtract$.MODULE$.toInt(json.$bslash("page")));
    }

    public List<AppendEmbeddedFileOperation> appendEmbeddedFileOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("appendEmbeddedFileOperations"), j -> {
            return this.appendEmbeddedFileOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public AppendEmbeddedFileOperation appendEmbeddedFileOperation(final JsonAST.JValue json) {
        return new AppendEmbeddedFileOperation(fileSource(json.$bslash("file")));
    }

    public Source<File> fileSource(final JsonAST.JValue json) {
        return this.fs.getSource(JsonExtract$.MODULE$.toString(json));
    }

    public List<DeleteEmbeddedFileOperation> deleteEmbeddedFileOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("deleteEmbeddedFileOperations"), j -> {
            return this.deleteEmbeddedFileOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public DeleteEmbeddedFileOperation deleteEmbeddedFileOperation(final JsonAST.JValue json) {
        return new DeleteEmbeddedFileOperation(JsonExtract$.MODULE$.toString(json.$bslash("filename")));
    }

    public List<DeleteImageOperation> deleteImageOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("deleteImageOperations"), j -> {
            return this.deleteImageOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public DeleteImageOperation deleteImageOperation(final JsonAST.JValue json) {
        return new DeleteImageOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")), JsonExtract$.MODULE$.toString(json.$bslash("objId")));
    }

    public List<DeleteHighlightOperation> deleteHighlightOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("deleteHighlightOperations"), j -> {
            return this.deleteHighlightOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public DeleteHighlightOperation deleteHighlightOperation(final JsonAST.JValue json) {
        int x$1 = annotationId(json.$bslash("annotationId"));
        int x$2 = JsonExtract$.MODULE$.toInt(json.$bslash("page"));
        return new DeleteHighlightOperation(x$2, x$1);
    }

    public List<AppendDrawOperation> appendDrawOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("appendDraw"), j -> {
            return this.appendDrawOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public AppendDrawOperation appendDrawOperation(final JsonAST.JValue json) {
        return new AppendDrawOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")), toRectangularBox(json.$bslash("boundingBox")), (Color) color(json).getOrElse(() -> {
            return Color.YELLOW;
        }), JsonExtract$.MODULE$.toList(json.$bslash("inklist"), v -> {
            return BoxesRunTime.boxToDouble($anonfun$appendDrawOperation$2(v));
        }), JsonExtract$.MODULE$.toList(json.$bslash("appearance"), v2 -> {
            return JsonExtract$.MODULE$.toList(v2, v2 -> {
                return BoxesRunTime.boxToDouble($anonfun$appendDrawOperation$4(v2));
            });
        }), BoxesRunTime.unboxToDouble(JsonExtract$.MODULE$.toDoubleOption(json.$bslash("lineSize")).getOrElse(() -> {
            return 10.0d;
        })));
    }

    public static final /* synthetic */ double $anonfun$appendDrawOperation$2(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toDouble(v);
    }

    public static final /* synthetic */ double $anonfun$appendDrawOperation$4(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toDouble(v);
    }

    public List<AppendLineOperation> appendLineOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("appendLine"), j -> {
            return this.appendLineOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public AppendLineOperation appendLineOperation(final JsonAST.JValue json) {
        return new AppendLineOperation(JsonExtract$.MODULE$.toInt(json.$bslash("page")), toRectangularBox(json.$bslash("boundingBox")), (Color) color(json).getOrElse(() -> {
            return Color.RED;
        }), JsonExtract$.MODULE$.toList(json.$bslash("points"), v -> {
            return BoxesRunTime.boxToDouble($anonfun$appendLineOperation$2(v));
        }), BoxesRunTime.unboxToDouble(JsonExtract$.MODULE$.toDoubleOption(json.$bslash("lineSize")).getOrElse(() -> {
            return 2.0d;
        })), JsonExtract$.MODULE$.toStringOr(json.$bslash("kind"), "line"));
    }

    public static final /* synthetic */ double $anonfun$appendLineOperation$2(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toDouble(v);
    }

    public List<EditHighlightOperation> editHighlightOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("editHighlightOperations"), j -> {
            return this.editHighlightOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public EditHighlightOperation editHighlightOperation(final JsonAST.JValue json) {
        int x$1 = annotationId(json.$bslash("annotationId"));
        int x$2 = JsonExtract$.MODULE$.toInt(json.$bslash("page"));
        Color x$3 = (Color) color(json).getOrElse(() -> {
            throw new RuntimeException("Unknown highlight color");
        });
        return new EditHighlightOperation(x$2, x$1, x$3);
    }

    private int annotationId(final JsonAST.JValue json) {
        return BoxesRunTime.unboxToInt(JsonExtract$.MODULE$.toIntOption(json).getOrElse(() -> {
            return StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(JsonExtract$.MODULE$.toString(json).split("-")[1]));
        }));
    }

    public List<AppendFormFieldOperation> appendFormFieldOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("appendFormFieldOperations"), j -> {
            return this.appendFormFieldOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    private String removeDots(final String s) {
        return StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(s), ".", "");
    }

    private FormFieldAlign formFieldAlign(final JsonAST.JValue json) {
        return (FormFieldAlign) JsonExtract$.MODULE$.toStringOption(json.$bslash("align")).flatMap(s -> {
            return FormFieldAlign$.MODULE$.fromString(s);
        }).getOrElse(() -> {
            return FormFieldAlign$Left$.MODULE$;
        });
    }

    public AppendFormFieldOperation appendFormFieldOperation(final JsonAST.JValue json) {
        FormFieldType x$1 = formFieldKind(json);
        String x$2 = removeDots((String) JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("name")).getOrElse(() -> {
            return UUID.randomUUID().toString();
        }));
        int x$3 = JsonExtract$.MODULE$.toInt(json.$bslash("page"));
        RectangularBox x$4 = toRectangularBox(json.$bslash("boundingBox"));
        boolean x$5 = JsonExtract$.MODULE$.toBoolOr(json.$bslash("multiline"), false);
        boolean x$6 = JsonExtract$.MODULE$.toBoolOr(json.$bslash("selected"), false);
        Option x$7 = JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("value"));
        Seq x$8 = formFieldOptions(json);
        boolean x$9 = JsonExtract$.MODULE$.toBoolOr(json.$bslash("multiselect"), false);
        Option x$10 = borderColor(json);
        Option x$11 = color(json);
        Option x$12 = JsonExtract$.MODULE$.toDoubleOption(json.$bslash("fontSize"));
        FormFieldAlign x$13 = formFieldAlign(json);
        boolean x$14 = JsonExtract$.MODULE$.toBoolOr(json.$bslash("required"), false);
        String x$15 = (String) JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("id")).getOrElse(() -> {
            return new StringBuilder(8).append("autogen_").append(StringHelpers$.MODULE$.randomString(8)).toString();
        });
        Option x$16 = JsonExtract$.MODULE$.toIntOption(json.$bslash("combChars"));
        boolean x$17 = AppendFormFieldOperation$.MODULE$.$lessinit$greater$default$12();
        return new AppendFormFieldOperation(x$1, x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$17, x$12, x$13, x$14, x$15, x$16);
    }

    public List<DeleteFormFieldOperation> deleteFormFieldOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("deleteFormFieldOperations"), j -> {
            return this.deleteFormFieldOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public DeleteFormFieldOperation deleteFormFieldOperation(final JsonAST.JValue json) {
        return new DeleteFormFieldOperation(JsonExtract$.MODULE$.toString(json.$bslash("objId")), JsonExtract$.MODULE$.toInt(json.$bslash("page")));
    }

    public Seq<String> formFieldOptions(final JsonAST.JValue json) {
        return (Seq) JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("options")).map(s -> {
            return ArrayOps$.MODULE$.toSeq$extension(Predef$.MODULE$.refArrayOps(s.split("\\n")));
        }).getOrElse(() -> {
            return package$.MODULE$.Seq().empty();
        });
    }

    public FormFieldType formFieldKind(final JsonAST.JValue json) {
        return (FormFieldType) FormFieldType$.MODULE$.fromString(JsonExtract$.MODULE$.toString(json.$bslash("kind"))).getOrElse(() -> {
            throw new RuntimeException("Unknown form field kind");
        });
    }

    public List<AddShapeOperation> shapeOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("shapeOperations"), j -> {
            return this.shapeOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public AddShapeOperation shapeOperation(final JsonAST.JValue json) {
        return new AddShapeOperation(shape(json), width(json), height(json), position(json), (PageRange) pageRanges(json).head(), borderWidth(json), (Color) borderColor(json).orElse(() -> {
            return this.backgroundColor(json);
        }).getOrElse(() -> {
            return Color.WHITE;
        }), (Color) backgroundColor(json).orNull($less$colon$less$.MODULE$.refl()));
    }

    public Iterable<FillFormOperation> fillFormOperations(final JsonAST.JValue json) {
        return (Iterable) ((IterableOps) JsonExtract$.MODULE$.toMapOption(json.$bslash("formValues"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return Predef$.MODULE$.Map().empty();
        })).map(x0$1 -> {
            if (x0$1 != null) {
                String key = (String) x0$1._1();
                String value = (String) x0$1._2();
                return new FillFormOperation(key, value);
            }
            throw new MatchError(x0$1);
        });
    }

    public Map<String, String> formWidgets(final JsonAST.JValue json) {
        return (Map) JsonExtract$.MODULE$.toMapOption(json.$bslash("formWidgets"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return Predef$.MODULE$.Map().empty();
        });
    }

    public List<String> tabOrder(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("tabOrder"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        });
    }

    public List<RotatePageOperation> rotatePageOperations(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("rotatePageOperations"), j -> {
            return this.rotatePageOperation(j);
        }).getOrElse(() -> {
            return Nil$.MODULE$;
        });
    }

    public RotatePageOperation rotatePageOperation(final JsonAST.JValue json) {
        return new RotatePageOperation(pageNum(json), (Rotation) rotationDegrees(json).getOrElse(() -> {
            return Rotation.DEGREES_0;
        }));
    }

    public List<String> formErrors(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("formErrors"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        });
    }

    private Option<Color> borderColor(final JsonAST.JValue json) {
        return _color(json.$bslash("borderColor"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Option<Color> backgroundColor(final JsonAST.JValue json) {
        return _color(json.$bslash("backgroundColor"));
    }

    private Shape shape(final JsonAST.JValue json) {
        String lowerCase = JsonExtract$.MODULE$.toString(json.$bslash("shape")).toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case -1656480802:
                if ("ellipse".equals(lowerCase)) {
                    return Shape.ELLIPSE;
                }
                break;
            case 1121299823:
                if ("rectangle".equals(lowerCase)) {
                    return Shape.RECTANGLE;
                }
                break;
        }
        throw new RuntimeException(new StringBuilder(14).append("Unknown shape ").append(lowerCase).toString());
    }

    private float borderWidth(final JsonAST.JValue json) {
        return (float) JsonExtract$.MODULE$.toDouble(json.$bslash("borderWidth"));
    }

    private Color hex2Rgb(final String s) {
        return new Color(Predef$.MODULE$.Integer2int(Integer.valueOf(s.substring(1, 3), 16)), Predef$.MODULE$.Integer2int(Integer.valueOf(s.substring(3, 5), 16)), Predef$.MODULE$.Integer2int(Integer.valueOf(s.substring(5, 7), 16)));
    }

    public boolean discardOutline(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("discardOutline"), false);
    }

    public Option<Object> imageDpi(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOption(json.$bslash("imageDpi"));
    }

    public boolean imageGrayscale(final JsonAST.JValue json) {
        Option<String> stringOption = JsonExtract$.MODULE$.toStringOption(json.$bslash("imageConversion"));
        Some some = new Some("grayscale");
        return stringOption != null ? stringOption.equals(some) : some == null;
    }

    public boolean discardMultimedia(final JsonAST.JValue json) {
        boolean z = false;
        Some some = null;
        Option<String> stringOption = JsonExtract$.MODULE$.toStringOption(json.$bslash("discardMultimedia"));
        if (stringOption instanceof Some) {
            z = true;
            some = (Some) stringOption;
            if ("all".equals((String) some.value())) {
                return true;
            }
        }
        return (z && "none".equals((String) some.value())) ? false : true;
    }

    public boolean subsetFonts(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("subsetFonts"), false);
    }

    public Option<Object> speed(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOption(json.$bslash("speed"));
    }

    public Option<Object> imageQuality(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toDoubleOption(json.$bslash("imageQuality"));
    }

    public int nupN(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOr(json.$bslash(OperatorName.ENDPATH), 2);
    }

    public PageOrder pageOrder(final JsonAST.JValue json) {
        String stringOr = JsonExtract$.MODULE$.toStringOr(json.$bslash("pageOrder"), "horizontal");
        switch (stringOr == null ? 0 : stringOr.hashCode()) {
            case -1984141450:
                if ("vertical".equals(stringOr)) {
                    return PageOrder.VERTICAL;
                }
                break;
        }
        return PageOrder.HORIZONTAL;
    }

    public TopLeftRectangularBox topLeftRect(final JsonAST.JValue json) {
        int top = JsonExtract$.MODULE$.toInt(json.$bslash("top"));
        int left = JsonExtract$.MODULE$.toInt(json.$bslash("left"));
        int width = JsonExtract$.MODULE$.toInt(json.$bslash("width"));
        int height = JsonExtract$.MODULE$.toInt(json.$bslash("height"));
        return new TopLeftRectangularBox(left, top, width, height);
    }

    public double ratio(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toDoubleOr(json.$bslash("ratio"), 1.0d);
    }

    public SplitDownTheMiddleMode splitDownTheMiddleMode(final JsonAST.JValue json) {
        String lowerCase = JsonExtract$.MODULE$.toStringOr(json.$bslash("mode"), "auto").toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case -1984141450:
                if ("vertical".equals(lowerCase)) {
                    return SplitDownTheMiddleMode.VERTICAL;
                }
                break;
            case 1387629604:
                if ("horizontal".equals(lowerCase)) {
                    return SplitDownTheMiddleMode.HORIZONTAL;
                }
                break;
        }
        return SplitDownTheMiddleMode.AUTO;
    }

    public boolean preservePageSize(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("preservePageSize"), false);
    }

    public int fileCountStartFrom(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOr(json.$bslash("fileCountStartFrom"), 1);
    }

    public ExistingOutputPolicy existingOutputPolicy(final JsonAST.JValue json) {
        String lowerCase = JsonExtract$.MODULE$.toStringOr(json.$bslash("existingOutputPolicy"), "fail").toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case -934594754:
                if ("rename".equals(lowerCase)) {
                    return ExistingOutputPolicy.RENAME;
                }
                break;
            case -745078901:
                if ("overwrite".equals(lowerCase)) {
                    return ExistingOutputPolicy.OVERWRITE;
                }
                break;
            case 3532159:
                if ("skip".equals(lowerCase)) {
                    return ExistingOutputPolicy.SKIP;
                }
                break;
        }
        return ExistingOutputPolicy.FAIL;
    }

    public Option<File> defaultOutputFolder(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("defaultOutputFolder")).map(s -> {
            return new File(s);
        });
    }

    public Option<String> defaultOutputFile(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("defaultOutputFile"));
    }

    public boolean addMargins(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("addMargins"), false);
    }

    public boolean includePageAfter(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("includePageAfter"), false);
    }

    public int opacity(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOr(json.$bslash("opacity"), 100);
    }

    public int rotation(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOr(json.$bslash("rotation"), 0);
    }

    public List<Object> exceptPages(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toListOr(json.$bslash("exceptPages"), v -> {
            return BoxesRunTime.boxToInteger($anonfun$exceptPages$1(v));
        }, package$.MODULE$.List().empty());
    }

    public static final /* synthetic */ int $anonfun$exceptPages$1(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toInt(v);
    }

    public boolean rightToLeft(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("rightToLeft"), false);
    }

    public boolean autoDetectExcludedPages(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("autoDetectExcludedPages"), false);
    }

    public String fontFamilyName(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toString(json.$bslash("font"));
    }

    public Option<String> fontFamilyNameOpt(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("font"));
    }

    public boolean fontBold(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("bold"), false);
    }

    public boolean fontItalic(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("italic"), false);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0203  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public scala.Option<org.sejda.model.scale.Margins> marginInInches(final net.liftweb.json.JsonAST.JValue r15) {
        /*
            Method dump skipped, instructions count: 683
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.model.TaskJsonParser.marginInInches(net.liftweb.json.JsonAST$JValue):scala.Option");
    }

    private double toInches(final double cm) {
        return cm / 2.54d;
    }

    public Option<PageSize> pageSizeFromUnitDimensions(final JsonAST.JValue json) {
        String unit = (String) JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("unit")).getOrElse(() -> {
            return "inch";
        });
        Tuple2 tuple2 = new Tuple2(JsonExtract$.MODULE$.toDoubleOption(json.$bslash("pageSizeWidth")), JsonExtract$.MODULE$.toDoubleOption(json.$bslash("pageSizeHeight")));
        if (tuple2 != null) {
            Some some = (Option) tuple2._1();
            Some some2 = (Option) tuple2._2();
            if (some instanceof Some) {
                double width = BoxesRunTime.unboxToDouble(some.value());
                if (some2 instanceof Some) {
                    double height = BoxesRunTime.unboxToDouble(some2.value());
                    if (width != 0 && height != 0 && (unit != null ? unit.equals(OperatorName.CONCAT) : OperatorName.CONCAT == 0)) {
                        return new Some(PageSize.fromMillimeters(((float) width) * 10, ((float) height) * 10));
                    }
                }
            }
        }
        if (tuple2 != null) {
            Some some3 = (Option) tuple2._1();
            Some some4 = (Option) tuple2._2();
            if (some3 instanceof Some) {
                double width2 = BoxesRunTime.unboxToDouble(some3.value());
                if (some4 instanceof Some) {
                    double height2 = BoxesRunTime.unboxToDouble(some4.value());
                    if (width2 != 0 && height2 != 0) {
                        return new Some(PageSize.fromInches((float) width2, (float) height2));
                    }
                }
            }
        }
        return None$.MODULE$;
    }

    public Set<String> urls(final JsonAST.JValue json) {
        Some stringOption = JsonExtract$.MODULE$.toStringOption(json.$bslash("url"));
        if (stringOption instanceof Some) {
            String url = (String) stringOption.value();
            return (Set) ((IterableOps) ((IterableOps) Predef$.MODULE$.Set().apply(ScalaRunTime$.MODULE$.wrapRefArray(new String[]{url}))).map(x$12 -> {
                return x$12.trim();
            })).filter(x$13 -> {
                return BoxesRunTime.boxToBoolean($anonfun$urls$2(x$13));
            });
        }
        List urls = (List) JsonExtract$.MODULE$.toStringListOpt(json.$bslash("urls")).orElse(() -> {
            return JsonExtract$.MODULE$.toStringOption(json.$bslash("urls")).map(s -> {
                return Predef$.MODULE$.wrapRefArray(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(s), "\r\n", "\n")), " http://", "\nhttp://")), " https://", "\nhttps://")), ",\nhttp://", "\nhttp://")), ",\nhttps://", "\nhttps://").split("\n")).toList();
            });
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        });
        return urls.map(x$14 -> {
            return x$14.trim();
        }).filter(x$15 -> {
            return BoxesRunTime.boxToBoolean($anonfun$urls$7(x$15));
        }).take(100).toSet();
    }

    public static final /* synthetic */ boolean $anonfun$urls$2(final String x$13) {
        return StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(x$13));
    }

    public static final /* synthetic */ boolean $anonfun$urls$7(final String x$15) {
        return StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(x$15));
    }

    public Option<String> htmlCode(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("htmlCode")).flatMap(s -> {
            return s.trim().isEmpty() ? None$.MODULE$ : new Some(s);
        });
    }

    public boolean textGrayscaleToBlack(final JsonAST.JValue json) {
        Option<String> stringOption = JsonExtract$.MODULE$.toStringOption(json.$bslash("textConversion"));
        Some some = new Some("black");
        return stringOption != null ? stringOption.equals(some) : some == null;
    }

    public boolean skipImageConversion(final JsonAST.JValue json) {
        Option<String> stringOption = JsonExtract$.MODULE$.toStringOption(json.$bslash("imageConversion"));
        Some some = new Some("none");
        return stringOption != null ? stringOption.equals(some) : some == null;
    }

    public boolean resizeImages(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("resizeImages"), true);
    }

    public boolean failIfOutputSizeNotReduced(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("failIfOutputSizeNotReduced"), true);
    }

    public Option<PageSize> pageSizeOpt(final JsonAST.JValue json) {
        return Try$.MODULE$.apply(() -> {
            return this.pageSize(json);
        }).toOption();
    }

    public PageSize pageSize(final JsonAST.JValue json) {
        String lowerCase = JsonExtract$.MODULE$.toStringOr(json.$bslash("pageSize"), "").toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case -1552895245:
                if ("tabloid".equals(lowerCase)) {
                    return PageSize.TABLOID;
                }
                break;
            case -1349088399:
                if ("custom".equals(lowerCase)) {
                    double width = JsonExtract$.MODULE$.toDouble(json.$bslash("pageSizeCustomWidth"));
                    double height = JsonExtract$.MODULE$.toDouble(json.$bslash("pageSizeCustomHeight"));
                    return (width == 0.0d || height == 0.0d) ? PageSize.A4 : PageSize.fromInches((float) width, (float) height);
                }
                break;
            case -1106662039:
                if ("ledger".equals(lowerCase)) {
                    return PageSize.LEDGER;
                }
                break;
            case -1106172890:
                if ("letter".equals(lowerCase)) {
                    return PageSize.LETTER;
                }
                break;
            case -1090974744:
                if ("executive".equals(lowerCase)) {
                    return PageSize.EXECUTIVE;
                }
                break;
            case 3055:
                if ("a0".equals(lowerCase)) {
                    return PageSize.A0;
                }
                break;
            case 3056:
                if ("a1".equals(lowerCase)) {
                    return PageSize.A1;
                }
                break;
            case 3057:
                if ("a2".equals(lowerCase)) {
                    return PageSize.A2;
                }
                break;
            case 3058:
                if ("a3".equals(lowerCase)) {
                    return PageSize.A3;
                }
                break;
            case 3059:
                if ("a4".equals(lowerCase)) {
                    return PageSize.A4;
                }
                break;
            case 3060:
                if ("a5".equals(lowerCase)) {
                    return PageSize.A5;
                }
                break;
            case 3061:
                if ("a6".equals(lowerCase)) {
                    return PageSize.A6;
                }
                break;
            case 102851257:
                if ("legal".equals(lowerCase)) {
                    return PageSize.LEGAL;
                }
                break;
        }
        throw new RuntimeException(new StringBuilder(19).append("Unknown page size: ").append(lowerCase).toString());
    }

    public boolean pageSizeMatchesImageSize(final JsonAST.JValue json) {
        Option map = JsonExtract$.MODULE$.toStringOption(json.$bslash("pageSize")).map(x$16 -> {
            return x$16.toLowerCase();
        });
        Some some = new Some("auto");
        return map != null ? map.equals(some) : some == null;
    }

    public double pageMargin(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toDoubleOr(json.$bslash("pageMargin"), 0.0d);
    }

    public PageOrientation pageOrientation(final JsonAST.JValue json) {
        String lowerCase = JsonExtract$.MODULE$.toStringOr(json.$bslash("pageOrientation"), "auto").toLowerCase();
        switch (lowerCase == null ? 0 : lowerCase.hashCode()) {
            case 3005871:
                if ("auto".equals(lowerCase)) {
                    return PageOrientation.AUTO;
                }
                break;
            case 729267099:
                if ("portrait".equals(lowerCase)) {
                    return PageOrientation.PORTRAIT;
                }
                break;
            case 1430647483:
                if ("landscape".equals(lowerCase)) {
                    return PageOrientation.LANDSCAPE;
                }
                break;
        }
        logger().debug(new StringBuilder(26).append("Unknown page orientation: ").append(lowerCase).toString());
        return PageOrientation.AUTO;
    }

    public Option<String> pageMargins(final JsonAST.JValue json) {
        Tuple2 tuple2 = new Tuple2(JsonExtract$.MODULE$.toDoubleOption(json.$bslash("pageMargin")), JsonExtract$.MODULE$.toStringOption(json.$bslash("pageMarginUnits")));
        if (tuple2 != null) {
            Some some = (Option) tuple2._1();
            Some some2 = (Option) tuple2._2();
            if (some instanceof Some) {
                double pageMargin = BoxesRunTime.unboxToDouble(some.value());
                if (some2 instanceof Some) {
                    String pageMarginUnits = (String) some2.value();
                    if (pageMargin > 0) {
                        return new Some(new StringBuilder(0).append(pageMargin).append(pageMarginUnits).toString());
                    }
                }
            }
        }
        return None$.MODULE$;
    }

    public Option<Object> viewportWidth(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOption(json.$bslash("viewportWidth"));
    }

    public Option<Object> hideNotices(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOption(json.$bslash("hideNotices"));
    }

    public int delay(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toIntOr(json.$bslash("delay"), 0);
    }

    public Option<Object> usePrintMedia(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOption(json.$bslash("usePrintMedia"));
    }

    public Option<Object> partialContentAllowed(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOption(json.$bslash("partialContentAllowed"));
    }

    public Option<String> waitForSelector(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("waitForSelector"));
    }

    public Option<String> timezone(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("timezone"));
    }

    public Option<String> browserLang(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("lang"));
    }

    public Option<String> headerFooterExtras(final JsonAST.JValue json) {
        boolean include = JsonExtract$.MODULE$.toBoolOr(json.$bslash("includeHeaderFooterDetails"), false);
        if (include) {
            return new Some("footer_date_page_url");
        }
        return None$.MODULE$;
    }

    public Option<String> browserLocation(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOptionNoneIfBlank(json.$bslash("browserLocation")).map(x$17 -> {
            return x$17.toUpperCase();
        });
    }

    public Option<Object> forceOnePage(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOption(json.$bslash("forceOnePage"));
    }

    public Option<Object> scrollPage(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOption(json.$bslash("scrollPage"));
    }

    public Option<String> authUsername(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("authUsername"));
    }

    public Option<String> authPassword(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("authPassword"));
    }

    public Option<String> exportFormat(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("exportFormat"));
    }

    public Seq<OutputFormat> outputFormats(final JsonAST.JValue json) {
        List formats = ((List) ((StrictOptimizedIterableOps) JsonExtract$.MODULE$.toListOption(json.$bslash("outputFormats"), v -> {
            return JsonExtract$.MODULE$.toStringOption(v);
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        })).flatten(Predef$.MODULE$.$conforms())).map(x$18 -> {
            return x$18.toLowerCase();
        }).flatMap(x0$1 -> {
            switch (x0$1 == null ? 0 : x0$1.hashCode()) {
                case 110834:
                    if (SejdaFileExtensions.PDF_EXTENSION.equals(x0$1)) {
                        return new Some(OutputFormat$Pdf$.MODULE$);
                    }
                    return None$.MODULE$;
                case 115312:
                    break;
                case 3556653:
                    break;
                default:
                    return None$.MODULE$;
            }
        });
        return formats.isEmpty() ? new $colon.colon(OutputFormat$Pdf$.MODULE$, Nil$.MODULE$) : formats;
    }

    public Option<String> lang(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringOption(json.$bslash("lang"));
    }

    public List<Tuple3<Object, Object, Object>> pageRotationsPerSource(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toStringList(json.$bslash("pageRotationsPerSource")).map(i -> {
            String[] fragments = StringOps$.MODULE$.split$extension(Predef$.MODULE$.augmentString(i), ':');
            return new Tuple3(BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(fragments[0]))), BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(fragments[1]))), BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(fragments[2]))));
        });
    }

    public FlattenMode flattenMode(final JsonAST.JValue json) {
        return (FlattenMode) FlattenMode$.MODULE$.fromString(JsonExtract$.MODULE$.toString(json.$bslash("flattenMode"))).getOrElse(() -> {
            throw new RuntimeException("No flatten mode defined");
        });
    }

    public Set<AnnotationType> annotationTypes(final JsonAST.JValue json) {
        return ((List) JsonExtract$.MODULE$.toListOption(json.$bslash("annotationTypes"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        })).flatMap(x0$1 -> {
            switch (x0$1 == null ? 0 : x0$1.hashCode()) {
                case 96673:
                    if ("all".equals(x0$1)) {
                        return None$.MODULE$;
                    }
                    break;
            }
            return new Some(AnnotationType$.MODULE$.fromString(x0$1));
        }).toSet();
    }

    public Map<String, String> metadata(final JsonAST.JValue json) {
        return (Map) JsonExtract$.MODULE$.toMapOption(json.$bslash("metadata"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return Predef$.MODULE$.Map().empty();
        });
    }

    public List<String> fieldsToRemove(final JsonAST.JValue json) {
        return (List) JsonExtract$.MODULE$.toListOption(json.$bslash("fieldsToRemove"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        });
    }

    public boolean removeAllMetadata(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("removeAllMetadata"), false);
    }

    public Seq<String> specificResultFilenames(final JsonAST.JValue json) {
        return (Seq) JsonExtract$.MODULE$.toListOption(json.$bslash("specificResultFilenames"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).getOrElse(() -> {
            return package$.MODULE$.List().empty();
        });
    }

    public List<RenameArea> renameAreas(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toList(json.$bslash("areas"), json2 -> {
            return this.renameArea(json2);
        });
    }

    public RenameArea renameArea(final JsonAST.JValue json) {
        return new RenameArea(JsonExtract$.MODULE$.toInt(json.$bslash("x")), JsonExtract$.MODULE$.toInt(json.$bslash(OperatorName.CURVE_TO_REPLICATE_FINAL_POINT)), JsonExtract$.MODULE$.toInt(json.$bslash("width")), JsonExtract$.MODULE$.toInt(json.$bslash("height")), JsonExtract$.MODULE$.toInt(json.$bslash("pageNum")), JsonExtract$.MODULE$.toString(json.$bslash("name")));
    }

    public String renamePattern(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toString(json.$bslash("renamePattern"));
    }

    public boolean singleSheet(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("singleSheet"), false);
    }

    public boolean onlyStronglySkewed(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toBoolOr(json.$bslash("onlyStronglySkewed"), false);
    }

    public Map<Object, Object> angles(final JsonAST.JValue json) {
        return ((MapOps) JsonExtract$.MODULE$.toMapOption(json.$bslash("angles"), v -> {
            return BoxesRunTime.boxToDouble($anonfun$angles$1(v));
        }).getOrElse(() -> {
            return Predef$.MODULE$.Map().empty();
        })).map(x0$1 -> {
            if (x0$1 != null) {
                String key = (String) x0$1._1();
                double value = x0$1._2$mcD$sp();
                return new Tuple2.mcID.sp(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(key)), value);
            }
            throw new MatchError(x0$1);
        });
    }

    public static final /* synthetic */ double $anonfun$angles$1(final JsonAST.JValue v) {
        return JsonExtract$.MODULE$.toDouble(v);
    }

    public List<DeletedBookmark> deletedBookmarks(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toList(json.$bslash("deletedBookmarks"), v -> {
            return JsonExtract$.MODULE$.toString(v);
        }).map(id -> {
            return new DeletedBookmark(id);
        });
    }

    public List<AddedBookmark> addedBookmarks(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toList(json.$bslash("addedBookmarks"), json2 -> {
            return this.addedBookmark(json2);
        });
    }

    public AddedBookmark addedBookmark(final JsonAST.JValue json) {
        return new AddedBookmark(JsonExtract$.MODULE$.toString(json.$bslash("id")), JsonExtract$.MODULE$.toString(json.$bslash("parentId")), JsonExtract$.MODULE$.toString(json.$bslash("title")), JsonExtract$.MODULE$.toInt(json.$bslash("page")), JsonExtract$.MODULE$.toInt(json.$bslash("index")));
    }

    public List<UpdatedBookmark> updatedBookmarks(final JsonAST.JValue json) {
        return JsonExtract$.MODULE$.toList(json.$bslash("updatedBookmarks"), json2 -> {
            return this.updatedBookmark(json2);
        });
    }

    public UpdatedBookmark updatedBookmark(final JsonAST.JValue json) {
        return new UpdatedBookmark(JsonExtract$.MODULE$.toString(json.$bslash("id")), JsonExtract$.MODULE$.toString(json.$bslash("title")), JsonExtract$.MODULE$.toIntOption(json.$bslash("page")));
    }

    public void switchExistingOutputPolicyToRenameOnWeb(final TaskParameters p) {
        ExistingOutputPolicy existingOutputPolicy = p.getExistingOutputPolicy();
        ExistingOutputPolicy existingOutputPolicy2 = ExistingOutputPolicy.FAIL;
        if (existingOutputPolicy == null) {
            if (existingOutputPolicy2 != null) {
                return;
            }
        } else if (!existingOutputPolicy.equals(existingOutputPolicy2)) {
            return;
        }
        p.setExistingOutputPolicy(ExistingOutputPolicy.RENAME);
    }
}

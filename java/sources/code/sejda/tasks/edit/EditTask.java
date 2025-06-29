package code.sejda.tasks.edit;

import code.sejda.tasks.common.AcroFormsHelper;
import code.sejda.tasks.common.AcroFormsHelper$;
import code.sejda.tasks.common.AnnotationsHelper$;
import code.sejda.tasks.common.BaseBaseTask;
import code.sejda.tasks.common.DocumentFontSearcher;
import code.sejda.tasks.common.FontsHelper$;
import code.sejda.tasks.common.PageTextRedactor;
import code.sejda.tasks.common.Redaction;
import code.sejda.tasks.common.RedactionResult;
import code.sejda.tasks.common.Replacement;
import code.sejda.tasks.common.SystemFonts;
import code.sejda.tasks.common.SystemFonts$;
import code.sejda.tasks.common.image.ImageRedaction;
import code.sejda.tasks.common.image.ImageRedactionResults;
import code.sejda.tasks.common.image.PageImageRedactor;
import code.util.ImplicitJavaConversions$;
import code.util.StringHelpers$;
import code.util.pdf.ObjIdUtils$;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.SortedSet;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.sejda.commons.util.StringUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.DefaultPdfSourceOpener;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PageImageWriter;
import org.sejda.impl.sambox.component.PageTextWriter;
import org.sejda.impl.sambox.component.PdfRotator;
import org.sejda.impl.sambox.component.SignatureClipper;
import org.sejda.impl.sambox.pro.component.PageGeometricalShapeWriter;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.io.SeekableSource;
import org.sejda.model.RectangularBox;
import org.sejda.model.SejdaFileExtensions;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.model.encryption.EncryptionAtRestPolicy;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.exception.TaskPermissionsException;
import org.sejda.model.input.FileSource;
import org.sejda.model.input.PdfSource;
import org.sejda.model.input.PdfSourceOpener;
import org.sejda.model.input.Source;
import org.sejda.model.pdf.StandardType1Font;
import org.sejda.model.pdf.encryption.PdfAccessPermission;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDDocumentNameDictionary;
import org.sejda.sambox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.common.PDNameTreeNode;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.sejda.sambox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.sejda.sambox.pdmodel.common.filespecification.PDFileSpecification;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.graphics.image.UnsupportedImageFormatException;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.sejda.sambox.pdmodel.graphics.state.RenderingMode;
import org.sejda.sambox.pdmodel.interactive.action.PDAction;
import org.sejda.sambox.pdmodel.interactive.action.PDActionGoTo;
import org.sejda.sambox.pdmodel.interactive.action.PDActionURI;
import org.sejda.sambox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.sejda.sambox.pdmodel.interactive.form.PDButton;
import org.sejda.sambox.pdmodel.interactive.form.PDCheckBox;
import org.sejda.sambox.pdmodel.interactive.form.PDChoice;
import org.sejda.sambox.pdmodel.interactive.form.PDComboBox;
import org.sejda.sambox.pdmodel.interactive.form.PDField;
import org.sejda.sambox.pdmodel.interactive.form.PDListBox;
import org.sejda.sambox.pdmodel.interactive.form.PDRadioButton;
import org.sejda.sambox.pdmodel.interactive.form.PDSignatureField;
import org.sejda.sambox.pdmodel.interactive.form.PDTextField;
import org.sejda.sambox.pdmodel.interactive.form.PDVariableText;
import org.sejda.sambox.pdmodel.interactive.form.Tabs;
import org.sejda.sambox.util.filetypedetector.FileType;
import org.sejda.sambox.util.filetypedetector.FileTypeDetector;
import scala.$less$colon$less$;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.PartialFunction;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.Tuple4;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.SeqOps;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.Map;
import scala.collection.mutable.Map$;
import scala.collection.mutable.Set;
import scala.collection.mutable.Set$;
import scala.math.Ordering$Int$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.IntRef;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.util.Try$;
import scala.util.control.NonFatal$;

/* compiled from: EditTask.scala */
@ScalaSignature(bytes = "\u0006\u0005\tmg\u0001B\u0013'\u0001=BQ\u0001\u0011\u0001\u0005\u0002\u0005Cqa\u0011\u0001A\u0002\u0013%A\tC\u0004L\u0001\u0001\u0007I\u0011\u0002'\t\rI\u0003\u0001\u0015)\u0003F\u0011\u001d\u0019\u0006\u00011A\u0005\nQCqA\u0019\u0001A\u0002\u0013%1\r\u0003\u0004f\u0001\u0001\u0006K!\u0016\u0005\bM\u0002\u0001\r\u0011\"\u0003h\u0011\u001d\u0011\b\u00011A\u0005\nMDa!\u001e\u0001!B\u0013A\u0007b\u0002<\u0001\u0001\u0004%Ia\u001e\u0005\n\u0003\u0003\u0001\u0001\u0019!C\u0005\u0003\u0007Aq!a\u0002\u0001A\u0003&\u0001\u0010C\u0005\u0002\n\u0001\u0001\r\u0011\"\u0003\u0002\f!I\u00111\u0003\u0001A\u0002\u0013%\u0011Q\u0003\u0005\t\u00033\u0001\u0001\u0015)\u0003\u0002\u000e!I\u00111\u0004\u0001C\u0002\u0013\u0005\u0011Q\u0004\u0005\t\u0003_\u0001\u0001\u0015!\u0003\u0002 !9\u0011\u0011\u0007\u0001\u0005B\u0005M\u0002bBA0\u0001\u0011\u0005\u0013\u0011\r\u0005\b\u0003O\u0002A\u0011AA5\u0011%\ti\b\u0001b\u0001\n\u0003\ty\b\u0003\u0005\u0002D\u0002\u0001\u000b\u0011BAA\u0011\u001d\t)\r\u0001C\u0001\u0003\u000fDq!!6\u0001\t\u0003\t9\u000eC\u0004\u0002`\u0002!\t!!9\t\u0013\u0005-\bA1A\u0005\u0002\u00055\b\u0002\u0003B\u0004\u0001\u0001\u0006I!a<\t\u000f\t%\u0001\u0001\"\u0001\u0003\f!9!Q\b\u0001\u0005B\t}\u0002b\u0002B!\u0001\u0011%!1\t\u0005\b\u0005G\u0002A\u0011\u0002B3\u0011\u001d\u0011\t\t\u0001C\u0005\u0005\u0007CqAa'\u0001\t\u0013\u0011i\nC\u0004\u0003>\u0002!IAa0\t\u0019\tM\u0007\u0001%A\u0001\u0002\u0003%\tA!6\u0003\u0011\u0015#\u0017\u000e\u001e+bg.T!a\n\u0015\u0002\t\u0015$\u0017\u000e\u001e\u0006\u0003S)\nQ\u0001^1tWNT!a\u000b\u0017\u0002\u000bM,'\u000eZ1\u000b\u00035\nAaY8eK\u000e\u00011c\u0001\u00011uA\u0019\u0011\u0007\u000e\u001c\u000e\u0003IR!a\r\u0015\u0002\r\r|W.\\8o\u0013\t)$G\u0001\u0007CCN,')Y:f)\u0006\u001c8\u000e\u0005\u00028q5\ta%\u0003\u0002:M\tqQ\tZ5u!\u0006\u0014\u0018-\\3uKJ\u001c\bCA\u001e?\u001b\u0005a$BA\u001f-\u0003\u0011)H/\u001b7\n\u0005}b$\u0001\u0003'pO\u001e\f'\r\\3\u0002\rqJg.\u001b;?)\u0005\u0011\u0005CA\u001c\u0001\u0003)!x\u000e^1m'R,\u0007o]\u000b\u0002\u000bB\u0011a)S\u0007\u0002\u000f*\t\u0001*A\u0003tG\u0006d\u0017-\u0003\u0002K\u000f\n\u0019\u0011J\u001c;\u0002\u001dQ|G/\u00197Ti\u0016\u00048o\u0018\u0013fcR\u0011Q\n\u0015\t\u0003\r:K!aT$\u0003\tUs\u0017\u000e\u001e\u0005\b#\u000e\t\t\u00111\u0001F\u0003\rAH%M\u0001\fi>$\u0018\r\\*uKB\u001c\b%A\be_\u000e,X.\u001a8u\u0011\u0006tG\r\\3s+\u0005)\u0006C\u0001,a\u001b\u00059&B\u0001-Z\u0003%\u0019w.\u001c9p]\u0016tGO\u0003\u0002[7\u000611/Y7c_bT!\u0001X/\u0002\t%l\u0007\u000f\u001c\u0006\u0003WyS\u0011aX\u0001\u0004_J<\u0017BA1X\u0005E\u0001F\tR8dk6,g\u000e\u001e%b]\u0012dWM]\u0001\u0014I>\u001cW/\\3oi\"\u000bg\u000e\u001a7fe~#S-\u001d\u000b\u0003\u001b\u0012Dq!\u0015\u0004\u0002\u0002\u0003\u0007Q+\u0001\te_\u000e,X.\u001a8u\u0011\u0006tG\r\\3sA\u0005aq.\u001e;qkR<&/\u001b;feV\t\u0001\u000e\u0005\u0002ja6\t!N\u0003\u0002lY\u0006\u0011\u0011n\u001c\u0006\u0003[:\fqa];qa>\u0014HO\u0003\u0002p;\u0006!1m\u001c:f\u0013\t\t(N\u0001\u000bNk2$\u0018\u000e\u001d7f\u001fV$\b/\u001e;Xe&$XM]\u0001\u0011_V$\b/\u001e;Xe&$XM]0%KF$\"!\u0014;\t\u000fEK\u0011\u0011!a\u0001Q\u0006iq.\u001e;qkR<&/\u001b;fe\u0002\na\u0002Z8dk6,g\u000e\u001e'pC\u0012,'/F\u0001y!\rIh0V\u0007\u0002u*\u00111\u0010`\u0001\u0006S:\u0004X\u000f\u001e\u0006\u0003{v\u000bQ!\\8eK2L!a >\u0003\u001fA#gmU8ve\u000e,w\n]3oKJ\f!\u0003Z8dk6,g\u000e\u001e'pC\u0012,'o\u0018\u0013fcR\u0019Q*!\u0002\t\u000fEc\u0011\u0011!a\u0001q\u0006yAm\\2v[\u0016tG\u000fT8bI\u0016\u0014\b%A\u0006tsN$X-\u001c$p]R\u001cXCAA\u0007!\r\t\u0014qB\u0005\u0004\u0003#\u0011$aC*zgR,WNR8oiN\fqb]=ti\u0016lgi\u001c8ug~#S-\u001d\u000b\u0004\u001b\u0006]\u0001\u0002C)\u0010\u0003\u0003\u0005\r!!\u0004\u0002\u0019ML8\u000f^3n\r>tGo\u001d\u0011\u0002+5+F\nV%`-\u0006cU+R0T\u000bB\u000b%+\u0011+P%V\u0011\u0011q\u0004\t\u0005\u0003C\tY#\u0004\u0002\u0002$)!\u0011QEA\u0014\u0003\u0011a\u0017M\\4\u000b\u0005\u0005%\u0012\u0001\u00026bm\u0006LA!!\f\u0002$\t11\u000b\u001e:j]\u001e\fa#T+M)&{f+\u0011'V\u000b~\u001bV\tU!S\u0003R{%\u000bI\u0001\u0007E\u00164wN]3\u0015\u000b5\u000b)$!\u000f\t\r\u0005]2\u00031\u00017\u0003)\u0001\u0018M]1nKR,'o\u001d\u0005\b\u0003w\u0019\u0002\u0019AA\u001f\u0003A)\u00070Z2vi&|gnQ8oi\u0016DH\u000f\u0005\u0003\u0002@\u0005\u0015SBAA!\u0015\r\t\u0019\u0005`\u0001\u0005i\u0006\u001c8.\u0003\u0003\u0002H\u0005\u0005#\u0001\u0006+bg.,\u00050Z2vi&|gnQ8oi\u0016DH\u000fK\u0003\u0014\u0003\u0017\ni\u0006E\u0003G\u0003\u001b\n\t&C\u0002\u0002P\u001d\u0013a\u0001\u001e5s_^\u001c\b\u0003BA*\u00033j!!!\u0016\u000b\u0007\u0005]C0A\u0005fq\u000e,\u0007\u000f^5p]&!\u00111LA+\u00055!\u0016m]6Fq\u000e,\u0007\u000f^5p]\u000e\u0012\u0011\u0011K\u0001\bKb,7-\u001e;f)\ri\u00151\r\u0005\u0007\u0003o!\u0002\u0019\u0001\u001c)\u000bQ\tY%!\u0018\u0002\u001d\rd\u0017\u000e]*jO:\fG/\u001e:fgR\u0019Q*a\u001b\t\u000f\u00055T\u00031\u0001\u0002p\u0005\u0019Am\\2\u0011\t\u0005E\u0014\u0011P\u0007\u0003\u0003gRA!!\u001e\u0002x\u00059\u0001\u000fZ7pI\u0016d'B\u0001.^\u0013\u0011\tY(a\u001d\u0003\u0015A#Ei\\2v[\u0016tG/A\u0006dC\u000eDW\r\u001a$p]R\u001cXCAAA!!\t\u0019)!$\u0002\u0012\u0006EVBAAC\u0015\u0011\t9)!#\u0002\u000f5,H/\u00192mK*\u0019\u00111R$\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u0010\u0006\u0015%aA'baB9a)a%\u0002\u0018\u0006-\u0016bAAK\u000f\n1A+\u001e9mKJ\u0002B!!'\u0002(:!\u00111TAR!\r\tijR\u0007\u0003\u0003?S1!!)/\u0003\u0019a$o\\8u}%\u0019\u0011QU$\u0002\rA\u0013X\rZ3g\u0013\u0011\ti#!+\u000b\u0007\u0005\u0015v\tE\u0002G\u0003[K1!a,H\u0005\u001d\u0011un\u001c7fC:\u0004RARAZ\u0003oK1!!.H\u0005\u0019y\u0005\u000f^5p]B!\u0011\u0011XA`\u001b\t\tYL\u0003\u0003\u0002>\u0006M\u0014\u0001\u00024p]RLA!!1\u0002<\n1\u0001\u000b\u0012$p]R\fAbY1dQ\u0016$gi\u001c8ug\u0002\n1C]3t_24X-\u0012=uKJt\u0017\r\u001c$p]R$\u0002\"!-\u0002J\u00065\u0017\u0011\u001b\u0005\u0007\u0003\u0017D\u0002\u0019A+\u0002\u0015\u0011|7\rS1oI2,'\u000fC\u0004\u0002Pb\u0001\r!a&\u0002\r\u0019\fW.\u001b7z\u0011\u001d\t\u0019\u000e\u0007a\u0001\u0003W\u000bAAY8mI\u0006!rL]3t_24X-\u0012=uKJt\u0017\r\u001c$p]R$\u0002\"!-\u0002Z\u0006m\u0017Q\u001c\u0005\u0007\u0003\u0017L\u0002\u0019A+\t\u000f\u0005=\u0017\u00041\u0001\u0002\u0018\"9\u00111[\rA\u0002\u0005-\u0016a\u00064bY2\u0014\u0017mY6G_:$x+\u001b;i/\u0006\u0014h.\u001b8h)\u0011\t\u0019/!;\u0011\t\u0005e\u0016Q]\u0005\u0005\u0003O\fYLA\u0006Q\tRK\b/Z\u0019G_:$\bbBAh5\u0001\u0007\u0011qS\u0001\rS6\fw-\u001a$pe6\fGo]\u000b\u0003\u0003_\u0004\u0002\"!=\u0002x\u0006e\u0018qD\u0007\u0003\u0003gTA!!>\u0002\n\u0006I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0003\u001f\u000b\u0019\u0010\u0005\u0003\u0002|\n\rQBAA\u007f\u0015\u0011\tyP!\u0001\u0002!\u0019LG.\u001a;za\u0016$W\r^3di>\u0014(bA\u001f\u0002x%!!QAA\u007f\u0005!1\u0015\u000e\\3UsB,\u0017!D5nC\u001e,gi\u001c:nCR\u001c\b%\u0001\u0004s_R\fG/\u001a\u000b\u0007\u0005\u001b\u0011YC!\u000f1\t\t=!\u0011\u0004\t\u0006s\nE!QC\u0005\u0004\u0005'Q(AB*pkJ\u001cW\r\u0005\u0003\u0003\u0018\teA\u0002\u0001\u0003\f\u00057i\u0012\u0011!A\u0001\u0006\u0003\u0011iBA\u0002`II\nBAa\b\u0003&A\u0019aI!\t\n\u0007\t\rrIA\u0004O_RD\u0017N\\4\u0011\u0007\u0019\u00139#C\u0002\u0003*\u001d\u00131!\u00118z\u0011\u001d\u0011i#\ba\u0001\u0005_\taa]8ve\u000e,\u0007\u0007\u0002B\u0019\u0005k\u0001R!\u001fB\t\u0005g\u0001BAa\u0006\u00036\u0011a!q\u0007B\u0016\u0003\u0003\u0005\tQ!\u0001\u0003\u001e\t\u0019q\fJ\u0019\t\r\tmR\u00041\u0001F\u0003\u001d!Wm\u001a:fKN\fQ!\u00194uKJ$\u0012!T\u0001\ni>\u0004FiQ8m_J$BA!\u0012\u0003VA!!q\tB)\u001b\t\u0011IE\u0003\u0003\u0003L\t5\u0013!B2pY>\u0014(\u0002\u0002B(\u0003g\n\u0001b\u001a:ba\"L7m]\u0005\u0005\u0005'\u0012IEA\u0004Q\t\u000e{Gn\u001c:\t\u000f\t-s\u00041\u0001\u0003XA!!\u0011\fB0\u001b\t\u0011YF\u0003\u0003\u0003^\u0005\u001d\u0012aA1xi&!!\u0011\rB.\u0005\u0015\u0019u\u000e\\8s\u0003\u0019Y\u0017N\u001c3PMR!!q\rB7!\r9$\u0011N\u0005\u0004\u0005W2#!\u0004$pe64\u0015.\u001a7e)f\u0004X\rC\u0004\u0003p\u0001\u0002\rA!\u001d\u0002\u0003\u0019\u0004BAa\u001d\u0003~5\u0011!Q\u000f\u0006\u0005\u0005o\u0012I(\u0001\u0003g_Jl'\u0002\u0002B>\u0003g\n1\"\u001b8uKJ\f7\r^5wK&!!q\u0010B;\u0005\u001d\u0001FIR5fY\u0012\fA#[:V]N,\b\u000f]8si\u0016$\u0017*\\1hK\u0016CH\u0003BAV\u0005\u000bCqAa\"\"\u0001\u0004\u0011I)\u0001\u0002fqB!!1\u0012BK\u001d\u0011\u0011iI!%\u000f\t\u0005u%qR\u0005\u0002\u0011&\u0019!1S$\u0002\u000fA\f7m[1hK&!!q\u0013BM\u0005%!\u0006N]8xC\ndWMC\u0002\u0003\u0014\u001e\u000b\u0011E]3n_Z,\u0017\t\u001e;bG\"lWM\u001c;Ge>lg*Y7fIR\u0013X-\u001a(pI\u0016$b!a+\u0003 \ne\u0006b\u0002BQE\u0001\u0007!1U\u0001\u0005]>$W\r\u0005\u0004\u0003&\n%&QV\u0007\u0003\u0005OS1aMA:\u0013\u0011\u0011YKa*\u0003\u001dA#e*Y7f)J,WMT8eKB!!q\u0016B[\u001b\t\u0011\tL\u0003\u0003\u00034\n\u001d\u0016!\u00054jY\u0016\u001c\b/Z2jM&\u001c\u0017\r^5p]&!!q\u0017BY\u0005i\u0001FiQ8na2,\u0007PR5mKN\u0003XmY5gS\u000e\fG/[8o\u0011\u001d\u0011YL\ta\u0001\u0003/\u000b\u0001BZ5mK:\fW.Z\u0001\u0015e\u0016\fGm\u00148ms\u0016k'-\u001a3eK\u00124\u0015\u000e\\3\u0015\t\t\u0005'q\u0019\t\u0004-\n\r\u0017b\u0001Bc/\nI\"+Z1e\u001f:d\u0017PR5mi\u0016\u0014X\rZ\"P'N#(/Z1n\u0011\u001d\u0011ic\ta\u0001\u0005\u0013\u0004DAa3\u0003PB)\u0011P!\u0005\u0003NB!!q\u0003Bh\t1\u0011\tNa2\u0002\u0002\u0003\u0005)\u0011\u0001B\u000f\u0005\ryFeM\u0001\u001baJ|G/Z2uK\u0012$S\r_3dkRLwN\\\"p]R,\u0007\u0010\u001e\u000b\u0005\u0005/\u0014I\u000e\u0006\u0002\u0002>!9\u0011\u000bJA\u0001\u0002\u0004\u0011\u0005")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditTask.class */
public class EditTask extends BaseBaseTask<EditParameters> {
    private int totalSteps = 0;
    private PDDocumentHandler documentHandler = null;
    private MultipleOutputWriter outputWriter = null;
    private PdfSourceOpener<PDDocumentHandler> documentLoader = null;
    private SystemFonts systemFonts = null;
    private final String MULTI_VALUE_SEPARATOR = Pattern.quote("|||");
    private final Map<Tuple2<String, Object>, Option<PDFont>> cachedFonts = (Map) Map$.MODULE$.apply(Nil$.MODULE$);
    private final scala.collection.immutable.Map<FileType, String> imageFormats = (scala.collection.immutable.Map) Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray(new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(FileType.BMP), "bmp"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(FileType.GIF), "gif"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(FileType.JPEG), "jpg"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(FileType.PNG), "png"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(FileType.RIFF), "webp"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(FileType.WEBP), "webp"), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(FileType.TIFF), SejdaFileExtensions.TIFF_EXTENSION)}));

    private int totalSteps() {
        return this.totalSteps;
    }

    private void totalSteps_$eq(final int x$1) {
        this.totalSteps = x$1;
    }

    private PDDocumentHandler documentHandler() {
        return this.documentHandler;
    }

    private void documentHandler_$eq(final PDDocumentHandler x$1) {
        this.documentHandler = x$1;
    }

    private MultipleOutputWriter outputWriter() {
        return this.outputWriter;
    }

    private void outputWriter_$eq(final MultipleOutputWriter x$1) {
        this.outputWriter = x$1;
    }

    private PdfSourceOpener<PDDocumentHandler> documentLoader() {
        return this.documentLoader;
    }

    private void documentLoader_$eq(final PdfSourceOpener<PDDocumentHandler> x$1) {
        this.documentLoader = x$1;
    }

    private SystemFonts systemFonts() {
        return this.systemFonts;
    }

    private void systemFonts_$eq(final SystemFonts x$1) {
        this.systemFonts = x$1;
    }

    public String MULTI_VALUE_SEPARATOR() {
        return this.MULTI_VALUE_SEPARATOR;
    }

    @Override // org.sejda.model.task.BaseTask, org.sejda.model.task.Task
    public void before(final EditParameters parameters, final TaskExecutionContext executionContext) throws TaskException {
        super.before((EditTask) parameters, executionContext);
        totalSteps_$eq(parameters.getSourceList().size());
        documentLoader_$eq(new DefaultPdfSourceOpener(executionContext));
        outputWriter_$eq(OutputWriters.newMultipleOutputWriter(parameters.getExistingOutputPolicy(), executionContext));
        systemFonts_$eq(new SystemFonts(ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(parameters.systemFonts())));
    }

    @Override // org.sejda.model.task.Task
    public void execute(final EditParameters parameters) throws TaskException {
        IntRef currentStep = IntRef.create(0);
        ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(parameters.getSourceList()).foreach(source -> {
            $anonfun$execute$1(this, currentStep, parameters, source);
            return BoxedUnit.UNIT;
        });
        parameters.getOutput().accept(outputWriter());
    }

    public static final /* synthetic */ void $anonfun$execute$1(final EditTask $this, final IntRef currentStep$1, final EditParameters parameters$1, final PdfSource source) throws IllegalStateException, TaskException {
        currentStep$1.elem++;
        $this.logger().debug("Opening {}", source);
        $this.documentHandler_$eq((PDDocumentHandler) source.open($this.documentLoader()));
        $this.documentHandler().setCreatorOnPDDocument();
        Map cachedPages = (Map) Map$.MODULE$.apply(Nil$.MODULE$);
        PDDocument doc = $this.documentHandler().getUnderlyingPDDocument();
        Map annotationsIndex = (Map) Map$.MODULE$.apply(Nil$.MODULE$);
        ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(doc.getPages()).foreach(page -> {
            return annotationsIndex.put(page, ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(page.getAnnotations()).toSeq());
        });
        Set removedAnnotationIds = (Set) Set$.MODULE$.apply(Nil$.MODULE$);
        DocumentFontSearcher docFontsSearcher = new DocumentFontSearcher(doc);
        File tmpFile = IOUtils.createTemporaryBuffer(parameters$1.getOutput());
        $this.logger().debug("Created output on temporary buffer {}", tmpFile);
        $this.documentHandler().setVersionOnPDDocument(parameters$1.getVersion());
        $this.documentHandler().setCompress(parameters$1.isCompress());
        PDRectangle firstPageMediaBox = $this.documentHandler().getPage(1).getMediaBox();
        int firstPageRotation = $this.documentHandler().getPage(1).getRotation();
        List pagesToDeleteSorted = ((List) ((IterableOnceOps) parameters$1.deletePageOperations().map(x$1 -> {
            return BoxesRunTime.boxToInteger(x$1.pageNumber());
        })).toSet().toList().sorted(Ordering$Int$.MODULE$)).reverse();
        pagesToDeleteSorted.foreach(pageNumber -> {
            $this.logger().debug("Deleting page {}", BoxesRunTime.boxToInteger(pageNumber));
            $this.ensureModifyPerm$1();
            if ($this.documentHandler().getNumberOfPages() < pageNumber) {
                ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).taskWarning(new StringBuilder(46).append("Page ").append(pageNumber).append(" was not deleted, could not be processed.").toString());
                return;
            }
            try {
                PDPage page2 = $this.documentHandler().getPage(pageNumber);
                ((IterableOnceOps) JavaConverters$.MODULE$.asScalaBufferConverter(page2.getAnnotations()).asScala()).foreach(a -> {
                    return removedAnnotationIds.$plus$eq(ObjIdUtils$.MODULE$.objIdOfOrEmpty(a));
                });
                $this.documentHandler().removePage(pageNumber);
            } catch (PageNotFoundException e) {
                $this.protected$executionContext($this).assertTaskIsLenient(e);
                ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).taskWarning(new StringBuilder(45).append("Page ").append(pageNumber).append(" was not deleted, could not be processed").toString(), e);
            }
        });
        parameters$1.deleteAttachmentOperations().foreach(operation -> {
            $anonfun$execute$7($this, cachedPages, annotationsIndex, removedAnnotationIds, operation);
            return BoxedUnit.UNIT;
        });
        parameters$1.deleteEmbeddedFileOperations().foreach(operation2 -> {
            $anonfun$execute$8($this, annotationsIndex, doc, cachedPages, removedAnnotationIds, operation2);
            return BoxedUnit.UNIT;
        });
        List pagesToInsertSorted = (List) ((IterableOnceOps) parameters$1.insertPageOperations().map(x$4 -> {
            return BoxesRunTime.boxToInteger(x$4.pageNumber());
        })).toSet().toList().sorted(Ordering$Int$.MODULE$);
        pagesToInsertSorted.foreach(pageNumber2 -> {
            return $anonfun$execute$15($this, firstPageMediaBox, firstPageRotation, BoxesRunTime.unboxToInt(pageNumber2));
        });
        parameters$1.rotatePageOperations().foreach(rotatePageOperation -> {
            $anonfun$execute$16($this, cachedPages, rotatePageOperation);
            return BoxedUnit.UNIT;
        });
        int totalPages = $this.documentHandler().getNumberOfPages();
        PageGeometricalShapeWriter shapeWriter = new PageGeometricalShapeWriter($this.documentHandler().getUnderlyingPDDocument());
        IntRef removedAnnotsFromUnderWhiteouts = IntRef.create(0);
        IntRef smallOverlapAnnotsWhiteouts = IntRef.create(0);
        parameters$1.shapeOperations().foreach(shapeOperation -> {
            $anonfun$execute$17($this, totalPages, shapeWriter, smallOverlapAnnotsWhiteouts, removedAnnotsFromUnderWhiteouts, cachedPages, removedAnnotationIds, shapeOperation);
            return BoxedUnit.UNIT;
        });
        if (removedAnnotsFromUnderWhiteouts.elem > 0) {
            String annotationsSingularOrPlural = removedAnnotsFromUnderWhiteouts.elem == 1 ? "annotation" : "annotations";
            $this.emitTaskWarning(new StringBuilder(30).append("Removed ").append(removedAnnotsFromUnderWhiteouts.elem).append(" ").append(annotationsSingularOrPlural).append(" from under whiteouts").toString());
        }
        if (smallOverlapAnnotsWhiteouts.elem > 0) {
            String annotationsSingularOrPlural2 = smallOverlapAnnotsWhiteouts.elem == 1 ? "annotation" : "annotations";
            $this.emitTaskWarning(new StringBuilder(39).append("Found ").append(smallOverlapAnnotsWhiteouts.elem).append(" ").append(annotationsSingularOrPlural2).append(" partially overlapping whiteouts").toString());
        }
        ListBuffer imagesToDelete = (ListBuffer) parameters$1.deleteImageOperations().map(o -> {
            $this.ensureModifyPerm$1();
            return new ImageRedaction($this.getPageCached$1(o.pageNumber(), cachedPages), o.pageNumber(), o.objId());
        });
        ImageRedactionResults imageRedactionResults = new PageImageRedactor().redact(ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(imagesToDelete));
        imageRedactionResults.notFound().foreach(item -> {
            $anonfun$execute$23($this, item);
            return BoxedUnit.UNIT;
        });
        parameters$1.imageOperations().foreach(imageOperation -> {
            $anonfun$execute$24($this, totalPages, cachedPages, imageOperation);
            return BoxedUnit.UNIT;
        });
        PageTextRedactor textReplacer = new PageTextRedactor(doc);
        ListBuffer editTextOperationsSorted = (ListBuffer) ((SeqOps) parameters$1.editTextOperations().sortBy(x$7 -> {
            return BoxesRunTime.boxToInteger($anonfun$execute$29(x$7));
        }, Ordering$Int$.MODULE$)).sortBy(x$8 -> {
            return BoxesRunTime.boxToInteger($anonfun$execute$30(x$8));
        }, Ordering$Int$.MODULE$);
        ListBuffer redactions = (ListBuffer) editTextOperationsSorted.map(editTextOperation -> {
            $this.ensureModifyPerm$1();
            int pageNumber3 = editTextOperation.pageNumber();
            PDPage page2 = $this.getPageCached$1(pageNumber3, cachedPages);
            String text = editTextOperation.text();
            String supportedText = FontUtils.removeUnsupportedCharacters(text, doc);
            if (supportedText != null ? !supportedText.equals(text) : text != null) {
                $this.emitUnsupportedCharactersTaskWarning$1(text, supportedText);
            }
            Option fontOpt = editTextOperation.fontOpt().map(fontFamily -> {
                return (PDFont) $this.resolveExternalFont($this.documentHandler(), fontFamily, editTextOperation.bold()).orElse(() -> {
                    return editTextOperation.fontId().flatMap(fontObjId -> {
                        return docFontsSearcher.findFontByObjId(fontObjId);
                    }).filter(f -> {
                        return BoxesRunTime.boxToBoolean($anonfun$execute$35(fontFamily, f));
                    }).orElse(() -> {
                        return docFontsSearcher.findBestMatching(fontFamily, supportedText);
                    });
                }).getOrElse(() -> {
                    return $this.fallbackFontWithWarning(fontFamily);
                });
            });
            Option fontSize = editTextOperation.fontSize();
            Option position = editTextOperation.position();
            Option color = editTextOperation.color().map(color2 -> {
                return $this.toPDColor(color2);
            });
            String originalText = editTextOperation.originalText();
            TopLeftRectangularBox boundingBox = editTextOperation.boundingBox();
            boolean fauxItalic = editTextOperation.italic();
            Option lineHeight = editTextOperation.lineHeight();
            return new Redaction(page2, pageNumber3, originalText, boundingBox, new Replacement(supportedText, fontOpt, fontSize, position, color, fauxItalic, lineHeight));
        });
        textReplacer.replaceText(ImplicitJavaConversions$.MODULE$.listBufferToImmutableSeq(redactions)).foreach(x0$5 -> {
            $anonfun$execute$41($this, x0$5);
            return BoxedUnit.UNIT;
        });
        parameters$1.appendTextOperations().foreach(textOperation -> {
            $anonfun$execute$42($this, doc, totalPages, docFontsSearcher, cachedPages, textOperation);
            return BoxedUnit.UNIT;
        });
        parameters$1.deleteHighlightOperations().foreach(operation3 -> {
            $anonfun$execute$48($this, cachedPages, annotationsIndex, removedAnnotationIds, operation3);
            return BoxedUnit.UNIT;
        });
        parameters$1.editHighlightOperations().foreach(operation4 -> {
            $anonfun$execute$49($this, cachedPages, annotationsIndex, operation4);
            return BoxedUnit.UNIT;
        });
        parameters$1.highlightTextOperations().foreach(highlightTextOperation -> {
            $anonfun$execute$50($this, cachedPages, highlightTextOperation);
            return BoxedUnit.UNIT;
        });
        parameters$1.deleteLinkOperations().foreach(operation5 -> {
            $anonfun$execute$52($this, cachedPages, annotationsIndex, removedAnnotationIds, operation5);
            return BoxedUnit.UNIT;
        });
        parameters$1.editLinkOperations().foreach(operation6 -> {
            $anonfun$execute$53($this, cachedPages, annotationsIndex, operation6);
            return BoxedUnit.UNIT;
        });
        parameters$1.appendLinkOperations().foreach(operation7 -> {
            $anonfun$execute$55($this, cachedPages, operation7);
            return BoxedUnit.UNIT;
        });
        parameters$1.appendEmbeddedFileOperations().foreach(operation8 -> {
            $anonfun$execute$57($this, doc, operation8);
            return BoxedUnit.UNIT;
        });
        parameters$1.appendAttachmentOperations().foreach(operation9 -> {
            $anonfun$execute$59($this, doc, cachedPages, operation9);
            return BoxedUnit.UNIT;
        });
        AcroFormsHelper acroFormHelper = new AcroFormsHelper(doc);
        parameters$1.appendDraw().foreach(op -> {
            return BoxesRunTime.boxToBoolean($anonfun$execute$61($this, acroFormHelper, cachedPages, op));
        });
        parameters$1.appendLine().foreach(op2 -> {
            return BoxesRunTime.boxToBoolean($anonfun$execute$62($this, acroFormHelper, cachedPages, op2));
        });
        if (parameters$1.formErrors().nonEmpty()) {
            $this.logger().warn(new StringBuilder(36).append("Form errors received from frontend: ").append(parameters$1.formErrors()).toString());
            throw new RuntimeException(new StringBuilder(43).append("Sanity check failed, form errors received: ").append(parameters$1.formErrors()).toString());
        }
        if (parameters$1.fillFormOperations().nonEmpty() && acroFormHelper.hasXfa()) {
            $this.emitTaskWarning("Document XFA-based form features are no longer available");
            acroFormHelper.removeXfa();
        }
        parameters$1.fillFormOperations().foreach(operation10 -> {
            $anonfun$execute$73($this, acroFormHelper, doc, operation10);
            return BoxedUnit.UNIT;
        });
        parameters$1.formWidgets().foreach(x0$7 -> {
            $anonfun$execute$75($this, removedAnnotationIds, parameters$1, acroFormHelper, x0$7);
            return BoxedUnit.UNIT;
        });
        parameters$1.deleteFormFieldOperations().foreach(operation11 -> {
            $anonfun$execute$79($this, acroFormHelper, cachedPages, removedAnnotationIds, operation11);
            return BoxedUnit.UNIT;
        });
        Map addedFields = (Map) Map$.MODULE$.apply(Nil$.MODULE$);
        Map addedWidgets = (Map) Map$.MODULE$.apply(Nil$.MODULE$);
        parameters$1.appendFormFieldOperations().foreach(operation12 -> {
            $anonfun$execute$81($this, addedFields, acroFormHelper, addedWidgets, cachedPages, doc, operation12);
            return BoxedUnit.UNIT;
        });
        acroFormHelper.postProcessWidgetAndFieldWhereOneKid(addedFields);
        RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), totalPages).foreach$mVc$sp(pageNumber3 -> {
            PDPage page2 = $this.getPageCached$1(pageNumber3, cachedPages);
            if (parameters$1.tabOrder().nonEmpty()) {
                Buffer annotationsList = (Buffer) JavaConverters$.MODULE$.asScalaBufferConverter(page2.getAnnotations()).asScala();
                scala.collection.immutable.Map positionMap = ((IterableOnceOps) parameters$1.tabOrder().zipWithIndex()).toMap($less$colon$less$.MODULE$.refl());
                Buffer reordered = (Buffer) annotationsList.sortBy(annotation -> {
                    return BoxesRunTime.boxToInteger($anonfun$execute$88(positionMap, addedWidgets, annotation));
                }, Ordering$Int$.MODULE$);
                page2.setAnnotations((java.util.List) JavaConverters$.MODULE$.bufferAsJavaListConverter(reordered).asJava());
                page2.setTabs(Tabs.STRUCTURE_ORDER);
            }
        });
        $this.clipSignatures($this.documentHandler().getUnderlyingPDDocument());
        $this.documentHandler().savePDDocument(tmpFile, parameters$1.getOutput().getEncryptionAtRestPolicy());
        String outName = NameGenerator.nameGenerator(parameters$1.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().originalName(source.getName()).fileNumber(currentStep$1.elem));
        $this.outputWriter().addOutput(FileOutput.file(tmpFile).name(outName));
        FontUtils.clearLoadedFontCache($this.documentHandler().getUnderlyingPDDocument());
        ApplicationEventsNotifier.notifyEvent($this.protected$executionContext($this).notifiableTaskMetadata()).stepsCompleted(currentStep$1.elem).outOf($this.totalSteps());
    }

    private final void ensureModifyPerm$1() throws TaskPermissionsException {
        documentHandler().getPermissions().ensurePermission(PdfAccessPermission.MODIFY);
    }

    private final PDPage getPageCached$1(final int num, final Map cachedPages$1) {
        return (PDPage) cachedPages$1.getOrElseUpdate(BoxesRunTime.boxToInteger(num), () -> {
            return this.documentHandler().getPage(num);
        });
    }

    private static final Option findAnnotation$1(final PDPage page, final int index, final Map annotationsIndex$1) {
        return (Option) ((PartialFunction) annotationsIndex$1.apply(page)).lift().apply(BoxesRunTime.boxToInteger(index));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeAnnotation$1(final PDPage page, final PDAnnotation toRemove, final Set removedAnnotationIds$1) {
        ArrayList annots = new ArrayList(page.getAnnotations());
        annots.remove(toRemove);
        page.setAnnotations(annots);
        removedAnnotationIds$1.$plus$eq(ObjIdUtils$.MODULE$.objIdOfOrEmpty(toRemove));
    }

    public /* synthetic */ TaskExecutionContext protected$executionContext(final EditTask x$1) {
        return x$1.executionContext();
    }

    public static final /* synthetic */ void $anonfun$execute$7(final EditTask $this, final Map cachedPages$1, final Map annotationsIndex$1, final Set removedAnnotationIds$1, final DeleteAttachmentOperation operation) {
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        Some someFindAnnotation$1 = findAnnotation$1(page, operation.id(), annotationsIndex$1);
        if (someFindAnnotation$1 instanceof Some) {
            PDAnnotation toRemove = (PDAnnotation) someFindAnnotation$1.value();
            if (toRemove instanceof PDAnnotationFileAttachment) {
                removeAnnotation$1(page, toRemove, removedAnnotationIds$1);
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        $this.emitTaskWarning(new StringBuilder(36).append("Could not remove attachment on page ").append(operation.pageNumber()).toString());
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ void $anonfun$execute$8(final EditTask $this, final Map annotationsIndex$1, final PDDocument doc$1, final Map cachedPages$1, final Set removedAnnotationIds$1, final DeleteEmbeddedFileOperation operation) {
        String filename = operation.filename();
        RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), $this.documentHandler().getNumberOfPages()).foreach$mVc$sp(pageNumber -> {
            PDPage page = $this.getPageCached$1(pageNumber, cachedPages$1);
            ((IterableOnceOps) annotationsIndex$1.apply(page)).foreach(x0$1 -> {
                $anonfun$execute$10($this, filename, page, removedAnnotationIds$1, x0$1);
                return BoxedUnit.UNIT;
            });
        });
        Option$.MODULE$.apply(doc$1.getDocumentCatalog().getNames()).map(x$3 -> {
            return x$3.getEmbeddedFiles();
        }).foreach(embeddedFiles -> {
            $anonfun$execute$13($this, filename, embeddedFiles);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$execute$10(final EditTask $this, final String filename$1, final PDPage page$1, final Set removedAnnotationIds$1, final PDAnnotation x0$1) {
        if (x0$1 instanceof PDAnnotationFileAttachment) {
            PDAnnotationFileAttachment pDAnnotationFileAttachment = (PDAnnotationFileAttachment) x0$1;
            if (Option$.MODULE$.apply(pDAnnotationFileAttachment.getFile()).exists(x$2 -> {
                return BoxesRunTime.boxToBoolean($anonfun$execute$11(filename$1, x$2));
            })) {
                $this.logger().info(new StringBuilder(62).append("Removing attachment annotation from page: ").append(page$1).append(" matching filename: ").append(filename$1).toString());
                removeAnnotation$1(page$1, pDAnnotationFileAttachment, removedAnnotationIds$1);
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ boolean $anonfun$execute$11(final String filename$1, final PDFileSpecification x$2) {
        String file = x$2.getFile();
        return file != null ? file.equals(filename$1) : filename$1 == null;
    }

    public static final /* synthetic */ void $anonfun$execute$13(final EditTask $this, final String filename$1, final PDEmbeddedFilesNameTreeNode embeddedFiles) throws IOException {
        boolean removed = $this.removeAttachmentFromNamedTreeNode(embeddedFiles, filename$1);
        if (removed) {
            return;
        }
        $this.logger().warn(new StringBuilder(29).append("Could not remove attachment: ").append(filename$1).toString());
        $this.emitTaskWarning(new StringBuilder(29).append("Could not remove attachment: ").append(filename$1).toString());
    }

    public static final /* synthetic */ PDPage $anonfun$execute$15(final EditTask $this, final PDRectangle firstPageMediaBox$1, final int firstPageRotation$1, final int pageNumber) throws TaskPermissionsException {
        $this.ensureModifyPerm$1();
        if ($this.documentHandler().getNumberOfPages() == 0) {
            return $this.documentHandler().addBlankPage(firstPageMediaBox$1.rotate(firstPageRotation$1));
        }
        if (pageNumber > 1) {
            $this.logger().debug(new StringBuilder(27).append("Adding new page after page ").append(pageNumber - 1).toString());
            return $this.documentHandler().addBlankPageAfter(pageNumber - 1);
        }
        $this.logger().debug(new StringBuilder(28).append("Adding new page before page ").append(pageNumber).toString());
        return $this.documentHandler().addBlankPageBefore(pageNumber);
    }

    public static final /* synthetic */ void $anonfun$execute$16(final EditTask $this, final Map cachedPages$1, final RotatePageOperation rotatePageOperation) throws TaskPermissionsException {
        $this.ensureModifyPerm$1();
        PDPage page = $this.getPageCached$1(rotatePageOperation.pageNumber(), cachedPages$1);
        PdfRotator.rotate(page, rotatePageOperation.rotation());
    }

    public static final /* synthetic */ void $anonfun$execute$17(final EditTask $this, final int totalPages$1, final PageGeometricalShapeWriter shapeWriter$1, final IntRef smallOverlapAnnotsWhiteouts$1, final IntRef removedAnnotsFromUnderWhiteouts$1, final Map cachedPages$1, final Set removedAnnotationIds$1, final AddShapeOperation shapeOperation) throws TaskPermissionsException {
        $this.ensureModifyPerm$1();
        SortedSet pageNumbers = shapeOperation.pageRange().getPages(totalPages$1);
        ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(pageNumbers).foreach(pageNumber -> {
            $anonfun$execute$18($this, shapeOperation, shapeWriter$1, smallOverlapAnnotsWhiteouts$1, removedAnnotsFromUnderWhiteouts$1, cachedPages$1, removedAnnotationIds$1, pageNumber);
            return BoxedUnit.UNIT;
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$execute$18(final EditTask $this, final AddShapeOperation shapeOperation$1, final PageGeometricalShapeWriter shapeWriter$1, final IntRef smallOverlapAnnotsWhiteouts$1, final IntRef removedAnnotsFromUnderWhiteouts$1, final Map cachedPages$1, final Set removedAnnotationIds$1, final Integer pageNumber) throws MatchError, TaskIOException {
        Tuple4 tuple4;
        PDPage page = $this.getPageCached$1(Predef$.MODULE$.Integer2int(pageNumber), cachedPages$1);
        PDRectangle mediaBox = page.getMediaBox();
        PDRectangle cropBox = page.getCropBox();
        float _cx1 = cropBox.getLowerLeftX();
        float _cy1 = cropBox.getLowerLeftY();
        float _cx2 = mediaBox.getUpperRightX() - cropBox.getUpperRightX();
        float _cy2 = mediaBox.getUpperRightY() - cropBox.getUpperRightY();
        double _x = shapeOperation$1.position().getX();
        double _y = shapeOperation$1.position().getY();
        float _w = shapeOperation$1.width();
        float _h = shapeOperation$1.height();
        float pageWidth = mediaBox.getWidth();
        float pageHeight = mediaBox.getHeight();
        switch (page.getRotation()) {
            case 90:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble(((pageWidth - _y) - _h) - _cx2), BoxesRunTime.boxToDouble(_x + _cy1), BoxesRunTime.boxToFloat(_h), BoxesRunTime.boxToFloat(_w));
                break;
            case 180:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble(((pageWidth - _x) - _w) - _cx2), BoxesRunTime.boxToDouble(((pageHeight - _y) - _h) - _cy2), BoxesRunTime.boxToFloat(_w), BoxesRunTime.boxToFloat(_h));
                break;
            case 270:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble(_y + _cx1), BoxesRunTime.boxToDouble(((pageHeight - _x) - _w) - _cy2), BoxesRunTime.boxToFloat(_h), BoxesRunTime.boxToFloat(_w));
                break;
            default:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble(_x + _cx1), BoxesRunTime.boxToDouble(_y + _cy1), BoxesRunTime.boxToFloat(_w), BoxesRunTime.boxToFloat(_h));
                break;
        }
        Tuple4 tuple42 = tuple4;
        if (tuple42 == null) {
            throw new MatchError(tuple42);
        }
        double x = BoxesRunTime.unboxToDouble(tuple42._1());
        double y = BoxesRunTime.unboxToDouble(tuple42._2());
        float w = BoxesRunTime.unboxToFloat(tuple42._3());
        float h = BoxesRunTime.unboxToFloat(tuple42._4());
        Tuple4 tuple43 = new Tuple4(BoxesRunTime.boxToDouble(x), BoxesRunTime.boxToDouble(y), BoxesRunTime.boxToFloat(w), BoxesRunTime.boxToFloat(h));
        double x2 = BoxesRunTime.unboxToDouble(tuple43._1());
        double y2 = BoxesRunTime.unboxToDouble(tuple43._2());
        float w2 = BoxesRunTime.unboxToFloat(tuple43._3());
        float h2 = BoxesRunTime.unboxToFloat(tuple43._4());
        Point p = new Point((int) x2, (int) y2);
        shapeWriter$1.drawShape(shapeOperation$1.shape(), page, p, w2, h2, shapeOperation$1.borderColor(), shapeOperation$1.backgroundColor(), shapeOperation$1.borderWidth());
        if (shapeOperation$1.whiteout()) {
            Seq annotationsWithOverlapPercentage = AnnotationsHelper$.MODULE$.findAnnotationsInBoundingBox(page, shapeOperation$1.position(), shapeOperation$1.width(), shapeOperation$1.height());
            Seq toRemove = (Seq) annotationsWithOverlapPercentage.flatMap(x0$2 -> {
                if (x0$2 != null) {
                    PDAnnotation annot = (PDAnnotation) x0$2._1();
                    float overlapPercent = BoxesRunTime.unboxToFloat(x0$2._2());
                    return overlapPercent >= ((float) 50) ? new Some(annot) : None$.MODULE$;
                }
                throw new MatchError(x0$2);
            });
            Seq smallOverlapping = (Seq) annotationsWithOverlapPercentage.flatMap(x0$3 -> {
                if (x0$3 != null) {
                    PDAnnotation annot = (PDAnnotation) x0$3._1();
                    float overlapPercent = BoxesRunTime.unboxToFloat(x0$3._2());
                    return (overlapPercent <= ((float) 0) || overlapPercent >= ((float) 50)) ? None$.MODULE$ : new Some(annot);
                }
                throw new MatchError(x0$3);
            });
            smallOverlapAnnotsWhiteouts$1.elem += smallOverlapping.size();
            if (toRemove.nonEmpty()) {
                removedAnnotsFromUnderWhiteouts$1.elem += toRemove.size();
                toRemove.foreach(annot -> {
                    removeAnnotation$1(page, annot, removedAnnotationIds$1);
                    return BoxedUnit.UNIT;
                });
            }
        }
    }

    public static final /* synthetic */ void $anonfun$execute$23(final EditTask $this, final ImageRedaction item) {
        $this.emitTaskWarning(new StringBuilder(31).append("Could not delete image on page ").append(item.pageNum()).toString());
    }

    public static final /* synthetic */ void $anonfun$execute$24(final EditTask $this, final int totalPages$1, final Map cachedPages$1, final AddImageOperation imageOperation) throws TaskPermissionsException {
        $this.ensureModifyPerm$1();
        PageImageWriter imageWriter = new PageImageWriter($this.documentHandler().getUnderlyingPDDocument());
        try {
            PDImageXObject image = PageImageWriter.toPDXImageObject($this.rotate(imageOperation.imageSource(), imageOperation.rotation()));
            SortedSet pageNumbers = imageOperation.pageRange().getPages(totalPages$1);
            ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(pageNumbers).withFilter(pageNumber -> {
                return BoxesRunTime.boxToBoolean($anonfun$execute$25(imageOperation, pageNumber));
            }).foreach(pageNumber2 -> {
                $anonfun$execute$26($this, imageOperation, imageWriter, image, cachedPages$1, pageNumber2);
                return BoxedUnit.UNIT;
            });
        } catch (Throwable th) {
            if (!(th instanceof Exception) || !$this.isUnsupportedImageEx((Exception) th)) {
                throw th;
            }
            $this.emitTaskWarning(new StringBuilder(19).append("Unsupported image: ").append(imageOperation.imageSource().getName()).toString());
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }

    public static final /* synthetic */ boolean $anonfun$execute$25(final AddImageOperation imageOperation$1, final Integer pageNumber) {
        return !imageOperation$1.exceptPages().contains(pageNumber) && imageOperation$1.width() > ((float) 0) && imageOperation$1.height() > ((float) 0);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$execute$26(final EditTask $this, final AddImageOperation imageOperation$1, final PageImageWriter imageWriter$1, final PDImageXObject image$1, final Map cachedPages$1, final Integer pageNumber) throws MatchError, TaskIOException {
        Tuple4 tuple4;
        PDPage page = $this.getPageCached$1(Predef$.MODULE$.Integer2int(pageNumber), cachedPages$1);
        PDRectangle mediaBox = page.getMediaBox();
        PDRectangle cropBox = page.getCropBox();
        float _cx1 = cropBox.getLowerLeftX();
        float _cy1 = cropBox.getLowerLeftY();
        float _cx2 = mediaBox.getUpperRightX() - cropBox.getUpperRightX();
        float _cy2 = mediaBox.getUpperRightY() - cropBox.getUpperRightY();
        double _x = imageOperation$1.position().getX();
        double _y = imageOperation$1.position().getY();
        float _w = imageOperation$1.width();
        float _h = imageOperation$1.height();
        float pageWidth = mediaBox.getWidth();
        float pageHeight = mediaBox.getHeight();
        switch (page.getRotation()) {
            case 90:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble((pageWidth - _y) - _cx2), BoxesRunTime.boxToDouble(_x + _cy1), BoxesRunTime.boxToFloat(_h), BoxesRunTime.boxToFloat(_w));
                break;
            case 180:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble((pageWidth - _x) - _cx2), BoxesRunTime.boxToDouble((pageHeight - _y) - _cy2), BoxesRunTime.boxToFloat(_w), BoxesRunTime.boxToFloat(_h));
                break;
            case 270:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble(_y + _cx1), BoxesRunTime.boxToDouble((pageHeight - _x) - _cy2), BoxesRunTime.boxToFloat(_h), BoxesRunTime.boxToFloat(_w));
                break;
            default:
                tuple4 = new Tuple4(BoxesRunTime.boxToDouble(_x + _cx1), BoxesRunTime.boxToDouble(_y + _cy1), BoxesRunTime.boxToFloat(_w), BoxesRunTime.boxToFloat(_h));
                break;
        }
        Tuple4 tuple42 = tuple4;
        if (tuple42 == null) {
            throw new MatchError(tuple42);
        }
        double x = BoxesRunTime.unboxToDouble(tuple42._1());
        double y = BoxesRunTime.unboxToDouble(tuple42._2());
        Tuple4 tuple43 = new Tuple4(BoxesRunTime.boxToDouble(x), BoxesRunTime.boxToDouble(y), BoxesRunTime.boxToFloat(BoxesRunTime.unboxToFloat(tuple42._3())), BoxesRunTime.boxToFloat(BoxesRunTime.unboxToFloat(tuple42._4())));
        double x2 = BoxesRunTime.unboxToDouble(tuple43._1());
        double y2 = BoxesRunTime.unboxToDouble(tuple43._2());
        float w = BoxesRunTime.unboxToFloat(tuple43._3());
        float h = BoxesRunTime.unboxToFloat(tuple43._4());
        Point2D point = new Point((int) x2, (int) y2);
        AnnotationsHelper$.MODULE$.findAnnotationsInBoundingBox(page, point, w, h).foreach(x0$4 -> {
            $anonfun$execute$27(x0$4);
            return BoxedUnit.UNIT;
        });
        imageWriter$1.append(page, image$1, point, w, h, (PDExtendedGraphicsState) null, page.getRotation());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    /* JADX WARN: Removed duplicated region for block: B:22:0x009e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ void $anonfun$execute$27(final scala.Tuple2 r5) throws scala.MatchError {
        /*
            r0 = r5
            r8 = r0
            r0 = r8
            if (r0 == 0) goto Laa
            r0 = r8
            java.lang.Object r0 = r0._1()
            org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation r0 = (org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation) r0
            r9 = r0
            r0 = r8
            java.lang.Object r0 = r0._2()
            float r0 = scala.runtime.BoxesRunTime.unboxToFloat(r0)
            r10 = r0
            r0 = r9
            r11 = r0
            r0 = r11
            boolean r0 = r0 instanceof org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget
            if (r0 == 0) goto L9b
            r0 = r11
            org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget r0 = (org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget) r0
            r12 = r0
            r0 = r10
            r1 = 0
            float r1 = (float) r1
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L98
            code.util.pdf.ObjIdUtils$ r0 = code.util.pdf.ObjIdUtils$.MODULE$
            r1 = r12
            org.sejda.sambox.cos.COSDictionary r1 = r1.getCOSObject()
            org.sejda.sambox.cos.COSName r2 = org.sejda.sambox.cos.COSName.FT
            code.util.pdf.ObjIdUtils$ r3 = code.util.pdf.ObjIdUtils$.MODULE$
            scala.collection.immutable.Set r3 = r3.getInheritableAttribute$default$3()
            org.sejda.sambox.cos.COSBase r0 = r0.getInheritableAttribute(r1, r2, r3)
            r13 = r0
            r0 = r13
            org.sejda.sambox.cos.COSName r1 = org.sejda.sambox.cos.COSName.SIG
            r14 = r1
            r1 = r0
            if (r1 != 0) goto L5d
        L55:
            r0 = r14
            if (r0 == 0) goto L65
            goto L91
        L5d:
            r1 = r14
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L91
        L65:
            r0 = r12
            org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary r0 = r0.getAppearanceCharacteristics()
            r15 = r0
            r0 = r15
            if (r0 == 0) goto L8a
            r0 = r15
            org.sejda.sambox.cos.COSDictionary r0 = r0.getCOSObject()
            org.sejda.sambox.cos.COSName r1 = org.sejda.sambox.cos.COSName.BG
            r0.removeItem(r1)
            r0 = r12
            r1 = r15
            r0.setAppearanceCharacteristics(r1)
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto La5
        L8a:
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto La5
        L91:
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto La5
        L98:
            goto L9e
        L9b:
            goto L9e
        L9e:
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto La5
        La5:
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            return
        Laa:
            goto Lad
        Lad:
            scala.MatchError r0 = new scala.MatchError
            r1 = r0
            r2 = r8
            r1.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.edit.EditTask.$anonfun$execute$27(scala.Tuple2):void");
    }

    private static final int truncateForDisplay$default$2$1() {
        return 20;
    }

    private static final String truncateForDisplay$1(final String s, final int len) {
        return s.length() <= len ? s : new StringBuilder(3).append(StringOps$.MODULE$.take$extension(Predef$.MODULE$.augmentString(s), len).trim()).append("...").toString();
    }

    private final void emitUnsupportedCharactersTaskWarning$1(final String text, final String supportedText) {
        scala.collection.immutable.Set unsupportedChars = Predef$.MODULE$.wrapCharArray(text.toCharArray()).toSet().diff(Predef$.MODULE$.wrapCharArray(supportedText.toCharArray()).toSet());
        if (unsupportedChars.nonEmpty()) {
            String displayUnsupportedChars = ((IterableOnceOps) unsupportedChars.map(c -> {
                return $anonfun$execute$28(BoxesRunTime.unboxToChar(c));
            })).mkString(", ");
            emitTaskWarning(new StringBuilder(42).append("Unsupported characters (").append(displayUnsupportedChars).append(") were removed: '").append(truncateForDisplay$1(text, truncateForDisplay$default$2$1())).append(OperatorName.SHOW_TEXT_LINE).toString());
        }
    }

    public static final /* synthetic */ String $anonfun$execute$28(final char c) {
        return StringUtils.asUnicodes(Character.toString(c));
    }

    public static final /* synthetic */ int $anonfun$execute$29(final EditTextOperation x$7) {
        return x$7.boundingBox().getHeight();
    }

    public static final /* synthetic */ int $anonfun$execute$30(final EditTextOperation x$8) {
        return -x$8.boundingBox().getLeft();
    }

    public static final /* synthetic */ boolean $anonfun$execute$35(final String fontFamily$1, final PDFont f) {
        String name = f.getName();
        if (name != null ? !name.equals(fontFamily$1) : fontFamily$1 != null) {
            String strFamilyNameWithoutSubsetPrefix = FontsHelper$.MODULE$.familyNameWithoutSubsetPrefix(name);
            if (strFamilyNameWithoutSubsetPrefix != null ? !strFamilyNameWithoutSubsetPrefix.equals(fontFamily$1) : fontFamily$1 != null) {
                Object orElse = Option$.MODULE$.apply(f.getFontDescriptor()).map(x$9 -> {
                    return x$9.getFontName();
                }).getOrElse(() -> {
                    return "";
                });
                if (orElse != null ? !orElse.equals(fontFamily$1) : fontFamily$1 != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$execute$41(final EditTask $this, final Tuple2 x0$5) throws MatchError {
        if (x0$5 != null) {
            Redaction redaction = (Redaction) x0$5._1();
            RedactionResult redacted = (RedactionResult) x0$5._2();
            if (redacted.textFoundAndRedacted()) {
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            } else {
                $this.emitTaskWarning(new StringBuilder(29).append("Problem replacing '").append(truncateForDisplay$1(redaction.originalText(), truncateForDisplay$default$2$1())).append("' on page ").append(redaction.pageNumber()).toString());
                BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                return;
            }
        }
        throw new MatchError(x0$5);
    }

    public static final /* synthetic */ void $anonfun$execute$42(final EditTask $this, final PDDocument doc$1, final int totalPages$1, final DocumentFontSearcher docFontsSearcher$1, final Map cachedPages$1, final AppendTextOperation textOperation) throws TaskPermissionsException {
        $this.ensureModifyPerm$1();
        PageTextWriter textWriter = new PageTextWriter(doc$1);
        SortedSet pageNumbers = textOperation.pageRange().getPages(totalPages$1);
        ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(pageNumbers).foreach(pageNumber -> {
            $anonfun$execute$43($this, textOperation, doc$1, docFontsSearcher$1, textWriter, cachedPages$1, pageNumber);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$execute$43(final EditTask $this, final AppendTextOperation textOperation$1, final PDDocument doc$1, final DocumentFontSearcher docFontsSearcher$1, final PageTextWriter textWriter$1, final Map cachedPages$1, final Integer pageNumber) {
        PDPage page = $this.getPageCached$1(Predef$.MODULE$.Integer2int(pageNumber), cachedPages$1);
        List lines = Predef$.MODULE$.wrapRefArray(StringOps$.MODULE$.replaceAllLiterally$extension(Predef$.MODULE$.augmentString(textOperation$1.text()), "\r\n", "\n").split("\\n")).toList();
        ((List) lines.zipWithIndex()).foreach(x0$6 -> {
            $anonfun$execute$44($this, doc$1, textOperation$1, docFontsSearcher$1, textWriter$1, page, x0$6);
            return BoxedUnit.UNIT;
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$execute$44(final EditTask $this, final PDDocument doc$1, final AppendTextOperation textOperation$1, final DocumentFontSearcher docFontsSearcher$1, final PageTextWriter textWriter$1, final PDPage page$3, final Tuple2 x0$6) throws MatchError, TaskIOException, IOException {
        double dMin;
        boolean zItalic;
        if (x0$6 != null) {
            String text = (String) x0$6._1();
            int i = x0$6._2$mcI$sp();
            String supportedText = FontUtils.removeUnsupportedCharacters(text, doc$1);
            if (supportedText != null ? !supportedText.equals(text) : text != null) {
                $this.emitUnsupportedCharactersTaskWarning$1(text, supportedText);
            }
            String fontFamily = textOperation$1.font();
            PDFont font = (PDFont) $this.resolveExternalFont($this.documentHandler(), fontFamily, textOperation$1.bold()).orElse(() -> {
                return docFontsSearcher$1.findBestMatching(fontFamily, supportedText);
            }).getOrElse(() -> {
                return $this.fallbackFontWithWarning(fontFamily);
            });
            Point2D originalPosition = textOperation$1.position();
            double fontSize = textOperation$1.fontSize();
            double lineHeight = BoxesRunTime.unboxToDouble(textOperation$1.lineHeight().getOrElse(() -> {
                return (font.getBoundingBox().getHeight() / 1000) * fontSize;
            }));
            PDType1Font pDType1FontCOURIER = PDType1Font.COURIER();
            if (font != null ? font.equals(pDType1FontCOURIER) : pDType1FontCOURIER == null) {
                dMin = (-0.30029297000000005d) * fontSize;
            } else {
                dMin = Math.min(0.0d, (font.getFontDescriptor().getDescent() / 1000) * fontSize);
            }
            double descent = dMin;
            Point position = new Point((int) originalPosition.getX(), (int) ((originalPosition.getY() - (i * lineHeight)) - descent));
            if (FontUtils.isItalic(font)) {
                zItalic = false;
            } else {
                zItalic = textOperation$1.italic();
            }
            boolean fauxItalic = zItalic;
            textWriter$1.write(page$3, position, supportedText, font, Predef$.MODULE$.double2Double(fontSize), $this.toPDColor(textOperation$1.color()), RenderingMode.FILL, fauxItalic);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
            return;
        }
        throw new MatchError(x0$6);
    }

    public static final /* synthetic */ void $anonfun$execute$48(final EditTask $this, final Map cachedPages$1, final Map annotationsIndex$1, final Set removedAnnotationIds$1, final DeleteHighlightOperation operation) {
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        Some someFindAnnotation$1 = findAnnotation$1(page, operation.id(), annotationsIndex$1);
        if (!(someFindAnnotation$1 instanceof Some)) {
            $this.emitTaskWarning(new StringBuilder(35).append("Could not remove highlight on page ").append(operation.pageNumber()).toString());
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            PDAnnotation toRemove = (PDAnnotation) someFindAnnotation$1.value();
            removeAnnotation$1(page, toRemove, removedAnnotationIds$1);
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
    }

    public static final /* synthetic */ void $anonfun$execute$49(final EditTask $this, final Map cachedPages$1, final Map annotationsIndex$1, final EditHighlightOperation operation) {
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        Some someFindAnnotation$1 = findAnnotation$1(page, operation.id(), annotationsIndex$1);
        if (someFindAnnotation$1 instanceof Some) {
            PDAnnotation toEdit = (PDAnnotation) someFindAnnotation$1.value();
            if (toEdit instanceof PDAnnotationTextMarkup) {
                ((PDAnnotationTextMarkup) toEdit).setColor($this.toPDColor(operation.color()));
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        $this.emitTaskWarning(new StringBuilder(33).append("Could not edit highlight on page ").append(operation.pageNumber()).toString());
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ void $anonfun$execute$50(final EditTask $this, final Map cachedPages$1, final HighlightTextOperation highlightTextOperation) {
        PDPage page = $this.getPageCached$1(highlightTextOperation.pageNumber(), cachedPages$1);
        highlightTextOperation.boundingBoxes().foreach(boundingBox -> {
            return BoxesRunTime.boxToBoolean($anonfun$execute$51($this, highlightTextOperation, page, boundingBox));
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ boolean $anonfun$execute$51(final EditTask $this, final HighlightTextOperation highlightTextOperation$1, final PDPage page$4, final RectangularBox boundingBox) throws MatchError {
        Tuple2 tuple2;
        HighlightType highlightTypeKind = highlightTextOperation$1.kind();
        if (HighlightType$Strikethrough$.MODULE$.equals(highlightTypeKind)) {
            tuple2 = new Tuple2(PDAnnotationTextMarkup.SUB_TYPE_STRIKEOUT, BoxesRunTime.boxToFloat(1.0f));
        } else if (HighlightType$Highlight$.MODULE$.equals(highlightTypeKind)) {
            tuple2 = new Tuple2(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT, BoxesRunTime.boxToFloat(0.4f));
        } else {
            if (!HighlightType$Underline$.MODULE$.equals(highlightTypeKind)) {
                throw new MatchError(highlightTypeKind);
            }
            tuple2 = new Tuple2("Underline", BoxesRunTime.boxToFloat(1.0f));
        }
        Tuple2 tuple22 = tuple2;
        if (tuple22 == null) {
            throw new MatchError(tuple22);
        }
        String subtype = (String) tuple22._1();
        float opacity = BoxesRunTime.unboxToFloat(tuple22._2());
        Tuple2 tuple23 = new Tuple2(subtype, BoxesRunTime.boxToFloat(opacity));
        String subtype2 = (String) tuple23._1();
        float opacity2 = BoxesRunTime.unboxToFloat(tuple23._2());
        PDAnnotationTextMarkup markup = new PDAnnotationTextMarkup(subtype2);
        PDRectangle rect = AnnotationsHelper$.MODULE$.toPDRectangle(AnnotationsHelper$.MODULE$.boundingBoxAdjustedToPage(boundingBox, page$4));
        markup.setRectangle(rect);
        markup.setQuadPoints(AnnotationsHelper$.MODULE$.quadsOf(rect));
        markup.setConstantOpacity(opacity2);
        markup.setColor($this.toPDColor(highlightTextOperation$1.color()));
        markup.setPrinted(true);
        return page$4.getAnnotations().add(markup);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    private final void updateLinkAnnotationAction$1(final PDAnnotationLink annotation, final LinkType kind, final String href, final PDPage page, final RectangularBox boundingBox, final Map cachedPages$1) throws MatchError, IOException {
        if (!LinkType$URI$.MODULE$.equals(kind)) {
            if (!LinkType$Page$.MODULE$.equals(kind)) {
                throw new MatchError(kind);
            }
            PDAction action = annotation.getAction();
            PDActionGoTo action2 = action instanceof PDActionGoTo ? (PDActionGoTo) action : new PDActionGoTo();
            PDDestination destination = action2.getDestination();
            PDPageDestination pageDest = destination instanceof PDPageDestination ? (PDPageDestination) destination : new PDPageXYZDestination();
            int destPageNumber = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(href));
            PDPage destinationPage = getPageCached$1(destPageNumber, cachedPages$1);
            pageDest.setPage(destinationPage);
            action2.setDestination(pageDest);
            annotation.setAction(action2);
            annotation.setDestination(null);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            PDAction action3 = annotation.getAction();
            PDActionURI action4 = action3 instanceof PDActionURI ? (PDActionURI) action3 : new PDActionURI();
            action4.setURI(href);
            annotation.setAction(action4);
            annotation.setDestination(null);
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
        }
        annotation.setRectangle(AnnotationsHelper$.MODULE$.toPDRectangle(AnnotationsHelper$.MODULE$.boundingBoxAdjustedToPage(boundingBox, page)));
    }

    public static final /* synthetic */ void $anonfun$execute$52(final EditTask $this, final Map cachedPages$1, final Map annotationsIndex$1, final Set removedAnnotationIds$1, final DeleteLinkOperation operation) {
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        Some someFindAnnotation$1 = findAnnotation$1(page, operation.id(), annotationsIndex$1);
        if (someFindAnnotation$1 instanceof Some) {
            PDAnnotation toRemove = (PDAnnotation) someFindAnnotation$1.value();
            if (toRemove instanceof PDAnnotationLink) {
                removeAnnotation$1(page, toRemove, removedAnnotationIds$1);
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        $this.emitTaskWarning(new StringBuilder(30).append("Could not remove link on page ").append(operation.pageNumber()).toString());
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ void $anonfun$execute$53(final EditTask $this, final Map cachedPages$1, final Map annotationsIndex$1, final EditLinkOperation operation) {
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        operation.boundingBoxes().foreach(boundingBox -> {
            $anonfun$execute$54($this, page, operation, annotationsIndex$1, cachedPages$1, boundingBox);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$execute$54(final EditTask $this, final PDPage page$5, final EditLinkOperation operation$1, final Map annotationsIndex$1, final Map cachedPages$1, final RectangularBox boundingBox) {
        Some someFindAnnotation$1 = findAnnotation$1(page$5, operation$1.id(), annotationsIndex$1);
        if (someFindAnnotation$1 instanceof Some) {
            PDAnnotation toEdit = (PDAnnotation) someFindAnnotation$1.value();
            if (toEdit instanceof PDAnnotationLink) {
                try {
                    $this.updateLinkAnnotationAction$1((PDAnnotationLink) toEdit, operation$1.kind(), operation$1.href(), page$5, boundingBox, cachedPages$1);
                    BoxedUnit boxedUnit = BoxedUnit.UNIT;
                    return;
                } catch (Throwable th) {
                    if (!NonFatal$.MODULE$.apply(th)) {
                        throw th;
                    }
                    $this.emitTaskWarning(new StringBuilder(28).append("Could not edit link on page ").append(operation$1.pageNumber()).toString());
                    BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                    BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
                    return;
                }
            }
        }
        $this.emitTaskWarning(new StringBuilder(28).append("Could not edit link on page ").append(operation$1.pageNumber()).toString());
        BoxedUnit boxedUnit4 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ void $anonfun$execute$55(final EditTask $this, final Map cachedPages$1, final AppendLinkOperation operation) {
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        operation.boundingBoxes().foreach(boundingBox -> {
            $anonfun$execute$56($this, operation, page, cachedPages$1, boundingBox);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$execute$56(final EditTask $this, final AppendLinkOperation operation$2, final PDPage page$6, final Map cachedPages$1, final RectangularBox boundingBox) {
        try {
            PDAnnotationLink link = new PDAnnotationLink();
            $this.updateLinkAnnotationAction$1(link, operation$2.kind(), operation$2.href(), page$6, boundingBox, cachedPages$1);
            PDBorderStyleDictionary border = new PDBorderStyleDictionary();
            border.setWidth(0.0f);
            link.setBorderStyle(border);
            java.util.List annots = page$6.getAnnotations();
            annots.add(link);
            page$6.setAnnotations(annots);
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            $this.emitTaskWarning(new StringBuilder(27).append("Could not add link on page ").append(operation$2.pageNumber()).toString());
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }

    public static final /* synthetic */ void $anonfun$execute$57(final EditTask $this, final PDDocument doc$1, final AppendEmbeddedFileOperation operation) {
        String filename = operation.file().getName();
        PDDocumentNameDictionary names = doc$1.getDocumentCatalog().getNames();
        if (names == null) {
            names = new PDDocumentNameDictionary(doc$1.getDocumentCatalog());
        }
        PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
        if (embeddedFiles == null) {
            embeddedFiles = new PDEmbeddedFilesNameTreeNode();
            names.setEmbeddedFiles(embeddedFiles);
        }
        LinkedHashMap embeddedFilesMap = new LinkedHashMap();
        if (embeddedFiles.getNames() != null) {
            embeddedFiles.getNames().forEach((name, spec) -> {
                embeddedFilesMap.put(name, spec);
            });
        }
        PDEmbeddedFile embeddedFile = new PDEmbeddedFile($this.readOnlyEmbeddedFile(operation.file()));
        embeddedFile.setCreationDate(new GregorianCalendar());
        PDComplexFileSpecification fs = (PDComplexFileSpecification) embeddedFilesMap.get(filename);
        if (fs == null) {
            fs = new PDComplexFileSpecification(null);
            fs.setFile(filename);
        }
        fs.setEmbeddedFile(embeddedFile);
        embeddedFilesMap.put(filename, fs);
        embeddedFiles.setNames(embeddedFilesMap);
    }

    public static final /* synthetic */ void $anonfun$execute$59(final EditTask $this, final PDDocument doc$1, final Map cachedPages$1, final AppendAttachmentOperation operation) {
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        operation.boundingBoxes().foreach(boundingBox -> {
            $anonfun$execute$60($this, doc$1, operation, page, boundingBox);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$execute$60(final EditTask $this, final PDDocument doc$1, final AppendAttachmentOperation operation$3, final PDPage page$7, final RectangularBox boundingBox) {
        try {
            PDComplexFileSpecification fs = doc$1.getDocumentCatalog().getNames().getEmbeddedFiles().getNames().get(operation$3.filename());
            PDAnnotationFileAttachment annotation = new PDAnnotationFileAttachment();
            annotation.setFile(fs);
            annotation.setAttachmentName(PDAnnotationFileAttachment.ATTACHMENT_NAME_PAPERCLIP);
            annotation.setCreationDate(new GregorianCalendar());
            annotation.setRectangle(AnnotationsHelper$.MODULE$.toPDRectangle(AnnotationsHelper$.MODULE$.boundingBoxAdjustedToPage(boundingBox, page$7)));
            PDBorderStyleDictionary border = new PDBorderStyleDictionary();
            border.setWidth(0.0f);
            annotation.setBorderStyle(border);
            java.util.List annots = page$7.getAnnotations();
            annots.add(annotation);
            page$7.setAnnotations(annots);
        } catch (Throwable th) {
            if (!NonFatal$.MODULE$.apply(th)) {
                throw th;
            }
            $this.emitTaskWarning(new StringBuilder(33).append("Could not add attachment on page ").append(operation$3.pageNumber()).toString());
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }

    public static final /* synthetic */ boolean $anonfun$execute$61(final EditTask $this, final AcroFormsHelper acroFormHelper$1, final Map cachedPages$1, final AppendDrawOperation op) {
        RectangularBox boundingBox = op.boundingBox();
        PDPage page = $this.getPageCached$1(op.pageNumber(), cachedPages$1);
        return acroFormHelper$1.addInkMarkup(boundingBox, page, op.inklist(), op.appearance(), op.color(), (float) op.lineSize());
    }

    public static final /* synthetic */ boolean $anonfun$execute$62(final EditTask $this, final AcroFormsHelper acroFormHelper$1, final Map cachedPages$1, final AppendLineOperation op) {
        RectangularBox boundingBox = op.boundingBox();
        PDPage page = $this.getPageCached$1(op.pageNumber(), cachedPages$1);
        return acroFormHelper$1.addLineAnnotation(boundingBox, page, op.points(), op.color(), (float) op.lineSize(), op.kind());
    }

    private static final void _assertFieldValueSet$1(final String expectedValue, final PDField field$1, final String name$1, final String value$1) {
        ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(field$1.getWidgets()).foreach(w -> {
            $anonfun$execute$63(field$1, name$1, w);
            return BoxedUnit.UNIT;
        });
        COSName actualV = field$1.getCOSObject().getCOSName(COSName.V);
        COSName expectedV = COSName.getPDFName(expectedValue);
        if (actualV != null ? !actualV.equals(expectedV) : expectedV != null) {
            throw new RuntimeException(new StringBuilder(55).append("Assert field value was set failed: ").append(field$1.getFullyQualifiedName()).append(" actual: ").append(actualV).append(" expected: ").append(expectedV).toString());
        }
        COSName expectedAS = COSName.getPDFName(expectedValue);
        Seq matchingWidgets = (Seq) ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(field$1.getWidgets()).filter(x$12 -> {
            return BoxesRunTime.boxToBoolean($anonfun$execute$65(expectedAS, x$12));
        });
        if (matchingWidgets.isEmpty()) {
            throw new RuntimeException(new StringBuilder(70).append("Assert field value was set failed: ").append(field$1.getFullyQualifiedName()).append(" no widgets matching /AS expected: ").append(value$1).toString());
        }
    }

    public static final /* synthetic */ void $anonfun$execute$63(final PDField field$1, final String name$1, final PDAnnotationWidget w) {
        PDAppearanceDictionary appearance = w.getAppearance();
        PDAppearanceEntry normalAppearance = (PDAppearanceEntry) Option$.MODULE$.apply(appearance).map(x$11 -> {
            return x$11.getNormalAppearance();
        }).orNull($less$colon$less$.MODULE$.refl());
        if (appearance == null || normalAppearance == null || !normalAppearance.isSubDictionary()) {
            boolean isVisible = w.getRectangle() != null;
            if ((field$1 instanceof PDButton) && isVisible) {
                throw new RuntimeException(new StringBuilder(50).append("Field with malformed normal appearance detected: ").append(ObjIdUtils$.MODULE$.objIdOf(w)).append(" ").append(name$1).toString());
            }
        }
    }

    public static final /* synthetic */ boolean $anonfun$execute$65(final COSName expectedAS$1, final PDAnnotationWidget x$12) {
        COSName cOSName = x$12.getCOSObject().getCOSName(COSName.AS);
        return cOSName != null ? cOSName.equals(expectedAS$1) : expectedAS$1 == null;
    }

    private final Object setFormFieldValue$1(final PDField field, final String value, final String name, final AcroFormsHelper acroFormHelper$1, final PDDocument doc$1) throws IOException, TaskException {
        List colonVar;
        if (field instanceof PDCheckBox) {
            PDCheckBox pDCheckBox = (PDCheckBox) field;
            pDCheckBox.setIgnoreExportOptions(true);
            String altValue1 = StringHelpers$.MODULE$.asWindows1252(value);
            if (value != null ? !value.equals("Off") : "Off" != 0) {
                if (!pDCheckBox.getOnValues().contains(value)) {
                    String onValue = pDCheckBox.getOnValue();
                    if (onValue != null) {
                    }
                }
                pDCheckBox.setValue(value);
                _assertFieldValueSet$1(value, field, name, value);
                return BoxedUnit.UNIT;
            }
            if (altValue1 != null ? !altValue1.equals("Off") : "Off" != 0) {
                if (!pDCheckBox.getOnValues().contains(altValue1)) {
                    String onValue2 = pDCheckBox.getOnValue();
                    if (onValue2 != null) {
                    }
                }
                pDCheckBox.setValue(altValue1);
                _assertFieldValueSet$1(altValue1, field, name, value);
                return BoxedUnit.UNIT;
            }
            pDCheckBox.unCheck();
            _assertFieldValueSet$1(COSName.Off.getName(), field, name, value);
            return BoxedUnit.UNIT;
        }
        if (field instanceof PDComboBox) {
            setValueEnsuringCanBeDisplayed$1((PDComboBox) field, value, acroFormHelper$1, doc$1);
            return BoxedUnit.UNIT;
        }
        if (field instanceof PDListBox) {
            PDListBox pDListBox = (PDListBox) field;
            if (pDListBox.isMultiSelect()) {
                colonVar = Predef$.MODULE$.wrapRefArray(value.split(MULTI_VALUE_SEPARATOR())).toList();
            } else {
                colonVar = new $colon.colon(value, Nil$.MODULE$);
            }
            List values = colonVar;
            setValuesEnsuringCanBeDisplayed$1(pDListBox, values);
            return Try$.MODULE$.apply(() -> {
                java.util.List exportValues = pDListBox.getOptionsExportValues();
                List indices = values.map(v -> {
                    return BoxesRunTime.boxToInteger(exportValues.indexOf(v));
                }).filter(x$13 -> {
                    return x$13 >= 0;
                }).map(i -> {
                    return Integer.valueOf(BoxesRunTime.unboxToInt(i));
                });
                if (indices.size() == values.size()) {
                    pDListBox.setSelectedOptionsIndex(ImplicitJavaConversions$.MODULE$.scalaSeqToJavaList(indices));
                }
            });
        }
        if (field instanceof PDRadioButton) {
            PDRadioButton pDRadioButton = (PDRadioButton) field;
            pDRadioButton.setIgnoreExportOptions(true);
            String altValue12 = StringHelpers$.MODULE$.asWindows1252(value);
            if (pDRadioButton.getOnValues().contains(value)) {
                pDRadioButton.setValue(value);
                _assertFieldValueSet$1(value, field, name, value);
                return BoxedUnit.UNIT;
            }
            if (pDRadioButton.getOnValues().contains(altValue12)) {
                pDRadioButton.setValue(altValue12);
                _assertFieldValueSet$1(altValue12, field, name, value);
                return BoxedUnit.UNIT;
            }
            pDRadioButton.setValue(COSName.Off.getName());
            _assertFieldValueSet$1(COSName.Off.getName(), field, name, value);
            return BoxedUnit.UNIT;
        }
        if (field instanceof PDTextField) {
            setValueEnsuringCanBeDisplayed$1((PDTextField) field, value, acroFormHelper$1, doc$1);
            return BoxedUnit.UNIT;
        }
        if (field == null) {
            throw new TaskException(new StringBuilder(17).append("Field not found: ").append(name).toString());
        }
        throw new TaskException(new StringBuilder(39).append("Unsupported field type: ").append(field.getClass().getSimpleName()).append(" name: ").append(name).append(" value: ").append(value).toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void setValueEnsuringCanBeDisplayed$1(final org.sejda.sambox.pdmodel.interactive.form.PDVariableText r6, final java.lang.String r7, final code.sejda.tasks.common.AcroFormsHelper r8, final org.sejda.sambox.pdmodel.PDDocument r9) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 502
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.edit.EditTask.setValueEnsuringCanBeDisplayed$1(org.sejda.sambox.pdmodel.interactive.form.PDVariableText, java.lang.String, code.sejda.tasks.common.AcroFormsHelper, org.sejda.sambox.pdmodel.PDDocument):void");
    }

    public static final /* synthetic */ void $anonfun$execute$70(final EditTask $this, final PDVariableText field$2, final PDFormFieldAdditionalActions actions) {
        if (actions.getF() != null) {
            $this.logger().debug(new StringBuilder(31).append("Removing F actions from field: ").append(field$2.getFullyQualifiedName()).toString());
            actions.setF(null);
        }
    }

    public static final /* synthetic */ String $anonfun$execute$71(final char c) {
        return StringUtils.asUnicodes(Character.toString(c));
    }

    private final void setValuesEnsuringCanBeDisplayed$1(final PDListBox field, final Seq originalValues) throws IOException {
        Option$.MODULE$.apply(field.getActions()).foreach(actions -> {
            $anonfun$execute$72(this, field, actions);
            return BoxedUnit.UNIT;
        });
        String stringForFontLookup = ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(field.getOptionsDisplayValues()).mkString("").replaceAll("\t", "    ");
        PDFont font = FontUtils.fontOrFallback(stringForFontLookup, PDType1Font.HELVETICA(), documentHandler().getUnderlyingPDDocument());
        if (font == null) {
            emitTaskWarning(new StringBuilder(29).append("Could not set field value: '").append(truncateForDisplay$1(originalValues.mkString(", "), truncateForDisplay$default$2$1())).append(OperatorName.SHOW_TEXT_LINE).toString());
            return;
        }
        field.getAcroForm().getDefaultResources().add(font).getName();
        field.setAppearanceOverrideFont(font);
        if (originalValues instanceof $colon.colon) {
            $colon.colon colonVar = ($colon.colon) originalValues;
            String originalValue = (String) colonVar.head();
            if (Nil$.MODULE$.equals(colonVar.next$access$1())) {
                field.setValue(originalValue);
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        field.setValue(ImplicitJavaConversions$.MODULE$.scalaSeqToJavaList(originalValues));
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ void $anonfun$execute$72(final EditTask $this, final PDListBox field$3, final PDFormFieldAdditionalActions actions) {
        if (actions.getF() != null) {
            $this.logger().debug(new StringBuilder(31).append("Removing F actions from field: ").append(field$3.getFullyQualifiedName()).toString());
            actions.setF(null);
        }
    }

    public static final /* synthetic */ void $anonfun$execute$73(final EditTask $this, final AcroFormsHelper acroFormHelper$1, final PDDocument doc$1, final FillFormOperation operation) {
        Seq matchingFields = acroFormHelper$1.findEditableFieldsByName(operation.name());
        if (matchingFields.isEmpty()) {
            $this.emitTaskWarning(new StringBuilder(29).append("Could not update form field: ").append(operation.name()).toString());
        }
        matchingFields.foreach(field -> {
            acroFormHelper$1.generateAppearancesIfRequired(field);
            return $this.setFormFieldValue$1(field, operation.value(), operation.name(), acroFormHelper$1, doc$1);
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: scala.MatchError */
    public static final /* synthetic */ void $anonfun$execute$75(final EditTask $this, final Set removedAnnotationIds$1, final EditParameters parameters$1, final AcroFormsHelper acroFormHelper$1, final Tuple2 x0$7) throws MatchError {
        if (x0$7 != null) {
            String objId = (String) x0$7._1();
            String value = (String) x0$7._2();
            if (removedAnnotationIds$1.contains(objId)) {
                $this.logger().warn(new StringBuilder(42).append("Sanity checks: widget ").append(objId).append(" was removed earlier").toString());
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            } else if (parameters$1.deleteFormFieldOperations().exists(x$14 -> {
                return BoxesRunTime.boxToBoolean($anonfun$execute$76(objId, x$14));
            })) {
                $this.logger().warn(new StringBuilder(70).append("Sanity checks: widget ").append(objId).append(" was found in the deleteFormFieldOperations list").toString());
                BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
                return;
            } else {
                acroFormHelper$1.getWidgetsByObjId(objId).foreach(widget -> {
                    $anonfun$execute$77($this, value, acroFormHelper$1, objId, widget);
                    return BoxedUnit.UNIT;
                });
                BoxedUnit boxedUnit3 = BoxedUnit.UNIT;
                return;
            }
        }
        throw new MatchError(x0$7);
    }

    public static final /* synthetic */ boolean $anonfun$execute$76(final String objId$1, final DeleteFormFieldOperation x$14) {
        String strObjId = x$14.objId();
        return strObjId != null ? strObjId.equals(objId$1) : objId$1 == null;
    }

    public static final /* synthetic */ void $anonfun$execute$77(final EditTask $this, final String value$2, final AcroFormsHelper acroFormHelper$1, final String objId$1, final PDAnnotationWidget widget) {
        COSBase fieldType = ObjIdUtils$.MODULE$.getInheritableAttribute(widget.getCOSObject(), COSName.FT, ObjIdUtils$.MODULE$.getInheritableAttribute$default$3());
        COSName cOSName = COSName.BTN;
        if (fieldType == null) {
            if (cOSName != null) {
                return;
            }
        } else if (!fieldType.equals(cOSName)) {
            return;
        }
        COSName actual = widget.getAppearanceState();
        COSName expected = COSName.getPDFName(value$2);
        COSName expectedAlt1 = COSName.getPDFName(StringHelpers$.MODULE$.asWindows1252(value$2));
        if (actual == null) {
            if (expected == null) {
                return;
            }
        } else if (actual.equals(expected)) {
            return;
        }
        if (actual == null) {
            if (expectedAlt1 == null) {
                return;
            }
        } else if (actual.equals(expectedAlt1)) {
            return;
        }
        BooleanRef booleanRefCreate = BooleanRef.create(true);
        if (value$2 != null ? value$2.equals("Off") : "Off" == 0) {
            acroFormHelper$1.findFieldByWidget(widget).foreach(x0$8 -> {
                $anonfun$execute$78($this, actual, booleanRefCreate, x0$8);
                return BoxedUnit.UNIT;
            });
        }
        if (booleanRefCreate.elem) {
            throw new RuntimeException(new StringBuilder(65).append("Widget has unexpected appearance state, id: ").append(objId$1).append(" actual: ").append(actual).append(", expected: ").append(expected).toString());
        }
    }

    public static final /* synthetic */ void $anonfun$execute$78(final EditTask $this, final COSName actual$1, final BooleanRef throws$1, final PDField x0$8) {
        if (x0$8 instanceof PDCheckBox) {
            PDCheckBox pDCheckBox = (PDCheckBox) x0$8;
            String value = pDCheckBox.getValue();
            String name = actual$1.getName();
            if (value != null ? value.equals(name) : name == null) {
                $this.logger().warn(new StringBuilder(101).append("Widget expected appearance state was Off, but actual is the on value from the other selected widget: ").append(pDCheckBox.getValue()).toString());
                throws$1.elem = false;
                BoxedUnit boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
    }

    public static final /* synthetic */ void $anonfun$execute$79(final EditTask $this, final AcroFormsHelper acroFormHelper$1, final Map cachedPages$1, final Set removedAnnotationIds$1, final DeleteFormFieldOperation operation) throws TaskPermissionsException {
        $this.ensureModifyPerm$1();
        PDPage page = $this.getPageCached$1(operation.pageNumber(), cachedPages$1);
        ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(page.getAnnotations()).foreach(annotation -> {
            $anonfun$execute$80($this, operation, acroFormHelper$1, page, removedAnnotationIds$1, annotation);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$execute$80(final EditTask $this, final DeleteFormFieldOperation operation$5, final AcroFormsHelper acroFormHelper$1, final PDPage page$8, final Set removedAnnotationIds$1, final PDAnnotation annotation) {
        String objId = AcroFormsHelper$.MODULE$.getObjId(annotation);
        String strObjId = operation$5.objId();
        if (objId == null) {
            if (strObjId != null) {
                return;
            }
        } else if (!objId.equals(strObjId)) {
            return;
        }
        PDAnnotationWidget widget = (PDAnnotationWidget) annotation;
        acroFormHelper$1.removeWidget(widget);
        $this.logger().info(new StringBuilder(25).append("Deleting widget ").append(objId).append(" on page ").append(operation$5.pageNumber()).toString());
        removeAnnotation$1(page$8, annotation, removedAnnotationIds$1);
    }

    private final void renameCreatedField$1(final AppendFormFieldOperation operation) {
        emitTaskWarning(new StringBuilder(85).append("A field with the name '").append(operation.name()).append("' already exists, we used a different name for the added field").toString());
        operation.name_$eq(new StringBuilder(1).append(operation.name()).append("_").append(StringHelpers$.MODULE$.randomString(4)).toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x01ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ void $anonfun$execute$81(final code.sejda.tasks.edit.EditTask r7, final scala.collection.mutable.Map r8, final code.sejda.tasks.common.AcroFormsHelper r9, final scala.collection.mutable.Map r10, final scala.collection.mutable.Map r11, final org.sejda.sambox.pdmodel.PDDocument r12, final code.sejda.tasks.edit.AppendFormFieldOperation r13) throws scala.MatchError {
        /*
            Method dump skipped, instructions count: 731
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.edit.EditTask.$anonfun$execute$81(code.sejda.tasks.edit.EditTask, scala.collection.mutable.Map, code.sejda.tasks.common.AcroFormsHelper, scala.collection.mutable.Map, scala.collection.mutable.Map, org.sejda.sambox.pdmodel.PDDocument, code.sejda.tasks.edit.AppendFormFieldOperation):void");
    }

    public static final /* synthetic */ boolean $anonfun$execute$85(final String x$15) {
        return !x$15.isBlank();
    }

    private static final String getObjIdOrTransientId$1(final PDAnnotation annot, final Map addedWidgets$1) {
        return (String) Option$.MODULE$.apply(AcroFormsHelper$.MODULE$.getObjId(annot)).filter(x$15 -> {
            return BoxesRunTime.boxToBoolean($anonfun$execute$85(x$15));
        }).orElse(() -> {
            return addedWidgets$1.get(annot);
        }).getOrElse(() -> {
            return "";
        });
    }

    public static final /* synthetic */ int $anonfun$execute$88(final scala.collection.immutable.Map positionMap$1, final Map addedWidgets$1, final PDAnnotation annotation) {
        String objId = getObjIdOrTransientId$1(annotation, addedWidgets$1);
        return BoxesRunTime.unboxToInt(positionMap$1.getOrElse(objId, () -> {
            return Integer.MAX_VALUE;
        }));
    }

    public void clipSignatures(final PDDocument doc) {
        ImplicitJavaConversions$.MODULE$.javaIterableToScalaIterable(doc.getPages()).foreach(page -> {
            $anonfun$clipSignatures$1(page);
            return BoxedUnit.UNIT;
        });
    }

    public static final /* synthetic */ void $anonfun$clipSignatures$1(final PDPage page) {
        SignatureClipper.clipSignatures(page.getAnnotations());
    }

    public Map<Tuple2<String, Object>, Option<PDFont>> cachedFonts() {
        return this.cachedFonts;
    }

    public Option<PDFont> resolveExternalFont(final PDDocumentHandler docHandler, final String family, final boolean bold) {
        Tuple2 key = new Tuple2(family, BoxesRunTime.boxToBoolean(bold));
        return (Option) cachedFonts().getOrElseUpdate(key, () -> {
            return this._resolveExternalFont(docHandler, family, bold);
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0065, code lost:
    
        if ("courier new".equals(r0) != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0076, code lost:
    
        if ("times_roman".equals(r0) != false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0087, code lost:
    
        if ("courier".equals(r0) != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c0, code lost:
    
        if (r9 == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00cf, code lost:
    
        return scala.Option$.MODULE$.apply(org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(org.sejda.model.pdf.StandardType1Font.TIMES_BOLD));
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00dc, code lost:
    
        return scala.Option$.MODULE$.apply(org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(org.sejda.model.pdf.StandardType1Font.TIMES_ROMAN));
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00de, code lost:
    
        if (r9 == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ed, code lost:
    
        return scala.Option$.MODULE$.apply(org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(org.sejda.model.pdf.StandardType1Font.CURIER_BOLD));
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fa, code lost:
    
        return scala.Option$.MODULE$.apply(org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(org.sejda.model.pdf.StandardType1Font.CURIER));
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0054, code lost:
    
        if ("times new roman".equals(r0) != false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public scala.Option<org.sejda.sambox.pdmodel.font.PDFont> _resolveExternalFont(final org.sejda.impl.sambox.component.PDDocumentHandler r7, final java.lang.String r8, final boolean r9) {
        /*
            r6 = this;
            r0 = r7
            org.sejda.sambox.pdmodel.PDDocument r0 = r0.getUnderlyingPDDocument()
            r11 = r0
            r0 = r8
            java.lang.String r0 = r0.toLowerCase()
            r12 = r0
            r0 = r12
            if (r0 != 0) goto L15
            r0 = 0
            goto L1a
        L15:
            r0 = r12
            int r0 = r0.hashCode()
        L1a:
            switch(r0) {
                case -477633757: goto L4c;
                case -353086163: goto L5d;
                case 851308484: goto L6e;
                case 957939245: goto L7f;
                case 1474706577: goto L90;
                default: goto Lbc;
            }
        L4c:
            java.lang.String r0 = "times new roman"
            r1 = r12
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L5a
            goto Lbf
        L5a:
            goto Lfb
        L5d:
            java.lang.String r0 = "courier new"
            r1 = r12
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L6b
            goto Ldd
        L6b:
            goto Lfb
        L6e:
            java.lang.String r0 = "times_roman"
            r1 = r12
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L7c
            goto Lbf
        L7c:
            goto Lfb
        L7f:
            java.lang.String r0 = "courier"
            r1 = r12
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L8d
            goto Ldd
        L8d:
            goto Lfb
        L90:
            java.lang.String r0 = "helvetica"
            r1 = r12
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto Lb9
            r0 = r9
            if (r0 == 0) goto Lac
            scala.Option$ r0 = scala.Option$.MODULE$
            org.sejda.model.pdf.StandardType1Font r1 = org.sejda.model.pdf.StandardType1Font.HELVETICA_BOLD
            org.sejda.sambox.pdmodel.font.PDType1Font r1 = org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(r1)
            scala.Option r0 = r0.apply(r1)
            return r0
        Lac:
            scala.Option$ r0 = scala.Option$.MODULE$
            org.sejda.model.pdf.StandardType1Font r1 = org.sejda.model.pdf.StandardType1Font.HELVETICA
            org.sejda.sambox.pdmodel.font.PDType1Font r1 = org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(r1)
            scala.Option r0 = r0.apply(r1)
            return r0
        Lb9:
            goto Lfb
        Lbc:
            goto Lfb
        Lbf:
            r0 = r9
            if (r0 == 0) goto Ld0
            scala.Option$ r0 = scala.Option$.MODULE$
            org.sejda.model.pdf.StandardType1Font r1 = org.sejda.model.pdf.StandardType1Font.TIMES_BOLD
            org.sejda.sambox.pdmodel.font.PDType1Font r1 = org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(r1)
            scala.Option r0 = r0.apply(r1)
            return r0
        Ld0:
            scala.Option$ r0 = scala.Option$.MODULE$
            org.sejda.model.pdf.StandardType1Font r1 = org.sejda.model.pdf.StandardType1Font.TIMES_ROMAN
            org.sejda.sambox.pdmodel.font.PDType1Font r1 = org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(r1)
            scala.Option r0 = r0.apply(r1)
            return r0
        Ldd:
            r0 = r9
            if (r0 == 0) goto Lee
            scala.Option$ r0 = scala.Option$.MODULE$
            org.sejda.model.pdf.StandardType1Font r1 = org.sejda.model.pdf.StandardType1Font.CURIER_BOLD
            org.sejda.sambox.pdmodel.font.PDType1Font r1 = org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(r1)
            scala.Option r0 = r0.apply(r1)
            return r0
        Lee:
            scala.Option$ r0 = scala.Option$.MODULE$
            org.sejda.model.pdf.StandardType1Font r1 = org.sejda.model.pdf.StandardType1Font.CURIER
            org.sejda.sambox.pdmodel.font.PDType1Font r1 = org.sejda.impl.sambox.util.FontUtils.getStandardType1Font(r1)
            scala.Option r0 = r0.apply(r1)
            return r0
        Lfb:
            code.sejda.tasks.common.GoogleFonts$ r0 = code.sejda.tasks.common.GoogleFonts$.MODULE$
            r1 = r12
            scala.Option r0 = r0.find(r1)
            r1 = r11
            r2 = r9
            scala.Option<org.sejda.sambox.pdmodel.font.PDFont> r1 = (v2) -> { // scala.Function1.apply(java.lang.Object):java.lang.Object
                return $anonfun$_resolveExternalFont$1(r1, r2, v2);
            }
            scala.Option r0 = r0.flatMap(r1)
            r1 = r6
            r2 = r8
            r3 = r9
            r4 = r11
            scala.Option<org.sejda.sambox.pdmodel.font.PDFont> r1 = () -> { // scala.Function0.apply():java.lang.Object
                return $anonfun$_resolveExternalFont$2(r1, r2, r3, r4);
            }
            scala.Option r0 = r0.orElse(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.edit.EditTask._resolveExternalFont(org.sejda.impl.sambox.component.PDDocumentHandler, java.lang.String, boolean):scala.Option");
    }

    public PDType1Font fallbackFontWithWarning(final String family) {
        if (family != null ? !family.equals("system") : "system" != 0) {
            String failureReason = (String) SystemFonts$.MODULE$.getFailureReason(family).getOrElse(() -> {
                return "Could not resolve font";
            });
            emitTaskWarning(new StringBuilder(20).append(failureReason).append(" '").append(family).append("', using Helvetica").toString());
        }
        return FontUtils.getStandardType1Font(StandardType1Font.HELVETICA);
    }

    public scala.collection.immutable.Map<FileType, String> imageFormats() {
        return this.imageFormats;
    }

    public Source<?> rotate(final Source<?> source, final int degrees) throws TaskIOException, IOException {
        Scalr.Rotation rotation;
        switch (degrees) {
            case 0:
                return source;
            default:
                SeekableSource seekableSource = source.getSeekableSource();
                EncryptionAtRestPolicy encryptionAtRestPolicy = source.getEncryptionAtRestPolicy();
                BufferedImage image = ImageIO.read(seekableSource.asNewInputStream());
                if (image == null) {
                    throw new UnsupportedImageFormatException(FileType.UNKNOWN, source.getName(), null);
                }
                switch (degrees) {
                    case 90:
                        rotation = Scalr.Rotation.CW_90;
                        break;
                    case 180:
                        rotation = Scalr.Rotation.CW_180;
                        break;
                    case 270:
                        rotation = Scalr.Rotation.CW_270;
                        break;
                    default:
                        throw new RuntimeException(new StringBuilder(28).append("Unsupported image rotation: ").append(degrees).toString());
                }
                Scalr.Rotation rotation2 = rotation;
                BufferedImage result = Scalr.rotate(image, rotation2, new BufferedImageOp[0]);
                FileType fileType = FileTypeDetector.detectFileType(seekableSource);
                String format = (String) imageFormats().apply(fileType);
                File tmpFile = IOUtils.createTemporaryBufferWithName(source.getName());
                OutputStream tmpOut = encryptionAtRestPolicy.encrypt(new FileOutputStream(tmpFile));
                try {
                    ImageIO.write(result, format, tmpOut);
                    org.apache.commons.io.IOUtils.closeQuietly(tmpOut);
                    FileSource resultSource = FileSource.newInstance(tmpFile);
                    resultSource.setEncryptionAtRestPolicy(encryptionAtRestPolicy);
                    return resultSource;
                } catch (Throwable th) {
                    org.apache.commons.io.IOUtils.closeQuietly(tmpOut);
                    throw th;
                }
        }
    }

    @Override // org.sejda.model.task.Task
    public void after() {
        org.sejda.commons.util.IOUtils.closeQuietly(documentHandler());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PDColor toPDColor(final Color color) {
        return PageTextWriter.toPDColor(color);
    }

    private FormFieldType kindOf(final PDField f) {
        if (f instanceof PDTextField) {
            return FormFieldType$Text$.MODULE$;
        }
        if (f instanceof PDRadioButton) {
            return FormFieldType$Radio$.MODULE$;
        }
        if (f instanceof PDCheckBox) {
            return FormFieldType$Checkbox$.MODULE$;
        }
        if (f instanceof PDChoice) {
            return FormFieldType$Dropdown$.MODULE$;
        }
        if (f instanceof PDSignatureField) {
            return FormFieldType$SignatureBox$.MODULE$;
        }
        throw new RuntimeException(new StringBuilder(24).append("Unsupported field type: ").append(f).toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean isUnsupportedImageEx(final java.lang.Throwable r4) {
        /*
            r3 = this;
            r0 = r4
            r8 = r0
            r0 = r8
            boolean r0 = r0 instanceof org.sejda.model.exception.TaskIOException
            if (r0 == 0) goto L31
            r0 = r8
            org.sejda.model.exception.TaskIOException r0 = (org.sejda.model.exception.TaskIOException) r0
            r9 = r0
            r0 = r4
            java.lang.String r0 = r0.getMessage()
            if (r0 == 0) goto L2e
            r0 = r4
            java.lang.String r0 = r0.getMessage()
            java.lang.String r1 = "An error occurred creating PDImageXObject"
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L2e
            r0 = r9
            java.lang.Throwable r0 = r0.getCause()
            goto L38
        L2e:
            goto L34
        L31:
            goto L34
        L34:
            r0 = r4
            goto L38
        L38:
            r7 = r0
            r0 = r7
            r10 = r0
            r0 = r10
            boolean r0 = r0 instanceof org.sejda.sambox.pdmodel.graphics.image.UnsupportedImageFormatException
            if (r0 == 0) goto L48
            r0 = 1
            return r0
        L48:
            goto L4b
        L4b:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.edit.EditTask.isUnsupportedImageEx(java.lang.Throwable):boolean");
    }

    private boolean removeAttachmentFromNamedTreeNode(final PDNameTreeNode<PDComplexFileSpecification> node, final String filename) throws IOException {
        BooleanRef removed = BooleanRef.create(false);
        if (node.getNames() != null) {
            java.util.Map names = node.getNames();
            if (names.containsKey(filename)) {
                LinkedHashMap newNames = new LinkedHashMap(names);
                newNames.remove(filename);
                node.setNames(newNames);
                removed.elem = true;
            }
        } else if (node.getKids() != null) {
            ImplicitJavaConversions$.MODULE$.javaListToScalaSeq(node.getKids()).foreach(kid -> {
                $anonfun$removeAttachmentFromNamedTreeNode$1(this, removed, filename, kid);
                return BoxedUnit.UNIT;
            });
        }
        return removed.elem;
    }

    public static final /* synthetic */ void $anonfun$removeAttachmentFromNamedTreeNode$1(final EditTask $this, final BooleanRef removed$1, final String filename$2, final PDNameTreeNode kid) {
        removed$1.elem = $this.removeAttachmentFromNamedTreeNode(kid, filename$2) || removed$1.elem;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream readOnlyEmbeddedFile(final org.sejda.model.input.Source<?> r8) {
        /*
            r7 = this;
            org.sejda.sambox.cos.COSDictionary r0 = new org.sejda.sambox.cos.COSDictionary
            r1 = r0
            r1.<init>()
            r10 = r0
            r0 = r10
            org.sejda.sambox.cos.COSName r1 = org.sejda.sambox.cos.COSName.FILTER
            org.sejda.sambox.cos.COSName r2 = org.sejda.sambox.cos.COSName.FLATE_DECODE
            r0.setItem(r1, r2)
            code.sejda.tasks.edit.EditTask$$anon$1 r0 = new code.sejda.tasks.edit.EditTask$$anon$1
            r1 = r0
            r2 = 0
            r3 = r8
            r1.<init>(r2, r3)
            r11 = r0
            org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream r0 = new org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream
            r1 = r0
            r2 = r10
            r3 = r11
            r4 = -1
            r1.<init>(r2, r3, r4)
            r12 = r0
            r0 = r8
            org.sejda.model.encryption.EncryptionAtRestPolicy r0 = r0.getEncryptionAtRestPolicy()
            org.sejda.model.encryption.NoEncryptionAtRest r1 = org.sejda.model.encryption.NoEncryptionAtRest.INSTANCE
            r13 = r1
            r1 = r0
            if (r1 != 0) goto L44
        L3c:
            r0 = r13
            if (r0 == 0) goto L55
            goto L4c
        L44:
            r1 = r13
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L55
        L4c:
            r0 = r8
            org.sejda.model.encryption.EncryptionAtRestPolicy r0 = r0.getEncryptionAtRestPolicy()
            if (r0 != 0) goto Lb3
        L55:
            r0 = r8
            java.lang.Object r0 = r0.getSource()
            r14 = r0
            r0 = r14
            boolean r0 = r0 instanceof java.io.File
            if (r0 == 0) goto La9
            r0 = r14
            java.io.File r0 = (java.io.File) r0
            r15 = r0
            java.util.GregorianCalendar r0 = new java.util.GregorianCalendar
            r1 = r0
            r1.<init>()
            r16 = r0
            r0 = r16
            r1 = r15
            long r1 = r1.lastModified()
            r0.setTimeInMillis(r1)
            r0 = r12
            org.sejda.sambox.cos.COSName r1 = org.sejda.sambox.cos.COSName.PARAMS
            java.lang.String r1 = r1.getName()
            org.sejda.sambox.cos.COSName r2 = org.sejda.sambox.cos.COSName.MOD_DATE
            r3 = r16
            r0.setEmbeddedDate(r1, r2, r3)
            r0 = r12
            org.sejda.sambox.cos.COSName r1 = org.sejda.sambox.cos.COSName.PARAMS
            java.lang.String r1 = r1.getName()
            org.sejda.sambox.cos.COSName r2 = org.sejda.sambox.cos.COSName.SIZE
            r3 = r15
            long r3 = r3.length()
            r0.setEmbeddedInt(r1, r2, r3)
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto Lb3
        La9:
            goto Lac
        Lac:
            scala.runtime.BoxedUnit r0 = scala.runtime.BoxedUnit.UNIT
            goto Lb3
        Lb3:
            r0 = r12
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: code.sejda.tasks.edit.EditTask.readOnlyEmbeddedFile(org.sejda.model.input.Source):org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream");
    }
}

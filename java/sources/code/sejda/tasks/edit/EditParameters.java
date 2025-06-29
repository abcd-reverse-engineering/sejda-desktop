package code.sejda.tasks.edit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters;
import scala.collection.immutable.List;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.Map;
import scala.reflect.ScalaSignature;

/* compiled from: EditParameters.scala */
@ScalaSignature(bytes = "\u0006\u0005\u0011=e\u0001\u0002;v\u0001yD!\"!\u0007\u0001\u0005\u000b\u0007I\u0011AA\u000e\u0011)\tI\u0004\u0001B\u0001B\u0003%\u0011Q\u0004\u0005\u000b\u0003w\u0001!Q1A\u0005\u0002\u0005u\u0002BCA$\u0001\t\u0005\t\u0015!\u0003\u0002@!Q\u0011\u0011\n\u0001\u0003\u0006\u0004%\t!a\u0013\t\u0015\u0005U\u0003A!A!\u0002\u0013\ti\u0005\u0003\u0006\u0002X\u0001\u0011)\u0019!C\u0001\u00033B!\"a\u0019\u0001\u0005\u0003\u0005\u000b\u0011BA.\u0011)\t)\u0007\u0001BC\u0002\u0013\u0005\u0011q\r\u0005\u000b\u0003c\u0002!\u0011!Q\u0001\n\u0005%\u0004BCA:\u0001\t\u0015\r\u0011\"\u0001\u0002v!Q\u0011q\u0010\u0001\u0003\u0002\u0003\u0006I!a\u001e\t\u0015\u0005\u0005\u0005A!b\u0001\n\u0003\t\u0019\t\u0003\u0006\u0002\u000e\u0002\u0011\t\u0011)A\u0005\u0003\u000bC!\"a$\u0001\u0005\u000b\u0007I\u0011AAI\u0011)\tY\n\u0001B\u0001B\u0003%\u00111\u0013\u0005\u000b\u0003;\u0003!Q1A\u0005\u0002\u0005}\u0005BCAU\u0001\t\u0005\t\u0015!\u0003\u0002\"\"Q\u00111\u0016\u0001\u0003\u0006\u0004%\t!!,\t\u0015\u0005]\u0006A!A!\u0002\u0013\ty\u000b\u0003\u0006\u0002:\u0002\u0011)\u0019!C\u0001\u0003wC!\"!2\u0001\u0005\u0003\u0005\u000b\u0011BA_\u0011)\t9\r\u0001BC\u0002\u0013\u0005\u0011\u0011\u001a\u0005\u000b\u0003'\u0004!\u0011!Q\u0001\n\u0005-\u0007BCAk\u0001\t\u0015\r\u0011\"\u0001\u0002X\"Q\u0011\u0011\u001d\u0001\u0003\u0002\u0003\u0006I!!7\t\u0015\u0005\r\bA!b\u0001\n\u0003\t)\u000f\u0003\u0006\u0002p\u0002\u0011\t\u0011)A\u0005\u0003OD!\"!=\u0001\u0005\u000b\u0007I\u0011AAz\u0011)\ti\u0010\u0001B\u0001B\u0003%\u0011Q\u001f\u0005\u000b\u0003\u007f\u0004!Q1A\u0005\u0002\t\u0005\u0001B\u0003B\u0006\u0001\t\u0005\t\u0015!\u0003\u0003\u0004!Q!Q\u0002\u0001\u0003\u0006\u0004%\tAa\u0004\t\u0015\te\u0001A!A!\u0002\u0013\u0011\t\u0002\u0003\u0006\u0003\u001c\u0001\u0011)\u0019!C\u0001\u0005;A!Ba\n\u0001\u0005\u0003\u0005\u000b\u0011\u0002B\u0010\u0011)\u0011I\u0003\u0001BC\u0002\u0013\u0005!1\u0006\u0005\u000b\u0005k\u0001!\u0011!Q\u0001\n\t5\u0002B\u0003B\u001c\u0001\t\u0015\r\u0011\"\u0001\u0003:!Q!1\t\u0001\u0003\u0002\u0003\u0006IAa\u000f\t\u0015\t\u0015\u0003A!b\u0001\n\u0003\u00119\u0005\u0003\u0006\u0003R\u0001\u0011\t\u0011)A\u0005\u0005\u0013B!Ba\u0015\u0001\u0005\u000b\u0007I\u0011\u0001B+\u0011)\u0011y\u0006\u0001B\u0001B\u0003%!q\u000b\u0005\u000b\u0005C\u0002!Q1A\u0005\u0002\t\r\u0004B\u0003B7\u0001\t\u0005\t\u0015!\u0003\u0003f!Q!q\u000e\u0001\u0003\u0006\u0004%\tA!\u001d\t\u0015\t=\u0005A!A!\u0002\u0013\u0011\u0019\b\u0003\u0006\u0003\u0012\u0002\u0011)\u0019!C\u0001\u0005'C!Ba&\u0001\u0005\u0003\u0005\u000b\u0011\u0002BK\u0011)\u0011I\n\u0001BC\u0002\u0013\u0005!1\u0013\u0005\u000b\u00057\u0003!\u0011!Q\u0001\n\tU\u0005B\u0003BO\u0001\t\u0015\r\u0011\"\u0001\u0003 \"Q!\u0011\u0016\u0001\u0003\u0002\u0003\u0006IA!)\t\u000f\t-\u0006\u0001\"\u0001\u0003.\"9!q\u001d\u0001\u0005\u0002\t%\bb\u0002B|\u0001\u0011\u0005!\u0011 \u0005\b\u0005{\u0004A\u0011\u0001B��\u0011\u001d\u0019\u0019\u0001\u0001C\u0001\u0007\u000bAqa!\u0003\u0001\t\u0003\u0019Y\u0001C\u0004\u0004\u0010\u0001!\ta!\u0005\t\u000f\rU\u0001\u0001\"\u0001\u0004\u0018!911\u0004\u0001\u0005\u0002\ru\u0001bBB\u0011\u0001\u0011\u000511\u0005\u0005\b\u0007O\u0001A\u0011AB\u0015\u0011\u001d\u0019i\u0003\u0001C\u0001\u0007_Aqaa\r\u0001\t\u0003\u0019)\u0004C\u0004\u0004:\u0001!\taa\u000f\t\u000f\r}\u0002\u0001\"\u0001\u0004B!91Q\t\u0001\u0005\u0002\r\u001d\u0003bBB&\u0001\u0011\u00051Q\n\u0005\b\u0007#\u0002A\u0011AB*\u0011\u001d\u00199\u0006\u0001C\u0001\u00073Bqa!\u0018\u0001\t\u0003\u0019y\u0006C\u0004\u0004d\u0001!\ta!\u001a\t\u000f\r-\u0004\u0001\"\u0001\u0004n!911\u000f\u0001\u0005\u0002\rU\u0004bBB=\u0001\u0011\u000511\u0010\u0005\b\u0007\u007f\u0002A\u0011ABA\u0011\u001d\u0019I\t\u0001C\u0001\u0007\u0017Cqaa$\u0001\t\u0003\u0019\t\nC\u0004\u0004(\u0002!\ta!+\t\u000f\r5\u0006\u0001\"\u0001\u00040\"911\u0017\u0001\u0005B\rU\u0006bBBd\u0001\u0011\u00053\u0011Z\u0004\n\u0007#,\u0018\u0011!E\u0001\u0007'4\u0001\u0002^;\u0002\u0002#\u00051Q\u001b\u0005\b\u0005W;F\u0011ABo\u0011%\u0019ynVI\u0001\n\u0003\u0019\t\u000fC\u0005\u0004x^\u000b\n\u0011\"\u0001\u0004z\"I1Q`,\u0012\u0002\u0013\u00051q \u0005\n\t\u00079\u0016\u0013!C\u0001\t\u000bA\u0011\u0002\"\u0003X#\u0003%\t\u0001b\u0003\t\u0013\u0011=q+%A\u0005\u0002\u0011E\u0001\"\u0003C\u000b/F\u0005I\u0011\u0001C\f\u0011%!YbVI\u0001\n\u0003!i\u0002C\u0005\u0005\"]\u000b\n\u0011\"\u0001\u0005$!IAqE,\u0012\u0002\u0013\u0005A\u0011\u0006\u0005\n\t[9\u0016\u0013!C\u0001\t_A\u0011\u0002b\rX#\u0003%\t\u0001\"\u000e\t\u0013\u0011er+%A\u0005\u0002\u0011m\u0002\"\u0003C /F\u0005I\u0011\u0001C!\u0011%!)eVI\u0001\n\u0003!9\u0005C\u0005\u0005L]\u000b\n\u0011\"\u0001\u0005N!IA\u0011K,\u0012\u0002\u0013\u0005A1\u000b\u0005\n\t/:\u0016\u0013!C\u0001\t3B\u0011\u0002\"\u0018X#\u0003%\t\u0001b\u0018\t\u0013\u0011\rt+%A\u0005\u0002\u0011\u0015\u0004\"\u0003C5/F\u0005I\u0011\u0001C6\u0011%!ygVI\u0001\n\u0003!\t\bC\u0005\u0005v]\u000b\n\u0011\"\u0001\u0005x!IA1P,\u0012\u0002\u0013\u0005AQ\u0010\u0005\n\t\u0003;\u0016\u0013!C\u0001\t\u0007C\u0011\u0002b\"X#\u0003%\t\u0001b!\t\u0013\u0011%u+%A\u0005\u0002\u0011-%AD#eSR\u0004\u0016M]1nKR,'o\u001d\u0006\u0003m^\fA!\u001a3ji*\u0011\u00010_\u0001\u0006i\u0006\u001c8n\u001d\u0006\u0003un\fQa]3kI\u0006T\u0011\u0001`\u0001\u0005G>$Wm\u0001\u0001\u0014\u0005\u0001y\b\u0003BA\u0001\u0003+i!!a\u0001\u000b\t\u0005\u0015\u0011qA\u0001\u0005E\u0006\u001cXM\u0003\u0003\u0002\n\u0005-\u0011!\u00039be\u0006lW\r^3s\u0015\u0011\ti!a\u0004\u0002\u000b5|G-\u001a7\u000b\u0007i\f\tB\u0003\u0002\u0002\u0014\u0005\u0019qN]4\n\t\u0005]\u00111\u0001\u0002*\u001bVdG/\u001b9mKB#gmU8ve\u000e,W*\u001e7uSBdWmT;uaV$\b+\u0019:b[\u0016$XM]:\u0002)\u0005\u0004\b/\u001a8e)\u0016DHo\u00149fe\u0006$\u0018n\u001c8t+\t\ti\u0002\u0005\u0004\u0002 \u00055\u0012\u0011G\u0007\u0003\u0003CQA!a\t\u0002&\u00059Q.\u001e;bE2,'\u0002BA\u0014\u0003S\t!bY8mY\u0016\u001cG/[8o\u0015\t\tY#A\u0003tG\u0006d\u0017-\u0003\u0003\u00020\u0005\u0005\"A\u0003'jgR\u0014UO\u001a4feB!\u00111GA\u001b\u001b\u0005)\u0018bAA\u001ck\n\u0019\u0012\t\u001d9f]\u0012$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]\u0006)\u0012\r\u001d9f]\u0012$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]N\u0004\u0013AE3eSR$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]N,\"!a\u0010\u0011\r\u0005}\u0011QFA!!\u0011\t\u0019$a\u0011\n\u0007\u0005\u0015SOA\tFI&$H+\u001a=u\u001fB,'/\u0019;j_:\f1#\u001a3jiR+\u0007\u0010^(qKJ\fG/[8og\u0002\nq\"[7bO\u0016|\u0005/\u001a:bi&|gn]\u000b\u0003\u0003\u001b\u0002b!a\b\u0002.\u0005=\u0003\u0003BA\u001a\u0003#J1!a\u0015v\u0005E\tE\rZ%nC\u001e,w\n]3sCRLwN\\\u0001\u0011S6\fw-Z(qKJ\fG/[8og\u0002\nqb\u001d5ba\u0016|\u0005/\u001a:bi&|gn]\u000b\u0003\u00037\u0002b!a\b\u0002.\u0005u\u0003\u0003BA\u001a\u0003?J1!!\u0019v\u0005E\tE\rZ*iCB,w\n]3sCRLwN\\\u0001\u0011g\"\f\u0007/Z(qKJ\fG/[8og\u0002\nA#\u001b8tKJ$\b+Y4f\u001fB,'/\u0019;j_:\u001cXCAA5!\u0019\ty\"!\f\u0002lA!\u00111GA7\u0013\r\ty'\u001e\u0002\u0014\u0013:\u001cXM\u001d;QC\u001e,w\n]3sCRLwN\\\u0001\u0016S:\u001cXM\u001d;QC\u001e,w\n]3sCRLwN\\:!\u0003Q!W\r\\3uKB\u000bw-Z(qKJ\fG/[8ogV\u0011\u0011q\u000f\t\u0007\u0003?\ti#!\u001f\u0011\t\u0005M\u00121P\u0005\u0004\u0003{*(a\u0005#fY\u0016$X\rU1hK>\u0003XM]1uS>t\u0017!\u00063fY\u0016$X\rU1hK>\u0003XM]1uS>t7\u000fI\u0001\u0018Q&<\u0007\u000e\\5hQR$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]N,\"!!\"\u0011\r\u0005}\u0011QFAD!\u0011\t\u0019$!#\n\u0007\u0005-UO\u0001\fIS\u001eDG.[4iiR+\u0007\u0010^(qKJ\fG/[8o\u0003aA\u0017n\u001a5mS\u001eDG\u000fV3yi>\u0003XM]1uS>t7\u000fI\u0001\u0015CB\u0004XM\u001c3MS:\\w\n]3sCRLwN\\:\u0016\u0005\u0005M\u0005CBA\u0010\u0003[\t)\n\u0005\u0003\u00024\u0005]\u0015bAAMk\n\u0019\u0012\t\u001d9f]\u0012d\u0015N\\6Pa\u0016\u0014\u0018\r^5p]\u0006)\u0012\r\u001d9f]\u0012d\u0015N\\6Pa\u0016\u0014\u0018\r^5p]N\u0004\u0013AE3eSRd\u0015N\\6Pa\u0016\u0014\u0018\r^5p]N,\"!!)\u0011\r\u0005}\u0011QFAR!\u0011\t\u0019$!*\n\u0007\u0005\u001dVOA\tFI&$H*\u001b8l\u001fB,'/\u0019;j_:\f1#\u001a3ji2Kgn[(qKJ\fG/[8og\u0002\nA\u0003Z3mKR,G*\u001b8l\u001fB,'/\u0019;j_:\u001cXCAAX!\u0019\ty\"!\f\u00022B!\u00111GAZ\u0013\r\t),\u001e\u0002\u0014\t\u0016dW\r^3MS:\\w\n]3sCRLwN\\\u0001\u0016I\u0016dW\r^3MS:\\w\n]3sCRLwN\\:!\u0003i\t\u0007\u000f]3oI\u0006#H/Y2i[\u0016tGo\u00149fe\u0006$\u0018n\u001c8t+\t\ti\f\u0005\u0004\u0002 \u00055\u0012q\u0018\t\u0005\u0003g\t\t-C\u0002\u0002DV\u0014\u0011$\u00119qK:$\u0017\t\u001e;bG\"lWM\u001c;Pa\u0016\u0014\u0018\r^5p]\u0006Y\u0012\r\u001d9f]\u0012\fE\u000f^1dQ6,g\u000e^(qKJ\fG/[8og\u0002\n!\u0004Z3mKR,\u0017\t\u001e;bG\"lWM\u001c;Pa\u0016\u0014\u0018\r^5p]N,\"!a3\u0011\r\u0005}\u0011QFAg!\u0011\t\u0019$a4\n\u0007\u0005EWOA\rEK2,G/Z!ui\u0006\u001c\u0007.\\3oi>\u0003XM]1uS>t\u0017a\u00073fY\u0016$X-\u0011;uC\u000eDW.\u001a8u\u001fB,'/\u0019;j_:\u001c\b%\u0001\u000fbaB,g\u000eZ#nE\u0016$G-\u001a3GS2,w\n]3sCRLwN\\:\u0016\u0005\u0005e\u0007CBA\u0010\u0003[\tY\u000e\u0005\u0003\u00024\u0005u\u0017bAApk\nY\u0012\t\u001d9f]\u0012,UNY3eI\u0016$g)\u001b7f\u001fB,'/\u0019;j_:\fQ$\u00199qK:$W)\u001c2fI\u0012,GMR5mK>\u0003XM]1uS>t7\u000fI\u0001\u001dI\u0016dW\r^3F[\n,G\rZ3e\r&dWm\u00149fe\u0006$\u0018n\u001c8t+\t\t9\u000f\u0005\u0004\u0002 \u00055\u0012\u0011\u001e\t\u0005\u0003g\tY/C\u0002\u0002nV\u00141\u0004R3mKR,W)\u001c2fI\u0012,GMR5mK>\u0003XM]1uS>t\u0017!\b3fY\u0016$X-R7cK\u0012$W\r\u001a$jY\u0016|\u0005/\u001a:bi&|gn\u001d\u0011\u0002%\u0019LG\u000e\u001c$pe6|\u0005/\u001a:bi&|gn]\u000b\u0003\u0003k\u0004b!a\b\u0002.\u0005]\b\u0003BA\u001a\u0003sL1!a?v\u0005E1\u0015\u000e\u001c7G_Jlw\n]3sCRLwN\\\u0001\u0014M&dGNR8s[>\u0003XM]1uS>t7\u000fI\u0001\u001aCB\u0004XM\u001c3G_Jlg)[3mI>\u0003XM]1uS>t7/\u0006\u0002\u0003\u0004A1\u0011qDA\u0017\u0005\u000b\u0001B!a\r\u0003\b%\u0019!\u0011B;\u00031\u0005\u0003\b/\u001a8e\r>\u0014XNR5fY\u0012|\u0005/\u001a:bi&|g.\u0001\u000ebaB,g\u000e\u001a$pe64\u0015.\u001a7e\u001fB,'/\u0019;j_:\u001c\b%A\reK2,G/\u001a$pe64\u0015.\u001a7e\u001fB,'/\u0019;j_:\u001cXC\u0001B\t!\u0019\ty\"!\f\u0003\u0014A!\u00111\u0007B\u000b\u0013\r\u00119\"\u001e\u0002\u0019\t\u0016dW\r^3G_Jlg)[3mI>\u0003XM]1uS>t\u0017A\u00073fY\u0016$XMR8s[\u001aKW\r\u001c3Pa\u0016\u0014\u0018\r^5p]N\u0004\u0013aF3eSRD\u0015n\u001a5mS\u001eDGo\u00149fe\u0006$\u0018n\u001c8t+\t\u0011y\u0002\u0005\u0004\u0002 \u00055\"\u0011\u0005\t\u0005\u0003g\u0011\u0019#C\u0002\u0003&U\u0014a#\u00123ji\"Kw\r\u001b7jO\"$x\n]3sCRLwN\\\u0001\u0019K\u0012LG\u000fS5hQ2Lw\r\u001b;Pa\u0016\u0014\u0018\r^5p]N\u0004\u0013!\u00073fY\u0016$X\rS5hQ2Lw\r\u001b;Pa\u0016\u0014\u0018\r^5p]N,\"A!\f\u0011\r\u0005}\u0011Q\u0006B\u0018!\u0011\t\u0019D!\r\n\u0007\tMRO\u0001\rEK2,G/\u001a%jO\"d\u0017n\u001a5u\u001fB,'/\u0019;j_:\f!\u0004Z3mKR,\u0007*[4iY&<\u0007\u000e^(qKJ\fG/[8og\u0002\n!\"\u00199qK:$GI]1x+\t\u0011Y\u0004\u0005\u0004\u0002 \u00055\"Q\b\t\u0005\u0003g\u0011y$C\u0002\u0003BU\u00141#\u00119qK:$GI]1x\u001fB,'/\u0019;j_:\f1\"\u00199qK:$GI]1xA\u0005Q\u0011\r\u001d9f]\u0012d\u0015N\\3\u0016\u0005\t%\u0003CBA\u0010\u0003[\u0011Y\u0005\u0005\u0003\u00024\t5\u0013b\u0001B(k\n\u0019\u0012\t\u001d9f]\u0012d\u0015N\\3Pa\u0016\u0014\u0018\r^5p]\u0006Y\u0011\r\u001d9f]\u0012d\u0015N\\3!\u0003-\u0019\u0018p\u001d;f[\u001a{g\u000e^:\u0016\u0005\t]\u0003CBA\u0010\u0003[\u0011I\u0006\u0005\u0003\u00024\tm\u0013b\u0001B/k\nQ1+_:uK64uN\u001c;\u0002\u0019ML8\u000f^3n\r>tGo\u001d\u0011\u0002+\u0011,G.\u001a;f\u00136\fw-Z(qKJ\fG/[8ogV\u0011!Q\r\t\u0007\u0003?\tiCa\u001a\u0011\t\u0005M\"\u0011N\u0005\u0004\u0005W*(\u0001\u0006#fY\u0016$X-S7bO\u0016|\u0005/\u001a:bi&|g.\u0001\feK2,G/Z%nC\u001e,w\n]3sCRLwN\\:!\u0003-1wN]7XS\u0012<W\r^:\u0016\u0005\tM\u0004\u0003CA\u0010\u0005k\u0012IH!\u001f\n\t\t]\u0014\u0011\u0005\u0002\u0004\u001b\u0006\u0004\b\u0003\u0002B>\u0005\u0013sAA! \u0003\u0006B!!qPA\u0015\u001b\t\u0011\tIC\u0002\u0003\u0004v\fa\u0001\u0010:p_Rt\u0014\u0002\u0002BD\u0003S\ta\u0001\u0015:fI\u00164\u0017\u0002\u0002BF\u0005\u001b\u0013aa\u0015;sS:<'\u0002\u0002BD\u0003S\tABZ8s[^KGmZ3ug\u0002\n!BZ8s[\u0016\u0013(o\u001c:t+\t\u0011)\n\u0005\u0004\u0002 \u00055\"\u0011P\u0001\fM>\u0014X.\u0012:s_J\u001c\b%\u0001\u0005uC\n|%\u000fZ3s\u0003%!\u0018MY(sI\u0016\u0014\b%\u0001\u000bs_R\fG/\u001a)bO\u0016|\u0005/\u001a:bi&|gn]\u000b\u0003\u0005C\u0003b!a\b\u0002.\t\r\u0006\u0003BA\u001a\u0005KK1Aa*v\u0005M\u0011v\u000e^1uKB\u000bw-Z(qKJ\fG/[8o\u0003U\u0011x\u000e^1uKB\u000bw-Z(qKJ\fG/[8og\u0002\na\u0001P5oSRtD\u0003\u000fBX\u0005c\u0013\u0019L!.\u00038\ne&1\u0018B_\u0005\u007f\u0013\tMa1\u0003F\n\u001d'\u0011\u001aBf\u0005\u001b\u0014yM!5\u0003T\nU'q\u001bBm\u00057\u0014iNa8\u0003b\n\r(Q\u001d\t\u0004\u0003g\u0001\u0001\"CA\roA\u0005\t\u0019AA\u000f\u0011%\tYd\u000eI\u0001\u0002\u0004\ty\u0004C\u0005\u0002J]\u0002\n\u00111\u0001\u0002N!I\u0011qK\u001c\u0011\u0002\u0003\u0007\u00111\f\u0005\n\u0003K:\u0004\u0013!a\u0001\u0003SB\u0011\"a\u001d8!\u0003\u0005\r!a\u001e\t\u0013\u0005\u0005u\u0007%AA\u0002\u0005\u0015\u0005\"CAHoA\u0005\t\u0019AAJ\u0011%\tij\u000eI\u0001\u0002\u0004\t\t\u000bC\u0005\u0002,^\u0002\n\u00111\u0001\u00020\"I\u0011\u0011X\u001c\u0011\u0002\u0003\u0007\u0011Q\u0018\u0005\n\u0003\u000f<\u0004\u0013!a\u0001\u0003\u0017D\u0011\"!68!\u0003\u0005\r!!7\t\u0013\u0005\rx\u0007%AA\u0002\u0005\u001d\b\"CAyoA\u0005\t\u0019AA{\u0011%\typ\u000eI\u0001\u0002\u0004\u0011\u0019\u0001C\u0005\u0003\u000e]\u0002\n\u00111\u0001\u0003\u0012!I!1D\u001c\u0011\u0002\u0003\u0007!q\u0004\u0005\n\u0005S9\u0004\u0013!a\u0001\u0005[A\u0011Ba\u000e8!\u0003\u0005\rAa\u000f\t\u0013\t\u0015s\u0007%AA\u0002\t%\u0003\"\u0003B*oA\u0005\t\u0019\u0001B,\u0011%\u0011\tg\u000eI\u0001\u0002\u0004\u0011)\u0007C\u0005\u0003p]\u0002\n\u00111\u0001\u0003t!I!\u0011S\u001c\u0011\u0002\u0003\u0007!Q\u0013\u0005\n\u00053;\u0004\u0013!a\u0001\u0005+C\u0011B!(8!\u0003\u0005\rA!)\u0002-\u0005$G-\u00119qK:$G+\u001a=u\u001fB,'/\u0019;j_:$BAa;\u0003tB!!Q\u001eBx\u001b\t\tI#\u0003\u0003\u0003r\u0006%\"\u0001B+oSRDqA!>9\u0001\u0004\t\t$A\u0005pa\u0016\u0014\u0018\r^5p]\u0006!\u0012\r\u001a3FI&$H+\u001a=u\u001fB,'/\u0019;j_:$BAa;\u0003|\"9!Q_\u001dA\u0002\u0005\u0005\u0013!E1eI&k\u0017mZ3Pa\u0016\u0014\u0018\r^5p]R!!1^B\u0001\u0011\u001d\u0011)P\u000fa\u0001\u0003\u001f\n\u0011#\u00193e'\"\f\u0007/Z(qKJ\fG/[8o)\u0011\u0011Yoa\u0002\t\u000f\tU8\b1\u0001\u0002^\u00051\u0012\r\u001a3J]N,'\u000f\u001e)bO\u0016|\u0005/\u001a:bi&|g\u000e\u0006\u0003\u0003l\u000e5\u0001b\u0002B{y\u0001\u0007\u00111N\u0001\u0017C\u0012$G)\u001a7fi\u0016\u0004\u0016mZ3Pa\u0016\u0014\u0018\r^5p]R!!1^B\n\u0011\u001d\u0011)0\u0010a\u0001\u0003s\n\u0011$\u00193e\u0011&<\u0007\u000e\\5hQR$V\r\u001f;Pa\u0016\u0014\u0018\r^5p]R!!1^B\r\u0011\u001d\u0011)P\u0010a\u0001\u0003\u000f\u000ba#\u00193e\u0003B\u0004XM\u001c3MS:\\w\n]3sCRLwN\u001c\u000b\u0005\u0005W\u001cy\u0002C\u0004\u0003v~\u0002\r!!&\u0002)\u0005$G-\u00123ji2Kgn[(qKJ\fG/[8o)\u0011\u0011Yo!\n\t\u000f\tU\b\t1\u0001\u0002$\u00061\u0012\r\u001a3EK2,G/\u001a'j].|\u0005/\u001a:bi&|g\u000e\u0006\u0003\u0003l\u000e-\u0002b\u0002B{\u0003\u0002\u0007\u0011\u0011W\u0001\u001dC\u0012$\u0017\t\u001d9f]\u0012\fE\u000f^1dQ6,g\u000e^(qKJ\fG/[8o)\u0011\u0011Yo!\r\t\u000f\tU(\t1\u0001\u0002@\u0006a\u0012\r\u001a3EK2,G/Z!ui\u0006\u001c\u0007.\\3oi>\u0003XM]1uS>tG\u0003\u0002Bv\u0007oAqA!>D\u0001\u0004\ti-\u0001\u0010bI\u0012\f\u0005\u000f]3oI\u0016k'-\u001a3eK\u00124\u0015\u000e\\3Pa\u0016\u0014\u0018\r^5p]R!!1^B\u001f\u0011\u001d\u0011)\u0010\u0012a\u0001\u00037\fa$\u00193e\t\u0016dW\r^3F[\n,G\rZ3e\r&dWm\u00149fe\u0006$\u0018n\u001c8\u0015\t\t-81\t\u0005\b\u0005k,\u0005\u0019AAu\u0003Q\tG\r\u001a$jY24uN]7Pa\u0016\u0014\u0018\r^5p]R!!1^B%\u0011\u001d\u0011)P\u0012a\u0001\u0003o\f1$\u00193e\u0003B\u0004XM\u001c3G_Jlg)[3mI>\u0003XM]1uS>tG\u0003\u0002Bv\u0007\u001fBqA!>H\u0001\u0004\u0011)!A\u000ebI\u0012$U\r\\3uK\u001a{'/\u001c$jK2$w\n]3sCRLwN\u001c\u000b\u0005\u0005W\u001c)\u0006C\u0004\u0003v\"\u0003\rAa\u0005\u00023\u0005$G-\u00123ji\"Kw\r\u001b7jO\"$x\n]3sCRLwN\u001c\u000b\u0005\u0005W\u001cY\u0006C\u0004\u0003v&\u0003\rA!\t\u00027\u0005$G\rR3mKR,\u0007*[4iY&<\u0007\u000e^(qKJ\fG/[8o)\u0011\u0011Yo!\u0019\t\u000f\tU(\n1\u0001\u00030\u0005i\u0011\r\u001a3TsN$X-\u001c$p]R$BAa;\u0004h!91\u0011N&A\u0002\te\u0013!\u00014\u0002\u001b\u0005$G-\u00119qK:$GI]1x)\u0011\u0011Yda\u001c\t\u000f\rED\n1\u0001\u0003>\u0005\tq.A\u0007bI\u0012\f\u0005\u000f]3oI2Kg.\u001a\u000b\u0005\u0005\u0013\u001a9\bC\u0004\u0004r5\u0003\rAa\u0013\u0002/\u0005$G\rR3mKR,\u0017*\\1hK>\u0003XM]1uS>tG\u0003\u0002B3\u0007{Bqa!\u001dO\u0001\u0004\u00119'\u0001\btKR4uN]7XS\u0012<W\r^:\u0015\t\t-81\u0011\u0005\b\u0007cz\u0005\u0019ABC!!\u0011Yha\"\u0003z\te\u0014\u0002\u0002B<\u0005\u001b\u000b1\"\u00193e)\u0006\u0014wJ\u001d3feR!!QSBG\u0011\u001d\u0019\t\b\u0015a\u0001\u0005s\n1b]3u)\u0006\u0014wJ\u001d3feR!!1^BJ\u0011\u001d\u0019\t(\u0015a\u0001\u0007+\u0003baa&\u0004\"\ned\u0002BBM\u0007;sAAa \u0004\u001c&\u0011\u00111F\u0005\u0005\u0007?\u000bI#A\u0004qC\u000e\\\u0017mZ3\n\t\r\r6Q\u0015\u0002\u0005\u0019&\u001cHO\u0003\u0003\u0004 \u0006%\u0012\u0001D1eI\u001a{'/\\#se>\u0014H\u0003\u0002BK\u0007WCqa!\u001dS\u0001\u0004\u0011I(\u0001\fbI\u0012\u0014v\u000e^1uKB\u000bw-Z(qKJ\fG/[8o)\u0011\u0011Yo!-\t\u000f\tU8\u000b1\u0001\u0003$\u00061Q-];bYN$Baa.\u0004>B!!Q^B]\u0013\u0011\u0019Y,!\u000b\u0003\u000f\t{w\u000e\\3b]\"91q\u0018+A\u0002\r\u0005\u0017!B8uQ\u0016\u0014\b\u0003\u0002Bw\u0007\u0007LAa!2\u0002*\t\u0019\u0011I\\=\u0002\u0011!\f7\u000f[\"pI\u0016$\"aa3\u0011\t\t58QZ\u0005\u0005\u0007\u001f\fICA\u0002J]R\fa\"\u00123jiB\u000b'/Y7fi\u0016\u00148\u000fE\u0002\u00024]\u001b2aVBl!\u0011\u0011io!7\n\t\rm\u0017\u0011\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0005\rM\u0017a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0004d*\"\u0011QDBsW\t\u00199\u000f\u0005\u0003\u0004j\u000eMXBABv\u0015\u0011\u0019ioa<\u0002\u0013Ut7\r[3dW\u0016$'\u0002BBy\u0003S\t!\"\u00198o_R\fG/[8o\u0013\u0011\u0019)pa;\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u000b\u0003\u0007wTC!a\u0010\u0004f\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM*\"\u0001\"\u0001+\t\u000553Q]\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\u0011\u001d!\u0006BA.\u0007K\f1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012*TC\u0001C\u0007U\u0011\tIg!:\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00137+\t!\u0019B\u000b\u0003\u0002x\r\u0015\u0018a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$s'\u0006\u0002\u0005\u001a)\"\u0011QQBs\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%qU\u0011Aq\u0004\u0016\u0005\u0003'\u001b)/A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%O\u000b\u0003\tKQC!!)\u0004f\u0006aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\u0002TC\u0001C\u0016U\u0011\tyk!:\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132cU\u0011A\u0011\u0007\u0016\u0005\u0003{\u001b)/\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%\r\u001a\u0016\u0005\u0011]\"\u0006BAf\u0007K\fA\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n4'\u0006\u0002\u0005>)\"\u0011\u0011\\Bs\u0003q!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cQ*\"\u0001b\u0011+\t\u0005\u001d8Q]\u0001\u001dI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u00196+\t!IE\u000b\u0003\u0002v\u000e\u0015\u0018\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013GN\u000b\u0003\t\u001fRCAa\u0001\u0004f\u0006aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE:TC\u0001C+U\u0011\u0011\tb!:\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132qU\u0011A1\f\u0016\u0005\u0005?\u0019)/\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%M\u001d\u0016\u0005\u0011\u0005$\u0006\u0002B\u0017\u0007K\fA\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0004'\u0006\u0002\u0005h)\"!1HBs\u0003q!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%eE*\"\u0001\"\u001c+\t\t%3Q]\u0001\u001dI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a3+\t!\u0019H\u000b\u0003\u0003X\r\u0015\u0018\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$#gM\u000b\u0003\tsRCA!\u001a\u0004f\u0006aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII\"TC\u0001C@U\u0011\u0011\u0019h!:\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133kU\u0011AQ\u0011\u0016\u0005\u0005+\u001b)/\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HE\r\u001c\u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133oU\u0011AQ\u0012\u0016\u0005\u0005C\u001b)\u000f")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/edit/EditParameters.class */
public class EditParameters extends MultiplePdfSourceMultipleOutputParameters {
    private final ListBuffer<AppendTextOperation> appendTextOperations;
    private final ListBuffer<EditTextOperation> editTextOperations;
    private final ListBuffer<AddImageOperation> imageOperations;
    private final ListBuffer<AddShapeOperation> shapeOperations;
    private final ListBuffer<InsertPageOperation> insertPageOperations;
    private final ListBuffer<DeletePageOperation> deletePageOperations;
    private final ListBuffer<HighlightTextOperation> highlightTextOperations;
    private final ListBuffer<AppendLinkOperation> appendLinkOperations;
    private final ListBuffer<EditLinkOperation> editLinkOperations;
    private final ListBuffer<DeleteLinkOperation> deleteLinkOperations;
    private final ListBuffer<AppendAttachmentOperation> appendAttachmentOperations;
    private final ListBuffer<DeleteAttachmentOperation> deleteAttachmentOperations;
    private final ListBuffer<AppendEmbeddedFileOperation> appendEmbeddedFileOperations;
    private final ListBuffer<DeleteEmbeddedFileOperation> deleteEmbeddedFileOperations;
    private final ListBuffer<FillFormOperation> fillFormOperations;
    private final ListBuffer<AppendFormFieldOperation> appendFormFieldOperations;
    private final ListBuffer<DeleteFormFieldOperation> deleteFormFieldOperations;
    private final ListBuffer<EditHighlightOperation> editHighlightOperations;
    private final ListBuffer<DeleteHighlightOperation> deleteHighlightOperations;
    private final ListBuffer<AppendDrawOperation> appendDraw;
    private final ListBuffer<AppendLineOperation> appendLine;
    private final ListBuffer<SystemFont> systemFonts;
    private final ListBuffer<DeleteImageOperation> deleteImageOperations;
    private final Map<String, String> formWidgets;
    private final ListBuffer<String> formErrors;
    private final ListBuffer<String> tabOrder;
    private final ListBuffer<RotatePageOperation> rotatePageOperations;

    public ListBuffer<AppendTextOperation> appendTextOperations() {
        return this.appendTextOperations;
    }

    public EditParameters(final ListBuffer<AppendTextOperation> appendTextOperations, final ListBuffer<EditTextOperation> editTextOperations, final ListBuffer<AddImageOperation> imageOperations, final ListBuffer<AddShapeOperation> shapeOperations, final ListBuffer<InsertPageOperation> insertPageOperations, final ListBuffer<DeletePageOperation> deletePageOperations, final ListBuffer<HighlightTextOperation> highlightTextOperations, final ListBuffer<AppendLinkOperation> appendLinkOperations, final ListBuffer<EditLinkOperation> editLinkOperations, final ListBuffer<DeleteLinkOperation> deleteLinkOperations, final ListBuffer<AppendAttachmentOperation> appendAttachmentOperations, final ListBuffer<DeleteAttachmentOperation> deleteAttachmentOperations, final ListBuffer<AppendEmbeddedFileOperation> appendEmbeddedFileOperations, final ListBuffer<DeleteEmbeddedFileOperation> deleteEmbeddedFileOperations, final ListBuffer<FillFormOperation> fillFormOperations, final ListBuffer<AppendFormFieldOperation> appendFormFieldOperations, final ListBuffer<DeleteFormFieldOperation> deleteFormFieldOperations, final ListBuffer<EditHighlightOperation> editHighlightOperations, final ListBuffer<DeleteHighlightOperation> deleteHighlightOperations, final ListBuffer<AppendDrawOperation> appendDraw, final ListBuffer<AppendLineOperation> appendLine, final ListBuffer<SystemFont> systemFonts, final ListBuffer<DeleteImageOperation> deleteImageOperations, final Map<String, String> formWidgets, final ListBuffer<String> formErrors, final ListBuffer<String> tabOrder, final ListBuffer<RotatePageOperation> rotatePageOperations) {
        this.appendTextOperations = appendTextOperations;
        this.editTextOperations = editTextOperations;
        this.imageOperations = imageOperations;
        this.shapeOperations = shapeOperations;
        this.insertPageOperations = insertPageOperations;
        this.deletePageOperations = deletePageOperations;
        this.highlightTextOperations = highlightTextOperations;
        this.appendLinkOperations = appendLinkOperations;
        this.editLinkOperations = editLinkOperations;
        this.deleteLinkOperations = deleteLinkOperations;
        this.appendAttachmentOperations = appendAttachmentOperations;
        this.deleteAttachmentOperations = deleteAttachmentOperations;
        this.appendEmbeddedFileOperations = appendEmbeddedFileOperations;
        this.deleteEmbeddedFileOperations = deleteEmbeddedFileOperations;
        this.fillFormOperations = fillFormOperations;
        this.appendFormFieldOperations = appendFormFieldOperations;
        this.deleteFormFieldOperations = deleteFormFieldOperations;
        this.editHighlightOperations = editHighlightOperations;
        this.deleteHighlightOperations = deleteHighlightOperations;
        this.appendDraw = appendDraw;
        this.appendLine = appendLine;
        this.systemFonts = systemFonts;
        this.deleteImageOperations = deleteImageOperations;
        this.formWidgets = formWidgets;
        this.formErrors = formErrors;
        this.tabOrder = tabOrder;
        this.rotatePageOperations = rotatePageOperations;
    }

    public ListBuffer<EditTextOperation> editTextOperations() {
        return this.editTextOperations;
    }

    public ListBuffer<AddImageOperation> imageOperations() {
        return this.imageOperations;
    }

    public ListBuffer<AddShapeOperation> shapeOperations() {
        return this.shapeOperations;
    }

    public ListBuffer<InsertPageOperation> insertPageOperations() {
        return this.insertPageOperations;
    }

    public ListBuffer<DeletePageOperation> deletePageOperations() {
        return this.deletePageOperations;
    }

    public ListBuffer<HighlightTextOperation> highlightTextOperations() {
        return this.highlightTextOperations;
    }

    public ListBuffer<AppendLinkOperation> appendLinkOperations() {
        return this.appendLinkOperations;
    }

    public ListBuffer<EditLinkOperation> editLinkOperations() {
        return this.editLinkOperations;
    }

    public ListBuffer<DeleteLinkOperation> deleteLinkOperations() {
        return this.deleteLinkOperations;
    }

    public ListBuffer<AppendAttachmentOperation> appendAttachmentOperations() {
        return this.appendAttachmentOperations;
    }

    public ListBuffer<DeleteAttachmentOperation> deleteAttachmentOperations() {
        return this.deleteAttachmentOperations;
    }

    public ListBuffer<AppendEmbeddedFileOperation> appendEmbeddedFileOperations() {
        return this.appendEmbeddedFileOperations;
    }

    public ListBuffer<DeleteEmbeddedFileOperation> deleteEmbeddedFileOperations() {
        return this.deleteEmbeddedFileOperations;
    }

    public ListBuffer<FillFormOperation> fillFormOperations() {
        return this.fillFormOperations;
    }

    public ListBuffer<AppendFormFieldOperation> appendFormFieldOperations() {
        return this.appendFormFieldOperations;
    }

    public ListBuffer<DeleteFormFieldOperation> deleteFormFieldOperations() {
        return this.deleteFormFieldOperations;
    }

    public ListBuffer<EditHighlightOperation> editHighlightOperations() {
        return this.editHighlightOperations;
    }

    public ListBuffer<DeleteHighlightOperation> deleteHighlightOperations() {
        return this.deleteHighlightOperations;
    }

    public ListBuffer<AppendDrawOperation> appendDraw() {
        return this.appendDraw;
    }

    public ListBuffer<AppendLineOperation> appendLine() {
        return this.appendLine;
    }

    public ListBuffer<SystemFont> systemFonts() {
        return this.systemFonts;
    }

    public ListBuffer<DeleteImageOperation> deleteImageOperations() {
        return this.deleteImageOperations;
    }

    public Map<String, String> formWidgets() {
        return this.formWidgets;
    }

    public ListBuffer<String> formErrors() {
        return this.formErrors;
    }

    public ListBuffer<String> tabOrder() {
        return this.tabOrder;
    }

    public ListBuffer<RotatePageOperation> rotatePageOperations() {
        return this.rotatePageOperations;
    }

    public void addAppendTextOperation(final AppendTextOperation operation) {
        appendTextOperations().$plus$eq(operation);
    }

    public void addEditTextOperation(final EditTextOperation operation) {
        editTextOperations().$plus$eq(operation);
    }

    public void addImageOperation(final AddImageOperation operation) {
        imageOperations().$plus$eq(operation);
    }

    public void addShapeOperation(final AddShapeOperation operation) {
        shapeOperations().$plus$eq(operation);
    }

    public void addInsertPageOperation(final InsertPageOperation operation) {
        insertPageOperations().$plus$eq(operation);
    }

    public void addDeletePageOperation(final DeletePageOperation operation) {
        deletePageOperations().$plus$eq(operation);
    }

    public void addHighlightTextOperation(final HighlightTextOperation operation) {
        highlightTextOperations().$plus$eq(operation);
    }

    public void addAppendLinkOperation(final AppendLinkOperation operation) {
        appendLinkOperations().$plus$eq(operation);
    }

    public void addEditLinkOperation(final EditLinkOperation operation) {
        editLinkOperations().$plus$eq(operation);
    }

    public void addDeleteLinkOperation(final DeleteLinkOperation operation) {
        deleteLinkOperations().$plus$eq(operation);
    }

    public void addAppendAttachmentOperation(final AppendAttachmentOperation operation) {
        appendAttachmentOperations().$plus$eq(operation);
    }

    public void addDeleteAttachmentOperation(final DeleteAttachmentOperation operation) {
        deleteAttachmentOperations().$plus$eq(operation);
    }

    public void addAppendEmbeddedFileOperation(final AppendEmbeddedFileOperation operation) {
        appendEmbeddedFileOperations().$plus$eq(operation);
    }

    public void addDeleteEmbeddedFileOperation(final DeleteEmbeddedFileOperation operation) {
        deleteEmbeddedFileOperations().$plus$eq(operation);
    }

    public void addFillFormOperation(final FillFormOperation operation) {
        fillFormOperations().$plus$eq(operation);
    }

    public void addAppendFormFieldOperation(final AppendFormFieldOperation operation) {
        appendFormFieldOperations().$plus$eq(operation);
    }

    public void addDeleteFormFieldOperation(final DeleteFormFieldOperation operation) {
        deleteFormFieldOperations().$plus$eq(operation);
    }

    public void addEditHighlightOperation(final EditHighlightOperation operation) {
        editHighlightOperations().$plus$eq(operation);
    }

    public void addDeleteHighlightOperation(final DeleteHighlightOperation operation) {
        deleteHighlightOperations().$plus$eq(operation);
    }

    public void addSystemFont(final SystemFont f) {
        systemFonts().$plus$eq(f);
    }

    public ListBuffer<AppendDrawOperation> addAppendDraw(final AppendDrawOperation o) {
        return appendDraw().$plus$eq(o);
    }

    public ListBuffer<AppendLineOperation> addAppendLine(final AppendLineOperation o) {
        return appendLine().$plus$eq(o);
    }

    public ListBuffer<DeleteImageOperation> addDeleteImageOperation(final DeleteImageOperation o) {
        return deleteImageOperations().$plus$eq(o);
    }

    public void setFormWidgets(final scala.collection.immutable.Map<String, String> o) {
        formWidgets().clear();
        o.foreach(e -> {
            return this.formWidgets().put(e._1(), e._2());
        });
    }

    public ListBuffer<String> addTabOrder(final String o) {
        return tabOrder().$plus$eq(o);
    }

    public void setTabOrder(final List<String> o) {
        tabOrder().clear();
        o.foreach(o2 -> {
            return this.addTabOrder(o2);
        });
    }

    public ListBuffer<String> addFormError(final String o) {
        return formErrors().$plus$eq(o);
    }

    public void addRotatePageOperation(final RotatePageOperation operation) {
        rotatePageOperations().$plus$eq(operation);
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public boolean equals(final Object other) {
        if (other instanceof EditParameters) {
            EditParameters editParameters = (EditParameters) other;
            return new EqualsBuilder().appendSuper(super.equals(editParameters)).append(appendTextOperations(), editParameters.appendTextOperations()).append(editTextOperations(), editParameters.editTextOperations()).append(imageOperations(), editParameters.imageOperations()).append(shapeOperations(), editParameters.shapeOperations()).append(insertPageOperations(), editParameters.insertPageOperations()).append(deletePageOperations(), editParameters.deletePageOperations()).append(highlightTextOperations(), editParameters.highlightTextOperations()).append(appendLinkOperations(), editParameters.appendLinkOperations()).append(editLinkOperations(), editParameters.editLinkOperations()).append(deleteLinkOperations(), editParameters.deleteLinkOperations()).append(appendAttachmentOperations(), editParameters.appendAttachmentOperations()).append(deleteAttachmentOperations(), editParameters.deleteAttachmentOperations()).append(appendEmbeddedFileOperations(), editParameters.appendEmbeddedFileOperations()).append(deleteEmbeddedFileOperations(), editParameters.deleteEmbeddedFileOperations()).append(fillFormOperations(), editParameters.fillFormOperations()).append(appendFormFieldOperations(), editParameters.appendFormFieldOperations()).append(deleteFormFieldOperations(), editParameters.deleteFormFieldOperations()).append(systemFonts(), editParameters.systemFonts()).append(editHighlightOperations(), editParameters.editHighlightOperations()).append(deleteHighlightOperations(), editParameters.deleteHighlightOperations()).append(appendDraw(), editParameters.appendDraw()).append(deleteImageOperations(), editParameters.deleteImageOperations()).append(formWidgets(), editParameters.formWidgets()).append(formErrors(), editParameters.formErrors()).append(tabOrder(), editParameters.tabOrder()).append(rotatePageOperations(), editParameters.rotatePageOperations()).isEquals();
        }
        return false;
    }

    @Override // org.sejda.model.parameter.base.MultiplePdfSourceMultipleOutputParameters, org.sejda.model.parameter.base.MultiplePdfSourceParameters, org.sejda.model.parameter.base.AbstractPdfOutputParameters, org.sejda.model.parameter.base.AbstractParameters
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(appendTextOperations()).append(editTextOperations()).append(imageOperations()).append(shapeOperations()).append(insertPageOperations()).append(deletePageOperations()).append(highlightTextOperations()).append(appendLinkOperations()).append(editLinkOperations()).append(deleteLinkOperations()).append(appendAttachmentOperations()).append(deleteAttachmentOperations()).append(appendEmbeddedFileOperations()).append(deleteEmbeddedFileOperations()).append(fillFormOperations()).append(appendFormFieldOperations()).append(systemFonts()).append(editHighlightOperations()).append(deleteHighlightOperations()).append(appendDraw()).append(deleteImageOperations()).append(formWidgets()).append(formErrors()).append(tabOrder()).append(rotatePageOperations()).toHashCode();
    }
}

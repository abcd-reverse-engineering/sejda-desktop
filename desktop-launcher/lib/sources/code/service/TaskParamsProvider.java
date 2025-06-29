package code.service;

import net.liftweb.json.JsonAST;
import org.sejda.model.parameter.base.TaskParameters;
import scala.reflect.ScalaSignature;

/* compiled from: InJvmTaskExecutor.scala */
@ScalaSignature(bytes = "\u0006\u0005Y2qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001C\u0001\nUCN\\\u0007+\u0019:b[N\u0004&o\u001c<jI\u0016\u0014(B\u0001\u0003\u0006\u0003\u001d\u0019XM\u001d<jG\u0016T\u0011AB\u0001\u0005G>$Wm\u0001\u0001\u0014\u0005\u0001I\u0001C\u0001\u0006\u000e\u001b\u0005Y!\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\n\u00059Y!AB!osJ+g-A\u0003qCJ\u001cX\r\u0006\u0002\u0012?A\u0011!#H\u0007\u0002')\u0011A#F\u0001\u0005E\u0006\u001cXM\u0003\u0002\u0017/\u0005I\u0001/\u0019:b[\u0016$XM\u001d\u0006\u00031e\tQ!\\8eK2T!AG\u000e\u0002\u000bM,'\u000eZ1\u000b\u0003q\t1a\u001c:h\u0013\tq2C\u0001\bUCN\\\u0007+\u0019:b[\u0016$XM]:\t\u000b\u0001\n\u0001\u0019A\u0011\u0002\t)\u001cxN\u001c\t\u0003EMr!a\t\u0019\u000f\u0005\u0011rcBA\u0013,\u001d\t1\u0013&D\u0001(\u0015\tAs!\u0001\u0004=e>|GOP\u0005\u0002U\u0005\u0019a.\u001a;\n\u00051j\u0013a\u00027jMR<XM\u0019\u0006\u0002U%\u0011\u0001e\f\u0006\u0003Y5J!!\r\u001a\u0002\u000fA\f7m[1hK*\u0011\u0001eL\u0005\u0003iU\u0012aA\u0013,bYV,'BA\u00193\u0001")
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/TaskParamsProvider.class */
public interface TaskParamsProvider {
    TaskParameters parse(final JsonAST.JValue json);
}

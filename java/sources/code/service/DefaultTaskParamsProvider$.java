package code.service;

import code.model.TaskJsonParser;
import net.liftweb.json.JsonAST;
import org.sejda.model.parameter.base.TaskParameters;

/* compiled from: InJvmTaskExecutor.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/service/DefaultTaskParamsProvider$.class */
public final class DefaultTaskParamsProvider$ implements TaskParamsProvider {
    public static final DefaultTaskParamsProvider$ MODULE$ = new DefaultTaskParamsProvider$();

    private DefaultTaskParamsProvider$() {
    }

    @Override // code.service.TaskParamsProvider
    public TaskParameters parse(final JsonAST.JValue json) {
        return new TaskJsonParser() { // from class: code.model.TaskJsonParser$
            {
                DefaultFileProvider$ defaultFileProvider$ = DefaultFileProvider$.MODULE$;
            }
        }.fromJson(json);
    }
}

package org.sejda.sambox.pdmodel.interactive.form;

import org.sejda.sambox.pdmodel.interactive.action.PDActionJavaScript;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/ScriptingHandler.class */
public interface ScriptingHandler {
    String keyboard(PDActionJavaScript pDActionJavaScript, String str);

    String format(PDActionJavaScript pDActionJavaScript, String str);

    boolean validate(PDActionJavaScript pDActionJavaScript, String str);

    String calculate(PDActionJavaScript pDActionJavaScript, String str);
}

package org.sejda.sambox.pdmodel.interactive.annotation.handlers;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/annotation/handlers/PDAppearanceHandler.class */
public interface PDAppearanceHandler {
    void generateAppearanceStreams();

    void generateNormalAppearance();

    void generateRolloverAppearance();

    void generateDownAppearance();
}

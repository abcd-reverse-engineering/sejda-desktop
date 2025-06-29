package org.sejda.model.rotation;

import org.sejda.model.FriendlyNamed;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/rotation/Rotation.class */
public enum Rotation implements FriendlyNamed {
    DEGREES_0(0),
    DEGREES_90(D_90),
    DEGREES_180(180),
    DEGREES_270(D_270);

    private static final int D_360 = 360;
    private static final int D_90 = 90;
    private static final int D_270 = 270;
    private final int degrees;
    private final String displayName;

    Rotation(int degrees) {
        this.displayName = String.valueOf(degrees);
        this.degrees = degrees;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }

    public int getDegrees() {
        return this.degrees;
    }

    public static Rotation getRotation(int degrees) {
        int actualRotation = degrees % D_360;
        for (Rotation rotation : values()) {
            if (rotation.getDegrees() == actualRotation) {
                return rotation;
            }
        }
        return DEGREES_0;
    }

    public Rotation addRotation(Rotation rotation) {
        return getRotation(getDegrees() + rotation.getDegrees());
    }

    public Rotation rotateClockwise() {
        return getRotation((this.degrees + D_90) % D_360);
    }

    public Rotation rotateAnticlockwise() {
        return getRotation((this.degrees + D_270) % D_360);
    }
}

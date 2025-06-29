package code.sejda.tasks.annotation;

import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLine;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationMarkup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationPopup;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationSquareCircle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationText;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import scala.Predef$;
import scala.collection.immutable.Set;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

/* compiled from: DeleteAnnotationsParameters.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/annotation/AnnotationType$.class */
public final class AnnotationType$ {
    public static final AnnotationType$ MODULE$ = new AnnotationType$();
    private static final Set<AnnotationType> All = (Set) Predef$.MODULE$.Set().apply(ScalaRunTime$.MODULE$.wrapRefArray(new AnnotationType[]{new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Highlight$
        public String toString() {
            return "highlight";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationTextMarkup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT) : PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Strikeout$
        public String toString() {
            return "strikeout";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationTextMarkup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationTextMarkup.SUB_TYPE_STRIKEOUT) : PDAnnotationTextMarkup.SUB_TYPE_STRIKEOUT == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Underline$
        public String toString() {
            return "underline";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationTextMarkup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals("Underline") : "Underline" == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Ink$
        public String toString() {
            return "ink";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationMarkup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationMarkup.SUB_TYPE_INK) : PDAnnotationMarkup.SUB_TYPE_INK == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Polygon$
        public String toString() {
            return "polygon";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationMarkup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationMarkup.SUB_TYPE_POLYGON) : PDAnnotationMarkup.SUB_TYPE_POLYGON == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Polyline$
        public String toString() {
            return "polyline";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationMarkup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationMarkup.SUB_TYPE_POLYLINE) : PDAnnotationMarkup.SUB_TYPE_POLYLINE == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Square$
        public String toString() {
            return "square";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationSquareCircle) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals("Square") : "Square" == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Circle$
        public String toString() {
            return "circle";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationSquareCircle) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals("Circle") : "Circle" == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Line$
        public String toString() {
            return "line";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationLine) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationLine.SUB_TYPE) : PDAnnotationLine.SUB_TYPE == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Text$
        public String toString() {
            return "text";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationText) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationText.SUB_TYPE) : PDAnnotationText.SUB_TYPE == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Popup$
        public String toString() {
            return "popup";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationPopup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals(PDAnnotationPopup.SUB_TYPE) : PDAnnotationPopup.SUB_TYPE == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$FreeText$
        public String toString() {
            return "freetext";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationMarkup) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals("FreeText") : "FreeText" == 0) {
                    return true;
                }
            }
            return false;
        }
    }, new AnnotationType() { // from class: code.sejda.tasks.annotation.AnnotationType$Link$
        public String toString() {
            return "link";
        }

        @Override // code.sejda.tasks.annotation.AnnotationType
        public boolean matches(final PDAnnotation a) {
            if (a instanceof PDAnnotationLink) {
                String subtype = a.getSubtype();
                if (subtype != null ? subtype.equals("Link") : "Link" == 0) {
                    return true;
                }
            }
            return false;
        }
    }}));

    private AnnotationType$() {
    }

    public Set<AnnotationType> All() {
        return All;
    }

    public AnnotationType fromString(final String s) {
        return (AnnotationType) All().find(a -> {
            return BoxesRunTime.boxToBoolean($anonfun$fromString$1(s, a));
        }).getOrElse(() -> {
            throw new RuntimeException(new StringBuilder(25).append("Unknown annotation type: ").append(s).toString());
        });
    }

    public static final /* synthetic */ boolean $anonfun$fromString$1(final String s$1, final AnnotationType a) {
        String string = a.toString();
        String lowerCase = s$1.toLowerCase();
        return string != null ? string.equals(lowerCase) : lowerCase == null;
    }
}

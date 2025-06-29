package code.sejda.tasks.common;

import java.awt.geom.Point2D;
import org.sejda.model.RectangularBox;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Seq;
import scala.runtime.BoxesRunTime;

/* compiled from: AnnotationsHelper.scala */
/* loaded from: com.sejda.common-0.1-SNAPSHOT.jar:code/sejda/tasks/common/AnnotationsHelper$.class */
public final class AnnotationsHelper$ {
    public static final AnnotationsHelper$ MODULE$ = new AnnotationsHelper$();

    private AnnotationsHelper$() {
    }

    public float[] quadsOf(final PDRectangle position) {
        float[] quads = new float[8];
        quads[0] = position.getLowerLeftX();
        quads[1] = position.getUpperRightY();
        quads[2] = position.getUpperRightX();
        quads[3] = quads[1];
        quads[4] = quads[0];
        quads[5] = position.getLowerLeftY();
        quads[6] = quads[2];
        quads[7] = quads[5];
        return quads;
    }

    public RectangularBox boundingBoxAdjustedToPage(final RectangularBox boundingBox, final PDPage page) {
        PDRectangle cropBox = page.getCropBox();
        PDRectangle mediaBox = page.getMediaBox();
        float deltaX = cropBox.getLowerLeftX();
        float deltaY = cropBox.getLowerLeftY();
        float bottom = boundingBox.getBottom() + deltaY;
        float left = boundingBox.getLeft() + deltaX;
        float top = boundingBox.getTop() + deltaY;
        float right = boundingBox.getRight() + deltaX;
        RectangularBox adjusted = RectangularBox.newInstance((int) bottom, (int) left, (int) top, (int) right);
        int height = adjusted.getTop() - adjusted.getBottom();
        int width = adjusted.getRight() - adjusted.getLeft();
        int pageHeight = (int) mediaBox.getHeight();
        int pageWidth = (int) mediaBox.getWidth();
        switch (page.getRotation()) {
            case 90:
                int bottom2 = adjusted.getLeft();
                int left2 = pageWidth - adjusted.getTop();
                int top2 = bottom2 + width;
                int right2 = left2 + height;
                return RectangularBox.newInstance(bottom2, left2, top2, right2);
            case 180:
                int bottom3 = pageHeight - adjusted.getTop();
                int left3 = pageWidth - adjusted.getRight();
                int top3 = bottom3 + height;
                int right3 = left3 + width;
                return RectangularBox.newInstance(bottom3, left3, top3, right3);
            case 270:
                int bottom4 = pageHeight - adjusted.getRight();
                int left4 = adjusted.getBottom();
                int top4 = bottom4 + width;
                int right4 = left4 + height;
                return RectangularBox.newInstance(bottom4, left4, top4, right4);
            default:
                return adjusted;
        }
    }

    public PDRectangle toPDRectangle(final RectangularBox boundingBox) {
        return new PDRectangle(boundingBox.getLeft(), boundingBox.getBottom(), boundingBox.getRight() - boundingBox.getLeft(), boundingBox.getTop() - boundingBox.getBottom());
    }

    public float overlapPercent(final PDRectangle target, final PDRectangle other) {
        float x = Math.max(target.getLowerLeftX(), other.getLowerLeftX());
        float y = Math.max(target.getLowerLeftY(), other.getLowerLeftY());
        float x1 = Math.min(target.getUpperRightX(), other.getUpperRightX());
        float y1 = Math.min(target.getUpperRightY(), other.getUpperRightY());
        if (x1 < x || y1 < y) {
            return 0.0f;
        }
        PDRectangle overlap = new PDRectangle(x, y, x1 - x, y1 - y);
        float overlapArea = overlap.getWidth() * overlap.getHeight();
        float otherArea = other.getWidth() * other.getHeight();
        return (overlapArea * 100) / otherArea;
    }

    private int roundUp(final double in) {
        return (int) Math.ceil(in);
    }

    public Seq<Tuple2<PDAnnotation, Object>> findAnnotationsInBoundingBox(final PDPage page, final Point2D position, final float width, final float height) {
        int bottom = roundUp(position.getY());
        int left = roundUp(position.getX());
        int top = roundUp(position.getY() + height);
        int right = roundUp(position.getX() + width);
        PDRectangle targetRect = toPDRectangle(boundingBoxAdjustedToPage(RectangularBox.newInstance(bottom, left, top, right), page));
        return ((IterableOnceOps) ((IterableOps) ((IterableOps) JavaConverters$.MODULE$.asScalaBufferConverter(page.getAnnotations()).asScala()).filter(x$1 -> {
            return BoxesRunTime.boxToBoolean($anonfun$findAnnotationsInBoundingBox$1(x$1));
        })).map(annot -> {
            float p = MODULE$.overlapPercent(targetRect, annot.getRectangle());
            return new Tuple2(annot, BoxesRunTime.boxToFloat(p));
        })).toList();
    }

    public static final /* synthetic */ boolean $anonfun$findAnnotationsInBoundingBox$1(final PDAnnotation x$1) {
        return x$1.getRectangle() != null;
    }
}

package org.sejda.impl.sambox.pro.component;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.impl.sambox.component.PageTextWriter;
import org.sejda.model.exception.TaskIOException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationFileAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator.class */
public class AttachmentsSummaryCreator {
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int DEFAULT_LINE_HEIGHT = 23;
    private static final int DEFAULT_MARGIN = 40;
    private static final String SEPARATOR = "  ";
    private final Deque<ToCItem> items = new LinkedList();
    private PDDocument document;
    private PageTextWriter writer;
    private static final Logger LOG = LoggerFactory.getLogger(AttachmentsSummaryCreator.class);
    private static final PDFont FONT = PDType1Font.HELVETICA();
    private static final PDRectangle PAGE_SIZE = PDRectangle.A4;

    public AttachmentsSummaryCreator(PDDocument document) {
        RequireUtils.requireNotNullArg(document, "Containing document cannot be null");
        this.document = document;
        this.writer = new PageTextWriter(document);
    }

    public void appendItem(String attachmentName, PDAnnotationFileAttachment annotation) {
        RequireUtils.requireNotBlank(attachmentName, "Attachment name cannot be blank");
        RequireUtils.requireNotNullArg(annotation, "ToC annotation cannot be null");
        this.items.add(new ToCItem(attachmentName, annotation));
    }

    public void addToC() {
        try {
            PDPageTree pagesTree = this.document.getPages();
            Optional.ofNullable(generateToC()).filter(l -> {
                return !l.isEmpty();
            }).ifPresent(t -> {
                t.descendingIterator().forEachRemaining(p -> {
                    if (pagesTree.getCount() > 0) {
                        pagesTree.insertBefore(p, pagesTree.get(0));
                    } else {
                        pagesTree.add(p);
                    }
                });
            });
        } catch (IOException | TaskIOException e) {
            LOG.error("An error occurred while create the ToC. Skipping ToC creation.", e);
        }
    }

    private LinkedList<PDPage> generateToC() throws TaskIOException, IOException {
        LinkedList<PDPage> pages = new LinkedList<>();
        int maxRows = (int) ((PAGE_SIZE.getHeight() - 80.0f) / 23.0f);
        while (!this.items.isEmpty()) {
            int row = 0;
            float separatorWidth = stringLength(SEPARATOR);
            PDPage page = createPage(pages);
            while (!this.items.isEmpty() && row < maxRows) {
                ToCItem i = this.items.poll();
                if (Objects.nonNull(i)) {
                    row++;
                    float y = (PAGE_SIZE.getHeight() - 40.0f) - (row * DEFAULT_LINE_HEIGHT);
                    String itemText = sanitize(i.text, separatorWidth);
                    writeText(page, itemText, 40.0f, y);
                    float textLenght = stringLength(itemText);
                    writeText(page, SEPARATOR, 40.0f + textLenght, y);
                    i.annotation.setRectangle(new PDRectangle(40.0f + textLenght + separatorWidth, y, 14.0f, 14.0f));
                    page.getAnnotations().add(i.annotation);
                }
            }
        }
        return pages;
    }

    private void writeText(PDPage page, String s, float x, float y) throws TaskIOException, IOException {
        this.writer.write(page, (Point2D) new Point2D.Float(x, y), s, FONT, Double.valueOf(14.0d), Color.BLACK);
    }

    private String sanitize(String text, float separatorWidth) throws TaskIOException {
        int currentLength;
        float maxLen = ((PAGE_SIZE.getWidth() - 80.0f) - 14.0f) - separatorWidth;
        if (stringLength(text) > maxLen) {
            LOG.debug("Truncating ToC text to fit available space");
            int length = text.length();
            while (true) {
                currentLength = length / 2;
                if (stringLength(text.substring(0, currentLength)) <= maxLen) {
                    break;
                }
                length = currentLength;
            }
            int currentChunk = currentLength;
            while (currentChunk > 1) {
                currentChunk /= 2;
                if (stringLength(text.substring(0, currentLength + currentChunk)) < maxLen) {
                    currentLength += currentChunk;
                }
            }
            return text.substring(0, currentLength);
        }
        return text;
    }

    private PDPage createPage(LinkedList<PDPage> pages) {
        LOG.debug("Creating new ToC page");
        PDPage page = new PDPage(PAGE_SIZE);
        pages.add(page);
        return page;
    }

    private float stringLength(String text) throws TaskIOException {
        return this.writer.getStringWidth(text, FONT, 14.0f);
    }

    public boolean hasToc() {
        return !this.items.isEmpty();
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator$ToCItem.class */
    private static final class ToCItem extends Record {
        private final String text;
        private final PDAnnotationFileAttachment annotation;

        private ToCItem(String text, PDAnnotationFileAttachment annotation) {
            this.text = text;
            this.annotation = annotation;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ToCItem.class), ToCItem.class, "text;annotation", "FIELD:Lorg/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator$ToCItem;->text:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator$ToCItem;->annotation:Lorg/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationFileAttachment;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ToCItem.class), ToCItem.class, "text;annotation", "FIELD:Lorg/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator$ToCItem;->text:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator$ToCItem;->annotation:Lorg/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationFileAttachment;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ToCItem.class, Object.class), ToCItem.class, "text;annotation", "FIELD:Lorg/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator$ToCItem;->text:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/pro/component/AttachmentsSummaryCreator$ToCItem;->annotation:Lorg/sejda/sambox/pdmodel/interactive/annotation/PDAnnotationFileAttachment;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public String text() {
            return this.text;
        }

        public PDAnnotationFileAttachment annotation() {
            return this.annotation;
        }
    }
}

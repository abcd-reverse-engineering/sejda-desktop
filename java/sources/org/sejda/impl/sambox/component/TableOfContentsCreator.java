package org.sejda.impl.sambox.component;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.MergeParameters;
import org.sejda.model.toc.ToCPolicy;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSInteger;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/TableOfContentsCreator.class */
public class TableOfContentsCreator {
    private static final Logger LOG = LoggerFactory.getLogger(TableOfContentsCreator.class);
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final int DEFAULT_MARGIN = 72;
    private static final String SEPARATOR = "  ";
    private PDDocument document;
    private float lineHeight;
    private MergeParameters params;
    private PageTextWriter writer;
    private LinkedList<PDPage> generatedToC;
    private final Deque<ToCItem> items = new LinkedList();
    private PDRectangle pageSize = null;
    private float fontSize = 14.0f;
    private float margin = 72.0f;
    private PDFont font = PDType1Font.HELVETICA();

    public TableOfContentsCreator(MergeParameters params, PDDocument document) {
        RequireUtils.requireNotNullArg(document, "Containing document cannot be null");
        RequireUtils.requireNotNullArg(params, "Parameters cannot be null");
        this.document = document;
        this.params = params;
        this.writer = new PageTextWriter(document);
    }

    public void appendItem(String text, long pageNumber, PDPage page) {
        RequireUtils.requireNotBlank(text, "ToC item cannot be blank");
        RequireUtils.requireArg(pageNumber > 0, "ToC item cannot point to a negative page");
        RequireUtils.requireNotNullArg(page, "ToC page cannot be null");
        requireNotAlreadyGenerated();
        if (shouldGenerateToC()) {
            this.items.add(new ToCItem(text, pageNumber, linkAnnotationFor(page)));
        }
    }

    private PDAnnotationLink linkAnnotationFor(PDPage importedPage) {
        PDPageXYZDestination pageDest = OutlineUtils.pageDestinationFor(importedPage);
        PDAnnotationLink link = new PDAnnotationLink();
        link.setDestination(pageDest);
        link.setBorder(new COSArray(COSInteger.ZERO, COSInteger.ZERO, COSInteger.ZERO));
        return link;
    }

    public int addToC() throws TaskException {
        return addToC(0);
    }

    public int addToC(int beforePageNumber) throws TaskException {
        PDPageTree pagesTree = this.document.getPages();
        LinkedList<PDPage> toc = generateToC();
        toc.descendingIterator().forEachRemaining(p -> {
            if (pagesTree.getCount() > 0) {
                pagesTree.insertBefore(p, pagesTree.get(beforePageNumber));
            } else {
                pagesTree.add(p);
            }
        });
        return toc.size();
    }

    private LinkedList<PDPage> generateToC() throws TaskIOException {
        if (this.generatedToC == null) {
            this.generatedToC = _generateToC();
        }
        return this.generatedToC;
    }

    private LinkedList<PDPage> _generateToC() throws TaskIOException {
        int tocNumberOfPages = _generateToC(0).size();
        return _generateToC(tocNumberOfPages);
    }

    private LinkedList<PDPage> _generateToC(int tocNumberOfPages) throws TaskIOException {
        LinkedList<PDPage> pages = new LinkedList<>();
        recalculateDimensions();
        int maxRowsPerPage = (int) (((pageSize().getHeight() - (this.margin * 2.0f)) + this.lineHeight) / this.lineHeight);
        Deque<ToCItem> items = new LinkedList<>(this.items);
        if (shouldGenerateToC()) {
            while (!items.isEmpty()) {
                int row = 0;
                float separatorWidth = stringLength(SEPARATOR);
                float separatingLineEndingX = getSeparatingLineEndingX(separatorWidth);
                PDPage page = createPage(pages);
                try {
                    PDPageContentStream stream = new PDPageContentStream(this.document, page);
                    while (!items.isEmpty() && row < maxRowsPerPage) {
                        try {
                            ToCItem i = items.peek();
                            if (Objects.nonNull(i)) {
                                float y = (pageSize().getHeight() - this.margin) - (row * this.lineHeight);
                                float x = this.margin;
                                List<String> lines = multipleLinesIfRequired(i.text, separatingLineEndingX, separatorWidth);
                                if (row + lines.size() > maxRowsPerPage) {
                                    row = maxRowsPerPage;
                                } else {
                                    items.poll();
                                    for (int j = 0; j < lines.size(); j++) {
                                        String line = lines.get(j);
                                        writeText(page, line, x, y);
                                        if (j < lines.size() - 1) {
                                            row++;
                                            y = (pageSize().getHeight() - this.margin) - (row * this.lineHeight);
                                        }
                                    }
                                    long pageNumber = i.page + tocNumberOfPages;
                                    String pageString = "  " + pageNumber;
                                    float x2 = getPageNumberX(separatorWidth);
                                    writeText(page, pageString, x2, y);
                                    float spacing = (this.lineHeight - this.fontSize) / 2.0f;
                                    float height = (this.lineHeight * lines.size()) - (2.0f * spacing);
                                    i.annotation.setRectangle(new PDRectangle(this.margin, y - spacing, pageSize().getWidth() - (2.0f * this.margin), height));
                                    page.getAnnotations().add(i.annotation);
                                    String lastLine = lines.get(lines.size() - 1);
                                    stream.moveTo(this.margin + separatorWidth + stringLength(lastLine), y);
                                    stream.lineTo(separatingLineEndingX, y);
                                    stream.setLineWidth(0.5f);
                                    stream.stroke();
                                }
                            }
                            row++;
                        } finally {
                        }
                    }
                    stream.close();
                } catch (IOException e) {
                    throw new TaskIOException("An error occurred while create the ToC", e);
                }
            }
            if (this.params.isBlankPageIfOdd() && pages.size() % 2 == 1) {
                PDPage lastTocPage = pages.getLast();
                PDPage blankPage = new PDPage(lastTocPage.getMediaBox());
                pages.add(blankPage);
            }
        }
        return pages;
    }

    private void requireNotAlreadyGenerated() {
        if (this.generatedToC != null) {
            throw new IllegalStateException("ToC has already been generated");
        }
    }

    private void writeText(PDPage page, String s, float x, float y) throws TaskIOException, IOException {
        this.writer.write(page, (Point2D) new Point2D.Float(x, y), s, this.font, Double.valueOf(this.fontSize), Color.BLACK);
    }

    private List<String> multipleLinesIfRequired(String text, float separatingLineEndingX, float separatorWidth) throws TaskIOException {
        float maxWidth = ((pageSize().getWidth() - this.margin) - (pageSize().getWidth() - separatingLineEndingX)) - separatorWidth;
        return FontUtils.wrapLines(text, this.font, this.fontSize, maxWidth, this.document);
    }

    private PDPage createPage(LinkedList<PDPage> pages) {
        LOG.debug("Creating new ToC page");
        PDPage page = new PDPage(pageSize());
        pages.add(page);
        return page;
    }

    private float getSeparatingLineEndingX(float separatorWidth) throws TaskIOException {
        return getPageNumberX(separatorWidth);
    }

    private float getPageNumberX(float separatorWidth) throws TaskIOException {
        return ((pageSize().getWidth() - this.margin) - separatorWidth) - stringLength(Long.toString(9999L));
    }

    private float stringLength(String text) throws TaskIOException {
        return this.writer.getStringWidth(text, this.font, this.fontSize);
    }

    public boolean hasToc() {
        return !this.items.isEmpty();
    }

    public boolean shouldGenerateToC() {
        return this.params.getTableOfContentsPolicy() != ToCPolicy.NONE;
    }

    public void pageSizeIfNotSet(PDRectangle pageSize) {
        requireNotAlreadyGenerated();
        if (this.pageSize == null) {
            this.pageSize = pageSize;
        }
    }

    private void recalculateDimensions() {
        float scalingFactor = pageSize().getHeight() / PDRectangle.A4.getHeight();
        this.fontSize = scalingFactor * 14.0f;
        this.margin = scalingFactor * 72.0f;
        this.lineHeight = (float) (this.fontSize + (this.fontSize * 0.7d));
    }

    private PDRectangle pageSize() {
        return (PDRectangle) Optional.ofNullable(this.pageSize).orElse(PDRectangle.A4);
    }

    public float getFontSize() {
        return this.fontSize;
    }

    PDDocument getDoc() {
        return this.document;
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem.class */
    private static final class ToCItem extends Record {
        private final String text;
        private final long page;
        private final PDAnnotation annotation;

        private ToCItem(String text, long page, PDAnnotation annotation) {
            this.text = text;
            this.page = page;
            this.annotation = annotation;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ToCItem.class), ToCItem.class, "text;page;annotation", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->text:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->page:J", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->annotation:Lorg/sejda/sambox/pdmodel/interactive/annotation/PDAnnotation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ToCItem.class), ToCItem.class, "text;page;annotation", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->text:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->page:J", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->annotation:Lorg/sejda/sambox/pdmodel/interactive/annotation/PDAnnotation;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ToCItem.class, Object.class), ToCItem.class, "text;page;annotation", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->text:Ljava/lang/String;", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->page:J", "FIELD:Lorg/sejda/impl/sambox/component/TableOfContentsCreator$ToCItem;->annotation:Lorg/sejda/sambox/pdmodel/interactive/annotation/PDAnnotation;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public String text() {
            return this.text;
        }

        public long page() {
            return this.page;
        }

        public PDAnnotation annotation() {
            return this.annotation;
        }
    }
}

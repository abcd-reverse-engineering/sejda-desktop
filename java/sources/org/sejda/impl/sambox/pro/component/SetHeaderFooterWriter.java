package org.sejda.impl.sambox.pro.component;

import java.io.Closeable;
import java.util.Iterator;
import java.util.SortedSet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.impl.sambox.component.PDDocumentHandler;
import org.sejda.impl.sambox.component.PageTextWriter;
import org.sejda.impl.sambox.util.FontUtils;
import org.sejda.model.HorizontalAlign;
import org.sejda.model.VerticalAlign;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.pro.parameter.SetHeaderFooterParameters;
import org.sejda.model.pro.pdf.TextStampPattern;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PageNotFoundException;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/SetHeaderFooterWriter.class */
public class SetHeaderFooterWriter implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(SetHeaderFooterWriter.class);
    private PDDocumentHandler documentHandler;
    private PageTextWriter headerFooterWriter;
    private int totalPages;

    public SetHeaderFooterWriter(PDDocumentHandler documentHandler) {
        this.documentHandler = documentHandler;
        this.headerFooterWriter = new PageTextWriter(documentHandler.getUnderlyingPDDocument());
        this.totalPages = documentHandler.getNumberOfPages();
    }

    public void write(String pattern, SetHeaderFooterParameters parameters, int currentFileCounter, String filename, TaskExecutionContext executionContext) throws TaskIOException, TaskExecutionException {
        PDFont font = (PDFont) ObjectUtils.defaultIfNull(FontUtils.getStandardType1Font(parameters.getFont()), PDType1Font.HELVETICA());
        Double fontSize = (Double) ObjectUtils.defaultIfNull(Double.valueOf(parameters.getFontSize()), Double.valueOf(10.0d));
        HorizontalAlign hAlign = (HorizontalAlign) ObjectUtils.defaultIfNull(parameters.getHorizontalAlign(), HorizontalAlign.CENTER);
        VerticalAlign vAlign = (VerticalAlign) ObjectUtils.defaultIfNull(parameters.getVerticalAlign(), VerticalAlign.BOTTOM);
        SortedSet<Integer> pages = parameters.getPages(this.totalPages);
        int userDefinedPageOffset = 0;
        if (parameters.getPageCountStartFrom() != null && !pages.isEmpty()) {
            userDefinedPageOffset = parameters.getPageCountStartFrom().intValue() - pages.first().intValue();
        }
        Iterator<Integer> it = pages.iterator();
        while (it.hasNext()) {
            int pageNumber = it.next().intValue();
            int toLabelPageNumber = pageNumber + userDefinedPageOffset;
            String batesSeq = null;
            if (parameters.getBatesSequence() != null) {
                batesSeq = parameters.getBatesSequence().next();
            }
            String label = new TextStampPattern().withPage(toLabelPageNumber, this.totalPages).withBatesSequence(batesSeq).withFileSequence(String.valueOf(currentFileCounter)).withFilename(filename).build(pattern);
            Logger logger = LOG;
            Object[] objArr = new Object[3];
            objArr[0] = vAlign == VerticalAlign.BOTTOM ? "footer" : "header";
            objArr[1] = label;
            objArr[2] = Integer.valueOf(pageNumber);
            logger.debug("Applying {} '{}' to document page {}", objArr);
            try {
                this.headerFooterWriter.write(this.documentHandler.getPage(pageNumber), hAlign, vAlign, label, font, fontSize, parameters.getColor());
            } catch (PageNotFoundException e) {
                executionContext.assertTaskIsLenient(e);
                ApplicationEventsNotifier.notifyEvent(executionContext.notifiableTaskMetadata()).taskWarning(String.format("Page %d was skipped, could not be processed", Integer.valueOf(pageNumber)), e);
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        IOUtils.closeQuietly(this.documentHandler);
    }
}

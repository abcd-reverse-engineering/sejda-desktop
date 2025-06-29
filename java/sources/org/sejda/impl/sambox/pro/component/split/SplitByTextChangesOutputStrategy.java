package org.sejda.impl.sambox.pro.component.split;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.sejda.commons.util.StringUtils;
import org.sejda.core.Sejda;
import org.sejda.core.support.util.RuntimeUtils;
import org.sejda.impl.sambox.component.PdfTextExtractorByArea;
import org.sejda.impl.sambox.component.ReloadablePDDocumentHandler;
import org.sejda.model.TopLeftRectangularBox;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.split.NextOutputStrategy;
import org.sejda.model.split.SplitPages;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/split/SplitByTextChangesOutputStrategy.class */
public class SplitByTextChangesOutputStrategy implements NextOutputStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(SplitByTextChangesOutputStrategy.class);
    private SplitPages delegate;
    private Collection<Integer> pages;
    private Map<Integer, String> textByPage = new HashMap();
    private ReloadablePDDocumentHandler documentHandler;

    public SplitByTextChangesOutputStrategy(ReloadablePDDocumentHandler documentHandler, TopLeftRectangularBox area, String startsWith, String endsWith) throws TaskIOException {
        this.documentHandler = documentHandler;
        this.pages = findPageToSplitAt(area, startsWith, endsWith);
        this.delegate = new SplitPages(this.pages);
    }

    Collection<Integer> findPageToSplitAt(TopLeftRectangularBox area, String startsWith, String endsWith) throws TaskIOException {
        int percentageMemoryUsed;
        Collection<Integer> pagesToSplitAt = new HashSet<>();
        String prevPageText = null;
        for (int pageNumber = 1; pageNumber <= this.documentHandler.getDocumentHandler().getNumberOfPages(); pageNumber++) {
            PDPage page = this.documentHandler.getDocumentHandler().getPage(pageNumber);
            String pageText = StringUtils.normalizeWhitespace(extractTextFromPageArea(page, area)).trim();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(startsWith)) {
                if (!pageText.startsWith(startsWith)) {
                    LOG.debug("Detected page text does not match specified prefix: '{}' on '{}'", startsWith, pageText);
                    pageText = "";
                } else {
                    pageText = pageText.substring(startsWith.length()).trim();
                }
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(endsWith)) {
                if (!pageText.endsWith(endsWith)) {
                    LOG.debug("Detected page text does not match specified suffix: '{}' on '{}'", endsWith, pageText);
                    pageText = "";
                } else {
                    pageText = pageText.substring(0, pageText.length() - endsWith.length()).trim();
                }
            }
            boolean noChanges = prevPageText == null || org.apache.commons.lang3.StringUtils.isBlank(pageText) || prevPageText.equals(pageText);
            boolean someChanges = !noChanges;
            if (someChanges) {
                LOG.debug("Text changed from: '{}' to: '{}' on page: {} in area: {}", new Object[]{prevPageText, pageText, Integer.valueOf(pageNumber), area});
                pagesToSplitAt.add(Integer.valueOf(pageNumber - 1));
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(pageText)) {
                prevPageText = pageText;
            }
            this.textByPage.put(Integer.valueOf(pageNumber), pageText);
            if (Boolean.getBoolean(Sejda.PERFORM_MEMORY_OPTIMIZATIONS_PROPERTY_NAME) && (percentageMemoryUsed = RuntimeUtils.getPercentageMemoryUsed()) > 60) {
                LOG.debug("Closing and reopening source doc, memory usage reached: {}%", Integer.valueOf(percentageMemoryUsed));
                this.documentHandler.reload();
            }
        }
        return pagesToSplitAt;
    }

    String extractTextFromPageArea(PDPage page, TopLeftRectangularBox area) throws TaskIOException {
        String text = new PdfTextExtractorByArea().extractTextFromArea(page, area.asRectangle());
        String result = (String) org.apache.commons.lang3.StringUtils.defaultIfBlank(text, "");
        return org.apache.commons.lang3.StringUtils.strip(result);
    }

    Collection<Integer> getPages() {
        return this.pages;
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public void ensureIsValid() throws TaskExecutionException {
        try {
            this.delegate.ensureIsValid();
        } catch (TaskExecutionException tee) {
            boolean noTextFound = org.apache.commons.lang3.StringUtils.isBlank(getTextByPage(1));
            if (noTextFound) {
                throw tee;
            }
        }
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public boolean isOpening(Integer page) {
        return this.delegate.isOpening(page);
    }

    @Override // org.sejda.model.split.NextOutputStrategy
    public boolean isClosing(Integer page) {
        return this.delegate.isClosing(page);
    }

    public String getTextByPage(int page) {
        return this.textByPage.get(Integer.valueOf(page));
    }
}

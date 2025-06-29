package org.sejda.impl.sambox.component;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.outline.OutlineExtractPageDestinations;
import org.sejda.model.outline.OutlineLevelsHandler;
import org.sejda.model.outline.OutlinePageDestinations;
import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/SamboxOutlineLevelsHandler.class */
public class SamboxOutlineLevelsHandler implements OutlineLevelsHandler {
    private Pattern titleMatchingPattern;
    private PDDocument document;

    public SamboxOutlineLevelsHandler(PDDocument document, String matchingTitleRegEx) {
        this.titleMatchingPattern = Pattern.compile(".+", 32);
        Objects.requireNonNull(document, "Unable to retrieve bookmarks from a null document.");
        this.document = document;
        if (StringUtils.isNotBlank(matchingTitleRegEx)) {
            this.titleMatchingPattern = Pattern.compile(matchingTitleRegEx, 32);
        }
    }

    @Override // org.sejda.model.outline.OutlineLevelsHandler
    public OutlinePageDestinations getPageDestinationsForLevel(int level) {
        OutlinePageDestinations destinations = new OutlinePageDestinations();
        OutlineUtils.getFlatOutline(this.document).stream().filter(i -> {
            return i.level == level;
        }).filter(i2 -> {
            return StringUtils.isNotBlank(i2.title);
        }).filter(i3 -> {
            return this.titleMatchingPattern.matcher(i3.title).matches();
        }).forEach(i4 -> {
            destinations.addPage(Integer.valueOf(i4.page), i4.title);
        });
        return destinations;
    }

    @Override // org.sejda.model.outline.OutlineLevelsHandler
    public OutlineExtractPageDestinations getExtractPageDestinations(int level, boolean includePageAfter) {
        OutlineExtractPageDestinations destinations = new OutlineExtractPageDestinations();
        List<OutlineItem> flatOutline = OutlineUtils.getFlatOutline(this.document);
        for (int i = 0; i < flatOutline.size(); i++) {
            OutlineItem item = flatOutline.get(i);
            if (item.level == level) {
                int startPage = item.page;
                String title = item.title;
                if (StringUtils.isNotBlank(title) && this.titleMatchingPattern.matcher(title).matches()) {
                    int endPage = this.document.getNumberOfPages();
                    int j = i + 1;
                    while (true) {
                        if (j >= flatOutline.size()) {
                            break;
                        }
                        OutlineItem after = flatOutline.get(j);
                        if (after.level > item.level) {
                            j++;
                        } else {
                            endPage = includePageAfter ? after.page : after.page - 1;
                            if (endPage < startPage) {
                                endPage = startPage;
                            }
                        }
                    }
                    destinations.add(startPage, title, endPage);
                }
            }
        }
        return destinations;
    }
}

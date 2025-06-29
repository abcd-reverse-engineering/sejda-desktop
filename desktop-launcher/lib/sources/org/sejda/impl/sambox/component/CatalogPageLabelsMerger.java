package org.sejda.impl.sambox.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.sejda.impl.sambox.util.PageLabelUtils;
import org.sejda.model.outline.CatalogPageLabelsPolicy;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.common.PDPageLabelRange;
import org.sejda.sambox.pdmodel.common.PDPageLabels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/CatalogPageLabelsMerger.class */
public class CatalogPageLabelsMerger {
    private static final Logger LOG = LoggerFactory.getLogger(CatalogPageLabelsMerger.class);
    private int totalPages = 0;
    private PDPageLabels mergedPageLabels;
    private final CatalogPageLabelsPolicy policy;

    public CatalogPageLabelsMerger(CatalogPageLabelsPolicy policy) {
        this.mergedPageLabels = new PDPageLabels();
        this.policy = policy;
        if (policy == CatalogPageLabelsPolicy.DISCARD) {
            this.mergedPageLabels = null;
        }
    }

    public void add(PDDocument doc, Set<Integer> pagesToImport) {
        try {
            if (this.policy == CatalogPageLabelsPolicy.DISCARD) {
                return;
            }
            try {
                PDPageLabels docLabels = doc.getDocumentCatalog().getPageLabels();
                if (docLabels == null) {
                    docLabels = new PDPageLabels();
                }
                if (pagesToImport.size() < doc.getNumberOfPages()) {
                    List<Integer> pagesToRemove = computePagesToRemove(doc, pagesToImport);
                    docLabels = PageLabelUtils.removePages(docLabels, pagesToRemove, doc.getNumberOfPages());
                }
                for (Map.Entry<Integer, PDPageLabelRange> entry : docLabels.getLabels().entrySet()) {
                    PDPageLabelRange range = entry.getValue();
                    int pageIndex = entry.getKey().intValue();
                    int newPageIndex = pageIndex + this.totalPages;
                    if (range.hasStart()) {
                        range = new PDPageLabelRange(range.getStyle(), range.getPrefix(), null);
                    }
                    this.mergedPageLabels.setLabelItem(newPageIndex, range);
                }
                this.totalPages += pagesToImport.size();
            } catch (Exception e) {
                LOG.warn("An error occurred retrieving /PageLabels of document {}, will not be merged", doc);
                this.totalPages += pagesToImport.size();
            }
        } catch (Throwable th) {
            this.totalPages += pagesToImport.size();
            throw th;
        }
    }

    private static List<Integer> computePagesToRemove(PDDocument doc, Set<Integer> pagesToImport) {
        if (doc.getNumberOfPages() == pagesToImport.size()) {
            return new ArrayList();
        }
        List<Integer> pagesToRemove = new ArrayList<>();
        for (int i = 1; i <= doc.getNumberOfPages(); i++) {
            if (!pagesToImport.contains(Integer.valueOf(i))) {
                pagesToRemove.add(Integer.valueOf(i));
            }
        }
        return pagesToRemove;
    }

    public boolean hasPageLabels() {
        return this.mergedPageLabels != null;
    }

    public PDPageLabels getMergedPageLabels() {
        return this.mergedPageLabels;
    }
}

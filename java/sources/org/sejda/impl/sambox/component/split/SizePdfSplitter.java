package org.sejda.impl.sambox.component.split;

import java.io.IOException;
import java.util.function.Supplier;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.PagesExtractor;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.exception.TaskIOException;
import org.sejda.model.parameter.SplitBySizeParameters;
import org.sejda.model.split.NextOutputStrategy;
import org.sejda.sambox.output.ExistingPagesSizePredictor;
import org.sejda.sambox.output.WriteOption;
import org.sejda.sambox.pdmodel.PDDocument;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/SizePdfSplitter.class */
public class SizePdfSplitter extends AbstractPdfSplitter<SplitBySizeParameters> {
    private static final WriteOption[] COMPRESSED_OPTS = {WriteOption.COMPRESS_STREAMS, WriteOption.XREF_STREAM};
    private static final int PDF_HEADER_SIZE = 15;
    private static final int ID_VALUE_SIZE = 70;
    private static final int PAGE_OVERHEAD = 10;
    private OutputSizeStrategy nextOutputStrategy;

    public SizePdfSplitter(PDDocument document, SplitBySizeParameters parameters, boolean optimize) {
        super(document, parameters, optimize, parameters.discardOutline());
        this.nextOutputStrategy = new OutputSizeStrategy(document, parameters, optimize);
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    public NameGenerationRequest enrichNameGenerationRequest(NameGenerationRequest request) {
        return request;
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    public NextOutputStrategy nextOutputStrategy() {
        return this.nextOutputStrategy;
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    protected void onOpen(int page) throws TaskIOException {
        this.nextOutputStrategy.newPredictor();
        this.nextOutputStrategy.addPage(page);
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    protected void onRetain(int page) throws TaskIOException {
        this.nextOutputStrategy.addPage(page + 1);
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    protected void onClose(int page) {
        this.nextOutputStrategy.closePredictor();
    }

    @Override // org.sejda.impl.sambox.component.split.AbstractPdfSplitter
    protected PagesExtractor supplyPagesExtractor(PDDocument document) {
        return new PagesExtractor(document) { // from class: org.sejda.impl.sambox.component.split.SizePdfSplitter.1
            @Override // org.sejda.impl.sambox.component.PagesExtractor
            public void setCompress(boolean compress) {
                if (compress) {
                    destinationDocument().addWriteOption(SizePdfSplitter.COMPRESSED_OPTS);
                } else {
                    destinationDocument().removeWriteOption(SizePdfSplitter.COMPRESSED_OPTS);
                }
            }
        };
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/SizePdfSplitter$OutputSizeStrategy.class */
    static class OutputSizeStrategy implements NextOutputStrategy {
        private long sizeLimit;
        private PDDocument document;
        private ExistingPagesSizePredictor predictor;
        private Supplier<ExistingPagesSizePredictor> predictorSupplier;
        private PageCopier copier;

        OutputSizeStrategy(PDDocument document, SplitBySizeParameters parameters, boolean optimize) {
            this.predictorSupplier = () -> {
                return ExistingPagesSizePredictor.instance(new WriteOption[0]);
            };
            this.sizeLimit = parameters.getSizeToSplitAt();
            this.document = document;
            this.copier = new PageCopier(optimize);
            if (parameters.isCompress()) {
                this.predictorSupplier = () -> {
                    return ExistingPagesSizePredictor.instance(WriteOption.COMPRESS_STREAMS, WriteOption.XREF_STREAM);
                };
            }
        }

        public void newPredictor() throws TaskIOException {
            try {
                this.predictor = this.predictorSupplier.get();
                this.predictor.addIndirectReferenceFor(this.document.getDocumentInformation());
                this.predictor.addIndirectReferenceFor(this.document.getDocumentCatalog().getViewerPreferences());
            } catch (IOException e) {
                throw new TaskIOException("Unable to initialize the pages size predictor", e);
            }
        }

        public void addPage(int page) throws TaskIOException {
            try {
                if (page <= this.document.getNumberOfPages()) {
                    this.predictor.addPage(this.copier.copyOf(this.document.getPage(page - 1)));
                }
            } catch (IOException e) {
                throw new TaskIOException("Unable to simulate page " + page + " addition", e);
            }
        }

        public void closePredictor() {
            IOUtils.closeQuietly(this.predictor);
            this.predictor = null;
        }

        @Override // org.sejda.model.split.NextOutputStrategy
        public void ensureIsValid() throws TaskExecutionException {
            if (this.sizeLimit < 1) {
                throw new TaskExecutionException(String.format("Unable to split at %d, a positive size is required.", Long.valueOf(this.sizeLimit)));
            }
        }

        @Override // org.sejda.model.split.NextOutputStrategy
        public boolean isOpening(Integer page) {
            return this.predictor == null || !this.predictor.hasPages();
        }

        @Override // org.sejda.model.split.NextOutputStrategy
        public boolean isClosing(Integer page) throws TaskIOException {
            try {
                long currentPageSize = this.predictor.predictedPagesSize();
                return (((85 + currentPageSize) + this.predictor.predictedXrefTableSize()) + ((long) documentFooterSize(currentPageSize))) + (this.predictor.pages() * 10) > this.sizeLimit;
            } catch (IOException e) {
                throw new TaskIOException("Unable to simulate page " + page + " addition", e);
            }
        }

        private int documentFooterSize(long documentSize) {
            return 17 + Long.toString(documentSize).length();
        }
    }
}

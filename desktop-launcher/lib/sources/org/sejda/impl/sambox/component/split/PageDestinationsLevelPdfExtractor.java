package org.sejda.impl.sambox.component.split;

import java.io.File;
import org.apache.commons.lang3.StringUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.core.support.io.MultipleOutputWriter;
import org.sejda.core.support.io.OutputWriters;
import org.sejda.core.support.io.model.FileOutput;
import org.sejda.core.support.prefix.NameGenerator;
import org.sejda.core.support.prefix.model.NameGenerationRequest;
import org.sejda.impl.sambox.component.PagesExtractor;
import org.sejda.impl.sambox.component.optimization.OptimizationRuler;
import org.sejda.model.exception.TaskException;
import org.sejda.model.exception.TaskExecutionException;
import org.sejda.model.input.PdfSource;
import org.sejda.model.outline.OutlineExtractPageDestinations;
import org.sejda.model.parameter.ExtractByOutlineParameters;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.model.util.IOUtils;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/PageDestinationsLevelPdfExtractor.class */
public class PageDestinationsLevelPdfExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(PageDestinationsLevelPdfExtractor.class);
    private final OutlineExtractPageDestinations outlineDestinations;
    private final ExtractByOutlineParameters parameters;
    private final PDDocument document;
    private MultipleOutputWriter outputWriter;
    private final PdfSource<?> source;

    public PageDestinationsLevelPdfExtractor(PDDocument document, ExtractByOutlineParameters parameters, OutlineExtractPageDestinations outlineDestinations, PdfSource<?> source) {
        this.outlineDestinations = outlineDestinations;
        this.parameters = parameters;
        this.document = document;
        this.source = source;
    }

    public void extract(TaskExecutionContext executionContext) throws TaskException {
        int outputDocumentsCounter = 0;
        this.outputWriter = OutputWriters.newMultipleOutputWriter(this.parameters.getExistingOutputPolicy(), executionContext);
        PagesExtractor extractor = new PagesExtractor(this.document);
        try {
            int totalExtractions = this.outlineDestinations.sections.size();
            if (totalExtractions == 0) {
                throw new TaskExecutionException("No page has been selected for extraction.");
            }
            boolean optimize = new OptimizationRuler(this.parameters.getOptimizationPolicy()).apply(this.document).booleanValue();
            for (int s = 0; s < totalExtractions; s++) {
                OutlineExtractPageDestinations.OutlineItemBoundaries section = this.outlineDestinations.sections.get(s);
                int page = section.startPage;
                LOG.debug("Starting extracting {} pages {} {}", new Object[]{section.title, Integer.valueOf(section.startPage), Integer.valueOf(section.endPage)});
                outputDocumentsCounter++;
                File tmpFile = IOUtils.createTemporaryBuffer(this.parameters.getOutput());
                LOG.debug("Created output temporary buffer {}", tmpFile);
                String outName = this.parameters.getSpecificResultFilename(executionContext.incrementAndGetOutputDocumentsCounter());
                if (StringUtils.isBlank(outName)) {
                    outName = NameGenerator.nameGenerator(this.parameters.getOutputPrefix()).generate(NameGenerationRequest.nameRequest().page(page).originalName(this.source.getName()).fileNumber(outputDocumentsCounter).bookmark(section.title));
                }
                this.outputWriter.addOutput(FileOutput.file(tmpFile).name(outName));
                while (page <= section.endPage) {
                    LOG.trace("Retaining page {} of the original document", Integer.valueOf(page));
                    extractor.retain(page, executionContext);
                    page++;
                }
                extractor.setVersion(this.parameters.getVersion());
                extractor.setCompress(this.parameters.isCompress());
                if (optimize) {
                    extractor.optimize();
                }
                extractor.save(tmpFile, this.parameters.discardOutline(), this.parameters.getOutput().getEncryptionAtRestPolicy());
                extractor.reset();
                LOG.debug("Ending extracting {}", section.title);
                ApplicationEventsNotifier.notifyEvent(executionContext.notifiableTaskMetadata()).stepsCompleted(s).outOf(totalExtractions);
            }
            extractor.close();
            this.parameters.getOutput().accept(this.outputWriter);
        } catch (Throwable th) {
            try {
                extractor.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}

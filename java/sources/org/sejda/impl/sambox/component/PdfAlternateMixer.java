package org.sejda.impl.sambox.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.sejda.commons.util.IOUtils;
import org.sejda.core.notification.dsl.ApplicationEventsNotifier;
import org.sejda.model.exception.TaskException;
import org.sejda.model.input.PdfMixInput;
import org.sejda.model.task.TaskExecutionContext;
import org.sejda.sambox.pdmodel.PDPage;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/PdfAlternateMixer.class */
public class PdfAlternateMixer extends PDDocumentHandler {
    private List<PdfMixFragment> mixFragments = new ArrayList();

    public void mix(List<PdfMixInput> inputs, TaskExecutionContext executionContext) throws TaskException {
        setCreatorOnPDDocument();
        for (PdfMixInput input : inputs) {
            executionContext.notifiableTaskMetadata().setCurrentSource(input.getSource());
            this.mixFragments.add(PdfMixFragment.newInstance(input, executionContext));
        }
        int maxNumberOfPages = 0;
        for (PdfMixFragment fragment : this.mixFragments) {
            maxNumberOfPages = Math.max(fragment.getNumberOfPages(), maxNumberOfPages);
        }
        int currentStep = 0;
        int maxSteps = (this.mixFragments.size() * maxNumberOfPages) + 1;
        ApplicationEventsNotifier.notifyEvent(executionContext.notifiableTaskMetadata()).progressUndetermined();
        while (this.mixFragments.stream().anyMatch((v0) -> {
            return v0.hasNotReachedTheEnd();
        })) {
            this.mixFragments.stream().filter((v0) -> {
                return v0.hasNextPage();
            }).forEach(f -> {
                for (int i = 0; i < f.getStep() && f.hasNextPage(); i++) {
                    executionContext.notifiableTaskMetadata().setCurrentSource(f.source());
                    PDPage current = f.nextPage();
                    f.addLookupEntry(current, importPage(current));
                }
            });
            currentStep++;
            if (currentStep > maxSteps) {
                throw new RuntimeException("Too many loops, currentStep: " + currentStep + ", maxSteps: " + maxSteps);
            }
        }
        this.mixFragments.forEach(f2 -> {
            executionContext.notifiableTaskMetadata().setCurrentSource(f2.source());
            f2.saintizeAnnotations();
        });
        executionContext.notifiableTaskMetadata().clearCurrentSource();
    }

    @Override // org.sejda.impl.sambox.component.PDDocumentHandler, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        super.close();
        this.mixFragments.forEach((v0) -> {
            IOUtils.closeQuietly(v0);
        });
        this.mixFragments.clear();
    }
}

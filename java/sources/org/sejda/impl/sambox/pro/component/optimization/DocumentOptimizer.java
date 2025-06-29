package org.sejda.impl.sambox.pro.component.optimization;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import org.sejda.model.pro.optimization.Optimization;
import org.sejda.sambox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/DocumentOptimizer.class */
public class DocumentOptimizer implements Consumer<PDDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentOptimizer.class);
    private Consumer<PDDocument> optimizer = d -> {
        LOG.debug("Optimizing document");
    };

    public DocumentOptimizer(Set<Optimization> optimizations) {
        ((Set) Optional.ofNullable(optimizations).orElse(Collections.emptySet())).forEach(o -> {
            Optional.ofNullable(Optimizers.documentOptimizer(o)).ifPresent(toAdd -> {
                this.optimizer = this.optimizer.andThen(toAdd);
            });
        });
    }

    @Override // java.util.function.Consumer
    public void accept(PDDocument d) {
        this.optimizer.accept(d);
    }
}

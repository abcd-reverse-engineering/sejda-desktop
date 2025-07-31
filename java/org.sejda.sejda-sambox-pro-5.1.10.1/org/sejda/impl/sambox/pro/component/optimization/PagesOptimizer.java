package org.sejda.impl.sambox.pro.component.optimization;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import org.sejda.impl.sambox.component.optimization.ResourcesHitter;
import org.sejda.impl.sambox.pro.component.optimization.font.Restore;
import org.sejda.impl.sambox.pro.component.optimization.font.Save;
import org.sejda.impl.sambox.pro.component.optimization.font.SetFontAndSize;
import org.sejda.impl.sambox.pro.component.optimization.font.SetGraphicStateParameters;
import org.sejda.impl.sambox.pro.component.optimization.font.ShowText;
import org.sejda.impl.sambox.pro.component.optimization.font.ShowTextAdjusted;
import org.sejda.impl.sambox.pro.component.optimization.images.XObjectsOptimizerOperator;
import org.sejda.model.pro.optimization.Optimization;
import org.sejda.model.pro.parameter.OptimizeParameters;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.text.ShowTextLine;
import org.sejda.sambox.contentstream.operator.text.ShowTextLineAndSpace;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/PagesOptimizer.class */
public class PagesOptimizer implements Consumer<PDPage> {
    private static final Logger LOG = LoggerFactory.getLogger(PagesOptimizer.class);
    public final ContentStreamOptimizationContext optimizationContext;
    private Consumer<PDPage> optimizer;

    public PagesOptimizer(OptimizeParameters parameters) {
        this.optimizer = p -> {
            LOG.trace("Optimizing page");
        };
        this.optimizationContext = new ContentStreamOptimizationContext(parameters);
        Set<Optimization> optimizations = (Set) Optional.ofNullable(parameters).map((v0) -> {
            return v0.getOptimizations();
        }).orElse(Collections.emptySet());
        optimizations.forEach(o -> {
            Optional.ofNullable(Optimizers.pageOptimizer(o)).ifPresent(toAdd -> {
                this.optimizer = this.optimizer.andThen(toAdd);
            });
        });
        if (optimizations.stream().anyMatch(PagesOptimizer::contentStreamOptimization)) {
            ContentStreamOptimizer csOptimizer = new ContentStreamOptimizer(this.optimizationContext);
            if (optimizations.stream().anyMatch(PagesOptimizer::xobjectsOptimization)) {
                csOptimizer.addOptimizationOperator(new XObjectsOptimizerOperator());
            }
            csOptimizer.addOperatorIfAbsent(new ResourcesHitter.XObjectHitterOperator());
            if (optimizations.contains(Optimization.SUBSET_FONTS)) {
                csOptimizer.addOperator(new SetGraphicStateParameters(this.optimizationContext));
                csOptimizer.addOperator(new Save(this.optimizationContext));
                csOptimizer.addOperator(new Restore(this.optimizationContext));
                csOptimizer.addOperator(new SetFontAndSize(this.optimizationContext));
                csOptimizer.addOptimizationOperator(new ShowText());
                csOptimizer.addOptimizationOperator(new ShowTextAdjusted());
                csOptimizer.addOperator(new ShowTextLine());
                csOptimizer.addOperator(new ShowTextLineAndSpace());
            }
            csOptimizer.addOperatorIfAbsent(new ResourcesHitter.FontsHitterOperator());
            tilingPatternOperators(csOptimizer);
            this.optimizer = this.optimizer.andThen(csOptimizer);
        }
    }

    private void tilingPatternOperators(ContentStreamOptimizer csOptimizer) {
        csOptimizer.addOperatorIfAbsent(new SetNonStrokingColorSpace());
        csOptimizer.addOperatorIfAbsent(new SetStrokingColorSpace());
        csOptimizer.addOperatorIfAbsent(new ResourcesHitter.TilingPatternHitterSetStrokingColor("SCN"));
        csOptimizer.addOperatorIfAbsent(new ResourcesHitter.TilingPatternHitterSetNonStrokingColor("scn"));
    }

    @Override // java.util.function.Consumer
    public void accept(PDPage d) {
        this.optimizer.accept(d);
    }

    public static boolean xobjectsOptimization(Optimization o) {
        return o == Optimization.COMPRESS_IMAGES || o == Optimization.DISCARD_ALTERNATE_IMAGES || o == Optimization.DISCARD_PIECE_INFO || o == Optimization.DISCARD_METADATA || o == Optimization.GRAYSCALE_IMAGES;
    }

    public static boolean contentStreamOptimization(Optimization o) {
        return o == Optimization.DISCARD_UNUSED_RESOURCES || xobjectsOptimization(o);
    }
}

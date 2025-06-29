package org.sejda.impl.sambox.component.optimization;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import org.sejda.commons.util.RequireUtils;
import org.sejda.model.optimization.OptimizationPolicy;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/OptimizationRuler.class */
public class OptimizationRuler implements Function<PDDocument, Boolean> {
    private static final Logger LOG = LoggerFactory.getLogger(OptimizationRuler.class);
    private OptimizationPolicy policy;

    public OptimizationRuler(OptimizationPolicy policy) {
        RequireUtils.requireNotNullArg(policy, "Optimization policy cannot be null");
        this.policy = policy;
    }

    @Override // java.util.function.Function
    public Boolean apply(PDDocument document) {
        if (this.policy == OptimizationPolicy.YES) {
            return true;
        }
        if (this.policy == OptimizationPolicy.AUTO) {
            return Boolean.valueOf(willNeedOptimization(document));
        }
        return false;
    }

    private boolean willNeedOptimization(PDDocument document) {
        return hasSharedNameDictionaries(document) || hasInheritedResources(document);
    }

    private boolean hasInheritedResources(PDDocument document) {
        List<COSDictionary> resources = document.getPages().streamNodes().filter(PDPageTree::isPageTreeNode).map(d -> {
            return (COSDictionary) d.getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).distinct().toList();
        Stream streamFilter = resources.stream().map(d2 -> {
            return (COSDictionary) d2.getDictionaryObject(COSName.XOBJECT, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).flatMap(d3 -> {
            return d3.getValues().stream();
        }).map((v0) -> {
            return v0.getCOSObject();
        }).filter(d4 -> {
            return d4 instanceof COSDictionary;
        }).map(d5 -> {
            return (COSDictionary) d5;
        }).map(d6 -> {
            return d6.getNameAsString(COSName.SUBTYPE);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        });
        String name = COSName.IMAGE.getName();
        Objects.requireNonNull(name);
        long inheritedImage = streamFilter.filter((v1) -> {
            return r1.equals(v1);
        }).count();
        long inheritedFonts = resources.stream().map(d7 -> {
            return (COSDictionary) d7.getDictionaryObject(COSName.FONT, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).flatMap(d8 -> {
            return d8.getValues().stream();
        }).map((v0) -> {
            return v0.getCOSObject();
        }).filter(d9 -> {
            return d9 instanceof COSDictionary;
        }).count();
        long inheritedExtGState = resources.stream().map(d10 -> {
            return (COSDictionary) d10.getDictionaryObject(COSName.EXT_G_STATE, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).flatMap(d11 -> {
            return d11.getValues().stream();
        }).map((v0) -> {
            return v0.getCOSObject();
        }).filter(d12 -> {
            return d12 instanceof COSDictionary;
        }).count();
        LOG.debug("Found {} inherited images, {} inherited fonts and {} inherited graphic states potentially unused", new Object[]{Long.valueOf(inheritedImage), Long.valueOf(inheritedFonts), Long.valueOf(inheritedExtGState)});
        return (inheritedImage + inheritedFonts) + inheritedExtGState > 0;
    }

    private boolean hasSharedNameDictionaries(PDDocument document) {
        List<COSDictionary> optimizableDictionaries = document.getPages().stream().map((v0) -> {
            return v0.getCOSObject();
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).map(d -> {
            return (COSDictionary) d.getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).flatMap(d2 -> {
            return Stream.of((Object[]) new COSDictionary[]{(COSDictionary) d2.getDictionaryObject(COSName.EXT_G_STATE, COSDictionary.class), (COSDictionary) d2.getDictionaryObject(COSName.XOBJECT, COSDictionary.class), (COSDictionary) d2.getDictionaryObject(COSName.FONT, COSDictionary.class)});
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).filter(x -> {
            return x.size() > 0;
        }).toList();
        long distinctFontDictionaries = optimizableDictionaries.stream().distinct().count();
        if (optimizableDictionaries.size() > distinctFontDictionaries) {
            LOG.debug("Found shared named dictionaries");
            return true;
        }
        return false;
    }
}

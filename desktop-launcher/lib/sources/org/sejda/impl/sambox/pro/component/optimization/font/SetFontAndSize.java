package org.sejda.impl.sambox.pro.component.optimization.font;

import java.util.Optional;
import org.sejda.impl.sambox.component.optimization.ResourcesHitter;
import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationContext;
import org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.pdmodel.MissingResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/SetFontAndSize.class */
public class SetFontAndSize extends OperatorProcessorDecorator {
    private static final Logger LOG = LoggerFactory.getLogger(SetFontAndSize.class);

    public SetFontAndSize(ContentStreamOptimizationContext optimizationContext) {
        super(new ResourcesHitter.FontsHitterOperator());
        setConsumer((operator, operands) -> {
            COSBase operand = (COSBase) operands.get(0);
            if (operand instanceof COSName) {
                COSName fontName = (COSName) operand;
                Optional<COSDictionary> fonts = Optional.ofNullable(getContext().getResources()).map(r -> {
                    return (COSDictionary) r.getCOSObject().getDictionaryObject(COSName.FONT, COSDictionary.class);
                });
                COSDictionary fontDictionary = (COSDictionary) fonts.map(d -> {
                    return (COSDictionary) d.getDictionaryObject(fontName, COSDictionary.class);
                }).orElseThrow(() -> {
                    return new MissingResourceException("Font resource '" + fontName.getName() + "' missing or unexpected type");
                });
                LOG.trace("Setting current font '{}'", fontName);
                optimizationContext.fontSubsettingContext().maybeSetCurrentSubsettableFont(fontDictionary, subFont -> {
                    ((COSDictionary) fonts.get()).setItem(fontName, (COSBase) subFont);
                });
            }
        });
    }
}

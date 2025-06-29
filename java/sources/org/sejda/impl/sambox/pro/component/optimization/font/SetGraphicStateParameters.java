package org.sejda.impl.sambox.pro.component.optimization.font;

import java.util.Objects;
import java.util.Optional;
import org.sejda.impl.sambox.component.optimization.ResourcesHitter;
import org.sejda.impl.sambox.pro.component.optimization.ContentStreamOptimizationContext;
import org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/optimization/font/SetGraphicStateParameters.class */
public class SetGraphicStateParameters extends OperatorProcessorDecorator {
    private static final Logger LOG = LoggerFactory.getLogger(SetGraphicStateParameters.class);

    public SetGraphicStateParameters(ContentStreamOptimizationContext optimizationContext) {
        super(new ResourcesHitter.SetGraphicState());
        setConsumer((operator, operands) -> {
            Object patt1501$temp = operands.get(0);
            if (patt1501$temp instanceof COSName) {
                COSName gsName = (COSName) patt1501$temp;
                Optional<COSDictionary> states = Optional.ofNullable(getContext().getResources()).map(r -> {
                    return (COSDictionary) r.getCOSObject().getDictionaryObject(COSName.EXT_G_STATE, COSDictionary.class);
                });
                COSDictionary gsDictionary = (COSDictionary) states.map(d -> {
                    return (COSDictionary) d.getDictionaryObject(gsName, COSDictionary.class);
                }).orElseGet(() -> {
                    LOG.warn("Graphic state resource '{}' missing or unexpected type", gsName.getName());
                    return null;
                });
                if (Objects.nonNull(gsDictionary)) {
                    COSArray fontArray = (COSArray) gsDictionary.getDictionaryObject(COSName.FONT, COSArray.class);
                    Optional.ofNullable(fontArray).map(f -> {
                        return (COSDictionary) f.getObject(0, COSDictionary.class);
                    }).ifPresent(fontDictionary -> {
                        optimizationContext.fontSubsettingContext().maybeSetCurrentSubsettableFont(fontDictionary, subFont -> {
                            fontArray.set(0, (COSBase) subFont);
                        });
                    });
                }
            }
        });
    }
}

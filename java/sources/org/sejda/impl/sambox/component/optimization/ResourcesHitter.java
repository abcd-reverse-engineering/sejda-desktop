package org.sejda.impl.sambox.component.optimization;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.sejda.commons.util.RequireUtils;
import org.sejda.impl.sambox.component.ContentStreamProcessor;
import org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorSpace;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.pdmodel.MissingResourceException;
import org.sejda.sambox.pdmodel.font.PDType3CharProc;
import org.sejda.sambox.pdmodel.font.PDType3Font;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDPattern;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.form.PDTransparencyGroup;
import org.sejda.sambox.pdmodel.graphics.pattern.PDTilingPattern;
import org.sejda.sambox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourcesHitter.class */
public class ResourcesHitter extends ContentStreamProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ResourcesHitter.class);

    public ResourcesHitter() {
        addOperator(new XObjectHitterOperator());
        addOperator(new FontsHitterOperator());
        addOperator(new SetGraphicState());
        addOperator(new SetNonStrokingColorSpace());
        addOperator(new SetStrokingColorSpace());
        addOperator(new TilingPatternHitterSetStrokingColor(OperatorName.STROKING_COLOR_N));
        addOperator(new TilingPatternHitterSetNonStrokingColor(OperatorName.NON_STROKING_COLOR_N));
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourcesHitter$XObjectHitterOperator.class */
    public static class XObjectHitterOperator extends OperatorProcessor {
        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public void process(Operator operator, List<COSBase> operands) throws IOException {
            if (operands.isEmpty()) {
                throw new MissingOperandException(operator, operands);
            }
            COSBase operand = operands.get(0);
            if (operand instanceof COSName) {
                COSName objectName = (COSName) operand;
                Optional<COSDictionary> xobjects = Optional.ofNullable(getContext().getResources()).map(r -> {
                    return (COSDictionary) r.getCOSObject().getDictionaryObject(COSName.XOBJECT, COSDictionary.class);
                });
                COSBase existing = (COSBase) xobjects.map(d -> {
                    return d.getDictionaryObject(objectName);
                }).orElseThrow(() -> {
                    return new MissingResourceException("Missing XObject: " + objectName.getName());
                });
                if (existing instanceof COSStream) {
                    if (!(existing instanceof ReadOnlyFilteredCOSStream)) {
                        COSStream imageStream = (COSStream) existing;
                        ResourcesHitter.LOG.trace("Hit image with name {}", objectName.getName());
                        xobjects.get().setItem(objectName, (COSBase) ReadOnlyFilteredCOSStream.readOnly(imageStream));
                        if (COSName.FORM.getName().equals(imageStream.getNameAsString(COSName.SUBTYPE))) {
                            PDXObject xobject = PDXObject.createXObject(imageStream, getContext().getResources());
                            if (xobject instanceof PDTransparencyGroup) {
                                getContext().showTransparencyGroup((PDTransparencyGroup) xobject);
                                return;
                            } else {
                                if (xobject instanceof PDFormXObject) {
                                    getContext().showForm((PDFormXObject) xobject);
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                ResourcesHitter.LOG.warn("Unexpected type {} for xObject {}", existing.getClass(), objectName.getName());
            }
        }

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public String getName() {
            return OperatorName.DRAW_OBJECT;
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourcesHitter$FontsHitterOperator.class */
    public static class FontsHitterOperator extends OperatorProcessor {
        private final Map<IndirectCOSObjectIdentifier, InUseDictionary> hitFontsById = new HashMap();

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public void process(Operator operator, List<COSBase> operands) throws IOException {
            if (operands.size() < 2) {
                throw new MissingOperandException(operator, operands);
            }
            COSBase operand = operands.get(0);
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
                if (!(fontDictionary instanceof InUseDictionary)) {
                    if (fontDictionary.hasId()) {
                        ResourcesHitter.LOG.trace("Hit font with name {} id {}", fontName.getName(), fontDictionary.id());
                        fonts.get().setItem(fontName, (COSBase) Optional.ofNullable(this.hitFontsById.get(fontDictionary.id())).orElseGet(() -> {
                            InUseDictionary font = new InUseDictionary(fontDictionary);
                            this.hitFontsById.put(fontDictionary.id(), font);
                            return font;
                        }));
                    } else {
                        ResourcesHitter.LOG.trace("Hit font with name {}", fontName.getName());
                        fonts.get().setItem(fontName, (COSBase) new InUseDictionary(fontDictionary));
                    }
                    if (COSName.TYPE3.equals(fontDictionary.getCOSName(COSName.SUBTYPE))) {
                        PDType3Font font = new PDType3Font(fontDictionary);
                        Collection<COSBase> glyphStreams = (Collection) Optional.ofNullable((COSDictionary) fontDictionary.getDictionaryObject(COSName.CHAR_PROCS, COSDictionary.class)).map((v0) -> {
                            return v0.getValues();
                        }).filter(v -> {
                            return !v.isEmpty();
                        }).orElseGet(Collections::emptyList);
                        List<PDType3CharProc> pdStreams = glyphStreams.stream().map((v0) -> {
                            return v0.getCOSObject();
                        }).filter(s -> {
                            return s instanceof COSStream;
                        }).map(s2 -> {
                            return (COSStream) s2;
                        }).map(s3 -> {
                            return new PDType3CharProc(font, s3);
                        }).toList();
                        ResourcesHitter.LOG.trace("Found type3 font {} with {} streams to parse", fontName.getName(), Integer.valueOf(pdStreams.size()));
                        for (PDType3CharProc glyph : pdStreams) {
                            getContext().processStream(glyph);
                        }
                    }
                }
            }
        }

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public String getName() {
            return OperatorName.SET_FONT_AND_SIZE;
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourcesHitter$SetGraphicState.class */
    public static class SetGraphicState extends OperatorProcessor {
        private final Map<IndirectCOSObjectIdentifier, InUseDictionary> hitGSById = new HashMap();

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public void process(Operator operator, List<COSBase> operands) throws Throwable {
            RequireUtils.require(!operands.isEmpty(), () -> {
                return new MissingOperandException(operator, operands);
            });
            COSBase operand = operands.get(0);
            if (operand instanceof COSName) {
                COSName gsName = (COSName) operand;
                Optional<COSDictionary> states = Optional.ofNullable(getContext().getResources()).map(r -> {
                    return (COSDictionary) r.getCOSObject().getDictionaryObject(COSName.EXT_G_STATE, COSDictionary.class);
                });
                COSDictionary gsDictionary = (COSDictionary) states.map(d -> {
                    return (COSDictionary) d.getDictionaryObject(gsName, COSDictionary.class);
                }).orElseGet(() -> {
                    ResourcesHitter.LOG.warn("Graphic state resource '{}' missing or unexpected type", gsName.getName());
                    return null;
                });
                if (Objects.nonNull(gsDictionary)) {
                    new PDExtendedGraphicsState(gsDictionary).copyIntoGraphicsState(getContext().getGraphicsState());
                    if (!(gsDictionary instanceof InUseDictionary)) {
                        if (gsDictionary.hasId()) {
                            ResourcesHitter.LOG.trace("Hit ExtGState with name {} id {}", gsName.getName(), gsDictionary.id());
                            states.get().setItem(gsName, (COSBase) Optional.ofNullable(this.hitGSById.get(gsDictionary.id())).orElseGet(() -> {
                                InUseDictionary gs = new InUseDictionary(gsDictionary);
                                this.hitGSById.put(gsDictionary.id(), gs);
                                return gs;
                            }));
                        } else {
                            ResourcesHitter.LOG.trace("Hit ExtGState with name {}", gsName.getName());
                            states.get().setItem(gsName, (COSBase) new InUseDictionary(gsDictionary));
                        }
                        Optional<COSStream> softMask = Optional.ofNullable((COSDictionary) gsDictionary.getDictionaryObject(COSName.SMASK, COSDictionary.class)).map(d2 -> {
                            return (COSStream) d2.getDictionaryObject(COSName.G, COSStream.class);
                        }).filter(s -> {
                            return COSName.FORM.getName().equals(s.getNameAsString(COSName.SUBTYPE));
                        });
                        if (softMask.isPresent()) {
                            PDXObject xobject = PDXObject.createXObject(softMask.get(), getContext().getResources());
                            if (xobject instanceof PDTransparencyGroup) {
                                getContext().showTransparencyGroup((PDTransparencyGroup) xobject);
                            } else if (xobject instanceof PDFormXObject) {
                                getContext().showForm((PDFormXObject) xobject);
                            }
                        }
                    }
                }
            }
        }

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public String getName() {
            return OperatorName.SET_GRAPHICS_STATE_PARAMS;
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourcesHitter$BaseTilingPatternHitterSetColor.class */
    static abstract class BaseTilingPatternHitterSetColor extends OperatorProcessor {
        private final String name;

        abstract PDColorSpace colorSpace();

        abstract PDColor color();

        BaseTilingPatternHitterSetColor(String name) {
            this.name = name;
        }

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public void process(Operator operator, List<COSBase> arguments) throws IOException {
            if (Objects.nonNull(arguments) && !arguments.isEmpty()) {
                PDColorSpace colorSpace = colorSpace();
                PDColor color = color();
                if (colorSpace instanceof PDPattern) {
                    COSBase base = arguments.get(arguments.size() - 1);
                    if (base instanceof COSName) {
                        COSName patternName = (COSName) base;
                        COSStream pattern = (COSStream) Optional.ofNullable(getContext().getResources()).map(r -> {
                            return (COSDictionary) r.getCOSObject().getDictionaryObject(COSName.PATTERN, COSDictionary.class);
                        }).map(d -> {
                            return (COSStream) d.getDictionaryObject(patternName, COSStream.class);
                        }).orElse(null);
                        if (Objects.nonNull(pattern) && pattern.getInt(COSName.PATTERN_TYPE) == 1) {
                            ResourcesHitter.LOG.trace("Hit tiling pattern with name {}", patternName.getName());
                            PDTilingPattern tilingPattern = new PDTilingPattern(pattern);
                            getContext().processTilingPattern(tilingPattern, color, colorSpace);
                        }
                    }
                }
            }
        }

        @Override // org.sejda.sambox.contentstream.operator.OperatorProcessor
        public String getName() {
            return this.name;
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourcesHitter$TilingPatternHitterSetStrokingColor.class */
    public static class TilingPatternHitterSetStrokingColor extends BaseTilingPatternHitterSetColor {
        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
        public /* bridge */ /* synthetic */ void process(Operator operator, List list) throws IOException {
            super.process(operator, list);
        }

        public TilingPatternHitterSetStrokingColor(String name) {
            super(name);
        }

        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor
        PDColorSpace colorSpace() {
            return getContext().getGraphicsState().getStrokingColorSpace();
        }

        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor
        PDColor color() {
            return getContext().getGraphicsState().getStrokingColor();
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourcesHitter$TilingPatternHitterSetNonStrokingColor.class */
    public static class TilingPatternHitterSetNonStrokingColor extends BaseTilingPatternHitterSetColor {
        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
        public /* bridge */ /* synthetic */ String getName() {
            return super.getName();
        }

        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor, org.sejda.sambox.contentstream.operator.OperatorProcessor
        public /* bridge */ /* synthetic */ void process(Operator operator, List list) throws IOException {
            super.process(operator, list);
        }

        public TilingPatternHitterSetNonStrokingColor(String name) {
            super(name);
        }

        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor
        PDColorSpace colorSpace() {
            return getContext().getGraphicsState().getNonStrokingColorSpace();
        }

        @Override // org.sejda.impl.sambox.component.optimization.ResourcesHitter.BaseTilingPatternHitterSetColor
        PDColor color() {
            return getContext().getGraphicsState().getNonStrokingColor();
        }
    }
}

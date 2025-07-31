package org.sejda.impl.sambox.pro.component.grayscale;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.sejda.commons.util.RequireUtils;
import org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream;
import org.sejda.impl.sambox.pro.component.ContentStreamWriterStack;
import org.sejda.impl.sambox.pro.component.optimization.images.ImageOptimizer;
import org.sejda.sambox.contentstream.PDFStreamEngine;
import org.sejda.sambox.contentstream.operator.MissingOperandException;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorProcessor;
import org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator;
import org.sejda.sambox.contentstream.operator.color.SetColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorN;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetNonStrokingDeviceRGBColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorN;
import org.sejda.sambox.contentstream.operator.color.SetStrokingColorSpace;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceCMYKColor;
import org.sejda.sambox.contentstream.operator.color.SetStrokingDeviceRGBColor;
import org.sejda.sambox.contentstream.operator.state.Concatenate;
import org.sejda.sambox.contentstream.operator.state.Restore;
import org.sejda.sambox.contentstream.operator.state.Save;
import org.sejda.sambox.contentstream.operator.state.SetGraphicsStateParameters;
import org.sejda.sambox.contentstream.operator.state.SetMatrix;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSFloat;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.cos.IndirectCOSObjectIdentifier;
import org.sejda.sambox.pdmodel.MissingResourceException;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.sejda.sambox.pdmodel.graphics.PDXObject;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.form.PDFormXObject;
import org.sejda.sambox.pdmodel.graphics.image.JPEGFactory;
import org.sejda.sambox.pdmodel.graphics.image.PDImageXObject;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotation;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/GrayscaleConverter.class */
public class GrayscaleConverter extends PDFStreamEngine {
    private Map<IndirectCOSObjectIdentifier, ReadOnlyFilteredCOSStream> convertedById;
    private ContentStreamWriterStack writers;
    private boolean convertImages;
    private boolean convertTextToBlack;
    private boolean compressImages;
    private static final Logger LOG = LoggerFactory.getLogger(GrayscaleConverter.class);
    private static final float[] WHITE = {1.0f, 1.0f, 1.0f};

    public GrayscaleConverter(ContentStreamWriterStack writers) {
        this(writers, true, false, false);
    }

    public GrayscaleConverter(ContentStreamWriterStack writers, boolean convertImages, boolean convertTextToBlack, boolean compressImages) {
        this.convertedById = new HashMap();
        Objects.requireNonNull(writers, "GrayscaleConverter requires a writers stack");
        this.writers = writers;
        this.convertImages = convertImages;
        this.convertTextToBlack = convertTextToBlack;
        this.compressImages = compressImages;
        addOperator(new ContentStreamWritingOperator(new Concatenate()));
        addOperator(new ContentStreamWritingOperator(new SetGraphicsStateParameters()));
        addOperator(new ContentStreamWritingOperator(new Save()));
        addOperator(new ContentStreamWritingOperator(new Restore()));
        addOperator(new ContentStreamWritingOperator(new SetMatrix()));
        addOperator(new ContentStreamWritingOperator(new XObjectOperator()));
        addOperator(new ContentStreamWritingOperator(new InlineImageToGrayscaleOperator()));
        addOperator(new ColorSpaceConverterOperator(writers, new SetStrokingColorSpace()));
        addOperator(new ColorSpaceConverterOperator(writers, new SetNonStrokingColorSpace()));
        addOperator(new ConvertToGraySetColor(new SetStrokingColor(), Operator.getOperator("SC")));
        addOperator(new ConvertToGraySetColor(new SetNonStrokingColor(), Operator.getOperator("sc")));
        addOperator(new ConvertToGraySetColor(new SetStrokingColorN(), Operator.getOperator("SCN")));
        addOperator(new ConvertToGraySetColor(new SetNonStrokingColorN(), Operator.getOperator("scn")));
        addOperator(new ConvertToGraySetColor(new SetStrokingDeviceRGBColor(), Operator.getOperator("G")));
        addOperator(new ConvertToGraySetColor(new SetNonStrokingDeviceRGBColor(), Operator.getOperator("g")));
        addOperator(new ConvertToGraySetColor(new SetStrokingDeviceCMYKColor(), Operator.getOperator("G")));
        addOperator(new ConvertToGraySetColor(new SetNonStrokingDeviceCMYKColor(), Operator.getOperator("g")));
    }

    public void convert(PDPage page) throws IOException {
        processPage(page);
        processAnnotations(page);
    }

    public void processPage(PDPage page) throws IOException {
        COSStream stream = this.writers.newWriter();
        try {
            super.processPage(page);
            page.getCOSObject().setItem(COSName.CONTENTS, stream);
        } finally {
            this.writers.closeCurrentWriter();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeOperator(List<COSBase> operands, Operator operator) throws IOException {
        this.writers.writeOperator(operands, operator);
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/GrayscaleConverter$XObjectOperator.class */
    private class XObjectOperator extends OperatorProcessor {
        private XObjectOperator() {
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: org.sejda.sambox.contentstream.operator.MissingOperandException */
        public void process(Operator operator, List<COSBase> operands) throws MissingOperandException, IOException {
            if (operands.isEmpty()) {
                throw new MissingOperandException(operator, operands);
            }
            COSBase operand = operands.get(0);
            if (operand instanceof COSName) {
                COSName objectName = (COSName) operand;
                COSStream cOSStream = (COSBase) Optional.ofNullable(getContext().getResources().getCOSObject().getDictionaryObject(COSName.XOBJECT, COSDictionary.class)).map(d -> {
                    return d.getDictionaryObject(objectName);
                }).orElseThrow(() -> {
                    return new MissingResourceException("Missing XObject: " + objectName.getName());
                });
                if (cOSStream instanceof COSStream) {
                    if (!(cOSStream instanceof ReadOnlyFilteredCOSStream)) {
                        COSStream stream = cOSStream;
                        String subtype = stream.getNameAsString(COSName.SUBTYPE);
                        if (!GrayscaleConverter.this.convertImages || !COSName.IMAGE.getName().equals(subtype)) {
                            if (COSName.FORM.getName().equals(subtype)) {
                                COSStream formStream = GrayscaleConverter.this.writers.newWriter();
                                try {
                                    GrayscaleConverter.this.showForm((PDFormXObject) PDXObject.createXObject(stream, getContext().getResources()));
                                    formStream.mergeWithoutOverwriting(stream);
                                } catch (IOException | RuntimeException ex) {
                                    formStream = stream;
                                    GrayscaleConverter.LOG.warn("Failed to convert form image " + objectName + ", skipping and continuing with next.", ex);
                                }
                                GrayscaleConverter.this.writers.closeCurrentWriter();
                                setXObject(objectName, ReadOnlyFilteredCOSStream.readOnly(formStream));
                                return;
                            }
                            return;
                        }
                        ReadOnlyFilteredCOSStream converted = ReadOnlyFilteredCOSStream.readOnly(stream);
                        if (!GrayscaleUtils.isGray((COSDictionary) stream)) {
                            try {
                                PDImageXObject image = new PDImageXObject(new PDStream(stream), getContext().getResources());
                                if (!image.isEmpty() && !image.isStencil() && !GrayscaleUtils.isGray(image)) {
                                    converted = convertToGrayscale(objectName, image, stream.id(), GrayscaleConverter.this.compressImages);
                                }
                                image.getCOSObject().unDecode();
                            } catch (IOException | RuntimeException ex2) {
                                GrayscaleConverter.LOG.warn("Failed to convert image " + objectName + ", skipping and continuing with next.", ex2);
                            }
                        }
                        setXObject(objectName, converted);
                        return;
                    }
                    return;
                }
                GrayscaleConverter.LOG.warn("Unexpected type {} for xObject {}", cOSStream.getClass(), objectName.getName());
            }
        }

        private ReadOnlyFilteredCOSStream convertToGrayscale(COSName objectName, PDImageXObject image, IndirectCOSObjectIdentifier id, boolean shouldCompress) throws IOException {
            PDImageXObject grayImage;
            int targetHeight;
            int targetWidth;
            GrayscaleConverter.LOG.debug("Converting image {} {}", objectName.getName(), id.toString());
            ReadOnlyFilteredCOSStream convertedImage = GrayscaleConverter.this.convertedById.get(id);
            if (Objects.isNull(convertedImage)) {
                if (shouldCompress) {
                    Matrix ctmNew = getContext().getGraphicsState().getCurrentTransformationMatrix();
                    float displayWidth = ctmNew.getScalingFactorX();
                    float displayHeight = ctmNew.getScalingFactorY();
                    if (Optional.ofNullable(image.getCOSObject().getDictionaryObject(COSName.SMASK, COSStream.class)).map(sm -> {
                        return sm.getDictionaryObject(COSName.MATTE, COSArray.class);
                    }).isPresent()) {
                        GrayscaleConverter.LOG.debug("Found image {} {}x{} (displayed as {}x{}, has smask with matte and won't be resized)", new Object[]{objectName.getName(), Integer.valueOf(image.getWidth()), Integer.valueOf(image.getHeight()), Float.valueOf(displayWidth), Float.valueOf(displayHeight)});
                        targetHeight = image.getHeight();
                        targetWidth = image.getWidth();
                    } else {
                        int width = image.getWidth();
                        int height = image.getWidth();
                        targetHeight = Math.abs((int) ((displayHeight / 72.0f) * 144));
                        targetWidth = (int) Math.abs(Math.round((targetHeight * width) / height));
                        GrayscaleConverter.LOG.debug("Found image {} {}x{} (displayed as {}x{}, to be resized to {}x{})", new Object[]{objectName.getName(), Integer.valueOf(image.getWidth()), Integer.valueOf(image.getHeight()), Float.valueOf(displayWidth), Float.valueOf(displayHeight), Integer.valueOf(targetWidth), Integer.valueOf(targetHeight)});
                    }
                    BufferedImage imageWithoutMask = image.getImageWithoutMasks();
                    File tmpImageFile = ImageOptimizer.optimize(imageWithoutMask, 0.65f, 144, targetWidth, targetHeight, true);
                    imageWithoutMask.flush();
                    grayImage = GrayscaleConverter.createFromJpegFile(tmpImageFile, image);
                } else {
                    BufferedImage imageWithoutMask2 = image.getImageWithoutMasks();
                    grayImage = GrayscaleUtils.toGray(imageWithoutMask2);
                    imageWithoutMask2.flush();
                }
                convertedImage = GrayscaleConverter.readOnlyStreamForImage(grayImage.getCOSObject(), image);
                GrayscaleConverter.this.convertedById.put(id, convertedImage);
                GrayscaleConverter.LOG.trace("Image converted to grayscale");
            } else {
                GrayscaleConverter.LOG.debug(String.format("Skipping already converted image with id %s", id));
            }
            return convertedImage;
        }

        private void setXObject(COSName objectName, ReadOnlyFilteredCOSStream stream) {
            COSDictionary resources = getContext().getResources().getCOSObject();
            COSDictionary xobjects = (COSDictionary) Optional.ofNullable(resources.getDictionaryObject(COSName.XOBJECT)).filter(b -> {
                return b instanceof COSDictionary;
            }).map(b2 -> {
                return (COSDictionary) b2;
            }).orElseGet(() -> {
                COSDictionary ret = new COSDictionary();
                resources.setItem(COSName.XOBJECT, ret);
                return ret;
            });
            xobjects.setItem(objectName, stream);
        }

        public String getName() {
            return "Do";
        }
    }

    protected void unsupportedOperator(Operator operator, List<COSBase> operands) throws IOException {
        writeOperator(operands, operator);
    }

    private static ReadOnlyFilteredCOSStream readOnlyStreamForImage(COSStream existing, PDImageXObject original) throws IOException {
        ReadOnlyFilteredCOSStream stream = ReadOnlyFilteredCOSStream.readOnly(existing);
        stream.setItem(COSName.OC, original.getCOSObject().getDictionaryObject(COSName.OC));
        COSStream sm = original.getCOSObject().getDictionaryObject(COSName.SMASK, COSStream.class);
        if (Objects.nonNull(sm)) {
            COSArray matte = sm.getDictionaryObject(COSName.MATTE, COSArray.class);
            if (Objects.nonNull(matte)) {
                PDColor color = new PDColor(matte, original.getColorSpace());
                COSDictionary newSmDic = sm.duplicate();
                newSmDic.setItem(COSName.MATTE, new COSArray(new COSBase[]{new COSFloat(GrayscaleUtils.toGray(original.getColorSpace().toRGB(color.getComponents())))}));
                COSName cOSName = COSName.SMASK;
                Objects.requireNonNull(sm);
                stream.setItem(cOSName, new ReadOnlyFilteredCOSStream(newSmDic, sm::getFilteredStream, sm.getFilteredLength()));
            } else {
                stream.setItem(COSName.SMASK, sm);
            }
        }
        stream.setItem(COSName.MASK, original.getCOSObject().getDictionaryObject(COSName.MASK, COSStream.class));
        return stream;
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/GrayscaleConverter$ContentStreamWritingOperator.class */
    private class ContentStreamWritingOperator extends OperatorProcessorDecorator {
        ContentStreamWritingOperator(OperatorProcessor delegate) {
            super(delegate, (operator, operands)
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000d: CONSTRUCTOR 
                  (r6v0 'delegate' org.sejda.sambox.contentstream.operator.OperatorProcessor A[D('delegate' org.sejda.sambox.contentstream.operator.OperatorProcessor)])
                  (wrap:org.sejda.sambox.contentstream.operator.OperatorConsumer:0x0008: INVOKE_CUSTOM 
                  (wrap:org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter:IGET 
                  (r4v0 'this' org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter$ContentStreamWritingOperator A[D('this' org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter$ContentStreamWritingOperator), IMMUTABLE_TYPE, THIS])
                 A[WRAPPED] org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ContentStreamWritingOperator.this$0 org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter)
                 A[MD:(org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter):org.sejda.sambox.contentstream.operator.OperatorConsumer (s), WRAPPED]
                 handle type: INVOKE_STATIC
                 lambda: org.sejda.sambox.contentstream.operator.OperatorConsumer.apply(org.sejda.sambox.contentstream.operator.Operator, java.util.List):void
                 call insn: INVOKE 
                  (r2 I:org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter)
                  (v1 org.sejda.sambox.contentstream.operator.Operator)
                  (v2 java.util.List)
                 STATIC call: org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ContentStreamWritingOperator.lambda$new$0(org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.Operator, java.util.List):void A[MD:(org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.Operator, java.util.List):void throws java.io.IOException (m)])
                 (LINE:319) call: org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator.<init>(org.sejda.sambox.contentstream.operator.OperatorProcessor, org.sejda.sambox.contentstream.operator.OperatorConsumer):void type: SUPER in method: org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ContentStreamWritingOperator.<init>(org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.OperatorProcessor):void, file: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/GrayscaleConverter$ContentStreamWritingOperator.class
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:301)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                	at java.base/java.util.ArrayList.forEach(Unknown Source)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline.forEach(Unknown Source)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
                	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:310)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:299)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                	at java.base/java.util.ArrayList.forEach(Unknown Source)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline.forEach(Unknown Source)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
                	at jadx.core.ProcessClass.process(ProcessClass.java:79)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
                	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
                	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Unexpected argument type in lambda call: InsnWrapArg
                	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:1069)
                	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:962)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:853)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:782)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                	... 52 more
                */
            /*
                this = this;
                r0 = r4
                r1 = r5
                org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.this = r1
                r0 = r4
                r1 = r6
                r2 = r5
                void r2 = (v1, v2) -> { // org.sejda.sambox.contentstream.operator.OperatorConsumer.apply(org.sejda.sambox.contentstream.operator.Operator, java.util.List):void
                    lambda$new$0(r2, v1, v2);
                }
                r0.<init>(r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ContentStreamWritingOperator.<init>(org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.OperatorProcessor):void");
        }
    }

    private float toBlackOrGray(float[] rgb) {
        if (this.convertTextToBlack && !Arrays.equals(WHITE, rgb)) {
            return 0.0f;
        }
        return GrayscaleUtils.toGray(rgb);
    }

    private static PDImageXObject createFromJpegFile(File file, PDImageXObject original) throws IOException {
        BufferedImage awtImage = ImageIO.read(file);
        RequireUtils.requireIOCondition(Objects.nonNull(awtImage), "Cannot read image");
        if (awtImage.getColorModel().hasAlpha()) {
            throw new UnsupportedOperationException("alpha channel not implemented");
        }
        ReadOnlyFilteredCOSStream stream = ReadOnlyFilteredCOSStream.readOnlyJpegImage(file, awtImage.getWidth(), awtImage.getHeight(), awtImage.getColorModel().getComponentSize(0), JPEGFactory.getColorSpaceFromAWT(awtImage));
        stream.setItem(COSName.OC, original.getCOSObject().getDictionaryObject(COSName.OC));
        stream.setItem(COSName.SMASK, original.getCOSObject().getDictionaryObject(COSName.SMASK, COSStream.class));
        stream.setItem(COSName.MASK, original.getCOSObject().getDictionaryObject(COSName.MASK, COSStream.class));
        return new PDImageXObject(new PDStream(stream), (PDResources) null);
    }

    /* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/GrayscaleConverter$ConvertToGraySetColor.class */
    private class ConvertToGraySetColor extends OperatorProcessorDecorator {
        ConvertToGraySetColor(SetColor d, Operator replace) {
            super(d, (operator, operands)
            /*  JADX ERROR: Method code generation error
                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x000f: CONSTRUCTOR 
                  (r8v0 'd' org.sejda.sambox.contentstream.operator.color.SetColor A[D('d' org.sejda.sambox.contentstream.operator.color.SetColor)])
                  (wrap:org.sejda.sambox.contentstream.operator.OperatorConsumer:0x000a: INVOKE_CUSTOM 
                  (r8v0 'd' org.sejda.sambox.contentstream.operator.color.SetColor A[D('d' org.sejda.sambox.contentstream.operator.color.SetColor), DONT_INLINE])
                  (r9v0 'replace' org.sejda.sambox.contentstream.operator.Operator A[D('replace' org.sejda.sambox.contentstream.operator.Operator), DONT_INLINE])
                  (wrap:org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter:IGET 
                  (r6v0 'this' org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter$ConvertToGraySetColor A[D('this' org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter$ConvertToGraySetColor), IMMUTABLE_TYPE, THIS])
                 A[WRAPPED] org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ConvertToGraySetColor.this$0 org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter)
                 A[MD:(org.sejda.sambox.contentstream.operator.color.SetColor, org.sejda.sambox.contentstream.operator.Operator, org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter):org.sejda.sambox.contentstream.operator.OperatorConsumer (s), WRAPPED]
                 handle type: INVOKE_STATIC
                 lambda: org.sejda.sambox.contentstream.operator.OperatorConsumer.apply(org.sejda.sambox.contentstream.operator.Operator, java.util.List):void
                 call insn: INVOKE 
                  (r2 I:org.sejda.sambox.contentstream.operator.color.SetColor)
                  (r3 I:org.sejda.sambox.contentstream.operator.Operator)
                  (r4 I:org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter)
                  (v3 org.sejda.sambox.contentstream.operator.Operator)
                  (v4 java.util.List)
                 STATIC call: org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ConvertToGraySetColor.lambda$new$2(org.sejda.sambox.contentstream.operator.color.SetColor, org.sejda.sambox.contentstream.operator.Operator, org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.Operator, java.util.List):void A[MD:(org.sejda.sambox.contentstream.operator.color.SetColor, org.sejda.sambox.contentstream.operator.Operator, org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.Operator, java.util.List):void throws java.io.IOException (m)])
                 (LINE:354) call: org.sejda.sambox.contentstream.operator.OperatorProcessorDecorator.<init>(org.sejda.sambox.contentstream.operator.OperatorProcessor, org.sejda.sambox.contentstream.operator.OperatorConsumer):void type: SUPER in method: org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ConvertToGraySetColor.<init>(org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.color.SetColor, org.sejda.sambox.contentstream.operator.Operator):void, file: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/grayscale/GrayscaleConverter$ConvertToGraySetColor.class
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:301)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                	at java.base/java.util.ArrayList.forEach(Unknown Source)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline.forEach(Unknown Source)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
                	at jadx.core.codegen.ClassGen.addInnerClass(ClassGen.java:310)
                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:299)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
                	at java.base/java.util.ArrayList.forEach(Unknown Source)
                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
                	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.copyInto(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(Unknown Source)
                	at java.base/java.util.stream.AbstractPipeline.evaluate(Unknown Source)
                	at java.base/java.util.stream.ReferencePipeline.forEach(Unknown Source)
                	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
                	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
                	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
                	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
                	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
                	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
                	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
                	at jadx.core.ProcessClass.process(ProcessClass.java:79)
                	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
                	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
                	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
                	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Unexpected argument type in lambda call: InsnWrapArg
                	at jadx.core.codegen.InsnGen.makeInlinedLambdaMethod(InsnGen.java:1069)
                	at jadx.core.codegen.InsnGen.makeInvokeLambda(InsnGen.java:962)
                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:853)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:782)
                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                	... 52 more
                */
            /*
                this = this;
                r0 = r6
                r1 = r7
                org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.this = r1
                r0 = r6
                r1 = r8
                r2 = r8
                r3 = r9
                r4 = r7
                void r2 = (v3, v4) -> { // org.sejda.sambox.contentstream.operator.OperatorConsumer.apply(org.sejda.sambox.contentstream.operator.Operator, java.util.List):void
                    lambda$new$2(r2, r3, r4, v3, v4);
                }
                r0.<init>(r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter.ConvertToGraySetColor.<init>(org.sejda.impl.sambox.pro.component.grayscale.GrayscaleConverter, org.sejda.sambox.contentstream.operator.color.SetColor, org.sejda.sambox.contentstream.operator.Operator):void");
        }
    }

    public void processAnnotations(PDPage page) {
        LOG.trace("Converting annotations to gray scale");
        List<PDAnnotation> converted = new ArrayList<>();
        for (PDAnnotation annotation : page.getAnnotations()) {
            converted.add(processAnnotation(annotation));
        }
        if (!converted.isEmpty()) {
            LOG.trace("Converted {} annotations", Integer.valueOf(converted.size()));
            page.setAnnotations(converted);
        }
    }

    private PDAnnotation processAnnotation(PDAnnotation annotation) {
        LOG.trace("Converting annotation {}", annotation);
        try {
            processAnnotationColor(COSName.C, annotation);
            processAnnotationColor(COSName.IC, annotation);
            COSDictionary ap = annotation.getCOSObject().getDictionaryObject(COSName.AP, COSDictionary.class);
            if (Objects.nonNull(ap)) {
                COSDictionary toAP = new COSDictionary();
                processAppearanceStreamOrDictionary(annotation, COSName.N, ap.getDictionaryObject(COSName.N), toAP);
                processAppearanceStreamOrDictionary(annotation, COSName.R, ap.getDictionaryObject(COSName.R), toAP);
                processAppearanceStreamOrDictionary(annotation, COSName.D, ap.getDictionaryObject(COSName.D), toAP);
                annotation.getCOSObject().setItem(COSName.AP, toAP);
            }
        } catch (Exception e) {
            LOG.warn("Failed to convert annotation to gray scale", e);
        }
        return annotation;
    }

    private void processAnnotationColor(COSName key, PDAnnotation annotation) {
        Optional.ofNullable(annotation.getColor(key)).ifPresent(c -> {
            try {
                annotation.getCOSObject().setItem(key, new COSArray(new COSBase[]{new COSFloat(toBlackOrGray(c.getColorSpace().toRGB(c.getComponents())))}));
            } catch (Exception e) {
                LOG.warn("Failed to convert annotation color to gray scale", e);
            }
        });
    }

    private void processAppearanceStreamOrDictionary(PDAnnotation annotation, COSName key, COSBase entry, COSDictionary toAP) {
        if (Objects.nonNull(entry)) {
            PDAppearanceEntry streamOrDic = new PDAppearanceEntry(entry);
            if (streamOrDic.isStream()) {
                toAP.setItem(key, processAppearance(annotation, streamOrDic.getAppearanceStream()));
                return;
            }
            COSDictionary sub = new COSDictionary();
            for (Map.Entry<COSName, PDAppearanceStream> subEntry : streamOrDic.getSubDictionary().entrySet()) {
                sub.setItem(subEntry.getKey(), processAppearance(annotation, subEntry.getValue()));
            }
            toAP.setItem(key, sub);
        }
    }

    private COSStream processAppearance(PDAnnotation annotation, PDAppearanceStream appearance) {
        COSStream grayAppearance = this.writers.newWriter();
        try {
            processAnnotation(annotation, appearance);
            this.writers.closeCurrentWriter();
            grayAppearance.mergeWithoutOverwriting(appearance.getCOSObject());
            return grayAppearance;
        } catch (IOException | RuntimeException ex) {
            this.writers.closeCurrentWriter();
            LOG.warn("Failed to convert annotation to gray scale", ex);
            return appearance.getCOSObject();
        }
    }
}

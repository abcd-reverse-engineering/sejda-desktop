package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.sejda.io.SeekableSources;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSNumber;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.input.ContentStreamParser;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.graphics.color.PDColorSpace;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceCMYK;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceGray;
import org.sejda.sambox.pdmodel.graphics.color.PDDeviceRGB;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PDDefaultAppearanceString.class */
class PDDefaultAppearanceString {
    private static final Logger LOG = LoggerFactory.getLogger(PDDefaultAppearanceString.class);
    private static final float DEFAULT_FONT_SIZE = 12.0f;
    private COSName fontName;
    private PDFont font;
    private float fontSize;
    private PDColor fontColor;
    private final PDResources defaultResources;

    PDDefaultAppearanceString(COSString defaultAppearance, PDResources defaultResources) throws IOException {
        this.fontSize = DEFAULT_FONT_SIZE;
        defaultAppearance = defaultAppearance == null ? new COSString("/Helvetica 0 Tf 0 g".getBytes()) : defaultAppearance;
        this.defaultResources = defaultResources == null ? new PDResources() : defaultResources;
        processAppearanceStringOperators(defaultAppearance.getBytes());
    }

    PDDefaultAppearanceString() throws IOException {
        this(null, null);
    }

    private void processAppearanceStringOperators(byte[] content) throws IOException {
        List<COSBase> arguments = new ArrayList<>();
        ContentStreamParser parser = new ContentStreamParser(SeekableSources.inMemorySeekableSourceFrom(content));
        try {
            for (Object token : parser.tokens()) {
                if (token instanceof COSBase) {
                    arguments.add(((COSBase) token).getCOSObject());
                } else if (token instanceof Operator) {
                    processOperator((Operator) token, arguments);
                    arguments = new ArrayList<>();
                } else {
                    LOG.error("Unrecognized operator type {}", token);
                }
            }
            parser.close();
        } catch (Throwable th) {
            try {
                parser.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        String name = operator.getName();
        if (OperatorName.SET_FONT_AND_SIZE.equals(name)) {
            processSetFont(operands);
            return;
        }
        if (OperatorName.NON_STROKING_GRAY.equals(name)) {
            processSetFontColor(operands);
        } else if (OperatorName.NON_STROKING_RGB.equals(name)) {
            processSetFontColor(operands);
        } else if (OperatorName.NON_STROKING_CMYK.equals(name)) {
            processSetFontColor(operands);
        }
    }

    private void processSetFont(List<COSBase> operands) throws IOException {
        if (operands.size() < 2) {
            throw new IOException("Missing operands for set font operator " + Arrays.toString(operands.toArray()));
        }
        COSBase base0 = operands.get(0);
        COSBase base1 = operands.get(1);
        if (!(base0 instanceof COSName)) {
            return;
        }
        COSName fontName = (COSName) base0;
        if (!(base1 instanceof COSNumber)) {
            return;
        }
        PDFont font = this.defaultResources.getFont(fontName);
        float fontSize = ((COSNumber) base1).floatValue();
        if (font == null) {
            LOG.warn("Could not find font: /" + fontName.getName() + ", will use Helvetica instead");
            font = PDType1Font.HELVETICA();
        }
        setFontName(fontName);
        setFont(font);
        setFontSize(fontSize);
    }

    private void processSetFontColor(List<COSBase> operands) throws IOException {
        PDColorSpace colorSpace;
        switch (operands.size()) {
            case 1:
                colorSpace = PDDeviceGray.INSTANCE;
                break;
            case 2:
            default:
                throw new IOException("Missing operands for set non stroking color operator " + Arrays.toString(operands.toArray()));
            case 3:
                colorSpace = PDDeviceRGB.INSTANCE;
                break;
            case 4:
                colorSpace = PDDeviceCMYK.INSTANCE;
                break;
        }
        COSArray array = new COSArray();
        array.addAll(operands);
        setFontColor(new PDColor(array, colorSpace));
    }

    COSName getFontName() {
        return this.fontName;
    }

    void setFontName(COSName fontName) {
        this.fontName = fontName;
    }

    PDFont getFont() {
        return this.font;
    }

    void setFont(PDFont font) {
        this.font = font;
    }

    public float getFontSize() {
        return this.fontSize;
    }

    void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    PDColor getFontColor() {
        return this.fontColor;
    }

    void setFontColor(PDColor fontColor) {
        this.fontColor = fontColor;
    }

    void writeTo(PDPageContentStream contents, float zeroFontSize) throws IOException {
        float fontSize = getFontSize();
        if (fontSize == 0.0f) {
            fontSize = zeroFontSize;
        }
        if (getFont() != null) {
            contents.setFont(getFont(), fontSize);
        }
        if (getFontColor() != null) {
            contents.setNonStrokingColor(getFontColor());
        }
    }

    void copyNeededResourcesTo(PDAppearanceStream appearanceStream) throws IOException {
        PDResources streamResources = appearanceStream.getResources();
        if (streamResources == null) {
            streamResources = new PDResources();
            appearanceStream.setResources(streamResources);
        }
        if (streamResources.getFont(this.fontName) == null) {
            streamResources.put(this.fontName, getFont());
        }
    }
}

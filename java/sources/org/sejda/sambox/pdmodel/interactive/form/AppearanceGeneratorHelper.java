package org.sejda.sambox.pdmodel.interactive.form;

import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.fontbox.util.BoundingBox;
import org.sejda.commons.util.RequireUtils;
import org.sejda.io.CountingWritableByteChannel;
import org.sejda.sambox.contentstream.operator.Operator;
import org.sejda.sambox.contentstream.operator.OperatorName;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSString;
import org.sejda.sambox.input.ContentStreamParser;
import org.sejda.sambox.output.ContentStreamWriter;
import org.sejda.sambox.pdmodel.PDPageContentStream;
import org.sejda.sambox.pdmodel.PDResources;
import org.sejda.sambox.pdmodel.common.PDPageLabelRange;
import org.sejda.sambox.pdmodel.common.PDRectangle;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.StandardStructureTypes;
import org.sejda.sambox.pdmodel.font.PDFont;
import org.sejda.sambox.pdmodel.font.PDSimpleFont;
import org.sejda.sambox.pdmodel.font.PDType1Font;
import org.sejda.sambox.pdmodel.font.PDType3CharProc;
import org.sejda.sambox.pdmodel.font.PDType3Font;
import org.sejda.sambox.pdmodel.font.PDVectorFont;
import org.sejda.sambox.pdmodel.graphics.color.PDColor;
import org.sejda.sambox.pdmodel.interactive.action.PDAction;
import org.sejda.sambox.pdmodel.interactive.action.PDActionJavaScript;
import org.sejda.sambox.pdmodel.interactive.action.PDFormFieldAdditionalActions;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.sejda.sambox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.sejda.sambox.pdmodel.interactive.form.PlainTextFormatter;
import org.sejda.sambox.util.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/AppearanceGeneratorHelper.class */
public class AppearanceGeneratorHelper {
    private final PDVariableText field;
    private PDDefaultAppearanceString defaultAppearance;
    private String value;
    private static final int FONTSCALE = 1000;
    private static final float DEFAULT_FONT_SIZE = 12.0f;
    private static final float DEFAULT_PADDING = 0.5f;
    private static final Logger LOG = LoggerFactory.getLogger(AppearanceGeneratorHelper.class);
    private static final Operator BMC = Operator.getOperator(OperatorName.BEGIN_MARKED_CONTENT);
    private static final Operator EMC = Operator.getOperator(OperatorName.END_MARKED_CONTENT);
    private static final float[] HIGHLIGHT_COLOR = {0.6f, 0.75686276f, 0.84313726f};

    public AppearanceGeneratorHelper(PDVariableText field) throws IOException {
        this.field = field;
        validateAndEnsureAcroFormResources();
        try {
            this.defaultAppearance = field.getDefaultAppearanceString();
        } catch (IOException ex) {
            throw new IOException("Could not process default appearance string '" + field.getDefaultAppearance() + "' for field '" + field.getFullyQualifiedName() + "'", ex);
        }
    }

    private void validateAndEnsureAcroFormResources() {
        PDResources acroFormResources = this.field.getAcroForm().getDefaultResources();
        if (acroFormResources == null) {
            return;
        }
        for (PDAnnotationWidget widget : this.field.getWidgets()) {
            PDAppearanceStream normalAppearanceStream = widget.getNormalAppearanceStream();
            if (Objects.nonNull(normalAppearanceStream)) {
                PDResources widgetResources = normalAppearanceStream.getResources();
                if (Objects.nonNull(widgetResources)) {
                    COSDictionary widgetFontDict = (COSDictionary) widgetResources.getCOSObject().getDictionaryObject(COSName.FONT, COSDictionary.class);
                    Map<COSName, COSBase> missingFonts = new HashMap<>();
                    for (COSName fontResourceName : widgetResources.getFontNames()) {
                        try {
                            if (acroFormResources.getFont(fontResourceName) == null) {
                                LOG.debug("Adding font resource " + fontResourceName + " from widget to AcroForm");
                                missingFonts.put(fontResourceName, widgetFontDict.getItem(fontResourceName));
                            }
                        } catch (IOException e) {
                            LOG.warn("Unable to match field level font with AcroForm font");
                        }
                    }
                    COSDictionary acroFormFontDict = (COSDictionary) acroFormResources.getCOSObject().getDictionaryObject(COSName.FONT, COSDictionary.class);
                    if (acroFormFontDict == null) {
                        acroFormFontDict = new COSDictionary();
                        acroFormResources.getCOSObject().setItem(COSName.FONT, (COSBase) acroFormFontDict);
                    }
                    for (COSName key : missingFonts.keySet()) {
                        acroFormFontDict.setItem(key, missingFonts.get(key));
                    }
                }
            }
        }
    }

    public void setAppearanceValue(String apValue) throws IOException {
        PDAppearanceStream appearanceStream;
        this.value = getFormattedValue(apValue);
        this.value = this.value.replaceAll("\\u3000", " ");
        if (this.field instanceof PDTextField) {
            this.value = this.value.replaceAll("\\u00A0", " ");
            this.value = this.value.replaceAll("\\u0009", "   ");
        }
        if ((this.field instanceof PDTextField) && !((PDTextField) this.field).isMultiline()) {
            this.value = this.value.replaceAll("\\u000D\\u000A|[\\u000A\\u000B\\u000C\\u000D\\u0085\\u2028\\u2029]", " ");
        }
        for (PDAnnotationWidget widget : this.field.getWidgets()) {
            if (widget.getCOSObject().containsKey("PMD")) {
                LOG.warn("Widget of field {} is a PaperMetaData, no appearance stream created", this.field.getFullyQualifiedName());
            } else {
                PDDefaultAppearanceString acroFormAppearance = this.defaultAppearance;
                if (widget.getCOSObject().getDictionaryObject(COSName.DA) != null) {
                    this.defaultAppearance = getWidgetDefaultAppearanceString(widget);
                }
                PDRectangle rect = widget.getRectangle();
                if (rect == null) {
                    widget.getCOSObject().removeItem(COSName.AP);
                    LOG.warn("widget of field {} has no rectangle, no appearance stream created", this.field.getFullyQualifiedName());
                } else {
                    PDAppearanceDictionary appearanceDict = widget.getAppearance();
                    if (appearanceDict == null) {
                        appearanceDict = new PDAppearanceDictionary();
                        widget.setAppearance(appearanceDict);
                    }
                    PDAppearanceEntry appearance = appearanceDict.getNormalAppearance();
                    if (isValidAppearanceStream(appearance)) {
                        appearanceStream = appearance.getAppearanceStream();
                    } else {
                        appearanceStream = prepareNormalAppearanceStream(widget);
                        appearanceDict.setNormalAppearance(appearanceStream);
                    }
                    PDAppearanceCharacteristicsDictionary appearanceCharacteristics = widget.getAppearanceCharacteristics();
                    if (appearanceCharacteristics != null || appearanceStream.getContentStream().getLength() == 0) {
                        initializeAppearanceContent(widget, appearanceCharacteristics, appearanceStream);
                    }
                    setAppearanceContent(widget, appearanceStream);
                    this.defaultAppearance = acroFormAppearance;
                }
            }
        }
    }

    private String getFormattedValue(String apValue) {
        PDFormFieldAdditionalActions actions = this.field.getActions();
        if (actions == null) {
            return apValue;
        }
        PDAction actionF = actions.getF();
        if (actionF != null) {
            if (this.field.getAcroForm().getScriptingHandler() != null) {
                ScriptingHandler scriptingHandler = this.field.getAcroForm().getScriptingHandler();
                return scriptingHandler.format((PDActionJavaScript) actionF, apValue);
            }
            LOG.info("Field contains a formatting action but no ScriptingHandler has been supplied - formatted value might be incorrect");
        }
        return apValue;
    }

    private static boolean isValidAppearanceStream(PDAppearanceEntry appearance) {
        PDRectangle bbox;
        return !Objects.isNull(appearance) && appearance.isStream() && (bbox = appearance.getAppearanceStream().getBBox()) != null && Math.abs(bbox.getWidth()) > 0.0f && Math.abs(bbox.getHeight()) > 0.0f;
    }

    private PDAppearanceStream prepareNormalAppearanceStream(PDAnnotationWidget widget) {
        PDAppearanceStream appearanceStream = new PDAppearanceStream();
        int rotation = resolveRotation(widget);
        PDRectangle rect = widget.getRectangle();
        Matrix matrix = Matrix.getRotateInstance(Math.toRadians(rotation), 0.0f, 0.0f);
        Point2D.Float point2D = matrix.transformPoint(rect.getWidth(), rect.getHeight());
        PDRectangle bbox = new PDRectangle(Math.abs((float) point2D.getX()), Math.abs((float) point2D.getY()));
        appearanceStream.setBBox(bbox);
        AffineTransform at = calculateMatrix(bbox, rotation);
        if (!at.isIdentity()) {
            appearanceStream.setMatrix(at);
        }
        appearanceStream.setFormType(1);
        appearanceStream.setResources(new PDResources());
        return appearanceStream;
    }

    private PDDefaultAppearanceString getWidgetDefaultAppearanceString(PDAnnotationWidget widget) throws IOException {
        COSString da = DefaultAppearanceHelper.getDefaultAppearance(widget.getCOSObject().getDictionaryObject(COSName.DA));
        PDResources dr = this.field.getAcroForm().getDefaultResources();
        try {
            return new PDDefaultAppearanceString(da, dr);
        } catch (IOException e) {
            LOG.warn("Failed to process default appearance string for widget {}, will use fallback default appearance", widget);
            return new PDDefaultAppearanceString();
        }
    }

    private int resolveRotation(PDAnnotationWidget widget) {
        PDAppearanceCharacteristicsDictionary characteristicsDictionary = widget.getAppearanceCharacteristics();
        if (characteristicsDictionary != null) {
            return characteristicsDictionary.getRotation();
        }
        return 0;
    }

    private void initializeAppearanceContent(PDAnnotationWidget widget, PDAppearanceCharacteristicsDictionary appearanceCharacteristics, PDAppearanceStream appearanceStream) throws IOException {
        PDPageContentStream contents = new PDPageContentStream(this.field.getAcroForm().getDocument(), appearanceStream);
        if (appearanceCharacteristics != null) {
            try {
                PDColor backgroundColour = appearanceCharacteristics.getBackground();
                if (backgroundColour != null) {
                    contents.setNonStrokingColor(backgroundColour);
                    PDRectangle bbox = resolveBoundingBox(widget, appearanceStream);
                    contents.addRect(bbox.getLowerLeftX(), bbox.getLowerLeftY(), bbox.getWidth(), bbox.getHeight());
                    contents.fill();
                }
                float lineWidth = 0.0f;
                PDColor borderColour = appearanceCharacteristics.getBorderColour();
                if (borderColour != null) {
                    contents.setStrokingColor(borderColour);
                    lineWidth = 1.0f;
                }
                PDBorderStyleDictionary borderStyle = widget.getBorderStyle();
                if (borderStyle != null && borderStyle.getWidth() > 0.0f) {
                    lineWidth = borderStyle.getWidth();
                }
                if (lineWidth > 0.0f && borderColour != null) {
                    if (lineWidth != 1.0f) {
                        contents.setLineWidth(lineWidth);
                    }
                    PDRectangle clipRect = applyPadding(resolveBoundingBox(widget, appearanceStream), Math.max(DEFAULT_PADDING, lineWidth / 2.0f));
                    contents.addRect(clipRect.getLowerLeftX(), clipRect.getLowerLeftY(), clipRect.getWidth(), clipRect.getHeight());
                    contents.closeAndStroke();
                }
            } catch (Throwable th) {
                try {
                    contents.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        contents.close();
    }

    private List<Object> tokenize(PDAppearanceStream appearanceStream) throws IOException {
        return new ContentStreamParser(appearanceStream).tokens();
    }

    private void setAppearanceContent(PDAnnotationWidget widget, PDAppearanceStream appearanceStream) throws IOException {
        this.defaultAppearance.copyNeededResourcesTo(appearanceStream);
        List<Object> tokens = tokenize(appearanceStream);
        ContentStreamWriter writer = new ContentStreamWriter(CountingWritableByteChannel.from(appearanceStream.getCOSObject().createUnfilteredStream()));
        try {
            int bmcIndex = tokens.indexOf(BMC);
            if (bmcIndex == -1) {
                writer.writeTokens(tokens);
                writer.writeTokens(Arrays.asList(COSName.TX, BMC));
            } else {
                writer.writeTokens(tokens.subList(0, bmcIndex + 1));
            }
            insertGeneratedAppearance(widget, appearanceStream, writer);
            int emcIndex = tokens.indexOf(EMC);
            if (emcIndex == -1) {
                writer.writeTokens(EMC);
            } else {
                writer.writeTokens(tokens.subList(emcIndex, tokens.size()));
            }
            writer.close();
        } catch (Throwable th) {
            try {
                writer.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void insertGeneratedAppearance(PDAnnotationWidget widget, PDAppearanceStream appearanceStream, ContentStreamWriter writer) throws IOException {
        float fontCapAtSize;
        float fontDescentAtSize;
        float y;
        PDPageContentStream contents = new PDPageContentStream(this.field.getAcroForm().getDocument(), appearanceStream, writer);
        PDRectangle bbox = resolveBoundingBox(widget, appearanceStream);
        float borderWidth = 0.0f;
        if (widget.getBorderStyle() != null) {
            borderWidth = widget.getBorderStyle().getWidth();
        }
        float padding = Math.max(1.0f, borderWidth);
        PDRectangle clipRect = applyPadding(bbox, padding);
        PDRectangle contentRect = applyPadding(clipRect, padding);
        contents.saveGraphicsState();
        contents.addRect(clipRect.getLowerLeftX(), clipRect.getLowerLeftY(), clipRect.getWidth(), clipRect.getHeight());
        contents.clip();
        PDFont font = (PDFont) Optional.ofNullable(this.field.getAppearanceFont()).or(() -> {
            return Optional.ofNullable(this.defaultAppearance.getFont());
        }).orElse(PDType1Font.HELVETICA());
        RequireUtils.requireNotNullArg(font, "font is null, check whether /DA entry is incomplete or incorrect");
        if (font.getName().contains("+")) {
            LOG.warn("Font '" + this.defaultAppearance.getFontName().getName() + "' of field '" + this.field.getFullyQualifiedName() + "' contains subsetted font '" + font.getName() + "'");
            LOG.warn("This may bring trouble with PDField.setValue(), PDAcroForm.flatten() or PDAcroForm.refreshAppearances()");
            LOG.warn("You should replace this font with a non-subsetted font:");
            LOG.warn("PDFont font = PDType0Font.load(doc, new FileInputStream(fontfile), false);");
            LOG.warn("acroForm.getDefaultResources().put(COSName.getPDFName(\"" + this.defaultAppearance.getFontName().getName() + "\", font);");
        }
        float fontSize = this.defaultAppearance.getFontSize();
        boolean recalculateFontSize = fontSize == 0.0f;
        if ((this.field instanceof PDTextField) && !((PDTextField) this.field).isMultiline()) {
            recalculateFontSize = true;
        }
        if (recalculateFontSize) {
            float newFontSize = calculateFontSize(font, contentRect);
            if (Float.isFinite(newFontSize) && (newFontSize < fontSize || fontSize == 0.0f)) {
                fontSize = newFontSize;
            }
        }
        if (this.field instanceof PDListBox) {
            insertGeneratedListboxSelectionHighlight(contents, appearanceStream, font, fontSize);
        }
        contents.beginText();
        this.defaultAppearance.writeTo(contents, fontSize);
        float fontScaleY = fontSize / 1000.0f;
        float height = font.getBoundingBox().getHeight() * fontScaleY;
        if (font.getFontDescriptor() != null) {
            fontCapAtSize = font.getFontDescriptor().getCapHeight() * fontScaleY;
            fontDescentAtSize = font.getFontDescriptor().getDescent() * fontScaleY;
        } else {
            float fontCapHeight = resolveCapHeight(font);
            float fontDescent = resolveDescent(font);
            LOG.debug("missing font descriptor - resolved Cap/Descent to " + fontCapHeight + "/" + fontDescent);
            fontCapAtSize = fontCapHeight * fontScaleY;
            fontDescentAtSize = fontDescent * fontScaleY;
        }
        if ((this.field instanceof PDTextField) && ((PDTextField) this.field).isMultiline()) {
            y = contentRect.getUpperRightY() - calculateLineHeight(font, fontScaleY);
        } else if (fontCapAtSize > clipRect.getHeight()) {
            y = clipRect.getLowerLeftY() + (-fontDescentAtSize);
        } else {
            y = clipRect.getLowerLeftY() + ((clipRect.getHeight() - fontCapAtSize) / 2.0f) + 1.0f;
            if (y - clipRect.getLowerLeftY() < (-fontDescentAtSize)) {
                float fontDescentBased = (-fontDescentAtSize) + contentRect.getLowerLeftY();
                float fontCapBased = (contentRect.getHeight() - contentRect.getLowerLeftY()) - fontCapAtSize;
                y = Math.min(fontDescentBased, Math.max(y, fontCapBased));
            }
        }
        float x = contentRect.getLowerLeftX();
        if (shallComb()) {
            insertGeneratedCombAppearance(contents, bbox, font, fontSize);
        } else if (this.field instanceof PDListBox) {
            insertGeneratedListboxAppearance(contents, appearanceStream, contentRect, font, fontSize);
        } else {
            PlainText textContent = new PlainText(this.value);
            AppearanceStyle appearanceStyle = new AppearanceStyle();
            appearanceStyle.setFont(font);
            appearanceStyle.setFontSize(fontSize);
            appearanceStyle.setLeading(calculateLineHeight(font, fontScaleY));
            PlainTextFormatter formatter = new PlainTextFormatter.Builder(contents).style(appearanceStyle).text(textContent).width(contentRect.getWidth()).wrapLines(isMultiLine()).initialOffset(x, y).textAlign(getTextAlign(widget)).build();
            formatter.format();
        }
        contents.endTextIfRequired();
        contents.restoreGraphicsState();
    }

    private int getTextAlign(PDAnnotationWidget widget) {
        return widget.getCOSObject().getInt(COSName.Q, this.field.getQ());
    }

    private AffineTransform calculateMatrix(PDRectangle bbox, int rotation) {
        if (rotation == 0) {
            return new AffineTransform();
        }
        float tx = 0.0f;
        float ty = 0.0f;
        switch (rotation) {
            case 90:
                tx = bbox.getUpperRightY();
                break;
            case 180:
                tx = bbox.getUpperRightY();
                ty = bbox.getUpperRightX();
                break;
            case 270:
                ty = bbox.getUpperRightX();
                break;
        }
        Matrix matrix = Matrix.getRotateInstance(Math.toRadians(rotation), tx, ty);
        return matrix.createAffineTransform();
    }

    private boolean isMultiLine() {
        return (this.field instanceof PDTextField) && ((PDTextField) this.field).isMultiline();
    }

    private boolean shallComb() {
        return (!(this.field instanceof PDTextField) || !((PDTextField) this.field).isComb() || ((PDTextField) this.field).getMaxLen() == -1 || ((PDTextField) this.field).isMultiline() || ((PDTextField) this.field).isPassword() || ((PDTextField) this.field).isFileSelect()) ? false : true;
    }

    private void insertGeneratedCombAppearance(PDPageContentStream contents, PDRectangle bbox, PDFont font, float fontSize) throws IOException {
        int maxLen = ((PDTextField) this.field).getMaxLen();
        int quadding = this.field.getQ();
        int numChars = Math.min(this.value.length(), maxLen);
        PDRectangle paddingEdge = applyPadding(bbox, 1.0f);
        float combWidth = bbox.getWidth() / maxLen;
        float ascentAtFontSize = (font.getFontDescriptor().getAscent() / 1000.0f) * fontSize;
        float baselineOffset = paddingEdge.getLowerLeftY() + ((bbox.getHeight() - ascentAtFontSize) / 2.0f);
        float prevCharWidth = 0.0f;
        float xOffset = combWidth / 2.0f;
        contents.saveGraphicsState();
        contents.setFont(font, fontSize);
        if (quadding == 2) {
            xOffset += (maxLen - numChars) * combWidth;
        } else if (quadding == 1) {
            xOffset += ((maxLen - numChars) / 2) * combWidth;
        }
        for (int i = 0; i < numChars; i++) {
            String combString = this.value.substring(i, i + 1);
            float currCharWidth = ((font.getStringWidth(combString) / 1000.0f) * fontSize) / 2.0f;
            contents.newLineAtOffset((xOffset + (prevCharWidth / 2.0f)) - (currCharWidth / 2.0f), baselineOffset);
            contents.showText(combString);
            baselineOffset = 0.0f;
            prevCharWidth = currCharWidth;
            xOffset = combWidth;
        }
        contents.restoreGraphicsState();
    }

    private void insertGeneratedListboxSelectionHighlight(PDPageContentStream contents, PDAppearanceStream appearanceStream, PDFont font, float fontSize) throws IOException {
        PDListBox listBox = (PDListBox) this.field;
        List<Integer> indexEntries = listBox.getSelectedOptionsIndex();
        List<String> values = listBox.getValue();
        List<String> options = listBox.getOptionsExportValues();
        if (!values.isEmpty() && !options.isEmpty() && indexEntries.isEmpty()) {
            indexEntries = new ArrayList(values.size());
            for (String v : values) {
                indexEntries.add(Integer.valueOf(options.indexOf(v)));
            }
        }
        int topIndex = listBox.getTopIndex();
        float highlightBoxHeight = (font.getBoundingBox().getHeight() * fontSize) / 1000.0f;
        PDRectangle paddingEdge = applyPadding(appearanceStream.getBBox(), 1.0f);
        Iterator<Integer> it = indexEntries.iterator();
        while (it.hasNext()) {
            int selectedIndex = it.next().intValue();
            contents.setNonStrokingColor(HIGHLIGHT_COLOR[0], HIGHLIGHT_COLOR[1], HIGHLIGHT_COLOR[2]);
            contents.addRect(paddingEdge.getLowerLeftX(), (paddingEdge.getUpperRightY() - (highlightBoxHeight * ((selectedIndex - topIndex) + 1))) + 2.0f, paddingEdge.getWidth(), highlightBoxHeight);
            contents.fill();
        }
        contents.setNonStrokingColor(0.0d);
    }

    private void insertGeneratedListboxAppearance(PDPageContentStream contents, PDAppearanceStream appearanceStream, PDRectangle contentRect, PDFont font, float fontSize) throws IOException {
        contents.setNonStrokingColor(0.0d);
        int q = this.field.getQ();
        if (q == 1 || q == 2) {
            float fieldWidth = appearanceStream.getBBox().getWidth();
            float stringWidth = (font.getStringWidth(this.value) / 1000.0f) * fontSize;
            float adjustAmount = (fieldWidth - stringWidth) - 4.0f;
            if (q == 1) {
                adjustAmount /= 2.0f;
            }
            contents.newLineAtOffset(adjustAmount, 0.0f);
        } else if (q != 0) {
            throw new IOException("Error: Unknown justification value:" + q);
        }
        List<String> options = ((PDListBox) this.field).getOptionsDisplayValues();
        int numOptions = options.size();
        float yTextPos = contentRect.getUpperRightY();
        int topIndex = ((PDListBox) this.field).getTopIndex();
        float ascent = font.getFontDescriptor().getAscent();
        float height = font.getBoundingBox().getHeight();
        for (int i = topIndex; i < numOptions; i++) {
            if (i == topIndex) {
                yTextPos -= (ascent / 1000.0f) * fontSize;
            } else {
                yTextPos -= (height / 1000.0f) * fontSize;
                contents.beginText();
            }
            contents.newLineAtOffset(contentRect.getLowerLeftX(), yTextPos);
            contents.setFont(font, fontSize);
            contents.showText(replaceTabChars(options.get(i)));
            if (i != numOptions - 1) {
                contents.endText();
            }
        }
    }

    private String replaceTabChars(String input) {
        return input.replace("\t", "    ");
    }

    private float calculateLineHeight(PDFont font, float fontScaleY) throws IOException {
        float fontCapAtSize;
        float fontDescentAtSize;
        float fontBoundingBoxAtSize = font.getBoundingBox().getHeight() * fontScaleY;
        if (font.getFontDescriptor() != null) {
            fontCapAtSize = font.getFontDescriptor().getCapHeight() * fontScaleY;
            fontDescentAtSize = font.getFontDescriptor().getDescent() * fontScaleY;
        } else {
            float fontCapHeight = resolveCapHeight(font);
            float fontDescent = resolveDescent(font);
            LOG.debug("missing font descriptor - resolved Cap/Descent to " + fontCapHeight + "/" + fontDescent);
            fontCapAtSize = fontCapHeight * fontScaleY;
            fontDescentAtSize = fontDescent * fontScaleY;
        }
        float lineHeight = fontCapAtSize - fontDescentAtSize;
        if (lineHeight < 0.0f) {
            lineHeight = fontBoundingBoxAtSize;
        }
        return lineHeight;
    }

    float calculateFontSize(PDFont font, PDRectangle contentRect) throws IOException {
        float yScalingFactor = 1000.0f * font.getFontMatrix().getScaleY();
        float xScalingFactor = 1000.0f * font.getFontMatrix().getScaleX();
        if (isMultiLine()) {
            float lineHeight = calculateLineHeight(font, font.getFontMatrix().getScaleY());
            float scaledContentHeight = contentRect.getHeight() * yScalingFactor;
            boolean looksLikeFauxMultiline = calculateLineHeight(font, 0.012f) > scaledContentHeight;
            int userTypedLinesCount = new PlainText(this.value).getParagraphs().size();
            boolean userTypedMultipleLines = userTypedLinesCount > 1;
            if (looksLikeFauxMultiline && !userTypedMultipleLines) {
                LOG.warn("Faux multiline field found: {}", this.field.getFullyQualifiedName());
            } else {
                int minimumLinesToFitInAMultilineField = Math.max(2, userTypedLinesCount);
                float fontSize = scaledContentHeight / (minimumLinesToFitInAMultilineField * lineHeight);
                return Math.min(fontSize, DEFAULT_FONT_SIZE);
            }
        }
        float width = font.getStringWidth(this.value) * font.getFontMatrix().getScaleX();
        float widthBasedFontSize = (contentRect.getWidth() / width) * xScalingFactor;
        float height = calculateLineHeight(font, font.getFontMatrix().getScaleY());
        float heightBasedFontSize = (contentRect.getHeight() / height) * yScalingFactor;
        return Math.min(heightBasedFontSize, widthBasedFontSize);
    }

    private float resolveCapHeight(PDFont font) throws IOException {
        return resolveGlyphHeight(font, StandardStructureTypes.H.codePointAt(0));
    }

    private float resolveDescent(PDFont font) throws IOException {
        return resolveGlyphHeight(font, OperatorName.CURVE_TO_REPLICATE_FINAL_POINT.codePointAt(0)) - resolveGlyphHeight(font, PDPageLabelRange.STYLE_LETTERS_LOWER.codePointAt(0));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private float resolveGlyphHeight(PDFont pDFont, int code2) throws IOException {
        GeneralPath path = null;
        if (pDFont instanceof PDType3Font) {
            PDType3Font t3Font = (PDType3Font) pDFont;
            PDType3CharProc charProc = t3Font.getCharProc(code2);
            if (charProc != null) {
                BoundingBox fontBBox = t3Font.getBoundingBox();
                PDRectangle glyphBBox = charProc.getGlyphBBox();
                if (glyphBBox != null) {
                    glyphBBox.setLowerLeftX(Math.max(fontBBox.getLowerLeftX(), glyphBBox.getLowerLeftX()));
                    glyphBBox.setLowerLeftY(Math.max(fontBBox.getLowerLeftY(), glyphBBox.getLowerLeftY()));
                    glyphBBox.setUpperRightX(Math.min(fontBBox.getUpperRightX(), glyphBBox.getUpperRightX()));
                    glyphBBox.setUpperRightY(Math.min(fontBBox.getUpperRightY(), glyphBBox.getUpperRightY()));
                    path = glyphBBox.toGeneralPath();
                }
            }
        } else if (pDFont instanceof PDVectorFont) {
            PDVectorFont vectorFont = (PDVectorFont) pDFont;
            path = vectorFont.getPath(code2);
        } else if (pDFont instanceof PDSimpleFont) {
            PDSimpleFont simpleFont = (PDSimpleFont) pDFont;
            String name = simpleFont.getEncoding().getName(code2);
            path = simpleFont.getPath(name);
        } else {
            LOG.warn("Unknown font class: " + pDFont.getClass());
        }
        if (path == null) {
            return -1.0f;
        }
        return (float) path.getBounds2D().getHeight();
    }

    private PDRectangle resolveBoundingBox(PDAnnotationWidget fieldWidget, PDAppearanceStream appearanceStream) {
        PDRectangle boundingBox = appearanceStream.getBBox();
        if (boundingBox == null || hasZeroDimensions(boundingBox)) {
            boundingBox = fieldWidget.getRectangle().createRetranslatedRectangle();
        }
        return boundingBox;
    }

    private boolean hasZeroDimensions(PDRectangle bbox) {
        return bbox.getHeight() == 0.0f || bbox.getWidth() == 0.0f;
    }

    private PDRectangle applyPadding(PDRectangle box, float padding) {
        return new PDRectangle(box.getLowerLeftX() + padding, box.getLowerLeftY() + padding, box.getWidth() - (2.0f * padding), box.getHeight() - (2.0f * padding));
    }
}

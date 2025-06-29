package org.sejda.sambox.pdmodel.interactive.form;

import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import org.sejda.sambox.pdmodel.font.PDFont;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PlainText.class */
class PlainText {
    private static final float FONTSCALE = 1000.0f;
    private final ArrayList<Paragraph> paragraphs = new ArrayList<>();

    PlainText(String textValue) {
        String[] parts = textValue.replace("\t", " ").split("\\r\\n|\\n|\\r|\\u2028|\\u2029");
        for (String part : parts) {
            if (part.length() == 0) {
                part = " ";
            }
            this.paragraphs.add(new Paragraph(part));
        }
        if (this.paragraphs.isEmpty()) {
            this.paragraphs.add(new Paragraph(""));
        }
    }

    PlainText(List<String> listValue) {
        for (String part : listValue) {
            this.paragraphs.add(new Paragraph(part));
        }
    }

    List<Paragraph> getParagraphs() {
        return this.paragraphs;
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PlainText$TextAttribute.class */
    static class TextAttribute extends AttributedCharacterIterator.Attribute {
        private static final long serialVersionUID = -3138885145941283005L;
        public static final AttributedCharacterIterator.Attribute WIDTH = new TextAttribute("width");

        protected TextAttribute(String name) {
            super(name);
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PlainText$Paragraph.class */
    static class Paragraph {
        private String textContent;

        Paragraph(String text) {
            this.textContent = text;
        }

        String getText() {
            return this.textContent;
        }

        List<Line> getLines(PDFont font, float fontSize, float width) throws IOException {
            String substring;
            float substringWidth;
            BreakIterator iterator = BreakIterator.getLineInstance();
            iterator.setText(this.textContent);
            float scale = fontSize / PlainText.FONTSCALE;
            int start = iterator.first();
            int end = iterator.next();
            float lineWidth = 0.0f;
            List<Line> textLines = new ArrayList<>();
            Line textLine = new Line();
            while (end != -1) {
                String word = this.textContent.substring(start, end);
                float wordWidth = font.getStringWidth(word) * scale;
                boolean wordNeedsSplit = false;
                int splitOffset = end - start;
                lineWidth += wordWidth;
                if (lineWidth >= width && Character.isWhitespace(word.charAt(word.length() - 1))) {
                    float whitespaceWidth = font.getStringWidth(word.substring(word.length() - 1)) * scale;
                    lineWidth -= whitespaceWidth;
                }
                if (lineWidth >= width && !textLine.getWords().isEmpty()) {
                    textLine.setWidth(textLine.calculateWidth(font, fontSize));
                    textLines.add(textLine);
                    textLine = new Line();
                    lineWidth = font.getStringWidth(word) * scale;
                }
                if (wordWidth > width && textLine.getWords().isEmpty() && word.length() > 1 && width > 10.0f) {
                    wordNeedsSplit = true;
                    do {
                        splitOffset--;
                        substring = word.substring(0, splitOffset);
                        substringWidth = font.getStringWidth(substring) * scale;
                    } while (substringWidth >= width);
                    word = substring;
                    wordWidth = font.getStringWidth(word) * scale;
                    lineWidth = wordWidth;
                }
                AttributedString as = new AttributedString(word);
                as.addAttribute(TextAttribute.WIDTH, Float.valueOf(wordWidth));
                Word wordInstance = new Word(word);
                wordInstance.setAttributes(as);
                textLine.addWord(wordInstance);
                if (wordNeedsSplit) {
                    start += splitOffset;
                } else {
                    start = end;
                    end = iterator.next();
                }
            }
            textLine.setWidth(textLine.calculateWidth(font, fontSize));
            textLines.add(textLine);
            return textLines;
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PlainText$Line.class */
    static class Line {
        private List<Word> words = new ArrayList();
        private float lineWidth;

        Line() {
        }

        float getWidth() {
            return this.lineWidth;
        }

        void setWidth(float width) {
            this.lineWidth = width;
        }

        float calculateWidth(PDFont font, float fontSize) throws IOException {
            float scale = fontSize / PlainText.FONTSCALE;
            float calculatedWidth = 0.0f;
            int indexOfWord = 0;
            for (Word word : this.words) {
                calculatedWidth += ((Float) word.getAttributes().getIterator().getAttribute(TextAttribute.WIDTH)).floatValue();
                String text = word.getText();
                if (indexOfWord == this.words.size() - 1 && Character.isWhitespace(text.charAt(text.length() - 1))) {
                    float whitespaceWidth = font.getStringWidth(text.substring(text.length() - 1)) * scale;
                    calculatedWidth -= whitespaceWidth;
                }
                indexOfWord++;
            }
            return calculatedWidth;
        }

        List<Word> getWords() {
            return this.words;
        }

        float getInterWordSpacing(float width) {
            return (width - this.lineWidth) / (this.words.size() - 1);
        }

        void addWord(Word word) {
            this.words.add(word);
        }
    }

    /* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/form/PlainText$Word.class */
    static class Word {
        private AttributedString attributedString;
        private String textContent;

        Word(String text) {
            this.textContent = text;
        }

        String getText() {
            return this.textContent;
        }

        AttributedString getAttributes() {
            return this.attributedString;
        }

        void setAttributes(AttributedString as) {
            this.attributedString = as;
        }
    }
}

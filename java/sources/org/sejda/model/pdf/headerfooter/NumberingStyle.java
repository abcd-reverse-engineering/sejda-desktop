package org.sejda.model.pdf.headerfooter;

import org.sejda.model.FriendlyNamed;
import org.sejda.model.util.RomanNumbersUtils;

/* loaded from: org.sejda.sejda-model-5.1.10.jar:org/sejda/model/pdf/headerfooter/NumberingStyle.class */
public enum NumberingStyle implements FriendlyNamed {
    ARABIC("arabic") { // from class: org.sejda.model.pdf.headerfooter.NumberingStyle.1
        @Override // org.sejda.model.pdf.headerfooter.NumberingStyle
        public String toStyledString(int number) {
            return Integer.toString(number);
        }
    },
    EMPTY("empty") { // from class: org.sejda.model.pdf.headerfooter.NumberingStyle.2
        @Override // org.sejda.model.pdf.headerfooter.NumberingStyle
        public String toStyledString(int number) {
            return "";
        }
    },
    ROMAN("roman") { // from class: org.sejda.model.pdf.headerfooter.NumberingStyle.3
        @Override // org.sejda.model.pdf.headerfooter.NumberingStyle
        public String toStyledString(int number) {
            return RomanNumbersUtils.toRoman(number);
        }
    };

    private final String displayName;

    public abstract String toStyledString(int number);

    NumberingStyle(String displayName) {
        this.displayName = displayName;
    }

    @Override // org.sejda.model.FriendlyNamed
    public String getFriendlyName() {
        return this.displayName;
    }
}

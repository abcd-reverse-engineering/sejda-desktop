package org.sejda.core.support.prefix.processor;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.sambox.pdmodel.documentinterchange.taggedpdf.PDLayoutAttributeObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/processor/NumberPrefixProcessor.class */
abstract class NumberPrefixProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(NumberPrefixProcessor.class);
    protected final Pattern pattern;

    NumberPrefixProcessor(String prefix) {
        RequireUtils.requireNotBlank(prefix, "Prefix cannot be blank");
        this.pattern = Pattern.compile(String.format("\\[%s(#*)(-?[0-9]*)\\]", prefix));
    }

    protected String findAndReplace(String inputString, Integer num) {
        StringBuilder sb = new StringBuilder();
        Matcher m = this.pattern.matcher(inputString);
        while (m.find()) {
            String replacement = getReplacement(m.group(1), m.group(2), num);
            m.appendReplacement(sb, replacement);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String getReplacement(String numberPatter, String startingNumber, Integer num) {
        Integer number = getReplacementNumber(startingNumber, num);
        if (StringUtils.isNotBlank(numberPatter)) {
            return formatter(numberPatter).format(number);
        }
        return number.toString();
    }

    private Integer getReplacementNumber(String startingNumber, Integer num) {
        if (StringUtils.isNotBlank(startingNumber)) {
            return Integer.valueOf(num.intValue() + Integer.parseInt(startingNumber));
        }
        return num;
    }

    private DecimalFormat formatter(String numberPattern) {
        try {
            if (StringUtils.isNotBlank(numberPattern)) {
                return new DecimalFormat(numberPattern.replaceAll("#", PDLayoutAttributeObject.GLYPH_ORIENTATION_VERTICAL_ZERO_DEGREES));
            }
        } catch (IllegalArgumentException iae) {
            LOG.error(String.format("Error applying pattern %s", numberPattern), iae);
        }
        return new DecimalFormat("00000");
    }
}

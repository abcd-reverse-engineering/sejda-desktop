package org.sejda.model.pro.pdf;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.pdf.headerfooter.NumberingStyle;

/* loaded from: org.sejda.sejda-model-pro-5.1.10.1.jar:org/sejda/model/pro/pdf/TextStampPattern.class */
public class TextStampPattern {
    private int currentPage;
    private int totalPages;
    private String batesSeq;
    private String fileSeq;
    private String filename;

    public TextStampPattern withPage(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        return this;
    }

    public TextStampPattern withBatesSequence(String batesSeq) {
        this.batesSeq = batesSeq;
        return this;
    }

    public TextStampPattern withFileSequence(String fileSeq) {
        this.fileSeq = fileSeq;
        return this;
    }

    public TextStampPattern withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public String build(String pattern) {
        String result = pattern;
        if (this.currentPage >= 0) {
            result = StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(result, "[PAGE_ROMAN]", NumberingStyle.ROMAN.toStyledString(this.currentPage)), "[PAGE_ARABIC]", NumberingStyle.ARABIC.toStyledString(this.currentPage)), "[PAGE_NUMBER]", NumberingStyle.ARABIC.toStyledString(this.currentPage)), "[TOTAL_PAGES_ROMAN]", NumberingStyle.ROMAN.toStyledString(this.totalPages)), "[TOTAL_PAGES_ARABIC]", NumberingStyle.ARABIC.toStyledString(this.totalPages)), "[TOTAL_PAGES]", NumberingStyle.ARABIC.toStyledString(this.totalPages)), "[PAGE_OF_TOTAL]", String.format("%d of %d", Integer.valueOf(this.currentPage), Integer.valueOf(this.totalPages)));
        }
        String result2 = StringUtils.replace(result, "[DATE]", dateNow());
        if (this.filename != null) {
            result2 = StringUtils.replace(result2, "[BASE_NAME]", FilenameUtils.getBaseName(this.filename));
        }
        if (this.batesSeq != null) {
            result2 = StringUtils.replace(result2, "[BATES_NUMBER]", this.batesSeq);
        }
        if (this.fileSeq != null) {
            result2 = StringUtils.replace(result2, "[FILE_NUMBER]", this.fileSeq);
        }
        return result2;
    }

    public static String dateNow() {
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }
}

package org.sejda.model.pro.pdf;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.sejda.model.pdf.headerfooter.NumberingStyle;

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
         result = StringUtils.replace(pattern, "[PAGE_ROMAN]", NumberingStyle.ROMAN.toStyledString(this.currentPage));
         result = StringUtils.replace(result, "[PAGE_ARABIC]", NumberingStyle.ARABIC.toStyledString(this.currentPage));
         result = StringUtils.replace(result, "[PAGE_NUMBER]", NumberingStyle.ARABIC.toStyledString(this.currentPage));
         result = StringUtils.replace(result, "[TOTAL_PAGES_ROMAN]", NumberingStyle.ROMAN.toStyledString(this.totalPages));
         result = StringUtils.replace(result, "[TOTAL_PAGES_ARABIC]", NumberingStyle.ARABIC.toStyledString(this.totalPages));
         result = StringUtils.replace(result, "[TOTAL_PAGES]", NumberingStyle.ARABIC.toStyledString(this.totalPages));
         result = StringUtils.replace(result, "[PAGE_OF_TOTAL]", String.format("%d of %d", this.currentPage, this.totalPages));
      }

      result = StringUtils.replace(result, "[DATE]", dateNow());
      if (this.filename != null) {
         result = StringUtils.replace(result, "[BASE_NAME]", FilenameUtils.getBaseName(this.filename));
      }

      if (this.batesSeq != null) {
         result = StringUtils.replace(result, "[BATES_NUMBER]", this.batesSeq);
      }

      if (this.fileSeq != null) {
         result = StringUtils.replace(result, "[FILE_NUMBER]", this.fileSeq);
      }

      return result;
   }

   public static String dateNow() {
      return (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
   }
}

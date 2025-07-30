module sejda.fonts.pro {
   requires org.sejda.model;

   exports org.sejda.fonts.pro;

   provides org.sejda.model.pdf.font.Type0FontsProvider with
      org.sejda.fonts.pro.ProUnicodeType0FontsProvider;
}

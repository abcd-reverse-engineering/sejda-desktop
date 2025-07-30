open module org.sejda.model.pro {
   requires org.apache.commons.io;
   requires org.apache.commons.lang3;
   requires org.sejda.commons;
   requires jakarta.validation;
   requires java.desktop;
   requires org.sejda.model;

   exports org.sejda.model.pro;
   exports org.sejda.model.pro.nup;
   exports org.sejda.model.pro.optimization;
   exports org.sejda.model.pro.parameter;
   exports org.sejda.model.pro.parameter.excel;
   exports org.sejda.model.pro.pdf;
   exports org.sejda.model.pro.pdf.collection;
   exports org.sejda.model.pro.pdf.encryption;
   exports org.sejda.model.pro.pdf.numbering;
   exports org.sejda.model.pro.split;
   exports org.sejda.model.pro.validation.constraint;
   exports org.sejda.model.pro.validation.validator;
   exports org.sejda.model.pro.watermark;
}

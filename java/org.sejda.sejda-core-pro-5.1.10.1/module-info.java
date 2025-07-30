module org.sejda.core.pro {
   requires org.slf4j;
   requires org.sejda.core;
   requires org.apache.commons.lang3;
   requires org.sejda.commons;

   exports org.sejda.core.pro;
   exports org.sejda.core.pro.support.prefix.processor;

   provides org.sejda.core.support.prefix.processor.PrefixProcessor with
      org.sejda.core.pro.support.prefix.processor.TextPrefixProcessor;
}

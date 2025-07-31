module org.sejda.impl.sambox.pro {
    requires java.base mandated;
    requires java.xml;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.sejda.commons;
    requires org.sejda.io;
    requires org.slf4j;
    requires net.coobird.thumbnailator;
    requires transitive java.desktop;
    requires transitive org.apache.fontbox;
    requires transitive org.sejda.core;
    requires transitive org.sejda.impl.sambox;
    requires transitive org.sejda.model;
    requires transitive org.sejda.sambox;
    requires transitive org.sejda.model.pro;
    requires com.github.romankh3.image.comparison;
    
    exports org.sejda.impl.sambox.pro;
    exports org.sejda.impl.sambox.pro.util;
    
}

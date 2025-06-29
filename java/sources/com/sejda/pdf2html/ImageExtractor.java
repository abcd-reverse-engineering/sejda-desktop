package com.sejda.pdf2html;

import com.sejda.pdf2html.pojo.Image;
import java.util.List;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;

/* loaded from: com.sejda.pdf2html.pdf2html-0.0.72.jar:com/sejda/pdf2html/ImageExtractor.class */
public interface ImageExtractor {
    ImageExtractor processDocument(PDDocument pDDocument) throws Exception;

    List<Image> pageImages(PDPage pDPage);
}

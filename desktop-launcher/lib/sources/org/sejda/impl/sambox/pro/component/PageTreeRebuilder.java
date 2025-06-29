package org.sejda.impl.sambox.pro.component;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.PDPageTree;
import org.sejda.sambox.pdmodel.common.PDStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-pro-5.1.10.1.jar:org/sejda/impl/sambox/pro/component/PageTreeRebuilder.class */
public class PageTreeRebuilder {
    private static final Logger LOG = LoggerFactory.getLogger(PageTreeRebuilder.class);
    private PDDocument document;

    public PageTreeRebuilder(PDDocument document) {
        this.document = document;
    }

    public void rebuild() {
        LOG.info("Rebuilding page tree");
        this.document.getPages().streamNodes().forEach(n -> {
            if (PDPageTree.isPageTreeNode(n)) {
                n.setItem(COSName.TYPE, (COSBase) COSName.PAGES);
                n.setInt(COSName.COUNT, 0);
                n.setItem(COSName.KIDS, (COSBase) new COSArray());
                COSDictionary parent = getAndRepairParent(n);
                if (Objects.nonNull(parent)) {
                    ((COSArray) parent.getDictionaryObject(COSName.KIDS, COSArray.class)).add((COSBase) n);
                    return;
                }
                return;
            }
            if (isPage(n)) {
                cleanContentsArray(n);
                if (canDecodeContents(n)) {
                    n.setItem(COSName.TYPE, (COSBase) COSName.PAGE);
                    COSDictionary parent2 = getAndRepairParent(n);
                    ((COSArray) parent2.getDictionaryObject(COSName.KIDS, COSArray.class)).add((COSBase) n);
                    while (Objects.nonNull(parent2)) {
                        parent2.setInt(COSName.COUNT, parent2.getInt(COSName.COUNT) + 1);
                        parent2 = getAndRepairParent(parent2);
                    }
                }
            }
        });
    }

    void cleanContentsArray(COSDictionary page) {
        COSArray contents = (COSArray) page.getDictionaryObject(COSName.CONTENTS, COSArray.class);
        if (Objects.nonNull(contents)) {
            COSArray newContents = new COSArray();
            Stream streamFilter = contents.stream().map((v0) -> {
                return v0.getCOSObject();
            }).filter(s -> {
                return s instanceof COSStream;
            });
            Objects.requireNonNull(newContents);
            streamFilter.forEach(newContents::add);
            page.setItem(COSName.CONTENTS, (COSBase) newContents);
        }
    }

    static boolean isPage(COSDictionary page) {
        if (Objects.isNull(page.getCOSName(COSName.TYPE))) {
            LOG.warn("Missing required 'Page' type for page");
            return Objects.nonNull(getAndRepairParent(page));
        }
        return COSName.PAGE.equals(page.getCOSName(COSName.TYPE));
    }

    static boolean canDecodeContents(COSDictionary page) {
        try {
            Iterator<PDStream> iter = new PDPage(page).getContentStreams();
            while (iter.hasNext()) {
                COSStream stream = iter.next().getCOSObject();
                stream.getUnfilteredStream();
                stream.unDecode();
            }
            return true;
        } catch (Exception e) {
            LOG.warn("Cannot decode page stream, skipping page.", e);
            return false;
        }
    }

    static COSDictionary getAndRepairParent(COSDictionary child) {
        COSDictionary parent = (COSDictionary) child.getDictionaryObject(COSName.PARENT, COSDictionary.class);
        if (Objects.isNull(parent)) {
            parent = (COSDictionary) child.getDictionaryObject(COSName.P, COSDictionary.class);
            if (Objects.nonNull(parent)) {
                child.removeItem(COSName.P);
                child.setItem(COSName.PARENT, (COSBase) parent);
            }
        }
        return parent;
    }
}

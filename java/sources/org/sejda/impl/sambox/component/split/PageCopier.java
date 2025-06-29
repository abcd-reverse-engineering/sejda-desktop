package org.sejda.impl.sambox.component.split;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.sejda.impl.sambox.component.optimization.NameResourcesDuplicator;
import org.sejda.impl.sambox.component.optimization.ResourceDictionaryCleaner;
import org.sejda.impl.sambox.component.optimization.ResourcesHitter;
import org.sejda.sambox.cos.COSArray;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDPage;
import org.sejda.sambox.pdmodel.interactive.annotation.PDAnnotationPopup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/PageCopier.class */
class PageCopier {
    private static final Logger LOG = LoggerFactory.getLogger(PageCopier.class);
    private boolean optimize;
    private Consumer<PDPage> hitAndClean;

    public PageCopier(boolean optimize) {
        Consumer<PDPage> consumerAndThen = new NameResourcesDuplicator().andThen(new ResourcesHitter());
        ResourceDictionaryCleaner resourceDictionaryCleaner = new ResourceDictionaryCleaner();
        this.hitAndClean = consumerAndThen.andThen(resourceDictionaryCleaner::clean);
        this.optimize = optimize;
    }

    public PDPage copyOf(PDPage page) {
        PDPage copy = new PDPage(page.getCOSObject().duplicate());
        copy.setCropBox(page.getCropBox());
        copy.setMediaBox(page.getMediaBox());
        copy.setResources(page.getResources());
        copy.setRotation(page.getRotation());
        copy.getCOSObject().removeItem(COSName.B);
        COSArray annots = (COSArray) page.getCOSObject().getDictionaryObject(COSName.ANNOTS, COSArray.class);
        if (Objects.nonNull(annots)) {
            COSArray cleanedAnnotationsCopy = new COSArray();
            annots.stream().map((v0) -> {
                return v0.getCOSObject();
            }).filter(d -> {
                return d instanceof COSDictionary;
            }).map(d2 -> {
                return (COSDictionary) d2;
            }).map((v0) -> {
                return v0.duplicate();
            }).forEach(a -> {
                a.removeItem(COSName.P);
                a.removeItem(COSName.DEST);
                a.removeItem(COSName.getPDFName(PDAnnotationPopup.SUB_TYPE));
                a.removeItem(COSName.PARENT);
                if (((Boolean) Optional.ofNullable((COSDictionary) a.getDictionaryObject(COSName.A, COSDictionary.class)).map(d3 -> {
                    return Boolean.valueOf(d3.containsKey(COSName.D));
                }).orElse(false)).booleanValue()) {
                    a.removeItem(COSName.A);
                }
                cleanedAnnotationsCopy.add((COSBase) a);
            });
            copy.getCOSObject().setItem(COSName.ANNOTS, (COSBase) cleanedAnnotationsCopy);
        }
        if (this.optimize) {
            this.hitAndClean.accept(copy);
        }
        duplicatePageStreams(page, copy);
        copy.sanitizeDictionary();
        return copy;
    }

    private void duplicatePageStreams(PDPage page, PDPage copy) {
        COSStream stream = (COSStream) page.getCOSObject().getDictionaryObject(COSName.CONTENTS, COSStream.class);
        if (Objects.nonNull(stream)) {
            copy.getCOSObject().setItem(COSName.CONTENTS, (COSBase) new MockPageStream(stream));
            return;
        }
        COSArray streams = (COSArray) page.getCOSObject().getDictionaryObject(COSName.CONTENTS, COSArray.class);
        if (Objects.nonNull(streams)) {
            COSArray streamsCopy = new COSArray();
            Stream streamFilter = streams.stream().map((v0) -> {
                return v0.getCOSObject();
            }).filter(s -> {
                return s instanceof COSStream;
            });
            Class<COSStream> cls = COSStream.class;
            Objects.requireNonNull(COSStream.class);
            Stream map = streamFilter.map((v1) -> {
                return r1.cast(v1);
            }).map(x$0 -> {
                return new MockPageStream(x$0);
            });
            Objects.requireNonNull(streamsCopy);
            map.forEach((v1) -> {
                r1.add(v1);
            });
            copy.getCOSObject().setItem(COSName.CONTENTS, (COSBase) streamsCopy);
        }
    }

    /* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/split/PageCopier$MockPageStream.class */
    private class MockPageStream extends COSStream {
        private long length;

        private MockPageStream(COSStream original) {
            super(original.duplicate());
            this.length = 0L;
            try {
                this.length = original.getFilteredLength();
            } catch (IOException e) {
                PageCopier.LOG.error("An error occurred while calculating the COSStream length", e);
            }
        }

        @Override // org.sejda.sambox.cos.COSStream
        public long getFilteredLength() throws IOException {
            return this.length;
        }
    }
}

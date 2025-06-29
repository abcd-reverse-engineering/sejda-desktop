package org.sejda.impl.sambox.component.optimization;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.sejda.impl.sambox.component.ReadOnlyFilteredCOSStream;
import org.sejda.sambox.cos.COSBase;
import org.sejda.sambox.cos.COSDictionary;
import org.sejda.sambox.cos.COSName;
import org.sejda.sambox.cos.COSStream;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/optimization/ResourceDictionaryCleaner.class */
public class ResourceDictionaryCleaner implements Consumer<PDDocument> {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceDictionaryCleaner.class);

    @Override // java.util.function.Consumer
    public void accept(PDDocument p) {
        LOG.debug("Cleaning resource dictionaries from unused resources");
        clean(() -> {
            return p.getPages().streamNodes();
        });
    }

    public void clean(PDPage page) {
        clean(() -> {
            return Stream.of(page.getCOSObject());
        });
    }

    private void clean(Supplier<Stream<COSDictionary>> nodes) {
        Supplier<Stream<COSDictionary>> resources = () -> {
            return ((Stream) nodes.get()).map(d -> {
                return (COSDictionary) d.getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
            }).filter((v0) -> {
                return Objects.nonNull(v0);
            });
        };
        cleanResources((Set) resources.get().collect(Collectors.toSet()));
        Stream<COSDictionary> formsResources = resources.get().map(d -> {
            return (COSDictionary) d.getDictionaryObject(COSName.XOBJECT, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).flatMap(d2 -> {
            return d2.getValues().stream();
        }).filter(d3 -> {
            return d3.getCOSObject() instanceof COSDictionary;
        }).map(d4 -> {
            return (COSDictionary) d4.getCOSObject();
        }).filter(d5 -> {
            return COSName.FORM.equals(d5.getCOSName(COSName.SUBTYPE));
        }).map(d6 -> {
            return (COSDictionary) d6.getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        });
        Stream<COSDictionary> softmaskResources = resources.get().map(d7 -> {
            return (COSDictionary) d7.getDictionaryObject(COSName.EXT_G_STATE, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).flatMap(d8 -> {
            return d8.getValues().stream();
        }).filter(d9 -> {
            return d9.getCOSObject() instanceof COSDictionary;
        }).map(d10 -> {
            return (COSDictionary) ((COSDictionary) d10.getCOSObject()).getDictionaryObject(COSName.SMASK, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).map(d11 -> {
            return (COSDictionary) d11.getDictionaryObject(COSName.G, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).map(d12 -> {
            return (COSDictionary) d12.getDictionaryObject(COSName.RESOURCES, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        });
        cleanResources((Set) Stream.of((Object[]) new Stream[]{formsResources, softmaskResources}).flatMap(s -> {
            return s;
        }).collect(Collectors.toSet()));
    }

    private void cleanResources(Set<COSDictionary> resources) {
        LOG.trace("Found {} distinct resource dictionaries to clean", Integer.valueOf(resources.size()));
        cleanXObject(resources.stream().map(d -> {
            return (COSDictionary) d.getDictionaryObject(COSName.XOBJECT, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }));
        cleanUnused(resources.stream().map(d2 -> {
            return (COSDictionary) d2.getDictionaryObject(COSName.FONT, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }), COSName.FONT);
        cleanUnused(resources.stream().map(d3 -> {
            return (COSDictionary) d3.getDictionaryObject(COSName.EXT_G_STATE, COSDictionary.class);
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }), COSName.EXT_G_STATE);
    }

    private void cleanXObject(Stream<COSDictionary> xobjects) {
        xobjects.forEach(x -> {
            Set<COSName> toRemove = (Set) x.entrySet().stream().filter(e -> {
                return !(((COSBase) e.getValue()).getCOSObject() instanceof ReadOnlyFilteredCOSStream);
            }).filter(e2 -> {
                return ((COSBase) e2.getValue()).getCOSObject() instanceof COSStream;
            }).map((v0) -> {
                return v0.getKey();
            }).collect(Collectors.toSet());
            LOG.trace("Removing {} unused {} from {}", new Object[]{Integer.valueOf(toRemove.size()), COSName.XOBJECT.getName(), x});
            Objects.requireNonNull(x);
            toRemove.forEach(x::removeItem);
        });
    }

    private void cleanUnused(Stream<COSDictionary> resources, COSName type) {
        resources.forEach(f -> {
            Set<COSName> toRemove = (Set) f.entrySet().stream().filter(e -> {
                return !(((COSBase) e.getValue()).getCOSObject() instanceof InUseDictionary);
            }).map((v0) -> {
                return v0.getKey();
            }).collect(Collectors.toSet());
            LOG.trace("Removing {} unused {}", Integer.valueOf(toRemove.size()), type.getName());
            Objects.requireNonNull(f);
            toRemove.forEach(f::removeItem);
        });
    }
}

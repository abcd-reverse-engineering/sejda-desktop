package org.sejda.sambox.pdmodel.interactive.documentnavigation.destination;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import org.sejda.sambox.cos.COSObjectable;
import org.sejda.sambox.pdmodel.PDDocumentCatalog;
import org.sejda.sambox.pdmodel.interactive.action.PDAction;
import org.sejda.sambox.pdmodel.interactive.action.PDActionGoTo;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/pdmodel/interactive/documentnavigation/destination/WithActionOrDestination.class */
public interface WithActionOrDestination extends COSObjectable {
    PDDestination getDestination() throws IOException;

    PDAction getAction();

    default Optional<PDPageDestination> resolveToPageDestination(PDDocumentCatalog catalog) throws IOException {
        PDDestination destination = getDestination();
        if (Objects.isNull(destination)) {
            PDAction action = getAction();
            if (action instanceof PDActionGoTo) {
                destination = ((PDActionGoTo) action).getDestination();
            }
        }
        if (Objects.nonNull(catalog) && (destination instanceof PDNamedDestination)) {
            return Optional.ofNullable(catalog.findNamedDestinationPage((PDNamedDestination) destination));
        }
        Optional optionalOfNullable = Optional.ofNullable(destination);
        Class<PDPageDestination> cls = PDPageDestination.class;
        Objects.requireNonNull(PDPageDestination.class);
        Optional optionalFilter = optionalOfNullable.filter((v1) -> {
            return r1.isInstance(v1);
        });
        Class<PDPageDestination> cls2 = PDPageDestination.class;
        Objects.requireNonNull(PDPageDestination.class);
        return optionalFilter.map((v1) -> {
            return r1.cast(v1);
        });
    }
}

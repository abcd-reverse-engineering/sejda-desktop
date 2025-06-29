package org.sejda.impl.sambox.component;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import java.io.InputStream;
import org.sejda.model.input.Source;

/* loaded from: org.sejda.sejda-sambox-5.1.10.jar:org/sejda/impl/sambox/component/ExifHelper.class */
public class ExifHelper {
    public static int getRotationBasedOnExifOrientation(InputStream inputStream) {
        try {
            int orientation = readExifOrientation(ImageMetadataReader.readMetadata(inputStream));
            return getRotation(orientation);
        } catch (Throwable th) {
            return 0;
        }
    }

    public static int getRotationBasedOnExifOrientation(Source<?> source) {
        try {
            return getRotationBasedOnExifOrientation(source.getSeekableSource().asNewInputStream());
        } catch (Throwable th) {
            return 0;
        }
    }

    private static int readExifOrientation(Metadata metadata) throws MetadataException {
        Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        return directory.getInt(274);
    }

    private static int getRotation(int orientation) {
        switch (orientation) {
        }
        return 0;
    }
}

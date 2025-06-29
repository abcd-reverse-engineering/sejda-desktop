package org.sejda.core.support.prefix.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.sejda.commons.util.RequireUtils;
import org.sejda.model.SejdaFileExtensions;

/* loaded from: org.sejda.sejda-core-5.1.10.jar:org/sejda/core/support/prefix/model/NameGenerationRequest.class */
public final class NameGenerationRequest {
    private static final String EXTENSION_KEY = "extension";
    private static final String BOOKMARK_KEY = "bookmark";
    private static final String ORIGINAL_NAME_KEY = "originalName";
    private static final String TEXT_KEY = "text";
    private static final String PAGE_KEY = "page";
    private static final String FILENUMBER_KEY = "fileNumber";
    private final Map<String, Object> values = new HashMap();

    private NameGenerationRequest(String extension) {
        RequireUtils.requireNotBlank(extension, "Extension cannot be blank");
        setValue(EXTENSION_KEY, extension);
    }

    public static NameGenerationRequest nameRequest() {
        return new NameGenerationRequest(SejdaFileExtensions.PDF_EXTENSION);
    }

    public static NameGenerationRequest nameRequest(String extension) {
        return new NameGenerationRequest(extension);
    }

    public NameGenerationRequest page(int page) {
        setValue(PAGE_KEY, Integer.valueOf(page));
        return this;
    }

    public NameGenerationRequest fileNumber(int fileNumber) {
        setValue(FILENUMBER_KEY, Integer.valueOf(fileNumber));
        return this;
    }

    public NameGenerationRequest bookmark(String bookmark) {
        setValue(BOOKMARK_KEY, StringUtils.trim(bookmark));
        return this;
    }

    public NameGenerationRequest originalName(String originalName) {
        RequireUtils.requireNotBlank(originalName, "Original name cannot be blank");
        if (originalName.lastIndexOf(46) >= 1) {
            setValue(ORIGINAL_NAME_KEY, originalName.substring(0, originalName.lastIndexOf(46)));
        } else {
            setValue(ORIGINAL_NAME_KEY, originalName);
        }
        return this;
    }

    public NameGenerationRequest text(String text) {
        setValue(TEXT_KEY, text);
        return this;
    }

    public Integer getPage() {
        return (Integer) getValue(PAGE_KEY, Integer.class);
    }

    public Integer getFileNumber() {
        return (Integer) getValue(FILENUMBER_KEY, Integer.class);
    }

    public String getBookmark() {
        return (String) getValue(BOOKMARK_KEY, String.class);
    }

    public String getOriginalName() {
        return (String) getValue(ORIGINAL_NAME_KEY, String.class);
    }

    public String getExtension() {
        return (String) getValue(EXTENSION_KEY, String.class);
    }

    public String getText() {
        return (String) getValue(TEXT_KEY, String.class);
    }

    public <T> T getValue(String str, Class<T> cls) {
        Optional optionalOfNullable = Optional.ofNullable(this.values.get(str));
        Objects.requireNonNull(cls);
        Optional<T> optionalFilter = optionalOfNullable.filter(cls::isInstance);
        Objects.requireNonNull(cls);
        return (T) optionalFilter.map(cls::cast).orElse(null);
    }

    public void setValue(String key, Object value) {
        RequireUtils.requireNotNullArg(key, "Key cannot be null");
        this.values.put(key, value);
    }
}

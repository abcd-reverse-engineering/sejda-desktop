package org.sejda.sambox.cos;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.sejda.sambox.util.DateConverter;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSDictionary.class */
public class COSDictionary extends COSBase {
    protected Map<COSName, COSBase> items = new LinkedHashMap();

    public COSDictionary() {
    }

    public COSDictionary(COSDictionary dict) {
        this.items.putAll(dict.items);
    }

    public COSName getKeyForValue(COSBase value) {
        for (Map.Entry<COSName, COSBase> entry : this.items.entrySet()) {
            if (entry.getValue().getCOSObject().equals(value.getCOSObject())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int size() {
        return this.items.size();
    }

    public void clear() {
        this.items.clear();
    }

    public COSBase getDictionaryObject(String key) {
        return getDictionaryObject(COSName.getPDFName(key));
    }

    public <T extends COSBase> T getDictionaryObject(String str, Class<T> cls) {
        return (T) getDictionaryObject(COSName.getPDFName(str), cls);
    }

    public COSBase getDictionaryObject(COSName firstKey, COSName secondKey) {
        COSBase retval = getDictionaryObject(firstKey);
        if (retval == null && secondKey != null) {
            return getDictionaryObject(secondKey);
        }
        return retval;
    }

    public <T extends COSBase> T getDictionaryObject(COSName firstKey, COSName secondKey, Class<T> clazz) {
        return (T) Optional.ofNullable(getDictionaryObject(firstKey, clazz)).orElseGet(() -> {
            if (Objects.nonNull(secondKey)) {
                return getDictionaryObject(secondKey, clazz);
            }
            return null;
        });
    }

    public COSBase getDictionaryObject(COSName key) {
        return (COSBase) Optional.ofNullable(this.items.get(key)).map((v0) -> {
            return v0.getCOSObject();
        }).filter(i -> {
            return !COSNull.NULL.equals(i);
        }).orElse(null);
    }

    public <T extends COSBase> T getDictionaryObject(COSName key, Class<T> clazz) {
        Optional map = Optional.ofNullable(this.items.get(key)).map((v0) -> {
            return v0.getCOSObject();
        });
        Objects.requireNonNull(clazz);
        Optional optionalFilter = map.filter((v1) -> {
            return r1.isInstance(v1);
        });
        Objects.requireNonNull(clazz);
        return (T) optionalFilter.map((v1) -> {
            return r1.cast(v1);
        }).orElse(null);
    }

    public void setItem(COSName key, COSBase value) {
        if (value == null) {
            removeItem(key);
        } else {
            this.items.put(key, value);
        }
    }

    public void putIfAbsent(COSName key, COSBase value) {
        this.items.putIfAbsent(key, value);
    }

    public void setItem(COSName key, COSObjectable value) {
        COSBase base = null;
        if (value != null) {
            base = value.getCOSObject();
        }
        setItem(key, base);
    }

    public void putIfAbsent(COSName key, COSObjectable value) {
        if (Objects.nonNull(value)) {
            this.items.putIfAbsent(key, value.getCOSObject());
        }
    }

    public void setItem(String key, COSObjectable value) {
        setItem(COSName.getPDFName(key), value);
    }

    public void setBoolean(String key, boolean value) {
        setItem(COSName.getPDFName(key), (COSBase) COSBoolean.valueOf(value));
    }

    public void setBoolean(COSName key, boolean value) {
        setItem(key, (COSBase) COSBoolean.valueOf(value));
    }

    public void putIfAbsent(COSName key, boolean value) {
        this.items.putIfAbsent(key, COSBoolean.valueOf(value));
    }

    public void setItem(String key, COSBase value) {
        setItem(COSName.getPDFName(key), value);
    }

    public void setName(String key, String value) {
        setName(COSName.getPDFName(key), value);
    }

    public void setName(COSName key, String value) {
        setItem(key, (COSBase) COSName.getPDFName(value));
    }

    public void setDate(String key, Calendar date) {
        setDate(COSName.getPDFName(key), date);
    }

    public void setDate(COSName key, Calendar date) {
        setString(key, DateConverter.toString(date));
    }

    public void setEmbeddedDate(String embedded, String key, Calendar date) {
        setEmbeddedDate(embedded, COSName.getPDFName(key), date);
    }

    public void setEmbeddedDate(String embedded, COSName key, Calendar date) {
        COSDictionary dic = (COSDictionary) getDictionaryObject(embedded, COSDictionary.class);
        if (dic == null && date != null) {
            dic = new COSDictionary();
            setItem(embedded, (COSBase) dic);
        }
        if (dic != null) {
            dic.setDate(key, date);
        }
    }

    public void setString(String key, String value) {
        setString(COSName.getPDFName(key), value);
    }

    public void setString(COSName key, String value) {
        COSString name = null;
        if (value != null) {
            name = COSString.parseLiteral(value);
        }
        setItem(key, (COSBase) name);
    }

    public void putIfAbsent(COSName key, String value) {
        this.items.putIfAbsent(key, COSString.parseLiteral(value));
    }

    public void setEmbeddedString(String embedded, String key, String value) {
        setEmbeddedString(embedded, COSName.getPDFName(key), value);
    }

    public void setEmbeddedString(String embedded, COSName key, String value) {
        COSDictionary dic = (COSDictionary) getDictionaryObject(embedded, COSDictionary.class);
        if (dic == null && value != null) {
            dic = new COSDictionary();
            setItem(embedded, (COSBase) dic);
        }
        if (dic != null) {
            dic.setString(key, value);
        }
    }

    public void setInt(String key, int value) {
        setInt(COSName.getPDFName(key), value);
    }

    public void setInt(COSName key, int value) {
        setItem(key, (COSBase) COSInteger.get(value));
    }

    public void putIfAbsent(COSName key, int value) {
        this.items.putIfAbsent(key, COSInteger.get(value));
    }

    public void setLong(String key, long value) {
        setLong(COSName.getPDFName(key), value);
    }

    public void setLong(COSName key, long value) {
        setItem(key, (COSBase) COSInteger.get(value));
    }

    public void putIfAbsent(COSName key, long value) {
        this.items.putIfAbsent(key, COSInteger.get(value));
    }

    public void setEmbeddedInt(String embeddedDictionary, String key, long value) {
        setEmbeddedInt(embeddedDictionary, COSName.getPDFName(key), value);
    }

    public void setEmbeddedInt(String embeddedDictionary, COSName key, long value) {
        COSDictionary embedded = (COSDictionary) getDictionaryObject(embeddedDictionary, COSDictionary.class);
        if (embedded == null) {
            embedded = new COSDictionary();
            setItem(embeddedDictionary, (COSBase) embedded);
        }
        embedded.setLong(key, value);
    }

    public void setFloat(String key, float value) {
        setFloat(COSName.getPDFName(key), value);
    }

    public void setFloat(COSName key, float value) {
        setItem(key, (COSBase) new COSFloat(value));
    }

    public void setFlag(COSName field, int bitFlag, boolean value) {
        int currentFlags;
        int currentFlags2 = getInt(field, 0);
        if (value) {
            currentFlags = currentFlags2 | bitFlag;
        } else {
            currentFlags = currentFlags2 & (bitFlag ^ (-1));
        }
        setInt(field, currentFlags);
    }

    public COSName getCOSName(COSName key) {
        return getCOSName(key, null);
    }

    public COSArray getCOSArray(COSName key) {
        COSBase array = getDictionaryObject(key);
        if (array instanceof COSArray) {
            return (COSArray) array;
        }
        return null;
    }

    public COSName getCOSName(COSName key, COSName defaultValue) {
        return (COSName) Optional.ofNullable((COSName) getDictionaryObject(key, COSName.class)).orElse(defaultValue);
    }

    public String getNameAsString(String key) {
        return getNameAsString(COSName.getPDFName(key));
    }

    public String getNameAsString(COSName key) {
        COSBase name = getDictionaryObject(key);
        if (name instanceof COSName) {
            return ((COSName) name).getName();
        }
        if (name instanceof COSString) {
            return ((COSString) name).getString();
        }
        return null;
    }

    public String getNameAsString(String key, String defaultValue) {
        return getNameAsString(COSName.getPDFName(key), defaultValue);
    }

    public String getNameAsString(COSName key, String defaultValue) {
        return (String) Optional.ofNullable(getNameAsString(key)).orElse(defaultValue);
    }

    public String getString(String key) {
        return getString(COSName.getPDFName(key));
    }

    public String getString(COSName key) {
        return (String) Optional.ofNullable((COSString) getDictionaryObject(key, COSString.class)).map((v0) -> {
            return v0.getString();
        }).orElse(null);
    }

    public String getString(String key, String defaultValue) {
        return getString(COSName.getPDFName(key), defaultValue);
    }

    public String getString(COSName key, String defaultValue) {
        return (String) Optional.ofNullable(getString(key)).orElse(defaultValue);
    }

    public String getEmbeddedString(String embedded, String key) {
        return getEmbeddedString(embedded, COSName.getPDFName(key), (String) null);
    }

    public String getEmbeddedString(String embedded, COSName key) {
        return getEmbeddedString(embedded, key, (String) null);
    }

    public String getEmbeddedString(String embedded, String key, String defaultValue) {
        return getEmbeddedString(embedded, COSName.getPDFName(key), defaultValue);
    }

    public String getEmbeddedString(String embedded, COSName key, String defaultValue) {
        return (String) Optional.ofNullable((COSDictionary) getDictionaryObject(embedded, COSDictionary.class)).map(d -> {
            return d.getString(key, defaultValue);
        }).orElse(defaultValue);
    }

    public Calendar getDate(String key) {
        return getDate(COSName.getPDFName(key));
    }

    public Calendar getDate(COSName key) {
        return DateConverter.toCalendar((COSString) getDictionaryObject(key, COSString.class));
    }

    public Calendar getDate(String key, Calendar defaultValue) {
        return getDate(COSName.getPDFName(key), defaultValue);
    }

    public Calendar getDate(COSName key, Calendar defaultValue) {
        return (Calendar) Optional.ofNullable(getDate(key)).orElse(defaultValue);
    }

    public Calendar getEmbeddedDate(String embedded, String key) {
        return getEmbeddedDate(embedded, COSName.getPDFName(key), (Calendar) null);
    }

    public Calendar getEmbeddedDate(String embedded, COSName key) {
        return getEmbeddedDate(embedded, key, (Calendar) null);
    }

    public Calendar getEmbeddedDate(String embedded, String key, Calendar defaultValue) {
        return getEmbeddedDate(embedded, COSName.getPDFName(key), defaultValue);
    }

    public Calendar getEmbeddedDate(String embedded, COSName key, Calendar defaultValue) {
        return (Calendar) Optional.ofNullable((COSDictionary) getDictionaryObject(embedded, COSDictionary.class)).map(d -> {
            return d.getDate(key, defaultValue);
        }).orElse(defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getBoolean(COSName.getPDFName(key), defaultValue);
    }

    public boolean getBoolean(COSName key, boolean defaultValue) {
        return getBoolean(key, null, defaultValue);
    }

    public boolean getBoolean(COSName firstKey, COSName secondKey, boolean defaultValue) {
        COSBoolean value = (COSBoolean) getDictionaryObject(firstKey, secondKey, COSBoolean.class);
        if (Objects.nonNull(value)) {
            return value.getValue();
        }
        return defaultValue;
    }

    public int getEmbeddedInt(String embeddedDictionary, String key) {
        return getEmbeddedInt(embeddedDictionary, COSName.getPDFName(key));
    }

    public int getEmbeddedInt(String embeddedDictionary, COSName key) {
        return getEmbeddedInt(embeddedDictionary, key, -1);
    }

    public int getEmbeddedInt(String embeddedDictionary, String key, int defaultValue) {
        return getEmbeddedInt(embeddedDictionary, COSName.getPDFName(key), defaultValue);
    }

    public int getEmbeddedInt(String embeddedDictionary, COSName key, int defaultValue) {
        return ((Integer) Optional.ofNullable((COSDictionary) getDictionaryObject(embeddedDictionary, COSDictionary.class)).map(d -> {
            return Integer.valueOf(d.getInt(key, defaultValue));
        }).orElse(Integer.valueOf(defaultValue))).intValue();
    }

    public int getInt(String key) {
        return getInt(COSName.getPDFName(key), -1);
    }

    public int getInt(COSName key) {
        return getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return getInt(COSName.getPDFName(key), defaultValue);
    }

    public int getInt(COSName key, int defaultValue) {
        return getInt(key, null, defaultValue);
    }

    public int getInt(COSName firstKey, COSName secondKey) {
        return getInt(firstKey, secondKey, -1);
    }

    public int getInt(COSName firstKey, COSName secondKey, int defaultValue) {
        COSBase obj = getDictionaryObject(firstKey, secondKey);
        if (obj instanceof COSNumber) {
            return ((COSNumber) obj).intValue();
        }
        return defaultValue;
    }

    public long getLong(String key) {
        return getLong(COSName.getPDFName(key), -1L);
    }

    public long getLong(COSName key) {
        return getLong(key, -1L);
    }

    public long getLong(String key, long defaultValue) {
        return getLong(COSName.getPDFName(key), defaultValue);
    }

    public long getLong(COSName key, long defaultValue) {
        return ((Long) Optional.ofNullable((COSNumber) getDictionaryObject(key, COSNumber.class)).map((v0) -> {
            return v0.longValue();
        }).orElse(Long.valueOf(defaultValue))).longValue();
    }

    public float getFloat(String key) {
        return getFloat(COSName.getPDFName(key), -1.0f);
    }

    public float getFloat(COSName key) {
        return getFloat(key, -1.0f);
    }

    public float getFloat(String key, float defaultValue) {
        return getFloat(COSName.getPDFName(key), defaultValue);
    }

    public float getFloat(COSName key, float defaultValue) {
        return ((Float) Optional.ofNullable((COSNumber) getDictionaryObject(key, COSNumber.class)).map((v0) -> {
            return v0.floatValue();
        }).orElse(Float.valueOf(defaultValue))).floatValue();
    }

    public boolean getFlag(COSName field, int bitFlag) {
        int ff = getInt(field, 0);
        return (ff & bitFlag) == bitFlag;
    }

    public void removeItem(COSName key) {
        this.items.remove(key);
    }

    public void removeItems(COSName... keys) {
        Stream stream = Arrays.stream(keys);
        Map<COSName, COSBase> map = this.items;
        Objects.requireNonNull(map);
        stream.forEach((v1) -> {
            r1.remove(v1);
        });
    }

    public COSBase getItem(COSName key) {
        return this.items.get(key);
    }

    public COSBase getItem(String key) {
        return getItem(COSName.getPDFName(key));
    }

    public COSBase getItem(COSName firstKey, COSName secondKey) {
        COSBase retval = getItem(firstKey);
        if (retval == null && secondKey != null) {
            retval = getItem(secondKey);
        }
        return retval;
    }

    public Set<COSName> keySet() {
        return this.items.keySet();
    }

    public Set<Map.Entry<COSName, COSBase>> entrySet() {
        return this.items.entrySet();
    }

    public Collection<COSBase> getValues() {
        return this.items.values();
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    public void addAll(COSDictionary dic) {
        for (Map.Entry<COSName, COSBase> entry : dic.entrySet()) {
            setItem(entry.getKey(), entry.getValue());
        }
    }

    public boolean containsKey(COSName name) {
        return this.items.containsKey(name);
    }

    public boolean containsKey(String name) {
        return containsKey(COSName.getPDFName(name));
    }

    public void mergeWithoutOverwriting(COSDictionary dic) {
        for (Map.Entry<COSName, COSBase> entry : dic.entrySet()) {
            if (getItem(entry.getKey()) == null) {
                setItem(entry.getKey(), entry.getValue());
            }
        }
    }

    public void merge(COSDictionary dic) {
        for (Map.Entry<COSName, COSBase> entry : dic.entrySet()) {
            setItem(entry.getKey(), entry.getValue());
        }
    }

    public COSDictionary asUnmodifiableDictionary() {
        return new UnmodifiableCOSDictionary(this);
    }

    public COSDictionary duplicate() {
        return new COSDictionary(this);
    }

    public String toString() {
        StringBuilder retVal = new StringBuilder(getClass().getSimpleName());
        retVal.append("{");
        for (COSName key : this.items.keySet()) {
            retVal.append("(");
            retVal.append(key);
            retVal.append(":");
            retVal.append((String) Optional.ofNullable(getItem(key)).map(v -> {
                if (v instanceof COSDictionary) {
                    return "COSDictionary{.." + ((COSDictionary) v).size() + " items ..}";
                }
                if (v instanceof COSArray) {
                    return "COSArray{.." + ((COSArray) v).size() + " items ..}";
                }
                return v.toString();
            }).orElse("null"));
            retVal.append(") ");
        }
        retVal.append("}");
        return retVal.toString();
    }
}

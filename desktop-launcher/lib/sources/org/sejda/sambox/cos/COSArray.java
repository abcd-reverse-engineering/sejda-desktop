package org.sejda.sambox.cos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSArray.class */
public class COSArray extends COSBase implements List<COSBase> {
    private final List<COSBase> objects = new ArrayList();

    public COSArray() {
    }

    public COSArray(COSBase... items) {
        this.objects.addAll(Arrays.asList(items));
    }

    public boolean add(COSObjectable object) {
        return add(object.getCOSObject());
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(COSBase object) {
        return this.objects.add(object);
    }

    public void add(int index, COSObjectable object) {
        add(index, object.getCOSObject());
    }

    @Override // java.util.List
    public void add(int index, COSBase object) {
        this.objects.add(index, object);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.objects.clear();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> objectsList) {
        return this.objects.removeAll(objectsList);
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> objectsList) {
        return this.objects.retainAll(objectsList);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends COSBase> objectsList) {
        return this.objects.addAll(objectsList);
    }

    public boolean addAll(COSArray objectList) {
        if (objectList != null) {
            return this.objects.addAll(objectList.objects);
        }
        return false;
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends COSBase> objectList) {
        return this.objects.addAll(i, objectList);
    }

    @Override // java.util.List
    public COSBase set(int index, COSBase object) {
        return this.objects.set(index, object);
    }

    public void set(int index, COSObjectable object) {
        COSBase base = null;
        if (object != null) {
            base = object.getCOSObject();
        }
        set(index, base);
    }

    public COSBase getObject(int index) {
        return (COSBase) Optional.of(this.objects.get(index)).map((v0) -> {
            return v0.getCOSObject();
        }).filter(i -> {
            return i != COSNull.NULL;
        }).orElse(null);
    }

    public <T extends COSBase> T getObject(int index, Class<T> clazz) {
        Optional map = Optional.ofNullable(this.objects.get(index)).map((v0) -> {
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

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    public COSBase get(int index) {
        return this.objects.get(index);
    }

    public int getInt(int index) {
        return getInt(index, -1);
    }

    public int getInt(int index, int defaultValue) {
        if (index < size()) {
            COSBase obj = this.objects.get(index);
            if (obj instanceof COSNumber) {
                return ((COSNumber) obj).intValue();
            }
        }
        return defaultValue;
    }

    public String getName(int index) {
        return getName(index, null);
    }

    public String getName(int index, String defaultValue) {
        if (index < size()) {
            COSBase obj = this.objects.get(index);
            if (obj instanceof COSName) {
                return ((COSName) obj).getName();
            }
        }
        return defaultValue;
    }

    public void setString(int index, String string) {
        if (string != null) {
            set(index, (COSBase) COSString.parseLiteral(string));
        } else {
            set(index, (COSBase) null);
        }
    }

    public String getString(int index) {
        return getString(index, null);
    }

    public String getString(int index, String defaultValue) {
        if (index < size()) {
            Object obj = this.objects.get(index);
            if (obj instanceof COSString) {
                return ((COSString) obj).getString();
            }
        }
        return defaultValue;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.objects.size();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    public COSBase remove(int i) {
        return this.objects.remove(i);
    }

    public COSBase removeLast() {
        if (!this.objects.isEmpty()) {
            return this.objects.remove(this.objects.size() - 1);
        }
        return null;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object o) {
        return this.objects.remove(o);
    }

    public boolean removeObject(COSBase o) {
        boolean removed = remove(o);
        if (!removed) {
            for (int i = 0; i < size(); i++) {
                COSBase entry = get(i);
                if (entry.getCOSObject().equals(o)) {
                    return remove(entry);
                }
            }
        }
        return removed;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<COSBase> iterator() {
        return this.objects.iterator();
    }

    @Override // java.util.List
    public ListIterator<COSBase> listIterator() {
        return this.objects.listIterator();
    }

    @Override // java.util.List
    public ListIterator<COSBase> listIterator(int index) {
        return this.objects.listIterator(index);
    }

    @Override // java.util.List
    public int lastIndexOf(Object o) {
        return this.objects.lastIndexOf(o);
    }

    @Override // java.util.List
    public int indexOf(Object object) {
        return this.objects.indexOf(object);
    }

    public int indexOfObject(COSBase object) {
        for (int i = 0; i < size(); i++) {
            COSBase cosBase = get(i);
            if (Objects.equals(cosBase, object) || Objects.equals(cosBase.getCOSObject(), object)) {
                return i;
            }
        }
        return -1;
    }

    public COSArray growToSize(int size) {
        return growToSize(size, null);
    }

    public COSArray growToSize(int size, COSBase object) {
        while (size() < size) {
            add(object);
        }
        return this;
    }

    public COSArray trimToSize(int size) {
        if (size() > size) {
            this.objects.subList(size, size()).clear();
        }
        return this;
    }

    public float[] toFloatArray() {
        float[] retval = new float[size()];
        for (int i = 0; i < retval.length; i++) {
            retval[i] = ((Float) Optional.ofNullable((COSNumber) getObject(i, COSNumber.class)).map((v0) -> {
                return v0.floatValue();
            }).orElse(Float.valueOf(0.0f))).floatValue();
        }
        return retval;
    }

    public void setFloatArray(float[] value) {
        clear();
        for (float aValue : value) {
            add((COSBase) new COSFloat(aValue));
        }
    }

    public List<? extends COSBase> toList() {
        return new ArrayList(this.objects);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.objects.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.objects.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return (T[]) this.objects.toArray(tArr);
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object o) {
        return this.objects.contains(o);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> c) {
        return this.objects.containsAll(c);
    }

    @Override // java.util.List
    public List<COSBase> subList(int fromIndex, int toIndex) {
        return this.objects.subList(fromIndex, toIndex);
    }

    @Override // org.sejda.sambox.cos.COSBase
    public void accept(COSVisitor visitor) throws IOException {
        visitor.visit(this);
    }

    public COSArray duplicate() {
        COSArray ret = new COSArray();
        ret.addAll(this);
        return ret;
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof COSArray)) {
            return false;
        }
        return this.objects.equals(((COSArray) o).objects);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.objects.hashCode();
    }

    public String toString() {
        return "COSArray{" + this.objects + "}";
    }
}

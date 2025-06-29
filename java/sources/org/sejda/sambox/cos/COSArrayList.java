package org.sejda.sambox.cos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;

/* loaded from: org.sejda.sambox-3.0.24.jar:org/sejda/sambox/cos/COSArrayList.class */
public class COSArrayList<E> implements List<E> {
    private final COSArray array;
    private final List<E> actual;
    private boolean isFiltered;
    private COSDictionary parentDict;
    private COSName dictKey;

    public COSArrayList() {
        this.isFiltered = false;
        this.array = new COSArray();
        this.actual = new ArrayList();
    }

    public COSArrayList(List<E> actualList, COSArray cosArray) {
        this.isFiltered = false;
        this.actual = actualList;
        this.array = cosArray;
        if (this.actual.size() != this.array.size()) {
            this.isFiltered = true;
        }
    }

    public COSArrayList(E actualObject, COSBase item, COSDictionary dictionary, COSName dictionaryKey) {
        this.isFiltered = false;
        this.array = new COSArray();
        this.array.add(item);
        this.actual = new ArrayList();
        this.actual.add(actualObject);
        this.parentDict = dictionary;
        this.dictKey = dictionaryKey;
    }

    public COSArrayList(COSDictionary dictionary, COSName dictionaryKey) {
        this.isFiltered = false;
        this.array = new COSArray();
        this.actual = new ArrayList();
        this.parentDict = dictionary;
        this.dictKey = dictionaryKey;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.actual.size();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.actual.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object o) {
        return this.actual.contains(o);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return this.actual.iterator();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.actual.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public <X> X[] toArray(X[] xArr) {
        return (X[]) this.actual.toArray(xArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.List, java.util.Collection
    public boolean add(E e) {
        if (this.parentDict != null) {
            this.parentDict.setItem(this.dictKey, (COSBase) this.array);
            this.parentDict = null;
        }
        if (e instanceof String) {
            this.array.add((COSBase) COSString.parseLiteral((String) e));
        } else if (this.array != null) {
            this.array.add(((COSObjectable) e).getCOSObject());
        }
        return this.actual.add(e);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object o) {
        if (this.isFiltered) {
            throw new UnsupportedOperationException("removing entries from a filtered List is not permitted");
        }
        int index = this.actual.indexOf(o);
        if (index >= 0) {
            this.actual.remove(index);
            this.array.remove(index);
            return true;
        }
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> c) {
        return this.actual.containsAll(c);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends E> c) {
        throw new RuntimeException("Unsupported");
    }

    @Override // java.util.List
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new RuntimeException("Unsupported operation");
    }

    public static List<Integer> convertIntegerCOSArrayToList(COSArray intArray) {
        List<Integer> retval = null;
        if (intArray != null) {
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0; i < intArray.size(); i++) {
                COSNumber num = (COSNumber) intArray.get(i).getCOSObject();
                numbers.add(Integer.valueOf(num.intValue()));
            }
            retval = new COSArrayList<>(numbers, intArray);
        }
        return retval;
    }

    public static List<Float> convertFloatCOSArrayToList(COSArray floatArray) {
        if (Objects.nonNull(floatArray)) {
            List<Float> numbers = new ArrayList<>(floatArray.size());
            for (int i = 0; i < floatArray.size(); i++) {
                numbers.add((Float) Optional.ofNullable(floatArray.getObject(i)).filter(v -> {
                    return v instanceof COSNumber;
                }).map(v2 -> {
                    return Float.valueOf(((COSNumber) v2).floatValue());
                }).orElse(null));
            }
            return new COSArrayList(numbers, floatArray);
        }
        return null;
    }

    public static List<String> convertCOSNameCOSArrayToList(COSArray nameArray) {
        List<String> retval = null;
        if (nameArray != null) {
            List<String> names = new ArrayList<>();
            for (int i = 0; i < nameArray.size(); i++) {
                names.add(((COSName) nameArray.getObject(i)).getName());
            }
            retval = new COSArrayList<>(names, nameArray);
        }
        return retval;
    }

    public static List<String> convertCOSStringCOSArrayToList(COSArray stringArray) {
        List<String> retval = null;
        if (stringArray != null) {
            List<String> string = new ArrayList<>();
            for (int i = 0; i < stringArray.size(); i++) {
                string.add(((COSString) stringArray.getObject(i)).getString());
            }
            retval = new COSArrayList<>(string, stringArray);
        }
        return retval;
    }

    public static COSArray convertStringListToCOSNameCOSArray(List<String> strings) {
        COSArray retval = new COSArray();
        for (String string : strings) {
            retval.add((COSBase) COSName.getPDFName(string));
        }
        return retval;
    }

    public static COSArray convertStringListToCOSStringCOSArray(List<String> strings) {
        COSArray retval = new COSArray();
        for (String string : strings) {
            retval.add((COSBase) COSString.parseLiteral(string));
        }
        return retval;
    }

    public static COSArray converterToCOSArray(List<?> cosObjectableList) {
        if (cosObjectableList != null && !cosObjectableList.isEmpty()) {
            if (cosObjectableList instanceof COSArrayList) {
                return ((COSArrayList) cosObjectableList).array;
            }
            COSArray array = new COSArray();
            for (Object current : cosObjectableList) {
                if (current instanceof String) {
                    array.add((COSBase) COSString.parseLiteral((String) current));
                } else if ((current instanceof Integer) || (current instanceof Long)) {
                    array.add((COSBase) COSInteger.get(((Number) current).longValue()));
                } else if ((current instanceof Float) || (current instanceof Double)) {
                    array.add((COSBase) new COSFloat(((Number) current).floatValue()));
                } else if (current instanceof COSObjectable) {
                    array.add(((COSObjectable) current).getCOSObject());
                } else if (current == null) {
                    array.add((COSBase) COSNull.NULL);
                } else {
                    throw new RuntimeException("Unable to convert '" + current.getClass().getName() + "' to COSBase ");
                }
            }
            return array;
        }
        return null;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> c) {
        if (this.isFiltered) {
            throw new UnsupportedOperationException("removing entries from a filtered List is not permitted");
        }
        boolean retval = false;
        for (Object o : c) {
            retval = retval || remove(o);
        }
        return retval;
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> c) {
        if (this.isFiltered) {
            throw new UnsupportedOperationException("removing entries from a filtered List is not permitted");
        }
        List<E> toRemove = new LinkedList<>();
        for (E o : this.actual) {
            if (!c.contains(o)) {
                toRemove.add(o);
            }
        }
        return removeAll(toRemove);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        if (this.parentDict != null) {
            this.parentDict.setItem(this.dictKey, (COSBase) null);
        }
        this.actual.clear();
        this.array.clear();
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object o) {
        return this.actual.equals(o);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.actual.hashCode();
    }

    @Override // java.util.List
    public E get(int index) {
        return this.actual.get(index);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.List
    public E set(int index, E e) {
        if (e instanceof String) {
            COSString item = COSString.parseLiteral((String) e);
            if (this.parentDict != null && index == 0) {
                this.parentDict.setItem(this.dictKey, (COSBase) item);
            }
            this.array.set(index, (COSBase) item);
        } else {
            if (this.parentDict != null && index == 0) {
                this.parentDict.setItem(this.dictKey, ((COSObjectable) e).getCOSObject());
            }
            this.array.set(index, ((COSObjectable) e).getCOSObject());
        }
        return this.actual.set(index, e);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.List
    public void add(int index, E e) {
        if (this.parentDict != null) {
            this.parentDict.setItem(this.dictKey, (COSBase) this.array);
            this.parentDict = null;
        }
        this.actual.add(index, e);
        if (e instanceof String) {
            this.array.add(index, (COSBase) COSString.parseLiteral((String) e));
        } else {
            this.array.add(index, ((COSObjectable) e).getCOSObject());
        }
    }

    @Override // java.util.List
    public E remove(int index) {
        if (this.isFiltered) {
            throw new UnsupportedOperationException("removing entries from a filtered List is not permitted");
        }
        this.array.remove(index);
        return this.actual.remove(index);
    }

    @Override // java.util.List
    public int indexOf(Object o) {
        return this.actual.indexOf(o);
    }

    @Override // java.util.List
    public int lastIndexOf(Object o) {
        return this.actual.lastIndexOf(o);
    }

    @Override // java.util.List
    public ListIterator<E> listIterator() {
        return this.actual.listIterator();
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int index) {
        return this.actual.listIterator(index);
    }

    @Override // java.util.List
    public List<E> subList(int fromIndex, int toIndex) {
        return this.actual.subList(fromIndex, toIndex);
    }

    public String toString() {
        return "COSArrayList{" + this.array.toString() + "}";
    }

    public COSArray getCOSArray() {
        return this.array;
    }
}

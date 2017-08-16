package d.candy.f.com.ralgo.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by daichi on 8/16/17.
 */

public class SortedArrayList<E> implements Iterable<E> {

    @NonNull private Comparator<E> mComparator;
    @NonNull private ArrayList<E> mElements;
    private boolean mIsInTransaction = false;

    public SortedArrayList(@NonNull Comparator<E> comparator) {
        mComparator = comparator;
        mElements = new ArrayList<>();
    }

    public void changeComparator(@NonNull Comparator<E> comparator) {
        mComparator = comparator;
        sort();
    }

    private void sort() {
        if (!mIsInTransaction) {
            Collections.sort(mElements, mComparator);
        }
    }

    public boolean add(E element) {
        final boolean result = mElements.add(element);
        sort();
        return result;
    }

    public void add(int index, E element) {
        mElements.add(index, element);
        sort();
    }

    public boolean addAll(Collection<? extends E> collection) {
        final boolean result = mElements.addAll(collection);
        sort();
        return result;
    }

    @SafeVarargs
    final public boolean addAll(E... objects) {
        final boolean result = mElements.addAll(Arrays.asList(objects));
        sort();
        return result;
    }

    public boolean addAll(int index, Collection<? extends E> collection) {
        final boolean result = mElements.addAll(index, collection);
        sort();
        return result;
    }

    @SafeVarargs
    final public boolean addAll(int index, E... objects) {
        final boolean result = mElements.addAll(Arrays.asList(objects));
        sort();
        return result;
    }

    public void clear() {
        mElements.clear();
    }

    public boolean contains(E object) {
        return mElements.contains(object);
    }

    public E get(int index) {
        return mElements.get(index);
    }

    public int indexOf(E object) {
        return mElements.indexOf(object);
    }

    public boolean isEmpty() {
        return mElements.isEmpty();
    }

    public E remove(int index) {
        return mElements.remove(index);
    }

    public boolean remove(E object) {
        return mElements.remove(object);
    }

    public boolean removeAll(Collection<? extends E> collection) {
        return mElements.removeAll(collection);
    }

    @SafeVarargs
    final public boolean removeAll(E... objects) {
        return mElements.removeAll(Arrays.asList(objects));
    }

    public E set(int index, E object) {
        final E replaced = mElements.set(index, object);
        sort();
        return replaced;
    }

    public E[] toArray(E[] array) {
        return mElements.toArray(array);
    }

    public ArrayList<E> asArrayList() {
        return mElements;
    }

    public ArrayList<E> asUnmodifiableArrayList() {
        return (ArrayList<E>) Collections.unmodifiableList(mElements);
    }

    public int size() {
        return mElements.size();
    }

    public void beginTransaction() {
        mIsInTransaction = true;
    }

    public void endTransaction() {
        mIsInTransaction = false;
        sort();
    }

    public E front() {
        if (mElements.isEmpty()) {
            return null;
        }
        return mElements.get(0);
    }

    public E back() {
        if (mElements.isEmpty()) {
            return null;
        }
        return mElements.get(mElements.size() - 1);
    }

    /**
     * region; Iterable implementation
     */
    @Override
    public Iterator<E> iterator() {
        return mElements.iterator();
    }
}

package by.aston.list;

import java.io.Serializable;
import java.util.*;

/**
 * Resizable-array implementation of the {@code List} interface.  Implements
 * all optional list operations, and permits all elements, including
 * {@code null}.  In addition to implementing the {@code List} interface,
 * this class provides methods to manipulate the size of the array that is
 * used internally to store the list.
 *
 * <p>An application can increase the capacity of an {@code ArrayList} instance
 * before adding a large number of elements.
 *
 * @param <T> the type of elements in this list
 * @author Yulyan Paulouski
 */
public class ArrayList<T extends Comparable<T>> extends AbstractList<T> implements Serializable {

    /**
     * Default capacity of list
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Array for storing list items
     */
    private Object[] elements;

    /**
     * Size of list
     */
    private int size;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public ArrayList(int initCapacity) {
        if (initCapacity > 0) {
            elements = new Object[initCapacity];
        } else if (initCapacity == 0) {
            elements = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Invalid initCapacity: " + initCapacity);
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Increases the capacity to ensure that it can hold
     * at least the number of elements.
     */
    private void grow() {
        int newCapacity = (int) Math.round(size * 1.5);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of size list
     */
    public T get(int index) {
        Objects.checkIndex(index, size);
        return (T) elements[index];
    }


    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index   index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if index out of size list
     */
    public void add(int index, T element) {
        checkIndex(index);
        if (size == elements.length) {
            grow();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Inserts the specified element at the end of list.
     *
     * @param t element to be inserted
     * @return {@code true} if element was inserted
     */
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if index out of size list
     */
    public T set(int index, T element) {
        checkIndex(index);
        T oldElement = (T) elements[index];
        elements[index] = element;
        return oldElement;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if index out of size list
     */
    public T remove(int index) {
        checkIndex(index);
        T removeElement = (T) elements[index];
        size--;
        System.arraycopy(elements, index + 1, elements, index, size - index);
        return removeElement;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this element was removed
     */
    public boolean remove(Object o) {
        int removeIndex;
        if ((removeIndex = indexOf(o)) > 0) {
            remove(removeIndex);
            return true;
        }
        return false;
    }

    /**
     * Returns the index of the first occurrence of the specified
     * element in this list, or -1 if this list does not contain the element.
     *
     * @param o the specified element
     * @return the index of the first occurrence of the specified element
     */
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Sorts this list according to the implementation of the element T
     * of the Comparable interface
     */
    public void quickSort() {
        quickSort(elements, 0, size - 1, null);
    }

    /**
     * Sorts this list according to the order induced by the specified Comparator.
     * The sort is stable: this method must not reorder equal elements.
     *
     * @param c the Comparator used to compare list elements.
     */
    public void sort(Comparator<? super T> c) {
        quickSort(elements, 0, size - 1, c);
    }

    /**
     * Check the index is included in the size of the array
     *
     * @param index the index being checked
     */
    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds: " + size);
        }
    }

    /**
     * Sorts the specified range of the specified array of objects
     * according to the order induced by the specified comparator.
     *
     * @param array array to sort
     * @param from  the first index to sorting
     * @param to    the last index to sorting
     * @param c     the Comparator used to compare list elements.
     */
    private void quickSort(Object[] array, int from, int to, Comparator<? super T> c) {
        if (from >= to) return;

        int left = from;
        int right = to;
        int middle = (from + to) / 2;
        T midElement = (T) array[middle];

        while (left <= right) {
            int[] boards;
            if (c != null) {
                boards = compareWithComparator(c, left, right, midElement, array);
            } else {
                boards = compareWithoutComparator(left, right, midElement, array);
            }
            left = boards[0];
            right = boards[1];
            if (left <= right) {
                T tmp = (T) array[left];
                array[left] = array[right];
                array[right] = tmp;
                left++;
                right--;
            }
        }
        quickSort(array, from, right, c);
        quickSort(array, left, to, c);
    }

    /**
     * Compares the midElement with the left and right part
     * with the help of Comparator
     *
     * @param c          the Comparator used to compare list elements.
     * @param left       the left border of compare
     * @param right      the right border of compare
     * @param midElement the element to compare with
     * @param array      an array with elements to compare
     * @return array[left offset, right offset]
     */
    private int[] compareWithComparator(Comparator<? super T> c, int left, int right,
                                        T midElement, Object[] array) {
        while (c.compare(midElement, (T) array[left]) > 0) {
            left++;
        }
        while (c.compare(midElement, (T) array[right]) < 0) {
            right--;
        }
        return new int[]{left, right};
    }

    /**
     * Compares the midElement with the left and right part
     * without Comparator but use method compareTo of interface
     * Comparable
     *
     * @param left       the left border of compare
     * @param right      the right border of compare
     * @param midElement the element to compare with
     * @param array      an array with elements to compare
     * @return array[left offset, right offset]
     */
    private int[] compareWithoutComparator(int left, int right, T midElement, Object[] array) {
        while (midElement.compareTo((T) array[left]) > 0) {
            left++;
        }
        while (midElement.compareTo((T) array[right]) < 0) {
            right--;
        }
        return new int[]{left, right};
    }


    /**
     * Compares the specified object with this list for equality.
     * Returns true if and only if the specified object is also a list,
     * both lists have the same size, and all corresponding pairs
     * of elements in the two lists are equal.
     *
     * @param o compare object
     * @return {@code true} if this and compare object is equals
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ArrayList<?> arrayList = (ArrayList<?>) o;
        return size == arrayList.size && Arrays.equals(elements, arrayList.elements);
    }

    /**
     * Returns the hash code value for this list.
     *
     * @return the hash code value
     */
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }
}

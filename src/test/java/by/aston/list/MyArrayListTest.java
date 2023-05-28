package by.aston.list;

import by.aston.collection.MyCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MyArrayListTest {

    private MyArrayList<Integer> list;
    private MyArrayList<Integer> sortedList;
    private MyArrayList<Integer> reverseSortList;
    private final Integer[] elements = {10, 5, 12, 42, 33, 900, 43, 5, 123, 643, 123, 543, 662, 221, 888, 19, 43, 12, 55, 3};
    private final Integer[] sortElements = {3, 5, 5, 10, 12, 12, 19, 33, 42, 43, 43, 55, 123, 123, 221, 543, 643, 662, 888, 900};
    private final Integer[] sortByComparator = {900, 888, 662, 643, 543, 221, 123, 123, 55, 43, 43, 42, 33, 19, 12, 12, 10, 5, 5, 3};

    @BeforeEach
    void setUp() {
        list = new MyArrayList<>();
        sortedList = new MyArrayList<>();
        reverseSortList = new MyArrayList<>();
        Collections.addAll(list, elements);
        Collections.addAll(sortedList, sortElements);
        Collections.addAll(reverseSortList, sortByComparator);
    }

    @Test
    void set() {
        assertThat(list.get(1)).isEqualTo(5);
        list.set(1, 55);
        assertThat(list.get(1)).isEqualTo(55);
    }

    @Test
    void get() {
        assertThat(list.get(0)).isEqualTo(10);
    }

    @Test
    void add() {
        assertThat(list.size()).isEqualTo(20);
        list.add(11);
        assertThat(list.size()).isEqualTo(21);
    }

    @Test
    void addByIndex() {
        assertThat(list.size()).isEqualTo(20);
        list.add(3, 11);
        assertThat(list.size()).isEqualTo(21);
        assertThat(list.get(3)).isEqualTo(11);
    }

    @Test
    void size() {
        assertThat(list.size()).isEqualTo(20);
        list.remove(0);
        list.remove(1);
        list.remove(2);
        assertThat(list.size()).isEqualTo(17);
        list.add(23);
        assertThat(list.size()).isEqualTo(18);
    }

    @Test
    void remove() {
        assertThat(list.size()).isEqualTo(20);
        list.remove(3);
        assertThat(list.size()).isEqualTo(19);
    }

    @Test
    void removeObject() {
        assertThat(list.size()).isEqualTo(20);
        list.remove(Integer.valueOf(5));
        assertThat(list.size()).isEqualTo(19);
        assertThat(list.get(1)).isNotEqualTo(5);
    }

    @Test
    void indexOf() {
        assertThat(list.indexOf(5)).isEqualTo(1);
        assertThat(list.indexOf(-22)).isEqualTo(-1);
    }

    @Test
    void clear() {
        assertThat(list.size()).isEqualTo(20);
        list.clear();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void sortByComparator() {
        list.sort(Comparator.reverseOrder());
        assertThat(list).isEqualTo(reverseSortList);
    }

    @Test
    void quickSort() {
        MyCollections.quickSort(list);
        assertThat(list).isEqualTo(sortedList);
    }
}
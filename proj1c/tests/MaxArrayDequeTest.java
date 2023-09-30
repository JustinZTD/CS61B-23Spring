import deque.MaxArrayDeque;
import org.junit.jupiter.api.*;
import deque.Deque;
import deque.ArrayDeque;
import deque.LinkedListDeque;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDequeTest {
    private static class MaxArrayDequeComparator<T extends Comparable<T>> implements Comparator<T> {
        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }
    @Test
    public void testMaxWithConstructorComparator() {
        Comparator<Integer> c = new MaxArrayDequeComparator<>();
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(c);
        mad.addLast(5);
        mad.addLast(3);
        mad.addLast(10);
        mad.addLast(15);
        mad.addLast(16);
        mad.addLast(17);
        mad.addLast(18);
        mad.addLast(19);
        mad.addLast(100);
        mad.addLast(13);

        assertThat(mad.max()).isEqualTo(100);
    }

    @Test
    public void testMaxWithParameterComparator() {
        Comparator<Integer> c = new MaxArrayDequeComparator<>();
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(c);
        mad.addLast(5);
        mad.addLast(3);
        mad.addLast(10);
        mad.addLast(15);
        mad.addLast(16);
        mad.addLast(17);
        mad.addLast(18);
        mad.addLast(19);
        mad.addLast(100);
        mad.addLast(13);

        assertThat(mad.max(c)).isEqualTo(100);
    }
}

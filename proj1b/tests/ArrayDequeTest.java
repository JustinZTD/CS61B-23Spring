import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDequeTest {
    @Test
    public void addFirst_singleItem() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);

        assertThat(deque.toList()).containsExactly(1);
    }

    @Test
    public void addFirst_multipleItems() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(2);
        deque.addFirst(1);

        assertThat(deque.toList()).containsExactly(1, 2).inOrder();
    }

    @Test
    public void addLast_singleItem() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);

        assertThat(deque.toList()).containsExactly(1);
    }

    @Test
    public void addLast_multipleItems() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);

        assertThat(deque.toList()).containsExactly(1, 2).inOrder();
    }

    @Test
    public void resize_whileAdding() {
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            deque.addLast(i);
        }

        deque.addLast(16); // this should trigger resize

        assertThat(deque.toList()).hasSize(17);
        assertThat(deque.get(16)).isEqualTo(16);
    }

    @Test
    public void removeFirst() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(2);
        deque.addFirst(1);
        int removedItem = deque.removeFirst();

        assertThat(removedItem).isEqualTo(1);
        assertThat(deque.toList()).containsExactly(2);
    }

    @Test
    public void removeLast() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        int removedItem = deque.removeLast();

        assertThat(removedItem).isEqualTo(2);
        assertThat(deque.toList()).containsExactly(1);
    }

    @Test
    public void resize_whileRemoving() {
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 20; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 15; i++) {
            deque.removeFirst();
        }

        assertThat(deque.toList()).hasSize(5);
    }

    @Test
    public void isEmpty() {
        Deque<Integer> deque = new ArrayDeque<>();
        assertThat(deque.isEmpty()).isTrue();

        deque.addLast(1);
        assertThat(deque.isEmpty()).isFalse();

        deque.removeLast();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void get_outOfBounds() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);

        assertThat(deque.get(1)).isNull();
        assertThat(deque.get(-1)).isNull();
    }
}

import java.util.ArrayList;
import java.util.List;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int first;
    private int last;
    private int capacity;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = 0;
        last = 0;
        size = 0;
        capacity = 8;
    }

    public static void main(String[] args) {
        Deque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            ad.addLast(i);
        }
        ad.addLast(16);
    }

    @Override
    public void addFirst(T x) {
        if (items[first] == null) {
            items[first] = x;
            size++;
            return;
        }
        int newFirst = (first + capacity - 1) % capacity;
        if (items[newFirst] != null) {
            resize(size * 2);
            newFirst = (first + capacity - 1) % capacity;
        }
        items[newFirst] = x;
        first = newFirst;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (items[last] == null) {
            items[last] = x;
            size++;
            return;
        }
        int newLast = (last + capacity + 1) % capacity;
        if (items[newLast] != null) {
            resize(size * 2);
            newLast = (last + 1) % capacity;
        }
        items[newLast] = x;
        last = newLast;
        size++;
    }

    private void resize(int capacity) {
        this.capacity = capacity;
        T[] newAd = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newAd[i] = items[(first + i) % items.length];
        }
        first = 0;
        last = size - 1;
        items = newAd;
    }
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(items[(first + i) % capacity]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        int oldFirst = first;
        T itemRemoved = items[oldFirst];
        items[oldFirst] = null;
        first = (first + 1) % capacity;
        size--;
        if ((double) size / capacity < 0.25 && capacity > 16) {
            resize(capacity / 2);
        }
        return itemRemoved;
    }

    @Override
    public T removeLast() {
        int oldLast = last;
        T itemRemoved = items[oldLast];
        items[oldLast] = null;
        last = (last - 1 + capacity) % capacity;
        size--;
        if ((double) size / capacity < 0.25 && capacity > 16) {
            resize(capacity / 2);
        }
        return itemRemoved;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[(first + index) % capacity];
    }
}

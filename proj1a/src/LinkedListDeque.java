import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private class Node {
        private Node prev;
        private Node next;
        private T item;

        private Node(Node prev, T item, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public static void main(String[] args) {
        Deque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(10);
        lld.addLast(5);
    }

    @Override
    public void addFirst(T x) {
        Node oldFirst = sentinel.next;
        Node newNode = new Node(sentinel, x, oldFirst);
        sentinel.next = newNode;
        oldFirst.prev = newNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        Node oldLast = sentinel.prev;
        Node newNode = new Node(oldLast, x, sentinel);
        sentinel.prev = newNode;
        oldLast.next = newNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next != sentinel) {
            return false;
        } else {
            return  true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            T returnValue = sentinel.next.item;
            Node newFirst = sentinel.next.next;
            sentinel.next = newFirst;
            newFirst.prev = sentinel;
            return returnValue;
        }
    }

    @Override
    public T removeLast() {
        if (sentinel.prev == sentinel) {
            return null;
        } else {
            T returnValue = sentinel.prev.item;
            Node newLast = sentinel.prev.prev;
            newLast.next = sentinel;
            sentinel.prev = newLast;
            return returnValue;
        }

    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            Node p = sentinel.next;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p.item;
        }
    }

    private T getRecursiveHelper(int index, int count, Node node) {
        if (index == count) {
            return node.item;
        } else {
            return getRecursiveHelper(index, count+1, node.next);
        }
    }

    @Override
    public T getRecursive(int index) {
        Node p = sentinel.next;
        int count = 0;
        if (index >= size || index < 0) {
            return null;
        } else {
            return getRecursiveHelper(index, count, p);
        }
    }
}

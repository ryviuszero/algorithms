/* *****************************************************************************
 *  Name:   ryviuszero
 *  Date:   2020/3/9
 *  Description:
 ****************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item value;
        private Node prev;
        private Node next;

        private Node(Item value) {
            this.value = value;
            this.prev = null;
            this.next = null;
        }

        private Node(Item value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        head = new Node(null);
        tail = head;
        size = 0;

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (isEmpty()) {
            head = new Node(item);
            tail = head;
        }
        else {
            Node node = new Node(item, null, head);
            head.prev = node;
            head = node;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (isEmpty()) {
            head = new Node(item);
            tail = head;
        }
        else {
            Node node = new Node(item, tail, null);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty!");
        }
        else if (head == tail) {
            Node node = head;
            head = new Node(null);
            tail = head;
            size--;
            return node.value;
        }
        else {
            Node node = head;
            head = head.next;
            head.prev = null;
            size--;
            return node.value;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty!");
        }
        else if (head == tail) {
            Node node = tail;
            tail = new Node(null);
            head = tail;
            size--;
            return node.value;
        }
        else {
            Node node = tail;
            tail = tail.prev;
            tail.next = null;
            size--;
            return node.value;
        }

    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // private class DequeIterator implements Iterator<Item> {
    //     private Node node;
    //     private int k;
    //
    //     DequeIterator() {
    //         Node dummyHead = new Node(null);
    //         dummyHead.next = head;
    //         node = dummyHead;
    //         k = size;
    //     }
    //
    //     public boolean hasNext() {
    //         return k != 0;
    //     }
    //
    //     public Item next() {
    //         if (!hasNext())
    //             throw new NoSuchElementException();
    //         node = node.next;
    //         k--;
    //         return node.value;
    //     }
    //
    //     public void remove() {
    //         throw new UnsupportedOperationException();
    //     }

    private class DequeIterator implements Iterator<Item> {

        private Node current = head;

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException("There are no more elements");

            Item item = current.value;
            current = current.next;

            return item;
        }

        public boolean hasNext() {
            return current != null && current.value != null;
        }

        public void remove() {
            throw new UnsupportedOperationException(
                    "This operation is not supported by this class");
        }


    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        for (int it : deque) {
            System.out.println(it);
        }
        System.out.println(deque.removeFirst()); //==> 2
        System.out.println(deque.removeFirst()); // ==> 1
        System.out.println(deque.removeLast()); // ==> 5
        System.out.println(deque.removeFirst()); // ==> 3
        System.out.println(deque.removeLast()); // ==> 4
    }
}

/* *****************************************************************************
 *  Name:   ryviuszero
 *  Date:   2020/3/9
 *  Description:
 *****************************************************************************/

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    private void resize(int capacity) {
        Item[] tmp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            tmp[i] = items[i];
        }
        items = tmp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {

        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int idx = randomIndex();
        Item item = items[idx];
        items[idx] = items[size - 1];
        items[--size] = null;
        if (size <= items.length / 4)
            resize(items.length / 2 + 1);
        return item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        return items[randomIndex()];
    }

    private int randomIndex() {
        int idx = StdRandom.uniform(0, size);
        return idx;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] randomIndex;
        // private final RandomizedQueue<Item> copy;
        private int cur;

        RandomizedQueueIterator() {
            randomIndex = new int[size];
            for (int i = 0; i < size; i++) {
                randomIndex[i] = i;
            }

            StdRandom.shuffle(randomIndex);
            // copy = new RandomizedQueue<Item>();

            // for (int i = 0; i < size; i++) {
            //             //     copy.enqueue(items[i]);
            //             // }
            cur = 0;
        }

        public boolean hasNext() {
            return cur != size;
            // return copy.size() > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[randomIndex[cur++]];
            // return copy.dequeue();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}

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
        int idx = 0;
        for (Item it : items) {
            tmp[idx++] = it;
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
            resize(size * 2);
        }
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int idx = StdRandom.uniform(0, size);
        Item item = items[idx];
        items[idx] = items[size - 1];
        items[--size] = null;
        return item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        int idx = StdRandom.uniform(0, size);
        return items[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] randomIndex;
        private int cur;

        RandomizedQueueIterator() {
            randomIndex = new int[size];
            for (int i = 0; i < size; i++) {
                randomIndex[i] = i;
            }

            StdRandom.shuffle(randomIndex);
            cur = 0;
        }

        public boolean hasNext() {
            return cur != size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[cur++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}

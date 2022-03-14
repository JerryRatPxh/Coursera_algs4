import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue(){
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if(item == null)
            throw new IllegalArgumentException("can't enqueue null");
        items[size++] = item;
        //resize
        if (size == items.length){
            Item[] oldItems = items;
            items = (Item[]) new Object[2*size];
            for (int i = 0; i < oldItems.length; i++) {
                items[i] = oldItems[i];
            }
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if(isEmpty())
            throw new NoSuchElementException("the queue is empty");
        int pos = StdRandom.uniform(size);
        Item item = items[pos];
        items[pos] = items[size-1];
        items[size-1] = null;
        size--;
        //resize
        if(size <= items.length/4){
            Item[] oldItems = items;
            items = (Item[]) new Object[items.length/2];
            for (int i = 0; i < size; i++) {
                items[i] = oldItems[i];
            }
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if(isEmpty())
            throw new NoSuchElementException("the queue is empty");
        int pos = StdRandom.uniform(size);
        return items[pos];
    }

    private class MyIterator implements Iterator<Item> {
        private Item[] iteratorItems;
        private int N;

        MyIterator(){
            iteratorItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iteratorItems[i] = items[i];
            }
            N = size;
        }

        @Override
        public boolean hasNext() {
            return N > 0;
        }

        @Override
        public Item next() {
            if(!hasNext())
                throw new NoSuchElementException("there is no item to iterate");
            int pos = StdRandom.uniform(N);
            Item item = iteratorItems[pos];
            iteratorItems[pos] = iteratorItems[N-1];
            iteratorItems[N-1] = null;
            N--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("unsupport remove operation");
        }
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new MyIterator();
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
        }
        for (int i:randomizedQueue) {
            StdOut.print(i + " ");
        }
        StdOut.println();
        StdOut.println("sample: " + randomizedQueue.sample());
        StdOut.println("isEmpty: " + randomizedQueue.isEmpty());
        StdOut.println("size: " + randomizedQueue.size());

        for (int i = 0; i < 10; i++) {
            int val = randomizedQueue.dequeue();
            StdOut.println("val: " + val);
            for (int j:randomizedQueue) {
                StdOut.print(j + " ");
            }
            StdOut.println();
        }
    }

}
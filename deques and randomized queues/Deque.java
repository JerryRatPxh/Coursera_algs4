import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node<Item>{
        Item item;
        Node<Item> next;
        Node<Item> pre;
    }

    private Node<Item> first;
    private Node<Item> last;
    private int size = 0;

    // construct an empty deque
    public Deque(){}

    // is the deque empty?
    public boolean isEmpty(){
        return first == null;
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    public void addFirst(Item item){
        if (item == null)
            throw new IllegalArgumentException("addFirst failed");
        Node<Item> node = new Node<>();
        node.item = item;
        node.next = first;
        node.pre = null;
        // when the linked list is empty
        if (isEmpty()) {
            first = node;
            last = node;
        }
        // not empty
        else {
            first.pre = node;
            first = node;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null)
            throw new IllegalArgumentException("addLast failed");
        Node<Item> node = new Node<>();
        node.item = item;
        node.next = null;
        node.pre = last;
        if (isEmpty()) {
            last = node;
            first = node;
        }
        else{
            last.next = node;
            last = node;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(isEmpty())
            throw new NoSuchElementException("removeFirst failed");
        Node<Item> oldFirst = first;
        first = first.next;
        oldFirst.next = null;
        if(first == null)
            last = null;
        else
            first.pre = null;
        Item item = oldFirst.item;
        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(isEmpty())
            throw new NoSuchElementException("removeLast failed");
        Node<Item> oldLast = last;
        last = last.pre;
        oldLast.pre = null;
        if (last == null)
            first = null;
        else
            last.next = null;
        Item item = oldLast.item;
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new Iterator<Item>() {
            private Node<Item> current = first;
            @Override
            public boolean hasNext() {
                return current != null;
            }

            public void remove(){
                throw new UnsupportedOperationException("unsupport remove operation");
            }

            @Override
            public Item next() {
                if(!hasNext())
                    throw new NoSuchElementException("there is no item to iterate");
                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args){
        // test constructor
        Deque<Integer> deque = new Deque<>();

        int[] testNums = new int[] {1,2,3,4,5,6,7,8,9,10};

        for (int i:testNums) {
            if (i%2 == 0)
                deque.addFirst(i);
            else
                deque.addLast(i);
        }

        StdOut.println("size: " + deque.size());
        StdOut.println("isEmpty: " + deque.isEmpty());

        for (int i:deque) {
            StdOut.print(i + " ");
        }
        StdOut.println();

        for (int i:testNums){
            if (i%2 == 0)
                deque.removeFirst();
            else
                deque.removeLast();
            for (int j:deque) {
                StdOut.print(j + " ");
            }
            StdOut.println();
        }
    }

}
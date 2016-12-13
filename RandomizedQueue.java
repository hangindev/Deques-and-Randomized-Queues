import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int n;
    
    public RandomizedQueue() {              // construct an empty randomized queue
        a = (Item[]) new Object[2];
        n = 0;
    }
    
    
    public boolean isEmpty() {               // is the queue empty?
        return n == 0;
    }
    
    public int size() {                      // return the number of items on the queue
        return n;
    }
    
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    
    private void pack() {
        Item[] temp = (Item[]) new Object[n];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    
    public void enqueue(Item item) {         // add the item
        if(item == null) throw new  NullPointerException("argument to contains() is null");
        if(n == a.length) resize(2*n);
        a[n++] = item;
    }
    
    public Item dequeue() {                  // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int r = StdRandom.uniform(0, n);
        Item item = a[r];
        a[r] = a[n-1];
        a[n-1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }
    
    public Item sample() {                     // return (but do not remove) a random item
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[StdRandom.uniform(0, n)];
    }
    
    public Iterator<Item> iterator() {       // return an independent iterator over items in random order
        pack();
        return new RandomIterator();
    }
    
    private class RandomIterator implements Iterator<Item> {
        private int i;
        private Item[] temp = (Item[]) new Object[n];
        private Item[] b = (Item[]) new Object[n];
        public RandomIterator() {
            i = 0;
            temp = a;
            StdRandom.shuffle(a);
            b = a;
            a = temp;
            temp = null;
        }
        
        public boolean hasNext() {
            return i < n;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return b[i++];
        }
    }
    
    public static void main(String[] args){    // unit testing
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        //rq.enqueue(StdIn.readString());
        rq.enqueue("1");
        rq.enqueue("2");
        rq.enqueue("3");
        rq.enqueue("4");
        rq.enqueue("5");
        rq.enqueue("6");
        //StdOut.print(rq.sample());
        //StdOut.print(rq.dequeue());
        //StdOut.print(rq.dequeue());
        StdOut.println("\n(" + rq.size() + " left on stack)"+"\n");
        for (String s : rq) {
            StdOut.println(s);
            for (String j : rq) {
                StdOut.println("- "+j);
            }
        }
    }   
}
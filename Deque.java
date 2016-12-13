import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of queue
    private Node<Item> last;     // end of queue
    private int n;               // number of elements on queue
    
    private static class Node<Item> {    // helper linked list class
        private Item item;
        private Node<Item> before;
        private Node<Item> next;
    }
    
    public Deque() {    // construct an empty deque
        first = null;
        last  = null;
        n = 0;
    }
    
    public boolean isEmpty()  {    // is the deque empty?
        return n==0;
    }   
    
    public int size() {    // return the number of items on the deque
        return n;
    }                     
    
    public void addFirst(Item item) {    // add the item to the front
        if(item == null) throw new  NullPointerException("argument to contains() is null");
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.before = null;
        if (!isEmpty()) {
            oldfirst.before = first;
            first.next = oldfirst;
        }
        else last = first;
        n++;
    }
    
    public void addLast(Item item) {    // add the item to the end
        if(item == null) throw new  NullPointerException("argument to contains() is null");
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (!isEmpty()) 
        {
            oldlast.next = last;
            last.before = oldlast;
        }           
        else first = last;
        n++;
    }        
    
    public Item removeFirst() {    // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        // to avoid loitering
        if (isEmpty()) last = null;  
        else first.before = null;  
        return item;
    }  
    
    public Item removeLast() {    // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = last.item;
        last = last.before;
        n--;
        // to avoid loitering
        if (isEmpty()) first = null; 
        else last.next = null;
        return item;
    }  
    
    public Iterator<Item> iterator() {    // return an iterator over items in order from front to end
        return new ListIterator<Item>(first);  
    }
    
    private class ListIterator<Item> implements Iterator<Item> {    // an iterator, doesn't implement remove() since it's optional
        private Node<Item> current;
        
        public ListIterator(Node<Item> first) {
            current = first;
        }
        
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    public static void main(String[] args)  {    // unit testing
        Deque<String> deque = new Deque<String>();
        deque.addLast(" A ");
        deque.addFirst(" B ");
        deque.addLast(" C ");
        StdOut.print(deque.removeLast());
        StdOut.print(deque.removeFirst());
        StdOut.print(deque.removeFirst());
        StdOut.println("\n(" + deque.size() + " left on stack)"+"\n"); 
    } 
}
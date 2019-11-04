/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private ListNode front;
    private int size = 0;

    public RandomizedQueue() {

    }                 // construct an empty randomized queue

    public boolean isEmpty() {
        if (size == 0) return true;
        return false;
    }// is the randomized queue empty?

    public int size() {
        return size;
    }                       // return the number of items on the randomized queue

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        else if (front == null) {
            front = new ListNode(item, null);
            size++;
        }
        else {
            ListNode temp = front;
            ListNode addNode = new ListNode(item, temp);
            front = addNode;
            size++;
        }
    }                                         // add the item

    public Item dequeue() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        else {

            //get random integer between 0 and q.size()

            double randomDouble = Math.random() * size;
            int index = (int) randomDouble;

            ListNode current = front;
            ListNode previous = front;
            for (int i = 0; i < index; i++) {
                previous = current;
                current = current.next;
            }
            size--;
            Item toReturn = current.data;

            if (current == front) {
                front = current.next;
            }
            else {
                previous.next = current.next;
            }

            return toReturn;
        }

    }                   // remove and return a random item

    public Item sample() {

        if (front == null) {
            throw new NoSuchElementException();
        }
        else {

            //get random integer between 0 and q.size()

            double randomDouble = Math.random() * size();
            int index = (int) randomDouble;

            ListNode current = front;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Item toReturn = current.data;
            return toReturn;
        }

    }                    // return a random item (but do not remove it)

    public Iterator<Item> iterator() {

        return new RQIterator();

    }        // return an independent iterator over items in random order

    private class RQIterator implements Iterator<Item> {

        private RandomizedQueue<Item> itQ = new RandomizedQueue<Item>();

        public RQIterator() {
            
            ListNode current = front;
            while (current != null) {
                itQ.enqueue(current.data);
                current = current.next;

            }
        }

        public boolean hasNext() {
            return !itQ.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (itQ.isEmpty()) {
                throw new NoSuchElementException();
            }
            else {
                return itQ.dequeue();

            }
        }
    }

    private class ListNode {
        Item data;
        ListNode next;

        ListNode(Item data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }


    //public static void main(String[] args)   // unit testing (optional)
}

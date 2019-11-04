/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

    private ListNode front;
    private ListNode back;
    private int size = 0;

    public Deque() {

    }                    // construct an empty deque

    public boolean isEmpty() {
        if (size > 0) {
            return false;
        }
        return true;
    }               // is the deque empty?

    public int size() {
        return size;
    }                       // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        else if (front == null) {
            front = new ListNode(item, null, null);
            back = front;
            size++;
        }
        else {
            ListNode temp = front;
            ListNode addNode = new ListNode(item, temp, null);
            temp.last = addNode;
            front = addNode;
            size++;
        }

    }         // add the item to the front

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        else if (back == null) {
            back = new ListNode(item, null, null);
            front = back;
            size++;
        }
        else {
            ListNode temp = back;
            ListNode addNode = new ListNode(item, null, temp);
            temp.next = addNode;
            back = addNode;
            size++;
        }
    }           // add the item to the end

    public Item removeFirst() {
        if (front == null) {
            throw new NoSuchElementException();
        }
        else {
            ListNode temp = front;
            size--;
            front = front.next;
            if (size == 0) back = null;
            return temp.data;
        }

    }               // remove and return the item from the front

    public Item removeLast() {
        if (back == null) {
            throw new NoSuchElementException();
        }
        else {
            ListNode temp = back;
            size--;
            back = back.last;
            if (size == 0) front = null;
            return temp.data;
        }

    }                // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }      // return an iterator over items in order from front to end

    //public static void main(String[] args) {

    //}   // unit testing (optional)


    private class DequeIterator implements Iterator<Item> {
        private ListNode current = front;

        DequeIterator() {

        }

        public boolean hasNext() {
            if (current == null) {
                return false;
            }
            else {
                return true;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item currentData = current.data;
            current = current.next;
            return currentData;
        }
    }

    private class ListNode {
        Item data;
        ListNode next;
        ListNode last;

        ListNode(Item data, ListNode next, ListNode last) {
            this.data = data;
            this.next = next;
            this.last = last;
        }
    }


}

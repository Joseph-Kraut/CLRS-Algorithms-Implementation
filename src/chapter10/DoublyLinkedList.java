package chapter10;

import java.util.Currency;
import java.util.List;

public class DoublyLinkedList<T> {

    // This simple data structure will hold the Linked List info
    private class ListElement {
        public ListElement next;
        public ListElement prev;
        public T value;

        public ListElement(T value, ListElement next, ListElement prev) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }

        public boolean equals(Object o) {
            // Must certainly be a ListElement
            if (o.getClass() != this.getClass()) {
                return false;
            }

            // Otherwise we can just compare the values
            ListElement obj = (ListElement) o;

            if (obj.value.equals(this.value)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private ListElement head;
    private ListElement tail;
    private int size;

    public DoublyLinkedList(T[] startingArray) {
        this.size = 0;

        // Loop over elements in array and add them to the list
        for (T element : startingArray) {
            // Append to linked list
            this.addBack(element);
        }
    }

    public void addFront(T element) {
        /*
        Adds an element to the front of the linked list
         */

        // If it's empty we need to set up the first element
        if (this.isEmpty()) {
            // Set the head to the new element
            this.head = new ListElement(element, null, null);

            // Make its pointers point to itself
            this.head.next = this.head;
            this.head.prev = this.head;

            // Set the tail to also be the head
            this.tail = this.head;

        } else {
            // Otherwise we add to the front of the list and change the pointers
            ListElement newElement = new ListElement(element, this.head, this.tail);

            // Set the head and tail pointers to recognize this as the new head
            this.head.prev = newElement;
            this.tail.next = newElement;

            // Change the head pointer
            this.head = newElement;
        }

        this.size += 1;
    }

    public void addBack(T element) {
        /*
        Adds an element to the back of the linked list
         */

        // If the list is empty, this is equivalent to adding to the front
        if (this.isEmpty()) {
            this.addFront(element);

        } else {
            // Otherwise, we need to adjust more pointers

            ListElement newElement = new ListElement(element, this.head, this.tail);

            // Adjust the head and tail pointers to recognize this as the new tail
            this.head.prev = newElement;
            this.tail.next = newElement;

            // Finally, change the tail pointer
            this.tail = newElement;

            // Increment the size element
            this.size += 1;
        }
    }

    public T peekBack() {
        /*
        Returns the back element without popping it
         */

        if (this.isEmpty()) {
            // If empty return null
            return null;
        }

        return this.tail.value;
    }

    public T peekFront() {
        /*
        Returns the front element without popping it
         */

        if (this.isEmpty()) {
            // If empty return null
            return null;
        }

        return this.head.value;
    }

    public T popBack() {
        /*
        Removes and returns the back element
         */

        // Catch the case in which we are empty
        if (this.isEmpty()) {
            return null;
        }

        // If this is the last element we must be careful
        if (this.size == 1) {
            // Placeholder for last element
            T holder = this.head.value;

            // Set tail and head to null
            this.head = null;
            this.tail = null;

            // Decrement size
            this.size -= 1;

            return holder;
        }

        T holder = this.tail.value;

        // Reset the head and tail pointers
        this.tail = this.tail.prev;
        this.tail.next = this.head;
        this.head.prev = this.tail;

        // Decrement size
        this.size -= 1;

        return holder;
    }

    public T popFront() {
        /*
        Removes and returns the front element
         */

        if (this.size <= 1) {
            // This is equivalent to popping back, so we do that
            return this.popBack();
        }

        // Otherwise we just take care of the front
        T holder = this.head.value;

        // Reset the head and tail pointers
        this.head = this.head.next;
        this.head.prev = this.tail;
        this.tail.next = this.head;

        // Decrement size
        this.size -= 1;

        return holder;
    }

    public void reverse() {
        /*
        Reverses the linked list
         */
        if (this.size() <= 1) {
            return;
        }

        ListElement temp;

        // Loop over the list and change all the pointers
        ListElement currentElement = this.head;

        while (currentElement != this.tail) {
            // Swap the two pointers
            temp = currentElement.next;
            currentElement.next = currentElement.prev;
            currentElement.prev = temp;

            // Move to the next element
            currentElement = temp;
        }

        // Finally, take care of the tail
        this.tail.next = this.tail.prev;
        this.tail.prev = this.head;

        // Switch around head and tail pointers
        temp = this.tail;
        this.tail = this.head;
        this.head = temp;
    }

    public T contains(T element) {
        /*
        Checks whether the list contains an element
         */

        // Catch empty list early stop
        if (this.isEmpty()) {
            return null;
        }

        if (this.tail.value.equals(element)) {
            return this.tail.value;
        } else {
            ListElement currentElement = this.head;

            while (currentElement != this.tail) {
                if (currentElement.value.equals(element)) {
                    return currentElement.value;
                }

                currentElement = currentElement.next;
            }

            return null;
        }
    }

    public T delete(T element) {
        /*
        Deletes the first occurrence of element

        Parameters:
            element: The element to delete

        Returns:
            element: If element was found and deleted
            null: If element was not found
         */

        // Catch a possible early stop
        if (this.isEmpty()) {
            return null;
        }

        ListElement currentElement = this.head;

        do {
            // Check if we have found a match
            if (currentElement.value.equals(element)) {
                // If this is the only element in the list handle it as such
                if (this.size == 1) {
                    T temp = this.head.value;
                    this.head = null;
                    this.tail = null;
                    this.size = 0;

                    return temp;
                }

                // If this is the head element reset the pointer
                if (currentElement == this.head) {
                    this.head = this.head.next;
                }

                // If this is the tail reset the pointer
                if (currentElement == this.tail) {
                    this.tail = this.tail.prev;
                }

                // Delete the element
                currentElement.prev.next = currentElement.next;
                currentElement.next.prev = currentElement.prev;

                this.size -= 1;

                return element;
            }

            // Otherwise move on to the next element
            currentElement = currentElement.next;
        } while (currentElement != this.head);

        return null;
    }

    public int size() {
        /*
        Returns the number of elements in the linked list
         */

        return this.size;
    }

    public boolean isEmpty() {
        /*
        Returns whether the linked list is empty
         */

        return this.size == 0;
    }

    public void printList() {
        /*
        Prints out the linked list for visualization
         */

        // Catch the empty case
        if (this.isEmpty()) {
            System.out.println("[]");
            return;
        }

        // Otherwise loop over elements and print them
        ListElement currentElement = this.head;

        while (currentElement != this.tail) {
            System.out.print("[" + currentElement.value + "] --> ");
            currentElement = currentElement.next;
        }

        System.out.println("[" + currentElement.value + "]");
    }
}

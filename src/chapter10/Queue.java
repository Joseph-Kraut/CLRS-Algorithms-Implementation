package chapter10;

public class Queue<T> {

    // A class to store queue elements in
    private class QueueElement {
        private QueueElement next;
        private T element;
        private boolean isSentinel;

        public QueueElement(T element, QueueElement next, boolean isSentinel) {
            this.element = element;
            this.next = next;
            this.isSentinel = isSentinel;
        }

        public void setNext(QueueElement nextElement) {
            /*
            Sets the next pointer to a queue element
             */
            this.next = nextElement;
        }

        public QueueElement getNext() {
            return this.next;
        }

        public boolean isSentinel() {
            /*
            Returns whether or not this is the sentinel element in the queue
             */

            return this.isSentinel;
        }

        public T getElement() {
            /*
            Returns the element stored in this QueueElement
             */

            return this.element;
        }
    }

    private T[] queueArray;
    private int queueLength;
    private QueueElement firstElement;
    private QueueElement lastElement;
    private QueueElement sentinel;

    public Queue(T[] startingArray) {
        this.queueLength = 0;

        // Build the sentinel element
        this.sentinel = new QueueElement(null, null, true);

    }

    public void enqueue(T element) {
        /*
        Enqueues a new element to the queue
         */

        // Build a new QueueElement object
        // Since this will sit at the back of the queue, the sentinel should be the next node
        QueueElement enqueuedElement = new QueueElement(element, this.sentinel, false);

        // Add to the last element in the array
        if (this.isEmpty()) {
            // Set both the first and last element to be this
            this.firstElement = enqueuedElement;
            this.lastElement = enqueuedElement;

        } else {
            this.lastElement.setNext(enqueuedElement);
            this.lastElement = enqueuedElement;
        }

        this.queueLength += 1;
    }

    public T dequeue() {
        /*
        Returns the next element in the stack removing it from the stack
         */

        // Return null if empty
        if (this.isEmpty()) {
            return null;
        }

        // Otherwise return the first element in the stack
        T returnValue = this.firstElement.getElement();

        // Set the first pointer to the new first element
        this.firstElement = this.firstElement.getNext();

        // Decrement the length
        this.queueLength -= 1;

        return returnValue;
    }

    public T peek() {
        /*
        Return the first element in the queue without returning it
         */

        return this.firstElement.getElement();
    }

    public int size() {
        /*
        Return the number of elements in the queue
         */

        return this.queueLength;
    }

    public boolean isEmpty() {
        /*
        Returns whether the queue is empty
         */

        return this.queueLength == 0;
    }
}

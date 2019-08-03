package chapter10;

public class Stack<T> {
    private Object[] stackArray;
    private int topIndex;
    private int stackSize;

    public Stack(T[] startingArray) {
        this.topIndex = -1;
        this.stackSize = 0;

        if (startingArray.length == 0) {
            // Initialize to size 10
            this.stackArray = (T[]) new Object[10];
        } else {
            // Initialize to 2x the starting array size
            this.stackArray = (T[]) new Object[startingArray.length * 2];

            // Push all the values in the startingArray to the stack
            for (T element : startingArray) {
                this.push(element);
            }
        }
    }

    public void push(T obj) {
        /*
        Pushes a new object to the stack

        Parameters:
            obj: The object to push to the stack
         */

        // Add the new element to the array
        this.stackArray[this.topIndex + 1] = obj;

        // Update the top index
        this.topIndex += 1;

        // Incrememnt the number of elements in the stack
        this.stackSize += 1;

        // Resize the array if necessary
        if (this.size() == this.stackArray.length) {
            // Double the stack size
            resizeStack(2);
        }
    }

    public T pop() {
        /*
        Pops the top element off the stack, returns null if empty
         */

        if (this.isEmpty()) {
            return null;
        }

        // Get the object that will be popped
        T returnElement = (T) this.stackArray[this.topIndex];

        // Decrement the pointer to the top element
        this.topIndex -= 1;

        // Decrement the number of stack elements
        this.stackSize -= 1;

        // Resize the array if necessary
        if (this.stackSize < this.stackArray.length * 0.25) {
            // Halve the array size
            this.resizeStack(0.25);
        }

        return returnElement;
    }

    public T peek() {
        /*
        Return the top element without removing it
         */

        if (this.isEmpty()) {
            return null;
        }

        return (T) this.stackArray[this.topIndex];
    }

    public boolean isEmpty() {
        /*
        Returns whether the stack is empty
         */

        return this.stackSize == 0;
    }

    public int size() {
        /*
        Returns the size of the stack
         */

        return this.stackSize;
    }

    private void resizeStack(double resizeFactor) {
        /*
        Resizes the stack to grow or shrink with elements

        Parameters:
            resizeFactor: The multiplicative factor by which to resize the stack capacity
         */

        // Get the new length and build an object array
        int newLength = (int) (resizeFactor * this.stackArray.length);
        T[] newArr = (T[]) new Object[newLength];

        // Copy the elements of the old array in
        for (int i = 0; i < this.size(); i++) {
            newArr[i] = (T) this.stackArray[i];
        }

        this.stackArray = newArr;
    }
}

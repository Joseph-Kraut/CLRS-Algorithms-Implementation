package chapter11;

import chapter10.DoublyLinkedList;

public class HashMap<K, V>{

    // Simple data structure to hold keys and values
    private class TableElement<K, V> {
        public K key;
        public V value;

        public TableElement(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public boolean equals(Object o) {
            if (o.getClass() != this.getClass()) {
                return false;
            }

            TableElement otherElem = (TableElement) o;

            if (otherElem.key.equals(this.key)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private int tableSize;
    private double loadFactor; // Defined as (# elements) / (# buckets)
    private DoublyLinkedList<TableElement>[] hashBuckets;
    private int size;

    public HashMap(int size) {
        this.tableSize = size;
        this.loadFactor = 0.0;
        this.size = 0;

        // Create the buckets for hashing
        this.hashBuckets = new DoublyLinkedList[this.tableSize];

        // Add new linked lists to the buckets
        for (int i = 0; i < this.tableSize; i++) {
            this.hashBuckets[i] = new DoublyLinkedList<>(new TableElement[0]);
        }
    }


    // A constructor with a default table size of 100
    public HashMap() {
        this(100);
    }

    public void insert(K key, V value) {
        /*
        Inserts a key value pair into the hash table

        Parameters:
            key: The key to hash and insert into the table
            value: The value corresponding to the hashed key
         */

        // First compute the hash
        int hashValue = key.hashCode() % this.tableSize;

        // A possible case that happens in testing
        if (hashValue < 0) {
            hashValue = this.tableSize + hashValue;
        }

        // Create a new TableElement
        TableElement newElem = new TableElement(key, value);

        // Append to linked list in bucket
        this.hashBuckets[hashValue].addFront(newElem);

        // Increase size and update load factor
        this.size += 1;
        this.updateLoadFactor();

        // Check load factor for resizing
        if (this.loadFactor > 2) {
            this.resizeHashTable(2.0);
        }
    }

    private void updateLoadFactor() {
        /*
        Updates the load factor after add or delete
         */

        this.loadFactor = ((double) this.size) / ((double) this.tableSize);
    }

    private void resizeHashTable(double factor) {
        /*
        Resizes the hash table to grow while maintaining asymptotic guarantees

        Parameters:
            factor: The factor by which to resize the hash table
         */

        int newSize = (int) factor * this.hashBuckets.length;

        // Allocate an array of the new size
        DoublyLinkedList<TableElement>[] newTable = new DoublyLinkedList[newSize];

        // Initialize this array
        for (int i = 0; i < newSize; i++) {
            newTable[i] = new DoublyLinkedList<>(new TableElement[0]);
        }

        // Maintain a pointer to the old table
        DoublyLinkedList<TableElement>[] oldTable = this.hashBuckets;


        // Set the new table up
        this.hashBuckets = newTable;
        this.tableSize = newSize;
        this.size = 0; // Set size to zero because re-inserting elements will increment size

        TableElement nextElement;

        for (DoublyLinkedList linkedList : oldTable) {
            // Loop through the linked lists and hash them into the new table
            // Get the element from the linked list
            while (linkedList.peekFront() != null) {
                // Get the next element from this list
                nextElement = (TableElement) linkedList.popFront();

                // Insert this element into the new table
                this.insert((K) nextElement.key, (V) nextElement.value);
            }
        }
    }

    public V search(K key) {
        /*
        Searches for a key and returns its value

        Parameters:
            key: The key to search for in the hash table

        Returns:
            null: If the key is not in the hash table
            value in <key, value> if key is in hash table
         */

        // Hash the key to the appropriate spot
        int hash = key.hashCode() % this.tableSize;

        if (hash < 0) {
            hash = this.tableSize + hash;
        }

        // A dummy object we use to interface with the doubly linked list
        TableElement searchElement = new TableElement(key, null);
        // The result of the search, either the element or null
        TableElement searchResult = this.hashBuckets[hash].contains(searchElement);

        if (searchResult == null) {
            return null;
        } else {
            return (V) searchResult.value;
        }
    }

    public V delete(K key) {
        /*
        Deletes the first instance of an element from the hash table

        Parameters:
            key: The key of the table element to delete

        Returns:
            null: If the element is not in the table
            value: In <key, value> if the element is in the table
         */

        // Compute the hash
        int hash = key.hashCode() % this.tableSize;

        if (hash < 0) {
            hash = this.tableSize + hash;
        }

        // Delete the element in the relevant bucket
        TableElement dummyElement = new TableElement(key, null);

        TableElement deleted = this.hashBuckets[hash].delete(dummyElement);

        if (deleted == null) {
            return null;
        } else {
            this.size -= 1;
            return (V) deleted.value;
        }
    }

    public int getSize() {
        /*
        Returns the number of elements stored in the table
         */

        return this.size;
    }

}

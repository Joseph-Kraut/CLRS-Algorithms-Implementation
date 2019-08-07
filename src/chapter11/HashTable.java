package chapter11;

import chapter10.DoublyLinkedList;

public class HashTable<K, V>{

    // Simple data structure to hold keys and values
    private class TableElement<K, V> {
        public K key;
        public V value;

        public TableElement(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int tableSize;
    private double loadFactor; // Defined as (# elements) / (# buckets)
    private DoublyLinkedList[] hashBuckets;

    public HashTable(int size) {
        this.tableSize = size;
        this.loadFactor = 0.0;

        // Create the buckets for hashing
        this.hashBuckets = new DoublyLinkedList[this.tableSize];

    }


    // A constructor with a default table size of 100
    public HashTable() {
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
        return;
    }


}

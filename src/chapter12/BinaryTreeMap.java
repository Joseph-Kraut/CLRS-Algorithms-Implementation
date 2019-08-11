package chapter12;

import jdk.nashorn.api.tree.Tree;

public class BinaryTreeMap<K extends Comparable, V> {

    // Class for tree elements
    private class TreeElement {
        K key;
        V value;
        TreeElement rightChild;
        TreeElement leftChild;
        TreeElement parent;

        public TreeElement(K key, V value) {
            this.key = key;
            this.value = value;
            this.rightChild = rightChild;
            this.leftChild = leftChild;
            this.parent = parent;
        }

        public int compareTo(TreeElement otherElement) {
            /*
            Compares two tree elements by their compare method
             */

            return this.key.compareTo(otherElement.key);
        }
    }

    private TreeElement root;
    private int size;

    public BinaryTreeMap() {
        // Builds an empty binary search tree
        this.root = null;
        this.size = 0;
    }

    public void insert(K key, V value) {
        /*
        Inserts a key value pair into the search tree

        Parameters:
            key: The key to compare to other elements
            value: The value to store in this TreeMap
         */
        // Create the new Tree Element
        TreeElement newElement = new TreeElement(key, value);

        // Pointers we will use during iteration
        TreeElement currentNode = this.root;
        TreeElement currentParent = null;

        // Traverse the tree to find the appropriate leaf
        while (currentNode != null) {
            currentParent = currentNode;

            if (newElement.compareTo(currentNode) >= 0) {
                // Add to right subtree
                currentNode = currentNode.rightChild;
            } else {
                currentNode = currentParent.leftChild;
            }
        }

        // Set the parent of the new element to be the last node we traversed
        newElement.parent = currentParent;

        // Set the correct child
        if (currentParent == null) {
            this.root = newElement;
        } else if (newElement.compareTo(currentParent) >= 0) {
            // Insert to the right
            currentParent.rightChild = newElement;
        } else {
            currentParent.leftChild = newElement;
        }

        this.size += 1;
    }

    private TreeElement lookUpTreeElement(K key) {
        /*
        Searches for key and outputs its Tree Node

        Parameters:
            key: The key to look in the binary tree for

        Returns:
            null: If the key is not in the binary tree
            TreeNode: The node that has this key in the tree
         */

        TreeElement currentNode = this.root;
        // A dummy node for comparisons
        TreeElement dummyElement = new TreeElement(key, null);

        while (currentNode != null) {
            // If this is the correct element return it
            if (dummyElement.compareTo(currentNode) == 0) {
                // Return the value
                return currentNode;
            } else if (dummyElement.compareTo(currentNode) < 0) {
                // Search the left sub-tree
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
        }

        return null;
    }

    private TreeElement min(TreeElement subTreeRoot) {
        /*
        Finds the minimum element of the sub-tree rooted at subTreeRoot
         */

        TreeElement currentNode = subTreeRoot;

        while (currentNode.leftChild != null) {
            currentNode = currentNode.leftChild;
        }

        return currentNode;
    }

    public V min() {
        /*
        Returns the node of min value in the tree
         */

        return this.min(this.root).value;
    }

    private TreeElement max(TreeElement subTreeRoot) {
        /*
        Finds the element of maximum value in the sub-tree rooted at subTreeRoot
         */

        TreeElement currentNode = subTreeRoot;

        while (currentNode.rightChild != null) {
            currentNode = currentNode.rightChild;
        }

        return currentNode;
    }

    public V max() {
        /*
        Returns the maximum value in the tree
         */

        return this.max(this.root).value;
    }

    public V lookup(K key) {
        /*
        Searches for key and outputs its value

        Parameters:
            key: The key to look in the binary tree for

        Returns:
            null: If the key is not in the binary tree
            value: The value corresponding to the key if it exists
         */

        return lookUpTreeElement(key).value;
    }

    public V delete(K key) {
        /*
        Deletes the tree element containing the key and returns its value

        Parameters:
            key: The key of the tree element to delete

        Returns:
            The value corresponding to the passed key, or null if this key isn't in the tree
         */

        // First find the node in question
        TreeElement nodeToDelete = this.lookUpTreeElement(key);

        if (nodeToDelete == null) {
            return null;
        }

        if (nodeToDelete.leftChild == null) {
            // Replace with right child
            this.transplant(nodeToDelete, nodeToDelete.rightChild);
        } else if (nodeToDelete.rightChild == null) {
            // Otherwise replace with left child
            this.transplant(nodeToDelete, nodeToDelete.leftChild);
        } else {
            // Otherwise we have to get the correct child to replace
            // Find the min of the tree rooted at the node we want to delete's right subtree
            TreeElement replaceWith = this.min(nodeToDelete.rightChild);

            if (replaceWith.parent != nodeToDelete) {
                // If this isn't a direct child of the delete node we have to do a bit more work
                this.transplant(replaceWith, replaceWith.rightChild);
                replaceWith.rightChild = nodeToDelete.rightChild;
                replaceWith.rightChild.parent = replaceWith;
            }

            // Now transplant the node to delete with the one we just set up
            this.transplant(nodeToDelete, replaceWith);
            replaceWith.leftChild = nodeToDelete.leftChild;
            replaceWith.leftChild.parent = replaceWith;
        }

        this.size -= 1;

        return nodeToDelete.value;
    }

    private void transplant(TreeElement firstNode, TreeElement secondNode) {
        /*
        Replaces first node with second node
         */

        if (firstNode.parent == null) {
            this.root = secondNode;
        } else if (firstNode == firstNode.parent.leftChild) {
            // If this is a left child, replacement should also be
            firstNode.parent.leftChild = secondNode;
        } else {
            // This is a right child, replacement should follow suit
            firstNode.parent.rightChild = secondNode;
        }

        if (secondNode != null) {
            secondNode.parent = firstNode.parent;
        }
    }

    public int getSize() {
        /*
        Returns the number of elements stored in the tree
         */
        return this.size;
    }
}

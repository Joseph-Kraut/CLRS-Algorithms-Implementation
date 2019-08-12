package chapter13;
import chapter12.BinaryTreeMap;

public class RedBlackTree<K extends Comparable, V> extends BinaryTreeMap<K, V> {

    // A class to hold the Tree Nodes
    private class TreeElement {
        public K key;
        public V value;
        public TreeElement parent;
        public TreeElement rightChild;
        public TreeElement leftChild;
        public boolean isRed;


        public TreeElement(K key, V value, TreeElement parent, TreeElement rightChild, TreeElement leftChild, boolean isRed) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.rightChild = rightChild;
            this.leftChild = leftChild;
            this.isRed = isRed;
        }

        public int compareTo(TreeElement other) {
            /*
            The standard compareTo method of comparables
             */

            return this.key.compareTo(other.key);
        }
    }

    private int size;
    private TreeElement root;
    private TreeElement sentinel;

    public RedBlackTree() {
        // Constructs a new red-black tree with no elements
        this.size = 0;
        this.root = null;
        this.sentinel = new TreeElement(null, null, null, null, null, false);
    }

    private void rotateLeft(TreeElement node) {
        /*
        Performs the red black left rotation on the given node
         */

        TreeElement newParent = node.rightChild;
        // Switch the left child into the former parent
        node.rightChild = newParent.leftChild;

        // Set the parent pointer for this swapped node
        if (newParent.leftChild != this.sentinel) {
            newParent.leftChild.parent = node;
        }

        // Set the new parent's pointers
        newParent.parent = node.parent;

        // Set the new parent's parent's pointers
        if (node.parent == this.sentinel) {
            this.root = newParent;
        } else if (node == node.parent.leftChild) {
            node.parent.leftChild = newParent;
        } else {
            node.parent.rightChild = newParent;
        }

        newParent.leftChild = node;
        node.parent = newParent;
    }

    private void rotateRight(TreeElement node) {
        /*
        Performs a rotate right red black transform on the given node
         */

        TreeElement newParent = node.leftChild;
        // Switch the left child into the former parent
        node.leftChild = newParent.rightChild;

        // Set the parent pointer for this swapped node
        if (newParent.rightChild != this.sentinel) {
            newParent.rightChild.parent = node;
        }

        // Set the new parent's pointers
        newParent.parent = node.parent;

        // Set the new parent's parent's pointers
        if (node.parent == this.sentinel) {
            this.root = newParent;
        } else if (node == node.parent.leftChild) {
            node.parent.leftChild = newParent;
        } else {
            node.parent.rightChild = newParent;
        }

        newParent.rightChild = node;
        node.parent = newParent;
    }

    private void fixInsert(TreeElement node) {
        /*
        Fixes the red black violations that may have occured when inserting node

        Parameters:
            node: The TreeElement just inserted that may need fixing
         */

        while (node.parent.isRed) {

            if (node.parent == node.parent.parent.leftChild) {
                // Case for which the parent is a left child
                TreeElement uncle = node.parent.parent.rightChild;

                if (uncle.isRed) {
                    // If the uncle is red we encounter our first case
                    node.parent.isRed = false;
                    uncle.isRed = false;

                    // Color in the grandparent as red
                    node.parent.parent.isRed = true;

                    // Move up to the grandparent and correct any violations there
                    node = node.parent.parent;
                } else {
                    // If the inserted node is a right child and its uncle is black
                    if (node == node.parent.rightChild) {
                        // Rotate to left child
                        node = node.parent;
                        this.rotateLeft(node);
                    }

                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    this.rotateRight(node.parent.parent);
                }
            } else {
                // Case for which the parent is a right child
                TreeElement uncle = node.parent.parent.leftChild;

                if (uncle.isRed) {
                    // If the uncle is red, we must make it black and move up the tree
                    node.parent.isRed = false;
                    uncle.isRed = false;

                    // Color in the grandparent
                    node.parent.parent.isRed = true;

                    node = node.parent.parent;
                } else {
                    // If the inserted node is a right child, rotate left then work from there
                    if (node == node.parent.leftChild) {
                        node = node.parent;
                        this.rotateRight(node);
                    }

                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    this.rotateLeft(node.parent.parent);
                }
            }
        }

        this.root.isRed = false;
    }

    @Override
    public void insert(K key, V value) {
        /*
        Inserts a key value pair into the tree

        Parameters:
            key: The key to be inserted
            value: The value associated with this key
         */
        TreeElement newElement = new TreeElement(key, value, null, this.sentinel, this.sentinel, true);

        // If this is the first element, just make it the root and return
        if (this.getSize() == 0) {
            this.root = newElement;
            this.root.parent = this.sentinel;
            this.root.isRed = false;
            this.size += 1;
            return;
        }

        TreeElement parentNode = null;
        TreeElement currentNode = this.root;

        // Find the correct place to put this new node
        while (currentNode != this.sentinel) {
            parentNode = currentNode;

            if (newElement.compareTo(currentNode) > 0) {
                // Move to the right subtree
                currentNode = currentNode.rightChild;
            } else {
                currentNode = currentNode.leftChild;
            }
        }

        if (newElement.compareTo(parentNode) > 0) {
            parentNode.rightChild = newElement;
        } else {
            parentNode.leftChild = newElement;
        }

        newElement.parent = parentNode;

        // Fix the red black properties
        this.fixInsert(newElement);

        this.size += 1;
    }

    @Override
    public V delete(K key) {
        /*
        Deletes a key from the tree and returns its corresponding value

        Parameters:
            key: the key to be deleted

        Returns:
            null: If the key is not in the red black tree
            value: The value associated with the key if the key is in the tree
         */

        return null;
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
        TreeElement dummyElement = new TreeElement(key, null, null, null, null, true);

        while (currentNode != this.sentinel) {
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

        while (currentNode.leftChild != this.sentinel) {
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

        while (currentNode.rightChild != sentinel) {
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

    public int getSize() {
        return this.size;
    }
}

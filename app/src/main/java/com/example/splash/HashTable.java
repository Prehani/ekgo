package com.example.splash;
import java.lang.reflect.Array;

// Instantiable hash table object. Uses an array of linked nodes, with three linked
// nodes at each location for for collision resolution
//
// The hash function uses the java hashcode for the object V, and finds an index
// by taking that number modulo the table capacity

@SuppressWarnings("unchecked")
public class HashTable<K extends Comparable<K>, V> {

    private Node[] hashTable; // hashtable that is an array of type nodes
    private final double maxLF; // max load factor for the table
    private int keyNum; // current number of keys in the hashtable
    private int addrNum; // current number of address in hashTable being used
    private int[] addrDepth; // array of ints, where the number at addrDepht[i] is the number of nodes
    // at hashTable[i]

    /**
     * Nodes that will hold the value data in the hashtable
     */
    private class Node {
        private K key;
        private V value;
        private Node child;

        // no argument constructor
        private Node() {
            key = null;
            value = null;
            child = null;
        }
    }

    // no argument constructor
    public HashTable() {
        hashTable = (HashTable<K, V>.Node[]) Array.newInstance(HashTable.Node.class, 1025);
        maxLF = .75; // default load factor of 75%
        keyNum = 0; // no keys in the table
        addrNum = 0; // no addresses filled yet
        addrDepth = new int[1025];
    }

    // initializes the hashtable with capacity of initialCapacity and threshold of loadFactorThreshold
    public HashTable(int initialCapacity, double loadFactorThreshold) {
        hashTable = (HashTable<K, V>.Node[]) Array.newInstance(HashTable.Node.class, initialCapacity);
        maxLF = loadFactorThreshold;
        keyNum = 0; // no keys in the table
        addrNum = 0; // no addresses filled yet
        addrDepth = new int[initialCapacity];
    }

    // Helper method that provides the index in the hashtable for the specified key
    private int hashFunction(K key, int capacity) {
        int idx = key.hashCode() % capacity;
        if (idx < 0) { // gets absolute value of hashCode
            idx *= -1;
        }
        return (idx);
    }

    // Helper method that resizes the table
    // new table has capacity 2 * previous capacity, plus one and copies old elements
    private Node[] resizeTable(Node[] oldTable, int newCapacity) {
        // new hashtable with 2 * previous capacity, plus one
        Node[] hashTable2 =
                (HashTable<K, V>.Node[]) Array.newInstance(HashTable.Node.class, newCapacity);
        hashTable = hashTable2;
        addrDepth = new int[newCapacity];

        // add previous nodes from old table to new table
        for (int i = 0; i < oldTable.length; ++i) {
            // only copy over to new array if non-null
            if (oldTable[i] != null) {
                // nonempty array idx in old hashtable
                Node currentNode = oldTable[i];
                while (currentNode != null) {
                    K insKey = currentNode.key;
                    V insValue = currentNode.value;
                    int idx = hashFunction(insKey, this.getCapacity());

                    // need another resize(resize triggered another resize)
                    if (addrDepth[idx] >= 3) {
                        int nextCapacity = (2 * newCapacity) + 1; // even newer capacity
                        return resizeTable(oldTable, nextCapacity);
                    } else { // add the element to the new table
                        try {
                            this.insert(insKey, insValue);
                            currentNode = currentNode.child; // advanced to next node to copy
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return hashTable;
    }

    // Add the key,value pair to the data structure and increase the number of keys.
    // If key is null, throw IllegalNullKeyException;
    // If key is already in data structure, replace value with new value
    public void insert(K key, V value) throws IllegalNullKeyException {
        if (key == null) {
            throw new IllegalNullKeyException();
        }
        int newCapacity = (this.getCapacity() * 2) + 1; // new capacity upon resize
        int idx = hashFunction(key, this.getCapacity()); // index for the key in the table

        // no nodes at index yet
        if (hashTable[idx] == null) {

            Node node1 = new Node(); // nodes that will hold keys in the table index
            hashTable[idx] = node1; // add nodes to the index of the table
            node1.key = key;
            node1.value = value;
            keyNum += 1; // one more key added to table
            addrNum += 1; // one more address used in table
            addrDepth[idx] += 1; // one more node at this idx
            if (this.getLoadFactor() >= maxLF) { // resize if load factor exceeded
                resizeTable(hashTable, newCapacity);
            }

        } else { // already nodes at index

            // resize required
            if (addrDepth[idx] >= 3) {
                resizeTable(hashTable, newCapacity);
                insert(key, value);
            } else { // resize not required

                idx = hashFunction(key, this.getCapacity());
                Node newNode = new Node(); // node to add
                newNode.key = key;
                newNode.value = value;

                // check first node if open or duplicate
                if (hashTable[idx] == null) {
                    hashTable[idx] = newNode;
                    keyNum += 1;
                    addrNum += 1;
                    addrDepth[idx] += 1;
                } else if (hashTable[idx].key.compareTo(key) == 0) {
                    hashTable[idx].value = value;
                }
                // check second node if open or duplicate
                else if (hashTable[idx].child == null) {
                    hashTable[idx].child = newNode;
                    keyNum += 1;
                    addrDepth[idx] += 1;
                } else if (hashTable[idx].child.key.compareTo(key) == 0) {
                    hashTable[idx].child.value = value;
                }
                // check third node if open or duplicate
                else if (hashTable[idx].child.child == null) {
                    hashTable[idx].child.child = newNode;
                    keyNum += 1;
                    addrDepth[idx] += 1;
                } else if (hashTable[idx].child.child.key.compareTo(key) == 0) {
                    hashTable[idx].child.child.value = value;
                }
            }
        }
    }

    // If key is found,
    // remove the key,value pair from the data structure
    // decrease number of keys.
    // return true
    // If key is null, throw IllegalNullKeyException
    // If key is not found, return false
    public boolean remove(K key) throws IllegalNullKeyException {
        if (key == null) {
            throw new IllegalNullKeyException();
        }

        // use get method to see if key is in the table
        try {
            get(key); // will throw exception if key does not exist in table
            int idx = hashFunction(key, this.getCapacity()); // idx of the key
            Node currentNode = hashTable[idx]; // current node in traversal

            // first node is to be removed
            if (currentNode.key.compareTo(key) == 0) {
                hashTable[idx] = currentNode.child; // remove node
                keyNum -= 1; // one less key in table
                addrDepth[idx] -= 1; // one less node this idx
                if (hashTable[idx] == null) { // no more nodes at the idx
                    addrNum -= 1; // one less address taken up
                }
            } else if (currentNode.child.key.compareTo(key) == 0) { // second node is to be removed
                currentNode.child = currentNode.child.child; // remove node
                keyNum -= 1;
                addrDepth[idx] -= 1;
            } else { // third node is to be removed
                currentNode.child.child = null; // remove node
                keyNum -= 1;
                addrDepth[idx] -= 1;
            }
            return true;
        } catch (Exception e) { // KeyNotFoundException thrown by get
            return false;
        }
    }

    // Returns the value associated with the specified key
    // Does not remove key or decrease number of keys
    //
    // If key is null, throw IllegalNullKeyException
    // If key is not found, throw KeyNotFoundException().
    public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
        if (key == null) {
            throw new IllegalNullKeyException();
        }
        int idx; // index of the current key
        idx = hashFunction(key, this.getCapacity()); // get index of the current key

        // no nodes at index
        if (hashTable[idx] == null) {
            throw new KeyNotFoundException();
        }

        // there are nodes at index
        Node currentNode = hashTable[idx];
        for (int i = 0; i < addrDepth[idx]; ++i) {
            if (currentNode.key.compareTo(key) == 0) {
                return currentNode.value;
            }
            currentNode = currentNode.child;
        }
        throw new KeyNotFoundException();
    }

    // Returns the number of key,value pairs in the data structure
    public int numKeys() {
        return keyNum;
    }

    // Returns the load factor threshold that was
    // passed into the constructor when creating
    // the instance of the HashTable.
    // When the current load factor is greater than or
    // equal to the specified load factor threshold,
    // the table is resized and elements are rehashed.
    public double getLoadFactorThreshold() {
        return maxLF;
    }

    // Returns the current load factor for this hash table
    // load factor = number of items / current table size
    public double getLoadFactor() {
        return ((double) addrNum / this.getCapacity());
    }

    // Return the current Capacity (table size)
    // of the hash table array.
    //
    // The initial capacity must be a positive integer, 1 or greater
    // and is specified in the constructor.
    //
    // REQUIRED: When the load factor threshold is reached,
    // the capacity must increase to: 2 * capacity + 1
    //
    // Once increased, the capacity never decreases
    public int getCapacity() {
        return hashTable.length;
    }

}


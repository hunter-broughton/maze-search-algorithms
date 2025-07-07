/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * Heap.java
 * 
 * This class implements a binary heap data structure that serves as a priority queue.
 * It uses a node-based approach with references to parent and child nodes, making it
 * suitable for dynamic insertion and deletion operations. The heap can be configured
 * as either a min-heap or max-heap and supports custom comparators for defining
 * element priority. This implementation is used by the A* search algorithm.
 * 
 * To compile: javac Heap.java
 */

import java.util.Comparator;

/**
 * Binary heap implementation of a priority queue using a node-based tree structure.
 * Supports both min-heap and max-heap configurations with custom comparators.
 */
public class Heap<T> implements PriorityQueue<T>
{
    /**
     * Node class representing individual elements in the heap tree.
     * Each node contains data and references to its parent and children.
     */
    private static class Node<T>
    {
        Node<T> left, right, parent;  // Tree structure references
        T data;                       // The data stored in this node

        /**
         * Constructs a new node with the specified data and references.
         * 
         * @param data the data to store in this node
         * @param left reference to the left child node
         * @param right reference to the right child node
         * @param parent reference to the parent node
         */
        public Node(T data, Node<T> left, Node<T> right, Node<T> parent)
        {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    // Instance fields for heap management
    private int size;                    // Number of elements in the heap
    private Node<T> root, last;          // Root and last inserted nodes
    private Comparator<T> comparator;    // Comparator for element ordering

    /**
     * Default constructor creates a min-heap with natural ordering.
     */
    public Heap()
    {
        this(null, false);
    }

    /**
     * Constructor that allows specification of min-heap vs max-heap.
     * 
     * @param maxHeap true for max-heap, false for min-heap
     */
    public Heap(boolean maxHeap)
    {
        this(null, maxHeap);
    }

    /**
     * Constructor that accepts a custom comparator for element ordering.
     * Creates a min-heap by default.
     * 
     * @param comparator the comparator to use for element ordering
     */
    public Heap(Comparator<T> comparator)
    {
        this(comparator, false);
    }

    /**
     * Full constructor that allows specification of both comparator and heap type.
     * If comparator is null, uses natural ordering of Comparable elements.
     * 
     * @param comparator the comparator to use for element ordering
     * @param maxHeap true for max-heap, false for min-heap
     */
    public Heap(Comparator<T> comparator, boolean maxHeap)
    {
        if (comparator != null)
        {
            this.comparator = comparator;
        }
        else 
        {
            // Create default comparator using natural ordering
            this.comparator = new Comparator<T>()
            {
                @Override
                public int compare(T o1, T o2)
                {
                    return ((Comparable<T>) o1).compareTo(o2);
                }
            };
        }
        if (maxHeap)
        {
            this.comparator = new Comparator<T>()
            {
                @Override
                public int compare(T o1, T o2)
                {
                    return Heap.this.comparator.compare(o2, o1);
                }
            };
        }
    }


    /*
     * swaps the data of two specified nodes
     */
    private void swap(Node<T> node1, Node<T> node2)
    {
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }


    /*
     * bubbles up a node to where it should be in the minHeap
     */
    private void bubbleUp(Node<T> curNode)
    {
        if (curNode == root)
        {
            return;
        }

        T myData = curNode.data;
        T parentData = curNode.parent.data;

        if (comparator.compare(myData, parentData) < 0)
        {
            swap(curNode, curNode.parent);
            bubbleUp(curNode.parent);
        }
    }


    /*
     * bubbles a node down to where it should be in the minHeap
     */
    private void bubbleDown(Node<T> curNode)
    {
        if (curNode.left == null)
        {
            return;
        }
        else if (curNode.right == null)
        {
            if (comparator.compare(curNode.left.data, curNode.data) < 0)
            {
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        }
        else 
        {
            if (comparator.compare(curNode.left.data, curNode.right.data) < 0)
            {
                if (comparator.compare(curNode.left.data, curNode.data) < 0)
                {
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            }
            else 
            {
                if (comparator.compare(curNode.right.data, curNode.data) < 0)
                {
                    swap(curNode, curNode.right);
                    bubbleDown(curNode.right);
                }
            }
        }
    }


    /*
     * offer  - adds an item to the priority Queue
     */
    public void offer(T item) 
    {
        if (size == 0)
        {
            root = new Node<T>(item, null, null, null);
            last = root;
            size++;
            return;
        }

        if (size % 2 == 0)
        {
            last.parent.right = new Node<T>(item, null, null, last.parent);
            size++;
            last = last.parent.right;
            bubbleUp(last);
            return;
        }
        else 
        {
            Node<T> curNode = last;
            while (curNode != root && curNode == curNode.parent.right)
            {
                curNode = curNode.parent;
            }

            if (curNode != root)
            {
                curNode = curNode.parent.right;
            }

            while (curNode.left != null)
            {
                curNode = curNode.left;
            }
            curNode.left = new Node<T>(item, null, null, curNode);
            last = curNode.left;
        }
        size++;
        bubbleUp(last);
        return;
    }


    /*
     * poll - removes the item of highest priority from the priority queue
     */
    public T poll() 
    {
        if (size == 0)
        {
            return null;
        }
        
        if(size == 1)
        {
            T removed = root.data;
            root = null;
            last = null;
            size--;
            return removed;
        }
        
        swap(root, last);
        
        if (size % 2 == 1)
        {
            T removed = last.data;
            last.parent.right = null;
            last = last.parent.left;
            bubbleDown(root);
            size--;
            return removed;
        }
        else
        {
            T removed = last.data;
            Node<T> curNode = last;
            while (curNode != root && curNode == curNode.parent.left)
            {
                curNode = curNode.parent;
            }

            if (curNode != root)
            {
                curNode = curNode.parent.left;
            }

            while (curNode.right != null)
            {
                curNode = curNode.right;
            }
            last.parent.left = null;
            last = curNode;
            size--;
            bubbleDown(root);
            return removed;
        }
    }
    

    /*
     * returns the size of the priority queue
     */
    public int size() 
    {
        return size;
    }

    /*
     * returns the data from the hgihest priority object in the priority queue
     */
    public T peek() 
    {
        return root.data;
    }


    /*
     * updates the priority of a specifeid item in the priority queue
     */
    public void updatePriority(T item) 
    {
        Node<T> node = findNode(item);
        updatePriority(item, node);
    }


    /*
     * recursive method to update the priority of a specified item, but this method 
     * takes in a node parameter to find the node who has data with the parameter: T item
     */
    private void updatePriority(T item, Node<T> node)
    {
        if (comparator.compare(node.data, node.parent.data) < 0)
        {
            swap(node, node.parent);
            node = node.parent;
        }
        else if (comparator.compare(node.data, node.left.data) > 0)
        {
            swap(node, node.left);
            node = node.left;
        }
        else if (comparator.compare(node.data, node.right.data) > 0)
        {
            swap(node, node.right);
            node = node.right;
        }
        else 
        {
            return;
        }
        updatePriority(item, node);
    }


    /*
     * finds a specified node in the heap with a specifed set of data 
     */
    private Node<T> findNode(T item)
    {
        if (size == 0)
        {
            return null;
        }
        return findNode(item, root);
    }


    /*
     * recursive method to find the specified node
     */
    private Node<T> findNode(T item, Node<T> curNode)
    {
        if (curNode == null)
        {
            return null;
        }
        if (comparator.compare(curNode.data, item) == 0)
        {
            return curNode;
        }
        Node<T> node = findNode(item, curNode.left);
        node = findNode(item, curNode.right);
        return node;
    }
}




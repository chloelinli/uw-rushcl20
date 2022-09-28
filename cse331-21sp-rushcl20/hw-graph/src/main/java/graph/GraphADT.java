package graph;

import java.util.*;

/**
 * GraphADT represents a mutable directed labeled graph of nodes
 *
 * Spec fields:
 * @spec.specfield lst : List // List of nodes
 * @spec.specfield size : int // size of graph
 *
 * Abstract Invariant:
 * No nodes in graph may contain exactly the same data
 */
public class GraphADT {

    /**
     * List of Nodes
     */
    private final List<Node> lst;

    /**
     * Size of graph
     */
    public int size;

    // Representation Invariant:
    // graph doesn't contain null nodes
    //
    // Abstraction Function:
    // AF(this) = [adt[0], adt[1], ..., adt[adt.size()-1]]

    /**
     * Constructs new empty GraphADT
     */
    public GraphADT() {
        this.lst = new ArrayList<Node>();
        this.size = 0;
        checkRep();
    }

    /**
     * Constructs new GraphADT containing n
     *
     * @param n node to insert
     * @spec.requires n not null
     */
    public GraphADT(Node n) {
        this.lst = new ArrayList<Node>();
        this.size = 0;
        if (n != null) {
            this.lst.add(n);
            this.size++;
        }
        checkRep();
    }

    /**
     * Constructs new GraphADT containing nodes in l, if null list throw exception
     *
     * @param l list containing nodes to add
     * @throws IllegalArgumentException if null list
     */
    public GraphADT(List<Node> l) {
        if (l == null) {
            throw new IllegalArgumentException("bad list");
        }
        this.lst = new ArrayList<Node>();
        this.size = 0;
        for (int i = 0; i < l.size(); i++) {
            Node n = l.get(i);
            if (n != null) {
                this.lst.add(n);
                this.size++;
            }
        }
        checkRep();
    }

    /**
     * Constructs new GraphADT containing node with value data
     *
     * @param data value to be held by node to insert in graph
     * @spec.requires data not null
     */
    public GraphADT(Object data) {
        this.lst = new ArrayList<Node>();
        this.size = 0;
        if (data != null) {
            Node n = new Node(data);
            this.lst.add(n);
            this.size++;
        }
        checkRep();
    }

    /**
     * Returns true if graph contains a node with same data as n, if null node throw exception
     *
     * @param n node to be compared
     * @throws IllegalArgumentException if null node
     * @return true iff graph contains a node with same data as n
     */
    public boolean contains(Node n) {
        if (n == null) {
            throw new IllegalArgumentException("bad node");
        }

        for (Node k : this.lst) {
            if (k.data == n.data) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns node at given index, if index out of range throw exception
     *
     * @param index requestd index in graph
     * @throws IllegalArgumentException if index out of range
     * @return a Node n at index in graph
     */
    public Node get(int index) {
        if (index >= this.size) {
            throw new IllegalArgumentException("index out of range");
        }

        return this.lst.get(index);
    }

    /**
     * Returns index of node in graph, if null node or graph doesn't contain throw exception
     *
     * @param n node in question
     * @throws IllegalFormatCodePointException if null node or graph does'nt contain
     * @return an integer i such that i >= 0
     */
    public int indexOf(Node n) {
        if (n == null || !this.contains(n)) {
            throw new IllegalArgumentException("bad node or graph does not contain");
        }
        for (int i = 0; i < this.size; i++) {
            Node k = this.lst.get(i);
            if (k.equals(n)) {
                return i;
            }
        }

        // returns -1 if does not contain - unnecessary but java is complaining
        return -1;
    }

    /**
     * Adds new node at end of graph, if null or duplicate node throw exception
     *
     * @param n node to add
     * @throws IllegalArgumentException if null or duplicate node
     * @return a GraphADT g such that the nodes contain n
     * @spec.effects size++
     */
    public GraphADT add(Node n) {
        checkRep();

        if (this.contains(n) || n == null) {
            throw new IllegalArgumentException("bad node or duplicate node");
        }

        List<Node> temp = this.lst;
        temp.add(new Node(n.data));
        this.size++;

        checkRep();
        return new GraphADT(temp);
    }

    /**
     * Remove node from beginning of graph, if empty graph throw exception
     *
     * @throws IllegalArgumentException if graph has no nodes
     * @return a GraphADT g such that g does not contain node n at beginning of list
     * @spec.effects size-- and outgoing edges to node are removed
     */
    public GraphADT remove() {
        checkRep();

        if (this.size == 0) {
            throw new IllegalArgumentException("empty graph");
        }

        List<Node> tempList = this.lst;
        Node tempNode = tempList.get(0);
        tempList.remove(0);
        this.size--;

        for (int i = tempList.size()-1; i >= 0; i--) {
            Node currNode = tempList.get(i);
            if (currNode.containsN(tempNode)) {
                tempList.remove(i);
                currNode = currNode.removeN(tempNode);
                tempList.add(i, currNode);
            }
        }

        checkRep();
        return new GraphADT(tempList);
    }

    /**
     * Removes node at index in graph, if empty graph or index out of range throw exception
     *
     * @param index requested index of graph
     * @throws IllegalArgumentException if empty graph or index out of range
     * @return a GraphADT g such that it doesn't contain the original Node at index in graph
     * @spec.effects size-- and outgoing edges to original node are removed
     */
    public GraphADT remove(int index) {
        checkRep();

        if (this.size == 0 || index >= this.size) {
            throw new IllegalArgumentException("empty graph or index out of range");
        }

        List<Node> tempList = this.lst;
        Node tempNode = tempList.get(index);
        tempList.remove(index);
        this.size--;

        for (int i = tempList.size()-1; i >= 0; i--) {
            Node currNode = tempList.get(i);
            tempList.remove(i);
            if (currNode.equals(tempNode)) {
                currNode = currNode.removeN(tempNode);
            }
            tempList.add(i, currNode);
        }

        checkRep();
        return new GraphADT(tempList);
    }

    /**
     * Removes node n from graph, if null node, empty graph, or graph doesn't contain node throw exception
     *
     * @param n requested node to remove
     * @throws IllegalArgumentException if null node, empty graph, or graph doesn't contain
     * @return a GraphADT such that it doesn't contain n
     * @spec.effects size-- and outgoing edges to n are removed
     */
    public GraphADT remove(Node n) {
        checkRep();

        if (this.size == 0 || !this.contains(n) || n == null) {
            throw new IllegalArgumentException("bad node, empty graph, or graph doesn't contain");
        }

        List<Node> tempList = this.lst;
        for (int i = tempList.size()-1; i >= 0; i--) {
            Node k = tempList.get(i);
            if (k.equals(n)) {
                tempList.remove(i);
                this.size--;
            }
        }

        for (int i = tempList.size()-1; i >= 0; i--) {
            Node currNode = tempList.get(i);
            if (currNode.containsN(n)) {
                tempList.remove(i);
                currNode = currNode.removeN(n);
                tempList.add(i, currNode);
            }
        }

        checkRep();
        return new GraphADT(tempList);
    }

    /**
     * Adds edge from n1 to n2 with name e, if null node(s), null e, graph doesn't contain node(s), throw exception
     *
     * @param n1 node to add edge to
     * @param n2 node edge goes to
     * @param e name of edge
     * @throws IllegalArgumentException if null node(s), null e, graph doesn't contain node, or  edge exists
     * @return a GraphADT g such that n1 contains an edge to n2 with name e
     */
    public GraphADT addEdge(Node n1, Node n2, Object e) {
        checkRep();

        Map<Object, Node> m = new HashMap<Object, Node>();
        m.put(e, n2);

        if (n1 == null || n2 == null || e == null || !this.contains(n1) || !this.contains(n2)) {
            throw new IllegalArgumentException("bad node(s), bad object name, or edge already exists");
        }

        List<Node> tempList = this.lst;
        GraphADT g = new GraphADT(tempList);

        int index = g.indexOf(n1);
        Node tempNode = this.lst.get(index);
        tempList.remove(index);
        Node newNode = tempNode.add(m);

        tempList.add(index, newNode);

        checkRep();
        return new GraphADT(tempList);
    }

    /**
     * Removes edge (e, n2) from n1, if null node(s)/edge name, graph doesn't contain node(s), or edge doesn't exist throw exception
     *
     * @param n1 node to remove edge from
     * @param n2 edge to node to remove
     * @param e name of edge to remove
     * @throws IllegalArgumentException if null node(s)/edge name, graph doesn't contain node(s), or edge doesn't exist
     * @return a GraphADT g such that n1 doesn't contain an edge (e, n2)
     */
    public GraphADT removeEdge(Node n1, Node n2, Object e) {
        checkRep();

        Map<Object, Node> m = new HashMap<Object, Node>();
        m.put(e, n2);

        if (n1 == null || n2 == null || e == null || !this.contains(n1) || !this.contains(n2) || !n1.containsO(e) || !n1.containsN(n2)) {
            throw new IllegalArgumentException("bad node(s), bad edge name, graph doesn't contain node(s), or edge doesn't exist");
        }

        List<Node> tempList = this.lst;
        GraphADT g = new GraphADT(tempList);

        int index = g.indexOf(n1);
        Node tempNode = g.get(index);
        tempList.remove(index);
        Node newNode = tempNode.remove(m);

        tempList.add(index, newNode);

        checkRep();
        return new GraphADT(tempList);
    }

    /**
     * Returns true if graphs contain same nodes in same order, if null graph throw exception
     *
     * @param g graph to compare
     * @throws IllegalArgumentException if null grpah
     * @return true iff graphs contain same node in same order
     */
    public boolean equals(GraphADT g) {
        if (g == null) {
            throw new IllegalArgumentException("bad graph");
        }
        else if (this.size != g.size) {
            return false;
        }
        else if (this.size == 0 && g.size == 0) {
            return true;
        }
        for (int i = 0; i < this.size; i++) {
            Node n1 = this.get(i);
            Node n2 = g.get(i);
            if (!n1.equals(n2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return list of nodes in graph
     *
     * @return list of nodes
     */
    public List<Node> getNodes() {
        return this.lst;
    }

    /**
     * Returns copy of graph
     *
     * @return graph of nodes
     */
    public GraphADT copy() {
        return new GraphADT(this.lst);
    }

    /**
     * Throws exception if rep inv is violated
     */
    public void checkRep() {
        assert (this.lst != null) : "invalid graph";

        for (int i = 0; i < this.size-1; i++) {
            Node n1 = this.get(i);
            for (int j = 1; j < this.size; j++) {
                Node n2 = this.get(j);
                if (i != j) {
                    assert (!n1.equals(n2)) : "contains duplicate node";
                }
            }
        }
    }


    /**
     * Node represents a single node that can be inserted into a Graph
     *
     * Spec fields:
     * @spec.specfield data : Object // value to be held by node
     * @spec.specfield edges : List // list of single-set maps (edges) extending from node
     */
    public static class Node {
        Object data;
        List<Map<Object, Node>> edges;

        /**
         * Constructs new Node holding data and new list of edges, if null data throw exception
         *
         * @param data value to be held by node
         * @throws IllegalArgumentException if null data
         * @spec.requires data not null
         */
        public Node(Object data) {
            if (data == null) {
                throw new IllegalArgumentException("no data found");
            }

            this.data = data;
            this.edges = new ArrayList<Map<Object, Node>>();
        }

        /**
         * Constructs new Node of d with edges in l, if null data or list throw exception
         *
         * @param d value to be held by node
         * @param l list of outgoing edges from node
         * @throws IllegalArgumentException if null data or edges
         * @spec.requires d and l not null
         */
        public Node(Object d, List<Map<Object, Node>> l) {
            if (data == null || l == null) {
                throw new IllegalArgumentException("no data or bad edges");
            }

            this.data = d;
            this.edges = new ArrayList<Map<Object, Node>>();
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i) != null) {
                    this.edges.add(l.get(i));
                }
            }
        }

        /**
         * Constructs new node of data with edge m, if null data or map throw exception
         *
         * @param d value to be held by node
         * @param m outgoing edges from m
         * @throws IllegalArgumentException if null data or edge
         * @spec.requires d and m not null
         */
        public Node(Object d, Map<Object, Node> m) {
            if (d == null || m == null) {
                throw new IllegalArgumentException("bad node or edge");
            }

            this.data = d;
            this.edges = new ArrayList<Map<Object, Node>>();
            boolean notNull = false;
            for (Object k : m.keySet()) {
                if (k != null && m.get(k) != null) {
                    notNull = true;
                }
            }
            if (notNull) {
                this.edges.add(m);
            }
        }

        /**
         * Returns true if edges contain Object,Node pair, if null map throw exception
         *
         * @param m map holding a single requested edge
         * @throws IllegalArgumentException if null map
         * @return true iff edges contain Map of Object,Node in m
         */
        public boolean contains(Map<Object, Node> m) {
            if (m == null) {
                throw new IllegalArgumentException("bad edge");
            }

            Object d = new Object();
            for (Object k : m.keySet()) {
                d = k;
            }
            return this.containsO(d) && this.containsN(m.get(d));
        }

        /**
         * Returns true if edges contain Object e, if null object throw exception
         *
         * @param e requested edge name
         * @throws IllegalArgumentException if null object
         * @return true iff edges contain object with key e
         */
        public boolean containsO(Object e) {
            if (e == null) {
                throw new IllegalArgumentException("bad edge name");
            }

            for (int i = 0; i < this.edges.size(); i++) {
                Map<Object, Node> m = this.edges.get(i);
                for (Object k : m.keySet()) {
                    if (k == e) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Returns true if edges contain edge to Node n, if null node throw exception
         *
         * @param n requested node
         * @throws IllegalArgumentException if null node
         * @return true iff edges contain outgoing edge to n
         */
        public boolean containsN(Node n) {
            if (n == null) {
                throw new IllegalArgumentException("bad node");
            }

            for (int i = 0; i < this.edges.size(); i++) {
                Map<Object, Node> m = this.edges.get(i);
                for (Object k : m.keySet()) {
                    if (m.get(k).equals(n)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Adds new edge pair to edges, if null map or edges already contain throw exception
         *
         * @param m edge to add
         * @throws IllegalArgumentException if null map or edges contain
         * @return a Node n such that the edges contain m
         */
        public Node add(Map<Object, Node> m) {
            if (this.contains(m)) {
                throw new IllegalArgumentException("duplicate edge");
            }
            else if (m == null) {
                throw new IllegalArgumentException("bad edge");
            }

            List<Map<Object, Node>> temp = this.edges;
            temp.add(m);
            return new Node(this.data, temp);
        }

        /**
         * Removes edge m from edges, if null node or doesn't contain throw exception
         *
         * @param m edge to be removed
         * @throws IllegalArgumentException if null edge or edge does not exist
         * @return a Node n such that the edges don't contain m
         */
        public Node remove(Map<Object, Node> m) {
            if (m == null) {
                throw new IllegalArgumentException("bad edge");
            }
            else if (!this.contains(m)) {
                throw new IllegalArgumentException("does not contain edge");
            }

            List<Map<Object, Node>> temp = this.edges;
            for (int i = temp.size()-1; i >= 0; i--) {
                Map<Object, Node> n = temp.get(i);
                if (n.equals(m)) {
                    temp.remove(i);
                }
            }
            return new Node(this.data, temp);
        }

        /**
         * Removes edge with name e, if null name or does not exist throw exception
         *
         * @param e name of edge to remove
         * @throws IllegalArgumentException if null node or edge name doesn't exist
         * @return a Node n such that there are no edges with name e
         */
        public Node removeO(Object e) {
            if (e == null) {
                throw new IllegalArgumentException("bad edge name");
            }
            else if (!this.containsO(e)) {
                throw new IllegalArgumentException("edge name doesn't exist");
            }

            List<Map<Object, Node>> temp = this.edges;
            for (int i = temp.size()-1; i >= 0; i--) {
                Map<Object, Node> n = temp.get(i);
                for (Object o : n.keySet()) {
                    if (o == e) {
                        temp.remove(i);
                    }
                }
            }

            return new Node(this.data, temp);
        }

        /**
         * Removes edge to n, if null node or does not exist throw exception
         *
         * @param n node to remove edges to
         * @throws IllegalArgumentException if null node or edges don't contain
         * @return a Node k such that there are no outgoing edges to n
         */
        public Node removeN(Node n) {
            if (n == null) {
                throw new IllegalArgumentException("bad node");
            }
            else if (!this.containsN(n)) {
                throw new IllegalArgumentException("edge to node doesn't exist");
            }

            List<Map<Object, Node>> temp = this.edges;
            for (int i = temp.size()-1; i >= 0; i--) {
                Map<Object, Node> k = temp.get(i);
                for (Object o : k.keySet()) {
                    if (k.get(o).equals(n)) {
                        temp.remove(i);
                    }
                }
            }
            return new Node(this.data, temp);
        }

        /**
         * Returns true if data in nodes is same, if null node throw exception
         *
         * @param n node to compare
         * @throws IllegalArgumentException if null node
         * @return true iff data of nodes is equal
         */
        public boolean equals(Node n) {
            if (n == null) {
                throw new IllegalArgumentException("bad node");
            }

            return this.data == n.data;
        }

        /**
         * Returns list of edges of node
         *
         * @return list of maps
         */
        public List<Map<Object, Node>> getEdges() {
            return this.edges;
        }

        /**
         * Returns value held by node
         *
         * @return Object value
         */
        public Object getData() {
            return this.data;
        }
    }
}
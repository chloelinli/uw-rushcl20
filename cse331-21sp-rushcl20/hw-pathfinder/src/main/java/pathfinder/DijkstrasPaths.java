package pathfinder;

import graph.GraphADT;
import graph.GraphADT.*;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Builds graph from file and finds path between nodes using Dijkstra's algorithm.
 */
public class DijkstrasPaths {

    /**
     * Builds graph using nodes/edges from test driver
     *
     * @param nodes map of list of maps from test driver
     * @return a GraphADT g such that the nodes and edges correspond to the map
     */
    public static GraphADT buildGraph(Map<Object, List<Map<Double, Object>>> nodes) {
        GraphADT g = new GraphADT();

        // sort parent nodes, add to graph
        List<String> sortNodes = new ArrayList<String>();
        for (Object k : nodes.keySet()) {
            sortNodes.add((String) k);
        }
        Collections.sort(sortNodes);
        for (String s : sortNodes) {
            Node n = new Node(s);
            if (!g.contains(n) && s != null) {
                g = g.add(n);
            }
        }

        // add sorted edges for each parent node
        for (int i = 0; i < g.size; i++) {
            Node n = g.get(i);
            Object parent = n.getData();
            List<Double> sortEdges = new ArrayList<Double>();
            List<Map<Double, Object>> edges = nodes.get(parent);

            // get edges
            for (int j = 0; j < edges.size(); j++) {
                Map<Double, Object> currEdge = edges.get(j);
                for (Double d : currEdge.keySet()) {
                    if (d != null && currEdge.get(d) != null) {
                        sortEdges.add(d);
                    }
                }
            }
            Collections.sort(sortEdges);

            // go through sorted edges, get from actual edges and insert into graph
            for (Double d : sortEdges) {
                for (int j = 0; j < edges.size(); j++) {
                    Map<Double, Object> currEdge = edges.get(j);
                    Map<Object, Node> tempMap = new HashMap<Object, Node>();
                    if (currEdge.containsKey(d) && d != null && !n.contains(tempMap)) {
                        g.addEdge(n, new Node(currEdge.get(d)), d);
                    }
                }
            }
        }
        return g;
    }

    /**
     * Finds path using graph built in buildGraph
     *
     * @param g GraphADT to iterate through
     * @param start start of path
     * @param end end of path
     * @return a list of maps such that we either have no path or we can find the minimum path by going through the list
     */
    public static List<Map<Double, Node>> findPath(GraphADT g, String start, String end) {        // initialize priority queue
        PriorityQueue<Entry> active = new PriorityQueue<Entry>();
        // add path from start to self with cost of 0.0
        Map<Double, Node> m = new HashMap<Double, Node>();
        m.put(0.0, new Node(start));
        List<Map<Double, Node>> l = new ArrayList<Map<Double, Node>>();
        l.add(m);
        Entry e = new Entry(l);
        active.add(e);

        // finished nodes
        List<Node> finished = new ArrayList<Node>();

        // while active not empty
        while (!active.isEmpty()) {
            // get minPath and last node in path
            List<Map<Double, Node>> minPath = active.remove().getKey();
            Node minDest = new Node(0);
            Map<Double, Node> temp = minPath.get(minPath.size()-1);
            for (Double d : temp.keySet()) {
                minDest = temp.get(d);
            }

            // if minDest = dest return minPath
            if (minDest.equals(new Node(end))) {
                return minPath;
            }

            // if minDest in finished, stop current interation
            if (finished.contains(new Node(minDest.getData()))) {
                continue;
            }

            // else for each edge from minDest, find more paths
            int index = g.indexOf(minDest);
            minDest = g.get(index);

            // list of edges
            List<Map<Object, Node>> minDestEdges = minDest.getEdges();
            for (int i = 0; i < minDestEdges.size(); i++) {
                // current edge
                Map<Object, Node> tempM = minDestEdges.get(i);
                for (Object o : tempM.keySet()) {
                    Node tempN = tempM.get(o);
                    // if node not in finished, add edge to path, add path to active
                    if (!finished.contains(new Node(tempN.getData()))) {
                        // save copy of minPath
                        List<Map<Double, Node>> minPathCopy = new ArrayList<>(minPath);
                        Map<Double, Node> tempM2 = new HashMap<Double, Node>();
                        tempM2.put((Double) o, tempN);
                        minPathCopy.add(tempM2);
                        // new entry
                        Entry newE = new Entry(minPathCopy);
                        active.add(newE);
                    }
                }
            }
            // add minDest to finished
            finished.add(new Node(minDest.getData()));
        }
        String str = "path from " + start + " to " + end + ":";
        str += "\nno path found";
        List<Map<Double, Node>> t = new ArrayList<Map<Double, Node>>();
        Node tn = new Node(str);
        Map<Double, Node> tm = new HashMap<Double, Node>();
        tm.put(0.0, tn);
        t.add(tm);
        return t;
    }
}

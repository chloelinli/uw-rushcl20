package marvel;

import java.io.*;
import java.util.*;

import graph.GraphADT;
import graph.GraphADT.*;
import marvel.MarvelParser;

/**
 * Builds graph from file and finds path between specified nodes
 */
public class MarvelPaths {

    /**
     * Main method
     * @param args main args
     * @throws IOException if filename doesn't exist
     */
    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        System.out.print("enter 'q' to quit, else play: ");
        String play = console.nextLine();
        while (!play.substring(0, 1).equals('q')) {
            System.out.print("enter file name: ");
            String filename = console.nextLine();
            GraphADT g = buildGraph(filename);

            System.out.print("enter first character name: ");
            String char1 = console.nextLine();
            System.out.print("enter second character name: ");
            String char2 = console.nextLine();

            System.out.println(mainFindPath(g, char1, char2));
            System.out.print("would you like to keep playing? 'q' to quit, else play: ");
        }
        System.out.println("i hope that worked T_T");
    }

    /**
     * Takes in file, passing it into MarvelParser, receiving new List of Maps of (book/edge, character/node).
     * Creates and returns GraphADT of nodes and edges.
     *
     * @param fileName csv file to be read in format (charName,bookName)
     * @return a Map m such that it contains all nodes and their edges (book, character)
     * @throws IOException if error occurs while reading file
     * @spec.requires filename is valid file in resources/data folder
     */
    public static GraphADT buildGraph(String fileName) throws IOException {
        GraphADT g = new GraphADT();
        MarvelParser mp = new MarvelParser();
        List<Map<String, String>> bc = mp.parseData(fileName);
        List<String> parentNodes = new ArrayList<String>();

        for (int i = 0; i < bc.size(); i++) {
            Map<String, String> temp = bc.get(i);
            for (String e : temp.keySet()) {
                parentNodes.add(temp.get(e));
            }
        }
        Collections.sort(parentNodes);
        for (int i = 0; i < parentNodes.size(); i++) {
            g.add(new Node(parentNodes.get(i)));
        }

        for (int i = 0; i < bc.size(); i++) {
            Map<String, String> temp = bc.get(i);
            for (int j = 0; j < bc.size(); j++) {
                Map<String, String> temp2 = bc.get(j);
                if (i != j) {
                    for (String e1 : temp.keySet()) {
                        for (String e2 : temp2.keySet()) {
                            Node n1 = new Node(temp.get(e1));
                            Node n2 = new Node(temp2.get(e2));
                            Map<Object, Node> newEdge = new HashMap<Object, Node>();
                            newEdge.put((Object) e2, n2);
                            if (e1.equals(e2) && !n1.contains(newEdge) && !temp.equals(temp2)) {
                                n1.add(newEdge);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < bc.size(); i++) {
            Map<String, String> m1 = bc.get(i);
            for (String e1 : m1.keySet()) {
                Node n1 = g.get(g.indexOf(new Node(m1.get(e1))));
                for (int j = 0; j < bc.size(); j++) {
                    Map<String, String> m2 = bc.get(j);
                    for (String e2 : m2.keySet()) {
                        Node n2 = g.get(g.indexOf(new Node(m2.get(e2))));
                        Map<Object, Node> temp = new HashMap<Object, Node>();
                        temp.put(e2, n2);
                        if (!n1.equals(n2) && !n1.contains(temp)) {
                            g = g.addEdge(n1, n2, temp);
                        }
                    }
                }
            }
        }
        return g;
    }

    /**
     * Takes nodes and parses through edges and neighbors to find path from start to end node.
     *
     * @param nodes map of nodes in graph
     * @param start requested node at beginning of path
     * @param end requested node at end of path
     * @return a String such that  it indicates the path between characters via books.
     */
    public static String findPath(Map<String, List<Map<String, String>>> nodes, String start, String end) {
        // queue to parse each node in path, visited list to keep track of visited nodes
        Queue<String> queue = new ArrayDeque<String>();
        List<String> visited = new ArrayList<String>();

        // string path to hold path
        String path = "path from " + start + " to " + end + ":";

        // add start
        queue.add(start);
        visited.add(start);

        // search until empty queue - empty means path not found
        while (!queue.isEmpty()) {
            // get next node
            String curr = queue.remove();

            // if end return path
            if (curr.equals(end)) {
                return path;
            }

            // else go through edges; if non-visited edge, add to path, visited, and queue
            List<Map<String, String>> edges = nodes.get(curr);
            for (int i = 0; i < edges.size(); i++) {
                // edge
                Map<String, String> neighbor = edges.get(i);
                for (String e : neighbor.keySet()) {
                    // set
                    if (!visited.contains(neighbor.get(e))) {
                        path += "\n" + curr + " to " + neighbor.get(e) + " via " + e;
                        visited.add(neighbor.get(e));
                        queue.add(neighbor.get(e));
                    }
                }
            }
        }

        return "path from " + start + " to " + end + ":\nno path found";
    }

    /**
     * Helper method for main method
     *
     * @param g graph of nodes
     * @param char1 start character
     * @param char2 end character
     * @return a String of the path from char1 to char2
     */
    public static String mainFindPath(GraphADT g, String char1, String char2) {
        Map<String, List<Map<String, String>>> nodes = new HashMap<String, List<Map<String, String>>>();

        for (int i = 0; i < g.size; i++) {
            Node n = g.get(i);
            List<Map<String, String>> edges = new ArrayList<Map<String, String>>();
            List<Map<Object, Node>> nEdges = n.getEdges();

            // insert edges
            for (int j = 0; j < nEdges.size(); j++) {
                Map<Object, Node> currEdge = nEdges.get(j);
                for (Object e : currEdge.keySet()) {
                    Map<String, String> newEdge = new HashMap<String, String>();
                    newEdge.put((String) e, (String) currEdge.get(e).getData());
                    edges.add(newEdge);
                }
            }

            // insert node
            nodes.put((String) n.getData(), edges);
        }

        if (!nodes.containsKey(char1) || !nodes.containsKey(char2)) {
            String str = "";
            if (!nodes.containsKey(char1)) {
               str += "unknown: " + char1 + "\n";
            }
            if (!nodes.containsKey(char2)) {
                str += "unknown: " + char2 + "\n";
            }
            return str;
        }

        else {
            MarvelPaths mp = new MarvelPaths();
            return mp.findPath(nodes, char1, char2);
        }
    }
}
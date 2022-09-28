/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.scriptTestRunner;

import java.io.*;
import java.util.*;

import graph.GraphADT;
import graph.GraphADT.*;
import marvel.MarvelParser;
import marvel.MarvelParser.*;
import marvel.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Map<String, List<Map<String, String>>>> graphs = new HashMap<String, Map<String, List<Map<String, String>>>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new GraphTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "LoadGraph":
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new HashMap<String, List<Map<String, String>>>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Map<String, List<Map<String, String>>> nodes = graphs.get(graphName);
        nodes.put(nodeName, new ArrayList<Map<String, String>>());
        graphs.put(graphName, nodes);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        Map<String, List<Map<String, String>>> nodes = graphs.get(graphName);
        List<Map<String, String>> edges = nodes.get(parentName);

        if (edgeLabel != null && !edgeLabel.equals("null")) {
            boolean contains = false;
            Map<String, String> m = new HashMap<String, String>();
            m.put(edgeLabel, childName);
            for (int i = 0; i < edges.size(); i++) {
                Map<String, String> n = edges.get(i);
                if (n.equals(m)) {
                    contains = true;
                }
            }

            if (!contains) {
                edges.add(m);
            }
            nodes.put(parentName, edges);
            graphs.put(graphName, nodes);
            output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
        }
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        String str = graphName + " contains:";
        Map<String, List<Map<String, String>>> nodes = graphs.get(graphName);
        List<String> sortedNodes = new ArrayList<String>();
        for (String n : nodes.keySet()) {
            sortedNodes.add(n);
        }
        Collections.sort(sortedNodes);
        for (String n : sortedNodes) {
            str += " " + n;
        }

        output.println(str);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Map<String, List<Map<String, String>>> nodes = graphs.get(graphName);
        List<Map<String, String>> edges = nodes.get(parentName);
        List<String> sortChild = new ArrayList<String>();
        for (int i = 0; i < edges.size(); i++) {
            Map<String, String> m = edges.get(i);
            for (String e : m.keySet()) {
                sortChild.add(m.get(e) + "(" + e+ ")");
            }
        }
        Collections.sort(sortChild);
        String str = "";
        str += sortChild.get(0);
        for (int i = 1; i < sortChild.size(); i++) {
            str += "\n" + sortChild.get(i);
        }
        output.println(str);
    }

    public void loadGraph(List<String> arguments) throws IOException {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String fileName = arguments.get(1);
        loadGraph(graphName, fileName);
    }

    public void loadGraph(String graphName, String fileName) throws IOException {
        MarvelPaths mp = new MarvelPaths();
        GraphADT g = mp.buildGraph(fileName);

        Map<String, List<Map<String, String>>> nodes = graphs.get(graphName);
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

        graphs.put(graphName, nodes);
        output.println("loaded graph " + graphName);
    }

    public void findPath(List<String> arguments) throws IOException {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String end = arguments.get(2);
        findPath(graphName, start, end);
    }

    public void findPath(String graphName, String start, String end) throws IOException {
        Map<String, List<Map<String, String>>> nodes = graphs.get(graphName);

        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            if (!nodes.containsKey(start)) {
                output.println("unknown: " + start);
            }
            if (!nodes.containsKey(end)) {
                output.println("unknown: " + end);
            }
        }

        else {
            MarvelPaths mp = new MarvelPaths();
            output.println(mp.findPath(nodes, start, end));
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}

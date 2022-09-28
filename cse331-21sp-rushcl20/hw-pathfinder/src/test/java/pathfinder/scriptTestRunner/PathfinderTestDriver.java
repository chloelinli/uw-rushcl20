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

package pathfinder.scriptTestRunner;

import graph.GraphADT;
import graph.GraphADT.*;
import pathfinder.DijkstrasPaths;
import pathfinder.DijkstrasPaths.*;
import pathfinder.Entry;

import java.io.Reader;
import java.io.Writer;
import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    /**
     * String/Object -> Graph: maps names of graphs to actual Graph
     */

    private final Map<Object, Map<Object, List<Map<Double, Object>>>> graphs = new HashMap<Object, Map<Object, List<Map<Double, Object>>>>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);

    }

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
        graphs.put(graphName, new HashMap<Object, List<Map<Double, Object>>>());
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
        Map<Object, List<Map<Double, Object>>> nodes = graphs.get(graphName);
        nodes.put(nodeName, new ArrayList<Map<Double, Object>>());
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
        double edgeLabel = Double.parseDouble(arguments.get(3));

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         double edgeLabel) {
        Map<Object, List<Map<Double, Object>>> nodes = graphs.get(graphName);
        List<Map<Double, Object>> edges = nodes.get(parentName);

        boolean contains = false;
        Map<Double, Object> m = new HashMap<Double, Object>();
        m.put(edgeLabel, childName);
        for (int i = 0; i < edges.size(); i++) {
            Map<Double, Object> n = edges.get(i);
            if (n.equals(m)) {
                contains = true;
            }
        }

        if (!contains) {
            edges.add(m);
        }
        nodes.put(parentName, edges);
        graphs.put(graphName, nodes);
        output.println("added edge " + String.format("%.3f", edgeLabel) + " from " + parentName + " to " + childName + " in " + graphName);
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
        Map<Object, List<Map<Double, Object>>> nodes = graphs.get(graphName);
        List<String> sortedNodes = new ArrayList<String>();
        for (Object n : nodes.keySet()) {
            sortedNodes.add((String) n);
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
        Map<Object, List<Map<Double, Object>>> nodes = graphs.get(graphName);
        List<Map<Double, Object>> edges = nodes.get(parentName);
        List<String> sortChild = new ArrayList<String>();
        for (int i = 0; i < edges.size(); i++) {
            Map<Double, Object> m = edges.get(i);
            for (Double e : m.keySet()) {
                sortChild.add(m.get(e) + "(" + String.format("%.3f", e) + ")");
            }
        }
        Collections.sort(sortChild);
        String str = "the children of " + parentName + " in " + graphName + " are:";
        for (int i = 0; i < sortChild.size(); i++) {
            str += " " + sortChild.get(i);
        }
        output.println(str);
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
        DijkstrasPaths dp = new DijkstrasPaths();
        Map<Object, List<Map<Double, Object>>> nodes = graphs.get(graphName);
        if (!nodes.containsKey(start) || !nodes.containsKey(end)) {
            if (!nodes.containsKey(start)) {
                output.println("unknown: " + start);
            }
            if (!nodes.containsKey(end)) {
                output.println("unknown: " + end);
            }
        }

        else if (start.equals(end)) {
            output.println("path from " + start + " to " + end + ":");
            output.println("total cost: " + String.format("%.3f", 0.0));
        }

        else {
            GraphADT g = dp.buildGraph(nodes);
            List<Map<Double, Node>> lst = dp.findPath(g, start, end);
            if (lst.size() == 1) {
                Map<Double, Node> m = lst.get(0);
                for (Double d : m.keySet()) {
                    output.println("path from " + start + " to " + end + ":");
                    output.println("no path found");
                }
            }
            Entry e = new Entry(lst);
            // iterate through nodes and get costs
            output.println("path from " + start + " to " + end + ":");
            String curr = start;
            for (int i = 0; i < lst.size(); i++) {
                Map<Double, Node> m = lst.get(i);
                for (Double d : m.keySet()) {
                    output.println(curr + " to " + m.get(d).getData() + " with weight " + String.format("%.3f", d));
                    curr = (String) m.get(d).getData();
                }
            }
            output.println("total cost: " + String.format("%.3f", e.getValue()));
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

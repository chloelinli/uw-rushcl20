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

package campuspaths;

import campuspaths.utils.CORSFilter;
import static spark.Spark.*;
import graph.GraphADT;
import graph.GraphADT.*;
import org.eclipse.jetty.util.IO;
import pathfinder.CampusMap;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;
import java.io.*;
import java.util.*;

public class SparkServer {

    public static void main(String[] args) throws IOException {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // i haven't had time to go to lecture or section or fully focus on assignments and i really regret taking
        // more than one programming class
        CampusPathsParser cpp = new CampusPathsParser();
        List<CampusBuilding> cb = cpp.parseCampusBuildings("campus_buildings.csv");
        List<CampusPath> cp = cpp.parseCampusPaths("campus_buildings.csv");
        GraphADT g = new GraphADT();

        for (CampusBuilding c : cb) {
            Node n = new Node(c);
            g.add(n);
        }

        get("/graph", (req, res) -> {
            res.type("text/plain");
            return g.toString();
        });
    }

}

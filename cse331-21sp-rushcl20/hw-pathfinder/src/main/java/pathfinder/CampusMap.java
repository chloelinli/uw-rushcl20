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

package pathfinder;

import pathfinder.datastructures.Path;

import java.util.Map;

import graph.GraphADT;
import graph.GraphADT.*;
import pathfinder.DijkstrasPaths;
import pathfinder.Entry;
import pathfinder.datastructures.Point;

/**
 * This class implements and overrides the methods in ModelAPI by taking
 * an existing short name of a building and using it to find a path between
 * buildings.
 */
public class CampusMap implements ModelAPI {

    /**
     * Map of short and long names of buildings
     */
    public Map<String, String> buildings;

    @Override
    public boolean shortNameExists(String shortName) {
        Map<String, String> buildings = this.buildingNames();
        return buildings.containsKey(shortName) || shortName != null;
    }

    @Override
    public String longNameForShort(String shortName) {
        boolean exists = this.shortNameExists(shortName);
        if (!exists) {
            throw new IllegalArgumentException("short name does not exist");
        }
        return this.buildingNames().get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        return this.buildings;
    }

    @Override
    public Path findShortestPath(String startShortName, String endShortName) {
        if (startShortName == null || endShortName == null || !shortNameExists(startShortName) || !shortNameExists(endShortName)) {
            throw new IllegalArgumentException("null name(s) or short name(s) doesn't exists");
        }
    }

}

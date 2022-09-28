package pathfinder;

import graph.GraphADT;
import graph.GraphADT.*;

import java.util.List;
import java.util.Map;

/**
 * Entry class to compare list of maps for priority queue
 * Takes value of list/maps and compares
 */
public class Entry implements Comparable<Entry> {
    private List<Map<Double, Node>> key;
    private double value;

    /**
     * Constructs Entry using list of maps
     * @param key list to save as key
     */
    public Entry(List<Map<Double, Node>> key) {
        this.key = key;
        this.value = this.getValue();
    }

    /**
     * Returns path of nodes
     * @return list of maps
     */
    public List<Map<Double, Node>> getKey() {
        return this.key;
    }

    /**
     * Computes value of list of maps
     * @return double d which is sum of Double keys in maps
     */
    public double getValue() {
        double v = 0.0;
        for (int i = 0; i < this.key.size(); i++) {
            Map<Double, Node> m = this.key.get(i);
            for (Double d : m.keySet()) {
                v += d;
            }
        }
        return v;
    }

    /**
     * Override method for comparing values of Entry objects
     * @param other Entry object to compare
     * @return int result of comparison (+ for lower priority, - for higher, 0 for same)
     */
    @Override
    public int compareTo(Entry other) {
        return (int) (this.getValue() - other.getValue());
    }
}

package graph.junitTests;

import graph.GraphADT;
import graph.GraphADT.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.*;

/**
 * This class contains a few small tests for the implementation of the GraphADT class. This
 * class does not contain multiple tests to use on large graphs, to maintain the
 */
public final class GraphADTSmallTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    // a few base nodes
    private Node one = new Node(1);
    private Node two = new Node(2);
    private Node three = new Node(3);

    // small GraphADTs
    private GraphADT g1 = new GraphADT(one);
    private GraphADT g2 = new GraphADT(new ArrayList<Node>() {
        {
            add(one);
            add(two);
            add(three);
        }
    });

    @Test
    public void testEquals() {
        // test equal to self
        assertEquals(one, one);
        assertEquals(two, two);
        assertEquals(three, three);
        assertEquals(g1, g1);
        assertEquals(g2, g2);
    }

    @Test
    public void testNotEqual() {
        // test graphs not equal
        assertTrue(!g1.equals(g2));
        assertTrue(!g2.equals(g1));

        // test nodes not equal
        assertNotEquals(one, two);
        assertNotEquals(one, three);
        assertNotEquals(two, three);
    }

    @Test
    public void testContains() {
        assertTrue(g1.contains(one));
        assertTrue(g2.contains(one));
        assertTrue(g2.contains(two));
        assertTrue(g2.contains(three));
    }

    @Test
    public void testAddEquals() {
        assertTrue(g2.equals(g1.add(two).add(three)));
    }

    @Test
    public void testRemoveEquals() {
        assertTrue(g1.copy().remove().equals(g2.copy().remove().remove().remove()));
        assertTrue(g1.copy().remove().equals(g2.copy().remove(0).remove(0).remove(0)));
        assertTrue(g1.copy().remove(0).equals(g2.copy().remove(0).remove().remove(0)));
        assertTrue(g1.copy().equals(g2.copy().remove(2).remove(1)));
        assertTrue(g1.copy().remove().equals(g2.copy().remove(1).remove(1).remove(0)));
        assertTrue(g1.copy().remove().equals(g2.copy().remove(2).remove(1).remove(0)));
        assertTrue(g1.copy().equals(g2.copy().remove(2).remove(1)));
        assertTrue(g1.copy().equals(g2.copy().remove(two).remove(three)));
        assertTrue(g1.copy().remove(one).equals(g2.copy().remove(one).remove(two).remove(three)));
    }

    @Test
    public void testSize() {
        assertTrue(g1.size == 1);
        assertTrue(g2.size == 3);
    }

    @Test
    public void testGet() {
        assertTrue(g1.get(0).equals(one));
        assertTrue(g2.get(0).equals(one));
        assertTrue(g2.get(1).equals(two));
        assertTrue(g2.get(2).equals(three));
    }

    @Test
    public void testIndex() {
        assertTrue(g1.indexOf(one) == 0);
        assertTrue(g2.indexOf(one) == 0);
        assertTrue(g2.indexOf(two) == 1);
        assertTrue(g2.indexOf(three) == 2);
    }
}
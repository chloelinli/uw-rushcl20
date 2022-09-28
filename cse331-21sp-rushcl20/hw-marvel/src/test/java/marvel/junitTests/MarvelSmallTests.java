package marvel.junitTests;

import graph.GraphADT;
import graph.GraphADT.*;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.*;

/**
 * This class tests main class of MarvelPaths
 */
public class MarvelSmallTests {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Test
    public void testGraph() {
        MarvelPaths mp = new MarvelPaths();
    }
}

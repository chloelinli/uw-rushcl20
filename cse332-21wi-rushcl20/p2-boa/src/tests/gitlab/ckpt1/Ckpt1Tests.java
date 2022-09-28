package tests.gitlab.ckpt1;

import datastructures.worklists.MinFourHeap;
import datastructures.worklists.MinFourHeapComparable;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        MoveToFrontListTests.class,
        CircularArrayComparatorTests.class,
        MinFourHeapComparableTests.class
})

public class Ckpt1Tests {

}
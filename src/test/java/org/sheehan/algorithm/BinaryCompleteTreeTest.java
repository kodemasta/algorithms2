package org.sheehan.algorithm;

import org.junit.Test;
import org.sheehan.algorithm.data_structures.BinaryCompleteTree;

public class BinaryCompleteTreeTest {

    @Test
    public void testPrint() throws Exception {
        BinaryCompleteTree<Integer> tree = new BinaryCompleteTree<>(10);

        for (int i = 0; i < 6; ++i)
            tree.add(i);

        tree.print(0);
    }


}
package ru.algo.ds.tree.btree;

import org.junit.jupiter.api.BeforeEach;

public abstract class BTreeBaseTest {
    protected final AbstractBTree<Integer> tree1 = createTree();

    protected final AbstractBTree<Integer> tree2 = createTree();

    protected final AbstractBTree<Integer> tree3 = new BSTree();

    protected final AbstractBTree<Integer> oneNodeTree = createTree();

    protected final AbstractBTree<Integer> disbalancedTree = createTree();

    protected final AbstractBTree<Integer> binaryKeyTree = createTree();

    @BeforeEach
    public void before(){
        tree1.add(25);
        tree1.add(48);
        tree1.add(32);
        tree1.add(1);
        tree1.add(12);
        tree1.add(6);
        tree1.add(7);
        tree1.add(13);

        tree2.add(4);
        tree2.add(2);
        tree2.add(7);
        tree2.add(1);
        tree2.add(3);
        tree2.add(6);
        tree2.add(9);

        tree3.add(7);
        tree3.add(4);
        tree3.add(9);
        tree3.add(2);
        tree3.add(6);
        tree3.add(10);
        tree3.add(5);
        tree3.add(8);
        tree3.add(1);
        tree3.add(3);
        tree3.add(11);

        disbalancedTree.add(1);
        disbalancedTree.add(2);
        disbalancedTree.add(3);

        binaryKeyTree.add(1);
        binaryKeyTree.add(0);
        binaryKeyTree.add(1);
        binaryKeyTree.add(0);
        binaryKeyTree.add(1);
        binaryKeyTree.add(0);
        binaryKeyTree.add(1);

        oneNodeTree.add(1);
    }

    protected abstract AbstractBTree<Integer> createTree();
}

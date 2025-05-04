package ru.algo.ds.tree.btree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import ru.algo.ds.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static ru.algo.ds.tree.btree.BTreeUtils.inOrderTraversal;

@Testable
@Tag("tree")
public class BSTreeTest extends BTreeBaseTest {

    @Test
    public void addTest(){
        final List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

        assertEquals(expected.size(), BTreeUtils.countNodes(tree3.getRoot()));

        List<Integer> actual = inOrderTraversal(tree3.getRoot())
                .orElse(new ArrayList<>())
                .stream()
                .map(BTreeNode::getKey)
                .toList();

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void searchTest(){
        Optional<BTreeNode<Integer>> actualOpt = tree3.search(1);
        assertTrue(actualOpt.isPresent());
        assertEquals(1, actualOpt.get().getKey());

        actualOpt = tree3.search(10);
        assertTrue(actualOpt.isPresent());
        assertEquals(10, actualOpt.get().getKey());

        actualOpt = tree3.search(4);
        assertTrue(actualOpt.isPresent());
        assertEquals(4, actualOpt.get().getKey());

        actualOpt = tree3.search(21);
        assertTrue(actualOpt.isEmpty());
    }

    @Test
    public void deleteTest(){
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 9, 10, 11);
        tree3.delete(8);
        List<Integer> actual = tree3.inOrderTraversal()
                .stream()
                .map(TreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), actual.toArray());

        expected = List.of(1, 2, 3, 4, 5, 6, 7, 9, 11);
        tree3.delete(10);
        actual = tree3.inOrderTraversal()
                .stream()
                .map(TreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), actual.toArray());

        expected = List.of(1, 2, 3, 4, 6, 7, 9, 11);
        tree3.delete(5);
        actual = tree3.inOrderTraversal()
                .stream()
                .map(TreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), actual.toArray());

        tree3.delete(7);
        assertNull(tree3.getRoot());
    }

    @Test
    public void testIncreasing(){
        final List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        final BTreeNode<Integer> actualRoot = BTreeUtils.increasing (tree3.getRoot());
        assertNotNull(actualRoot);

        AtomicReference<List<Integer>> list = new AtomicReference<>();
        inOrderTraversal(actualRoot)
                .ifPresent(bTreeNodes -> {
                    list.set(bTreeNodes
                            .stream()
                            .map(BTreeNode::getKey)
                            .toList());
                });
        assertArrayEquals(expected.toArray(), list.get().toArray());
    }

    @Test
    @Disabled
    public void testRangeSum(){
        int actual = ((BSTree)tree3).rangeSum(2, 9);
        assertEquals(44, actual);

        actual = ((BSTree)tree3).rangeSum(2, 6);
        assertEquals(20, actual);

        actual = ((BSTree)tree3).rangeSum(9, 10);
        assertEquals(19, actual);

        actual = ((BSTree)tree3).rangeSum(8, 8);
        assertEquals(8, actual);

        tree3.setRoot(null);
        actual = ((BSTree)tree3).rangeSum(8, 8);
        assertEquals(0, actual);
    }

    @Test
    public void findKthMinNodeTest(){
        BTreeNode<Integer> actual = ((BSTree)tree3).findKthMinNode(6);
        assertEquals(6, actual.getKey());

        actual = ((BSTree)tree3).findKthMinNode(1);
        assertEquals(1, actual.getKey());

        actual = ((BSTree)tree3).findKthMinNode(11);
        assertEquals(11, actual.getKey());
    }

    @Override
    protected AbstractBTree<Integer> createTree() {
        return TreeFactory.create(TreeType.BS_TREE);
    }
}

package ru.algo.ds.tree.btree;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import ru.algo.ds.tree.TreeNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.algo.ds.tree.btree.BTreeUtils.*;

@Testable
@Tag("tree")
public class BTreeTest extends BTreeBaseTest {

    @Test
    public void testMergeTrees(){
        final List<Integer> expected = List.of(13, 2, 50, 15, 29, 12, 39, 16);
        BTreeNode<Integer> actual = BTree.merge(tree1.getRoot(), tree2.getRoot());

        List<Integer> keys = inOrderTraversal(actual)
                .get()
                .stream()
                .map(TreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), keys.toArray());
    }

    @Test
    public void testIsSameTree(){
        BTreeNode<Integer> t1 = tree1.search(48).orElseThrow();
        BTreeNode<Integer> t2 = tree1.search(48).orElseThrow();

        boolean actual = isSameTree(t1, t2);

        assertTrue(actual);

        t1 = tree1.search(1).orElseThrow();
        t2 = tree1.search(32).orElseThrow();

        actual = isSameTree(t1, t2);

        assertFalse(actual);
    }

    @Test
    public void testIsSymmetrictree(){
        BTreeNode<Integer> t = tree1.search(48).orElseThrow();
        boolean actual = isSymmetricTree(t);
        assertFalse(actual);

        t = tree1.search(13).orElseThrow();
        actual = isSymmetricTree(t);
        assertTrue(actual);
    }

    @Test
    public void testAverageOfLevels(){
        List<Double> expected = List.of(25.0, 40.0, 6.5, 13.0);
        List<Double> actual = averageOfLevels(tree1.getRoot())
                .orElseThrow();
        assertArrayEquals(expected.toArray(), actual.toArray());

        assertThrows(RuntimeException.class, () -> averageOfLevels(null).orElseThrow());

        expected = List.of(1.0, 13.0);

        actual = averageOfLevels(tree1.search(1).orElse(null))
                .orElseThrow();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testHeight(){
        int actual = tree1.height();
        assertEquals(4, actual);

        BTree<Integer> t1 = new BTree<>();
        assertEquals(0, t1.height());

        t1.add(2);
        assertEquals(1, t1.height());
    }

    @Test
    public void testIsBalanced(){
        boolean actual = isBalanced(tree1.getRoot());
        assertTrue(actual);

        actual = isBalanced(disbalancedTree.getRoot());
        assertTrue(actual);

        actual = isBalanced(null);
        assertTrue(actual);

        disbalancedTree.setRoot(null);
        actual = isBalanced(disbalancedTree.getRoot());
        assertTrue(actual);

    }

    @Test
    public void testUnivalTree(){
        BTree<Integer> tree = new BTree<>();
        tree.setRoot(null);

        boolean actual = isUnivalTree(tree.getRoot());
        assertTrue(actual);

        actual = isUnivalTree(tree1.getRoot());
        assertFalse(actual);

        tree.add(1);
        tree.add(1);
        tree.add(1);
        tree.add(1);
        actual = isUnivalTree(tree.getRoot());
        assertEquals(true, actual);

        tree = new BTree<>();
        tree.add(1);
        tree.add(2);
        actual = isUnivalTree(tree.getRoot());
        assertFalse(actual);
    }

    @Test
    public void testDepth(){
        BTreeNode<Integer> node = tree1.search(25).orElseThrow();
        int actual = tree1.depth(node);

        assertEquals(1, actual);

        node = tree1.search(13).orElseThrow();
        actual = tree1.depth(node);

        assertEquals(4, actual);

        node = tree1.search(32).orElseThrow();
        actual = tree1.depth(node);

        assertEquals(2, actual);
    }

    @Test
    public void testMaxDepth(){
        BTreeNode<Integer> node = tree1.search(25).orElseThrow();
        int actual = tree1.maxDepth(node);

        assertEquals(4, actual);

        node = tree1.search(48).orElseThrow();
        actual = tree1.maxDepth(node);

        assertEquals(3, actual);

        node = tree1.search(32).orElseThrow();
        actual = tree1.maxDepth(node);

        assertEquals(2, actual);
    }

    @Test
    public void testLevel(){
        assertEquals(1, tree1.level(25));
        assertEquals(4, tree1.level(13));
        assertEquals(-1, tree1.level(100));
    }

    @Test
    public void testCount(){
        assertEquals(8, tree1.count());

        final BTree<Integer> t1 = new BTree<>();
        assertEquals(0, t1.count());

        t1.add(99);
        assertEquals(1, t1.count());
    }

    @Test
    public void testParent(){
        assertTrue(tree1.parent(25).isEmpty());
        assertTrue(tree1.parent(100).isEmpty());

        Optional<BTreeNode<Integer>> actualOpt = tree1.parent(1);
        assertTrue(actualOpt.isPresent());
        assertEquals(48, actualOpt.get().getKey());

        actualOpt = tree1.parent(13);
        assertTrue(actualOpt.isPresent());
        assertEquals(1, actualOpt.get().getKey());
    }

    @Test
    public void testLeaves(){
        List<BTreeNode<Integer>> expected = List.of(new BTreeNode<>(13), new BTreeNode<>(12), new BTreeNode<>(6), new BTreeNode<>(7));
        List<? extends BTreeNode<Integer>> actual = tree1.leaves();
        for (int i = 0; i < actual.size(); i++){
            assertNotNull(actual.get(i));
            assertEquals(expected.get(i).getKey(), actual.get(i).getKey());
        }

        BTree<Integer> tree1 = new BTree<>();
        actual = tree1.leaves();
        assertEquals(0, actual.size());

        tree1.add(10);
        actual = tree1.leaves();
        assertEquals(1, actual.size());
    }

    @Test
    public void testIsCousins(){
        disbalancedTree.delete(3);
        assertFalse(disbalancedTree.isCousins(1, 2));
        assertFalse(tree1.isCousins(105, 1));
        assertFalse(tree1.isCousins(25, 48));
        assertTrue(tree1.isCousins(12, 6));
    }

    @Test
    public void testIsSubTree() throws CloneNotSupportedException {
        final BTree<Integer> tree = (BTree<Integer>) tree1.clone();
        BTreeNode<Integer> root = tree.search(1).orElseThrow();

        assertNotSame(tree1, tree);
        assertTrue(BTreeUtils.isSubtree(tree1.getRoot(), root));

        tree1.add(100);
        assertFalse(BTreeUtils.isSubtree(tree1.getRoot(), root));
    }

    @Test
    public void testSumRootToLeaf(){
        assertEquals(22, BTreeUtils.sumRootToLeaf(binaryKeyTree.getRoot()));
    }

    @Test
    public void testInOrderTraversal(){
        BTreeNode<Integer> root = tree1.search(25).orElseThrow();
        List<Integer> expected = List.of(13, 1, 48, 12, 25, 6, 32, 7);
        List<Integer> actual = inOrderTraversal(root)
                .get()
                .stream()
                .map(BTreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testPreOrderTraversal(){
        BTreeNode<Integer> root = tree1.search(25).orElseThrow();
        List<Integer> expected = List.of(25, 48, 1, 13, 12, 32, 6, 7);
        List<Integer> actual = preOrderTraversal(root)
                .get()
                .stream()
                .map(TreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testPostOrderTraversal(){
        BTreeNode<Integer> root = tree1.search(25).orElseThrow();
        List<Integer> expected = List.of(13, 1, 12, 48, 6, 7, 32, 25);
        List<Integer> actual = postOrderTraversal(root)
                .get()
                .stream()
                .map(TreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testInvertTree(){
        List<Integer> expected = List.of(7, 32, 6, 25, 12, 48, 1, 13);

        BTreeNode<Integer> root = tree1.search(25).orElseThrow();
        root = invertTree(root);

        List<Integer> actual = inOrderTraversal(root)
                .get()
                .stream()
                .map(BTreeNode::getKey)
                .toList();
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void getTargetCopyTest() throws CloneNotSupportedException {
        final AbstractBTree<Integer> clone = (AbstractBTree<Integer>) tree1.clone();
        BTreeNode<Integer> cloneNode = getTargetCopy(tree1.getRoot(), clone.getRoot(), new BTreeNode<>(12))
                .orElseThrow();
        BTreeNode<Integer> originalNode = tree1.search(12).orElseThrow();

        assertNotNull(clone);
        assertNotNull(cloneNode);
        assertNotNull(originalNode);
        assertSame(originalNode.getKey(), cloneNode.getKey());
        assertNotSame(originalNode, cloneNode);
    }

    @Test
    public void testLevelOrderTraversal(){
        final List<List<Integer>> expected1 = List.of(List.of(25), List.of(48, 32), List.of(1, 12, 6, 7), List.of(13));
        final List<List<BTreeNode<Integer>>> actual1 = tree1.levelOrderTraversal();

        assertEquals(expected1.size(), actual1.size());

        IntStream.range(0, expected1.size()).forEach(i -> {
            List<Integer> exp = expected1.get(i);
            List<Integer> act = actual1.get(i).stream().map(TreeNode::getKey).toList();
            assertArrayEquals(exp.toArray(), act.toArray());
        });

        final List<List<Integer>> expected2 = List.of(List.of(1), List.of(0, 1), List.of(0, 1, 0, 1));
        final List<List<BTreeNode<Integer>>> actual2 = binaryKeyTree.levelOrderTraversal();

        IntStream.range(0, expected2.size()).forEach(i -> {
            List<Integer> exp = expected2.get(i);
            List<Integer> act = actual2.get(i).stream().map(TreeNode::getKey).toList();
            assertArrayEquals(exp.toArray(), act.toArray());
        });
    }

    @Test
    public void testIsLeafSimilar() throws CloneNotSupportedException {
        BTree<Integer> clone = (BTree<Integer>) tree1.clone();
        boolean actual = BTreeUtils.isLeafSimilar(tree1.getRoot(), clone.getRoot());
        assertTrue(actual);

        tree1.setRoot(null);
        tree1.add(13);
        tree2.setRoot(null);
        tree2.add(15);
        actual = BTreeUtils.isLeafSimilar(tree1.getRoot(), tree2.getRoot());
        assertFalse(actual);

        tree2.setRoot(null);
        tree2.add(13);
        actual = BTreeUtils.isLeafSimilar(tree1.getRoot(), tree2.getRoot());
        assertTrue(actual);

        clone = (BTree<Integer>) disbalancedTree.clone();
        clone.delete(2);
        clone.delete(3);

        clone.add(3);
        clone.add(2);
        actual = BTreeUtils.isLeafSimilar(disbalancedTree.getRoot(), clone.getRoot());
        assertFalse(actual);

    }

    @Test
    public void testSumOfLeftLeaves(){
        int actual = BTreeUtils.sumOfLeftLeaves(tree1.getRoot());
        assertEquals(19, actual);

        tree1.setRoot(null);
        tree1.add(4);
        actual = BTreeUtils.sumOfLeftLeaves(tree1.getRoot());
        assertEquals(0, actual);

        tree3.setRoot(null);
        tree3.add(2);
        final BTreeNode<Integer> actualRoot = BTreeUtils.increasing(tree3.getRoot());
        actual = BTreeUtils.sumOfLeftLeaves(actualRoot);
        assertEquals(0, actual);
    }

    @Override
    protected AbstractBTree<Integer> createTree() {
        return TreeFactory.create(TreeType.B_TREE);
    }
}

package ru.algo.ds.tree.btree;

import ru.algo.ds.tree.TreeNode;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class BTreeUtils {

    private BTreeUtils(){}


    public static <K extends Comparable<K>> int level(K key, BTreeNode<K> root) {
        int level = 1;
        final Queue<BTreeNode<K>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++){
                BTreeNode<K> temp = queue.remove();
                if (temp.getKey().compareTo(key) == 0){
                    return level;
                }
                if (temp.getLeft() != null){
                    queue.add(temp.getLeft());
                }
                if (temp.getRight() != null){
                    queue.add(temp.getRight());
                }
            }
            level++;
        }
        return -1;
    }

    public static <K extends Comparable<K>> int childCount(BTreeNode<K> root){
        int count = 0;
        if((root.getLeft() == null && root.getRight() != null) || (root.getLeft() != null && root.getRight() == null)){
            count = 1;
        }
        if(root.getLeft() != null && root.getRight() != null){
            count = 2;
        }
        return count;
    }

    public static <K extends Comparable<K>> int countNodes(BTreeNode<K> root) {
        if(root == null){
            return 0;
        }
        return 1 + countNodes(root.getLeft()) + countNodes(root.getRight());
    }

    public static <K extends Comparable<K>> List<BTreeNode<K>> childs(BTreeNode<K> root){
        if(root == null) {
            throw new IllegalArgumentException();
        }

        final List<BTreeNode<K>> childs = new LinkedList<>();
        if(root.getLeft() != null){
            childs.add(root.getLeft());
        }
        if(root.getRight() != null){
            childs.add(root.getRight());
        }
        return childs;
    }

    public static <K extends Comparable<K>> boolean isLeaf(BTreeNode<K> root){
        return (root == null) || (root.getLeft() == null && root.getRight() == null);
    }

    public static int rangeSum(BTreeNode<Integer> root, int sum, int low, int high){
        if(root == null) return 0;
        if(root.getKey().compareTo(high) > 0){
            sum += rangeSum(root.getLeft(), sum, low, high);
        }else if(root.getKey().compareTo(low) < 0 ){
            sum += rangeSum(root.getRight(), sum, low, high);
        }else{
            sum += root.getKey() + rangeSum(root.getLeft(), sum, low, high) + rangeSum(root.getRight(), sum, low, high);
        }
        return sum;
    }

    public static <K extends Comparable<K>> Optional<List<List<BTreeNode<K>>>> levelOrderTraversal(BTreeNode<K> root) {
        if(root == null){
            return Optional.empty();
        }
        final List<BTreeNode<K>> lvlNodes = new ArrayList<>();
        final List<List<BTreeNode<K>>> nodes = new ArrayList<>();
        lvlNodes.add(root);
        nodes.add(lvlNodes);
        levelOrderTraversal(lvlNodes, nodes);
        if(nodes.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(nodes);
    }

    private static <K extends Comparable<K>> void levelOrderTraversal(List<BTreeNode<K>> lvlNodes, List<List<BTreeNode<K>>> nodes){
        if(lvlNodes == null || lvlNodes.isEmpty()){
            return;
        }
        final List<BTreeNode<K>> childNodes = new ArrayList<>();
        lvlNodes.stream()
                .filter(Objects::nonNull)
                .forEach(node -> {
                    if(node.getLeft() != null) childNodes.add(node.getLeft());
                    if(node.getRight() != null) childNodes.add(node.getRight());
                });
        if(!childNodes.isEmpty()){
            nodes.add(childNodes);
        }
        levelOrderTraversal(childNodes, nodes);
    }

    public static <K extends Comparable<K>> Optional<List<BTreeNode<K>>> inOrderTraversal(BTreeNode<K> root) {
        if(root == null){
            return Optional.empty();
        }
        final List<BTreeNode<K>> res = new ArrayList<>();
        inOrderTraversal(root, res::add);
        if(res.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(res);
    }

    private static <K extends Comparable<K>> void inOrderTraversal(BTreeNode<K> root, Consumer<BTreeNode<K>> param){
        if(root == null){
            return;
        }
        inOrderTraversal(root.getLeft(), param);
        param.accept(root);
        inOrderTraversal(root.getRight(), param);
    }

    public static <K extends Comparable<K>> Optional<List<BTreeNode<K>>> preOrderTraversal(BTreeNode<K> root) {
        if(root == null){
            return Optional.empty();
        }
        final List<BTreeNode<K>> res = new ArrayList<>();
        preOrderTraversal(root, res::add);
        if(res.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(res);
    }

    private static <K extends Comparable<K>> void preOrderTraversal(BTreeNode<K> root, Consumer<BTreeNode<K>> param){
        if(root == null){
            return;
        }
        param.accept(root);
        preOrderTraversal(root.getLeft(), param);
        preOrderTraversal(root.getRight(), param);
    }

    public static <K extends Comparable<K>> Optional<List<BTreeNode<K>>> postOrderTraversal(BTreeNode<K> root) {
        if(root == null){
            return Optional.empty();
        }
        final List<BTreeNode<K>> res = new ArrayList<>();
        postOrderInternal(root, res);
        if(res.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(res);
    }

    private static <K extends Comparable<K>> void postOrderInternal(BTreeNode<K> next, List<BTreeNode<K>> res){
        if(next == null){
            return;
        }
        postOrderInternal(next.getLeft(), res);
        postOrderInternal(next.getRight(), res);
        res.add(next);
    }

    /*public static <K extends Comparable<K>> Optional<List<BTreeNode<K>>> traversal(BTreeNode<K> root, String traversalType){
        final Optional<List<BTreeNode<K>>> nodes;
        switch (traversalType) {
            case "IN_ORDER" -> {
                inOrderTraversal(root, node -> {
                    if(root == null){
                        return Optional.empty();
                    }
                    final List<BTreeNode<K>> res = new ArrayList<>();
                    inOrderTraversal(root, res::add);
                    if(res.isEmpty()){
                        return Optional.empty();
                    }
                    return Optional.of(res);
                });
            }
        }
    }*/

    public static <K extends Comparable<K>> Optional<List<Double>> averageOfLevels(BTreeNode<K> root) {
        final AtomicReference<List<Double>> avgLstRef = new AtomicReference<>(new LinkedList<>());
        levelOrderTraversal(root).ifPresent((nodes) -> {
            nodes.forEach((node) -> {
                avgLstRef.get().add(average(node));
            });
        });
        final List<Double> avgLst = avgLstRef.get();
        if(avgLst.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(avgLst);
    }

    public static <K extends Comparable<K>> boolean isBalanced(BTreeNode<K> root) {
        if(root == null){
            return true;
        }
        int hLeft = height(root.getLeft());
        int hRight = height(root.getRight());
        if(Math.abs(hLeft - hRight) > 1){
            return false;
        }
        return isBalanced(root.getLeft()) && isBalanced(root.getRight());
    }

    public static <K extends Comparable<K>> boolean isSameTree(BTreeNode<K> root1, BTreeNode<K> root2) {
        if(root1 == null && root2 == null){
            return true;
        }
        if(root1 == null || root2 == null){
            return false;
        }
        if(root1.getKey() != root2.getKey()){
            return false;
        }
        return isSameTree(root1.getLeft(), root2.getLeft()) && isSameTree(root1.getRight(), root2.getRight());
    }

    public static <K extends Comparable<K>> boolean isSymmetricTree(BTreeNode<K> root){
        if(root == null){
            return true;
        }
        return isSymmetricTreeInternal(root.getLeft(), root.getRight());
    }

    public static <K extends Comparable<K>> BTreeNode<K> invertTree(BTreeNode<K> root){
        if(root == null){
            return null;
        }
        BTreeNode<K> left = root.getLeft();
        root.setLeft(invertTree(root.getRight()));
        root.setRight(invertTree(left));
        return root;
    }

    public static <K extends Comparable<K>> Optional<BTreeNode<K>> getTargetCopy(final BTreeNode<K> original, final BTreeNode<K> cloned, final BTreeNode<K> target) {
        final Queue<BTreeNode<K>> q = new LinkedList<>();
        BTreeNode<K> tmp;
        q.add(cloned);

        while (!q.isEmpty()){
            tmp = q.remove();
            if(tmp.getKey() == target.getKey()){
                return Optional.of(tmp);
            }
            if(tmp.getLeft() != null){
                q.add(tmp.getLeft());
            }
            if(tmp.getRight() != null){
                q.add(tmp.getRight());
            }
        }
        return Optional.empty();
    }

    public static <K extends Comparable<K>> List<BTreeNode<K>> leaves(BTreeNode<K> root){
        final List<BTreeNode<K>> leaves = new LinkedList<>();
        leavesInternal(root, leaves::add);
        return leaves;
    }

    public static <K extends Comparable<K>> boolean isLeafSimilar(BTreeNode<K> root1, BTreeNode<K> root2){
        final List<K> leaves1 = leaves(root1)
                .stream()
                .map(TreeNode::getKey)
                .toList();

        final List<K> leaves2 = leaves(root2)
                .stream()
                .map(TreeNode::getKey)
                .toList();

        return leaves1.equals(leaves2);
    }

    public static int sumOfLeftLeaves(BTreeNode<Integer> root){
        if(root == null){
            return 0;
        }
        return sumOfLeftLeaves(root, false);
    }

    private static <K extends Comparable<K>> int sumOfLeftLeaves(BTreeNode<K> root, boolean isLeft){
        if(root == null) return 0;
        if(root.getLeft() == null && root.getRight() == null){
            return isLeft ? Integer.class.cast(root.getKey()) : 0;
        }
        return sumOfLeftLeaves(root.getLeft(), true) + sumOfLeftLeaves(root.getRight(), false);
    }

    public static <K extends Comparable<K>> int getMinimumDifference(BTreeNode<K> root) {
        if(root == null) return 0;
        return getMinimumDifference(root, Integer.MAX_VALUE);
    }

    private static <K extends Comparable<K>> int getMinimumDifference(BTreeNode<K> root, int min) {
        if(root == null){
            return 0;
        }
        if(root.getLeft() != null){
            min = Math.min(min, getMinimumDifference(root.getLeft(), min));
        }
        return Math.min(min, getMinimumDifference(root.getRight(), min));
    }

    public static <K extends Comparable<K>> List<BTreeNode<K>> siblings(K key, BTreeNode<K> root){
        if(root == null){
            return Collections.emptyList();
        }
        final List<BTreeNode<K>> siblings = new ArrayList<>();
        final Stack<BTreeNode<K>> stack = new Stack<>();
        stack.push(root);
        BTreeNode<K> tmp;

        while (!stack.isEmpty()){
            tmp = stack.pop();

            if (tmp.getLeft() != null && tmp.getLeft().getKey() == key){
                siblings.add(tmp.getRight());
            }
            if (tmp.getRight() != null && tmp.getRight().getKey() == key){
                siblings.add(tmp.getLeft());
            }
            if (tmp.getLeft() != null){
                stack.push(tmp.getLeft());
            }
            if (tmp.getRight() != null){
                stack.push(tmp.getRight());
            }
        }
        return siblings;
    }

    public static <K extends Comparable<K>> boolean isSubtree(BTreeNode<K> root, BTreeNode<K> subRoot) {
        if(root == null){
            return false;
        }
        return isSameTree(root, subRoot) || isSubtree(root.getLeft(), subRoot) || isSubtree(root.getRight(), subRoot);
    }

    public static int sumRootToLeaf(BTreeNode<Integer> root) {
        if(root == null){
            return 0;
        }

        final int[] sum = {0};
        final StringBuilder buffer = new StringBuilder();
        sumRootToLeaf(root, buffer, sum);
        return sum[0];
    }

    private static int sumRootToLeaf(BTreeNode<Integer> root, StringBuilder costs, int[] buffer){
        costs.append(root.getKey());
        if (isLeaf(root)) {
            buffer[0] += Integer.parseInt(costs.toString(), 2);
            costs.deleteCharAt(costs.length() - 1);
        } else {
            if (root.getLeft() != null) {
                sumRootToLeaf(root.getLeft(), costs, buffer);
            }
            if (root.getRight() != null) {
                sumRootToLeaf(root.getRight(), costs, buffer);
            }
            costs.deleteCharAt(costs.length() - 1);
        }
        return buffer[0];
    }

    public static BTreeNode<Integer> increasing(BTreeNode<Integer> root){
        if(root == null){
            return null;
        }
        final BSTree increasingTree = new BSTree();
        final List<BTreeNode<Integer>> nodes = inOrderTraversal(root).orElse(Collections.emptyList());
        nodes.stream()
                .forEach((node) -> increasingTree.add(node.getKey()));
        return increasingTree.getRoot();
    }

    private static <K extends Comparable<K>> void leavesInternal(BTreeNode<K> root, Consumer<BTreeNode<K>> param){
        if (root == null){
            return;
        }
        if (root.getLeft() == null && root.getRight() == null){
            param.accept(root);
        }
        if (root.getLeft() != null){
            leavesInternal(root.getLeft(), param);
        }
        if (root.getRight() != null){
            leavesInternal(root.getRight(), param);
        }
    }

    private static <K extends Comparable<K>> boolean isSymmetricTreeInternal(BTreeNode<K> left, BTreeNode<K> right){
        if(left == null && right == null){
            return true;
        }
        if(left == null || right == null){
            return false;
        }
        return left.getKey() == right.getKey()
                && isSymmetricTreeInternal(left.getLeft(), right.getRight())
                && isSymmetricTreeInternal(left.getRight(), right.getLeft());
    }

    public static <K extends Comparable<K>> int height(BTreeNode<K> root) {
        if(root == null){
            return 0;
        }
        int leftHeight = height(root.getLeft());
        int rightHeight = height(root.getRight());

        if(leftHeight > rightHeight){
            return leftHeight + 1;
        }
        return rightHeight + 1;
    }

    public static <K extends Comparable<K>> boolean isUnivalTree(BTreeNode<K> root) {
        if(root == null){
            return true;
        }
        final BTreeNode<K> left = root.getLeft();
        final BTreeNode<K> right = root.getRight();
        if((left != null && left.getKey() != root.getKey())
                || (right != null && right.getKey() != root.getKey())){
            return false;
        }
        return  isUnivalTree(left) && isUnivalTree(right);
    }

    private static <K extends Comparable<K>> Optional<BTreeNode<K>> search(K key, BTreeNode<K> root){
        if(root == null){
            return Optional.empty();
        }
        BTreeNode<K> tmp = root;
        final Queue<BTreeNode<K>> q = new LinkedList<>();
        q.add(tmp);

        while (!q.isEmpty()){
            tmp = q.remove();

            if(tmp.getKey().compareTo(key) == 0){
                return Optional.of(tmp);
            }

            if(tmp.getLeft() != null){
                q.add(tmp.getLeft());
            }
            if(tmp.getRight() != null){
                q.add(tmp.getRight());
            }
        }
        return Optional.empty();
    }

    public static <K extends Comparable<K>> boolean isCousins(BTreeNode<K> root, K key1, K key2) {
        if(root == null || root.getLeft() == null || root.getRight() == null){
            return false;
        }
        AtomicBoolean result = new AtomicBoolean(false);
        search(key1, root).ifPresent(first -> search(key2, root).ifPresent(second -> {
            int fDepth = depth(root, first);
            int sDepth = depth(root, second);

            parent(first.getKey(), root).ifPresent(fParent -> {
                parent(second.getKey(), root).ifPresent(sParent -> {
                    if((fParent.getKey() != sParent.getKey()) && (fDepth == sDepth)){
                        result.set(true);
                    }
                });
            });
        }));
        return result.get();
    }

    public static <K extends Comparable<K>> int depth(BTreeNode<K> root, BTreeNode<K> node) {
        if(root == null || node == null){
            return 0;
        }
        int depth = 1;
        Optional<BTreeNode<K>> parent = parent(node.getKey(), root);
        while (parent.isPresent()){
            parent = parent(parent.get().getKey(), root);
            depth++;
        }
        return depth;
    }

    public static <K extends Comparable<K>> int minimumDifference(BTreeNode<K> root) {
        if(root == null){
            return 0;
        }
        return minimumDifference(Integer.MAX_VALUE, root);
    }

    private static <K extends Comparable<K>> int minimumDifference(int minDifference, BTreeNode<K> root){
        if(root == null){
            return 0;
        }
        minDifference = Math.min(minimumDifference(minDifference, root.getLeft()), minDifference);
        minDifference = Math.min(minimumDifference(minDifference, root.getRight()), minDifference);
        return minDifference;
    }

    public static <K extends Comparable<K>> Optional<BTreeNode<K>> parent(K key, BTreeNode<K> root) {
        if(root == null || root.getKey().compareTo(key) == 0){
            return Optional.empty();
        }
        return parentInternal(root, key, null);
    }

    private static <K extends Comparable<K>> Double average(List<BTreeNode<K>> src){
        return src.stream()
                .mapToInt(node -> (Integer) node.getKey())
                .average()
                .orElseThrow(IllegalStateException::new);
    }

    private static <K extends Comparable<K>> Optional<BTreeNode<K>> parentInternal(BTreeNode<K> node, K key, BTreeNode<K> parent) {
        if (node == null) {
            return Optional.empty();
        }
        if (node.getKey().compareTo(key) == 0) {
            return Optional.of(parent);
        }

        Optional<BTreeNode<K>> tmpOpt = parentInternal(node.getLeft(), key, node);

        if (tmpOpt.isPresent()) {
            return tmpOpt;
        }
        return parentInternal(node.getRight(), key, node);
    }
}

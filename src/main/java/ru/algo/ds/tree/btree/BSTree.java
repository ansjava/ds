package ru.algo.ds.tree.btree;

import java.util.*;

public class BSTree extends AbstractBTree<Integer>{

    public BSTree(){
        super();
    }

    public BSTree(int[] src){
        int[] sorted = Arrays.stream(src)
                .sorted()
                .toArray();
        this.root = fromArray(sorted, 0, src.length - 1)
                .orElseThrow(IllegalStateException::new);
    }

    public static Optional<BTreeNode<Integer>> fromArray(int[] nums, int start, int end){
        if(start > end){
            return Optional.empty();
        }
        int middle = (start + end)/2;
        BTreeNode<Integer> node = new BTreeNode<>(nums[middle]);
        fromArray(nums, start, middle - 1)
                .ifPresent(node::setLeft);

        fromArray(nums, middle + 1, end)
                .ifPresent(node::setRight);

        return Optional.of(node);
    }

    @Override
    public void add(Integer key) {
        if(root == null) {
            root = new BTreeNode<>(key);
            return;
        }
        add(key, root);
    }

    @Override
    public Optional<BTreeNode<Integer>> search(Integer key) {
        if(root == null){
            return Optional.empty();
        }
        final BTreeNode<Integer> res = search(key, root);
        if(res == null){
            return Optional.empty();
        }
        return Optional.of(res);
    }

    private BTreeNode<Integer> search(Integer key, BTreeNode<Integer> root){
        if(root == null){
            return null;
        }
        BTreeNode<Integer> res;
        if(root.getKey() == key){
            res = root;
        }else if(root.getKey() > key){
            res = search(key, root.getLeft());
        }else{
            res = search(key, root.getRight());
        }
        return res;
    }

    @Override
    public void delete(Integer key) {
        search(key).ifPresent(node -> {
            Optional<BTreeNode<Integer>> parentOpt = parent(key);
            if(parentOpt.isEmpty()){
                root = null;
                return;
            }
            final List<BTreeNode<Integer>> childs = BTreeUtils.childs(node);
            int childCount = childs.size();
            final BTreeNode<Integer> parent = parentOpt.get();
            if(childCount == 0){
                if(node.getKey() < parent.getKey()){
                    parent.setLeft(null);
                }else if(node.getKey() > parent.getKey()){
                    parent.setRight(null);
                }
            }else if(childCount == 1){
                if(node.getKey() < parent.getKey()){
                    parent.setLeft(childs.get(0));
                }else if(node.getKey() > parent.getKey()){
                    parent.setRight(childs.get(0));
                }
            }else{
                final BTreeNode<Integer> heir = searchSuccessorForDeletedParent(node.getRight(), node);
                parent.setRight(heir);
            }
        });
    }

    public BTreeNode<Integer> findKthMinNode(int k){
        if(root == null){
            return null;
        }
        final Stack<BTreeNode<Integer>> stack = new Stack<>();
        BTreeNode<Integer> current = root;
        while (current != null){
            stack.push(current);
            current = current.getLeft();
        }
        BTreeNode<Integer> node;
        while (k != 0) {
            node = stack.pop();
            k--;
            if (k == 0) return node;
            BTreeNode<Integer> right = node.getRight();
            while (right != null) {
                stack.push(right);
                right = right.getLeft();
            }
        }
        return null;
    }

    @Override
    public List<BTreeNode<Integer>> view(View view) {
        return null;
    }

    public int rangeSum(int low, int high) {
        if(root == null){
            return 0;
        }
        int sum = 0;
        return rangeSum(root, sum, low, high);
    }

    public static /*<K extends Comparable<K>>*/ int rangeSum(BTreeNode<? super Integer> root, int sum, int low, int high){
        /*if(root == null) return 0;
        if(root.getKey().compareTo(high) > 0){
            sum += rangeSum(root.getLeft(), sum, low, high);
        }else if(root.getKey().compareTo(low) < 0 ){
            sum += rangeSum(root.getRight(), sum, low, high);
        }else{
            sum += root.getKey() + rangeSum(root.getLeft(), sum, low, high) + rangeSum(root.getRight(), sum, low, high);
        }
        return sum;*/
        return 0;
    }

    public BTreeNode<Integer> increasing(){
        if(root == null){
            return null;
        }
        final BSTree increasingTree = new BSTree();
        final List<BTreeNode<Integer>> nodes = inOrderTraversal();
        nodes.stream()
                .forEach((node) -> increasingTree.add(node.getKey()));
        return increasingTree.getRoot();
    }

    private BTreeNode<Integer> searchSuccessorForDeletedParent(BTreeNode<Integer> current, BTreeNode<Integer> previous){
        if(current == null){
            return previous;
        }
        return searchSuccessorForDeletedParent(current.getLeft(), current);
    }

    private BTreeNode<Integer> add(Integer key, BTreeNode<Integer> root){
        if(root == null){
            return new BTreeNode<>(key);
        }
        if(key <= root.getKey()){
            root.setLeft(add(key, root.getLeft()));
        }
        if(key > root.getKey()){
            root.setRight(add(key, root.getRight()));
        }
        return root;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public Optional<BTreeNode<Integer>> minValueNode() {
        if(root == null){
            return Optional.empty();
        }
        BTreeNode<Integer> current = root;
        while (current.getLeft() != null){
            current = current.getLeft();
        }
        return Optional.of(current);
    }
}
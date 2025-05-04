package ru.algo.ds.tree.btree;

import ru.algo.ds.tree.Tree;

import java.util.*;

public abstract class AbstractBTree<K extends Comparable<K>> implements Tree<K, BTreeNode<K>>, Cloneable{

    protected BTreeNode<K> root;

    public BTreeNode<K> getRoot() {
        return root;
    }

    public void setRoot(BTreeNode<K> root) {
        this.root = root;
    }

    @Override
    public int count() {
        return BTreeUtils.countNodes(root);
    }

    @Override
    public int diameter() {
        if(root == null){
            return 0;
        }

        Map<Integer,K> paths = new HashMap<>();
        diameterInternal(root, paths);

        Integer maxPath = paths.keySet()
                .stream()
                .max(Integer::compareTo)
                .get();

        BTreeNode<K> maxNode = search(paths.get(maxPath)).get();
        paths.clear();
        diameterInternal(maxNode, paths);
        return paths.keySet()
                .stream()
                .max(Integer::compareTo)
                .get();
    }

    private void diameterInternal(BTreeNode<K> root, Map<Integer,K> paths){
        if(root == null){
            return;
        }
        final Queue<BTreeNode<K>> q = new LinkedList<>();
        q.add(root);
        BTreeNode<K> tmp;
        while (!q.isEmpty()){
            tmp = q.remove();
            paths.putIfAbsent(level(tmp.getKey()), tmp.getKey());
            if(tmp.getLeft() != null){
                q.add(tmp.getLeft());
            }
            if(tmp.getRight() != null){
                q.add(tmp.getRight());
            }

        }
    }

    @Override
    public int level(K key) {
        return BTreeUtils.level(key, root);
    }

    @Override
    public int height() {
        return BTreeUtils.height(root);
    }

    @Override
    public int maxDepth(BTreeNode<K> node) {
        if(node == null){
            return 0;
        }
        return 1 + Math.max(maxDepth(node.getLeft()), maxDepth(node.getRight()));
    }

    @Override
    public int depth(BTreeNode<K> node) {
        if(node == null || root == null){
            return 0;
        }
        int depth = 1;
        Optional<BTreeNode<K>> prnt = parent(node.getKey());

        while (prnt.isPresent()){
            prnt = parent(prnt.get().getKey());
            depth++;
        }
        return depth;
    }

    @Override
    public List<BTreeNode<K>> leaves() {
        return BTreeUtils.leaves(root);
    }

    public List<BTreeNode<K>> notLeaves(){
        if(root == null){
            return Collections.emptyList();
        }
        final List<BTreeNode<K>> notLeafNodes = new LinkedList<>();
        notLeaves(root, notLeafNodes);
        return notLeafNodes;
    }

    public void notLeaves(BTreeNode<K> root, List<BTreeNode<K>> nodes){
        if(root == null || (root.getLeft() == null && root.getRight() == null)){
            return;
        }
        if(root.getLeft() != null || root.getRight() != null){
            nodes.add(root);
        }
        notLeaves(root.getLeft(), nodes);
        notLeaves(root.getRight(), nodes);
    }

    @Override
    public List<BTreeNode<K>> siblings(K key) {
        final List<BTreeNode<K>> siblings = new ArrayList<>();
        search(key).flatMap(node -> parent(node.getKey())).ifPresent(parent -> {
            BTreeNode<K> tmp = parent.getLeft();
            if (tmp != null && tmp.getKey() != key) {
                siblings.add(tmp);
            }
            tmp = parent.getRight();
            if (tmp != null && tmp.getKey() != key) {
                siblings.add(tmp);
            }
        });
        return siblings;
    }

    @Override
    public List<BTreeNode<K>> childs(K key) {
        final List<BTreeNode<K>> childs = new ArrayList<>();
        search(key).ifPresent(node -> {
            if(node.getLeft() != null){
                childs.add(node.getLeft());
            }
            if(node.getRight() != null){
                childs.add(node.getRight());
            }
        });
        return childs;
    }

    @Override
    public boolean isCousins(K key1, K key2) {
        return BTreeUtils.isCousins(root, key1, key2);
    }

    public boolean isChildsSumEqualsParent(BTreeNode<K> root) {
        return false;
        /*int nodeCount = count();
        if((root == null) || (root.getLeft() == null) || root.getRight() == null || (nodeCount < 1) || (nodeCount > 3)){
            return false;
        }
        return root.getKey().compareTo(Integer.class.cast(root.getLeft().getKey()) + Integer.class.cast(root.getRight().getKey()));*/
    }

    @Override
    public Optional<BTreeNode<K>> parent(K key) {
        if(root == null || root.getKey() == key){
            return Optional.empty();
        }
        final BTreeNode<K> parent = parentInternal(root, key, null);
        if(parent == null){
            return Optional.empty();
        }
        return Optional.of(parent);
    }
    
    @Override
    public List<BTreeNode<K>> view(View view){
        if(root == null){
            return Collections.emptyList();
        }
        int maxLevel = 0;
        final List<BTreeNode<K>> nodes = new LinkedList<>();
        if(View.RIGHT.equals(view)){
            rightView(root, 1, maxLevel, nodes);
        }else if(View.LEFT.equals(view)){
            leftView(root, 1, maxLevel, nodes);
        }
        return nodes;
    }
    
    @Override
    public List<List<BTreeNode<K>>> levelOrderTraversal() {
        return BTreeUtils.levelOrderTraversal(root).orElse(Collections.emptyList());
    }

    @Override
    public List<BTreeNode<K>> inOrderTraversal() {
        return BTreeUtils.inOrderTraversal(root).orElse(Collections.emptyList());
    }

    @Override
    public List<BTreeNode<K>> preOrderTraversal() {
        return BTreeUtils.preOrderTraversal(root).orElse(Collections.emptyList());
    }

    @Override
    public List<BTreeNode<K>> postOrderTraversal() {
        return BTreeUtils.postOrderTraversal(root).orElse(Collections.emptyList());
    }

    private int levelInternal(BTreeNode<K> node, K key, int level){
        if (node == null){
            return 0;
        }
        if (node.getKey() == key){
            return level;
        }
        int downLevel = levelInternal(node.getLeft(), key, level + 1);
        if (downLevel != 0){
            return downLevel;
        }
        downLevel = levelInternal(node.getRight(), key, level + 1);
        return downLevel;
    }

    private BTreeNode<K> parentInternal(BTreeNode<K> node, K key, BTreeNode<K> parent) {
        BTreeNode<K> temp;
        if (node.getKey() == key){
            return parent;
        }
        if(node.getLeft() != null){
            temp = parentInternal(node.getLeft(), key, node);
            if (temp != null){
                return temp;
            }
        }
        if (node.getRight() != null){
            temp = parentInternal(node.getRight(), key, node);
            return temp;
        }
        return null;
    }

    private void leftView(BTreeNode<K> node, int level, int maxLevel, List<BTreeNode<K>> nodes) {
        if(maxLevel < level){
            nodes.add(root);
            maxLevel = level;
        }
        leftView(root.getLeft(), level + 1, maxLevel, nodes);
    }

    private void rightView(BTreeNode<K> node, int level, int maxLevel, List<BTreeNode<K>> nodes) {
        if(maxLevel < level){
            nodes.add(node);
            maxLevel = level;
        }
        rightView(node.getRight(), level + 1, maxLevel, nodes);
    }

    @Override
    public abstract Object clone() throws CloneNotSupportedException;

    public abstract Optional<BTreeNode<K>> minValueNode();
}

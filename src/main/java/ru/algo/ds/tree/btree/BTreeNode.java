package ru.algo.ds.tree.btree;

import ru.algo.ds.tree.TreeNode;

public class BTreeNode<T extends Comparable<T>> extends TreeNode<T> {
    private BTreeNode<T> left;
    private BTreeNode<T> right;


    public BTreeNode(T key) {
        this(key, null, null);
    }

    public BTreeNode(T key, BTreeNode<T> left, BTreeNode<T> right) {
        super(key);
        this.left = left;
        this.right = right;
    }

    public BTreeNode<T> getLeft() {
        return left;
    }

    public BTreeNode<T> getRight() {
        return right;
    }

    public void setLeft(BTreeNode<T> left) {
        this.left = left;
    }

    public void setRight(BTreeNode<T> right) {
        this.right = right;
    }
}

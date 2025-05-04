package ru.algo.ds.tree;

public class TreeNode<T extends Comparable<T>> {
    private T key;

    public TreeNode(T key){
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }
}

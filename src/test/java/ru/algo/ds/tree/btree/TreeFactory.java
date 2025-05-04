package ru.algo.ds.tree.btree;

public class TreeFactory {

    public static AbstractBTree<Integer> create(TreeType type){
        return switch (type){
            case B_TREE -> new BTree<>();
            case BS_TREE -> new BSTree();
            default -> throw new IllegalArgumentException("Wrong a type of tree");
        };
    }
}

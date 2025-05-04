package ru.algo.ds.tree.btree;

public enum TreeType {
    B_TREE("Бинарное дерево"),
    BS_TREE("Бинарное дерево поиска"),
    N_TREE("N-арное дерево"),
    SEGMENT_TREE("Сегментное дерево")
    ;

    private final String value;

    TreeType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

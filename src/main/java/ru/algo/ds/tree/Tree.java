package ru.algo.ds.tree;

import ru.algo.ds.tree.btree.View;

import java.util.List;
import java.util.Optional;

public interface Tree<K,T> {

    int height();

    int maxDepth(T node);

    int depth(T node);

    int diameter();

    int level(K key);

    int count();

    Optional<T> search(K key);

    void add(K key);

    void delete(K key);

    Optional<T> parent(K key);

    List<T> leaves();

    List<T> siblings(K key);

    List<T> childs(K key);

    boolean isCousins(K key1, K key2);

    List<T> view(View view);

    List<List<T>> levelOrderTraversal();

    List<T> inOrderTraversal();

    List<T> preOrderTraversal();

    List<T> postOrderTraversal();
}

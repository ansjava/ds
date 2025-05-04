package ru.algo.ds.tree.btree;

import java.util.*;

public class BTree<K extends Comparable<K>> extends AbstractBTree<K> {

    @Override
    public Optional<BTreeNode<K>> search(K key) {
        if(root == null){
            return Optional.empty();
        }
        BTreeNode<K> tmp = root;
        final Queue<BTreeNode<K>> q = new LinkedList<>();
        q.add(tmp);

        while (!q.isEmpty()){
            tmp = q.remove();

            if(tmp.getKey() == key){
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

    @Override
    public void add(K key) {
        if(root == null){
            root = new BTreeNode<>(key);
            return;
        }
        BTreeNode<K> temp = root;
        BTreeNode<K> newNode;
        final Queue<BTreeNode<K>> q = new LinkedList<>();
        q.add(temp);

        while (!q.isEmpty()){
            temp = q.remove();

            if (temp.getLeft() == null){
                newNode = new BTreeNode<>(key);
                temp.setLeft(newNode);
                break;
            }else{
                q.add(temp.getLeft());
            }

            if(temp.getRight() == null){
                newNode = new BTreeNode<>(key);
                temp.setRight(newNode);
                break;
            }else{
                q.add(temp.getRight());
            }
        }
    }

    @Override
    public void delete(K key) {

    }

    public static BTreeNode<Integer> merge(BTreeNode<Integer> t1, BTreeNode<Integer> t2){
        if (t1 == null)
            return t2;
        if (t2 == null)
            return t1;
        t1.setKey(t1.getKey() + t2.getKey());
        t1.setLeft(merge(t1.getLeft(), t2.getLeft()));
        t1.setRight(merge(t1.getRight(), t2.getRight()));
        return t1;
    }

    public boolean isSameTree(BTreeNode<K> t1, BTreeNode<K> t2) {
        if(t1 == null && t2 == null){
            return true;
        }
        if(t1 == null || t2 == null){
            return false;
        }
        if(t1.getKey() != t2.getKey()){
            return false;
        }
        return isSameTree(t1.getLeft(), t2.getLeft()) && isSameTree(t1.getRight(), t2.getRight());
    }

    public boolean isSymmetricTree(){
        return BTreeUtils.isSymmetricTree(root);
    }

    public boolean isBalanced(){
        return BTreeUtils.isBalanced(root);
    }

    public BTreeNode<K> invertTree(){
        return BTreeUtils.invertTree(root);
    }

    public Optional<BTreeNode<K>> getTargetCopy(final BTreeNode<K> target) {
        return BTreeUtils.getTargetCopy(root, root, target);
    }

    @Override
    public Optional<BTreeNode<K>> minValueNode() {
        return Optional.empty();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final BTree<K> cloned = new BTree<>();
        cloned.setRoot(cloneInternal(root));
        return cloned;
    }

    private BTreeNode<K> cloneInternal(BTreeNode<K> src){
        if(src == null){
            return null;
        }
        final BTreeNode<K> cloned = new BTreeNode<>(src.getKey());
        cloned.setLeft(cloneInternal(src.getLeft()));
        cloned.setRight(cloneInternal(src.getRight()));
        return cloned;
    }
}

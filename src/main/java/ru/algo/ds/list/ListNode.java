package ru.algo.ds.list;

import java.util.Objects;

public class ListNode<T> {
    public T item;
    public ListNode<T> next;

    ListNode() {}
    public ListNode(T item) {
        this.item = item;
    }

    ListNode(T item, ListNode<T> next) {
        this.item = item;
        this.next = next;
    }

    ListNode(ListNode<T> next){
        this.next = next;
    }

    @Override
    public String toString() {
        return item + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode<T> listNode = (ListNode<T>) o;
        return item == listNode.item;
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
}
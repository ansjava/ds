package ru.algo.ds.list;

import java.util.Collection;
import java.util.NoSuchElementException;

public class LinkedList<T> {
    private ListNode<T> head;

    public LinkedList(){}

    public LinkedList(Collection<? extends T> from){
        if (from.isEmpty())
            return;
        addAll(0, from);
    }

    public ListNode<T> getHead() {
        return head;
    }

    public void set(int index, T obj){
        ListNode<T> current = head;
        for (int i = 0; current != null && i < index; i++) {
            current = current.next;
        }
        if (current == null || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        current.item = obj;
    }

    public T get(int index){
        int size = size();
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ListNode<T> current = head;
        while (index > 0){
            index--;
            current = current.next;
        }
        return current.item;
    }

    public T getFirst(){
        int size = size();
        if (size == 0)
            throw new NoSuchElementException();
        return head.item;
    }

    public T getLast(){
        int size = size();
        if (size == 0)
            throw new NoSuchElementException();
        return get(size - 1);
    }

    public int indexOf(T obj){
        ListNode<T> current = head;
        int index = 0;
        if (obj == null){
            while (current != null){
                if (current.item == null)
                    return index;
                index++;
                current = current.next;
            }
        }

        while (current != null){
            if (obj.equals(current.item))
                return index;
            current = current.next;
            index++;
        }
        return -1;
    }

    public int lastIndexOf(T obj){
        ListNode<T> current = head;
        int i = 0, index = -1;
        if (obj == null){
            while (current != null){
                if (current.item == null)
                    index = i;
                current = current.next;
                i++;
            }
        } else{
            while (current != null){
                if (obj.equals(current.item))
                    index = i;
                current = current.next;
                i++;
            }
        }
        return index;
    }

    public boolean contains(T obj){
        return indexOf(obj) != -1;
    }

    public boolean containsAll(Collection<? extends T> collection){
        for (T item : collection){
            if (indexOf(item) == -1)
                return false;
        }
        return true;
    }

    public void addFirst(T obj){
        if (head == null){
            head = new ListNode<>(obj);
            return;
        }
        ListNode<T> n = new ListNode<>(obj);
        n.next = head;
        head = n;
    }

    public void addLast(T obj){
        if (head == null){
            head = new ListNode<>(obj);
            return;
        }
        ListNode<T> current = head;
        while (current.next != null){
            current = current.next;
        }
        current.next = new ListNode<>(obj);
    }

    public void add(T obj){
        addLast(obj);
    }

    public void add(int index, T obj){
        int size = size();
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (index == 0){
            addFirst(obj);
            return;
        }
        if (index == size - 1){
            addLast(obj);
            return;
        }
        ListNode<T> current = head;
        while (index - 1 > 0){
            index--;
            current = current.next;
        }
        ListNode<T> node = new ListNode<>(obj);
        node.next = current.next;
        current.next = node;
    }

    public void addAll(Collection<? extends T> collection){
        addAll(size(),collection);
    }

    public void addAll(int index, Collection<? extends T> collection){
        if (collection.isEmpty())
            return;
        int size = size();
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        ListNode<T> res = new ListNode<>();
        ListNode<T> current = res;
        for (T item : collection) {
            current.next = new ListNode<>(item);
            current = current.next;
        }

        if (index == 0){
            current.next = head;
            head = res.next;
        } else {
            ListNode<T> prev = null, curr = current;
            current = head;

            while (index > 0){
                index--;
                prev = current;
                current = current.next;
            }
            prev.next = res.next;
            curr.next = current;
        }
    }

    public void remove(){
        remove(0);
    }

    public void removeLast(){
        remove(size() - 1);
    }

    public void remove(int index){
        ListNode<T> current = head;
        ListNode<T> prev = null;

        if (isEmpty())
            throw new NoSuchElementException();

        if (index == 0) {
            head = head.next;
            System.gc();
            return;
        }

        for (int i = 0; current != null && i < index; i++) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            prev.next = current.next;
            System.gc();
            return;
        }
        throw new NoSuchElementException();
    }

    public void remove(Object value){

    }

    public int size(){
        int size = 0;
        ListNode<T> current = head;
        while (current != null){
            size++;
            current = current.next;
        }
        return size;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public void clear(){
        for (ListNode<T> node = head; node != null; ){
            ListNode<T> next = node.next;
            node.item = null;
            node.next = null;
            node = next;
        }
        head = null;
        System.gc();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[");
        ListNode<T> current = head;
        while (current != null){
            sb.append(current.item).append(" ");
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
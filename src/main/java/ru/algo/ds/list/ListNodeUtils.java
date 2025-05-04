package ru.algo.ds.list;

public class ListNodeUtils {

    public static <T extends Comparable<T>> void print(ListNode<T> head){
        System.out.println();
        System.out.println("Your list is:");
        while (head != null){
            System.out.print(head);
            head = head.next;
        }
    }

    public static void printInt(ListNode<? extends Integer> head){
        System.out.println();
        System.out.println("Your list is:");
        while (head != null){
            System.out.print(head);
            head = head.next;
        }
    }
}

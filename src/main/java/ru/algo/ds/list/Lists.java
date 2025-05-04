package ru.algo.ds.list;

import java.util.HashSet;
import java.util.Set;

public final class Lists {
    private Lists(){}

    /**
     *  21. Merge Two Sorted Lists
     * @param list1 first list
     * @param list2 two list
     * @return merged list
     */
    public static <T extends Comparable<T>> ListNode<T> mergeTwoLists(ListNode<T> list1, ListNode<T> list2) {
        if(list1 != null && list2 != null){
            if(list1.item.compareTo(list2.item) < 0){
                list1.next = mergeTwoLists(list1.next,list2);
                return list1;
            }else{
                list2.next = mergeTwoLists(list1,list2.next);
                return list2;
            }
        }
        if(list2 == null){
            return list1;
        }
        return list2;
    }

    public static <T extends Comparable<T>> ListNode<T> swapPairs(ListNode<T> head) {
        if (head == null || head.next == null)
            return head;
        ListNode<T> dummy = new ListNode<>();
        dummy.next = head;
        ListNode<T> current = dummy;
        while (current.next != null && current.next.next != null){
            ListNode<T> tmp1 = current.next;
            ListNode<T> tmp2 = current.next.next;
            tmp1.next = tmp2.next;
            tmp2.next = tmp1;
            current.next = tmp2;
            current = tmp1;
        }
        return dummy.next;
    }

    /**
     *  2. Add Two Numbers
     * @param list1
     * @param list2
     * @return
     */
    public static ListNode<? extends Integer> addTwoNumbers(ListNode<? extends Integer> list1, ListNode<? extends Integer> list2) {
        if(list1 == null && list2 == null){
            return null;
        }
        ListNode<? extends Integer> res = new ListNode<>();
        ListNode<? extends Integer> current = res;
        int temp = 0;

        while(list1 != null || list2 != null || temp == 1) {
            int sum = 0;
            if(list1 != null){
                sum += list1.item;
                list1 = list1.next;
            }
            if(list2 != null){
                sum += list2.item;
                list2 = list2.next;
            }
            sum += temp;
            temp = sum/10;

            current.next = new ListNode(sum % 10);
            current = current.next;
        }
        return res.next;
    }

    /**
     *  1290. Convert Binary Number in a Linked List to Integer
     * @param head
     * @return
     */
    public static int getDecimalValue(ListNode<? extends Integer> head) {
        if(head == null)
            return 0;
        int result = 0;
        while(head != null){
            result = result * 2 + head.item;
            head = head.next;
        }
        return result;
    }

    /**
     *  83. Remove Duplicates from Sorted List
     * @param head
     * @return
     */
    public static <T extends Comparable<T>> ListNode<T> deleteDuplicates1(ListNode<T> head) {
        if (head == null || head.next == null)
            return head;

        ListNode<T> current = head;
        while(current.next != null){
            if(current.item.compareTo(current.next.item) != 0){
                current = current.next;
            }else{
                current.next = current.next.next;
            }
        }
        return head;
    }

    /**
     * 82. Remove Duplicates from Sorted List II
     * @param head
     * @return
     * @param <T>
     */
    public static <T extends Comparable<T>> ListNode<T> deleteDuplicates2(ListNode<T> head) {
        if (head == null)
            return null;

        ListNode<T> result = new ListNode<>(head);
        ListNode<T> prev = result;
        while(head != null){
            if (head.next != null && head.item.compareTo(head.next.item) == 0){
                while (head.next != null && head.item.compareTo(head.next.item) == 0){
                    head = head.next;
                }
                prev.next = head.next;
            }else {
                prev = prev.next;
            }
            head = head.next;
        }
        return result.next;
    }

    /**
     * 237. Delete Node in a Linked List
     * @param node node to be deleted
     */
    public static void deleteNode(ListNode<Integer> node) {
        if (node == null || node.next == null)
            return;
        node.item = node.next.item;
        node.next = node.next.next;
    }

    /**
     * 3217. Delete Nodes From Linked List Present in Array
     * @param nums
     * @param head
     * @return
     */
    public static ListNode<Integer> modifiedList(int[] nums, ListNode<Integer> head) {
        if (nums.length < 1)
            return head;
        if (head == null)
            return null;

        final Set<Integer> numsAsSet = new HashSet<>(nums.length);
        for (int num : nums)
            numsAsSet.add(num);

        ListNode<Integer> result = new ListNode<>(head);
        ListNode<Integer> current = result;
        while (head != null){
            if (numsAsSet.contains(head.item)){
                current.next = head.next;
            }else {
                current = head;
            }
            head = head.next;
        }
        return result.next;
    }

    /**
     *  234. Palindrome Linked List
     * @param head
     * @return
     */
    public static <T extends Comparable<T>> boolean isPalindrome(ListNode<T> head) {
        if(head == null)
            return false;
        if(head.next == null)
            return true;

        ListNode<T> prev = null;
        ListNode<T> current = head;

        while(current != null){
            ListNode<T> temp = new ListNode<>(current.item);
            temp.next = prev;
            prev = temp;
            current = current.next;
        }

        while(prev != null && head != null){
            if(prev.item.compareTo(head.item) != 0){
                return false;
            }
            prev = prev.next;
            head = head.next;

        }
        return true;
    }

    /**
     *  206. Reverse Linked List
     * @param head
     * @return
     */
    public static <T> ListNode<T> reverseList(ListNode<T> head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode<T> current = head;
        ListNode<T> prev = null;
        ListNode<T> next;

        while(current != null){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        head = prev;

        return head;
    }

    /**
     *  876. Middle of the Linked List
     * @param head
     * @return
     */
    public static <T> ListNode<T> middleNode(ListNode<T> head) {
        if (head == null)
            return null;

        ListNode<T> slow = head, fast = head;

        while(fast.next != null && fast.next.next != null){
            slow = fast.next;
            fast = fast.next.next;
        }
        if(fast.next != null){
            return slow.next;
        }
        return slow;
    }

    /**
     *  141. Linked List Cycle
     *  Floyd's Cycle-Finding algorithm
     * @param head
     * @return
     */
    public static <T> boolean hasCycle(ListNode<T> head) {
        ListNode<T> first = head, last = head;

        while(first != null && first.next != null){
            first = first.next.next;
            last = last.next;
            if(first == last)
                return true;
        }
        return false;
    }

    /**
     * 142. Linked List Cycle II
     * @param head
     * @return
     * @param <T>
     */
    public static <T> ListNode<T> detectCycle(ListNode<T> head) {
        ListNode<T> fast = head;
        ListNode<T> slow = head;

        while(fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow)
            {
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }

    private static <T> int cycleLength(ListNode<T> head){
        ListNode<T> fast = head;
        ListNode<T> slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow){
                int l = 0;
                ListNode<T> tmp = slow;
                do {
                    l++;
                    tmp = tmp.next;
                }
                while (tmp != slow);
                return l;
            }
        }
        return 0;
    }

    /**
     *  2181. Merge Nodes in Between Zeros
     * @param head
     * @return
     */
    public static ListNode<? extends Integer> mergeNodes(ListNode<? extends Integer> head) {
        if(head == null){
            return null;
        }
        ListNode<? extends Integer> result = new ListNode<>();
        ListNode<? extends Integer> current = result;
        int sum = 0;
        head = head.next;

        while(head != null && head.next != null){
            if(head.item.compareTo(0) != 0)
                sum += head.item;
            if(head.next.item.compareTo(0) == 0){
                current.next = new ListNode(sum);
                current = current.next;
                sum = 0;
            }
            head = head.next;
        }
        return result.next;
    }

    /**
     * 61. Rotate List
     * <p>
     * Given the head of a linked list, rotate the list to the right by k places.
     * @param head
     * @param k
     * @return
     */
    public static <T> ListNode<T> rotateRight(ListNode<T> head, int k) {
        if(head == null || head.next == null){
            return head;
        }

        int size = k % size(head);
        if(size == 0){
            return head;
        }

        ListNode<T> first = head, last = head;
        while(size > 0){
            last = last.next;
            --size;
        }

        while (last.next != null){
            last = last.next;
            first = first.next;
        }
        last.next = head;
        head = first.next;
        first.next = null;
        return head;
    }

    public static <T> int size(ListNode<T> head){
        int size = 0;
        ListNode<T> current = head;
        while (current != null){
            size++;
            current = current.next;
        }
        return size;
    }

    /**
     * 203. Remove Linked List Elements
     * @param head
     * @param value
     * @return
     * @param <T>
     */
    public static <T extends Comparable<T>> ListNode<T> removeElements(ListNode<T> head, T value) {
        if(head == null)
            return null;

        ListNode<T> result = new ListNode<>(head);
        ListNode<T> current = result;

        while (current.next != null){
            if(current.next.item.compareTo(value) == 0){
                current.next = current.next.next;
            }else{
                current = current.next;
            }
        }
        return result.next;
    }

    /**
     *  19. Remove Nth Node From End of List
     * @param head
     * @param n
     * @return
     */
    public static <T> ListNode removeNthFromEnd(ListNode<T> head, int n) {
        if (head == null)
            throw new IndexOutOfBoundsException();

        ListNode<T> slow = head;
        ListNode<T> fast = head;
        ListNode<T> previous = null;

        for (int i = 1; i <= n && fast != null; i++){
            fast = fast.next;
        }

        if (fast == null)
            return head.next;

        while (fast != null){
            previous = slow;
            fast = fast.next;
            slow = slow.next;
        }
        previous.next = slow.next;

        return head;
    }

    /**
     *  19. Remove Nth Node From End of List
     * @param head
     * @param n
     * @return
     */
    public static <T> ListNode<T> removeNthFromEndArray(ListNode<T> head, int n) {
        ListNode<T>[] temp = new ListNode[30];
        int count = 0;

        while(head != null){
            temp[count] = head;
            count++;
            head = head.next;
        }
        if(n == count){
            return temp[1];
        }
        temp[count - n - 1].next = temp[count - n + 1];
        return temp[0];
    }
}
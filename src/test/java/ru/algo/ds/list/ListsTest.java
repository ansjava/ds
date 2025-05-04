package ru.algo.ds.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.algo.ds.list.Lists.*;

public class ListsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void addTwoNumbersTest(){
        {
            LinkedList<Integer> list1 = new LinkedList<>(List.of());
            LinkedList<Integer> list2 = new LinkedList<>(List.of(2,3,1));
            ListNode<? extends Integer> res = addTwoNumbers(list1.getHead(), list2.getHead());
            ListNodeUtils.printInt(res);
        }

        {
            LinkedList<Integer> list1 = new LinkedList<>(List.of(2,3,1));
            LinkedList<Integer> list2 = new LinkedList<>(List.of());
            ListNode<? extends Integer> res = addTwoNumbers(list1.getHead(), list2.getHead());
            ListNodeUtils.printInt(res);
        }

        {
            LinkedList<Integer> list1 = new LinkedList<>(List.of(1,2));
            LinkedList<Integer> list2 = new LinkedList<>(List.of(2,3,1));
            ListNode<? extends Integer> res = addTwoNumbers(list1.getHead(), list2.getHead());
            ListNodeUtils.printInt(res);
        }

        {
            LinkedList<Integer> list1 = new LinkedList<>(List.of(1,2,2));
            LinkedList<Integer> list2 = new LinkedList<>(List.of(2,3,9));
            ListNode<? extends Integer> res = addTwoNumbers(list1.getHead(), list2.getHead());
            ListNodeUtils.printInt(res);
        }

        {
            LinkedList<Integer> list1 = new LinkedList<>(List.of(9,2,2));
            LinkedList<Integer> list2;
            list2 = new LinkedList<>(List.of(5,3,9));
            ListNode<? extends Integer> res = addTwoNumbers(list1.getHead(), list2.getHead());
            ListNodeUtils.printInt(res);
        }
    }

    @Test
    public void getDecimalValueTest(){
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(0));
            assertEquals(0, getDecimalValue(list.getHead()));
        }
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(1));
            assertEquals(1, getDecimalValue(list.getHead()));
        }
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(1,0,1,0));
            assertEquals(10, getDecimalValue(list.getHead()));
        }
    }

    @Test
    public void removeNthFromEndTest() {
        final LinkedList<Integer> list = new LinkedList<>();
        assertThrows(IndexOutOfBoundsException.class,
                () -> Lists.removeNthFromEnd(list.getHead(),1));

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        ListNode<Integer> res = removeNthFromEnd(list.getHead(),5);
        ListNodeUtils.print(res);

        res = removeNthFromEnd(list.getHead(),1);
        ListNodeUtils.print(res);

        res = removeNthFromEnd(list.getHead(),2);
        ListNodeUtils.print(res);
    }

    @Test
    public void isPalindromeTest() {
        LinkedList<Integer> list = new LinkedList<>();
        assertFalse(isPalindrome(list.getHead()));

        list.add(1);
        assertTrue(isPalindrome(list.getHead()));

        list.addAll(List.of(1,2,1));
        assertFalse(isPalindrome(list.getHead()));

        list.add(1);
        assertTrue(isPalindrome(list.getHead()));
    }

    @Test
    public void mergeTwoListTest(){
        {
            LinkedList<Integer> list1 = new LinkedList<>();
            list1.add(1);
            list1.add(2);
            list1.add(4);
            list1.add(8);

            LinkedList<Integer> list2 = new LinkedList<>();
            list2.add(1);
            list2.add(3);
            list2.add(4);

            ListNode<Integer> actual = mergeTwoLists(list1.getHead(),list2.getHead());
            ListNodeUtils.print(actual);
        }
        {
            LinkedList<Integer> list1 = new LinkedList<>();

            LinkedList<Integer> list2 = new LinkedList<>();
            list2.add(1);
            list2.add(3);
            list2.add(4);

            ListNode<Integer> actual = mergeTwoLists(list1.getHead(),list2.getHead());
            ListNodeUtils.print(actual);
        }
        {
            LinkedList<Integer> list1 = new LinkedList<>();
            list1.add(1);
            list1.add(2);
            list1.add(4);
            list1.add(8);

            LinkedList<Integer> list2 = new LinkedList<>();

            ListNode<Integer> actual = mergeTwoLists(list1.getHead(),list2.getHead());
            ListNodeUtils.print(actual);
        }
    }

    @Test
    public void swapPairTest(){
        {LinkedList<Integer> list = new LinkedList<>();
            assertNull(list.getHead());

            list.add(1);
            assertEquals(1, swapPairs(list.getHead()).item);

            list.addAll(List.of(2,3));

            ListNode<Integer> res = swapPairs(list.getHead());
            ListNodeUtils.print(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(1,2,3,4));
            ListNode<Integer> res = swapPairs(list.getHead());
            ListNodeUtils.print(res);
        }
    }

    @Test
    public void deleteDuplicate1Test(){
        LinkedList<Integer> list = new LinkedList<>();
        assertNull(list.getHead());

        list.add(1);
        assertEquals(1, deleteDuplicates1(list.getHead()).item);

        list.addAll(List.of(1,1,0,4,4,5,2,2,2));
        ListNode<Integer> res = deleteDuplicates1(list.getHead());
        ListNodeUtils.print(res);
    }

    @Test
    public void deleteDuplicate2Test(){
        {
            LinkedList<Integer> list = new LinkedList<>();
            assertNull(list.getHead());

            list.add(1);
            assertEquals(1, deleteDuplicates2(list.getHead()).item);

            list.addAll(List.of(1,1,0,4,4,5,2,2,2));
            ListNode<Integer> res = deleteDuplicates2(list.getHead());
            ListNodeUtils.print(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>();
            list.addAll(List.of(1,1,1,0,4,4,5,2,2,2));
            ListNode<Integer> res = deleteDuplicates2(list.getHead());
            ListNodeUtils.print(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>();
            list.addAll(List.of(1, 2, 3));
            ListNode<Integer> res = deleteDuplicates2(list.getHead());
            ListNodeUtils.print(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>();
            list.addAll(List.of(1, 2, 2));
            ListNode<Integer> res = deleteDuplicates2(list.getHead());
            ListNodeUtils.print(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>();
            list.addAll(List.of(1, 1, 1));
            assertNull(deleteDuplicates2(list.getHead()));
        }
    }

    @Test
    public void modifiedListTest(){
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(1,2,1,3,1));
            int[] numbers = {1};
            ListNode<Integer> res = modifiedList(numbers,list.getHead());
            ListNodeUtils.print(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(1));
            int[] numbers = {1};
            assertNull(modifiedList(numbers,list.getHead()));
        }
    }

    @Test
    public void mergeNodesTest(){
        {
            LinkedList<Integer> list = new LinkedList<>();
            list.add(0);
            list.add(3);
            list.add(1);
            list.add(0);
            list.add(4);
            list.add(5);
            list.add(2);
            list.add(0);

            ListNode<? extends Integer> res = mergeNodes(list.getHead());
            ListNodeUtils.printInt(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>();
            list.add(0);
            list.add(1);
            list.add(0);
            list.add(3);
            list.add(0);
            list.add(2);
            list.add(2);
            list.add(0);

            ListNode<? extends Integer> res = mergeNodes(list.getHead());
            ListNodeUtils.printInt(res);
        }
    }

    @Test
    public void removeElementsTest(){
        {
            LinkedList<Integer> list = new LinkedList<>(List.of());
            assertNull(removeElements(list.getHead(), 2));
        }
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(1,1,2));
            ListNode<Integer> res = removeElements(list.getHead(), 1);
            ListNodeUtils.print(res);
        }
        {
            LinkedList<Integer> list = new LinkedList<>(List.of(1,2,2));
            ListNode<Integer> res = removeElements(list.getHead(), 2);
            ListNodeUtils.print(res);
        }
    }

    @Test
    public void reverseListTest(){
        LinkedList<Integer> list = new LinkedList<>();
        assertNull(reverseList(list.getHead()));

        list.add(1);
        assertEquals(1, reverseList(list.getHead()).item);

        list.addAll(List.of(2,3,4));
        assertEquals(4, reverseList(list.getHead()).item);
    }

    @Test
    public void middleNodeTest(){
        LinkedList<Integer> list = new LinkedList<>();
        assertNull(middleNode(list.getHead()));

        list.addAll(List.of(1,2,3));
        assertEquals(2, middleNode(list.getHead()).item);

        list.add(4);
        assertEquals(3, middleNode(list.getHead()).item);
    }

    @Test
    public void hasCycleTest(){
        ListNode<Integer> n1 = new ListNode<>(1);
        ListNode<Integer> n2 = new ListNode<>(2);
        ListNode<Integer> n3 = new ListNode<>(3);
        ListNode<Integer> n4 = new ListNode<>(4);
        ListNode<Integer> n5 = new ListNode<>(5);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n2;

        assertTrue(hasCycle(n1));
        assertFalse(hasCycle(null));

        ListNode<Integer> n6 = new ListNode<>(6);
        assertFalse(hasCycle(n6));

        n2.next = n1;
        assertTrue(hasCycle(n1));
    }

    @Test
    public void detectCycleTest(){
        ListNode<Integer> n1 = new ListNode<>(1);
        ListNode<Integer> n2 = new ListNode<>(2);
        ListNode<Integer> n3 = new ListNode<>(3);
        ListNode<Integer> n4 = new ListNode<>(4);
        ListNode<Integer> n5 = new ListNode<>(5);

        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n2;

        assertNotNull(detectCycle(n1));
        assertEquals(n2.item, detectCycle(n1).item);

        ListNode<Integer> n6 = new ListNode<>(6);
        assertNull(detectCycle(n6));

        n2.next = n1;
        assertNotNull(detectCycle(n1));
        assertEquals(n1.item, detectCycle(n1).item);
    }
}

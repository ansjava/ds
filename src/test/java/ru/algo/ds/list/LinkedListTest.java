package ru.algo.ds.list;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedListTest {

    @Test
    public void setTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        assertThrows(IndexOutOfBoundsException.class,
                () -> ls.set(0, 10));
        ls.add(1);
        ls.add(2);
        ls.add(3);
        ls.set(0,10);
        assertEquals(10, ls.get(0));
        ls.set(1,20);
        assertEquals(20, ls.get(1));
        ls.set(2,30);
        assertEquals(30, ls.get(2));
    }

    @Test
    public void getTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        ls.addFirst(4);
        ls.addFirst(3);
        ls.addFirst(2);
        ls.addFirst(1);

        assertThrows(IndexOutOfBoundsException.class,
                () -> ls.get(4));
        assertThrows(IndexOutOfBoundsException.class,
                () -> ls.get(-1));
        assertEquals(1, ls.get(0));
        assertEquals(4, ls.get(3));
        assertEquals(2, ls.get(1));
    }

    @Test
    public void getFirstTest(){
        final LinkedList<Integer> ls = new LinkedList<>();

        assertThrows(NoSuchElementException.class,
                ls::getFirst);

        ls.addFirst(4);
        assertEquals(4, ls.getFirst());

        ls.addFirst(3);
        assertEquals(3, ls.getFirst());
    }

    @Test
    public void getLastTest(){
        final LinkedList<Integer> ls = new LinkedList<>();

        assertThrows(NoSuchElementException.class,
                ls::getLast);

        ls.addLast(4);
        assertEquals(4, ls.getLast());

        ls.addFirst(3);
        assertEquals(4, ls.getLast());
    }

    @Test
    public void indexOfTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        assertEquals(-1, ls.indexOf(4));

        ls.addLast(2);
        ls.addLast(2);
        assertEquals(0, ls.indexOf(2));

        ls.addLast(4);
        assertEquals(2, ls.indexOf(4));

        ls.addFirst(4);
        assertEquals(0, ls.indexOf(4));
    }

    @Test
    public void lastIndexOfTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        assertEquals(-1, ls.lastIndexOf(4));

        ls.addLast(2);
        ls.addLast(2);
        assertEquals(1, ls.lastIndexOf(2));

        ls.addLast(4);
        assertEquals(2, ls.lastIndexOf(4));

        ls.addFirst(4);
        assertEquals(3, ls.lastIndexOf(4));
    }

    @Test
    public void containsTest(){
        LinkedList<Integer> l = new LinkedList<>();
        assertFalse(l.contains(0));
        l.addFirst(0);
        assertTrue(l.contains(0));
    }

    @Test
    public void addFirstTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        ls.addFirst(4);
        ls.addFirst(3);
        ls.addFirst(2);
        ls.addFirst(1);
        assertEquals(1, ls.get(0), "Get equality");
        assertEquals(4, ls.size(), "Size equality");
    }

    @Test
    public void addLastTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        ls.addLast(4);
        ls.addLast(3);
        ls.addLast(2);
        ls.addLast(1);
        assertEquals(1, ls.get(3), "Get equality");
        assertEquals(4, ls.size(), "Size equality");
    }

    @Test
    public void addTest(){
        final LinkedList<Integer> ls = new LinkedList<>();

        assertThrows(IndexOutOfBoundsException.class,
                () -> ls.add(1, 1)
        );
        ls.addFirst(4);
        ls.addFirst(3);
        ls.addFirst(2);
        ls.addFirst(1);

        ls.add(2, 10);
        assertEquals(10, ls.get(2));
        assertEquals(3, ls.get(3));

        ls.add(3, 20);
        assertEquals(20, ls.get(3));
        assertEquals(3, ls.get(4));
    }

    @Test
    public void addAllTest(){
        {
            LinkedList<Integer> src = new LinkedList<>();
            src.add(1);
            src.add(2);
            src.add(3);
            src.add(4);
            List<Integer> list = List.of(5,6,7);

            src.addAll(0, list);

            assertEquals(7, src.size());
            assertEquals(5, src.get(0));
            assertEquals(1, src.get(3));
            assertEquals(4, src.get(6));
        }
        {
            LinkedList<Integer> src = new LinkedList<>();
            src.add(1);
            src.add(2);
            src.add(3);
            src.add(4);
            List<Integer> list = List.of(5,6,7);

            src.addAll(4, list);

            assertEquals(7, src.size());
            assertEquals(1, src.get(0));
            assertEquals(5, src.get(4));
            assertEquals(7, src.get(6));
        }
        {
            LinkedList<Integer> src = new LinkedList<>();
            src.add(1);
            src.add(2);
            src.add(3);
            src.add(4);

            List<Integer> list = List.of(5,6,7);

            src.addAll(1, list);

            assertEquals(7, src.size());
            assertEquals(1, src.get(0));
            assertEquals(5, src.get(1));
            assertEquals(2, src.get(4));
            assertEquals(4, src.get(6));
        }
        {
            LinkedList<Integer> src = new LinkedList<>();
            src.add(1);
            src.add(2);
            src.add(3);
            src.add(4);
            List<Integer> list = List.of(5,6,7);

            src.addAll(list);

            assertEquals(7, src.size());
            assertEquals(1, src.get(0));
            assertEquals(5, src.get(4));
            assertEquals(7, src.get(6));
        }
    }

    @Test
    public void removeTest(){
        final LinkedList<Integer> ls = new LinkedList<>();

        assertThrows(NoSuchElementException.class,
                ls::remove
        );
        ls.add(4);
        ls.add(3);
        ls.add(2);
        ls.add(1);

        ls.remove();

        assertEquals(3, ls.get(0));
    }

    @Test
    public void removeLastTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        ls.add(4);
        ls.add(3);
        ls.add(2);
        ls.add(1);

        ls.removeLast();
        assertEquals(2, ls.get(2));
        ls.removeLast();
        assertEquals(3, ls.get(1));
        ls.removeLast();
        assertEquals(4, ls.get(0));
        ls.removeLast();
        assertThrows(NoSuchElementException.class,
                ls::removeLast
        );
    }

    @Test
    public void sizeTest(){
        final LinkedList<Integer> ls = new LinkedList<>();
        assertEquals(0, ls.size());

        ls.addFirst(0);
        assertEquals(1, ls.size());
    }

    @Test
    public void isEmptyTest(){
        LinkedList<Integer> l = new LinkedList<>();
        assertTrue(l.isEmpty());
        l.addFirst(0);
        assertFalse(l.isEmpty());
    }

    @Test
    public void clearTest(){
        LinkedList<Integer> l = new LinkedList<>();
        l.addFirst(0);
        assertFalse(l.isEmpty());
        l.clear();
        assertTrue(l.isEmpty());
    }
}
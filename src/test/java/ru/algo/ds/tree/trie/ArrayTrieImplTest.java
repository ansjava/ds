package ru.algo.ds.tree.trie;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testable
@Tag("tree")
public class ArrayTrieImplTest {
    protected Alphabet alphabet = Alphabet.ASCII;

    protected ArrayTrieImpl<Integer> trie = new ArrayTrieImpl<>(alphabet);

    @Test
    public void putTest(){
        trie.put("s", null);
        assertTrue(trie.get("s").isEmpty());

        trie.put("s", 1);
        trie.put("she", 2);
        assertTrue(trie.get("sh").isEmpty());

        final Optional<Integer> value = trie.get("she");
        assertTrue(value.isPresent());
        assertEquals(2, value.get());
    }

    @Test
    public void deleteTest(){
        trie.put("she", 1);
        trie.put("so", 2);
        trie.put("sort", 3);
        assertEquals(3, trie.size());

        trie.delete("so");
        assertEquals(2, trie.size());
        assertTrue(trie.get("so").isEmpty());
        assertTrue(trie.get("sort").isPresent());
        assertEquals(3, trie.get("sort").get());

        trie.put("so", 2);
        assertEquals(3, trie.size());
        trie.delete("sort");
        assertTrue(trie.get("so").isPresent());
        assertEquals(2, trie.size());
    }

    @Test
    public void sizeTest(){
        trie.put("s", null);
        assertEquals(0, trie.size());

        trie.put("s", 1);
        trie.put("she", 2);
        assertEquals(2, trie.size());
    }

    @Test
    public void keysTest(){
        trie.put("she", 1);
        trie.put("so", 2);
        trie.put("sort", 3);

        final Iterable<String> actual = trie.keys();
        assertIterableEquals(List.of("she", "so", "sort") , actual);
    }

    @Test
    public void keysWithPrefixTest(){
        trie.put("she", 1);
        trie.put("so", 2);
        trie.put("sort", 3);
        trie.put("sorting", 4);

        final Iterable<String> actual = trie.keysWithPrefix("so");
        assertIterableEquals(List.of("so", "sort", "sorting"), actual);
    }

    @Test
    public void keysThatMatchTest(){
        trie.put("she", 1);
        trie.put("so", 2);
        trie.put("sort", 3);
        trie.put("sorting", 4);

        Iterable<String> actual = trie.keysThatMatch("sor.");
        assertIterableEquals(List.of("sort"), actual);

        actual = trie.keysThatMatch(".o");
        assertIterableEquals(List.of("so"), actual);

        actual = trie.keysThatMatch(".");
        assertIterableEquals(List.of(), actual);
    }

    @Test
    public void longestPrefixOfTest(){
        trie.put("she", 1);
        trie.put("so", 2);
        trie.put("sort", 3);
        trie.put("sorting", 4);

        assertEquals("sort", trie.longestPrefixOf("sortin"));
        assertEquals("so", trie.longestPrefixOf("sor"));
        assertEquals("", trie.longestPrefixOf("sh"));
    }

    @Test
    public void longestCommonPrefixOf(){
        trie.put("she", 1);
        trie.put("so", 2);
        trie.put("sort", 3);
        trie.put("sorting", 4);

        assertEquals("s", trie.longestCommonPrefixOf(new String[]{"sorting","she","so","sort"}));

        ArrayTrieImpl<Integer> t = new ArrayTrieImpl<>();
        t.put("so", 2);
        t.put("sort", 3);
        t.put("sorting", 4);
        assertEquals("so", t.longestCommonPrefixOf(new String[]{"sort","so","sorting"}));
    }
}
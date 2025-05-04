package ru.algo.ds.tree.trie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Collection;
import java.util.List;

@Testable
@Tag("tree")
public class MapTrieImplTest {

    @Test
    public void keysWithPrefixTest() {
        MapTrieImpl<Integer> trie = new MapTrieImpl<>();
        trie.put("w", 1);
        trie.put("wo", 2);
        trie.put("wor", 3);
        trie.put("worl", 4);
        trie.put("world", 5);
        trie.put("work", 6);

        Collection<String> actual = trie.keysWithPrefix("");
        Assertions.assertIterableEquals(List.of("w", "wo", "wor", "work", "worl", "world"), actual);
    }
}
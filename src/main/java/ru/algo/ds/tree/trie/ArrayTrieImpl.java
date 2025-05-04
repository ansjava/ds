package ru.algo.ds.tree.trie;

import java.util.*;

public class ArrayTrieImpl<V> {
    private static Alphabet alphabet;
    private ArrayEntry root;

    public ArrayTrieImpl(){
        this(Alphabet.EXTENDED_ASCII);
    }

    public ArrayTrieImpl(String alphabet){
        this(new Alphabet(alphabet));
    }

    public ArrayTrieImpl(Alphabet alphabet){
        ArrayTrieImpl.alphabet = alphabet;
        this.root = new ArrayEntry(null);
    }

    public void put(String key, V value){
        if (Objects.isNull(key)){
            return;
        }

        ArrayEntry current = root;
        for (char ch : key.toCharArray()){
            if (!current.contains(ch))
                current.put(ch, new ArrayEntry());
            current = current.get(ch);
            current.edgesCount++;
        }
        current.setWord(value);
    }

    public Optional<V> get(String key){
        ArrayEntry node = getNodeByPrefix(key);
        if (Objects.isNull(node))
            return Optional.empty();
        return node.isWord() ? Optional.of((V)node.value) : Optional.empty();
    }

    private ArrayEntry getNodeByPrefix(String prefix){
        if (Objects.isNull(prefix)){
            return null;
        }
        ArrayEntry current = root;
        for (char ch : prefix.toCharArray()){
            if (!current.contains(ch))
                return null;
            current = current.get(ch);
        }
        return current;
    }

    public void delete(String key){
        root = delete(root, key, 0);
    }

    private ArrayEntry delete(ArrayEntry node, String key, int d){
        if (Objects.isNull(node))
            return null;
        if (d == key.length())
            node.value = null;
        else {
            char ch = key.charAt(d);
            node.entries[ch] = delete(node.get(ch), key, d+1);
        }
        if (Objects.nonNull(node.value))
            return node;
        for (char c : alphabet.toChar()){
            if (Objects.nonNull(node.get(c)))
                return node;
        }
        return null;
    }

    public boolean contains(String key){
        if (Objects.isNull(key) || key.isEmpty())
            return false;
        ArrayEntry current = root;
        for (char ch : key.toCharArray()){
            if (!current.contains(ch))
                return false;
            current = current.get(ch);
        }
        return current.isWord();
    }

    public boolean containsMatch(String pattern){
        if (Objects.isNull(pattern) || pattern.isEmpty())
            return false;
        return containsMatch(pattern, 0, root);
    }

    private boolean containsMatch(String pattern, int index, ArrayEntry node){
        if (index == pattern.length())
            return node.isWord();
        char ch = pattern.charAt(index);
        if (ch == '.'){
            for (char c : alphabet.toChar()){
                if (node.contains(c) && containsMatch(pattern, index + 1, node.get(c))){
                    return true;
                }
            }
            return false;
        }else {
            if (Objects.isNull(node.get(ch))){
                return false;
            }
        }
        return containsMatch(pattern, index + 1, node.get(ch));
    }

    public boolean isEmpty(){
        throw new UnsupportedOperationException();
    }

    public Collection<String> keys(){
        return keysWithPrefix("");
    }

    public Collection<String> keysWithPrefix(String prefix){
        final Queue<String> queue = new LinkedList<>();
        collect(getNodeByPrefix(prefix), prefix, queue);
        return queue;
    }

    public Collection<String> keysThatMatch(String pattern){
        final Queue<String> queue = new LinkedList<>();
        collect(root, "", pattern, queue, 0);
        return queue;
    }

    public String longestCommonPrefixOf(String[] keys){
        if(keys.length == 0){
            return "";
        }
        if(keys.length == 1){
            return keys[0];
        }
        int keysLength = keys.length;
        int length = 0;
        ArrayEntry current = root;
        for (char ch : keys[0].toCharArray()){
            ArrayEntry next = current.get(ch);
            if (next != null && next.edgesCount == keysLength){
                length++;
                current = current.get(ch);
            }else {
                return keys[0].substring(0,length);
            }
        }
        return keys[0];
    }

    public String longestPrefixOf(String key){
        int length = search(root, key, 0, 0);
        return key.substring(0, length);
    }

    public String longestSuffix() {
        return longestSuffix(root);
    }

    private String longestSuffix(ArrayEntry entry) {
        String longestSuffix = "";

        for(char ch : alphabet.toChar()) {
            if(Objects.isNull(entry.get(ch)) || !entry.get(ch).isWord())
                continue;

            String suffix = ch + longestSuffix(entry.get(ch));

            if(longestSuffix.length() < suffix.length())
                longestSuffix = suffix;
        }

        return longestSuffix;
    }

    private void collect(ArrayEntry node, String prefix, Queue<String> queue){
        if (Objects.isNull(node))
            return;
        if (node.isWord() /*&& Objects.nonNull(node.value)*/)
            queue.offer(prefix);
        for (char c : alphabet.toChar()){
            collect(node.get(c), prefix + c, queue);
        }
    }

    private void collect(ArrayEntry node, String prefix, String pattern, Queue<String> queue, int i){
        int d = prefix.length();
        if (Objects.isNull(node))
            return;
        if (d == pattern.length() && node.isWord())
            queue.offer(prefix);
        if (d == pattern.length())
            return;
        char next = pattern.charAt(d);
        for (char c : alphabet.toChar()){
            if (next == '.' || next == c)
                collect(node.get(c), prefix + c, pattern, queue, ++i);
        }
    }

    private int search(ArrayEntry node, String s, int d, int length){
        if (Objects.isNull(node))
            return length;
        if (Objects.nonNull(node.value))
            length = d;
        if (d == s.length())
            return length;
        char ch = s.charAt(d);
        return search(node.get(ch), s, d + 1, length);
    }

    public int size(){
        return size(root);
    }

    private int size(ArrayEntry node){
        if (node == null)
            return 0;
        int count = 0;
        if (node.isWord())
            count++;
        for (char ch : alphabet.toChar()){
            count += size(node.get(ch));
        }
        return count;
    }

    private static class ArrayEntry {
        private Object value;
        private ArrayEntry[] entries;
        private int edgesCount;

        private ArrayEntry(){
            this(null);
        }

        private ArrayEntry(Object value){
            this.value = value;
            this.entries = new ArrayEntry[alphabet.R()];
        }

        private ArrayEntry get(char ch){
            return entries[alphabet.toIndex(ch)];
        }

        private void put(char ch, ArrayEntry entry){
            entries[alphabet.toIndex(ch)] = entry;
        }

        private boolean contains(char ch){
            return Objects.nonNull(entries[alphabet.toIndex(ch)]);
        }

        private boolean isWord(){
            return value != null;
        }

        private void setWord(Object value){
            this.value = value;
        }
    }
}

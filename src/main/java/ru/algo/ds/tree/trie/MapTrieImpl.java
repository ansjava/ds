package ru.algo.ds.tree.trie;

import java.util.*;

public class MapTrieImpl<V> {
    private Alphabet alphabet;
    private MapEntry root;

    public MapTrieImpl(){
        this(Alphabet.ASCII);
    }

    public MapTrieImpl(String alphabet){
        this(new Alphabet(alphabet));
    }

    public MapTrieImpl(Alphabet alphabet) {
        this.alphabet = alphabet;
        this.root = new MapEntry();
    }

    public void put(String key, V value) {
        if(Objects.isNull(key) || key.isBlank()){
            return;
        }
        MapEntry current = root;
        for(char ch : key.toCharArray()){
            if(!current.containsKey(ch)){
                current.put(ch, new MapEntry());
            }
            current = current.get(ch);
        }
        current.setWord();
    }

    public Optional<V> get(String key) {
        MapEntry entry = getNodeByPrefix(key);
        if (Objects.isNull(entry))
            return Optional.empty();
        return entry.isWord() ? Optional.of((V)entry.value) : Optional.empty();
    }

    private MapEntry getNodeByPrefix(String prefix){
        if (Objects.isNull(prefix)){
            return null;
        }
        MapEntry current = root;
        for (char ch : prefix.toCharArray()){
            if (!current.containsKey(ch))
                return null;
            current = current.get(ch);
        }
        return current;
    }

    public boolean contains(String key) {
        if(Objects.isNull(key) || key.isBlank()){
            return false;
        }
        MapEntry current = root;
        for(char ch : key.toCharArray()){
            if(!current.containsKey(ch)){
                return false;
            }
            current = current.get(ch);
        }
        return current.isWord();
    }

    public Collection<String> keys(){
        return keysWithPrefix("");
    }

    public Collection<String> keysWithPrefix(String prefix){
        final Queue<String> queue = new LinkedList<>();
        collect(getNodeByPrefix(prefix), prefix, queue);
        return queue;
    }

    private void collect(MapEntry entry, String prefix, Queue<String> queue){
        if (Objects.isNull(entry))
            return;
        if (entry.isWord() /*&& Objects.nonNull(node.value)*/)
            queue.offer(prefix);
        for (char c : entry.entries.keySet()){
            collect(entry.get(c), prefix + c, queue);
        }
    }

    public boolean startsWith(String prefix) {
        if(prefix == null){
            return false;
        }
        if(prefix.isEmpty()){
            return true;
        }
        MapEntry current = root;
        for(char ch : prefix.toCharArray()){
            if(!current.containsKey(ch)){
                return false;
            }
            current = current.get(ch);
        }
        return true;
    }

    public List<String> getWordsStartingWith(String prefix) {
        MapEntry current = root;
        List<String> currentSuggestions = new ArrayList<>();

        for(char ch : prefix.toCharArray()){
            if(!current.containsKey(ch)){
                return currentSuggestions;
            }
            current = current.get(ch);
        }
        deepFirstSearch(current,prefix,currentSuggestions);
        return currentSuggestions;
    }

    private void deepFirstSearch(MapEntry current, String prefix, List<String> currentSuggestions) {
        if(currentSuggestions.size() == 3) return;
        if(current.isWord()) currentSuggestions.add(prefix);
        for(char ch = 'a'; ch <= 'z'; ch++){
            if(current.containsKey(ch)){
                deepFirstSearch(current.get(ch),prefix + ch, currentSuggestions);
            }
        }
    }

    private static class MapEntry{
        private UUID uuid = UUID.randomUUID();
        private Object value;
        private final Map<Character, MapEntry> entries;
        private boolean isWord;

        MapEntry(){
            this(null);
        }

        MapEntry(Object value){
            this.value = value;
            this.entries = new HashMap<>();
        }

        String getUUID(){
            return this.uuid.toString();
        }

        MapEntry get(char ch){
            return entries.get(ch);
        }

        void put(char ch, MapEntry entry){
            entries.put(ch,entry);
        }

        boolean containsKey(char ch){
            return entries.containsKey(ch);
        }

        boolean containsValue(char ch){
            return Objects.nonNull(entries.get(ch));
        }

        boolean isWord(){
            return isWord;
        }

        void setWord(){
            isWord = true;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            MapEntry mapEntry = (MapEntry) o;
            return Objects.equals(uuid, mapEntry.uuid);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(uuid);
        }
    }
}

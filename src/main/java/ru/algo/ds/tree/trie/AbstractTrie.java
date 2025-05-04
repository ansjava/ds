package ru.algo.ds.tree.trie;

import java.util.Optional;

public abstract class AbstractTrie<K,V> {
    protected final Alphabet alphabet;

    public AbstractTrie(){
        this(Alphabet.ASCII);
    }

    public AbstractTrie(String alphabet){
        this(new Alphabet(alphabet));
    }

    public AbstractTrie(Alphabet alphabet){
        this.alphabet = alphabet;
    }

    public abstract void put(K key, V value);

    public abstract Optional<V> get(K key);

    public abstract void delete(K key);

    public abstract boolean containsKey(K key);

}

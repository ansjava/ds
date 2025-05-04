package ru.algo.ds.tree.trie;

import java.util.*;

public class Alphabet {
    public static final Alphabet BINARY = new Alphabet("01");
    public static final Alphabet DNA = new Alphabet("ACTG");
    public static final Alphabet OCTAL = new Alphabet("01234567");
    public static final Alphabet DECIMAL = new Alphabet("0123456789");
    public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF");
    public static final Alphabet PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY");
    public static final Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");
    public static final Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public static final Alphabet BASE64 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
    public static final Alphabet ASCII = new Alphabet(128);
    public static final Alphabet EXTENDED_ASCII = new Alphabet(256);
    public static final Alphabet UNICODE16 = new Alphabet(65563);


    private final char[] alphabet;
    private final int[] indices;
    private final int radix;


    public Alphabet(){
        this(256);
    }

    public Alphabet(String src){
        boolean[] unicode = new boolean[Character.MAX_VALUE];
        for (char ch : src.toCharArray()){
            if (unicode[ch]) throw new IllegalStateException("Source string contains repeatable characters");
            unicode[ch] = true;
        }
        alphabet = src.toCharArray();
        radix = src.length();
        indices = new int[Character.MAX_VALUE];
        Arrays.fill(indices, -1);
        for (int i = 0; i < radix; i++) {
            indices[alphabet[i]] = i;
        }
    }

    public Alphabet(int radix){
        alphabet = new char[radix];
        indices = new int[radix];
        this.radix = radix;
        for (int i = 0; i < radix; i++) {
            alphabet[i] = (char)i;
            indices[i] = i;
        }
    }

    public char toChar(int index){
        if (index < 0 || index > radix)
            throw new RuntimeException("Alphabet out of bounds");
        return alphabet[index];
    }

    public char[] toChar(){
        return alphabet;
    }

    public int toIndex(char ch){
        if (ch >= indices.length || indices[ch] == -1) {
            throw new RuntimeException("Character " + ch + " not in alphabet");
        }
        return indices[ch];
    }

    public boolean contains(char ch){
        return indices[ch] != -1;
    }

    public int R(){
        return radix;
    }

    public int lgR(){
        int lgR = 0;
        for (int t = radix-1; t >= 1; t /= 2)
            lgR++;
        return lgR;
    }

    public int[] toIndices(String src){
        int[] indices = new int[src.length()];
        for (int i = 0; i < src.length(); i++) {
            indices[i] = toIndex(src.charAt(i));
        }
        return indices;
    }

    public String toChars(int[] indices){
        final StringBuilder sb = new StringBuilder(indices.length);
        for (int index : indices) {
            sb.append(toChar(index));
        }
        return sb.toString();
    }
}

package com.algorithm.datastructure;

/*
 * problem : https://leetcode.com/problems/design-add-and-search-words-data-structure
 */

public class Trie {

    /** Initialize your data structure here. */
    TrieNode root;
    public Trie() {
        root = new TrieNode();
    }
    
    /** Adds a word into the data structure. */
    public void addWord(String word) {
        addWord(root, word);
    }
    
    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        if(word == null || word.equals(""))return false;
        return search(root, word);
    }
    
    public boolean search(TrieNode root, String word){
        
        if(root == null)return false;
        if(word.equals("")){
            if(root.hasEnd == true)return true;
            return false;
        }
        
        
        String newWord = word.substring(1);
        char ch = word.charAt(0);
        if(ch == '.'){
            for(int i = 0; i < 26; ++i){
                if(root.children[i] != null){
                    boolean found = search(root.children[i], newWord);
                    if(found)return true;
                }
                
            }
            return false;
        }else return search(root.children[ch - 'a'], newWord);
    }
    
    public void addWord(TrieNode root, String word){
        for(char ch : word.toCharArray()){
            int id = ch - 'a';
            if(root.children[id] == null){
                root.children[id] = new TrieNode();
            }
            root = root.children[id];
        }
        root.hasEnd = true;
    }
    
    class TrieNode {
        TrieNode[] children;
        boolean hasEnd;
        TrieNode(){
            children = new TrieNode[26];
            hasEnd = false;
        }
    }
}

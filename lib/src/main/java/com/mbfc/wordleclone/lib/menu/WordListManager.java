package com.mbfc.wordleclone.lib.menu;

import com.mbfc.wordleclone.lib.parser.Parser;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager class for storing and loading word lists in memory.
 *
 * <p>This class is responsible for loading word lists from either classpath resources
 * or file system files using a provided parser and caching them in a map.
 *
 * @param <T> the type of the object representing each entry (e.g., String)
 */
public class WordListManager<T> {

    private final Map<String, List<T>> cachedWordLists;
    private final Parser<T> parser;

    /**
     * Constructs a new instance of WordListManager.
     *
     * @param parser the parser used to convert file contents into a list of objects of type T
     */
    public WordListManager(Parser<T> parser) {
        this.cachedWordLists = new HashMap<>();
        this.parser = parser;
    }

    /**
     * Returns the map of cached word lists.
     *
     * @return a map where the key is the word list name and the value is the list of objects of type T
     */
    public Map<String, List<T>> getCachedWordLists() {
        return cachedWordLists;
    }

    /**
     * Adds a new word list to the cache.
     *
     * @param name     the name under which the word list will be stored
     * @param wordList the list of objects of type T
     */
    public void addWordList(String name, List<T> wordList) {
        cachedWordLists.put(name, wordList);
    }

    /**
     * Loads a word list from a resource file (classpath).
     *
     * @param resourcePath the path to the resource file to load
     * @return a list of objects of type T loaded from the resource
     * @throws IOException if an I/O error occurs while reading the resource
     */
    public List<T> loadFromResource(String resourcePath) throws IOException {
        return parser.parseResource(resourcePath);
    }

    /**
     * Loads a word list from a file on the file system.
     *
     * @param filePath the path to the file to load
     * @return a list of objects of type T loaded from the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public List<T> loadFromFile(String filePath) throws IOException {
        return parser.parseFile(filePath);
    }
}

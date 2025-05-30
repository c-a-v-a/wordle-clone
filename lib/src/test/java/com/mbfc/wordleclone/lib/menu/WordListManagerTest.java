package com.mbfc.wordleclone.lib.menu;

import com.mbfc.wordleclone.lib.parser.Parser;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link WordListManager} class.
 */
public class WordListManagerTest {

    /**
     * Dummy parser implementation for testing, always returns predefined lists.
     */
    private static class DummyParser implements Parser<String> {
        @Override
        public List<String> parseResource(String resourcePath) throws IOException {
            return List.of("alpha", "beta", "gamma");
        }

        @Override
        public List<String> parseFile(String filePath) throws IOException {
            return List.of("delta", "epsilon");
        }
    }

    /**
     * Tests that loading a word list from a resource returns the expected list.
     */
    @Test
    public void testLoadFromResource() throws IOException {
        Parser<String> parser = new DummyParser();
        WordListManager<String> manager = new WordListManager<>(parser);
        List<String> wordList = manager.loadFromResource("dummy_resource.txt");
        Assertions.assertEquals(List.of("alpha", "beta", "gamma"), wordList);
    }

    /**
     * Tests that loading a word list from a file returns the expected list.
     */
    @Test
    public void testLoadFromFile() throws IOException {
        Parser<String> parser = new DummyParser();
        WordListManager<String> manager = new WordListManager<>(parser);
        List<String> wordList = manager.loadFromFile("dummy_file.txt");
        Assertions.assertEquals(List.of("delta", "epsilon"), wordList);
    }

    /**
     * Tests that adding a word list to the cache correctly stores the list.
     */
    @Test
    public void testAddWordListToCache() {
        Parser<String> parser = new DummyParser();
        WordListManager<String> manager = new WordListManager<>(parser);
        List<String> sampleList = List.of("one", "two", "three");
        manager.addWordList("testList", sampleList);
        Assertions.assertTrue(manager.getCachedWordLists().containsKey("testList"));
        Assertions.assertEquals(sampleList, manager.getCachedWordLists().get("testList"));
    }
}

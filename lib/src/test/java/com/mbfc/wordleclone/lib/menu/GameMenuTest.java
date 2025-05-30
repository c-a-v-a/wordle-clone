package com.mbfc.wordleclone.lib.menu;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import com.mbfc.wordleclone.lib.game.Game;
import com.mbfc.wordleclone.lib.game.GameBoard;
import com.mbfc.wordleclone.lib.game.GameException;
import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.game.GameFactory;
import com.mbfc.wordleclone.lib.parser.SimpleStringParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link GameMenu} class.
 */
public class GameMenuTest {

    /**
     * Dummy game implementation that immediately finishes the game.
     *
     * @param <T> the type used in the game
     */
    private static class DummyGame<T> extends Game<T, List<T>> {

        /**
         * Constructs a new instance of DummyGame.
         *
         * @param comparator the comparator used in the game
         * @param guessList  the list of valid words
         * @param tries      the maximum number of tries
         */
        public DummyGame(Comparator<T> comparator, List<T> guessList, int tries) {
            super(comparator, guessList, tries);
            // Immediately finish the game.
            this.gameFinished = true;
            this.playerWon = true;
        }

        @Override
        protected void validate(T guess) throws GameException {
            // No validation in dummy implementation.
        }

        @Override
        protected void selectRandomTarget() {
            this.target = guessList.get(0);
        }

        @Override
        public GameBoard<T> play(T guess) throws CompareException, GameException {
            return board;
        }
    }

    /**
     * Dummy game factory for tests that creates instances of DummyGame.
     *
     * @param <T> the type used in the game
     */
    private static class DummyGameFactory<T> implements GameFactory<T, List<T>> {
        @Override
        public Game<T, List<T>> createGame(Comparator<T> comparator, List<T> guessList, int maxTries)
                throws NoSuchElementException {
            return new DummyGame<>(comparator, guessList, maxTries);
        }
    }

    /**
     * Dummy parser implementation for testing that returns predetermined lists.
     */
    private static class DummyParser implements com.mbfc.wordleclone.lib.parser.Parser<String> {
        @Override
        public List<String> parseResource(String resourcePath) throws IOException {
            return List.of("resource1", "resource2");
        }

        @Override
        public List<String> parseFile(String filePath) throws IOException {
            return List.of("file1", "file2");
        }
    }

    /**
     * Tests that choosing the Exit option terminates the menu display.
     */
    @Test
    public void testDisplayMenuExitOption() {
        String input = "3\n"; // Option "3" is Exit.
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            WordListManager<String> manager = new WordListManager<>(new SimpleStringParser());
            Scanner scanner = new Scanner(System.in);
            GameMenu<String> menu = new GameMenu<>(manager, scanner, new StringComparator(), new DummyGameFactory<>());
            // When the menu exits, no further interaction occurs.
            menu.displayMenu();
        } finally {
            System.setIn(originalIn);
        }
    }

    /**
     * Tests that loading a word list from a file adds it to the cache.
     */
    @Test
    public void testLoadWordListOption() {
        // Simulate input: option 2 (load word list),
        // then file path "dummy.txt", word list name "testList", and then option 3 (exit).
        String input = "2\n"          // Choose "Load word list" option.
                + "dummy.txt\n"        // Enter file path.
                + "testList\n"         // Enter name for the word list.
                + "3\n";               // Exit the menu.
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            WordListManager<String> manager = new WordListManager<>(new DummyParser());
            Scanner scanner = new Scanner(System.in);
            GameMenu<String> menu = new GameMenu<>(manager, scanner, new StringComparator(), new DummyGameFactory<>());
            menu.displayMenu();
            // Verify that the word list "testList" has been added to the cache.
            Assertions.assertTrue(manager.getCachedWordLists().containsKey("testList"));
            Assertions.assertEquals(List.of("file1", "file2"), manager.getCachedWordLists().get("testList"));
        } finally {
            System.setIn(originalIn);
        }
    }
}

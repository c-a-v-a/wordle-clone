package com.mbfc.wordleclone.lib.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test suite for the {@link SimpleGame} class. */
public class SimpleGameTest {
    private StringComparator comparator;
    private List<String> emptyList;
    private List<String> validWordList;
    private int lives;

    @BeforeEach
    void setUp() {
        comparator = new StringComparator();
        emptyList = new ArrayList<>();
        validWordList = new ArrayList<>(Arrays.asList("abc", "def", "ghi"));
        lives = 6;
    }

    /** Test that comparing strings of different lengths throws an exception. */
    @Test
    void compare_differentLengths_throwsException() {
        String guess = "abc";
        String target = "a";
        String expectedMessage = "Cannot compare \"abc\" to \"a\". Strings have different length.";

        Exception exception = assertThrows(CompareException.class, () -> {
            comparator.compare(guess, target);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    /** Test that validate throws GameException when guess has an invalid length. */
    @Test
    void validate_invalidLength_throwsGameException() {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        Exception exception = assertThrows(GameException.class, () -> {
            game.validate("a");
        });
        assertEquals("Invalid guess. The length of guess and target does not match.", exception.getMessage());
    }

    /** Test that validate throws GameException when guess is not present in the word list. */
    @Test
    void validate_guessNotInTheList_throwsGameException() {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        Exception exception = assertThrows(GameException.class, () -> {
            game.validate("aaa");
        });
        assertEquals("Invalid guess. The guess is not in the word list.", exception.getMessage());
    }

    /** Test that constructing a game with an empty word list throws NoSuchElementException. */
    @Test
    void constructor_emptyList_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> {
            new SimpleGame(comparator, emptyList, lives);
        });
    }

    /** Test that the target is selected from the word list. */
    @Test
    void selectRandomTarget_targetIsInWordList() {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        String target = game.target;
        assertTrue(validWordList.contains(target));
    }

    /** Test that playing an empty string throws a GameException. */
    @Test
    void play_emptyString_throwsGameException() {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        Exception exception = assertThrows(GameException.class, () -> {
            game.play("");
        });
        assertEquals("Invalid guess. The length of guess and target does not match.", exception.getMessage());
    }

    /** Test that playing a guess not in the list throws a GameException. */
    @Test
    void play_guessNotInTheList_throwsGameException() {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        Exception exception = assertThrows(GameException.class, () -> {
            game.play("aaa");
        });
        assertEquals("Invalid guess. The guess is not in the word list.", exception.getMessage());
    }

    /** Test that playing a valid guess returns a non-empty game board. */
    @Test
    void play_validGuess_returnsNonEmptyGameBoard() throws CompareException, GameException {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        GameBoard board = game.play("abc");
        assertNotNull(board);
        assertTrue(!board.isEmpty());
        assertEquals(lives - 1, game.getTriesLeft());
    }

    /** Test that a correct guess results in a win and the game is finished. */
    @Test
    void play_correctGuess_gameFinishedAndPlayerWon() throws CompareException, GameException {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        game.play(game.target);
        assertTrue(game.getPlayerWon());
        assertTrue(game.getGameFinished());
    }

    /** Test that running out of lives leads to game finished and a loss. */
    @Test
    void play_runOutOfLives_gameFinishedAndPlayerLost() throws CompareException, GameException {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        game.target = "abc";
        game.triesLeft = 1;
        game.play("def");
        assertTrue(!game.getPlayerWon());
        assertTrue(game.getGameFinished());
    }

    /** Test that calling reset resets the game state properly. */
    @Test
    void reset_calledAfterPlay_resetsGameState() throws CompareException, GameException {
        SimpleGame game = new SimpleGame(comparator, validWordList, lives);
        game.target = "ddd"; // Force a specific target
        game.play("abc");
        int livesBeforeReset = game.getTriesLeft();
        game.reset();
        assertTrue(game.board.isEmpty());
        // After reset, triesLeft should equal maxTries
        assertEquals(game.maxTries, game.getTriesLeft());
        assertTrue(!"ddd".equals(game.target));
    }
}

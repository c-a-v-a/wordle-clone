package com.mbfc.wordleclone.lib.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the endless game mode based on the concrete {@code SimpleEndlessGame} class.
 *
 * <p>The tests verify that:
 *
 * <ul>
 *   <li>An incorrect guess decrements the number of attempts (without resetting the round) and
 *       leaves the score unchanged.
 *   <li>A correct guess resets the round (clearing the board and restoring attempts) and increments
 *       the score.
 *   <li>Exhausting all attempts (by entering only incorrect guesses) ends the game without
 *       incrementing the score.
 * </ul>
 *
 * <p>Note: For these tests the bonusTries parameter is set to 1, so that after a correct guess the
 * remaining attempts equal the initial number.
 */
public class SimpleEndlessGameTest {
  private StringComparator comparator;
  private List<String> wordList;
  private int initialTries;

  @BeforeEach
  void setUp() {
    comparator = new StringComparator();
    // Sample word list for testing
    wordList = new ArrayList<>(Arrays.asList("apple", "berry", "melon"));
    initialTries = 3;
  }

  /**
   * Tests that an incorrect guess does not reset the round, decrements the attempts by one, and
   * leaves the score unchanged.
   */
  @Test
  void play_incorrectGuess_doesNotResetRound()
      throws CompareException, GameException, NoSuchElementException {
    // Given: a SimpleEndlessGame instance with target set to "apple" and bonusTries = 1.
    SimpleEndlessGame game = new SimpleEndlessGame(comparator, wordList, initialTries, 1);
    game.target = "apple"; // setting a known target for test reproducibility

    // When: an incorrect guess "berry" is played.
    game.play("berry");

    // Then: the board should not be reset (i.e. not empty),
    // and the remaining attempts (lives) should equal initialTries - 1.
    // The score remains 0.
    assertFalse(game.getBoard().isEmpty());
    assertEquals(initialTries - 1, Integer.parseInt(game.getTriesLeft()));
    assertEquals(0, game.getScore());
  }

  /**
   * Tests that a correct guess resets the round, restores the number of attempts, and increments
   * the score.
   */
  @Test
  void play_correctGuess_resetsRoundAndIncrementsScore()
      throws CompareException, GameException, NoSuchElementException {
    // Given: a SimpleEndlessGame instance with target set to "apple" and bonusTries = 1.
    SimpleEndlessGame game = new SimpleEndlessGame(comparator, wordList, initialTries, 1);
    game.target = "apple";

    // When: a correct guess "apple" is played.
    game.play("apple");
    game.commitRound();

    // Then: the board should be reset (empty), the remaining attempts restored to initialTries,
    // and the score incremented by 1.
    assertTrue(game.getBoard().isEmpty());
    assertEquals(initialTries, Integer.parseInt(game.getTriesLeft()));
    assertEquals(1, game.getScore());
  }

  /**
   * Tests that exhausting all attempts with incorrect guesses ends the game without incrementing
   * the score.
   */
  @Test
  void play_exhaustsAttempts_endsGameWithoutIncrementingScore()
      throws CompareException, GameException, NoSuchElementException {
    // Given: a SimpleEndlessGame instance with target set to "apple" and bonusTries = 1.
    SimpleEndlessGame game = new SimpleEndlessGame(comparator, wordList, initialTries, 1);
    game.target = "apple";

    // When: playing incorrect guesses to exhaust all attempts.
    game.play("berry"); // attempt 1: remaining tries from 3 to 2
    game.play("melon"); // attempt 2: remaining tries from 2 to 1
    game.play("berry"); // attempt 3: remaining tries from 1 to 0 --> game should end

    // Then: the game is finished, the remaining tries are 0, and score remains 0.
    assertTrue(game.getGameFinished());
    assertEquals(0, Integer.parseInt(game.getTriesLeft()));
    assertEquals(0, game.getScore());
  }
}

package com.mbfc.wordleclone.lib.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the endless randomly generated game mode based on the concrete {@code
 * EndlessRandomGame} class.
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
public class EndlessRandomGameTest {
  private StringComparator comparator;
  private int initialTries;
  private int bonusTries;
  private int length;

  @BeforeEach
  void setUp() {
    comparator = new StringComparator();
    initialTries = 3;
    bonusTries = 1;
    length = 5;
  }

  /**
   * Tests that an incorrect guess does not reset the round, decrements the attempts by one, and
   * leaves the score unchanged.
   */
  @Test
  void play_incorrectGuess_doesNotResetRound()
      throws CompareException, GameException, NoSuchElementException {
    // Given: an EndlessRandomGame instance with target set to "apple" and bonusTries = 1.
    EndlessRandomGame game = new EndlessRandomGame(comparator, initialTries, bonusTries, length);
    game.target = "apple"; // ustawienie znanego celu dla powtarzalności testu

    // When: an incorrect guess "berry" is played.
    game.play("berry");

    // Then: the board should not be reset (i.e. not empty),
    // the remaining attempts should equal initialTries - 1, and the score remains 0.
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
    // Given: an EndlessRandomGame instance with target set to "apple" and bonusTries = 1.
    EndlessRandomGame game = new EndlessRandomGame(comparator, initialTries, bonusTries, length);
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
    // Given: an EndlessRandomGame instance with target set to "apple" and bonusTries = 1.
    EndlessRandomGame game = new EndlessRandomGame(comparator, initialTries, bonusTries, length);
    game.target = "apple";

    // When: playing incorrect guesses to exhaust all attempts.
    game.play("berry"); // attempt 1: remaining tries from 3 to 2
    game.play("melon"); // attempt 2: remaining tries from 2 to 1
    game.play("berry"); // attempt 3: remaining tries from 1 to 0 --> game should end

    // Then: the game is finished, the remaining attempts are 0, and the score remains 0.
    assertTrue(game.getGameFinished());
    assertEquals(0, Integer.parseInt(game.getTriesLeft()));
    assertEquals(0, game.getScore());
  }
}

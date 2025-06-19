package com.mbfc.wordleclone.lib.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EndlessGameTest {
  private StringComparator comparator;
  private List<String> wordList;
  private int maxTries;

  @BeforeEach
  void setUp() {
    comparator = new StringComparator();
    wordList = new ArrayList<>(Arrays.asList("apple", "berry", "melon"));
    maxTries = 3;
  }

  /**
   * Tests that an incorrect guess does not reset the round, decrements round attempts by one, and
   * leaves the score unchanged
   */
  @Test
  void play_incorrectGuess_doesNotResetRound() throws CompareException, GameException {
    // given
    EndlessGame game = new EndlessGame(comparator, wordList, maxTries);
    game.target = "apple";

    // when
    game.play("berry");

    // then
    assertFalse(game.getBoard().isEmpty());
    assertEquals(maxTries - 1, game.getTriesLeftInRound());
    assertEquals(0, game.getScore());
  }

  /**
   * Tests that a correct guess resets the round, restores the number of round attempts to maxTries,
   * and increments the score.
   */
  @Test
  void play_correctGuess_resetsRoundAndIncrementsScore() throws CompareException, GameException {
    // given
    EndlessGame game = new EndlessGame(comparator, wordList, maxTries);
    game.target = "apple";

    // when
    game.play("apple");

    // then
    assertTrue(game.getBoard().isEmpty());
    assertEquals(maxTries, game.getTriesLeftInRound());
    assertEquals(1, game.getScore());
  }

  /**
   * Tests that exhausting the round attempts with incorrect guesses resets the round without
   * incrementing the score.
   */
  @Test
  void play_exhaustAttempts_resetsRoundWithoutIncrementingScore()
      throws CompareException, GameException {
    // given
    EndlessGame game = new EndlessGame(comparator, wordList, maxTries);
    game.target = "apple";

    // when
    game.play("berry"); // attempt 1
    game.play("melon"); // attempt 2
    game.play("berry"); // attempt 3, triggers round reset

    // then
    assertTrue(game.getBoard().isEmpty());
    assertEquals(maxTries, game.getTriesLeftInRound());
    assertEquals(0, game.getScore());
  }
}

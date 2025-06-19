package com.mbfc.wordleclone.lib.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test suite for the {@link RandomGame} class. */
public class RandomGameTest {
  private StringComparator comparator;
  private int lives;
  private int length;

  @BeforeEach
  void setUp() {
    comparator = new StringComparator();
    lives = 6;
    length = 5;
  }

  /** Test that validate throws GameException when guess has an invalid length. */
  @Test
  void validate_invalidLength_throwsGameException() {
    // given
    RandomGame game = new RandomGame(comparator, lives, length);

    // when
    Exception exception = assertThrows(GameException.class, () -> game.validate("a"));

    // then
    assertEquals(
        "Invalid guess. The length of guess and target does not match.", exception.getMessage());
  }

  /** Test that validate throws GameException when guess is not present in the word list. */
  @Test
  void validate_guessNotInTheLettersSet_throwsGameException() {
    // given
    RandomGame game = new RandomGame(comparator, lives, length);

    // when
    Exception exception = assertThrows(GameException.class, () -> game.validate("asdf%"));

    // then
    assertEquals(
        "Invalid guess. The guess is composed out of characters outside the letters list.",
        exception.getMessage());
  }

  /** Test that playing a valid guess returns a non-empty game board. */
  @Test
  void play_validGuess_returnsNonEmptyGameBoard() throws CompareException, GameException {
    // given
    RandomGame game = new RandomGame(comparator, lives, length);

    // when
    game.play("abcde");
    GameBoard<String> board = game.getBoard();

    // then
    assertNotNull(board);
    assertFalse(board.isEmpty());
    assertEquals(Integer.toString(lives - 1), game.getTriesLeft());
  }

  /** Test that a correct guess results in a win and the game is finished. */
  @Test
  void play_correctGuess_gameFinishedAndPlayerWon() throws CompareException, GameException {
    // given
    RandomGame game = new RandomGame(comparator, lives, length);

    // when
    game.play(game.target);

    // then
    assertTrue(game.getPlayerWon());
    assertTrue(game.getGameFinished());
  }

  /** Test that running out of lives leads to game finished and a loss. */
  @Test
  void play_runOutOfLives_gameFinishedAndPlayerLost() throws CompareException, GameException {
    // given
    RandomGame game = new RandomGame(comparator, lives, length);
    game.target = "abc";

    // when
    game.triesUsed = lives - 1;
    game.play("def");

    // then
    assertFalse(game.getPlayerWon());
    assertTrue(game.getGameFinished());
  }

  /** Test that calling reset resets the game state properly. */
  @Test
  void reset_calledAfterPlay_resetsGameState() throws CompareException, GameException {
    // given
    RandomGame game = new RandomGame(comparator, lives, length);
    game.target = "ddd";

    // when
    game.play("abc");
    game.reset();

    // then
    assertTrue(game.board.isEmpty());

    // After reset, triesUsed should equal 0
    assertEquals(0, game.getTriesUsed());
    assertNotEquals("ddd", game.target);
  }
}

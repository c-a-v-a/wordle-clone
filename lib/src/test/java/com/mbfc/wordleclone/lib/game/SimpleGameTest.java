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
  private List<String> simpleList;
  private int tries;

  @BeforeEach
  void setUp() {
    comparator = new StringComparator();
    emptyList = new ArrayList<String>();
    simpleList = new ArrayList<String>(Arrays.asList("abc", "def", "ghi"));
    tries = 6;
  }

  /** Test that guess of invalid length does throw the exception. */
  @Test
  void validate_invalidLenght_throwGameException() throws GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);

    // when
    Exception exception =
        assertThrows(
            GameException.class,
            () -> {
              game.validate("a");
            });

    // then
    assertEquals(
        "Invalid guess. The length of guess and target does not match.", exception.getMessage());
  }

  /** Test that guess not in the list does throw the exception. */
  @Test
  void validate_guessNotInTheList_throwGameException() throws GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);

    // when
    Exception exception =
        assertThrows(
            GameException.class,
            () -> {
              game.validate("aaa");
            });

    // then
    assertEquals("Invalid guess. The guess is not in the word list.", exception.getMessage());
  }

  /** Test that game cannot be created with empty guess list. */
  @Test
  void constructor_emptyList_throwNoSuchElementException() throws NoSuchElementException {
    // given
    Exception exception =
        assertThrows(
            NoSuchElementException.class,
            () -> {
              new SimpleGame(comparator, emptyList, tries);
            });

    // when

    // then
    assertNotNull(exception);
  }

  /** Test that target is selected randomly from the guess list. */
  @Test
  void selectRandomTarget_simpleList_targetInList() throws NoSuchElementException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);

    // when
    String target = game.target;

    // then
    assertTrue(simpleList.contains(target));
  }

  /** Test that playing empty string is not valid. */
  @Test
  void play_emptyString_throwGameException() throws CompareException, GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);

    // when
    Exception exception =
        assertThrows(
            GameException.class,
            () -> {
              game.play("");
            });

    // then
    assertEquals(
        "Invalid guess. The length of guess and target does not match.", exception.getMessage());
  }

  /** Test that playing string not in the guess list is not valid. */
  @Test
  void play_guessNotInTheList_throwGameException() throws CompareException, GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);

    // when
    Exception exception =
        assertThrows(
            GameException.class,
            () -> {
              game.play("aaa");
            });

    // then
    assertEquals("Invalid guess. The guess is not in the word list.", exception.getMessage());
  }

  /** Test that playing string from guess list is valid. */
  @Test
  void play_guessInTheList_validGameBoard() throws CompareException, GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);

    // when
    GameBoard board = game.play("abc");

    // then
    assertNotNull(board);
    assertTrue(!board.isEmpty());
    assertEquals(tries - 1, game.triesLeft);
  }

  /** Test that player can win and his win ends the game. */
  @Test
  void play_targetGuessed_gameFinishedAndPlayerWon() throws CompareException, GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);

    // when
    GameBoard board = game.play(game.target);

    // then
    assertTrue(game.getPlayerWon());
    assertTrue(game.getGameFinished());
  }

  /** Test that player can loose. */
  @Test
  void play_runOutOfTries_gameFinishedAndPlayerLoose() throws CompareException, GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);
    game.target = "abc";
    game.triesLeft = 1;

    // when
    GameBoard board = game.play("def");

    // then
    assertTrue(!game.getPlayerWon());
    assertTrue(game.getGameFinished());
  }

  /** Test if the reset is resetting the game. */
  @Test
  void reset_playedGame_cleanGame() throws CompareException, GameException {
    // given
    SimpleGame game = new SimpleGame(comparator, simpleList, tries);
    game.target = "ddd";

    game.play("abc");

    int triesLeft = game.triesLeft;

    // when
    game.reset();

    // then
    assertTrue(game.board.isEmpty());
    assertEquals(triesLeft + 1, game.triesLeft);
    assertEquals(tries, game.triesLeft);
    assertEquals(game.maxTries, game.triesLeft);
    assertTrue(!game.target.equals("ddd"));
  }
}

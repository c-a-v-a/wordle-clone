package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.json.Field;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * An implementation of the {@link Game} class using strings.
 *
 * <p>This class implements a logic for a endless object wordle game (like LoLdle without limited
 * guesses). Player can guess the word until he guesses it, ergo there are no limited tries.
 */
public class ZenObjectGame extends SimpleObjectGame {
  private final String key;

  /**
   * Creates a new game instance, using {@link Game} constructor.
   *
   * @param comparator comparator that determines correctness of the guess
   * @param guessList the list of valid guesses
   * @param key the key of the value that player tries to guess
   */
  public ZenObjectGame(
      Comparator<TreeMap<String, Field>> comparator,
      List<TreeMap<String, Field>> guessList,
      String key)
      throws NoSuchElementException {
    super(comparator, guessList, 0, key);

    this.key = key;
  }

  @Override
  public String getTriesLeft() {
    return "Infinite (feel the /Z E N/)";
  }

  /**
   * Checks if the game is finished and if player has won.
   *
   * <p>This function just sets the according flags, {@link Game#playerWon} and {@link
   * Game#gameFinished}.
   *
   * <p>Since this game mode doesn't have limited tries, we only check if player has won the game.
   *
   * @param result results of the last user's guess
   */
  @Override
  public void isGameFinished(List<ComparatorResult> result) {
    playerWon = result.stream().allMatch(x -> x.equals(ComparatorResult.CORRECT));
    gameFinished = playerWon;
  }
}

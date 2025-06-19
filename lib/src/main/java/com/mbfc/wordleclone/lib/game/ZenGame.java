package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link Game} class using strings.
 *
 * <p>This class implements a logic for a endless wordle game. Each guess is a {@code String} and
 * the guess list is just {@code List<String>}. Player can guess the word until he guesses it, ergo
 * there are no limited tries.
 */
public class ZenGame extends SimpleGame {
  /**
   * Creates a new game instance, using {@link Game} constructor.
   *
   * @param comparator {@code String} comparator that determines correctness of the guess
   * @param guessList the {@code List<String>} of valid guesses
   */
  public ZenGame(Comparator<String> comparator, List<String> guessList)
      throws NoSuchElementException {
    super(comparator, guessList, 0);
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

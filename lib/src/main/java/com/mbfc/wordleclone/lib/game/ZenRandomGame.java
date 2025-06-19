package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link Game} class using strings.
 *
 * <p>This class implements a logic for a endless, randomized wordle game. Each guess is a {@code
 * String} and the guess list doesn't exist, so players can guess whatever they want as long as the
 * length is the same as target and there are no numbers or symbols in the player's guess. Player
 * can guess the word until they guess it, ergo there are no limited tries.
 */
public class ZenRandomGame extends RandomGame {
  /**
   * Creates a new game instance, using {@link Game} constructor.
   *
   * @param comparator {@code String} comparator that determines correctness of the guess
   * @param length the desired length of the target word
   */
  public ZenRandomGame(Comparator<String> comparator, int length) throws NoSuchElementException {
    super(comparator, 0, length);
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

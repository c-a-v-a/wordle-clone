package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * An implementation of the {@link Game} class using strings.
 *
 * <p>This class implements a logic for a endless wordle game. Each guess is a {@code String} and
 * the guess list is just {@code List<String>}. Player can guess the word until he guesses it, ergo
 * there are no limited tries.
 */
public class ZenGame extends Game<String, List<String>> {
  /**
   * Creates a new game instance, using {@link Game} constructor.
   *
   * @param comparator {@code String} comparator that determines correctness of the guess
   * @param guessList the {@code List<String>} of valid guesses
   * @param tries the maximum number of guesses that user can make
   */
  public ZenGame(Comparator<String> comparator, List<String> guessList)
      throws NoSuchElementException {
    super(String.class, comparator, guessList, 0);
  }

  /** {@inheritDoc} */
  @Override
  protected void validate(String guess) throws GameException {
    if (target.length() != guess.length()) {
      throw new GameException("Invalid guess. The length of guess and target does not match.");
    } else if (!guessList.contains(guess)) {
      throw new GameException("Invalid guess. The guess is not in the word list.");
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void selectRandomTarget() throws NoSuchElementException {
    if (guessList.isEmpty()) {
      throw new NoSuchElementException("Cannot select random element. The list is empty.");
    }

    Random rand = new Random();
    target = guessList.get(rand.nextInt(guessList.size()));
  }

  /** {@inheritDoc} */
  @Override
  protected String convertGuess(String guess) throws GameException {
    return guess;
  }

  @Override
  public String getTarget() {
    return target;
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

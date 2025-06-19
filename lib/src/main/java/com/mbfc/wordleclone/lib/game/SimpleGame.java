package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * An implementation of the {@link Game} class using strings.
 *
 * <p>This class implements a logic for a standard wordle game. Each guess is a {@code String} and
 * the guess list is just {@code List<String>}. It follows the standard rules of wordle.
 */
public class SimpleGame extends Game<String, List<String>> {
  /**
   * Creates a new game instance, using {@link Game} constructor.
   *
   * @param comparator {@code String} comparator that determines correctness of the guess
   * @param guessList the {@code List<String>} of valid guesses
   * @param tries the maximum number of guesses that user can make
   */
  public SimpleGame(Comparator<String> comparator, List<String> guessList, int tries)
      throws NoSuchElementException {
    super(String.class, comparator, guessList, tries);
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

  /** {@inheritDoc} */
  @Override
  public String getFinalGameMessage() {
    if (getPlayerWon()) {
      return "\nCongratulations. You won in " + getTriesUsed() + " guesses.";
    } else {
      return "\nYou lost. The target was: " + getTarget();
    }
  }

  @Override
  public String getTarget() {
    return target;
  }
}

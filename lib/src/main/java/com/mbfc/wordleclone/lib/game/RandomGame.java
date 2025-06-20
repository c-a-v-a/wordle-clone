package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.util.RandomGen;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An implementation of the {@link Game} class using random strings.
 *
 * <p>This class implements a logic for a randomized wordle game. Each guess is a generated {@code
 * String} and the guess list doesn't exist, so players can guess whatever they want as long as the
 * length is the same as target and there are no numbers or symbols in the player's guess.
 */
public class RandomGame extends Game<String, List<String>> {
  private final String letters = RandomGen.getLetters();
  private int length = 0;

  /**
   * Creates a new game instance, using {@link Game} constructor.
   *
   * @param comparator {@code String} comparator that determines correctness of the guess
   * @param tries the maximum number of guesses that user can make
   * @param length the desired length of the target word
   */
  public RandomGame(Comparator<String> comparator, int tries, int length)
      throws NoSuchElementException {
    super(String.class, comparator, null, tries);

    this.length = length;

    selectRandomTarget();
  }

  /** {@inheritDoc} */
  @Override
  protected void validate(String guess) throws GameException {
    if (target.length() != guess.length()) {
      throw new GameException("Invalid guess. The length of guess and target does not match.");
    }

    for (int i = 0; i < guess.length(); i++) {
      if (letters.indexOf(guess.charAt(i)) == -1) {
        throw new GameException(
            "Invalid guess. The guess is composed out of characters outside the letters list.");
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void selectRandomTarget() throws NoSuchElementException {
    target = RandomGen.generate(length);
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

  /** {@inheritDoc} */
  @Override
  public String getFinalGameMessage() {
    if (getPlayerWon()) {
      return "\nCongratulations. You won in " + getTriesUsed() + " guesses.";
    } else {
      return "\nYou lost. The target was: " + getTarget();
    }
  }
}

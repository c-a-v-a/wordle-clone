package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Concrete class implementing endless mode for a word game. Inherits all common endless game logic
 * from {@link EndlessGame} and provides implementations specific to operations on Strings.
 */
public class SimpleEndlessGame extends EndlessGame<String, List<String>> {

  /**
   * Creates a new instance of SimpleEndlessGame.
   *
   * @param comparator the String comparator that determines the correctness of a guess
   * @param guessList the list of valid words for guesses
   * @param initialTries the initial number of tries (lives)
   * @param bonusTriesOnWin the bonus lives awarded when a round is won
   * @throws NoSuchElementException if the guess list is empty
   */
  public SimpleEndlessGame(
      Comparator<String> comparator, List<String> guessList, int initialTries, int bonusTriesOnWin)
      throws NoSuchElementException {
    super(String.class, comparator, guessList, initialTries, bonusTriesOnWin);
  }

  /**
   * Validates the player's guess. The guess is considered invalid if its length does not match the
   * target or if it is not present in the guess list.
   *
   * @param guess the player's guess to validate
   * @throws GameException if the guess is invalid
   */
  @Override
  protected void validate(String guess) throws GameException {
    if (target.length() != guess.length()) {
      throw new GameException("Invalid guess. The length of guess and target does not match.");
    } else if (!guessList.contains(guess)) {
      throw new GameException("Invalid guess. The guess is not in the word list.");
    }
  }

  /**
   * Selects a random target from the guess list.
   *
   * @throws NoSuchElementException if the guess list is empty
   */
  @Override
  protected void selectRandomTarget() throws NoSuchElementException {
    if (guessList.isEmpty()) {
      throw new NoSuchElementException("Cannot select random element. The list is empty.");
    }
    Random rand = new Random();
    target = guessList.get(rand.nextInt(guessList.size()));
  }

  /**
   * Converts the guess to the appropriate type. For a String-based game, no conversion is needed.
   *
   * @param guess the player's guess string
   * @return the converted guess (in this case, unchanged)
   * @throws GameException should a conversion error occur (not applicable here)
   */
  @Override
  protected String convertGuess(String guess) throws GameException {
    return guess;
  }

  /**
   * Returns the current target word.
   *
   * @return the target word
   */
  @Override
  public String getTarget() {
    return target;
  }
}

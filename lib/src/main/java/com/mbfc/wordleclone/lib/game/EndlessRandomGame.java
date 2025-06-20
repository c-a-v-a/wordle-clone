package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.util.RandomGen;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Concrete class implementing endless mode for a randomized word game. It combines the mechanics of
 * {@link EndlessGame} with the word generation logic of {@link RandomGame}. The player guesses
 * randomly generated words, earning points and bonus lives for each correct guess, continuing until
 * they run out of lives.
 */
public class EndlessRandomGame extends EndlessGame<String, List<String>> {

  private final String letters = RandomGen.getLetters();
  private final int length;

  /**
   * Creates a new instance of EndlessRandomGame.
   *
   * @param comparator the String comparator that determines the correctness of a guess
   * @param initialTries the initial number of tries (lives)
   * @param bonusTriesOnWin the bonus lives awarded when a round is won
   * @param length the desired length of the randomly generated target word
   * @throws NoSuchElementException if there's an issue during initialization (though unlikely here)
   */
  public EndlessRandomGame(
      Comparator<String> comparator, int initialTries, int bonusTriesOnWin, int length)
      throws NoSuchElementException {
    super(String.class, comparator, null, initialTries, bonusTriesOnWin);
    this.length = length;
    selectRandomTarget(); // Select the first target word
  }

  /**
   * Validates the player's guess. The guess is considered invalid if its length does not match the
   * target or if it contains characters outside the allowed alphabet.
   *
   * @param guess the player's guess to validate
   * @throws GameException if the guess is invalid
   */
  @Override
  protected void validate(String guess) throws GameException {
    if (target.length() != guess.length()) {
      throw new GameException("Invalid guess. The length of guess and target does not match.");
    }

    for (int i = 0; i < guess.length(); i++) {
      if (letters.indexOf(Character.toLowerCase(guess.charAt(i))) == -1) {
        throw new GameException(
            "Invalid guess. The guess contains characters outside the allowed letters.");
      }
    }
  }

  /** Selects a new random target word by generating a random string of the configured length. */
  @Override
  protected void selectRandomTarget() throws NoSuchElementException {
    target = RandomGen.generate(length);
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

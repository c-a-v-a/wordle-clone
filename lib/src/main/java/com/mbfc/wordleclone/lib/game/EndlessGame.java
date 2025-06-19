package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.comparator.CompareException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * EndlessGame – implementation of endless mode where maxTries indicates the limit of attempts per
 * round (per word). When attempts are exhausted the word is changed.
 */
public class EndlessGame extends SimpleGame {
  private int score; // number of solved words
  private int currentRoundTries; // number of attempts in the current round

  /**
   * [Constructor Heading] Initializes the EndlessGame with a comparator, guess list, and maximum
   * attempts per round.
   *
   * @param comparator the word comparator
   * @param guessList the list of valid words
   * @param maxTries number of attempts allowed per round
   * @throws NoSuchElementException if the guess list is empty
   */
  public EndlessGame(Comparator<String> comparator, List<String> guessList, int maxTries)
      throws NoSuchElementException {
    super(comparator, guessList, maxTries);
    this.score = 0;
    this.currentRoundTries = 0;
  }

  /**
   * [Play Method Heading] Processes a guess by incrementing the round attempt counter; if the word
   * is guessed or maximum attempts are reached, the round resets accordingly.
   *
   * <p>given a guess, when play(...) is called, then if the guess is correct the score increments
   * and the round resets; if maximum attempts are exhausted, the round resets without incrementing
   * the score.
   */
  @Override
  public void play(String guess) throws CompareException, GameException {
    super.play(
        guess); // executes validation, comparison, adding to board, and global attempt increment
    currentRoundTries++;

    if (getPlayerWon()) {
      score++;
      System.out.println(
          "Congratulations! You solved the word in "
              + currentRoundTries
              + " attempts. Current score: "
              + score);
      resetRound();
    } else if (currentRoundTries >= maxTries) {
      System.out.println(
          "You've used all " + maxTries + " attempts. The correct word was: " + getTarget());
      resetRound();
    }
  }

  /**
   * [Reset Round Heading] Resets the round by clearing the board, resetting counters, and selecting
   * a new target.
   *
   * <p>given a reset command, when resetRound() is invoked, then the board and round attempt
   * counter are reinitialized.
   */
  private void resetRound() {
    reset(); // resets board, global counters, playerWon, gameFinished, and selects a new target
    currentRoundTries = 0;
  }

  /**
   * [isGameFinished Heading] Determines round completion only when the word is correctly guessed.
   *
   * <p>given a list of comparison results, when isGameFinished() is called, then it sets playerWon
   * to true only if every character is correct.
   */
  @Override
  public void isGameFinished(List<ComparatorResult> result) {
    playerWon = result.stream().allMatch(x -> x.equals(ComparatorResult.CORRECT));
    gameFinished = playerWon;
  }

  /**
   * [getGameFinished Heading] Returns false to ensure the main loop continues indefinitely in
   * endless mode.
   *
   * <p>given endless mode, when getGameFinished() is invoked, then it always returns false.
   */
  @Override
  public boolean getGameFinished() {
    return false;
  }

  /**
   * [getScore Heading] Returns the current score.
   *
   * <p>given the current game state, when getScore() is called, then it returns the total count of
   * solved words.
   */
  public int getScore() {
    return score;
  }

  /**
   * [getTriesLeftInRound Heading] Returns the number of attempts remaining in the current round.
   *
   * <p>given maxTries and currentRoundTries, when getTriesLeftInRound() is called, then it returns
   * (maxTries - currentRoundTries).
   */
  public int getTriesLeftInRound() {
    return maxTries - currentRoundTries;
  }
}

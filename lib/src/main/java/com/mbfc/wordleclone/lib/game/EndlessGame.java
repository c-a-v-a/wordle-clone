package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.comparator.CompareException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Abstract class defining the common structure and logic for games in endless mode. It manages the
 * score, lives, and bonus lives.
 *
 * @param <T> the type of game elements (e.g., String)
 * @param <U> the type of collection from which valid guesses are drawn (e.g., {@code List<String>})
 */
public abstract class EndlessGame<T, U> extends Game<T, U> {

  protected int score;
  protected String lastRoundResult;
  protected int lives;
  protected boolean roundComplete = false;
  protected int attemptsTaken;
  protected final int bonusTriesOnWin;

  /**
   * Creates a new endless mode game instance.
   *
   * @param type the type of game elements
   * @param comparator the comparator that determines the correctness of a guess
   * @param guessList the collection of valid guesses
   * @param initialTries the initial number of lives (tries)
   * @param bonusTriesOnWin the bonus lives awarded when a round is won
   * @throws NoSuchElementException if the guess list is empty
   */
  public EndlessGame(
      Class<T> type, Comparator<T> comparator, U guessList, int initialTries, int bonusTriesOnWin)
      throws NoSuchElementException {
    super(type, comparator, guessList, initialTries); // initial tries serve as the starting pool
    this.score = 0;
    this.lastRoundResult = null;
    this.lives = initialTries;
    this.bonusTriesOnWin = bonusTriesOnWin;
    this.gameFinished = false;
  }

  /**
   * Main game loop for endless mode. This method is final to ensure consistent behavior across
   * subclasses.
   *
   * @param guess the player's guess in string format
   * @throws CompareException if an error occurs during comparison
   * @throws GameException if the guess is invalid
   */
  @Override
  public final void play(String guess) throws CompareException, GameException {
    T convertedGuess = convertGuess(guess);
    validate(convertedGuess);

    lives--;
    triesUsed++;

    List<ComparatorResult> result = comparator.compare(convertedGuess, target);
    board.add(result, convertedGuess);

    boolean roundWon = result.stream().allMatch(x -> x.equals(ComparatorResult.CORRECT));

    if (roundWon) {
      attemptsTaken = triesUsed;
      roundComplete = true;
    } else if (lives <= 0) {
      this.gameFinished = true;
      this.playerWon = false;
    }
  }

  /** Commits the finished round and adds score for user. */
  public final void commitRound() {
    if (roundComplete) {
      score++;
      lives += bonusTriesOnWin;
      roundComplete = false;
      resetRound();
    }
  }

  /**
   * Resets the state of the current round to start a new one. This method is final to ensure
   * consistent behavior.
   */
  protected final void resetRound() {
    this.board = new GameBoard<>(getBoard().getType()); // Using the type from the existing board
    this.triesUsed = 0;
    selectRandomTarget(); // Abstract method implemented by the subclass
  }

  /**
   * Checks if the game has finished.
   *
   * @return {@code true} if the game is over, {@code false} otherwise
   */
  @Override
  public final boolean getGameFinished() {
    return gameFinished;
  }

  /**
   * Checks if the current round is complete.
   *
   * @return {@code true} if the round is complete, {@code false} otherwise
   */
  public final boolean isRoundComplete() {
    return roundComplete;
  }

  /**
   * Returns the final game message to be shown when the game is over.
   *
   * @return the final game message including the final score
   */
  @Override
  public final String getFinalGameMessage() {
    return "\nGame over! You've run out of lives.\nFinal score: " + getScore() + " words guessed.";
  }

  /**
   * Retrieves and clears the result message of the last round.
   *
   * @return the last round result message, or {@code null} if there is none
   */
  public final String consumeLastRoundResult() {
    String result = this.lastRoundResult;
    this.lastRoundResult = null;
    return result;
  }

  /**
   * Returns the current score.
   *
   * @return the score
   */
  public int getScore() {
    return score;
  }

  /**
   * Returns the remaining lives.
   *
   * @return the number of lives left
   */
  public int getLives() {
    return lives;
  }

  /**
   * Returns the number of attempts taken in the current round.
   *
   * @return the number of attempts taken
   */
  public final int getAttemptsTaken() {
    return attemptsTaken;
  }

  /**
   * Returns the number of guesses user gains after win.
   *
   * @return the number of guesses after win
   */
  public final int getBonusTriesOnWin() {
    return bonusTriesOnWin;
  }
}

package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.comparator.CompareException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An abstract base class representing a generic game framework.
 *
 * <p>This clas provides the structure and api, for every possible game type.
 *
 * @param <T> the type of values used in game
 * @param <U> the type of guess list, should generally be a collection of {@code T} type elements
 */
public abstract class Game<T, U> {

  /**
   * Comparator used to compare guess against target.
   *
   * @see Comparator
   */
  protected final Comparator<T> comparator;

  /** List of valid guesses. */
  protected final U guessList;

  private final Class<T> type;

  /**
   * The value that needs to be guessed.
   *
   * <p>It's selected randomly from the @{link Game#guessList}.
   *
   * @see Game#selectRandomTarget
   */
  protected T target;

  /**
   * Represents the state of the board.
   *
   * @see GameBoard
   */
  protected GameBoard<T> board;

  /** Maximum number of tries that user has to guess the value. */
  protected final int maxTries;

  /** Number of tries that user has used. */
  protected int triesUsed;

  /** Flag that tells if game is finished. */
  protected boolean gameFinished;

  /** Flag that tells if the player has won the game. */
  protected boolean playerWon;

  /**
   * Constructs a new game instance.
   *
   * @param type the type of the game board, needs to be there due to the type erasure
   * @param comparator the comparator that determines correctness of the guess
   * @param guessList the list of valid guesses
   * @param tries the maximum number of guesses that user can make
   */
  public Game(Class<T> type, Comparator<T> comparator, U guessList, int tries)
      throws NoSuchElementException {
    this.comparator = comparator;
    this.guessList = guessList;
    this.board = new GameBoard<T>(type);
    this.maxTries = tries;
    this.triesUsed = 0;
    this.gameFinished = false;
    this.playerWon = false;
    this.type = type;

    selectRandomTarget();
  }

  public boolean getGameFinished() {
    return gameFinished;
  }

  public boolean getPlayerWon() {
    return playerWon;
  }

  public int getTriesUsed() {
    return triesUsed;
  }

  public int getTriesLeft() {
    return maxTries - triesUsed;
  }

  public GameBoard<T> getBoard() {
    return board;
  }

  public abstract String getTarget();

  /**
   * Validates whether a given guess is acceptable according to rules.
   *
   * @param guess the guess to validate
   * @throws GameException if the guess is not valid
   */
  protected abstract void validate(T guess) throws GameException;

  /**
   * Set the {@code target} to a random value from the {@code guessList}.
   *
   * <p>This method is called during initialization and needs to be implemented by a subclass to
   * define how to choose the target.
   *
   * @throws NoSuchElementException if guess list is empty
   */
  protected abstract void selectRandomTarget() throws NoSuchElementException;

  /**
   * Converts the player's {@code String} guess into a valid guess of type {@code T}.
   *
   * @param guess the player's guess
   * @return the converted guess of a valid type
   * @throws GameException if the guess cannot be converted
   */
  protected abstract T convertGuess(String guess) throws GameException;

  /**
   * Processes a guess and updates the game board accordingly.
   *
   * @param guess the player's guess
   * @throws CompareException if an error occurs during comparison
   * @throws GameException if the guess is not valid
   */
  public void play(String guess) throws CompareException, GameException {
    T convertedGuess = convertGuess(guess);

    validate(convertedGuess);

    List<ComparatorResult> result = comparator.compare(convertedGuess, target);

    triesUsed++;

    board.add(result, convertedGuess);
    isGameFinished(result);
  }

  /**
   * Checks if the game is finished and if player has won.
   *
   * <p>This function just sets the according flags, {@link Game#playerWon} and {@link
   * Game#gameFinished}.
   *
   * @param result results of the last user's guess
   */
  public void isGameFinished(List<ComparatorResult> result) {
    playerWon = result.stream().allMatch(x -> x.equals(ComparatorResult.CORRECT));
    gameFinished = triesUsed >= maxTries || playerWon;
  }

  /**
   * Resets the game to it's initial state.
   *
   * <p>Clears the board, resets the number of attempts and selects new target.
   */
  public void reset() {
    board = new GameBoard<>(type);
    triesUsed = 0;

    selectRandomTarget();
  }
}

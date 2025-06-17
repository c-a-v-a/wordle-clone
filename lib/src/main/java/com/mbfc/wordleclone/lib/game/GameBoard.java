package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class that represents a game board.
 *
 * <p>Stores the list of past guesses alongside their results (from comparison against the target),
 * so that the player can keep track of their previous guesses.
 *
 * <p>It supports iteration over the result-guess pairs and provides access to the most recent
 * entry.
 *
 * @param <T> the type of each guess
 */
public class GameBoard<T> implements Iterable<Pair<List<ComparatorResult>, T>> {
  private List<List<ComparatorResult>> results;
  private List<T> guesses;
  private final Class<T> type;

  /**
   * Constructs an empty {@code GameBoard}.
   *
   * <p>Type of the game board must be passed as a parameter to allow for generic printing in the
   * cli, due to the java's type erasure.
   *
   * @param type type of the game board
   */
  public GameBoard(Class<T> type) {
    results = new ArrayList();
    guesses = new ArrayList();
    this.type = type;
  }

  public Class<T> getType() {
    return type;
  }

  /**
   * Adds new guess and its corresponding result ot the board.
   *
   * @param result the result of comparing guess against the target
   * @param guess the player's guess
   */
  public void add(List<ComparatorResult> result, T guess) {
    results.add(result);
    guesses.add(guess);
  }

  /**
   * Checks if the board is empty.
   *
   * @return {@code true} if board is empty; @{code false} otherwise
   */
  public boolean isEmpty() {
    return results.size() < 1 || guesses.size() < 1;
  }

  /**
   * Returns the most recent result-guess pair.
   *
   * @return the last result-guess pair added to the board
   * @throws IndexOutOfBoundsException if the board is empty
   */
  public Pair<List<ComparatorResult>, T> getLast() throws IndexOutOfBoundsException {
    return new Pair(results.get(results.size() - 1), guesses.get(guesses.size() - 1));
  }

  /**
   * Returns an iterator over the result-guess pairs stored in the board.
   *
   * @return an iterator over {@code Pair<List<ComparatorResult>, T>}
   */
  @Override
  public Iterator<Pair<List<ComparatorResult>, T>> iterator() {
    return new Iterator<>() {
      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < results.size() && index < guesses.size();
      }

      @Override
      public Pair<List<ComparatorResult>, T> next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }

        Pair<List<ComparatorResult>, T> pair = new Pair<>(results.get(index), guesses.get(index));
        index++;

        return pair;
      }
    };
  }
}

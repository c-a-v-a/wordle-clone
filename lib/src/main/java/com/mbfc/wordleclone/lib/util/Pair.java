package com.mbfc.wordleclone.lib.util;

/**
 * A generic class representing a pair of objects.
 *
 * <p>The types of stored objects may be different.
 *
 * @param <T> the type of the left object
 * @param <U> the type of the right object
 */
public class Pair<T, U> {

  /** Left object of the pair. */
  private T left;

  /** Right object of the pair. */
  private U right;

  /**
   * Constructs a new {@code Pair} with the specific values.
   *
   * @param left the value for the left element
   * @param right the value for the right element
   */
  public Pair(T left, U right) {
    this.left = left;
    this.right = right;
  }

  /**
   * Getter for the left value.
   *
   * @return the left value of the pair
   */
  public T getLeft() {
    return left;
  }

  /**
   * Getter for the right value.
   *
   * @return the right value of the pair
   */
  public U getRight() {
    return right;
  }
}

package com.mbfc.wordleclone.lib.comparator;

import java.util.List;

/**
 * Functional interface defining a comparison operation between two objects.
 *
 * <p>Compares guess against the target and produces a list of {@link ComparatorResult}, that
 * describe the comparison outcome.
 *
 * @param <T> the type of objects to compare
 */
@FunctionalInterface
public interface Comparator<T> {

  /**
   * Compares the guess object against target object.
   *
   * @param guess the object to be compared (e.g., the user's guess)
   * @param target the object against which the guess is compared
   * @return a {@code List} of {@link ComparatorResult} representing the result of the comparison
   * @throws CompareException if two objects cannot be compared
   */
  List<ComparatorResult> compare(T guess, T target) throws CompareException;
}

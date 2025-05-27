package com.mbfc.wordleclone.lib.comparator;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation fo {@link Comparator} interface for comparing two strings character by
 * character.
 *
 * <p>The {@code StringComparator} compares each character in the {@code guess} string against the
 * character in the {@code target} string. If the strings have different lengths, an exception will
 * be thrown.
 *
 * <p>The result of the comparison is a {@code List<ComparatorResult>} where each element represents
 * the outcome of comparing the characters at given position:
 *
 * <ul>
 *   <li>{@link ComparatorResult#CORRECT} - the character in {@code guess} matches the character in
 *       {@code target}, that is in the same position.
 *   <li>{@link ComparatorResult#PARTIAL} - the character in {@code guess} exists somewhere in the
 *       {@code target} string.
 *   <li>{@link ComparatorResult#INCORRECT} - the character in {@code guess} does not exist in the
 *       {@code target} string.
 * </ul>
 */
public class StringComparator implements Comparator<String> {

  /**
   * Compares two string character by character and returns the result.
   *
   * @param guess the string that will be compared
   * @param target the string that {@code guess} will be compared against
   * @return a list of {@link ComparatorResult} representing the outcome of the comparison
   * @throws CompareException if the two strings have different lengths
   */
  @Override
  public List<ComparatorResult> compare(String guess, String target) throws CompareException {
    if (guess.length() != target.length()) {
      throw new CompareException(
          String.format(
              "Cannot compare \"%s\" to \"%s\". Strings have different length.", guess, target));
    }

    List<ComparatorResult> result = new ArrayList();

    for (int i = 0; i < guess.length(); i++) {
      char guessChar = guess.charAt(i);
      char targetChar = target.charAt(i);

      if (guessChar == targetChar) {
        result.add(ComparatorResult.CORRECT);
      } else if (target.indexOf(guessChar) != -1) {
        result.add(ComparatorResult.PARTIAL);
      } else {
        result.add(ComparatorResult.INCORRECT);
      }
    }

    return result;
  }
}

package com.mbfc.wordleclone.lib.comparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    int length = guess.length();
    List<ComparatorResult> result = new ArrayList<>(length);

    for (int i = 0; i < length; i++) {
      result.add(null);
    }

    Map<Character, Integer> remainingLetters = new HashMap<>();

    for (int i = 0; i < length; i++) {
      char guessChar = guess.charAt(i);
      char targetChar = target.charAt(i);
      if (guessChar == targetChar) {
        result.set(i, ComparatorResult.CORRECT);
      } else {
        result.set(i, null); // placeholder
        remainingLetters.put(targetChar, remainingLetters.getOrDefault(targetChar, 0) + 1);
      }
    }

    for (int i = 0; i < length; i++) {
      if (result.get(i) == null) {
        char guessChar = guess.charAt(i);
        int count = remainingLetters.getOrDefault(guessChar, 0);
        if (count > 0) {
          result.set(i, ComparatorResult.PARTIAL);
          remainingLetters.put(guessChar, count - 1);
        } else {
          result.set(i, ComparatorResult.INCORRECT);
        }
      }
    }

    return result;
  }
}

package com.mbfc.wordleclone.lib.util;

import java.util.Random;

/** Utility class for generating random string of letters. */
public class RandomGen {
  private static final String letters = "abcdefghijklmnopqrstuvwxyz";

  public static String getLetters() {
    return letters;
  }

  /**
   * Generates the random string of the specified length from the {@code letters} list.
   *
   * @param length desired length of the generated string
   * @return a randomly generated string of given length
   */
  public static String generate(int length) {
    Random rand = new Random();
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < length; i++) {
      result.append(letters.charAt(rand.nextInt(letters.length())));
    }

    return result.toString();
  }
}

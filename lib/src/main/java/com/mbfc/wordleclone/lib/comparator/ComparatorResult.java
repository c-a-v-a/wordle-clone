package com.mbfc.wordleclone.lib.comparator;

/**
 * Represents the possible values of a comparison operation in {@link Comparator} interface.
 *
 * <p>Each value is associated with a description that provides explanation for the meaning of the
 * result, so that user will know what each result means.
 */
public enum ComparatorResult {

  /** Indicates that the guess is exactly correct. */
  CORRECT("Correct guess"),

  /**
   * Indicates that the guess is partially correct.
   *
   * <p>For example: the letter is in the target word, but is in the wrong place.
   */
  PARTIAL("Partially correct"),

  /** Indicates that the guess is incorrect. */
  INCORRECT("Incorrect");

  private final String text;

  ComparatorResult(String s) {
    text = s;
  }

  /**
   * Returns the explanation associated with given result.
   *
   * @return a string describing the result
   */
  public String getText() {
    return this.text;
  }
}

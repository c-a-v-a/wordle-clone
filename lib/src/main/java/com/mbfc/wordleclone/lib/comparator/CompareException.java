package com.mbfc.wordleclone.lib.comparator;

/**
 * Checked exception indicating the comparison error.
 *
 * <p>Used in {@link Comparator} interface.
 */
public class CompareException extends Exception {

  /**
   * Create the exception with the error message.
   *
   * @param errorMessage the detail message
   */
  public CompareException(String errorMessage) {
    super(errorMessage);
  }
}

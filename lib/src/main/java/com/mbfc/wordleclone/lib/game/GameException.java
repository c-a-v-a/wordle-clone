package com.mbfc.wordleclone.lib.game;

/** Checked exception indicating the user's guess is not valid. */
public class GameException extends Exception {

  /**
   * Create the exception with the error message.
   *
   * @param errorMessage the detail message
   */
  public GameException(String errorMessage) {
    super(errorMessage);
  }
}

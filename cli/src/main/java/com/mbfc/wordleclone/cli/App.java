package com.mbfc.wordleclone.cli;

/**
 * Main application entry point.
 *
 * <p>This class starts the interactive game menu.
 */
public class App {
  /** Program entry point. */
  public static void main() {
    GameMenu menu = new GameMenu();
    menu.displayMenu();
  }
}

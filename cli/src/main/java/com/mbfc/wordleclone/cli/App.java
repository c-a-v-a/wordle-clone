package com.mbfc.wordleclone.cli;

import org.fusesource.jansi.AnsiConsole;

/**
 * Main application entry point.
 *
 * <p>This class starts the interactive game menu.
 */
public class App {
  /** Program entry point. */
  public static void main(String[] args) {
    AnsiConsole.systemInstall();

    GameMenu menu = new GameMenu();
    menu.displayMenu();

    AnsiConsole.systemUninstall();
  }
}

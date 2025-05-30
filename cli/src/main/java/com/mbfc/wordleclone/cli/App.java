package com.mbfc.wordleclone.cli;

/**
 * Main application entry point.
 *
 * <p>This class starts the interactive game menu.
 */
public class App {
    public static void main(String[] args) {
        GameMenu menu = new GameMenu();
        menu.displayMenu();
    }
}

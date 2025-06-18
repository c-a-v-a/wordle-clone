package com.mbfc.wordleclone.cli;

import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import com.mbfc.wordleclone.lib.game.Game;
import com.mbfc.wordleclone.lib.game.GameException;
import com.mbfc.wordleclone.lib.game.ZenGame;
import com.mbfc.wordleclone.lib.parser.SimpleStringParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.fusesource.jansi.Ansi;

/**
 * Interactive game menu.
 *
 * <p>This menu provides three top-level options: Play, Load a list, and Exit. If "Play" is
 * selected, the user is prompted to choose a data type (String or Object), and when String is
 * chosen, they must select a game mode (Endless, not implemented, or Normal). In Normal mode the
 * number of lives is set and the user chooses one of the loaded word lists. Then the game
 * (SimpleGame) is launched.
 */
public class GameMenu {

  private final Scanner scanner;
  // Cache of word lists stored as key: list name, value: List<String>
  private final Map<String, List<String>> wordLists;
  private final SimpleStringParser parser;

  /** Constructs a new GameMenu instance. */
  public GameMenu() {
    scanner = new Scanner(System.in);
    wordLists = new HashMap<>();
    parser = new SimpleStringParser();

    resourceLoader();
  }

  private void resourceLoader() {
    try {
      wordLists.put("5 letters", parser.parseResource("5letters.txt"));
    } catch (Exception ignore) {
      return;
    }
  }

  /** Displays the main menu and processes user commands until Exit is selected. */
  public void displayMenu() {
    while (true) {
      System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
      System.out.println("\n==== Main Menu ====");
      System.out.println("1. Play");
      System.out.println("2. Load a list");
      System.out.println("3. Exit");
      System.out.print("Choose an option: ");

      String option = scanner.nextLine().trim();

      switch (option) {
        case "1":
          playOption();
          break;
        case "2":
          loadListOption();
          break;
        case "3":
          System.out.println("Goodbye!");
          return;
        default:
          System.out.println("Invalid option. Please try again.");
      }
    }
  }

  /** Handles the "Load a list" option: prompts for file path and list name then loads the list. */
  private void loadListOption() {
    Ansi.ansi().eraseScreen().cursor(0, 0);
    System.out.print("Enter the file path to the word list (.txt): ");
    String filePath = scanner.nextLine().trim();

    System.out.print("Enter a name for this list: ");
    String listName = scanner.nextLine().trim();

    try {
      List<String> words = parser.parseFile(filePath);

      if (words.isEmpty()) {
        System.out.println("The loaded word list is empty.");
        return;
      }

      wordLists.put(listName, words);
      System.out.println("Word list '" + listName + "' loaded successfully.");
    } catch (IOException e) {
      System.out.println("Error loading word list: " + e.getMessage());
    }
  }

  /**
   * Handles the "Play" option.
   *
   * <p>Provides a choice between String and Object (with only String implemented).
   */
  private void playOption() {
    System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
    System.out.println("\nSelect data type to play with:");
    System.out.println("1. String");
    System.out.println("2. Object (not implemented)");
    System.out.print("Choose an option: ");
    String typeOption = scanner.nextLine().trim();

    switch (typeOption) {
      case "1":
        playStringMode();
        break;
      case "2":
        System.out.println("Object mode not implemented yet.");
        break;
      default:
        System.out.println("Invalid option.");
    }
  }

  /**
   * Handles the "Play" option for String-based games.
   *
   * <p>Provides a choice between Endless (not implemented) and Normal modes. In Normal mode, asks
   * for the number of lives and word list selection.
   */
  private void playStringMode() {
    System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
    System.out.println("\nSelect game mode:");
    System.out.println("1. Endless (not implemented)");
    System.out.println("2. Normal");
    System.out.print("Choose an option: ");
    String modeOption = scanner.nextLine().trim();

    if (!"2".equals(modeOption)) {
      System.out.println("Only Normal mode is implemented.");
      return;
    }

    System.out.print("Enter the number of attempts (default 6): ");
    int lives;

    try {
      lives = Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
      // System.out.println("Invalid number format. Using default lives = 6.");
      lives = 6;
    }

    if (wordLists.isEmpty()) {
      System.out.println("No word lists loaded. Please load a word list first.");
      return;
    }

    System.out.println("Available word lists:");

    for (String key : wordLists.keySet()) {
      System.out.println("- " + key);
    }

    System.out.print("Enter the name of the word list to use: ");
    String listKey = scanner.nextLine().trim();
    List<String> chosenList = wordLists.get(listKey);

    if (chosenList == null) {
      System.out.println("No word list with that name exists.");
      return;
    }

    StringComparator comparator = new StringComparator();
    Game<String, List<String>> game;

    try {
      // game = new SimpleGame(comparator, chosenList, lives);
      game = new ZenGame(comparator, chosenList);
    } catch (NoSuchElementException e) {
      System.out.println("Error: " + e.getMessage());
      return;
    }

    gameLoop(game);
  }

  private void gameLoop(Game<?, ?> game) {
    do {
      System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
      System.out.println("Guesses left: " + game.getTriesLeft());
      Printer.printBoard(game.getBoard());
      System.out.print("\nGuess: ");

      String guess = scanner.nextLine().trim();

      try {
        game.play(guess);
      } catch (CompareException | GameException e) {
        System.out.println("Error: " + e.getMessage());
        scanner.nextLine();

        continue;
      }

    } while (!game.getGameFinished());

    if (game.getPlayerWon()) {
      System.out.println("\nCongratulations. You won in " + game.getTriesUsed() + " guesses.");
    } else {
      System.out.println("You lost. The target was: " + game.getTarget());
    }

    System.out.println("\nDo you want to play again? [y/n]");
    String option = scanner.nextLine().trim();

    if (option.equals("y") || option.equals("Y")) {
      game.reset();
      gameLoop(game);
    }
  }
}

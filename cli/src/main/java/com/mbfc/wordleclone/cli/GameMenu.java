package com.mbfc.wordleclone.cli;

import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import com.mbfc.wordleclone.lib.game.EndlessGame;
import com.mbfc.wordleclone.lib.game.Game;
import com.mbfc.wordleclone.lib.game.GameException;
import com.mbfc.wordleclone.lib.game.SimpleEndlessGame;
import com.mbfc.wordleclone.lib.game.SimpleGame;
import com.mbfc.wordleclone.lib.game.ZenRandomGame;
import com.mbfc.wordleclone.lib.parser.SimpleStringParser;
import com.mbfc.wordleclone.lib.util.HighScoreManager;
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
 * <p>This menu provides three top-level options: Play, Load a list, and Exit. When "Play" is
 * selected, the user is prompted to choose a data type (String or Object), and when String is
 * chosen, they must select a game mode (Endless or Normal). In Normal mode the number of attempts
 * is set and the user selects one of the loaded word lists before launching the game (SimpleGame).
 */
public class GameMenu {

  private final Scanner scanner;
  // Cache of word lists stored as <list name, List<String>>
  private final Map<String, List<String>> wordLists;
  private final SimpleStringParser parser;

  /** Constructs a new GameMenu instance. */
  public GameMenu() {
    scanner = new Scanner(System.in);
    wordLists = new HashMap<>();
    parser = new SimpleStringParser();

    loadDefaultResources();
  }

  /**
   * Loads default resources.
   *
   * <p>Adds a default word list ("5 letters") from the resource file.
   */
  private void loadDefaultResources() {
    try {
      wordLists.put("4 letters", parser.parseResource("4letters.txt"));
      wordLists.put("5 letters", parser.parseResource("5letters.txt"));
      wordLists.put("6 letters", parser.parseResource("6letters.txt"));
    } catch (Exception ignore) {
      // Ignore errors during default resource loading.
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

  /**
   * Handles the "Load a list" option.
   *
   * <p>Prompts for file path and a name for the list, then loads the word list.
   */
  private void loadListOption() {
    System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
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
   * <p>Provides a choice between String and Object (only String is implemented).
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
   * <p>Provides a choice between Endless and Normal modes. In Normal mode, requests the number of
   * attempts and a word list selection.
   */
  private void playStringMode() {
    System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
    System.out.println("\nSelect game mode:");
    System.out.println("1. Endless");
    System.out.println("2. Normal");
    System.out.println("3. Zen Random");
    System.out.print("Choose an option: ");
    String modeOption = scanner.nextLine().trim();

    System.out.print("Enter the number of attempts (default 6): ");
    int lives;
    try {
        lives = Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid number format. Using default lives = 6.");
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
        System.out.println("No word list with that name exists. Chosen: \"5 letters\" by default");
        chosenList = wordLists.get("5 letters");
    }

    StringComparator comparator = new StringComparator();
    try {
        if ("1".equals(modeOption)) {
            final int bonusTries = 2;
            System.out.println("Endless Mode! You get " + bonusTries + " bonus lives for each correct word.");
            System.out.println("Press Enter to start...");
            scanner.nextLine();

            SimpleEndlessGame game = new SimpleEndlessGame(comparator, chosenList, lives, bonusTries);
            HighScoreManager highScoreManager = new HighScoreManager("cli/src/main/resources/highscore_endless_classic.txt");
            // HighScoreManager tworzy plik (np. w folderze .wrodle-clone w katalogu domowym)
            endlessGameLoop(game, highScoreManager);

        } else if ("2".equals(modeOption)) {
            SimpleGame game = new SimpleGame(comparator, chosenList, lives);
            gameLoop(game);

        } else if ("3".equals(modeOption)) {
            ZenRandomGame game = new ZenRandomGame(comparator, 5);
            gameLoop(game);

        } else {
            System.out.println("Invalid option.");
        }
    } catch (NoSuchElementException e) {
        System.out.println("Error creating game: " + e.getMessage());
    }
  }


  /**
   * Handles the game loop for Normal game mode.
   *
   * <p>Displays the current board and the number of remaining attempts, processes user input (with
   * an option to exit by typing "q"), and determines whether the game is won or lost. After the
   * game ends, asks the user if they want to play again and restarts the game loop if confirmed.
   */
  private void gameLoop(Game<?, ?> game) {
    do {
      System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
      System.out.println("Guesses left: " + game.getTriesLeft());
      Printer.printBoard(game.getBoard());
      System.out.print("\nGuess (or type 'q' to exit): ");

      String guess = scanner.nextLine().trim().toLowerCase();
      if (guess.equalsIgnoreCase("q")) {
        break;
      }

      try {
        game.play(guess);
      } catch (CompareException | GameException e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("Press Enter to try again...");
        scanner.nextLine();
      }

    } while (!game.getGameFinished());

    System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
    System.out.println("Guesses left: " + game.getTriesLeft());
    Printer.printBoard(game.getBoard());

    System.out.println(game.getFinalGameMessage());

    System.out.println("\nDo you want to play again? [y/n]");
    String option = scanner.nextLine().trim();

    if (option.equalsIgnoreCase("y")) {
      game.reset();
      gameLoop(game);
    }
  }

  /**
   * Handles the game loop for endless mode.
   *
   * <p>This method now accepts an abstract EndlessGame instance. Thanks to polymorphism, any
   * subclass (e.g., SimpleEndlessGame, RandomEndlessGame) can be used.
   *
   * @param game an instance of EndlessGame
   * @param highScoreManager the high score manager responsible for tracking and updating the high
   *     score
   */
  private void endlessGameLoop(EndlessGame<?, ?> game, HighScoreManager highScoreManager) {
    while (!game.getGameFinished()) {
      System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
      System.out.println("Current Score: " + game.getScore());
      System.out.println("High Score: " + highScoreManager.getHighScore());
      System.out.println("Lives left: " + game.getLives());
      Printer.printBoard(game.getBoard());
      System.out.print("\nGuess (or type 'q' to exit): ");

      String guess = scanner.nextLine().trim().toLowerCase();
      if (guess.equalsIgnoreCase("q")) {
        break;
      }

      try {
        game.play(guess);
        if (game.isRoundComplete()) {
          String provenTarget = game.getTarget();
          int attempts = game.getAttemptsTaken();
          int bonus = game.getBonusTriesOnWin();
          int simulatedLives = game.getLives() + bonus;
          int simulatedScore = game.getScore() + 1;

          System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
          System.out.println("Current Score: " + simulatedScore);
          System.out.println("High Score: " + highScoreManager.getHighScore());
          System.out.println("Lives left: " + simulatedLives);
          Printer.printBoard(game.getBoard());
          System.out.println(
              "\nCongratulations! The word was guessed: "
                  + provenTarget
                  + ". It took "
                  + attempts
                  + " attempts.");
          System.out.println("Press Enter to continue...");
          scanner.nextLine();
          game.commitRound();
        }
        highScoreManager.updateHighScore(game.getScore());
      } catch (CompareException | GameException e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
      }
    }

    System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
    Printer.printBoard(game.getBoard());
    System.out.println(game.getFinalGameMessage());
    System.out.println("High Score: " + highScoreManager.getHighScore());
    System.out.println("\nPress Enter to return to the main menu...");
    scanner.nextLine();
  }
}

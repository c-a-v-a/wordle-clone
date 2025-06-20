package com.mbfc.wordleclone.cli;

import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.ObjectComparator;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import com.mbfc.wordleclone.lib.game.EndlessGame;
import com.mbfc.wordleclone.lib.game.EndlessRandomGame;
import com.mbfc.wordleclone.lib.game.Game;
import com.mbfc.wordleclone.lib.game.GameException;
import com.mbfc.wordleclone.lib.game.GameMode;
import com.mbfc.wordleclone.lib.game.ObjectEndlessGame;
import com.mbfc.wordleclone.lib.game.ObjectGameMode;
import com.mbfc.wordleclone.lib.game.RandomGame;
import com.mbfc.wordleclone.lib.game.SimpleEndlessGame;
import com.mbfc.wordleclone.lib.game.SimpleGame;
import com.mbfc.wordleclone.lib.game.SimpleObjectGame;
import com.mbfc.wordleclone.lib.game.ZenGame;
import com.mbfc.wordleclone.lib.game.ZenObjectGame;
import com.mbfc.wordleclone.lib.game.ZenRandomGame;
import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.parser.JsonParser;
import com.mbfc.wordleclone.lib.parser.SimpleStringParser;
import com.mbfc.wordleclone.lib.util.HighScoreManager;
import com.mbfc.wordleclone.lib.util.Pair;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.TreeMap;
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
  private final Map<String, Pair<String, List<TreeMap<String, Field>>>> objectWordLists;
  private final SimpleStringParser parser;
  private final JsonParser jsonParser;

  /** Constructs a new GameMenu instance. */
  public GameMenu() {
    scanner = new Scanner(System.in);
    wordLists = new HashMap<>();
    objectWordLists = new HashMap<>();
    parser = new SimpleStringParser();
    jsonParser = new JsonParser();

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

      objectWordLists.put(
          "Programming languages", jsonParser.parseResource("/programming_languages.json"));
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
    System.out.println("Select the type of list you want to load");
    System.out.println("1. Normal (.txt)");
    System.out.println("2. Object (.json)");
    String option = scanner.nextLine().trim();

    switch (option) {
      case "1":
        System.out.println("Example that you can try in wordle-clone dir: \n"
                          +"./cli/src/main/resources/spanish5letters.txt");        
        System.out.print("Enter the file path to the word list (absolute or inside project folder as above):");
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
        break;
      case "2":
        System.out.println("Example that you can try in wordle-clone dir: \n"
                          +"./cli/src/main/resources/music_genres.json");        
        System.out.print("Enter the file path to the word list (absolute or inside project folder as above):");
        String p = scanner.nextLine().trim();

        System.out.print("Enter a name for this list: ");
        String n = scanner.nextLine().trim();

        try {
          Pair<String, List<TreeMap<String, Field>>> map = jsonParser.parseFile(p);

          objectWordLists.put(n, map);
          System.out.println("Word list '" + n + "' loaded successfully.");
        } catch (IOException e) {
          System.out.println("Error loading word list: " + e.getMessage());
        }
        break;
      default:
        System.out.println("Invalid option.");
    }

    System.out.println("Press Enter to continue...");
    scanner.nextLine();
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
    System.out.println("2. Object");
    System.out.print("Choose an option: ");
    String typeOption = scanner.nextLine().trim();

    switch (typeOption) {
      case "1":
        playStringMode();
        break;
      case "2":
        playObjectMode();
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
    System.out.println("1. Normal");
    System.out.println("2. Endless");
    System.out.println("3. Zen Classic");
    System.out.println("4. Random");
    System.out.println("5. Endless Random");
    System.out.println("6. Zen Random");

    System.out.print("Choose an option: ");
    String modeOption = scanner.nextLine().trim();

    Optional<GameMode> optionalMode = GameMode.fromOption(modeOption);
    if (!optionalMode.isPresent()) {
      System.out.println("Invalid option.");
      return;
    }
    GameMode selectedMode = optionalMode.get();

    // Domyślna liczba żyć ustawiona na 6.
    // Jeśli tryb nie zawiera "ZEN" w nazwie, użytkownik ma możliwość zmiany liczby prób.
    int lives = 6;
    if (!selectedMode.name().contains("ZEN")) {
      System.out.print("Enter the number of attempts (default 6): ");
      String livesInput = scanner.nextLine().trim();
      if (!livesInput.isEmpty()) {
        try {
          lives = Integer.parseInt(livesInput);
        } catch (NumberFormatException e) {
          System.out.println("Invalid number format. Using default lives = 6.");
        }
      }
    }

    int addedLives = 2;
    if (selectedMode.name().contains("ENDLESS")) {
      System.out.print(
          "Enter the number of bonus tries added after each succesfull guess (default = 2): ");
      String addedInput = scanner.nextLine().trim();
      if (!addedInput.isEmpty()) {
        try {
          addedLives = Integer.parseInt(addedInput);
        } catch (NumberFormatException e) {
          System.out.println("Invalid number format. Using default added tries = 2.");
        }
      }
    }

    int length = 5;
    List<String> chosenList = null;
    if (selectedMode.name().contains("RANDOM")) {
      System.out.println("Using random chains of characters.");
      System.out.print("Enter the length of the words (default 5): ");
      String lengthInput = scanner.nextLine().trim();
      if (!lengthInput.isEmpty()) {
        try {
          length = Integer.parseInt(lengthInput);
        } catch (NumberFormatException e) {
          System.out.println("Invalid number format. Using default length = 5.");
        }
      }
    } else {
      if (wordLists.isEmpty()) {
        System.out.println("No word lists loaded. Please load a word list first.");
        return;
      }
      System.out.println("Available word lists:");
      for (String key : wordLists.keySet()) {
        System.out.println("- " + key);
      }
      System.out.print("Enter the name of the word list to use (\"5 letters\" by default): ");
      String listKey = scanner.nextLine().trim();
      chosenList = wordLists.get(listKey);
      if (chosenList == null) {
        System.out.println("No word list with that name exists. Chosen: \"5 letters\" by default.");
        chosenList = wordLists.get("5 letters");
      }
      length = chosenList.get(0).length();
    }
    System.out.println("Press Enter to continue...");
    scanner.nextLine();

    StringComparator comparator = new StringComparator();
    try {
      switch (selectedMode) {
        case SIMPLE:
          SimpleGame simpleGame = new SimpleGame(comparator, chosenList, lives);
          gameLoop(simpleGame, null);
          break;

        case ENDLESS:
          System.out.println(
              "Endless Mode! You get " + addedLives + " bonus lives for each correct word.");
          System.out.println("Press Enter to start...");
          scanner.nextLine();

          SimpleEndlessGame endlessGame =
              new SimpleEndlessGame(comparator, chosenList, lives, addedLives);
          HighScoreManager highScoreManager = new HighScoreManager("highscore_endless_classic.txt");
          endlessGameLoop(endlessGame, highScoreManager, null);
          break;

        case ZEN_CLASSIC:
          // Tryby Zen nie wymagają liczby żyć ani wyboru listy, ale ZenClassic potrzebuje listy
          // słów.
          ZenGame zenGame = new ZenGame(comparator, chosenList);
          gameLoop(zenGame, null);
          break;

        case RANDOM:
          RandomGame randomGame = new RandomGame(comparator, lives, length);
          gameLoop(randomGame, null);
          break;

        case ENDLESS_RANDOM:
          System.out.println(
              "Endless Random Mode! You get " + addedLives + " bonus lives for each correct word.");
          System.out.println("Press Enter to start...");
          scanner.nextLine();

          EndlessRandomGame endlessGameRandom =
              new EndlessRandomGame(comparator, lives, addedLives, length);
          HighScoreManager highScoreManagerRandom =
              new HighScoreManager("highscore_endless_random.txt");
          endlessGameLoop(endlessGameRandom, highScoreManagerRandom, null);
          break;

        case ZEN_RANDOM:
          ZenRandomGame zenRandGame = new ZenRandomGame(comparator, length);
          gameLoop(zenRandGame, null);
          break;

        default:
          System.out.println("Invalid option.");
          break;
      }
    } catch (NoSuchElementException e) {
      System.out.println("Error creating game: " + e.getMessage());
    }
  }

  private void playObjectMode() {
    System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
    System.out.println("\nSelect game mode:");
    System.out.println("1. Normal");
    System.out.println("2. Endless");
    System.out.println("3. Zen");

    System.out.print("Choose an option: ");
    String modeOption = scanner.nextLine().trim();

    Optional<ObjectGameMode> optionalMode = ObjectGameMode.fromOption(modeOption);
    if (!optionalMode.isPresent()) {
      System.out.println("Invalid option.");
      return;
    }
    ObjectGameMode selectedMode = optionalMode.get();

    int lives = 6;
    if (!selectedMode.name().contains("ZEN")) {
      System.out.print("Enter the number of attempts (default 6): ");
      String livesInput = scanner.nextLine().trim();
      if (!livesInput.isEmpty()) {
        try {
          lives = Integer.parseInt(livesInput);
        } catch (NumberFormatException e) {
          System.out.println("Invalid number format. Using default lives = 6.");
        }
      }
    }

    int addedLives = 2;
    if (selectedMode.name().contains("ENDLESS")) {
      System.out.print(
          "Enter the number of bonus tries added after each succesfull guess (default = 2): ");
      String addedInput = scanner.nextLine().trim();
      if (!addedInput.isEmpty()) {
        try {
          addedLives = Integer.parseInt(addedInput);
        } catch (NumberFormatException e) {
          System.out.println("Invalid number format. Using default added tries = 2.");
        }
      }
    }

    if (objectWordLists.isEmpty()) {
      System.out.println("No word lists loaded. Please load a word list first.");
      return;
    }
    System.out.println("Available word lists:");
    for (String key : objectWordLists.keySet()) {
      System.out.println("- " + key);
    }
    System.out.print(
        "Enter the name of the word list to use (\"Programming languages\" by default): ");
    String listKey = scanner.nextLine().trim();
    Pair<String, List<TreeMap<String, Field>>> chosenList = objectWordLists.get(listKey);
    if (chosenList == null) {
      System.out.println(
          "No word list with that name exists. Chosen: \"Programming languages\" by default.");
      chosenList = objectWordLists.get("Programming languages");
    }

    System.out.println("Press Enter to continue...");
    scanner.nextLine();
    ObjectComparator comparator = new ObjectComparator();
    try {
      switch (selectedMode) {
        case SIMPLE:
          SimpleObjectGame simpleGame =
              new SimpleObjectGame(comparator, chosenList.right(), lives, chosenList.left());
          gameLoop(simpleGame, chosenList.left());
          break;

        case ENDLESS:
          System.out.println(
              "Endless Mode! You get " + addedLives + " bonus lives for each correct word.");
          System.out.println("Press Enter to start...");
          scanner.nextLine();

          ObjectEndlessGame endlessGame =
              new ObjectEndlessGame(
                  comparator, chosenList.right(), lives, addedLives, chosenList.left());
          HighScoreManager highScoreManager = new HighScoreManager("highscore_endless_object.txt");
          endlessGameLoop(endlessGame, highScoreManager, chosenList.left());
          break;

        case ZEN_CLASSIC:
          // Tryby Zen nie wymagają liczby żyć ani wyboru listy, ale ZenClassic potrzebuje listy
          // słów.
          ZenObjectGame zenGame =
              new ZenObjectGame(comparator, chosenList.right(), chosenList.left());
          gameLoop(zenGame, chosenList.left());
          break;

        default:
          System.out.println("Invalid option.");
          break;
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
  private void gameLoop(Game<?, ?> game, String key) {
    do {
      System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
      Printer.printColorCodeInfo();
      System.out.println("Guesses left: " + game.getTriesLeft());
      Printer.printBoard(game.getBoard(), key);
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
    Printer.printBoard(game.getBoard(), key);

    System.out.println(game.getFinalGameMessage());

    System.out.println("\nDo you want to play again? [y/n]");
    String option = scanner.nextLine().trim();

    if (option.equalsIgnoreCase("y")) {
      game.reset();
      gameLoop(game, key);
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
  private void endlessGameLoop(
      EndlessGame<?, ?> game, HighScoreManager highScoreManager, String key) {
    while (!game.getGameFinished()) {
      System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
      Printer.printColorCodeInfo();
      System.out.println("Current Score: " + game.getScore());
      System.out.println("High Score: " + highScoreManager.getHighScore());
      System.out.println("Lives left: " + game.getLives());
      Printer.printBoard(game.getBoard(), key);
      System.out.print("\nGuess (or type 'q' to exit): ");

      String guess = scanner.nextLine().trim().toLowerCase();
      if (guess.equalsIgnoreCase("q")) {
        break;
      }

      try {
        game.play(guess);
        if (game.isRoundComplete()) {
          final String provenTarget = game.getTarget();
          final int attempts = game.getAttemptsTaken();
          int bonus = game.getBonusTriesOnWin();
          int simulatedLives = game.getLives() + bonus;
          int simulatedScore = game.getScore() + 1;

          System.out.println(Ansi.ansi().eraseScreen().cursor(0, 0));
          System.out.println("Current Score: " + simulatedScore);
          System.out.println("High Score: " + highScoreManager.getHighScore());
          System.out.println("Lives left: " + simulatedLives);
          Printer.printBoard(game.getBoard(), key);
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
    Printer.printBoard(game.getBoard(), key);
    System.out.println(game.getFinalGameMessage());
    System.out.println("High Score: " + highScoreManager.getHighScore());
    System.out.println("\nDo you want to play again? [y/n]");
    String option = scanner.nextLine().trim();

    if (option.equalsIgnoreCase("y")) {
      game.reset();
      endlessGameLoop(game, highScoreManager, key);
    }
  }
}

package com.mbfc.wordleclone.lib.menu;

import com.mbfc.wordleclone.lib.game.Game;
import com.mbfc.wordleclone.lib.game.GameException;
import com.mbfc.wordleclone.lib.game.GameFactory;
import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.CompareException;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.util.Pair;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for handling the interactive game menu.
 *
 * <p>The menu allows the user to either start a game with a selected word list (from resources or cache)
 * or load new word lists from files into memory. This class is implemented generically to support
 * different data types for the game.
 *
 * @param <T> the type of objects used in the game (e.g., String)
 */
public class GameMenu<T> {

    private static final int MAX_TRIES = 6;

    private final WordListManager<T> wordListManager;
    private final Scanner scanner;
    private final Comparator<T> comparator;
    private final GameFactory<T, List<T>> gameFactory;

    /**
     * Constructs a new GameMenu instance.
     *
     * @param wordListManager the manager handling word list caching
     * @param scanner         the Scanner used for reading user input
     * @param comparator      the comparator for comparing the player's guess with the target
     * @param gameFactory     the factory for creating new game instances
     */
    public GameMenu(WordListManager<T> wordListManager, Scanner scanner, Comparator<T> comparator,
                    GameFactory<T, List<T>> gameFactory) {
        this.wordListManager = wordListManager;
        this.scanner = scanner;
        this.comparator = comparator;
        this.gameFactory = gameFactory;
    }

    /**
     * Displays the interactive menu in a loop until the user chooses to exit.
     */
    public void displayMenu() {
        while (true) {
            System.out.println("\n==== Menu ====");
            System.out.println("1. Start Game");
            System.out.println("2. Load word list from file");
            System.out.println("3. Exit");
            System.out.print("Please choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    startGameOption();
                    break;
                case "2":
                    loadWordListOption();
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
     * Handles the option to start a game.
     *
     * <p>The user selects the source of the word list – built-in (resource) or cached – and a new game
     * is instantiated accordingly.
     */
    private void startGameOption() {
        List<T> wordList = null;

        System.out.println("\nSelect the source of the word list:");
        System.out.println("1. Built-in word list (resource: test_resource.txt)");
        System.out.println("2. Cached word list");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                try {
                    // Load word list from resource
                    wordList = wordListManager.loadFromResource("test_resource.txt");
                } catch (IOException e) {
                    System.out.println("Error loading built-in word list: " + e.getMessage());
                    return;
                }
                break;
            case "2":
                if (wordListManager.getCachedWordLists().isEmpty()) {
                    System.out.println("No word lists available in memory.");
                    return;
                }
                System.out.println("Available word lists: ");
                wordListManager.getCachedWordLists().keySet().forEach(System.out::println);
                System.out.print("Enter the name of the word list: ");
                String key = scanner.nextLine().trim();
                wordList = wordListManager.getCachedWordLists().get(key);
                if (wordList == null) {
                    System.out.println("Word list with the given name does not exist.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        Game<T, List<T>> game;
        try {
            game = gameFactory.createGame(comparator, wordList, MAX_TRIES);
        } catch (NoSuchElementException e) {
            System.out.println("Word list is empty or invalid: " + e.getMessage());
            return;
        }

        System.out.println("\nStarting the game!");
        // Main game loop
        while (!game.getGameFinished()) {
            System.out.print("Enter your guess: ");
            String guessInput = scanner.nextLine().trim();
            T guess = parseGuess(guessInput);
            try {
                game.play(guess);
                System.out.println("\nCurrent board status:");
                // Iterate through the board; note the type parameter is now Pair<List<ComparatorResult>, T>
                for (Pair<List<ComparatorResult>, T> entry : game.getBoard()) {
                    System.out.println("Guessed word: " + entry.getRight() +
                            " | Result: " + entry.getLeft());
                }
                System.out.println("Attempts remaining: " + game.getTriesLeft());
            } catch (CompareException | GameException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        if (game.getPlayerWon()) {
            System.out.println("\nCongratulations! You won.");
        } else {
            System.out.println("\nYou lost. The correct word was: " + game.getTarget());
        }
    }

    /**
     * Converts user input into the type used for guesses.
     *
     * <p>The default implementation assumes that type T is String. Override this method if another type is used.
     *
     * @param input the input string from the user
     * @return a value of type T created from the input
     */
    @SuppressWarnings("unchecked")
    protected T parseGuess(String input) {
        return (T) input;
    }

    /**
     * Handles the option to load a word list from a file.
     */
    private void loadWordListOption() {
        System.out.print("\nEnter the path to the word list file (.txt): ");
        String filePath = scanner.nextLine().trim();
        System.out.print("Enter a name for this word list: ");
        String wordListName = scanner.nextLine().trim();

        try {
            List<T> wordList = wordListManager.loadFromFile(filePath);
            if (wordList.isEmpty()) {
                System.out.println("The loaded word list is empty.");
                return;
            }
            wordListManager.addWordList(wordListName, wordList);
            System.out.println("Word list '" + wordListName + "' has been successfully loaded into memory.");
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}

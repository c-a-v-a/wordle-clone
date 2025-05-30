package com.mbfc.wordleclone.cli;

import com.mbfc.wordleclone.lib.component.GameComponentFactory;
import com.mbfc.wordleclone.lib.component.StringGameComponentFactory;
import com.mbfc.wordleclone.lib.menu.GameMenu;
import com.mbfc.wordleclone.lib.menu.WordListManager;
import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.game.GameFactory;
import java.util.List;
import java.util.Scanner;

/**
 * Main application entry point.
 *
 * <p>This class initializes the game components in a generic way and starts the interactive game menu.
 */
public class App {
    public static void main(String[] args) {
        // You can later decide the type via command-line args or configuration.
        // For now, we choose the String implementation.
        GameComponentFactory<String> componentFactory = new StringGameComponentFactory();
        runApp(componentFactory);
    }

    /**
     * Generic method to run the application using the provided game component factory.
     *
     * @param <T>               the type used in the game
     * @param componentFactory  the generic factory providing components for the game
     */
    public static <T> void runApp(GameComponentFactory<T> componentFactory) {
        // Create components using the factory.
        WordListManager<T> manager = componentFactory.createWordListManager();
        Scanner scanner = new Scanner(System.in);
        Comparator<T> comparator = componentFactory.createComparator();
        GameFactory<T, List<T>> gameFactory = componentFactory.createGameFactory();

        // Optionally display the game type.
        System.out.println("Starting game of type: " + componentFactory.getTypeName());

        // Create and run the game menu.
        GameMenu<T> menu = new GameMenu<>(manager, scanner, comparator, gameFactory);
        menu.displayMenu();
    }
}

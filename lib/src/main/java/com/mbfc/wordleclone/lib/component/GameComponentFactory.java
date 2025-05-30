package com.mbfc.wordleclone.lib.component;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.game.GameFactory;
import com.mbfc.wordleclone.lib.menu.WordListManager;
import com.mbfc.wordleclone.lib.parser.Parser;
import java.util.List;

/**
 * Provides the components needed to initialize a game.
 *
 * @param <T> the type used in the game (e.g., String)
 */
public interface GameComponentFactory<T> {

    /**
     * Creates a new WordListManager instance using a specific parser.
     *
     * @return a new WordListManager for type T
     */
    WordListManager<T> createWordListManager();

    /**
     * Creates a new comparator for the game.
     *
     * @return a new Comparator for type T
     */
    Comparator<T> createComparator();

    /**
     * Creates a new parser for type T.
     *
     * @return a new Parser for type T
     */
    Parser<T> createParser();

    /**
     * Creates a new GameFactory.
     *
     * @return a GameFactory that produces Game<T,List<T>> instances
     */
    GameFactory<T, List<T>> createGameFactory();

    /**
     * Returns a name for this type of game.
     *
     * @return a string with the type name
     */
    String getTypeName();
}

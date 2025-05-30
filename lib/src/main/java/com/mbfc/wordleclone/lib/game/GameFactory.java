package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Functional interface for creating a new game instance.
 *
 * <p>This interface allows you to create a new game by providing a comparator, a valid guess list,
 * and the maximum number of attempts.
 *
 * @param <T> the type of values used in the game (e.g., String)
 * @param <U> the type of the guess list (generally a collection of T elements)
 */
@FunctionalInterface
public interface GameFactory<T, U> {

    /**
     * Creates a new game instance with the given parameters.
     *
     * @param comparator the comparator used for comparing the guess against the target
     * @param guessList  the list of valid guesses
     * @param maxTries   the maximum number of allowed attempts
     * @return a new game instance
     * @throws NoSuchElementException if the guess list is empty or invalid
     */
    Game<T, U> createGame(Comparator<T> comparator, U guessList, int maxTries)
        throws NoSuchElementException;
}

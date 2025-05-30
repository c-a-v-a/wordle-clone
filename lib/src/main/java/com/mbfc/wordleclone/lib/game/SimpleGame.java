package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.comparator.CompareException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * An implementation of the {@link Game} class using strings.
 *
 * <p>This class implements a logic for a standard wordle game. Each guess is a {@code String} and
 * the guess list is just {@code List<String>}. It follows the standard rules of wordle.
 */
public class SimpleGame extends Game<String, List<String>> {
  /** Creates a new game instance, using {@link Game} constructor. */
  public SimpleGame(Comparator<String> comparator, List<String> guessList, int tries)
      throws NoSuchElementException {
    super(comparator, guessList, tries);
  }

  /** {@inheritDoc} */
  @Override
  protected void validate(String guess) throws GameException {
    if (target.length() != guess.length()) {
      throw new GameException("Invalid guess. The length of guess and target does not match.");
    } else if (!guessList.contains(guess)) {
      throw new GameException("Invalid guess. The guess is not in the word list.");
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void selectRandomTarget() throws NoSuchElementException {
    if (guessList.size() < 1) {
      throw new NoSuchElementException("Cannot select random element. The list is empty.");
    }

    Random rand = new Random();
    target = guessList.get(rand.nextInt(guessList.size()));
  }

  /** {@inheritDoc} */
  @Override
  public GameBoard<String> play(String guess) throws CompareException, GameException {
    validate(guess);

    List<ComparatorResult> result = comparator.compare(guess, target);

    triesLeft--;

    board.add(result, guess);
    isGameFinished(result);

    return board;
  }
}

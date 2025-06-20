package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.json.StringField;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeMap;

/**
 * An implementation of the {@link Game} class using objects.
 *
 * <p>This class implements a logic for a standard wordle game, similiar to LoLdle and games like
 * that.
 */
public class SimpleObjectGame extends Game<TreeMap<String, Field>, List<TreeMap<String, Field>>> {
  private final String key;

  /**
   * Creates a new game instance, using {@link Game} constructor.
   *
   * @param comparator {@code String} comparator that determines correctness of the guess
   * @param guessList the {@code List<String>} of valid guesses
   * @param tries the maximum number of guesses that user can make
   */
  public SimpleObjectGame(
      Comparator<TreeMap<String, Field>> comparator,
      List<TreeMap<String, Field>> guessList,
      int tries,
      String key)
      throws NoSuchElementException {
    super(TreeMap.class, comparator, guessList, tries);

    this.key = key;
  }

  /** {@inheritDoc} */
  @Override
  protected void validate(TreeMap<String, Field> guess) throws GameException {
    if (target.size() != guess.size()) {
      throw new GameException("Invalid guess. The length of guess and target does not match.");
    } else if (!guessList.contains(guess)) {
      throw new GameException("Invalid guess. The guess is not in the word list.");
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void selectRandomTarget() throws NoSuchElementException {
    if (guessList.isEmpty()) {
      throw new NoSuchElementException("Cannot select random element. The list is empty.");
    }

    Random rand = new Random();
    target = guessList.get(rand.nextInt(guessList.size()));
  }

  /** {@inheritDoc} */
  @Override
  protected TreeMap<String, Field> convertGuess(String guess) throws GameException {
    StringField guessField = new StringField(guess);
    TreeMap<String, Field> converted =
        guessList.stream()
            .filter(x -> ((StringField) x.get(key)).equal(guessField))
            .findFirst()
            .orElseThrow(
                () -> new GameException("Invalid guess. The guess is not in the word list."));

    return converted;
  }

  /** {@inheritDoc} */
  @Override
  public String getFinalGameMessage() {
    if (getPlayerWon()) {
      return "\nCongratulations. You won in " + getTriesUsed() + " guesses.";
    } else {
      return "\nYou lost. The target was: " + getTarget();
    }
  }

  @Override
  public String getTarget() {
    return ((StringField) target.get(key)).getValue();
  }
}

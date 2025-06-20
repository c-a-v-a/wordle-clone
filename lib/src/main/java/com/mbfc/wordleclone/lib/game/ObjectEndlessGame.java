package com.mbfc.wordleclone.lib.game;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.json.StringField;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeMap;

/**
 * Concrete class implementing endless mode for a word game. Inherits all common endless game logic
 * from {@link EndlessGame} and provides implementations specific to operations on Strings.
 */
public class ObjectEndlessGame
    extends EndlessGame<TreeMap<String, Field>, List<TreeMap<String, Field>>> {
  private final String key;

  /**
   * Creates a new instance of ObjectEndlessGame.
   *
   * @param comparator the comparator that determines the correctness of a guess
   * @param guessList the list of valid words for guesses
   * @param initialTries the initial number of tries (lives)
   * @param bonusTriesOnWin the bonus lives awarded when a round is won
   * @throws NoSuchElementException if the guess list is empty
   */
  public ObjectEndlessGame(
      Comparator<TreeMap<String, Field>> comparator,
      List<TreeMap<String, Field>> guessList,
      int initialTries,
      int bonusTriesOnWin,
      String key)
      throws NoSuchElementException {
    super(TreeMap.class, comparator, guessList, initialTries, bonusTriesOnWin);

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

  @Override
  public String getTarget() {
    return ((StringField) target.get(key)).getValue();
  }
}

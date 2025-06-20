package com.mbfc.wordleclone.lib.comparator;

import com.mbfc.wordleclone.lib.json.DateTimeField;
import com.mbfc.wordleclone.lib.json.DoubleField;
import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.json.IntegerField;
import com.mbfc.wordleclone.lib.json.SetField;
import com.mbfc.wordleclone.lib.json.StringField;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;

/**
 * Compares two {@code TreeMap<String, Field>} instances by comparing the values associated with
 * each key using type-specific comparison logic.
 *
 * <p>Supports {@link StringField}, {@link IntegerField}, {@link DoubleField}, {@link
 * DateTimeField}, and {@link SetField}. Throws an exception if maps are different sizes or contain
 * unsupported field types.
 */
public class ObjectComparator implements Comparator<TreeMap<String, Field>> {

  /**
   * Compares two maps of {@code Field} objects, key by key, and returns a list of {@link
   * ComparatorResult} for each entry.
   *
   * @param guess the guess map, where each value is a {@link Field}
   * @param target the target map, to compare against
   * @return list of comparison results in the same order as the map's keys
   * @throws CompareException if the maps differ in size or contain unsupported field types
   */
  @Override
  public List<ComparatorResult> compare(TreeMap<String, Field> guess, TreeMap<String, Field> target)
      throws CompareException {
    if (guess.size() != target.size()) {
      throw new CompareException("Cannot compare guess to target. Maps have different size.");
    }

    List<ComparatorResult> result = new ArrayList<>(guess.size());

    for (String key : guess.keySet()) {
      Field guessValue = guess.get(key);
      Field targetValue = target.get(key);

      if (guessValue.getType() == String.class) {
        result.add(cmp((StringField) guessValue, (StringField) targetValue));
      } else if (guessValue.getType() == Integer.class) {
        result.add(cmp((IntegerField) guessValue, (IntegerField) targetValue));
      } else if (guessValue.getType() == Double.class) {
        result.add(cmp((DoubleField) guessValue, (DoubleField) targetValue));
      } else if (guessValue.getType() == LocalDateTime.class) {
        result.add(cmp((DateTimeField) guessValue, (DateTimeField) targetValue));
      } else if (guessValue.getType() == HashSet.class) {
        result.add(cmp((SetField) guessValue, (SetField) targetValue));
      } else {
        throw new CompareException("Cannot compare guess to target. Unknown type.");
      }
    }

    return result;
  }

  private ComparatorResult cmp(DateTimeField guess, DateTimeField target) {
    if (guess.equal(target)) {
      return ComparatorResult.CORRECT;
    } else if (guess.higher(target)) {
      return ComparatorResult.TOO_HIGH;
    } else if (guess.lower(target)) {
      return ComparatorResult.TOO_LOW;
    }

    return ComparatorResult.INCORRECT;
  }

  private ComparatorResult cmp(DoubleField guess, DoubleField target) {
    if (guess.equal(target)) {
      return ComparatorResult.CORRECT;
    } else if (guess.higher(target)) {
      return ComparatorResult.TOO_HIGH;
    } else if (guess.lower(target)) {
      return ComparatorResult.TOO_LOW;
    }

    return ComparatorResult.INCORRECT;
  }

  private ComparatorResult cmp(IntegerField guess, IntegerField target) {
    if (guess.equal(target)) {
      return ComparatorResult.CORRECT;
    } else if (guess.higher(target)) {
      return ComparatorResult.TOO_HIGH;
    } else if (guess.lower(target)) {
      return ComparatorResult.TOO_LOW;
    }

    return ComparatorResult.INCORRECT;
  }

  private ComparatorResult cmp(SetField guess, SetField target) {
    if (guess.equal(target)) {
      return ComparatorResult.CORRECT;
    } else if (guess.partial(target)) {
      return ComparatorResult.PARTIAL;
    }

    return ComparatorResult.INCORRECT;
  }

  private ComparatorResult cmp(StringField guess, StringField target) {
    if (guess.equal(target)) {
      return ComparatorResult.CORRECT;
    } else if (guess.partial(target)) {
      return ComparatorResult.PARTIAL;
    }

    return ComparatorResult.INCORRECT;
  }
}

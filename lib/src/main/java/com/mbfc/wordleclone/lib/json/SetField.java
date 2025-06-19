package com.mbfc.wordleclone.lib.json;

import java.util.HashSet;

/** Implementation of {@link Field} for the {@code HashSet<String>} values. */
public class SetField extends Field<HashSet<String>> {

  /**
   * Creates a new {@code HashSetField} from the given value.
   *
   * @param value the value of the field.
   */
  public SetField(HashSet<String> value) {
    super(value, HashSet.class);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equal(Field<HashSet<String>> other) {
    return value.equals(other.getValue());
  }

  /** {@inheritDoc} */
  @Override
  public boolean partial(Field<HashSet<String>> other) {
    for (String element : value) {
      if (other.getValue().contains(element)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Method not applicable to the {@code HashSet<String>} type.
   *
   * <p>Always retuns {@code false}
   *
   * <p>{@inheritDoc}
   */
  @Override
  public boolean higher(Field<HashSet<String>> other) {
    return false;
  }

  /**
   * Method not applicable to the {@code HashSet<String>} type.
   *
   * <p>Always retuns {@code false}
   *
   * <p>{@inheritDoc}
   */
  @Override
  public boolean lower(Field<HashSet<String>> other) {
    return false;
  }
}

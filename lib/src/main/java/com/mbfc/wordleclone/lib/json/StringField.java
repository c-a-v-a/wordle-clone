package com.mbfc.wordleclone.lib.json;

/** Implementation of {@link Field} for the {@code String} values. */
public class StringField extends Field<String> {

  /**
   * Creates a new {@code StringField} from the given value.
   *
   * @param value the value of the field.
   */
  public StringField(String value) {
    super(value, String.class);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equal(Field<String> other) {
    return value.toLowerCase().equals(other.getValue().toLowerCase());
  }

  /** {@inheritDoc} */
  @Override
  public boolean partial(Field<String> other) {
    return value.contains(other.getValue()) || other.getValue().contains(value);
  }

  /**
   * Method not applicable to the {@code String} type.
   *
   * <p>Always retuns {@code false}
   *
   * <p>{@inheritDoc}
   */
  @Override
  public boolean higher(Field<String> other) {
    return false;
  }

  /**
   * Method not applicable to the {@code String} type.
   *
   * <p>Always retuns {@code false}
   *
   * <p>{@inheritDoc}
   */
  @Override
  public boolean lower(Field<String> other) {
    return false;
  }
}

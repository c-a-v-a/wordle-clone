package com.mbfc.wordleclone.lib.json;

/** Implementation of {@link Field} for the {@code Integer} values. */
public class IntegerField extends Field<Integer> {

  /**
   * Creates a new {@code IntegerField} from the given value.
   *
   * @param value the value of the field.
   */
  public IntegerField(Integer value) {
    super(value, Integer.class);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equal(Field<Integer> other) {
    return value.equals(other.getValue());
  }

  /**
   * Method not applicable to the {@code Integer} type.
   *
   * <p>Always retuns {@code false}
   *
   * <p>{@inheritDoc}
   */
  @Override
  public boolean partial(Field<Integer> other) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean higher(Field<Integer> other) {
    return value > other.getValue();
  }

  /** {@inheritDoc} */
  @Override
  public boolean lower(Field<Integer> other) {
    return value < other.getValue();
  }
}

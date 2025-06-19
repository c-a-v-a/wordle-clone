package com.mbfc.wordleclone.lib.json;

/** Implementation of {@link Field} for the {@code Double} values. */
public class DoubleField extends Field<Double> {

  /**
   * Creates a new {@code DoubleField} from the given value.
   *
   * @param value the value of the field.
   */
  public DoubleField(Double value) {
    super(value, Double.class);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equal(Field<Double> other) {
    return value.equals(other.getValue());
  }

  /**
   * Method not applicable to the {@code Double} type.
   *
   * <p>Always retuns {@code false}
   *
   * <p>{@inheritDoc}
   */
  @Override
  public boolean partial(Field<Double> other) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean higher(Field<Double> other) {
    return value > other.getValue();
  }

  /** {@inheritDoc} */
  @Override
  public boolean lower(Field<Double> other) {
    return value < other.getValue();
  }
}

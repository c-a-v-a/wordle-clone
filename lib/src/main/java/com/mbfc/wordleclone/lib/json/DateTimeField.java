package com.mbfc.wordleclone.lib.json;

import java.time.LocalDateTime;

/** Implementation of {@link Field} for the {@code LocalDateTime} type. */
public class DateTimeField extends Field<LocalDateTime> {
  /**
   * Constructs a new {@code DateTimeField} from the given value.
   *
   * @param value the value of the field
   */
  public DateTimeField(LocalDateTime value) {
    super(value, LocalDateTime.class);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equal(Field<LocalDateTime> other) {
    return value.equals(other.getValue());
  }

  /**
   * Method not applicable to the {@code LocalDateTime} type.
   *
   * <p>Always retuns {@code false}
   *
   * <p>{@inheritDoc}
   */
  @Override
  public boolean partial(Field<LocalDateTime> other) {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean higher(Field<LocalDateTime> other) {
    return value.isAfter(other.getValue());
  }

  /** {@inheritDoc} */
  @Override
  public boolean lower(Field<LocalDateTime> other) {
    return value.isBefore(other.getValue());
  }
}

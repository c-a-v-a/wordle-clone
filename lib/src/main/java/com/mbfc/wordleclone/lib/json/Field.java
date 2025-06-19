package com.mbfc.wordleclone.lib.json;

/**
 * Represents a typed field with value.
 *
 * <p>Subclasses need to define the comparison operations for a given data type.
 *
 * @param <T> tye type of the value held by this field
 */
public abstract class Field<T> {

  /** Value stored in this field. */
  protected final T value;

  /** The type of stored value. */
  protected final Class<?> type;

  /**
   * Constructs new {@code Field} with the specified value and type.
   *
   * @param value the value of the field
   * @param type tye {@code Class} representing the value type e.g. {@code String.class}
   */
  public Field(T value, Class<?> type) {
    this.value = value;
    this.type = type;
  }

  public T getValue() {
    return value;
  }

  public Class<?> getType() {
    return type;
  }

  /**
   * Checks if the field's value is equal to the other field's value.
   *
   * @param other the other field to compare with
   * @return {@code true} if the values are equal, {@code false} otherwise
   */
  public abstract boolean equal(Field<T> other);

  /**
   * Check if two field's values partialy match (e.g. lists have common elements).
   *
   * @param other the other field to compare with
   * @return {@code true} if the values match partialy, {@code false} otherwise
   */
  public abstract boolean partial(Field<T> other);

  /**
   * Checks if this field's value is greater than the other field's value.
   *
   * @param other the other field to compare with
   * @return {@code true} if this value is higher, {@code false} otherwise
   */
  public abstract boolean higher(Field<T> other);

  /**
   * Checks if this field's value is less than the other field's value.
   *
   * @param other the other field to compare with
   * @return {@code true} if this value is lower, {@code false} otherwise
   */
  public abstract boolean lower(Field<T> other);
}

package com.mbfc.wordleclone.lib.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

/** Test suite for {@link DateTimeField}. */
public class DateTimeFieldTest {
  @Test
  void equal_differentValues_false() {
    // given
    DateTimeField a = new DateTimeField(LocalDateTime.of(2024, 1, 1, 10, 0));
    DateTimeField b = new DateTimeField(LocalDateTime.of(2025, 1, 1, 10, 0));

    // when
    boolean result = a.equal(b);

    // then
    assertFalse(result);
  }

  @Test
  void equal_sameValues_true() {
    // given
    LocalDateTime now = LocalDateTime.now();
    DateTimeField a = new DateTimeField(now);
    DateTimeField b = new DateTimeField(now);

    // when
    boolean result = a.equal(b);

    // then
    assertTrue(result);
  }

  @Test
  void higher_aBeforeB_false() {
    // given
    LocalDateTime now = LocalDateTime.now();
    DateTimeField a = new DateTimeField(now.minusDays(1));
    DateTimeField b = new DateTimeField(now);

    // when
    boolean result = a.higher(b);

    // then
    assertFalse(result);
  }

  @Test
  void higher_aAfterB_true() {
    // given
    LocalDateTime now = LocalDateTime.now();
    DateTimeField a = new DateTimeField(now);
    DateTimeField b = new DateTimeField(now.minusDays(1));

    // when
    boolean result = a.higher(b);

    // then
    assertTrue(result);
  }

  @Test
  void lower_aBeforeB_true() {
    // given
    LocalDateTime now = LocalDateTime.now();
    DateTimeField a = new DateTimeField(now.minusDays(1));
    DateTimeField b = new DateTimeField(now);

    // when
    boolean result = a.lower(b);

    // then
    assertTrue(result);
  }

  @Test
  void lower_aAfterB_false() {
    // given
    LocalDateTime now = LocalDateTime.now();
    DateTimeField a = new DateTimeField(now);
    DateTimeField b = new DateTimeField(now.minusDays(1));

    // when
    boolean result = a.lower(b);

    // then
    assertFalse(result);
  }
}

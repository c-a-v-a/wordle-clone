package com.mbfc.wordleclone.lib.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Test suite for {@link DoubleField}. */
public class DoubleFieldTest {
  @Test
  void equal_differentValues_false() {
    // given
    DoubleField a = new DoubleField(3.14);
    DoubleField b = new DoubleField(2.12);

    // when
    boolean result = a.equal(b);

    // then
    assertFalse(result);
  }

  @Test
  void equal_sameValues_true() {
    // given
    DoubleField a = new DoubleField(3.14);
    DoubleField b = new DoubleField(3.14);

    // when
    boolean result = a.equal(b);

    // then
    assertTrue(result);
  }
}

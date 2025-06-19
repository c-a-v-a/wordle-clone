package com.mbfc.wordleclone.lib.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Test suite for {@link IntegerField}. */
public class IntegerFieldTest {
  @Test
  void equal_differentValues_false() {
    // given
    IntegerField a = new IntegerField(31);
    IntegerField b = new IntegerField(22);

    // when
    boolean result = a.equal(b);

    // then
    assertFalse(result);
  }

  @Test
  void equal_sameValues_true() {
    // given
    IntegerField a = new IntegerField(22);
    IntegerField b = new IntegerField(22);

    // when
    boolean result = a.equal(b);

    // then
    assertTrue(result);
  }
}

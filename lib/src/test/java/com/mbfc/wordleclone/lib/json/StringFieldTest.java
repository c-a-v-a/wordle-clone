package com.mbfc.wordleclone.lib.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Test suite for {@link StringField}. */
public class StringFieldTest {
  @Test
  void equal_differentValues_false() {
    // given
    StringField a = new StringField("abc");
    StringField b = new StringField("xyz");

    // when
    boolean result = a.equal(b);

    // then
    assertFalse(result);
  }

  @Test
  void equal_sameValues_true() {
    // given
    StringField a = new StringField("abc");
    StringField b = new StringField("abc");

    // when
    boolean result = a.equal(b);

    // then
    assertTrue(result);
  }

  @Test
  void equal_differentCase_true() {
    // given
    StringField a = new StringField("abc");
    StringField b = new StringField("ABC");

    // when
    boolean result = a.equal(b);

    // then
    assertTrue(result);
  }

  @Test
  void partial_different_false() {
    // given
    StringField a = new StringField("abc");
    StringField b = new StringField("xyz");

    // when
    boolean result = a.partial(b);

    // then
    assertFalse(result);
  }

  @Test
  void partial_aContainsB_true() {
    // given
    StringField a = new StringField("abc");
    StringField b = new StringField("a");

    // when
    boolean result = a.partial(b);

    // then
    assertTrue(result);
  }

  @Test
  void partial_bContainsA_true() {
    // given
    StringField a = new StringField("a");
    StringField b = new StringField("abc");

    // when
    boolean result = a.partial(b);

    // then
    assertTrue(result);
  }
}

package com.mbfc.wordleclone.lib.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Test suite for {@link SetField}. */
public class SetFieldTest {
  @Test
  void equal_differentValues_false() {
    // given
    SetField a = new SetField(new HashSet<>(Set.of("a", "b", "c")));
    SetField b = new SetField(new HashSet<>(Set.of("a", "b")));

    // when
    boolean result = a.equal(b);

    // then
    assertFalse(result);
  }

  @Test
  void equal_sameValues_true() {
    // given
    SetField a = new SetField(new HashSet<>(Set.of("a", "b", "c")));
    SetField b = new SetField(new HashSet<>(Set.of("a", "b", "c")));

    // when
    boolean result = a.equal(b);

    // then
    assertTrue(result);
  }

  @Test
  void partial_different_false() {
    // given
    SetField a = new SetField(new HashSet<>(Set.of("a", "b", "c")));
    SetField b = new SetField(new HashSet<>(Set.of("x", "y", "z")));

    // when
    boolean result = a.partial(b);

    // then
    assertFalse(result);
  }

  @Test
  void partial_oneCommon_true() {
    // given
    SetField a = new SetField(new HashSet<>(Set.of("a", "b", "c")));
    SetField b = new SetField(new HashSet<>(Set.of("a", "y", "z")));

    // when
    boolean result = a.partial(b);

    // then
    assertTrue(result);
  }
}

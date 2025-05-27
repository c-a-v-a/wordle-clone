package com.mbfc.wordleclone.lib.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test suite for {@link StringComparator}. */
public class StringComparatorTest {
  private StringComparator comparator;

  @BeforeEach
  void setUp() {
    comparator = new StringComparator();
  }

  /** Test that comparing strings of different lengths throws an exception. */
  @Test
  void compare_differentLengths_throwsException() throws CompareException {
    // given
    String guess = "abc";
    String target = "a";
    String expectedMessage = "Cannot compare \"abc\" to \"a\". Strings have different length.";

    // when
    Exception exception =
        assertThrows(
            CompareException.class,
            () -> {
              comparator.compare(guess, target);
            });

    // then
    assertEquals(expectedMessage, exception.getMessage());
  }

  /** Test that comparing empty string is valid and yields an empty list. */
  @Test
  void compare_emptyStrings_emptyList() throws CompareException {
    // given
    String guess = "";
    String target = "";

    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  /** Test that comparing different strings gives correct result. */
  @Test
  void compare_differentStrings_listOfIncorrect() throws CompareException {
    // given
    String guess = "abc";
    String target = "xyz";

    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertTrue(result.stream().allMatch(x -> x.equals(ComparatorResult.INCORRECT)));
  }

  /**
   * Test that comparing strings build from the same letters in differen order gives correct result.
   */
  @Test
  void compare_similiarStrings_listOfPartial() throws CompareException {
    // given
    String guess = "abc";
    String target = "cab";

    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertTrue(result.stream().allMatch(x -> x.equals(ComparatorResult.PARTIAL)));
  }

  /** Test that comparing the same strings gives the correct result. */
  @Test
  void compare_identicalStrings_listOfCorrect() throws CompareException {
    // given
    String guess = "abc";
    String target = "abc";

    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertTrue(result.stream().allMatch(x -> x.equals(ComparatorResult.CORRECT)));
  }
}

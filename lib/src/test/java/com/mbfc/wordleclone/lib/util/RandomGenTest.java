package com.mbfc.wordleclone.lib.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Test suite for {@link RandomGen}. */
public class RandomGenTest {

  /** Test thet {@link RandomGen#generate(int)} generates a empty string for negative length. */
  @Test
  void generate_negativeLength_emptyString() {
    // given
    int length = -1;

    // when
    String generated = RandomGen.generate(length);

    // then
    assertEquals("", generated);
  }

  /** Test thet {@link RandomGen#generate(int)} generates a empty string for length = 0. */
  @Test
  void generate_zeroLength_emptyString() {
    // given
    int length = 0;

    // when
    String generated = RandomGen.generate(length);

    // then
    assertEquals("", generated);
  }

  /** Test thet {@link RandomGen#generate(int)} generates a string of correct length. */
  @Test
  void generate_normalLength_correctLength() {
    // given
    int length = 5;

    // when
    String generated = RandomGen.generate(length);

    // then
    assertEquals(length, generated.length());
  }

  /** Test thet {@link RandomGen#generate(int)} generates a string from the letters set. */
  @Test
  void generate_normalLength_everyCharacterIsInLetters() {
    // given
    String letters = RandomGen.getLetters();

    // when
    String result = RandomGen.generate(100);

    // then
    for (char c : result.toCharArray()) {
      assertTrue(letters.indexOf(c) >= 0);
    }
  }
}

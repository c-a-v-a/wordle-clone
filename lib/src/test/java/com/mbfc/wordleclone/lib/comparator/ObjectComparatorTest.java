package com.mbfc.wordleclone.lib.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.json.StringField;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test suite for {@link ObjectComparator}. */
public class ObjectComparatorTest {
  private ObjectComparator comparator;

  @BeforeEach
  void setUp() {
    comparator = new ObjectComparator();
  }

  @Test
  void compare_differentLengths_throwsException() throws CompareException {
    // given
    TreeMap<String, Field> guess =
        new TreeMap<>(
            Map.of("name", new StringField("name"), "lastName", new StringField("lastName")));
    TreeMap<String, Field> target = new TreeMap<>(Map.of("name", new StringField("name")));
    String expectedMessage = "Cannot compare guess to target. Maps have different size.";

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

  @Test
  void compare_emptyMaps_emptyList() throws CompareException {
    // given
    TreeMap<String, Field> guess = new TreeMap<>();
    TreeMap<String, Field> target = new TreeMap<>();
    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  @Test
  void compare_differentMaps_listOfIncorrect() throws CompareException {
    // given
    TreeMap<String, Field> guess =
        new TreeMap<>(
            Map.of("name", new StringField("name"), "lastName", new StringField("lastName")));
    TreeMap<String, Field> target =
        new TreeMap<>(Map.of("name", new StringField("xxxx"), "lastName", new StringField("xxxx")));

    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertTrue(result.stream().allMatch(x -> x.equals(ComparatorResult.INCORRECT)));
  }

  @Test
  void compare_similiarValues_listOfPartial() throws CompareException {
    // given
    TreeMap<String, Field> guess =
        new TreeMap<>(
            Map.of("name", new StringField("name"), "lastName", new StringField("lastName2")));
    TreeMap<String, Field> target =
        new TreeMap<>(
            Map.of("name", new StringField("name2"), "lastName", new StringField("lastName")));

    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertTrue(result.stream().allMatch(x -> x.equals(ComparatorResult.PARTIAL)));
  }

  @Test
  void compare_identicalValues_listOfCorrect() throws CompareException {
    // given
    TreeMap<String, Field> guess =
        new TreeMap<>(
            Map.of("name", new StringField("name"), "lastName", new StringField("lastName")));
    TreeMap<String, Field> target =
        new TreeMap<>(
            Map.of("name", new StringField("name"), "lastName", new StringField("lastName")));

    // when
    List<ComparatorResult> result = comparator.compare(guess, target);

    // then
    assertNotNull(result);
    assertTrue(result.stream().allMatch(x -> x.equals(ComparatorResult.CORRECT)));
  }
}

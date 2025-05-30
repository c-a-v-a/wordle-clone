package com.mbfc.wordleclone.lib.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test suite for {@link SimpleStringParser}. */
public class SimpleStringParserTest {
  private SimpleStringParser parser;

  @BeforeEach
  void setUp() {
    parser = new SimpleStringParser();
  }

  /**
   * Test that {@link SimpleStringParser#parseResource(string)} throws an exception, when it doesn't
   * find the resource file.
   */
  @Test
  void parseResource_missingResource_throwsException() throws IOException {
    // given
    String resourcePath = "non_existing_resource.txt";
    String expectedMessage = "Resource not found: non_existing_resource.txt";

    // when
    Exception exception =
        assertThrows(
            IOException.class,
            () -> {
              parser.parseResource(resourcePath);
            });

    // then
    assertEquals(expectedMessage, exception.getMessage());
  }

  /**
   * Test that {@link SimpleStringParser#parseResource(string)} correctly parses empty resource
   * file.
   */
  @Test
  void praseResource_emptyFile_returnsEmptyList() throws IOException {
    // given
    String resourcePath = "empty_resource.txt";

    // when
    List<String> result = parser.parseResource(resourcePath);

    // then
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  /**
   * Test that {@link SimpleStringParser#parseResource(string)} correctly parses the resource file.
   */
  @Test
  void parseResource_fileExists_returnsExpectedList() throws IOException {
    // given
    String resourcePath = "test_resource.txt";

    // when
    List<String> result = parser.parseResource(resourcePath);

    // then
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("one", result.get(0));
    assertEquals("two", result.get(1));
    assertEquals("three", result.get(2));
  }

  /** Test that parsing nonexistent files throws and exception. */
  @Test
  void parseFile_nonexistentFile_throwsException() throws IOException {
    // given
    String filePath = "non_existing_file.txt";
    String expectedMessage = "File not found: non_existing_file.txt";

    // when
    Exception exception =
        assertThrows(
            IOException.class,
            () -> {
              parser.parseFile(filePath);
            });

    // then
    assertEquals(expectedMessage, exception.getMessage());
  }

  /** Test that parsing empty file yields an empty list. */
  @Test
  void parseFile_emptyFile_returnsEmptyList() throws IOException {
    // given
    Path tempFile = Files.createTempFile("empty_file", ".txt");

    // when
    List<String> result = parser.parseFile(tempFile.toString());

    // then
    assertNotNull(result);
    assertEquals(0, result.size());
  }

  /** Test that parsing an existing file has the correct behavior. */
  @Test
  void parseFile_fileExists_returnsExpectedList() throws IOException {
    // given
    Path tempFile = Files.createTempFile("test_file", ".txt");
    Files.write(tempFile, List.of("one", "two", "three"));

    // when
    List<String> result = parser.parseFile(tempFile.toString());

    // then
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("one", result.get(0));
    assertEquals("two", result.get(1));
    assertEquals("three", result.get(2));
  }
}

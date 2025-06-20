package com.mbfc.wordleclone.lib.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.util.Pair;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test suite for {@link JsonParser}. */
public class JsonParserTest {
  private JsonParser parser;

  @BeforeEach
  void setUp() {
    parser = new JsonParser();
  }

  @Test
  void parseResource_missingResource_throwsException() {
    // given
    String resourcePath = "non_existing_resource.json";
    String expectedMessage = "Resource not found: non_existing_resource.json";

    // when
    Exception exception = assertThrows(IOException.class, () -> parser.parseResource(resourcePath));

    // then
    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void parseResource_emptyFile_throwsException() throws IOException {
    // given
    String resourcePath = "empty_resource.json";

    // when

    // then
    Exception exception = assertThrows(IOException.class, () -> parser.parseResource(resourcePath));
  }

  @Test
  void parseResource_fileExists_returnsExpected() throws IOException {
    // given
    String resourcePath = "/test_resource.json";

    // when
    Pair<String, List<TreeMap<String, Field>>> result = parser.parseResource(resourcePath);

    // then
    assertNotNull(result);
    assertEquals("name", result.left());
    assertEquals(2, result.right().size());
    assertEquals(5, result.right().get(0).size());
    assertEquals(5, result.right().get(1).size());
    assertEquals(String.class, result.right().get(1).get("name").getType());
    assertEquals(LocalDateTime.class, result.right().get(1).get("birth").getType());
    assertEquals(Double.class, result.right().get(1).get("salary").getType());
    assertEquals(Integer.class, result.right().get(1).get("cars").getType());
    assertEquals(HashSet.class, result.right().get(1).get("kids").getType());
    assertEquals("john", result.right().get(1).get("name").getValue());
    assertEquals(
        LocalDateTime.parse("2000-01-01 11:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        result.right().get(1).get("birth").getValue());
    assertEquals(2000.50, result.right().get(1).get("salary").getValue());
    assertEquals(2, result.right().get(1).get("cars").getValue());
    assertEquals(2, ((HashSet<String>) result.right().get(1).get("kids").getValue()).size());
  }

  @Test
  void parseFile_nonexistentFile_throwsException() {
    // given
    String filePath = "non_existing_file.json";
    String expectedMessage = "File not found: non_existing_file.json";

    // when

    // then
    Exception exception = assertThrows(IOException.class, () -> parser.parseFile(filePath));
  }

  @Test
  void parseFile_emptyFile_throwsException() throws IOException {
    // given
    Path tempFile = Files.createTempFile("empty_file", ".jon");

    // when

    // then
    Exception exception =
        assertThrows(IOException.class, () -> parser.parseFile(tempFile.toString()));
  }

  @Test
  void parseFile_fileExists_returnsExpectedList() throws IOException {
    // given
    Path tempFile = Files.createTempFile("test_file", ".json");
    String jsonString =
        """
        {
          "key": "name",
          "fields": {
            "name": "string",
            "birth": "datetime",
            "salary": "double",
            "cars": "integer",
            "kids": "set"
          },
          "data": [
            {
              "name": "john",
              "birth": "2000-01-01 11:00",
              "salary": "2000.50",
              "cars": "2",
              "kids": "kamil,asia"
            },
            {
              "name": "john",
              "birth": "2000-01-01 11:00",
              "salary": "2000.50",
              "cars": "2",
              "kids": "kamil,asia"
            }
          ]
        }
        """;
    Files.writeString(tempFile, jsonString, StandardCharsets.UTF_8);

    // when
    Pair<String, List<TreeMap<String, Field>>> result = parser.parseFile(tempFile.toString());

    // then
    assertNotNull(result);
    assertEquals("name", result.left());
    assertEquals(2, result.right().size());
    assertEquals(5, result.right().get(0).size());
    assertEquals(5, result.right().get(1).size());
    assertEquals(String.class, result.right().get(1).get("name").getType());
    assertEquals(LocalDateTime.class, result.right().get(1).get("birth").getType());
    assertEquals(Double.class, result.right().get(1).get("salary").getType());
    assertEquals(Integer.class, result.right().get(1).get("cars").getType());
    assertEquals(HashSet.class, result.right().get(1).get("kids").getType());
    assertEquals("john", result.right().get(1).get("name").getValue());
    assertEquals(
        LocalDateTime.parse("2000-01-01 11:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        result.right().get(1).get("birth").getValue());
    assertEquals(2000.50, result.right().get(1).get("salary").getValue());
    assertEquals(2, result.right().get(1).get("cars").getValue());
    assertEquals(2, ((HashSet<String>) result.right().get(1).get("kids").getValue()).size());
  }
}

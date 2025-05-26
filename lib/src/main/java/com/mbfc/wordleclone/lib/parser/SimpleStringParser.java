package com.mbfc.wordleclone.lib.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser that reads a file or classpath resource and parses it into a list of strings.
 *
 * <p>This parser expects the input file to contain multiple words, each separated by new line. Each
 * line is treated as a separate entry in the resulting list.
 *
 * <p>Example input:
 *
 * <pre>
 * one
 * two
 * three
 * </pre>
 *
 * <p>Would result in a list: {@code ["one", "two", "three"]}
 *
 * <p>This class implements the {@link Parser} interface for {@code String} elements.
 *
 * @see Parser
 */
public class SimpleStringParser implements Parser<String> {

  /**
   * {@inheritDoc}
   *
   * <p>This implementation reads the classpath resource line by line and returns each line as a
   * separate string in the list.
   */
  @Override
  public List<String> parseResource(String resourcePath) throws IOException {
    InputStream inputStream =
        SimpleStringParser.class.getClassLoader().getResourceAsStream(resourcePath);

    if (inputStream == null) {
      throw new IOException("Resource not found: " + resourcePath);
    }

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

      return reader.lines().collect(Collectors.toList());
    } catch (Exception e) {
      throw new IOException("Unable to read the resource file: " + resourcePath, e);
    }
  }

  // TODO: Implement the simple parser for the system files.
  @Override
  public List<String> parseFile(String filePath) throws IOException {
    throw new UnsupportedOperationException("Not yet implemented");
  }
}

package com.mbfc.wordleclone.lib.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbfc.wordleclone.lib.json.DateTimeField;
import com.mbfc.wordleclone.lib.json.DoubleField;
import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.json.IntegerField;
import com.mbfc.wordleclone.lib.json.SetField;
import com.mbfc.wordleclone.lib.json.StringField;
import com.mbfc.wordleclone.lib.util.Pair;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Parser that reads a file or classpath resource and parses it into a {@link Pair} of {@code
 * String} and {@code TreeMap<String, Field>}.
 *
 * <p>The {@code String} in the {@link Pair} is the primary key, so the thing that player will try
 * to guess. The second element of the {@link Pair} is the word list.
 *
 * <p>This parser expects the input file to be a json in a specific format. It needs a "key", that
 * will be the primary key which the player tries to guess value of, "fields" that will specify the
 * type of each data field, and "data" that will have the data, e.g.:
 *
 * <pre>
 * {
 *   "key": "name",
 *   "fields": {
 *     "name": "string",
 *     "birth": "datetime",
 *     "salary": "double",
 *     "cars": "integer",
 *     "kids": "set"
 *   },
 *   "data": [
 *     {
 *       "name": "john",
 *       "birth": "2000-01-01 11:00",
 *       "salary": "2000.50",
 *       "cars": "2",
 *       "kids": "kamil,asia"
 *     },
 *     {
 *       "name": "john",
 *       "birth": "2000-01-01 11:00",
 *       "salary": "2000.50",
 *       "cars": "2",
 *       "kids": "kamil,asia"
 *     }
 *   ]
 * }
 * </pre>
 *
 * @see Parser
 */
public class JsonParser implements Parser<Pair<String, List<TreeMap<String, Field>>>> {
  /**
   * {@inheritDoc}
   *
   * <p>This implementation reads the classpath resource json file.
   */
  @Override
  public Pair<String, List<TreeMap<String, Field>>> parseResource(String resourcePath)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    JsonData wrapper = null;
    List<TreeMap<String, Field>> wordList = new ArrayList<>();
    InputStream inputStream = JsonParser.class.getResourceAsStream(resourcePath);

    if (inputStream == null) {
      throw new IOException("Resource not found: " + resourcePath);
    }

    try {
      wrapper = mapper.readValue(inputStream, JsonData.class);
    } catch (Exception e) {
      throw new IOException("Unable to read the resource file: " + resourcePath, e);
    }

    try {
      for (Map<String, String> element : wrapper.getData()) {
        TreeMap<String, Field> x = new TreeMap<>();

        for (Map.Entry<String, String> entry : element.entrySet()) {
          switch (wrapper.getFields().get(entry.getKey())) {
            case "string":
              x.put(entry.getKey(), new StringField(entry.getValue()));
              break;
            case "integer":
              x.put(entry.getKey(), new IntegerField(Integer.parseInt(entry.getValue())));
              break;
            case "double":
              x.put(entry.getKey(), new DoubleField(Double.parseDouble(entry.getValue())));
              break;
            case "datetime":
              x.put(
                  entry.getKey(),
                  new DateTimeField(LocalDateTime.parse(entry.getValue(), formatter)));
              break;
            case "set":
              x.put(
                  entry.getKey(),
                  new SetField(new HashSet<>(Arrays.asList(entry.getValue().split(",")))));
              break;
            default:
              throw new IOException("Unable to parse the resource file: " + resourcePath);
          }
        }

        wordList.add(x);
      }
    } catch (Exception e) {
      throw new IOException("Unable to parse the resource file: " + resourcePath, e);
    }

    return new Pair(wrapper.getKey(), wordList);
  }

  /**
   * {@inheritDoc}
   *
   * <p>This implementation reads the specified json file.
   */
  @Override
  public Pair<String, List<TreeMap<String, Field>>> parseFile(String filePath) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    JsonData wrapper = null;
    List<TreeMap<String, Field>> wordList = new ArrayList<>();
    InputStream inputStream = new FileInputStream(filePath);

    if (inputStream == null) {
      throw new IOException("File not found: " + filePath);
    }

    try {
      wrapper = mapper.readValue(inputStream, JsonData.class);
    } catch (Exception e) {
      throw new IOException("Unable to read the file: " + filePath, e);
    }

    try {
      for (Map<String, String> element : wrapper.getData()) {
        TreeMap<String, Field> x = new TreeMap<>();

        for (Map.Entry<String, String> entry : element.entrySet()) {
          switch (wrapper.getFields().get(entry.getKey())) {
            case "string":
              x.put(entry.getKey(), new StringField(entry.getValue()));
              break;
            case "integer":
              x.put(entry.getKey(), new IntegerField(Integer.parseInt(entry.getValue())));
              break;
            case "double":
              x.put(entry.getKey(), new DoubleField(Double.parseDouble(entry.getValue())));
              break;
            case "datetime":
              x.put(
                  entry.getKey(),
                  new DateTimeField(LocalDateTime.parse(entry.getValue(), formatter)));
              break;
            case "set":
              x.put(
                  entry.getKey(),
                  new SetField(new HashSet<>(Arrays.asList(entry.getValue().split(",")))));
              break;
            default:
              throw new IOException("Unable to parse the file: " + filePath);
          }
        }

        wordList.add(x);
      }
    } catch (Exception e) {
      throw new IOException("Unable to parse the file: " + filePath, e);
    }

    return new Pair(wrapper.getKey(), wordList);
  }
}

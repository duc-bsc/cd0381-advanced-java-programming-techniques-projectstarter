package com.udacity.webcrawler.json;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.udacity.webcrawler.json.CrawlerConfiguration.Builder;

/**
 * A static utility class that loads a JSON configuration file.
 */
@JsonDeserialize(builder = CrawlerConfiguration.Builder.class)
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given
   * {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() {

    System.out.println("Loading configuration from " + path);
    // TODO: Fill in this method.
    try {
      char[] data = new char[10];

      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      CrawlerConfiguration config = mapper.readValue(Files.newBufferedReader(path,
          StandardCharsets.UTF_8),
          CrawlerConfiguration.class);

      System.out.println(config);

    } catch (Exception e) {
      throw new IllegalStateException("Failed to load configuration from " + path,
          e);
    }

    return new CrawlerConfiguration.Builder().build();
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler
   *               configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(reader);
    // // TODO: Fill in this method
    // // System.out.println("Loading configuration from " + reader);
    char[] data = new char[10];

    String json = "";

    try {
      while (reader.read(data) != -1) {
        json += new String(data);
      }

      ObjectMapper mapper = new ObjectMapper();
      // mapper.registerModule(new module);
      mapper.registerModule(new JavaTimeModule());
      Builder config = mapper.readValue(json,
          CrawlerConfiguration.Builder.class);
      // //
      // System.out.println("---------------------------------x--------------------");
      // System.out.println(config);
      // //
      // System.out.println("---------------------------------x--------------------");

      return config.build();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("Error loading configuration: " + e);
    }
    return null;

  }
}

package org.arshtyi.yugiohcards.server.getallcards;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.arshtyi.yugiohcards.controller.config.Config;
import org.arshtyi.yugiohcards.controller.log.Logger;
import org.json.JSONObject;

/** Placeholder class for retrieving all card data. */
public class Getallcards {
    private Getallcards() {
        // Prevent instantiation of utility class
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Main method to retrieve all card data from the Yu-Gi-Oh! API. Fetches card information from
     * the configured base URL, parses the JSON response, logs the process, and saves the formatted
     * JSON data to the configured output file. Handles potential exceptions during HTTP request
     * execution, JSON parsing, and file writing.
     */
    public static void main() {
        String logFilePath = Config.Path.FilePath.getGetAllCardsLogFilePath();
        String allCardsJsonFilePath = Config.Path.FilePath.getAllCardsJsonFilePath();
        CloseableHttpClient httpClient = Config.Url.createHttpClient();
        HttpGet httpGet = Config.Url.createHttpGet(Config.Url.getBaseUrl());
        Logger.log("INFO", "Url: " + Config.Url.getBaseUrl(), Getallcards.class, logFilePath);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            JSONObject root = new JSONObject(EntityUtils.toString(response.getEntity()));
            // Log that we're starting to write to file
            Logger.log(
                    "INFO",
                    "Starting to write card data to file: " + allCardsJsonFilePath,
                    Getallcards.class,
                    logFilePath);
            String jsonString = root.toString(4);
            java.nio.file.Path filePath = java.nio.file.Paths.get(allCardsJsonFilePath);
            java.nio.file.Files.createDirectories(filePath.getParent());
            java.nio.file.Files.writeString(
                    filePath, jsonString, java.nio.charset.StandardCharsets.UTF_8);
            Logger.log(
                    "INFO",
                    "Successfully wrote card data to file: " + allCardsJsonFilePath,
                    Getallcards.class,
                    logFilePath);
        } catch (Exception e) {
            Logger.log(
                    "ERROR",
                    "Failed to execute HTTP GET request: " + e.getMessage(),
                    Getallcards.class,
                    logFilePath);
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                Logger.log(
                        "ERROR",
                        "Failed to close HTTP client: " + e.getMessage(),
                        Getallcards.class,
                        logFilePath);
            }
        }
    }
}

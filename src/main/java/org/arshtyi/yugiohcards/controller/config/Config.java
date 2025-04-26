package org.arshtyi.yugiohcards.controller.config;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.security.CodeSource;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Configuration class providing constants and utility methods for the application. Contains nested
 * classes for URL configurations and file path management. This class should not be instantiated.
 *
 * @throws AssertionError if an attempt is made to instantiate this utility class.
 */
public class Config { // Renamed class from config to Config
    /**
     * Utility/configuration class that should not be instantiated.
     *
     * @throws AssertionError if an attempt is made to instantiate this class.
     */
    private Config() { // Updated constructor name
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Nested class for managing URL-related configurations and HTTP client setup. Provides base
     * URLs, user agent strings, and methods to create HTTP requests and clients for accessing the
     * Yu-Gi-Oh! API. This class should not be instantiated.
     *
     * @throws AssertionError if an attempt is made to instantiate this utility class.
     */
    public static class Url { // Renamed nested class from url to Url
        /**
         * Provides URLs and HTTP client configuration for the Yu-Gi-Oh! API. This is a utility
         * class and should not be instantiated.
         *
         * @throws AssertionError if an attempt is made to instantiate this class.
         */
        private Url() { // Updated constructor name
            throw new AssertionError("Utility class should not be instantiated");
        }

        private static final String BASE_URL = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
        private static final String AGENT =
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/135.0.0.0 Safari/537.36 Edg/135.0.0.0";

        /**
         * Returns the base URL for the Yu-Gi-Oh! ProDeck API card information endpoint.
         *
         * @return The base API URL string.
         */
        public static String getBaseUrl() {
            return BASE_URL;
        }

        /**
         * Returns the User-Agent string used for HTTP requests.
         *
         * @return The User-Agent string.
         */
        public static String getAgent() {
            return AGENT;
        }

        /**
         * Constructs a URL to query the API for cards belonging to a specific archetype.
         *
         * @param archetype The name of the archetype to filter by.
         * @return The API URL string with the archetype query parameter.
         */
        public static String getUrlByArchetype(String archetype) {
            return BASE_URL + "?archetype=" + archetype;
        }

        /**
         * Creates and configures an HttpGet request object for the given URL. Sets connection and
         * socket timeouts.
         *
         * @param url The URL for the GET request.
         * @return A configured HttpGet object.
         */
        public static HttpGet createHttpGet(String url) {
            HttpGet httpGet = new HttpGet(url);
            // httpGet.setHeader("User-Agent", AGENT);
            RequestConfig requestConfig =
                    RequestConfig.custom().setConnectTimeout(5000).setSocketTimeout(5000).build();
            httpGet.setConfig(requestConfig);
            return httpGet;
        }

        /**
         * Creates and configures a CloseableHttpClient instance. Sets SSL hostname verification and
         * default request configuration (timeouts).
         *
         * @return A configured CloseableHttpClient instance.
         */
        public static CloseableHttpClient createHttpClient() {
            return HttpClients.custom()
                    .setSSLHostnameVerifier(new DefaultHostnameVerifier())
                    .setDefaultRequestConfig(
                            RequestConfig.custom()
                                    .setConnectTimeout(5000)
                                    .setSocketTimeout(5000)
                                    .build())
                    .build();
        }
    }

    /**
     * Nested class for managing file and directory paths used by the application. Provides methods
     * to get paths for resources, output files, and log files, ensuring paths are constructed
     * safely and correctly based on the application's execution context (JAR or file system). This
     * class should not be instantiated.
     *
     * @throws AssertionError if an attempt is made to instantiate this utility class.
     */
    public static class Path { // Renamed nested class from path to Path
        /**
         * Provides utility methods for path manipulation. This is a utility class and should not be
         * instantiated.
         *
         * @throws AssertionError if an attempt is made to instantiate this class.
         */
        private Path() { // Updated constructor name
            throw new AssertionError("Utility class should not be instantiated");
        }

        /**
         * Nested class specifically for managing file paths within the application's structure.
         * Defines constants for directory names and file names, and provides methods to retrieve
         * the full paths for various resources like output JSON and log files. This class should
         * not be instantiated.
         *
         * @throws AssertionError if an attempt is made to instantiate this utility class.
         */
        public static class FilePath { // Renamed nested class from filePath to FilePath
            private FilePath() { // Updated constructor name
                throw new AssertionError("Utility class should not be instantiated");
            }

            private static final String RESOURCE_DIRECTORY_PATH =
                    getApplicationRootPathString() + File.separator + "resource";

            /**
             * Returns the absolute path to the main resource directory.
             *
             * @return The resource directory path string.
             */
            public static String getResourceDirectoryPath() {
                return RESOURCE_DIRECTORY_PATH;
            }

            private static final String OUTPUT_DIRECTORY_NAME = "output";
            private static final String OUTPUT_DIRECTORY_PATH =
                    safeJoinPath(getResourceDirectoryPath(), OUTPUT_DIRECTORY_NAME);

            /**
             * Returns the absolute path to the output directory.
             *
             * @return The output directory path string.
             */
            public static String getOutputDirectoryPath() {
                return OUTPUT_DIRECTORY_PATH;
            }

            private static final String ALL_CARDS_JSON_FILE_NAME = "allcards.json";
            private static final String ALL_CARDS_JSON_FILE_PATH =
                    safeJoinPath(OUTPUT_DIRECTORY_PATH, ALL_CARDS_JSON_FILE_NAME);

            /**
             * Returns the absolute path to the JSON file where all card data is stored.
             *
             * @return The allcards.json file path string.
             */
            public static String getAllCardsJsonFilePath() {
                return ALL_CARDS_JSON_FILE_PATH;
            }

            private static final String LOG_DIRECTORY_NAME = "log";
            private static final String LOG_DIRECTORY_PATH =
                    safeJoinPath(getResourceDirectoryPath(), LOG_DIRECTORY_NAME);

            /**
             * Returns the absolute path to the log directory.
             *
             * @return The log directory path string.
             */
            public static String getLogDirectoryPath() {
                return LOG_DIRECTORY_PATH;
            }

            private static final String MAIN_LOG_FILE_NAME = "main.log";
            private static final String MAIN_LOG_FILE_PATH =
                    safeJoinPath(LOG_DIRECTORY_PATH, MAIN_LOG_FILE_NAME);

            /**
             * Returns the absolute path to the main application log file.
             *
             * @return The main.log file path string.
             */
            public static String getMainLogFilePath() {
                return MAIN_LOG_FILE_PATH;
            }

            private static final String GET_ALL_CARDS_LOG_FILE_NAME = "getAllCards.log";
            private static final String GET_ALL_CARDS_LOG_FILE_PATH =
                    safeJoinPath(LOG_DIRECTORY_PATH, GET_ALL_CARDS_LOG_FILE_NAME);

            /**
             * Returns the absolute path to the log file specifically for the Getallcards process.
             *
             * @return The getAllCards.log file path string.
             */
            public static String getGetAllCardsLogFilePath() {
                return GET_ALL_CARDS_LOG_FILE_PATH;
            }
        }

        /**
         * Gets the application's root directory path string, compatible with JAR execution and
         * local folder development. If run from a JAR, returns the directory containing the JAR
         * file. If run from a file system directory (e.g., IDE), returns the root directory of the
         * classes (e.g., target/classes or bin).
         *
         * @return A string representing the absolute path to the application's root directory.
         * @throws IllegalStateException If the code source location cannot be determined or an
         *     error occurs during path processing.
         */
        public static String getApplicationRootPathString() {
            try {
                CodeSource codeSource =
                        Path.class.getProtectionDomain().getCodeSource(); // Updated class name
                URL locationUrl = (codeSource != null) ? codeSource.getLocation() : null;

                // If codeSource or location is null, try a fallback method
                if (locationUrl == null) {
                    URL resourceUrl = Path.class.getResource("/"); // Updated class name
                    if (resourceUrl == null) {
                        // Last resort: user.dir, but it might be inaccurate
                        System.err.println(
                                "Warning: Could not determine code source location or root resource. Falling back to 'user.dir'. This might be inaccurate.");
                        return System.getProperty("user.dir");
                    }
                    locationUrl = resourceUrl; // Use the resource URL as the location
                }

                java.nio.file.Path path;
                URI locationUri = locationUrl.toURI();

                if ("jar".equalsIgnoreCase(locationUri.getScheme())) {
                    // Running from a JAR
                    String schemeSpecificPart = locationUri.getSchemeSpecificPart();
                    // Locate the '!' separator
                    int bangSeparator = schemeSpecificPart.indexOf("!/");
                    String jarFilePathStr =
                            (bangSeparator != -1)
                                    ? schemeSpecificPart.substring(0, bangSeparator)
                                    : schemeSpecificPart;

                    // jarFilePathStr might be file:/path/to/file.jar or /path/to/file.jar (depends
                    // on system and URL construction)
                    // Use URI to handle the path correctly
                    URI jarUri = new URI(jarFilePathStr);

                    // Paths.get(URI) correctly handles the "file:" protocol
                    java.nio.file.Path jarFilePath = Paths.get(jarUri);
                    path = jarFilePath.getParent(); // Get the directory containing the JAR file

                } else if ("file".equalsIgnoreCase(locationUri.getScheme())) {
                    // Running from the file system (e.g., IDE, target/classes)
                    path = Paths.get(locationUri);
                    // If the path points to a file (e.g., running a .class file directly, though
                    // rare), get its parent directory
                    if (path.toFile().isFile()) {
                        path = path.getParent();
                    }
                } else {
                    throw new IllegalStateException(
                            "Unsupported scheme for code source location: "
                                    + locationUri.getScheme());
                }

                if (path == null) {
                    throw new IllegalStateException(
                            "Could not determine application root directory from location: "
                                    + locationUrl);
                }

                return path.toAbsolutePath().toString();

            } catch (URISyntaxException e) {
                throw new IllegalStateException("Failed to process code source location URL", e);
            } catch (Exception e) { // Catch other potential exceptions
                throw new IllegalStateException(
                        "An unexpected error occurred while determining the application root path",
                        e);
            }
        }

        /**
         * Safely joins a base path with a next-level sub-path (file or directory name). Only allows
         * joining a single level; does not permit path separators or '..'.
         *
         * @param basePath The absolute or relative path string of the base directory.
         * @param subPath The next-level sub-path to join (file name or single-level directory
         *     name). Must not contain '/' or '\\', nor be "." or "..".
         * @return The joined absolute path string, or null if the input is invalid or unsafe.
         */
        public static String safeJoinPath(String basePath, String subPath) {
            if (basePath == null
                    || basePath.trim().isEmpty()
                    || subPath == null
                    || subPath.trim().isEmpty()) {
                System.err.println("Error: Base path and sub path cannot be null or empty.");
                return null;
            }

            // Check if subPath contains path separators or special directory names
            if (subPath.contains("/")
                    || subPath.contains("\\")
                    || subPath.equals(".")
                    || subPath.equals("..")) {
                System.err.println(
                        "Error: Sub path cannot contain path separators ('/', '\\') or be '.' or '..'. Found: "
                                + subPath);
                return null;
            }

            try {
                java.nio.file.Path basePathObj = Paths.get(basePath).toAbsolutePath().normalize();
                java.nio.file.Path resolvedPath =
                        basePathObj.resolve(subPath).normalize(); // Use resolve for joining
                // and normalize

                // Security check: Ensure the resolved path is still under the base path
                // Prevents '..' escapes (though already filtered) by checking if the resolved
                // path starts with the base path.
                // This provides an extra layer of security against potential path manipulation
                // techniques.
                if (!resolvedPath.startsWith(basePathObj)) {
                    System.err.println(
                            "Error: Resolved path attempts to escape the base directory. Base: "
                                    + basePathObj
                                    + ", Resolved: "
                                    + resolvedPath);
                    return null;
                }

                return resolvedPath.toString();

            } catch (InvalidPathException e) {
                System.err.println("Error creating path object: " + e.getMessage());
                return null;
            } catch (Exception e) { // Catch other potential exceptions
                System.err.println(
                        "An unexpected error occurred during path joining: " + e.getMessage());
                return null;
            }
        }
    }
}

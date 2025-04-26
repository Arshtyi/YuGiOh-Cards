package org.arshtyi.yugiohcards.controller.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.arshtyi.yugiohcards.controller.log.Logger;

/**
 * Utility class providing environment check functionalities. Includes methods to verify the
 * existence of necessary files and directories, creating them if they are missing. Also performs
 * initial environment setup checks. This class should not be instantiated.
 *
 * @throws AssertionError if an attempt is made to instantiate this utility class.
 */
public class Check {
    private Check() {
        // Prevent instantiation of utility class
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Checks if a file exists at the given path. If not, attempts to create the file and any
     * necessary parent directories.
     *
     * @param filePath The path string of the file to check and create.
     * @return {@code true} if the file exists or was successfully created, {@code false} otherwise.
     */
    public static boolean checkFileExists(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return true; // File already exists
        } else {
            try {
                // Ensure parent directory exists
                Path parentDir = path.getParent();
                if (parentDir != null && !Files.exists(parentDir)) {
                    Files.createDirectories(parentDir); // Recursively create parent directories
                }
                // Create the file
                Files.createFile(path);
                System.out.println("File created: " + path);
                return true;
            } catch (IOException e) {
                System.err.println("Failed to create file: " + path + " - " + e.getMessage());
                return false;
            }
        }
    }

    /**
     * Checks if a directory exists at the given path. If not, attempts to create the directory and
     * any necessary parent directories.
     *
     * @param dirPath The path string of the directory to check and create.
     * @return {@code true} if the directory exists or was successfully created, {@code false}
     *     otherwise.
     */
    public static boolean checkDirectoryExists(String dirPath) {
        Path path = Paths.get(dirPath);
        // Check if it exists and is a directory
        if (Files.isDirectory(path)) {
            return true; // Directory already exists
        } else if (Files.exists(path)) {
            // Path exists but is not a directory
            System.err.println("Path exists but is not a directory: " + path);
            return false;
        } else {
            try {
                // Create the directory, including any necessary parent directories
                Files.createDirectories(path);
                System.out.println("Directory created: " + path);
                return true;
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + path + " - " + e.getMessage());
                return false;
            }
        }
    }

    /**
     * Performs the main environment check sequence. Ensures the log directory exists, cleans up old
     * log files, and then checks if the output directory exists. Logs errors if checks fail.
     *
     * @return {@code true} if the environment setup (directories) is valid or could be created,
     *     {@code false} otherwise.
     */
    public static boolean checkEnvironment() {
        String logFilePath = Config.Path.FilePath.getMainLogFilePath();
        if (!checkDirectoryExists(Config.Path.FilePath.getLogDirectoryPath())) {
            Logger.log(
                    "ERROR",
                    "Log directory does not exist and could not be created.",
                    Check.class,
                    null);
            return false;
        } else {
            // Log directory exists or was successfully created.
            // Delete all files within the log directory as requested.
            Path logDirPath = Paths.get(Config.Path.FilePath.getLogDirectoryPath());
            try (var stream = Files.list(logDirPath)) {
                stream.filter(Files::isRegularFile)
                        .forEach(
                                file -> {
                                    try {
                                        Files.delete(file);
                                        Logger.log(
                                                "INFO",
                                                "Deleted existing log file: " + file,
                                                Check.class,
                                                logFilePath);
                                    } catch (IOException e) {
                                        System.err.println(
                                                "Failed to delete existing log file: "
                                                        + file
                                                        + " - "
                                                        + e.getMessage());
                                        // Log this error appropriately, maybe using the Logger
                                        // class itself if possible
                                        // Be cautious about logging errors during log directory
                                        // cleanup
                                    }
                                });
            } catch (IOException e) {
                System.err.println(
                        "Failed to list log directory contents for cleanup: "
                                + logDirPath
                                + " - "
                                + e.getMessage());
                // Decide if this failure should prevent the application from continuing
                // return false; // Optionally return false if cleanup fails critically
            }
        }
        return main();
    }

    /**
     * Performs secondary checks after the initial log directory check. Specifically checks for the
     * existence of the output directory. Logs errors if the check fails.
     *
     * @return {@code true} if the output directory exists or was successfully created, {@code
     *     false} otherwise.
     */
    private static boolean main() {
        String logFilePath = Config.Path.FilePath.getMainLogFilePath();
        if (!checkDirectoryExists(Config.Path.FilePath.getOutputDirectoryPath())) {
            Logger.log(
                    "ERROR",
                    "Output directory does not exist and could not be created.",
                    Check.class,
                    logFilePath);
            return false;
        } else {
            Logger.log(
                    "INFO",
                    "Output directory exists or was successfully created.",
                    Check.class,
                    logFilePath);
        }
        return true;
    }
}

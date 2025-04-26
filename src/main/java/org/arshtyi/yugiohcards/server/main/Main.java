package org.arshtyi.yugiohcards.server.main;

import org.arshtyi.yugiohcards.server.getallcards.Getallcards;

/**
 * Main server class responsible for initiating the card data retrieval process. This class serves
 * as the entry point for the server-side logic.
 */
public class Main {
    private Main() {
        // Prevent instantiation of utility class
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * The main method for the server component. Currently, it directly calls the main method of
     * Getallcards to fetch data.
     *
     * @param args Command line arguments (not currently used).
     */
    public static void main(String[] args) {
        Getallcards.main();
    }
}

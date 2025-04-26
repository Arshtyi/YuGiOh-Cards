package org.arshtyi.yugiohcards;

import org.arshtyi.yugiohcards.controller.config.Check;
import org.arshtyi.yugiohcards.server.main.Main;

/** Main application class for the Yu-Gi-Oh Cards project. */
public class Yugiohcards {
    /**
     * The main entry point of the application.
     *
     * @param args Command line arguments (not currently used).
     */
    public static void main(String[] args) {
        System.out.println("Yu-Gi-Oh Cards Application Started!"); // Keep existing English message
        if (!Check.checkEnvironment()) { // Updated class name
            System.out.println("Environment check failed!");
        } else {
            System.out.println("Environment check passed!");
            Main.main(args);
        }
    }
}

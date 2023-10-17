package com.thnkscj.socket.common.util;

/**
 * Super simple logger
 *
 * @author Thnks_CJ
 */
public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private final String name;
    private final boolean debug = false;

    /**
     * Private constructor for the logger
     *
     * @param name name of the logger
     */
    private Logger(String name) {
        this.name = name;
    }

    /**
     * Create a logger with the given name
     *
     * @param name name of the logger
     * @return Logger
     */
    public static Logger getLogger(String name) {
        return new Logger(name);
    }

    /**
     * Log an info message
     *
     * @param message message to log
     */
    public void info(String message) {
        System.out.println("[" + name + "] [INFO] " + message);
    }

    /**
     * Log a warning message (prints in yellow)
     *
     * @param message message to log
     */
    public void warn(String message) {
        System.out.println(ANSI_YELLOW + "[" + name + "] [WARN] " + message + ANSI_RESET);
    }

    /**
     * Log an error message (prints in red)
     *
     * @param message message to log
     */
    public void error(String message) {
        System.out.println(ANSI_RED + "[" + name + "] [ERROR] " + message + ANSI_RESET);
    }

    /**
     * Log an error message (prints in red)
     *
     * @param message message to log
     * @param cause   cause of the error
     */
    public void error(String message, String cause) {
        System.out.println(ANSI_RED + "[" + name + "] [ERROR] " + message + " (" + cause + ")" + ANSI_RESET);
    }

    /**
     * Log a debug message
     *
     * @param message message to log
     */
    public void debug(String message) {
        if (!debug) return;
        System.out.println("[" + name + "] [DEBUG] " + message);
    }
}

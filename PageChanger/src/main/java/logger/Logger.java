package logger;

import org.apache.logging.log4j.LogManager;

public class Logger {

    private static int step = 1;

    /**
     * This method logs information
     * @param clazz the class that is displayed in log
     * @param message the message that is displayed in log
     */
    public static void log(Class clazz, String message) {
        LogManager.getLogger(clazz).info(message);
    }

    /**
     * This method logs step of running program
     * @param message the message that is displayed in log
     */
    public static void logStep(String message) {
        LogManager.getLogger("").info("********** STEP " + step++ + ". " + message + " **********");
    }
}

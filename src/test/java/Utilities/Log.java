package Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger log = LoggerFactory.getLogger(Log.class);

    //Info Level Logs
    public static void info(String message) {
        log.info(message);
    }

    //Warn Level Logs
    public static void warn(String message) {
        log.warn(message);
    }

    //Error Level Logs
    public static void error(String message) {
        log.error(message);
    }

    //Fatal Level Logs
    public static void fatal(String message) {
        log.trace(message);
    }

    //Debug Level Logs
    public static void debug(String message) {
        log.debug(message);
    }
}

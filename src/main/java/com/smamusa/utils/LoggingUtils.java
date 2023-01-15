package com.smamusa.utils;

import java.util.logging.Level;

public class LoggingUtils {

    public static void disableHtmlUnitLogging() {
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
    }

    public static void disableApacheHttpClientLogging() {
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
    }

    public static void disableNonAppLoggers() {
        LoggingUtils.disableHtmlUnitLogging();
        LoggingUtils.disableApacheHttpClientLogging();
    }
}

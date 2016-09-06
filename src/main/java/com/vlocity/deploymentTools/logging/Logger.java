package com.vlocity.deploymentTools.logging;

import java.util.HashSet;

/**
 * Created by Derek on 25/08/2016.
 */
public class Logger extends Thread {

    private static HashSet<ILogHandler> handlers = new HashSet<>();

    private static Logger instance;

    public static void RegisterHandler(ILogHandler handler) {
        Logger.handlers.add(handler);
    }

    public static void LogAsync(String item) {
        LogAsync(item, Severity.Info);
    }

    public static void LogAsync(String item, Severity severity) {
        instance.Log(item, severity);
    }

    static {
        instance = new Logger();
        instance.start();
    }

    public void Log(String item, Severity severity) {
        for (ILogHandler handler : handlers) {
            handler.ProcessItem(item, severity);
        }
    }

    public enum Severity {
        Verbose,
        Info,
        Warning,
        Error,
        Fatal
    }

}

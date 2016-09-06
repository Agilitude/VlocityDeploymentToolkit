package com.vlocity.deploymentTools.logging;

/**
 * Created by Derek on 25/08/2016.
 */
public interface ILogHandler {

    void ProcessItem(String message, Logger.Severity severity);

}

package com.vlocity.deploymentTools.client;

/**
 * Created by Derek on 23/06/2016.
 */
public class PackageNotSupportedException extends Exception {
    public PackageNotSupportedException(String packageName) {
        super(String.format("Package '%1s' is not supported.", packageName));
    }
}

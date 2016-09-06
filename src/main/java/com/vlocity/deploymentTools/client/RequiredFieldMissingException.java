package com.vlocity.deploymentTools.client;

/**
 * Created by Derek on 23/06/2016.
 */
public class RequiredFieldMissingException extends Exception {
    public RequiredFieldMissingException(String field) {
        super(String.format("Field '%1s' is missing in the json document.", field));
    }
}

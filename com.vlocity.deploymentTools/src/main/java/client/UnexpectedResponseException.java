package client;

/**
 * Created by Derek on 25/08/2016.
 */
public class UnexpectedResponseException extends Exception {
    public UnexpectedResponseException(String message) {
        super(message);
    }
}

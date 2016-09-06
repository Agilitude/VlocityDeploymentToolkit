package client;

/**
 * Created by Derek on 23/06/2016.
 */
public class ArtifactNotSupportedException extends Exception {
    public ArtifactNotSupportedException(String artifactType) {
        super(String.format("Artifact %1s is not support", artifactType));
    }
}

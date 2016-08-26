package storage;

import client.ArtifactTypeEnum;
import client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 26/08/2016.
 */
public class ArtifactCommitter extends Thread {

    private static ArtifactCommitter instance;

    private static ArrayList<ICommitHandler> handlers = new ArrayList<ICommitHandler>();

    public static void RegisterHandler(ICommitHandler handler) {
        handlers.add(handler);
    }

    public static void CommitAsync(VlocityArtifact artifact) {
        instance.Commit(artifact);
    }

    static {
        instance = new ArtifactCommitter();
        instance.start();
    }

    public void Commit(VlocityArtifact artifact) {
        for (ICommitHandler handler : ArtifactCommitter.handlers) {
            handler.HandleCommit(artifact);
        }
    }



}

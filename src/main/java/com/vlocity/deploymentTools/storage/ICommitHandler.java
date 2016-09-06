package com.vlocity.deploymentTools.storage;

import com.vlocity.deploymentTools.client.VlocityArtifact;

/**
 * Created by Derek on 26/08/2016.
 */
public interface ICommitHandler {

    void HandleCommit(VlocityArtifact artifact);

}

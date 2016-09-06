package com.vlocity.deploymentTools.client.ins12x;


import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 25/08/2016.
 */
public class ApplicationTemplate extends VlocityArtifact {
    public ApplicationTemplate(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", VlocityArtifact.FieldTypeEnum.STRING, true, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("HtmlBlob__c", "HtmlBlob", VlocityArtifact.FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Type__c", "Type", VlocityArtifact.FieldTypeEnum.STRING));
        }};
    }

    @Override
    public void onAfterDeserialisation() {

    }

    @Override
    public void onBeforeSerialisation() {

    }

    @Override
    public void onAfterRetrieve() throws Exception {

    }

    @Override
    public void onBeforeDeploy() {

    }

    @Override
    public String getSObjectTypeName() {
        return "ApplicationTemplate__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

}

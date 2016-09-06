package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 30/08/2016.
 */
public class PartyRelationshipType extends VlocityArtifact {
    public PartyRelationshipType(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", VlocityArtifact.FieldTypeEnum.STRING, true, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Color__c", "Color", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Data_Raptor_ID__c", "DataRaptorId", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("SourceString__c", "SourceString", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Process__c", "Process", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("SourcePartyType__c", "SourcePartyType", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("TargetPartyType__c", "TargetPartyType", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("TargetRole__c", "TargetRole", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
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
        return "PartyRelationshipType__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

}

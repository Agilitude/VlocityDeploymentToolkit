package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 30/08/2016.
 */
public class RequiredDocument extends VlocityArtifact {
    public RequiredDocument(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Account_Access__c", "AccountAccess", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Account_Relation__c ", "AccountRelation", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Account_Status__c", "AccountStatus", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Customer_Types__c", "CustomerTypes", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Description__c", "Description", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Document_Level__c", "DocumentLevel", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Required_Document_Mapping_Code__c", "MappingCode", VlocityArtifact.FieldTypeEnum.STRING, true, true, false));
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
        return "Required_Document__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

    @Override
    public Boolean isPackageMember() {
        return false;
    }
}

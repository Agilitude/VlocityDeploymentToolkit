package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 30/08/2016.
 */
public class Action extends VlocityArtifact {
    public Action(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", VlocityArtifact.FieldTypeEnum.STRING, true, true, false));


            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsActive__c", "IsActive", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AdditionalFilter__c", "AdditionalFilter", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ApplicableTypes__c", "ApplicableTypes", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ApplicableUserProfiles__c", "ApplicableUserProfiles", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ActionIcon__c", "ActionIcon", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DisplayLabel__c", "DisplayLabel", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DisplayOn__c", "DisplayOn", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DisplaySequence__c", "DisplaySequence", FieldTypeEnum.INT, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Filter__c", "Filter", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("InvokeClassName__c", "InvokeClassName", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("InvokeMethodName__c", "InvokeMethodName", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsSeedAction__c", "IsSeedAction", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("LinkType__c", "LinkType", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("OpenUrlMode__c", "OpenUrlMode", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("StateModelId__r.Name", "StateModel", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("StateModelId__r.vlocity_ins__FieldAPIName__c", "StateModelField", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("StateModelId__r.vlocity_ins__ObjectAPIName__c", "StateModelObject", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Url__c", "Url", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("StateId__r.Name", "State", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("UrlParameters__c", "UrlParameters", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ValidationClassName__c", "ValidationClassName", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ValidationMethodName__c", "ValidationMethodName", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("VlocityIcon__c", "VlocityIcon", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));


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
        return "VlocityAction__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

}

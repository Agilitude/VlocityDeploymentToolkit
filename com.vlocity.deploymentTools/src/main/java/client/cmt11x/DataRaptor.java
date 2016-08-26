package client.cmt11x;

import client.ArtifactTypeEnum;
import client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 29/06/2016.
 */
public class DataRaptor extends VlocityArtifact{
    public DataRaptor(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", FieldTypeEnum.STRING, true, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("BatchSize__c", "BatchSize", FieldTypeEnum.INT));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DRMapName__c", "MapName", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DeleteOnSuccess__c", "DeleteOnSuccess", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Description__c", "Description", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("TargetOutJson__c", "ExpectedOutput", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IgnoreErrors__c", "IgnoreErrors", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("InputJson__c", "Input", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("InterfaceObject__c", "InterfaceObject", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsDefaultForInterface__c", "IsDefaultInterface", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("OuboundStagingObjectDataField__c", "OutboundStagingObjectDataField", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("OutboundConfigurationField__c", "OutboundConfigurationField", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("OutboundConfigurationName__c", "OutboundConfigurationName", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("OutboundStagingObjectName__c", "OutboundStagingObjectName", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("PreprocessorClassName__c", "PreprocessorClassName", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ProcessNowThreshold__c", "ProcessNowThreshold", FieldTypeEnum.INT));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsProcessSuperBulk__c", "ProcessSuperBulk", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("TargetOutDocuSignTemplateId__c", "TargetOutputDocuSignTemplateId", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("TargetOutPdfDocName__c", "TargetOutPdfDocName", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Type__c", "Type", FieldTypeEnum.STRING));

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
        return "DRBundle__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }
}

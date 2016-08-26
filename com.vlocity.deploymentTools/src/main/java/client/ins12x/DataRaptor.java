package client.ins12x;

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
            add(new VlocityArtifactFieldDefinition("Name", "Name", FieldTypeEnum.STRING, true, true, false));
            add(new VlocityArtifactFieldDefinition("BatchSize__c", "BatchSize", FieldTypeEnum.INT));
            add(new VlocityArtifactFieldDefinition("DRMapName__c", "MapName", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("DeleteOnSuccess__c", "DeleteOnSuccess", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifactFieldDefinition("Description__c", "Description", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("TargetOutJson__c", "ExpectedOutput", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("IgnoreErrors__c", "IgnoreErrors", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifactFieldDefinition("InputJson__c", "Input", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("InterfaceObject__c", "InterfaceObject", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("IsDefaultForInterface__c", "IsDefaultInterface", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifactFieldDefinition("OuboundStagingObjectDataField__c", "OutboundStagingObjectDataField", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("OutboundConfigurationField__c", "OutboundConfigurationField", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("OutboundConfigurationName__c", "OutboundConfigurationName", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("OutboundStagingObjectName__c", "OutboundStagingObjectName", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("PreprocessorClassName__c", "PreprocessorClassName", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("ProcessNowThreshold__c", "ProcessNowThreshold", FieldTypeEnum.INT));
            add(new VlocityArtifactFieldDefinition("IsProcessSuperBulk__c", "ProcessSuperBulk", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifactFieldDefinition("TargetOutDocuSignTemplateId__c", "TargetOutputDocuSignTemplateId", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("TargetOutPdfDocName__c", "TargetOutPdfDocName", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("Type__c", "Type", FieldTypeEnum.STRING));

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

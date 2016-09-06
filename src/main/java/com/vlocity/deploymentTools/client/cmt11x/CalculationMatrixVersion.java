package com.vlocity.deploymentTools.client.cmt11x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 29/06/2016.
 */
public class CalculationMatrixVersion extends VlocityArtifact {
    public CalculationMatrixVersion(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsEnabled__c", "IsEnabled", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("EndDateTime__c", "EndDateTime", FieldTypeEnum.DATETIME));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Priority__c", "Priority", FieldTypeEnum.INT));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("StartDateTime__c", "StartDateTime", FieldTypeEnum.DATETIME));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("VersionNumber__c", "Version", FieldTypeEnum.INT));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Vlocity_Calculation_Table_Line_Items__r", "Rows", FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT, ArtifactTypeEnum.CALCULATION_MATRIX_ROW));
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
        return "CalculationMatrixVersion__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }
}

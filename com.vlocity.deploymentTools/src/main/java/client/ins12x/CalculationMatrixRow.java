package client.ins12x;

import client.ArtifactTypeEnum;
import client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 29/06/2016.
 */
public class CalculationMatrixRow extends VlocityArtifact {
    public CalculationMatrixRow(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifactFieldDefinition("InputData__c", "InputData", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("OutputData__c", "OutputData", FieldTypeEnum.STRING));
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
        return "CalculationMatrixRow__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }
}

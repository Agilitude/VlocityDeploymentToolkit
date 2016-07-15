package client.cmt11x;

import client.ArtifactTypesEnum;
import client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 29/06/2016.
 */
public class CalculationMatrix extends VlocityArtifact {
    public CalculationMatrix(ArtifactTypesEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Vlocity_Calculation_Table_Versions__r", "CalculationMatrixVersions", FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT, ArtifactTypesEnum.CALCULATION_MATRIX_VERSION));
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
        return "CalculationMatrix__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

}

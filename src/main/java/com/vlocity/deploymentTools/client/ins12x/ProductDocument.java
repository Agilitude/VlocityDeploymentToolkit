package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 30/08/2016.
 */
public class ProductDocument extends VlocityArtifact {
    public ProductDocument(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Product_Document_Mapping_Code__c", "ProductDocumentMappingCode", VlocityArtifact.FieldTypeEnum.STRING, true, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Document__r.Required_Document_Mapping_Code__c", "RequiredDocumentMappingCode", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
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
        return "Product_Document__c";
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

package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactNotSupportedException;
import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;
import com.vlocity.deploymentTools.client.VlocityClient;

/**
 * Created by Derek on 16/06/2016.
 */
public class VlocityPackage extends com.vlocity.deploymentTools.client.VlocityPackage {
    private String packageVersion;
    private String packageMajorVersion;
    private String packageMinorVersion;

    public VlocityPackage(VlocityClient client) {
        super(client);
    }

    @Override
    public String getPackageName() {
        return "vlocity_ins";
    }

    @Override
    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String value) {
        this.packageVersion = value;
    }

    @Override
    public String getDatapackStub() {
        return "/services/apexrest/vlocity_ins/v1/VlocityDataPacks/";
    }

	@Override
    public VlocityArtifact InitialiseArtifact(ArtifactTypeEnum artifactType) throws ArtifactNotSupportedException {
        if (artifactType == null) return null;

        if (artifactType == artifactType.OMNISCRIPT) {
            return InitialiseOmniscript();
        }
        else if (artifactType == artifactType.ELEMENT) {
            return InitialiseElement();
        }
        else if (artifactType == artifactType.COMPILED_OUTPUT) {
            return InitialiseCompiledOutput();
        }
        else if (artifactType == artifactType.DATARAPTOR) {
            return InitialiseDataraptor();
        }
        else if (artifactType == artifactType.CALCULATION_MATRIX) {
            return InitialiseCalculationMatrix();
        }
        else if (artifactType == artifactType.CALCULATION_MATRIX_VERSION) {
            return InitialiseCalculationMatrixVersion();
        }
        else if (artifactType == artifactType.CALCULATION_MATRIX_ROW) {
            return InitialiseCalculationMatrixRow();
        }
        else if (artifactType == artifactType.APPLICATION_TEMPLATE) {
            return InitialiseApplicationTemplate();
        }
        else if (artifactType == artifactType.CARD) {
            return InitialiseCard();
        }
        else if (artifactType == artifactType.PRODUCT) {
            return InitiliaseProduct();
        }
        else if (artifactType == artifactType.ATTRIBUTE_ASSIGNMENT) {
            return InitiliaseAttributeAssignment();
        }
        else if (artifactType == artifactType.REQUIRED_DOCUMENT) {
            return InitiliaseRequiredDocument();
        }
        else if (artifactType == artifactType.PRODUCT_DOCUMENT) {
            return InitiliaseProductDocument();
        }
        else if (artifactType == artifactType.ACTION) {
            return InitiliaseAction();
        }
        else if (artifactType == artifactType.PARTY_RELATIONSHIP_TYPE) {
            return InitiliasePartyRelationshipType();
        }

        throw new ArtifactNotSupportedException(artifactType.toString());
    }

    @Override
    public Class getArtifactClass(ArtifactTypeEnum artifactTypeName) throws ArtifactNotSupportedException {
        if (artifactTypeName == ArtifactTypeEnum.OMNISCRIPT) {
            return Omniscript.class;
        }
        else if (artifactTypeName == ArtifactTypeEnum.DATARAPTOR) {
            return DataRaptor.class;
        }
        else if (artifactTypeName == ArtifactTypeEnum.CALCULATION_MATRIX) {
            return CalculationMatrix.class;
        }
        else if (artifactTypeName == ArtifactTypeEnum.APPLICATION_TEMPLATE) {
            return ApplicationTemplate.class;
        }
        else if (artifactTypeName == ArtifactTypeEnum.CARD) {
            return Card.class;
        }
        else {
            throw new ArtifactNotSupportedException(artifactTypeName.toString());
        }
    }

    public VlocityArtifact InitialiseOmniscript() {
        return new Omniscript(ArtifactTypeEnum.OMNISCRIPT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseDataraptor() {
        return new DataRaptor(ArtifactTypeEnum.DATARAPTOR, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseCalculationMatrix() {
        return new CalculationMatrix(ArtifactTypeEnum.CALCULATION_MATRIX, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseElement() {
        return new Element(ArtifactTypeEnum.ELEMENT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseCompiledOutput() {
        return new CompiledDefinition(ArtifactTypeEnum.COMPILED_OUTPUT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseDataRaptor() {
        return new DataRaptor(ArtifactTypeEnum.DATARAPTOR, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseCalculationMatrixVersion() {
        return new CalculationMatrixVersion(ArtifactTypeEnum.CALCULATION_MATRIX_VERSION, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseCalculationMatrixRow() {
        return new CalculationMatrixRow(ArtifactTypeEnum.CALCULATION_MATRIX_ROW, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseApplicationTemplate() {
        return new ApplicationTemplate(ArtifactTypeEnum.APPLICATION_TEMPLATE, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseCard() {
        return new Card(ArtifactTypeEnum.CARD, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitiliaseProduct() {
        return new Product(ArtifactTypeEnum.PRODUCT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitiliaseAttributeAssignment() {
        return new AttributeAssignment(ArtifactTypeEnum.ATTRIBUTE_ASSIGNMENT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitiliaseRequiredDocument() {
        return new RequiredDocument(ArtifactTypeEnum.REQUIRED_DOCUMENT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitiliaseProductDocument() {
        return new ProductDocument(ArtifactTypeEnum.PRODUCT_DOCUMENT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitiliaseAction() {
        return new Action(ArtifactTypeEnum.ACTION, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitiliasePartyRelationshipType() {
        return new PartyRelationshipType(ArtifactTypeEnum.PARTY_RELATIONSHIP_TYPE, this.getPackageName(), this.getPackageVersion());
    }

    public DataPackRequest InitialiseDataPackRequest(DataPackRequest.RequestTypeEnum requestType, VlocityArtifact artifact, String artifactRecordId) {
        return new DataPackRequest(requestType, artifact.getDataPackType(), artifactRecordId);
    }

}

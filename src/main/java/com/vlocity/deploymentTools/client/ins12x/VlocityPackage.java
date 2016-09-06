package client.ins12x;

import client.ArtifactNotSupportedException;
import client.ArtifactTypeEnum;
import client.VlocityArtifact;
import client.VlocityClient;

/**
 * Created by Derek on 16/06/2016.
 */
public class VlocityPackage extends client.VlocityPackage {
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
            return initialiseElement();
        }
        else if (artifactType == artifactType.COMPILED_OUTPUT) {
            return initialiseCompiledOutput();
        }
        else if (artifactType == artifactType.DATARAPTOR) {
            return InitialiseDataraptor();
        }
        else if (artifactType == artifactType.CALCULATION_MATRIX) {
            return InitialiseCalculationMatrix();
        }
        else if (artifactType == artifactType.CALCULATION_MATRIX_VERSION) {
            return initialiseCalculationMatrixVersion();
        }
        else if (artifactType == artifactType.CALCULATION_MATRIX_ROW) {
            return initialiseCalculationMatrixRow();
        }
        else if (artifactType == artifactType.APPLICATION_TEMPLATE) {
            return initialiseApplicationTemplate();
        }
        else if (artifactType == artifactType.CARD) {
            return initialiseCard();
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

    public VlocityArtifact initialiseElement() {
        return new Element(ArtifactTypeEnum.ELEMENT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseCompiledOutput() {
        return new CompiledDefinition(ArtifactTypeEnum.COMPILED_OUTPUT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseDataRaptor() {
        return new DataRaptor(ArtifactTypeEnum.DATARAPTOR, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseCalculationMatrixVersion() {
        return new CalculationMatrixVersion(ArtifactTypeEnum.CALCULATION_MATRIX_VERSION, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseCalculationMatrixRow() {
        return new CalculationMatrixRow(ArtifactTypeEnum.CALCULATION_MATRIX_ROW, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseApplicationTemplate() {
        return new ApplicationTemplate(ArtifactTypeEnum.APPLICATION_TEMPLATE, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseCard() {
        return new Card(ArtifactTypeEnum.CARD, this.getPackageName(), this.getPackageVersion());
    }

    public DataPackRequest InitialiseDataPackRequest(client.DataPackRequest.RequestTypeEnum requestType, VlocityArtifact artifact, String artifactRecordId) {
        return new DataPackRequest(requestType, artifact.getDataPackType(), artifactRecordId);
    }

}

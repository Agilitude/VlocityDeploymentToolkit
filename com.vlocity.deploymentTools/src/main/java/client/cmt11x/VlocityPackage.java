package client.cmt11x;

import client.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

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
        return "vlocity_cmt";
    }

    @Override
    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String value) {
        this.packageVersion = value;
    }

    @Override
    public VlocityArtifact InitialiseArtifact(ArtifactTypesEnum artifactType) throws ArtifactNotSupportedException {
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

        throw new ArtifactNotSupportedException(artifactType.toString());
    }

    @Override
    public Class GetArtifactClass(ArtifactTypesEnum artifactTypeName) throws ArtifactNotSupportedException {
        if (artifactTypeName == ArtifactTypesEnum.OMNISCRIPT) {
            return Omniscript.class;
        }
        else if (artifactTypeName == ArtifactTypesEnum.DATARAPTOR) {
            return DataRaptor.class;
        }
        else if (artifactTypeName == ArtifactTypesEnum.CALCULATION_MATRIX) {
            return CalculationMatrix.class;
        }
        else {
            throw new ArtifactNotSupportedException(artifactTypeName.toString());
        }
    }

    public VlocityArtifact InitialiseOmniscript() {
        return new Omniscript(ArtifactTypesEnum.OMNISCRIPT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseDataraptor() {
        return new DataRaptor(ArtifactTypesEnum.DATARAPTOR, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact InitialiseCalculationMatrix() {
        return new CalculationMatrix(ArtifactTypesEnum.CALCULATION_MATRIX, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseElement() {
        return new Element(ArtifactTypesEnum.ELEMENT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseCompiledOutput() {
        return new CompiledDefinition(ArtifactTypesEnum.COMPILED_OUTPUT, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseDataRaptor() {
        return new DataRaptor(ArtifactTypesEnum.DATARAPTOR, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseCalculationMatrixVersion() {
        return new CalculationMatrixVersion(ArtifactTypesEnum.CALCULATION_MATRIX_VERSION, this.getPackageName(), this.getPackageVersion());
    }

    public VlocityArtifact initialiseCalculationMatrixRow() {
        return new CalculationMatrixRow(ArtifactTypesEnum.CALCULATION_MATRIX_ROW, this.getPackageName(), this.getPackageVersion());
    }

}

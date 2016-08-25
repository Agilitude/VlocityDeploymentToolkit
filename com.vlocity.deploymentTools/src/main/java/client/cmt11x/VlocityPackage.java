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

    /*@Override
    public ArrayList<VlocityArtifact> GetOmniscripts(ArrayList<String> names) throws Exception {
        ArrayList<VlocityArtifact> omniscripts = new ArrayList<>();

        if (this.Client.getPartnerApiConnection() == null) {
            throw new Exception("connection is null");
        }

        QueryResult queryResults = this.Client.getPartnerApiConnection().query(getOmniScriptQueryString(names));

        do {

            if ((queryResults != null) && (queryResults.getSize() > 0)) {
                for (SObject record : queryResults.getRecords()) {
                    VlocityArtifact omniscript = InitialiseOmniscript();
                    omniscript.setProperties((XmlObject) record);

                    omniscript.Datapack = getDatapack(omniscript.getDataPackType(), record.getId());

                    omniscript.onAfterRetrieve();
                    omniscripts.add(omniscript);
                }
            }

            if (queryResults.isDone()) {
                queryResults = null;
            }
            else {
                queryResults = this.Client.getPartnerApiConnection().queryMore(queryResults.getQueryLocator());
            }
        }
        while (queryResults != null);

        return omniscripts;
    }

    @Override
    public ArrayList<VlocityArtifact> GetDataRaptors(ArrayList<String> names) throws Exception {
        ArrayList<VlocityArtifact> dataraptors = new ArrayList<>();

        if (this.Client.getPartnerApiConnection() == null) {
            throw new Exception("connection is null");
        }

        QueryResult queryResults = this.Client.getPartnerApiConnection().query(getDataRaptorQueryString(names));

        do {

            if ((queryResults != null) && (queryResults.getSize() > 0)) {
                for (SObject record : queryResults.getRecords()) {
                    VlocityArtifact dataraptor = InitialiseDataraptor();
                    dataraptor.setProperties((XmlObject) record);
                    dataraptor.onAfterRetrieve();
                    dataraptors.add(dataraptor);
                }
            }

            if (queryResults.isDone()) {
                queryResults = null;
            }
            else {
                queryResults = this.Client.getPartnerApiConnection().queryMore(queryResults.getQueryLocator());
            }

        }
        while (queryResults != null);

        return dataraptors;
    }

    @Override
    public ArrayList<VlocityArtifact> GetCalculationMatrices(ArrayList<String> names) throws Exception {
        ArrayList<VlocityArtifact> matrices = new ArrayList<>();

        if (this.Client.getPartnerApiConnection() == null) {
            throw new Exception("connection is null");
        }

        QueryResult queryResults = this.Client.getPartnerApiConnection().query(getCalculationMatricesQueryString(names));

        do {
            if ((queryResults != null) && (queryResults.getSize() > 0)) {
                for (SObject record : queryResults.getRecords()) {
                    VlocityArtifact matrix = InitialiseCalculationMatrix();
                    matrix.setProperties((XmlObject) record);
                    matrix.onAfterRetrieve();
                    matrices.add(matrix);
                }
            }

            if (queryResults.isDone()) {
                queryResults = null;
            }
            else {
                queryResults = this.Client.getPartnerApiConnection().queryMore(queryResults.getQueryLocator());
            }
        }
        while (queryResults != null);

        return matrices;
    }*/

    public void setPackageVersion(String value) {
        this.packageVersion = value;
    }

    /*private String getOmniScriptQueryString(ArrayList<String> names) throws ArtifactNotSupportedException {

        String query =  "SELECT Id, Name, " +
                "       vlocity_cmt__IsActive__c, " +
                "       vlocity_cmt__AdditionalInformation__c, " +
                "       vlocity_cmt__CustomJavaScript__c, " +
                "       vlocity_cmt__DataRaptorBundleId__c, " +
                "       vlocity_cmt__DataRaptorBundleName__c, " +
                "       vlocity_cmt__JSON_Output__c, " +
                "       vlocity_cmt__Language__c, " +
                "       vlocity_cmt__PropertySet__c, " +
                "       vlocity_cmt__IsReusable__c, " +
                "       vlocity_cmt__SubType__c, " +
                "       vlocity_cmt__TestHTMLTemplates__c, " +
                "       vlocity_cmt__Type__c, " +
                "       vlocity_cmt__Version__c, " +
                "       (SELECT Id, Name, " +
                "               vlocity_cmt__Active__c, " +
                "               vlocity_cmt__Level__c, " +
                "               vlocity_cmt__Order__c, " +
                "               vlocity_cmt__ParentElementId__c, " +
                "               vlocity_cmt__PropertySet__c, " +
                "               vlocity_cmt__SearchKey__c, " +
                "               vlocity_cmt__Type__c " +
                "        FROM vlocity_cmt__Elements__r" +
                "        ORDER BY vlocity_cmt__Level__c, vlocity_cmt__Order__c), " +
                "       (SELECT Id, Name, " +
                "               vlocity_cmt__Content__c, " +
                "               vlocity_cmt__Sequence__c " +
                "        FROM vlocity_cmt__OmniScriptDefinitions__r" +
                "        ORDER BY vlocity_cmt__Sequence__c) " +
                "FROM vlocity_cmt__OmniScript__c ";

        if (names != null && names.size() > 0) {
            query += "WHERE Name IN ('" + String.join("', '", names) + "')";
        }

        return query;

    }*/

    /*private String getDataRaptorQueryString(ArrayList<String> names) {
        String query =  "SELECT Id, Name, " +
                "       vlocity_cmt__BatchSize__c, " +
                "       vlocity_cmt__DRMapName__c," +
                "       vlocity_cmt__DeleteOnSuccess__c," +
                "       vlocity_cmt__Description__c," +
                "       vlocity_cmt__TargetOutJson__c," +
                "       vlocity_cmt__IgnoreErrors__c," +
                "       vlocity_cmt__InputJson__c," +
                "       vlocity_cmt__InterfaceObject__c," +
                "       vlocity_cmt__IsDefaultForInterface__c," +
                "       vlocity_cmt__OuboundStagingObjectDataField__c," +
                "       vlocity_cmt__OutboundConfigurationField__c," +
                "       vlocity_cmt__OutboundConfigurationName__c," +
                "       vlocity_cmt__OutboundStagingObjectName__c," +
                "       vlocity_cmt__PreprocessorClassName__c," +
                "       vlocity_cmt__ProcessNowThreshold__c," +
                "       vlocity_cmt__IsProcessSuperBulk__c," +
                "       vlocity_cmt__TargetOutDocuSignTemplateId__c," +
                "       vlocity_cmt__TargetOutPdfDocName__c," +
                "       vlocity_cmt__Type__c " +
                "FROM vlocity_cmt__DRBundle__c ";

        if (names != null && names.size() > 0) {
            query += "WHERE Name IN ('" + String.join("', '", names) + "')";
        }

        return query;
    }

    private String getCalculationMatricesQueryString(ArrayList<String> names) {
        String query =  "SELECT Id, Name, " +
                "           (SELECT Id, Name, " +
                "               vlocity_cmt__IsEnabled__c," +
                "               vlocity_cmt__EndDateTime__c," +
                "               vlocity_cmt__Priority__c," +
                "               vlocity_cmt__StartDateTime__c," +
                "               vlocity_cmt__VersionNumber__c" +
                "           FROM vlocity_cmt__Vlocity_Calculation_Table_Versions__r) " +
                "FROM vlocity_cmt__CalculationMatrix__c ";

        if (names != null && names.size() > 0) {
            query += "WHERE Name IN ('" + String.join("', '", names) + "')";
        }

        return query;
    }*/

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

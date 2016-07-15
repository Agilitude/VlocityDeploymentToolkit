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

    @Override
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
    }

    private String getDatapack(String artifactType, String artifactId) throws IOException, UnexpectedResponseException, UnexpectedDataPackException {

        URL partnerUrl = new URL(this.Client.getServerUrl());

        URL dataPackUri = new URL(partnerUrl.getProtocol(), partnerUrl.getHost(), "/services/apexrest/"+ this.getPackageName() + "/v1/VlocityDataPacks/");

        DataPackRequest requestData = new  DataPackRequest("Export", artifactType, artifactId);

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost dpCreateMethod = new HttpPost(dataPackUri.toExternalForm());
        dpCreateMethod.setHeader("Authorization", "Bearer " + Client.getSessionId());
        dpCreateMethod.setHeader("Content-Type", "application/json; charset=UTF-8");
        dpCreateMethod.setHeader("Accept", "application/json");
        dpCreateMethod.setHeader("Accept-Charset", "UTF-8");

        String status;

        Gson gson = new GsonBuilder().create();

        do {

            dpCreateMethod.setEntity(serialiseRequest(requestData));

            HttpResponse response = httpClient.execute(dpCreateMethod);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new UnexpectedResponseException(generateRequestResponseString(dpCreateMethod, response));
            }

            String resultText = EntityUtils.toString(response.getEntity());

            LinkedTreeMap result;

            try {
                result = gson.fromJson(resultText, LinkedTreeMap.class);
            }
            catch (com.google.gson.JsonSyntaxException ex) {
                throw new UnexpectedResponseException(generateRequestResponseString(dpCreateMethod, response));
            }

            requestData.processData.VlocityDataPackId = (String)result.get("VlocityDataPackId");
            status = (String)result.get("Status");

            if ("Error".equals(status)) {
                //String message = (String)result.get("Message");
                //throw new UnexpectedDataPackException("Unable to extract datapack + " + requestData.processData.VlocityDataPackId + " for " + artifactType + " " + artifactId + ". Error message is \n" + message);
                return null;
            }
        }
        while ("Ready".equals(status) || "InProgress".equals(status));


        dataPackUri = new URL(dataPackUri, requestData.processData.VlocityDataPackId);

        HttpGet getDpMethod = new HttpGet(dataPackUri.toExternalForm());
        getDpMethod.setHeader("Authorization", "Bearer " + Client.getSessionId());
        getDpMethod.setHeader("Accept", "application/json");
        getDpMethod.setHeader("Accept-Charset", "UTF-8");

        HttpResponse response = httpClient.execute(getDpMethod);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new UnexpectedResponseException(generateRequestResponseString(getDpMethod, response));
        }

        String resultText = EntityUtils.toString(response.getEntity());

        return resultText;

    }

    private HttpEntity serialiseRequest(DataPackRequest requestData) throws UnsupportedEncodingException {
        Gson gson = new GsonBuilder().create();
        String bodyText = gson.toJson(requestData);
        HttpEntity entity = new ByteArrayEntity(bodyText.getBytes("UTF-8"));

        return entity;
    }

    private String generateRequestString(HttpRequestBase method) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(method.getMethod() + " " + method.getURI().toURL().toExternalForm());

        for (Header header : method.getAllHeaders()) {
            lines.add(header.getName() + ": " + header.getValue());
        }

        if (method.getClass() == HttpPost.class) {
            lines.add(EntityUtils.toString(((HttpPost)method).getEntity()));
        }

        return String.join("\n", lines) + "\n";
    }

    private String generateRequestResponseString(HttpRequestBase method, HttpResponse response) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(generateRequestString(method));

        lines.add(String.valueOf(response.getStatusLine().getProtocolVersion().toString() + " " + response.getStatusLine().getStatusCode() + "/" + response.getStatusLine().getReasonPhrase()));

        for (Header header : response.getAllHeaders()) {
            lines.add(header.getName() + ": " + header.getValue());
        }

        lines.add(EntityUtils.toString(response.getEntity()));

        return String.join("\n", lines) + "\n";
    }


    public void setPackageVersion(String value) {
        this.packageVersion = value;
    }

    private String getOmniScriptQueryString(ArrayList<String> names) {

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

    }

    private String getDataRaptorQueryString(ArrayList<String> names) {
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

    @Override
    public void Deploy(ArrayList<VlocityArtifact> artifacts) throws UnexpectedResponseException, UnexpectedDataPackException, IOException {
        if (artifacts == null || artifacts.size() == 0) return;

        if (artifacts.get(0).hasDataPack()) {
            for (VlocityArtifact artifact : artifacts) {
                artifact.onBeforeDeploy();
                DeployDataPack(artifact);
            }

        }
    }

    private void DeploySObject(ArrayList<VlocityArtifact> artifacts) {
        ArrayList<SObject> sObjects = new ArrayList<>();

        artifacts.forEach(a -> {
            a.onBeforeDeploy();
            sObjects.add(a.ToSObject());
        });

        //Client.getPartnerApiConnection().update(artifacts);
    }

    private void DeployDataPack(VlocityArtifact artifact)  throws IOException, UnexpectedResponseException, UnexpectedDataPackException {
        URL partnerUrl = new URL(this.Client.getServerUrl());

        URL dataPackUri = new URL(partnerUrl.getProtocol(), partnerUrl.getHost(), "/services/apexrest/"+ this.getPackageName() + "/v1/VlocityDataPacks/");

        DataPackRequest requestData = new  DataPackRequest("Import", artifact.getDataPackType());
        requestData.setDataPackContent(artifact.Datapack);

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost dpCreateMethod = new HttpPost(dataPackUri.toExternalForm());
        dpCreateMethod.setHeader("Authorization", "Bearer " + Client.getSessionId());
        dpCreateMethod.setHeader("Content-Type", "application/json; charset=UTF-8");
        dpCreateMethod.setHeader("Accept", "application/json");
        dpCreateMethod.setHeader("Accept-Charset", "UTF-8");

        String status;

        Gson gson = new GsonBuilder().create();

        do {

            dpCreateMethod.setEntity(serialiseRequest(requestData));

            HttpResponse response = httpClient.execute(dpCreateMethod);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new UnexpectedResponseException(generateRequestResponseString(dpCreateMethod, response));
            }

            String resultText = EntityUtils.toString(response.getEntity());

            LinkedTreeMap result;

            try {
                result = gson.fromJson(resultText, LinkedTreeMap.class);
            }
            catch (com.google.gson.JsonSyntaxException ex) {
                throw new UnexpectedResponseException(generateRequestResponseString(dpCreateMethod, response));
            }

            requestData.processData.VlocityDataPackId = (String)result.get("VlocityDataPackId");
            requestData.processData.VlocityDataPackData = null;

            status = (String)result.get("Status");

            if ("Error".equals(status)) {
                //String message = (String)result.get("Message");
                //throw new UnexpectedDataPackException("Unable to extract datapack + " + requestData.processData.VlocityDataPackId + " for " + artifactType + " " + artifactId + ". Error message is \n" + message);
                return;
            }
        }
        while ("Ready".equals(status) || "InProgress".equals(status));

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

    public class UnexpectedResponseException extends Exception {
        public UnexpectedResponseException(String message) {
            super(message);
        }
    }

    public class UnexpectedDataPackException extends Exception {
        public UnexpectedDataPackException(String message) {
            super(message);
        }
    }

}

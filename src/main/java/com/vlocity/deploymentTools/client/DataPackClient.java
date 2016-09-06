package com.vlocity.deploymentTools.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.vlocity.deploymentTools.logging.Logger;
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
 * Created by Derek on 26/08/2016.
 */
public class DataPackClient {
    private VlocityClient Client;
    private VlocityPackage VlocityPackage;

    public DataPackClient(VlocityClient client, VlocityPackage vlocityPackage) {
        this.Client = client;
        this.VlocityPackage = vlocityPackage;
    }

    public String getDatapack(VlocityArtifact artifact, String artifactId) throws IOException, UnexpectedResponseException, UnexpectedDataPackException {

        Logger.LogAsync("Reading DataPack for " + artifact.getDataPackType() + " " + artifactId);

        URL partnerUrl = new URL(this.Client.getServerUrl());

        URL dataPackUri = new URL(partnerUrl.getProtocol(), partnerUrl.getHost(), this.VlocityPackage.getDatapackStub());

        DataPackRequest requestData = this.VlocityPackage.InitialiseDataPackRequest(DataPackRequest.RequestTypeEnum.Export, artifact, artifactId);

        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost dpCreateMethod = generatePostRequest(dataPackUri);

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

            requestData.setDataPackId((String)result.get("VlocityDataPackId"));
            status = (String)result.get("Status");

            if ("Error".equals(status)) {
                String message = (String)result.get("Message");
                throw new UnexpectedDataPackException("Unable to extract datapack " + requestData.getDataPackDataId() + " for " + artifact.getDataPackType() + " " + artifactId + ". Error message is \n" + message);
            }
        }
        while ("Ready".equals(status) || "InProgress".equals(status));


        dataPackUri = new URL(dataPackUri, requestData.getDataPackId());

        HttpGet getDpMethod = generateGetRequest(dataPackUri);

        HttpResponse response = httpClient.execute(getDpMethod);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new UnexpectedResponseException(generateRequestResponseString(getDpMethod, response));
        }

        String resultText = EntityUtils.toString(response.getEntity());

        return resultText;

    }

    private HttpGet generateGetRequest(URL dataPackUri) {
        HttpGet getDpMethod = new HttpGet(dataPackUri.toExternalForm());
        getDpMethod.setHeader("Authorization", "Bearer " + Client.getSessionId());
        getDpMethod.setHeader("Accept", "application/json");
        getDpMethod.setHeader("Accept-Charset", "UTF-8");

        return getDpMethod;
    }

    private HttpPost generatePostRequest(URL dataPackUri) {
        HttpPost dpCreateMethod = new HttpPost(dataPackUri.toExternalForm());
        dpCreateMethod.setHeader("Authorization", "Bearer " + Client.getSessionId());
        dpCreateMethod.setHeader("Content-Type", "application/json; charset=UTF-8");
        dpCreateMethod.setHeader("Accept", "application/json");
        dpCreateMethod.setHeader("Accept-Charset", "UTF-8");

        return dpCreateMethod;
    }

    protected HttpEntity serialiseRequest(DataPackRequest requestData) throws UnsupportedEncodingException {
        Gson gson = new GsonBuilder().create();
        String bodyText = gson.toJson(requestData);
        HttpEntity entity = new ByteArrayEntity(bodyText.getBytes("UTF-8"));

        return entity;
    }

    protected String generateRequestString(HttpRequestBase method) throws IOException {
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

    protected String generateRequestResponseString(HttpRequestBase method, HttpResponse response) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        lines.add(generateRequestString(method));

        lines.add(String.valueOf(response.getStatusLine().getProtocolVersion().toString() + " " + response.getStatusLine().getStatusCode() + "/" + response.getStatusLine().getReasonPhrase()));

        for (Header header : response.getAllHeaders()) {
            lines.add(header.getName() + ": " + header.getValue());
        }

        lines.add(EntityUtils.toString(response.getEntity()));

        return String.join("\n", lines) + "\n";
    }
}

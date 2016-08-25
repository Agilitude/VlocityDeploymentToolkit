package taskDefs;

import client.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;
import com.sforce.ws.ConnectionException;

import java.text.ParseException;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by Derek Choate on 25/05/2016.
 */
public class Retrieve extends Task {

    private String username;
    private String password;
    private String sessionId;
    private String serverurl;
    private String retrieveTarget;
    private String packageNames;
    private String apiVersion;
    private Integer pollWaitMillis;
    private Integer maxPoll;
    private Boolean singlePackage;
    private Boolean trace;
    private String unpackaged;
    private Boolean unzip;
    private VlocityClient client;
    private String extractLastModifiedBy;

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setServerurl(String serverurl) {
        this.serverurl = serverurl;
    }

    public void setRetrieveTarget(String retrieveTarget) {
        this.retrieveTarget = retrieveTarget;
    }

    public void setPackageNames(String packageNames) {
        this.packageNames = packageNames;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public void setPollWaitMillis(Integer pollWaitMillis) {
        this.pollWaitMillis = pollWaitMillis;
    }

    public void setMaxPoll(Integer maxPoll) {
        this.maxPoll = maxPoll;
    }

    public void setSinglePackage(Boolean singlePackage) {
        this.singlePackage = singlePackage;
    }

    public void setTrace(Boolean trace) {
        this.trace = trace;
    }

    public void setUnpackaged(String unpackaged) {
        this.unpackaged = unpackaged;
    }

    public void setUnzip(Boolean unzip) {
        this.unzip = unzip;
    }

    public void setExtractLastModifiedBy(String extractLastModifiedBy) {this.extractLastModifiedBy = extractLastModifiedBy;}

    public void execute() throws BuildException {
        try {
            initialiseClient();

            ArrayList<VlocityArtifact> omniscripts;
            ArrayList<VlocityArtifact> dataRaptors;
            ArrayList<VlocityArtifact> matrices;

            if (extractLastModifiedBy != null && !extractLastModifiedBy.isEmpty()) {
                omniscripts = retrieveOmniscripts(extractLastModifiedBy);
                dataRaptors = retrieveDataRaptors(extractLastModifiedBy);
                matrices = retrieveMatrices(extractLastModifiedBy);
            }
            else {
                omniscripts = retrieveOmniscripts(new ArrayList<>());
                dataRaptors = retrieveDataRaptors(new ArrayList<>());
                matrices = retrieveMatrices(new ArrayList<>());
            }

            writeFiles(omniscripts, Constants.OMNISCRIPT_EXT);
            writeFiles(dataRaptors, Constants.DATARAPTOR_EXT);
            writeFiles(matrices, Constants.CALCULATIONMATRIX_EXT);

        } catch (ConnectionException e) {
            log("The following ConnectionException exception was thrown", Project.MSG_ERR);
            log(e, Project.MSG_ERR);
            throw new BuildException(e);
        } catch (IOException e) {
            log("The following IOException exception was thrown", Project.MSG_ERR);
            log(e, Project.MSG_ERR);
            throw new BuildException(e);
        } catch (Exception e) {
            log("The following unexpected exception was thrown", Project.MSG_ERR);
            log(e, Project.MSG_ERR);
            throw new BuildException(e);
        }
    }

    private void initialiseClient() throws ConnectionException, PackageNotSupportedException, VersionNotSupportedException, ParseException, PackageNotFoundException {
        client = new VlocityClient();
        client.Login(this.username, this.password, this.serverurl);

        log("Logged in to Salesforce.com", Project.MSG_INFO);
    }

    private ArrayList<VlocityArtifact> retrieveOmniscripts(String username) throws ConnectionException, PackageNotSupportedException, VersionNotSupportedException, ParseException, PackageNotFoundException, ArtifactNotSupportedException, Exception {
        log("Reading Omniscripts", Project.MSG_INFO);
        return client.QueryOmniscripts(username);
    }

    private ArrayList<VlocityArtifact> retrieveDataRaptors(String username) throws ConnectionException, PackageNotSupportedException, VersionNotSupportedException, ParseException, PackageNotFoundException, ArtifactNotSupportedException, Exception {
        log("Reading DataRaptors", Project.MSG_INFO);
        return client.QueryDataRaptors(username);
    }

    private ArrayList<VlocityArtifact> retrieveMatrices(String username) throws ConnectionException, PackageNotSupportedException, VersionNotSupportedException, ParseException, PackageNotFoundException, ArtifactNotSupportedException, Exception {
        log("Reading Calculation Matrices", Project.MSG_INFO);
        return client.QueryCalculationMatrices(username);
    }

    private ArrayList<VlocityArtifact> retrieveOmniscripts(ArrayList<String> names) throws ConnectionException, PackageNotSupportedException, VersionNotSupportedException, ParseException, PackageNotFoundException, ArtifactNotSupportedException, Exception {
        log("Reading Omniscripts", Project.MSG_INFO);
        return client.QueryOmniscripts(names);
    }

    private ArrayList<VlocityArtifact> retrieveDataRaptors(ArrayList<String> names) throws ConnectionException, PackageNotSupportedException, VersionNotSupportedException, ParseException, PackageNotFoundException, ArtifactNotSupportedException, Exception {
        log("Reading DataRaptors", Project.MSG_INFO);
        return client.QueryDataRaptors(names);
    }

    private ArrayList<VlocityArtifact> retrieveMatrices(ArrayList<String> names) throws ConnectionException, PackageNotSupportedException, VersionNotSupportedException, ParseException, PackageNotFoundException, ArtifactNotSupportedException, Exception {
        log("Reading Calculation Matrices", Project.MSG_INFO);
        return client.QueryCalculationMatrices(names);
    }

    private void writeFiles(ArrayList<VlocityArtifact> artifacts, String artifactType) throws IOException {
        if (artifacts == null) return;

        log("Writing " + artifactType + "...", Project.MSG_INFO);

        String folder = this.unpackaged;
        if (!folder.endsWith(File.separator)) {
            folder += File.separator;
        }

        log(String.format("Folder: %1s", folder), Project.MSG_INFO);

        for (VlocityArtifact artifact : artifacts) {

            String fileName = artifact.Key;

            fileName = fileName.replace(File.separator, "_");
            fileName = folder + fileName + "." + artifactType;

            log(String.format("Writing %1s", fileName), Project.MSG_INFO);

            try (OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(fileName), Constants.ENCODING)) {
                Gson gson = new GsonBuilder()
                        .registerTypeHierarchyAdapter(VlocityArtifact.class, new VlocityArtifactSerialiser())
                        .setPrettyPrinting()
                        .create();
                gson.toJson(artifact, writer);
            }
        }

    }

}

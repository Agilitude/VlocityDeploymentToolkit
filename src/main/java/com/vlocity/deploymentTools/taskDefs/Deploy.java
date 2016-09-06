package com.vlocity.deploymentTools.taskDefs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sforce.ws.ConnectionException;
import com.vlocity.deploymentTools.client.ArtifactNotSupportedException;
import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.PackageNotFoundException;
import com.vlocity.deploymentTools.client.PackageNotSupportedException;
import com.vlocity.deploymentTools.client.VersionNotSupportedException;
import com.vlocity.deploymentTools.client.VlocityArtifact;
import com.vlocity.deploymentTools.client.VlocityArtifactDeserialiser;
import com.vlocity.deploymentTools.client.VlocityClient;
import com.vlocity.deploymentTools.logging.ILogHandler;
import com.vlocity.deploymentTools.logging.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Derek on 01/07/2016.
 */
public class Deploy extends Task implements ILogHandler {
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

    private HashMap<String, List<String>> AllFiles;

    public Deploy() {
        Logger.RegisterHandler(this);
    }

    public void execute() throws BuildException {
        try {
            initialiseClient();
            initialiseAllFiles();

            HashMap<String, ArrayList<VlocityArtifact>> artifacts = new HashMap<>();

            for (String extension : AllFiles.keySet()) {
                artifacts.put(extension, retrieveArtifacts(extension, Constants.ARTIFACT_TYPE_MAPPED_TO_EXTENSION.get(extension)));
            }

            for (String extension : artifacts.keySet()) {
                ArrayList<VlocityArtifact> items = artifacts.get(extension);

                log("Deploying " + String.valueOf(items.size()) + " " + extension + "...", Project.MSG_INFO);
                client.Deploy(items);
            }

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

    private ArrayList<VlocityArtifact> retrieveArtifacts(String extension, ArtifactTypeEnum artifactType) throws ArtifactNotSupportedException, IOException {
        log("Reading " + extension + " files...", Project.MSG_INFO);

        ArrayList<VlocityArtifact> artifacts = new ArrayList<>();

        for (String fileName : AllFiles.get(extension)) {
            artifacts.add(readArtifact(fileName, client.GetArtifactClass(artifactType)));
        }

        return artifacts;
    }

    private VlocityArtifact readArtifact(String fileName, Class type) throws IOException {
        log("Parsing " + fileName, Project.MSG_INFO);

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(fileName), Constants.ENCODING)) {

            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(VlocityArtifact.class, new VlocityArtifactDeserialiser())
                    .create();

            return (VlocityArtifact)gson.fromJson(reader, type);

        }

    }

    private void initialiseAllFiles() {
        log("Enumerating files", Project.MSG_INFO);

        AllFiles = new HashMap<>();

        for (String ext : Constants.ALL_EXTENSIONS) {
            AllFiles.put(ext, new ArrayList<>());
        }

        File artifactFolder = new File(this.unpackaged);

        for (String fileName : artifactFolder.list()) {
            String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
            if (!Constants.ALL_EXTENSIONS.contains(ext)) {
                continue;
            }

            AllFiles.get(ext).add(fileName);
        }
    }

    public void ProcessItem(String item, Logger.Severity severity) {
        Integer projectSeverity = Project.MSG_INFO;

        if (severity == Logger.Severity.Fatal || severity == Logger.Severity.Error) {
            projectSeverity = Project.MSG_ERR;
        }
        else if (severity == Logger.Severity.Warning) {
            projectSeverity = Project.MSG_WARN;
        }
        else if (severity == Logger.Severity.Verbose) {
            projectSeverity = Project.MSG_VERBOSE;
        }

        log(item, projectSeverity);
    }
}

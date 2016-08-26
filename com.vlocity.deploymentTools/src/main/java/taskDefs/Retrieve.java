package taskDefs;

import client.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logging.ILogHandler;
import logging.Logger;
import org.apache.commons.logging.Log;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.Project;
import com.sforce.ws.ConnectionException;
import storage.ICommitHandler;

import java.text.ParseException;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

/**
 * Created by Derek Choate on 25/05/2016.
 */
public class Retrieve extends Task implements ILogHandler, ICommitHandler {

    private String username;
    private String password;
    //private String sessionId;
    private String serverurl;
    //private String retrieveTarget;
    //private String packageNames;
    //private String apiVersion;
    //private Integer pollWaitMillis;
    //private Integer maxPoll;
    //private Boolean singlePackage;
    //private Boolean trace;
    private String unpackaged;
    private String artifacts = String.join(",", Constants.SUPPORTED_PRIMARY_ARTIFACTS);
    //private Boolean unzip;
    private VlocityClient client;
    private String extractLastModifiedBy;

    public Retrieve() {
        logging.Logger.RegisterHandler(this);
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    //public void setSessionId(String sessionId) {
    //    this.sessionId = sessionId;
    //}

    public void setServerurl(String serverurl) {
        this.serverurl = serverurl;
    }

//    public void setRetrieveTarget(String retrieveTarget) {
//        this.retrieveTarget = retrieveTarget;
//    }
//
//    public void setPackageNames(String packageNames) {
//        this.packageNames = packageNames;
//    }

//    public void setApiVersion(String apiVersion) {
//        this.apiVersion = apiVersion;
//    }

//    public void setPollWaitMillis(Integer pollWaitMillis) {
//        this.pollWaitMillis = pollWaitMillis;
//    }

//    public void setMaxPoll(Integer maxPoll) {
//        this.maxPoll = maxPoll;
//    }

//    public void setSinglePackage(Boolean singlePackage) {
//        this.singlePackage = singlePackage;
//    }

//    public void setTrace(Boolean trace) {
//        this.trace = trace;
//    }

    public void setUnpackaged(String unpackaged) {
        this.unpackaged = unpackaged;
    }

//    public void setUnzip(Boolean unzip) {
//        this.unzip = unzip;
//    }

    public void setArtifacts(String artifacts) {
        this.artifacts = artifacts;
    }

    public void setExtractLastModifiedBy(String extractLastModifiedBy) {this.extractLastModifiedBy = extractLastModifiedBy;}

    public void execute() throws BuildException {
        try {

            initialiseClient();

            HashMap<ArtifactTypeEnum, ArrayList<VlocityArtifact>> artifactsMappedToType = new HashMap<>();

            for (String artifact : artifacts.split(",")) {

                if (!Constants.SUPPORTED_PRIMARY_ARTIFACTS.contains(artifact)) {
                    log("Skipping '" + artifact + "' because is not supported.  Supported artifacts are " + String.join(", ", Constants.SUPPORTED_PRIMARY_ARTIFACTS), Project.MSG_ERR);
                    continue;
                }

                log("Reading " + artifact + "...", Project.MSG_INFO);

                ArtifactTypeEnum artifactType = ArtifactTypeEnum.valueOf(artifact);

                if (this.extractLastModifiedBy != null && !this.extractLastModifiedBy.isEmpty()) {
                    artifactsMappedToType.put(artifactType, client.QueryArtifacts(artifactType, this.extractLastModifiedBy));
                }
                else {
                    artifactsMappedToType.put(artifactType, client.QueryArtifacts(artifactType, new ArrayList<String>()));
                }
            }

            for (ArtifactTypeEnum artifactType : artifactsMappedToType.keySet()) {
                //writeFiles(artifactsMappedToType.get(artifactType), Constants.EXTENSIONS_MAPPED_TO_ARTIFACT_TYPE.get(artifactType));
            }

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

    @Override
    public void HandleCommit(VlocityArtifact artifact) {
        try {
            writeFiles(new ArrayList<VlocityArtifact>() {{
                add(artifact);
            }}, Constants.EXTENSIONS_MAPPED_TO_ARTIFACT_TYPE.get(artifact.ArtifactType));
        }
        catch (IOException e) {
            log(e.getMessage() + "\n" + e.getStackTrace().toString(), Project.MSG_ERR);
        }
    }
}

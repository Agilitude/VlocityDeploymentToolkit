package com.vlocity.deploymentTools.client;

import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.InstalledPackage;
import com.sforce.soap.metadata.ListMetadataQuery;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.ReadResult;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.SessionRenewer;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Derek on 15/06/2016.
 */
public class VlocityClient {

    private PartnerConnection partnerApiConnection;
    private MetadataConnection metadataApiConnection;
    private String serverUrl;
    private String sessionId;
    private String packageName;
    private String packageVersion;
    private VlocityPackage vlPackage;
    private Integer loginAttempts = 0;
    private static final Integer MAX_LOGIN_ATTEMPTS = 2;

    public Boolean ReadDataPacks = true;

    public VlocityClient() {

    }

    public void Login(String username, String password, String serverUrl) throws ConnectionException, PackageNotFoundException, VersionNotSupportedException, PackageNotSupportedException, ParseException {
        LoginResult lr = loginToPartner(username,password,serverUrl);

        establishMetadataConnection(lr);
        initialiseVersionInformation();
    }

    public ArrayList<VlocityArtifact> QueryArtifacts(ArtifactTypeEnum type, String username) throws ConnectionException, ParseException, PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException, Exception {
        return vlPackage.GetArtifacts(type, username);
    }

    public ArrayList<VlocityArtifact> QueryArtifacts(ArtifactTypeEnum type, ArrayList<String> names)throws ConnectionException, ParseException, PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException, Exception {
        return vlPackage.GetArtifacts(type, names);
    }


    public Class GetArtifactClass(ArtifactTypeEnum artifactType) throws ArtifactNotSupportedException {
        return vlPackage.getArtifactClass(artifactType);
    }

    public void Deploy(ArrayList<VlocityArtifact> artifacts) throws UnexpectedResponseException, UnexpectedDataPackException, IOException {
        vlPackage.Deploy(artifacts);
    }

    private void initialiseVersionInformation() throws ConnectionException, PackageNotSupportedException, PackageNotFoundException, VersionNotSupportedException {
        ListMetadataQuery lmq = new ListMetadataQuery();
        lmq.setType("InstalledPackage");

        FileProperties[] fileProperties = getMetadataApiConnection().listMetadata(new ListMetadataQuery[]{lmq}, 37.0);

        for (FileProperties fp : fileProperties) {
            if (VlocityPackageFactory.isSupported(fp.getFullName())) {
                this.packageName = fp.getFullName();
                break;
            }
        }

        if (this.packageName == null || this.packageName.isEmpty()) {
            throw new PackageNotFoundException();
        }

        ReadResult rr = getMetadataApiConnection().readMetadata("InstalledPackage", new String[]{this.packageName});

        for (Metadata md : rr.getRecords()) {
            //if (md.getFullName() != this.packageName) continue;

            InstalledPackage ip = (InstalledPackage)md;
            this.packageVersion = ip.getVersionNumber();
        }

        this.vlPackage = VlocityPackageFactory.getPackage(this, this.packageName, this.packageVersion);
    }


    private LoginResult loginToPartner(String username, String password, String serverUrl) throws ConnectionException {
        ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(serverUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setReadTimeout(120000);
        config.setConnectionTimeout(120000);

        this.partnerApiConnection = new PartnerConnection(config);

        LoginResult lr = this.getPartnerApiConnection().login(username, password);

        this.serverUrl = lr.getServerUrl();
        this.sessionId = lr.getSessionId();

        return lr;
    }

    private void establishMetadataConnection(LoginResult lr) throws ConnectionException {
        ConnectorConfig config = new ConnectorConfig();
        config.setServiceEndpoint(lr.getMetadataServerUrl());
        config.setSessionId(lr.getSessionId());

        this.metadataApiConnection = new MetadataConnection(config);
    }

    public PartnerConnection getPartnerApiConnection() {
        return partnerApiConnection;
    }

    public MetadataConnection getMetadataApiConnection() {
        return metadataApiConnection;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public class ClientSessionRenewer implements SessionRenewer {

        private VlocityClient Client;

        public ClientSessionRenewer(VlocityClient client) {
            this.Client = client;
        }

        @Override
        public SessionRenewalHeader renewSession(ConnectorConfig config) throws ConnectionException {

            LoginResult lr = this.Client.loginToPartner(config.getUsername(), config.getPassword(), config.getAuthEndpoint());

            SessionRenewalHeader header = new SessionRenewalHeader();
            header.name = new QName("urn:partner.soap.sforce.com", "SessionHeader");
            header.headerElement = this.Client.getSessionId();
            return header;
        }

    }
}

package client;

import com.sforce.soap.metadata.*;
import com.sforce.soap.partner.*;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

import java.text.ParseException;
import java.util.*;

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

    public VlocityClient() {

    }

    public void Login(String username, String password, String serverUrl) throws ConnectionException, PackageNotFoundException, VersionNotSupportedException, PackageNotSupportedException, ParseException {
        LoginResult lr = loginToPartner(username,password,serverUrl);

        this.serverUrl = lr.getServerUrl();
        this.sessionId = lr.getSessionId();

        establishMetadataConnection(lr);
        initialiseVersionInformation();
    }

    public ArrayList<VlocityArtifact> QueryOmniscripts(ArrayList<String> names) throws ConnectionException, ParseException, PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException, Exception {
        return vlPackage.GetOmniscripts(names);
    }

    public ArrayList<VlocityArtifact> QueryDataRaptors(ArrayList<String> names)throws ConnectionException, ParseException, PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException, Exception {
        return vlPackage.GetDataRaptors(names);
    }

    public ArrayList<VlocityArtifact> QueryCalculationMatrices(ArrayList<String> names)throws ConnectionException, ParseException, PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException, Exception {
        return vlPackage.GetCalculationMatrices(names);
    }

    public Class GetArtifactClass(ArtifactTypesEnum artifactType) throws ArtifactNotSupportedException {
        return vlPackage.GetArtifactClass(artifactType);
    }

    public void Deploy(ArrayList<VlocityArtifact> artifacts) {
        //TODO: Call on VlocityPackage to deploy the code
    }

    private void initialiseVersionInformation() throws ConnectionException, PackageNotSupportedException, PackageNotFoundException, VersionNotSupportedException {
        ListMetadataQuery lmq = new ListMetadataQuery();
        lmq.setType("InstalledPackage");

        FileProperties[] fileProperties = getMetadataApiConnection().listMetadata(new ListMetadataQuery[]{lmq}, 36.0);

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

        this.partnerApiConnection = new PartnerConnection(config);
        return this.getPartnerApiConnection().login(username, password);
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
}

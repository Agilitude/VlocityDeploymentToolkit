package client;

import client.VlocityArtifact;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Derek on 16/06/2016.
 */
public abstract class VlocityPackage {

    protected VlocityClient Client;

    public VlocityPackage(VlocityClient client) {
        this.Client = client;
    }

    public abstract String getPackageName();
    public abstract String getPackageVersion();
    public abstract ArrayList<VlocityArtifact> GetOmniscripts(ArrayList<String> names) throws Exception;
    public abstract ArrayList<VlocityArtifact> GetDataRaptors(ArrayList<String> names) throws Exception;
    public abstract ArrayList<VlocityArtifact> GetCalculationMatrices(ArrayList<String> names) throws Exception;

    public abstract VlocityArtifact InitialiseArtifact(ArtifactTypesEnum artifactType) throws ArtifactNotSupportedException;

    public abstract Class GetArtifactClass(ArtifactTypesEnum artifactTypeName) throws ArtifactNotSupportedException;

    public abstract void Deploy(ArrayList<VlocityArtifact> artifacts) throws client.cmt11x.VlocityPackage.UnexpectedResponseException, client.cmt11x.VlocityPackage.UnexpectedDataPackException, IOException;
}

package client.cmt11x;

import client.ArtifactTypeEnum;
import client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 29/06/2016.
 */
public class CompiledDefinition extends VlocityArtifact implements Comparable<CompiledDefinition> {

    public Integer getSequence() {
        if (this.Properties.containsKey("Sequence")) {
            return (Integer) this.Properties.get("Sequence");
        }

        return null;
    }

    public CompiledDefinition(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Content__c", "Content", FieldTypeEnum.JSON_STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Sequence__c", "Sequence", FieldTypeEnum.INT));
        }};
    }

    @Override
    public void onAfterDeserialisation() {

    }

    @Override
    public void onBeforeSerialisation() {

    }

    @Override
    public void onAfterRetrieve() {

    }

    @Override
    public void onBeforeDeploy() {

    }

    @Override
    public String getSObjectTypeName() {
        return "OmniScriptDefinition__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

    public int compareTo(CompiledDefinition other) {
        Integer thisSequence = this.getSequence();
        Integer otherSequence = other.getSequence();

        if (thisSequence == null && otherSequence == null) {
            return 0;
        } else if (thisSequence == null) {
            return -1;
        } else if (otherSequence == null) {
            return 1;
        } else {
            return thisSequence.compareTo(otherSequence);
        }
    }
}

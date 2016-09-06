package client.cmt11x;

import client.ArtifactTypeEnum;
import client.CorruptedDataException;
import client.VlocityArtifact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Derek on 28/06/2016.
 */
public class Omniscript extends VlocityArtifact {

    public Omniscript(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Version__c", "Version", FieldTypeEnum.INT));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Language__c", "Language", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsActive__c", "IsActive", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AdditionalInformation__c", "AdditionalInformation", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("CustomJavaScript__c", "CustomJavaScript", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DataRaptorBundleName__c", "DataRaptorBundleName", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("JSON_Output__c", "JsonOutput", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("PropertySet__c", "PropertySet", FieldTypeEnum.JSON_STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsReusable__c", "IsReusable", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("SubType__c", "SubType", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("TestHTMLTemplates__c", "TestHTMLTemplates", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Type__c", "Type", FieldTypeEnum.STRING));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Elements__r", "Elements", FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT, ArtifactTypeEnum.ELEMENT));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("OmniScriptDefinitions__r", "CompiledDefinitions", FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT, ArtifactTypeEnum.COMPILED_OUTPUT));
        }};
    }

    @Override
    public void onAfterDeserialisation() {

    }

    @Override
    public void onBeforeSerialisation() {

    }

    @Override
    public void onAfterRetrieve() throws Exception {
        createOmniscriptKey();
        sortElements();
        sortCompiledDefinitions();
    }

    @Override
    public void onBeforeDeploy() {

    }

    @Override
    public String getSObjectTypeName() {
        return "OmniScript__c";
    }

    @Override
    public String getDataPackType() { return "OmniScript"; }

    @Override
    public Boolean hasDataPack() { return true; }

    private void sortElements() throws CorruptedDataException {
        if (!Properties.containsKey("Elements")) return;

        ArrayList<Element> elements = (ArrayList<Element>) Properties.get("Elements");

        HashMap<String, Element> result = new HashMap<>();

        for (Element element : elements) {
            result.put(element.getPropertyAsString("Id"), element);
        }


        for (Element element : elements) {
            String parentId = element.getParentId();

            if (parentId == null || parentId.isEmpty()) continue;

            if (!result.containsKey(parentId)) {
                throw new CorruptedDataException("Was expecting to find parent with id " + parentId + " in the result set.");
            }

            result.get(parentId).AddChild(element);

        }

        ArrayList<String> idsToRemove = new ArrayList<>();

        for (Element element : result.values()) {
            String parentId = element.getParentId();

            if (parentId != null && !parentId.isEmpty()) {
                idsToRemove.add(element.getId());
            }

            element.SortChildren();
        }

        for (String key : idsToRemove) {
            result.remove(key);
        }

        ArrayList<Element> parentElems = new ArrayList<>(result.values());
        Collections.sort(parentElems);

        Properties.put("Elements", parentElems);
    }

    private void sortCompiledDefinitions() {
        if (!Properties.containsKey("CompiledDefinitions")) return;

        ArrayList<CompiledDefinition> definitions = (ArrayList<CompiledDefinition>) Properties.get("CompiledDefinitions");

        Collections.sort(definitions);
    }

    private void createOmniscriptKey() {
        String name = this.getPropertyAsString("Name");
        String version = this.getPropertyAsInteger("Version").toString();
        String language = this.getPropertyAsString("Language");

        this.Key = name + "." + version + "." + language;
    }
}
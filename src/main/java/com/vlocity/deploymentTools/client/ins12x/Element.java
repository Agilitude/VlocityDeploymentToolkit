package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Derek on 29/06/2016.
 */
public class Element extends VlocityArtifact implements Comparable<Element> {

    public String getId() {
        return this.getPropertyAsString("Id");
    }

    public Integer getLevel() {
        return this.getPropertyAsInteger("Level");
    }

    public Integer getOrder() {
        return this.getPropertyAsInteger("Order");
    }

    public String getParentId() {
        return this.getPropertyAsString("ParentId");
    }

    public void AddChild(Element element) {
        Object childObj = this.Properties.get("ChildElements");

        if (childObj == null) {
            childObj = new ArrayList<Element>();
        }

        ArrayList<Element> children = (ArrayList<Element>)childObj;

        children.add(element);

        this.Properties.put("ChildElements", children);
    }

    public void SortChildren() {
        Object childObj = this.Properties.get("ChildElements");

        if (childObj == null) return;

        Collections.sort((ArrayList<Element>)childObj);
    }

    public Element(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifactFieldDefinition("Id", "Id", FieldTypeEnum.STRING, false, false, false));
            add(new VlocityArtifactFieldDefinition("Active__c", "Active", FieldTypeEnum.BOOLEAN));
            add(new VlocityArtifactFieldDefinition("Level__c", "Level", FieldTypeEnum.INT));
            add(new VlocityArtifactFieldDefinition("Order__c", "Order", FieldTypeEnum.INT));
            add(new VlocityArtifactFieldDefinition("InternalNotes__c", "InternalNotes", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("PropertySet__c", "PropertySet", FieldTypeEnum.JSON_STRING));
            add(new VlocityArtifactFieldDefinition("SearchKey__c", "SearchKey", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("Type__c", "Type", FieldTypeEnum.STRING));
            add(new VlocityArtifactFieldDefinition("ParentElementId__c", "ParentId", FieldTypeEnum.STRING, false, false, true));
            add(new VlocityArtifactFieldDefinition("Elements__r", "ChildElements", FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT, ArtifactTypeEnum.ELEMENT));
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
        return "Element__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

    public int compareTo(Element other) {
        Integer thisLevel = this.getLevel();
        Integer otherLevel = other.getLevel();
        Integer thisOrder = this.getOrder();
        Integer otherOrder = other.getOrder();

        if (thisLevel == otherLevel) {
            if (thisOrder == otherOrder) {
                return 0;
            } else if (thisOrder == null) {
                return -1;
            } else if (other.getOrder() == null) {
                return 1;
            } else {
                return thisOrder.compareTo(other.getOrder());
            }
        } else if (thisLevel == null) {
            return -1;
        } else if (otherLevel == null) {
            return 1;
        } else {
            return thisLevel.compareTo(otherLevel);
        }
    }
}

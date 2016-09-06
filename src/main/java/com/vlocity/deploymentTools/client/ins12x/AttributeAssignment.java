package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 30/08/2016.
 */
public class AttributeAssignment extends VlocityArtifact {
    public AttributeAssignment(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", VlocityArtifact.FieldTypeEnum.STRING, true, true, false));

            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsActiveAssignment__c", "IsActiveAssignment", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("StartStory__c", "StartStory", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ObjectId__c", "ObjectId", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeId__r.vlocity_ins__Code__c", "Attribute", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Attribute_Assignment_mapping_code__c", "AttributeAssignmentMappingCode", VlocityArtifact.FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeCategoryId__r.vlocity_ins__Code__c", "AttributeCategory", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeCategory__c", "AttributeCategoryName", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeCategoryCode__c", "AttributeCategoryCode", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeCode__c", "AttributeCode", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeDisplaySequence__c", "AttributeDisplaySequence", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("RemovedFlag__c", "RemovedFlag", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeName__c", "AttributeName", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AttributeDisplayNameOverride__c", "AttributeDisplayNameOverride", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DisplaySequence__c", "DisplaySequence", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ConfigurationDetail__c", "ConfigurationDetail", FieldTypeEnum.JSON_STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Data__c", "Data", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("AddDate__c", "AddDate", FieldTypeEnum.DATETIME, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("RemoveDate__c", "RemoveDate", FieldTypeEnum.DATETIME, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("DefaultPicklistValueId__r.vlocity_ins__Code__c", "DefaultPicklistValueCode", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ExcludedPicklistValues__c", "ExcludedPicklistValues", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("FormatMask__c", "FormatMask", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("HasRule__c", "HasRule", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("HelpText__c", "HelpText", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsConfigurable__c", "IsConfigurable", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsHidden__c", "IsHidden", VlocityArtifact.FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsOverride__c", "IsOverride", VlocityArtifact.FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ObjectType__c", "ObjectType", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("PicklistId__r.vlocity_ins__GlobalKey__c", "PicklistCode", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("QueryCode__c", "QueryCode", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsQueryDriven__c", "IsQueryDriven", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("QueryLabel__c", "QueryLabel", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsReadOnly__c", "IsReadOnly", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("EndStory__c", "EndStory", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsRequired__c", "IsRequired", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("RuleData__c", "RuleData", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("RuleMessage__c", "RuleMessage", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("UIDisplayType__c", "UIDisplayType", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("UIWidgetId__c", "UIWidgetId", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ValidValues__c", "ValidValues", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ValidValuesData__c", "ValidValuesData", FieldTypeEnum.JSON_STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Value__c", "Value", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ValueDataType__c", "ValueDataType", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ValueDescription__c", "ValueDescription", VlocityArtifact.FieldTypeEnum.STRING, false, true, true));



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

    }

    @Override
    public void onBeforeDeploy() {

    }

    @Override
    public String getSObjectTypeName() {
        return "AttributeAssignment__c";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

}

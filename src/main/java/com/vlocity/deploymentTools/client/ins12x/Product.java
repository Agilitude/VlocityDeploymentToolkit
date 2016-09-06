package com.vlocity.deploymentTools.client.ins12x;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;
import com.vlocity.deploymentTools.client.VlocityArtifact;

import java.util.ArrayList;

/**
 * Created by Derek on 30/08/2016.
 */
public class Product extends VlocityArtifact {

    public Product(ArtifactTypeEnum artifactType, String packageName, String packageVersion) {
        super(artifactType, packageName, packageVersion);

        this.FieldDefinitions = new ArrayList<VlocityArtifactFieldDefinition>() {{
            //String sObjectFieldName, String propertyName, FieldTypeEnum fieldType, ArtifactTypeEnum listElementTypeName, Boolean isKey, Boolean serialise, Boolean isPackageMember
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Name", "Name", FieldTypeEnum.STRING, true, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ProductCode", "ProductCode", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("CurrencyIsoCode", "CurrencyIsoCode", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Description", "Description", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Family", "Family", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("RecordType.DeveloperName", "RecordType", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Account_SubType__c", "AccountSubType", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Application_Form__c", "ApplicationForm", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ApprovedBy__r.Email", "ApprovedBy", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ApprovedOn__c", "ApprovedOn", FieldTypeEnum.DATETIME, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Service_ATM__c", "ServiceATM", FieldTypeEnum.BOOLEAN, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Availability__c", "Availability", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Business_Required_Documents__c", "BusinessRequiredDocuments", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("CarrierId__r.Name", "Carrier", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Service_Chequebook__c", "ServiceChequebook", FieldTypeEnum.BOOLEAN, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Community_Type__c", "CommunityType", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Company__c", "Company", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsConfigurable__c", "IsConfigurable", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("EffectiveDate__c", "EffectiveDate", FieldTypeEnum.DATE, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("EndDate__c", "EndDate", FieldTypeEnum.DATE, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("For_Minors__c", "ForMinors", FieldTypeEnum.BOOLEAN, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("GlobalKey__c", "GlobalKey", FieldTypeEnum.STRING, true, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("HelpText__c", "HelpText", FieldTypeEnum.STRING, true, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ImageId__c", "ImageId", FieldTypeEnum.STRING, true, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Img_Url__c", "ImgUrl", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Internet_Banking__c", "InternetBanking", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("JSONAttribute__c", "JSONAttribute", FieldTypeEnum.JSON_STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("LineOfBusiness__c", "LineOfBusiness", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("MarketSegment__c", "MarketSegment", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Maximum_Age__c", "MaximumAge", FieldTypeEnum.INT, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Minimum_Age__c", "MinimumAge", FieldTypeEnum.INT, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Service_MobileBanking__c", "ServiceMobileBanking", FieldTypeEnum.BOOLEAN, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("IsOrderable__c", "IsOrderable", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ParentClassId__r.vlocity_ins__GlobalKey__c", "ParentClass", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("PAS_Product_Code__c", "PASProductCode", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Product_Mapping_Code__c", "ProductMappingCode", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Product_Required_Documents__c", "ProductRequiredDocuments", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Product_Rules_attachment_ID__c", "ProductRulesAttachmentId", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ProductSpecId__r.vlocity_ins__GlobalKey__c", "ProductSpec", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("ProductTemplateProductId__r.vlocity_ins__GlobalKey__c", "ProductTemplateProduct", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("RateBandId__r.Name", "RateBand", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Required_Documents__c", "RequiredDocuments", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Sales_Channel__c", "SalesChannel", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Signatory_Required_Documents__c", "SignatoryRequiredDocuments", FieldTypeEnum.STRING, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Service_SignatureCards__c", "ServiceSignatureCards", FieldTypeEnum.BOOLEAN, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Service_SMSAlerts__c", "ServiceSMSAlerts", FieldTypeEnum.BOOLEAN, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("SpecificationType__c", "SpecificationType", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("StandardPremium__c", "StandardPremium", FieldTypeEnum.DECIMAL, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Service_StatementOfAccount__c", "ServiceStatementOfAccount", FieldTypeEnum.BOOLEAN, false, true, false));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Status__c", "Status", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("SubType__c", "SubType", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("TrackAsAgreement__c", "TrackAsAgreement", FieldTypeEnum.BOOLEAN, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Type__c", "Type", FieldTypeEnum.STRING, false, true, true));
            add(new VlocityArtifact.VlocityArtifactFieldDefinition("Product_required_documents__r", "RequiredDocuments", FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT, ArtifactTypeEnum.PRODUCT_DOCUMENT, false, true, false));

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
        return "Product2";
    }

    @Override
    public String getDataPackType() { return ""; }

    @Override
    public Boolean hasDataPack() { return false; }

    @Override
    public Boolean isPackageMember() {
        return false;
    }

}

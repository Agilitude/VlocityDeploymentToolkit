package com.vlocity.deploymentTools.client;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.lang.Object;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.bind.XmlObject;
import com.vlocity.deploymentTools.logging.Logger;

/**
 * Created by Derek on 16/06/2016.
 */
public abstract class VlocityArtifact {
    protected static final String DATE_FORMAT = "yyyy-MM-dd";
    protected static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    protected static final String KEY_NAME = "Key";
    protected static final String ARTIFACT_TYPE_KEY_NAME = "ArtifactType";
    protected static final String PACKAGE_NAME_KEY_NAME = "PackageName";
    protected static final String PACKAGE_VERSION_KEY_NAME = "PackageVersion";
    protected static final String DATAPACK_KEY_NAME = "Datapack";
    protected static final String NULL_STRING = null;
    protected static final Integer NULL_INT = null;
    protected static final BigDecimal NULL_DECIMAL = null;
    protected static final Boolean NULL_BOOLEAN = null;
    protected static final Date NULL_DATE = null;
    protected static final VlocityArtifact NULL_VLOCITY_ARTIFACT = null;
    protected static final Integer MAX_RELATIONSHIP_DEPTH = 1;

    public String Key;
    public String ArtifactType;
    public String PackageName;
    public String PackageVersion;
    public String Datapack;

    protected transient HashMap<String, Object> Properties;
    protected transient ArrayList<VlocityArtifactFieldDefinition> FieldDefinitions;

    public abstract void onAfterDeserialisation();
    public abstract void onBeforeSerialisation();
    public abstract void onAfterRetrieve() throws Exception;
    public abstract void onBeforeDeploy();

    public abstract String getSObjectTypeName();
    public abstract String getDataPackType();
    public abstract Boolean hasDataPack();

    public Boolean isPackageMember() {
        return true;
    }

    public VlocityArtifact(ArtifactTypeEnum artifactType, String packageName, String packageVersion)
    {
        this.ArtifactType = artifactType.toString();
        this.PackageName = packageName;
        this.PackageVersion = packageVersion;
        this.Properties = new HashMap<>();
    }

    public String getPropertyAsString(String key) {
        if (!Properties.containsKey(key)) {
            return null;
        }

        return (String)Properties.get(key);
    }

    public LinkedTreeMap getPropertyAsJsonString(String key) {
        if (!Properties.containsKey(key)) {
            return null;
        }

        return (LinkedTreeMap)Properties.get(key);
    }

    public Integer getPropertyAsInteger(String key) {
        if (!Properties.containsKey(key)) {
            return null;
        }

        return (Integer)Properties.get(key);
    }

    public BigDecimal getPropertyAsDecimal(String key) {
        if (!Properties.containsKey(key)) {
            return null;
        }

        return (BigDecimal)Properties.get(key);
    }

    public Boolean getPropertyAsBoolean(String key) {
        if (!Properties.containsKey(key)) {
            return null;
        }

        return (Boolean)Properties.get(key);
    }

    public Date getPropertyAsDate(String key) {
        if (!Properties.containsKey(key)) {
            return null;
        }

        return (Date)Properties.get(key);
    }

    public ArrayList<VlocityArtifact> getPropertyAsListVlocityObject(String key) {
        if (!Properties.containsKey(key)) {
            return null;
        }

        return (ArrayList<VlocityArtifact>)Properties.get(key);
    }

    public void setProperty(String key, Object value) {
        Properties.put(key, value);
    }

    public void setProperties(XmlObject record) throws ParseException, ArtifactNotSupportedException, PackageNotSupportedException, VersionNotSupportedException {
        for (VlocityArtifactFieldDefinition field : this.FieldDefinitions) {

            String sObjectFieldName = getQualifiedName(field.SObjectFieldName, field.IsPackageMember);

            Object value = record.getField(sObjectFieldName);

            if (value == null) continue;

            if (field.FieldType == FieldTypeEnum.STRING) {
                value = getStringValue(record, sObjectFieldName);
                if (field.IsKey) {
                    this.Key = (String)value;
                }
            }
            else if (field.FieldType == FieldTypeEnum.JSON_STRING) {
                try {
                    value = getJsonStringValue(record, sObjectFieldName);
                }
                catch (Exception ex) {
                    value = getStringValue(record, sObjectFieldName);
                    Logger.LogAsync("Value of field '" + sObjectFieldName + "', '" + value  + "', on record '" + record.getName() + "' is not a valid JSON string", Logger.Severity.Error);
                }
            }
            else if (field.FieldType == FieldTypeEnum.INT) {
                value = getIntValue(record, sObjectFieldName);
            }
            else if (field.FieldType == FieldTypeEnum.DECIMAL) {
                value = getDecimalValue(record, sObjectFieldName);
            }
            else if (field.FieldType == FieldTypeEnum.BOOLEAN) {
                value = getBooleanValue(record, sObjectFieldName);
            }
            else if (field.FieldType == FieldTypeEnum.DATE) {
                value = getDateValue(record, sObjectFieldName);
            }
            else if (field.FieldType == FieldTypeEnum.DATETIME) {
                value = getDateTimeValue(record, sObjectFieldName);
            }
            else if (field.FieldType == FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT) {
                value = getChildElements(record, sObjectFieldName, field.ListElementTypeName, this.PackageName, this.PackageVersion);
            }

            if (value != null) {
                this.setProperty(field.PropertyName, value);
            }
        }
    }

    public SObject ToSObject() {
        SObject sObject = new SObject();
        sObject.setType(this.getQualifiedName(this.getSObjectTypeName(), this.isPackageMember()));

        for (VlocityArtifactFieldDefinition field : this.FieldDefinitions) {
            if (field.FieldType == FieldTypeEnum.JSON_STRING) {
                Gson gson = new Gson();
                sObject.setField(field.SObjectFieldName, gson.toJson(getPropertyAsJsonString(field.PropertyName)));
            }
            else {
                sObject.setField(field.SObjectFieldName, this.Properties.get(field.PropertyName));
            }
        }

        return sObject;
    }

    protected String getQualifiedName(String name) {
        return getQualifiedName(name, true);
    }

    protected String getQualifiedName(String name, Boolean isPackageMember) {
        if (!isPackageMember) return name;
        return this.PackageName + "__" + name;
    }

    public class VlocityArtifactFieldDefinition {
        public FieldTypeEnum FieldType;
        public String SObjectFieldName;
        public String PropertyName;
        public Boolean IsKey = false;
        public Boolean Serialise = true;
        public Boolean IsPackageMember = true;
        public ArtifactTypeEnum ListElementTypeName;

        public VlocityArtifactFieldDefinition(String sObjectFieldName, String propertyName, FieldTypeEnum fieldType) {
            this(sObjectFieldName, propertyName, fieldType, false, true, true);
        }

        public VlocityArtifactFieldDefinition(String sObjectFieldName, String propertyName, FieldTypeEnum fieldType, ArtifactTypeEnum listElementTypeName) {
            this(sObjectFieldName, propertyName, fieldType, listElementTypeName, false, true, true);
        }

        public VlocityArtifactFieldDefinition(String sObjectFieldName, String propertyName, FieldTypeEnum fieldType, Boolean isKey, Boolean serialise, Boolean isPackageMember) {
            this(sObjectFieldName, propertyName, fieldType, null, isKey, serialise, isPackageMember);
        }

        public VlocityArtifactFieldDefinition(String sObjectFieldName, String propertyName, FieldTypeEnum fieldType, ArtifactTypeEnum listElementTypeName, Boolean isKey, Boolean serialise, Boolean isPackageMember) {
            this.FieldType = fieldType;
            this.SObjectFieldName = sObjectFieldName;
            this.PropertyName = propertyName;
            this.ListElementTypeName = listElementTypeName;
            this.IsKey = isKey;
            this.Serialise = serialise;
            this.IsPackageMember = isPackageMember;
        }
    }

    public enum FieldTypeEnum {
        STRING,
        INT,
        DECIMAL,
        BOOLEAN,
        DATE,
        DATETIME,
        JSON_STRING,
        LIST_OF_VLOCITY_ARTIFACT,
    }

    private static String getStringValue(XmlObject record, String fieldName) {
        return (String)record.getField(fieldName);
    }

    private static LinkedTreeMap getJsonStringValue(XmlObject record, String fieldName) {
        Gson gson = new Gson();
        return gson.fromJson((String)record.getField(fieldName), LinkedTreeMap.class);
    }

    public static Integer getIntValue(XmlObject record, String fieldName) throws ParseException {
        String value = (String)record.getField(fieldName);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return (int)Float.parseFloat(value);
    }

    public static BigDecimal getDecimalValue(XmlObject record, String fieldName) throws ParseException {
        DecimalFormat fmt = new DecimalFormat();
        fmt.setParseBigDecimal(true);
        return (BigDecimal)fmt.parse((String)record.getField(fieldName));
    }

    public static Boolean getBooleanValue(XmlObject record, String fieldName) {
        return Boolean.parseBoolean((String)record.getField(fieldName));
    }

    public static LocalDate getDateValue(XmlObject record, String fieldName) {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_DATE;
        return LocalDate.parse((String)record.getField(fieldName), fmt);
    }

    public static OffsetDateTime getDateTimeValue(XmlObject record, String fieldName) {
        String value = (String)record.getField(fieldName);
        if (value == null || value.isEmpty()) {
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return OffsetDateTime.parse(value, fmt);
    }

    public static ArrayList<VlocityArtifact> getChildElements (XmlObject record, String fieldName, ArtifactTypeEnum elementTypeName, String packageName, String packageVersion) throws PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException, ParseException {

        XmlObject listOfElements = (XmlObject)record.getField(fieldName);
        if (listOfElements == null) {
            return null;
        }

        Iterator<XmlObject> elements = listOfElements.getChildren();

        ArrayList<VlocityArtifact> result = new ArrayList<>();

        while (elements.hasNext()) {
            XmlObject element = elements.next();

            if (!element.getName().getLocalPart().equals("records"))
                continue;

            VlocityArtifact vlArtifact = VlocityPackageFactory.getPackage(null, packageName, packageVersion).InitialiseArtifact(elementTypeName);
            vlArtifact.setProperties(element);
            result.add(vlArtifact);
        }

        return result;
    }

    public SoqlQueryStringBuilder getQueryStringBuilder() throws PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException {
        return getQueryStringBuilder(0);
    }

    private SoqlQueryStringBuilder getQueryStringBuilder(Integer currentRelationshipDept) throws PackageNotSupportedException, VersionNotSupportedException, ArtifactNotSupportedException {
        SoqlQueryStringBuilder builder = new SoqlQueryStringBuilder(getQualifiedName(this.getSObjectTypeName(), this.isPackageMember()));

        for (VlocityArtifactFieldDefinition field : this.FieldDefinitions) {
            if (field.FieldType == FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT) {
                if (currentRelationshipDept < MAX_RELATIONSHIP_DEPTH) {
                    VlocityArtifact child = VlocityPackageFactory.getPackage(null, this.PackageName, this.PackageVersion).InitialiseArtifact(field.ListElementTypeName);
                    builder.AddSubQuery(getQualifiedName(field.SObjectFieldName, field.IsPackageMember), child.getQueryStringBuilder(currentRelationshipDept + 1));
                }
            }
            else {
                builder.AddField(getQualifiedName(field.SObjectFieldName, field.IsPackageMember));
            }
        }

        return builder;
    }

}

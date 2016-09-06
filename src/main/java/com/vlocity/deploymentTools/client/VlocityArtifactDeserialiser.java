package com.vlocity.deploymentTools.client;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Derek on 29/06/2016.
 */
public class VlocityArtifactDeserialiser implements JsonDeserializer<VlocityArtifact> {
    @Override
    public VlocityArtifact deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jsObj = (JsonObject) json;

        if (!jsObj.has(VlocityArtifact.KEY_NAME)) {
            throw new JsonParseException(new RequiredFieldMissingException(VlocityArtifact.KEY_NAME));
        }
        else if (!jsObj.has(VlocityArtifact.ARTIFACT_TYPE_KEY_NAME)) {
            throw new JsonParseException(new RequiredFieldMissingException(VlocityArtifact.ARTIFACT_TYPE_KEY_NAME));
        }
        else if (!jsObj.has(VlocityArtifact.PACKAGE_NAME_KEY_NAME)) {
            throw new JsonParseException(new RequiredFieldMissingException(VlocityArtifact.PACKAGE_NAME_KEY_NAME));
        }
        else if (!jsObj.has(VlocityArtifact.PACKAGE_VERSION_KEY_NAME)) {
            throw new JsonParseException(new RequiredFieldMissingException(VlocityArtifact.PACKAGE_VERSION_KEY_NAME));
        }

        ArtifactTypeEnum artifactType = ArtifactTypeEnum.valueOf(jsObj.get(VlocityArtifact.ARTIFACT_TYPE_KEY_NAME).getAsString());
        String packageName = jsObj.get(VlocityArtifact.PACKAGE_NAME_KEY_NAME).getAsString();
        String packageVersion = jsObj.get(VlocityArtifact.PACKAGE_VERSION_KEY_NAME).getAsString();

        VlocityPackage vlPackage;

        try {
            vlPackage = VlocityPackageFactory.getPackage(null, packageName, packageVersion);
        }
        catch (Exception ex) {
            throw new JsonParseException(ex);
        }

        VlocityArtifact vlArtifact;

        try {
            vlArtifact = vlPackage.InitialiseArtifact(artifactType);
        }
        catch (Exception ex) {
            throw new JsonParseException(ex);
        }

        vlArtifact.Key = jsObj.get(VlocityArtifact.KEY_NAME).getAsString();
        vlArtifact.ArtifactType = artifactType.toString();
        vlArtifact.PackageName = packageName;
        vlArtifact.PackageVersion = packageVersion;

        for (VlocityArtifact.VlocityArtifactFieldDefinition field : vlArtifact.FieldDefinitions) {
            String key = field.PropertyName;

            if (!jsObj.has(key)) {
                continue;
            }

            if (field.FieldType == VlocityArtifact.FieldTypeEnum.STRING) {
                vlArtifact.setProperty(key, jsObj.get(key).getAsString());
            }
            else if (field.FieldType == VlocityArtifact.FieldTypeEnum.INT) {
                vlArtifact.setProperty(key, jsObj.get(key).getAsInt());
            }
            else if (field.FieldType == VlocityArtifact.FieldTypeEnum.DECIMAL) {
                vlArtifact.setProperty(key, jsObj.get(key).getAsBigDecimal());
            }
            else if (field.FieldType == VlocityArtifact.FieldTypeEnum.BOOLEAN) {
                vlArtifact.setProperty(key, jsObj.get(key).getAsBoolean());
            }
            else if (field.FieldType == VlocityArtifact.FieldTypeEnum.DATE) {
                SimpleDateFormat sdf = new SimpleDateFormat(VlocityArtifact.DATE_FORMAT);
                try {
                    vlArtifact.setProperty(key, sdf.parse(jsObj.get(key).getAsString()));
                }
                catch (Exception ex) {
                    throw new JsonParseException(ex);
                }
            }
            else if (field.FieldType == VlocityArtifact.FieldTypeEnum.DATETIME) {
                SimpleDateFormat sdf = new SimpleDateFormat(VlocityArtifact.DATE_FORMAT);
                try {
                    vlArtifact.setProperty(key, sdf.parse(jsObj.get(key).getAsString()));
                }
                catch (Exception ex) {
                    throw new JsonParseException(ex);
                }
            }
            else if (field.FieldType == VlocityArtifact.FieldTypeEnum.LIST_OF_VLOCITY_ARTIFACT) {
                ArrayList<VlocityArtifact> classDef = new ArrayList<>();
                vlArtifact.setProperty(key, context.deserialize(jsObj.get(key), classDef.getClass()));
            }

        }

        vlArtifact.onAfterDeserialisation();

        return vlArtifact;

    }
}

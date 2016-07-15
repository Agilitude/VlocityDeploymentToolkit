package client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Derek on 29/06/2016.
 */
public class VlocityArtifactSerialiser implements JsonSerializer<VlocityArtifact> {
    @Override
    public JsonElement serialize(VlocityArtifact src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject jsObj = new JsonObject();
        src.onBeforeSerialisation();

        jsObj.addProperty(VlocityArtifact.KEY_NAME, src.Key);
        jsObj.addProperty(VlocityArtifact.ARTIFACT_TYPE_KEY_NAME, src.ArtifactType);
        jsObj.addProperty(VlocityArtifact.PACKAGE_NAME_KEY_NAME, src.PackageName);
        jsObj.addProperty(VlocityArtifact.PACKAGE_VERSION_KEY_NAME, src.PackageVersion);
        jsObj.addProperty(VlocityArtifact.DATAPACK_KEY_NAME, src.Datapack);

        for (VlocityArtifact.VlocityArtifactFieldDefinition field : src.FieldDefinitions) {

            if (!field.Serialise || !src.Properties.containsKey(field.PropertyName)) continue;

            jsObj.add(field.PropertyName, context.serialize(src.Properties.get(field.PropertyName)));
        }

        return jsObj;

    }

}

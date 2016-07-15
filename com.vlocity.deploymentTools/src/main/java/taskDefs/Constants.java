package taskDefs;

import client.ArtifactTypesEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Derek on 04/07/2016.
 */
public final class Constants {
    public static final String OMNISCRIPT_EXT = "omniscript";
    public static final String DATARAPTOR_EXT = "dataraptor";
    public static final String CALCULATIONMATRIX_EXT = "matrix";
    public static final String ENCODING = "UTF-8";

    public static final HashSet<String> ALL_EXTENSIONS = new HashSet<>(Arrays.asList(
        OMNISCRIPT_EXT,
        DATARAPTOR_EXT,
        CALCULATIONMATRIX_EXT
    ));

    public static final HashMap<String, ArtifactTypesEnum> ARTIFACT_TYPE_MAPPED_TO_EXTENSION = new HashMap<String, ArtifactTypesEnum>() {{
        put(OMNISCRIPT_EXT, ArtifactTypesEnum.OMNISCRIPT);
        put(DATARAPTOR_EXT, ArtifactTypesEnum.DATARAPTOR);
        put(CALCULATIONMATRIX_EXT, ArtifactTypesEnum.CALCULATION_MATRIX);
    }};

    public static final HashMap<ArtifactTypesEnum, String> EXTENSIONS_MAPPED_TO_ARTIFACT_TYPE = new HashMap<ArtifactTypesEnum, String>() {{
        put(ArtifactTypesEnum.OMNISCRIPT, OMNISCRIPT_EXT);
        put(ArtifactTypesEnum.DATARAPTOR, DATARAPTOR_EXT);
        put(ArtifactTypesEnum.CALCULATION_MATRIX, CALCULATIONMATRIX_EXT);
    }};
}

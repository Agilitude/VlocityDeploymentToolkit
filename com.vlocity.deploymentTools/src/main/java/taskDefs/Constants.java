package taskDefs;

import client.ArtifactTypeEnum;

import java.util.ArrayList;
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
    public static final String APPLICATION_TEMPLATE_EXT = "apptemplate";
    public static final String CARD_EXT = "card";
    public static final String ENCODING = "UTF-8";

    public static final HashSet<String> ALL_EXTENSIONS = new HashSet<>(Arrays.asList(
        OMNISCRIPT_EXT,
        DATARAPTOR_EXT,
        CALCULATIONMATRIX_EXT,
        APPLICATION_TEMPLATE_EXT,
        CARD_EXT
    ));

    public static final HashMap<String, ArtifactTypeEnum> ARTIFACT_TYPE_MAPPED_TO_EXTENSION = new HashMap<String, ArtifactTypeEnum>() {{
        put(OMNISCRIPT_EXT, ArtifactTypeEnum.OMNISCRIPT);
        put(DATARAPTOR_EXT, ArtifactTypeEnum.DATARAPTOR);
        put(CALCULATIONMATRIX_EXT, ArtifactTypeEnum.CALCULATION_MATRIX);
        put(APPLICATION_TEMPLATE_EXT, ArtifactTypeEnum.APPLICATION_TEMPLATE);
        put(CARD_EXT, ArtifactTypeEnum.CARD);
    }};

    public static final HashMap<ArtifactTypeEnum, String> EXTENSIONS_MAPPED_TO_ARTIFACT_TYPE = new HashMap<ArtifactTypeEnum, String>() {{
        put(ArtifactTypeEnum.OMNISCRIPT, OMNISCRIPT_EXT);
        put(ArtifactTypeEnum.DATARAPTOR, DATARAPTOR_EXT);
        put(ArtifactTypeEnum.CALCULATION_MATRIX, CALCULATIONMATRIX_EXT);
        put(ArtifactTypeEnum.APPLICATION_TEMPLATE, APPLICATION_TEMPLATE_EXT);
        put(ArtifactTypeEnum.CARD, CARD_EXT);
    }};

    public static final HashSet<String> SUPPORTED_PRIMARY_ARTIFACTS = new HashSet<String>() {{
        add(ArtifactTypeEnum.OMNISCRIPT.name());
        add(ArtifactTypeEnum.DATARAPTOR.name());
        add(ArtifactTypeEnum.CALCULATION_MATRIX.name());
        add(ArtifactTypeEnum.APPLICATION_TEMPLATE.name());
        add(ArtifactTypeEnum.CARD.name());
    }};
}

package com.vlocity.deploymentTools.taskDefs;

import com.vlocity.deploymentTools.client.ArtifactTypeEnum;

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
    public static final String PRODUCT_EXT = "product";
    public static final String ATTRIBUTE_ASSIGNMENT_EXT = "attrAssignment";
    public static final String REQUIRED_DOC_EXT = "requiredDoc";
    public static final String ACTION_EXT = "action";
    public static final String PARTY_RELATIONSHIP_TYPE_EXT = "partyRelationship";

    public static final String ENCODING = "UTF-8";

    public static final HashSet<String> ALL_EXTENSIONS = new HashSet<>(Arrays.asList(
        OMNISCRIPT_EXT,
        DATARAPTOR_EXT,
        CALCULATIONMATRIX_EXT,
        APPLICATION_TEMPLATE_EXT,
        CARD_EXT,
        PRODUCT_EXT,
        ATTRIBUTE_ASSIGNMENT_EXT,
        REQUIRED_DOC_EXT,
        ACTION_EXT,
        PARTY_RELATIONSHIP_TYPE_EXT
    ));

    public static final HashMap<String, ArtifactTypeEnum> ARTIFACT_TYPE_MAPPED_TO_EXTENSION = new HashMap<String, ArtifactTypeEnum>() {{
        put(OMNISCRIPT_EXT, ArtifactTypeEnum.OMNISCRIPT);
        put(DATARAPTOR_EXT, ArtifactTypeEnum.DATARAPTOR);
        put(CALCULATIONMATRIX_EXT, ArtifactTypeEnum.CALCULATION_MATRIX);
        put(APPLICATION_TEMPLATE_EXT, ArtifactTypeEnum.APPLICATION_TEMPLATE);
        put(CARD_EXT, ArtifactTypeEnum.CARD);
        put(PRODUCT_EXT, ArtifactTypeEnum.PRODUCT);
        put(ATTRIBUTE_ASSIGNMENT_EXT, ArtifactTypeEnum.ATTRIBUTE_ASSIGNMENT);
        put(REQUIRED_DOC_EXT, ArtifactTypeEnum.REQUIRED_DOCUMENT);
        put(ACTION_EXT, ArtifactTypeEnum.ACTION);
        put(PARTY_RELATIONSHIP_TYPE_EXT, ArtifactTypeEnum.PARTY_RELATIONSHIP_TYPE);
    }};

    public static final HashMap<ArtifactTypeEnum, String> EXTENSIONS_MAPPED_TO_ARTIFACT_TYPE = new HashMap<ArtifactTypeEnum, String>() {{
        put(ArtifactTypeEnum.OMNISCRIPT, OMNISCRIPT_EXT);
        put(ArtifactTypeEnum.DATARAPTOR, DATARAPTOR_EXT);
        put(ArtifactTypeEnum.CALCULATION_MATRIX, CALCULATIONMATRIX_EXT);
        put(ArtifactTypeEnum.APPLICATION_TEMPLATE, APPLICATION_TEMPLATE_EXT);
        put(ArtifactTypeEnum.CARD, CARD_EXT);
        put(ArtifactTypeEnum.PRODUCT, PRODUCT_EXT);
        put(ArtifactTypeEnum.ATTRIBUTE_ASSIGNMENT, ATTRIBUTE_ASSIGNMENT_EXT);
        put(ArtifactTypeEnum.REQUIRED_DOCUMENT, REQUIRED_DOC_EXT);
        put(ArtifactTypeEnum.ACTION, ACTION_EXT);
        put(ArtifactTypeEnum.PARTY_RELATIONSHIP_TYPE, PARTY_RELATIONSHIP_TYPE_EXT);
    }};

    public static final HashSet<String> SUPPORTED_PRIMARY_ARTIFACTS = new HashSet<String>() {{
        add(ArtifactTypeEnum.OMNISCRIPT.name());
        add(ArtifactTypeEnum.DATARAPTOR.name());
        add(ArtifactTypeEnum.CALCULATION_MATRIX.name());
        add(ArtifactTypeEnum.APPLICATION_TEMPLATE.name());
        add(ArtifactTypeEnum.CARD.name());
        add(ArtifactTypeEnum.PRODUCT.name());
        add(ArtifactTypeEnum.ATTRIBUTE_ASSIGNMENT.name());
        add(ArtifactTypeEnum.REQUIRED_DOCUMENT.name());
        add(ArtifactTypeEnum.ACTION.name());
        add(ArtifactTypeEnum.PARTY_RELATIONSHIP_TYPE.name());
    }};
}

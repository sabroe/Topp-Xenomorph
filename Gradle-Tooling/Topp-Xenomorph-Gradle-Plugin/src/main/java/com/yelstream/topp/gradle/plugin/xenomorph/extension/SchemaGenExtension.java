package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Data
@NoArgsConstructor
public class SchemaGenExtension {
    /**
     * Extension name as used in Gradle build files.
     */
    public static final String EXTENSION_NAME="schemaGen";

/*
    private String modules;  //Eh?

    private String schemaFileName;  //Eh?

    private String schemaFileDefinitionsName;  //Eh?
*/
}

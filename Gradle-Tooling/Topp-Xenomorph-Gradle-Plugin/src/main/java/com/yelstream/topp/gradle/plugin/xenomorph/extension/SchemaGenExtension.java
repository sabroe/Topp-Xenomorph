package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Data
@RequiredArgsConstructor
public class SchemaGenExtension {
    /**
     * Extension name as used in Gradle build files.
     */
    public static final String EXTENSION_NAME="schemaGen";

    public static SchemaGenExtension get(Project project) {
        ExtensionContainer extension=project.getExtensions();
        return (SchemaGenExtension)extension.getByName(EXTENSION_NAME);
    }

    private final Project project;
}

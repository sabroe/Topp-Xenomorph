package com.yelstream.topp.gradle.plugin.xenomorph.util;

import lombok.AllArgsConstructor;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;


@AllArgsConstructor
@SuppressWarnings("java:S1192")
public class SchemaReferenceFactory {

    private final Project project;

    private SchemaReference logCreation(SchemaReference schemaReference) {
        Logger logger=project.getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Schema reference is '%s'.", schemaReference));
        }
        return schemaReference;
    }

    public SchemaReference of(String name) {
        return logCreation(SchemaReference.of(name));
    }

    public SchemaReference of(File file) {
        return logCreation(SchemaReference.of(file));
    }

    public SchemaReference of(Path path) {
        return logCreation(SchemaReference.of(path));
    }

    public SchemaReference of(URL url) {
        return logCreation(SchemaReference.of(url));
    }

    public SchemaReference of(URI uri) {
        return logCreation(SchemaReference.of(uri));
    }
}

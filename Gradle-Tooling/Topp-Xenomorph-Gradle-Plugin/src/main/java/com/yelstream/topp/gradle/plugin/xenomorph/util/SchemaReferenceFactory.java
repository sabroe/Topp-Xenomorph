package com.yelstream.topp.gradle.plugin.xenomorph.util;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public class SchemaReferenceFactory {


    public SchemaReference of(String name) {
        return SchemaReference.of(name);
    }

    public static SchemaReference of(File file) {
        return SchemaReference.of(file);
    }

    public static SchemaReference of(Path path) {
        return SchemaReference.of(path);
    }

    public static SchemaReference of(URL url) {
        return SchemaReference.of(url);
    }

    public static SchemaReference of(URI uri) {
        return SchemaReference.of(uri);
    }

}

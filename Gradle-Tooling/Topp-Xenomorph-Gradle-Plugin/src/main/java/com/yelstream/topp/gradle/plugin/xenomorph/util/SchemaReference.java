package com.yelstream.topp.gradle.plugin.xenomorph.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.gradle.api.Project;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.function.BiFunction;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@ToString
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=true)
public class SchemaReference {

    private final String name;
    private final File file;
    private final Path path;
    private final URL url;
    private final URI uri;

    private final BiFunction<Project,SchemaReference,String> mapToText;

    public String resolve(Project project) {
        return mapToText.apply(project,this);
    }

    public static SchemaReference of(String name) {
        SchemaReference.Builder builder=SchemaReference.builder();
        builder.name(name).build();
        builder.mapToText((project,schemaReference) -> {
            File file=new File(project.getProjectDir(),schemaReference.name);
            return file.getAbsolutePath();
        });
        return builder.build();
    }

    public static SchemaReference of(File file) {
        SchemaReference.Builder builder=SchemaReference.builder();
        builder.file(file).build();
        builder.mapToText((project,schemaReference) -> {
            if (file.isAbsolute()) {
                return file.toString();
            } else {
                return project.file(file).toString();
            }
        });
        return builder.build();
    }

    public static SchemaReference of(Path path) {
        SchemaReference.Builder builder=SchemaReference.builder();
        builder.path(path).build();
        builder.mapToText((project,schemaReference) -> {
            if (path.isAbsolute()) {
                return path.toString();
            } else {
                Path projectDir=project.getProjectDir().toPath();
                return projectDir.resolve(path).toString();
            }
        });
        return builder.build();
    }

    public static SchemaReference of(URL url) {
        SchemaReference.Builder builder=SchemaReference.builder();
        builder.url(url).build();
        builder.mapToText((project,schemaReference) -> {
            return url.toString();
        });
        return builder.build();
    }

    public static SchemaReference of(URI uri) {
        SchemaReference.Builder builder=SchemaReference.builder();
        builder.uri(uri).build();
        builder.mapToText((project,schemaReference) -> {
            try {
                return uri.toURL().toString();
            } catch (MalformedURLException ex) {
                throw new IllegalStateException(String.format("Failure to resolve reference; URI %s can not be converted to an URL!",uri),ex);
            }
        });
        return builder.build();
    }
}

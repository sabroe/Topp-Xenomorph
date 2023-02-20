package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import com.yelstream.topp.gradle.api.ResourceFactory;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

/**
 * Gradle extension linked to usages of task {@link com.yelstream.topp.gradle.plugin.xenomorph.task.SchemaGenTask}.
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


    public SchemaGenExtension(Project project,
                              SourceSet sourceSet) {
        this.project=project;
        resource=ResourceFactory.of(project,sourceSet);
    }

    private final Project project;


    @Getter
    private final ResourceFactory resource;
}

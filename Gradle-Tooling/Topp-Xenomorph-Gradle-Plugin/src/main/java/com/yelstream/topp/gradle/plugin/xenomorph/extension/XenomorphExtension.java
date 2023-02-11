package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import lombok.AccessLevel;
import lombok.Getter;
import org.gradle.api.Project;

/**
 * Gradle extension linked to usages of plugin.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
public class XenomorphExtension {
    /**
     * Extension name as used in Gradle build files.
     */
    public static final String EXTENSION_NAME="xenomorph";

    public XenomorphExtension(Project project) {
        this.project=project;
/*
        xjc=XJCExtension.get(project);
        schemaGen=SchemaGenExtension.get(project);
*/
    }

    @Getter(AccessLevel.PROTECTED)
    private final Project project;

/*
    @Getter
    private final XJCExtension xjc;
*/

/*
    @Getter
    private final SchemaGenExtension schemaGen;
*/
}

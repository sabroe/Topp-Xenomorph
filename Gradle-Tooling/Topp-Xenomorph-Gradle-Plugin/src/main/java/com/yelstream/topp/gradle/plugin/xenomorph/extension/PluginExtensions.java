package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

/**
 * Creates and holds Gradle extensions.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class PluginExtensions {

    /**
     *
     */
    private final XenomorphExtensions xenomorphExtensions;

    /**
     *
     */
    private final XJCExtensions xjcExtensions;

    /**
     *
     */
    private final SchemaGenExtensions schemaGenExtensions;

    public void register(SourceSet sourceSet) {
        xenomorphExtensions.register(sourceSet);
        xjcExtensions.register(sourceSet);
        schemaGenExtensions.register(sourceSet);
    }

    /**
     *
     */
    public static PluginExtensions of(Project project) {
        Builder builder=builder();
        builder.xenomorphExtensions(XenomorphExtensions.of(project));
        builder.xjcExtensions(XJCExtensions.of(project));
        builder.schemaGenExtensions(SchemaGenExtensions.of(project));
        return builder.build();
    }
}

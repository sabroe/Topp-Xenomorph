package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

/**
 * Creates and holds Gradle extensions for the overall plugin.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-10
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class XenomorphExtensions {
    /**
     *
     */
    private final Project project;

    public void register(SourceSet sourceSet) {
//
    }

    /**
     *
     * @param project Project.
     * @return Instance.
     */
    public static XenomorphExtensions of(Project project) {
        Builder builder=builder();
        builder.project(project);
        return builder.build();
    }
}

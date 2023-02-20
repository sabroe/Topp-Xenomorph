package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import com.yelstream.topp.gradle.api.SourceSets;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.SourceSet;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates and holds Gradle extensions for all tasks of type
 * {@link com.yelstream.topp.gradle.plugin.xenomorph.task.SchemaGenTask}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-10
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class SchemaGenExtensions {
    /**
     * Project.
     */
    private final Project project;

    @AllArgsConstructor
    public static class ExtensionGroup {
        private final SchemaGenExtension globalExtension;

        private final SchemaGenExtension sourceSetExtension;
    }

    /**
     * Registered extensions.
     * Elements are (<source set name>, <extension>).
     */
    private final Map<String,ExtensionGroup> extensions=new HashMap<>();

    public void register(SourceSet sourceSet) {
        SchemaGenExtension globalExtension=createGlobalExtension(project,sourceSet);
        SchemaGenExtension sourceSetExtension=createSourceSetExtension(project,sourceSet);
        extensions.put(sourceSet.getName(),new ExtensionGroup(globalExtension,sourceSetExtension));
    }

    private static SchemaGenExtension createGlobalExtension(Project project,
                                                            SourceSet sourceSet) {
        ExtensionContainer extensions=project.getExtensions();
        String name=SourceSets.NameStrategy.ShortConvention.toString("jxc",sourceSet.getName());
        return extensions.create(name,SchemaGenExtension.class,project,sourceSet);
    }

    private static SchemaGenExtension createSourceSetExtension(Project project,
                                                               SourceSet sourceSet) {
        ExtensionContainer extensions=sourceSet.getExtensions();
        String name=SchemaGenExtension.EXTENSION_NAME;  //Yes, the naming is always the same and disregarding the source set!
        SchemaGenExtension extension=extensions.create(name,SchemaGenExtension.class,project,sourceSet);
        Logger logger=project.getLogger();
//        logger.info(String.format("Created custom extension '%s' and added it to source-set '%s'!", name, sourceSet.getName()));
        return extension;
    }

    /**
     *
     * @param project Project.
     * @return Instance.
     */
    public static SchemaGenExtensions of(Project project) {
        Builder builder=builder();
        builder.project(project);
        return builder.build();
    }
}

package com.yelstream.topp.gradle.api;

import com.yelstream.topp.lang.Strings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;

/**
 * Describes information associated with a named source-set.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-07
 */
@ToString
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=false)
public class SourceSetDescriptor {
    /**
     * Name of source-set.
     * This identifies an instance of {@link SourceSet}.
     */
    private final String sourceSetName;

    /**
     * For the given source-set, the name of the associated "classes" task.
     * @See <a href="https://docs.gradle.org/current/userguide/java_plugin.html#java_source_set_tasks">SourceSet Tasks</a>
     */
    private final String classesTaskName;

    /**
     * For the given source-set, the name of the associated "processResources" task.
     * @See <a href="https://docs.gradle.org/current/userguide/java_plugin.html#java_source_set_tasks">SourceSet Tasks</a>
     */
    private final String processResourcesTaskName;

    /**
     * For the given source-set, the name of the associated "compileJava" task.
     * @See <a href="https://docs.gradle.org/current/userguide/java_plugin.html#java_source_set_tasks">SourceSet Tasks</a>
     */
    private final String compileJavaTaskName;

    /**
     * Creates a new source-set descriptor.
     * @param sourceSet Source-set.
     * @return Source-set descriptor.
     */
    public static SourceSetDescriptor of(SourceSet sourceSet) {
        return of(sourceSet.getName());
    }

    /**
     * Creates a new source-set descriptor.
     * @param sourceSetName Name of source-set.
     * @return Source-set descriptor.
     */
    public static SourceSetDescriptor of(String sourceSetName) {
        SourceSetDescriptor descriptor=null;
        if (sourceSetName!=null) {
            Builder builder=builder().sourceSetName(sourceSetName);
            if (sourceSetName.equals(SourceSet.MAIN_SOURCE_SET_NAME)) {
                builder.classesTaskName(JavaPlugin.CLASSES_TASK_NAME)
                       .processResourcesTaskName(JavaPlugin.PROCESS_RESOURCES_TASK_NAME)
                       .compileJavaTaskName(JavaPlugin.COMPILE_JAVA_TASK_NAME);
            } else {
                if (sourceSetName.equals(SourceSet.TEST_SOURCE_SET_NAME)) {
                    builder.classesTaskName(JavaPlugin.TEST_CLASSES_TASK_NAME)
                           .processResourcesTaskName(JavaPlugin.PROCESS_TEST_RESOURCES_TASK_NAME)
                           .compileJavaTaskName(JavaPlugin.COMPILE_TEST_JAVA_TASK_NAME);
                } else {
                    String capitalizedSourceSetName=Strings.capitalizeFirstLetter(sourceSetName);
                    builder.classesTaskName(String.format("%sClasses",sourceSetName))
                           .processResourcesTaskName(String.format("process%sResources",capitalizedSourceSetName))
                           .compileJavaTaskName(String.format("compile%sJava",capitalizedSourceSetName));
                }
            }
            descriptor=builder.build();
        }
        return descriptor;
    }
}

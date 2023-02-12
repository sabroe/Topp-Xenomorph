package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.gradle.api.Configurations;
import com.yelstream.topp.gradle.api.SourceSetDescriptor;
import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-11
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class SchemaGenTasks {
    /**
     *
     */
    private final Project project;

    /**
     * Provider of task named {@link SchemaGenTask#TASK_NAME}.
     * Elements are (<source-set-name>,<task-provider>).
     */
    private final Map<String, TaskProvider<SchemaGenTask>> schemaGenTaskProviders=new HashMap<>();




    public void register(PluginConfigurations pluginConfigurations,
                         PluginExtensions pluginExtensions,
                         SourceSet sourceSet) {
        TaskContainer tasks= project.getTasks();
        String taskName= Configurations.NameStrategy.ShortConvention.toString(SchemaGenTask.TASK_NAME,sourceSet);
        TaskProvider<SchemaGenTask> taskProvider=tasks.register(taskName,SchemaGenTask.class,pluginConfigurations,pluginExtensions,sourceSet);
        SourceSetDescriptor.declareTaskDependencyFromCompileJavaTask(project,sourceSet,taskProvider);
        schemaGenTaskProviders.put(sourceSet.getName(),taskProvider);
    }

    /**
     *
     */
    public static SchemaGenTasks of(Project project) {
        Builder builder=builder();
        builder.project(project);
        return builder.build();
    }
}

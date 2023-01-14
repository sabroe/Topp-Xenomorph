package com.yelstream.topp.gradle.plugin.xenomorph.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

/**
 * Plugin tasks.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class PluginTasks {

    private final TaskProvider<XJCTask> xjcTaskProvider;

    private final TaskProvider<SchemaGenTask> schemaGenTaskProvider;

    public static PluginTasks register(Project project) {
        TaskContainer tasks=project.getTasks();
        return register(tasks);
    }

    public static PluginTasks register(TaskContainer tasks) {
        Builder builder=builder();
        builder.xjcTaskProvider(tasks.register(XJCTask.TASK_NAME,XJCTask.class));
        builder.schemaGenTaskProvider(tasks.register(SchemaGenTask.TASK_NAME,SchemaGenTask.class));
        return builder.build();
    }
}

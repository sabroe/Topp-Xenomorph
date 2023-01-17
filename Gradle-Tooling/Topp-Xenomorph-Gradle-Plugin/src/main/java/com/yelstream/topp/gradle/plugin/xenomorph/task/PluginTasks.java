package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

import java.util.function.Supplier;

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

    private final TaskProvider<XenTask> xenTaskProvider;

    private final TaskProvider<XJCTask> xjcTaskProvider;

    private final TaskProvider<SchemaGenTask> schemaGenTaskProvider;

    public static PluginTasks register(Project project,
                                       Supplier<PluginContext> pluginContextSupplier) {
        TaskContainer tasks=project.getTasks();
        PluginTasks pluginTasks=create(tasks,pluginContextSupplier);
        pluginTasks.declareTaskDependencies(project);
        return pluginTasks;
    }

    private static PluginTasks create(TaskContainer tasks,
                                      Supplier<PluginContext> pluginContextSupplier) {
        Builder builder=builder();
        builder.xenTaskProvider(tasks.register(XenTask.TASK_NAME,XenTask.class,pluginContextSupplier));
        builder.xjcTaskProvider(tasks.register(XJCTask.TASK_NAME,XJCTask.class,pluginContextSupplier));
        builder.schemaGenTaskProvider(tasks.register(SchemaGenTask.TASK_NAME,SchemaGenTask.class,pluginContextSupplier));
        return builder.build();
    }

    public void declareTaskDependencies(Project project) {
        project.getPluginManager().withPlugin("java"/*JavaBasePlugin*/, appliedPlugin->{
            TaskContainer tasks=project.getTasks();
            Task compileJavaTask=tasks.getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME);

            compileJavaTask.dependsOn(xjcTaskProvider);
            compileJavaTask.dependsOn(schemaGenTaskProvider);
        });
        project.getPluginManager().withPlugin("java", appliedPlugin->{
            TaskContainer tasks=project.getTasks();
            Task sourcesJarTask=tasks.getByName("sourcesJar");  //Which Gradle Plugin defines this task?

            sourcesJarTask.dependsOn(xjcTaskProvider);
            sourcesJarTask.dependsOn(schemaGenTaskProvider);
        });
    }
}

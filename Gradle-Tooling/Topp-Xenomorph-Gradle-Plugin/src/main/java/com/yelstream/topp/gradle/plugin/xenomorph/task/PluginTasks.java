package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.gradle.api.Configurations;
import com.yelstream.topp.gradle.api.SourceSetDescriptor;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

import java.util.Map;
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
    /**
     * Provider of task named {@link XenTask#TASK_NAME}.
     */
    private final TaskProvider<XenTask> xenTaskProvider;

    /**
     * Provider of task named {@link XJCTask#TASK_NAME}.
     */
    @Singular
    private final Map<String,TaskProvider<XJCTask>> xjcTaskProviders;

    /**
     * Provider of task named {@link SchemaGenTask#TASK_NAME}.
     */
    @Singular
    private final Map<String,TaskProvider<SchemaGenTask>> schemaGenTaskProviders;

    /**
     * Registers all tasks.
     * @param project Project.
     * @param pluginContextSupplier Supplier of plugin context.
     * @return Plugin tasks.
     */
    public static PluginTasks register(Project project,
                                       Supplier<PluginContext> pluginContextSupplier) {
        TaskContainer tasks=project.getTasks();
        PluginTasks pluginTasks=create(project,tasks,pluginContextSupplier);
        pluginTasks.declareTaskDependencies(project);
        return pluginTasks;
    }

    /**
     * Registers all tasks.
     * @param tasks Tasks.
     * @param pluginContextSupplier Supplier of plugin context.
     * @return Plugin tasks.
     */
    private static PluginTasks create(Project project,
                                      TaskContainer tasks,
                                      Supplier<PluginContext> pluginContextSupplier) {


        Builder builder=builder();
        builder.xenTaskProvider(tasks.register(XenTask.TASK_NAME,XenTask.class,pluginContextSupplier));

        JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
        javaPluginExtension.getSourceSets().all(
                sourceSet -> {
                    String taskName=Configurations.NameStrategy.ShortConvention.toString(XJCTask.TASK_NAME,sourceSet);
                    String configurationName=taskName;
                    String sourceSetName=sourceSet.getName();
                    builder.xjcTaskProvider(sourceSetName,tasks.register(taskName,XJCTask.class,pluginContextSupplier,configurationName,sourceSetName));
                });

        builder.schemaGenTaskProvider(SchemaGenTask.TASK_NAME,tasks.register(SchemaGenTask.TASK_NAME,SchemaGenTask.class,pluginContextSupplier));
        return builder.build();
    }

    /**
     * Declares dependencies to the known tasks.
     * @param project Project.
     */
    public void declareTaskDependencies(Project project) {

        project.getPluginManager().withPlugin("java"/*JavaBasePlugin*/,appliedPlugin->{
            JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
            javaPluginExtension.getSourceSets().all(
                sourceSet -> {
                    SourceSetDescriptor x=SourceSetDescriptor.of(sourceSet);
System.out.println();
System.out.println("AAA: "+x);

                    TaskContainer tasks=project.getTasks();
System.out.println("AAA: tasks: "+tasks);
System.out.println("AAA: xjcTaskProviders: "+xjcTaskProviders.keySet());
                    {
                        Task task=tasks.getByName(x.getCompileJavaTaskName());
System.out.println("AAA-0: "+x.getCompileJavaTaskName());
System.out.println("AAA-1: "+task);
System.out.println("AAA-2: "+xjcTaskProviders.get(x.getSourceSetName()));
System.out.println("AAA-3: "+schemaGenTaskProviders.get(x.getSourceSetName()));
System.out.println();

//                        task.dependsOn(xjcTaskProviders.get(x.getSourceSetName()));
//                        task.dependsOn(schemaGenTaskProviders.get(x.getSourceSetName()));
                    }

                    {
                        Task task=tasks.getByName(x.getClassesTaskName());

//                        task.dependsOn(xjcTaskProviders.get(x.getSourceSetName()));
//                        task.dependsOn(schemaGenTaskProviders.get(x.getSourceSetName()));
                    }
            });
        });
    }
}

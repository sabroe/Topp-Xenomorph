package com.yelstream.topp.gradle.plugin.xenomorph;

import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import com.yelstream.topp.gradle.plugin.xenomorph.task.PluginTasks;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.TaskContainer;

/**
 * The "Xenomorph" Gradle plugin addressing Jakarta JAXB.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-11
 */
public class XenomorphPlugin implements Plugin<Project> {

    private PluginExtensions pluginExtensions;

    private PluginTasks pluginTasks;

    private PluginConfigurations pluginConfigurations;

    @Override
    public void apply(Project project) {
        pluginExtensions=PluginExtensions.register(project);

        pluginTasks=PluginTasks.register(project);

        project.getPluginManager().withPlugin("java", appliedPlugin->{
            TaskContainer tasks=project.getTasks();
            Task generateTask=tasks.getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME);
            generateTask.dependsOn(pluginTasks.getXjcTaskProvider());
            generateTask.dependsOn(pluginTasks.getSchemaGenTaskProvider());
        });

        pluginConfigurations=PluginConfigurations.register(project);

        /*
        project.getPluginManager().withPlugin("java", appliedPlugin->{
            Jar jarTask=(Jar)project.getTasks().getByName(JavaPlugin.JAR_TASK_NAME);
//            x.exclude((FileTreeElement el) -> el.getFile().toPath().startsWith("${project.buildDir}/resources/main"));
//            x.from("${project.buildDir}/oggy/json-schema/normalized");
            File buildDir=project.getBuildDir();
            jarTask.exclude((FileTreeElement el)->el.getFile().toPath().startsWith(new File(buildDir,"resources/main").toPath()));
            jarTask.from(new File(buildDir,"oggy/json-schema/normalized"));
        });
        */
    }
}

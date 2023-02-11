package com.yelstream.topp.gradle.plugin.xenomorph;

import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import com.yelstream.topp.gradle.plugin.xenomorph.task.PluginTasks;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;

import java.util.function.Supplier;

/**
 * The "Xenomorph" Gradle plugin addressing Jakarta JAXB.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-11
 */
public class XenomorphPlugin implements Plugin<Project> {
    /**
     * Plugin context.
     */
    private PluginContext pluginContext;

    @Override
    public void apply(Project project) {
        Supplier<PluginContext> pluginContextSupplier=()->pluginContext;

        pluginContext=PluginContext.builder().pluginConfigurations(PluginConfigurations.of(project)).build();
        pluginContext=pluginContext.toBuilder().pluginExtensions(PluginExtensions.of(project)).build();
        pluginContext=pluginContext.toBuilder().pluginTasks(PluginTasks.register(project,pluginContextSupplier)).build();

        //Add extension "xjc" to each and every source-set:
        {
            project.getPluginManager().withPlugin("java"/*JavaBasePlugin*/,appliedPlugin-> {
                JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
                javaPluginExtension.getSourceSets().all(
                    sourceSet -> {

                        pluginContextSupplier.get().getPluginConfigurations().register(sourceSet);
                        pluginContextSupplier.get().getPluginExtensions().register(sourceSet);

                });
            });
        }
    }
}

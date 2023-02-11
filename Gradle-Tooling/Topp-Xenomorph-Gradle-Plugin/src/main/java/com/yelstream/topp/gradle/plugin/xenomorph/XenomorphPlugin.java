package com.yelstream.topp.gradle.plugin.xenomorph;

import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;

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
        pluginContext=PluginContext.of(project);

        project.getPluginManager().withPlugin("java"/*JavaBasePlugin*/,appliedPlugin-> {
            JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
            javaPluginExtension.getSourceSets().all(sourceSet -> pluginContext.register(sourceSet));
        });
    }
}

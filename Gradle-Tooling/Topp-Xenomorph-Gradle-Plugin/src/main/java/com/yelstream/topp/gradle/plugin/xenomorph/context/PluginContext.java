package com.yelstream.topp.gradle.plugin.xenomorph.context;

import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import com.yelstream.topp.gradle.plugin.xenomorph.task.PluginTasks;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

/**
 * Plugin context.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-16
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class PluginContext {
    /**
     * Plugin configurations.
     */
    private final PluginConfigurations pluginConfigurations;

    /**
     * Plugin extensions.
     */
    private final PluginExtensions pluginExtensions;

    /**
     * Plugin tasks.
     */
    private final PluginTasks pluginTasks;

    public static PluginContext of(Project project) {
        Builder builder=builder();
        builder.pluginConfigurations(PluginConfigurations.of(project));
        builder.pluginExtensions(PluginExtensions.of(project));
        builder.pluginTasks(PluginTasks.of(project));
        return builder.build();
    }

    public void register(SourceSet sourceSet) {
        pluginConfigurations.register(sourceSet);
        pluginExtensions.register(sourceSet);
        pluginTasks.register(sourceSet,this);
    }
}

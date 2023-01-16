package com.yelstream.topp.gradle.plugin.xenomorph.context;

import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import com.yelstream.topp.gradle.plugin.xenomorph.task.PluginTasks;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Plugin context.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-16
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
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
}

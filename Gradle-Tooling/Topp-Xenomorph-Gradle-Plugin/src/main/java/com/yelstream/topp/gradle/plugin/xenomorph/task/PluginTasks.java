package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

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


    private final XenTasks xenTasks;

    private final SchemaGenTasks schemaGenTasks;

    private final XJCTasks xjcTasks;


    public void register(PluginConfigurations pluginConfigurations,
                         PluginExtensions pluginExtensions,
                         SourceSet sourceSet) {
        xenTasks.register(pluginConfigurations,pluginExtensions,sourceSet);
        schemaGenTasks.register(pluginConfigurations,pluginExtensions,sourceSet);
        xjcTasks.register(pluginConfigurations,pluginExtensions,sourceSet);
    }

    /**
     *
     */
    public static PluginTasks of(Project project) {
        Builder builder=builder();
        builder.xenTasks(XenTasks.of(project));
        builder.schemaGenTasks(SchemaGenTasks.of(project));
        builder.xjcTasks(XJCTasks.of(project));
        return builder.build();
    }


}

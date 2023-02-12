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
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-11
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class XenTasks {
    /**
     *
     */
    private final Project project;

    /**
     * Provider of task named {@link XenTask#TASK_NAME}.
     */
//    private final TaskProvider<XenTask> xenTaskProvider;

    public void register(PluginConfigurations pluginConfigurations,
                         PluginExtensions pluginExtensions,
                         SourceSet sourceSet) {
    }

    /**
     *
     */
    public static XenTasks of(Project project) {
        Builder builder=builder();
        builder.project(project);
        return builder.build();
    }
}

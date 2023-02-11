package com.yelstream.topp.gradle.plugin.xenomorph.configuration;

import com.yelstream.topp.gradle.api.ConfigurationDescriptor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.SourceSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates and holds Gradle configurations for all tasks of type
 * {@link com.yelstream.topp.gradle.plugin.xenomorph.task.XJCTask}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class XJCConfigurations {
    /**
     * Name of configuration to be used by task "xjc".
     */
    public static final String XJC_CONFIGURATION_NAME="xjc";

    /**
     * Default dependencies for configuration {@link #XJC_CONFIGURATION_NAME}.
     */
    public static final List<String> xjcDefaultDependencies=
        List.of("com.sun.xml.bind:jaxb-xjc:4.0.1");

    public static final ConfigurationDescriptor XJC_CONFIGURATION_DESCRIPTOR=
        ConfigurationDescriptor.of(XJC_CONFIGURATION_NAME,
            name->String.format("JAXB dependencies for generation of sources from schema for source set '%s'.",name),
            xjcDefaultDependencies);

    /**
     * Gradle project.
     */
    private final Project project;

    /**
     * Registered providers of configurations named {@link #XJC_CONFIGURATION_NAME}.
     * Elements are (<source set name>, <configuration provider>).
     */
    private final Map<String,NamedDomainObjectProvider<Configuration>> configurationProviders=new HashMap<>();

    public NamedDomainObjectProvider<Configuration> getConfigurationProvider(SourceSet sourceSet) {
        return configurationProviders.get(sourceSet.getName());
    }

    public NamedDomainObjectProvider<Configuration> getConfigurationProvider(String sourceSetName) {
        return configurationProviders.get(sourceSetName);
    }

    /**
     * Registers all plugin configurations.
     * @param project Project.
     * @return Plugin configurations.
     */
    public static XJCConfigurations of(Project project) {
        Builder builder=builder();
        builder.project(project);
        return builder.build();
    }

    /**
     * Registers a new configuration.
     * @return Configuration provider.
     */
    public NamedDomainObjectProvider<Configuration> register(SourceSet sourceSet) {
        NamedDomainObjectProvider<Configuration> configuration=XJC_CONFIGURATION_DESCRIPTOR.register(project,sourceSet);
        configurationProviders.put(sourceSet.getName(),configuration);
        return configuration;
    }
}

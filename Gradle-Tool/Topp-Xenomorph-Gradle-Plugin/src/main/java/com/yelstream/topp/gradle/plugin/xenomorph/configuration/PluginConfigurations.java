package com.yelstream.topp.gradle.plugin.xenomorph.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;

/**
 * Plugin configurations.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class PluginConfigurations {

    public final static String SCHEMA_REFERENCE_CONFIGURATION_NAME="schemaReference";

    public final static String SCHEMA_INCLUSION_CONFIGURATION_NAME="schemaInclusion";

    private final NamedDomainObjectProvider<Configuration> schemaReferenceConfigurationProvider;
    private final NamedDomainObjectProvider<Configuration> schemaInclusionConfigurationProvider;

    public static PluginConfigurations register(Project project) {
        ConfigurationContainer configurations=project.getConfigurations();
        return register(configurations);
    }

    public static PluginConfigurations register(ConfigurationContainer configurations) {
        Builder builder=builder();
        builder.schemaInclusionConfigurationProvider(configurations.register(SCHEMA_REFERENCE_CONFIGURATION_NAME));
        builder.schemaInclusionConfigurationProvider(configurations.register(SCHEMA_INCLUSION_CONFIGURATION_NAME));
        return builder.build();
    }
}

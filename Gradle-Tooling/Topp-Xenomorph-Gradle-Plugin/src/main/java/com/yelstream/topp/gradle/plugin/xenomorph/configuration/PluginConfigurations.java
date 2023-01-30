package com.yelstream.topp.gradle.plugin.xenomorph.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;

import java.util.List;

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
    /*
     * Note:
     *     Jakarta JAXB 4 has these dependencies and in total with tools and runtime dependencies and everything:
     *         1) 'jakarta.annotation:jakarta.annotation-api:2.1.1'
     *         2) 'jakarta.activation:jakarta.activation-api:2.1.0'
     *         3) 'org.eclipse.angus:angus-activation:1.0.0'
     *         4) 'jakarta.xml.bind:jakarta.xml.bind-api:4.0.0'
     *         5) 'com.sun.xml.bind:jaxb-core:4.0.1'
     *         6) 'com.sun.xml.bind:jaxb-impl:4.0.1'
     *         7) 'com.sun.xml.bind:jaxb-xjc:4.0.1'  //XJC
     *         8) 'com.sun.xml.bind:jaxb-jxc:4.0.1'  //JXC/SchemaGen
     *     See https://eclipse-ee4j.github.io/jaxb-ri/ !
     */

    /**
     * Name of configuration to be used by task "xjc".
     */
    public static final String XJC_CONFIGURATION_NAME="xjc";

    /**
     * Name of configuration to be used by task "jxc".
     */
    public static final String JXC_CONFIGURATION_NAME="jxc";

    /**
     * Default dependencies for configuration {@link #XJC_CONFIGURATION_NAME}.
     */
    public static final List<String> xjcDefaultDependencies=
        List.of("com.sun.xml.bind:jaxb-xjc:4.0.1");

    /**
     * Default dependencies for configuration {@link #JXC_CONFIGURATION_NAME}.
     */
    public static final List<String> jxcDefaultDependencies =
        List.of("com.sun.xml.bind:jaxb-jxc:4.0.1");

    /**
     * Registered provider of configuration named {@link #XJC_CONFIGURATION_NAME}.
     */
    private final NamedDomainObjectProvider<Configuration> xjcConfigurationProvider;

    /**
     * Registered provider of configuration named {@link #JXC_CONFIGURATION_NAME}.
     */
    private final NamedDomainObjectProvider<Configuration> jxcConfigurationProvider;

    /**
     * Registers all plugin configurations.
     * @param project Project.
     * @return Plugin configurations.
     */
    public static PluginConfigurations register(Project project) {
        ConfigurationContainer configurations=project.getConfigurations();
        Builder builder=builder();
        builder.xjcConfigurationProvider(configurations.register(XJC_CONFIGURATION_NAME,configuration -> {
            configuration.setVisible(false);
            configuration.setCanBeConsumed(false);
            configuration.setCanBeResolved(true);
            configuration.setDescription("JAXB dependencies for generation of sources from schema.");
            configuration.defaultDependencies(dependencySet->xjcDefaultDependencies.forEach(dependencyNotation->dependencySet.add(project.getDependencies().create(dependencyNotation))));
        }));
        builder.jxcConfigurationProvider(configurations.register(JXC_CONFIGURATION_NAME,configuration -> {
            configuration.setVisible(false);
            configuration.setCanBeConsumed(false);
            configuration.setCanBeResolved(true);
            configuration.setDescription("JAXB dependencies for generation of schema from sources.");
            configuration.defaultDependencies(dependencySet->jxcDefaultDependencies.forEach(dependencyNotation->dependencySet.add(project.getDependencies().create(dependencyNotation))));
        }));
        return builder.build();
    }
}

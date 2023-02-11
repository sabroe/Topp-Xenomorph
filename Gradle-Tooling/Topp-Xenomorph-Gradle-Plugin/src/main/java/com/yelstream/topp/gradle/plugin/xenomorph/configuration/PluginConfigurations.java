package com.yelstream.topp.gradle.plugin.xenomorph.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;

/**
 * Creates and holds Gradle configurations for all tasks.
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

    private final XJCConfigurations xjcConfigurations;

    private final SchemaGenConfigurations schemaGenConfigurations;

    /**
     * Registers all plugin configurations.
     * @param project Project.
     * @return Plugin configurations.
     */
    public static PluginConfigurations of(Project project) {
        Builder builder=builder();
        builder.xjcConfigurations(XJCConfigurations.of(project));
        builder.schemaGenConfigurations(SchemaGenConfigurations.of(project));
        return builder.build();
    }

    /**
     * Registers all plugin configurations.
     * @return Plugin configurations.
     */
    public void register(SourceSet sourceSet) {
        xjcConfigurations.register(sourceSet);
        schemaGenConfigurations.register(sourceSet);
    }
}

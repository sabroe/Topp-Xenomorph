package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

/**
 * Plugin extensions.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Builder(builderClassName="Builder",toBuilder=true)
public class PluginExtensions {

    private final XenomorphExtension xenomorphExtension;

    private final XJCExtension xjcExtension;

    private final SchemaGenExtension schemaGenExtension;

    public static PluginExtensions register(Project project) {
        ExtensionContainer extensions=project.getExtensions();
        return register(extensions);
    }

    public static PluginExtensions register(ExtensionContainer extensions) {
        Builder builder=builder();
        builder.xjcExtension(extensions.create(XJCExtension.EXTENSION_NAME,XJCExtension.class));
        builder.schemaGenExtension(extensions.create(SchemaGenExtension.EXTENSION_NAME,SchemaGenExtension.class));
        builder.xenomorphExtension(extensions.create(XenomorphExtension.EXTENSION_NAME,XenomorphExtension.class));  //Yes, create this last!
        return builder.build();
    }
}

package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.grind.gradle.api.io.ChainedResourceLoader;
import com.yelstream.topp.grind.gradle.api.io.ConfigurationResourceLoader;
import com.yelstream.topp.grind.gradle.api.io.FileCollectionResourceLoader;
import com.yelstream.topp.grind.gradle.api.io.ResourceLoader;
import lombok.Getter;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
public class ResourceLoaderContainer {
    @Getter
    private final NamedDomainObjectProvider<Configuration> schemaReferenceConfiguration;

    @Getter
    private final SourceSetContainer javaSourceSets;

    @Getter
    private final SourceSet mainJavaSourceSet;

    @Getter
    private final FileCollection compileClassPathMainJavaFileCollection;

    @Getter(lazy=true)
    private final Supplier<FileCollectionResourceLoader> compileClassPathMainJavaResourceLoaderSupplier=()->new FileCollectionResourceLoader(javaSourceSets,mainJavaSourceSet,compileClassPathMainJavaFileCollection);

    @Getter(lazy=true)
    private final Supplier<ConfigurationResourceLoader> schemaResourceLoaderSupplier=()->new ConfigurationResourceLoader(getSchemaReferenceConfiguration().get());

    @Getter(lazy=true)
    private final Supplier<ResourceLoader> resourceLoaderSupplier=()-> ChainedResourceLoader.of(getCompileClassPathMainJavaResourceLoaderSupplier().get(),getSchemaResourceLoaderSupplier().get());

    public ResourceLoaderContainer(Project project,
                                   PluginConfigurations pluginConfigurations) {
        schemaReferenceConfiguration=pluginConfigurations.getSchemaReferenceConfigurationProvider();

        JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
        javaSourceSets=javaPluginExtension.getSourceSets();
        mainJavaSourceSet=javaSourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
        compileClassPathMainJavaFileCollection=mainJavaSourceSet.getCompileClasspath();
    }
}

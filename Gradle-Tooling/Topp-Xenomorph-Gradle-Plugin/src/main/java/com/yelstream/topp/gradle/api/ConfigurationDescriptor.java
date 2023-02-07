package com.yelstream.topp.gradle.api;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Singular;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Describes information associated with a named configuration.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-07
 */
@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@lombok.Builder(builderClassName="Builder",toBuilder=false)
public class ConfigurationDescriptor {

    private final String configurationNamePart;

    @lombok.Builder.Default
    private final Configurations.NameStrategy nameStrategy=Configurations.NameStrategy.ShortConvention;

    @lombok.Builder.Default
    private final boolean visible=false;

    @lombok.Builder.Default
    private final boolean canBeConsumed=false;

    @lombok.Builder.Default
    private final boolean canBeResolved=true;

    private final UnaryOperator<String> descriptionProvider;

    @Singular
    private final List<String> defaultDependencies;

    public NamedDomainObjectProvider<Configuration> register(Project project) {
        String configurationName=configurationNamePart;
        String sourceSetName=null;
        return register(project,configurationName,sourceSetName);
    }

    public Map<String,NamedDomainObjectProvider<Configuration>> register(Project project,
                                                                         SourceSetContainer sourceSets) {
        Map<String,NamedDomainObjectProvider<Configuration>> configurations=new HashMap<>();
        sourceSets.all(sourceSet->{
            NamedDomainObjectProvider<Configuration> configuration=register(project,sourceSet);
            configurations.put(configuration.getName(),configuration);
        });
        return configurations;
    }

    public NamedDomainObjectProvider<Configuration> register(Project project,
                                                             SourceSet sourceSet) {
        String configurationName=nameStrategy.toString(configurationNamePart,sourceSet);
        String sourceSetName=sourceSet.getName();
        return register(project,configurationName,sourceSetName);
    }

    private NamedDomainObjectProvider<Configuration> register(Project project,
                                                              String configurationName,
                                                              String sourceSetName) {
        ConfigurationContainer configurations=project.getConfigurations();
        return configurations.register(configurationName,configuration -> {
            configuration.setVisible(visible);
            configuration.setCanBeConsumed(canBeConsumed);
            configuration.setCanBeResolved(canBeResolved);
            configuration.setDescription(descriptionProvider.apply(sourceSetName));
            configuration.defaultDependencies(dependencySet -> defaultDependencies.forEach(dependencyNotation -> dependencySet.add(project.getDependencies().create(dependencyNotation))));
        });
    }

    public static ConfigurationDescriptor of(String configurationNamePart,
                                             UnaryOperator<String> descriptionProvider,
                                             List<String> defaultDependencies) {
        return builder().configurationNamePart(configurationNamePart).descriptionProvider(descriptionProvider).defaultDependencies(defaultDependencies).build();
    }
}

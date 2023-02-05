package com.yelstream.topp.gradle.plugin.xenomorph;

import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.XJCExtension;
import com.yelstream.topp.gradle.plugin.xenomorph.task.PluginTasks;
import groovy.lang.Closure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.plugins.DslObject;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.reflect.HasPublicType;
import org.gradle.api.reflect.TypeOf;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import javax.inject.Inject;
import java.io.File;
import java.util.function.Supplier;

import static org.gradle.api.reflect.TypeOf.typeOf;
import static org.gradle.util.internal.ConfigureUtil.configure;

/**
 * The "Xenomorph" Gradle plugin addressing Jakarta JAXB.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-11
 */
public class XenomorphPlugin implements Plugin<Project> {
    /**
     * Plugin context.
     */
    private PluginContext pluginContext;

//https://stackoverflow.com/questions/42040560/how-to-add-new-language-source-directories-in-gradle-plugin
//https://www.programcreek.com/java-api-examples/?api=org.gradle.api.tasks.SourceSetContainer

    @Getter
    @Setter
//    @NoArgsConstructor
    public static class MyExtension {
        private String foo;

        static int count=0;

        public MyExtension() {
            count++;
            System.out.println("Count: "+count);
        }
    }

    @Override
    public void apply(Project project) {
        Supplier<PluginContext> pluginContextSupplier=()->pluginContext;

        pluginContext=PluginContext.builder().pluginConfigurations(PluginConfigurations.register(project)).build();
        pluginContext=pluginContext.toBuilder().pluginExtensions(PluginExtensions.register(project)).build();
        pluginContext=pluginContext.toBuilder().pluginTasks(PluginTasks.register(project,pluginContextSupplier)).build();

        {
            JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
            javaPluginExtension.getSourceSets().all(
                    sourceSet -> {
                String name="qqq"+sourceSet.getName();
                ExtensionContainer ex=sourceSet.getExtensions();
                XJCExtension myExtension=ex.create(XJCExtension.EXTENSION_NAME, XJCExtension.class, project, sourceSet);

System.out.println("Created extension custom"+sourceSet.getName());

System.out.println("Extension qqqmain: "+ex.findByName(name));
System.out.println("Extension qqqmain: "+ex.findByName("xjc"));
            });
            XJCExtension x=(XJCExtension)javaPluginExtension.getSourceSets().getByName("main").getExtensions().getByName("xjc");
            System.out.println("Lookup: "+x+", "+x.getRuns().size());
        }
    }
}

// TODO: take inspiration from https://github.com/gradle/gradle/blob/master/subprojects/plugins/src/main/java/org/gradle/api/tasks/GroovySourceSet.java
interface UmpleSourceSet {
    SourceDirectorySet getUmple();
    UmpleSourceSet umple(Closure<UmpleSourceSet> configureClosure);
//    UmpleSourceSet umple(Action<? super SourceDirectorySet> configureAction);
    UmpleSourceSet umple(Action<? super SourceDirectorySet> configureAction);
    SourceDirectorySet getAllUmple();
}

// TODO: take inspiration from https://github.com/gradle/gradle/blob/master/subprojects/plugins/src/main/java/org/gradle/api/internal/tasks/DefaultGroovySourceSet.java
class DefaultUmpleSourceSet implements UmpleSourceSet, HasPublicType {

    private final DefaultUmpleSourceSet groovy;
    private final SourceDirectorySet allUmple;

    @Inject
    public DefaultUmpleSourceSet(String name, String displayName, ObjectFactory objectFactory) {
        this.groovy = createGroovySourceDirectorySet(name, displayName, objectFactory);
        allUmple = objectFactory.sourceDirectorySet("all" + name, displayName + " Groovy source");
//      allUmple.source(groovy);
        allUmple.getFilter().include("**/*.groovy");
    }

    private static DefaultUmpleSourceSet createGroovySourceDirectorySet(String name, String displayName, ObjectFactory objectFactory) {
        DefaultUmpleSourceSet groovySourceDirectorySet = objectFactory.newInstance(DefaultUmpleSourceSet.class, objectFactory.sourceDirectorySet(name, displayName + " Groovy source"));
//        groovySourceDirectorySet.getFilter().include("**/*.java", "**/*.groovy");
        return groovySourceDirectorySet;
    }

    @Override
    public SourceDirectorySet getUmple() {
        return allUmple;
    }

    @Override
    public UmpleSourceSet umple(Closure configureClosure) {
        configure(configureClosure, getUmple());
        return this;
    }

    @Override
    public UmpleSourceSet umple(Action<? super SourceDirectorySet> configureAction) {
        configureAction.execute(getUmple());
        return this;
    }

    @Override
    public SourceDirectorySet getAllUmple() {
        return null;
    }

    @Override
    public TypeOf<?> getPublicType() {
        return typeOf(UmpleSourceSet.class);
    }
}

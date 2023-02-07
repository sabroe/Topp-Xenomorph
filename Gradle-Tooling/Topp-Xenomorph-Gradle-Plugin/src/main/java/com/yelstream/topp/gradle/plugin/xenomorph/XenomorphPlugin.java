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

    @Override
    public void apply(Project project) {
        Supplier<PluginContext> pluginContextSupplier=()->pluginContext;

        pluginContext=PluginContext.builder().pluginConfigurations(PluginConfigurations.register(project)).build();
        pluginContext=pluginContext.toBuilder().pluginExtensions(PluginExtensions.register(project)).build();
        pluginContext=pluginContext.toBuilder().pluginTasks(PluginTasks.register(project,pluginContextSupplier)).build();

        {
            JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
javaPluginExtension.getSourceSets().forEach(x->System.out.println("XXX(3): "+x.getName()));
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

        //https://docs.gradle.org/current/userguide/java_plugin.html

        /*
         * Tasks:
         *     xjc
         *     xjcTest
         *     xjcIntegrationTest
         *     ...
         * Configurations:
         *     xjc
         *     xjcTest
         *     xjcIntegrationTest
         *     ...
         * Extensions (in addition to SourceSet-extensions):
         *     xjc
         *     xjcTest
         *     xjcIntegrationTest
         *     ...
         */
    }
}

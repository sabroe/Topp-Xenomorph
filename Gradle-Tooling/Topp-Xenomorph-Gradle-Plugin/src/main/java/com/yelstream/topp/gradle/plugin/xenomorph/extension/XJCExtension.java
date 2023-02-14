package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import com.yelstream.topp.gradle.api.ResourceFactory;
import com.yelstream.topp.gradle.plugin.xenomorph.util.SchemaReference;
import com.yelstream.topp.gradle.plugin.xenomorph.util.SchemaReferenceFactory;
import groovy.lang.Closure;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.SourceSet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Gradle extension linked to usages of task {@link com.yelstream.topp.gradle.plugin.xenomorph.task.XJCTask}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Data
public class XJCExtension {
    /**
     * Extension name as used in Gradle build files.
     */
    public static final String EXTENSION_NAME="xjc";

    public XJCExtension(Project project) {
        this.project=project;
        resource=ResourceFactory.of(project);
        schema=new SchemaReferenceFactory(project);
    }

    public XJCExtension(Project project,
                        SourceSet sourceSet) {
        this.project=project;
        resource=ResourceFactory.of(project,sourceSet);
        schema=new SchemaReferenceFactory(project);
    }

    public static XJCExtension get(Project project) {
        ExtensionContainer extension=project.getExtensions();
        return (XJCExtension)extension.getByName(EXTENSION_NAME);
    }

//    @Getter(AccessLevel.PROTECTED)
    private final Project project;

    @Getter
    private final ResourceFactory resource;

    private boolean enable=true;

    private boolean dry;

    @Data
    @NoArgsConstructor
    public class Run {

        private String name;

        private boolean enable=true;

        private boolean dry;

        private List<SchemaReference> sourceSchema=new ArrayList<>();

        //Arguments: Complete argument line!
        //Arguments: Prepend prefix!
        //Arguments: Append suffix!

        @Data
        @NoArgsConstructor
        public static class Option {

            private boolean nv;

            private boolean extension;

            private List<File> bindingFile=new ArrayList<>();

            private File outputDirectory;

            private String targetPackage;

            private String moduleName;

            private String httpProxy;

            private File httpProxyFile;

            private String classpath;  //TODO: Consider this!

            private File catalogFile;

            private boolean readOnly;

            private boolean npa;

            private boolean noHeader;

            private String target;

            private String encoding;  //TODO: Consider default!

            private boolean enableIntrospection;

            private boolean disableXmlSecurity;

            private boolean contentForWildcard;

            private boolean xmlschema;

            private boolean dtd;

            private boolean wsdl;

            private boolean verbose;

            private boolean quiet;

            private boolean help;

            private boolean version;

            private boolean fullversion;
        }

        @Data
        @NoArgsConstructor
        public static class Extension {
            private boolean xInjectCode;
            private boolean xLocator;
            private boolean xSyncMethods;
            private boolean markGenerated;
            private boolean noDate;
            private String xAnn;
            private File episode;
            private boolean xPropertyAccessors;
        }

        private Option option;
        private Extension extension;

        @Getter
        @Setter
        public Extension extension2;

        public void setOption(Option option) {
            if (this.option!=null) {
                throw new IllegalStateException("Failure to set options; the options structure has already been set!");
            }
            this.option=option;
        }

        public void setExtension(Extension extension) {
            if (this.extension !=null) {
                throw new IllegalStateException("Failure to set extensions; the extension structure has already been set!");
            }
            this.extension = extension;
        }

        public Option option(Closure<Option> closure) {
            @SuppressWarnings("java:S1117")
            Option option=(Option)project.configure(new Option(),closure);
            setOption(option);
            return option;
        }

        public Extension extension(Closure<Extension> closure) {
            @SuppressWarnings("java:S1117")
            Extension extension=(Extension)project.configure(new Extension(),closure);
            setExtension(extension);
            return extension;
        }
    }

    private final List<Run> runs=new ArrayList<>();

    public Run run(Closure<Run> closure) {
        Run run=(Run)project.configure(new Run(), closure);
        runs.add(run);
        return run;
    }

    private final SchemaReferenceFactory schema;
}

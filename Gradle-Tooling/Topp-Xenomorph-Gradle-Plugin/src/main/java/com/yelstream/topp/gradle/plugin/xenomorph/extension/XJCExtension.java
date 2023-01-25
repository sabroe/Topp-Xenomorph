package com.yelstream.topp.gradle.plugin.xenomorph.extension;

import com.yelstream.topp.gradle.plugin.xenomorph.util.SchemaReference;
import com.yelstream.topp.gradle.plugin.xenomorph.util.SchemaReferenceFactory;
import groovy.lang.Closure;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
@Data
@RequiredArgsConstructor
public class XJCExtension {
    /**
     * Extension name as used in Gradle build files.
     */
    public static final String EXTENSION_NAME="xjc";

    public static XJCExtension get(Project project) {
        ExtensionContainer extension=project.getExtensions();
        return (XJCExtension)extension.getByName(EXTENSION_NAME);
    }

    private final Project project;

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
        public static class Options {

            private boolean nv;
            private boolean extension;
            private List<File> bindingFile=new ArrayList<>();

            private File outputDirectory;
            private String targetPackage;

            private File catalogFile;

            private Boolean enableIntrospection;

            private boolean verbose;
            private boolean quiet;
            private boolean help;
            private boolean version;
            private boolean fullversion;
        }

        @Data
        @NoArgsConstructor
        public static class Extensions {
            private Boolean xInjectCode;
            private Boolean xLocator;
            private Boolean xSyncMethods;
            private Boolean markGenerated;
            private Boolean noDate;
            private String xAnn;
            private File episode;
            private Boolean xPropertyAccessors;
        }

        private Options options;
        private Extensions extensions;

        public void setOptions(Options options) {
            if (this.options!=null) {
                throw new IllegalStateException("Failure to set options; the options structure has already been set!");
            }
            this.options=options;
        }

        public void setExtensions(Extensions extensions) {
            if (this.extensions!=null) {
                throw new IllegalStateException("Failure to set extensions; the extension structure has already been set!");
            }
            this.extensions=extensions;
        }

        public Options options(Closure<Options> closure) {
            @SuppressWarnings("java:S1117")
            Options options=(Options)project.configure(new Options(),closure);
            setOptions(options);
            return options;
        }

        public Extensions extensions(Closure<Extensions> closure) {
            @SuppressWarnings("java:S1117")
            Extensions extensions=(Extensions)project.configure(new Extensions(),closure);
            setExtensions(extensions);
            return extensions;
        }
    }

    private final List<Run> runs=new ArrayList<>();

    public Run run(Closure<Run> closure) {
        Run run=(Run)project.configure(new Run(), closure);
        runs.add(run);
        return run;
    }

    private SchemaReferenceFactory schema=new SchemaReferenceFactory();
}

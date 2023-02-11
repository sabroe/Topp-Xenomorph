package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.command.Status;
import com.yelstream.topp.gradle.plugin.xenomorph.context.PluginContext;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.XJCExtension;
import com.yelstream.topp.gradle.plugin.xenomorph.tool.XJCUtility;
import com.yelstream.topp.gradle.plugin.xenomorph.util.SchemaReference;
import com.yelstream.topp.gradle.plugin.xenomorph.util.XenomorphPluginUtility;
import com.yelstream.topp.grind.gradle.api.Projects;
import com.yelstream.topp.grind.gradle.api.Tasks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;
import org.gradle.api.tasks.options.Option;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 *
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-01-14
 */
public abstract class XJCTask extends DefaultTask {
    /**
     * Task name.
     */
    public static final String TASK_NAME="xjc";

    /**
     * Task description.
     */
    public static final String DESCRIPTION="Generates JAXB objects from XML Schema (W3C XML Schema/XML DTD/XML inside WSDL).";

    /**
     * Name of task group.
     */
    public static final String GROUP_NAME="build";

    public static final String TASK_OUTPUT_DIRECTORY_NAME="xjc";


    public static final String RESOURCES_DIRECTORY_NAME="src/main/resources";

    public static final String PLUGIN_OUTPUT_DIRECTORY_NAME=XenomorphPluginUtility.PLUGIN_NAME;

    /**
     *
     */
    public static final String JAKARTA_JAXB_DOMAIN_OUTPUT_DIRECTORY_NAME="jakarta-jaxb";

    /**
     *
     */
    public static final String JACKSON_DOMAIN_OUTPUT_DIRECTORY_NAME="jackson-pojo";

    /**
     *
     */
    public static final String REPORT_DOMAIN_OUTPUT_DIRECTORY_NAME="report";


    public static final String OUTPUT_DIRECTORY_NAME=
        String.format("%s/%s/%s/%s",
                      PLUGIN_OUTPUT_DIRECTORY_NAME,
                      JAKARTA_JAXB_DOMAIN_OUTPUT_DIRECTORY_NAME,
                      TASK_OUTPUT_DIRECTORY_NAME,
                      "java/src");

    private final Supplier<PluginContext> pluginContextSupplier;

    @Inject
    protected abstract FileOperations getFileOperations();

    @Inject
    protected abstract ObjectFactory getObjectFactory();

    @Inject
    protected abstract ProjectLayout getProjectLayout();

    @Getter(onMethod_={@InputFiles})
    private final ConfigurableFileCollection inputSchemaFiles;

    @Getter(onMethod_={@Classpath})
    private final NamedDomainObjectProvider<Configuration> xjcDependencies;

    @Getter(onMethod_={@OutputDirectory})
    private final DirectoryProperty outputDirectory;

    @Optional
    @Getter(onMethod_={@Input})
    @Setter(onMethod_={@Option(option="help",description="Shows some initial help for how use this task.")})
    private Boolean optionHelp;

    @Optional
    @Getter(onMethod_={@Input})
    @Setter(onMethod_={@Option(option="native-help",description="Shows the help as output from native JAXB XJC.")})
    private Boolean optionNativeHelp;

    @Optional
    @Getter(onMethod_={@Input})
    @Setter(onMethod_={@Option(option="native-version",description="Shows the version as output from native JAXB XJC.")})
    private Boolean optionNativeVersion;

    @Optional
    @Getter(onMethod_={@Input})
    @Setter(onMethod_={@Option(option="native-full-version",description="Shows the full version as output from native JAXB XJC.")})
    private Boolean optionNativeFullVersion;

    @Optional
    @Getter(onMethod_={@Input})
    @Setter(onMethod_={@Option(option="dry",description="Configures task invocation to process arguments without actually running.")})
    private Boolean optionDry;

    @Inject
    @SuppressWarnings("java:S5993")
    public XJCTask(Supplier<PluginContext> pluginContextSupplier,
                   String configurationName,
                   String sourceSetName) {
        this.pluginContextSupplier=pluginContextSupplier;

        setDescription(DESCRIPTION);
        setGroup(GROUP_NAME);

        Project project=getProject();
        File buildDir=project.getBuildDir();

        xjcDependencies=pluginContextSupplier.get().getPluginConfigurations().getXjcConfigurations().getConfigurationProvider(sourceSetName);

        inputSchemaFiles=getObjectFactory().fileCollection().from(RESOURCES_DIRECTORY_NAME);
        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(OUTPUT_DIRECTORY_NAME));

//        resourceLoaderContainer=new ResourceLoaderContainer(getProject(),pluginContextSupplier.get().getPluginConfigurations());
//        schemaReferenceConfiguration=resourceLoaderContainer.getSchemaReferenceConfiguration();
//        compileClassPathMainJavaFileCollection=resourceLoaderContainer.getCompileClassPathMainJavaFileCollection();


        project.getPluginManager().withPlugin("java", appliedPlugin->{
            JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
            SourceSetContainer javaSourceSets=javaPluginExtension.getSourceSets();
            SourceSet mainJavaSourceSet=javaSourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
javaSourceSets.forEach(x->System.out.println("XXX(2): "+x.getName()));

            String dir=new File(buildDir,OUTPUT_DIRECTORY_NAME).toString();
            mainJavaSourceSet.getJava().srcDir(dir);
        });


{
    JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
    SourceSetContainer javaSourceSets=javaPluginExtension.getSourceSets();

    javaSourceSets.forEach(s->System.out.println("Sources-set name: "+s.getName()));

    SourceSet mainJavaSourceSet=javaSourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
    SourceDirectorySet resources=mainJavaSourceSet.getResources();
    System.out.println("XXX: resources="+resources);
    System.out.println("XXX: dirs="+resources.getSrcDirs());

    SourceSet mainJavaSourceSet2=javaSourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME);
    SourceDirectorySet resources2=mainJavaSourceSet2.getResources();
    System.out.println("XXX2: resources="+resources2);
    System.out.println("XXX2: dirs="+resources2.getSrcDirs());

    ExtensionContainer ex=mainJavaSourceSet.getExtensions();


    XJCExtension x=(XJCExtension)javaPluginExtension.getSourceSets().getByName("main").getExtensions().getByName("xjc");
    System.out.println("Lookup(2): "+x+", "+x.getRuns().size());

}
    }


    public static final Set<String> validTaskPropertyKeys=Set.of("native-help","native-version","native-fullversion","dry","help");

    @Getter
    @AllArgsConstructor
    @Builder(builderClassName="Builder",toBuilder=true)
    private static class RunContext {

        private final Map<String, Object> projectProperties;

        private final Map<String, Object> taskProperties;

        private final XJCExtension extension;

        private final Integer runIndex;

        private final XJCExtension.Run run;
    }

    @SuppressWarnings("java:S106")
    @TaskAction
    public void run() throws TaskExecutionException {
        Project project=getProject();
        Logger logger=getLogger();
        Tasks.logHello(this);

        Map<String,Object> projectProperties=Projects.getProjectProperties(project);
        Map<String,Object> taskProperties=Tasks.getTaskProperties(this);

        try {
            Tasks.verifyTaskPropertyKeys(this, validTaskPropertyKeys);

            if (optionNativeVersion==Boolean.TRUE || taskProperties.containsKey("native-version")) {
                List<String> args=List.of("-version");
                XJCUtility.executeCommandToConsole(args,Status.EMPTY_STATUS_VERIFIER);
            }

            if (optionNativeFullVersion==Boolean.TRUE || taskProperties.containsKey("native-fullversion")) {
                List<String> args=List.of("-fullversion");
                XJCUtility.executeCommandToConsole(args,Status.EMPTY_STATUS_VERIFIER);
            }

            if (optionNativeHelp==Boolean.TRUE || taskProperties.containsKey("native-help")) {
                List<String> args=List.of("-help");
                XJCUtility.executeCommandToConsole(args,Status.EMPTY_STATUS_VERIFIER);
            }

            if (optionHelp==Boolean.TRUE || taskProperties.containsKey("help")) {
                XJCUtility.showHelp(System.out);
            }

            XJCExtension extension=XJCExtension.get(project);
            if (!extension.isEnable()) {
                logger.info("Task internals is disabled!");
            } else {
                RunContext.Builder builder= RunContext.builder();
                builder.projectProperties(projectProperties);
                builder.taskProperties(taskProperties);
                builder.extension(extension);
                RunContext runContext=builder.build();

                List<XJCExtension.Run> runs=extension.getRuns();

                if (runs == null || runs.isEmpty()) {
                    logger.warn("Nothing to do! To remove this warning, do disable this run.");
                } else {

                    int runIndex = 0;
                    for (XJCExtension.Run run : runs) {
                        runContext=runContext.toBuilder().runIndex(runIndex).run(run).build();
                        executeRun(runContext);
                        runIndex++;
                    }
                }
            }
        } catch (Exception ex) {
            throw new TaskExecutionException(this,ex);
        }
    }
/*
    -Djavax.xml.accessExternalSchema=all
    -Djavax.xml.accessExternalSchema=file,http
    //https://docs.oracle.com/javase/tutorial/jaxp/properties/properties.html
 */

/*
    DGML
    GraphML
    XGMML
    PhyloXML
    NeXML
*/

    private void executeRun(RunContext runContext) throws Exception {
        Project project=getProject();
        Logger logger=getLogger();

        Integer index=runContext.getRunIndex();
        String name=runContext.getRun().getName();
        XJCExtension extension=runContext.getExtension();
        XJCExtension.Run run=runContext.getRun();

        if (logger.isDebugEnabled()) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Executing run; run #%d, name %s, run is %s!",index,name,run));
            }
        }

        if (!run.isEnable()) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Executing run is disabled; run #%d, name %s, run is %s!",index,name,run));
            }
        } else {
            XJCExtension.Run.Options options=run.getOptions();
            XJCExtension.Run.Extensions extensions=run.getExtensions();

            File buildDir=project.getBuildDir();
            Path buildDirectoryPath=project.getBuildDir().toPath();
            Path projectDir=project.getProjectDir().toPath();

            List<String> args=new ArrayList<>();

            if (options.isNv()) {
                args.add("-nv");
            }

            if (options.isExtension()) {
                args.add("-extension");
            }

            {  //TODO: DO SOMETHING ELSE IF DIR IS DECLARED!
                Path generatedFilesDirectory=buildDirectoryPath.resolve(Paths.get(OUTPUT_DIRECTORY_NAME));
                args.add("-d");
                args.add(generatedFilesDirectory.toAbsolutePath().toString());
            }

            {
                String targetPackage=options.getTargetPackage();
                if (targetPackage!=null) {
                    args.add("-p");
                    args.add(targetPackage);
                }
            }

            {
                String moduleName=options.getModuleName();
                if (moduleName!=null) {
                    args.add("-m");
                    args.add(moduleName);
                }
            }

            {
                String httpProxy=options.getHttpProxy();
                if (httpProxy!=null) {
                    args.add("-httpproxy");
                    args.add(httpProxy);
                }
            }

            {
                File httpProxyFile=options.getHttpProxyFile();
                if (httpProxyFile!=null) {
                    args.add("-httpproxyfile");
                    args.add(httpProxyFile.getAbsolutePath());  //TODO: Ensure file is resolved properly!
                }
            }

            {
                File httpProxyFile=options.getHttpProxyFile();
                if (httpProxyFile!=null) {
                    args.add("-httpproxyfile");
                    args.add(httpProxyFile.getAbsolutePath());  //TODO: Ensure file is resolved properly!
                }
            }

            {
                String classpath=options.getClasspath();
                if (classpath!=null) {
                    args.add("-classpath");
                    args.add(classpath);
                }
            }

            {
                File catalogFile=options.getCatalogFile();
                if (catalogFile!=null) {
                    args.add("-catalog");
/*
            if (!Files.exists(catalogFile)) {
                throw new IllegalStateException();
            }
*/
                    args.add(catalogFile.getAbsolutePath());  //TODO: Ensure file is resolved properly!
                }

                logger.info("Catalog files may be tracked by using '-Dxml.catalog.verbosity=999'.");
            }

            if (options.isReadOnly()) {
                args.add("-readOnly");
            }

            if (options.isNpa()) {
                args.add("-npa");
            }

            if (options.isNoHeader()) {
                args.add("-no-header");
            }

            {
                String target=options.getTarget();
                if (target!=null) {
                    args.add("-target");
                    args.add(target);
                }
            }

            {
                String encoding=options.getEncoding();  //TODO: Consider default!
                if (encoding!=null) {
                    args.add("-encoding");
                    args.add(encoding);
                }
            }

            if (options.isEnableIntrospection()!=false) {
                args.add("-enableIntrospection");
            }

            if (options.isDisableXmlSecurity()) {
                args.add("-disableXmlSecurity");
            }

            if (options.isContentForWildcard()) {
                args.add("-contentForWildcard");
            }

            if (options.isXmlschema()) {
                args.add("-xmlschema");
            }

            if (options.isDtd()) {
                args.add("-dtd");
            }

            if (options.isWsdl()) {
                args.add("-wsdl");
            }

            if (options.isVerbose()) {
                args.add("-verbose");
            }

            if (options.isQuiet()) {
                args.add("-quiet");
            }

            if (options.isHelp()) {
                args.add("-help");
            }

            if (options.isVersion()) {
                args.add("-version");
            }

            if (options.isFullversion()) {
                args.add("-fullversion");
            }

            if (run.getSourceSchema()!=null) {
                List<SchemaReference> schemas=run.getSourceSchema();
                for (SchemaReference schema: schemas) {
                    String schemaText=schema.resolve(project);
                    args.add(schemaText);
    /*
                    Path schemaFile=projectDir.resolve(Paths.get(schema));
                    args.add(schemaFile.toAbsolutePath().toString());
    */
                }
            }

            if (options.getBindingFile()!=null) {
                List<File> bindingFiles=options.getBindingFile();
                for (File binding: bindingFiles) {
                    Path bindingPath=binding.toPath();
                    Path bindingFilesDirectory=projectDir.resolve(bindingPath);
                    args.add("-b");
                    args.add(bindingFilesDirectory.toAbsolutePath().toString());
                }
            }

            if (extensions.isXInjectCode()) {
                args.add("-Xinject-code");
            }

            if (extensions.isXLocator()) {
                args.add("-Xlocator");
            }

            if (extensions.isXSyncMethods()) {
                args.add("-Xsync-methods");
            }

            if (extensions.isMarkGenerated()!=false) {
                args.add("-mark-generated");
            }

            if (extensions.isNoDate()) {
                args.add("-noDate");
            }

            {
                String annotation=extensions.getXAnn();
                if (annotation!=null) {
                    args.add("-Xann");
                    args.add(annotation);
                }
            }

            {
                File episode=extensions.getEpisode();
                if (episode!=null) {
                    args.add("-episode");
                    args.add(episode.getAbsolutePath());  //TODO: Ensure file is resolved properly!
                }
            }

            if (extensions.isXPropertyAccessors()) {
                args.add("-Xpropertyaccessors");
            }

            if (logger.isInfoEnabled()) {
                 logger.info(String.format("Run has arguments; the arguments to 'xjc' are '%s'!",args));
            }

            if (optionDry==Boolean.TRUE || runContext.getTaskProperties().containsKey("dry")) {
                logger.warn("Run is dry; task property indicates a dry run!");
            } else {
                if (extension.isDry()) {
                    logger.warn("Run is dry; task is marked a dry run!");
                } else {
                    if (run.isDry()) {
                        logger.warn("Run is dry; run is marked a dry run!");
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug(String.format("Executing run; run #%d, name %s, run is %s, arguments to 'xjc' are '%s'!",index,name,run,args));
                        }
                        XJCUtility.executeCommandToLogger(args,null,getLogger());
                    }
                }
            }
        }
    }
}

package com.yelstream.topp.gradle.plugin.xenomorph.task;

import com.yelstream.topp.command.Status;
import com.yelstream.topp.gradle.api.SourceSets;
import com.yelstream.topp.gradle.plugin.xenomorph.configuration.PluginConfigurations;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.PluginExtensions;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.XJCExtension;
import com.yelstream.topp.gradle.plugin.xenomorph.extension.XJCExtensions;
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
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceSet;
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

    private final PluginConfigurations pluginConfigurations;

    private final PluginExtensions pluginExtensions;

    private final SourceSet sourceSet;

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
    public XJCTask(PluginConfigurations pluginConfigurations,
                   PluginExtensions pluginExtensions,
                   SourceSet sourceSet) {
        this.pluginConfigurations=pluginConfigurations;
        this.pluginExtensions=pluginExtensions;
        this.sourceSet=sourceSet;

        setDescription(DESCRIPTION);
        setGroup(GROUP_NAME);

        Project project=getProject();
        File buildDir=project.getBuildDir();

        xjcDependencies=pluginConfigurations.getXjcConfigurations().getConfigurationProvider(sourceSet.getName());

//        inputSchemaFiles=getObjectFactory().fileCollection().from(RESOURCES_DIRECTORY_NAME);

{
SourceDirectorySet x=sourceSet.getResources();
        inputSchemaFiles=getObjectFactory().fileCollection().from(x.getSrcDirs());
System.out.println("inputSchemaFiles: "+inputSchemaFiles.getFiles());
}
//        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(OUTPUT_DIRECTORY_NAME));
//        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(String.format("Xenomorph/%s/java/src",this.getName())));
        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(String.format("Xenomorph/%s",this.getName())));

//File _dir=new File(buildDir,String.format("Xenomorph/%s/java/src",this.getName()));
//_dir.mkdirs();


//        resourceLoaderContainer=new ResourceLoaderContainer(getProject(),pluginContextSupplier.get().getPluginConfigurations());
//        schemaReferenceConfiguration=resourceLoaderContainer.getSchemaReferenceConfiguration();
//        compileClassPathMainJavaFileCollection=resourceLoaderContainer.getCompileClassPathMainJavaFileCollection();


/*
        {
//            String dir=new File(buildDir,OUTPUT_DIRECTORY_NAME).toString();
            File dir=new File(buildDir,String.format("Xenomorph/%s/java/src",this.getName()));
            SourceSets.addJavaSourceDirectory(sourceSet,dir);
        }
*/
    }


    public static final Set<String> validTaskPropertyKeys=Set.of("native-help","native-version","native-fullversion","dry","help");

    @Getter
    @AllArgsConstructor
    @Builder(builderClassName="Builder",toBuilder=true)
    private static class RunContext {

        private final Map<String, Object> projectProperties;

        private final Map<String, Object> taskProperties;

        private final XJCExtension extension;

        private final String setupName;

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

            XJCExtensions.ExtensionGroup eg=pluginExtensions.getXjcExtensions().getExtensions().get(sourceSet.getName());
            XJCExtension extension=eg.getGlobalExtension();
XJCExtension extension2=eg.getSourceSetExtension();
//System.out.println("Global extension: "+extension);
//System.out.println("SourceSet extension: "+extension2);

            RunContext.Builder builder=RunContext.builder();
            builder.projectProperties(projectProperties);
            builder.taskProperties(taskProperties);

            if (extension!=null) {
                builder.setupName="Global";
                builder.extension(extension);
                executeRuns(builder.build());
            }

            if (extension2!=null) {
                builder.setupName="SourceSet";
                builder.extension(extension2);
                executeRuns(builder.build());
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

    private void executeRuns(RunContext runContext) throws Exception {
        Logger logger=getLogger();

        XJCExtension extension=runContext.getExtension();
        if (!extension.isEnable()) {
            logger.info("Task internals is disabled!");
        } else {
            List<XJCExtension.Run> runs=extension.getRuns();

            if (runs==null || runs.isEmpty()) {
                logger.debug("Nothing to do!");
            } else {

                int runIndex = 0;
                for (XJCExtension.Run run : runs) {
                    runContext=runContext.toBuilder().runIndex(runIndex).run(run).build();
                    executeRun(runContext);
                    runIndex++;
                }
            }
        }
    }

    private void executeRun(RunContext runContext) throws Exception {
        Project project=getProject();
        Logger logger=getLogger();

        String setupName=runContext.getSetupName();
        Integer runIndex=runContext.getRunIndex();
        String name=runContext.getRun().getName();
        XJCExtension extension=runContext.getExtension();
        XJCExtension.Run run=runContext.getRun();

        if (logger.isDebugEnabled()) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Executing run; run #%d, name %s, run is %s!",runIndex,name,run));
            }
        }

        if (!run.isEnable()) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Executing run is disabled; run #%d, name %s, run is %s!",runIndex,name,run));
            }
        } else {
            XJCExtension.Run.Option option=run.getOption();  //TODO: Rename to .... runOption
            XJCExtension.Run.Extension extensions=run.getExtension();  //TODO: Rename to .... runExtension

            File buildDir=project.getBuildDir();
            Path buildDirectoryPath=project.getBuildDir().toPath();
            Path projectDir=project.getProjectDir().toPath();

            List<String> args=new ArrayList<>();

            if (option.isNv()) {
                args.add("-nv");
            }

            if (option.isExtension()) {
                args.add("-extension");
            }

            {  //TODO: DO SOMETHING ELSE IF DIR IS DECLARED!
//                Path generatedFilesDirectory=buildDirectoryPath.resolve(Paths.get(OUTPUT_DIRECTORY_NAME));
                Path generatedFilesDirectory=buildDirectoryPath.resolve(Paths.get(String.format("Xenomorph/%s/Setup-%s/Run/Run-%d/java/src",this.getName(),setupName,runIndex)));
                args.add("-d");
                args.add(generatedFilesDirectory.toAbsolutePath().toString());

{
    File dir=new File(buildDir,String.format("Xenomorph/%s/Setup-%s/Run/Run-%d/java/src",this.getName(),setupName,runIndex));
dir.mkdirs();
    SourceSets.addJavaSourceDirectory(sourceSet,dir);
//System.out.println(sourceSet.getAllJava().getSrcDirs());
}
            }

            {
                String targetPackage=option.getTargetPackage();
                if (targetPackage!=null) {
                    args.add("-p");
                    args.add(targetPackage);
                }
            }

            {
                String moduleName=option.getModuleName();
                if (moduleName!=null) {
                    args.add("-m");
                    args.add(moduleName);
                }
            }

            {
                String httpProxy=option.getHttpProxy();
                if (httpProxy!=null) {
                    args.add("-httpproxy");
                    args.add(httpProxy);
                }
            }

            {
                File httpProxyFile=option.getHttpProxyFile();
                if (httpProxyFile!=null) {
                    args.add("-httpproxyfile");
                    args.add(httpProxyFile.getAbsolutePath());  //TODO: Ensure file is resolved properly!
                }
            }

            {
                File httpProxyFile=option.getHttpProxyFile();
                if (httpProxyFile!=null) {
                    args.add("-httpproxyfile");
                    args.add(httpProxyFile.getAbsolutePath());  //TODO: Ensure file is resolved properly!
                }
            }

            {
                String classpath=option.getClasspath();
                if (classpath!=null) {
                    args.add("-classpath");
                    args.add(classpath);
                }
            }

            {
                File catalogFile=option.getCatalogFile();
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

            if (option.isReadOnly()) {
                args.add("-readOnly");
            }

            if (option.isNpa()) {
                args.add("-npa");
            }

            if (option.isNoHeader()) {
                args.add("-no-header");
            }

            {
                String target=option.getTarget();
                if (target!=null) {
                    args.add("-target");
                    args.add(target);
                }
            }

            {
                String encoding=option.getEncoding();  //TODO: Consider default!
                if (encoding!=null) {
                    args.add("-encoding");
                    args.add(encoding);
                }
            }

            if (option.isEnableIntrospection()!=false) {
                args.add("-enableIntrospection");
            }

            if (option.isDisableXmlSecurity()) {
                args.add("-disableXmlSecurity");
            }

            if (option.isContentForWildcard()) {
                args.add("-contentForWildcard");
            }

            if (option.isXmlschema()) {
                args.add("-xmlschema");
            }

            if (option.isDtd()) {
                args.add("-dtd");
            }

            if (option.isWsdl()) {
                args.add("-wsdl");
            }

            if (option.isVerbose()) {
                args.add("-verbose");
            }

            if (option.isQuiet()) {
                args.add("-quiet");
            }

            if (option.isHelp()) {
                args.add("-help");
            }

            if (option.isVersion()) {
                args.add("-version");
            }

            if (option.isFullversion()) {
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

            if (option.getBindingFile()!=null) {
                List<File> bindingFiles=option.getBindingFile();
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
                            logger.debug(String.format("Executing run; run #%d, name %s, run is %s, arguments to 'xjc' are '%s'!",runIndex,name,run,args));
                        }
                        XJCUtility.executeCommandToLogger(args,null,getLogger());
                    }
                }
            }
        }
    }
}

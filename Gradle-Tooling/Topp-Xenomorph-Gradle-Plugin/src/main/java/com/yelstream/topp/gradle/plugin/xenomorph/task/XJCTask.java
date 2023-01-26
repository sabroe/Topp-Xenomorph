package com.yelstream.topp.gradle.plugin.xenomorph.task;

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
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.internal.file.FileOperations;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.Classpath;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.TaskExecutionException;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntConsumer;
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
     *
     */
    public static final String TASK_NAME="xjc";

    /**
     *
     */
    public static final String DESCRIPTION="Generates JAXB objects from XML Schema (W3C XML Schema/XML DTD/XML inside WSDL).";

    /**
     *
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

    private Supplier<PluginContext> pluginContextSupplier;

    @Inject
    protected abstract FileOperations getFileOperations();

    @Inject
    protected abstract ObjectFactory getObjectFactory();

    @Inject
    protected abstract ProjectLayout getProjectLayout();

    private final ConfigurableFileCollection inputSchemaFiles;
    @InputFiles
    public ConfigurableFileCollection getInputSchemaFiles() { return inputSchemaFiles; }

    private final DirectoryProperty outputDirectory;
    @OutputDirectory
    public DirectoryProperty getOutputDirectory() { return outputDirectory; }

/*
    @Classpath
    public NamedDomainObjectProvider<Configuration> getSchemaReferenceConfiguration() { return schemaReferenceConfiguration; }
    private final NamedDomainObjectProvider<Configuration> schemaReferenceConfiguration;
*/

    private final FileCollection compileClassPathMainJavaFileCollection;
    @Classpath
    public FileCollection getCompileClassPathMainJavaFileCollection() { return compileClassPathMainJavaFileCollection; }

    private ResourceLoaderContainer resourceLoaderContainer;

    @Inject
    public XJCTask(Supplier<PluginContext> pluginContextSupplier) {
        this.pluginContextSupplier=pluginContextSupplier;

        setDescription(DESCRIPTION);
        setGroup(GROUP_NAME);

        Project project=getProject();
        File buildDir=project.getBuildDir();

        inputSchemaFiles=getObjectFactory().fileCollection().from(RESOURCES_DIRECTORY_NAME);
        outputDirectory=getObjectFactory().directoryProperty().convention(getProjectLayout().getBuildDirectory().dir(OUTPUT_DIRECTORY_NAME));

        resourceLoaderContainer=new ResourceLoaderContainer(getProject(),pluginContextSupplier.get().getPluginConfigurations());
//        schemaReferenceConfiguration=resourceLoaderContainer.getSchemaReferenceConfiguration();
        compileClassPathMainJavaFileCollection=resourceLoaderContainer.getCompileClassPathMainJavaFileCollection();


        project.getPluginManager().withPlugin("java", appliedPlugin->{
            JavaPluginExtension javaPluginExtension=project.getExtensions().getByType(JavaPluginExtension.class);
            SourceSetContainer javaSourceSets=javaPluginExtension.getSourceSets();
            SourceSet mainJavaSourceSet=javaSourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);

            String dir=new File(buildDir,OUTPUT_DIRECTORY_NAME).toString();
            mainJavaSourceSet.getJava().srcDir(dir);
        });
    }

    public static final Set<String> validTaskPropertyKeys=Set.of("help","version","fullversion","dry");

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

    @TaskAction
    public void run() throws TaskExecutionException {
        Project project=getProject();
        Logger logger=getLogger();
        Tasks.logHello(this);

        Map<String, Object> projectProperties = Projects.getProjectProperties(project);
        Map<String, Object> taskProperties = Tasks.getTaskProperties(this);

        try {
            Tasks.verifyTaskPropertyKeys(this, validTaskPropertyKeys);

            if (taskProperties.containsKey("help")) {
                List<String> args=List.of("-help");
                run(args);
            }

            if (taskProperties.containsKey("version")) {
                List<String> args=List.of("-version");
                run(args);
            }

            if (taskProperties.containsKey("fullversion")) {
                List<String> args=List.of("-fullversion");
                run(args);
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

            if (options.isEnableIntrospection()) {
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

            if (extensions.isMarkGenerated()) {
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

            if (runContext.getTaskProperties().containsKey("dry")) {
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
                        run(args);
                    }
                }
            }
        }
    }

    /*
JAXBContext jaxbContext = JAXBContext.newInstance(packageName);
Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
Graphml graphml= (Graphml) jaxbUnmarshaller.unmarshal(xmlFile);
     */

    public void run(List<String> args) throws Exception {
        run(args,null);
    }

    public void run(List<String> args,
                    IntConsumer statusConsumer) throws Exception {
        Logger logger=getLogger();
        String outText=process(out->{
            String errText=process(err->{
                XJCUtility.CommandContext commandContext=
                    XJCUtility.CommandContext.builder().out(out).err(err).statusConsumer(statusConsumer).build();
                XJCUtility.run(commandContext,args);
            });
            if (errText!=null && !errText.isEmpty()) {
                if (logger.isErrorEnabled()) {
                    logger.error(String.format("Failure to run'; error is:%n%s", errText));
                }
            }
        });
        if (outText!=null && !outText.isEmpty()) {
            if (logger.isInfoEnabled()) {
                logger.info(String.format("Informational is:%n%s", outText));
            }
        }
    }

    @FunctionalInterface
    interface ConsumerWithException<V,E extends Exception> {
        void accept(V v) throws E;
    }

    public static <E extends Exception> String process(ConsumerWithException<PrintStream,E> consumer) throws E, IOException {
        try (ByteArrayOutputStream outStream=new ByteArrayOutputStream()) {
            try (PrintStream out=new PrintStream(outStream)) {
                consumer.accept(out);
                out.flush();
                return outStream.toString(Charset.defaultCharset());
            }
        }
    }
}

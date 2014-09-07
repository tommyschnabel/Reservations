/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.plugins.tomcat.tasks

import org.gradle.api.GradleException
import org.gradle.api.InvalidUserDataException
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.tomcat.embedded.TomcatServerFactory
import org.gradle.api.plugins.tomcat.internal.ShutdownMonitor
import org.gradle.api.plugins.tomcat.internal.ssl.SSLKeyStore
import org.gradle.api.plugins.tomcat.internal.ssl.SSLKeyStoreImpl
import org.gradle.api.plugins.tomcat.internal.ssl.StoreType
import org.gradle.api.plugins.tomcat.internal.utils.ThreadContextClassLoader
import org.gradle.api.plugins.tomcat.internal.utils.TomcatThreadContextClassLoader
import org.gradle.api.tasks.*

import java.util.logging.Level

import static org.gradle.api.plugins.tomcat.internal.LoggingHandler.withJdkFileLogger

/**
 * Base class for all tasks which deploy a web application to an embedded Tomcat web container.
 *
 * @author Benjamin Muschko
 */
abstract class AbstractTomcatRun extends Tomcat {
    static final CONFIG_FILE = 'META-INF/context.xml'

    /**
     * Forces context scanning if you don't use a context file. Defaults to true.
     */
    @Input
    Boolean reloadable = Boolean.TRUE

    /**
     * The URL context path under which the web application will be registered. Defaults to WAR name.
     */
    @Input
    @Optional
    String contextPath

    /**
     * The TCP port which Tomcat should listen for HTTP requests. Defaults to 8080.
     */
    @Input
    Integer httpPort = 8080

    /**
     * The TCP port which Tomcat should listen for HTTPS requests. Defaults to 8443.
     */
    Integer httpsPort = 8443

    /**
     * The TCP port which Tomcat should listen for admin requests. Defaults to 8081.
     */
    @Input
    Integer stopPort = 8081

    /**
     * The key to pass to Tomcat when requesting it to stop. Defaults to "stopKey".
     */
    @Input
    String stopKey = 'stopKey'

    /**
     * The HTTP protocol handler class name to be used. Defaults to "org.apache.coyote.http11.Http11Protocol".
     */
    @Input
    String httpProtocol = 'org.apache.coyote.http11.Http11Protocol'

    /**
     * The HTTPS protocol handler class name to be used. Defaults to "org.apache.coyote.http11.Http11Protocol".
     */
    @Input
    String httpsProtocol = 'org.apache.coyote.http11.Http11Protocol'

    /**
     * The default web.xml. If it doesn't get defined an instance of org.apache.catalina.servlets.DefaultServlet and
     * org.apache.jasper.servlet.JspServlet will be set up.
     */
    @InputFile
    @Optional
    File webDefaultXml

    /**
     * Defines additional runtime JARs that are not provided by the web application.
     */
    @InputFiles
    Iterable<File> additionalRuntimeJars = []

    /**
     * Specifies the character encoding used to decode the URI bytes by the HTTP Connector. Defaults to "UTF-8".
     */
    @Input
    String URIEncoding = 'UTF-8'

    /**
     * Specifies whether the Tomcat server should run in the background. When true, this task completes as soon as the
     * server has started. When false, this task blocks until the Tomcat server is stopped. Defaults to false.
     */
    @Input
    Boolean daemon = Boolean.FALSE

    /**
     * The build script's classpath.
     */
    @InputFiles
    FileCollection buildscriptClasspath

    /**
     * Classpath for Tomcat libraries.
     */
    @InputFiles
    FileCollection tomcatClasspath

    /**
     * The path to the Tomcat context XML file.
     */
    @InputFile
    @Optional
    File configFile

    /**
     * Determines whether the HTTPS connector should be created. Defaults to false.
     */
    @Input
    Boolean enableSSL = Boolean.FALSE

    /**
     * Doesn't override existing SSL key. Defaults to false.
     */
    @Input
    Boolean preserveSSLKey = Boolean.FALSE

    /**
     * The keystore file to use for SSL, if enabled (by default, a keystore will be generated).
     */
    @InputFile
    @Optional
    File keystoreFile

    /**
     * The keystore password to use for SSL, if enabled.
     */
    @Input
    @Optional
    String keystorePass

    /**
     * The truststore file to use for SSL, if enabled.
     */
    @InputFile
    @Optional
    File truststoreFile

    /**
     * The truststore password to use for SSL, if enabled.
     */
    @Input
    @Optional
    String truststorePass

    /**
     * The clientAuth setting to use, values may be: "true", "false" or "want".  Defaults to "false".
     */
    @Input
    @Optional
    String clientAuth = 'false'

    /**
     * The file to write Tomcat log messages to. If the file already exists new messages will be appended.
     */
    @OutputFile
    @Optional
    File outputFile

    /**
     * The TCP port which Tomcat should listen for AJP requests. Defaults to 8009.
     */
    @Input
    Integer ajpPort = 8009

    /**
     * The AJP protocol handler class name to be used. Defaults to "org.apache.coyote.ajp.AjpProtocol".
     */
    @Input
    String ajpProtocol = 'org.apache.coyote.ajp.AjpProtocol'

    def server
    def realm
    URL resolvedConfigFile

    private final ThreadContextClassLoader threadContextClassLoader = new TomcatThreadContextClassLoader()
    private final SSLKeyStore sslKeyStore = new SSLKeyStoreImpl()

    abstract void setWebApplicationContext()

    @TaskAction
    protected void start() {
        logger.info "Configuring Tomcat for ${getProject()}"

        threadContextClassLoader.withClasspath(getBuildscriptClasspath().files, getTomcatClasspath().files) {
            validateConfigurationAndStartTomcat()
        }
    }

    void validateConfigurationAndStartTomcat() {
        validateConfiguration()

        withJdkFileLogger(getOutputFile(), true, Level.INFO) {
            startTomcat()
        }
    }

    /**
     * Validates configuration and throws an exception if
     */
    protected void validateConfiguration() {
        // Check existence of default web.xml if provided
        if(getWebDefaultXml()) {
            logger.info "Default web.xml = ${getWebDefaultXml().canonicalPath}"
        }

        // Check the location of context.xml if it was provided.
        if(getConfigFile()) {
            setResolvedConfigFile(getConfigFile().toURI().toURL())
            logger.info "context.xml = ${getResolvedConfigFile().toString()}"
        }

        // Check HTTP(S) protocol handler class names
        if(getHttpProtocol()) {
            logger.info "HTTP protocol handler classname = ${getHttpProtocol()}"
        }

        if(getHttpsProtocol()) {
            logger.info "HTTPS protocol handler classname = ${getHttpsProtocol()}"
        }

        if(getOutputFile()) {
            logger.info "Output file = ${getOutputFile().canonicalPath}"
        }

        if(getEnableSSL()) {
            validateStore(getKeystoreFile(), getKeystorePass(), StoreType.KEY)
            validateStore(getTruststoreFile(), getTruststorePass(), StoreType.TRUST)
            def validClientAuthPhrases = ["true", "false", "want"]

            if(getClientAuth() && (!validClientAuthPhrases.contains(getClientAuth()))) {
                throw new InvalidUserDataException("If specified, clientAuth must be one of: ${validClientAuthPhrases}")
            }
        }
    }

    /**
     * Validates that the necessary parameters have been provided for the specified key/trust store.
     *
     * @param storeFile The file representing the store
     * @param keyStorePassword The password to the store
     * @param storeType identifies whether the store is a KeyStore or TrustStore
     */
    private void validateStore(File storeFile, String keyStorePassword, StoreType storeType) {
        if(!storeFile ^ !keyStorePassword) {
            throw new InvalidUserDataException("If you want to provide a $storeType.description then password and file may not be null or blank")
        }
        else if(storeFile && keyStorePassword) {
            if(!storeFile.exists()) {
                throw new InvalidUserDataException("$storeType.description file does not exist at location $storeFile.canonicalPath")
            }
            else {
                logger.info "$storeType.description file = ${storeFile}"
            }
        }
    }

    protected void addWebappResource(File resource) {
        getServer().addWebappResource(resource)
    }

    /**
     * Configures web application
     */
    protected void configureWebApplication() {
        setWebApplicationContext()
        getServer().createLoader(Thread.currentThread().contextClassLoader)

        getAdditionalRuntimeJars().each { File additionalRuntimeJar ->
            addWebappResource(additionalRuntimeJar)
        }

        getServer().context.reloadable = getReloadable()
        getServer().configureDefaultWebXml(getWebDefaultXml())

        if(getResolvedConfigFile()) {
            getServer().configFile = getResolvedConfigFile()
        }
    }

    void startTomcat() {
        try {
            logger.debug 'Starting Tomcat Server ...'

            setServer(createServer())
            getServer().home = getTemporaryDir().absolutePath
            getServer().realm = realm

            configureWebApplication()

            getServer().configureContainer()
            getServer().configureHttpConnector(getHttpPort(), getURIEncoding(), getHttpProtocol())
            getServer().configureAjpConnector(getAjpPort(), getURIEncoding(), getAjpProtocol())

            if(getEnableSSL()) {
                if(!getKeystoreFile()) {
                    final File keystore = project.file("$project.buildDir/tmp/ssl/keystore")
                    final String keyPassword = 'gradleTomcat'
                    sslKeyStore.createSSLCertificate(keystore, keyPassword, getPreserveSSLKey())
                    keystoreFile = keystore
                    keystorePass = keyPassword
                }

                if(getTruststoreFile()) {
                    getServer().configureHttpsConnector(getHttpsPort(), getURIEncoding(), getHttpsProtocol(), getKeystoreFile(),
                                                        getKeystorePass(), getTruststoreFile(), getTruststorePass(), getClientAuth())
                }
                else {
                    getServer().configureHttpsConnector(getHttpsPort(), getURIEncoding(), getHttpsProtocol(), getKeystoreFile(), getKeystorePass())
                }
            }

            // Start server
            getServer().start()

            registerShutdownHook()

            logger.quiet 'Started Tomcat Server'
            logger.quiet "The Server is running at http://localhost:${getHttpPort()}${getServer().context.path}"

            Thread shutdownMonitor = new ShutdownMonitor(getStopPort(), getStopKey(), getServer(), daemon)
            shutdownMonitor.start()

            if(!getDaemon()) {
                shutdownMonitor.join()
            }
        }
        catch(Exception e) {
            throw new GradleException('An error occurred starting the Tomcat server.', e)
        }
        finally {
            if(!getDaemon()) {
                logger.info 'Tomcat server exiting.'
            }
        }
    }

    protected String getFullContextPath() {
        if(getContextPath() == '/' || getContextPath() == '') {
            return ''
        }

        getContextPath().startsWith('/') ? getContextPath() : '/' + getContextPath()
    }

    def createServer() {
        TomcatServerFactory.instance.tomcatServer
    }

    /**
     * Registers shutdown hook that stops Tomcat's context lifecycle when triggered.
     */
    void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            void run() {
                if(!getServer().stopped) {
                    getServer().stop()
                }
            }
        })
    }
}
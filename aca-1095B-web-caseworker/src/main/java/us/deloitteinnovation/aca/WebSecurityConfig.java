package us.deloitteinnovation.aca;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.saml2.metadata.provider.FilesystemMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.saml.*;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.context.SAMLContextProviderLB;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.*;
import org.springframework.security.saml.parser.ParserPoolHolder;
import org.springframework.security.saml.processor.*;
import org.springframework.security.saml.storage.EmptyStorageFactory;
import org.springframework.security.saml.trust.httpclient.TLSProtocolConfigurer;
import org.springframework.security.saml.trust.httpclient.TLSProtocolSocketFactory;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import us.deloitteinnovation.aca.security.CustomCsrfProtectionMatcher;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.unicorn.saml.web.UnicornAuthSuccessHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by sdalavi on 11/9/2015.
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@Profile({"dev", "qa", "preprod", "prod", "dryrun"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private ProfileProperties profileProperties;

    private final static String ROTTWEILER_FILE = "ROTTWEILER_FILE";
    private final static String SP_METADATA_FILE = "SPMETADATA_FILE";
    private final static String ENTITY_ID = "ENTITY_ID";
    private final static String ENTITY_ALIAS = "ENTITY_ALIAS";
    private final static String SUCCESS_URL = "SUCCESS_URL";
    private final static String IDP_DISCOVERY_URL = "IDP_DISCOVERY_URL";
    private final static String IDP_DISCOVERY_RESPONSE_URL = "IDP_DISCOVERY_RESPONSE_URL";
    private final static String HTTP_SCHEME = "HTTP_SCHEME";
    private final static String SERVER_NAME = "SERVER_NAME";
    private final static String SERVER_PORT = "SERVER_PORT";
    private final static String SERVER_APPNAME = "SERVER_APPNAME";
    private final static String METAIOP = "metaiop";
    private final static String PKIX = "PKIX";
    private final static String IDP_PRIVATE = "idpprivate";
    private final static String defaultenterpriseprivatekey = "defaultenterpriseprivatekey";

    // Initialization of the velocity engine
    @Bean
    public VelocityEngine velocityEngine() {
        return VelocityFactory.getEngine();
    }

    // XML parser pool needed for OpenSAML parsing
    @Bean(initMethod = "initialize")
    public StaticBasicParserPool parserPool() {
        return new StaticBasicParserPool();
    }

    @Bean(name = "parserPoolHolder")
    public ParserPoolHolder parserPoolHolder() {
        return new ParserPoolHolder();
    }

    // Bindings, encoders and decoders used for creating and parsing messages
    @Bean
    public MultiThreadedHttpConnectionManager multiThreadedHttpConnectionManager() {
        return new MultiThreadedHttpConnectionManager();
    }

    @Bean
    public HttpClient httpClient() {
        return new HttpClient(multiThreadedHttpConnectionManager());
    }

    // SAML Authentication Provider responsible for validating of received SAML
    // messages
    @Bean
    public SAMLAuthenticationProvider samlAuthenticationProvider() {
        SAMLAuthenticationProvider samlAuthenticationProvider = new SAMLAuthenticationProvider();
        samlAuthenticationProvider.setUserDetails(new us.deloitteinnovation.aca.security.UnicornSAMLCredential());
        samlAuthenticationProvider.setForcePrincipalAsString(false);
        return samlAuthenticationProvider;
    }

    // Provider of default SAML Context
    @Bean
    public SAMLContextProviderImpl contextProvider() {

        SAMLContextProviderLB samlContextProvider = new SAMLContextProviderLB();
        samlContextProvider.setScheme(profileProperties.getProperty(HTTP_SCHEME));
        samlContextProvider.setServerName(profileProperties.getProperty(SERVER_NAME));
        samlContextProvider.setServerPort(Integer.valueOf(profileProperties.getProperty(SERVER_PORT)));
        samlContextProvider.setIncludeServerPortInRequestURL(false);
        samlContextProvider.setContextPath(profileProperties.getProperty(SERVER_APPNAME));
        samlContextProvider.setStorageFactory(new EmptyStorageFactory());
        return samlContextProvider;
    }

    // Initialization of OpenSAML library
    @Bean
    public static SAMLBootstrap sAMLBootstrap() {
        return new SAMLBootstrap();
    }

    // Logger for SAML messages and events
    @Bean
    public SAMLDefaultLogger samlLogger() {
        return new SAMLDefaultLogger();
    }

    // SAML 2.0 WebSSO Assertion Consumer
    @Bean
    public WebSSOProfileConsumer webSSOprofileConsumer() {
        return new WebSSOProfileConsumerImpl();
    }

    // SAML 2.0 Holder-of-Key WebSSO Assertion Consumer
    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    // SAML 2.0 Web SSO profile
    @Bean
    public WebSSOProfile webSSOprofile() {
        return new WebSSOProfileImpl();
    }

    // SAML 2.0 Holder-of-Key Web SSO profile
    @Bean
    public WebSSOProfileConsumerHoKImpl hokWebSSOProfile() {
        return new WebSSOProfileConsumerHoKImpl();
    }

    // SAML 2.0 ECP profile
    @Bean
    public WebSSOProfileECPImpl ecpprofile() {
        return new WebSSOProfileECPImpl();
    }

    @Bean
    public SingleLogoutProfile logoutprofile() {
        return new SingleLogoutProfileImpl();
    }

    // Central storage of cryptographic keys
    @Bean
    public KeyManager keyManager() {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource storeFile = loader.getResource("classpath:security/samlKeystore.jks");
        String storePass = "nalle123";
        Map<String, String> passwords = new HashMap<>();
        passwords.put("apollo", "nalle123");
        passwords.put("idpprivate", "nalle123");
        passwords.put("defaultenterpriseprivatekey", "nalle123");
        String defaultKey = "apollo";
        return new JKSKeyManager(storeFile, storePass, passwords, defaultKey);
    }

    // Setup TLS Socket Factory
    @Bean
    public TLSProtocolConfigurer tlsProtocolConfigurer() {
        return new TLSProtocolConfigurer();
    }

    @Bean
    public ProtocolSocketFactory socketFactory() {
        return new TLSProtocolSocketFactory(keyManager(), null, "default");
    }

    @Bean
    public Protocol socketFactoryProtocol() {
        return new Protocol("https", socketFactory(), 443);
    }

    @Bean
    public MethodInvokingFactoryBean socketFactoryInitialization() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(Protocol.class);
        methodInvokingFactoryBean.setTargetMethod("registerProtocol");
        Object[] args = {"https", socketFactoryProtocol()};
        methodInvokingFactoryBean.setArguments(args);
        return methodInvokingFactoryBean;
    }

    @Bean
    public WebSSOProfileOptions defaultWebSSOProfileOptions() {
        WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();
        webSSOProfileOptions.setIncludeScoping(false);
        return webSSOProfileOptions;
    }

    // Entry point to initialize authentication, default values taken from
    // properties file
    @Bean
    public SAMLEntryPoint samlEntryPoint() {
        SAMLEntryPoint samlEntryPoint = new SAMLEntryPoint();
        //    samlEntryPoint.setDefaultProfileOptions(defaultWebSSOProfileOptions());
        return samlEntryPoint;
    }

    // Setup advanced info about metadata
    @Bean
    public ExtendedMetadata extendedMetadata() {
        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        extendedMetadata.setIdpDiscoveryEnabled(true);
        extendedMetadata.setSignMetadata(true);
        return extendedMetadata;
    }

    // IDP Discovery Service
    @Bean
    public SAMLDiscovery samlIDPDiscovery() {
        SAMLDiscovery idpDiscovery = new SAMLDiscovery();
        idpDiscovery.setIdpSelectionPath("/WEB-INF/security/idpSelection.jsp");
        return idpDiscovery;
    }

    // IDP Metadata configuration - paths to metadata of IDPs in circle of trust
    // is here
    // Do no forget to call iniitalize method on providers
    @Bean
    @Qualifier("metadata")
    public CachingMetadataManager metadata() throws MetadataProviderException, IOException {

        List<MetadataProvider> providers = new ArrayList<>();
        providers.add(getRottweilerIdEntityMetadata());
        providers.add(getSPMetadata());

        CachingMetadataManager cachingMetadataManager = new CachingMetadataManager(providers);
        cachingMetadataManager.setHostedSPName(profileProperties.getProperty(ENTITY_ID));

        return cachingMetadataManager;
    }

    private ExtendedMetadataDelegate getRottweilerIdEntityMetadata() throws MetadataProviderException, IOException {
        try{
            ClassPathResource resource = new ClassPathResource(profileProperties.getProperty(ROTTWEILER_FILE)) ;
            InputStream in = resource.getInputStream();
            File tempFile = File.createTempFile("SP_METADATA", ".xml");
            tempFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(in, out);
            return new ExtendedMetadataDelegate(getFileSystemFilesystemMetadataProvider(tempFile));
        }
        catch (Exception e)
        {
            logger.error("error in opening exception is ROTTWEILER_FILE xml file and error is :",e);
        }
        return null;
       /*     DefaultResourceLoader loader = new DefaultResourceLoader();
            Resource fileSystemMetaProvider = loader.getResource(profileProperties.getProperty(ROTTWEILER_FILE));

            return new ExtendedMetadataDelegate(getFileSystemFilesystemMetadataProvider(fileSystemMetaProvider.getFile()));*/
    }


    private ExtendedMetadataDelegate getSPMetadata() throws MetadataProviderException, IOException {
        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        extendedMetadata.setLocal(true);
        extendedMetadata.setAlias(profileProperties.getProperty(ENTITY_ALIAS));
        extendedMetadata.setSecurityProfile(METAIOP);
        extendedMetadata.setSslSecurityProfile(PKIX);
        extendedMetadata.setSignMetadata(true);
        extendedMetadata.setSigningKey(IDP_PRIVATE);
        extendedMetadata.setEncryptionKey(defaultenterpriseprivatekey);
        extendedMetadata.setRequireArtifactResolveSigned(true);
        extendedMetadata.setRequireLogoutRequestSigned(true);
        extendedMetadata.setRequireLogoutResponseSigned(false);
        extendedMetadata.setIdpDiscoveryEnabled(false);
        extendedMetadata.setIdpDiscoveryURL(profileProperties.getProperty(IDP_DISCOVERY_URL));
        extendedMetadata.setIdpDiscoveryResponseURL(profileProperties.getProperty(IDP_DISCOVERY_RESPONSE_URL));

        try{
            ClassPathResource resource = new ClassPathResource(profileProperties.getProperty(SP_METADATA_FILE)) ;
            InputStream in = resource.getInputStream();
            File tempFile = File.createTempFile("SP_METADATA", ".xml");
            tempFile.deleteOnExit();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(in, out);
            return new ExtendedMetadataDelegate(getFileSystemFilesystemMetadataProvider(tempFile), extendedMetadata);
        }
        catch (Exception e)
        {
            logger.error("error in opening exception is SP_METADATA xml file and error is :",e);
        }

        return null;
//             DefaultResourceLoader loader = new DefaultResourceLoader();
//            Resource fileSystemMetaProvider = loader.getResource(profileProperties.getProperty(SP_METADATA_FILE));
//
//            return new ExtendedMetadataDelegate(getFileSystemFilesystemMetadataProvider(fileSystemMetaProvider.getFile()), extendedMetadata);



    }


    private FilesystemMetadataProvider getFileSystemFilesystemMetadataProvider(File file) throws MetadataProviderException {
        FilesystemMetadataProvider filesystemMetadataProvider = new FilesystemMetadataProvider(file);
        filesystemMetadataProvider.setParserPool(parserPool());
        return filesystemMetadataProvider;
    }

    // Filter automatically generates default SP metadata
    @Bean
    public MetadataGenerator metadataGenerator() {
        MetadataGenerator metadataGenerator = new MetadataGenerator();
        metadataGenerator.setEntityId(profileProperties.getProperty(ENTITY_ID));

        metadataGenerator.setExtendedMetadata(extendedMetadata());
        metadataGenerator.setAssertionConsumerIndex(1);

        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        extendedMetadata.setSignMetadata(false);
        metadataGenerator.setExtendedMetadata(extendedMetadata);

        metadataGenerator.setKeyManager(keyManager());
        return metadataGenerator;
    }

    // The filter is waiting for connections on URL suffixed with filterSuffix
    // and presents SP metadata there
    @Bean
    public MetadataDisplayFilter metadataDisplayFilter() {
        return new MetadataDisplayFilter();
    }

    // Handler deciding where to redirect user after successful login
    @Bean
    public UnicornAuthSuccessHandler successRedirectHandler() {
        UnicornAuthSuccessHandler unicornAuthSuccessHandler = new UnicornAuthSuccessHandler();
        unicornAuthSuccessHandler.setDefaultTargetUrl(profileProperties.getProperty(SUCCESS_URL));
        unicornAuthSuccessHandler.setTargetUrlParameter(profileProperties.getProperty(SUCCESS_URL));
        unicornAuthSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
        return unicornAuthSuccessHandler;
    }

    // Handler deciding where to redirect user after failed login
    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler =
                new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setUseForward(true);
        failureHandler.setDefaultFailureUrl("/error");
        return failureHandler;
    }

    @Bean
    public SAMLWebSSOHoKProcessingFilter samlWebSSOHoKProcessingFilter() throws Exception {
        SAMLWebSSOHoKProcessingFilter samlWebSSOHoKProcessingFilter = new SAMLWebSSOHoKProcessingFilter();
        samlWebSSOHoKProcessingFilter.setAuthenticationSuccessHandler(new UnicornAuthSuccessHandler());
        samlWebSSOHoKProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSSOHoKProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return samlWebSSOHoKProcessingFilter;
    }

    // Processing filter for WebSSO profile messages
    @Bean
    public SAMLProcessingFilter samlWebSSOProcessingFilter() throws Exception {
        SAMLProcessingFilter samlWebSSOProcessingFilter = new SAMLProcessingFilter();
        samlWebSSOProcessingFilter.setAuthenticationManager(authenticationManager());
        samlWebSSOProcessingFilter.setAuthenticationSuccessHandler(successRedirectHandler());
        samlWebSSOProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return samlWebSSOProcessingFilter;
    }

    @Bean
    public MetadataGeneratorFilter metadataGeneratorFilter() {
        return new MetadataGeneratorFilter(metadataGenerator());
    }

    // Handler for successful logout
    @Bean
    public SimpleUrlLogoutSuccessHandler successLogoutHandler() {
        SimpleUrlLogoutSuccessHandler successLogoutHandler = new SimpleUrlLogoutSuccessHandler();
        successLogoutHandler.setDefaultTargetUrl("/");
        return successLogoutHandler;
    }

    // Logout handler terminating local session
    @Bean
    public SecurityContextLogoutHandler logoutHandler() {
        SecurityContextLogoutHandler logoutHandler =
                new SecurityContextLogoutHandler();
        logoutHandler.setInvalidateHttpSession(true);
        logoutHandler.setClearAuthentication(true);
        return logoutHandler;
    }

    // Filter processing incoming logout messages
    // First argument determines URL user will be redirected to after successful
    // global logout
    @Bean
    public SAMLLogoutProcessingFilter samlLogoutProcessingFilter() {
        return new SAMLLogoutProcessingFilter(successLogoutHandler(),
                logoutHandler());
    }

    // Overrides default logout processing filter with the one processing SAML
    // messages
    @Bean
    public SAMLLogoutFilter samlLogoutFilter() {
        return new SAMLLogoutFilter(successLogoutHandler(),
                new LogoutHandler[]{logoutHandler()},
                new LogoutHandler[]{logoutHandler()});
    }

    // Bindings
    private ArtifactResolutionProfile artifactResolutionProfile() {
        final ArtifactResolutionProfileImpl artifactResolutionProfile =
                new ArtifactResolutionProfileImpl(httpClient());
        artifactResolutionProfile.setProcessor(new SAMLProcessorImpl(soapBinding()));
        return artifactResolutionProfile;
    }

    @Bean
    public HTTPArtifactBinding artifactBinding(ParserPool parserPool, VelocityEngine velocityEngine) {
        return new HTTPArtifactBinding(parserPool, velocityEngine, artifactResolutionProfile());
    }

    @Bean
    public HTTPSOAP11Binding soapBinding() {
        return new HTTPSOAP11Binding(parserPool());
    }

    @Bean
    public HTTPPostBinding httpPostBinding() {
        return new HTTPPostBinding(parserPool(), velocityEngine());
    }

    @Bean
    public HTTPRedirectDeflateBinding httpRedirectDeflateBinding() {
        return new HTTPRedirectDeflateBinding(parserPool());
    }

    @Bean
    public HTTPSOAP11Binding httpSOAP11Binding() {
        return new HTTPSOAP11Binding(parserPool());
    }

    @Bean
    public HTTPPAOS11Binding httpPAOS11Binding() {
        return new HTTPPAOS11Binding(parserPool());
    }

    // Processor
    @Bean
    public SAMLProcessorImpl processor() {
        Collection<SAMLBinding> bindings = new ArrayList<>();
        bindings.add(httpRedirectDeflateBinding());
        bindings.add(httpPostBinding());
        bindings.add(artifactBinding(parserPool(), velocityEngine()));
        bindings.add(httpSOAP11Binding());
        bindings.add(httpPAOS11Binding());
        return new SAMLProcessorImpl(bindings);
    }

    /**
     * Define the security filter chain in order to support SSO Auth by using SAML 2.0
     *
     * @return Filter chain proxy
     *
     * @throws Exception
     */
    @Bean
    public FilterChainProxy samlFilter() throws Exception {
        List<SecurityFilterChain> chains = new ArrayList<>();
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/login/**"),
                samlEntryPoint()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/logout/**"),
                samlLogoutFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/metadata/**"),
                metadataDisplayFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSO/**"),
                samlWebSSOProcessingFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SSOHoK/**"),
                samlWebSSOHoKProcessingFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/SingleLogout/**"),
                samlLogoutProcessingFilter()));
        chains.add(new DefaultSecurityFilterChain(new AntPathRequestMatcher("/saml/discovery/**"),
                samlIDPDiscovery()));
        return new FilterChainProxy(chains);
    }

    /**
     * Returns the authentication manager currently used by Spring.
     * It represents a bean definition with the aim allow wiring from
     * other classes performing the Inversion of Control (IoC).
     *
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Defines the web based security configuration.
     *
     * @param http
     *     It allows configuring web based security for specific http requests.
     *
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(samlEntryPoint());
        http
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .requireCsrfProtectionMatcher(new CustomCsrfProtectionMatcher());
        http
                .addFilterBefore(metadataGeneratorFilter(), ChannelProcessingFilter.class)
                .addFilterAfter(samlFilter(), BasicAuthenticationFilter.class);

        if (profileProperties.getProperty("security.enabled") != null &&
                Boolean.valueOf(profileProperties.getProperty("security.enabled"))) {
            http
                    .authorizeRequests()
                    .antMatchers("/api/caseworkerportal/**").authenticated()
                    .antMatchers("/api/opsportal/**").authenticated()
                    .antMatchers("/swagger-ui.html").denyAll()
                    .anyRequest().permitAll();

        } else {
            http
                    .authorizeRequests()
                    .anyRequest().permitAll();
        }
        http
                .logout()
                .logoutSuccessUrl("/");
    }

    /**
     * Create custom CSRF Token repository which returns CSRF tokens in cookies
     * @return CSRF token repository bean
     * */
    @Bean
    protected CsrfTokenRepository csrfTokenRepository() {
        CookieCsrfTokenRepository tokenRepository = new CookieCsrfTokenRepository();
        tokenRepository.setCookieHttpOnly(Boolean.FALSE);
        return tokenRepository;
    }

    /**
     * Sets a custom authentication provider.
     *
     * @param auth
     *     SecurityBuilder used to create an AuthenticationManager.
     *
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(samlAuthenticationProvider());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**")
                .antMatchers("*.{js,html,css,jpg}");

    }
}
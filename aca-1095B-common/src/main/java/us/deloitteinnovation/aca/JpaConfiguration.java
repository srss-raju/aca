package us.deloitteinnovation.aca;

import java.beans.PropertyVetoException;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import us.deloitteinnovation.aca.batch.dataservice.SourceSystemConfigDataService;
import us.deloitteinnovation.aca.model.SourceSystemConfigMapper;
import us.deloitteinnovation.profile.ProfileProperties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import static us.deloitteinnovation.aca.constants.CommonDataConstants.*;

/**
 * ACA Common JPA Configuration
 *
 * @author ltornquist
 * @since 6/8/2015
 */
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "us.deloitteinnovation.aca.entity")
@EnableJpaRepositories(basePackages = "us.deloitteinnovation.aca")
@Profile({"local", "dev", "qa", "preprod", "prod", "dryrun"})
public class JpaConfiguration {

    //private static final Logger logger = LoggerFactory.getLogger(JpaConfiguration.class) ;

    private static final Base64.Decoder decoder = Base64.getDecoder();

    @Autowired
    protected ProfileProperties profileProperties;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            Properties properties = new Properties();
            properties.setProperty(SEND_STRING_PARAMETERS_AS_UNICODE, Boolean.FALSE.toString().toLowerCase());
            dataSource.setProperties(properties);

            dataSource.setDriverClass(profileProperties.getDriverClassName());
            dataSource.setJdbcUrl(profileProperties.getDbUrl());
            dataSource.setUser(profileProperties.getDbUser());
            dataSource.setPassword(password());
            dataSource.setInitialPoolSize(10);
            dataSource.setMinPoolSize(10);
            dataSource.setMaxPoolSize(30);
            dataSource.setAcquireIncrement(5);
            dataSource.setMaxIdleTime(240);
            dataSource.setIdleConnectionTestPeriod(60);
            dataSource.setNumHelperThreads(10);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("DataSource class threw PropertyVeto", e) ;
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource jpaDataSource) throws UnknownHostException {
        return new JdbcTemplate(jpaDataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource jpaDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(jpaDataSource);
        entityManagerFactoryBean.setPackagesToScan("us.deloitteinnovation.aca.entity");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(additionalProperties());
        return entityManagerFactoryBean;
    }

    Properties additionalProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put(Environment.DIALECT, profileProperties.getHibernateDialect());
        jpaProperties.put(Environment.HBM2DDL_AUTO, profileProperties.getHbm2ddlAuto());
        jpaProperties.put(Environment.SHOW_SQL, profileProperties.getShowSql());
        jpaProperties.put(Environment.FORMAT_SQL, profileProperties.getFormatSql());
        return jpaProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public String password() {
        final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        final String decodedKey = new String(decoder.decode(DEK));
        encryptor.setPassword(decodedKey);
        encryptor.setAlgorithm(PBE_WITH_MD5_AND_TRIPLE_DES);
        final String encryptedPassword = String.valueOf(profileProperties.getDbPassword());
        final String plainTextPassword = encryptor.decrypt(encryptedPassword);
        //logger.error("Database encrypted password '{}'.  After decryption '{}'.", encryptedPassword, plainTextPassword) ;

        return plainTextPassword;
    }

    @Bean
    public SourceSystemConfigDataService sourceSystemConfigDataService(JdbcTemplate jdbcTemplate) {
        SourceSystemConfigDataService service = new SourceSystemConfigDataService() ;
        service.setJdbcTemplate(jdbcTemplate);
        return service;
    }

    @Bean
    public SourceSystemConfigMapper sourceSystemConfigMapper() {
        return new SourceSystemConfigMapper() ;
    }

}

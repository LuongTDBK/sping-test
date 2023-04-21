package vn.neo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableAutoConfiguration
public class NEODBConfiguration {
   /* *//**
     * config for DataSource DB primary
     *//*
    @Bean("dsConfig")
    @ConditionalOnProperty(
            prefix = "spring",
            name = {"datasource-jdbc.jdbc-url"}
    )
    @ConfigurationProperties("spring.datasource")
    public HikariConfig primaryConfig() {
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource primaryDataSource(HikariConfig dsConfig, StringEncryptor encryptor) throws InterruptedException {
        String decryptPassword = encryptor.decrypt(dsConfig.getPassword());
        dsConfig.setPassword(decryptPassword);
        HikariDataSource ds = new HikariDataSource(dsConfig);
        return ds;
    }

    *//**
     * config DataSource for DB_LOG
     *//*
    @Bean("dsLogConfig")
    @ConditionalOnProperty(
            prefix = "spring",
            name = {"datasource-log.jdbc-url"}
    )
    @ConfigurationProperties("spring.datasource-log")
    public HikariConfig secondConfig() {
        return new HikariConfig();
    }

    @Bean(
            name = {"ds-log"}
    )
    public HikariDataSource secondDataSource(final HikariConfig dsLogConfig, final StringEncryptor encryptor) throws InterruptedException {
        System.out.println(dsLogConfig.getPassword());
        String decryptPassword = encryptor.decrypt(dsLogConfig.getPassword());
        dsLogConfig.setPassword(decryptPassword);
        HikariDataSource ds = new HikariDataSource(dsLogConfig);
        return ds;
    }

    *//**
     *
     *//*
    @Bean(name = "encryptorBean")
    @Primary
    public StringEncryptor getPasswordEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("private_key"); // encryptor's private key
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }*/

}

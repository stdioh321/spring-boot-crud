package com.stdioh321.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Optional;

/*

@Configuration
@EnableJpaRepositories(
        basePackages = "com.stdioh321.crud.repository",
        entityManagerFactoryRef = "dbTestEntityManager",
        transactionManagerRef = "dbTestTranscationManager"
)
@PropertySource("classpath:persistence-multiple-db.properties")
@EnableJpaAuditing(auditorAwareRef = "dbTestAuditorProvider")

public class DbTestConfig {
    @Autowired
    private Environment env;


    @Bean("dbTestAuditorProvider")
    public AuditorAware<String> dbTestAuditorProvider() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.empty();
            }
        };
    }

    @Bean("dbTestDataSource")
    public DataSource dbTestDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db.test.driver"));
        dataSource.setUrl(env.getProperty("db.test.url"));
        dataSource.setUsername(env.getProperty("db.test.user"));
        dataSource.setPassword(env.getProperty("db.test.pass"));
        return dataSource;
    }

    @Bean("dbTestEntityManager")
    public LocalContainerEntityManagerFactoryBean dbTestEntityManager() {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dbTestDataSource());
        em.setPackagesToScan(
                new String[]{"com.stdioh321.crud.model"});

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("db.test.hbm2ddl"));
        properties.put("hibernate.dialect", env.getProperty("db.test.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean("dbTestTranscationManager")
    public PlatformTransactionManager dbTestTranscationManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                dbTestEntityManager().getObject());
        return transactionManager;
    }
}

*/


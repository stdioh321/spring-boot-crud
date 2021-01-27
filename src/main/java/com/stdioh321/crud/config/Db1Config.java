package com.stdioh321.crud.config;

import com.stdioh321.crud.repository.CityRepository;
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

@Configuration
@EnableJpaRepositories(basePackages = "com.stdioh321.crud.repository", entityManagerFactoryRef = "db1EntityManager", transactionManagerRef = "db1TranscationManager")
@PropertySource("classpath:persistence-multiple-db.properties")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")

public class Db1Config {
    @Autowired
    private Environment env;


    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAware<String>() {
            @Override
            public Optional<String> getCurrentAuditor() {
                return Optional.empty();
            }
        };
    }

    @Bean
    public DataSource db1DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("db1.driver"));
        dataSource.setUrl(env.getProperty("db1.url"));
        dataSource.setUsername(env.getProperty("db1.user"));
        dataSource.setPassword(env.getProperty("db1.pass"));
        return dataSource   ;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean db1EntityManager() {

        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(db1DataSource());
        em.setPackagesToScan(
                new String[]{"com.stdioh321.crud.model"});

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("db1.hbm2ddl"));
        properties.put("hibernate.dialect", env.getProperty("db1.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager db1TranscationManager(){
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                db1EntityManager().getObject());
        return transactionManager;
    }

}

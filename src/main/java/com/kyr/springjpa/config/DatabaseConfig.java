package com.kyr.springjpa.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "springjpaEntityManager",
        transactionManagerRef = "springjpaTransactionManager",
        basePackages = "com.kyr.springjpa.repository"
)
public class DatabaseConfig {

    @Setter(onMethod_ = @Autowired)
    private Environment environment;

    @Bean
    public DataSource springjpaDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean springjpaEntityManager(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(springjpaDataSource())
                .properties(hibernateProperties())
                .packages("com.kyr.springjpa.model.entity")
                .persistenceUnit("springjpaEntityManager")
                .build();
    }

    @Bean
    public PlatformTransactionManager springjpaTransactionManager() {
        return springjpaTransactionManager();
    }

    @Bean
    public PlatformTransactionManager springjpaTransactionManager(@Qualifier(value = "springjpaEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map hibernateProperties() {
        Resource resource = new ClassPathResource("hibernate.properties");
        Map result = new HashMap();

        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);

            result = properties.entrySet().stream().collect(Collectors.toMap(
                    keyElement -> keyElement.getKey().toString(),
                    valueElement -> valueElement.getValue())
            );
        } catch (IOException e) {
            log.error("set resource data -> {}" , resource);
            log.error("set datasource - " +
                            "driver-class-name -> {} " +
                            ", url -> {} " +
                            ", username -> {} " +
                            ", password -> {}"
                    , environment.getProperty("spring.datasource.driver-class-name")
                    , environment.getProperty("spring.datasource.url")
                    , environment.getProperty("spring.datasource.username")
                    ,environment.getProperty("spring.datasource.password"));
        }
        return result;
    }
}


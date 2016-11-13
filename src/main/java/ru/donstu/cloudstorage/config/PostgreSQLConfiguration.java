package ru.donstu.cloudstorage.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.donstu.cloudstorage.config.util.HibernateUtil;

import javax.sql.DataSource;

import static ru.donstu.cloudstorage.config.constant.Constants.*;

/**
 * Конфигурация DataSource, EntityManager и TransactionManager
 *
 * @author v.solomasov
 */
@Configuration
@EnableJpaRepositories(PACKAGE_ENTITY)
@EnableTransactionManagement
@ComponentScan(PACKAGE_CONFIG)
@PropertySource(RESOURCES_PROPERTY)
public class PostgreSQLConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private HibernateUtil hibernateUtil;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(PACKAGE_ENTITY);
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(hibernateUtil.getJpaProperties());
        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("datasource.postgresql.driver"));
        dataSource.setUrl(environment.getRequiredProperty("datasource.postgresql.url"));
        dataSource.setUsername(environment.getRequiredProperty("datasource.postgresql.username"));
        dataSource.setPassword(environment.getRequiredProperty("datasource.postgresql.password"));
        dataSource.setInitialSize(Integer.valueOf(environment.getRequiredProperty("datasource.dpcp.initialSize")));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}

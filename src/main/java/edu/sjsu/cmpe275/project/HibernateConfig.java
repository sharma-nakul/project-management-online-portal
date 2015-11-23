package edu.sjsu.cmpe275.project;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Nakul Sharma
 * Class to configure Hibernate properties, transaction and SQL drivers.
 * PropertySource annotation is used to fetch properties file from /resources folder
 */
@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
@Component
@PropertySource(value = {"classpath:application.properties"})
public class HibernateConfig {

    /**
     * Object of an Environment is autowired
     */
    @Autowired
    private Environment environment;

    /**
     * Bean method to create LocalSessionFactoryBean
     * @return Object of session factory
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("edu.sjsu.cmpe275.project.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    /**
     * Method to set hibernate properties
     * @return Properties after set
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }


    /**
     * Bean method to create data source bean
     * @return created data source bean object
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    /**
     * Bean method setup transaction manger bean for performing transactions using @Transactional annotation
     * @param sessionFactory Object of session factory to initiate session
     * @return current transactions
     */
    @Bean (name ="tx1")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager tx = new HibernateTransactionManager();
        tx.setSessionFactory(sessionFactory);
        tx.setDataSource(dataSource());
        return tx;
    }

}

package com.epam.esm.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableWebMvc
public class DBConfig {
    @Value("${db.driver}")
    private static String DRIVER_CLASS;
    @Value("${db.url}")
    private static String URL;
    @Value("${db.user}")
    private static String USER_NAME;
    @Value("${db.password}")
    private static String PASSWORD;


    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(DRIVER_CLASS);
            dataSource.setJdbcUrl(URL);
            dataSource.setUser(USER_NAME);
            dataSource.setPassword(PASSWORD);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        return new JdbcTemplate(dataSource());
    }
}

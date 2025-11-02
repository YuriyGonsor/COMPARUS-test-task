package com.example.configurations;

import com.example.atoms.Pair;
import com.example.configurations.ConnectionProperties.DataSourceConfig;
import com.example.configurations.ConnectionProperties.Strategy;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.sql.SQLException;
import java.util.Map;

import static com.example.atoms.Pair.pair;
import static com.example.configurations.ConnectionProperties.Strategy.fromString;
import static java.util.stream.Collectors.toMap;
import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableConfigurationProperties(ConnectionProperties.class)
@Lazy(value = false)
public class DataSourceConfiguration {

    private final Logger logger = getLogger(DataSourceConfiguration.class);

    private final ConnectionProperties connectionProperties;

    public DataSourceConfiguration(ConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    @Bean("jdbcTemplates")
    public Map<String, EnhancedNamedParameterJdbcTemplate> jdbcTemplates() {
        return connectionProperties.dataSources().stream()
                .map(ds -> pair(ds.name(), createEnhancedJdbcTemplate(ds)))
                .filter(pair -> pair.getRight() != null)
                .collect(toMap(Pair::getLeft, Pair::getRight));
    }

    private EnhancedNamedParameterJdbcTemplate createEnhancedJdbcTemplate(DataSourceConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.url());
        hikariConfig.setUsername(config.user());
        hikariConfig.setPassword(config.password());
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("ds-" + config.name());
        hikariConfig.addDataSourceProperty("options", "-c TimeZone=Europe/Kyiv");
        Strategy strategy = fromString(config.strategy());
        switch (strategy) {
            case POSTGRES -> hikariConfig.setDriverClassName("org.postgresql.Driver");
            case MARIADB -> {
                hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
            }
            case MYSQL -> hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
            case ORACLE -> {
                hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
                hikariConfig.setConnectionInitSql("ALTER SESSION SET CURRENT_SCHEMA=APP");
                hikariConfig.setConnectionTestQuery("SELECT 1 FROM dual");
            }
            default -> throw new IllegalArgumentException("Unsupported strategy: " + strategy);
        }
        try {
            HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
            return new EnhancedNamedParameterJdbcTemplate(hikariDataSource, config.table(), config.mapping());
        } catch (HikariPool.PoolInitializationException e) {
            logger.error("Error while creating datasource. Connection pool cannot be initialized.");
            logger.error(e.getMessage());
            return null;
        }
    }

}

package com.example.configurations;

import jakarta.annotation.Nonnull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static java.util.Arrays.stream;

@Validated
@ConfigurationProperties(prefix = "userapp")
public record ConnectionProperties(@Nonnull List<DataSourceConfig> dataSources) {

    public record DataSourceConfig(@Nonnull String name, @Nonnull String strategy, @Nonnull String url,
                                   @Nonnull String table, @Nonnull String user, String password,
                                   @Nonnull Mapping mapping) {
    }

    public enum Strategy {
        POSTGRES("postgres"),
        MARIADB("mariaDB"),
        ORACLE("oracle"),
        MYSQL("mysql");
        private final String strategy;

        Strategy(String strategy) {
            this.strategy = strategy;
        }

        public String getStrategy() {
            return strategy;
        }

        public static Strategy fromString(String strategy) {
            return stream(Strategy.values())
                    .filter(key -> key.strategy.equals(strategy))
                    .findFirst()
                    .orElse(null);
        }

    }

    public record Mapping(@Nonnull String id, @Nonnull String username, @Nonnull String name, @Nonnull String surname) {
    }


}

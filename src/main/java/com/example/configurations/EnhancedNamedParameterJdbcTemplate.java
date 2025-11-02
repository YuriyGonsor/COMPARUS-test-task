package com.example.configurations;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class EnhancedNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {

    private final String table;
    private final ConnectionProperties.Mapping mapping;

    public EnhancedNamedParameterJdbcTemplate(DataSource dataSource, String table, ConnectionProperties.Mapping mapping) {
        super(dataSource);
        this.table = table;
        this.mapping = mapping;
    }

    public String getTable() {
        return table;
    }

    public ConnectionProperties.Mapping getMapping() {
        return mapping;
    }
}

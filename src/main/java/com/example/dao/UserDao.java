    package com.example.dao;

    import com.example.atoms.Pair;
    import com.example.configurations.ConnectionProperties;
    import com.example.configurations.EnhancedNamedParameterJdbcTemplate;
    import com.example.dao.interfaces.IUserDao;
    import com.example.models.User;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.jdbc.core.ResultSetExtractor;
    import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
    import org.springframework.stereotype.Repository;

    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.List;
    import java.util.Map;

    import static com.example.atoms.Pair.pair;

    @Repository
    public class UserDao implements IUserDao {

        private final Map<String, EnhancedNamedParameterJdbcTemplate> jdbcTemplates;

        public UserDao(@Qualifier("jdbcTemplates") Map<String, EnhancedNamedParameterJdbcTemplate> jdbcTemplates) {
            this.jdbcTemplates = jdbcTemplates;
        }

        private ResultSetExtractor<List<User>> getUserResultSetExtractor(ConnectionProperties.Mapping mapping) {
            return rs -> {
                List<User> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(createEntityFromRow(rs, mapping));
                }
                return users;
            };
        }

        private static User createEntityFromRow(ResultSet rs, ConnectionProperties.Mapping mapping) throws SQLException {
            return new User(
                    rs.getString(mapping.id()),
                    rs.getString(mapping.username()),
                    rs.getString(mapping.name()),
                    rs.getString(mapping.surname())
            );
        }

        private Pair<String, MapSqlParameterSource> getSelectQuery(String table, ConnectionProperties.Mapping mapping,
                                                                   String id, String login, String name, String surname) {
            var basicSelectQuery = getSelectQuery(table);
            if (id == null && login == null && name == null && surname == null) {
                return pair(basicSelectQuery, new MapSqlParameterSource());
            }
            basicSelectQuery += " WHERE ";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            if (id != null) {
                basicSelectQuery += mapping.id() + " = :id AND ";
                mapSqlParameterSource.addValue("id", id);
            }
            if (login != null) {
                basicSelectQuery += mapping.username() + " = :login AND ";
                mapSqlParameterSource.addValue("login", login);
            }
            if (name != null) {
                basicSelectQuery += mapping.name() + " = :name AND ";
                mapSqlParameterSource.addValue("name", name);
            }
            if (surname != null) {
                basicSelectQuery += mapping.surname() + " = :surname AND ";
                mapSqlParameterSource.addValue("surname", surname);
            }
            basicSelectQuery += "1 = 1";
            return pair(basicSelectQuery, mapSqlParameterSource);
        }

        private String getSelectQuery(String table) {
            return "SELECT * FROM " + table;
        }

        @Override
        public Collection<User> getUsers(String id, String login, String name, String surname) {
            return jdbcTemplates.values().stream()
                    .map(jdbcTemplate -> {
                        Pair<String, MapSqlParameterSource> queryAndParameters = getSelectQuery(jdbcTemplate.getTable(), jdbcTemplate.getMapping(), id, login, name, surname);
                        return jdbcTemplate.query(queryAndParameters.getLeft(), queryAndParameters.getRight(), getUserResultSetExtractor(jdbcTemplate.getMapping()));
                    })
                    .filter(users -> users != null && !users.isEmpty())
                    .flatMap(Collection::stream)
                    .toList();
        }
    }

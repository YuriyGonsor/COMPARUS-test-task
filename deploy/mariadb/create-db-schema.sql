CREATE TABLE IF NOT EXISTS user_table
(
    ldap_login VARCHAR(255) PRIMARY KEY NOT NULL DEFAULT uuid(),
    name       VARCHAR(255),
    surname    VARCHAR(255)
);

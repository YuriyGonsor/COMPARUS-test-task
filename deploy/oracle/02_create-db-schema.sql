whenever sqlerror exit failure
rollback;

-- Create objects directly in the target schema

CREATE TABLE john_doe.MY_USERS
(
    USER_ID   VARCHAR2(256) PRIMARY KEY,
    USER_NAME VARCHAR2(256),
    NAME      VARCHAR2(256),
    LASTNAME  VARCHAR2(256)
);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS users
(
    user_id    VARCHAR(255) PRIMARY KEY DEFAULT uuid_generate_v4(),
    username   VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);

# User Collector App
## Author
Yuriy Gonsor
## Description
The app is created as a test task for Comparus GmbH. employing process.
The app is a simple user information collector from different data sources, such as:
- Postgres DB
- MariaDB
- Mysql DB

## Requirements:
- Java 21
- Gradle

## Running the app
```shell
docker compose -f .\deploy\docker-compose.yml up --build
```

## Current setup
The docker compose file contains the following services:
- Postgres DB
- MariaDB
- Mysql DB
- User-app

Each DB has some inserted data to test the app.
The oracle DB is unaccessible (Only god knows how to deploy it as container with all permissions, easy-peasy table and data creation and so on...)

After the DBs are up and running, the app is accessible on port 8080.

Check the app on http://localhost:8080/swagger-ui/index.html

Request parameters are optional and can be used to filter the data by AND operator.
If no parameter is provided, all data will be returned.

Have fun!
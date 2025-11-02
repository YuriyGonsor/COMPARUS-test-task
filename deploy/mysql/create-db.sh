#!/usr/bin/env bash
set -euo pipefail

DB="${MYSQL_DATABASE:-mysql}"
PASS="${MYSQL_ROOT_PASSWORD:?MYSQL_ROOT_PASSWORD missing}"

mysql --protocol=SOCKET -uroot -p"${PASS}" "${DB}" < /docker-entrypoint-initdb.d/create-db-schema.sql
mysql --protocol=SOCKET -uroot -p"${PASS}" "${DB}" < /docker-entrypoint-initdb.d/insert-default-data.sql

echo "[MySQL] init done"

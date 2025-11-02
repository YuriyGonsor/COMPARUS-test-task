#!/usr/bin/env bash
set -euo pipefail

DB="${MARIADB_DATABASE:-mariadb_db}"
PASS="${MARIADB_ROOT_PASSWORD:?MARIADB_ROOT_PASSWORD missing}"
SOCK="${MARIADB_SOCKET:-/run/mysqld/mysqld.sock}"

mariadb --protocol=SOCKET --socket="${SOCK}" -uroot -p"${PASS}" "${DB}" \
  < /docker-entrypoint-initdb.d/create-db-schema.sql

mariadb --protocol=SOCKET --socket="${SOCK}" -uroot -p"${PASS}" "${DB}" \
  < /docker-entrypoint-initdb.d/insert-default-data.sql

echo "[MariaDB] init done"

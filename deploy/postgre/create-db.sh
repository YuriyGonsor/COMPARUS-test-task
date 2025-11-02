#!/usr/bin/env bash
set -euo pipefail
export PGPASSWORD="${POSTGRES_PASSWORD:-${PGPASSWORD:-}}"

psql -v ON_ERROR_STOP=1 \
  -U "${POSTGRES_USER:-postgres}" \
  -d "${POSTGRES_DB:-postgres}" \
  -f /docker-entrypoint-initdb.d/create-db-schema.sql \
  -f /docker-entrypoint-initdb.d/insert-default-data.sql

echo "[PG] init done"
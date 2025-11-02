#!/usr/bin/env bash
set -euo pipefail
PDB="${ORACLE_PDB:-XEPDB1}"

echo "[OracleXE] running APP scripts..."
sqlplus -s "sys/${ORACLE_PASSWORD}@//127.0.0.1:1521/${PDB} as sysdba" <<'SQL'
whenever sqlerror exit failure rollback;
@/container-entrypoint-initdb.d/01_create-app-user.sql
@/container-entrypoint-initdb.d/02_create-schema.sql
@/container-entrypoint-initdb.d/03_insert-data.sql
exit success
SQL
echo "[OracleXE] APP scripts done."
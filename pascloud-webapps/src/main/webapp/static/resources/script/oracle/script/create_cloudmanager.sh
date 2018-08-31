#!/bin/sh
export ORACLE_SID=$1
export ORACLE_HOME=$2
$ORACLE_HOME/bin/sqlplus /nolog <<EOF
conn / as sysdba
@/home/oracle/script/createCloudManager.sql
exit
EOF

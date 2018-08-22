#!/bin/sh
#su - oracle <<EON
export ORACLE_HOME=$2
export ORACLE_SID=$1
$2/bin/sqlplus / as sysdba @/home/oracle/script/oraCreateTableSpaces.sql $1
#exit
#EON

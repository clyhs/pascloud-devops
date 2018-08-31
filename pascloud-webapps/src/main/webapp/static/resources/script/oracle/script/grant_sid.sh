#!/bin/sh
##su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
$ORACLE_HOME/bin/sqlplus / as sysdba @/home/oracle/script/oraCreateUser.sql
##exit
##EON

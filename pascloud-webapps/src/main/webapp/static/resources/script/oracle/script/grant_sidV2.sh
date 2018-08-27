#!/bin/sh
##su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
$2/bin/sqlplus / as sysdba @/home/oracle/script/oraCreateUserV2.sql $3 $4
##exit
##EON

#!/bin/sh
export ORACLE_SID=$1
export ORACLE_HOME=$2
$ORACLE_HOME/bin/sqlplus $3/$4 @/home/oracle/script/createType.sql
$ORACLE_HOME/bin/sqlplus $3/$4 @/home/oracle/script/createJAVA_SOURCE.sql

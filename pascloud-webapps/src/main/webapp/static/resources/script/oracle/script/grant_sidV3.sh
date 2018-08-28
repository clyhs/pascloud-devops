#!/bin/sh
#su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
echo $ORACLE_HOME
$ORACLE_HOME/bin/sqlplus $3/$4 as sysdba @/home/oracle/script/oraCreateUserV2.sql $5 $6
#exit
#EON

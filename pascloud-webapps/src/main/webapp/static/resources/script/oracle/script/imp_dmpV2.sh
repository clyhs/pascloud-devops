#!/bin/sh
##su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
$ORACLE_HOME/bin/imp $3/$4@$ORACLE_SID file=/home/oracle/script/cloudpas_ddl.dmp fromuser=PAS touser=$3 ignore=y
$ORACLE_HOME/bin/imp $3/$4@$ORACLE_SID file=/home/oracle/script/data.dmp fromuser=PAS touser=$3 ignore=y
##exit
##EON

#!/bin/sh
##su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
$2/bin/imp $3/$3@$1 file=/home/oracle/script/cloudpas_ddl.dmp fromuser=pas touser=$3 ignore=y
$2/bin/imp $3/$3@$1 file=/home/oracle/script/data.dmp fromuser=pas touser=$3 ignore=y
##exit
##EON

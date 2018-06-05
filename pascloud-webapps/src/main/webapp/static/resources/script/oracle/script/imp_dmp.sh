#!/bin/sh
su - oracle <<EON
export ORACLE_SID=$1
imp pas/pas@$1 file=/home/oracle/script/cloudpas_ddl.dmp fromuser=pas touser=pas ignore=y
imp pas/pas@$1 file=/home/oracle/script/data.dmp fromuser=pas touser=pas ignore=y
exit
EON

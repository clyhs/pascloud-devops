#!/bin/sh
##su - oracle <<EON
export ORACLE_SID=$1 
export ORACLE_HOME=$2
$ORACLE_HOME/bin/lsnrctl stop
$ORACLE_HOME/bin/sqlplus /nolog <<EOF
conn / as sysdba
shutdown immediate
exit
EOF
##exit
##EON

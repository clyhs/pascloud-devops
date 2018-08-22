#!/bin/sh
##su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
$2/bin/lsnrctl start
$2/bin/sqlplus /nolog <<EOF
conn / as sysdba
startup
exit
EOF
##exit
##EON

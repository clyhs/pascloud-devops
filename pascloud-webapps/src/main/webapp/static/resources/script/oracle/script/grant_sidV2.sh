#!/bin/sh
#su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
echo $ORACLE_HOME
$ORACLE_HOME/bin/sqlplus /nolog <<EOF
conn / as sysdba
@/home/oracle/script/oraCreateUserV2.sql $3 $4
exit
EOF
#exit
#EON
##@/home/oracle/script/oraCreateUserV2.sql $3 $4

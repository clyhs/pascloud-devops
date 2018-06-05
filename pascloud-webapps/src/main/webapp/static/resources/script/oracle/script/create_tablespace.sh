##!/bin/sh
su - oracle <<EON
export ORACLE_SID=$1
sqlplus / as sysdba @/home/oracle/script/oraCreateTableSpaces.sql $1
exit
EON

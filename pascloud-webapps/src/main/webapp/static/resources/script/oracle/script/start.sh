#!/bin/sh
su - oracle <<EON
export ORACLE_SID=$1
lsnrctl start
sqlplus /nolog <<EOF
conn / as sysdba
startup
exit
EOF
exit
EON

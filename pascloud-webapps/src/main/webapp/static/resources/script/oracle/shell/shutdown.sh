#!/bin/sh
su - oracle <<EON
export ORACLE_SID=$1 
lsnrctl stop
sqlplus /nolog <<EOF
conn / as sysdba
shutdown immediate
exit  
EOF
exit
EON

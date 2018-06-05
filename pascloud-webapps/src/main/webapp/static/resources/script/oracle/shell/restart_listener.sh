#!/bin/sh
su - oracle <<EON
export ORACLE_SID=$1
lsnrctl stop
lsnrctl start
exit
EON

#!/bin/sh
##su - oracle <<EON
export ORACLE_SID=$1
export ORACLE_HOME=$2
$2/bin/lsnrctl status | grep $1
##EON

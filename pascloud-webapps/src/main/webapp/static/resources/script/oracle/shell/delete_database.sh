#!/bin/sh

find $ORACLE_BASE/ -name $1 -exec rm -rf {} \;

find $ORACLE_BASE/* -name $2 | grep -v admin | grep -v oradata | xargs rm -rf


#!/bin/sh
if [  $# -ne 1 ]; then
    echo "wrong input para number"
    echo "create_database.sh databasename"
    exit 0

fi

export ORACLE_SID=$1
#---------------------------------------------------

#----------------------------------------------------
basepath=$(cd `dirname $0`; pwd)
#00 set the parameter
dir_log=$basepath/log
mkdir -p $dir_log
#-------------------------------------------------------
#1 create the responce file
rspfile_std=$basepath/dbca_std.rsp
rspfile=$basepath/dbca.rsp
echo $rspfile
#------------------------------------------
cp $rspfile_std $rspfile
sed -i "s/cloudpas/$1/g" $rspfile
#----------------------------------------------------------
#2\ create the datafile directory 
sh $basepath/create_dir_std.sh $1

#-----------------------------------------------------------
#3\create the database
$ORACLE_HOME/bin/dbca -silent -responseFile $rspfile
#-----------------------------------------------------------
#4 create the tablespace
sqlplus / as sysdba @$basepath/oraCreateTableSpaces.sql $1
#-----------------------------------------------------------
#5 create pas user and grant priv
sqlplus / as sysdba @$basepath/oraCreateUser.sql

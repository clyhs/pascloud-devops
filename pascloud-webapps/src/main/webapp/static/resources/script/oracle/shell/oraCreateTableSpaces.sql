set verify off
set echo off

DEFINE oracle_sid=&1
/*
DROP TABLESPACE TBS_MXZ INCLUDING CONTENTS AND DATAFILES;
DROP TABLESPACE TBS_NBZZ INCLUDING CONTENTS AND DATAFILES;
DROP TABLESPACE TBS_JXDX INCLUDING CONTENTS AND DATAFILES;
DROP TABLESPACE TBS_JKSJ INCLUDING CONTENTS AND DATAFILES;
DROP TABLESPACE TBS_LSB INCLUDING CONTENTS AND DATAFILES;
DROP TABLESPACE TBS_PAS INCLUDING CONTENTS AND DATAFILES;
DROP TABLESPACE TBS_IDX INCLUDING CONTENTS AND DATAFILES;
DROP TABLESPACE TBS_TMP INCLUDING CONTENTS AND DATAFILES;
*/


CREATE TABLESPACE "TBS_MXZ"  
  LOGGING DATAFILE 
  '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_DATA/TBS_MXZ1.dbf' SIZE 500m
   autoextend on
   next 10M 
   maxsize unlimited;
  commit;
 
CREATE TABLESPACE "TBS_NBZZ"  
  LOGGING DATAFILE 
  '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_DATA/TBS_NBZZ1.dbf' SIZE 500m
   autoextend on
   next 10M
   maxsize unlimited;
  
CREATE TABLESPACE "TBS_JXDX"  
  LOGGING DATAFILE 
  '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_DATA/TBS_JXDX1.dbf' SIZE 3G
   autoextend on
   next 10M
   maxsize unlimited;

   commit;
CREATE TABLESPACE "TBS_JKSJ"  
  LOGGING DATAFILE 
  '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_DATA/TBS_JKSJ1.dbf' SIZE 500m
   autoextend on
   next 10M 
   maxsize unlimited;
   commit;
CREATE TABLESPACE "TBS_LSB"  
  LOGGING DATAFILE 
  '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_DATA/TBS_LSB1.dbf' SIZE 500m
   autoextend on
   next 10M 
   maxsize unlimited;
   commit;
CREATE TABLESPACE "TBS_PAS"  
  LOGGING DATAFILE 
  '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_DATA/TBS_PAS2.dbf' SIZE 1G
   autoextend on
   next 10M
   maxsize unlimited;
   commit;
CREATE TABLESPACE "TBS_IDX"  
  LOGGING DATAFILE 
  '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_IDX/TBS_IDX2.dbf' SIZE 1G
   autoextend on
   next 10M 
   maxsize unlimited;  
   commit;
CREATE TEMPORARY TABLESPACE TBS_TMP
  TEMPFILE
   '/home/oracle/db_tablespace/&oracle_sid/PAS_DATA/PAS_SPACE_TMP/TBS_TMP2.dbf' SIZE 1G
   autoextend on
   next 100M
   maxsize unlimited;  
    commit;
quit

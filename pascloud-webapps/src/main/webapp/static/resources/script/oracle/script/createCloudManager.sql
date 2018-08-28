-- Create the user 
create user CLOUDMANAGER IDENTIFIED BY CLOUDMANAGER default tablespace USERS temporary tablespace TEMP profile DEFAULT;
-- Grant/Revoke object privileges 
grant select on ALL_USERS to CLOUDMANAGER;
grant select on DBA_DATA_FILES to CLOUDMANAGER with grant option;
grant select on DBA_FREE_SPACE to CLOUDMANAGER with grant option;
grant select on DBA_TABLESPACES to CLOUDMANAGER with grant option;
-- Grant/Revoke role privileges 
grant connect to CLOUDMANAGER with admin option;
grant dba to CLOUDMANAGER with admin option;
grant javasyspriv to CLOUDMANAGER with admin option;
grant javauserpriv to CLOUDMANAGER with admin option;
grant resource to CLOUDMANAGER with admin option;
grant sysoper to cloudmanager;
-- Grant/Revoke system privileges 
grant alter user to CLOUDMANAGER;
grant create any index to CLOUDMANAGER with admin option;
grant create any table to CLOUDMANAGER with admin option;
grant create sequence to CLOUDMANAGER;
grant create session to CLOUDMANAGER;
grant create tablespace to CLOUDMANAGER;
grant create user to CLOUDMANAGER;
grant drop any sequence to CLOUDMANAGER;
grant unlimited tablespace to CLOUDMANAGER with admin option;

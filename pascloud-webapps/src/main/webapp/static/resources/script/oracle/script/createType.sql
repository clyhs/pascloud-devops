CREATE OR REPLACE TYPE "AQJS_ROW"                                                                                                                                                                                                                                                                                                     AS OBJECT
(
    jsdh      number,--角色代号
    sjjs    varchar2(200), --上级角色代号
    lev    varchar2(200) --层级代号
);
/

CREATE OR REPLACE TYPE AQJS_TABLE as table of aqjs_row;
/

CREATE OR REPLACE TYPE "IDXSTR_ROW"                                                                                                                                                                                                                                                                                                     AS OBJECT
(
    tabname     varchar2(200),--表名
    idx    varchar2(200) --主键字符串

);
/

CREATE OR REPLACE TYPE IDXSTR_TABLE as table of idxstr_row;
/

CREATE OR REPLACE TYPE "SJJG_ROW"                                                                                                                                                                                                                                                                                                     AS OBJECT
(
    khdxdh      number,--考核对象代号
    jgdh    varchar2(200), --机构代号
    jgmc    varchar2(200), --机构代号
    sjkhdxdh   number,     --上级考核对象代号
    jbjj      number,  --机构间距
    ckhy      varchar2(1)  --查看行员标志
);
/

CREATE OR REPLACE TYPE SJJG_TABLE as table of sjjg_row;
/

create or replace type vartabletype as table of varchar2(1000);
/

CREATE OR REPLACE TYPE WM_CONCAT_IM
    AUTHID CURRENT_USER AS OBJECT
    (
      CURR_STR VARCHAR2(32767),
      STATIC FUNCTION ODCIAGGREGATEINITIALIZE(SCTX IN OUT wm_concat_im) RETURN NUMBER,
      MEMBER FUNCTION ODCIAGGREGATEITERATE(SELF IN OUT wm_concat_im,
    		    P1 IN VARCHAR2) RETURN NUMBER,
      MEMBER FUNCTION ODCIAGGREGATETERMINATE(SELF IN wm_concat_im,
    				      RETURNVALUE OUT VARCHAR2,
   				      FLAGS IN NUMBER)
   			  RETURN NUMBER,
     MEMBER FUNCTION ODCIAGGREGATEMERGE(SELF IN OUT wm_concat_im,
   			 SCTX2 IN  wm_concat_im) RETURN NUMBER
  );
/

  CREATE OR REPLACE TYPE "XJHY_ROW"                                                                                                                                                                                                                                                                                                     AS OBJECT
(
    khdxdh      number,--考核对象代号
    hydh    varchar2(200), -- 行员代号
    hymc    varchar2(200) -- 行员名称
);
/

create or replace type xjhy_table as TABLE OF xjhy_row;
/

CREATE OR REPLACE TYPE "YWLB_ROW"                                                                                                                                                                                                                                                                                                     AS OBJECT
(
    ywlb    varchar2(2),  -- 业务类别
    ms    varchar2(200)   -- 业务类别描述
);
/

create or replace type YWLB_TABLE as TABLE OF YWLB_ROW;
/

quit;
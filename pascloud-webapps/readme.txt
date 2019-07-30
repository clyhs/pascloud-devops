1、初始化数据库
2、配置MYCAT服务地址
3、新增pas-cloud-demo-service的租户数据库地址
4、复制开发中心文件到 主服务目录，设置分行代号
5、抽行员数据到公共库

#service-demo:
docker run --name pas_service_demo   -d -v /home/pascloud/pas-cloud-service-demo:/home/pascloud/pas-cloud-service-demo -p 8201:8201 -p 8211:8211 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-demo/bin/start.sh
#paspm
docker run --name pas_mycat   -d -v /home/pascloud16/mycat:/home/pascloud16/mycat -p 8066:8066 -p 9066:9066 pascloud/jdk7:v1.0 /home/pascloud16/mycat/bin/mycat console & 
#zookeeper
docker run --name pascloud_zookeeper_admin  --restart=always  -d -p 8686:8686 -p 2181:2181 pascloud/zk_dubbo:v1.1

http://localhost:8080/graph/D_192.168.0.7_9066/MycatActiveThreadGraph?probe=MycatThreadPool

http://localhost:8080/graph/D_192.168.0.7_9066/MycatActiveThreadGraph?probe=MycatThreadPool&begin=2018-05-17%2000:00&end=2018-05-17%2016:54

docker run --name pascloud_zookeeper_admin  --restart=always --network=host  -d  zookeeper

缓冲队列分析
/mycat/graph/D_192.168.0.7_9066/MycatTaskQueueGraph?probe=MycatThreadPool
tps分析
/mycat/graph/D_192.168.0.7_9066/MycatTPSGraph?probe=MycatThreadPool
内存分析
/mycat/graph/D_192.168.0.7_9066/MycatMemoryGraph?probe=MycatMemory
浏量分析
/mycat/graph/D_192.168.0.7_9066/MycatFluxGraph?probe=MycatPerfProbe
连接分析
/mycat/graph/D_192.168.0.7_9066/MycatConnectionGraph?probe=MycatPerfProbe


菜单
  |--服务器管理
  |--|--节点管理
  |--云平台管理
  |--|--服务管理
  |--|--镜像管理
  |--|--版本管理  
  |--|--缓存管理
  |--|--租户管理（需要添加数据库，配置ZK,REDIS,MYCAT,MQ）
  |--配置管理
  |--|--云平台服务配置
  |--工具管理
  |--|--mycat中间件配置
  |--|--数据库客户端
  |--|--pb应用管理（去掉）
  
  步骤一：关闭数据库
1.export ORACLE_SID=cpas12
2.sqlplus / as sysdba
3.shutdown immediate
  
  oracle删除
  find $ORACLE_BASE/ -name cpas12
  用命令删除查询后文件（注意：如果上一步骤查出来的文件有非实例相关文件，则不能直接通过 "-exec rm -rf {} \;"命令进行删除，建议一条一条删除，以免误删除文件）
  find $ORACLE_BASE/ -name cpas12 -exec rm -rf {} \;
  
  
  find $ORACLE_BASE/* -name '*[Cc][Pp][Aa][Ss]12*' | grep -v admin| grep -v  oradata
  find $ORACLE_BASE/* -name '*[Cc][Pp][Aa][Ss]12*' | grep -v admin | grep -v oradata | xargs rm -rf
  
  删除实例配置文件中的信息
1.vi /etc/oratab
2.找到/Oracle_11g/oracle:N
3.将该行信息删除，并保存文件


docker pull mysql:5.7
docker run --name pascloud_mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 --lower_case_table_names=1 --character-set-server=utf8 --collation-server=utf8

#chmod -R 777 /var/tmp/.oracle
  
  
docker run --name pascloud_service_paspm_csifom --network=host  -d -v /home/pascloud/pas-cloud-service-paspm-csifom:/home/pascloud/pas-cloud-service-paspm-csifom -p 8202:8202 -p 8212:8212 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-paspm-csifom/bin/start.sh

docker run --name pascloud_service_paspm_lecaup  -d -v /home/pascloud/pas-cloud-service-paspm-lecaup:/home/pascloud/pas-cloud-service-paspm-lecaup -p 8202:8202 -p 8212:8212 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-paspm-lecaup/bin/start.sh

docker run --name pascloud_service_paspm_jpumpp  -d -v /home/pascloud/pas-cloud-service-paspm-jpumpp:/home/pascloud/pas-cloud-service-paspm-jpumpp -p 8202:8202 -p 8212:8212 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-paspm-jpumpp/bin/start.sh



docker run --name pascloud_service_demo_mdsqwb  -d -v /home/pascloud/pas-cloud-service-demo-mdsqwb:/home/pascloud/pas-cloud-service-demo-mdsqwb -p 8201:8201 -p 8211:8211 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-demo-mdsqwb/bin/start.sh
  
  
docker run --name pascloud_service_demo_cdkfp1 --network=host  -d -v /home/domains/pascloud/pas-cloud-service-demo-cdkfp1:/home/domains/pascloud/pas-cloud-service-demo-cdkfp1 pascloud/jdk7:v1.0 /home/domains/pascloud/pas-cloud-service-demo-cdkfp1/bin/start.sh

  
-------------------------  
redhat6.5
1
cd /etc/yum.repos.d
wget http://www.hop5.in/yum/el6/hop5.repo
yum install kernel-ml-aufs kernel-ml-aufs-devel

2、修改引导的内核
vi /etc/grub.conf
把默认的引导文件设置为0。因为升级内核之后，新的内核在第一个（0）位置。


3、重启系统，使用新的内核
#检查内核版本：
uname  -r
3.10.5-3.el6.x86_64
#检查aufs是否存在
grep aufs /proc/filesystems
nodev     aufs

4
wget http://ftp.riken.jp/Linux/fedora/epel/6/x86_64/epel-release-6-8.noarch.rpm
rpm -ivh  epel-release-6-8.noarch.rpm

5
安装docker
yum -y install docker-io  

Get http:///var/run/docker.sock/v1.19/containers/json: dial unix /var/run/docker.sock: no such file or directory. Are you trying to connect to a TLS-enabled daemon without TLS?
升级内核-》1

docker: relocation error: docker: symbol dm_task_get_info_with_deferred_remove, version Base not defined in file libdevmapper.so.1.02 with link time reference
yum install device-mapper-event-libs

------------------


docker swarm init --advertise-addr 192.168.0.16



-------------------
多实例：
1.create_database.sh cpas01 $ORACLE_HOME
2.create_tablespace.sh cpas01
3.grant_sid.sh cpas01 $ORACLE_HOME
4.imp_dmp.sh cpas01 $ORACLE_HOME

超级用户执行过程
export ORACLE_SID=cpas01
sqlplus /nolog
SQL>conn / as sysdba
SQL>@home/oracle/script/createCloudManager.sql

./grant_sidV3.sh cpas01 /u01/app/oracle/product/11.2.0/dbhome_1 CLOUDMANAGER CLOUDMANAGER PAS2 PAS2
./create_shell.sh cpas01 /u01/app/oracle/product/11.2.0/dbhome_1 PAS2 pas2
./imp_dmpV2.sh cpas01 /u01/app/oracle/product/11.2.0/dbhome_1 PAS2 pas2

docker
限制日志大小
--log-opt max-size=10m --log-opt max-file=3
查看日志
docker logs -f -t --tail 1000 pascloud_tomcat

docker run -d --name pascloud_nginx --network=host -v /app/app/pascloud/nginx/nginx.conf:/etc/nginx/nginx.conf -v /etc/localtime:/etc/localtime -v /app/app/pascloud/nginx/proxy_temp:/app/app/pascloud/nginx/proxy_temp -v /app/app/pascloud/nginx/proxy_cache:/app/app/pascloud/nginx/proxy_cache -v /home/domains/pascloud/ROOT:/home/domains/pascloud/ROOT nginx

docker run -d --name pascloud_nginx --network=host -v /home/domains/pascloud/nginx/nginx.conf:/etc/nginx/nginx.conf -v /etc/localtime:/etc/localtime -v /home/domains/pascloud/nginx/proxy_temp:/home/domains/pascloud/nginx/proxy_temp -v /home/domains/pascloud/nginx/proxy_cache:/home/domains/pascloud/nginx/proxy_cache -v /home/domains/pascloud/ROOT:/home/domains/pascloud/ROOT nginx


docker run -d --log-opt max-size=100m --log-opt max-file=3 --name pascloud_tomcat -p:8170:8170 -v /app/app/pascloud/tomcat:/app/app/pascloud/tomcat -v /app/app/pascloud/pas-cloud-service-demo:/app/app/pascloud/pas-cloud-service-demo pascloud/jdk7:v1.0  /app/app/pascloud/tomcat/bin/catalina.sh run 


docker run -d --log-opt max-size=100m --log-opt max-file=3 --name pascloud_tomcat -p:8170:8170 -v /home/domains/pascloud/tomcat:/home/domains/pascloud/tomcat -v /home/domains/pascloud/pas-cloud-service-demo:/home/domains/pascloud/pas-cloud-service-demo  pascloud/jdk7:v1.0  /home/domains/pascloud/tomcat/bin/catalina.sh run 

docker run -d --log-opt max-size=100m --log-opt max-file=3 --name pascloud_tomcat -p 1099:1099 -p:8170:8170 -v /home/domains/pascloud/tomcat:/home/domains/pascloud/tomcat -v /home/domains/pascloud/pas-cloud-service-demo:/home/domains/pascloud/pas-cloud-service-demo  pascloud/jdk7:v1.0  /home/domains/pascloud/tomcat/bin/catalina.sh run 


docker run -d --log-opt max-size=100m --log-opt max-file=3 --name pascloud_tomcat2 -p:8270:8170 -v /home/domains/pascloud/tomcat2:/home/domains/pascloud/tomcat2 -v /home/domains/pascloud/pas-cloud-service-demo:/home/domains/pascloud/pas-cloud-service-demo  pascloud/jdk7:v1.0  /home/domains/pascloud/tomcat2/bin/catalina.sh run 

docker run -d --log-opt max-size=100m --log-opt max-file=3 --name pascloud_tomcat3 -p:8370:8170 -v /home/domains/pascloud/tomcat3:/home/domains/pascloud/tomcat3 -v /home/domains/pascloud/pas-cloud-service-demo:/home/domains/pascloud/pas-cloud-service-demo  pascloud/jdk7:v1.0  /home/domains/pascloud/tomcat3/bin/catalina.sh run 


docker run -d --log-opt max-size=100m --log-opt max-file=3 --name pascloud_tomcat7 -p:8080:8080 -v /home/domains/pascloud/tomcat7:/home/domains/pascloud/tomcat7 -v /home/domains/pascloud/pas-cloud-service-demo:/home/domains/pascloud/pas-cloud-service-demo  pascloud/jdk7:v1.0  /home/domains/pascloud/tomcat7/bin/catalina.sh run 


安装nginx-1.12.1.tar.gz
cpp gcc gcc-c++ glibc-devel glibc-headers libstdc++ kernel-headers keyutils-lib-devel krb5-devel libmpc libselinux-devel libsepol-devel libverto-devel libcom_err-devel
zlib zlib-devel openssl openssl-devel pcre pcre-devel
./configure --prefix=/usr/local/nginx --with-http_ssl_module --with-http_flv_module --with-http_stub_status_module --with-http_gzip_static_module --with-http_stub_status_module --with-http_sub_module
make && make install

./nmon_linux_x86_64 -F  XXX.nmon -s 2 -c 300 -t 
./nmon_linux_x86_64 -F  XXX.nmon -s 120 -c 310 -t 
(只能在nmon路径下)2取一次，取300次

AOF重写可以手动触发和自动触发：
1.手动触发可以调用bgrewriteaof。
2.根据如下两个参数自动触发。
redis.conf
#代表当前AOF文件空间和上次重写后AOF空间的比值。
auto-aof-rewrite-percentage 100
#AOP超过64m就开始收缩
auto-aof-rewrite-min-size 64mb

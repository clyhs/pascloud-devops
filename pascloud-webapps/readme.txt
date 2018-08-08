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
2.找到 bgsp:/Oracle_11g/oracle:N
3.将该行信息删除，并保存文件


docker pull mysql:5.7
docker run --name pascloud_mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:5.7 --lower_case_table_names=1

#chmod -R 777 /var/tmp/.oracle
  
  
docker run --name pascloud_service_paspm_csifom --network=host  -d -v /home/pascloud/pas-cloud-service-paspm-csifom:/home/pascloud/pas-cloud-service-paspm-csifom -p 8202:8202 -p 8212:8212 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-paspm-csifom/bin/start.sh

docker run --name pascloud_service_paspm_lecaup  -d -v /home/pascloud/pas-cloud-service-paspm-lecaup:/home/pascloud/pas-cloud-service-paspm-lecaup -p 8202:8202 -p 8212:8212 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-paspm-lecaup/bin/start.sh

docker run --name pascloud_service_paspm_jpumpp  -d -v /home/pascloud/pas-cloud-service-paspm-jpumpp:/home/pascloud/pas-cloud-service-paspm-jpumpp -p 8202:8202 -p 8212:8212 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-paspm-jpumpp/bin/start.sh



docker run --name pascloud_service_demo_mdsqwb  -d -v /home/pascloud/pas-cloud-service-demo-mdsqwb:/home/pascloud/pas-cloud-service-demo-mdsqwb -p 8201:8201 -p 8211:8211 pascloud/jdk7:v1.0 /home/pascloud/pas-cloud-service-demo-mdsqwb/bin/start.sh
  
  
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
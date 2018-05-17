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
docker run --name pas_zk_dubbo_admin  --restart=always  -d -p 8686:8686 -p 2181:2181 pascloud/zk_dubbo:v1.1

http://localhost:8080/graph/D_192.168.0.7_9066/MycatActiveThreadGraph?probe=MycatThreadPool

http://localhost:8080/graph/D_192.168.0.7_9066/MycatActiveThreadGraph?probe=MycatThreadPool&begin=2018-05-17%2000:00&end=2018-05-17%2016:54


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
  
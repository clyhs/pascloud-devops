To add a worker to this swarm, run the following command:

    docker swarm join \
    --token SWMTKN-1-5rpsvo48osjrhaw9qlmis6foihvgqa9dr82dmn8ilhwr1096an-5ivkrom7l3pjceayw8o3jcpbf \
    192.168.0.17:2377

To add a manager to this swarm, run 'docker swarm join-token manager' and follow the instructions.

初始化报错

Error response from daemon: --live-restore daemon configuration is incompatible with swarm mode


修改/etc/docker/daemon.json文件，true改为false

{
    "live-restore": false
}


1.listimages()
{"created":"1509748757","id":"sha256:6ad733544a6317992a6fac4eb19fe1df577d4dec7529efec28a5bd0edad0fd30","parentId":"","repoTags":["busybox:latest"],"repoDigests":["busybox@sha256:bbc3a03235220b170ba48a157dd097dd1379299370e1ed99ce976df0355d24f0"],"size":1129289,"virtualSize":1129289}
{"created":"1509026348","id":"sha256:1616e2759a8ebc2ac1954533ccd4298c268306ddf05b6652fc3c646e420216f4","parentId":"","repoTags":["zabbix/zabbix-agent:latest"],"size":56662411,"virtualSize":56662411,"labels":{"maintainer":"Alexey Pustovalov \u003calexey.pustovalov@zabbix.com\u003e"}}
{"created":"1488322454","id":"sha256:47a81cdb853be1688596dd357feeb5f0886fd5bafd109d5763cc302404bcd51d","parentId":"","repoTags":["rethinkdb:latest"],"size":181786068,"virtualSize":181786068,"labels":{}}
{"created":"1484706726","id":"sha256:36b1e23becabc0b27c5787712dce019982c048665fd9e7e6cb032a46bcac510d","parentId":"","repoTags":["swarm:latest"],"size":15852351,"virtualSize":15852351,"labels":{}}
{"created":"1451362632","id":"sha256:cfee14e5d6f280892b9fb16e830aae5c9458e61a7502048ac45b6b1723e8b0b9","parentId":"","repoTags":["shipyard/docker-proxy:latest"],"size":9463693,"virtualSize":9463693,"labels":{}}
{"created":"1439009020","id":"sha256:6aef84b9ec5a64742d9a24a5191c9b17a48a12a57168d5cc5d053725d8d555c2","parentId":"","repoTags":["microbox/etcd:latest"],"size":17862381,"virtualSize":17862381,"labels":{}}

2.listnodes()

{"id":"l5pseptc4q9qpmq178rf75gih","version":{"index":9},"createdAt":"Dec 13, 2017 11:29:06 AM","updatedAt":"Dec 13, 2017 11:29:07 AM","spec":{"role":"manager","availability":"active"},"description":{"hostname":"centosdb.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":16000000000,"memoryBytes":84298596352},"engine":{"engineVersion":"1.13.1","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"127.0.0.1"},"managerStatus":{"leader":true,"reachability":"reachable","addr":"192.168.0.17:2377"}}
{"id":"laj6f0cwr4ramgfsgf8t25404","version":{"index":15},"createdAt":"Dec 13, 2017 11:31:05 AM","updatedAt":"Dec 13, 2017 11:31:05 AM","spec":{"role":"worker","availability":"active"},"description":{"hostname":"centoss2.pascloud.com","platform":{"architecture":"x86_64","os":"linux"},"resources":{"nanoCpus":8000000000,"memoryBytes":16659865600},"engine":{"engineVersion":"1.13.1","plugins":[{"type":"Network","name":"bridge"},{"type":"Network","name":"host"},{"type":"Network","name":"macvlan"},{"type":"Network","name":"null"},{"type":"Network","name":"overlay"},{"type":"Volume","name":"local"}]}},"status":{"state":"ready","addr":"192.168.0.7"}}

3.listcontainer()

{"id":"a7051c54908d4728def725eb4aa450367a0a6d528ce52b416f3f212901664822","names":["/shipyard-proxy"],"image":"shipyard/docker-proxy:latest","imageId":"sha256:cfee14e5d6f280892b9fb16e830aae5c9458e61a7502048ac45b6b1723e8b0b9","command":"/usr/local/bin/run","created":1513143941,"state":"running","status":"Up 21 minutes","ports":[{"privatePort":2375,"publicPort":2375,"type":"tcp","ip":"0.0.0.0"}],"labels":{},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"678c4a99af808e6ed174ca41eab7837d9e76cdda0f632f559ccb1e22c87ae67d","endpointId":"9dc9366bdad2ce0f71c0e4460b23f3b35fb0b232d6661835799c8fabb7639635","gateway":"172.17.0.1","ipAddress":"172.17.0.3","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:03"}}},"mounts":[{"type":"bind","source":"/var/run/docker.sock","destination":"/var/run/docker.sock","mode":"","rw":true,"propagation":""}]}
{"id":"6d09536f7e587c070376045861fe31064e13d2cc74db4645f26eecb274d00e7f","names":["/some-zabbix-agent"],"image":"zabbix/zabbix-agent","imageId":"sha256:1616e2759a8ebc2ac1954533ccd4298c268306ddf05b6652fc3c646e420216f4","command":"/bin/bash /run_zabbix_component.sh agentd none","created":1511228035,"state":"running","status":"Up 3 weeks","ports":[{"privatePort":10050,"publicPort":10050,"type":"tcp","ip":"0.0.0.0"}],"labels":{"maintainer":"Alexey Pustovalov \u003calexey.pustovalov@zabbix.com\u003e"},"networkSettings":{"ports":{},"networks":{"bridge":{"networkId":"678c4a99af808e6ed174ca41eab7837d9e76cdda0f632f559ccb1e22c87ae67d","endpointId":"250e4e6c4959e02eeb2286a0e6865ef4f661d72689b1de35ae82d9293c30de1a","gateway":"172.17.0.1","ipAddress":"172.17.0.2","ipPrefixLen":16,"ipv6Gateway":"","globalIPv6Address":"","globalIPv6PrefixLen":0,"macAddress":"02:42:ac:11:00:02"}}},"mounts":[{"type":"volume","name":"1b50b7a139ce2837cde1130f923efd45bae8bf7e5647373cc4190ed57d2d5145","source":"/var/lib/docker/volumes/1b50b7a139ce2837cde1130f923efd45bae8bf7e5647373cc4190ed57d2d5145/_data","destination":"/var/lib/zabbix/modules","driver":"local","mode":"","rw":true,"propagation":""},{"type":"volume","name":"6e4591d7538edc622e5085b3f69b9b2c6e4cbb7d45a4d84d99fc96e44d8a9c02","source":"/var/lib/docker/volumes/6e4591d7538edc622e5085b3f69b9b2c6e4cbb7d45a4d84d99fc96e44d8a9c02/_data","destination":"/etc/zabbix/zabbix_agentd.d","driver":"local","mode":"","rw":true,"propagation":""},{"type":"volume","name":"22345154c28c8b2b676c3bb38be4bf579904e96019c7270a41f66f22989a0149","source":"/var/lib/docker/volumes/22345154c28c8b2b676c3bb38be4bf579904e96019c7270a41f66f22989a0149/_data","destination":"/var/lib/zabbix/enc","driver":"local","mode":"","rw":true,"propagation":""}]}

4.inspectSwarm
{"id":"ss64pzvinnt7jm9m01mi41y1a","version":{"index":10},"createdAt":"Dec 13, 2017 11:29:06 AM","updatedAt":"Dec 13, 2017 11:29:07 AM","swarmSpec":{"name":"default","orchestration":{"taskHistoryRetentionLimit":5},"raft":{"snapshotInterval":10000,"keepOldSnapshots":0,"logEntriesForSlowFollowers":500,"electionTick":3,"heartbeatTick":1},"dispatcher":{"heartbeatPeriod":5000000000},"caConfig":{"nodeCertExpiry":7776000000000000},"encryptionConfig":{"autoLockManagers":false},"taskDefaults":{}},"joinTokens":{"worker":"SWMTKN-1-5rpsvo48osjrhaw9qlmis6foihvgqa9dr82dmn8ilhwr1096an-5ivkrom7l3pjceayw8o3jcpbf","manager":"SWMTKN-1-5rpsvo48osjrhaw9qlmis6foihvgqa9dr82dmn8ilhwr1096an-b9315fdmpdkm6tbo41n115w0v"}}



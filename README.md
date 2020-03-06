# DubboDemo
学习Dubbo

# 启动 Zookeeper
解压：tar -zxvf zookeeper-3.4.6.tar.gz
配置：进入conf目录重命名 mv zoo_sample.cfg zoo.cfg
      修改zoo.cfg文件 dataDir=/home/cheng/zookeeper-3.4.6/data
改变所属目录：
      [root@localhost local]# chown cheng:cheng ./zookeeper-3.4.6/ -R
      [root@localhost local]# chmod a+x *
切换成普通用户：su - cheng
启动：进入bin目录
      [cheng@localhost bin]$ ./zkServer.sh start
      JMX enabled by default
      Using config: /home/cheng/zookeeper-3.4.6/bin/../conf/zoo.cfg
      Starting zookeeper ... STARTED
查看是否启动：进入bin目录 ./zkServer.sh status
      [cheng@localhost bin]$  ./zkServer.sh status
      JMX enabled by default
      Using config: /home/cheng/zookeeper-3.4.6/bin/../conf/zoo.cfg
      Mode: standalone

停止：进入bin目录 ./zkServer.sh stop

查看节点：
[cheng@localhost bin]$ ./zkCli.sh
[zk: localhost:2181(CONNECTED) 0] ls /
[zookeeper]


1. 启动ZK服务:       sh bin/zkServer.sh start
2. 查看ZK服务状态:   sh bin/zkServer.sh status
3. 停止ZK服务:       sh bin/zkServer.sh stop
4. 重启ZK服务:       sh bin/zkServer.sh restart


使用delete命令可以删除指定znode. 当该znode拥有子znode时, 必须先删除其所有子znode, 否则操作将失败.
rmr命令可用于代替delete命令, rmr是一个递归删除命令, 如果发生指定节点拥有子节点时, rmr命令会首先删除子节点.

---
整合了dubbo既是生产又是消费者--dubbo-provider-consumer 测试--test04

是完全可以跑通的 消费者test01--test04是完全可以跑通的

说明待整理！！!



安裝
mvn install:install-file -Dfile=dubbo-2.7.2-SNAPSHOT.jar -DgroupId=org.apache.dubbo -DartifactId=dubbo -Dversion=2.7.2-SNAPSHOT -Dpackaging=jar

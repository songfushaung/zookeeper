# zookeeper
zookeeper实现分布式

1.zookeeper是:协调分布式应用的基本服务，它向外部应用暴露一组通用服务 分布式协调工具。

2.应用于:注册中心 发布订阅 负载均衡 分布式锁等

3.数据结构:树桩存储结构 依赖于节点存储(节点名称 节点值/内容) 节点名称不能重复

4.zookeeper节点类型:持久节点(存在硬盘上),临时节点(断开连接后就没了) 持久顺序节点 临时顺序节点

5.节点的watch:当节点改变时会产生事件

6.分布式锁:使用临时节点
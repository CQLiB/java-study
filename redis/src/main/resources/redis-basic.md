#            Redis 基础

##Redis简介
    Redis 是完全开源免费的，遵守BSD协议，是一个高性能的key-value数据库。
    
    Redis 与其他 key - value 缓存产品有以下三个特点：
        Redis支持数据的持久化，可以将内存中的数据保存在磁盘中，重启的时候可以再次加载进行使用。
        
        Redis不仅仅支持简单的key-value类型的数据，同时还提供list，set，zset，hash等数据结构的存储。
        
        Redis支持数据的备份，即master-slave模式的数据备份。
-----------------------
##Redis优点
    1、性能极高 – Redis能读的速度是110000次/s,写的速度是81000次/s 。
    
    2、丰富的数据类型 – Redis支持二进制案例的 Strings, Lists, Hashes, Sets 及 Ordered Sets 数据类型操作。
    
    3、原子 – Redis的所有操作都是原子性的，意思就是要么成功执行要么失败完全不执行。单个操作是原子性的。多个操作也支持事务，
        即原子性，通过MULTI和EXEC指令包起来。
    
    4、丰富的特性 – Redis还支持 publish/subscribe, 通知, key 过期等等特性。
---------------------------
##Redis与其他key-value存储有什么不同
    
    1、Redis有着更为复杂的数据结构并且提供对他们的原子性操作，这是一个不同于其他数据库的进化路径。Redis的数据类型都是基于基本数据结
        构的同时对程序员透明，无需进行额外的抽象。
    
    2、Redis运行在内存中但是可以持久化到磁盘，所以在对不同数据集进行高速读写时需要权衡内存，因为数据量不能大于硬件内存。在内存数据库
        方面的另一个优点是，相比在磁盘上相同的复杂的数据结构，在内存中操作起来非常简单，这样Redis可以做很多内部复杂性很强的事情。
        同时，在磁盘格式方面他们是紧凑的以追加的方式产生的，因为他们并不需要进行随机访问。
------------------------
##Redis数据库支持的数据类型
    Redis支持有丰富的数据类型，不仅仅支持String，还支持包括List(数组)，Set（集合），SortSet（有序集合）和Hash（对存储对象有意想不到的效果）。

###Redis HyperLogLog
    Redis HyperLogLog是什么？
    
        Redis在2.8.9版本添加了HyperLogLog结构。
        
        Redis HyperLogLog是用来做基数统计的算法。HyperLogLog 的优点是，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定 的、并且是很小的。
        
        在 Redis 里面，每个 HyperLogLog 键只需要花费 12 KB 内存，就可以计算接近 2^64 个不同元素的基 数。这和计算基数时，元素越多耗费内存就越多的集合形成鲜明对比。
        
        但是，因为 HyperLogLog 只会根据输入元素来计算基数，而不会储存输入元素本身，所以 HyperLogLog 不能像集合那样，返回输入的各个元素。
    
    什么是基数？
    
        比如数据集 {1, 3, 5, 7, 5, 7, 8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。 基数估计就是在误差可接受的范围内，快速计算基数。

---------------------------------

##Redis 发布与订阅
    Redis 发布订阅(pub/sub)是一种消息通信模式：发送者(pub)发送消息，订阅者(sub)接收消息。
    
    Redis 客户端可以订阅任意数量的频道。
--------------------------
**下图展示了channel，以及订阅该channel的三个客户端**
![](redis-publish-subscribe.png "redis-channel-client")

**下图通过命令publish发送消息给channel**
![](redis-publish-subscribe-message.png)




##Redis三种集群模式

###Redis主从复制
    
####为什么要使用Redis的主从复制？
    Redis优点就是读写速度快以及支持丰富的数据类型等，但是也避免不了读写压力过大以及发生故障时怎么解救数据不丢失等问题！Redis的主从
    复制就很好解救了以上的问题。







###Redis哨兵模式
####Redis哨兵的任务

    有了主从复制的实现以后，如果想对主服务器进行监控，那么在redis2.6以后提供了一个"哨兵"的机制。顾名思义，哨兵的含义就是监控redis
       系统的运行状态。可以启动多个哨兵，去监控redis数据库的运行状态。其主要功能有两点:
       1、监控所有节点数据库是否在正常运行。
       2、master数据库出现故障时，可以自动通过投票机制，从slave节点中选举新的master，实现将从数据库转换为主数据库的自动切换。
    
    Redis的哨兵(sentinel) 系统用于管理多个 Redis 服务器,该系统执行以下三个任务:
       1、监控(Monitoring): 哨兵(sentinel) 会不断地检查你的Master和Slave是否运作正常。
       2、提醒(Notification):当被监控的某个 Redis出现问题时, 哨兵(sentinel) 可以通过 API 向管理员或者其他应用程序发送通知。
       3、自动故障迁移(Automatic failover):当一个Master不能正常工作时，哨兵(sentinel) 会开始一次自动故障迁移操作,它会将失效
            Master的其中一个Slave升级为新的Master, 并让失效Master的其他Slave改为复制新的Master; 当客户端试图连接失效的Master时,
            集群也会向客户端返回新Master的地址,使得集群可以使用Master代替失效Master。

-----------------------------
####Redis哨兵的工作原理
    
    哨兵(sentinel) 是一个分布式系统,你可以在一个架构中运行多个哨兵(sentinel) 进程,这些进程使用流言协议(gossipprotocols)来接收关
        于Master是否下线的信息,并使用投票协议(agreement protocols)来决定是否执行自动故障迁移,以及选择哪个Slave作为新的Master.
    
    每个哨兵(sentinel) 会向其它哨兵(sentinel)、master、slave定时发送消息,以确认对方是否”活”着,如果发现对方在指定时间(可配置)内
        未回应,则暂时认为对方已挂(所谓的”主观认为宕机” Subjective Down,简称sdown).若“哨兵群”中的多数sentinel,都报告某一master
        没响应,系统才认为该master"彻底死亡"(即:客观上的真正down机,Objective Down,简称odown),通过一定的vote算法,从剩下的slave
        节点中,选一台提升为master,然后自动修改相关配置.
    
    虽然哨兵(sentinel) 初始为一个单独的可执行文件 redis-sentinel ,但实际上它只是一个运行在特殊模式下的 Redis 服务器，你可以在
        启动一个普通 Redis 服务器时通过给定 --sentinel 选项来启动哨兵(sentinel).
    
    哨兵(sentinel) 的一些设计思路和zookeeper非常类似
     
    1、监控
        sentinel会每秒一次的频率与之前创建了命令连接的实例发送PING，包括主服务器、从服务器和sentinel实例，以此来判断当前实例的状态。
            down-after-milliseconds时间内PING连接无效，则将该实例视为主观下线。之后该sentinel会向其他监控同一主服务器的sentinel
            实例询问是否也将该服务器视为主观下线状态，当超过某quorum后将其视为客观下线状态。
        
        当一个主服务器被某sentinel视为客观下线状态后，该sentinel会与其他sentinel协商选出零头sentinel进行故障转移工作。每个发现主
            服务器进入客观下线的sentinel都可以要求其他sentinel选自己为领头sentinel，选举是先到先得。同时每个sentinel每次选举都会
            自增配置纪元，每个纪元中只会选择一个领头sentinel。如果所有超过一半的sentinel选举某sentinel领头sentinel。之后该
            sentinel进行故障转移操作。
        
        如果一个Sentinel为了指定的主服务器故障转移而投票给另一个Sentinel，将会等待一段时间后试图再次故障转移这台主服务器。如果该
            次失败另一个将尝试，Redis Sentinel保证第一个活性(liveness)属性，如果大多数Sentinel能够对话，如果主服务器下线，最后
            只会有一个被授权来故障转移。 同时Redis Sentinel也保证安全(safety)属性，每个Sentinel将会使用不同的配置纪元来故障转
            移同一台主服务器。
            
    2、故障迁移
         首先是从主服务器的从服务器中选出一个从服务器作为新的主服务器。选点的依据依次是：网络连接正常->5秒内回复过INFO命令->10*down-after-milliseconds内与主连接过的->从服务器优先级->复制偏移量->运行id较小的。选出之后通过slaveif no ont将该从服务器升为新主服务器。
        
         通过slaveof ip port命令让其他从服务器复制该信主服务器。
    
         最后当旧主重新连接后将其变为新主的从服务器。注意如果客户端与就主服务器分隔在一起，写入的数据在恢复后由于旧主会复制新主的数据会造成数据丢失。
    
         故障转移成功后会通过发布订阅连接广播新的配置信息，其他sentinel收到后依据配置纪元更大来更新主服务器信息。Sentinel保证第二个活性属性：一个可以相互通信的Sentinel集合会统一到一个拥有更高版本号的相同配置上。       
         
         
   
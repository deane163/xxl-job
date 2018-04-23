
XXL-JOB是一个轻量级分布式任务调度框架，其核心设计目标是开发迅速、学习简单、轻量级、易扩展。现已开放源代码并接入多家公司线上产品线，开箱即用。


## Documentation
- [中文文档](http://www.xuxueli.com/xxl-job/)
- [English Documentation](http://www.xuxueli.com/xxl-job/en/)

## 二次开发内容
- 1、数据库连接池修改为druid
- 2、添加job的 Restful 接口服务，实现通过接口调用添加job

## Features
- 1、简单：支持通过Web页面对任务进行CRUD操作，操作简单，一分钟上手；
- 2、动态：支持动态修改任务状态、暂停/恢复任务，以及终止运行中任务，即时生效；
- 3、调度中心HA（中心式）：调度采用中心式设计，“调度中心”基于集群Quartz实现并支持集群部署，可保证调度中心HA；
- 4、执行器HA（分布式）：任务分布式执行，任务"执行器"支持集群部署，可保证任务执行HA；
- 5、注册中心: 执行器会周期性自动注册任务, 调度中心将会自动发现注册的任务并触发执行。同时，也支持手动录入执行器地址；
- 6、弹性扩容缩容：一旦有新执行器机器上线或者下线，下次调度时将会重新分配任务；
- 7、路由策略：执行器集群部署时提供丰富的路由策略，包括：第一个、最后一个、轮询、随机、一致性HASH、最不经常使用、最近最久未使用、故障转移、忙碌转移等；
- 8、故障转移：任务路由策略选择"故障转移"情况下，如果执行器集群中某一台机器故障，将会自动Failover切换到一台正常的执行器发送调度请求。
- 9、失败处理策略；调度失败时的处理策略，策略包括：失败告警（默认）、失败重试；
- 10、失败重试：调度中心调度失败且启用"失败重试"策略时，将会自动重试一次；执行器执行失败且回调失败重试状态时，也将会自动重试一次；
- 11、阻塞处理策略：调度过于密集执行器来不及处理时的处理策略，策略包括：单机串行（默认）、丢弃后续调度、覆盖之前调度；
- 12、分片广播任务：执行器集群部署时，任务路由策略选择"分片广播"情况下，一次任务调度将会广播触发集群中所有执行器执行一次任务，可根据分片参数开发分片任务；
- 13、动态分片：分片广播任务以执行器为维度进行分片，支持动态扩容执行器集群从而动态增加分片数量，协同进行业务处理；在进行大数据量业务操作时可显著提升任务处理能力和速度。
- 14、事件触发：除了"Cron方式"和"任务依赖方式"触发任务执行之外，支持基于事件的触发任务方式。调度中心提供触发任务单次执行的API服务，可根据业务事件灵活触发。
- 15、任务进度监控：支持实时监控任务进度；
- 16、Rolling实时日志：支持在线查看调度结果，并且支持以Rolling方式实时查看执行器输出的完整的执行日志；
- 17、GLUE：提供Web IDE，支持在线开发任务逻辑代码，动态发布，实时编译生效，省略部署上线的过程。支持30个版本的历史版本回溯。
- 18、脚本任务：支持以GLUE模式开发和运行脚本任务，包括Shell、Python、NodeJS等类型脚本;
- 19、任务依赖：支持配置子任务依赖，当父任务执行结束且执行成功后将会主动触发一次子任务的执行, 多个子任务用逗号分隔；
- 20、一致性：“调度中心”通过DB锁保证集群分布式调度的一致性, 一次任务调度只会触发一次执行；
- 21、自定义任务参数：支持在线配置调度任务入参，即时生效；
- 22、调度线程池：调度系统多线程触发调度运行，确保调度精确执行，不被堵塞；
- 23、数据加密：调度中心和执行器之间的通讯进行数据加密，提升调度信息安全性；
- 24、邮件报警：任务失败时支持邮件报警，支持配置多邮件地址群发报警邮件；
- 25、推送maven中央仓库: 将会把最新稳定版推送到maven中央仓库, 方便用户接入和使用;
- 26、运行报表：支持实时查看运行数据，如任务数量、调度次数、执行器数量等；以及调度报表，如调度日期分布图，调度成功分布图等；

## 添加接口实现job的操作

- 1、添加jobInfo   HTTP POST   
&ensp;&ensp;http://127.0.0.1:8080/xxl-job-admin/api/add   
&ensp;&ensp;&ensp;&ensp;{   
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"alarmEmail": "deane163@126.com",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"author": "deane163",   
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorBlockStrategy": "SERIAL_EXECUTION",   
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorFailStrategy": "FAIL_ALARM",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorHandler": "demoJobHandler",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorParam": "123",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorRouteStrategy": "FIRST",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"glueRemark": "GLUE代码初始化",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"glueType": "BEAN",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobCron": "0 */1 * * * ?",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobDesc": "我的描述",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobGroup": 1    
&ensp;&ensp;&ensp;&ensp;}
 
- 2、修改jobInfo   HTTP POST    
&ensp;&ensp;http://127.0.0.1:8080/xxl-job-admin/api/reschedule    
&ensp;&ensp;&ensp;&ensp;{     
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"id": 7,     
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"alarmEmail": "deane163@126.com",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"author": "deane163",   
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorBlockStrategy": "SERIAL_EXECUTION",   
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorFailStrategy": "FAIL_ALARM",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorHandler": "demoJobHandler",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorParam": "123",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"executorRouteStrategy": "FIRST",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"glueRemark": "GLUE代码初始化",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"glueType": "BEAN",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobCron": "0 */1 * * * ?",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobDesc": "我的描述",    
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobGroup": 1    
&ensp;&ensp;&ensp;&ensp;} 
    
- 3、暂停jobInfo   HTTP POST   其中 id = 7 为参数

    http://127.0.0.1:8080/xxl-job-admin/api/pause?id=7   
    
- 4、继续jobInfo   HTTP POST   其中 id = 7 为参数

    http://127.0.0.1:8080/xxl-job-admin/api/resume?id=7
    
- 5、删除jobInfo   HTTP DELETE   其中 id = 7 为参数

    http://127.0.0.1:8080/xxl-job-admin/api/remove?id=7   
    
- 6、执行jobInfo   HTTP POST   其中 id = 7 为参数

    http://127.0.0.1:8080/xxl-job-admin/api/trigger?id=7   
    
- 7、 通过传递参数  jobDes，删除jobInfo   HTTP DELETE   其中 '10005590_描述' 为参数

    http://127.0.0.1:8080/xxl-job-admin/api/removeByJobDesc?jobDesc=10005590_描述  
    
- 8、 通过传递参数  ，批量删除jobInfo    HTTP DELETE  
http://127.0.0.1:8080/xxl-job-admin/api/batchRemoveByJobDesc  
参数信息如下：(application/json;chartset=UTF-8); json 数组信息如下：     
&ensp;&ensp;&ensp;&ensp;[  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;{  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobDesc": "10005590_我的描述22"  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;},  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;{  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobDesc": "10005590_我的描述11"  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;},  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;{  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;"jobDesc": "10005590_我的描述33"  
&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;}  
&ensp;&ensp;&ensp;&ensp;]  

    
##  集群部署（可选）

- 1、 中央控制节点可以通过集群的方式部署。

- 2、 执行器节点同时可以以集群的方式部署，添加任务的时候，可以通过相应的策略进行添加。

- 3、 中央控制节点根据用户指定的策略，调用对应的执行器信息。

- 4、 中央控制节点可以根据不同的 控制测试，调用执行器 （第一个、最后一个、轮询、随机、一致性HASH、故障转移、最不经常使用、最近最久未使用、忙碌转移、分片广播）


##  接口调用鉴权（API接口调用鉴权）

- 1、调用API接口的时候，需要进行鉴权处理。

- 2、在调用API接口的时候，需要在Header头部添加Token，进行鉴权使用。

##  优势：

- 1、调度器和执行分离，解决了耦合性问题

- 2、调度器和执行器都可以通过集群的方式，进行部署

- 3、通过动态的节点扩展，提高系统的可靠性

##  缺点：

- 1、提高了部署维护的复杂度

- 2、提高了运维人员的维护难度

--3、出问题时，不是太容易查找问题

--4、日志量大，需要花费大量时间分析

##  改进建议：

- 1、Jetty + Hassion 建议使用 Dubbo或 motron这些成熟的框架，没有必要自己实现一套RPC框架。



1.【调度中心】数据中心就是一些表：

XXL_JOB_QRTZ_TRIGGER_GROUP, JOB所属的分组，一般一个应用配置一个group，然后每个group有多个address(一个应用部署在多台服务器上)

XXL_JOB_QRTZ_TRIGGER_INFO，定时任务配置，每条配置会有如下属性：

cron表达式

handler, 即这个Job要出发group里的哪个方法.(一个应用为一个group，一个应用可以有多个接口，或者说多个方法)

group Id, 即这个job是用来触发哪个group的

其他信息，ha策略、路由策略等

XXL_JOB_QRTZ_TRIGGER_LOG, 任务执行情况的日志

XXL_JOB_QRTZ_TRIGGER_REGISTRY, 保存注册服务收集的应用address, 最后会有线程收集并更新到XXL_JOB_QRTZ_TRIGGER_GROUP的addressList中去

2.【调度中心】调度器

用的是集群Quartz

3.【调度中心】Rolling日志

就是普通的rolling日志，没啥好说的。

4.【调度中心】 回调服务

回调接口，任务执行结束后，执行器通过该接口告知执行结果，调度中心根据结果记录一下日志。

5.【调度中心】 注册服务

提供一个接口，在应用启动的时候来注册一下，告知一下自己的ip地址和端口。还有一种方式是通过管理平台手动添加应用的名称和地址。

6.【执行器】执行器服务

暴露的一个RPC服务，负责接收调度中心的请求，然后把封装成任务丢到队列里

7.【执行器】JobHandler

定义的任务处理接口

8.【执行器】任务线程

负责从队列里提取任务，执行，并把执行结果通过回调的方式传给调度中心，这种方式的好处是通过异步的方式处理任务，不影响任务的执行。

9.【执行器】注册线程

执行器启动的时候，会通过调度中心的注册接口，将自己的name和address注册到调度中心。

1、如果我来设计这个项目的话，我会把RPC这部分改成使用Motan或Dubbo之类的，尽量弄成可扩展的，可配置的（将RPC定义成接口，实现通过Dubbo或者Motan来实现）
这样做的好处是：不需要 【调度中心】 注册服务 和 【执行器】注册线程了。 调度中心只需要维护一个服务引用即可，其他事情 RPC框架帮你做了。
而且，也不用自己去实现route策略了，RPC框架一般都自带route实现且可配置的。

2、本来我觉得没必要搞个执行器，直接调度中心 call 应用系统的接口就可以了。
但这样有个问题，应用系统的接口不统一，调度中心需要依赖各个接口的JAR包，这显然不合理。
所以执行器(我更倾向于叫它调度中心提供的client包)还是有必要的，主要负责以下工作：

定义一个统一的接口，在应用启动的时候，以服务的方法发布出去。这个接口是job的统一入口
在应用启动的时候，负责收集应用所有的job 入口，并将他们管理起来。

（可以是xml配置，也可以是annotation的方式来标注方法），通过Spring的一些hook接口来做
执行队列，采用 Disruptor. 将受到的调度请求，经过解析处理，封装成任务，丢给该队列。
然后从该队列中拿出来后，找到对应的方法，去执行。回调，我倾向于用MQ的方式，应用系统执行完任务后，发个MQ出来。

多层任务分发没有实现。

目标：
1)  RPC调用，可以使用Netty实现，实现数据的RPC的调用操作。

2)  消息系统可以使用RocketMQ，实现消息的传输，实现数据的回调操作，将执行结果数据放入到消息系统中。

3)  Spring 集成 Quartz，进行任务的处理



# 端云共享AI智能服务系统
+ 本系统可同时管理AI智能服务及硬件设备，可以通过本系统获取硬件数据，也可以通过本系统调用AI智能服务。
#### `硬件管理`：
+ 硬件设备使用mqtt协议向emqx服务器发送注册请求，本系统通过`MessageCenter`获取硬件信息，
同时通过`RegisterCenter`把硬件信息注册到本系统中。
+ 用户可以通过系统请求到硬件可获取数据例如 温度、湿度、光强度、摄像头、声音等硬件捕获的信息。
+ 用户可以直接利用硬件的数据并调用AI人工智能服务，达到硬件获取信息云端计算进行预测。例如：1. 硬件获取温度、湿度信息预测下一时刻的环境温度湿度
、2.硬件通过摄像头获取图片信息，进行人脸识别、行人识别等。
#### `AI服务管理`：
+ 系统可以对AI服务进行管理，可以动态的增加系统的AI服务，每个AI服务作为一个单独的微服务，并使用统一的输入参数，达到动态增加AI服务的效果。
+ 用户可以动态配置AI服务的调用链路，达到AI聚合服务的效果。例如：有两个AI服务一个是文字对话生成，一个是文字生成人声；用户可以通过把文字对话生成
AI服务作为第一个调用服务，文字生成人声AI服务作为第二个调用服务，生成一个聚合服务；当用户调用聚合服务的时候，会直接生成人声返回。
+ AI服务的数据源可以来自用户的输入，也可以选择适合的硬件数据。
## 系统结构
### 一、技术栈
#### 1.SpringBoot
+ 每个微服务都是一个SpringBoot项目。
#### 2.SpringCloud
+ 基于SpringCloud实现微服务。
#### 3.Nacos
+ `注册中心`：微服务向注册中心注册服务
+ `配置中心`：微服务向注册中心拉取配置文件，同时配置文件动态修改可以推送给微服务
#### 4.Sentinel
+ 服务熔断作用，同样是使用Nacos`配置中心`编写各个微服务的熔断规则。
#### 5.Docker
+ 使用Docker容器创建本项目所需的全部环境。
#### 6.Emqx
+ 物联网常用的消息队列服务器，本项目中用于微服务与硬件进行通信
#### 7.数据库相关
+ `Mysql`：主流的持久化数据库
+ `Redis`：用于缓存的数据库
+ `Mybatis-Plus`：方便Java访问数据库


## 启动项目

### 一、准备工作

#### 1. 准备镜像文件
+ 进入 `./docker/dockerfiles/sentinel` 中打开CMD，然后执行 `docker build -t sentinel:latest . ` 命令生成sentinel的镜像。
#### 2. 容器初始化（启动docker-compose.yaml后对容器进行操作）
+ 进入 `./docker/file` 文件夹，其中`sql`文件夹是`mysql容器`需要的`数据库sql文件`
，请创建一个名为`hardwarecenter`的数据库 ，并使用`./docker/file/sql/hardwarecenter.sql`文件生成必要的数据。
+ 进入 `./docker/file` 文件夹，其中`nacos`文件夹是`nacos容器`需要的`nacos配置中心文件`
，请打开浏览器进入`localhost:8848/nacos/index.html`在`命名空间`中新建命名空间为dev并指明`命名空间ID为 45c96965-c8f8-4c18-a721-7fee5be0dbec`
，然后在`配置列表`中选择dev并点击导入配置选择 `./docker/file/nacos/nacos_config_export.zip` 导入配置。

### 二、启动
#### 1.启动容器
+ 启动`./docker/docker-compose.yaml`文件共启动5个容器`nacos`、`mysql`、`redis`、`sentinel`、`emqx`。
#### 2.启动服务
+ 其中`GateWay`、`RegisterCenter`为必须启动的服务，当需要使用到与硬件通信时需要启动`MessageCenter`服务
，当需要使用天气预测服务时需启动`WeatherServer`服务,当需要使用GPT服务时需启动`GPTServer`
，当需要使用上传业务时需要启动`UploadCenter`服务
#### 3.GPT服务说明
+ GPT服务需要使用`科学上网`,目前代理端口必须是`7890` 可以在`GPTServer`中`com.parsley.service.Imp.GPT3ServiceImp`
类的`requestGPT3(String content)`方法中修改端口号 `写死了端口号和api_key不想改了`。
#### 4.UploadCenter说明
+ 目前没有关于图像、视频、语音的AI服务所以现在上传文件都会上传至`./uploadFiles`中可以在`nacos配置中心`修改`UploadCenter`对应配置文件
`UploadCenter-dev.yaml`修改上传位置
#### 5.WeatherServers说明
+ 自己训练的一个天气预测AI服务，准确度不高，因为使用python写的所以这里采用的是Socket通信与本地的python程序进行通信，
也就是说这个服务的运行还要依赖于一个python程序。
+ 你可以使用在线的AI服务，参照`GPTServer`修改`WeatherServer`服务就可以了。

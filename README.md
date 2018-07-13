# temp-file-viewer

## 简介

一般工作中会有我想把某个文件分享给别人,但是又不想直接发给对方,因此需要一个中转站,我把文件上传到这个中转站,然后中转站给我一个可以查看的url,我再把url发给要查看的人.
这个项目就是为了满足这个需求而诞生的.

目前功能很简单,开发思路是先支持markdown,提升体验,修复bug等等,后续再加入更多的文件支持.

## 功能

1. markdown √
2. json,xml,java,c,cpp,php,python等 √

## 使用

```bash

docker pull push ifreehub/temp-file-viewer:1.0

docker run -d -p 8081:8081 -e APP_OPTS="-Dspring.profiles.active=prod"   docker.io/ifreehub/temp-file-viewer:1.0

```

访问 ip:8081 即可.


**配置**

使用环境变量配置参数,可以使用`-Dspring.config.location`复写应用的配置.

```bash
# 应用参数
APP_OPTS="-Dspring.config.location=file:/application.properties -Dspring.profiles.active=prod"  

# 虚拟机参数
JVM_OPTS="-server -server -Xms256m -Xmx256m"
```


## 技术

1. Spring Boot
2. Angular6

## 开发

[如何运行项目?](doc/run.md)

## 示意图

**upload**

![](http://oobu4m7ko.bkt.clouddn.com/1529721579.png?imageMogr2/thumbnail/!100p)

**display**
![](http://oobu4m7ko.bkt.clouddn.com/1529721623.png?imageMogr2/thumbnail/!100p)




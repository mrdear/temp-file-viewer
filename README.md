# temp-file-viewer

## 简介

一般工作中会有我想把某个文件分享给别人，但是又不想直接发给对方，因此需要一个中转站，我把文件上传到这个中转站，然后中转站给我一个可以查看的url，我再把url发给要查看的人。
或者是想把一个文件分享给别人但是由于操作系统不同或者使用的文件编辑器不同而导致展示上有一些差异。

那么这个项目的目的就是为了解决这种需求。目前工作中我会把临时的接口文档写好，然后生成一个url分享给前端或者客户端，并且我这边是可以随时更新。

目前功能很简单,开发思路是先支持markdown,提升体验,修复bug等等,后续再加入更多的文件支持.

## 功能

1. markdown √
2. json,xml,java,c,cpp,php,python等 √

## 使用

```bash

docker pull push ifreehub/temp-file-viewer:1.0

docker run -d -p 8081:8081 -e APP_OPTS="-Dspring.profiles.active=prod" docker.io/ifreehub/temp-file-viewer:1.0

```

访问 ip:8081 即可.


**配置**

使用环境变量配置参数,可以使用`-Dspring.config.location`复写应用的配置.

```bash
# 应用参数
APP_OPTS="-Dspring.config.location=file:/application.properties -Dspring.profiles.active=prod"  

# 虚拟机参数
JVM_OPTS="-server -Xms256m -Xmx256m"
```

**Spring boot配置说明**

```txt
server.port=8081

# 文件上传相关配置
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.file-size-threshold=1
spring.http.encoding.charset=utf-8
spring.http.encoding.force=true
spring.http.encoding.force-request=true
spring.http.encoding.force-response=true

# root账户配置，该配置作为该项目的管理员账户
root.username=quding
root.password=123456
root.avatar=http://oobu4m7ko.bkt.clouddn.com/avatar.png

# jwt签名锁需要的密钥，管理员登录使用的是jwt方式，因此需要配置个秘钥。
jwt.secret=123456

# 上传的文件会存在该配置的本地目录中
temp.file.dist=/quding/data/file
# 对外访问域名，cookie会设置到该域名下,不设置则默认是当前response,对于nginx代理的可能会出问题
temp.hostname=
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




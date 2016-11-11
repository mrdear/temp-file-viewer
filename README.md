# MarkdownViewTools

学习springboot时写的一个工具,因为我做接口的经常写文档,改文档,前端每次从svn更新下来很不方便,所以写了一个本地md文档读取展示工具,
前端访问我的ip就能看到相应的文档了


##如何使用?

1. 使用IDEA clone下来
2. 修改配置文件setting.properties

```
#markdown文件位置
md.mdpath[0] = E://公司/
md.mdpath[1] = /home/web_as/markdown/doc/

#最大获取目录数
md.maxCount=20

#上传临时目录
md.uploadTemppath=E://temp/

#GZIP过滤后缀
md.filter[0]=*.css
md.filter[1]=*.js
md.filter[2]=*.json
md.filter[3]=*.eot
md.filter[4]=*.svg
md.filter[5]=*.woff
md.filter[6]=*.woff2

#页面缓存url
md.cache[0]=/

#首页欢迎语
md.welecome=#欢迎使用Markdown View Tools
```

3. 使用maven打包为jar
4. 运行java -jar mrdear-1.0.0.jar

效果图如下:

![这里写图片描述](http://img.blog.csdn.net/20161111122634004)

![这里写图片描述](http://img.blog.csdn.net/20161111122713924)


未完成功能,即时通讯,目前公司加班严重,等有空加上


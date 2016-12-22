# MarkdownViewTools

学习springboot时写的一个工具,因为我做接口的经常写文档,改文档,前端每次从svn更新下来很不方便,所以写了一个本地md文档读取展示工具,
前端访问我的ip就能看到相应的文档了

##现有功能
1. 文档多标签展示
2. toc目录解析
3. 文档上传与下载
4. 文本比较工具

##待完成功能
1. 文档目录缓存,文章本身就在本地,所以不设缓存
2. word与PDF展示,最终想做一个全能的文档展示系统

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

总效果图:
![1.jpg](http://upload-images.jianshu.io/upload_images/2148449-e22805b5008ef94f.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

文本比较工具:
![2.jpg](http://upload-images.jianshu.io/upload_images/2148449-a2cf45fff3a3b7ce.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

文档上传
![3.jpg](http://upload-images.jianshu.io/upload_images/2148449-5c846c7315275eed.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


##更新日志

####2016.12.20
1. 前端页面使用layui重新设计
2. 增加多标签显示,很方便的功能

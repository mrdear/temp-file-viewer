# MarkdownViewTools

学习springboot时写的一个工具,因为我做接口的经常写文档,改文档,前端每次从svn更新下来很不方便,所以写了一个本地md文档读取展示工具,
前端访问我的ip就能看到相应的文档了


##如何使用?

1. 使用IDEA clone下来
2. 修改配置文件config.properties,修改文件夹为本地文件夹,最多支持20个文件夹,也就是md.path1  -- md.path20
3. 使用maven打包为jar
4. 运行java -jar mrdear-1.0.0.jar

效果图如下:

![这里写图片描述](http://img.blog.csdn.net/20160826105218810)


未完成功能,文档刷新和下载,还打算加上即时通讯,目前公司加班严重,等有空加上


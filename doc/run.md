
# 运行后端
该项目是标准的Maven项目，因此IDE导入`temp-file-viewer-backend`后，执行`FreehubFileViewerApplication`类的main方法即可。
另外本地开发时建议使用`-Dspring.profiles.active=dev`参数来指定环境。

成功启动后日志如下图，如果没有指定dev环境的话，日志会打到当前目录下的logs文件夹哈。
![](http://oobu4m7ko.bkt.clouddn.com/1531264678.png?imageMogr2/thumbnail/!100p)

## 运行前端
前端使用Angular框架，因此需要Node环境，没有的话请自行安装。

- Node环境
- Angular-Cli
    ```sh
    npm install -g @angular/cli
    ```
- 进入项目目录
- npm start / yarn start

运行成功的截图如下
![](http://oobu4m7ko.bkt.clouddn.com/1531264934.png?imageMogr2/thumbnail/!100p)


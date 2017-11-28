# svnotify

# 1 使用方法
## 1.1 下载
从[GutHub](https://github.com/yziyz/svnotify/releases/download/1.0/svnotify-1.0.zip)下载程序到本地并解压，假定文件夹路径
为"D:\svnotify"；

## 1.2 修改变量

修改`bin/svnotify`的`dingTalkUrl`和`jar文件的路径`，修改后的内容例如：
```
java -jar -DdingTalkUrl=https://oapi.dingtalk.com/robot/send?access_token=909d1b06a713fbc28c611c6b4db26eb317c9ed1sd3faf D:\svnotify\svnotify-1.0-jar.jar
```

## 1.2 设置IDEA Intellij
导航至"File > Settings > Tools > External Tools"，点击绿色加号，如图：

![](bin/image/1.png)

填写"Name"和"Program"

![](bin/image/2.png)

点击"OK"，工具已添加：

![](bin/image/3.png)

## 1.3 使用
提交代码时，在"After Commit"的"Run Tool"中选中我们刚添加的"svnotify"

![](bin/image/4.png)

提交成功后，可在工具控制台查看日志：

![](bin/image/5.png)

Enjoy!

# 2 参考文档

* https://stackoverflow.com/questions/4824590/propagate-all-arguments-in-a-bash-shell-script
* https://www.jetbrains.com/help/idea/commit-changes-dialog.html

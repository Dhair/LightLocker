建立Groovy注意的事项:
1、根据http://ju.outofmemory.cn/entry/220838 操作创建项目
2、需要注意的是：需要将groovy文件夹和resources做成可编译的文件
右键src/main/groovy -> Make Directory As -> Source Root
右键src/main/resources -> Make Directory AS -> Resources Root
3、src/main/resources/META-INF/gradle-plugins 是固定格式
com.dhair.hotfix.properties 是将Plugin 类名重新定义，方便
其他项目引用这个plugin时直接使用properties 的文件名
4、Hack 文件 为 package :cn.dhair.hotfix.Hack.java
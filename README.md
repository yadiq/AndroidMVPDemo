# AndroidMVP
一个简单的 Android MVP 项目，整合了Retrofit+RxJava网络模块。

## 框架特点

- **MVP结构**

    1. 表示层 <=> 业务层 => 数据层
    2. View <=> Presenter => Model

## Gradle Plugin 插件版本 与 所需的最低 Gradle 构建工具版本 对应关系
https://cloud.tencent.com/developer/article/2271801
Gradle Plugin 插件在 Android Studio 工程根目录下的 build.gradle 构建脚本中配置
Gradle 构建工具在工程根目录下的 gradle/wrapper/gradle-wrapper.properties 文件中配置
GradlePlugin插件  Gradle构建工具
7.3               7.4
7.2               7.3.3
7.1               7.2    #当前版本
7.0               7.0
4.2.0+            6.7.1
下面是旧版本
4.1.0+            6.5+
4.0.0+            6.1.1+
3.6.0 - 3.6.4     5.6.4+
3.5.0 - 3.5.4     5.4.1+
3.4.0 - 3.4.3     5.1.1+
3.3.0 - 3.3.3     4.10.1+

AndroidStudio版本 GradlePlugin插件
Giraffe | 2022.3.1     3.2-8.1
Flamingo | 2022.2.1    3.2-8.0
ElectricEel | 2022.1.1 3.2-7.4
Dolphin | 2021.3.1     3.2-7.3
Chipmunk | 2021.2.1    3.2-7.2
Bumblebee | 2021.1.1   3.2-7.1
Arctic Fox | 2020.3.1  3.1-7.0


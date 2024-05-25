# AndroidMVP
一个简单的 Android MVP 项目，整合了Retrofit+RxJava网络模块。

## 框架特点

- **MVP结构**

    1. 表示层 <=> 业务层 => 数据层
    2. View <=> Presenter => Model

- **流行框架**

    1. [retrofit](https://github.com/square/retrofit)+[okhttp](https://github.com/square/okhttp)+[rxJava](https://github.com/ReactiveX/RxJava)负责网络请求
    2. [gson](https://github.com/google/gson)负责解析json数据
    3. AndPermission 权限管理
    4. SmartRefreshLayout 下拉刷新

- **基类封装**

    1. BaseActivity
    2. BaseFragment
    3. BasePresenter

- **全局操作**

    1. 全局的Activity堆栈式管理
    2. 全局拦截网络请求日志 LogInterceptor
     
## 图标

1. mipmap文件夹只存放启动图标icon
2. Android手机屏幕标准                    对应图标尺寸标准      屏幕密度       比例
   xxxhdpi 3840*2160                         192*192             640          16
   xxhdpi 1920*1080                          144*144             480          12
   xhdpi  1280*720                            96*96              320           8

## 屏幕适配

1. 主要适配屏幕信息：1080x1920 px ,360x640 dp (对角线2202.91px)
2. density（dp密度，1dp上有多少个像素）=1080px / 360dp = 3 px/dp
3. densitydpi（屏幕像素密度，简称dpi，表示1英寸上对应有多少个像素）=160 * density= 480（因为第一款Android设备 160dpi)
	(屏幕尺寸=对角线像素数/densitydpi=4.59英寸)
4. 注意.xml文件预览仅支持部分densitydpi（例如：400 420 440 480等）

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
Koala | 2024.1.1       3.2-8.5
Jellyfish | 2023.3.1   3.2-8.4 稳定版本
Iguana | 2023.2.1	   3.2-8.3
Hedgehog | 2023.1.1	   3.2-8.2
Giraffe | 2022.3.1     3.2-8.1
Flamingo | 2022.2.1    3.2-8.0 当前版本
ElectricEel | 2022.1.1 3.2-7.4
Dolphin | 2021.3.1     3.2-7.3
Chipmunk | 2021.2.1    3.2-7.2
Bumblebee | 2021.1.1   3.2-7.1
Arctic Fox | 2020.3.1  3.1-7.0


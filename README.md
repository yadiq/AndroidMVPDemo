# AndroidMVPDemo
AndroidMVP的精简版本，快速开发框架

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

## AGP与gradle、JDK、AS等版本的对应关系
1. 概念 
+ AGP (Android Gradle Plugin)
在 Android Studio 工程根目录下的 build.gradle 构建脚本中配置
+ Gradle (构建工具) 
在工程根目录下的 gradle/wrapper/gradle-wrapper.properties 文件中配置

2. AGP与Gradle、JDK 的版本对应关系
+ AGP           最小Gradle  最小JDK
+ 8.4	            8.6	    17 #当前选择 AGP8.4.0 Gradle8.7 JDK17 Kotlin1.9.22
+ 8.3	            8.4   	17
+ 8.2            	8.2	    17
+ 8.1	            8.0	    17
+ 8.0.0	            8.0	    17
+ 7.4.0	            7.5	    11
+ 7.3.1             7.4     11 #当前选择 对应JDK17
+ 7.2               7.3.3   11
+ 7.1.2             7.2     11   
+ 7.0               7.0     11
+ 4.2.1             6.7.1    8 #之前选择 对应JDK11
+ 4.1.0             6.5+     8
+ 4.0.0             6.1.1+   8
+ 3.6.0 - 3.6.4     5.6.4+   8
+ 3.5.0 - 3.5.4     5.4.1+   8
+ 3.4.0 - 3.4.3     5.1.1+   8
+ 3.3.0 - 3.3.3     4.10.1+  7
[官方文档](https://developer.android.google.cn/build/releases/gradle-plugin?hl=zh-cn#updating-gradle)
[其它文档](https://blog.csdn.net/fxjzzyo/article/details/134390809)

3. AS与AGP版本关系
+ AndroidStudio          AGP
+ Koala | 2024.1.1       3.2-8.5
+ Jellyfish | 2023.3.1   3.2-8.4 稳定版本
+ Iguana | 2023.2.1	   3.2-8.3
+ Hedgehog | 2023.1.1	   3.2-8.2
+ Giraffe | 2022.3.1     3.2-8.1
+ Flamingo | 2022.2.1    3.2-8.0 当前版本
+ ElectricEel | 2022.1.1 3.2-7.4
+ Dolphin | 2021.3.1     3.2-7.3
+ Chipmunk | 2021.2.1    3.2-7.2
+ Bumblebee | 2021.1.1   3.2-7.1
+ Arctic Fox | 2020.3.1  3.1-7.0


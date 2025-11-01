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
5. 
## 当前版本选择(NDK27.0代码调试速度慢，所以降低版本)
AGP8.3.2 Gradle8.4 NDK25.2.9519653 JDK17
Kotlin2.1.20(K2编译器)
C++版本 C++14
CMake版本 3.22.1

详见 [AndroidNative配置说明](https://github.com/yadiq/AndroidNative)
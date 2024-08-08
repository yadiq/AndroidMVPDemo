package com.hqumath.demo.utils.safe;

import android.content.Context;

import com.hqumath.demo.app.AppExecutors;
import com.hqumath.demo.utils.LogUtil;

import java.util.concurrent.TimeUnit;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2024/8/8 16:17
 * 文件描述: 安全检测。
 * 注意事项:
 * ****************************************************************
 */
public class SafeCheckUtil {

    public static void safeCheck(Context context) {
        //延时做安全检测，提高app响应速度
        AppExecutors.getInstance().scheduledWork().schedule(() -> {
            //反模拟器、反root。耗时20ms
            boolean isEmulator = EmulatorCheckUtil.readSysProperty(context);
            boolean isRoot = RootUtil.root();
            if (isEmulator || isRoot) {
                LogUtil.d("反模拟器、反root");
                System.exit(0);
                return;
            }

            //反调试、反Xposed、反Frida。仅支持两种架构 arm64-v8a armeabi-v7a
            /*Anti.init(() -> {
                LogUtil.d("反调试、反Xposed、反Frida");
                System.exit(0);
            });*/
        }, 2, TimeUnit.SECONDS);
    }
}

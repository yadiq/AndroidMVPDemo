package com.hqumath.demo.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.MediaRouter;
import android.os.Build;
import android.util.Log;
import android.view.Display;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author liwanlian
 * @date 2024/11/6 11:14
 */
public class NormalUtils {
    private static final String TAG = "ToolUtils";

    public static void startNewApp(String pkg, String cls, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ActivityOptions options = ActivityOptions.makeBasic();
            MediaRouter mediaRouter =
                    (MediaRouter) context.getSystemService(Context.MEDIA_ROUTER_SERVICE);
            MediaRouter.RouteInfo route = mediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
            if (route != null) {
                Display presentaionDisplay = route.getPresentationDisplay();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        options.setLaunchDisplayId(presentaionDisplay.getDisplayId());
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //需要启动的应用
                        intent.setComponent(new ComponentName(pkg, cls));

                        context.startActivity(intent, options.toBundle());
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "启动失败：" + e.getMessage());
                    }
                }
            }
        }
    }


    public static void startNewAppSelfScreen(String pkg, Class<?> cls, Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 获取 DisplayManager 服务
            DisplayManager displayManager = (DisplayManager) context.getSystemService(Context.DISPLAY_SERVICE);
            // 获取默认显示设备（通常是内置屏幕）
            Display defaultDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY);
            // 创建 ActivityOptions 对象
            ActivityOptions options = ActivityOptions.makeBasic();
            // 设置启动的显示设备 ID 为默认显示设备的 ID
            options.setLaunchDisplayId(defaultDisplay.getDisplayId());

            try {
                // 创建一个新的 Intent 对象
//                Intent intent = new Intent();
                Intent intent = new Intent(context, cls);
                // 为 Intent 添加 FLAG_ACTIVITY_NEW_TASK 标志，确保在新任务中启动活动
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // 设置要启动的组件，即指定应用的包名和类名
//                intent.setComponent(new ComponentName(pkg, cls));
                // 使用上下文启动指定的活动，并传入 ActivityOptions 的 Bundle 对象
                context.startActivity(intent, options.toBundle());
                ((Activity)context).finish();
            } catch (Exception e) {
                // 打印异常堆栈信息
                e.printStackTrace();
                // 记录错误日志，显示启动失败的原因
                Log.e(TAG, "启动失败：" + e.getMessage());
            }
        } else {
            try {
                // 创建一个新的 Intent 对象
//                Intent intent = new Intent();
                Intent intent = new Intent(context, cls);
                // 为 Intent 添加 FLAG_ACTIVITY_NEW_TASK 标志，确保在新任务中启动活动
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // 设置要启动的组件，即指定应用的包名和类名
//                intent.setComponent(new ComponentName(pkg, cls));
                // 使用上下文启动指定的活动
                context.startActivity(intent);
            } catch (Exception e) {
                // 打印异常堆栈信息
                e.printStackTrace();
                // 记录错误日志，显示启动失败的原因
                Log.e(TAG, "启动失败：" + e.getMessage());
            }
        }
    }

    /**
     * 一体机启动双屏异显新方案（devType = 1/2）
     * 启动软件
     * @param cmd
     */
    public static void startNewApp(String cmd) {
        Process process = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            int aa = process.waitFor();
            is = new DataInputStream(process.getInputStream());
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            String out = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

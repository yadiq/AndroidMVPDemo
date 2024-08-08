package com.hqumath.demo.utils.safe;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;

/**
 * root检测
 * 是否Root RootUtil.root();
 */

public class RootUtil {
    private static final String TAG = "RootCheck";

    /**
     * 是否Root
     * @return
     */
    public static boolean root() {
        boolean root = false;

        StringBuilder sb = new StringBuilder();
        sb.append("check safe[root]: ").append("\n");
        String temp = null;
        if ((temp = buildTagValid()) != null) {
            root = true;
            sb.append("tag invalid: ").append(temp).append("\n");
        }
        if (!rootAppValid()) {
            root = true;
            sb.append("root app found").append("\n");
        }
        if ((temp = suValid()) != null) {
            root = true;
            sb.append("su invalid: ").append(temp).append("\n");
        }
        if ((temp = propertyValid()) != null) {
            root = true;
            sb.append("property invalid: ").append(temp).append("\n");
        }

        Log.i(TAG, sb.toString());
        return root;
    }

    private static @Nullable String buildTagValid() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return buildTags;
        }
        return null;
    }

    private static boolean rootAppValid() {
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    private static @Nullable String suValid() {
        File file = null;
        String[] paths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/"};
        try {
            for (String path : paths) {
                file = new File(path + "su");
                if (file.exists() && file.canExecute()) {
                    return file.getAbsolutePath();
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return null;
    }

    private void empty() {
        /*5. todo 检测系统挂载目录权限
        检测Android 沙盒目录文件或文件夹读取权限（在Android系统中，有些目录是普通用户不能访问的，例如 /data、/system、/etc 等；比如微信沙盒目录下的文件或文件夹权限是否正常）*/
    }

    private @Nullable static String propertyValid() {
        StringBuilder sb = new StringBuilder();
        String temp = null;
        if (!"0".equals(temp = CommandUtil.getProperty("ro.debuggable"))) {
            sb.append("ro.debuggable: ").append(temp).append(", ");
        }
        if (!"1".equals(temp = CommandUtil.getProperty("ro.secure"))) {
            sb.append("ro.secure: ").append(temp).append(", ");
        }
        String res = sb.toString();
        return (res.isEmpty() ? null : res);
    }
}

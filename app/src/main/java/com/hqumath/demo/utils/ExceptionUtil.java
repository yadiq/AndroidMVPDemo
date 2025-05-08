package com.hqumath.demo.utils;

import com.hqumath.demo.app.AppExecutors;
import com.hqumath.demo.bean.LogInfoEntity;
import com.hqumath.demo.repository.AppDatabase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/5/7 11:20
 * 文件描述: 异常信息上报
 * 注意事项:
 * ****************************************************************
 */
public class ExceptionUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * Exception转String
     * @param e
     * @return
     */
    public static String ExceptionToString(Exception e) {
        /*StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer, true));
        return writer.toString();*/
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

    /**
     * 记录主动捕获异常
     * @param tag
     * @param e
     */
    public static void logException(String tag, Exception e) {
        String message = ExceptionToString(e);
        insertLog(LogInfoEntity.Level.Exception, tag, message);
    }


    private static void insertLog(LogInfoEntity.Level level, String tag, String message) {
        AppExecutors.getInstance().workThread().execute(() -> {
            String deviceId = "";//androidId
            String deviceType = "";//一体机 pad
            String appVersion = CommonUtil.getVersion();
            String createTime = sdf.format(new Date());
            String eventId = "";//后端事件id
            LogInfoEntity info = new LogInfoEntity(deviceId, deviceType, appVersion, createTime,
                    level.name(), tag, message, eventId);
            //插入到数据库
            AppDatabase.getInstance().logInfoDao().insert(info);
        });
    }
}

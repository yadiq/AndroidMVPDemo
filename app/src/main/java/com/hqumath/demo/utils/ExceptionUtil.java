package com.hqumath.demo.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/5/7 11:20
 * 文件描述: 异常信息上报
 * 注意事项:
 * ****************************************************************
 */
public class ExceptionUtil {

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



}

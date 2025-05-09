package com.hqumath.demo.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2025/5/7 14:15
 * 文件描述: 日志信息表
 * 注意事项:
 * ****************************************************************
 */

/*
{
        "deviceId": "000002",
        "deviceType":"ihao",
        "appVersion":"1",
        "createTime":"2025-04-16 14:38:23",
        "level": "error",
        "tag": "1",
        "message": "throw error",
        "eventId":"21313",
    }
 */

@Entity(tableName = "log_info")
public class LogInfoEntity {
    @PrimaryKey(autoGenerate = true) //自动ID分配
    private int id;
    //@ColumnInfo(name = "first_name")
    private String deviceId;//设备id
    private String deviceType;//设备类型
    private String appVersion;//app版本号
    private String createTime;//发生时间
    private String level;//事件级别
    private String tag;//事件标签
    private String message;//事件内容
    private String eventId;

    public LogInfoEntity(String deviceId, String deviceType, String appVersion, String createTime, String level, String tag, String message, String eventId) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.appVersion = appVersion;
        this.createTime = createTime;
        this.level = level;
        this.tag = tag;
        this.message = message;
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    //事件级别
    public enum Level {
        Info, //业务埋点
        Exception, //主动捕获异常
        JavaCrash, //java崩溃
        NativeCrash, //native崩溃
        anrCrash, //anr崩溃
    }
}

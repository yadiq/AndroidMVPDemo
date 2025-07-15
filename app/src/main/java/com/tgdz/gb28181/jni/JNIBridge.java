package com.tgdz.gb28181.jni;

import android.content.Context;
import android.view.Surface;

public class JNIBridge {
    Context context;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("osipparser2");
        System.loadLibrary("osip2");
        System.loadLibrary("eXosip2");
        System.loadLibrary("sox");
        System.loadLibrary("gb_native");
        System.loadLibrary("yuv");
    }
    public void setContext(Context context){
        this.context = context;
    }
//    public static native void gbStart(int id, GBParam param);
//
//    public static native void gdwStart(GDWParam param);
//
//    public static native void meetingStart(MeetingParam param);

    public static native void audioHandle(int number, int type);

    public static native int initMuxer(
            int gpsInterval,
            String basePath,
            boolean talkOptimise,
            String baseUrl,
            int cameraUsecase,
            String env
    );

    public static native int sendOneVideoFrame(byte[] data,int len,long pts,int isKeyFrame,int cameraType);

    public static native int sendOneAudioFrame(byte[] data,int len,long pts,int cameraType);
    public static native int sendOneTalkAudioFrame(int cameraIndex,byte[] data,int len,long pts);

    public static native int sendOneMeetingAudioFrame(int cameraIndex,byte[] data,int len,long pts);

    public static native int sendOneVideoFrameB(byte[] data,int len,long pts,int isKeyFrame);

    //type 0订阅 1告警
    public static native void sendGps(double lat,double lon);
    //设置网路信息
    public static native void sendNetInfo(String locationMsg);

    //ffmpeg camera
    public static native void ffmpegCameraStart2(String url, Surface surface);
    public static native void ffmpegCameraStop2();



    //这里可以不是static，为了方便就这样写了
//    public native static void setCallback1(JniCallback callback);

//    public native static void rotateI420(byte[] src,byte[] dst,int width,int height,int rotation);

    public native static void endGbServer(int id);
    public native static void endGdwServer();
    public native static void endMeetingServer();

    public native static void pushAlarm(double longitude,double latidute,int priority,int method,String desc,int alarm_type);



//    public native static void setEnableInvite(boolean value);

//    public native static void setVideoUpload(int channel,boolean value);
//    public native static void setAudioUpload(int channel,boolean value);

    public native static void setVideoRecordPath(int id,int channel,String recordPathOfWrite,String recordPathOfRead);

//    public native static void setGbOrGdwParam(int chn,boolean isOpen,String devId,String devUsername,
//                                            String devPassword,String serverIp,int serverPort,String serverId,
//                                            String serverDomain,String cameraId,String cameraName,GBParam gbParam);

    public native static byte[] changeSpsFrameRate(byte[] originSps,int spsLen,int frameRate);

    // 集群对讲
    // 初始化对讲 - 登录前清空之前保存的用户数据
    public native static int groupTalkInit(String serverIp, int port, String username, String userPwd);

    // 进入会议
    public native static void groupAcceptMeeting(long meetingId);

    // 拒接会议邀请
    public native static void groupRefuseMeeting(long meetingId);

    // 退出会议
    public native static void groupExitMeeting();

    // 发送语音流
    public native static void groupSendData(int payload, long timestamp, byte[] data, int len, boolean isMark);

    // 创建会议
    public native static void groupCreate(String groupName, int number, String[] user_ids);

}

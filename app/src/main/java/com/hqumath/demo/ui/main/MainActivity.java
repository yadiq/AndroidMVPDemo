package com.hqumath.demo.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Bundle;
import android.util.Range;
import android.util.Size;
import android.view.View;

import com.hqumath.demo.base.BaseActivity;
import com.hqumath.demo.databinding.ActivityMainBinding;
import com.hqumath.demo.ui.repos.MyReposActivity;
import com.hqumath.demo.utils.LogUtil;

import java.util.Arrays;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2023/10/25 9:35
 * 文件描述: 主界面
 * 注意事项:
 * ****************************************************************
 */
public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected View initContentView(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void initListener() {
        binding.btnMyRepos.setOnClickListener(v -> {
            startActivity(new Intent(mContext, MyReposActivity.class));
        });
    }

    @Override
    protected void initData() {
//        getCameraInfo();
    }


    //检测设备是否有相机
//    private boolean checkCameraHardware(Context context) {
//        //查看设备上的可用摄像头数量
//        int num = Camera.getNumberOfCameras();//方法查看设备上的可用摄像头数量
//        LogUtil.d("可用摄像头数量 " + num);
//        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//            // this device has a camera
//            return true;
//        } else {
//            // no camera on this device
//            return false;
//        }
//    }

    //检索所有相机的列表
    private void getCameraInfo() {
        CameraManager cameraManager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList(); // may be empty

            // iterate over available camera devices
            for (String cameraId : cameraIdList) {
                StringBuilder info = new StringBuilder("cameraId:"+cameraId+"{\n");

                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
                int sensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                info.append("sensorOrientation:"+sensorOrientation+",\n");//传感器旋转角度,有的摄像头不是0度

                Range<Integer>[] ranges = characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
                //LogUtil.d("帧率范围: "+ Arrays.toString(ranges));//相机设备支持的帧率范围
                Long aLong = characteristics.get(CameraCharacteristics.SENSOR_INFO_MAX_FRAME_DURATION);
                //LogUtil.d("最大的帧间隔时间: "+aLong);//两帧之间的间隔，单位是纳秒 使用1秒除以这个参数可以计算出帧率

                int cameraLensFacing = characteristics.get(CameraCharacteristics.LENS_FACING);//获取摄像头类型 相机设备相对于屏幕的方向
                switch (cameraLensFacing) {
                    case CameraMetadata.LENS_FACING_FRONT:
                        info.append("lensFacing(前后摄):"+"front"+",\n");
                        break;
                    case CameraMetadata.LENS_FACING_BACK:
                        info.append("lensFacing(前后摄):"+"back"+",\n");
                        break;
                    case CameraMetadata.LENS_FACING_EXTERNAL:
                        info.append("lensFacing(前后摄):"+"external"+",\n");
                        break;
                }
                info.append("}\n\n");
                LogUtil.d("相机信息: "+ info);//相机设备支持的帧率范围

                //相机设备支持的可用流的配置，包括最小帧间隔、不同格式、大小组合的失帧时长
                Size[] supportedSizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.YUV_420_888);//支持的分辨率
                for (Size size : supportedSizes) {
                    int width = size.getWidth();
                    int height = size.getHeight();
                    LogUtil.d("分辨率: "+ width + "x" + height);//相机设备支持的帧率范围
                }

            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
            LogUtil.d(e.getMessage());
        }
    }
}

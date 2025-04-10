package com.hqumath.demo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hqumath.demo.base.BaseActivity;
import com.hqumath.demo.databinding.ActivityMainBinding;

import java.io.DataInputStream;
import java.io.DataOutputStream;

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
        binding.btn1.setOnClickListener(v -> {
            //主屏 云电脑
            startNewApp("am start --display 0 com.cmss.cloudcomputer.hd/com.cmss.cloudcomputer.module.login.activity.SplashActivity");
            //副屏 相机
            startNewApp("am start --display 1 com.hqumath.demo/com.hqumath.demo.ui.main.CameraActivity");
            //startNewApp("am start --display 1 com.vpclub.cloudperipheral/com.vpclub.cloudperipheral.ui.activitynew.DeviceInfoActivity");
        });

        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(mContext, CameraActivity.class));
        });
    }

    @Override
    protected void initData() {
    }

    /**
     * 一体机启动双屏异显新方案（devType = 1/2）
     * 启动软件
     * @param cmd
     */
    public static void startNewApp(String cmd) {
        java.lang.Process process = null;
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

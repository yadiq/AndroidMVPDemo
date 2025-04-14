package com.hqumath.demo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hqumath.demo.base.BaseActivity;
import com.hqumath.demo.databinding.ActivityMainBinding;
import com.hqumath.demo.utils.NormalUtils;

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
            NormalUtils.startNewApp("am start --display 0 com.cmss.cloudcomputer.hd/com.cmss.cloudcomputer.module.login.activity.SplashActivity");
            //副屏 相机
            NormalUtils.startNewApp("com.hqumath.demo", "com.hqumath.demo.ui.main.CameraActivity", mContext);
            //五合一一体机，不可用下方命令
            //NormalUtils.startNewApp("am start --display 1 com.hqumath.demo/com.hqumath.demo.ui.main.CameraActivity");
            //NormalUtils.startNewApp("am start --display 1 com.vpclub.cloudperipheral/com.vpclub.cloudperipheral.ui.activitynew.DeviceInfoActivity");
        });

        binding.btn2.setOnClickListener(v -> {
            startActivity(new Intent(mContext, CameraActivity.class));
        });

        binding.btn2.performClick();
    }

    @Override
    protected void initData() {
    }

}

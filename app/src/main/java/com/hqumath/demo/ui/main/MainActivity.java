package com.hqumath.demo.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.hqumath.demo.base.BaseActivity;
import com.hqumath.demo.databinding.ActivityMainBinding;
import com.hqumath.demo.service.UpdateService;
import com.hqumath.demo.ui.repos.MyReposActivity;

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
            //startActivity(new Intent(mContext, MyReposActivity.class));


            Intent service = new Intent(mContext, UpdateService.class);
            //startService(service);
            //启动一个前台服务 在api大于26才可使用startForegroundService此方法
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(service);//TODO 版本限制？
            }else{
                startService(service);
            }
        });
    }

    @Override
    protected void initData() {
    }
}

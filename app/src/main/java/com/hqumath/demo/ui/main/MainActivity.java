package com.hqumath.demo.ui.main;

import android.os.Bundle;
import android.view.View;

import com.hqumath.demo.base.BaseActivity;
import com.hqumath.demo.databinding.ActivityMainBinding;
import com.hqumath.demo.utils.ExceptionUtil;
import com.hqumath.demo.utils.LogUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import xcrash.TombstoneManager;
import xcrash.TombstoneParser;
import xcrash.XCrash;

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
    }

    @Override
    protected void initData() {
    }

    public void testAddInfo_onClick(View view) {
        ExceptionUtil.logInfo("业务埋点", "信令：xxxx");
    }

    public void testCatchException_onClick(View view) {
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionUtil.logException("主动捕获异常", e);
            //LogUtil.d(ExceptionUtil.ExceptionToString(e));
        }
    }

    public void testNativeCrashInMainThread_onClick(View view) {
        XCrash.testNativeCrash(false);
    }

    public void testJavaCrashInMainThread_onClick(View view) {
        XCrash.testJavaCrash(false);
    }

    public void testAnrInput_onClick(View view) {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
    }

    public void uploadFile_onClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (File file : TombstoneManager.getAllTombstones()) {
                    sendThenDeleteCrashLog(file.getAbsolutePath(), null);
                }
            }
        }).start();
    }

    private void sendThenDeleteCrashLog(String logPath, String emergency) {
        try {
            //转为json
            Map<String, String> map = TombstoneParser.parse(logPath, emergency);
            String crashReport = new JSONObject(map).toString();
            //发送到服务端
            //...
            LogUtil.d("字节数：" + crashReport.length());
            LogUtil.d(crashReport);
            //成功后删除
            TombstoneManager.deleteTombstone(logPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

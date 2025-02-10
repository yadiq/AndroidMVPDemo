package com.hqumath.demo.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ****************************************************************
 * 文件名称: BaseActivity
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected BaseActivity mContext;
    private ProgressDialog mProgressDialog;//loaidng

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(initContentView(savedInstanceState));
        //事件监听
        initListener();
        //初始化数据
        initData();
    }

    /*@Override
    protected void attachBaseContext(Context newBase) {
        //多语言设置
        Context context = MultiLanguageUtil.updateConfiguration(newBase);
        super.attachBaseContext(context);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //当资源配置发生更改时，例如横竖屏切换，需要重新设置多语言
        MultiLanguageUtil.updateConfiguration(this);
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    protected abstract View initContentView(Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initData();

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.setMessage("loading");
        }
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void dismissLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}

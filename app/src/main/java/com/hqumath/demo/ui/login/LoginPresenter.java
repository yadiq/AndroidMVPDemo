package com.hqumath.demo.ui.login;

import com.hqumath.demo.base.BasePresenter;
import com.hqumath.demo.net.HttpListener;
import com.hqumath.demo.repository.MyModel;

/**
 * ****************************************************************
 * 文件名称: LoginPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 业务逻辑层
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class LoginPresenter extends BasePresenter<LoginPresenter.LoginContract> {

    public interface LoginContract {
        void showLoading();

        void dismissLoading();

        void onLoginSuccess(Object object);

        void onLoginError(String errorMsg, String code);
    }
    
    public LoginPresenter() {
        mModel = new MyModel();
    }

    public void login(String userName, String passWord) {
        if (mView == null) return;
        mView.showLoading();
        //模拟登陆接口
        ((MyModel) mModel).login(userName, passWord, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                mView.dismissLoading();
                mView.onLoginSuccess(object);
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.dismissLoading();
                mView.onLoginError(errorMsg, code);
            }
        });
    }
}

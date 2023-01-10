package com.hqumath.demo.net;

import com.hqumath.demo.bean.UserInfoEntity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ****************************************************************
 * 文件名称: MainService
 * 作    者: Created by gyd
 * 创建时间: 2019/1/22 17:11
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public interface ApiService {

    //获取用户信息
    @GET("users/{userName}")
    Observable<UserInfoEntity> getUserInfo(@Path("userName") String userName);
}

package com.hqumath.demo.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hqumath.demo.bean.LogInfoEntity;

import java.util.List;

/**
 * ****************************************************************
 * 文件名称: LogInfoDao
 * 作    者: Created by gyd
 * 创建时间: 2019/7/4 11:35
 * 文件描述: 日志信息表操作
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
@Dao
public interface LogInfoDao {
    @Query("select * from log_info")
    List<LogInfoEntity> getAll();

    @Insert
    void insert(LogInfoEntity info);

//    @Insert
//    void insertAll(LogInfoEntity... info);

    @Delete
    void delete(LogInfoEntity info);

    @Delete
    void deleteAll(List<LogInfoEntity> list);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(List<LogInfoEntity> entity);

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateAll(List<UserInfoEntity> entity);

//    @Query("delete from user_info")
//    void deleteAll();

//    @Query("SELECT MAX(indexInResponse) + 1 FROM user_info")
//    int getNextIndex();
}

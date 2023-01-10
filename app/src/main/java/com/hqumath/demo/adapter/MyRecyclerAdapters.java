package com.hqumath.demo.adapter;

import android.content.Context;

import com.hqumath.demo.R;
import com.hqumath.demo.base.BaseRecyclerAdapter;
import com.hqumath.demo.base.BaseRecyclerViewHolder;
import com.hqumath.demo.bean.UserInfoEntity;

import java.util.List;

public class MyRecyclerAdapters {

    //我的仓库
    public static class ReposRecyclerAdapter extends BaseRecyclerAdapter<UserInfoEntity> {
        public ReposRecyclerAdapter(Context context, List<UserInfoEntity> mData) {
            super(context, mData, R.layout.recycler_item_repos);
        }

        @Override
        public void convert(BaseRecyclerViewHolder holder, int position) {
            UserInfoEntity data = mData.get(position);
            holder.setText(R.id.tv_name, data.getName());
        }
    }
}

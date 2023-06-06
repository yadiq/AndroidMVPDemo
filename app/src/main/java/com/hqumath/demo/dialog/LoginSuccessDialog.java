package com.hqumath.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.hqumath.demo.R;
import com.hqumath.demo.databinding.DialogLoginSuccessBinding;

/**
 * ****************************************************************
 * 作    者: Created by gyd
 * 创建时间: 2023/6/6 10:09
 * 文件描述:
 * 注意事项:
 * ****************************************************************
 */
public class LoginSuccessDialog extends Dialog {
    private DialogLoginSuccessBinding binding;
    private ClickCallBack clickCallBack;

    private Context mContext;

    public LoginSuccessDialog(Context context, ClickCallBack clickCallBack) {
        super(context, R.style.dialog_common);
        this.mContext = context;
        this.clickCallBack = clickCallBack;
        binding = DialogLoginSuccessBinding.inflate(LayoutInflater.from(context));
        setContentView(binding.getRoot());
        initListener();
        initData();
    }

    private void initListener() {
        binding.btnOneYes.setOnClickListener(v -> {
            dismiss();
            clickCallBack.onConfirm();
        });
    }

    private void initData() {
        binding.tvTitle.setText("登录");
        binding.tvMessage.setText("登录成功");
    }

    public interface ClickCallBack {
        void onConfirm();
    }
}
package com.jzd.android.jon.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * 背景透明 不能取消的加载进度弹窗
 * Created by Jzd on 2017/12/6.
 */

public class JLoadDialog extends DialogFragment
{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        setCancelable(false);
        ProgressBar progressBar = new ProgressBar(getContext());
        return new AlertDialog.Builder(getContext()).setView(progressBar)
                .create();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if(getDialog() != null)
        {
            Window window = getDialog().getWindow();
            if(window != null)
            {
                // 设置全屏
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams attributes = window.getAttributes();
                // 设置背景透明
                attributes.dimAmount = 0;
                window.setAttributes(attributes);
            }
        }
    }
}

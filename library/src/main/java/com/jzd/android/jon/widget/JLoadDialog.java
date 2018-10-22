package com.jzd.android.jon.widget;

import android.annotation.SuppressLint;
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

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 背景透明 不能取消的加载进度弹窗,默认在5后可取消，防止页面“卡死”
 * Created by Jzd on 2017/12/6.
 */

public class JLoadDialog extends DialogFragment
{
    private Disposable subscribe;
    private boolean autoCancelable = false;
    private int delay = 5;

    public JLoadDialog()
    {

    }

    @SuppressLint("ValidFragment")
    public JLoadDialog(boolean autoCancelable)
    {
        this.autoCancelable = autoCancelable;
    }

    @SuppressLint("ValidFragment")
    public JLoadDialog(boolean autoCancelable, int delay)
    {
        this.autoCancelable = autoCancelable;
        this.delay = delay;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        setCancelable(false);
        ProgressBar progressBar = new ProgressBar(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext()).setView(progressBar)
                .create();
        if(autoCancelable)
        {
            subscribe = Observable.timer(delay, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>()
                    {
                        @Override
                        public void accept(Long aLong)
                        {
                            setCancelable(true);
                        }
                    });
        }
        return dialog;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(subscribe != null && !subscribe.isDisposed())
        {
            subscribe.dispose();
            subscribe = null;
        }
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

package com.maning.mndialoglibrary.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.maning.mndialoglibrary.R;

/**
 * 抽取公用的FragmentDialog
 */
public abstract class BaseFragmentDialog extends DialogFragment {

    public FragmentActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Window相关
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
            //动画
            int animations = initAnimations();
            if (animations != 0) {
                try {
                    getDialog().getWindow().setWindowAnimations(animations);
                } catch (Exception e) {

                }
            }
        }
        setStyle(R.style.MNCustomDialog, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //隐藏title
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //点击外部不可取消
        getDialog().setCanceledOnTouchOutside(false);
        //拦截外部返回
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        //初始化其他可以覆盖上面
        initDialog();
        return initView(inflater);
    }


    protected abstract View initView(LayoutInflater inflater);

    public void initDialog() {

    }

    public int initAnimations(){
        return 0;
    }

    public float initBackgroundAlpha() {
        return 0.8f;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }

    public void showDialog(FragmentActivity mActivity) {
        try {
            if (isShowing()) {
                return;
            }
            if (mActivity != null && mActivity.getSupportFragmentManager() != null) {
                this.mActivity = mActivity;
                FragmentManager supportFragmentManager = mActivity.getSupportFragmentManager();
                show(supportFragmentManager, mActivity.getLocalClassName());
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    public boolean isShowing() {
        if (getDialog() != null && getDialog().isShowing()) {
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = initBackgroundAlpha();
            windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(windowParams);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity = null;
    }

}

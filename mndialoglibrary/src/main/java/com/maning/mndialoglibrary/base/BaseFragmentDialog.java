package com.maning.mndialoglibrary.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.maning.mndialoglibrary.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * author : maning
 * time   : 2018/03/07
 * desc   : 抽取公用的FragmentDialog
 * version: 1.0
 */
public abstract class BaseFragmentDialog extends DialogFragment {

    protected static FragmentActivity mActivity;
    private boolean isShowing = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Window相关
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(), android.R.color.transparent));
            //动画
            int animations = initAnimations();
            if (animations != 0) {
                getDialog().getWindow().setWindowAnimations(animations);
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

    protected abstract int initAnimations();

    public float initBackgroundAlpha() {
        return 0.6f;
    }

    public void showDialog(FragmentActivity mAct) {
        try {
            if (mAct == null) {
                return;
            }
            mActivity = mAct;
            if (isShowing()) {
                return;
            }
            if (mActivity != null && mActivity.getSupportFragmentManager() != null) {
                FragmentManager supportFragmentManager = mActivity.getSupportFragmentManager();
                //在每个add事务前增加一个remove事务，防止连续的add
                supportFragmentManager.beginTransaction().remove(this).commitAllowingStateLoss();
                show(supportFragmentManager, mActivity.getLocalClassName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        isShowing = true;
        try {
            Class c = Class.forName("android.support.v4.app.DialogFragment");
            Constructor con = c.getConstructor();
            Object obj = con.newInstance();
            Field dismissed = c.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(obj, false);
            Field shownByMe = c.getDeclaredField("mShownByMe");
            shownByMe.setAccessible(true);
            shownByMe.set(obj, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }


    public boolean isShowing() {
        if ((isShowing) || (getDialog() != null && getDialog().isShowing())) {
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
        isShowing = false;
        mActivity = null;
    }

    @Override
    public void dismiss() {
        isShowing = false;
        mActivity = null;
        dismissAllowingStateLoss();
    }

    @Override
    public void dismissAllowingStateLoss() {
        isShowing = false;
        mActivity = null;
        super.dismissAllowingStateLoss();
    }

}

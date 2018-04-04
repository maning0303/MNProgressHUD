package com.maning.mnprogressdialog;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.maning.mndialoglibrary.base.BaseFragmentDialog;

/**
 * <pre>
 *     author : maning
 *     e-mail : xxx@xx
 *     time   : 2018/04/04
 *     desc   : 测试
 *     version: 1.0
 * </pre>
 */
public class TestFragmentDialog extends BaseFragmentDialog {

    ImageView iv_close;

    /**
     * 布局初始化
     *
     * @param inflater
     * @return
     */
    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_test, null);

        iv_close = view.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭
                dismiss();
            }
        });

        return view;
    }

    public void showDialog(FragmentActivity fragmentActivity) {
        show(fragmentActivity.getSupportFragmentManager(), "");
    }

    /**
     * 动画
     *
     * @return
     */
    @Override
    protected int initAnimations() {
        return R.style.animate_dialog;
    }

    /**
     * Dialog初始化相关
     */
    @Override
    public void initDialog() {
        //点击外部不可取消,默认false
        getDialog().setCanceledOnTouchOutside(true);
    }

    /**
     * 背景透明度
     *
     * @return
     */
    @Override
    public float initBackgroundAlpha() {
        //默认0.8f
        return 0.8f;
    }
}

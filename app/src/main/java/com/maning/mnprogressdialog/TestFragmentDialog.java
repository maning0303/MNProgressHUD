package com.maning.mnprogressdialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.maning.mndialoglibrary.base.BaseFragmentDialog;

/**
 * 测试
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

    /**
     * 动画，此方法默认不实现
     *
     * @return
     */
    @Override
    public int initAnimations() {
        return R.style.animate_dialog;
    }

    /**
     * Dialog初始化相关，此方法默认不实现
     */
    @Override
    public void initDialog() {
        //点击外部不可取消,默认false
        getDialog().setCanceledOnTouchOutside(true);
    }

    /**
     * 背景透明度，此方法默认不实现
     *
     * @return
     */
    @Override
    public float initBackgroundAlpha() {
        //默认0.8f
        return 0.8f;
    }
}

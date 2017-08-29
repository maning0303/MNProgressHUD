package com.maning.mndialoglibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by maning on 2017/8/9.
 * 进度Dialog
 */

public class MProgressDialog implements View.OnClickListener {

    //常量
    private static final String defaultTextShow = "加载中...";

    private Dialog mDialog;
    private Context mContext;
    private Builder mBuilder;

    //布局
    private RelativeLayout dialog_window_background;
    private RelativeLayout dialog_view_bg;
    private MProgressWheel progress_wheel;
    private TextView tv_show;

    public MProgressDialog(Context context) {
        this(context, new Builder(context));
    }

    public MProgressDialog(Context context, Builder builder) {
        mContext = context;
        mBuilder = builder;
        //初始化
        initDialog();
    }

    private void initDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mProgressDialogView = inflater.inflate(R.layout.mn_progress_dialog_layout, null);// 得到加载view
        mDialog = new Dialog(mContext, R.style.MNCustomProgressDialog);// 创建自定义样式dialog
        mDialog.setCancelable(false);// 不可以用“返回键”取消
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(mProgressDialogView);// 设置布局

        //设置整个Dialog的宽高
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.width = screenW;
        layoutParams.height = screenH;
        mDialog.getWindow().setAttributes(layoutParams);


        //布局相关
        dialog_window_background = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_window_background);
        dialog_view_bg = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_view_bg);
        progress_wheel = (MProgressWheel) mProgressDialogView.findViewById(R.id.progress_wheel);
        tv_show = (TextView) mProgressDialogView.findViewById(R.id.tv_show);

        //点击事件
        dialog_window_background.setOnClickListener(this);

        //默认相关
        progress_wheel.stopSpinning();
        tv_show.setText(defaultTextShow);

        //设置默认配置
        configView();

    }

    private void configView() {
        mDialog.setCanceledOnTouchOutside(mBuilder.canceledOnTouchOutside);
        dialog_window_background.setBackgroundColor(mBuilder.backgroundWindowColor);

        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setColor(mBuilder.backgroundViewColor);
        myGrad.setStroke(dip2px(mContext, mBuilder.strokeWidth), mBuilder.strokeColor);
        myGrad.setCornerRadius(dip2px(mContext, mBuilder.cornerRadius));
        dialog_view_bg.setBackground(myGrad);

        progress_wheel.setBarColor(mBuilder.progressColor);
        progress_wheel.setBarWidth(dip2px(mContext, mBuilder.progressWidth));
        progress_wheel.setRimColor(mBuilder.progressRimColor);
        progress_wheel.setRimWidth(mBuilder.progressRimWidth);

        tv_show.setTextColor(mBuilder.textColor);
    }

    public void refreshBuilder(Builder builder) {
        mBuilder = builder;
        configView();
    }

    public void show() {
        dismiss();
        tv_show.setVisibility(View.VISIBLE);
        tv_show.setText(defaultTextShow);
        if (mDialog != null) {
            progress_wheel.spin();
            mDialog.show();
        }
    }

    public void showNoText() {
        dismiss();
        tv_show.setVisibility(View.GONE);
        if (mDialog != null) {
            progress_wheel.spin();
            mDialog.show();
        }
    }

    public void show(String msg) {
        dismiss();
        if (TextUtils.isEmpty(msg)) {
            msg = defaultTextShow;
        }
        tv_show.setVisibility(View.VISIBLE);
        tv_show.setText(msg);
        if (mDialog != null) {
            progress_wheel.spin();
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            progress_wheel.stopSpinning();
            mDialog.dismiss();
            if (mBuilder.dialogDismissListener != null) {
                mBuilder.dialogDismissListener.dismiss();
            }
        }
    }

    public void showWithProgress() {
        dismiss();
        progress_wheel.stopSpinning();
        progress_wheel.setLinearProgress(true);
        progress_wheel.setProgress(0.0f);
        tv_show.setVisibility(View.VISIBLE);
        tv_show.setText(defaultTextShow);
        if (mDialog != null) {
            mDialog.show();
        }
    }


    /**
     * dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.dialog_window_background) {
            //取消Dialog
            if (mBuilder.canceledOnTouchOutside) {
                dismiss();
            }
        }
    }

    public interface OnDialogDismissListener {
        void dismiss();
    }

    public void setOnDialogDismissListener(OnDialogDismissListener dialogDismissListener) {
        mBuilder.dialogDismissListener = dialogDismissListener;
    }


    //---------------------分割线----------------------带有进度条的Dialog

    public void setDialogProgress(float progress, String dialogText) {
        progress_wheel.setProgress(progress);
        tv_show.setText(dialogText);
    }


    //---------------------构建者模式--------------------
    public static final class Builder {

        private Context mContext;

        //点击外部可以取消
        boolean canceledOnTouchOutside;
        //窗体背景色
        int backgroundWindowColor;
        //View背景色
        int backgroundViewColor;
        //View边框的颜色
        int strokeColor;
        //View背景圆角
        float cornerRadius;
        //View边框的宽度
        float strokeWidth;
        //Progress的颜色
        int progressColor;
        //Progress的宽度
        float progressWidth;
        //progress背景环的颜色
        int progressRimColor;
        //progress背景环的宽度
        int progressRimWidth;
        //文字的颜色
        int textColor;
        //消失的监听
        OnDialogDismissListener dialogDismissListener;


        public Builder(Context context) {
            mContext = context;
            //默认参数
            canceledOnTouchOutside = false;
            backgroundWindowColor = mContext.getResources().getColor(R.color.mn_colorDialogWindowBg);
            backgroundViewColor = mContext.getResources().getColor(R.color.mn_colorDialogViewBg);
            strokeColor = mContext.getResources().getColor(R.color.mn_colorDialogTrans);
            cornerRadius = 6;
            strokeWidth = 0;
            progressColor = mContext.getResources().getColor(R.color.mn_colorDialogProgressBarColor);
            progressRimColor = mContext.getResources().getColor(R.color.mn_colorDialogTrans);
            progressWidth = 2;
            progressRimWidth = 0;
            textColor = mContext.getResources().getColor(R.color.mn_colorDialogTextColor);
            dialogDismissListener = null;
        }

        public MProgressDialog build() {
            return new MProgressDialog(mContext, this);
        }

        /**
         * 设置点击外部取消Dialog
         *
         * @param canceledOnTouchOutside
         * @return
         */
        public Builder isCanceledOnTouchOutside(@Nullable boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setBackgroundWindowColor(@Nullable int backgroundWindowColor) {
            this.backgroundWindowColor = backgroundWindowColor;
            return this;
        }

        public Builder setBackgroundViewColor(@Nullable int backgroundViewColor) {
            this.backgroundViewColor = backgroundViewColor;
            return this;
        }

        public Builder setStrokeColor(@Nullable int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public Builder setStrokeWidth(@Nullable float strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public Builder setCornerRadius(@Nullable float cornerRadius) {
            this.cornerRadius = cornerRadius;
            return this;
        }

        public Builder setProgressColor(@Nullable int progressColor) {
            this.progressColor = progressColor;
            return this;
        }

        public Builder setProgressWidth(@Nullable float progressWidth) {
            this.progressWidth = progressWidth;
            return this;
        }

        public Builder setProgressRimColor(int progressRimColor) {
            this.progressRimColor = progressRimColor;
            return this;
        }

        public Builder setProgressRimWidth(int progressRimWidth) {
            this.progressRimWidth = progressRimWidth;
            return this;
        }

        public Builder setTextColor(@Nullable int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setOnDialogDismissListener(OnDialogDismissListener dialogDismissListener) {
            this.dialogDismissListener = dialogDismissListener;
            return this;
        }

    }


}

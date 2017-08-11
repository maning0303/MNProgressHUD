package com.maning.mndialoglibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

    private Dialog mProgressDialog;
    private Context mContext;
    private OnDialogDismissListener mDialogDismissListener;

    private RelativeLayout dialog_window_background;
    private RelativeLayout dialog_view_bg;
    private MProgressWheel progress_wheel;
    private TextView tv_show;


    private boolean canceledOnTouchOutside = false;
    private static final String defaultTextShow = "加载中...";

    public MProgressDialog(Context context) {
        mContext = context;
        //初始化
        initDialog();
    }

    private void initDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mProgressDialogView = inflater.inflate(R.layout.mn_progress_dialog_layout, null);// 得到加载view
        mProgressDialog = new Dialog(mContext, R.style.MNCustomProgressDialog);// 创建自定义样式dialog
        mProgressDialog.setCancelable(false);// 不可以用“返回键”取消
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setContentView(mProgressDialogView);// 设置布局

        //设置整个Dialog的宽高
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;

        WindowManager.LayoutParams layoutParams = mProgressDialog.getWindow().getAttributes();
        layoutParams.width = screenW;
        layoutParams.height = screenH;
        mProgressDialog.getWindow().setAttributes(layoutParams);


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
        setCanceledOnTouchOutside(false);
        setBackgroundWindowColor(getColor(R.color.mn_colorDialogWindowBg));
        setBackgroundViewColor(getColor(R.color.mn_colorDialogViewBg));
        setBackgroundViewStrokeWidthAndColor(0.0f, mContext.getResources().getColor(R.color.mn_colorDialogTrans));
        setProgressColor(getColor(R.color.mn_colorDialogProgressBarColor));
        setDialogTextColor(getColor(R.color.mn_colorDialogTextColor));
        setProgressWidth(2);
        setBackgroundViewCornerRadius(6);

    }

    public void show() {
        dismiss();
        tv_show.setVisibility(View.VISIBLE);
        tv_show.setText(defaultTextShow);
        if (mProgressDialog != null) {
            progress_wheel.spin();
            mProgressDialog.show();
        }
    }

    public void showNoText() {
        dismiss();
        tv_show.setVisibility(View.GONE);
        if (mProgressDialog != null) {
            progress_wheel.spin();
            mProgressDialog.show();
        }
    }

    public void show(String msg) {
        dismiss();
        if (TextUtils.isEmpty(msg)) {
            msg = defaultTextShow;
        }
        tv_show.setVisibility(View.VISIBLE);
        tv_show.setText(msg);
        if (mProgressDialog != null) {
            progress_wheel.spin();
            mProgressDialog.show();
        }
    }

    public void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            progress_wheel.stopSpinning();
            mProgressDialog.dismiss();
            if (mDialogDismissListener != null) {
                mDialogDismissListener.dismiss();
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
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    public void setCanceledOnTouchOutside(boolean cancle) {
        canceledOnTouchOutside = cancle;
    }

    public void setBackgroundWindowColor(int colorID) {
        dialog_window_background.setBackgroundColor(colorID);
    }

    public void setBackgroundWindowColor(String colorStr) {
        dialog_window_background.setBackgroundColor(getColor(colorStr));
    }

    public void setBackgroundViewColor(int colorID) {
        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setColor(colorID);
        dialog_view_bg.setBackground(myGrad);
    }

    public void setBackgroundViewColor(String colorStr) {
        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setColor(getColor(colorStr));
        dialog_view_bg.setBackground(myGrad);
    }

    //设置边框的颜色和宽度
    public void setBackgroundViewStrokeWidthAndColor(float width, int colorID) {
        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setStroke(dip2px(mContext, width), colorID);
        dialog_view_bg.setBackground(myGrad);
    }

    public void setBackgroundViewCornerRadius(float radius) {
        /*设置圆角*/
        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setCornerRadius(dip2px(mContext, radius));
        dialog_view_bg.setBackground(myGrad);
    }


    public void setProgressColor(int colorID) {
        progress_wheel.setBarColor(colorID);
    }

    public void setProgressColor(String colorStr) {
        progress_wheel.setBarColor(getColor(colorStr));
    }

    public void setProgressWidth(int progressWidth) {
        progress_wheel.setBarWidth(dip2px(mContext, progressWidth));
    }


    public void setDialogTextColor(int colorID) {
        tv_show.setTextColor(colorID);
    }

    public void setDialogTextColor(String colorStr) {
        progress_wheel.setBarColor(getColor(colorStr));
    }


    private int getColor(int colorID) {
        int color = mContext.getResources().getColor(colorID);
        return color;
    }

    private int getColor(String colorStr) {
        int color = Color.parseColor(colorStr);
        return color;
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
            if (canceledOnTouchOutside) {
                dismiss();
            }
        }
    }

    public interface OnDialogDismissListener {
        void dismiss();
    }

    public void setOnDialogDismissListener(OnDialogDismissListener dialogDismissListener) {
        mDialogDismissListener = dialogDismissListener;
    }


    //---------------------分割线----------------------带有进度条的Dialog

    public void setDialogProgress(float progress) {
        progress_wheel.setProgress(progress);
    }

    public void setProgressRimColor(int colorID) {
        progress_wheel.setRimColor(colorID);
    }

    public void setProgressRimWidth(int width) {
        progress_wheel.setRimWidth(width);
    }

    public void setDialogText(String dialogText) {
        tv_show.setText(dialogText);
    }


}

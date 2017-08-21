package com.maning.mndialoglibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.maning.mndialoglibrary.MProgressDialog.dip2px;

/**
 * Created by maning on 2017/8/10.
 * 提示Dialog
 */

public class MStatusDialog {

    private Handler mHandler = new Handler();
    private Context mContext;
    private Dialog mDialog;

    private Builder mBuilder;

    private RelativeLayout dialog_window_background;
    private RelativeLayout dialog_view_bg;
    private ImageView imageStatus;
    private TextView tvShow;

    public MStatusDialog(Context context) {
        this(context, new Builder(context));
    }

    public MStatusDialog(Context context, Builder builder) {
        mContext = context;
        mBuilder = builder;
        //初始化
        initDialog();
    }

    private void initDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View mProgressDialogView = inflater.inflate(R.layout.mn_status_dialog_layout, null);// 得到加载view
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

        //获取布局
        dialog_window_background = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_window_background);
        dialog_view_bg = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_view_bg);
        imageStatus = (ImageView) mProgressDialogView.findViewById(R.id.imageStatus);
        tvShow = (TextView) mProgressDialogView.findViewById(R.id.tvShow);

        //默认配置
        configView();

    }

    private void configView() {
        dialog_window_background.setBackgroundColor(mBuilder.backgroundWindowColor);
        tvShow.setTextColor(mBuilder.textColor);
        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setColor(mBuilder.backgroundViewColor);
        myGrad.setStroke(dip2px(mContext, mBuilder.strokeWidth), mBuilder.strokeColor);
        myGrad.setCornerRadius(dip2px(mContext, mBuilder.cornerRadius));
        dialog_view_bg.setBackground(myGrad);
    }


    public void refreshBuilder(Builder builder) {
        mBuilder = builder;
        configView();
    }


    public void show(String msg, Drawable drawable) {
        show(msg, drawable, 2000);
    }

    public void show(String msg, Drawable drawable, long delayMillis) {
        imageStatus.setImageDrawable(drawable);
        tvShow.setText(msg);
        mDialog.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                mHandler.removeCallbacksAndMessages(null);
            }
        }, delayMillis);
    }


    public static final class Builder {

        private Context mContext;

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
        //文字的颜色
        int textColor;

        public Builder(Context context) {
            mContext = context;
            //默认配置
            backgroundWindowColor = mContext.getResources().getColor(R.color.mn_colorDialogWindowBg);
            backgroundViewColor = mContext.getResources().getColor(R.color.mn_colorDialogViewBg);
            strokeColor = mContext.getResources().getColor(R.color.mn_colorDialogTrans);
            textColor = mContext.getResources().getColor(R.color.mn_colorDialogTextColor);
            cornerRadius = 6;
            strokeWidth = 0;
        }

        public MStatusDialog build() {
            return new MStatusDialog(mContext, this);
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

        public Builder setTextColor(@Nullable int textColor) {
            this.textColor = textColor;
            return this;
        }

    }

}

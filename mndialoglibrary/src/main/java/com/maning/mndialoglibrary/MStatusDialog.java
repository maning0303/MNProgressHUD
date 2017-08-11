package com.maning.mndialoglibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
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

    private RelativeLayout dialog_view_bg;
    private ImageView imageStatus;
    private TextView tvShow;

    private int imageTintColor;

    public MStatusDialog(Context context) {
        mContext = context;
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
        dialog_view_bg = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_view_bg);
        imageStatus = (ImageView) mProgressDialogView.findViewById(R.id.imageStatus);
        tvShow = (TextView) mProgressDialogView.findViewById(R.id.tvShow);

        //默认配置
        setBackgroundViewCornerRadius(6);
        setTextColor(mContext.getResources().getColor(R.color.mn_colorDialogTextColor));
        setBackgroundViewColor(mContext.getResources().getColor(R.color.mn_colorDialogViewBg));
        setBackgroundViewStrokeWidthAndColor(0.0f, mContext.getResources().getColor(R.color.mn_colorDialogTrans));
        setImageTintColor(mContext.getResources().getColor(R.color.mn_colorDialogImageTintColor));
    }

    private void setImageShow(int flag) {
        if (flag == 1) {
            imageStatus.setImageResource(R.drawable.mn_icon_dialog_ok);
        } else if (flag == 2) {
            imageStatus.setImageResource(R.drawable.mn_icon_dialog_error);
        } else if (flag == 3) {
            imageStatus.setImageResource(R.drawable.mn_icon_dialog_hint);
        }
    }

    public void showSuccess(String msg) {
        setImageShow(1);
        show(msg);
    }

    public void showError(String msg) {
        setImageShow(2);
        show(msg);
    }

    public void showHint(String msg) {
        setImageShow(3);
        show(msg);
    }

    public void showCustom(Drawable customDrawable, String msg) {
        imageStatus.setImageDrawable(customDrawable);
        show(msg);
    }

    private void show(String msg) {
        tvShow.setText(msg);
        mDialog.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                mHandler.removeCallbacksAndMessages(null);
            }
        }, 2000);
    }

    private int getColor(String colorStr) {
        int color = Color.parseColor(colorStr);
        return color;
    }

    //设置颜色
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

    /**
     * 设置圆角
     * @param radius 圆角
     */
    public void setBackgroundViewCornerRadius(float radius) {
        /*设置圆角*/
        GradientDrawable myGrad = (GradientDrawable) dialog_view_bg.getBackground();
        myGrad.setCornerRadius(dip2px(mContext, radius));
        dialog_view_bg.setBackground(myGrad);
    }

    public void setImageTintColor(int colorID) {
        imageTintColor = colorID;
    }

    public void setTextColor(int colorID) {
        tvShow.setTextColor(colorID);
    }

}

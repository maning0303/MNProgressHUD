package com.maning.mndialoglibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maning.mndialoglibrary.config.MToastConfig;
import com.maning.mndialoglibrary.utils.MSizeUtils;


/**
 * Created by maning on 2017/8/11.
 * 自定义Toast
 */

public class MToast {

    private static Toast currentToast;
    private static TextView tvShowToast;
    private static ImageView ivLeftShow;
    private static LinearLayout toastBackgroundView;

    public static void makeTextLong(@NonNull Context context, @NonNull CharSequence message, MToastConfig config) {
        make(config, context, message, Toast.LENGTH_LONG).show();
    }

    public static void makeTextShort(@NonNull Context context, @NonNull CharSequence message, MToastConfig config) {
        make(config, context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeTextLong(@NonNull Context context, @NonNull CharSequence message) {
        make(null, context, message, Toast.LENGTH_LONG).show();
    }

    public static void makeTextShort(@NonNull Context context, @NonNull CharSequence message) {
        make(null, context, message, Toast.LENGTH_SHORT).show();
    }

    private static void makeText(MToastConfig config, @NonNull Context context, @NonNull CharSequence message, int duration) {
        make(config, context, message, duration).show();
    }

    private static void makeText(@NonNull Context context, @NonNull CharSequence message, int duration) {
        make(null, context, message, duration).show();
    }

    private static Toast make(MToastConfig config, @NonNull Context context, @NonNull CharSequence message, int duration) {
        if (currentToast == null) {
            currentToast = new Toast(context);
        }

        View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.mn_toast_layout, null);

        tvShowToast = (TextView) toastLayout.findViewById(R.id.tvShowToast);
        ivLeftShow = (ImageView) toastLayout.findViewById(R.id.ivLeftShow);
        toastBackgroundView = (LinearLayout) toastLayout.findViewById(R.id.toastBackgroundView);
        currentToast.setView(toastLayout);

        //相关配置
        if (config == null) {
            config = new MToastConfig.Builder().build();
        }
        MToastConfig.MToastGravity ToastGravity = config.ToastGravity;
        int ToastTextColor = config.ToastTextColor;
        int ToastBackgroundColor = config.ToastBackgroundColor;
        float ToastBackgroundCornerRadius = config.ToastBackgroundCornerRadius;
        Drawable ToastIcon = config.ToastIcon;
        int ToastBackgroundStrokeColor = config.ToastBackgroundStrokeColor;
        float ToastBackgroundStrokeWidth = config.ToastBackgroundStrokeWidth;


        //图片的显示
        if (ToastIcon == null) {
            ivLeftShow.setVisibility(View.GONE);
        } else {
            ivLeftShow.setVisibility(View.VISIBLE);
            ivLeftShow.setImageDrawable(ToastIcon);
        }
        //文字的颜色
        tvShowToast.setTextColor(ToastTextColor);
        //背景色和圆角
        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setCornerRadius(MSizeUtils.dp2px(context, ToastBackgroundCornerRadius));
        myGrad.setColor(ToastBackgroundColor);
        myGrad.setStroke(MSizeUtils.dp2px(context, ToastBackgroundStrokeWidth), ToastBackgroundStrokeColor);
        toastBackgroundView.setBackground(myGrad);
        //文字
        tvShowToast.setText(message);
        //时间
        currentToast.setDuration(duration);
        //显示位置
        if (ToastGravity == MToastConfig.MToastGravity.CENTRE) {
            currentToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            currentToast.setGravity(Gravity.BOTTOM, 0, MSizeUtils.dp2px(context, 80));
        }

        return currentToast;
    }

}

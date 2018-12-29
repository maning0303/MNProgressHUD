package com.maning.mndialoglibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        cancelToast();
        Context mCotext = context.getApplicationContext();
        if (currentToast == null) {
            currentToast = new Toast(mCotext);
        }

        View toastLayout = ((LayoutInflater) mCotext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.mn_toast_layout, null);

        TextView tvShowToast = (TextView) toastLayout.findViewById(R.id.tvShowToast);
        ImageView ivLeftShow = (ImageView) toastLayout.findViewById(R.id.ivLeftShow);
        LinearLayout toastBackgroundView = (LinearLayout) toastLayout.findViewById(R.id.toastBackgroundView);
        currentToast.setView(toastLayout);

        //相关配置
        if (config == null) {
            config = new MToastConfig.Builder().build();
        }
        MToastConfig.MToastGravity toastGravity = config.toastGravity;
        int toastTextColor = config.toastTextColor;
        float toastTextSize = config.toastTextSize;
        int toastBackgroundColor = config.toastBackgroundColor;
        float toastBackgroundCornerRadius = config.toastBackgroundCornerRadius;
        Drawable toastIcon = config.toastIcon;
        int toastBackgroundStrokeColor = config.toastBackgroundStrokeColor;
        float toastBackgroundStrokeWidth = config.toastBackgroundStrokeWidth;

        //图片的显示
        if (toastIcon == null) {
            ivLeftShow.setVisibility(View.GONE);
        } else {
            ivLeftShow.setVisibility(View.VISIBLE);
            ivLeftShow.setImageDrawable(toastIcon);
        }
        //文字
        tvShowToast.setTextColor(toastTextColor);
        tvShowToast.setTextSize(TypedValue.COMPLEX_UNIT_SP, toastTextSize);
        tvShowToast.setText(message);
        //背景色和圆角
        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setCornerRadius(MSizeUtils.dp2px(mCotext, toastBackgroundCornerRadius));
        myGrad.setColor(toastBackgroundColor);
        myGrad.setStroke(MSizeUtils.dp2px(mCotext, toastBackgroundStrokeWidth), toastBackgroundStrokeColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            toastBackgroundView.setBackground(myGrad);
        } else {
            toastBackgroundView.setBackgroundDrawable(myGrad);
        }
        toastBackgroundView.setPadding(
                MSizeUtils.dp2px(mCotext, config.paddingLeft),
                MSizeUtils.dp2px(mCotext, config.paddingTop),
                MSizeUtils.dp2px(mCotext, config.paddingRight),
                MSizeUtils.dp2px(mCotext, config.paddingBottom)
        );

        //显示位置
        if (toastGravity == MToastConfig.MToastGravity.CENTRE) {
            currentToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            currentToast.setGravity(Gravity.BOTTOM, 0, MSizeUtils.dp2px(mCotext, 80));
        }
        //图片宽高
        if (config.imgWidth > 0 && config.imgHeight > 0) {
            ViewGroup.LayoutParams layoutParams = ivLeftShow.getLayoutParams();
            layoutParams.width = MSizeUtils.dp2px(mCotext, config.imgWidth);
            layoutParams.height = MSizeUtils.dp2px(mCotext, config.imgHeight);
            ivLeftShow.setLayoutParams(layoutParams);
        }
        //时间
        currentToast.setDuration(duration);
        return currentToast;
    }

    /**
     * 取消Toast
     */
    public static void cancelToast() {
        if (currentToast != null) {
            currentToast.cancel();
            currentToast = null;
        }
    }

}

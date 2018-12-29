package com.maning.mndialoglibrary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
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

import java.lang.ref.WeakReference;


/**
 * Created by maning on 2017/8/11.
 * 自定义Toast
 */

public class MToast {

    private static WeakReference<Context> mWeakReference;
    private static Toast currentToast = null;

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
        Context mContext = context.getApplicationContext();

        if (currentToast != null) {
            currentToast.cancel();
            currentToast = null;
        }
        currentToast = new Toast(mContext);

        View toastLayout = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.mn_toast_layout, null);

        TextView tvShowToast = (TextView) toastLayout.findViewById(R.id.tvShowToast);
        ImageView ivLeftShow = (ImageView) toastLayout.findViewById(R.id.ivLeftShow);
        LinearLayout toastBackgroundView = (LinearLayout) toastLayout.findViewById(R.id.toastBackgroundView);
        currentToast.setView(toastLayout);

        //相关配置
        if (config == null) {
            config = new MToastConfig.Builder().build();
        }
        MToastConfig.MToastGravity ToastGravity = config.ToastGravity;
        int ToastTextColor = config.ToastTextColor;
        float ToastTextSize = config.ToastTextSize;
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
        tvShowToast.setTextSize(ToastTextSize);
        //背景色和圆角
        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setCornerRadius(MSizeUtils.dp2px(mContext, ToastBackgroundCornerRadius));
        myGrad.setColor(ToastBackgroundColor);
        myGrad.setStroke(MSizeUtils.dp2px(mContext, ToastBackgroundStrokeWidth), ToastBackgroundStrokeColor);
        toastBackgroundView.setBackground(myGrad);
        toastBackgroundView.setPadding(
                MSizeUtils.dp2px(mContext, config.paddingLeft),
                MSizeUtils.dp2px(mContext, config.paddingTop),
                MSizeUtils.dp2px(mContext, config.paddingRight),
                MSizeUtils.dp2px(mContext, config.paddingBottom)
        );
        //文字
        tvShowToast.setText(message);
        //时间
        currentToast.setDuration(duration);
        //显示位置
        if (ToastGravity == MToastConfig.MToastGravity.CENTRE) {
            currentToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            currentToast.setGravity(Gravity.BOTTOM, 0, MSizeUtils.dp2px(mContext, 80));
        }
        //图片宽高
        if (config.imgWidth > 0 && config.imgHeight > 0) {
            ViewGroup.LayoutParams layoutParams = ivLeftShow.getLayoutParams();
            layoutParams.width = MSizeUtils.dp2px(mContext, config.imgWidth);
            layoutParams.height = MSizeUtils.dp2px(mContext, config.imgHeight);
            ivLeftShow.setLayoutParams(layoutParams);
        }

        return currentToast;
    }

}

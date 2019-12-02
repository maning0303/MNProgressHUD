package com.maning.mndialoglibrary;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
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

import java.lang.reflect.InvocationTargetException;


/**
 * Created by maning on 2017/8/11.
 * 自定义Toast
 */

public class MToast {

    private static Handler UIHandler = new Handler(Looper.getMainLooper());

    private static Toast currentToast;

    private static Context mAppContext;

    //----对外提供方法---------
    public static void init(Context context) {
        MToast.mAppContext = context.getApplicationContext();
    }


    public static void makeTextLong(@NonNull Context context, @NonNull CharSequence message, MToastConfig config) {
        show(config, context, message, Toast.LENGTH_LONG);
    }

    public static void makeTextShort(@NonNull Context context, @NonNull CharSequence message, MToastConfig config) {
        show(config, context, message, Toast.LENGTH_SHORT);
    }

    public static void makeTextLong(@NonNull Context context, @NonNull CharSequence message) {
        show(null, context, message, Toast.LENGTH_LONG);
    }

    public static void makeTextShort(@NonNull Context context, @NonNull CharSequence message) {
        show(null, context, message, Toast.LENGTH_SHORT);
    }

    public static void makeTextLong(@NonNull CharSequence message) {
        show(null, mAppContext, message, Toast.LENGTH_LONG);
    }

    public static void makeTextShort(@NonNull CharSequence message) {
        show(null, mAppContext, message, Toast.LENGTH_SHORT);
    }
    //----对外提供方法---------

    private static void show(final MToastConfig config, final Context context, final CharSequence message, final int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        try {
            if (mAppContext == null) {
                if (context != null) {
                    mAppContext = context.getApplicationContext();
                } else {
                    mAppContext = getApplicationByReflect().getApplicationContext();
                }
            }
            if (mAppContext == null) {
                return;
            }
            if (Looper.getMainLooper() == Looper.myLooper()) {
                getToast(config, mAppContext, message, duration).show();
            } else {
                UIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getToast(config, mAppContext, message, duration).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(">>>MToast>>>", "MToast异常：" + e.toString());
        }
    }

    private static Toast getToast(MToastConfig config, @NonNull Context context, @NonNull CharSequence message, int duration) {
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

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

}

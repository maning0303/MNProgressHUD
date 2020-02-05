package com.maning.mndialoglibrary.utils;

import android.graphics.Color;
import android.os.Build;
import androidx.core.graphics.ColorUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 部分代码源自：https://github.com/gyf-dev/ImmersionBar
 */
public class StatusBarUtils {

    /**
     * MIUI状态栏字体黑色与白色标识位
     */
    static final String IMMERSION_MIUI_STATUS_BAR_DARK = "EXTRA_FLAG_STATUS_BAR_DARK_MODE";

    private static boolean isSupportStatusBarDarkFont() {
        return OSUtils.isMIUI6Later() || OSUtils.isFlymeOS4Later()
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }


    private static void setStatusBarModeMIUI(Window window, boolean bDark) {
        if (OSUtils.isMIUI6Later()) {
            //修改miui状态栏字体颜色
            SpecialBarFontUtils.setMIUIBarDark(window, IMMERSION_MIUI_STATUS_BAR_DARK, bDark);
        }
    }

    private static void setStatusBarModeFlyme(Window window, boolean bDark) {
        // 修改Flyme OS状态栏字体颜色
        if (OSUtils.isFlymeOS4Later()) {
            SpecialBarFontUtils.setStatusBarDarkIcon(window, bDark);
        }
    }


    /**
     * Flag只有在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
     * 并且没有使用 FLAG_TRANSLUCENT_STATUS 的时候才有效，也就是只有在状态栏全透明的时候才有效。
     *
     * @param window
     * @param bDark   bDark为true时是黑色的字，为false时是白色的字
     */
    private static void setStatusBarModeDefault(Window window, boolean bDark) {
        //6.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (window == null) {
                return;
            }
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ColorUtils.blendARGB(Color.TRANSPARENT, Color.BLACK, 0.0f));
            View decorView = window.getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                vis |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    public static void setStatusBarMode(Window window, boolean bDark) {
        if (isSupportStatusBarDarkFont()) {
            setStatusBarModeDefault(window, bDark);
            setStatusBarModeMIUI(window, bDark);
            setStatusBarModeFlyme(window, bDark);
        }
    }


}

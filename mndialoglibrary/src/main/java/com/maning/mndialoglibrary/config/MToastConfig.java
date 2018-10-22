package com.maning.mndialoglibrary.config;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by maning on 2017/8/11.
 * Toast自定义相关参数
 */
public class MToastConfig {

    public enum MToastGravity {
        CENTRE, BOTTOM
    }

    public float toastTextSize = 13;
    public int toastTextColor = Color.parseColor("#FFFFFFFF");
    public int toastBackgroundColor = Color.parseColor("#b2000000");
    public float toastBackgroundCornerRadius = 4.0f;
    public float toastBackgroundStrokeWidth = 0.0f;
    public int toastBackgroundStrokeColor = Color.parseColor("#00000000");
    public MToastGravity toastGravity = MToastGravity.BOTTOM;
    public Drawable toastIcon = null;
    //布局的Padding--int left, int top, int right, int bottom
    public int paddingLeft = 20;
    public int paddingTop = 12;
    public int paddingRight = 20;
    public int paddingBottom = 12;
    //图片宽高
    public int imgWidth = 20;
    public int imgHeight = 20;


    private MToastConfig() {
    }

    public static class Builder {

        private MToastConfig mToastConfig = null;

        public Builder() {
            mToastConfig = new MToastConfig();
        }

        public MToastConfig build() {
            return mToastConfig;
        }

        public Builder setTextColor(@ColorInt int textColor) {
            mToastConfig.toastTextColor = textColor;
            return this;
        }

        public Builder setTextSize(float textSize) {
            mToastConfig.toastTextSize = textSize;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
            mToastConfig.toastBackgroundColor = backgroundColor;
            return this;
        }

        public Builder setBackgroundCornerRadius(float radius) {
            mToastConfig.toastBackgroundCornerRadius = radius;
            return this;
        }

        public Builder setGravity(MToastGravity toastGravity) {
            mToastConfig.toastGravity = toastGravity;
            return this;
        }

        public Builder setToastIcon(Drawable ToastIcon) {
            mToastConfig.toastIcon = ToastIcon;
            return this;
        }

        public Builder setBackgroundStrokeWidth(float width) {
            mToastConfig.toastBackgroundStrokeWidth = width;
            return this;
        }

        public Builder setBackgroundStrokeColor(@ColorInt int strokeColor) {
            mToastConfig.toastBackgroundStrokeColor = strokeColor;
            return this;
        }

        public Builder setImgWidthAndHeight(int imgWidth, int imgHeight) {
            mToastConfig.imgWidth = imgWidth;
            mToastConfig.imgHeight = imgHeight;
            return this;
        }

        public Builder setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
            mToastConfig.paddingLeft = paddingLeft;
            mToastConfig.paddingTop = paddingTop;
            mToastConfig.paddingRight = paddingRight;
            mToastConfig.paddingBottom = paddingBottom;
            return this;
        }

    }

}

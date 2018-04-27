package com.maning.mnprogressdialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.maning.mndialoglibrary.config.MDialogConfig;
import com.maning.mndialoglibrary.MProgressBarDialog;
import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.MStatusDialog;
import com.maning.mndialoglibrary.MToast;
import com.maning.mndialoglibrary.config.MToastConfig;
import com.maning.mndialoglibrary.listeners.OnDialogDismissListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String text01 = "从前有坐山,山上有坐庙,庙里有个老和尚在讲故事,讲的什么啊,从前有座山,山里有座庙,庙里有个盆,盆里有个锅,锅里有个碗,碗里有个匙,匙里有个花生仁,我吃了,你谗了,我的故事讲完了.";
    private Context mContext;
    private Handler mHandler = new Handler();


    private MProgressBarDialog mProgressBarDialog;

    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;
    private Button btn06;
    private Button btn07;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn13;
    private Button btn14;
    private Button btn15;
    private Button btn16;
    private Button btn17;
    private Button btn18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initViews();
    }


    private void initViews() {
        btn01 = (Button) findViewById(R.id.btn01);
        btn02 = (Button) findViewById(R.id.btn02);
        btn03 = (Button) findViewById(R.id.btn03);
        btn04 = (Button) findViewById(R.id.btn04);
        btn06 = (Button) findViewById(R.id.btn06);
        btn07 = (Button) findViewById(R.id.btn07);
        btn10 = (Button) findViewById(R.id.btn10);
        btn11 = (Button) findViewById(R.id.btn11);
        btn12 = (Button) findViewById(R.id.btn12);
        btn13 = (Button) findViewById(R.id.btn13);
        btn14 = (Button) findViewById(R.id.btn14);
        btn15 = (Button) findViewById(R.id.btn15);
        btn16 = (Button) findViewById(R.id.btn16);
        btn17 = (Button) findViewById(R.id.btn17);
        btn18 = (Button) findViewById(R.id.btn18);

        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        btn04.setOnClickListener(this);
        btn06.setOnClickListener(this);
        btn07.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn01:
                MProgressDialog.showProgress(this);
                //延时关闭
                delayDismissProgressDialog();
                break;
            case R.id.btn02:
                MProgressDialog.showProgress(this,text01);
                //延时关闭
                delayDismissProgressDialog();
                break;
            case R.id.btn03:
                MProgressDialog.showProgress(this,"");
                //延时关闭
                delayDismissProgressDialog();
                break;
            case R.id.btn04:
                MDialogConfig mDialogConfig = new MDialogConfig.Builder()
                        //点击外部是否可以取消
                        .isCanceledOnTouchOutside(true)
                        //全屏背景窗体的颜色
                        .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                        //View背景的颜色
                        .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg))
                        //View背景的圆角
                        .setCornerRadius(20)
                        //View 边框的颜色
                        .setStrokeColor(getMyColor(R.color.colorAccent))
                        //View 边框的宽度
                        .setStrokeWidth(2)
                        //Progress 颜色
                        .setProgressColor(getMyColor(R.color.colorDialogProgressBarColor))
                        //Progress 宽度
                        .setProgressWidth(3)
                        //Progress 内圈颜色
                        .setProgressRimColor(Color.YELLOW)
                        //Progress 内圈宽度
                        .setProgressRimWidth(4)
                        //文字的颜色
                        .setTextColor(getMyColor(R.color.colorDialogTextColor))
                        //ProgressBar 颜色
                        .setProgressColor(Color.GREEN)
                        //关闭的监听
                        .setOnDialogDismissListener(new OnDialogDismissListener() {
                            @Override
                            public void onDismiss() {
                                MToast.makeTextShort(mContext,"监听到了ProgressDialog关闭了");
                            }
                        })
                        .build();
                MProgressDialog.showProgress(this,"数据上传中...",mDialogConfig);
                break;
            case R.id.btn06:
                showStatusDialog01();
                break;
            case R.id.btn07:
                showStatusDialog02();
                break;
            case R.id.btn10:
                showToast();
                break;
            case R.id.btn11:
                showToastCustom();
                break;
            case R.id.btn12:
                showToastCustom2();
                break;
            case R.id.btn13:
                showToastCustom3();
                break;
            case R.id.btn14:
                configProgressbarHorizontalDialog();
                initTimer();
                break;
            case R.id.btn15:
                configProgressbarHorizontalDialog2();
                initTimer();
                break;
            case R.id.btn16:
                configProgressbarCircleDialog();
                initTimer();
                break;
            case R.id.btn17:
                configProgressbarCircleDialog2();
                initTimer();
                break;
            case R.id.btn18:
                new TestFragmentDialog().showDialog(MainActivity.this);
                break;

        }
    }


    /**
     * --------------------MProgressDialog start -------------------
     */

    private int getMyColor(int colorID) {
        return mContext.getResources().getColor(colorID);
    }


    private void delayDismissProgressDialog() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MProgressDialog.dismissProgress();
            }
        }, 3000);
    }


    /** --------------------MProgressDialog end ------------------- */


    /**
     * --------------------MToast start -------------------
     */

    private void showToastCustom3() {
        MToastConfig config = new MToastConfig.Builder()
                .setBackgroundStrokeColor(Color.YELLOW)
                .setBackgroundStrokeWidth(1)
                .setBackgroundCornerRadius(20)
                .build();
        MToast.makeTextShort(mContext, text01, config);
    }

    private void showToastCustom2() {
        MToastConfig config = new MToastConfig.Builder()
                .setGravity(MToastConfig.MToastGravity.CENTRE)
                .setTextColor(Color.MAGENTA)
                .setBackgroundColor(getMyColor(R.color.colorDialogTest))
                .setBackgroundCornerRadius(10)
                .build();
        MToast.makeTextShort(mContext, text01, config);
    }

    private void showToastCustom() {
        MToastConfig config = new MToastConfig.Builder()
                .setTextColor(getMyColor(R.color.white))
                .setBackgroundColor(getMyColor(R.color.colorDialogTest))
                .setToastIcon(mContext.getResources().getDrawable(R.drawable.mn_icon_dialog_ok))
                .build();
        MToast.makeTextShort(mContext, "欢迎使用自定义Toast", config);

    }

    private void showToast() {
        MToast.makeTextShort(mContext, "我是默认Toast");
    }


    /** --------------------MToast start ------------------- */


    /**
     * --------------------MStatusDialog start -------------------
     */

    private void showStatusDialog01() {
        new MStatusDialog(this).show("保存成功", mContext.getResources().getDrawable(R.drawable.mn_icon_dialog_ok));
    }

    private void showStatusDialog02() {
        MDialogConfig mDialogConfig = new MDialogConfig.Builder()
                //全屏背景窗体的颜色
                .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                //View背景的颜色
                .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg2))
                //字体的颜色
                .setTextColor(getMyColor(R.color.colorAccent))
                //View边框的颜色
                .setStrokeColor(getMyColor(R.color.white))
                //View边框的宽度
                .setStrokeWidth(2)
                //View圆角大小
                .setCornerRadius(10)
                //关闭的监听
                .setOnDialogDismissListener(new OnDialogDismissListener() {
                    @Override
                    public void onDismiss() {
                        MToast.makeTextShort(mContext,"监听到了MStatusDialog关闭了");
                    }
                })
                .build();
        new MStatusDialog(mContext,mDialogConfig).show("提交数据失败,请重新尝试!", mContext.getResources().getDrawable(R.mipmap.ic_launcher), 1000);
    }

    /** --------------------MStatusDialog end ------------------- */



    /**
     * --------------------MProgressBarDialog start -------------------
     */
    private void configProgressbarCircleDialog() {
        //新建一个Dialog
        mProgressBarDialog = new MProgressBarDialog.Builder(mContext)
                .setStyle(MProgressBarDialog.MProgressBarDialogStyle_Circle)
                .build();
    }

    private void configProgressbarCircleDialog2() {
        //新建一个Dialog
        mProgressBarDialog = new MProgressBarDialog.Builder(mContext)
                .setStyle(MProgressBarDialog.MProgressBarDialogStyle_Circle)
                //全屏背景窗体的颜色
                .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                //View背景的颜色
                .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg2))
                //字体的颜色
                .setTextColor(getMyColor(R.color.colorAccent))
                //View边框的颜色
                .setStrokeColor(getMyColor(R.color.white))
                //View边框的宽度
                .setStrokeWidth(2)
                //View圆角大小
                .setCornerRadius(10)
                //ProgressBar背景色
                .setProgressbarBackgroundColor(Color.BLUE)
                //ProgressBar 颜色
                .setProgressColor(Color.GREEN)
                //圆形内圈的宽度
                .setCircleProgressBarWidth(4)
                //圆形外圈的宽度
                .setCircleProgressBarBackgroundWidth(4)
                .build();
    }

    private void configProgressbarHorizontalDialog() {
        //新建一个Dialog
        mProgressBarDialog = new MProgressBarDialog.Builder(mContext)
                .setStyle(MProgressBarDialog.MProgressBarDialogStyle_Horizontal)
                .build();
    }

    private void configProgressbarHorizontalDialog2() {
        //新建一个Dialog
        mProgressBarDialog = new MProgressBarDialog.Builder(mContext)
                .setStyle(MProgressBarDialog.MProgressBarDialogStyle_Horizontal)
                //全屏背景窗体的颜色
                .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                //View背景的颜色
                .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg2))
                //字体的颜色
                .setTextColor(getMyColor(R.color.colorAccent))
                //View边框的颜色
                .setStrokeColor(getMyColor(R.color.white))
                //View边框的宽度
                .setStrokeWidth(2)
                //View圆角大小
                .setCornerRadius(10)
                //ProgressBar背景色
                .setProgressbarBackgroundColor(Color.BLUE)
                //ProgressBar 颜色
                .setProgressColor(Color.GREEN)
                //水平进度条Progress圆角
                .setProgressCornerRadius(0)
                //水平进度条的高度
                .setHorizontalProgressBarHeight(10)
                .build();
    }

    private Timer timer;
    private TimerTask task;
    private float currentProgress;

    private void initTimer() {
        destroyTimer();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (currentProgress < 100f) {
                            mProgressBarDialog.showProgress((int) currentProgress, "当前进度为：" + currentProgress + "%");
                            currentProgress += 5;
                        } else {
                            destroyTimer();
                            currentProgress = 0.0f;
                            mProgressBarDialog.showProgress(100, "完成");
                            //关闭
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBarDialog.dismiss();
                                }
                            }, 500);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 200); //延时1000ms后执行，1000ms执行一次
    }

    private void destroyTimer() {
        if (timer != null && task != null) {
            timer.cancel();
            task.cancel();
            timer = null;
            task = null;
        }
    }

    /**
     * --------------------MProgressBarDialog end -------------------
     */
}

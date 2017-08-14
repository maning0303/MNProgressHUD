# MNProgressDialog [![](https://jitpack.io/v/maning0303/MNProgressHUD.svg)](https://jitpack.io/#maning0303/MNProgressHUD)
一个常用的自定义弹框封装,加载ProgressDialog,状态显示的StatusDialog和自定义Toast,全部支持背景颜色,圆角,边框和文字的自定义。


## 截图：
### MProgressDialog
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_001.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_002.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_006.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_003.png)

### MStatusDialog
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_004.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_005.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_007.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_dialog_008.png)

### MSToast
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_toast_001.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_toast_002.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_toast_003.png)
![](https://github.com/maning0303/MNProgressHUD/raw/master/screenshots/mn_toast_004.png)


## 如何添加
### 1:Gradle添加：
#### 1.在Project的build.gradle中添加仓库地址

``` gradle
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

#### 2.在app目录下的build.gradle中添加依赖
``` gradle
	dependencies {
	     compile 'com.github.maning0303:MNProgressHUD:V1.0.0'
	}
```

### 2:源码Module添加：
#### 直接关联mndialoglibrary

``` gradle

	compile project(':mndialoglibrary')

```


## 使用方法(查看Demo详情):

### 1:MProgressDialog 加载Dialog代码使用:
``` java

        //新建一个ProgressDialog
        MProgressDialog mMProgressDialog = new MProgressDialog(this);
        //设置点击外面消失
        mMProgressDialog.setCanceledOnTouchOutside(false);
        //设置窗体的背景色
        mMProgressDialog.setBackgroundWindowColor(color01);
        //设置ProgressDialog的背景色
        mMProgressDialog.setBackgroundViewColor(color02);
        //设置Progress颜色
        mMProgressDialog.setProgressColor(color03);
        //设置Progress宽度(db)
        mMProgressDialog.setProgressWidth(progressWidth);
        //设置文本的颜色
        mMProgressDialog.setDialogTextColor(color04);
        //设置ProgressDialog的背景的圆角大小(db)
        mMProgressDialog.setBackgroundViewCornerRadius(cornerRadius);
        //设置内圈的颜色
        mMProgressDialog.setProgressRimColor(getMyColor(R.color.colorDialogProgressRimColor));
        //设置内圈的宽度
        mMProgressDialog.setProgressRimWidth(1);

        //加载显示
        mMProgressDialog.show();
        //mMProgressDialog.showNoText();
        //mMProgressDialog.show("自定义文案");

        //进度显示(0~1.0)
        mMProgressDialog.setDialogProgress(0.5);

        //Dialog消失的监听
        mMProgressDialog.setOnDialogDismissListener(new MProgressDialog.OnDialogDismissListener() {
            @Override
            public void dismiss() {

            }
        });

```

### 2:MStatusDialog 状态Dialog代码使用:
``` java

                MStatusDialog mMStatusDialog = new MStatusDialog(this);
                //背景色
                mMStatusDialog.setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg2));
                //文字的颜色
                mMStatusDialog.setTextColor(getMyColor(R.color.colorAccent));
                //背景View的圆角大小db
                mMStatusDialog.setBackgroundViewCornerRadius(2);
                //背景View的边框大小和颜色
                mMStatusDialog.setBackgroundViewStrokeWidthAndColor(2, getMyColor(R.color.white));


                //四种显示:
                mMStatusDialog.showSuccess("***");
                mMStatusDialog.showError("***");
                mMStatusDialog.showHint("***");
                mMStatusDialog.showCustom(mContext.getResources().getDrawable(R.mipmap.ic_launcher), "自定义图片和颜色");

```

### 3:MToast 代码使用:
``` java

        MToastConfig config = new MToastConfig.Builder()
                //设置显示的位置
                .setGravity(MToastConfig.MToastGravity.CENTRE)
                //文字的颜色
                .setTextColor(getMyColor(R.color.colorAccent))
                //背景色
                .setBackgroundColor(getMyColor(R.color.colorDialogTest))
                //背景圆角
                .setBackgroundCornerRadius(10)
                //背景边框的颜色
                .setBackgroundStrokeColor(Color.WHITE)
                //背景边框的宽度
                .setBackgroundStrokeWidth(1)
                .build();

        //多种方法
        MToast.makeTextShort(mContext, "", config).show();

```

## 感谢：
#### 源码里面的MNProgressWheel 使用的是下面的这个库,改了个名字,以免冲突!
#### [materialish-progress](https://github.com/pnikosis/materialish-progress)


## 推荐:
Name | Describe |
--- | --- |
[GankMM](https://github.com/maning0303/GankMM) | （Material Design & MVP & Retrofit + OKHttp & RecyclerView ...）Gank.io Android客户端：每天一张美女图片，一个视频短片，若干Android，iOS等程序干货，周一到周五每天更新，数据全部由 干货集中营 提供,持续更新。 |
[MNUpdateAPK](https://github.com/maning0303/MNUpdateAPK) | Android APK 版本更新的下载和安装,适配7.0,简单方便。 |
[MNImageBrowser](https://github.com/maning0303/MNImageBrowser) | 交互特效的图片浏览框架,微信向下滑动动态关闭 |
[MNCalendar](https://github.com/maning0303/MNCalendar) | 简单的日历控件练习，水平方向日历支持手势滑动切换，跳转月份；垂直方向日历选取区间范围。 |
[MClearEditText](https://github.com/maning0303/MClearEditText) | 带有删除功能的EditText |
[MNCrashMonitor](https://github.com/maning0303/MNCrashMonitor) | Debug监听程序崩溃日志,展示崩溃日志列表，方便自己平时调试。 |
[MNProgressHUD](https://github.com/maning0303/MNProgressHUD) | MNProgressHUD是对常用的自定义弹框封装,加载ProgressDialog,状态显示的StatusDialog和自定义Toast,支持背景颜色,圆角,边框和文字的自定义。 |
[MNXUtilsDB](https://github.com/maning0303/MNXUtilsDB) | xUtils3 数据库模块单独抽取出来，方便使用。 |
[MNVideoPlayer](https://github.com/maning0303/MNVideoPlayer) | SurfaceView + MediaPlayer 实现的视频播放器，支持横竖屏切换，手势快进快退、调节音量，亮度等。------代码简单，新手可以看一看。 |
[MNZXingCode](https://github.com/maning0303/MNZXingCode) | 快速集成二维码扫描和生成二维码 |
[MNChangeSkin](https://github.com/maning0303/MNChangeSkin) | Android夜间模式，通过Theme实现 |
[SwitcherView](https://github.com/maning0303/SwitcherView) | 垂直滚动的广告栏文字展示。 |

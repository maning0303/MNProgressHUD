package com.maning.mnprogressdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maning.mndialoglibrary.MProgressDialog;
import com.maning.mndialoglibrary.MStatusDialog;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
    }

    @Override
    public void onBackPressed() {
//        MProgressDialog.showProgress(this, "");
//        new MStatusDialog(this).show("保存成功", getResources().getDrawable(R.drawable.mn_icon_dialog_ok));
        super.onBackPressed();
    }
}

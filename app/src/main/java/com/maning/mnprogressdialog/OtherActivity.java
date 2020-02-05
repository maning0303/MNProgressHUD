package com.maning.mnprogressdialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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

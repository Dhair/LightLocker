package com.dhair.light.locker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.common.util.ToastUtils;
import com.library.processutil.AndroidProcesses;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToastUtils.showToast("sdfsdfdsf");
        AndroidProcesses.isMyProcessInTheForeground();
    }
}

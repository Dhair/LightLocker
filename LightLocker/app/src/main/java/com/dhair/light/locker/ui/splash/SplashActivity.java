package com.dhair.light.locker.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.WindowManager;

import com.dhair.light.locker.R;
import com.dhair.light.locker.ui.abs.AbsActivity;
import com.dhair.light.locker.ui.home.HomeActivity;

/**
 * Created by i on 2016/5/10.
 */
public class SplashActivity extends AbsActivity implements IMessageHandler {
    private MessageHandler mMessageHandler = new MessageHandler(this);
    private final static int WHAT_DIRECT_HOME = 0x0001;
    private final static int WHAT_FINISH_MYSELF = 0x0002;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        return intent;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initWidgets() {
        mMessageHandler.sendEmptyMessageDelayed(WHAT_DIRECT_HOME, 1000);
    }

    @Override
    protected void initWidgetsActions() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case WHAT_DIRECT_HOME:
                Intent intent = HomeActivity.getIntent(SplashActivity.this);
                startActivity(intent);
                mMessageHandler.sendEmptyMessageDelayed(WHAT_FINISH_MYSELF, 350);
                break;
            case WHAT_FINISH_MYSELF:
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                finish();
                break;
        }
    }
}

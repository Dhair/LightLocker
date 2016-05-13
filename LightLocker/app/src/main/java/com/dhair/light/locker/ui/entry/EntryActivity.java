package com.dhair.light.locker.ui.entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dhair.light.locker.ui.splash.SplashActivity;

/**
 * Created by dengshengjin on 16/5/13.
 */
public class EntryActivity extends Activity {
    private static final String INTENT_THIRD_PARTY_ACTION = ".third.party.entry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispatchIntentAction();
    }

    private void dispatchIntentAction() {
        String intentThirdPartyAction = getPackageName() + INTENT_THIRD_PARTY_ACTION;
        if (getIntent() != null && intentThirdPartyAction.equals(getIntent().getAction())) {
            Intent intent = SplashActivity.getIntent(EntryActivity.this);
            startActivity(intent);
        } else {
            Intent intent = SplashActivity.getIntent(EntryActivity.this);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}

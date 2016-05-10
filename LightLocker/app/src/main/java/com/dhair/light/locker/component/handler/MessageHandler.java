package com.dhair.light.locker.component.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Creator: dengshengjin on 16/5/9 23:08
 * Email: deng.shengjin@zuimeia.com
 */
public class MessageHandler extends Handler {
    private WeakReference<IMessageHandler> mMessageWeakReference;

    public MessageHandler(IMessageHandler messageHandler) {
        super(Looper.getMainLooper());
        mMessageWeakReference = new WeakReference<>(messageHandler);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mMessageWeakReference != null) {
            IMessageHandler messageHandler = mMessageWeakReference.get();
            messageHandler.handleMessage(msg);
        }
    }
}

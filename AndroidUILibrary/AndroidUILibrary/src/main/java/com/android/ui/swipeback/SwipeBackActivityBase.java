package com.android.ui.swipeback;


public interface SwipeBackActivityBase {

    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void scrollToFinishActivity();

}

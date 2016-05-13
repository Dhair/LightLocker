package com.android.ui.swipeback;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;


/**
 * @author Yrom
 */
public class SwipeBackActivityHelper {
    private Activity mActivity;

    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackActivityHelper(Activity activity) {
        mActivity = activity;
    }

    @SuppressWarnings("deprecation")
    public void onActivityCreate() {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = new SwipeBackLayout(mActivity);
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
//        mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(mActivity).inflate(
//                me.imid.swipebacklayout.lib.R.layout.swipeback_layout, null);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                //调用以下方法会导致上一个activity的onStop方法调用，需根据情况处理
//                if (state == ViewDragHelper.STATE_IDLE) {
//                    ActivityTranslucentUtil.convertActivityFromTranslucent(mActivity);
//                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                ActivityTranslucentUtil.convertActivityToTranslucent(mActivity);
            }

            @Override
            public void onScrollOverThreshold() {
            }
        });
    }

    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(mActivity);
    }

    public View findViewById(int id) {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }
}

package com.android.ui.ripple;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.ui.library.R;

/**
 * Created by dengshengjin on 16/5/17.
 */
public class RippleView extends RelativeLayout {
    private RippleUIService mRippleUIService;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.RippleViewStyle);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mRippleUIService = new RippleUIService(this, false);
        mRippleUIService.performClickBeforeCircleDismiss();
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView,
                defStyleAttr, 0);
        if (typedArray != null) {
            int backgroundColor = typedArray.getColor(R.styleable.RippleView_RV_Background_color, RippleUIService.BG_COLOR);
            int circleColor = typedArray.getColor(R.styleable.RippleView_RV_Circle_color, RippleUIService.CIRCLE_COLOR);
            mRippleUIService.setColor(backgroundColor, circleColor);
            typedArray.recycle();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0 && mRippleUIService != null) {
            mRippleUIService.updateData(w, h);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mRippleUIService.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mRippleUIService.setOnClickListener(l);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mRippleUIService.setOnTouchListener(l);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRippleUIService.onDraw(canvas);
        super.onDraw(canvas);
    }
}

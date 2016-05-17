package com.android.ui.ripple;

/**
 * Created by dengshengjin on 16/5/17.
 */

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

import com.android.ui.handler.IMessageHandler;
import com.android.ui.handler.MessageHandler;
import com.android.ui.utils.DoubleInvalidateUtils;


/**
 * Created by blue on 15-4-10.
 */
public class RippleUIService implements IMessageHandler {

    private Paint mBgPaint;
    private Paint mCirclePaint;
    private RectF mBgRectF = new RectF();

    private ValueAnimator mCircleXAnim;
    private ValueAnimator mCircleYAnim;
    private ValueAnimator mCircleRadiusAnim;

    private ValueAnimator mCircleAlphaAnim;
    private ValueAnimator mBgAlphaAnim;

    private int mCircleAlphaStart = 255;
    private int mCircleAlphaEnd = 0;
    private int mBgAlphaStart = 70;
    private int mBgAlphaEnd = 255;

    private int mBgColor = BG_COLOR;
    public static final int BG_COLOR = 0xFFE9EDF2;
    private int mCircleColor = CIRCLE_COLOR;
    public static final int CIRCLE_COLOR = 0xFFD9E0E7;

    private float mWidth;
    private float mHeight;

    private float mCircleMaxRadius;
    public float mBgMaxRadius;
    private boolean mIsPlayClicked;
    private DoubleInvalidateUtils mDoubleInvalidateUtils;

    private boolean mDrawCircle = true;
    private boolean mPostingAnim = false;

    private static final int MSG_PLAY_CLICKED = 0;
    private static boolean sHandleNextClick = true;
    private boolean mClicked = false;

    private long mMoveSlowCircleDuration = 1200;
    private long mCircleHideSlow = 500;

    private long mCircleHideFastDuration = 250;
    private long mCircleHideFast = 150;

    private boolean mPerformClickBeforeCircleDismiss = false;
    private boolean finishAnim = true;
    private MessageHandler mMessageHandler = new MessageHandler(this);

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_PLAY_CLICKED:
                sHandleNextClick = true;
                if (null != mOnClickListener) {
                    mOnClickListener.onClick(mView);
                }
                break;
            default:
                break;
        }
    }

    public RippleUIService(View v, boolean isCircle) {
        mView = v;
        mDrawCircle = isCircle;
        init();
    }

    public RippleUIService(View v) {
        this(v, true);
    }

    private View mView = null;

    public void resetAlpha(int moveAlphaStart, int moveAlphaEnd, int backAlphaStart, int backAlphaEnd) {
        mCircleAlphaStart = moveAlphaStart;
        mCircleAlphaEnd = moveAlphaEnd;
        mBgAlphaStart = backAlphaStart;
        mBgAlphaEnd = backAlphaEnd;
    }

    public void setColor(int back, int move) {
        mBgColor = back;
        mCircleColor = move;
        if (null != mBgPaint) {
            mBgPaint.setColor(mBgColor);
        }
        if (null != mCirclePaint) {
            mCirclePaint.setColor(mCircleColor);
        }
    }

    public void setDuration(long fastMove, long fastHide, long slowMove, long slowHide) {
        mCircleHideFastDuration = fastMove;
        mCircleHideFast = fastHide;

        mMoveSlowCircleDuration = slowMove;
        mCircleHideSlow = slowHide;
    }


    public void finishAnim(boolean v) {
        finishAnim = v;
    }

    private void init() {
        mBgPaint = new Paint();
        mBgPaint.setColor(mBgColor);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setAlpha(0);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setAlpha(0);

        mDoubleInvalidateUtils = new DoubleInvalidateUtils() {
            @Override
            protected void onInvalidate() {
                super.onInvalidate();
                mView.invalidate();
            }
        };
    }

    float mRectLeft = 0;
    float mRectRight = 0;
    float mRectTop = 0;
    float mRectBottom = 0;

    public void setOrigin(float left, float right, float top, float bottom) {
        mRectLeft = left;
        mRectRight = right;
        mRectTop = top;
        mRectBottom = bottom;
    }

    public void updateData(float width, float height) {
        mWidth = width;
        mHeight = height;
        float diagonal = (float) Math.sqrt(Math.pow(mHeight, 2) + Math.pow(mWidth, 2));
        mCircleMaxRadius = mDrawCircle ? Math.min(mHeight, mWidth) / 2.2f : diagonal / 2f;
        mBgMaxRadius = mDrawCircle ? Math.min(mHeight, mWidth) / 2.2f : diagonal / 2f;
        onBgMaxRadiusCal(mBgMaxRadius);
        mBgRectF.set(mRectLeft, mRectRight, mWidth, mHeight);
        mView.invalidate();
    }

    protected void onBgMaxRadiusCal(float bgMaxRadius) {

    }

    public void performClickBeforeCircleDismiss() {
        mPerformClickBeforeCircleDismiss = true;
    }


    private void onDismissCircleEvent(long duration, long startDelay, final boolean needPerformClick) {
        mCircleAlphaAnim = ValueAnimator.ofInt(mCircleAlphaStart, mCircleAlphaEnd).setDuration(duration);
        mCircleAlphaAnim.setInterpolator(new LinearInterpolator());
        mCircleAlphaAnim.setStartDelay(startDelay);
        mCircleAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                mCirclePaint.setAlpha(value);
                mDoubleInvalidateUtils.update(false);
            }
        });

        mCircleAlphaAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (null != mBgAlphaAnim) {
                    mBgAlphaAnim.cancel();
                }
                mBgPaint.setAlpha(0);
                mDoubleInvalidateUtils.update(false);
                if (needPerformClick) {
                    mMessageHandler.removeMessages(MSG_PLAY_CLICKED);
                    mMessageHandler.sendEmptyMessageDelayed(MSG_PLAY_CLICKED, mPerformClickBeforeCircleDismiss ? 0 : mCircleHideFast);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mDoubleInvalidateUtils.update(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mCircleAlphaAnim.start();
    }

    Runnable mAnimRunnable = new Runnable() {
        @Override
        public void run() {
            mPostingAnim = false;
            onDismissBgEvent(140);
        }
    };

    private void removeAnimRunnable() {
        mView.removeCallbacks(mAnimRunnable);
    }


    public void onTouchEvent(MotionEvent ev) {
        if (!sHandleNextClick && !mClicked) {
            return;
        }
        if (mOnTouchListener != null) {
            mOnTouchListener.onTouch(mView, ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mClicked = true;
                sHandleNextClick = false;
                resetData();
                mCenterX = ev.getX();
                mCenterY = ev.getY();
                if (mPostingAnim) {
                    removeAnimRunnable();
                }
                mPostingAnim = true;
                mView.postDelayed(mAnimRunnable, ViewConfiguration.getTapTimeout());
                break;
            case MotionEvent.ACTION_UP:
                if (!mIsPlayClicked) {
                    if (mPostingAnim) {
                        removeAnimRunnable();
                        mAnimRunnable.run();
                    }
                    mIsPlayClicked = true;
                    onTouchActionUp(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsPlayClicked && !mBgRectF.contains(ev.getX(), ev.getY())) {
                    removeAnimRunnable();
                    mIsPlayClicked = true;
                    onTouchActionUp(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mPostingAnim) {
                    removeAnimRunnable();
                }
                if (!mIsPlayClicked) {
                    mIsPlayClicked = true;
                    onTouchActionUp(false);
                }
                break;
            default:
                break;
        }
    }

    private void resetData() {
        mIsPlayClicked = false;
        mCurrCircleRadius = 0;
        if (null != mBgPaint) {
            mBgPaint.setAlpha(0);
        }
        if (null != mCirclePaint) {
            mCirclePaint.setAlpha(0);
        }
    }

    float mCenterX;
    float mCenterY;
    float mCurrCircleRadius;

    private void onDismissBgEvent(long duration) {
        mBgAlphaAnim = ValueAnimator.ofInt(mBgAlphaStart, mBgAlphaEnd).setDuration(duration);
        mBgAlphaAnim.setInterpolator(new LinearInterpolator());
        mBgAlphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                mBgPaint.setAlpha(value);
                mDoubleInvalidateUtils.update(false);
            }
        });

        mBgAlphaAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!mIsPlayClicked) {
                    onShowCircleEvent(mMoveSlowCircleDuration);
                }
                mDoubleInvalidateUtils.update(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mBgAlphaAnim.start();
    }

    private void onTouchActionUp(boolean onMotionEventUp) {
        mClicked = false;
        if (!onMotionEventUp) {
            sHandleNextClick = true;
        }
        if (mCircleXAnim != null && mCircleXAnim.isRunning() || mBgAlphaAnim != null && mBgAlphaAnim.isRunning()) {
            boolean isMoveAnim = (mCircleXAnim != null && mCircleXAnim.isRunning());
            if (null != mCircleAlphaAnim) {
                mCircleAlphaAnim.cancel();
            }
            if (null != mBgAlphaAnim) {
                mBgAlphaAnim.cancel();
            }
            if (null != mCircleXAnim) {
                mCircleXAnim.cancel();
            }
            if (null != mCircleYAnim) {
                mCircleYAnim.cancel();
            }
            float fraction = 1f;
            if (null != mCircleRadiusAnim) {
                if (mCircleRadiusAnim.isRunning()) {
                    fraction = 1 - mCircleRadiusAnim.getAnimatedFraction();
                }
                mCircleRadiusAnim.cancel();
            }
            if (null != mCirclePaint) {
                mCirclePaint.setAlpha(mCircleAlphaStart);
            }
            if (onMotionEventUp || isMoveAnim || finishAnim) {
                onShowCircleEvent((long) (mCircleHideFastDuration * fraction));
            }
            onDismissCircleEvent(mCircleHideFast, (long) (mCircleHideFastDuration * fraction), onMotionEventUp);
        } else {
            if (null != mCircleAlphaAnim) {
                mCircleAlphaAnim.cancel();
            }
            if (null != mBgAlphaAnim) {
                mBgAlphaAnim.cancel();
            }
            if (null != mBgPaint) {
                mBgPaint.setAlpha(0);
                mDoubleInvalidateUtils.update(false);
            }
            onDismissCircleEvent(mCircleHideSlow, 0, onMotionEventUp);
        }
    }

    private void onShowCircleEvent(long duration) {
        mCirclePaint.setAlpha(mCircleAlphaStart);
        mCircleXAnim = ValueAnimator.ofFloat(mCenterX, mWidth / 2).setDuration(duration);
        mCircleXAnim.setInterpolator(new LinearInterpolator());
        mCircleXAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                mCenterX = value;
            }
        });
        mCircleXAnim.start();

        mCircleYAnim = ValueAnimator.ofFloat(mCenterY, mHeight / 2).setDuration(duration);
        mCircleYAnim.setInterpolator(new LinearInterpolator());
        mCircleYAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                mCenterY = value;
            }
        });
        mCircleYAnim.start();

        mCircleRadiusAnim = ValueAnimator.ofFloat(mCurrCircleRadius, mCircleMaxRadius).setDuration(duration);
        mCircleRadiusAnim.setInterpolator(new LinearInterpolator());
        mCircleRadiusAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (Float) valueAnimator.getAnimatedValue();
                mCurrCircleRadius = value;
                mDoubleInvalidateUtils.update(false);
            }
        });
        mCircleRadiusAnim.start();
    }


    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener l) {
        mOnClickListener = l;
    }

    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    private View.OnTouchListener mOnTouchListener;

    public void setOnTouchListener(View.OnTouchListener l) {
        mOnTouchListener = l;
    }

    public void onDraw(Canvas canvas) {

        canvas.save();
        canvas.clipRect(mRectLeft, mRectTop, mWidth - mRectLeft, mHeight);
        if (null != mBgPaint && 0 != mBgPaint.getAlpha()) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mBgMaxRadius, mBgPaint);
        }
        if (null != mCirclePaint && 0 != mCirclePaint.getAlpha()) {
            canvas.drawCircle(mCenterX, mCenterY, mCurrCircleRadius, mCirclePaint);
        }
        canvas.restore();
    }


}


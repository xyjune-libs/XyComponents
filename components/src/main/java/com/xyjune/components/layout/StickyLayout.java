package com.xyjune.components.layout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xyjune.components.R;

@Deprecated
public class StickyLayout extends LinearLayout {

    private static final String TAG = "StickyLayout";

    private static final int STATE_EXPAND = 0;
    private static final int STATE_COLLAPSED = 1;

    private final int sticky_id;
    private View stickyView;
    private final int mMaxHeight;

    private int mCurrStickyHeight;
    private int mScaledTouchSlop;

    private int mLastInterceptX;
    private int mLastInterceptY;
    private int mLastX;
    private int mLastY;

    private int mState = STATE_COLLAPSED;

    private GiveUpTouchEventListener mGiveUpTouchEventListener;

    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StickyLayout);
        sticky_id = typedArray.getResourceId(R.styleable.StickyLayout_Sticky_id, -1);
        mMaxHeight = typedArray.getDimensionPixelOffset(R.styleable.StickyLayout_Sticky_height, 0);
        Log.d(TAG, "StickyLayout: " + sticky_id);
        typedArray.recycle();
    }

    public void setGiveUpTouchEventListener(GiveUpTouchEventListener giveUpTouchEventListener) {
        mGiveUpTouchEventListener = giveUpTouchEventListener;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (stickyView == null) {
                stickyView = findViewById(sticky_id);
            }
            mCurrStickyHeight = stickyView.getMeasuredHeight();
            mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastInterceptX = x;
                mLastInterceptY = y;
                mLastX = x;
                mLastY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastInterceptX;
                int dy = y - mLastInterceptY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercept = false;
                } else if (mState == STATE_EXPAND && dy <= -mScaledTouchSlop) {
                    intercept = true;
                } else if (mState == STATE_COLLAPSED && dy > mScaledTouchSlop) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                Log.d(TAG, "onInterceptTouchEvent: intercept=" + intercept + ", mCurrStickyHeight=" + mCurrStickyHeight + ", x=" + x +
                        ", y=" + y + ", dx=" + dx + ", dy=" + dy + ", mScaledTouchSlop=" + mScaledTouchSlop);
                break;
            case MotionEvent.ACTION_UP:
                mLastInterceptX = 0;
                mLastInterceptY = 0;
                intercept = false;
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                setStickyHeight(mCurrStickyHeight + dy);
                Log.d(TAG, "onTouchEvent: mLastX=" + mLastX + ", mLastY=" + mLastY + ", dx=" + dx + ", dy=" + dy + ", mCurrStickyHeight=" + mCurrStickyHeight);
                break;
            case MotionEvent.ACTION_UP:
                int dest = 0;
                if (getStickyHeight() <= mMaxHeight / 2) {
                    mState = STATE_COLLAPSED;
                } else {
                    dest = mMaxHeight;
                    mState = STATE_EXPAND;
                }
                mCurrStickyHeight = dest;
                smoothSetStickyHeight(getStickyHeight(), dest);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void smoothSetStickyHeight(int from, int to) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to).setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setStickyHeight((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void setStickyHeight(int height) {
        if (height <= 0) {
            height = 0;
        } else if (height >= mMaxHeight) {
            height = mMaxHeight;
        }
        if (height == 0) {
            mState = STATE_COLLAPSED;
        } else {
            mState = STATE_EXPAND;
        }
        if (stickyView != null) {
            stickyView.getLayoutParams().height = height;
            stickyView.requestLayout();
        }
    }

    private int getStickyHeight() {
        return stickyView.getLayoutParams().height;
    }

    public interface GiveUpTouchEventListener {
        boolean giveUpTouchEvent();
    }
}

package com.xyjune.components.widget.loading;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.xyjune.components.R;

public class CircularRing extends View {

    private float width = 0f, padding = 0f, startAngle = 0f;
    private Paint paint;
    private int bgColor = Color.argb(100, 255, 255, 255);
    private int barColor = Color.argb(200, 255, 255, 255);
    ValueAnimator valueAnimator;

    public CircularRing(Context context) {
        this(context, null);
    }

    public CircularRing(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularRing(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularRing);
        bgColor = array.getColor(R.styleable.CircularRing_bgColor, bgColor);
        barColor = array.getColor(R.styleable.CircularRing_barColor, barColor);
        array.recycle();
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getHeight()) {
            width = getMeasuredHeight();
        } else {
            width = getMeasuredWidth();
        }
        padding = 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制圆
        paint.setColor(bgColor);
        canvas.drawCircle(width / 2, width / 2, width / 2 - padding, paint);

        // 绘制圆弧
        paint.setColor(barColor);
        @SuppressLint("DrawAllocation")
        RectF rectF = new RectF(padding, padding, width - padding, width - padding);
        canvas.drawArc(rectF, startAngle, 100, false, paint);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true); // 抗锯齿
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
    }

    public void startAnim() {
        stopAnim();
        startViewAnim(0f, 1f, 1000);
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();
            valueAnimator.setRepeatCount(1);
            valueAnimator.cancel();
            valueAnimator.end();
        }
    }

    private ValueAnimator startViewAnim(float startF, float endF, long time) {
        valueAnimator = ValueAnimator.ofFloat(startF, endF);

        valueAnimator.setDuration(time);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                startAngle = 360 * value;
                invalidate();
            }
        });

        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }

        return valueAnimator;
    }
}

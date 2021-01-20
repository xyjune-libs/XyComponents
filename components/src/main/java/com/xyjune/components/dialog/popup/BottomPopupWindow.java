package com.xyjune.components.dialog.popup;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xyjune.components.dialog.base.BasePopupWindow;


public class BottomPopupWindow extends BasePopupWindow {

    private LinearLayout view;
    private int windowW;
    private int viewH;

    public BottomPopupWindow(Context context) {
        super(context);
        DisplayMetrics outMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        windowW = outMetrics.widthPixels;
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void setContentView(View contentView) {
        view = new LinearLayout(getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(windowW, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        view.addView(contentView);
        super.setContentView(view);
    }

    @Override
    public void setBackgroundDrawable(Drawable backgroundDrawable) {
        if (view != null) {
            view.setBackground(backgroundDrawable);
            setOutsideTouchable(isOutsideTouchable());
        }
    }

    public void show() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        viewH = view.getMeasuredHeight();
        showAtLocation(view, Gravity.BOTTOM | Gravity.START, 0, 0);
        showTranslationAnimator().start();
        showAnimator().start();
    }

    @Override
    public void dismiss() {
        dismissTranslationAnimator().start();
        dismissAnimator().start();
    }

    private ValueAnimator showTranslationAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(viewH, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setY((Float) animation.getAnimatedValue());
            }
        });
        animator.setDuration(360);
        return animator;
    }

    private ValueAnimator dismissTranslationAnimator() {
        final float dismissH = viewH + 10;
        ValueAnimator animator = ValueAnimator.ofFloat(0, dismissH);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y = (Float) animation.getAnimatedValue();
                view.setY(y);
                if (y >= dismissH) {
                    BottomPopupWindow.super.dismiss();
                }
            }
        });
        animator.setDuration(260);
        return animator;
    }
}

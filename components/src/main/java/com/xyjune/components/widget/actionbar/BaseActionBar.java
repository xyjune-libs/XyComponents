package com.xyjune.components.widget.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.xyjune.components.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("SameParameterValue")
public class BaseActionBar extends LinearLayout {

    private static final String TAG = "BaseActionBar";

    public static final int CENTER = 1;
    public static final int LEFT = 2;

    @IntDef({CENTER, LEFT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TitleGravity {
    }

    private RelativeLayout mRelativeLayout;
    private TextView mTitle;
    private boolean includeStatus;

    public BaseActionBar(Context context) {
        this(context, null);
    }

    public BaseActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int paddingStartAndEnd = dip2px(context, 15);
        initTitle();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseActionBar);
        includeStatus = typedArray.getBoolean(R.styleable.BaseActionBar_titleStatusBarInclude, false);
        setPadding(paddingStartAndEnd, includeStatus ? getStatusBarHeight(getContext()) : 0, paddingStartAndEnd, 0);
        String title = typedArray.getString(R.styleable.BaseActionBar_titleText);
        setTitle(title);
        int size = typedArray.getDimensionPixelSize(R.styleable.BaseActionBar_titleSize, sp2px(context, 16));
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        int color = typedArray.getColor(R.styleable.BaseActionBar_titleColor, Color.parseColor("#333333"));
        setTitleColor(color);
        int gravity = typedArray.getInt(R.styleable.BaseActionBar_titleGravity, CENTER);
        setGravity(gravity);
        typedArray.recycle();
    }

    private void addStatusBarHeight(int height) {
        View view = new View(getContext());
        super.addView(view, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (includeStatus) {
            int originalHeight = getMeasuredHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRelativeLayout.getLayoutParams();
            layoutParams.height = originalHeight;
            mRelativeLayout.setLayoutParams(layoutParams);
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + getStatusBarHeight(getContext()));
        }
    }

    private void initTitle() {
        mRelativeLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRelativeLayout.setLayoutParams(layoutParams);
        mTitle = new TextView(getContext());
        mTitle.setSingleLine();
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setMaxEms(10);
        mTitle.setTypeface(Typeface.DEFAULT_BOLD);
        mRelativeLayout.addView(mTitle, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mRelativeLayout);
    }

    public void addTitleView(View child, int index) {
        mRelativeLayout.addView(child, index);
    }

    public void addTitleView(View child, int index, ViewGroup.LayoutParams params) {
        mRelativeLayout.addView(child, index, params);
    }

    public void addTitleView(View child, ViewGroup.LayoutParams params) {
        mRelativeLayout.addView(child, params);
    }

    public void addTitleView(View child) {
        mRelativeLayout.addView(child);
    }

    @Override
    public void setGravity(@TitleGravity int gravity) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTitle.getLayoutParams();
        switch (gravity) {
            case CENTER:
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
            case LEFT:
            default:
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                break;
        }
        mTitle.setLayoutParams(layoutParams);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setTitle(int titleId) {
        mTitle.setText(titleId);
    }

    public void setTitleSize(float size) {
        mTitle.setTextSize(size);
    }

    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    protected int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    protected int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    //获取状态栏高度
    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

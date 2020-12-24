package com.xyjune.components.widget.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xyjune.components.R;

public class SubtextItem extends BaseItem {

    protected TextView mSubtext;
    private final String subtextStr;
    private final int subtextColor;
    private final float subtextSize;

    public SubtextItem(Context context) {
        this(context, null);
    }

    public SubtextItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubtextItem);
        subtextStr = typedArray.getString(R.styleable.SubtextItem_xy_subtext);
        subtextColor = typedArray.getColor(R.styleable.SubtextItem_xy_subtextColor, ContextCompat.getColor(context, R.color.def_subtext));
        subtextSize = typedArray.getDimensionPixelSize(R.styleable.SubtextItem_xy_subtextSize, dip2px(context, 14));
        typedArray.recycle();
        initSubtext();
    }

    protected void initSubtext() {
        mSubtext = new TextView(this.getContext());
        mSubtext.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtextSize);
        if (subtextStr != null) {
            mSubtext.setText(subtextStr);
        }
        mSubtext.setTextColor(subtextColor);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(mDefMargin);
        this.addView(mSubtext, params);
    }

    public void setSubtext(CharSequence charSequence) {
        if (mSubtext != null) {
            mSubtext.setText(charSequence);
        }
    }

    public String getSubtextStr() {
        return mSubtext != null ? mSubtext.getText().toString() : null;
    }
}
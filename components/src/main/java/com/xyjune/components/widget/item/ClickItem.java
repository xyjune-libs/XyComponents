package com.xyjune.components.widget.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.xyjune.components.R;

public final class ClickItem extends SubtextItem {

    private ImageView mClickIcon;

    private int clickIcon;
    private int clickIconWidth;
    private int clickIconHeight;

    public ClickItem(Context context) {
        super(context);
        initClickItem();
    }

    public ClickItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickItem);
        clickIcon = typedArray.getResourceId(R.styleable.ClickItem_xy_clickIcon, -1);
        clickIconWidth = typedArray.getDimensionPixelSize(R.styleable.ClickItem_xy_clickIcon_width, dip2px(getContext(), 15));
        clickIconHeight = typedArray.getDimensionPixelSize(R.styleable.ClickItem_xy_clickIcon_height, dip2px(getContext(), 15));
        typedArray.recycle();
        initClickItem();
    }

    private void initClickItem() {
        mClickIcon = new ImageView(getContext());
        setClickable(true);
        if (clickIcon != -1) {
            mClickIcon.setImageResource(clickIcon);
        } else {
            mClickIcon.setVisibility(GONE);
        }
        LayoutParams params = new LayoutParams(clickIconWidth, clickIconHeight);
        params.setMarginStart(mDefMargin);
        this.addView(mClickIcon, params);
    }

    public void setClickIcon(int icon) {
        mClickIcon.setImageResource(icon);
        mClickIcon.setVisibility(VISIBLE);
    }

    public void setClickIcon(Bitmap bitmap) {
        mClickIcon.setImageBitmap(bitmap);
        mClickIcon.setVisibility(VISIBLE);
    }

    public void setClickIcon(Drawable drawable) {
        mClickIcon.setImageDrawable(drawable);
        mClickIcon.setVisibility(VISIBLE);
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        if (mClickIcon != null) {
            if (clickable) {
                mClickIcon.setVisibility(View.VISIBLE);
            } else {
                mClickIcon.setVisibility(View.GONE);
            }
        }
    }
}

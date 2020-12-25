package com.xyjune.components.widget.actionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xyjune.components.R;

public class DDActionBar extends BaseActionBar {

    public static final int NO_TOOL = 0x0000;
    public static final int LEFT_TOOL = 0x0002;
    public static final int RIGHT_TOOL = 0x0004;

    public static final int SHOW_TEXT = 0;
    public static final int SHOW_ICON = 4;

    public static final int LEFT_TEXT = LEFT_TOOL << SHOW_TEXT;                 // 0000010
    public static final int LEFT_ICON = LEFT_TOOL << SHOW_ICON;                 // 0100000

    public static final int RIGHT_TEXT = RIGHT_TOOL << SHOW_TEXT;               // 0000100
    public static final int RIGHT_ICON = RIGHT_TOOL << SHOW_ICON;               // 1000000

    public static final int TEXT_TEXT = (LEFT_TOOL | RIGHT_TOOL) << SHOW_TEXT;  // 0000110
    public static final int ICON_ICON = (LEFT_TOOL | RIGHT_TOOL) << SHOW_ICON;  // 1100000

    public static final int TEXT_ICON = LEFT_TEXT | RIGHT_ICON;                 // 1000010
    public static final int ICON_TEXT = LEFT_ICON | RIGHT_TEXT;                 // 0100100

    private TextView mLeftText;
    private TextView mRightText;
    private ImageView mLeftImg;
    private ImageView mRightImg;

    private LinearLayout mLeftView;
    private LinearLayout mRightView;

    private ActionBarListener mActionBarListener;

    public DDActionBar(Context context) {
        this(context, null);
    }

    public DDActionBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLeft();
        initRight();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DDActionBar);

        int mode = typedArray.getInt(R.styleable.DDActionBar_titleMode, NO_TOOL);
        setMode(mode);

        int defColor = Color.parseColor("#333333");
        int defTextSize = sp2px(context, 14);

        String leftText = typedArray.getString(R.styleable.DDActionBar_leftText);
        if (!TextUtils.isEmpty(leftText))
            mLeftText.setText(leftText);
        int leftTextSize = typedArray.getDimensionPixelSize(R.styleable.DDActionBar_leftTextSize, defTextSize);
        mLeftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        int color = typedArray.getColor(R.styleable.DDActionBar_leftTextColor, defColor);
        mLeftText.setTextColor(color);
        int leftIconId = typedArray.getResourceId(R.styleable.DDActionBar_leftIcon, -1);
        if (leftIconId != -1) {
            mLeftImg.setImageResource(leftIconId);
        }
        int leftIconWidth = typedArray.getDimensionPixelOffset(R.styleable.DDActionBar_leftIconWidth, LayoutParams.WRAP_CONTENT);
        int leftIconHeight = typedArray.getDimensionPixelOffset(R.styleable.DDActionBar_leftIconHeight, LayoutParams.MATCH_PARENT);
        LayoutParams leftIconParams = (LayoutParams) mLeftImg.getLayoutParams();
        leftIconParams.width = leftIconWidth;
        leftIconParams.height = leftIconHeight;
        mLeftImg.setLayoutParams(leftIconParams);
        int leftIconColor = typedArray.getColor(R.styleable.DDActionBar_leftIconTint, -1);
        if (leftIconColor != -1)
            mLeftImg.setColorFilter(leftIconColor);

        String rightText = typedArray.getString(R.styleable.DDActionBar_rightText);
        if (!TextUtils.isEmpty(rightText))
            mRightText.setText(rightText);
        int rightTextSize = typedArray.getDimensionPixelSize(R.styleable.DDActionBar_rightTextSize, defTextSize);
        mRightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        int rightTextColor = typedArray.getColor(R.styleable.DDActionBar_rightTextColor, defColor);
        mRightText.setTextColor(rightTextColor);
        int rightIconId = typedArray.getResourceId(R.styleable.DDActionBar_rightIcon, -1);
        if (rightIconId != -1) {
            mRightImg.setImageResource(rightIconId);
        }
        int rightIconWidth = typedArray.getDimensionPixelOffset(R.styleable.DDActionBar_rightIconWidth, LayoutParams.WRAP_CONTENT);
        int rightIconHeight = typedArray.getDimensionPixelOffset(R.styleable.DDActionBar_rightIconHeight, LayoutParams.MATCH_PARENT);
        LayoutParams rightIconParams = (LayoutParams) mRightImg.getLayoutParams();
        rightIconParams.width = rightIconWidth;
        rightIconParams.height = rightIconHeight;
        mRightImg.setLayoutParams(rightIconParams);
        int rightIconColor = typedArray.getColor(R.styleable.DDActionBar_rightIconTint, -1);
        if (rightIconColor != -1)
            mLeftImg.setColorFilter(rightIconColor);

        typedArray.recycle();
    }

    /**
     * 右边按钮布局
     */
    private void initRight() {
        mRightView = new LinearLayout(getContext());
        mRightView.setGravity(Gravity.CENTER);
        int padding = dip2px(getContext(), 5);
        mRightView.setPadding(padding, 0, padding, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        addTitleView(mRightView, layoutParams);
        mRightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionBarListener != null)
                    mActionBarListener.onRightClick(v);
            }
        });

        // 文字按钮
        mRightText = new TextView(getContext());
        mRightView.addView(mRightText, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // 图标按钮
        mRightImg = new ImageView(getContext());
        mRightImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mRightView.addView(mRightImg, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 左边按钮布局
     */
    private void initLeft() {
        mLeftView = new LinearLayout(getContext());
        mLeftView.setGravity(Gravity.CENTER);
        int padding = dip2px(getContext(), 5);
        mLeftView.setPadding(padding, 0, padding, 0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        addTitleView(mLeftView, 0, layoutParams);
        mLeftView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionBarListener != null)
                    mActionBarListener.onLeftClick(v);
            }
        });

        // 文字按钮
        mLeftText = new TextView(getContext());
        mLeftView.addView(mLeftText, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // 图标按钮
        mLeftImg = new ImageView(getContext());
        mLeftImg.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mLeftView.addView(mLeftImg, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setActionBarListener(ActionBarListener actionBarListener) {
        mActionBarListener = actionBarListener;
    }

    public interface ActionBarListener {
        void onLeftClick(View v);

        void onRightClick(View v);
    }

    public void setLeftText(String text) {
        setText(text);
    }

    public void setRightText(String text) {
        setText("", text);
    }

    public void setText(String... text) {
        if (text.length > 2) {
            throw new IllegalArgumentException("参数最多两个");
        }
        TextView[] textViews = {mLeftText, mRightText};
        for (int i = 0; i < text.length; i++) {
            if (!TextUtils.isEmpty(text[i]))
                textViews[i].setText(text[i]);
        }
    }

    public void setLeftTextColor(int color) {
        setTextColor(color);
    }

    public void setRightTextColor(int color) {
        setTextColor(-1, color);
    }

    public void setTextColor(int... colors) {
        if (colors.length > 2) {
            throw new IllegalArgumentException("参数最多两个");
        }
        TextView[] textViews = {mLeftText, mRightText};
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] != -1)
                textViews[i].setTextColor(colors[i]);
        }
    }

    public void setLeftIconColorFilter(int color) {
        setIconColorFilter(color);
    }

    public void setRightIconColorFilter(int color) {
        setIconColorFilter(-1, color);
    }

    public void setIconColorFilter(int... colors) {
        if (colors.length > 2) {
            throw new IllegalArgumentException("参数最多两个");
        }
        ImageView[] views = {mLeftImg, mRightImg};
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] != -1)
                views[i].setColorFilter(colors[i]);
        }
    }

    public void setLeftIcon(int id) {
        setIcon(id);
    }

    public void setRightIcon(int id) {
        setIcon(-1, id);
    }

    public void setIcon(int... id) {
        if (id.length > 2) {
            throw new IllegalArgumentException("参数最多两个");
        }
        ImageView[] views = {mLeftImg, mRightImg};
        for (int i = 0; i < id.length; i++) {
            if (id[i] != -1)
                views[i].setImageResource(id[i]);
        }
    }

    public void setLeftTextSize(float size) {
        setTextSize(size);
    }

    public void setRightTextSize(float size) {
        setTextSize(-1, size);
    }

    public void setTextSize(float... size) {
        if (size.length > 2) {
            throw new IllegalArgumentException("参数最多两个");
        }
        TextView[] textViews = {mLeftText, mRightText};
        for (int i = 0; i < size.length; i++) {
            if (size[i] != -1)
                textViews[i].setTextSize(size[i]);
        }
    }

    public void setLeftIconSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = mLeftImg.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mLeftImg.setLayoutParams(layoutParams);
    }

    public void setRightIconSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = mRightImg.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mRightImg.setLayoutParams(layoutParams);
    }

    public void setMode(int mode) {
        if ((mode & LEFT_TEXT) == LEFT_TEXT) {
            setLeftMode(0);
        } else if ((mode & LEFT_ICON) == LEFT_ICON) {
            setLeftMode(1);
        } else {
            setLeftMode(-1);
        }

        if ((mode & RIGHT_TEXT) == RIGHT_TEXT) {
            setRightMode(0);
        } else if ((mode & RIGHT_ICON) == RIGHT_ICON) {
            setRightMode(1);
        } else {
            setRightMode(-1);
        }
    }

    private void setLeftMode(int i) {
        switch (i) {
            case -1:
                mLeftText.setVisibility(GONE);
                mLeftImg.setVisibility(GONE);
                mLeftView.setVisibility(GONE);
                break;
            case 0:
                mLeftView.setVisibility(VISIBLE);
                mLeftText.setVisibility(VISIBLE);
                mLeftImg.setVisibility(GONE);
                break;
            case 1:
                mLeftView.setVisibility(VISIBLE);
                mLeftText.setVisibility(GONE);
                mLeftImg.setVisibility(VISIBLE);
                break;
        }
    }

    private void setRightMode(int i) {
        switch (i) {
            case -1:
                mRightView.setVisibility(GONE);
                mRightText.setVisibility(GONE);
                mRightImg.setVisibility(GONE);
                break;
            case 0:
                mRightView.setVisibility(VISIBLE);
                mRightText.setVisibility(VISIBLE);
                mRightImg.setVisibility(GONE);
                break;
            case 1:
                mRightView.setVisibility(VISIBLE);
                mRightText.setVisibility(GONE);
                mRightImg.setVisibility(VISIBLE);
                break;
        }
    }
}

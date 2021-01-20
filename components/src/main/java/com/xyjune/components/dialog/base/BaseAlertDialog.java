package com.xyjune.components.dialog.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.xyjune.components.R;

public abstract class BaseAlertDialog<D extends BaseAlertDialog> extends BaseDialog<D> {

    private String titleStr;
    private int titleTextColor;
    private int titleTextSize;
    private int dividerColor;

    private String leftBtnText;
    private int leftBtnTextColor;
    private int leftBtnTextSize;
    private String rightBtnText;
    private int rightBtnTextColor;
    private int rightBtnTextSize;

    private OnLeftClickListener leftOnClickListener;
    private OnRightClickListener rightOnClickListener;

    private LinearLayout mRoom;

    public BaseAlertDialog(@NonNull Context context) {
        super(context);
        titleTextColor = ContextCompat.getColor(getContext(), R.color.def_title_textColor);
        dividerColor = ContextCompat.getColor(getContext(), R.color.def_divider_color);
        titleTextSize = sp2px(getContext(), 16);
        leftBtnTextSize = sp2px(getContext(), 14);
        rightBtnTextSize = sp2px(getContext(), 14);
        leftBtnTextColor = ContextCompat.getColor(getContext(), R.color.def_leftButton_textColor);
        rightBtnTextColor = ContextCompat.getColor(getContext(), R.color.def_rightButton_textColor);
    }

    @Override
    protected View createView() {

        mRoom = new LinearLayout(getContext());
        mRoom.setOrientation(LinearLayout.VERTICAL);

        createTitle();
        createCenter();
        createBottom();
        return mRoom;
    }

    private void createTitle() {
        if (TextUtils.isEmpty(titleStr))
            return;
        TextView titleView = new TextView(getContext());
        titleView.setText(titleStr);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        titleView.setTextColor(titleTextColor);
        int padding = dip2px(getContext(), 15);
        titleView.setPadding(padding, padding, padding, padding);
        titleView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleView.setSingleLine();
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        mRoom.addView(titleView, params);
        addDivider();
    }

    protected abstract View createContent();

    private void createCenter() {
        View content = createContent();
        if (content == null) {
            throw new IllegalArgumentException("You must create the view at method " + getClass().getName() + ".createContent()");
        }
        ScrollView scrollView = new ScrollView(getContext());
        LinearLayout centerLayout = new LinearLayout(getContext());
        centerLayout.setGravity(Gravity.CENTER);
        int padding = dip2px(getContext(), 15);
        centerLayout.setPadding(padding, padding, padding, padding);
        centerLayout.addView(content);
        centerLayout.setMinimumHeight(dip2px(getContext(), 80));
        scrollView.addView(centerLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.weight = 1;
        mRoom.addView(scrollView, layoutParams);
    }

    private void createBottom() {
        if (!TextUtils.isEmpty(leftBtnText) || !TextUtils.isEmpty(rightBtnText)) {
            addDivider();
            LinearLayout bottomLayout = new LinearLayout(getContext());
            bottomLayout.setOrientation(LinearLayout.HORIZONTAL);
            bottomLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.xy_def_divider));
            bottomLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

            if (!TextUtils.isEmpty(leftBtnText)) {
                Button leftButton = createBottomButton(leftBtnText, leftBtnTextSize, leftBtnTextColor);
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (leftOnClickListener != null)
                            leftOnClickListener.onClick(v);
                        cancel();
                    }
                });
                bottomLayout.addView(leftButton);
            }

            if (!TextUtils.isEmpty(rightBtnText)) {
                Button rightButton = createBottomButton(rightBtnText, rightBtnTextSize, rightBtnTextColor);
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (rightOnClickListener != null)
                            rightOnClickListener.onClick(v);
                        cancel();
                    }
                });
                bottomLayout.addView(rightButton);
            }

            mRoom.addView(bottomLayout);
        }
    }

    private Button createBottomButton(String text, int size, int color) {
        Button button = new Button(getContext());
        button.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.xy_def_item_press));
        button.setText(text);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        button.setTextColor(color);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        button.setLayoutParams(params);
        return button;
    }

    // 添加一条divider
    private void addDivider() {
        View view = new View(getContext());
        view.setBackgroundColor(dividerColor);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                dip2px(getContext(), 1));
        mRoom.addView(view, params);
    }

    public D setTitle(String title) {
        titleStr = title;
        return (D) this;
    }

    public D setTitleTextColor(int color) {
        titleTextColor = color;
        return (D) this;
    }

    public D setTitleTextSize(int spSize) {
        titleTextSize = sp2px(getContext(), spSize);
        return (D) this;
    }

    public D setButtonText(String... btnTexts) {
        if (btnTexts.length < 1 || btnTexts.length > 2) {
            throw new IllegalArgumentException("range of param btnTexts length is [1,2]!");
        }

        if (btnTexts.length == 1) {
            leftBtnText = btnTexts[0];
        } else {
            leftBtnText = btnTexts[0];
            rightBtnText = btnTexts[1];
        }
        return (D) this;
    }

    public D setButtonTextColor(int... colors) {
        if (colors.length < 1 || colors.length > 2) {
            throw new IllegalArgumentException("range of param colors length is [1,2]!");
        }

        if (colors.length == 1) {
            leftBtnTextColor = colors[0];
        } else {
            leftBtnTextColor = colors[0];
            rightBtnTextColor = colors[1];
        }
        return (D) this;
    }

    public D setButtonTextSize(int... spSizes) {
        if (spSizes.length < 1 || spSizes.length > 2) {
            throw new IllegalArgumentException("range of param sizes length is [1,2]!");
        }
        if (spSizes.length == 1) {
            leftBtnTextSize = sp2px(getContext(), spSizes[0]);
        } else {
            leftBtnTextSize = sp2px(getContext(), spSizes[0]);
            rightBtnTextSize = sp2px(getContext(), spSizes[1]);
        }
        return (D) this;
    }

    public D setButtonListener(OnLeftClickListener leftOnClickListener, OnRightClickListener rightOnClickListener) {
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        return (D) this;
    }

    public interface OnLeftClickListener {
        void onClick(View view);
    }

    public interface OnRightClickListener {
        void onClick(View view);
    }
}

package com.xyjune.components.widget.edititem;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.xyjune.components.R;
import com.xyjune.components.widget.item.BaseItem;

public class EditItem extends BaseItem {

    private static final String TAG = "EditItem";

    private static final int START = 0;
    private static final int CENTER = 1;
    private static final int END = 2;

    private int editColor;
    private float editSize;
    private int editGravity;
    private String editHint;
    private String editText;
    private int editWidth;
    private int editHeight;

    protected EditText mEditText;

    public EditItem(Context context) {
        this(context, null);
    }

    public EditItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditItem);
        int defColor = ContextCompat.getColor(context, R.color.def_text);
        editColor = typedArray.getColor(R.styleable.EditItem_xy_edit_textColor, defColor);
        editSize = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_textSize, dip2px(context, 16));
        editGravity = typedArray.getInt(R.styleable.EditItem_xy_edit_gravity, 0);
        editHint = typedArray.getString(R.styleable.EditItem_xy_edit_hint);
        editText = typedArray.getString(R.styleable.EditItem_xy_edit_text);
        editWidth = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_width, -3);
        editHeight = typedArray.getDimensionPixelSize(R.styleable.EditItem_xy_edit_height, LinearLayout.LayoutParams.MATCH_PARENT);
        typedArray.recycle();

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mText.getLayoutParams();
        params.weight = 0;
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        mText.setMaxEms(5);
        initEditItem();
    }

    private void initEditItem() {
        mEditText = new EditText(getContext());
        mEditText.setText(editText);
        mEditText.setHint(editHint);
        mEditText.setTextColor(editColor);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editSize);
        setEditGravity(editGravity);
        mEditText.setBackground(null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(editWidth, editHeight);
        if (editWidth == -3) {
            params.weight = 1;
        }
        params.setMarginStart(mDefMargin);
        mEditText.setSingleLine();
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        mEditText.setPadding(0, 0, 0, 0);
        addView(mEditText, params);
    }

    /**
     * @param gravity Pass {@link #START} or {@link #CENTER} or {@link #END}. Default
     *                value is {@link #START}.
     */
    public void setEditGravity(int gravity) {
        switch (gravity) {
            case START:
            default:
                mEditText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                break;
            case CENTER:
                mEditText.setGravity(Gravity.CENTER);
                break;
            case END:
                mEditText.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                break;
        }
    }

//    private OnFocusChangeListener mOnFocusChangeListener = new OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            if (!hasFocus) {
//                mEditText.clearFocus();
//            }
//        }
//    };

    @Override
    public void clearFocus() {
        super.clearFocus();
        mEditText.clearFocus();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mEditText.setEnabled(enabled);
    }

    //    private SoftKeyboardStateHelper.SoftKeyboardStateListener mSoftKeyboardStateListener = new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
//        @Override
//        public void onSoftKeyboardOpened(int keyboardHeightInPx) {
//
//        }
//
//        @Override
//        public void onSoftKeyboardClosed() {
//            if (mEditText.isFocused())
//                mEditText.clearFocus();
//        }
//    };

    public void addTextChangedListener(TextWatcher watcher) {
        mEditText.addTextChangedListener(watcher);
    }

    public String getEditText() {
        return mEditText.getText().toString();
    }

    public void setEditText(CharSequence text) {
        mEditText.setText(text);
    }
}

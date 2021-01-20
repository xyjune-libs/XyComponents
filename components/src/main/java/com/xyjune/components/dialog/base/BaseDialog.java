package com.xyjune.components.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

public abstract class BaseDialog<D extends BaseDialog> extends Dialog {

    protected static final String TAG = "MewDialog";

    protected View mCreateView;
    DisplayMetrics mDisplayMetrics;

    private LinearLayout mLayout;

    public BaseDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        mDisplayMetrics = getContext().getResources().getDisplayMetrics();
    }

    /**
     * dialog 填充内容
     *
     * @return View
     */
    protected abstract View createView();

    /**
     * dialog内容创建完成调用
     *
     * @param view dialog内容
     */
    protected void onViewCreated(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        mLayout = new LinearLayout(getContext());
        mLayout.setGravity(Gravity.CENTER);
        int mLayoutWidth = mDisplayMetrics.widthPixels - dip2px(getContext(), 50);

        mCreateView = createView();
        if (mCreateView == null) {
            throw new IllegalArgumentException("You must create the view at method " + getClass().getName() + ".createView()");
        }

        mLayout.addView(mCreateView);
        setContentView(mLayout, new LinearLayout.LayoutParams(mLayoutWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public View getCreateView() {
        return mCreateView;
    }

    protected int dip2px(Context context, int dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    protected int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    public void onBackPressed() {

    }
}

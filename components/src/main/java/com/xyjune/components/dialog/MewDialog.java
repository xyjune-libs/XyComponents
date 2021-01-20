package com.xyjune.components.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.xyjune.mdialog.base.BaseAlertDialog;

public class MewDialog extends BaseAlertDialog<com.xyjune.mdialog.MewDialog> {

    private String text;
    private int textSize;
    private int textColor;

    private int imageWidth;
    private int imageHeight;
    private int imgId;

    private LinearLayout mContent;

    public MewDialog(@NonNull Context context) {
        super(context);
        text = "";
        textColor = ContextCompat.getColor(getContext(), R.color.def_content_textColor);
        textSize = sp2px(getContext(), 16);
        imgId = -1;
        imageWidth = imageHeight = LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected View createContent() {
        mContent = new LinearLayout(getContext());
        mContent.setGravity(Gravity.CENTER_HORIZONTAL);
        mContent.setOrientation(LinearLayout.VERTICAL);

        createImage();

        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(textColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        mContent.addView(textView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return mContent;
    }

    private void createImage() {
        if (imgId != -1) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(imgId);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth, imageHeight);
            params.setMargins(0, 0, 0, dip2px(getContext(), 15));
            mContent.addView(imageView, params);
        }
    }

    public com.xyjune.mdialog.MewDialog setContent(String contentText) {
        text = contentText;
        return this;
    }

    public com.xyjune.mdialog.MewDialog setContentTextSize(int spSize) {
        this.textSize = sp2px(getContext(), spSize);
        return this;
    }

    public com.xyjune.mdialog.MewDialog setContentTextColor(int color) {
        this.textColor = color;
        return this;
    }

    public com.xyjune.mdialog.MewDialog setContentImg(int imgId) {
        this.imgId = imgId;
        return this;
    }

    public com.xyjune.mdialog.MewDialog setContentImgSize(int dipWidth, int dipHeight) {
        imageWidth = dip2px(getContext(), dipWidth);
        imageHeight = dip2px(getContext(), dipHeight);
        return this;
    }
}

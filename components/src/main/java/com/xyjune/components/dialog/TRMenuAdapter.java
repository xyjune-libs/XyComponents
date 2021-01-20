package com.xyjune.components.dialog;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xyjune.components.R;
import com.xyjune.components.dialog.bean.MenuItem;

import java.util.List;


public class TRMenuAdapter extends BaseQuickAdapter<MenuItem, BaseViewHolder> {

    private int iconWidth;
    private int iconHeight;
    private int textColor;
    private float textSize;
    private int iconColor;

    public TRMenuAdapter(@Nullable List<MenuItem> data) {
        super(R.layout.item_top_right_menu, data);
        iconWidth = -1;
        iconHeight = -1;
        iconColor = -1;
        textColor = Color.parseColor("#333333");
        textSize = 14;
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuItem item) {
        TextView textView = helper.getView(R.id.item_label);
        ImageView icon = helper.getView(R.id.item_icon);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) icon.getLayoutParams();
        if (iconWidth != -1) {
            layoutParams.width = iconWidth;
        }
        if (iconHeight != -1) {
            layoutParams.height = iconHeight;
        }
        if (iconColor != -1) {
            icon.setColorFilter(iconColor);
        }
        icon.setLayoutParams(layoutParams);
        icon.setImageResource(item.getIcon());
        textView.setText(item.getText());
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
    }

    public void setIconSize(int width, int height) {
        iconHeight = height;
        iconWidth = width;
        notifyDataSetChanged();
    }

    public void setTextColor(int color) {
        textColor = color;
        notifyDataSetChanged();
    }

    public void setTextSize(float size) {
        textSize = size;
        notifyDataSetChanged();
    }

    public void setIconColor(int iconColor) {
        this.iconColor = iconColor;
        notifyDataSetChanged();
    }
}

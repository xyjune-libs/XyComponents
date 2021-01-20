package com.xyjune.components.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xyjune.components.dialog.base.BasePopupWindow;
import com.xyjune.components.dialog.bean.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class TRMenuPopupWindow extends BasePopupWindow {

    private RecyclerView mRecyclerView;
    private List<MenuItem> mMenuItemList;
    private TRMenuAdapter mTRMenuAdapter;
    private OnMenuListener mOnMenuListener;

    public TRMenuPopupWindow(Context context) {
        super(context);
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mMenuItemList = new ArrayList<>();
        mTRMenuAdapter = new TRMenuAdapter(mMenuItemList);
        mTRMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mOnMenuListener != null)
                    mOnMenuListener.onMenuItemClick(position);
            }
        });
        setContentView(mRecyclerView);
    }

    public void setMenu(List<MenuItem> menuItemList) {
        mMenuItemList = menuItemList;
        mTRMenuAdapter.replaceData(menuItemList);
    }

    public void setOnMenuListener(OnMenuListener onMenuListener) {
        mOnMenuListener = onMenuListener;
    }

    public void setIconSize(int width, int height) {
        if (mTRMenuAdapter != null) {
            mTRMenuAdapter.setIconSize(width, height);
        }
    }

    public void setTextSize(float size) {
        if (mTRMenuAdapter != null) {
            mTRMenuAdapter.setTextSize(size);
        }
    }

    public void setTextColor(int color) {
        if (mTRMenuAdapter != null) {
            mTRMenuAdapter.setTextColor(color);
        }
    }

    public void setIconColor(int color) {
        if (mTRMenuAdapter != null) {
            mTRMenuAdapter.setIconColor(color);
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        mRecyclerView.setAdapter(mTRMenuAdapter);
        super.showAsDropDown(anchor, xoff, yoff);
        showAnimator().start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dismissAnimator().start();
    }

    public interface OnMenuListener {
        void onMenuItemClick(int position);
    }
}

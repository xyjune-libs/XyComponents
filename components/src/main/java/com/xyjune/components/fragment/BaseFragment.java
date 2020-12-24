package com.xyjune.components.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private View mRoot;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(getLayoutId(), container, false);
        } else {
            if (mRoot.getParent() != null) {
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preInit();
        initView();
        initData();
    }

    protected <T extends View> T $(@IdRes int viewId) {
        return mRoot.findViewById(viewId);
    }

    protected abstract int getLayoutId();

    protected abstract void initArgs(Bundle bundle);

    protected void preInit() {

    }

    protected abstract void initView();

    protected abstract void initData();

    public void showToast(final String content) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void showToast(final int resId) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
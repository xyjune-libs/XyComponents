package com.xyjune.components.mvp;

import android.content.Context;
import android.os.Bundle;

import com.xyjune.components.activity.BaseActivity;
import com.xyjune.components.mvp.proxy.ProxyMvpActivity;
import com.xyjune.components.mvp.view.IBaseView;

public abstract class BaseMvpActivity extends BaseActivity implements IBaseView {

    private ProxyMvpActivity mProxyMvpActivity;

    @Override
    protected void preInit(Bundle savedInstanceState) {
        super.preInit(savedInstanceState);
        mProxyMvpActivity = createProxyActivity();
        mProxyMvpActivity.bindPresenter();
    }

    private ProxyMvpActivity createProxyActivity() {
        if (mProxyMvpActivity == null) {
            mProxyMvpActivity = new ProxyMvpActivity(this);
        }
        return mProxyMvpActivity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProxyMvpActivity.unbindPresenter();
        mProxyMvpActivity = null;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToast(String message) {
        super.showToast(message);
    }
}
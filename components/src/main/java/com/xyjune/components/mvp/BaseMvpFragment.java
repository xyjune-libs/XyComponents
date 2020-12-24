package com.xyjune.components.mvp;

import com.xyjune.components.fragment.BaseFragment;
import com.xyjune.components.mvp.proxy.ProxyMvpFragment;
import com.xyjune.components.mvp.view.IBaseView;

public abstract class BaseMvpFragment extends BaseFragment implements IBaseView {

    private ProxyMvpFragment mProxyFragment;

    @Override
    protected void preInit() {
        super.preInit();
        mProxyFragment = createProxyFragment();
        mProxyFragment.bindPresenter();
    }

    private ProxyMvpFragment createProxyFragment() {
        if (mProxyFragment == null) {
            return new ProxyMvpFragment(this);
        }
        return mProxyFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProxyFragment != null) {
            mProxyFragment.unbindPresenter();
            mProxyFragment = null;
        }
    }

    @Override
    public void showToast(String content) {
        super.showToast(content);
    }
}
package com.xyjune.components.mvp.presenter;

import com.xyjune.components.mvp.view.IBaseView;

public interface IBasePresenter {

    void attach(IBaseView view);

    void detach();

}

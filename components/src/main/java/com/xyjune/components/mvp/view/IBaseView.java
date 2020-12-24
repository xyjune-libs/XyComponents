package com.xyjune.components.mvp.view;

import android.content.Context;

public interface IBaseView {

    Context getContext();

    void showToast(String message);
}

package com.xyjune.components.mvp.proxy;


import com.xyjune.components.mvp.inject.Presenter;
import com.xyjune.components.mvp.presenter.BasePresenter;
import com.xyjune.components.mvp.view.IBaseView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ProxyImpl implements IProxy {

    private IBaseView mView;
    private List<BasePresenter> mInjectPresenters;

    public ProxyImpl(IBaseView view) {
        this.mView = view;
        mInjectPresenters = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void bindPresenter() {
        //获得已经申明的变量，包括私有的
        List<Field> fields = getDeclaredFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            Presenter injectPresenter = field.getAnnotation(Presenter.class);
            if (injectPresenter != null) {
                Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                try {
                    BasePresenter presenter = type.newInstance();
                    presenter.attach(mView);
                    field.setAccessible(true);
                    field.set(mView, presenter);
                    mInjectPresenters.add(presenter);
                } catch (IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Field> getDeclaredFields() {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = mView.getClass();
        while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList;
    }

    @Override
    public void unbindPresenter() {
        // 解绑，避免内存泄漏
        for (BasePresenter presenter : mInjectPresenters) {
            presenter.detach();
        }
        mInjectPresenters.clear();
        mInjectPresenters = null;
    }
}

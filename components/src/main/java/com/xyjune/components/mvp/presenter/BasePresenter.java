package com.xyjune.components.mvp.presenter;

import com.xyjune.components.mvp.model.BaseModel;
import com.xyjune.components.mvp.view.IBaseView;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

public class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter {

    private SoftReference<IBaseView> mViewSoftReference;
    private V mProxyView;
    private M mModel;

    public V getView() {
        return mProxyView;
    }

    public M getModel() {
        return mModel;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void attach(IBaseView view) {
        mViewSoftReference = new SoftReference<>(view);

        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (mViewSoftReference == null || mViewSoftReference.get() == null) {
                    return null;
                }
                return method.invoke(mViewSoftReference.get(), args);
            }
        });

        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            try {
                mModel = (M) ((Class<?>) types[1]).newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void detach() {
        mViewSoftReference.clear();
        mViewSoftReference = null;
    }
}
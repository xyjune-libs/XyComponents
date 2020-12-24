package com.xyjune.testcomponents;

import com.xyjune.components.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        showToast("启动");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
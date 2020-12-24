package com.xyjune.components.activity;

import android.app.Instrumentation;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setMyContentView();
        preInit(savedInstanceState);
        initView();
        initData();
    }

    protected void preInit(Bundle savedInstanceState) {
    }

    protected void setMyContentView() {
        setContentView(getLayoutId());
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected <T extends View> T $(@IdRes int viewId) {
        return findViewById(viewId);
    }

    public void showToast(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showToast(final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void back() {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    Log.e("Exception when onBack", e.toString());
                }
            }
        }.start();
    }
}
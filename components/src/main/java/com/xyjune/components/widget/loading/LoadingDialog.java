package com.xyjune.components.widget.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyjune.components.R;

public class LoadingDialog {

    private CircularRing circularRing;
    private Dialog loadingDialog;
    private Context context;
    private String msg = "加载中...";
    private boolean cancelable = false;
    private boolean isShow;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    /**
     * set TextView text
     *
     * @param msg text
     * @return LoadingDialog
     */
    public LoadingDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * set cancelable
     *
     * @param cancelable boolean
     * @return LoadingDialog
     */
    public LoadingDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public void show() {
        View view = View.inflate(context, R.layout.dialog_loading, null);
        LinearLayout layout = view.findViewById(R.id.loading_view);
        circularRing = view.findViewById(R.id.loading_cr);
        TextView loadingText = view.findViewById(R.id.loading_tv);
        loadingText.setText(msg);

        loadingDialog = new Dialog(context, R.style.LoadingDialog);
        loadingDialog.setCancelable(cancelable);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        loadingDialog.show();
        circularRing.startAnim();
        isShow = true;
    }

    public void dismiss() {
        if (loadingDialog != null) {
            circularRing.stopAnim();
            loadingDialog.dismiss();
            loadingDialog = null;
            isShow = false;
        }
    }

    public boolean isShowing() {
        return isShow;
    }
}

package cn.sharing8.blood_platform_widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.sharing8.blood_platform_widget.R;

public class LoadingDialog extends Dialog {

    private static int default_width  = 200;
    private static int default_height = 120;

    public LoadingDialog(Context context) {
        this(context, default_width, default_height);
    }

    public LoadingDialog(Context context, int width, int height) {
        super(context, R.style.Theme_dialog);
        setContentView(R.layout.dlg_loading);

        // set window params
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        // set width,height by density and gravity
        float density = getDensity(context);
        params.width = (int) (width * density);
        params.height = (int) (height * density);
        params.gravity = Gravity.CENTER;

        window.setAttributes(params);
    }

    /**
     * 设置提示文字
     */
    public void setMessageTips(String tips) {
        TextView message = (TextView) findViewById(R.id.message);
        message.setText(tips);
    }

    public LoadingDialog(Context context, View view, int width, int height, int style) {
        super(context, style);

        // set content
        setContentView(view);

        // set window params
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        // set width,height by density and gravity
        float density = getDensity(context);
        params.width = (int) (width * density);
        params.height = (int) (height * density);
        params.gravity = Gravity.CENTER;

        window.setAttributes(params);


    }

    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }

}

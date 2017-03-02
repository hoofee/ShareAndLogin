package cn.sharing8.blood_platform_widget.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static String oldMsg;
    protected static Toast toast   = null;
    private static   long  oneTime = 0;
    private static   long  twoTime = 0;

    public static void showToast(Context context, String s, int time) {
        if (toast == null) {
            toast = Toast.makeText(context, s, time);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > time) {
                    toast.setDuration(time);
                    toast.show();
                }
            } else {
                toast.setDuration(time);
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToast(Context context, int resId, int time) {
        showToast(context, context.getString(resId), time);
    }

}

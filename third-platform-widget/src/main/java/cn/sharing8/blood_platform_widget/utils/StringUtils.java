package cn.sharing8.blood_platform_widget.utils;

import android.text.TextUtils;

/**
 * Created by hufei on 2017/2/9.
 */

public class StringUtils {

    /**
     * 获取子字符串，有最大字数限制
     */
    public static String getChildString(String str, int maxLength) {
        if (TextUtils.isEmpty(str)) return "";

        if (str.length() > maxLength) return String.copyValueOf(str.toCharArray(), 0, maxLength);
        return str;
    }
}

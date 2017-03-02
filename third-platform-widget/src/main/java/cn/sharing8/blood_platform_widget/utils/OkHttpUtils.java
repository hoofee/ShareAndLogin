package cn.sharing8.blood_platform_widget.utils;

import java.util.Iterator;
import java.util.Map;

import cn.sharing8.blood_platform_widget.config.UrlManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by hufei on 2017/2/6.
 * 网络请求工具
 */

public class OkHttpUtils {

    static UrlManager urlManager = UrlManager.getInstance();

    public static void get(String tagName, String url, Callback callback, Map<String, String> headerMap) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (headerMap != null && !headerMap.isEmpty()) {
            Iterator<String> headerIt = headerMap.keySet().iterator();
            while (headerIt.hasNext()) {
                String key = headerIt.next();
                String value = headerMap.get(key);
                requestBuilder.addHeader(key, value);
            }
        }
        Request request = requestBuilder.build();

        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(callback);
        urlManager.putCallList(tagName, call);
    }
}

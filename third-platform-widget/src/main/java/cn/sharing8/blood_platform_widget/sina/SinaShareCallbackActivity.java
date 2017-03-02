package cn.sharing8.blood_platform_widget.sina;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;

import cn.sharing8.blood_platform_widget.PlatformInitConfig;
import cn.sharing8.blood_platform_widget.utils.LogUtils;

/**
 * Created by hufei on 2017/2/7.
 * 新浪微博回调Activity，需要在清单文件中配置
 */

public class SinaShareCallbackActivity extends AppCompatActivity implements IWeiboHandler.Response {

    public static IWeiboShareAPI         sinaShareApi;
    public static IWeiboHandler.Response sinaShareCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PlatformInitConfig.registSina();

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            sinaShareApi.handleWeiboResponse(getIntent(), this);
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        sinaShareApi.handleWeiboResponse(intent, this);

        finish();
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        LogUtils.e("share_sina_result_" + baseResp.errCode + "---" + baseResp.errMsg);
        if (sinaShareCallback != null) sinaShareCallback.onResponse(baseResp);
    }
}

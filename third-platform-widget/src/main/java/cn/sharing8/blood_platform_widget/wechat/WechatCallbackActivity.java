package cn.sharing8.blood_platform_widget.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by hufei on 2017/1/17.
 * 微信回调Activity
 */

public class WechatCallbackActivity extends AppCompatActivity {

    public static IWXAPI             wechatApi;
    public static IWXAPIEventHandler callback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WechatLoginAction.hideLoadingDialog();
        if (wechatApi != null) {
            wechatApi.handleIntent(getIntent(), callback);
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WechatLoginAction.hideLoadingDialog();
        if (wechatApi != null) {
            wechatApi.handleIntent(intent, callback);
        }
        finish();
    }
}

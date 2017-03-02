package cn.sharing8.blood_platform_widget.sina;

import android.os.Bundle;
import android.text.TextUtils;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import cn.sharing8.blood_platform_widget.base.BaseLoginCallback;
import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.utils.LogUtils;

/**
 * Created by hufei on 2017/2/7.
 * 新浪微博登录(授权)回调
 */

public class SinaLoginCallback extends BaseLoginCallback<Oauth2AccessToken> implements WeiboAuthListener {

    public SinaLoginCallback(ILoginCallback loginCallback) {
        super(ChannelEnum.SINA_LOGIN, loginCallback);
    }

    @Override
    public void onComplete(Bundle bundle) {
        if (bundle == null) return;

        // 从 Bundle 中解析 Token
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (mAccessToken.isSessionValid()) {
            LogUtils.e("login_sina_success_" + mAccessToken.toString());
            loginCallback.loginSuccess(PlatformEnum.SINA, mAccessToken);
        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = bundle.getString("code");
            LogUtils.e("share_sina_login_fail_" + code);
            if (!TextUtils.isEmpty(code)) {
                loginCallback.loginFail(PlatformEnum.SINA, code);
            } else {
                loginCallback.loginFail(PlatformEnum.SINA, null);
            }
        }
    }

    @Override
    public void onWeiboException(WeiboException e) {
        LogUtils.e("login_sina_error_" + e.toString());
        loginCallback.loginFail(PlatformEnum.SINA, null);
    }

    @Override
    public void onCancel() {
        LogUtils.e("login_sina_cancel");
        loginCallback.loginCancel();
    }
}

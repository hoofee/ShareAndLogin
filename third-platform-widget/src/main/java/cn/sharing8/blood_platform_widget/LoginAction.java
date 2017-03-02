package cn.sharing8.blood_platform_widget;

import android.app.Activity;
import android.widget.Toast;

import cn.sharing8.blood_platform_widget.interfaces.ILoginAction;
import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.sina.SinaLoginAction;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.utils.ToastUtils;
import cn.sharing8.blood_platform_widget.utils.Util;
import cn.sharing8.blood_platform_widget.wechat.WechatLoginAction;

/**
 * Created by hufei on 2017/1/24.
 * 第三方登录
 */

public class LoginAction implements ILoginAction {

    private Activity       activity;
    private PlatformEnum   platformEnum;
    private ILoginCallback loginCallback;

    private ILoginAction innerLoginAction;

    public LoginAction(Activity activity, PlatformEnum platformEnum) {
        this.activity = activity;
        this.platformEnum = platformEnum;
    }

    public LoginAction setLoginCallback(ILoginCallback loginCallback) {
        this.loginCallback = loginCallback;
        return this;
    }

    @Override
    public void login() {
        if (!PlatformInitConfig.isInstalledApp(platformEnum)) {
            ToastUtils.showToast(activity, "您还没有安装该平台的客户端!", Toast.LENGTH_SHORT);
            return;
        }
        if (!Util.isNetworkConnected(activity, true)) return;

        switch (platformEnum) {
            case TENCENT_WECHAT:
                innerLoginAction = new WechatLoginAction(this);
                break;
            case TENCENT_QQ:
                break;
            case SINA:
                innerLoginAction = new SinaLoginAction(this);
                break;
        }

        if (innerLoginAction != null) innerLoginAction.login();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ILoginCallback getLoginCallback() {
        return loginCallback;
    }

    public PlatformEnum getPlatformEnum() {
        return platformEnum;
    }

    public void setPlatformEnum(PlatformEnum platformEnum) {
        this.platformEnum = platformEnum;
    }
}

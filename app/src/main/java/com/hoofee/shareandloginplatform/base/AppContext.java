package com.hoofee.shareandloginplatform.base;

import android.app.Application;
import android.content.Context;

import cn.sharing8.blood_platform_widget.PlatformInitConfig;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;

/**
 * Created by hufei on 2017/3/2.
 */

public class AppContext extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //分享初始化
        PlatformInitConfig.initPlatformApp(this, PlatformEnum.TENCENT_WECHAT, "WEIXIN_APPKEY", "WEIXIN_APPKEY_VALUE");
        PlatformInitConfig.initPlatformApp(this, PlatformEnum.TENCENT_QQ, "QQ_APPKEY", "QQ_APPKEY_VALUE");
        PlatformInitConfig.initPlatformApp(this, PlatformEnum.SINA, "SINA_WEIBO_APPKEY", "SINA_WEIBO_APPKEY_VALUE");
        PlatformInitConfig.AppName = "第三方登录和分享APP";
        PlatformInitConfig.AppIcon = "http://content.17donor.com/content/appimage/todayDonorAPPIcon.png";//App Logo图片链接
        PlatformInitConfig.IsShowLog = false;
    }
}

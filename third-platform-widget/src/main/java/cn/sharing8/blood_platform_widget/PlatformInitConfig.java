package cn.sharing8.blood_platform_widget;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;

import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.Map;

import cn.sharing8.blood_platform_widget.model.PlatformModel;
import cn.sharing8.blood_platform_widget.qq.QQShareAction;
import cn.sharing8.blood_platform_widget.sina.SinaShareCallbackActivity;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.utils.LogUtils;
import cn.sharing8.blood_platform_widget.wechat.WechatCallbackActivity;

/**
 * Created by hufei on 2017/1/16.
 * 初始化所有平台配置
 */

public class PlatformInitConfig {

    public static Context context;
    public static Map<PlatformEnum, PlatformModel> platformModelMap = new HashMap<>();//要分享的平台
    public static String  AppName;//App名称
    public static String  AppIcon;//App网络图标
    public static boolean IsShowLog;//是否显示打印的log
    public static Handler mainHandler;

    public static void initPlatformApp(Context mContext, PlatformEnum platformEnum, String metaKey, String metaValue) {
        if (context == null && mContext != null) {
            context = mContext;
            mainHandler = new Handler(Looper.getMainLooper());
        }
        try {
            ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            String appKeyId = String.valueOf(appInfo.metaData.get(metaKey));
            String appKeyValue = String.valueOf(appInfo.metaData.get(metaValue));
            LogUtils.e("appkey_" + platformEnum.getPlatformType() + "---" + appKeyId + "---" + appKeyValue);
            PlatformModel platformModel = new PlatformModel(appKeyId, appKeyValue);
            if (platformModel.isNull()) {
                throw new NullPointerException("第三方平台初始化信息为空");
            } else {
                platformModelMap.put(platformEnum, platformModel);
                //注册需要注册的平台
                if (platformEnum == PlatformEnum.TENCENT_WECHAT) registWechat(platformModel);
                else if (platformEnum == PlatformEnum.TENCENT_QQ) registQQ(platformModel);
                //else if (platformEnum == PlatformEnum.SINA) registSina(platformModel);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册微信
     */
    private static void registWechat(PlatformModel platformModel) {
        if (WechatCallbackActivity.wechatApi == null && platformModel != null) {
            WechatCallbackActivity.wechatApi = WXAPIFactory.createWXAPI(context, platformModel.getAppKeyId(), true);
            WechatCallbackActivity.wechatApi.registerApp(platformModel.getAppKeyId());
        }
    }

    /**
     * 注册QQ平台
     */
    public static void registQQ(PlatformModel platformModel) {
        if (QQShareAction.tencent == null && platformModel != null) {
            QQShareAction.tencent = Tencent.createInstance(platformModel.getAppKeyId(), context);
        }
    }

    /**
     * 注册新浪微博(不稳定，所以不能一开始就注册)
     */
    public static void registSina() {
        if (SinaShareCallbackActivity.sinaShareApi == null) {
            // 创建微博分享接口实例
            SinaShareCallbackActivity.sinaShareApi = WeiboShareSDK.createWeiboAPI(context, platformModelMap.get(PlatformEnum.SINA).getAppKeyId());

            // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
            // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
            // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
            SinaShareCallbackActivity.sinaShareApi.registerApp();
        }
    }

    /**
     * 注销
     */
    public static void logout(PlatformEnum platformEnum) {
        switch (platformEnum) {
            case TENCENT_WECHAT:
                if (WechatCallbackActivity.wechatApi != null) {
                    WechatCallbackActivity.wechatApi.unregisterApp();
                }
                break;
            case TENCENT_QQ:
                if (QQShareAction.tencent != null) {
                    QQShareAction.tencent.logout(context);
                }
                break;
            case SINA://新浪还没有提供注销方法
                break;
        }
    }

    /**
     * 是否安装该平台
     */
    public static boolean isInstalledApp(PlatformEnum platformEnum) {
        if (platformEnum == null) return true;

        switch (platformEnum) {
            case TENCENT_WECHAT:
                if (WechatCallbackActivity.wechatApi != null) {
                    return WechatCallbackActivity.wechatApi.isWXAppInstalled();
                }
                break;
            case TENCENT_QQ://QQ平台没有安装不要紧，QQ有默认的处理
                break;
            case SINA://新浪客户端没有安装的话有网页
                break;
        }
        return true;
    }
}

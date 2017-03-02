package cn.sharing8.blood_platform_widget;

import android.app.Activity;
import android.widget.Toast;

import cn.sharing8.blood_platform_widget.interfaces.IShareAction;
import cn.sharing8.blood_platform_widget.interfaces.IShareCallback;
import cn.sharing8.blood_platform_widget.model.ShareModel;
import cn.sharing8.blood_platform_widget.qq.QQShareAction;
import cn.sharing8.blood_platform_widget.sina.SinaShareAction;
import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.utils.ToastUtils;
import cn.sharing8.blood_platform_widget.utils.Util;
import cn.sharing8.blood_platform_widget.wechat.WechatFunctionEnum;
import cn.sharing8.blood_platform_widget.wechat.WechatShareAction;

/**
 * Created by hufei on 2017/1/16.
 * 分享构建
 */

public class ShareAction {

    private IShareAction shareAction;
    private Activity     activity;

    private ChannelEnum    channelEnum;
    private ShareModel     shareModel;
    private IShareCallback shareCallback;

    public ShareAction(Activity activity) {
        this.activity = activity;
    }

    public ShareAction setChannelEnum(ChannelEnum channelEnum) {
        this.channelEnum = channelEnum;
        return this;
    }

    public ShareAction setShareModel(ShareModel shareModel) {
        this.shareModel = shareModel;
        return this;
    }

    public ShareAction setShareCallback(IShareCallback callback) {
        this.shareCallback = callback;
        return this;
    }

    public void share() {
        if (activity == null || activity.isFinishing() || channelEnum == null)
            return;

        if (!PlatformInitConfig.isInstalledApp(PlatformEnum.toChannelEnum(channelEnum))) {
            ToastUtils.showToast(activity, "您还没有安装该平台的客户端!", Toast.LENGTH_SHORT);
            return;
        }
        if (!Util.isNetworkConnected(activity, true)) return;

        String channelType = channelEnum.getChannelType();
        //分享到QQ平台
        if (channelType.startsWith(PlatformEnum.TENCENT_QQ.getPlatformType())) {
            shareAction = new QQShareAction(this);
        }
        //分享到微信平台
        else if (channelType.startsWith(PlatformEnum.TENCENT_WECHAT.getPlatformType())) {
            switch (channelEnum) {
                case TENCENT_WECHAT_FRIENDS:
                    shareAction = new WechatShareAction(this, WechatFunctionEnum.FRIENDS);
                    break;
                case TENCENT_WECHAT_CIRCLE:
                    shareAction = new WechatShareAction(this, WechatFunctionEnum.CIRCLE);
                    break;
                case TENCENT_WECHAT_FAVORITE:
                    shareAction = new WechatShareAction(this, WechatFunctionEnum.FAVORITE);
                    break;
            }
        }
        //分享到新浪微博
        else if (channelType.startsWith(PlatformEnum.SINA.getPlatformType())) {
            shareAction = new SinaShareAction(this);
        }
        if (shareAction != null) shareAction.share();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ChannelEnum getChannelEnum() {
        return channelEnum;
    }

    public IShareCallback getShareCallback() {
        return shareCallback;
    }

    public ShareModel getShareModel() {
        return shareModel;
    }
}

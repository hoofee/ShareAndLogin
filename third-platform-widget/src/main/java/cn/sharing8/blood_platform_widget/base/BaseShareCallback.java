package cn.sharing8.blood_platform_widget.base;

import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.interfaces.IShareCallback;

/**
 * Created by hufei on 2017/1/18.
 */

public class BaseShareCallback extends BaseCallback {

    protected IShareCallback shareCallback;

    public BaseShareCallback(ChannelEnum channelEnum, IShareCallback shareCallback) {
        super(channelEnum);
        this.shareCallback = shareCallback;
    }

    public IShareCallback getShareCallback() {
        return shareCallback;
    }

    public void setShareCallback(IShareCallback shareCallback) {
        this.shareCallback = shareCallback;
    }
}

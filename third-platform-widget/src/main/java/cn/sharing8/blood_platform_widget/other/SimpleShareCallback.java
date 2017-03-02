package cn.sharing8.blood_platform_widget.other;

import cn.sharing8.blood_platform_widget.interfaces.IShareCallback;
import cn.sharing8.blood_platform_widget.model.ShareCallbackModel;
import cn.sharing8.blood_platform_widget.type.ChannelEnum;

/**
 * Created by hufei on 2017/2/9.
 * 空实现的分享回调
 */

public class SimpleShareCallback implements IShareCallback {

    @Override
    public void onSuccess(ChannelEnum channelEnum, ShareCallbackModel callbackModel) {

    }

    @Override
    public void onCancel(ChannelEnum channelEnum) {

    }

    @Override
    public void onError(ChannelEnum channelEnum, ShareCallbackModel callbackModel) {

    }
}

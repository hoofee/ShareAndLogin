package cn.sharing8.blood_platform_widget.interfaces;

import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.model.ShareCallbackModel;

/**
 * Created by hufei on 2017/1/16.
 * 分享后的回调
 */

public interface IShareCallback {

    void onSuccess(ChannelEnum channelEnum, ShareCallbackModel callbackModel);

    void onCancel(ChannelEnum channelEnum);

    void onError(ChannelEnum channelEnum, ShareCallbackModel callbackModel);
}
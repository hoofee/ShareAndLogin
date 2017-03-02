package cn.sharing8.blood_platform_widget.interfaces;

import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.model.ShareCallbackModel;

/**
 * Created by hufei on 2017/1/16.
 */

public class ShareSimpleCalback implements IShareCallback {

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

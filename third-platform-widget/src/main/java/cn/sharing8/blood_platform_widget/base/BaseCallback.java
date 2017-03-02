package cn.sharing8.blood_platform_widget.base;

import cn.sharing8.blood_platform_widget.type.ChannelEnum;

/**
 * Created by hufei on 2017/1/24.
 */

public class BaseCallback {

    protected ChannelEnum channelEnum;

    public BaseCallback(ChannelEnum channelEnum) {
        this.channelEnum = channelEnum;
    }

    public ChannelEnum getChannelEnum() {
        return channelEnum;
    }

    public void setChannelEnum(ChannelEnum channelEnum) {
        this.channelEnum = channelEnum;
    }
}

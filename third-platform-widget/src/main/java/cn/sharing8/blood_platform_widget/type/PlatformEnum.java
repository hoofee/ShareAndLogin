package cn.sharing8.blood_platform_widget.type;

/**
 * Created by hufei on 2017/1/16.
 * 平台
 */

public enum PlatformEnum {
    TENCENT_QQ("tencent_qq"),
    TENCENT_WECHAT("tencent_wechat"),
    SINA("sina");

    private String platformType;

    PlatformEnum(String platformType) {
        this.platformType = platformType;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * 将渠道转化为平台
     */
    public static PlatformEnum toChannelEnum(ChannelEnum channelEnum) {
        if (channelEnum == null) return null;

        String channelName = channelEnum.getChannelType();
        if (channelName.startsWith(TENCENT_QQ.getPlatformType())) {
            return TENCENT_QQ;
        } else if (channelName.startsWith(TENCENT_WECHAT.getPlatformType())) {
            return TENCENT_WECHAT;
        } else if (channelName.startsWith(SINA.getPlatformType())) {
            return SINA;
        }
        return null;
    }
}

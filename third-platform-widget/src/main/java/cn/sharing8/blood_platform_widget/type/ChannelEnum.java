package cn.sharing8.blood_platform_widget.type;

/**
 * Created by hufei on 2017/1/16.
 * 支持的平台
 */

public enum ChannelEnum {

    /**
     * QQ平台
     */
    TENCENT_QQ_FRIENDS("tencent_qq_friends"),
    TENCENT_QQ_ZONE("tencent_qq_zone"),
    TENCENT_QQ_WEIBO("tencent_qq_weibo"),//腾讯微博已挂

    /**
     * 微信平台
     */
    TENCENT_WECHAT_FRIENDS("tencent_wechat_friends"),
    TENCENT_WECHAT_CIRCLE("tencent_wechat_circle"),
    TENCENT_WECHAT_FAVORITE("tencent_wechat_favorite"),
    TENCENT_WECHAT_LOGIN("tencent_wechat_login"),

    /**
     * 新浪微博平台
     */
    SINA_SHARE("sina_share"),//同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）
    SINA_LOGIN("sina_login");



    private String channelType;

    ChannelEnum(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}

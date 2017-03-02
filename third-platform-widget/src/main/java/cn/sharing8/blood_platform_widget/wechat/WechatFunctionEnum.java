package cn.sharing8.blood_platform_widget.wechat;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * Created by hufei on 2017/1/20.
 * 微信功能项枚举
 */

public enum WechatFunctionEnum {

    /**
     * 分享到好友
     */
    FRIENDS(SendMessageToWX.Req.WXSceneSession),
    /**
     * 分享到朋友圈
     */
    CIRCLE(SendMessageToWX.Req.WXSceneTimeline),
    /**
     * 加入到微信收藏
     */
    FAVORITE(SendMessageToWX.Req.WXSceneFavorite),
    /**
     * 使用微信登录
     */
    LOGIN(3);

    private int wechatScene;

    WechatFunctionEnum(int wechatScene) {
        this.wechatScene = wechatScene;
    }

    public int getWechatScene() {
        return wechatScene;
    }

    public void setWechatScene(int wechatScene) {
        this.wechatScene = wechatScene;
    }
}

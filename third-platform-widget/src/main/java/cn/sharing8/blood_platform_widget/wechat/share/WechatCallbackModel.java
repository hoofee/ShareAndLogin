package cn.sharing8.blood_platform_widget.wechat.share;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import cn.sharing8.blood_platform_widget.model.ShareCallbackModel;

/**
 * Created by hufei on 2017/1/16.
 * 微信回调数据
 */

public class WechatCallbackModel extends ShareCallbackModel {

    private BaseResp respData;

    public WechatCallbackModel() {

    }

    public WechatCallbackModel(BaseResp respData) {
        this.respData = respData;
    }

    public BaseResp getRespData() {
        return respData;
    }

    public void setRespData(BaseResp respData) {
        this.respData = respData;
    }
}

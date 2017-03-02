package cn.sharing8.blood_platform_widget.wechat;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.base.BaseCallback;
import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.interfaces.IShareCallback;
import cn.sharing8.blood_platform_widget.utils.LogUtils;
import cn.sharing8.blood_platform_widget.wechat.share.WechatCallbackModel;

/**
 * Created by hufei on 2017/1/18.
 * 微信分享回调
 */

public class WechatCallback extends BaseCallback implements IWXAPIEventHandler {

    /**
     * BaseResp Code码
     * 正确返回:ERR_OK
     * 认证被否决:ERR_AUTH_DENIED,
     * 被屏蔽所有操作，可能由于签名不正确或无权限:ERR_BAN
     * 一般错误:ERR_COMM
     * 发送失败:ERR_SENT_FAILED
     * 不支持错误:ERR_UNSUPPORT
     * 用户取消:ERR_USER_CANCEL
     */

    private WechatFunctionEnum functionEnum;

    protected IShareCallback           shareCallback;
    protected ILoginCallback<BaseResp> loginCallback;

    public WechatCallback(WechatFunctionEnum functionEnum, ChannelEnum channelEnum) {
        super(channelEnum);
        this.functionEnum = functionEnum;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if ((functionEnum == WechatFunctionEnum.LOGIN && loginCallback == null)
                || (functionEnum != WechatFunctionEnum.LOGIN && shareCallback == null))
            return;
        LogUtils.e("share_wechat_callback_result_" + baseResp.errCode + "---" + baseResp.errStr);
        switch (baseResp.errCode) {
            //正确返回
            case BaseResp.ErrCode.ERR_OK:
                if (functionEnum == WechatFunctionEnum.LOGIN) {
                    loginCallback.loginSuccess(PlatformEnum.TENCENT_WECHAT, baseResp);
                } else {
                    shareCallback.onSuccess(channelEnum, new WechatCallbackModel(baseResp));
                }
                break;
            //用户取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (functionEnum == WechatFunctionEnum.LOGIN) {
                    loginCallback.loginCancel();
                } else {
                    shareCallback.onCancel(channelEnum);
                }
                break;
            //其他错误
            default:
                if (functionEnum == WechatFunctionEnum.LOGIN) {
                    loginCallback.loginFail(PlatformEnum.TENCENT_WECHAT, baseResp);
                } else {
                    shareCallback.onError(channelEnum, new WechatCallbackModel(baseResp));
                }
                break;
        }
    }

    public void setLoginCallback(ILoginCallback<BaseResp> loginCallback) {
        this.loginCallback = loginCallback;
    }

    public void setShareCallback(IShareCallback shareCallback) {
        this.shareCallback = shareCallback;
    }
}

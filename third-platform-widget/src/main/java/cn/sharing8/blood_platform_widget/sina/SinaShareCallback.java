package cn.sharing8.blood_platform_widget.sina;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;

import cn.sharing8.blood_platform_widget.base.BaseShareCallback;
import cn.sharing8.blood_platform_widget.interfaces.IShareCallback;
import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.utils.LogUtils;

/**
 * Created by hufei on 2017/2/7.
 * 新浪微博分享回调
 */

public class SinaShareCallback extends BaseShareCallback implements IWeiboHandler.Response {

    public SinaShareCallback(IShareCallback shareCallback) {
        super(ChannelEnum.SINA_SHARE, shareCallback);
    }

    @Override
    public void onResponse(BaseResponse baseResp) {
        if (shareCallback == null) return;
        if (baseResp != null) {
            LogUtils.e("share_sina_result_" + baseResp.errCode + "---" + baseResp.errMsg);
            switch (baseResp.errCode) {
                //分享成功
                case WBConstants.ErrorCode.ERR_OK:
                    shareCallback.onSuccess(ChannelEnum.SINA_SHARE, null);
                    break;
                //分享取消
                case WBConstants.ErrorCode.ERR_CANCEL:
                    shareCallback.onCancel(ChannelEnum.SINA_SHARE);
                    break;
                //分享失败
                case WBConstants.ErrorCode.ERR_FAIL:
                    shareCallback.onError(ChannelEnum.SINA_SHARE, null);
                    break;
            }
        }
    }
}

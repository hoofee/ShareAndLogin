package cn.sharing8.blood_platform_widget.qq;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.base.BaseShareCallback;
import cn.sharing8.blood_platform_widget.interfaces.IShareCallback;
import cn.sharing8.blood_platform_widget.utils.LogUtils;

/**
 * Created by hufei on 2017/1/16.
 * QQ分享的回调
 */

public class QQShareCallback extends BaseShareCallback implements IUiListener {

    public QQShareCallback(ChannelEnum channelEnum, IShareCallback shareCallback) {
        super(channelEnum, shareCallback);
    }

    @Override
    public void onComplete(Object o) {
        LogUtils.e("share_qq_success_" + o.toString());
        if (shareCallback != null) {
            shareCallback.onSuccess(channelEnum, new QQShareCallbackModel(o));
        }
    }

    @Override
    public void onError(UiError uiError) {
        LogUtils.e("share_qq_error_" + uiError.errorMessage + "---" + uiError.errorDetail);
        if (shareCallback != null) {
            shareCallback.onError(channelEnum, new QQShareCallbackModel(uiError));
        }

    }

    @Override
    public void onCancel() {
        LogUtils.e("share_qq_cancel");
        if (shareCallback != null) {
            shareCallback.onCancel(channelEnum);
        }
    }
}

package cn.sharing8.blood_platform_widget.qq;

import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import cn.sharing8.blood_platform_widget.PlatformInitConfig;
import cn.sharing8.blood_platform_widget.ShareAction;
import cn.sharing8.blood_platform_widget.base.BaseShareAction;
import cn.sharing8.blood_platform_widget.interfaces.IShareAction;
import cn.sharing8.blood_platform_widget.model.ShareModel;
import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.type.ShareTypeEnum;

/**
 * Created by hufei on 2017/1/16.
 * 分享到QQ平台，包含QQ,QQ空间，腾讯微博
 */

public class QQShareAction extends BaseShareAction implements IShareAction {

    public static Tencent     tencent;
    public static IUiListener callback;

    public QQShareAction(ShareAction shareAction) {
        super(shareAction);
    }

    @Override
    public void share() {
        ShareModel shareModel = shareAction.getShareModel();
        ChannelEnum channelEnum = shareAction.getChannelEnum();
        final Bundle params = new Bundle();

        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareModel.shareUrl);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareModel.shareTitle);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareModel.shareContent);
        if (shareModel.shareImageUrl != null) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareModel.shareImageUrl);
        }
        if (shareModel.shareLocalImageUrl != null) {
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareModel.shareLocalImageUrl);
        }
        if (shareModel.mediaUrl != null) {
            params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareModel.mediaUrl);
        }
        if (PlatformInitConfig.AppName != null) {
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, PlatformInitConfig.AppName);
        }

        /**
         * 默认是显示分享到QZone按钮且不自动打开分享到QZone的对话框）：
         * QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN，分享时自动打开分享到QZone的对话框。
         * QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE，分享时隐藏分享到QZone按钮。
         */
        //params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);

        switch (channelEnum) {
            case TENCENT_QQ_FRIENDS:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, getShareType(ChannelEnum.TENCENT_QQ_FRIENDS, shareModel));

                callback = new QQShareCallback(shareAction.getChannelEnum(), shareAction.getShareCallback());
                tencent.shareToQQ(shareAction.getActivity(), params, callback);
                break;
            case TENCENT_QQ_ZONE:
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, getShareType(ChannelEnum.TENCENT_QQ_ZONE, shareModel));
                params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
                if (shareModel.shareLocalImageUrl != null && !shareModel.shareLocalImageUrl.isEmpty()) {
                    params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, shareModel.shareImageListUrls);
                }
                callback = new QQShareCallback(shareAction.getChannelEnum(), shareAction.getShareCallback());
                tencent.shareToQQ(shareAction.getActivity(), params, callback);
                break;
            //QQ微博已挂
            case TENCENT_QQ_WEIBO:
                break;
        }
    }

    public static int getShareType(ChannelEnum channelEnum, ShareModel shareModel) {
        ShareTypeEnum shareTypeEnum = shareModel.shareTypeEnum;
        //QQ好友分享类型
        if (channelEnum == ChannelEnum.TENCENT_QQ_FRIENDS) {
            switch (shareTypeEnum) {
                case IMAGE:
                    return QQShare.SHARE_TO_QQ_TYPE_IMAGE;
                case MUSIC:
                    return QQShare.SHARE_TO_QQ_TYPE_AUDIO;
                case APPLICATION:
                    return QQShare.SHARE_TO_QQ_TYPE_APP;
                default:
                    return QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
            }
        }
        //QQ空间分享类型
        else if (channelEnum == ChannelEnum.TENCENT_QQ_ZONE) {
            switch (shareTypeEnum) {
                case IMAGE:
                    return QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE;
                case IMAGE_TEXT:
                    return QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
                case WEBPAGE:
                    return QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
                case APPLICATION:
                    return QzoneShare.SHARE_TO_QZONE_TYPE_APP;
                default:
                    return QzoneShare.SHARE_TO_QZONE_TYPE_NO_TYPE;
            }
        }
        //腾讯微博分享类型
        else if (channelEnum == ChannelEnum.TENCENT_QQ_WEIBO) {

        }
        return 0;
    }
}

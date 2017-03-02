package cn.sharing8.blood_platform_widget.wechat;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import cn.sharing8.blood_platform_widget.PlatformInitConfig;
import cn.sharing8.blood_platform_widget.R;
import cn.sharing8.blood_platform_widget.ShareAction;
import cn.sharing8.blood_platform_widget.base.BaseShareAction;
import cn.sharing8.blood_platform_widget.interfaces.IShareAction;
import cn.sharing8.blood_platform_widget.model.ShareModel;
import cn.sharing8.blood_platform_widget.utils.LogUtils;
import cn.sharing8.blood_platform_widget.utils.Util;

/**
 * Created by hufei on 2017/1/17.
 * 微信分享动作
 */

public class WechatShareAction extends BaseShareAction implements IShareAction {

    private WechatFunctionEnum functionType;//微信功能类型

    private ShareModel          shareModel;
    private SendMessageToWX.Req req;
    private WXMediaMessage      msg;

    public WechatShareAction(ShareAction shareAction, WechatFunctionEnum functionType) {
        super(shareAction);
        this.functionType = functionType;

        init();
    }

    private void init() {
        shareModel = shareAction.getShareModel();

        WechatCallback shareCallback = new WechatCallback(functionType, shareAction.getChannelEnum());
        shareCallback.setShareCallback(shareAction.getShareCallback());
        WechatCallbackActivity.callback = shareCallback;
    }

    @Override
    public void share() {
        msg = new WXMediaMessage();
        msg.title = shareModel.shareTitle;
        msg.description = shareModel.shareContent;

        req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(shareModel.shareTypeEnum.getTag());//用于唯一标识一个请求
        req.scene = functionType.getWechatScene();
        req.message = msg;

        ImageTarget imageTarget = new ImageTarget();
        //网络图片
        if (!TextUtils.isEmpty(shareModel.shareImageUrl)) {
            String imageUrl = TextUtils.isEmpty(shareModel.imageUrlAlyStyle) ? shareModel.shareImageUrl : shareModel.shareImageUrl + shareModel.imageUrlAlyStyle;
            Glide.with(shareAction.getActivity()).load(imageUrl).asBitmap().into(imageTarget);
        }
        //本地图片
        else if (!TextUtils.isEmpty(shareModel.shareLocalImageUrl)) {
            Uri imageUri = Uri.parse(shareModel.shareLocalImageUrl);
            Glide.with(shareAction.getActivity()).load(imageUri).asBitmap().into(imageTarget);
        }
        //App网络Icon
        else if (!TextUtils.isEmpty(PlatformInitConfig.AppIcon)) {
            Glide.with(shareAction.getActivity()).load(PlatformInitConfig.AppIcon).asBitmap().into(imageTarget);
        }
        //App本地Icon
        else {
            Glide.with(shareAction.getActivity()).load(R.drawable.ic_launcher).asBitmap().into(imageTarget);
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 加载图片
     */
    private class ImageTarget extends SimpleTarget<Bitmap> {

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            //ChannelEnum channelEnum = shareAction.getChannelEnum();
            LogUtils.e("share_image_load_success_" + shareAction.getChannelEnum().getChannelType());
            LogUtils.e("bitmap size: " + resource.getWidth() + "x" + resource.getHeight());
            //分享的略缩图, 微信略缩图不能超过32K
            msg.thumbData = Util.getSizeOfImage(resource, 32);
            LogUtils.d("thumbData length: " + msg.thumbData.length);
            //分享的内容
            switch (shareModel.shareTypeEnum) {
                case IMAGE:
                    WXImageObject imageObject = new WXImageObject();
                    imageObject.setImagePath(shareModel.shareLocalImageUrl);

                    msg.mediaObject = imageObject;
                    break;
                case MUSIC:
                    WXMusicObject musicObject = new WXMusicObject();
                    musicObject.musicUrl = shareModel.mediaUrl;

                    msg.mediaObject = musicObject;
                    break;
                case VIDIO:
                    WXVideoObject videoObject = new WXVideoObject();
                    videoObject.videoUrl = shareModel.mediaUrl;

                    msg.mediaObject = videoObject;
                    break;
                default://WEBPAGE
                    WXWebpageObject webPageObject = new WXWebpageObject();
                    webPageObject.webpageUrl = shareModel.shareUrl;

                    msg.mediaObject = webPageObject;
                    break;
            }
            WechatCallbackActivity.wechatApi.sendReq(req);
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            e.printStackTrace();
            Toast.makeText(shareAction.getActivity(), "略缩图加载失败!", Toast.LENGTH_SHORT).show();
        }
    }
}

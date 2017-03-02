package cn.sharing8.blood_platform_widget.sina;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.utils.Utility;

import cn.sharing8.blood_platform_widget.PlatformInitConfig;
import cn.sharing8.blood_platform_widget.R;
import cn.sharing8.blood_platform_widget.ShareAction;
import cn.sharing8.blood_platform_widget.base.BaseShareAction;
import cn.sharing8.blood_platform_widget.config.SPForPlatform;
import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.interfaces.IShareAction;
import cn.sharing8.blood_platform_widget.model.PlatformModel;
import cn.sharing8.blood_platform_widget.model.ShareModel;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.utils.LogUtils;
import cn.sharing8.blood_platform_widget.utils.StringUtils;

/**
 * Created by hufei on 2017/2/7.
 * 新浪微博分享
 */

public class SinaShareAction extends BaseShareAction implements IShareAction {

    private static int MAX_CONTENT_INT = 130;//内容字数限制130字

    private Oauth2AccessToken sinaAccesstokenModel;
    private ShareModel        shareModel;
    private PlatformModel     sinaPlatformModel;

    private WeiboMultiMessage msg;

    private SPForPlatform spForPlatform;
    private Gson gson = new Gson();

    public SinaShareAction(final ShareAction shareAction) {
        super(shareAction);
        PlatformInitConfig.registSina();

        sinaPlatformModel = PlatformInitConfig.platformModelMap.get(PlatformEnum.SINA);
        shareModel = shareAction.getShareModel();
        spForPlatform = new SPForPlatform(shareAction.getActivity());

        //新浪微博授权
        /*LoginAction loginAction = new LoginAction(shareAction.getActivity(), PlatformEnum.SINA);
        loginAction.setLoginCallback(new ILoginCallback<Oauth2AccessToken>() {
            @Override
            public void loginSuccess(PlatformEnum platformEnum, Oauth2AccessToken callbackModel) {
                LogUtils.e("login_sina_loginSuccess_" + callbackModel.toString());
                saveSinaAccesstokenModel(callbackModel);
                setShareData();
            }

            @Override
            public void loginFail(PlatformEnum platformEnum, Object callbackModel) {
                LogUtils.e("login_sina_loginFail_" + callbackModel.toString());
                if (shareAction.getShareCallback() != null) {
                    shareAction.getShareCallback().onSuccess(ChannelEnum.SINA_SHARE, null);
                }
            }

            @Override
            public void loginCancel() {
                LogUtils.e("login_sina_logincancel");
                if (shareAction.getShareCallback() != null) {
                    shareAction.getShareCallback().onCancel(ChannelEnum.SINA_SHARE);
                }
            }
        });
        sinaLoginAction = new SinaLoginAction(loginAction);*/
    }

    /**
     * 保存新浪微博授权
     */
    private void saveSinaAccesstokenModel(Oauth2AccessToken callbackModel) {
        sinaAccesstokenModel = callbackModel;

        String jsonModel = gson.toJson(callbackModel);
        LogUtils.e("share_sina_auth_model_" + jsonModel);
        spForPlatform.put(SPForPlatform.SINA_AUTH_MODEL_JSON_STRING, jsonModel);
    }

    @Override
    public void share() {
        String sinaAccessTokenModelJson = (String) spForPlatform.get(SPForPlatform.SINA_AUTH_MODEL_JSON_STRING, "");
        LogUtils.e("share_sina_login_data_" + sinaAccessTokenModelJson);
        if (TextUtils.isEmpty(sinaAccessTokenModelJson)) {
            //sinaLoginAction.login();
            setShareData();
        } else {
            sinaAccesstokenModel = gson.fromJson(sinaAccessTokenModelJson, Oauth2AccessToken.class);
            LogUtils.e("share_sina_accesstoken_model_" + sinaAccesstokenModel.toString());
            setShareData();
        }
    }

    /**
     * 组装分享数据
     */
    private void setShareData() {
        //设置分享回调
        SinaShareCallbackActivity.sinaShareCallback = new SinaShareCallback(shareAction.getShareCallback());

        // 1. 初始化微博的分享消息
        msg = new WeiboMultiMessage();
        msg.textObject = getTextObj(StringUtils.getChildString(shareModel.shareContent, MAX_CONTENT_INT));

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

    /**
     * 加载图片
     */
    private class ImageTarget extends SimpleTarget<Bitmap> {
        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            LogUtils.e("share_sina_load_image_success");
            //分享的略缩图
            // TODO: 2017/2/6  微博略缩图不能超过32K
            msg.imageObject = getImageObj(resource);
            //分享的内容
            switch (shareModel.shareTypeEnum) {
                case MUSIC:
                    msg.mediaObject = getMusicObj(resource);
                    break;
                case VIDIO:
                    msg.mediaObject = getVideoObj(resource);
                    break;
                default://WEBPAGE
                    msg.mediaObject = getWebpageObj(resource);
                    break;
            }

            toShareAction();
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            Toast.makeText(shareAction.getActivity(), "略缩图加载失败!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 最终的分享动作
     */
    private void toShareAction() {
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = buildTransaction(shareModel.shareTypeEnum.getTag());//用于唯一标识一个请求
        request.multiMessage = msg;

        // 3. 发送请求消息到微博，唤起微博分享界面
        AuthInfo authInfo = new AuthInfo(shareAction.getActivity(), sinaPlatformModel.getAppKeyId(), SinaLoginAction.REDIRECT_URL, SinaLoginAction.SCOPE);
        String token = "";
        if (sinaAccesstokenModel != null) {
            token = sinaAccesstokenModel.getToken();
        }
        SinaShareCallbackActivity.sinaShareApi.sendRequest(shareAction.getActivity(), request, authInfo, token, new SinaLoginCallback(new ILoginCallback<Oauth2AccessToken>() {
            @Override
            public void loginSuccess(PlatformEnum platformEnum, Oauth2AccessToken callbackModel) {
                saveSinaAccesstokenModel(callbackModel);
                LogUtils.e("login_sina_loginSuccess_" + callbackModel.toString());
            }

            @Override
            public void loginFail(PlatformEnum platformEnum, Object callbackModel) {
                LogUtils.e("login_sina_loginFail_" + callbackModel.toString());
            }

            @Override
            public void loginCancel() {
                LogUtils.e("login_sina_logincancel");
            }
        }));
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj(String text) {
        TextObject textObject = new TextObject();
        textObject.text = text;
        return textObject;
    }

    /**
     * 创建图片消息对象。注意：最终压缩过的缩略图大小不得超过 32kb。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj(Bitmap bitmap) {
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(Bitmap thumbBitmap) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = shareModel.shareTitle;
        mediaObject.description = shareModel.shareContent;

        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(thumbBitmap);
        mediaObject.actionUrl = shareModel.shareUrl;
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj(Bitmap thumbBitmap) {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = shareModel.shareTitle;
        musicObject.description = shareModel.shareContent;

        // 设置 Bitmap 类型的图片到视频对象里        设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        musicObject.setThumbImage(thumbBitmap);
        musicObject.actionUrl = shareModel.mediaUrl;
//        musicObject.dataUrl = "www.weibo.com";
//        musicObject.dataHdUrl = "www.weibo.com";
//        musicObject.duration = 10;
        musicObject.defaultText = "Music 默认文案";
        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj(Bitmap thumbBitmap) {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = shareModel.shareTitle;
        videoObject.description = shareModel.shareContent;
        // 设置 Bitmap 类型的图片到视频对象里  设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        videoObject.setThumbImage(thumbBitmap);
        videoObject.actionUrl = shareModel.mediaUrl;
//        videoObject.dataUrl = "www.weibo.com";
//        videoObject.dataHdUrl = "www.weibo.com";
//        videoObject.duration = 10;
        videoObject.defaultText = "Vedio 默认文案";
        return videoObject;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}

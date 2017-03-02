package cn.sharing8.blood_platform_widget.wechat;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.sharing8.blood_platform_widget.LoginAction;
import cn.sharing8.blood_platform_widget.base.BaseLoginAction;
import cn.sharing8.blood_platform_widget.config.SPForPlatform;
import cn.sharing8.blood_platform_widget.config.UrlManager;
import cn.sharing8.blood_platform_widget.dialog.LoadingDialog;
import cn.sharing8.blood_platform_widget.interfaces.ILoginAction;
import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.type.ChannelEnum;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.utils.LogUtils;
import cn.sharing8.blood_platform_widget.utils.OkHttpUtils;
import cn.sharing8.blood_platform_widget.wechat.api.ApiUrl;
import cn.sharing8.blood_platform_widget.wechat.api.WechatAccessTokenModel;
import cn.sharing8.blood_platform_widget.wechat.api.WechatUserInfoModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by hufei on 2017/1/24.
 * 微信登录
 */

public class WechatLoginAction extends BaseLoginAction implements ILoginAction {

    public static       String TAG_NAME     = "WechatLoginAction";
    public static final String WECHAT_LOGIN = "wechat_login_for_blood";

    private WechatCallback shareCallback;
    private ILoginCallback codeCallback;

    private Gson gson = new Gson();
    private SPForPlatform spForPlatform;

    private       WechatAccessTokenModel wechatAccessTokenModel;
    private       UrlManager             urlManager;
    public static LoadingDialog          loadingDialog;

    public WechatLoginAction(LoginAction loginAction) {
        super(loginAction);
        spForPlatform = new SPForPlatform(loginAction.getActivity());
        urlManager = UrlManager.getInstance();

        shareCallback = new WechatCallback(WechatFunctionEnum.LOGIN, ChannelEnum.TENCENT_WECHAT_LOGIN);
        initCodeCallback();
        WechatCallbackActivity.callback = shareCallback;
    }

    /**
     * loading
     */
    private void showLoadingDialog(boolean isCancelable) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(loginAction.getActivity());
            loadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    loadingDialog = null;
                }
            });
        }
        loadingDialog.setCancelable(isCancelable);
        loadingDialog.show();
    }

    public static void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 获取AccessToken的响应
     */
    private void getAccessTokenResp(Response response) {
        try {
            String jsonText = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonText);
            if (response.isSuccessful() && !jsonObject.has("errcode")) {
                LogUtils.e("login_wechat_get_accesstoken_success_response: " + jsonText);
                spForPlatform.put(SPForPlatform.WECHAT_ACCESS_TOKEN_MODEL_JSON_STRING, jsonText);
                wechatAccessTokenModel = gson.fromJson(jsonText, WechatAccessTokenModel.class);
                if (wechatAccessTokenModel != null) {
                    getUserInfoByAccessToken(wechatAccessTokenModel.getAccess_token());
                }
            } else {
                LogUtils.e("login_wechat_get_accesstoken_error: " + jsonObject.get("errmsg"));
                hasError();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一步：请求CODE
     */
    private void initCodeCallback() {
        if (isCancel) return;
        /**
         * 返回
         * appid: wxd477edab60670232
         * scope: snsapi_userinfo
         * state: wechat_sdk_demo
         */
        codeCallback = new ILoginCallback<SendAuth.Resp>() {
            @Override
            public void loginSuccess(PlatformEnum platformEnum, SendAuth.Resp callbackModel) {
                /*"access_token":"ACCESS_TOKEN",
                "expires_in":7200,
                "refresh_token":"REFRESH_TOKEN",
                "openid":"OPENID",
                "scope":"SCOPE"*/
                LogUtils.e(callbackModel.errCode);
                LogUtils.e(callbackModel.code);
                LogUtils.e(callbackModel.state);
                if (TextUtils.isEmpty(callbackModel.code)) {
                    hasError();
                    return;
                }

                //用户同意之后的处理
                hideLoadingDialog();
                String accessTokenJsonStr = (String) spForPlatform.get(SPForPlatform.WECHAT_ACCESS_TOKEN_MODEL_JSON_STRING, "");
                //没有Token的话通过Code获取Token
                if (TextUtils.isEmpty(accessTokenJsonStr)) {
                    getWechatAccessTokenByCode(callbackModel.code);
                }
                //有Token的话，看Token是否有效，如果失效则重新刷新并保存，如果有效则直接获取个人信息
                else {
                    wechatAccessTokenModel = gson.fromJson(accessTokenJsonStr, WechatAccessTokenModel.class);
                    validAccessToken(wechatAccessTokenModel.getAccess_token());
                }
            }

            @Override
            public void loginFail(PlatformEnum platformEnum, Object callbackModel) {
                hasError();
            }

            @Override
            public void loginCancel() {
                hasError();
                if (loginAction.getLoginCallback() != null) {
                    loginAction.getLoginCallback().loginCancel();
                }
            }
        };
        shareCallback.setLoginCallback(codeCallback);
    }

    /**
     * 第二步：通过Code换取Accesstoken
     */
    private void getWechatAccessTokenByCode(String code) {
        if (isCancel) return;
        String url = String.format(ApiUrl.URL_GET_WECHAT_ACCESSTOKEN, platformModel.getAppKeyId(), platformModel.getAppKeyValue(), code);
        LogUtils.d("accesstoken url: " + url);
        OkHttpUtils.get(TAG_NAME, url, new DefaultCallBack() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                getAccessTokenResp(response);
            }
        }, null);
    }

    /**
     * 校验AccessToken是否有效
     */
    private void validAccessToken(final String accessToken) {
        if (isCancel) return;
        String url = String.format(ApiUrl.URL_VALID_ACCESSTOKEN, accessToken, platformModel.getAppKeyId());
        OkHttpUtils.get(TAG_NAME, url, new DefaultCallBack() {
            //"errcode":40003,"errmsg":"invalid openid"
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                //"errcode":0,"errmsg":"ok"
                String jsonString = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    //Token有效
                    if (jsonObject.getInt("errcode") == 0) {
                        getUserInfoByAccessToken(accessToken);
                    }
                    //Token失效，重新刷新Token
                    else {
                        refreshAccessToken(wechatAccessTokenModel.getRefresh_token());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    hasError();
                }
            }
        }, null);
    }

    /**
     * 通过RefreshToken刷新AccessToken的有效时长
     */
    private void refreshAccessToken(String refreshToken) {
        if (isCancel) return;
        String url = String.format(ApiUrl.URL_REFRESH_WECHAT_ACCESSTOKEN, platformModel.getAppKeyId(), refreshToken);
        OkHttpUtils.get(TAG_NAME, url, new DefaultCallBack() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                getAccessTokenResp(response);
            }
        }, null);
    }

    /**
     * 通过AccessToken获取用户信息
     */
    private void getUserInfoByAccessToken(String accessToken) {
        if (isCancel) return;
        String url = String.format(ApiUrl.URL_GET_USER_INFO, accessToken, platformModel.getAppKeyId());
        OkHttpUtils.get(TAG_NAME, url, new DefaultCallBack() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                /*"openid":"OPENID",
                "nickname":"NICKNAME",
                "sex":1,
                "province":"PROVINCE",
                "city":"CITY",
                "country":"COUNTRY",
                "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
                "privilege":[ "PRIVILEGE1", "PRIVILEGE2" ],
                "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"*/

                hideLoadingDialog();
                WechatUserInfoModel wechatUserInfoModel = null;
                if (loginAction.getLoginCallback() != null) {
                    ILoginCallback callback = loginAction.getLoginCallback();
                    String wechatUserInfoJson = response.body().string();
                    LogUtils.d("userinfo: " + wechatUserInfoJson);
                    if (!TextUtils.isEmpty(wechatUserInfoJson)) {
                        wechatUserInfoModel = gson.fromJson(wechatUserInfoJson, WechatUserInfoModel.class);
                        callback.loginSuccess(PlatformEnum.TENCENT_WECHAT, wechatUserInfoModel);
                        return;
                    }

                    if (wechatUserInfoModel == null) {
                        callback.loginFail(PlatformEnum.TENCENT_WECHAT, null);
                    }
                }
            }
        }, null);
    }

    @Override
    public void login() {
        showLoadingDialog(true);
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = WECHAT_LOGIN;
        WechatCallbackActivity.wechatApi.sendReq(req);
    }

    /**
     * 发生错误时调用的方法
     */
    private void hasError() {
        setCancel(true);
        WechatCallbackActivity.callback = null;
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /**
     * 统一请求失败
     */
    private class DefaultCallBack implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {
            urlManager.cancelCallByTagName(TAG_NAME, true);
            hasError();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            urlManager.removeCall(TAG_NAME, call);
            if (!response.isSuccessful()) {
                hasError();
            }
        }
    }
}

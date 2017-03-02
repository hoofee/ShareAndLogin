package com.hoofee.shareandloginplatform.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hoofee.shareandloginplatform.R;
import com.hoofee.shareandloginplatform.base.BaseActivity;
import com.hoofee.shareandloginplatform.popwindow.ShareBoardPopupWindow;

import cn.sharing8.blood_platform_widget.LoginAction;
import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.model.ShareModel;
import cn.sharing8.blood_platform_widget.other.SimpleLoginCallback;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;
import cn.sharing8.blood_platform_widget.wechat.api.WechatUserInfoModel;

public class MainActivity extends BaseActivity {

    private ShareBoardPopupWindow popupWindow;

    private ILoginCallback<WechatUserInfoModel> loginCallback;//微信登录回调

    private WechatUserInfoModel wechatUserInfoModel;//微信登录成功后返回的数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEvent();
    }

    private void initEvent() {
        if (loginCallback != null) return;

        loginCallback = new SimpleLoginCallback<WechatUserInfoModel>() {
            @Override
            public void loginSuccess(PlatformEnum platformEnum, WechatUserInfoModel _wechatUserInfoModel) {
                wechatUserInfoModel = _wechatUserInfoModel;
                Log.d("login_success_info", wechatUserInfoModel.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 2017/3/2 完成微信登录后的逻辑
                    }
                });
            }
        };
    }

    /**
     * 分享功能
     */
    public synchronized void shareClick(View view) {
        if (popupWindow == null) {
            popupWindow = new ShareBoardPopupWindow(this);

            ShareModel shareModel = new ShareModel();
            shareModel.shareTitle = "分享的标题";
            shareModel.shareContent = "分享的内容";
            shareModel.shareImageUrl = "https://bs.sharing8.cn/content/0/album/146857185116207468.png";//分享的图片路径
            shareModel.shareUrl = "https://bs.sharing8.cn/manage/office/article/detail?articleId=259";//分享的资源URL

            popupWindow.setShareData(shareModel);
        }
        popupWindow.showPopWindow(view);
    }

    /**
     * 微信登录
     */
    public synchronized void wechatLoginClick(View view) {
        if (!isNetworkConnected()) {
            return;
        }
        LoginAction loginAction = new LoginAction(this, PlatformEnum.TENCENT_WECHAT);
        loginAction.setLoginCallback(loginCallback);
        loginAction.login();
    }

    /**
     * 检测网络是否可用
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }
}

# ShareAndLogin
[![](https://jitpack.io/v/hoofee/ShareAndLogin.svg)](https://jitpack.io/#hoofee/ShareAndLogin)

一个轻量、易扩展的第三方登录分享库，比友盟分享登录更稳定，兼容Android7.0，目前集成了微信、QQ、新浪微博3个平台的分享，以及微信登录
 
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

    maven

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

    //gradle
	dependencies {
	        compile 'com.github.hoofee:ShareAndLogin:1.0.0'
	}

 使用步骤：
 1：修改主module的build.gradle文件，在android->defaultConfig下增加
 manifestPlaceholders = [

                QQ_APPKEY              : "",
                QQ_APPKEY_VALUE        : "",

                WEIXIN_APPKEY          : "",
                WEIXIN_APPKEY_VALUE    : "",

                SINA_WEIBO_APPKEY      : "",
                SINA_WEIBO_APPKEY_VALUE: "",
        ]

2. 在 manifest 清单文件中增加：
        <!-- QQ开发者配置 -->
        <meta-data
            android:name="QQ_APPKEY"
            android:value="${QQ_APPKEY}"/>
        <meta-data
            android:name="QQ_APPKEY_VALUE"
            android:value="${QQ_APPKEY_VALUE}"/>

        <!-- 微信开发者配置 -->
        <meta-data
            android:name="WEIXIN_APPKEY"
            android:value="${WEIXIN_APPKEY}"/>
        <meta-data
            android:name="WEIXIN_APPKEY_VALUE"
            android:value="${WEIXIN_APPKEY_VALUE}"/>

        <!-- 新浪开发者配置 -->
        <meta-data
            android:name="SINA_WEIBO_APPKEY"
            android:value="${SINA_WEIBO_APPKEY}"/>
        <meta-data
            android:name="SINA_WEIBO_APPKEY_VALUE"
            android:value="${SINA_WEIBO_APPKEY_VALUE}"/>
        
        <!-- 微信回调begin -->
        <activity
            android:name=".wxapi.WXEntryActivity"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:exported="true"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
        <!-- 微信回调end -->

        <!-- 新浪微博begin -->
        <activity
            android:name=".activity.WBShareActivity"
            android:configChanges="keyboardHidden|orientation"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY"/>

                <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </activity>
        <activity
            android:name="com.sina.weibo.sdk.component.WeiboSdkBrowser"
            android:configChanges="keyboardHidden|orientation"
            android:exported="false"
            android:windowSoftInputMode="adjustResize"/>
        <!-- 新浪微博end -->

        <!-- QQ begin -->
        <activity
            android:name="com.tencent.tauth.AuthActivity"
            android:launchMode="singleTask"
            android:noHistory="true">
            <intent-filter>
                <action android:name="android.intent.action.VIEW"/>

                <category android:name="android.intent.category.DEFAULT"/>
                <category android:name="android.intent.category.BROWSABLE"/>

                <data android:scheme="tencent1105458066"/>
            </intent-filter>
        </activity>
        <activity
            android:name="com.tencent.connect.common.AssistActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="portrait"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
        <!-- QQ end -->
        
3.在Application中增加：
        //分享初始化
        PlatformInitConfig.initPlatformApp(this, PlatformEnum.TENCENT_WECHAT, "WEIXIN_APPKEY", "WEIXIN_APPKEY_VALUE");
        PlatformInitConfig.initPlatformApp(this, PlatformEnum.TENCENT_QQ, "QQ_APPKEY", "QQ_APPKEY_VALUE");
        PlatformInitConfig.initPlatformApp(this, PlatformEnum.SINA, "SINA_WEIBO_APPKEY", "SINA_WEIBO_APPKEY_VALUE");
        PlatformInitConfig.AppName = "第三方登录和分享APP";
        PlatformInitConfig.AppIcon = "http://content.17donor.com/content/appimage/todayDonorAPPIcon.png";//App Logo图片链接
        PlatformInitConfig.IsShowLog = false;
        
 4.分享后的回调（最好是在你的BaseActivity中，只有腾讯平台需要这样做，其他不用）：
 
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //腾讯客户端的回调
        if (QQShareAction.callback != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, QQShareAction.callback);
            QQShareAction.callback = null;
        }
    }
 5.最后的使用（详见项目中的例子，里面自带一个分享面板，还可以自己自定义）：
 
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
    
    第三方登录功能：
    //回调
    SimpleLoginCallback loginCallback = new SimpleLoginCallback<WechatUserInfoModel>() {
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
     //触发第三方登录
     LoginAction loginAction = new LoginAction(this, PlatformEnum.TENCENT_WECHAT);
     loginAction.setLoginCallback(loginCallback);
     loginAction.login();
            

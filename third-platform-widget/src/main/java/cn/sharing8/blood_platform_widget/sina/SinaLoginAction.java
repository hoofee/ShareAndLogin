package cn.sharing8.blood_platform_widget.sina;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import cn.sharing8.blood_platform_widget.LoginAction;
import cn.sharing8.blood_platform_widget.PlatformInitConfig;
import cn.sharing8.blood_platform_widget.base.BaseLoginAction;
import cn.sharing8.blood_platform_widget.interfaces.ILoginAction;

/**
 * Created by hufei on 2017/2/7.
 * 新浪登录(授权)
 */

public class SinaLoginAction extends BaseLoginAction implements ILoginAction {

    public static final String REDIRECT_URL = "http://www.sina.com";// 应用的回调页
    public static final String SCOPE        =                       // 应用申请的高级权限
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog,"
                    + "invitation_write";

    private AuthInfo   authInfo;
    private SsoHandler sinaSsoHandler;

    public SinaLoginAction(LoginAction loginAction) {
        super(loginAction);
        authInfo = new AuthInfo(PlatformInitConfig.context, platformModel.getAppKeyId(), REDIRECT_URL, SCOPE);
        sinaSsoHandler = new SsoHandler(loginAction.getActivity(), authInfo);
    }

    @Override
    public void login() {
        sinaSsoHandler.authorize(new SinaLoginCallback(loginAction.getLoginCallback()));
    }
}

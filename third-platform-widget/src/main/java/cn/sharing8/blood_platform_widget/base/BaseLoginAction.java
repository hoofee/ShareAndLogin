package cn.sharing8.blood_platform_widget.base;

import cn.sharing8.blood_platform_widget.LoginAction;
import cn.sharing8.blood_platform_widget.PlatformInitConfig;
import cn.sharing8.blood_platform_widget.model.PlatformModel;

/**
 * Created by hufei on 2017/2/7.
 */

public class BaseLoginAction {

    protected boolean       isCancel;//是否需要取消进度
    protected LoginAction   loginAction;
    protected PlatformModel platformModel;


    public BaseLoginAction(LoginAction loginAction) {
        this.loginAction = loginAction;
        platformModel = PlatformInitConfig.platformModelMap.get(loginAction.getPlatformEnum());
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }
}

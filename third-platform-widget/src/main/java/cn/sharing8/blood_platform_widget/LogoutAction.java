package cn.sharing8.blood_platform_widget;

import cn.sharing8.blood_platform_widget.interfaces.ILogoutAction;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;

/**
 * Created by hufei on 2017/2/8.
 * 所有平台的注销
 */

public class LogoutAction implements ILogoutAction {

    private PlatformEnum platformEnum;

    public LogoutAction(PlatformEnum platformEnum) {
        this.platformEnum = platformEnum;
    }

    @Override
    public void logout() {
        PlatformInitConfig.logout(platformEnum);
    }
}

package cn.sharing8.blood_platform_widget.other;

import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.type.PlatformEnum;

/**
 * Created by hufei on 2017/2/9.
 * 默认的第三方登录回调
 */

public class SimpleLoginCallback<T> implements ILoginCallback<T> {

    @Override
    public void loginSuccess(PlatformEnum platformEnum, T callbackModel) {

    }

    @Override
    public void loginFail(PlatformEnum platformEnum, Object callbackModel) {

    }

    @Override
    public void loginCancel() {

    }
}

package cn.sharing8.blood_platform_widget.interfaces;

import cn.sharing8.blood_platform_widget.type.PlatformEnum;

/**
 * Created by hufei on 2017/1/24.
 * 第三方登录的统一回调,T为回调接收的数据Model
 */

public interface ILoginCallback<T> {

    void loginSuccess(PlatformEnum platformEnum, T callbackModel);

    void loginFail(PlatformEnum platformEnum, Object callbackModel);

    void loginCancel();
}

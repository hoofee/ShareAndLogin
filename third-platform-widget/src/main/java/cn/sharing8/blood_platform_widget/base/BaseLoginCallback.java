package cn.sharing8.blood_platform_widget.base;

import cn.sharing8.blood_platform_widget.interfaces.ILoginCallback;
import cn.sharing8.blood_platform_widget.type.ChannelEnum;

/**
 * Created by hufei on 2017/1/18.
 */

public class BaseLoginCallback<T> extends BaseCallback {

    protected ILoginCallback<T> loginCallback;

    public BaseLoginCallback(ChannelEnum channelEnum, ILoginCallback loginCallback) {
        super(channelEnum);
        this.loginCallback = loginCallback;
    }

    public ILoginCallback<T> getLoginCallback() {
        return loginCallback;
    }

    public void setLoginCallback(ILoginCallback<T> loginCallback) {
        this.loginCallback = loginCallback;
    }
}

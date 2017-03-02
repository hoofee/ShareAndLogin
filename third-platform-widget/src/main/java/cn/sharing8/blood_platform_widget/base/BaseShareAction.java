package cn.sharing8.blood_platform_widget.base;

import cn.sharing8.blood_platform_widget.ShareAction;

/**
 * Created by hufei on 2017/1/17.
 * 分享Action基类
 */

public class BaseShareAction {

    protected ShareAction shareAction;

    public BaseShareAction(ShareAction shareAction) {
        this.shareAction = shareAction;
    }

    public ShareAction getShareAction() {
        return shareAction;
    }

    public void setShareAction(ShareAction shareAction) {
        this.shareAction = shareAction;
    }
}

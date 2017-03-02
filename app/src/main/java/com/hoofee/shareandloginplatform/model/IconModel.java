package com.hoofee.shareandloginplatform.model;

import android.graphics.drawable.Drawable;

import cn.sharing8.blood_platform_widget.type.ChannelEnum;

/**
 * user hufei
 * date 2016/3/18:17:04
 * describe:首页图标和文字
 */
public class IconModel {
    public IconModel() {
    }

    public IconModel(Drawable iconDrawable, String iconText) {
        this.iconId = iconDrawable;
        this.iconText = iconText;
    }

    public IconModel(Drawable iconDrawable, String iconText, ChannelEnum channelEnum) {
        this.iconId = iconDrawable;
        this.iconText = iconText;
        this.channelEnum = channelEnum;
    }

    public Drawable    iconId;
    public String      iconText;
    public ChannelEnum channelEnum;
}

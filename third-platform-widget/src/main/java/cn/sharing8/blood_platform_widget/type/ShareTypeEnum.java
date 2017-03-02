package cn.sharing8.blood_platform_widget.type;

/**
 * Created by hufei on 2017/1/16.
 * 分享内容的类型
 */

public enum ShareTypeEnum {

    IMAGE_TEXT("image_text"),//图文
    TEXT("text"),//纯文字
    IMAGE("image"),//纯图片
    MUSIC("music"),//音乐
    VIDIO("vidio"),//视频
    WEBPAGE("webpage"),//网页
    APPLICATION("application");//应用

    private String tag;

    ShareTypeEnum(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

package cn.sharing8.blood_platform_widget.model;

import java.io.Serializable;
import java.util.ArrayList;

import cn.sharing8.blood_platform_widget.type.ShareTypeEnum;

/**
 * Created by hufei on 2017/1/16.
 * 分享的内容Model
 */

public class ShareModel implements Serializable {

    //分享的类型
    public ShareTypeEnum shareTypeEnum = ShareTypeEnum.WEBPAGE;

    public String shareUrl;//网页
    public String shareTitle;//最长30个字符
    public String shareContent;//最长50个字
    public String mediaUrl;//媒体url

    public String            shareImageUrl;//网络图片
    public ArrayList<String> shareImageListUrls;//多张网络图片
    public String            shareLocalImageUrl;//本地图片(长度最大512)

    //使用的阿里云样式
    public String imageUrlAlyStyle = "?x-oss-process=image/resize,m_fill,h_100,w_100";//网络图片的阿里云样式（固定宽高的剪切样式）//m_lfit(等比例)

    public ShareModel() {
    }

    public ShareModel(String shareContent, String shareTitle, String shareUrl, ShareTypeEnum shareTypeEnum, String shareImageUrl) {
        this.shareContent = shareContent;
        this.shareTitle = shareTitle;
        this.shareUrl = shareUrl;
        this.shareTypeEnum = shareTypeEnum;
        this.shareImageUrl = shareImageUrl;
    }

    @Override
    public String toString() {
        return "ShareModel{" +
                "imageUrlAlyStyle='" + imageUrlAlyStyle + '\'' +
                ", shareTypeEnum=" + shareTypeEnum +
                ", shareUrl='" + shareUrl + '\'' +
                ", shareTitle='" + shareTitle + '\'' +
                ", shareContent='" + shareContent + '\'' +
                ", mediaUrl='" + mediaUrl + '\'' +
                ", shareImageUrl='" + shareImageUrl + '\'' +
                ", shareImageListUrls=" + shareImageListUrls +
                ", shareLocalImageUrl='" + shareLocalImageUrl + '\'' +
                '}';
    }
}

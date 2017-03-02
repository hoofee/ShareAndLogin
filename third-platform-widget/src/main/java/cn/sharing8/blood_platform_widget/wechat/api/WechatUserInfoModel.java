package cn.sharing8.blood_platform_widget.wechat.api;

import java.io.Serializable;

/**
 * Created by hufei on 2017/2/6.
 * 微信登录后获取的信息Model
 */

public class WechatUserInfoModel implements Serializable {

    /**
     * openid : OPENID
     * nickname : NICKNAME
     * sex : 1
     * headimgurl : http://wx.qlogo.cn/mmopen/g3MonUZtNHeavHiaiceqxibJxCfHe/0
     * unionid :  o6_bmasdasdsad6_2sgVt7hMZOPfL
     * province : PROVINCE
     * city : CITY
     * country : COUNTRY
     */

    private String openid;
    private String nickname;
    private int    sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "WechatUserInfoModel{" +
                "city='" + city + '\'' +
                ", openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}

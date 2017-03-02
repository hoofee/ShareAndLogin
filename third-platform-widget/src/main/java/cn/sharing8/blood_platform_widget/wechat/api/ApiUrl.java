package cn.sharing8.blood_platform_widget.wechat.api;

/**
 * Created by hufei on 2017/2/6.
 * 微信的一些接口及说明
 */

public class ApiUrl {

    //获取Accesstoken  access_token有效期（目前为2个小时）
    public static String URL_GET_WECHAT_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    //刷新Accesstoken  refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权，所以，请开发者在refresh_token即将过期时（如第29天时），进行定时的自动刷新并保存好它。
    public static String URL_REFRESH_WECHAT_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";

    //检验授权凭证（access_token）是否有效
    public static String URL_VALID_ACCESSTOKEN = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";

    //获取用户个人信息
    public static String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
}

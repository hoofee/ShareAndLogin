package cn.sharing8.blood_platform_widget.model;

/**
 * Created by hufei on 2017/1/16.
 * 平台信息Model
 */

public class PlatformModel {

    private String appKeyId;
    private String appKeyValue;

    public PlatformModel(String appKeyId, String appKeyValue) {
        this.appKeyId = appKeyId;
        this.appKeyValue = appKeyValue;
    }

    public String getAppKeyId() {
        return appKeyId;
    }

    public void setAppKeyId(String appKeyId) {
        this.appKeyId = appKeyId;
    }

    public String getAppKeyValue() {
        return appKeyValue;
    }

    public void setAppKeyValue(String appKeyValue) {
        this.appKeyValue = appKeyValue;
    }


    public boolean isNull() {
        return appKeyId == null || appKeyValue == null;
    }

    public boolean notNull() {
        return appKeyId != null && appKeyValue != null;
    }
}

package cn.sharing8.blood_platform_widget.qq;

import com.tencent.tauth.UiError;

import cn.sharing8.blood_platform_widget.model.ShareCallbackModel;

/**
 * Created by hufei on 2017/1/16.
 * QQ分享回调数据
 */

public class QQShareCallbackModel extends ShareCallbackModel {

    private Object successData;

    private UiError errorModel;

    public QQShareCallbackModel() {
    }

    public QQShareCallbackModel(Object successData) {
        this.successData = successData;
    }

    public QQShareCallbackModel(UiError errorModel) {
        this.errorModel = errorModel;
    }

    public Object getSuccessData() {
        return successData;
    }

    public void setSuccessData(Object successData) {
        this.successData = successData;
    }

    public UiError getErrorCode() {
        return errorModel;
    }

    public void setErrorCode(UiError errorModel) {
        this.errorModel = errorModel;
    }

}

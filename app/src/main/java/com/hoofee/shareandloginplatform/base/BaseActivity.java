package com.hoofee.shareandloginplatform.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.tencent.tauth.Tencent;

import cn.sharing8.blood_platform_widget.qq.QQShareAction;

/**
 * Created by hufei on 2017/3/2.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //腾讯客户端的回调
        if (QQShareAction.callback != null) {
            Tencent.onActivityResultData(requestCode, resultCode, data, QQShareAction.callback);
            QQShareAction.callback = null;
        }
    }
}

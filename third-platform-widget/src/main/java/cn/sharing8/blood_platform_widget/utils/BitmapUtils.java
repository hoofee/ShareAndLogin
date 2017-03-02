package cn.sharing8.blood_platform_widget.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by hufei on 2017/1/18.
 * bitmap操作工具
 */

public class BitmapUtils {

    // Bitmap转byte[]
    public static byte[] Bitmap2Bytes(Bitmap bm, Bitmap.CompressFormat compressFormat, int photoQuality) {
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(compressFormat, photoQuality, baos);
        return baos.toByteArray();
    }
}
